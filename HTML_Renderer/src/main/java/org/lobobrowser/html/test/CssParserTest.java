package org.lobobrowser.html.test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.html.style.CSSUtilities;
import org.lobobrowser.util.io.IORoutines;
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
	private static final Logger logger = Logger.getLogger(CssParserTest.class
			.getName());
	private final HtmlPanel cssOutput;
	private final JTextArea textArea;

	public CssParserTest() throws HeadlessException {
		this("CSS Parser Test Tool");
	}

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

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				process(textField.getText());
			}
		});
	}

	private void process(String uri) {
		try {
			URL url;
			try {
				url = new URL(uri);
			} catch (java.net.MalformedURLException mfu) {
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
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible;) Cobra/0.96.1+");
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
			String source = new String(content, "ISO-8859-1");
			System.out.println("source: " + source);
			this.textArea.setText(source);
			long time1 = System.currentTimeMillis();
			CSSOMParser parser = new CSSOMParser();
			InputSource is = CSSUtilities.getCssInputSourceForStyleSheet(source, uri);
			CSSStyleSheet styleSheet = parser.parseStyleSheet(is, null, null);
			long time2 = System.currentTimeMillis();
			logger.info("Parsed URI=[" + uri + "]: Parse elapsed: "
					+ (time2 - time1) + " ms. Load elapsed: " + (time1 - time0)
					+ " ms.");
			this.showStyleSheet(styleSheet);
		} catch (Exception err) {
			logger.log(Level.SEVERE, "Error trying to load URI=[" + uri + "].",
					err);
			this.clearCssOutput();
		}
	}

	private void clearCssOutput() {
		this.cssOutput.clearDocument();
	}

	private void showStyleSheet(CSSStyleSheet styleSheet) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		writer.println("<DL>");
		CSSRuleList ruleList = styleSheet.getCssRules();
		int length = ruleList.getLength();
		for (int i = 0; i < length; i++) {
			CSSRule rule = ruleList.item(i);
			writer.println("<DT><strong>Rule: type=" + rule.getType()
					+ ",class=" + rule.getClass().getName() + "</strong></DT>");
			writer.println("<DD>");
			this.writeRuleInfo(writer, rule);
			writer.println("</DD>");
		}
		writer.println("</DL>");
		writer.flush();
		String html = stringWriter.toString();
		HtmlRendererContext rcontext = new SimpleHtmlRendererContext(
				this.cssOutput);
		this.cssOutput.setHtml(html, "about:css", rcontext);
	}

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

	public static void main(String[] args) {
		CssParserTest frame = new CssParserTest();
		frame.setSize(800, 400);
		frame.setExtendedState(TestFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
}
