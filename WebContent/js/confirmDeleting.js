$(document).ready(function() {
	$("#deletingForm").submit(function() {
		return confirm(areYouSureMsg);
	});
});

