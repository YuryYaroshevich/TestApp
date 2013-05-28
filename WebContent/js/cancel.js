$(document).ready(function() {
	$("#cancel").click(function() {
		var $form = $(this).parents("form");
		$form.attr('action', '/TestApp/news.do?method=cancel');
		$form.submit();
	});
});
