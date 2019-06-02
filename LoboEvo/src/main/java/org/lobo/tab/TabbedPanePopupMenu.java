package org.lobo.tab;

import java.awt.Component;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.lobo.component.BrowserPanel;
import org.lobo.welcome.WelcomePanel;

public class TabbedPanePopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 1L;

	private final JMenuItem closeAll;

	private final JMenuItem closeAllButActive;

	private final JMenuItem closeLeft;

	private final JMenuItem closePage;

	private final JMenuItem closeRight;

	protected transient int count;

	public TabbedPanePopupMenu(BrowserPanel panel) {
		add("New tab").addActionListener(e -> {
			final DnDTabbedPane tabbedPane = (DnDTabbedPane) getInvoker();
			tabbedPane.addTab("New Tab", new WelcomePanel(panel));
			tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
			this.count++;
		});
		addSeparator();
		this.closePage = add("Close");
		this.closePage.addActionListener(e -> {
			final DnDTabbedPane tabbedPane = (DnDTabbedPane) getInvoker();
			tabbedPane.remove(tabbedPane.getSelectedIndex());
		});
		addSeparator();
		this.closeAll = add("Close all");
		this.closeAll.addActionListener(e -> {
			final DnDTabbedPane tabbedPane = (DnDTabbedPane) getInvoker();
			tabbedPane.removeAll();
		});
		this.closeAllButActive = add("Close all but active");
		this.closeAllButActive.addActionListener(e -> {
			final DnDTabbedPane tabbedPane = (DnDTabbedPane) getInvoker();
			final int tabidx = tabbedPane.getSelectedIndex();
			final String title = tabbedPane.getTitleAt(tabidx);
			final Component cmp = tabbedPane.getComponentAt(tabidx);
			tabbedPane.removeAll();
			tabbedPane.addTab(title, cmp);
		});
		this.closeRight = add("Close right");
		this.closeRight.setVisible(false);
		this.closeRight.addActionListener(e -> {

			final DnDTabbedPane tabbedPane = (DnDTabbedPane) getInvoker();
			final int tabidx = tabbedPane.getSelectedIndex();
			final int count = tabbedPane.getTabCount() - 1;
			for (int i = count; i > tabidx; i--) {
				tabbedPane.removeTabAt(i);
			}
		});
		this.closeLeft = add("Close left");
		this.closeLeft.addActionListener(e -> {
			final DnDTabbedPane tabbedPane = (DnDTabbedPane) getInvoker();
			final int tabidx = tabbedPane.getSelectedIndex();
			for (int i = tabidx - 1; i >= 0; i--) {
				tabbedPane.removeTabAt(i);
			}
		});
	}

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
}