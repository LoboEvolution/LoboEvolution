package org.loboevolution.menu.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.loboevolution.info.CookieInfo;

/**
 * <p>CookieWindow class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class CookieWindow extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new text viewer window.
	 *
	 * @param cookieList a {@link java.util.List} object.
	 */
	public CookieWindow(List<CookieInfo> cookieList) {
		createAndShowGUI(cookieList);
	}

	private void createAndShowGUI(List<CookieInfo> cookieList) {
		final Object columnNames[] = { "Name", "Value", "Expires" };

		final List<String[]> values = new ArrayList<String[]>();
		for (final CookieInfo info : cookieList) {
			values.add(new String[] { info.getName(), info.getValue(), info.getExpires() });
		}

		final DefaultTableModel model = new DefaultTableModel(values.toArray(new Object[][] {}), columnNames);
		final JTable jtable = new JTable(model);
		jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
		jtable.setShowGrid(false);
		add(new JScrollPane(jtable));
	}
}
