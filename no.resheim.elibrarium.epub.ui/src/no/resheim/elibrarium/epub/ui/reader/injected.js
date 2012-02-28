try {
	htmlID = document.getElementsByTagName('html')[0];
	bodyID = document.getElementsByTagName('body')[0];
	totalHeight = bodyID.offsetHeight;
	pageCount = Math.floor(totalHeight / desiredHeight) + 1;
	width = desiredWidth * pageCount;
	pageWidth = width/pageCount;
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
	
	/**
	 * Sets the left offset of the body to match the one of the identifier. This
	 * is done so that a complete page is shown.
	 * 
	 * @param identifier the element identifier
	 */
	function setOffsetToElement(identifier){
		var p = $('#'+identifier);
		if (p.length>0){
			var offset = p.offset();
			var page = Math.floor(offset.left/pageWidth);
			bodyID.scrollLeft = page*pageWidth;
		}
	}
	/**
	 * Marks the text identified by the serialised range and assigns it an
	 * element with the given identifier.
	 * 
	 * @param serialized the serialised range
	 * @param identifier the identifier to assign the range
	 */
	function markRange(serialized, identifier) {
		if ($('#'+identifier).length==0){			
			var selection = rangy.deserializeSelection(serialized);
			cssApplier.applyToSelection();
			insertHtmlBeforeSelection("<span id='"+identifier+"'/>");
		} else {
			alert('Mark already exists')
		}
	}
	
	var insertHtmlBeforeSelection, insertHtmlAfterSelection;
	(function() {
	    function createInserter(isBefore) {
	        return function(html) {
	            var sel, range, node;
	            if (window.getSelection) {
	                // IE9 and non-IE
	                sel = window.getSelection();
	                if (sel.getRangeAt && sel.rangeCount) {
	                    range = window.getSelection().getRangeAt(0);
	                    range.collapse(isBefore);

	                    // Range.createContextualFragment() would be useful here but is
	                    // non-standard and not supported in all browsers (IE9, for one)
	                    var el = document.createElement("div");
	                    el.innerHTML = html;
	                    var frag = document.createDocumentFragment(), node, lastNode;
	                    while ( (node = el.firstChild) ) {
	                        lastNode = frag.appendChild(node);
	                    }
	                    range.insertNode(frag);
	                }
	            } else if (document.selection && document.selection.createRange) {
	                // IE < 9
	                range = document.selection.createRange();
	                range.collapse(isBefore);
	                range.pasteHTML(html);
	            }
	        }
	    }

	    insertHtmlBeforeSelection = createInserter(true);
	    insertHtmlAfterSelection = createInserter(false);
	})();
	
	/***************************************************************************
	 * Unmark the text within the Rangy range.
	 **************************************************************************/	
	function removeMark(serialized) {
		cssApplier.undoToSelection();			
	}	

	/***************************************************************************
	 * Use Java code to mark the selected range.
	 **************************************************************************/	
	function showSelection(e) {
		var selection = window.getSelection();
		var serialization = rangy.serializeSelection();
		var yellow = cssApplier.isAppliedToSelection()
		javaMarkTextHandler(serialization,selection.toString(),yellow);
	}
	document.onmouseup = showSelection;
	
	/***************************************************************************
	 * Inject CSS for markers and annotations.
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