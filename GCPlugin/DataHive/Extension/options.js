let optionsUL = document.querySelector('.configurator ul.categorylist');

optionsUL.innerHTML = '';

GDPRConfig.getConsentTypes().then((consentTypes) => {
	consentTypes.forEach((consentType) => {
		addToggleItem(optionsUL, consentType.type, consentType.name, consentType.description, consentType.value)
			.querySelector('input')
			.addEventListener('input', function(evt) {
				if (this.checked) {
					this.parentNode.parentNode.classList.add('active');
				} else {
					this.parentNode.parentNode.classList.remove('active');
				}
				saveSettings();
			});
	});
});

function addToggleItem(parent, type, name, description, isChecked) {
	let optionLI = document.createElement('li');

	const optionHtml = `
        <label class="slider" for="${type}">
                <input type="checkbox" id="${type}" ${isChecked ? 'checked' : ''}>
                <div class="knob"></div>
        </label>
        <h2>${name}</h2>
        <div class="category_description">
${description}
		</div>`;

	let parser = new DOMParser();
	let parsed = parser.parseFromString(optionHtml, 'text/html');
	let children = parsed.querySelector('body');

	for (let child of children.childNodes) {
		optionLI.appendChild(child);
	}

	parent.appendChild(optionLI);
	optionLI.addEventListener('click', function(evt) {
		this.querySelector('input').click();
		evt.stopPropagation();
	});
	return optionLI;
}

function saveSettings() {
	let consentValues = {};
	optionsUL.querySelectorAll('li input').forEach((input) => {
		consentValues[input.id] = input.checked;
	});
	GDPRConfig.setConsentValues(consentValues);
}
