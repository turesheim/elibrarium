package no.resheim.elibrarium.library.ui;

import no.resheim.elibrarium.library.Book;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;

public class LibraryLabelProvider implements IStyledLabelProvider {

	@Override
	public Image getImage(Object obj) {
		return LibraryUIPlugin.getDefault().getImageRegistry().get(LibraryUIPlugin.IMG_BOOK);
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	@Override
	public StyledString getStyledText(Object element) {
		Book book =  ((Book) element);
		StyledString string = new StyledString(book.getTitle());
		String decorated = NLS.bind("{0} - {1}",
				new String[] { book.getTitle(),book.getAuthor() });

		return StyledCellLabelProvider.styleDecoratedString(decorated, StyledString.QUALIFIER_STYLER, string);
	}

}
