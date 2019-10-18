package org.loboevolution.component;

import javax.swing.JScrollPane;

import org.loboevolution.tab.DnDTabbedPane;

public interface IBrowserPanel {
	
	DnDTabbedPane getTabbedPane();
	
	JScrollPane getScroll();
	
	IBrowserFrame getBrowserFrame();

}
