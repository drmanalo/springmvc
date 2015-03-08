/**
 * Gives a confirm message before executing delete
 * 
 * @param url
 */
function deleteRow(url) {
	var isOK = confirm("Confirm delete?");
	if (isOK) {
		window.location = url;
	}
}