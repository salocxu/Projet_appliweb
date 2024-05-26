async function login(event) {
    event.preventDefault();

    let form = document.querySelector('form');
    let email = form.elements["email"].value;
    let password = form.elements["password"].value;

    let data = {
        mail: email,
        mdp: password
    };

    console.log("Sending data to server:", data);

    try {
        let response = await post('http://localhost:8080/PollHub/rest/login/connexion', data);
        console.log("Response from server:", response);
        if (response.ok) {
            let responseData = await response.json();
            displayMessage(`Bonjour ${responseData.prenom} ${responseData.nom}`, 'success');
        } else {
            displayMessage('Identifiants incorrects ou compte inexistant.', 'error');
        }
    } catch (error) {
        console.error("Error during fetch:", error);
        displayMessage('Une erreur s\'est produite. Veuillez réessayer.', 'error');
    }
}

function displayMessage(message, type) {
    let alertElement = document.getElementById('alert');
    alertElement.classList.remove('success', 'error');
    alertElement.innerHTML = message;
    alertElement.classList.add(type);
}

function post(url, data) {
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
}
