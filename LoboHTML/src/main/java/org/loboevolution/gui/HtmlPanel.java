/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Nov 19, 2005
 */
package org.loboevolution.gui;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.EventDispatch2;
import org.loboevolution.common.WrapperLayout;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.dom.nodeimpl.event.DocumentNotificationListener;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.html.renderer.BoundableRenderable;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.NodeRenderer;
import org.loboevolution.html.renderer.RenderableSpot;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.net.HttpNetwork;
import org.xml.sax.InputSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serial;
import java.net.SocketTimeoutException;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

/**
 * The HtmlPanel class is a Swing component that can render a HTML
 * DOM. It uses either {@link HtmlBlockPanel}
 * internally, depending on whether the document is determined to be a FRAMESET
 * or not.
 * <p>
 * Invoke method {@link #setDocument(Document, HtmlRendererContext)} in order to
 * schedule a document for rendering.
 */
@Slf4j
public class HtmlPanel extends JComponent implements FrameContext {

	private final class LocalDocumentNotificationListener implements DocumentNotificationListener {
		@Override
		public void allInvalidated() {
			addNotification(new DocumentNotification(DocumentNotification.GENERIC, null));
		}

		@Override
		public void externalScriptLoading(final NodeImpl node) {
			// Ignorable here.
		}

		@Override
		public void invalidated(final NodeImpl node) {
			addNotification(new DocumentNotification(DocumentNotification.GENERIC, node));
		}

		@Override
		public void lookInvalidated(final NodeImpl node) {
			addNotification(new DocumentNotification(DocumentNotification.LOOK, node));
		}

		@Override
		public void nodeLoaded(final NodeImpl node) {
			addNotification(new DocumentNotification(DocumentNotification.GENERIC, node));
		}

		@Override
		public void positionInvalidated(final NodeImpl node) {
			addNotification(new DocumentNotification(DocumentNotification.POSITION, node));
		}

		@Override
		public void sizeInvalidated(final NodeImpl node) {
			addNotification(new DocumentNotification(DocumentNotification.SIZE, node));
		}

		@Override
		public void structureInvalidated(final NodeImpl node) {
			addNotification(new DocumentNotification(DocumentNotification.GENERIC, node));
		}
	}

