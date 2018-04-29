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
/*
 * Created on Nov 19, 2005
 */
package org.loboevolution.html.gui;

import java.awt.Insets;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.dombl.DocumentNotificationListener;
import org.loboevolution.html.domimpl.DOMElementImpl;
import org.loboevolution.html.domimpl.DOMNodeImpl;
import org.loboevolution.html.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.html.renderer.BoundableRenderable;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.NodeRenderer;
import org.loboevolution.html.renderer.RenderableSpot;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.util.EventDispatch2;
import org.loboevolution.util.gui.WrapperLayout;
import org.loboevolution.w3c.html.HTMLFrameSetElement;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

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

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The selection dispatch. */
	private final transient EventDispatch2 selectionDispatch = new SelectionDispatch();

	/** The notification timer. */
	private final transient Timer notificationTimer;

	/** The notification listener. */
	private final transient DocumentNotificationListener notificationListener;

	/** The notification immediate action. */
	private final transient Runnable notificationImmediateAction;

	/** The Constant NOTIF_TIMER_DELAY. */
	private static final int NOTIF_TIMER_DELAY = 300;

	/** The is frame set. */
	private volatile boolean isFrameSet = false;

	/** The node renderer. */
	private transient volatile NodeRenderer nodeRenderer = null;

	/** The root node. */
	private transient volatile DOMNodeImpl rootNode;

	/** The preferred width. */
	private volatile int preferredWidth = -1;

	/** The default margin insets. */
	private volatile Insets defaultMarginInsets = new Insets(8, 8, 8, 8);

	/** The default overflow x. */
	private volatile int defaultOverflowX = RenderState.OVERFLOW_AUTO;

	/** The default overflow y. */
	private volatile int defaultOverflowY = RenderState.OVERFLOW_AUTO;

	/** The html block panel. */
	protected transient volatile HtmlBlockPanel htmlBlockPanel;

	/** The frame set panel. */
	protected transient volatile FrameSetPanel frameSetPanel;
	
	/** The notifications. */
	private ArrayList<DocumentNotification> notifications = new ArrayList<DocumentNotification>(1);

	/**
	 * Constructs an <code>HtmlPanel</code>.
	 */
	public HtmlPanel() {
		super();
		this.notificationTimer = new Timer(NOTIF_TIMER_DELAY, new NotificationTimerAction());
		this.notificationListener = new LocalDocumentNotificationListener();
		this.notificationImmediateAction = () -> processNotifications();
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		this.setLayout(WrapperLayout.getInstance());
		this.setOpaque(false);
		this.notificationTimer.setRepeats(false);
	}

	/**
	 * Sets the preferred width.
	 *
	 * @param width
	 *            the new preferred width
	 */
	public void setPreferredWidth(int width) {
		this.preferredWidth = width;
		HtmlBlockPanel htmlBlock = this.htmlBlockPanel;
		if (htmlBlock != null) {
			htmlBlock.setPreferredWidth(width);
		}
	}

	/**
	 * If the current document is not a FRAMESET, this method scrolls the body
	 * area to the given location.
	 * <p>
	 * This method should be called from the GUI thread.
	 *
	 * @param bounds
	 *            The bounds in the scrollable block area that should become
	 *            visible.
	 * @param xIfNeeded
	 *            If this parameter is true, scrolling will only occur if the
	 *            requested bounds are not currently visible horizontally.
	 * @param yIfNeeded
	 *            If this parameter is true, scrolling will only occur if the
	 *            requested bounds are not currently visible vertically.
	 */
	public void scrollTo(Rectangle bounds, boolean xIfNeeded, boolean yIfNeeded) {
		HtmlBlockPanel htmlBlock = this.htmlBlockPanel;
		if (htmlBlock != null) {
			htmlBlock.scrollTo(bounds, xIfNeeded, yIfNeeded);
		}
	}

	/**
	 * Scrolls the body area to the node given, if it is part of the current
	 * document.
	 * <p>
	 * This method should be called from the GUI thread.
	 *
	 * @param node
	 *            A DOM node.
	 */
	public void scrollTo(Node node) {
		HtmlBlockPanel htmlBlock = this.htmlBlockPanel;
		if (htmlBlock != null) {
			htmlBlock.scrollTo(node);
		}
	}

	/**
	 * Gets the block renderable.
	 *
	 * @return the block renderable
	 */
	public BoundableRenderable getBlockRenderable() {
		HtmlBlockPanel htmlBlock = this.htmlBlockPanel;
		return htmlBlock == null ? null : htmlBlock.getRootRenderable();
	}

	/**
	 * Gets the frame set panel.
	 *
	 * @return the frame set panel
	 */
	public FrameSetPanel getFrameSetPanel() {
		int componentCount = this.getComponentCount();
		if (componentCount == 0) {
			return null;
		}
		Object c = this.getComponent(0);
		if (c instanceof FrameSetPanel) {
			return (FrameSetPanel) c;
		}
		return null;
	}

	/**
	 * Sets the up as block.
	 *
	 * @param ucontext
	 *            the ucontext
	 * @param rcontext
	 *            the rcontext
	 */
	private void setUpAsBlock(UserAgentContext ucontext, HtmlRendererContext rcontext) {
		HtmlBlockPanel shp = this.createHtmlBlockPanel(ucontext, rcontext);
		shp.setPreferredWidth(this.preferredWidth);
		shp.setDefaultMarginInsets(this.defaultMarginInsets);
		shp.setDefaultOverflowX(this.defaultOverflowX);
		shp.setDefaultOverflowY(this.defaultOverflowY);
		this.htmlBlockPanel = shp;
		this.frameSetPanel = null;
		this.removeAll();
		this.add(shp);
		this.nodeRenderer = shp;
	}

	/**
	 * Sets the up frame set.
	 *
	 * @param fsrn
	 *            the new up frame set
	 */
	private void setUpFrameSet(DOMNodeImpl fsrn) {
		this.isFrameSet = true;
		this.htmlBlockPanel = null;
		FrameSetPanel fsp = this.createFrameSetPanel();
		this.frameSetPanel = fsp;
		this.nodeRenderer = fsp;
		this.removeAll();
		this.add(fsp);
		fsp.setRootNode(fsrn);
	}

	/**
	 * Method invoked internally to create a {@link HtmlBlockPanel}. It is made
	 * available so it can be overridden.
	 *
	 * @param ucontext
	 *            the ucontext
	 * @param rcontext
	 *            the rcontext
	 * @return the html block panel
	 */
	protected HtmlBlockPanel createHtmlBlockPanel(UserAgentContext ucontext, HtmlRendererContext rcontext) {
		return new HtmlBlockPanel(java.awt.Color.WHITE, true, ucontext, rcontext, this);
	}

	/**
	 * Method invoked internally to create a {@link FrameSetPanel}. It is made
	 * available so it can be overridden.
	 *
	 * @return the frame set panel
	 */
	protected FrameSetPanel createFrameSetPanel() {
		return new FrameSetPanel();
	}

	/**
	 * Scrolls the document such that x and y coordinates are placed in the
	 * upper-left corner of the panel.
	 * <p>
	 * This method may be called outside of the GUI Thread.
	 *
	 * @param x
	 *            The x coordinate.
	 * @param y
	 *            The y coordinate.
	 */
	public void scroll(final int x, final int y) {
		if (SwingUtilities.isEventDispatchThread()) {
			this.scrollImpl(x, y);
		} else {
			SwingUtilities.invokeLater(() -> scrollImpl(x, y));
		}
	}

	/**
	 * Scroll by.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void scrollBy(final int x, final int y) {
		if (SwingUtilities.isEventDispatchThread()) {
			this.scrollByImpl(x, y);
		} else {
			SwingUtilities.invokeLater(() -> scrollByImpl(x, y));
		}
	}

	/**
	 * Scroll impl.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	private void scrollImpl(int x, int y) {
		this.scrollTo(new Rectangle(x, y, 16, 16), false, false);
	}

	/**
	 * Scroll by impl.
	 *
	 * @param xOffset
	 *            the x offset
	 * @param yOffset
	 *            the y offset
	 */
	private void scrollByImpl(int xOffset, int yOffset) {
		HtmlBlockPanel bp = this.htmlBlockPanel;
		if (bp != null) {
			bp.scrollBy(xOffset, yOffset);
		}
	}

	/**
	 * Clears the current document if any. If called outside the GUI thread, the
	 * operation will be scheduled to be performed in the GUI thread.
	 */
	public void clearDocument() {
		if (SwingUtilities.isEventDispatchThread()) {
			this.clearDocumentImpl();
		} else {
			SwingUtilities.invokeLater(() -> HtmlPanel.this.clearDocumentImpl());
		}
	}

	/**
	 * Clear document impl.
	 */
	private void clearDocumentImpl() {
		HTMLDocumentImpl prevDocument = (HTMLDocumentImpl) this.rootNode;
		if (prevDocument != null) {
			prevDocument.removeDocumentNotificationListener(this.notificationListener);
		}
		NodeRenderer nr = this.nodeRenderer;
		if (nr != null) {
			nr.setRootNode(null);
		}
		this.rootNode = null;
		this.htmlBlockPanel = null;
		this.nodeRenderer = null;
		this.isFrameSet = false;
		this.removeAll();
		this.revalidate();
		this.repaint();
	}

	/**
	 * Sets an HTML DOM node and invalidates the component so it is rendered as
	 * soon as possible in the GUI thread.
	 * <p>
	 * If this method is called from a thread that is not the GUI dispatch
	 * thread, the document is scheduled to be set later. Note that
	 * {@link #setPreferredWidth(int) preferred size} calculations should be
	 * done in the GUI dispatch thread for this reason.
	 *
	 * @param node
	 *            This should normally be a Document instance obtained with
	 *            {@link org.loboevolution.html.parser.DocumentBuilderImpl}.
	 *            <p>
	 * @param rcontext
	 *            A renderer context.
	 * @see org.loboevolution.html.parser.DocumentBuilderImpl#parse(org.xml.sax.InputSource)
	 * @see org.loboevolution.html.test.SimpleHtmlRendererContext
	 */
	public void setDocument(final Document node, final HtmlRendererContext rcontext) {
		if (SwingUtilities.isEventDispatchThread()) {
			this.setDocumentImpl(node, rcontext);
		} else {
			SwingUtilities.invokeLater(() -> HtmlPanel.this.setDocumentImpl(node, rcontext));
		}
	}

	/**
	 * Scrolls to the element identified by the given ID in the current
	 * document.
	 * <p>
	 * If this method is invoked outside the GUI thread, the operation is
	 * scheduled to be performed as soon as possible in the GUI thread.
	 *
	 * @param nameOrId
	 *            The name or ID of the element in the document.
	 */
	public void scrollToElement(final String nameOrId) {
		if (SwingUtilities.isEventDispatchThread()) {
			this.scrollToElementImpl(nameOrId);
		} else {
			SwingUtilities.invokeLater(() -> scrollToElementImpl(nameOrId));
		}
	}

	/**
	 * Scroll to element impl.
	 *
	 * @param nameOrId
	 *            the name or id
	 */
	private void scrollToElementImpl(String nameOrId) {
		DOMNodeImpl node = this.rootNode;
		if (node instanceof HTMLDocumentImpl) {
			HTMLDocumentImpl doc = (HTMLDocumentImpl) node;
			org.w3c.dom.Element element = doc.getElementById(nameOrId);
			if (element != null) {
				this.scrollTo(element);
			}
		}
	}

	/**
	 * Sets the document impl.
	 *
	 * @param node
	 *            the node
	 * @param rcontext
	 *            the rcontext
	 */
	private void setDocumentImpl(Document node, HtmlRendererContext rcontext) {
		// Expected to be called in the GUI thread.
		if (!(node instanceof HTMLDocumentImpl)) {
			throw new IllegalArgumentException(
					"Only nodes of type HTMLDocumentImpl are currently supported. Use DocumentBuilderImpl.");
		}
		HTMLDocumentImpl prevDocument = (HTMLDocumentImpl) this.rootNode;
		if (prevDocument != null) {
			prevDocument.removeDocumentNotificationListener(this.notificationListener);
		}
		HTMLDocumentImpl nodeImpl = (HTMLDocumentImpl) node;
		nodeImpl.addDocumentNotificationListener(this.notificationListener);
		this.rootNode = nodeImpl;
		DOMNodeImpl fsrn = this.getFrameSetRootNode(nodeImpl);
		boolean newIfs = fsrn != null;
		if (newIfs != this.isFrameSet || this.getComponentCount() == 0) {
			this.isFrameSet = newIfs;
			if (newIfs) {
				this.setUpFrameSet(fsrn);
			} else {
				this.setUpAsBlock(rcontext.getUserAgentContext(), rcontext);
			}
		}
		NodeRenderer nr = this.nodeRenderer;
		if (nr != null) {
			// These subcomponents should take care
			// of revalidation.
			if (newIfs) {
				nr.setRootNode(fsrn);
			} else {
				nr.setRootNode(nodeImpl);
			}
		} else {
			this.invalidate();
			this.validate();
			this.repaint();
		}
	}

	/**
	 * Renders HTML given as a string.
	 *
	 * @param htmlSource
	 *            The HTML source code.
	 * @param uri
	 *            A base URI used to resolve item URIs.
	 * @param rcontext
	 *            The {@link HtmlRendererContext} instance.
	 * @see org.loboevolution.html.test.SimpleHtmlRendererContext
	 * @see #setDocument(Document, HtmlRendererContext)
	 */
	public void setHtml(String htmlSource, String uri, HtmlRendererContext rcontext) {
		try {
			DocumentBuilderImpl builder = new DocumentBuilderImpl(rcontext.getUserAgentContext(), rcontext);
			Reader reader = new StringReader(htmlSource);
			try {
				InputSourceImpl is = new InputSourceImpl(reader, uri);
				Document document = builder.parse(is);
				this.setDocument(document, rcontext);
			} finally {
				reader.close();
			}
		} catch (IOException ioe) {
			throw new IllegalStateException("Unexpected condition.", ioe);
		} catch (SAXException se) {
			throw new IllegalStateException("Unexpected condition.", se);
		}
	}

	/**
	 * Gets the root node.
	 *
	 * @return the root node
	 */
	public DOMNodeImpl getRootNode() {
		return this.rootNode;
	}

	/**
	 * Reset if frame set.
	 *
	 * @return true, if successful
	 */
	private boolean resetIfFrameSet() {
		DOMNodeImpl dOMNodeImpl = this.rootNode;
		DOMNodeImpl fsrn = this.getFrameSetRootNode(dOMNodeImpl);
		boolean newIfs = fsrn != null;
		if (newIfs != this.isFrameSet || this.getComponentCount() == 0) {
			this.isFrameSet = newIfs;
			if (newIfs) {
				this.setUpFrameSet(fsrn);
				NodeRenderer nr = this.nodeRenderer;
				nr.setRootNode(fsrn);
				// Set proper bounds and repaint.
				this.validate();
				this.repaint();
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the frame set root node.
	 *
	 * @param node
	 *            the node
	 * @return the frame set root node
	 */
	private DOMNodeImpl getFrameSetRootNode(DOMNodeImpl node) {
		if (node instanceof Document) {
			DOMElementImpl element = (DOMElementImpl) ((Document) node).getDocumentElement();
			if (element != null && "HTML".equalsIgnoreCase(element.getTagName())) {
				return this.getFrameSet(element);
			} else {
				return this.getFrameSet(node);
			}
		} else {
			return null;
		}
	}

	/**
	 * Gets the frame set.
	 *
	 * @param node
	 *            the node
	 * @return the frame set
	 */
	private DOMNodeImpl getFrameSet(DOMNodeImpl node) {
		DOMNodeImpl[] children = node.getChildrenArray();
		if (children == null) {
			return null;
		}
		int length = children.length;
		DOMNodeImpl frameSet = null;
		for (int i = 0; i < length; i++) {
			DOMNodeImpl child = children[i];
			if (child instanceof DOMElementImpl) {
				String tagName = child.getNodeName();
				if ("FRAMESET".equalsIgnoreCase(tagName)) {
					frameSet = child;
					break;
				} else if (!"HEAD".equalsIgnoreCase(tagName) 
						&& !"NOFRAMES".equalsIgnoreCase(tagName)
						&& !"TITLE".equalsIgnoreCase(tagName) && !"META".equalsIgnoreCase(tagName)
						&& !"SCRIPT".equalsIgnoreCase(tagName) && !"NOSCRIPT".equalsIgnoreCase(tagName)
						&& this.hasSomeHtml((DOMElementImpl) child)) {
						return null;
				}
			}
		}
		return frameSet;
	}

	/**
	 * Checks for some html.
	 *
	 * @param element
	 *            the element
	 * @return true, if successful
	 */
	private boolean hasSomeHtml(DOMElementImpl element) {
		String tagName = element.getTagName();
		if ("HEAD".equalsIgnoreCase(tagName) || "TITLE".equalsIgnoreCase(tagName) || "META".equalsIgnoreCase(tagName)) {
			return false;
		}
		DOMNodeImpl[] children = element.getChildrenArray();
		if (children != null) {
			int length = children.length;
			for (int i = 0; i < length; i++) {
				DOMNodeImpl child = children[i];
				if (child instanceof Text) {
					String textContent = ((Text) child).getTextContent();
					if (textContent != null && !"".equals(textContent.trim())) {
						return false;
					}
				} else if (child instanceof DOMElementImpl&& this.hasSomeHtml((DOMElementImpl) child)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Internal method used to expand the selection to the given point.
	 * <p>
	 * Note: This method should be invoked in the GUI thread.
	 *
	 * @param rpoint
	 *            the rpoint
	 */
	@Override
	public void expandSelection(RenderableSpot rpoint) {
		HtmlBlockPanel block = this.htmlBlockPanel;
		if (block != null) {
			block.setSelectionEnd(rpoint);
			block.repaint();
			this.selectionDispatch.fireEvent(new SelectionChangeEvent(this, block.isSelectionAvailable()));
		}
	}

	/**
	 * Internal method used to reset the selection so that it is empty at the
	 * given point. This is what is called when the user clicks on a point in
	 * the document.
	 * <p>
	 * Note: This method should be invoked in the GUI thread.
	 *
	 * @param rpoint
	 *            the rpoint
	 */
	@Override
	public void resetSelection(RenderableSpot rpoint) {
		HtmlBlockPanel block = this.htmlBlockPanel;
		if (block != null) {
			block.setSelectionStart(rpoint);
			block.setSelectionEnd(rpoint);
			block.repaint();
		}
		this.selectionDispatch.fireEvent(new SelectionChangeEvent(this, false));
	}

	/**
	 * Gets the selection text.
	 *
	 * @return the selection text
	 */
	public String getSelectionText() {
		HtmlBlockPanel block = this.htmlBlockPanel;
		if (block == null) {
			return null;
		} else {
			return block.getSelectionText();
		}
	}

	/**
	 * Gets the selection node.
	 *
	 * @return the selection node
	 */
	public Node getSelectionNode() {
		HtmlBlockPanel block = this.htmlBlockPanel;
		if (block == null) {
			return null;
		} else {
			return block.getSelectionNode();
		}
	}

	/**
	 * Returns true only if the current block has a selection. This method has
	 * no effect in FRAMESETs at the moment.
	 *
	 * @return true, if successful
	 */
	public boolean hasSelection() {
		HtmlBlockPanel block = this.htmlBlockPanel;
		if (block == null) {
			return false;
		} else {
			return block.hasSelection();
		}
	}

	/**
	 * Copies the current selection, if any, into the clipboard. This method has
	 * no effect in FRAMESETs at the moment.
	 *
	 * @return true, if successful
	 */
	public boolean copy() {
		HtmlBlockPanel block = this.htmlBlockPanel;
		if (block != null) {
			return block.copy();
		} else {
			return false;
		}
	}

	/**
	 * Adds listener of selection changes. Note that it does not have any effect
	 * on FRAMESETs.
	 *
	 * @param listener
	 *            An instance of {@link SelectionChangeListener}.
	 */
	public void addSelectionChangeListener(SelectionChangeListener listener) {
		this.selectionDispatch.addListener(listener);
	}

	/**
	 * Removes a listener of selection changes that was previously added.
	 *
	 * @param listener
	 *            the listener
	 */
	public void removeSelectionChangeListener(SelectionChangeListener listener) {
		selectionDispatch.removeListener(listener);
	}

	/**
	 * Sets the default margin insets.
	 *
	 * @param insets
	 *            the new default margin insets
	 */
	public void setDefaultMarginInsets(Insets insets) {
		this.defaultMarginInsets = insets;
		HtmlBlockPanel block = this.htmlBlockPanel;
		if (block != null) {
			block.setDefaultMarginInsets(insets);
		}
	}

	/**
	 * Sets the default overflow x.
	 *
	 * @param overflow
	 *            the new default overflow x
	 */
	public void setDefaultOverflowX(int overflow) {
		this.defaultOverflowX = overflow;
		HtmlBlockPanel block = this.htmlBlockPanel;
		if (block != null) {
			block.setDefaultOverflowX(overflow);
		}
	}

	/**
	 * Sets the default overflow y.
	 *
	 * @param overflow
	 *            the new default overflow y
	 */
	public void setDefaultOverflowY(int overflow) {
		this.defaultOverflowY = overflow;
		HtmlBlockPanel block = this.htmlBlockPanel;
		if (block != null) {
			block.setDefaultOverflowY(overflow);
		}
	}
	
	/**
	 * Adds the notification.
	 *
	 * @param notification
	 *            the notification
	 */
	public void addNotification(DocumentNotification notification) {
		// This can be called in a random thread.
		ArrayList<DocumentNotification> notifs = this.notifications;
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
	 * Invalidates the layout of the given node and schedules it to be layed out
	 * later. Multiple invalidations may be processed in a single document
	 * layout.
	 *
	 * @param node
	 *            the node
	 */
	@Override
	public void delayedRelayout(DOMNodeImpl node) {
		ArrayList<DocumentNotification> notifs = this.notifications;
		synchronized (notifs) {
			notifs.add(new DocumentNotification(DocumentNotification.SIZE, node));
		}
		this.notificationTimer.restart();
	}

	/**
	 * Process notifications.
	 */
	public void processNotifications() {
		// This is called in the GUI thread.
		ArrayList<DocumentNotification> notifs = this.notifications;
		DocumentNotification[] notifsArray;
		synchronized (notifs) {
			int size = notifs.size();
			if (size == 0) {
				return;
			}
			notifsArray = new DocumentNotification[size];
			notifsArray = notifs.toArray(notifsArray);
			notifs.clear();
		}
		int length = notifsArray.length;
		for (int i = 0; i < length; i++) {
			DocumentNotification dn = notifsArray[i];
			if (this.resetIfFrameSet() && dn.node instanceof HTMLFrameSetElement && this.htmlBlockPanel != null) {
				return;
			}
		}
		HtmlBlockPanel blockPanel = this.htmlBlockPanel;
		if (blockPanel != null) {
			blockPanel.processDocumentNotifications(notifsArray);
		}
		FrameSetPanel frameSetPanel = this.frameSetPanel;
		if (frameSetPanel != null) {
			frameSetPanel.processDocumentNotifications(notifsArray);
		}
	}
}
