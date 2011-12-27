try {
	htmlID = document.getElementsByTagName('html')[0];
	bodyID = document.getElementsByTagName('body')[0];
	totalHeight = bodyID.offsetHeight;
	pageCount = Math.floor(totalHeight / desiredHeight) + 1;
	width = desiredWidth * pageCount;
	bodyID.style.width = width + 'px';
	bodyID.style.height = desiredHeight + 'px';
	bodyID.style.WebkitColumnCount = pageCount;

	/***************************************************************************
	 * Resize images so that their width is never larger than the width of the
	 * display.
	 **************************************************************************/
	function adjustImages() {
		var imgs, i;
		imgs = bodyID.getElementsByTagName('img');
		for (i = 0; i < imgs.length; i++) {
			imgs[i].style.maxWidth = (desiredWidth - 10) + 'px';
		}
	}
	adjustImages();

	/***************************************************************************
	 * Handling of range, selection and marked text.
	 **************************************************************************/
	function markText(serialized) {
		var range = rangy.deserializeRange(serialized);
		cssApplier.toggleRange(range);
	}
	function showSelection() {
		var selection = window.getSelection();
		if (selection.rangeCount > 0) {
			var serialization = rangy.serializeSelection();
			javaMarkTextHandler(serialization);
		}
	}
	document.onmouseup = showSelection;
	
	/***************************************************************************
	 * Initialise.
	 **************************************************************************/
	bodyID.style.overflow = 'hidden';
	rangy.init();
	var cssApplier;
	cssApplier = rangy.createCssClassApplier("_epub-marked_yellow");
} catch (e) {
	document.write(e);
}