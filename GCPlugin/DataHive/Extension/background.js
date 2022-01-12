chrome.runtime.onMessage.addListener(function(message, sender, reply) {
	switch (message.split('|')[0]) {
		case 'GetTabUrl': {
			reply(sender.tab.url);
			return Promise.resolve('Response already sent...');
		}

		case 'GetRuleList': {
			GDPRConfig.getDebugValues().then((debugValues) => {
				fetchRules(debugValues.alwaysForceRulesUpdate).then((rules) => {
					reply(rules);
				});
			});
			//Return true to keep reply working after method has ended, async response
			return true;
		}

		case 'GetCustomRuleList': {
			GDPRConfig.getCustomRuleLists().then((customRules) => {
				reply(customRules);
			});
			//Return true to keep reply working after method has ended, async response
			return true;
		}

		case 'AddCustomRule': {
			let newRule = JSON.parse(message.substring(message.indexOf('|') + 1));

			GDPRConfig.getCustomRuleLists().then((customRules) => {
				let combinedCustomRules = Object.assign({}, customRules, newRule);

				GDPRConfig.setCustomRuleLists(combinedCustomRules);
			});

			return false;
		}

		case 'DeleteCustomRule': {
			let deleteRule = message.split('|')[1];

			GDPRConfig.getCustomRuleLists().then((customRules) => {
				delete customRules[deleteRule];

				GDPRConfig.setCustomRuleLists(customRules).then(() => {
					reply();
				});
			});

			return true;
		}

		case 'HandledCMP': {
			let json = JSON.parse(message.split('|')[1]);

			setBadgeCheckmark(true, sender.tab.id);

			GDPRConfig.getLogEntries().then((log) => {
				let data = {
					url: json.url,
					userId: 1, // WHEN LOGIN CREATED, insert logged in user
					cmp: json.cmp,
					date: Date.now()
				};

				fetch('http://localhost:8080/history', {
					method: 'PUT',
					headers: { 'Content-Type': 'application/json' },
					body: JSON.stringify(data)
				})
					.then((response) => response.json())
					.then((data) => {
						console.log('Success:', data);
					})
					.catch((error) => {
						console.log('Error:', error);
					});
			});

			return false;
		}

		default:
			console.log('Unhandled message:', message);
	}
});

function setBadgeCheckmark(enabled, id) {
	let text = '';

	if (enabled) {
		text = '✓';
	}

	chrome.browserAction.setBadgeText({
		text: text,
		tabId: id
	});
}

function fetchRules(forceUpdate) {
	// Make sure the cached rule-lists are up-to-date, fetch updates if needed
	let maxStaleness = 60 * 15; // Fetch frequency in seconds
	let rulePromise = new Promise((resolve, reject) => {
		GDPRConfig.getRuleLists().then((ruleLists) => {
			chrome.storage.local.get({ cachedEntries: {} }, async function({ cachedEntries: cachedEntries }) {
				let rules = [];
				for (let ruleList of ruleLists) {
					let entry = cachedEntries[ruleList];

					// Check for cache
					if (
						!forceUpdate &&
						(entry != null &&
							'timestamp' in entry &&
							Date.now() / 1000 - entry.timestamp < maxStaleness &&
							'rules' in entry)
					) {
						rules.push(entry.rules);
					} else {
						// No cache, or to old, try to fetch
						try {
							let response = await fetch(ruleList, { cache: 'no-store' });
							let theList = await response.json();
							let storedEntry = {};
							rules.push(theList);
							cachedEntries[ruleList] = {
								timestamp: Date.now() / 1000,
								rules: theList
							};
						} catch (e) {
							console.warn('Error fetching rulelist: ', ruleList, e.message);

							//Reuse cached entry even though its out of date at this point
							if (entry != null) {
								rules.push(entry.rules);
							}
						}
					}
				}

				chrome.storage.local.set(
					{
						cachedEntries: cachedEntries
					},
					() => {
						resolve(rules);
					}
				);
			});
		});
	});

	return rulePromise;
}

let tabsInfo = new Map();

chrome.tabs.onUpdated.addListener((tabId, info, tab) => {
	if (info.status != null && info.status === 'Loading') {
		setBadgeCheckmark(false, tabId);
	}
});
