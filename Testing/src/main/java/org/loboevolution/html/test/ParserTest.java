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
package org.loboevolution.html.test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.util.SSLCertificate;
import org.loboevolution.util.io.IORoutines;
import org.w3c.dom.Document;

/**
 * Parser-only test frame.
 */
public class ParserTest extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ParserTest.class);

	/** The tree. */
	private final JTree tree;

	/** The text area. */
	private final JTextArea textArea;

	/**
	 * Instantiates a new parser test.
	 *
	 * @throws HeadlessException
	 *             the headless exception
	 */
	public ParserTest() throws HeadlessException {
		this("HTML Parser-Only Test Tool");
	}

	/**
	 * Instantiates a new parser test.
	 *
	 * @param title
	 *            the title
	 * @throws HeadlessException
	 *             the headless exception
	 */
	public ParserTest(String title) throws HeadlessException {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		final JTextField textField = new JTextField();
		JButton button = new JButton("Parse & Render");
		final JTabbedPane tabbedPane = new JTabbedPane();
		final JTree tree = new JTree();
		tree.setModel(null);
		final JScrollPane scrollPane = new JScrollPane(tree);

		this.tree = tree;

		contentPane.add(topPanel, BorderLayout.NORTH);
		contentPane.add(bottomPanel, BorderLayout.CENTER);

		topPanel.add(new JLabel("URL: "), BorderLayout.WEST);
		topPanel.add(textField, BorderLayout.CENTER);
		topPanel.add(button, BorderLayout.EAST);

		bottomPanel.add(tabbedPane, BorderLayout.CENTER);

		final JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		this.textArea = textArea;
		final JScrollPane textAreaSp = new JScrollPane(textArea);

		tabbedPane.addTab("HTML DOM", scrollPane);
		tabbedPane.addTab("Source Code", textAreaSp);

		button.addActionListener(event -> process(textField.getText()));
	}

	/**
	 * Process.
	 *
	 * @param uri
	 *            the uri
	 */
	private void process(String uri) {
		try {
			URL url;
			try {
				url = new URL(uri);
			} catch (MalformedURLException mfu) {
				int idx = uri.indexOf(':');
				if (idx == -1 || idx == 1) {
					// try file
					url = new URL("file:" + uri);
				} else {
					throw mfu;
				}
			}
			logger.info("process(): Loading URI=[" + uri + "].");
			long time0 = System.currentTimeMillis();
			SSLCertificate.setCertificate();
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible;) Cobra/0.96.1+");
			connection.setRequestProperty("Cookie", "");
			if (connection instanceof HttpURLConnection) {
				HttpURLConnection hc = (HttpURLConnection) connection;
				hc.setInstanceFollowRedirects(true);
				int responseCode = hc.getResponseCode();
				logger.info("process(): HTTP response code: " + responseCode);
			}
			InputStream in = connection.getInputStream();
			byte[] content;
			try {
				content = IORoutines.load(in, 8192);
			} finally {
				in.close();
			}
			String source = new String(content, "UTF-8");

			this.textArea.setText(source);
			long time1 = System.currentTimeMillis();
			InputStream bin = new ByteArrayInputStream(content);
			UserAgentContext ucontext = new SimpleUserAgentContext();
			DocumentBuilderImpl builder = new DocumentBuilderImpl(ucontext);
			// Provide a proper URI, in case it was a file.
			String actualURI = url.toExternalForm();
			// Should change to use proper charset.
			Document document = builder.parse(new InputSourceImpl(bin, actualURI, "UTF-8"));
			long time2 = System.currentTimeMillis();
			logger.info("Parsed URI=[" + uri + "]: Parse elapsed: " + (time2 - time1) + " ms. Load elapsed: "
					+ (time1 - time0) + " ms.");
			this.tree.setModel(new NodeTreeModel(document));
		} catch (Exception err) {
			logger.error( "Error trying to load URI=[" + uri + "].", err);
		}
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		ParserTest frame = new ParserTest();
		frame.setSize(800, 400);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
}
