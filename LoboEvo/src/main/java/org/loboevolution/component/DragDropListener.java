/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.component;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.store.TabStore;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.util.List;

/**
 * <p>DragDropListener class.</p>
 */
@Slf4j
public class DragDropListener implements DropTargetListener {
	
	private final IBrowserPanel bpanel;

	/**
	 * <p>Constructor for DragDropListener.</p>
	 *
	 * @param bpanel a {@link org.loboevolution.component.IBrowserPanel} object.
	 */
	public DragDropListener(final IBrowserPanel bpanel) {
		this.bpanel = bpanel;
	}

	/** {@inheritDoc} */
	@Override
	public void drop(final DropTargetDropEvent dtde) {
		try {
			final Transferable tr = dtde.getTransferable();
			final DataFlavor[] flavors = tr.getTransferDataFlavors();
			for (final DataFlavor dataFlavor : flavors) {
				if (dataFlavor.isFlavorJavaFileListType()) {
					dtde.acceptDrop(DnDConstants.ACTION_COPY);
					final List<Object> list = (List<Object>) tr.getTransferData(dataFlavor);
					for (final Object object : list) {
						openFileDrop("file:///" + object);
					}
					dtde.dropComplete(true);
					return;
				}
			}
			dtde.rejectDrop();
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
			dtde.rejectDrop();
		}
	}
	
	private void openFileDrop(final String fullURL) {
		final String fUrl = fullURL.replace("\\", "/");
		final ITabbedPane tabbedPane = bpanel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(bpanel);
		final int index = TabStore.getTabs().size();
		final HtmlPanel hpanel = NavigatorFrame.createHtmlPanel(bpanel, fUrl);
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) hpanel.getRootNode();
		final String title = Strings.isNotBlank(nodeImpl.getTitle()) ? nodeImpl.getTitle() : "New Tab";
		tabbedPane.insertTab(title, null, hpanel, title, index+1);
		tabbedPane.setSelectedIndex(index+1);
		final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
		final IBrowserPanel panel = browserFrame.getPanel();
		final IWelcomePanel welcome = panel.getWelcome();
		browserFrame.getToolbar().getAddressBar().setText(fUrl);
		TabStore.insertTab(index+1, fUrl, title);
		welcome.setBackground(new Color(37, 51, 61));		
		bpanel.getScroll().getViewport().add((Component)tabbedPane);
	}

	/** {@inheritDoc} */
	@Override
	public void dropActionChanged(final DropTargetDragEvent dtde) {}

	/** {@inheritDoc} */
	@Override
	public void dragEnter(final DropTargetDragEvent dtde) {}

	/** {@inheritDoc} */
	@Override
	public void dragExit(final DropTargetEvent dte) {
		final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
		final IBrowserPanel panel = browserFrame.getPanel();
		final IWelcomePanel welcome = panel.getWelcome();
		welcome.setBackground(new Color(37, 51, 61));
		final ITabbedPane tabbedPane = bpanel.getTabbedPane();
		bpanel.getScroll().getViewport().add((Component)tabbedPane);
	}

	/** {@inheritDoc} */
	@Override
	public void dragOver(final DropTargetDragEvent dtde) {
		final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
		final IBrowserPanel panel = browserFrame.getPanel();
		final IWelcomePanel welcome = panel.getWelcome();
		welcome.setBackground(new Color(37, 51, 61, 65));
		final ITabbedPane tabbedPane = bpanel.getTabbedPane();
		bpanel.getScroll().getViewport().add((Component)tabbedPane);
	}
}
