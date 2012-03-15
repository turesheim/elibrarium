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
	 * Adjust the scroll position of the body so that the current page is in 
	 * view. Returns the text of the first H1 element. 
	 * 
	 * @param page the page number
	 */
	function navigateToPage(page) {
		bodyID.scrollLeft = + (pageWidth * (page - 1));
	}
	
	/**
	 * Returns the title of the chapter. If there are m
	 * @returns
	 */
	function getChapterTitle(){
		var elem = $('h1').filter(function() {
		    return ($(this).offset().left <= bodyID.scrollLeft+pageWidth);
		});
		var h1 = elem.last();
		return h1.text();		
	}
		
	/**
	 * Determines a suitable element on the current page to use as a reference
	 * and returns the serialized location.
	 * 
	 * @returns the serialized page bookmark
	 */
	function getPageBookmark(){
		var elem = $('p,h1,h2,h3,h4,h5,h6,pre,img').filter(function() {
		    return (($(this).offset().left >= bodyID.scrollLeft) 
		    		&& ($(this).offset().left < bodyID.scrollLeft+pageWidth));
		});
		if (elem!=null){
			var range = rangy.createRangyRange(document);
			range.startContainer = elem.get(0);
			range.endContainer = range.startContainer;
			range.startOffset=0;
			range.endOffset=0;
			var serialized = rangy.serializeRange(range,false,document);
			range.detach();
			return serialized;
		}		
	}
	
	/**
	 * Navigates to the given bookmark.
	 * 
	 * @param serialized the serialized page bookmark
	 */
	function navigateToBookmark(serialized){
		if (!rangy.initialized){
			rangy.init();
		}
		range = rangy.deserializeRange(serialized,document);
		var id = range.startContainer.id;
		range.startContainer.id='bookmark'; 
		setOffsetToElement('bookmark');
		range.detach();
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
			if (rangy.canDeserializeSelection(serialized)){
				var selection = rangy.deserializeSelection(serialized);
				cssApplier.applyToSelection();
				insertHtmlBeforeSelection("<span id='"+identifier+"'/>");
				selection.removeAllRanges();
				selection.detach();
			} else {
				alert('Could not mark range');
			}
		} else {
			alert('Mark already exists')
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
	                    while ( (node = el.firstChild) ) {
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
		var serialization = rangy.serializeSelection(selection,true);
		var text = document.getSelection().toString();
		var yellow = cssApplier.isAppliedToSelection()
		javaMarkTextHandler(serialization,text,yellow);
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