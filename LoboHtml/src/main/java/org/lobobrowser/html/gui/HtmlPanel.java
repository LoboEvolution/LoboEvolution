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
package org.lobobrowser.html.gui;

import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.lobo.common.Strings;
import org.lobobrowser.html.dom.HTMLFrameSetElement;
import org.lobobrowser.html.domimpl.DocumentNotificationListener;
import org.lobobrowser.html.domimpl.ElementImpl;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.NodeImpl;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.renderer.BoundableRenderable;
import org.lobobrowser.html.renderer.FrameContext;
import org.lobobrowser.html.renderer.NodeRenderer;
import org.lobobrowser.html.renderer.RenderableSpot;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.http.HtmlRendererContext;
import org.lobobrowser.http.UserAgentContext;
import org.lobo.common.EventDispatch2;
import org.lobo.common.WrapperLayout;
import org.w3c.dom.Document;
import org.w3c.dom.Text;

/**
 * The <code>HtmlPanel</code> class is a Swing component that can render a HTML
 * DOM. It uses either {@link HtmlBlockPanel} or {@link FrameSetPanel}
 * internally, depending on whether the document is determined to be a FRAMESET
 * or not.
 * <p>
 * Invoke method {@link #setDocument(Document, HtmlRendererContext)} in order to
 * schedule a document for rendering.
 */
public class HtmlPanel extends JComponent implements FrameContext {
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

	private class SelectionDispatch extends EventDispatch2 {

		@Override
		protected void dispatchEvent(EventListener listener, EventObject event) {
			((SelectionChangeListener) listener).selectionChanged((SelectionChangeEvent) event);
		}
	}

	private static final int NOTIF_TIMER_DELAY = 300;

	private static final long serialVersionUID = 1L;

	private volatile int defaultOverflowX = RenderState.OVERFLOW_AUTO;
	private volatile int defaultOverflowY = RenderState.OVERFLOW_AUTO;
	protected volatile FrameSetPanel frameSetPanel;
	protected volatile HtmlBlockPanel htmlBlockPanel;
	private volatile boolean isFrameSet = false;
	private volatile NodeRenderer nodeRenderer = null;
	private final Runnable notificationImmediateAction;

	private final DocumentNotificationListener notificationListener;
	private final ArrayList<DocumentNotification> notifications = new ArrayList<DocumentNotification>();

	private final Timer notificationTimer;

	private volatile int preferredWidth = -1;

	private volatile NodeImpl rootNode;

	private final EventDispatch2 selectionDispatch = new SelectionDispatch();

	/**
	 * Constructs an <code>HtmlPanel</code>.
	 */
	public HtmlPanel() {
		setLayout(WrapperLayout.getInstance());
		setOpaque(false);
		this.notificationTimer = new Timer(NOTIF_TIMER_DELAY, new NotificationTimerAction());
		this.notificationTimer.setRepeats(false);
		this.notificationListener = new LocalDocumentNotificationListener();
		this.notificationImmediateAction = () -> processNotifications();
	}

