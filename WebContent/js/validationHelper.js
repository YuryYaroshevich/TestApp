function isInputTooLong($input, maxLength,
		errorMessage, errorMessageId) {
	if ($input.val().length > maxLength) {
		if (!isErrorMessageExist(errorMessageId)) {
			var $errorList = prepareErrorList();
			$('<li id="' + errorMessageId + '"></li>')
			   .text(errorMessage)
			   .addClass("error-message")
			   .appendTo($errorList);
		} 
		return true;
	} else {
		$("#" + errorMessageId).remove();
		return false;
	}
}

function isInputEmpty($input, errorMessage, errorMessageId) {	
	if ($input.val() == '') {
		if (!isErrorMessageExist(errorMessageId)) {
			var $errorList = prepareErrorList();
			$('<li id="' + errorMessageId + '"></li>')
			   .text(errorMessage)
			   .addClass("error-message")
			   .appendTo($errorList);
		}
		return true;
	} else {
		$('#' + errorMessageId).remove();
		return false;
	}
}

function isErrorMessageExist(messageId) {
	return $('#' + messageId).length > 0;
}

function prepareErrorList() {
	var $errorList;
	if (isErrorListExist()) {
		$errorList = $("ul#error-list");
	} else {
		$errorList = $("<ul id='error-list'></ul>");
		$errorList.insertAfter("#content-head");
	}
	return $errorList;
}

function isErrorListExist() {
	return $("#error-list").length > 0;
}