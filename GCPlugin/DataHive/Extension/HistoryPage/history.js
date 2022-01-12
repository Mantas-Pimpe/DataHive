let history;
getHistoryFillHistoryTable();

function setup(history) {
	setupDeleteAllButton(history);
	fillHistoryTable(history);
}

function setupDeleteAllButton() {
	document.querySelector('#deleteAllButton').addEventListener('click', () => {
		for (var i = 0; i < history.length; i++) {
			clearCacheForWebsite(history[i]);
		}
	});
}

function fillHistoryTable(history) {
	$('#historyTable tbody tr').remove();
	for (var i = 0; i < history.length; i++) {
		buildRow(history[i], i);
	}
}

function getHistoryFillHistoryTable() {
	fetch('http://localhost:8080/history/search/findByUserId?userId=1&sort=date', {
		// WHEN LOGIN CREATED, insert logged in user
		method: 'GET',
		headers: { 'Content-Type': 'application/json' }
	})
		.then((response) => response.json())
		.then((data) => {
			history = data._embedded.history;
			//console.log('Success:', data._embedded.history);
			setup(history);
		})
		.catch((error) => {
			console.log('Error:', error);
		});
}

function buildRow(row, index) {
	var row$ = $('<tr/>');
	let dateTimeString = row.date.substring(0, 10) + ' ' + row.date.substring(11, 16);
	row$.append($('<td/>').html('<a target="_blank" href=https://' + row.url + '>' + row.url + '</a>'));
	row$.append($('<td/>').html(row.cmp));
	row$.append($('<td/>').html(dateTimeString));
	row$.append($('<td/>').html(getDeleteButtonHTML(index)));
	$('#historyTable').append(row$);
	setupDeleteButtonListener(row, index);
}

function getDeleteButtonHTML(index) {
	return (
		'<button class="button" id="delete_button-' +
		index +
		'"><img src="./../Resources/trash-can.png" height="20" width="20"></button>'
	);
}

function setupDeleteButtonListener(row, index) {
	document.querySelector('#delete_button-' + index).addEventListener('click', () => {
		clearCacheForWebsite(row);
	});
}

function clearCacheForWebsite(row) {
	let websiteUrl = 'https://' + row.url;
	var callback = function() {
		fetch('http://localhost:8080/history/' + row.id, {
			method: 'DELETE',
			headers: { 'Content-Type': 'application/json' }
		})
			.then((res) => res.text())
			.then((data) => {
				//console.log('Success:', data);
				getHistoryFillHistoryTable();
			})
			.catch((error) => {
				console.log('Error:', error);
			});
	};

	chrome.browsingData.remove(
		{
			origins: [ websiteUrl ]
		},
		{
			cacheStorage: true,
			cookies: true,
			fileSystems: true,
			indexedDB: true,
			localStorage: true,
			serviceWorkers: true,
			webSQL: true
		},
		callback
	);
}
