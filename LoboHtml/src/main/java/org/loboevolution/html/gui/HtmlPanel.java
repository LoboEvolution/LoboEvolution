/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Nov 19, 2005
 */
package org.loboevolution.html.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.loboevolution.common.EventDispatch2;
import org.loboevolution.common.WrapperLayout;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.html.dom.domimpl.DocumentNotificationListener;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.NodeImpl;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.html.renderer.BoundableRenderable;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.NodeRenderer;
import org.loboevolution.html.renderer.RenderableSpot;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.net.HttpNetwork;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * The HtmlPanel class is a Swing component that can render a HTML
 * DOM. It uses either {@link org.loboevolution.html.gui.HtmlBlockPanel}
 * internally, depending on whether the document is determined to be a FRAMESET
 * or not.
 * <p>
 * Invoke method {@link #setDocument(Document, HtmlRendererContext)} in order to
 * schedule a document for rendering.
 *
 * @author utente
 * @version $Id: $Id
 */
public class HtmlPanel extends JComponent implements FrameContext {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(HtmlPanel.class.getName());

	private class LocalDocumentNotificationListener implements DocumentNotificationListener {
		@Override
		public void allInvalidated() {
			addNotification(new DocumentNotification(DocumentNotification.GENERIC, null));
		}

		@Override
		public void externalScriptLoading(NodeImpl node) {
			// Ignorable here.
		}

		@Override
		public void invalidated(NodeImpl node) {
			addNotification(new DocumentNotification(DocumentNotification.GENERIC, node));
		}

		@Override
		public void lookInvalidated(NodeImpl node) {
			addNotification(new DocumentNotification(DocumentNotification.LOOK, node));
		}

		@Override
		public void nodeLoaded(NodeImpl node) {
			addNotification(new DocumentNotification(DocumentNotification.GENERIC, node));
		}

		@Override
		public void positionInvalidated(NodeImpl node) {
			addNotification(new DocumentNotification(DocumentNotification.POSITION, node));
		}

		@Override
		public void sizeInvalidated(NodeImpl node) {
			addNotification(new DocumentNotification(DocumentNotification.SIZE, node));
		}

		@Override
		public void structureInvalidated(NodeImpl node) {
			addNotification(new DocumentNotification(DocumentNotification.GENERIC, node));
		}
	}

	private class NotificationTimerAction implements java.awt.event.ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			processNotifications();
		}
	}

	private static class SelectionDispatch extends EventDispatch2 {

		@Override
		protected void dispatchEvent(EventListener listener, EventObject event) {
			((SelectionChangeListener) listener).selectionChanged((SelectionChangeEvent) event);
		}
	}

	private static final int NOTIF_TIMER_DELAY = 300;

	private static final long serialVersionUID = 1L;

	private volatile int defaultOverflowX = RenderState.OVERFLOW_AUTO;
	
	private volatile int defaultOverflowY = RenderState.OVERFLOW_AUTO;
	
	protected volatile HtmlBlockPanel htmlBlockPanel;
	
	private volatile NodeRenderer nodeRenderer = null;
	
	private final Runnable notificationImmediateAction;

	private final DocumentNotificationListener notificationListener;
	
	private final List<DocumentNotification> notifications = new ArrayList<DocumentNotification>();

	private final Timer notificationTimer;

	private volatile int preferredWidth = -1;

	private volatile NodeImpl rootNode;

	private final EventDispatch2 selectionDispatch = new SelectionDispatch();

	private IBrowserPanel browserPanel;

	/**
	 * Constructs an HtmlPanel.
	 */
	public HtmlPanel() {
		setLayout(WrapperLayout.getInstance());
		setOpaque(false);
		this.notificationTimer = new Timer(NOTIF_TIMER_DELAY, new NotificationTimerAction());
		this.notificationTimer.setRepeats(false);
		this.notificationListener = new LocalDocumentNotificationListener();
		this.notificationImmediateAction = this::processNotifications;
	}

	private void addNotification(DocumentNotification notification) {
		// This can be called in a random thread.
		final List<DocumentNotification> notifs = this.notifications;
		synchronized (notifs) {
			notifs.add(notification);
		}
		if (SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(this.notificationImmediateAction);
		} else {
			this.notificationTimer.restart();
		}
	}

	/**
	 * Adds listener of selection changes. Note that it does not have any effect on
	 * FRAMESETs.
	 *
	 * @param listener An instance of {@link org.loboevolution.html.gui.SelectionChangeListener}.
	 */
	public void addSelectionChangeListener(SelectionChangeListener listener) {
		this.selectionDispatch.addListener(listener);
	}

	/**
	 * Clears the current document if any. If called outside the GUI thread, the
	 * operation will be scheduled to be performed in the GUI thread.
	 */
	public void clearDocument() {
		if (SwingUtilities.isEventDispatchThread()) {
			clearDocumentImpl();
		} else {
			SwingUtilities.invokeLater(HtmlPanel.this::clearDocumentImpl);
		}
	}

	private void clearDocumentImpl() {
		final HTMLDocumentImpl prevDocument = (HTMLDocumentImpl) this.rootNode;
		if (prevDocument != null) {
			prevDocument.removeDocumentNotificationListener(this.notificationListener);
		}
		final NodeRenderer nr = this.nodeRenderer;
		if (nr != null) {
			nr.setRootNode(null);
		}
		this.rootNode = null;
		this.htmlBlockPanel = null;
		this.nodeRenderer = null;
		removeAll();
		revalidate();
		this.repaint();
	}

	/**
	 * Copies the current selection, if any, into the clipboard. This method has no
	 * effect in FRAMESETs at the moment.
	 *
	 * @return a boolean.
	 */
	public boolean copy() {
		final HtmlBlockPanel block = this.htmlBlockPanel;
		if (block != null) {
			return block.copy();
		} else {
			return false;
		}
	}

	/**
	 * Method invoked internally to create a {@link org.loboevolution.html.gui.HtmlBlockPanel}. It is made
	 * available so it can be overridden.
	 *
	 * @param ucontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @param rcontext a {@link org.loboevolution.http.HtmlRendererContext} object.
	 * @return a {@link org.loboevolution.html.gui.HtmlBlockPanel} object.
	 */
	protected HtmlBlockPanel createHtmlBlockPanel(UserAgentContext ucontext, HtmlRendererContext rcontext) {
		return new HtmlBlockPanel(java.awt.Color.WHITE, true, ucontext, rcontext, this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * Invalidates the layout of the given node and schedules it to be layed out
	 * later. Multiple invalidations may be processed in a single document layout.
	 */
	@Override
	public void delayedRelayout(NodeImpl node) {
		final List<DocumentNotification> notifs = this.notifications;
		synchronized (notifs) {
			notifs.add(new DocumentNotification(DocumentNotification.SIZE, node));
		}
		this.notificationTimer.restart();
	}

	/**
	 * {@inheritDoc}
	 *
	 * Internal method used to expand the selection to the given point.
	 * <p>
	 * Note: This method should be invoked in the GUI thread.
	 */
	@Override
	public void expandSelection(RenderableSpot rpoint) {
		final HtmlBlockPanel block = this.htmlBlockPanel;
		if (block != null) {
			block.setSelectionEnd(rpoint);
			block.repaint();
			this.selectionDispatch.fireEvent(new SelectionChangeEvent(this, block.isSelectionAvailable()));
		}
	}

	/**
	 * Gets the root Renderable of the HTML block. It returns
	 * null for FRAMESETs.
	 *
	 * @return a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
	 */
	public BoundableRenderable getBlockRenderable() {
		final HtmlBlockPanel htmlBlock = this.htmlBlockPanel;
		return htmlBlock == null ? null : htmlBlock.getRootRenderable();
	}


	/**
	 * Gets the HTML DOM node currently rendered if any.
	 *
	 * @return a {@link org.loboevolution.html.dom.domimpl.NodeImpl} object.
	 */
	public NodeImpl getRootNode() {
		return this.rootNode;
	}

	/**
	 * Gets a DOM node enclosing the selection. The node returned should be the
	 * inner-most node that encloses both selection start and end points. Note that
	 * the selection end point may be just outside of the selection.
	 * <p>
	 * Note: This method should be invoked in the GUI thread.
	 *
	 * @return A node enclosing the current selection, or null if there
	 *         is no such node. It also returns null for FRAMESETs.
	 */
	public Node getSelectionNode() {
		final HtmlBlockPanel block = this.htmlBlockPanel;
		if (block == null) {
			return null;
		} else {
			return block.getSelectionNode();
		}
	}

	/**
	 * Gets the selection text.
	 * <p>
	 * Note: This method should be invoked in the GUI thread.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSelectionText() {
		final HtmlBlockPanel block = this.htmlBlockPanel;
		if (block == null) {
			return null;
		} else {
			return block.getSelectionText();
		}
	}

	/**
	 * Returns true only if the current block has a selection. This method has no
	 * effect in FRAMESETs at the moment.
	 *
	 * @return a boolean.
	 */
	public boolean hasSelection() {
		final HtmlBlockPanel block = this.htmlBlockPanel;
		if (block == null) {
			return false;
		} else {
			return block.hasSelection();
		}
	}

	private void processNotifications() {
		// This is called in the GUI thread.
		final List<DocumentNotification> notifs = this.notifications;
		DocumentNotification[] notifsArray;
		synchronized (notifs) {
			final int size = notifs.size();
			if (size == 0) {
				return;
			}
			notifsArray = new DocumentNotification[size];
			notifsArray = (DocumentNotification[]) notifs.toArray(notifsArray);
			notifs.clear();
		}
		
		final HtmlBlockPanel blockPanel = this.htmlBlockPanel;
		if (blockPanel != null) {
			blockPanel.processDocumentNotifications(notifsArray);
		}
	}

	/**
	 * Removes a listener of selection changes that was previously added.
	 *
	 * @param listener a {@link org.loboevolution.html.gui.SelectionChangeListener} object.
	 */
	public void removeSelectionChangeListener(SelectionChangeListener listener) {
		this.selectionDispatch.removeListener(listener);
	}

	/**
	 * {@inheritDoc}
	 *
	 * Internal method used to reset the selection so that it is empty at the given
	 * point. This is what is called when the user clicks on a point in the
	 * document.
	 * <p>
	 * Note: This method should be invoked in the GUI thread.
	 */
	@Override
	public void resetSelection(RenderableSpot rpoint) {
		final HtmlBlockPanel block = this.htmlBlockPanel;
		if (block != null) {
			block.setSelectionStart(rpoint);
			block.setSelectionEnd(rpoint);
			block.repaint();
		}
		this.selectionDispatch.fireEvent(new SelectionChangeEvent(this, false));
	}

	/**
	 * Scrolls the document such that x and y coordinates are placed in the
	 * upper-left corner of the panel.
	 * <p>
	 * This method may be called outside of the GUI Thread.
	 *
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public void scroll(final int x, final int y) {
		if (SwingUtilities.isEventDispatchThread()) {
			scrollImpl(x, y);
		} else {
			SwingUtilities.invokeLater(() -> scrollImpl(x, y));
		}
	}

	/**
	 * <p>scrollBy.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 */
	public void scrollBy(final int x, final int y) {
		if (SwingUtilities.isEventDispatchThread()) {
			scrollByImpl(x, y);
		} else {
			SwingUtilities.invokeLater(() -> scrollByImpl(x, y));
		}
	}

	private void scrollByImpl(int xOffset, int yOffset) {
		final HtmlBlockPanel bp = this.htmlBlockPanel;
		if (bp != null) {
			bp.scrollBy(xOffset, yOffset);
		}
	}

	private void scrollImpl(int x, int y) {
		this.scrollTo(new Rectangle(x, y, 16, 16), false, false);
	}

	/**
	 * Scrolls the body area to the node given, if it is part of the current
	 * document.
	 * <p>
	 * This method should be called from the GUI thread.
	 *
	 * @param node A DOM node.
	 */
	public void scrollTo(org.w3c.dom.Node node) {
		final HtmlBlockPanel htmlBlock = this.htmlBlockPanel;
		if (htmlBlock != null) {
			htmlBlock.scrollTo(node);
		}
	}

	/**
	 * If the current document is not a FRAMESET, this method scrolls the body area
	 * to the given location.
	 * <p>
	 * This method should be called from the GUI thread.
	 *
	 * @param bounds    The bounds in the scrollable block area that should become
	 *                  visible.
	 * @param xIfNeeded If this parameter is true, scrolling will only occur if the
	 *                  requested bounds are not currently visible horizontally.
	 * @param yIfNeeded If this parameter is true, scrolling will only occur if the
	 *                  requested bounds are not currently visible vertically.
	 */
	public void scrollTo(Rectangle bounds, boolean xIfNeeded, boolean yIfNeeded) {
		final HtmlBlockPanel htmlBlock = this.htmlBlockPanel;
		if (htmlBlock != null) {
			htmlBlock.scrollTo(bounds, xIfNeeded, yIfNeeded);
		}
	}

	/**
	 * Scrolls to the element identified by the given ID in the current document.
	 * <p>
	 * If this method is invoked outside the GUI thread, the operation is scheduled
	 * to be performed as soon as possible in the GUI thread.
	 *
	 * @param nameOrId The name or ID of the element in the document.
	 */
	public void scrollToElement(final String nameOrId) {
		if (SwingUtilities.isEventDispatchThread()) {
			scrollToElementImpl(nameOrId);
		} else {
			SwingUtilities.invokeLater(() -> scrollToElementImpl(nameOrId));
		}
	}

	private void scrollToElementImpl(String nameOrId) {
		final NodeImpl node = this.rootNode;
		if (node instanceof HTMLDocumentImpl) {
			final HTMLDocumentImpl doc = (HTMLDocumentImpl) node;
			final org.w3c.dom.Element element = doc.getElementById(nameOrId);
			if (element != null) {
				this.scrollTo(element);
			}
		}
	}

	/**
	 * Sets the default horizontal overflow.
	 * <p>
	 * This method has no effect on FRAMESETs.
	 *
	 * @param overflow See {@link org.loboevolution.html.renderstate.RenderState}.
	 */
	public void setDefaultOverflowX(int overflow) {
		this.defaultOverflowX = overflow;
		final HtmlBlockPanel block = this.htmlBlockPanel;
		if (block != null) {
			block.setDefaultOverflowX(overflow);
		}
	}

	/**
	 * Sets the default vertical overflow.
	 * <p>
	 * This method has no effect on FRAMESETs.
	 *
	 * @param overflow See {@link org.loboevolution.html.renderstate.RenderState}.
	 */
	public void setDefaultOverflowY(int overflow) {
		this.defaultOverflowY = overflow;
		final HtmlBlockPanel block = this.htmlBlockPanel;
		if (block != null) {
			block.setDefaultOverflowY(overflow);
		}
	}

	/**
	 * Sets an HTML DOM node and invalidates the component so it is rendered as soon
	 * as possible in the GUI thread.
	 * <p>
	 * If this method is called from a thread that is not the GUI dispatch thread,
	 * the document is scheduled to be set later. Note that
	 * {@link #setPreferredWidth(int) preferred size} calculations should be done in
	 * the GUI dispatch thread for this reason.
	 *
	 * @param node     This should normally be a Document instance obtained with
	 *                 {@link org.loboevolution.html.parser.DocumentBuilderImpl}.
	 *                 <p>
	 * @param rcontext A renderer context.
	 * @see org.loboevolution.html.parser.DocumentBuilderImpl#parse(org.xml.sax.InputSource)
	 * 
	 */
	public void setDocument(final Document node, final HtmlRendererContext rcontext) {
		if (SwingUtilities.isEventDispatchThread()) {
			setDocumentImpl(node, rcontext);
		} else {
			SwingUtilities.invokeLater(() -> HtmlPanel.this.setDocumentImpl(node, rcontext));
		}
	}

	private void setDocumentImpl(Document node, HtmlRendererContext rcontext) {
		// Expected to be called in the GUI thread.
		if (!(node instanceof HTMLDocumentImpl)) {
			throw new IllegalArgumentException(
					"Only nodes of type HTMLDocumentImpl are currently supported. Use DocumentBuilderImpl.");
		}
		final HTMLDocumentImpl prevDocument = (HTMLDocumentImpl) this.rootNode;
		if (prevDocument != null) {
			prevDocument.removeDocumentNotificationListener(this.notificationListener);
		}
		final HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) node;
		nodeImpl.addDocumentNotificationListener(this.notificationListener);
		this.rootNode = nodeImpl;
		if (getComponentCount() == 0) {
			setUpAsBlock(rcontext.getUserAgentContext(), rcontext);
		}
		final NodeRenderer nr = this.nodeRenderer;
		if (nr != null) {
			nr.setRootNode(nodeImpl);

		} else {
			invalidate();
			validate();
			this.repaint();
		}
	}

	/**
	 * Renders HTML given as a string.
	 *
	 * @param htmlSource The HTML source code.
	 * @param uri        A base URI used to resolve item URIs.
	 * @param rcontext   The {@link org.loboevolution.http.HtmlRendererContext} instance.
	 * 
	 * @see #setDocument(Document, HtmlRendererContext)
	 */
	public void setHtml(String htmlSource, String uri, HtmlRendererContext rcontext) {
		try {
			final DocumentBuilderImpl builder = new DocumentBuilderImpl(rcontext.getUserAgentContext(), rcontext);
			try (Reader reader = new StringReader(htmlSource)) {
				final InputSourceImpl is = new InputSourceImpl(reader, uri);
				final Document document = builder.parse(is);
				setDocument(document, rcontext);
			}
		} catch (final java.io.IOException ioe) {
			throw new IllegalStateException("Unexpected condition.", ioe);
		} catch (final org.xml.sax.SAXException se) {
			throw new IllegalStateException("Unexpected condition.", se);
		}
	}
	
	/**
	 * <p>createHtmlPanel.</p>
	 *
	 * @param browserPanel a {@link org.loboevolution.component.IBrowserPanel} object.
	 * @param uri a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.gui.HtmlPanel} object.
	 */
	public static HtmlPanel createHtmlPanel(IBrowserPanel browserPanel, String uri) {
		final HtmlPanel panel = new HtmlPanel();
		panel.setBrowserPanel(browserPanel);
		try {
			final URL url = new URL(uri);
			final URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", HttpNetwork.getUserAgentValue());

			try (InputStream in = HttpNetwork.openConnectionCheckRedirects(connection);
					Reader reader = new InputStreamReader(in, "utf-8")) {

				final InputSource is = new InputSourceImpl(reader, uri);
				final UserAgentContext ucontext = new UserAgentContext();
				final HtmlRendererContext rendererContext = new HtmlRendererContext(panel, ucontext);
				panel.setPreferredSize(new Dimension(800, 400));
				final DocumentBuilderImpl builder = new DocumentBuilderImpl(rendererContext.getUserAgentContext(),rendererContext);
				final Document document = builder.parse(is);
				panel.setDocument(document, rendererContext);
			}

		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return panel;
	}

	/**
	 * Sets a preferred width that serves as a hint in calculating the preferred
	 * size of the HtmlPanel. Note that the preferred size can only be
	 * calculated when a document is available, and it will vary during incremental
	 * rendering.
	 * <p>
	 * This method currently does not have any effect when the document is a
	 * FRAMESET.
	 * <p>
	 * Note also that setting the preferred width (to a value other than
	 * -1) will negatively impact performance.
	 *
	 * @param width The preferred width, or -1 to unset.
	 */
	public void setPreferredWidth(int width) {
		this.preferredWidth = width;
		final HtmlBlockPanel htmlBlock = this.htmlBlockPanel;
		if (htmlBlock != null) {
			htmlBlock.setPreferredWidth(width);
		}
	}

	private void setUpAsBlock(UserAgentContext ucontext, HtmlRendererContext rcontext) {
		final HtmlBlockPanel shp = createHtmlBlockPanel(ucontext, rcontext);
		shp.setPreferredWidth(this.preferredWidth);
		shp.setDefaultOverflowX(this.defaultOverflowX);
		shp.setDefaultOverflowY(this.defaultOverflowY);
		this.htmlBlockPanel = shp;
		removeAll();
		this.add(shp);
		this.nodeRenderer = shp;
	}
	
	/**
	 * <p>Getter for the field browserPanel.</p>
	 *
	 * @return a {@link org.loboevolution.component.IBrowserPanel} object.
	 */
	public IBrowserPanel getBrowserPanel() {
		return browserPanel;
	}

	/**
	 * <p>Setter for the field browserPanel.</p>
	 *
	 * @param browserPanel a {@link org.loboevolution.component.IBrowserPanel} object.
	 */
	public void setBrowserPanel(IBrowserPanel browserPanel) {
		this.browserPanel = browserPanel;
	}
}
