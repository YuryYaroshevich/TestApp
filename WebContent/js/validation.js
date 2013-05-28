$(document).ready(function() {
	$("#submit").click(function() {
		var $titleInput = $("input[name='title']");
		var $dateInput = $("input[name='dateOfPublishing']");
		var $briefTextArea = $("textarea[name='brief']");
		var $contentTextArea = $("textarea[name='content']");
		
		var res = isTitleValid($titleInput) &
	       isDateValid($dateInput) &
	       isBriefValid($briefTextArea) & 
	       isContentValid($contentTextArea);
		return res == 1 ? true : false;
	});
});

// Title validation
function isTitleValid(input) {
	return !(isTitleEmpty(input)) & !(isTitleTooLong(input));
}

function isTitleEmpty(input) {
	return isInputEmpty(input, titleEmptyMsg, 'empty-title');
}

function isTitleTooLong(input) {
	return isInputTooLong(input, 100, titleTooLongMsg, 'too-long-title');
}

// Brief validation
function isBriefValid(input) {
	return !(isBriefEmpty(input)) & !(isBriefTooLong(input));
}

function isBriefEmpty(input) {
	return isInputEmpty(input, briefEmptyMsg, 'empty-brief');
}

function isBriefTooLong(input) {
	return isInputTooLong(input, 500, briefTooLongMsg, 'too-long-brief');
}

// Content validation
function isContentValid(input) {
	return !(isContentEmpty(input)) & !(isContentTooLong(input));
}

function isContentEmpty(input) {
	return isInputEmpty(input, contentEmptyMsg, 'empty-content');
}

function isContentTooLong(input) {
	return isInputTooLong(input, 2048, contentTooLongMsg, 'too-long-content');
}

// Date validation
function isDateValid(input) {
	return !(isDateEmpty(input)) & isDateFormatCorrect(input, dateWrongFormatMsg, 'wrong-date');
}

function isDateEmpty(input) {
	return isInputEmpty(input, dateEmptyMsg, 'empty-date');
}

function isDateFormatCorrect(input, errorMessage, errorMessageId) {
	var date = input.val();
	if (date == '') {
		return false;
	}
	if (!/\d{2}\/\d{2}\/\d{4}/.test(date)) {
		return reportError(errorMessage, errorMessageId);
	}
	var numberPattern = /\d+/g;
	var dateParts = date.match(numberPattern);
	var month = dateParts[0];
	if (1 > month || month > 12) {
		return reportError(errorMessage, errorMessageId);
	}
	var year = dateParts[3];
	if (year < 1) {
		return reportError(errorMessage, errorMessageId);
	}
	var day = dateParts[1];
	if (!isDayFitToMonth(year, month, day)) {
		return reportError(errorMessage, errorMessageId);
	}
	return dateIsValid(errorMessageId);
}

function isDayFitToMonth(year, month, day) {
	if (month == 1 || month == 3 || month == 5
			|| month == 7 || month == 8 || month == 10
			|| month == 12) {
		if (1 <= day && day <= 31) {
			return true;
		}
	}
	if (month == 4 || month == 6 || month == 9
			|| month == 11) {
		if (1 <= day && day <= 30) {
			return true;
		}
	}
	if (year % 4 == 0 && month == 2) {
		if (1 <= day && day <= 29) {
			return true;
		}
	}
	if (month == 2) {
		if (1 <= day && day <= 28) {
			return true;
		}
	}
	return false;
}

function reportError(errorMessage, errorMessageId) {
	if (!isErrorMessageExist(errorMessageId)) {
		var $errorList = prepareErrorList();
		$('<li id="' + errorMessageId + '"></li>')
		   .text(errorMessage)
		   .addClass("error-message")
		   .appendTo($errorList);
	}
	return false;
}

function dateIsValid(errorMessageId) {
	$('#' + errorMessageId).remove();
	return true;
}