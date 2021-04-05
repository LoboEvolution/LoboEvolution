/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.tab;

import java.awt.Component;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.loboevolution.component.IBrowserFrame;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.info.TabInfo;
import org.loboevolution.store.TabStore;
import org.loboevolution.store.WebStore;

/**
 * <p>TabbedPanePopupMenu class.</p>
 *
 *
 *
 */
public class TabbedPanePopupMenu extends JPopupMenu {

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
	public TabbedPanePopupMenu(IBrowserPanel panel) {
		add("New tab").addActionListener(e -> {
			final DnDTabbedPane tabbedPane = (DnDTabbedPane) getInvoker();
			int index = tabbedPane.getSelectedIndex() + 1;
			newTab(index, tabbedPane, panel);
		});
		addSeparator();
		this.closePage = add("Close");
		this.closePage.addActionListener(e -> {
			final DnDTabbedPane tabbedPane = (DnDTabbedPane) getInvoker();
			int tabidx = tabbedPane.getSelectedIndex();
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
	public void show(Component c, int x, int y) {
		if (c instanceof DnDTabbedPane) {
			final DnDTabbedPane tabbedPane = (DnDTabbedPane) c;
			this.closePage.setVisible(tabbedPane.indexAtLocation(x, y) >= 0);
			this.closeAll.setVisible(tabbedPane.getTabCount() > 0);
			this.closeAllButActive.setVisible(tabbedPane.getTabCount() > 0);
			this.closeRight.setVisible(tabbedPane.indexAtLocation(x, y) > 0
					&& tabbedPane.indexAtLocation(x, y) < tabbedPane.getTabCount() - 1);
			this.closeLeft.setVisible(tabbedPane.indexAtLocation(x, y) > 0);
			super.show(c, x, y);
		}
	}
	
	
	private void newTab(int index, DnDTabbedPane tabbedPane, IBrowserPanel panel) {
		tabbedPane.insertTab("New Tab", null, new JPanel(), null, index);
		tabbedPane.setSelectedIndex(index);
		TabStore.insertTab(index, "");
		IBrowserFrame browserFrame = panel.getBrowserFrame();
		browserFrame.getToolbar().getAddressBar().setText("");
		panel.getScroll().getViewport().add(tabbedPane);
	}
	
	private void closeTab(int index, DnDTabbedPane tabbedPane, IBrowserPanel panel) {
		List<TabInfo> tabs = TabStore.getTabs();
		for (int i = 0; i < tabs.size(); i++) {
			
			if(i == index) {
				WebStore.deleteStorage(1, index);
				TabStore.deleteTab(index);
			}
			
			if(i > index) {
				WebStore.deleteStorage(1, index);
				TabInfo tabInfo = tabs.get(i);
				TabStore.deleteTab(i);
				TabStore.insertTab(i-1, tabInfo.getUrl());
				IBrowserFrame browserFrame = panel.getBrowserFrame();
				browserFrame.getToolbar().getAddressBar().setText(tabInfo.getUrl());
			}
		}
		panel.getScroll().getViewport().add(tabbedPane);
	}
	
	
	private void closeAllTab() {
		List<TabInfo> tabs = TabStore.getTabs();
		for (int i = 0; i < tabs.size(); i++) {
			WebStore.deleteStorage(1, i);
			TabStore.deleteTab(i);

		}
	}
	
	private void closeOtherTab(int index, DnDTabbedPane tabbedPane, IBrowserPanel panel) {
		List<TabInfo> tabs = TabStore.getTabs();
		for (int i = 0; i < tabs.size(); i++) {
			if(i != index) {
				WebStore.deleteStorage(1, i);
				TabStore.deleteTab(i);
			} else {
				WebStore.deleteStorage(1, index);
				TabInfo tabInfo = tabs.get(index);
				TabStore.deleteTab(index);
				TabStore.insertTab(0, tabInfo.getUrl());
				IBrowserFrame browserFrame = panel.getBrowserFrame();
				browserFrame.getToolbar().getAddressBar().setText(tabInfo.getUrl());
			}

		}
		panel.getScroll().getViewport().add(tabbedPane);
	}
	
}
