class FormAction {
    form;
    constructor(formId) {
        this.form = document.getElementById(formId);
    }

    async #getData(url) {
        try {
            const response = await fetch(url);

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();

            if (data.status === "error") {
                errorToast(data);
                return null;
            }
            return data.data;
        } catch (error) {
            console.error("Ошибка при получении данных:", error);
            errorToast({ message: error.message });
            return null;
        }
    }


    async postForm() {
        try {
            let formData = new FormData(this.form);
            let requestData = new URLSearchParams();
            formData.forEach( (value, name) => {requestData.append(name, value)})
            console.log(requestData.toString());
            let response = await (await fetch('/api/v1' + this.form.getAttribute('action'), {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: requestData.toString()
            })).json();
            if (response.status == "error") {
                errorToast(response);
                return null;
            }
            else
                return response;
        } catch (error) {
        console.loh(error.message)
            errorToast({message: error.message})
        }
    }

    async updateCartridges() {
        try {
            let uriState = "";
            if (this.form.getAttribute('state')) {
                let arrayState = this.form.getAttribute('state').split(',');
                arrayState.forEach((elem, index) => {
                    if (index == 0) {uriState += '?state=' + elem; return;}
                    uriState += '&state=' + elem;
                });
            }
            if(this.form.getAttribute('unique')) {
                uriState+= "&unique=1";
            }
            let dataCartridges = await this.#getData(`/api/v1/cartridges${uriState}`);
            if (dataCartridges == null) {
                console.log("Пустой ответ для объекта:", this);
                return;
            }

            let selectCartridge = this.form.querySelector('.cartridge');
            selectCartridge.selectize.clear();
            while (selectCartridge.firstChild) {
                selectCartridge.removeChild(selectCartridge.firstChild);
            }
            dataCartridges.forEach(item => {
                selectCartridge.selectize.addOption({value: item.id, text:item.name})
            });
            selectCartridge.selectize.refreshOptions(false);
        } catch (error) {
            console.error("Ошибка при обновлении картриджей:", error);
        }
    }

    async updateOffices() {
        let dataOffices = await this.#getData('/api/v1/offices')
        let selectOffice = this.form.querySelector('.office');
        selectOffice.childNodes.forEach( child => {selectOffice.removeChild(child)})
        selectOffice.selectize.clear();
        dataOffices.forEach(item => {
            selectOffice.selectize.addOption({value: item.id, text:item.name})
        });
        selectOffice.selectize.refreshOptions(false);
    }

}

class FormWithoutCartridges extends FormAction {
    updateCartridges() {}
}

class FormWithoutOffices extends FormAction {
    updateOffices() {}
}

class FormReturnIntoRefuel extends FormWithoutOffices {
    async postForm() {
        try {
            let formData = new FormData(this.form);
            let requestData = new URLSearchParams();
            formData.forEach( (value, name) => {requestData.append(name, value)})
            console.log(requestData.toString());
            let response = await (await fetch('/api/v1' + this.form.getAttribute('action'), {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: requestData.toString()
            })).json();
            if (response.status == "success" && document.querySelector('.deleteIntoQueue')) {
                console.log("moveToQueue");
                let oldNavLink = document.getElementById('operations-tab');
                let newNavLink = document.getElementById('queue-tab');
                let oldTabPane = document.getElementById('operations');
                let newTabPane = document.getElementById('queue');

                oldNavLink.classList.remove('show', 'active');
                oldNavLink.setAttribute('aria-selected', 'false');
                oldTabPane.classList.remove('show', 'active');

                newNavLink.classList.add("active");
                newNavLink.setAttribute('aria-selected', 'true');
                newTabPane.classList.add('show', 'active');
            }
            if (response.status == "error") {
                errorToast(response);
                return null;
            }
            else
                return response;
        } catch (error) {
            errorToast({message: error.message})
        }
    }
}

class FormQueue extends FormAction {

    constructor(form) {
        super(form);
        this.form = form;
    }

    async postForm() {
    try {
        let formData = new FormData(this.form);
        let requestData = new URLSearchParams();
        formData.forEach( (value, name) => {requestData.append(name, value)})
        console.log(requestData.toString());
        let response = await (await fetch('/api/v1' + this.form.getAttribute('action'), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: requestData.toString()
        })).json();
        if (!response.status == "error")
            this.form.parentNode.parentNode.remove();

        return response;
        } catch (error) {
            errorToast({message: error.message})
        }
    }
}

