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
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.style.CSSUtilities;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.util.SSLCertificate;
import org.loboevolution.util.io.IORoutines;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSImportRule;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;

import com.steadystate.css.parser.CSSOMParser;

/**
 * Tests only the CSS parser.
 */
public class CssParserTest extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(CssParserTest.class);

	/** The css output. */
	private final HtmlPanel cssOutput;

	/** The text area. */
	private final JTextArea textArea;

	/**
	 * Instantiates a new css parser test.
	 *
	 * @throws HeadlessException
	 *             the headless exception
	 */
	public CssParserTest() throws HeadlessException {
		this("CSS Parser Test Tool");
	}

	/**
	 * Instantiates a new css parser test.
	 *
	 * @param title
	 *            the title
	 * @throws HeadlessException
	 *             the headless exception
	 */
	public CssParserTest(String title) throws HeadlessException {
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

		HtmlPanel htmlPanel = new HtmlPanel();
		this.cssOutput = htmlPanel;

		contentPane.add(topPanel, BorderLayout.NORTH);
		contentPane.add(bottomPanel, BorderLayout.CENTER);

		topPanel.add(new JLabel("URL: "), BorderLayout.WEST);
		topPanel.add(textField, BorderLayout.CENTER);
		topPanel.add(button, BorderLayout.EAST);

		bottomPanel.add(tabbedPane, BorderLayout.CENTER);

		final JTextArea textArea = new JTextArea();
		this.textArea = textArea;
		final JScrollPane textAreaSp = new JScrollPane(textArea);

		tabbedPane.addTab("Parsed CSS", htmlPanel);
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
			CSSOMParser parser = new CSSOMParser();
			InputSource is = CSSUtilities.getCssInputSourceForStyleSheet(source, uri);
			CSSStyleSheet styleSheet = parser.parseStyleSheet(is, null, null);
			long time2 = System.currentTimeMillis();
			logger.info("Parsed URI=[" + uri + "]: Parse elapsed: " + (time2 - time1) + " ms. Load elapsed: "
					+ (time1 - time0) + " ms.");
			this.showStyleSheet(styleSheet);
		} catch (Exception err) {
			logger.log(Level.ERROR, "Error trying to load URI=[" + uri + "].", err);
			this.clearCssOutput();
		}
	}

	/**
	 * Clear css output.
	 */
	private void clearCssOutput() {
		this.cssOutput.clearDocument();
	}

	/**
	 * Show style sheet.
	 *
	 * @param styleSheet
	 *            the style sheet
	 */
	private void showStyleSheet(CSSStyleSheet styleSheet) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		writer.println("<DL>");
		CSSRuleList ruleList = styleSheet.getCssRules();
		int length = ruleList.getLength();
		for (int i = 0; i < length; i++) {
			CSSRule rule = ruleList.item(i);
			writer.println("<DT><strong>Rule: type=" + rule.getType() + ",class=" + rule.getClass().getName()
					+ "</strong></DT>");
			writer.println("<DD>");
			this.writeRuleInfo(writer, rule);
			writer.println("</DD>");
		}
		writer.println("</DL>");
		writer.flush();
		String html = stringWriter.toString();
		HtmlRendererContext rcontext = new SimpleHtmlRendererContext(this.cssOutput, (UserAgentContext) null);
		this.cssOutput.setHtml(html, "about:css", rcontext);
	}

	/**
	 * Write rule info.
	 *
	 * @param writer
	 *            the writer
	 * @param rule
	 *            the rule
	 */
	private void writeRuleInfo(PrintWriter writer, CSSRule rule) {
		if (rule instanceof CSSStyleRule) {
			CSSStyleRule styleRule = (CSSStyleRule) rule;
			writer.println("Selector: " + styleRule.getSelectorText());
			writer.println("<br>");
			writer.println("CSS Text: " + styleRule.getCssText());
		} else if (rule instanceof CSSImportRule) {
			CSSImportRule styleRule = (CSSImportRule) rule;
			writer.println("HREF: " + styleRule.getHref());
			writer.println("<br>");
			writer.println("CSS Text: " + styleRule.getCssText());
		}
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		CssParserTest frame = new CssParserTest();
		frame.setSize(800, 400);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
}
