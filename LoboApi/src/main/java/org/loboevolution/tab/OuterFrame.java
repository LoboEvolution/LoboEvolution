package org.loboevolution.tab;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.JFrame;

class OuterFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	protected Component comp;

	protected Icon icon;

	protected Component tab;

	protected String tabTitle;

	protected String tip;

	/**
	 * <p>Constructor for OuterFrame.</p>
	 *
	 * @param tabbed a {@link org.loboevolution.tab.DnDTabbedPane} object.
	 */
	public OuterFrame(DnDTabbedPane tabbed) {
		super();
		setSize(200, 300);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				tabbed.addTab(OuterFrame.this.tabTitle, OuterFrame.this.icon, OuterFrame.this.comp,
						OuterFrame.this.tip);
				final int idx = tabbed.indexOfComponent(OuterFrame.this.comp);
				tabbed.setTabComponentAt(idx, OuterFrame.this.tab);
				tabbed.setSelectedIndex(idx);
				removeWindowListener(this);
			}
		});
	}
}