const installedForm = new FormAction('installed');
const deinstalledForm = new FormAction('deinstalled');
const sendToRefuel = new FormWithoutOffices('sendToRefuel');
const returnFromRefuel = new FormReturnIntoRefuel('returnFromRefuel');
const addToQueue = new FormAction('addToQueue');

const addCartridges = new FormWithoutOffices('addCartridge');
const deleteCartridges = new FormWithoutOffices('deleteCartridge');
const addOffices = new FormWithoutCartridges('addOffice');
const deleteOffices = new FormWithoutCartridges('deleteOffice');

const updateCartridgeInterface =
    [installedForm, deinstalledForm,sendToRefuel,
     returnFromRefuel, addToQueue, addCartridges,
     deleteCartridges];

const updateOfficesInterface =
    [installedForm, deinstalledForm,sendToRefuel,
     returnFromRefuel, addToQueue, addOffices,
     deleteOffices];

let formsList =
    [installedForm, deinstalledForm,sendToRefuel,
    returnFromRefuel, addToQueue, addCartridges,
    deleteCartridges, addOffices, deleteOffices];

let arrayNodesFormQueue = Array.from(document.querySelectorAll('.deleteIntoQueue'));

let queueList = arrayNodesFormQueue.map((item) => {console.log(item); return new FormQueue(item)});
formsList = formsList.concat(queueList);

formsList.forEach(async (item) => {
    await item.updateCartridges();
    await item.updateOffices();
    console.log(item);
    item.form.addEventListener("submit", async (event) => {
        try {
        event.preventDefault();
        let response = await item.postForm();
        console.log(response);
        if (response != null) {
            successToast();
            updateCartridgeInterface.map(async item => await item.updateCartridges());
            updateOfficesInterface.map(async item => await item.updateOffices());
        }
        } catch (e) {
            errorToast({message: e.message});
        }
    });
});

const successToast = (() => {
    let iterator = 0;

    return function() {
        const toast = document.createElement('div');
        toast.id = `toast${iterator}`;
        toast.className = "toast bg-success show";
        toast.role = "alert";
        toast.setAttribute("aria-live", "assertive");
        toast.setAttribute("aria-atomic", "true");

        const toastHeader = document.createElement('div');
        toastHeader.className = "toast-header";

        const strong = document.createElement('strong');
        strong.className = "me-auto";
        strong.textContent = "Успешно";

        const closeButton = document.createElement('button');
        closeButton.id = `${iterator}close-toast`;
        closeButton.type = "button";
        closeButton.className = "btn-close";
        closeButton.setAttribute("data-bs-dismiss", "toast");
        closeButton.setAttribute("aria-label", "Close");

        toastHeader.appendChild(strong);
        toastHeader.appendChild(closeButton);

        toast.appendChild(toastHeader);

        document.getElementById('toast').appendChild(toast);

        closeButton.addEventListener('click', (event) => closeHandler(event));

        iterator += 1;

        setTimeout(() => {
            toast.remove();
        }, 3000)
    };
})();

const errorToast = (() => {
    let iterator = 0;
    return function(response) {
    const toast = document.createElement('div');
    toast.id = `${iterator}toast`;
    toast.className = "toast bg-danger show";
    toast.role = "alert";
    toast.setAttribute("aria-live", "assertive");
    toast.setAttribute("aria-atomic", "true");
    toast.setAttribute("data-delay", "1000");
    toast.setAttribute("data-bs-autohide", "true");

    const toastHeader = document.createElement('div');
    toastHeader.className = "toast-header";

    const strong = document.createElement('strong');
    strong.className = "me-auto";
    strong.textContent = "Ошибка";

    const closeButton = document.createElement('button');
    closeButton.type = "button";
    closeButton.className = "btn-close";
    closeButton.setAttribute("data-bs-dismiss", "toast");
    closeButton.setAttribute("aria-label", "Close");

    toastHeader.appendChild(strong);
    toastHeader.appendChild(closeButton);

    const toastBody = document.createElement('div');
    toastBody.className = "toast-body";
    toastBody.textContent = response.message;

    toast.appendChild(toastHeader);
    toast.appendChild(toastBody);

    document.getElementById('toast').appendChild(toast);

    closeButton.addEventListener('click', (event) => closeHandler(event));
    iterator += 1;
    }
})();

document.querySelectorAll(".btn-close").forEach( elem => {
    elem.addEventListener("click" ,(event) => closeHandler(event));
})

function closeHandler(event) {
    event.target.parentNode.parentNode.remove();
}