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
package org.loboevolution.primary.ext;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.loboevolution.html.dombl.SVGRasterizer;
import org.loboevolution.http.SSLCertificate;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.info.MetaInfo;
import org.loboevolution.util.io.IORoutines;

/**
 * The Class InfoPageWindow.
 */
public class InfoPageWindow extends JFrame {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(InfoPageWindow.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new text viewer window.
	 */
	public InfoPageWindow(List<MetaInfo> infoList, List<MetaInfo> mediaList, List<MetaInfo> cssList, List<MetaInfo> jsList) {
		super("Lobo Info");
		createAndShowGUI(infoList, mediaList, cssList, jsList);
	}

	private void createAndShowGUI(List<MetaInfo> infoList, List<MetaInfo> mediaList, List<MetaInfo> cssList, List<MetaInfo> jsList) {
		setResizable(true);
		setLocationRelativeTo(null);

		ImageIcon info = new ImageIcon(getClass().getResource("/org/loboevolution/images/info.png"));
		ImageIcon media = new ImageIcon(getClass().getResource("/org/loboevolution/images/media.png"));
		ImageIcon css = new ImageIcon(getClass().getResource("/org/loboevolution/images/css.png"));
		ImageIcon js = new ImageIcon(getClass().getResource("/org/loboevolution/images/js.png"));

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

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(30, 30));
		tabbedPane.addTab("", info, infoContent(infoList));
		tabbedPane.addTab("", media, mediaContent(mediaList));
		tabbedPane.addTab("", css, content(cssList, SyntaxConstants.SYNTAX_STYLE_CSS));
		tabbedPane.addTab("", js, jsContent(jsList, SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT));
		add(tabbedPane, BorderLayout.CENTER);
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	private JScrollPane infoContent(List<MetaInfo> infoList) {
		try {
			Object columnNames[] = { "", "" };
			List<String[]> values = new ArrayList<String[]>();
			for (MetaInfo info : infoList) {
				if (!Strings.isBlank(info.getName())) {
					values.add(new String[] { info.getName(), info.getContent() });
				} else if (!Strings.isBlank(info.getItemprop())) {
					values.add(new String[] { info.getItemprop(), info.getContent() });
				} else if (!Strings.isBlank(info.getProperty())) {
					values.add(new String[] { info.getProperty(), info.getContent() });
				} else if (!Strings.isBlank(info.getHttpEqui())) {
					values.add(new String[] { info.getHttpEqui(), info.getContent() });
				}
			}
			DefaultTableModel model = new DefaultTableModel(values.toArray(new Object[][] {}), columnNames);
			JTable jtable = new JTable(model);
			jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
			jtable.setTableHeader(null);
			jtable.setShowGrid(false);
			return new JScrollPane(jtable);
		} catch (Exception e) {
			logger.error("infopage", e);
		}
		return null;
	}

	private JScrollPane content(List<MetaInfo> infoList, String syntax) {
		try {
			Object columnNames[] = { "" };
			List<String[]> values = new ArrayList<String[]>();
			for (MetaInfo info : infoList) {
				if (!Strings.isBlank(info.getName())) {
					values.add(new String[] { info.getName() });
				}
			}
			DefaultTableModel model = new DefaultTableModel(values.toArray(new Object[][] {}), columnNames);
			JTable jtable = new JTable(model);
			jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
			jtable.setTableHeader(null);
			jtable.setShowGrid(false);
			jtable.setColumnSelectionAllowed(true);
			jtable.setCellSelectionEnabled(true);

			RSyntaxTextArea textArea = new RSyntaxTextArea();
			textArea.setHighlightCurrentLine(true);
			textArea.setAnimateBracketMatching(true);
			textArea.setAntiAliasingEnabled(true);
			textArea.setEditable(false);
			textArea.setSyntaxEditingStyle(syntax);

			ListSelectionModel cellSelectionModel = jtable.getSelectionModel();
			cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {

					int[] selectedRow = jtable.getSelectedRows();
					int[] selectedColumns = jtable.getSelectedColumns();

					for (int i = 0; i < selectedRow.length; i++) {
						for (int j = 0; j < selectedColumns.length; j++) {
							String href = (String) jtable.getValueAt(selectedRow[i], selectedColumns[j]);
							textArea.setText(getSourceCode(href, syntax));
							textArea.repaint();
						}
					}
				}
			});

			JPanel tablePanel = new JPanel();
			tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
			tablePanel.add(jtable);
			tablePanel.add(textArea);
			return new JScrollPane(tablePanel);
		} catch (Exception e) {
			logger.error("infopage", e);
		}
		return null;
	}

	private JScrollPane jsContent(List<MetaInfo> infoList, String syntax) {
		try {
			Object columnNames[] = { "" };
			List<String[]> values = new ArrayList<String[]>();
			for (MetaInfo info : infoList) {
				if (!Strings.isBlank(info.getName())) {
					values.add(new String[] { info.getName() });
				}
			}
			DefaultTableModel model = new DefaultTableModel(values.toArray(new Object[][] {}), columnNames);
			JTable jtable = new JTable(model);
			jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
			jtable.setTableHeader(null);
			jtable.setShowGrid(false);
			jtable.setColumnSelectionAllowed(true);
			jtable.setCellSelectionEnabled(true);

			RSyntaxTextArea textArea = new RSyntaxTextArea();
			textArea.setHighlightCurrentLine(true);
			textArea.setAnimateBracketMatching(true);
			textArea.setAntiAliasingEnabled(true);
			textArea.setEditable(false);
			textArea.setSyntaxEditingStyle(syntax);

			ListSelectionModel cellSelectionModel = jtable.getSelectionModel();
			cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {

					int[] selectedRow = jtable.getSelectedRows();
					int[] selectedColumns = jtable.getSelectedColumns();

					for (int i = 0; i < selectedRow.length; i++) {
						for (int j = 0; j < selectedColumns.length; j++) {
							String href = (String) jtable.getValueAt(selectedRow[i], selectedColumns[j]);
							textArea.setText(getSourceCode(href, syntax));
							textArea.repaint();
						}
					}
				}
			});

			JPanel tablePanel = new JPanel();
			tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
			tablePanel.add(jtable);
			tablePanel.add(textArea);
			return new JScrollPane(tablePanel);
		} catch (Exception e) {
			logger.error("infopage", e);
		}
		return null;
	}

	private Component mediaContent(List<MetaInfo> mediaList) {
		try {
			Object columnNames[] = { "" };
			List<String[]> values = new ArrayList<String[]>();
			for (MetaInfo info : mediaList) {
				if (!Strings.isBlank(info.getName())) {
					values.add(new String[] { info.getName() });
				}
			}
			DefaultTableModel model = new DefaultTableModel(values.toArray(new Object[][] {}), columnNames);
			JTable jtable = new JTable(model);
			jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
			jtable.setTableHeader(null);
			jtable.setShowGrid(false);
			jtable.setColumnSelectionAllowed(true);
			jtable.setCellSelectionEnabled(true);

			JPanel jPanelImg = new JPanel();

			ListSelectionModel cellSelectionModel = jtable.getSelectionModel();
			cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					jPanelImg.removeAll();
					int[] selectedRow = jtable.getSelectedRows();
					int[] selectedColumns = jtable.getSelectedColumns();

					for (int i = 0; i < selectedRow.length; i++) {
						for (int j = 0; j < selectedColumns.length; j++) {
							String href = (String) jtable.getValueAt(selectedRow[i], selectedColumns[j]);
							jPanelImg.add(new JLabel(new ImageIcon(getImage(href))));
							jPanelImg.repaint();
						}
					}
				}
			});

			JPanel tablePanel = new JPanel();
			tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
			tablePanel.add(jtable);
			tablePanel.add(jPanelImg);
			return new JScrollPane(tablePanel);
		} catch (Exception e) {
			logger.error("infopage", e);
		}
		return null;
	}

	private Image getImage(String href) {
		try {
			URL u = new URL(href);
			URLConnection con = u.openConnection();
			con.setRequestProperty("User-Agent", UserAgentContext.DEFAULT_USER_AGENT);
			con.setRequestProperty("Accept-Encoding", UserAgentContext.GZIP_ENCODING);
			if (href.endsWith(".svg")) {
				SVGRasterizer r = new SVGRasterizer(u);
				return r.bufferedImageToImage();
			} else if (href.startsWith("https")) {
				return Toolkit.getDefaultToolkit().createImage(ImageIO.read(IORoutines.getInputStream(con)).getSource());
			} else if (href.endsWith(".gif")) {
				try {
					return new ImageIcon(u).getImage();
				} catch (Exception e) {
					return ImageIO.read(IORoutines.getInputStream(con));
				}
			} else if (href.endsWith(".bmp")) {
				try {
					return ImageIO.read(IORoutines.getInputStream(con));
				} catch (IOException e) {
					logger.error("read error: " + e);
				}
			} else {
				return ImageIO.read(IORoutines.getInputStream(con));
			}
		} catch (Exception e) {
			logger.error("infopage", e);
		}
		return null;
	}

	private String getSourceCode(String href, String syntax) {
		SSLCertificate.setCertificate();
		String source = "";
		URL url;
		try {
			url = new URL(href);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", UserAgentContext.DEFAULT_USER_AGENT);
			connection.setRequestProperty("Cookie", "");
			connection.setRequestProperty("Accept-Encoding", UserAgentContext.GZIP_ENCODING);
			InputStream in = IORoutines.getInputStream(connection);
			byte[] content;
			try {
				content = IORoutines.load(in, 8192);
				source = new String(content, "UTF-8");
				if (SyntaxConstants.SYNTAX_STYLE_CSS.equals(syntax)) {
					source = source.replace("}", "}\n");
				}
			} finally {
				in.close();
			}
		} catch (Exception e) {
			logger.error("infopage", e);
		}
		return source;
	}
}
