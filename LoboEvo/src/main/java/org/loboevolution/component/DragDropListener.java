package org.loboevolution.component;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.store.TabStore;
import org.loboevolution.tab.DnDTabbedPane;
import org.loboevolution.tab.TabbedPanePopupMenu;

/**
 * <p>DragDropListener class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class DragDropListener implements DropTargetListener {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(DragDropListener.class.getName());
	
	private final IBrowserPanel bpanel;

	/**
	 * <p>Constructor for DragDropListener.</p>
	 *
	 * @param bpanel a {@link org.loboevolution.component.IBrowserPanel} object.
	 */
	public DragDropListener(IBrowserPanel bpanel) {
		this.bpanel = bpanel;
	}

	/** {@inheritDoc} */
	@Override
	public void drop(DropTargetDropEvent dtde) {
		try {
			Transferable tr = dtde.getTransferable();
			DataFlavor[] flavors = tr.getTransferDataFlavors();
			for (DataFlavor dataFlavor : flavors) {
				if (dataFlavor.isFlavorJavaFileListType()) {
					dtde.acceptDrop(DnDConstants.ACTION_COPY);
					List<Object> list = (List<Object>) tr.getTransferData(dataFlavor);
					for (Object object : list) {
						openFileDrop("file:///" + object);
					}
					dtde.dropComplete(true);
					return;
				}
			}
			dtde.rejectDrop();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			dtde.rejectDrop();
		}
	}
	
	private void openFileDrop(String fullURL) {
		fullURL = fullURL.replace("\\", "/");
		final DnDTabbedPane tabbedPane = bpanel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(bpanel));
		int index = TabStore.getTabs().size();
		final HtmlPanel hpanel = HtmlPanel.createHtmlPanel(bpanel, fullURL);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
		tabbedPane.insertTab(title, null, hpanel, title, index+1);
		tabbedPane.setSelectedIndex(index+1);
		final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
		IBrowserPanel panel = browserFrame.getPanel();
		IWelcomePanel welcome = panel.getWelcome();
		browserFrame.getToolbar().getAddressBar().setText(fullURL);
		TabStore.insertTab(index+1, fullURL, title);
		welcome.setBackground(new Color(37, 51, 61));		
		bpanel.getScroll().getViewport().add(tabbedPane);
	}

	/** {@inheritDoc} */
	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {}

	/** {@inheritDoc} */
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {}

	/** {@inheritDoc} */
	@Override
	public void dragExit(DropTargetEvent dte) {
		IBrowserFrame browserFrame = bpanel.getBrowserFrame();
		IBrowserPanel panel = browserFrame.getPanel();
		IWelcomePanel welcome = panel.getWelcome();
		welcome.setBackground(new Color(37, 51, 61));
		final DnDTabbedPane tabbedPane = bpanel.getTabbedPane();
		bpanel.getScroll().getViewport().add(tabbedPane);
	}

	/** {@inheritDoc} */
	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		IBrowserFrame browserFrame = bpanel.getBrowserFrame();
		IBrowserPanel panel = browserFrame.getPanel();
		IWelcomePanel welcome = panel.getWelcome();
		welcome.setBackground(new Color(37, 51, 61, 65));
		final DnDTabbedPane tabbedPane = bpanel.getTabbedPane();
		bpanel.getScroll().getViewport().add(tabbedPane);
	}
}
