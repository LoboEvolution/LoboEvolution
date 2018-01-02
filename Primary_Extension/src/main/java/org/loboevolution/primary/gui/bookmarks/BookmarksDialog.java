/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.gui.bookmarks;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.loboevolution.primary.ext.HistoryEntry;
import org.loboevolution.primary.ext.IconFactory;
import org.loboevolution.primary.info.BookmarkInfo;
import org.loboevolution.ua.NavigatorWindow;

/**
 * The Class BookmarksDialog.
 */
public class BookmarksDialog extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The jtf filter. */
	private JTextField jtfFilter;

	/** The row sorter. */
	private TableRowSorter<TableModel> rowSorter;

	/** The window. */
	private NavigatorWindow window;

	/** The search. */
	private final String SEARCH = "/org/loboevolution/images/search.png";

	/**
	 * Instantiates a new bookmarks dialog.
	 */
	public BookmarksDialog(NavigatorWindow window) {
		this.window = window;
		jtfFilter = new JTextField();
		jtfFilter.setToolTipText("Keywords will be matched against URL, title, description and tags");
		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Specify a word to match:"), BorderLayout.WEST);
		panel.add(jtfFilter, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		add(tablePane(), BorderLayout.CENTER);

		jtfFilter.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = jtfFilter.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = jtfFilter.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}

		});
	}

	/**
	 * Creates the right pane.
	 *
	 * @param prefsPanel
	 *            the prefs panel
	 * @return the component
	 */
	private Component tablePane() {
		Object columnNames[] = { "Title", "Description", "Url", "Tag" };
		List<String[]> values = new ArrayList<String[]>();

		BookmarksHistory history = BookmarksHistory.getInstance();
		Collection<HistoryEntry<BookmarkInfo>> allEntries = history.getAllEntries();
		for (HistoryEntry<BookmarkInfo> entry : allEntries) {
			BookmarkInfo binfo = entry.getItemInfo();
			values.add(new String[] { binfo.getTitle(), binfo.getDescription(), binfo.getUrl().toString(),
					binfo.getTagsText() });
		}

		JTable jtable = new JTable(values.toArray(new Object[][] {}), columnNames);
		rowSorter = new TableRowSorter<>(jtable.getModel());
		jtable.setRowSorter(rowSorter);
		jtable.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					Point p = e.getPoint();
					int rowNumber = jtable.rowAtPoint(p);
					String[] vals = values.get(rowNumber);
					JPopupMenu popupMenu = new JPopupMenu();
					JMenuItem item = new JMenuItem("Open link in new window");
					item.setIcon(IconFactory.getInstance().getIcon(SEARCH));
					item.addActionListener(e1 -> {
						try {
							window.getTopFrame().open(new URL(vals[2]), "GET", null, "window", null);
						} catch (MalformedURLException mfu) {
							throw new IllegalStateException("not expected", mfu);
						}
					});
					popupMenu.add(item);
					jtable.setComponentPopupMenu(popupMenu);

				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		return new JScrollPane(jtable);
	}

}
