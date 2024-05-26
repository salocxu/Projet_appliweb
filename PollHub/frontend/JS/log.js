async function login(event) {

	console.log("hello");
	
	event.preventDefault();
	let data = {
		mail  : form.element["email"].value,
		mdp : form.element["mdp"].value,
		
	};

	console.log(data);
	
	post('http://localhost:8080/PollHub/rest/login/creation',data).then(data => {
		alertElement.classList.remove('succes','error');
		alertElement.innerHTML = data.messahe;
		console.log(data);
		
		if (data.succes) {
			alertElement.classList.add('success');
		}
		else {
			alertElement.classList.add('error');
		}
	})
}