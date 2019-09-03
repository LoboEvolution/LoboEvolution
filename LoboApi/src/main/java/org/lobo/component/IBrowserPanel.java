package org.lobo.component;

import javax.swing.JScrollPane;

import org.lobo.tab.DnDTabbedPane;

public interface IBrowserPanel {
	
	DnDTabbedPane getTabbedPane();
	
	JScrollPane getScroll();

}
