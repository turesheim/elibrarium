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
		cssApplier.applyToRange(range);
	}
	
	function showSelection() {
		var selection = window.getSelection();
		if (selection.rangeCount > 0) {
			var serialization = rangy.serializeSelection();
			javaMarkTextHandler(serialization,selection.toString());
		}
	}
	document.onmouseup = showSelection;
	
	/***************************************************************************
	 * Initialise.
	 **************************************************************************/
	function injectCSS() {
	    var headTag = document.getElementsByTagName("head")[0].innerHTML;	
		var newCSS = headTag + '<style type="text/css">*.yellowMarker {background-color: yellow;}</style>';
		document.getElementsByTagName('head')[0].innerHTML += newCSS;
	 }
	injectCSS();
	/***************************************************************************
	 * Initialise.
	 **************************************************************************/
	bodyID.style.overflow = 'hidden';
	rangy.init();
	var cssApplier;
	cssApplier = rangy.createCssClassApplier("yellowMarker");
} catch (e) {
	document.write(e);
}