	private void addNotification(DocumentNotification notification) {
		// This can be called in a random thread.
		final ArrayList<DocumentNotification> notifs = this.notifications;
		synchronized (notifs) {
			notifs.add(notification);
		}
		if (SwingUtilities.isEventDispatchThread()) {
			// In this case we want the notification to be processed
			// immediately. However, we don't want potential recursions
			// to occur when a Javascript property is set in the GUI thread.
			// Additionally, many property values may be set in one
			// event block.
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
			SwingUtilities.invokeLater(() -> HtmlPanel.this.clearDocumentImpl());
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
		this.isFrameSet = false;
		removeAll();
		revalidate();
		this.repaint();
	}

	/**
	 * Copies the current selection, if any, into the clipboard. This method has no
	 * effect in FRAMESETs at the moment.
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
	 * Method invoked internally to create a {@link FrameSetPanel}. It is made
	 * available so it can be overridden.
	 */
	protected FrameSetPanel createFrameSetPanel() {
		return new FrameSetPanel();
	}

	/**
	 * Method invoked internally to create a {@link HtmlBlockPanel}. It is made
	 * available so it can be overridden.
	 */
	protected HtmlBlockPanel createHtmlBlockPanel(UserAgentContext ucontext, HtmlRendererContext rcontext) {
		return new HtmlBlockPanel(java.awt.Color.WHITE, true, ucontext, rcontext, this);
	}

	/**
	 * Invalidates the layout of the given node and schedules it to be layed out
	 * later. Multiple invalidations may be processed in a single document layout.
	 */
	@Override
	public void delayedRelayout(NodeImpl node) {
		final ArrayList<DocumentNotification> notifs = this.notifications;
		synchronized (notifs) {
			notifs.add(new DocumentNotification(DocumentNotification.SIZE, node));
		}
		this.notificationTimer.restart();
	}

	/**
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
	 * Gets the root <code>Renderable</code> of the HTML block. It returns
	 * <code>null</code> for FRAMESETs.
	 */
	public BoundableRenderable getBlockRenderable() {
		final HtmlBlockPanel htmlBlock = this.htmlBlockPanel;
		return htmlBlock == null ? null : htmlBlock.getRootRenderable();
	}

	private NodeImpl getFrameSet(NodeImpl node) {
		final NodeImpl[] children = node.getChildrenArray();
		if (children == null) {
			return null;
		}
		final int length = children.length;
		NodeImpl frameSet = null;
		for (int i = 0; i < length; i++) {
			final NodeImpl child = children[i];
			if (child instanceof Text) {
				// Ignore
			} else if (child instanceof ElementImpl) {
				final String tagName = child.getNodeName();
				if ("HEAD".equalsIgnoreCase(tagName) || "NOFRAMES".equalsIgnoreCase(tagName)
						|| "TITLE".equalsIgnoreCase(tagName) || "META".equalsIgnoreCase(tagName)
						|| "SCRIPT".equalsIgnoreCase(tagName) || "NOSCRIPT".equalsIgnoreCase(tagName)) {
					// ignore it
				} else if ("FRAMESET".equalsIgnoreCase(tagName)) {
					frameSet = child;
					break;
				} else {
					if (hasSomeHtml((ElementImpl) child)) {
						return null;
					}
				}
			}
		}
		return frameSet;
	}

	/**
	 * Gets an instance of {@link FrameSetPanel} in case the currently rendered page
	 * is a FRAMESET.
	 * <p>
	 * Note: This method should be invoked in the GUI thread.
	 * 
	 * @return A <code>FrameSetPanel</code> instance or <code>null</code> if the
	 *         document currently rendered is not a FRAMESET.
	 */
	public FrameSetPanel getFrameSetPanel() {
		final int componentCount = getComponentCount();
		if (componentCount == 0) {
			return null;
		}
		final Object c = getComponent(0);
		if (c instanceof FrameSetPanel) {
			return (FrameSetPanel) c;
		}
		return null;
	}

	private NodeImpl getFrameSetRootNode(NodeImpl node) {
		if (node instanceof Document) {
			final ElementImpl element = (ElementImpl) ((Document) node).getDocumentElement();
			if (element != null && "HTML".equalsIgnoreCase(element.getTagName())) {
				return getFrameSet(element);
			} else {
				return getFrameSet(node);
			}
		} else {
			return null;
		}
	}

	/**
	 * Gets the HTML DOM node currently rendered if any.
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
	 * @return A node enclosing the current selection, or <code>null</code> if there
	 *         is no such node. It also returns <code>null</code> for FRAMESETs.
	 */
	public org.w3c.dom.Node getSelectionNode() {
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
	 */
	public boolean hasSelection() {
		final HtmlBlockPanel block = this.htmlBlockPanel;
		if (block == null) {
			return false;
		} else {
			return block.hasSelection();
		}
	}

	private boolean hasSomeHtml(ElementImpl element) {
		final String tagName = element.getTagName();
		if ("HEAD".equalsIgnoreCase(tagName) || "TITLE".equalsIgnoreCase(tagName) || "META".equalsIgnoreCase(tagName)) {
			return false;
		}
		final NodeImpl[] children = element.getChildrenArray();
		if (children != null) {
			final int length = children.length;
			for (int i = 0; i < length; i++) {
				final NodeImpl child = children[i];
				if (child instanceof Text) {
					final String textContent = ((Text) child).getTextContent();
					if (Strings.isNotBlank(textContent)) {
						return false;
					}
				} else if (child instanceof ElementImpl) {
					if (hasSomeHtml((ElementImpl) child)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private void processNotifications() {
		// This is called in the GUI thread.
		final ArrayList<DocumentNotification> notifs = this.notifications;
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
		final int length = notifsArray.length;
		for (int i = 0; i < length; i++) {
			final DocumentNotification dn = notifsArray[i];
			if (dn.node instanceof HTMLFrameSetElement && this.htmlBlockPanel != null) {
				if (resetIfFrameSet()) {
					// Revalidation already taken care of.
					return;
				}
			}
		}
		final HtmlBlockPanel blockPanel = this.htmlBlockPanel;
		if (blockPanel != null) {
			blockPanel.processDocumentNotifications(notifsArray);
		}
		final FrameSetPanel frameSetPanel = this.frameSetPanel;
		if (frameSetPanel != null) {
			frameSetPanel.processDocumentNotifications(notifsArray);
		}
	}

	/**
	 * Removes a listener of selection changes that was previously added.
	 */
	public void removeSelectionChangeListener(SelectionChangeListener listener) {
		this.selectionDispatch.removeListener(listener);
	}

	private boolean resetIfFrameSet() {
		final NodeImpl nodeImpl = this.rootNode;
		final NodeImpl fsrn = getFrameSetRootNode(nodeImpl);
		final boolean newIfs = fsrn != null;
		if (newIfs != this.isFrameSet || getComponentCount() == 0) {
			this.isFrameSet = newIfs;
			if (newIfs) {
				setUpFrameSet(fsrn);
				final NodeRenderer nr = this.nodeRenderer;
				nr.setRootNode(fsrn);
				// Set proper bounds and repaint.
				validate();
				this.repaint();
				return true;
			}
		}
		return false;
	}

	/**
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
	 * @param overflow See {@link org.lobobrowser.html.style.RenderState}.
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
	 * @param overflow See {@link org.lobobrowser.html.style.RenderState}.
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
	 *                 {@link org.lobobrowser.html.parser.DocumentBuilderImpl}.
	 *                 <p>
	 * @param rcontext A renderer context.
	 * @see org.lobobrowser.html.parser.DocumentBuilderImpl#parse(org.xml.sax.InputSource)
	 * @see org.lobobrowser.html.test.SimpleHtmlRendererContext
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
		final NodeImpl fsrn = getFrameSetRootNode(nodeImpl);
		final boolean newIfs = fsrn != null;
		if (newIfs != this.isFrameSet || getComponentCount() == 0) {
			this.isFrameSet = newIfs;
			if (newIfs) {
				setUpFrameSet(fsrn);
			} else {
				setUpAsBlock(rcontext.getUserAgentContext(), rcontext);
			}
		}
		final NodeRenderer nr = this.nodeRenderer;
		if (nr != null) {
			// These subcomponents should take care
			// of revalidation.
			if (newIfs) {
				nr.setRootNode(fsrn);
			} else {
				nr.setRootNode(nodeImpl);
			}
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
	 * @param rcontext   The {@link HtmlRendererContext} instance.
	 * @see org.lobobrowser.html.test.SimpleHtmlRendererContext
	 * @see #setDocument(Document, HtmlRendererContext)
	 */
	public void setHtml(String htmlSource, String uri, HtmlRendererContext rcontext) {
		try {
			final DocumentBuilderImpl builder = new DocumentBuilderImpl(rcontext.getUserAgentContext(), rcontext);
			final Reader reader = new StringReader(htmlSource);
			try {
				final InputSourceImpl is = new InputSourceImpl(reader, uri);
				final Document document = builder.parse(is);
				setDocument(document, rcontext);
			} finally {
				reader.close();
			}
		} catch (final java.io.IOException ioe) {
			throw new IllegalStateException("Unexpected condition.", ioe);
		} catch (final org.xml.sax.SAXException se) {
			throw new IllegalStateException("Unexpected condition.", se);
		}
	}

	/**
	 * Sets a preferred width that serves as a hint in calculating the preferred
	 * size of the <code>HtmlPanel</code>. Note that the preferred size can only be
	 * calculated when a document is available, and it will vary during incremental
	 * rendering.
	 * <p>
	 * This method currently does not have any effect when the document is a
	 * FRAMESET.
	 * <p>
	 * Note also that setting the preferred width (to a value other than
	 * <code>-1</code>) will negatively impact performance.
	 * 
	 * @param width The preferred width, or <code>-1</code> to unset.
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
		this.frameSetPanel = null;
		removeAll();
		this.add(shp);
		this.nodeRenderer = shp;
	}

	private void setUpFrameSet(NodeImpl fsrn) {
		this.isFrameSet = true;
		this.htmlBlockPanel = null;
		final FrameSetPanel fsp = createFrameSetPanel();
		this.frameSetPanel = fsp;
		this.nodeRenderer = fsp;
		removeAll();
		this.add(fsp);
		fsp.setRootNode(fsrn);
	}
}
