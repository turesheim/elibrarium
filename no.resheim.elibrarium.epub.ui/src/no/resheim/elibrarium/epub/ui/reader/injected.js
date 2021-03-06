try {
	htmlID = document.getElementsByTagName('html')[0];
	bodyID = document.getElementsByTagName('body')[0];
	totalHeight = bodyID.offsetHeight;
	pageCount = Math.floor(totalHeight / desiredHeight) + 1;
	width = desiredWidth * pageCount;
	pageWidth = (width / pageCount);
	bodyID.style.width = width + 'px';
	bodyID.style.height = desiredHeight + 'px';
	bodyID.style.webkitColumnGap = 0; /* Bug #15 */
	bodyID.style.WebkitColumnCount = pageCount;
	markedText = '';
	debugging = false;

	/**
	 * Resizes images so their width is never larger than the width of the 
	 * viewport.
	 */
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
	 * is done so that the full width of the page is shown instead of part of
	 * two pages.
	 * 
	 * @param identifier the element identifier
	 * @returns the page number of the element
	 */
	function setOffsetToElement(identifier) {
		var p = $('#' + identifier);
		if (p.length > 0) {
			var offset = p.offset();
			var page = Math.floor(offset.left / pageWidth);
			$('body').scrollLeft(page * pageWidth);
			return page;
		} else {
			alert('Could not find element with identifier #'+identifier+'. This most likely a bug.'); 
		}
	}

	/**
	 * Adjust the scroll position of the body so that the current page is in 
	 * view. Returns the text of the first H1 element. 
	 * 
	 * @param page the page number
	 */
	function navigateToPage(page) {
		bodyID.scrollLeft =+ (pageWidth * (page - 1));
	}

	/**
	 * Returns the title of the chapter. If there are m
	 * @returns
	 */
	function getChapterTitle() {
		var elem = $('h1').filter(function() {
			return ($(this).offset().left <= bodyID.scrollLeft + pageWidth);
		});
		var h1 = elem.last();
		return h1.text();
	}

	/**
	 * Returns a title suitable for use in a bookmark.
	 * 
	 * @returns a bookmark title
	 */
	function getBookmarkTitle() {
		var elem = $('h1,h2,h3,h4').filter(function() {
			return ($(this).offset().left <= bodyID.scrollLeft + pageWidth);
		});
		var h = elem.last();
		return h.text();
	}

	/**
	 * Creates a range containing the centermost visible element.  
	 * 
	 * @returns the serialised range
	 */
	function getPageLocation() {
		var elem = $('p,h1,h2,h3,h4,h5,h6,pre,tr,img,b,i,code,q,a')
				.filter(
						function() {
							return (($(this).offset().left >= bodyID.scrollLeft) && ($(
									this).offset().left < bodyID.scrollLeft
									+ pageWidth));
						});
		if (elem != null) {
			var range = rangy.createRangyRange();
			var pos = 0;
			if (elem.length > 1) {
				pos = Math.ceil(elem.length / 2) - 1;
			}
			range.startContainer = elem[pos];
			range.endContainer = elem[pos];
			range.startOffset = 0;
			range.endOffset = 0;
			var serialized = rangy.serializeRange(range, true, document);
			range.detach();
			return serialized;
		}
	}
	/**
	 * Creates a range encompassing the entire page view. It includes the 
	 * beginning of the first and last elements shown.
	 */
	function getPageRange() {
		var elem = $('p,h1,h2,h3,h4,h5,h6,pre,tr,img,b,i,code,q,a')
				.filter(
						function() {
							return (($(this).offset().left >= bodyID.scrollLeft) && ($(
									this).offset().left < bodyID.scrollLeft
									+ pageWidth));
						});
		if (elem != null) {
			var range = rangy.createRangyRange();
			range.startContainer = elem[0];
			range.endContainer = elem[elem.length - 1];
			range.startOffset = 0;
			range.endOffset = 0;
			return range;
		}
	}
	/**
	 * Determine whether or not the given serialized range intersects with
	 * the range that is currently visible.
	 * 
	 * @param serialized the serialized range
	 * @returns <code>True</code> if intersecting
	 */
	function intersects(serialized) {
		if (!rangy.initialized) {
			rangy.init();
		}
		if (rangy.canDeserializeRange(serialized, document)) {
			try {
				var bookmark = rangy.deserializeRange(serialized, document);
				var current = getPageRange();
				return bookmark.intersectsRange(current);
			} catch (error) {
				//alert(error);
			}
		}
		return false;
	}

	/**
	 * Navigates to the given location.
	 * 
	 * @param serialized the serialized location
	 */
	function navigateToLocation(serialized) {
		if (!rangy.initialized) {
			rangy.init();
		}
		range = rangy.deserializeRange(serialized, document);
		var id = range.startContainer.id;
		range.startContainer.id = 'bookmark';
		setOffsetToElement('bookmark');
		range.detach();
	}
	
	function createIdentifiedSpan(identifier){
		if (debugging){
			insertHtmlBeforeSelection("<span id='" + identifier + "' style='background-color: #00aa00;'><i>{"+identifier+"}<i></span>");
		} else {
			insertHtmlBeforeSelection("<span id='" + identifier + "'/>");					
		}		
	}

	/**
	 * Marks the text identified by the serialised range and assigns it an
	 * element with the given identifier.
	 * 
	 * @param serialized the serialised selection
	 * @param identifier the identifier to assign the range
	 * @returns page number, zero offset
	 */
	function markRange(serialized, identifier) {
		if ($('#' + identifier).length == 0) {
			if (rangy.canDeserializeSelection(serialized)) {
				var selection = rangy.deserializeSelection(serialized);
				cssApplier.applyToSelection();
				createIdentifiedSpan(identifier);
				markedText = selection.toString();
				selection.removeAllRanges();
				selection.detach();
				// Determine the offset
				var p = $('#' + identifier);
				var offset = p.offset();
				var page = Math.floor(offset.left / pageWidth);
				return page;
			}
		}
	}

	/**
	 * Injects a <code>span</code> element just before the specified range and
	 * identifies the range using the specified identifier.
	 * 
	 * @param serialized the serialized range
	 * @param identifier the identifier
	 * @returns the page number of the element
	 */
	function injectIdentifier(serialized, identifier) {
		if ($('#' + identifier).length == 0) {
			if (rangy.canDeserializeRange(serialized)) {
				var range = rangy.deserializeRange(serialized, document);
				var selection = rangy.getSelection();
				selection.setSingleRange(range);
				createIdentifiedSpan(identifier);
				selection.removeAllRanges();
				selection.detach();
				// Determine the offset
				var p = $('#' + identifier);
				var offset = p.offset();
				var page = Math.floor(offset.left / pageWidth);
				return page;
			}
		}
	}

	/**
	 * Insert HTML before or after selection.
	 */
	var insertHtmlBeforeSelection, insertHtmlAfterSelection;
	(function() {
		function createInserter(isBefore) {
			return function(html) {
				var sel, range, node;
				if (window.getSelection) {
					sel = window.getSelection();
					if (sel.getRangeAt && sel.rangeCount) {
						range = window.getSelection().getRangeAt(0);
						range.collapse(isBefore);
						var el = document.createElement("div");
						el.innerHTML = html;
						var frag = document.createDocumentFragment(), node, lastNode;
						while ((node = el.firstChild)) {
							lastNode = frag.appendChild(node);
						}
						range.insertNode(frag);
					}
				}
			}
		}

		insertHtmlBeforeSelection = createInserter(true);
		insertHtmlAfterSelection = createInserter(false);
	})();

	/***************************************************************************
	 * Notify the Java code about the selected range.
	 **************************************************************************/
	function showSelection(e) {
		// Omit checksum
		var selection = rangy.getSelection();
		var serialization = rangy.serializeSelection(selection, true);
		var text = document.getSelection().toString();
		var yellow = cssApplier.isAppliedToSelection()
		javaMarkTextHandler(serialization, text, yellow);
	}
	document.onmouseup = showSelection;

	/***************************************************************************
	 * Inject CSS for markers and annotations.
	 **************************************************************************/
	function injectCSS() {
		var headTag = document.getElementsByTagName("head")[0].innerHTML;
		var newCSS = headTag
				+ '<style type="text/css">html, body {margin:0; padding:0} *.yellowMarker {background-color: yellow;}</style>';
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