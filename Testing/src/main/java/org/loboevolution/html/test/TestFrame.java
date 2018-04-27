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
import java.awt.Component;
import java.awt.Container;
import java.awt.HeadlessException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.util.Objects;

/**
 * A Swing frame that can be used to test the Cobra HTML rendering engine.
 */
public class TestFrame extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(TestFrame.class);

	/** The rcontext. */
	private final transient SimpleHtmlRendererContext rcontext;

	/** The address field. */
	private final JTextField addressField;

	/**
	 * Instantiates a new test frame.
	 *
	 * @throws HeadlessException
	 *             the headless exception
	 */
	public TestFrame() throws HeadlessException {
		this("");
	}

	/**
	 * Instantiates a new test frame.
	 *
	 * @param title
	 *            the title
	 * @throws HeadlessException
	 *             the headless exception
	 */
	public TestFrame(String title) throws HeadlessException {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		final JTextField textField = new JTextField();
		this.addressField = textField;
		JButton button = new JButton("Parse & Render");
		final JTabbedPane tabbedPane = new JTabbedPane();
		final JTree tree = new JTree();
		final JScrollPane scrollPane = new JScrollPane(tree);

		contentPane.add(topPanel, BorderLayout.NORTH);
		contentPane.add(bottomPanel, BorderLayout.CENTER);

		topPanel.add(new JLabel("URL: "), BorderLayout.WEST);
		topPanel.add(textField, BorderLayout.CENTER);
		topPanel.add(button, BorderLayout.EAST);

		bottomPanel.add(tabbedPane, BorderLayout.CENTER);

		final HtmlPanel panel = new HtmlPanel();
		panel.addSelectionChangeListener(event -> {
			if (logger.isInfoEnabled()) {
				logger.info("selectionChanged(): selection node: " + panel.getSelectionNode());
			}
		});
		UserAgentContext ucontext = new SimpleUserAgentContext();
		this.rcontext = new LocalHtmlRendererContext(panel, ucontext);

		final JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		final JScrollPane textAreaSp = new JScrollPane(textArea);

		tabbedPane.addTab("HTML", panel);
		tabbedPane.addTab("Tree", scrollPane);
		tabbedPane.addTab("Source", textAreaSp);
		tabbedPane.addChangeListener(e -> {
			Component component = tabbedPane.getSelectedComponent();
			if (Objects.equals(component, scrollPane)) {
				tree.setModel(new NodeTreeModel(panel.getRootNode()));
			} else if (Objects.equals(component, textAreaSp)) {
				textArea.setText(rcontext.getSourceCode());
			}
		});

		button.addActionListener(event -> process(textField.getText()));
	}

	/**
	 * Gets the html renderer context.
	 *
	 * @return the html renderer context
	 */
	public HtmlRendererContext getHtmlRendererContext() {
		return this.rcontext;
	}

	/**
	 * Navigate.
	 *
	 * @param uri
	 *            the uri
	 */
	public void navigate(String uri) {
		this.addressField.setText(uri);
		this.process(uri);
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
			// Call SimpleHtmlRendererContext.navigate()
			// which implements incremental rendering.
			this.rcontext.navigate(url, null);
		} catch (Exception err) {
			logger.error( "Error trying to load URI=[" + uri + "].", err);
		}
	}
}
