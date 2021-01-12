/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.menu.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.loboevolution.common.Strings;
import org.loboevolution.http.HtmlContent;
import org.loboevolution.info.MetaInfo;
import org.loboevolution.net.HttpNetwork;
import org.w3c.dom.Document;

/**
 * <p>InfoPageWindow class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class InfoPageWindow extends JFrame {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(InfoPageWindow.class.getName());

	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for InfoPageWindow.</p>
	 *
	 * @param document a {@link org.w3c.dom.Document} object.
	 */
	public InfoPageWindow(Document document) {
		super("Lobo Info");
		createAndShowGUI(document);
	}

	private JScrollPane content(List<MetaInfo> infoList, String syntax) {
		try {
			final Object[] columnNames = { "" };
			final List<String[]> values = new ArrayList<>();
			for (final MetaInfo info : infoList) {
				if (Strings.isNotBlank(info.getName())) {
					values.add(new String[] { info.getName() });
				}
			}
			final DefaultTableModel model = new DefaultTableModel(values.toArray(new Object[][] {}), columnNames);
			final JTable jtable = new JTable(model);
			jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
			jtable.setTableHeader(null);
			jtable.setShowGrid(false);
			jtable.setColumnSelectionAllowed(true);
			jtable.setCellSelectionEnabled(true);

			final RSyntaxTextArea textArea = new RSyntaxTextArea();
			textArea.setHighlightCurrentLine(true);
			textArea.setAnimateBracketMatching(true);
			textArea.setAntiAliasingEnabled(true);
			textArea.setEditable(false);
			if (syntax != null) {
				textArea.setSyntaxEditingStyle(syntax);
			}

			final ListSelectionModel cellSelectionModel = jtable.getSelectionModel();
			cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			cellSelectionModel.addListSelectionListener(e -> {

				final int[] selectedRow = jtable.getSelectedRows();
				final int[] selectedColumns = jtable.getSelectedColumns();

				for (final int element : selectedRow) {
					for (final int selectedColumn : selectedColumns) {
						try {
							final String href = (String) jtable.getValueAt(element, selectedColumn);
							textArea.setText(HttpNetwork.getSource(href));
							textArea.repaint();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			});

			final JPanel tablePanel = new JPanel();
			tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
			tablePanel.add(jtable);
			tablePanel.add(textArea);
			return new JScrollPane(tablePanel);
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	private void createAndShowGUI(Document document) {
		setResizable(true);
		setLocationRelativeTo(null);

		ImageIcon info = new ImageIcon(getClass().getResource("/org/lobo/image/info.png"));
		ImageIcon media = new ImageIcon(getClass().getResource("/org/lobo/image/media.png"));
		ImageIcon css = new ImageIcon(getClass().getResource("/org/lobo/image/css.png"));
		ImageIcon js = new ImageIcon(getClass().getResource("/org/lobo/image/js.png"));

		Image img = info.getImage();
		Image newimg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		info = new ImageIcon(newimg);

		img = media.getImage();
		newimg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		media = new ImageIcon(newimg);

		img = css.getImage();
		newimg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		css = new ImageIcon(newimg);

		img = js.getImage();
		newimg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		js = new ImageIcon(newimg);

		final HtmlContent htmlcontent = new HtmlContent(document);

		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(30, 30));
		tabbedPane.addTab("", info, infoContent(htmlcontent.getMetaList()));
		tabbedPane.addTab("", media, mediaContent(htmlcontent.getMediaList()));
		tabbedPane.addTab("", css, content(htmlcontent.getStyleList(), SyntaxConstants.SYNTAX_STYLE_CSS));
		tabbedPane.addTab("", js, content(htmlcontent.getJSList(), SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT));
		add(tabbedPane, BorderLayout.CENTER);
	}

	private JScrollPane infoContent(List<MetaInfo> infoList) {
		try {
			final Object[] columnNames = { "", "" };
			final List<String[]> values = new ArrayList<>();
			for (final MetaInfo info : infoList) {
				if (Strings.isNotBlank(info.getName())) {
					values.add(new String[] { info.getName(), info.getContent() });
				} else if (Strings.isNotBlank(info.getItemprop())) {
					values.add(new String[] { info.getItemprop(), info.getContent() });
				} else if (Strings.isNotBlank(info.getProperty())) {
					values.add(new String[] { info.getProperty(), info.getContent() });
				} else if (Strings.isNotBlank(info.getHttpEqui())) {
					values.add(new String[] { info.getHttpEqui(), info.getContent() });
				}
			}
			final DefaultTableModel model = new DefaultTableModel(values.toArray(new Object[][] {}), columnNames);
			final JTable jtable = new JTable(model);
			jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
			jtable.setTableHeader(null);
			jtable.setShowGrid(false);
			return new JScrollPane(jtable);
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isVisible() {
		return true;
	}

	private Component mediaContent(List<MetaInfo> mediaList) {
		try {
			final Object[] columnNames = { "" };
			final List<String[]> values = new ArrayList<>();
			for (final MetaInfo info : mediaList) {
				if (Strings.isNotBlank(info.getName())) {
					values.add(new String[] { info.getName() });
				}
			}
			final DefaultTableModel model = new DefaultTableModel(values.toArray(new Object[][] {}), columnNames);
			final JTable jtable = new JTable(model);
			jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
			jtable.setTableHeader(null);
			jtable.setShowGrid(false);
			jtable.setColumnSelectionAllowed(true);
			jtable.setCellSelectionEnabled(true);

			final JPanel jPanelImg = new JPanel();

			final ListSelectionModel cellSelectionModel = jtable.getSelectionModel();
			cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			cellSelectionModel.addListSelectionListener(e -> {
				jPanelImg.removeAll();
				final int[] selectedRow = jtable.getSelectedRows();
				final int[] selectedColumns = jtable.getSelectedColumns();

				for (final int element : selectedRow) {
					for (final int selectedColumn : selectedColumns) {
						final String href = (String) jtable.getValueAt(element, selectedColumn);
						jPanelImg.add(new JLabel(new ImageIcon(HttpNetwork.getImage(href, null))));
						jPanelImg.repaint();
					}
				}
			});

			final JPanel tablePanel = new JPanel();
			tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
			tablePanel.add(jtable);
			tablePanel.add(jPanelImg);
			return new JScrollPane(tablePanel);
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}
}
