package no.resheim.elibrarium.library.ui;

import no.resheim.elibrarium.library.Annotation;
import no.resheim.elibrarium.library.Book;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class AnnotationViewer extends ContentViewer implements ISelectionProvider {

	private final Composite rootContainer;

	/**
	 * List of double-click state listeners (element type:
	 * <code>IDoubleClickListener</code>).
	 * 
	 * @see #fireDoubleClick
	 */
	private final ListenerList doubleClickListeners = new ListenerList();

	private Book input;

	private final ScrolledComposite scrollable;

	private final AnnotationMouseListener mouseListener;

	public AnnotationViewer(Composite parent, int style) {
		mouseListener = new AnnotationMouseListener();
		rootContainer = new Composite(parent, SWT.NONE);
		rootContainer.setLayout(new FillLayout());
		scrollable = new ScrolledComposite(rootContainer, SWT.V_SCROLL);
		scrollable.setExpandHorizontal(true);
		scrollable.setExpandVertical(true);
		final Composite composite = new Composite(scrollable, SWT.NONE);
		scrollable.setContent(composite);
		composite.setLayout(new GridLayout(1, true));
		composite.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		hookControl(rootContainer);
		scrollable.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Rectangle r = scrollable.getClientArea();
				scrollable.setMinSize(composite.computeSize(r.width, SWT.DEFAULT));
			}
		});
	}

	/**
	 * Adds a listener for double-clicks in this viewer. Has no effect if an
	 * identical listener is already registered.
	 * 
	 * @param listener
	 *            a double-click listener
	 */
	public void addDoubleClickListener(IDoubleClickListener listener) {
		doubleClickListeners.add(listener);
	}

	@Override
	public Control getControl() {
		return rootContainer;
	}

	private Composite getContents() {
		return (Composite) ((Composite) rootContainer.getChildren()[0]).getChildren()[0];
	}

	@Override
	public ISelection getSelection() {
		return selection;
	}

	@Override
	protected void handleDispose(DisposeEvent event) {
		if (rootContainer != null) {
			rootContainer.dispose();
		}
		super.handleDispose(event);
	}

	@Override
	protected void inputChanged(Object input, Object oldInput) {
		if (input instanceof Book) {
			this.input = (Book) input;
			refresh();
		}
	}

	private ISelection selection;

	private class AnnotationMouseListener extends MouseAdapter {

		@Override
		public void mouseDoubleClick(MouseEvent e) {
			Object[] l = doubleClickListeners.getListeners();
			final DoubleClickEvent event = new DoubleClickEvent(AnnotationViewer.this, selection);
			for (int i = 0; i < l.length; i++) {
				final IDoubleClickListener listener = (IDoubleClickListener) l[i];
				SafeRunner.run(new SafeRunnable() {
					@Override
					public void run() throws Exception {
						listener.doubleClick(event);
					}
				});
			}
		}

		@Override
		public void mouseDown(MouseEvent e) {
			clearSelection();
			selection = new StructuredSelection(e.widget.getData());
			if (e.widget instanceof Label) {
				clearSelection();
				((Label) e.widget).setBackground(e.widget.getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION));
				((Label) e.widget).setForeground(e.widget.getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION_TEXT));
			}
		}

	}

	/**
	 * Clear the the selection and restore color of any selected items.
	 */
	private void clearSelection() {
		if (selection != null) {
			Object data = ((IStructuredSelection) selection).getFirstElement();
			Composite composite = getContents();
			Control[] children = composite.getChildren();
			for (Control control : children) {
				if (control.getData() == data) {
					control.setBackground(control.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
					control.setForeground(control.getDisplay().getSystemColor(SWT.COLOR_LIST_FOREGROUND));
				}
			}
		}
	}

	@Override
	public void refresh() {
		// TODO: Retain selection if the selected item is still present
		selection = null;
		if (input != null) {
			Composite composite = getContents();
			Control[] children = composite.getChildren();
			EList<Annotation> annotations = input.getAnnotations();
			GridData gd = new GridData(GridData.FILL, SWT.BEGINNING, true, false);
			gd.verticalAlignment = SWT.BEGINNING;
			for (int i = 0; i < annotations.size(); i++) {
				Annotation annotation = annotations.get(i);
				Label textLabel = null;
				if (i >= children.length) {
					textLabel = new Label(composite, SWT.WRAP);
					textLabel.addMouseListener(mouseListener);
					textLabel.setLayoutData(gd);
				} else {
					textLabel = (Label) children[i];
					textLabel.pack(true);
				}
				textLabel.setData(annotation);
				textLabel.setText(annotation.getText());
			}
			if (children.length > annotations.size()) {
				for (int i = annotations.size(); i < children.length; i++) {
					children[i].dispose();
				}
			}
			composite.pack(true);
		}
	}

	/**
	 * Removes the given double-click listener from this viewer. Has no effect
	 * if an identical listener is not registered.
	 * 
	 * @param listener
	 *            a double-click listener
	 */
	public void removeDoubleClickListener(IDoubleClickListener listener) {
		doubleClickListeners.remove(listener);
	}

	@Override
	public void setSelection(ISelection selection, boolean reveal) {
		this.selection = selection;
	}

}
