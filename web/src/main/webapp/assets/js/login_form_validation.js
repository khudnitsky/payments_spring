function validateForm()
{
	var flag = true;
	var messageLogin = document.getElementById("emptyLogin");
	var messagePassword = document.getElementById("emptyPassword");
	var login = document.getElementById("login").value;
	var password = document.getElementById("password").value;

	var text = "" + "<br>";

	if(getCookie('LocaleCookie') == 'en'){
		text = "Field is empty";
	}
	else {
		text = "Поле не заполнено";
	}

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

function getCookie(name){
	var matches = document.cookie.match(new RegExp("(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"));
	return matches ? decodeURIComponent(matches[1]) : undefined;
}