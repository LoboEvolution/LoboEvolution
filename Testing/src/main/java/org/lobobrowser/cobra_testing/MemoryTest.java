/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.cobra_testing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.domimpl.DOMNodeImpl;
import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.renderer.DelayedPair;
import org.lobobrowser.html.renderer.FrameContext;
import org.lobobrowser.html.renderer.RBlock;
import org.lobobrowser.html.renderer.RenderableContainer;
import org.lobobrowser.html.renderer.RenderableSpot;
import org.lobobrowser.html.test.SimpleHtmlRendererContext;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.util.io.IORoutines;
import org.w3c.dom.Document;

/**
 * The Class MemoryTest.
 */
public class MemoryTest {
	// JVM setting -Xmx150m tried with:
	// - 500K file with fairly complex markup.
	// - 1.5M file with simple markup.

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(MemoryTest.class.getName());

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		MemoryTest mt = new MemoryTest();
		// mt.testParserLoop();
		// mt.testRendererLoop();
		mt.testRendererGUILoop();
		System.out.println("main() done!");
		System.exit(0);
	}

	/** The test url. */
	private static String TEST_URL = "file:c:\\temp\\html\\long.html";

	public void testParserLoop() throws Exception {
		URL url = new URL(TEST_URL);
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
		// String source = new String(content, "UTF-8");
		// long time1 = System.currentTimeMillis();
		logger.info("Content size: " + content.length + " bytes.");
		UserAgentContext context = new SimpleUserAgentContext();
		DocumentBuilderImpl builder = new DocumentBuilderImpl(context);
		for (int i = 0; i < 200; i++) {
			logger.info("Starting parse # " + i + ": freeMemory=" + Runtime.getRuntime().freeMemory());
			InputStream bin = new ByteArrayInputStream(content);
			Document document = builder.parse(new InputSourceImpl(bin, url.toExternalForm(), "UTF-8"));
			logger.info("Finished parsing: freeMemory=" + Runtime.getRuntime().freeMemory() + ",document=" + document);
			document = null;
			System.gc();
			logger.info("After GC: freeMemory=" + Runtime.getRuntime().freeMemory());
			Thread.sleep(2);
		}
	}

	public void testRendererLoop() throws Exception {
		URL url = new URL(TEST_URL);
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
		// String source = new String(content, "UTF-8");
		// long time1 = System.currentTimeMillis();
		logger.info("Content size: " + content.length + " bytes.");
		final UserAgentContext ucontext = new SimpleUserAgentContext();
		final HtmlPanel panel = new HtmlPanel();
		final HtmlRendererContext rcontext = new SimpleHtmlRendererContext(panel, (UserAgentContext) null);
		DocumentBuilderImpl builder = new DocumentBuilderImpl(ucontext, rcontext);
		InputStream bin = new ByteArrayInputStream(content);
		final FrameContext frameContext = new LocalFrameContext();
		final RenderableContainer renderableContainer = new LocalRenderableContainer();
		for (int i = 0; i < 100; i++) {
			logger.info("Starting parse # " + i + ": freeMemory=" + Runtime.getRuntime().freeMemory());
			bin = new ByteArrayInputStream(content);
			Document document = builder.parse(new InputSourceImpl(bin, url.toExternalForm(), "UTF-8"));
			logger.info("Finished parsing: freeMemory=" + Runtime.getRuntime().freeMemory());
			{
				final Document doc = document;
				EventQueue.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						RBlock block = new RBlock((DOMNodeImpl) doc, 0, rcontext.getUserAgentContext(), rcontext,
								frameContext, renderableContainer);
						block.layout(100, 100, false);
					}
				});
				// panel.setDocument(doc, rcontext, pcontext);
				Thread.sleep(50);
				// panel.clearDocument();
			}
			document = null;
			System.gc();
			logger.info("After GC: freeMemory=" + Runtime.getRuntime().freeMemory());
			Thread.sleep(2);
		}
	}

	public void testRendererGUILoop() throws Exception {
		URL url = new URL(TEST_URL);
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
		// String source = new String(content, "UTF-8");
		// long time1 = System.currentTimeMillis();
		logger.info("Content size: " + content.length + " bytes.");
		final UserAgentContext ucontext = new SimpleUserAgentContext();
		final HtmlPanel panel = new HtmlPanel();
		final HtmlRendererContext rcontext = new SimpleHtmlRendererContext(panel, (UserAgentContext) null);
		DocumentBuilderImpl builder = new DocumentBuilderImpl(ucontext, rcontext);
		JFrame testFrame = new JFrame("Testing...");
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testFrame.getContentPane().setLayout(new java.awt.BorderLayout());
		testFrame.getContentPane().add(panel, BorderLayout.CENTER);
		InputStream bin = new ByteArrayInputStream(content);
		testFrame.setSize(new java.awt.Dimension(600, 400));
		testFrame.setVisible(true);
		for (int i = 0; i < 100; i++) {
			logger.info("Starting parse # " + i + ": freeMemory=" + Runtime.getRuntime().freeMemory());
			bin = new ByteArrayInputStream(content);
			Document document = builder.parse(new InputSourceImpl(bin, url.toExternalForm(), "UTF-8"));
			logger.info("Finished parsing: freeMemory=" + Runtime.getRuntime().freeMemory());
			panel.setDocument(document, rcontext);
			EventQueue.invokeAndWait(new Runnable() {
				@Override
				public void run() {
				}
			});
			// Without these sleeps, it does apparently run out of memory.
			Thread.sleep(3000);
			panel.clearDocument();
			Thread.sleep(1000);
			document = null;
			System.gc();
			logger.info("After GC: freeMemory=" + Runtime.getRuntime().freeMemory());
			Thread.sleep(2000);
		}
	}

	/**
	 * The Class LocalRenderableContainer.
	 */
	private class LocalRenderableContainer implements RenderableContainer {
		@Override
		public void invalidateLayoutUpTree() {
			// nop
		}

		@Override
		public Component addComponent(Component component) {
			// nop
			return null;
		}

		public void remove(Component c) {
			// nop
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.lobobrowser.html.renderer.RenderableContainer#
		 * getPaintedBackgroundColor()
		 */
		@Override
		public Color getPaintedBackgroundColor() {
			return Color.BLACK;
		}

		/**
		 * Gets the insets.
		 *
		 * @return the insets
		 */
		public Insets getInsets() {
			return new Insets(0, 0, 0, 0);
		}

		@Override
		public void repaint(int x, int y, int width, int height) {
		}

		@Override
		public void relayout() {
			// nop
		}

		@Override
		public void updateAllWidgetBounds() {
			// nop
		}

		@Override
		public Point getGUIPoint(int clientX, int clientY) {
			return new Point(clientX, clientY);
		}

		@Override
		public void focus() {
			// nop
		}

		@Override
		public void addDelayedPair(DelayedPair pair) {
			// nop
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.lobobrowser.html.renderer.RenderableContainer#getParentContainer(
		 * )
		 */
		@Override
		public RenderableContainer getParentContainer() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.lobobrowser.html.renderer.RenderableContainer#getDelayedPairs()
		 */
		@Override
		public Collection getDelayedPairs() {
			return null;
		}

		@Override
		public void clearDelayedPairs() {
		}

		@Override
		public int getHeight() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getWidth() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getY() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Insets getInsets(boolean hscroll, boolean vscroll) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * The Class LocalFrameContext.
	 */
	private class LocalFrameContext implements FrameContext {
		@Override
		public void expandSelection(RenderableSpot rpoint) {
		}

		@Override
		public void resetSelection(RenderableSpot rpoint) {
		}

		@Override
		public void delayedRelayout(DOMNodeImpl node) {
			// TODO Auto-generated method stub

		}
	}
}
