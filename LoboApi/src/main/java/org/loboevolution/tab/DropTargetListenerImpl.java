package org.loboevolution.tab;

import java.awt.Component;
import java.awt.Point;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.util.ArrayList;
import java.util.List;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.component.IBrowserFrame;
import org.loboevolution.info.TabInfo;
import org.loboevolution.store.TabStore;

/**
 * <p>DropTargetListenerImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class DropTargetListenerImpl extends DropTargetAdapter {

	private final DnDTabbedPane tabbed;

	/**
	 * <p>Constructor for DropTargetListenerImpl.</p>
	 *
	 * @param tabbed a {@link org.loboevolution.tab.DnDTabbedPane} object.
	 */
	public DropTargetListenerImpl(DnDTabbedPane tabbed) {
		this.tabbed = tabbed;
	}

	/** {@inheritDoc} */
	@Override
	public void dragOver(final DropTargetDragEvent dtde) {
		this.tabbed.getGlass().setDragLocation(dtde.getLocation());
		this.tabbed.getGlass().setVisible(true);
		this.tabbed.getGlass().repaint();
	}

	/** {@inheritDoc} */
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
				TabStore.insertTab(i, tabInfo.getUrl(), tabInfo.getTitle());
				tabbed.addTab(tabInfo.getTitle(), null, cmp, tabInfo.getTitle());
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
