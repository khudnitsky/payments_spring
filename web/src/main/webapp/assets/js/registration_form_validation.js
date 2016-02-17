function validateForm()
{
	var flag = true;
	var messageFirstName = document.getElementById("emptyFirstName");
	var messageLastName = document.getElementById("emptyLastName");
	var messageLogin = document.getElementById("emptyLogin");
	var messagePassword_1 = document.getElementById("emptyPassword_1");
	var messagePassword_2 = document.getElementById("emptyPassword_2");
	var messageAccountId = document.getElementById("emptyAccountId");
	var message = document.getElementById("inputError");
	
	var firstName = document.getElementById("firstName").value;
	var lastName = document.getElementById("lastName").value;
	var login = document.getElementById("login").value;
	var password_1 = document.getElementById("password_1").value;
	var password_2 = document.getElementById("password_2").value;
	var accountId = document.getElementById("accountId").value;
	
	var point = "*";
	var emptyField = "Поле не заполнено";
	var notEqualsPasswords = "Пароли не совпадают";
	var invalidSymbols = "Недопустимые символы ";

	var templateNames = /^[a-zA-Zа-яА-Я ]{2,30}$/;
	var templatePassword = /\w*/g;
	var templateAccountId = /^[0-9]{1,10}$/;

	// проверка заполненности полей
	if (firstName == ""){
		messageFirstName.innerHTML = point;
		message.innerHTML = emptyField;
		flag = false;
	}
	else{
		// проверка валидности данных
		if (!templateNames.exec(firstName)){
			messageFirstName.innerHTML = point;
			message.innerHTML = invalidSymbols + "(только буквы)";
			flag = false;	
		}
		else{
			messageFirstName.innerHTML = "";
		}
	}
	if (lastName == ""){
		messageLastName.innerHTML = point;
		message.innerHTML = emptyField;
		flag = false;
	}
	else{
		// проверка валидности данных
		if (!templateNames.exec(lastName)){
			messageLastName.innerHTML = point;
			message.innerHTML = invalidSymbols + "(только буквы)";
			flag = false;	
		}
		else{
			messageLastName.innerHTML = "";
		}
	}		
	if (login == ""){
		messageLogin.innerHTML = point;
		message.innerHTML = emptyField;
		flag = false;
	}
	else{
		messageLogin.innerHTML = "";
	}
	if (password_1 == ""){
		messagePassword_1.innerHTML = point;
		message.innerHTML = emptyField;;
		flag = false;
	}
	else{
		messagePassword_1.innerHTML = "";
	}
	if (password_2 == ""){
		messagePassword_2.innerHTML = point;
		message.innerHTML = emptyField;
		flag = false;
	}
	else{
		// проверка совпадения паролей
		if (password_1 != templatePassword.exec(password_2)){
			messagePassword_2.innerHTML = point;
			message.innerHTML = notEqualsPasswords;
			flag = false;	
		}
		else{
			messagePassword_2.innerHTML = "";
		}
	}
	if (accountId == ""){
		messageAccountId.innerHTML = point;
		message.innerHTML = emptyField;
		flag = false;
	}
	else{
		// проверка валидности данных
		if (!templateAccountId.exec(accountId)){
			messageAccountId.innerHTML = point;
			message.innerHTML = invalidSymbols + "(только цифры)";
			flag = false;	
		}
		else{
			messageAccountId.innerHTML = "";
		}
	}
	
	return flag;
}