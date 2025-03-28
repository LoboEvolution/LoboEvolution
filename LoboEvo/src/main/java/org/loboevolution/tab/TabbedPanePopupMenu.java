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

package org.loboevolution.tab;

import org.loboevolution.component.IBrowserFrame;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.info.TabInfo;
import org.loboevolution.store.TabStore;
import org.loboevolution.store.WebStore;
import org.loboevolution.welcome.WelcomePanel;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.List;

/**
 * <p>TabbedPanePopupMenu class.</p>
 */
public class TabbedPanePopupMenu extends JPopupMenu {

	@Serial
    private static final long serialVersionUID = 1L;

	private final JMenuItem closeAll;

	private final JMenuItem closeAllButActive;

	private final JMenuItem closeLeft;

	private final JMenuItem closePage;

	private final JMenuItem closeRight;

	/**
	 * <p>Constructor for TabbedPanePopupMenu.</p>
	 *
	 * @param panel a {@link org.loboevolution.component.IBrowserPanel} object.
	 */
	public TabbedPanePopupMenu(final IBrowserPanel panel) {
		add("New tab").addActionListener(e -> {
			final DnDTabbedPane tabbedPane = (DnDTabbedPane) getInvoker();
			final int index = tabbedPane.getSelectedIndex() + 1;
			newTab(index, tabbedPane, panel);
		});
		addSeparator();
		this.closePage = add("Close");
		this.closePage.addActionListener(e -> {
			final DnDTabbedPane tabbedPane = (DnDTabbedPane) getInvoker();
			final int tabidx = tabbedPane.getSelectedIndex();
			tabbedPane.remove(tabidx);
			closeTab(tabidx, tabbedPane, panel);
		});
		addSeparator();
		this.closeAll = add("Close all");
		this.closeAll.addActionListener(e -> {
			final DnDTabbedPane tabbedPane = (DnDTabbedPane) getInvoker();
			tabbedPane.removeAll();
			closeAllTab();
		});
		this.closeAllButActive = add("Close other");
		this.closeAllButActive.addActionListener(e -> {
			final DnDTabbedPane tabbedPane = (DnDTabbedPane) getInvoker();
			final int tabidx = tabbedPane.getSelectedIndex();
			final String title = tabbedPane.getTitleAt(tabidx);
			final Component cmp = tabbedPane.getComponentAt(tabidx);
			tabbedPane.removeAll();
			tabbedPane.addTab(title, cmp);
			closeOtherTab(tabidx, tabbedPane, panel);
		});
		this.closeRight = add("Close right");
		this.closeRight.setVisible(false);
		this.closeRight.addActionListener(e -> {

			final DnDTabbedPane tabbedPane = (DnDTabbedPane) getInvoker();
			final int tabidx = tabbedPane.getSelectedIndex();
			final int count = tabbedPane.getTabCount() - 1;
			for (int i = count; i > tabidx; i--) {
				tabbedPane.removeTabAt(i);
				TabStore.deleteTab(i);
			}
		});
		this.closeLeft = add("Close left");
		this.closeLeft.addActionListener(e -> {
			final DnDTabbedPane tabbedPane = (DnDTabbedPane) getInvoker();
			final int tabidx = tabbedPane.getSelectedIndex();
			for (int i = tabidx - 1; i >= 0; i--) {
				tabbedPane.removeTabAt(i);
				closeTab(i, tabbedPane, panel);
			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public void show(final Component c, final int x, final int y) {
		if (c instanceof DnDTabbedPane tabbedPane) {
            this.closePage.setVisible(tabbedPane.indexAtLocation(x, y) >= 0);
			this.closeAll.setVisible(tabbedPane.getTabCount() > 0);
			this.closeAllButActive.setVisible(tabbedPane.getTabCount() > 0);
			this.closeRight.setVisible(tabbedPane.indexAtLocation(x, y) > 0
					&& tabbedPane.indexAtLocation(x, y) < tabbedPane.getTabCount() - 1);
			this.closeLeft.setVisible(tabbedPane.indexAtLocation(x, y) > 0);
			super.show(c, x, y);
		}
	}
	
	
	private void newTab(final int index, final DnDTabbedPane tabbedPane, final IBrowserPanel panel) {
		tabbedPane.insertTab("New Tab", null, new WelcomePanel(panel), null, index);
		tabbedPane.setSelectedIndex(index);
		TabStore.insertTab(index, "");
		final IBrowserFrame browserFrame = panel.getBrowserFrame();
		browserFrame.getToolbar().getAddressBar().setText("");
		panel.getScroll().getViewport().add(tabbedPane);
	}
	
	private void closeTab(final int index, final DnDTabbedPane tabbedPane, final IBrowserPanel panel) {
		final List<TabInfo> tabs = TabStore.getTabs();
		for (int i = 0; i < tabs.size(); i++) {
			
			if (i == index) {
				WebStore.deleteStorage(1, index);
				TabStore.deleteTab(index);
			}
			
			if (i > index) {
				WebStore.deleteStorage(1, index);
				final TabInfo tabInfo = tabs.get(i);
				TabStore.deleteTab(i);
				TabStore.insertTab(i-1, tabInfo.getUrl());
				final IBrowserFrame browserFrame = panel.getBrowserFrame();
				browserFrame.getToolbar().getAddressBar().setText(tabInfo.getUrl());
			}
		}
		panel.getScroll().getViewport().add(tabbedPane);
	}
	
	
	private void closeAllTab() {
		final List<TabInfo> tabs = TabStore.getTabs();
		for (int i = 0; i < tabs.size(); i++) {
			WebStore.deleteStorage(1, i);
			TabStore.deleteTab(i);
		}
	}
	
	private void closeOtherTab(final int index, final DnDTabbedPane tabbedPane, final IBrowserPanel panel) {
		final List<TabInfo> tabs = TabStore.getTabs();
		for (int i = 0; i < tabs.size(); i++) {
			if (i != index) {
				WebStore.deleteStorage(1, i);
				TabStore.deleteTab(i);
			} else {
				WebStore.deleteStorage(1, index);
				final TabInfo tabInfo = tabs.get(index);
				TabStore.deleteTab(index);
				TabStore.insertTab(0, tabInfo.getUrl());
				final IBrowserFrame browserFrame = panel.getBrowserFrame();
				browserFrame.getToolbar().getAddressBar().setText(tabInfo.getUrl());
			}
		}
		panel.getScroll().getViewport().add(tabbedPane);
	}
}
