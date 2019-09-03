package org.lobo.tab;

import java.awt.Component;
import java.awt.Point;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.Icon;

public class DropTargetListenerImpl extends DropTargetAdapter {

	private final DnDTabbedPane tabbed;

	public DropTargetListenerImpl(DnDTabbedPane tabbed) {
		this.tabbed = tabbed;
	}

	@Override
	public void dragOver(final DropTargetDragEvent dtde) {
		this.tabbed.getGlass().setDragLocation(dtde.getLocation());
		this.tabbed.getGlass().setVisible(true);
		this.tabbed.getGlass().repaint();
	}

	@Override
	public void drop(DropTargetDropEvent e) {
		final Point p = e.getLocation();
		final int idx = this.tabbed.getDropIndex(p);
		if (idx > -1 && this.tabbed.dragTabIdx > -1 && idx != this.tabbed.dragTabIdx) {
			final Component tab = this.tabbed.getTabComponentAt(this.tabbed.dragTabIdx);
			final Component comp = this.tabbed.getComponentAt(this.tabbed.dragTabIdx);
			final String title = this.tabbed.getTitleAt(this.tabbed.dragTabIdx);
			final Icon icon = this.tabbed.getIconAt(this.tabbed.dragTabIdx);
			final String tip = this.tabbed.getToolTipTextAt(this.tabbed.dragTabIdx);
			this.tabbed.removeTabAt(this.tabbed.dragTabIdx);
			this.tabbed.insertTab(title, icon, comp, tip, idx);
			this.tabbed.setTabComponentAt(idx, tab);
			this.tabbed.setSelectedIndex(idx);
			e.dropComplete(true);
		} else {
			e.dropComplete(false);
		}
		this.tabbed.dragTabIdx = -1;
	}

}
