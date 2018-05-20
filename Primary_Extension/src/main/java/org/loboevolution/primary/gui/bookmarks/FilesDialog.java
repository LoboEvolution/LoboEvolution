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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.primary.ext.history.NavigationHistory;
import org.loboevolution.util.OS;

/**
 * The Class FilesDialog.
 */
public class FilesDialog extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(FilesDialog.class);

	/** The jtf filter. */
	private JTextField jtfFilter;

	/** The row sorter. */
	private transient TableRowSorter<TableModel> rowSorter;
	
	/**
	 * Instantiates a new Opened Files Dialog.
	 */
	public FilesDialog(String type) {
		createAndShowGUI(type);
	}

	private void createAndShowGUI(String type) {
		jtfFilter = new JTextField();
		jtfFilter.setToolTipText("Keywords will be matched against URL, title, description and tags");
		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Specify a word to match:"), BorderLayout.WEST);
		panel.add(jtfFilter, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		add(tablePane(type), BorderLayout.CENTER);

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
				// Method not implemented
			}

		});
	}

	/**
	 * Creates the right pane.
	 * @return the component
	 */
	private Component tablePane(String type) {
		Object columnNames[] = { "File" };
		List<String[]> values = NavigationHistory.getFiles(type);
		JTable jtable = new JTable(values.toArray(new Object[][] {}), columnNames);
		rowSorter = new TableRowSorter<>(jtable.getModel());
		jtable.setRowSorter(rowSorter);
		jtable.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					Point p = e.getPoint();
					int rowNumber = jtable.rowAtPoint(p);
					String[] vals = values.get(rowNumber);
					JPopupMenu popupMenu = new JPopupMenu();
					
					JMenuItem item = new JMenuItem("Open File");
					item.addActionListener(e1 -> {
						try {
							File file = new File(vals[0]);
							OS.launchPath(file.getAbsolutePath());
						} catch (IOException e2) {
							logger.error(e2.getMessage());
						}
						
					});
					popupMenu.add(item);
					
					item = new JMenuItem("Open Folder");
					item.addActionListener(e1 -> {
						try {
							File file = new File(vals[0]);
							OS.launchPath(file.getParentFile().getAbsolutePath());
						} catch (IOException e2) {
							logger.error(e2.getMessage());
						}
					});
					popupMenu.add(item);
					jtable.setComponentPopupMenu(popupMenu);

				}
			}
		});
		return new JScrollPane(jtable);
	}
}
