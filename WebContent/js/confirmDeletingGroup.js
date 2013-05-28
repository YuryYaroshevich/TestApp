function confirmDeletingGroup(obj) {
	if ($(":checked").length > 0) {
		return confirm(areYouSureMsg);
	} else {
		alert(noCheckedNewsMsg);
		return false;
	}
}