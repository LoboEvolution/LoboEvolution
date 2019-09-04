package org.lobo.menu.crono;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
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

import org.lobo.component.BrowserFrame;
import org.lobo.component.BrowserPanel;
import org.lobo.http.NavigationHistory;
import org.lobo.http.NavigationManager;
import org.lobo.store.TabStore;
import org.lobo.tab.DnDTabbedPane;
import org.lobo.tab.TabbedPanePopupMenu;

public class ShowRecentWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	/** The jtf filter. */
	private JTextField jtfFilter;

	/** The row sorter. */
	private transient TableRowSorter<TableModel> rowSorter;

	public ShowRecentWindow(BrowserFrame frame) {
		createAndShowGUI(frame);
	}

	private void createAndShowGUI(BrowserFrame frame) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		final ImageIcon ico = new ImageIcon(getClass().getResource("/org/lobo/image/host.png"));
		setIconImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		this.jtfFilter = new JTextField();
		this.jtfFilter.setToolTipText("Keywords will be matched against URL, title, description and tags");
		setLayout(new BorderLayout());
		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Specify a word to match:"), BorderLayout.WEST);
		panel.add(this.jtfFilter, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		add(tablePane(frame), BorderLayout.CENTER);

		this.jtfFilter.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {
				// Method not implemented
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				final String text = ShowRecentWindow.this.jtfFilter.getText();

				if (text.trim().length() == 0) {
					ShowRecentWindow.this.rowSorter.setRowFilter(null);
				} else {
					ShowRecentWindow.this.rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				final String text = ShowRecentWindow.this.jtfFilter.getText();

				if (text.trim().length() == 0) {
					ShowRecentWindow.this.rowSorter.setRowFilter(null);
				} else {
					ShowRecentWindow.this.rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

		});
	}

	/**
	 * Creates the right pane.
	 *
	 * @param prefsPanel the prefs panel
	 * @return the component
	 */
	private Component tablePane(BrowserFrame frame) {
		final Object columnNames[] = { "" };
		final NavigationHistory history = new NavigationHistory();
		final List<String[]> hostEntries = history.getRecentHostEntries(100);
		final JTable jtable = new JTable(hostEntries.toArray(new Object[][] {}), columnNames);
		this.rowSorter = new TableRowSorter<>(jtable.getModel());
		jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
		jtable.setTableHeader(null);
		jtable.setShowGrid(false);
		jtable.setRowSorter(this.rowSorter);
		jtable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					final Point p = e.getPoint();
					final int rowNumber = jtable.rowAtPoint(p);
					final String[] vals = hostEntries.get(rowNumber);
					final JPopupMenu popupMenu = new JPopupMenu();
					final JMenuItem item = new JMenuItem("Open link in new tab");
					final ImageIcon ico = new ImageIcon(getClass().getResource("/org/lobo/image/search.png"));
					item.setIcon(ico);
					item.addActionListener(e1 -> {
						final BrowserPanel panel = frame.getPanel();
						final int indexPanel = panel.getTabbedPane().getIndex() + 1;
						final DnDTabbedPane tabbedPane = panel.getTabbedPane();
						tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(panel));
						tabbedPane.insertTab("New Tab", null, NavigationManager.getHtmlPanel(vals[0]), null, indexPanel);
						tabbedPane.setSelectedIndex(indexPanel);
						TabStore.insertTab(indexPanel, vals[0]);
					});
					popupMenu.add(item);
					jtable.setComponentPopupMenu(popupMenu);

				}
			}
		});
		return new JScrollPane(jtable);
	}

}