	private final class NotificationTimerAction implements java.awt.event.ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			processNotifications();
		}
	}

	private final static class SelectionDispatch extends EventDispatch2 {

		@Override
		protected void dispatchEvent(final EventListener listener, final EventObject event) {
			((SelectionChangeListener) listener).selectionChanged((SelectionChangeEvent) event);
		}
	}

	private static final int NOTIF_TIMER_DELAY = 300;

	@Serial
    private static final long serialVersionUID = 1L;

	private volatile int defaultOverflowX = RenderState.OVERFLOW_AUTO;
	
	private volatile int defaultOverflowY = RenderState.OVERFLOW_AUTO;
	
	protected volatile HtmlBlockPanel htmlBlockPanel;
	
	private volatile NodeRenderer nodeRenderer = null;
	
	private final Runnable notificationImmediateAction;

	private final DocumentNotificationListener notificationListener;
	
	private final List<DocumentNotification> notifications = new ArrayList<>();

	private final Timer notificationTimer;

	private volatile int preferredWidth = -1;

	@Getter
	private volatile NodeImpl rootNode;

	private final EventDispatch2 selectionDispatch = new SelectionDispatch();

	@Getter
	@Setter
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

	private void addNotification(final DocumentNotification notification) {
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
	 * @param listener An instance of {@link SelectionChangeListener}.
	 */
	public void addSelectionChangeListener(final SelectionChangeListener listener) {
		this.selectionDispatch.addListener(listener);
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
	 * Method invoked internally to create a {@link HtmlBlockPanel}. It is made
	 * available so it can be overridden.
	 *
	 * @param ucontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @param rcontext a {@link HtmlRendererContext} object.
	 * @return a {@link HtmlBlockPanel} object.
	 */
	protected HtmlBlockPanel createHtmlBlockPanel(final UserAgentContext ucontext, final HtmlRendererContext rcontext) {
		return new HtmlBlockPanel(Color.WHITE, true, ucontext, rcontext, this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * Invalidates the layout of the given node and schedules it to be layed out
	 * later. Multiple invalidations may be processed in a single document layout.
	 */
	@Override
	public void delayedRelayout(final NodeImpl node) {
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
	public void expandSelection(final RenderableSpot rpoint) {
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
			notifsArray = notifs.toArray(notifsArray);
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
	 * @param listener a {@link SelectionChangeListener} object.
	 */
	public void removeSelectionChangeListener(final SelectionChangeListener listener) {
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
	public void resetSelection(final RenderableSpot rpoint) {
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
	public void scroll(final double x, final double y) {
		if (SwingUtilities.isEventDispatchThread()) {
			scrollImpl(x, y);
		} else {
			SwingUtilities.invokeLater(() -> scrollImpl(x, y));
		}
	}

	/**
	 * <p>scrollBy.</p>
	 *
	 * @param x a {@link java.lang.Double} object.
	 * @param y a {@link java.lang.Double} object.
	 */
	public void scrollBy(final double x, final double y) {
		if (SwingUtilities.isEventDispatchThread()) {
			scrollByImpl(x, y);
		} else {
			SwingUtilities.invokeLater(() -> scrollByImpl(x, y));
		}
	}

	private void scrollByImpl(final double xOffset, final double yOffset) {
		final HtmlBlockPanel bp = this.htmlBlockPanel;
		if (bp != null) {
			bp.scrollBy(xOffset, yOffset);
		}
	}

	private void scrollImpl(final double x, final double y) {
		this.scrollTo(new Rectangle((int)x, (int)y, 16, 16), false, false);
	}

	/**
	 * Scrolls the body area to the node given, if it is part of the current
	 * document.
	 * <p>
	 * This method should be called from the GUI thread.
	 *
	 * @param node A DOM node.
	 */
	public void scrollTo(final Node node) {
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
	public void scrollTo(final Rectangle bounds, final boolean xIfNeeded, final boolean yIfNeeded) {
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

	private void scrollToElementImpl(final String nameOrId) {
		final NodeImpl node = this.rootNode;
		if (node instanceof HTMLDocument doc) {
            final Element element = doc.getElementById(nameOrId);
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
	 * @param overflow See {@link RenderState}.
	 */
	public void setDefaultOverflowX(final int overflow) {
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
	 * @param overflow See {@link RenderState}.
	 */
	public void setDefaultOverflowY(final int overflow) {
		this.defaultOverflowY = overflow;
		final HtmlBlockPanel block = this.htmlBlockPanel;
		if (block != null) {
			block.setDefaultOverflowY(overflow);
		}
	}

	public static HtmlPanel createlocalPanel(final URLConnection connection, final HtmlPanel panel, final HtmlRendererContext rendererContext,
                                             final HtmlRendererConfig config, final String uri) throws Exception {
		try (final InputStream in = HttpNetwork.openConnectionCheckRedirects(connection);
             final Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
			final InputSource is = new InputSourceImpl(reader, uri);
			final DocumentBuilderImpl builder = new DocumentBuilderImpl(rendererContext.getUserAgentContext(),rendererContext, config);
			final Document document = builder.parse(is);
			panel.setDocument(document, rendererContext);
		} catch (final SocketTimeoutException e) {
			log.error("More time elapsed {}", connection.getConnectTimeout());
		}
		return panel;
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
	 */
	public void setDocument(final Document node, final HtmlRendererContext rcontext) {
		if (SwingUtilities.isEventDispatchThread()) {
			setDocumentImpl(node, rcontext);
		} else {
			SwingUtilities.invokeLater(() -> HtmlPanel.this.setDocumentImpl(node, rcontext));
		}
	}

	private void setDocumentImpl(final Document node, final HtmlRendererContext rcontext) {
		// Expected to be called in the GUI thread.
		if (!(node instanceof HTMLDocument)) {
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
	public void setPreferredWidth(final int width) {
		this.preferredWidth = width;
		final HtmlBlockPanel htmlBlock = this.htmlBlockPanel;
		if (htmlBlock != null) {
			htmlBlock.setPreferredWidth(width);
		}
	}

	private void setUpAsBlock(final UserAgentContext ucontext, final HtmlRendererContext rcontext) {
		final HtmlBlockPanel shp = createHtmlBlockPanel(ucontext, rcontext);
		shp.setPreferredWidth(this.preferredWidth);
		shp.setDefaultOverflowX(this.defaultOverflowX);
		shp.setDefaultOverflowY(this.defaultOverflowY);
		this.htmlBlockPanel = shp;
		removeAll();
		this.add(shp);
		this.nodeRenderer = shp;
	}
}
