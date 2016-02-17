function validateForm()
{
	var flag = true;
	var messageLogin = document.getElementById("emptyLogin");
	var messagePassword = document.getElementById("emptyPassword");
	var login = document.getElementById("login").value;
	var password = document.getElementById("password").value;
	var text = "Поле не заполнено" + "<br>";

	// проверка заполненности полей
	if (login == ""){
		messageLogin.innerHTML = text;
		flag = false;
	}
	else{
		messageLogin.innerHTML = "";
	}
	if (password == ""){
		messagePassword.innerHTML = text;
		flag = false;
	}
	else{
		messagePassword.innerHTML = "";
	}
	return flag;
}