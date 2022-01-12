chrome.tabs.query(
	{
		active: true,
		currentWindow: true
	},
	(tabs) => {
		let url = tabs[0].url;
		url = url.substring(url.indexOf('://') + 3, url.indexOf('/', 8));

		let activeInput = document.querySelector('.siteselector input');

		document.querySelector('#site').textContent = url;
		document.querySelector('#settings').addEventListener('click', function() {
			chrome.runtime.openOptionsPage();
		});

		document.querySelector('#history').addEventListener('click', () => {
			window.open('/HistoryPage/history.html', '_blank');
		});

		GDPRConfig.isActive(url).then((active) => {
			activeInput.checked = active;
		});

		activeInput.addEventListener('input', () => {
			GDPRConfig.setPageActive(url, activeInput.checked);
		});

		document.querySelector('#unhandled').addEventListener('click', () => {
			if (confirm('Report that cookie handling did not work in this url: ' + url)) {
				fetch('http://localhost:8080/not_working_website', {
					method: 'PUT',
					headers: { 'Content-Type': 'application/json' },
					body: JSON.stringify({ url: url, date: Date.now() })
				})
					.then((response) => response.json())
					.then((data) => {
						console.log('Success:', data);
					})
					.catch((error) => {
						console.log('Error:', error);
					});
				window.close();
			}
		});
	}
);
