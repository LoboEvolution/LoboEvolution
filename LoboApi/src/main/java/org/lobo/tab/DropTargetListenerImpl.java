package org.lobo.tab;

import java.awt.Component;
import java.awt.Point;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import org.lobo.common.ArrayUtilities;
import org.lobo.component.IBrowserFrame;
import org.lobo.info.TabInfo;
import org.lobo.store.TabStore;

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
			List<TabInfo> tabs = TabStore.getTabs();
			List<Component> comps = new ArrayList<Component>(); 
			for (int i = 0; i < tabs.size(); i++) {
				Component comp = this.tabbed.getComponentAt(i);
				comps.add(comp);
			}
			
			tabbed.removeAll();
			TabStore.deleteAll();
			ArrayUtilities.moveItem(this.tabbed.dragTabIdx, idx, tabs);
			ArrayUtilities.moveItem(this.tabbed.dragTabIdx, idx, comps);

			for (int i = 0; i < tabs.size(); i++) {
				TabInfo tabInfo = tabs.get(i);
				Component cmp = comps.get(i);
				TabStore.insertTab(i, tabInfo.getUrl());
				tabbed.addTab("New Tab", cmp);
			}
			IBrowserFrame browserFrame = tabbed.getBrowserPanel().getBrowserFrame();
			browserFrame.getToolbar().getAddressBar().setText(tabs.get(idx).getUrl());
			tabbed.getBrowserPanel().getScroll().getViewport().add(this.tabbed);
			this.tabbed.setSelectedIndex(idx);
			e.dropComplete(true);
		} else {
			e.dropComplete(false);
		}
		this.tabbed.dragTabIdx = -1;
	}
}
