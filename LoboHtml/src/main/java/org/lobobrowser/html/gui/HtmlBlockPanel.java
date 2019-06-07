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
 * Created on Apr 16, 2005
 */
package org.lobobrowser.html.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.lobo.common.Nodes;
import org.lobo.laf.ColorFactory;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.domimpl.ModelNode;
import org.lobobrowser.html.domimpl.NodeImpl;
import org.lobobrowser.html.domimpl.UINode;
import org.lobobrowser.html.renderer.BoundableRenderable;
import org.lobobrowser.html.renderer.DelayedPair;
import org.lobobrowser.html.renderer.FrameContext;
import org.lobobrowser.html.renderer.NodeRenderer;
import org.lobobrowser.html.renderer.RBlock;
import org.lobobrowser.html.renderer.RCollection;
import org.lobobrowser.html.renderer.RElement;
import org.lobobrowser.html.renderer.Renderable;
import org.lobobrowser.html.renderer.RenderableContainer;
import org.lobobrowser.html.renderer.RenderableSpot;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.http.HtmlRendererContext;
import org.lobobrowser.http.UserAgentContext;
import java.util.Objects;
import org.w3c.dom.Node;

/**
 * A Swing component that renders a HTML block, given by a DOM root or an
 * internal element, typically a DIV. This component <i>cannot</i> render
 * FRAMESETs. <code>HtmlBlockPanel</code> is used by {@link HtmlPanel} whenever
 * the DOM is determined <i>not</i> to be a FRAMESET.
 * 
 * @see HtmlPanel
 * @see FrameSetPanel
 * @author J. H. S.
 */
public class HtmlBlockPanel extends JComponent implements NodeRenderer, RenderableContainer, ClipboardOwner {
	private static final Logger logger = Logger.getLogger(HtmlBlockPanel.class.getName());

	private static final long serialVersionUID = 1L;
	private Set<Component> components;
	protected Insets defaultMarginInsets = null;
	protected int defaultOverflowX = RenderState.OVERFLOW_AUTO;
	protected int defaultOverflowY = RenderState.OVERFLOW_SCROLL;
	protected RenderableSpot endSelection;
	protected final FrameContext frameContext;
	private BoundableRenderable mousePressTarget;
	protected int preferredWidth = -1;
	private boolean processingDocumentNotification = false;
	protected RBlock rblock;

	protected final HtmlRendererContext rcontext;

	protected RenderableSpot startSelection;

	protected final UserAgentContext ucontext;

	public HtmlBlockPanel(Color background, boolean opaque, UserAgentContext pcontext, HtmlRendererContext rcontext,
			FrameContext frameContext) {
		setLayout(null);
		setAutoscrolls(true);
		this.frameContext = frameContext;
		this.ucontext = pcontext;
		this.rcontext = rcontext;
		setOpaque(opaque);
		setBackground(background);
		final ActionListener actionListener = e -> {
			final String command = e.getActionCommand();
			if ("copy".equals(command)) {
				copy();
			}
		};
		if (!GraphicsEnvironment.isHeadless()) {
			this.registerKeyboardAction(actionListener, "copy", KeyStroke.getKeyStroke(KeyEvent.VK_COPY, 0),
					JComponent.WHEN_FOCUSED);
			this.registerKeyboardAction(actionListener, "copy",
					KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),
					JComponent.WHEN_FOCUSED);
		}
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onMouseClick(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				onMouseExited(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				onMousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				onMouseReleased(e);
			}
		});
		addMouseMotionListener(new MouseMotionListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseDragged(MouseEvent e) {
				onMouseDragged(e);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseMoved(MouseEvent arg0) {
				onMouseMoved(arg0);
			}
		});
		addMouseWheelListener(e -> onMouseWheelMoved(e));
	}

	public HtmlBlockPanel(UserAgentContext pcontext, HtmlRendererContext rcontext, FrameContext frameContext) {
		this(ColorFactory.TRANSPARENT, false, pcontext, rcontext, frameContext);
	}

	@Override
	public Component addComponent(Component component) {
		Set<Component> c = this.components;
		if (c == null) {
			c = new HashSet<Component>();
			this.components = c;
		}
		if (c.add(component)) {
			return component;
		} else {
			return null;
		}
	}

	@Override
	public void addDelayedPair(DelayedPair pair) {
		// NOP
	}

	private void clearComponents() {
		final Set<Component> c = this.components;
		if (c != null) {
			c.clear();
		}
	}

	@Override
	public void clearDelayedPairs() {
	}

	public boolean copy() {
		final String selection = HtmlBlockPanel.this.getSelectionText();
		if (selection != null) {
			final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(new StringSelection(selection), HtmlBlockPanel.this);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void doLayout() {
		try {
			final Dimension size = this.getSize();
			clearComponents();
			final RBlock block = this.rblock;
			if (block != null) {
				block.layout(size.width, size.height, true, true, null, false);
				// Only set origin
				block.setOrigin(0, 0);
				block.updateWidgetBounds(0, 0);
				updateGUIComponents();
			} else {
				if (getComponentCount() > 0) {
					removeAll();
				}
			}
		} catch (final Throwable thrown) {
			logger.log(Level.SEVERE, "Unexpected error in layout engine. Document is " + getRootNode(), thrown);
		}
	}

	@Override
	public void finalize() throws Throwable {
		super.finalize();
	}

	@Override
	public void focus() {
		grabFocus();
	}

	public Insets getDefaultMarginInsets() {
		return this.defaultMarginInsets;
	}

	public int getDefaultOverflowX() {
		return this.defaultOverflowX;
	}

	public int getDefaultOverflowY() {
		return this.defaultOverflowY;
	}

	@Override
	public Collection<?> getDelayedPairs() {
		return null;
	}

	public int getFirstLineHeight() {
		final RBlock block = this.rblock;
		return block == null ? 0 : block.getFirstLineHeight();
	}

	@Override
	public Point getGUIPoint(int clientX, int clientY) {
		// This is the GUI!
		return new Point(clientX, clientY);
	}

	/**
	 * Gets the rectangular bounds of the given node.
	 * <p>
	 * This method should be called from the GUI thread.
	 * 
	 * @param node                 A node in the current document.
	 * @param relativeToScrollable Whether the bounds should be relative to the
	 *                             scrollable body area. Otherwise, they are
	 *                             relative to the root block (which is the
	 *                             essentially the same as being relative to this
	 *                             <code>HtmlBlockPanel</code> minus Swing borders).
	 */
	public Rectangle getNodeBounds(Node node, boolean relativeToScrollable) {
		final RBlock block = this.rblock;
		if (block == null) {
			return null;
		}
		// Find UINode first
		Node currentNode = node;
		UINode uiNode = null;
		while (currentNode != null) {
			if (currentNode instanceof HTMLElementImpl) {
				final HTMLElementImpl element = (HTMLElementImpl) currentNode;
				uiNode = element.getUINode();
				if (uiNode != null) {
					break;
				}
			}
			currentNode = currentNode.getParentNode();
		}
		if (uiNode == null) {
			return null;
		}
		final RCollection relativeTo = relativeToScrollable ? (RCollection) block.getRBlockViewport()
				: (RCollection) block;
		if (node == currentNode) {
			final BoundableRenderable br = (BoundableRenderable) uiNode;
			final Point guiPoint = br.getOriginRelativeTo(relativeTo);
			final Dimension size = br.getSize();
			return new Rectangle(guiPoint, size);
		} else {
			return scanNodeBounds((RCollection) uiNode, node, relativeTo);
		}
	}

	@Override
	public Color getPaintedBackgroundColor() {
		return isOpaque() ? getBackground() : null;
	}

	@Override
	public RenderableContainer getParentContainer() {
		return null;
	}

	/**
	 * If the preferred size has been set with {@link #setPreferredSize(Dimension)},
	 * then that size is returned. Otherwise a preferred size is calculated by
	 * rendering the HTML DOM, provided one is available and a preferred width other
	 * than <code>-1</code> has been set with {@link #setPreferredWidth(int)}. An
	 * arbitrary preferred size is returned in other scenarios.
	 */
	@Override
	public Dimension getPreferredSize() {
		// Expected to be invoked in the GUI thread.
		if (isPreferredSizeSet()) {
			return super.getPreferredSize();
		}
		final int pw = this.preferredWidth;
		if (pw != -1) {
			final RBlock block = this.rblock;
			if (block != null) {
				// Layout should always be done in the GUI thread.
				if (SwingUtilities.isEventDispatchThread()) {
					block.layout(pw, 0, false, false, RenderState.OVERFLOW_VISIBLE, RenderState.OVERFLOW_VISIBLE, true);
				} else {
					try {
						EventQueue.invokeAndWait(() -> block.layout(pw, 0, false, false, RenderState.OVERFLOW_VISIBLE,
								RenderState.OVERFLOW_VISIBLE, true));
					} catch (final Exception err) {
						logger.log(Level.SEVERE, "Unable to do preferred size layout.", err);
					}
				}
				// Adjust for permanent vertical scrollbar.
				final int newPw = Math.max(block.width + block.getVScrollBarWidth(), pw);
				return new Dimension(newPw, block.height);
			}
		}
		return new Dimension(600, 400);
	}

	public NodeImpl getRootNode() {
		final RBlock block = this.rblock;
		return block == null ? null : (NodeImpl) block.getModelNode();
	}

	public BoundableRenderable getRootRenderable() {
		return this.rblock;
	}

	public org.w3c.dom.Node getSelectionNode() {
		final RenderableSpot start = this.startSelection;
		final RenderableSpot end = this.endSelection;
		if (start != null && end != null) {
			return Nodes.getCommonAncestor((Node) start.renderable.getModelNode(),
					(Node) end.renderable.getModelNode());
		} else {
			return null;
		}
	}

	public String getSelectionText() {
		final RenderableSpot start = this.startSelection;
		final RenderableSpot end = this.endSelection;
		if (start != null && end != null) {
			final StringBuffer buffer = new StringBuffer();
			this.rblock.extractSelectionText(buffer, false, start, end);
			return buffer.toString();
		} else {
			return null;
		}
	}

	public boolean hasSelection() {
		final RenderableSpot start = this.startSelection;
		final RenderableSpot end = this.endSelection;
		if (start != null && end != null && !start.equals(end)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void invalidateLayoutUpTree() {
		// Called when renderable branch is invalidated.
		// We shouldn't do anything here. Changes in renderer
		// tree do not have any bearing on validity of GUI
		// component.
	}

	public boolean isSelectionAvailable() {
		final RenderableSpot start = this.startSelection;
		final RenderableSpot end = this.endSelection;
		return start != null && end != null && !start.equals(end);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.datatransfer.ClipboardOwner#lostOwnership(java.awt.datatransfer.
	 * Clipboard, java.awt.datatransfer.Transferable)
	 */
	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
	}

	private void onMouseClick(MouseEvent event) {
		// Rely on AWT mouse-click only for double-clicks
		final RBlock block = this.rblock;
		if (block != null) {
			final int button = event.getButton();
			final int clickCount = event.getClickCount();
			if (button == MouseEvent.BUTTON1 && clickCount > 1) {
				// TODO: Double-click must be revised. It generates
				// a single click via mouse release.
				final Point point = event.getPoint();
				block.onDoubleClick(event, point.x, point.y);
			} else if (button == MouseEvent.BUTTON3 && clickCount == 1) {
				block.onRightClick(event, event.getX(), event.getY());
			}
		}
	}

	private void onMouseDragged(MouseEvent event) {
		final RBlock block = this.rblock;
		if (block != null) {
			final Point point = event.getPoint();
			final RenderableSpot rp = block.getLowestRenderableSpot(point.x, point.y);
			if (rp != null) {
				this.frameContext.expandSelection(rp);
			}
			block.ensureVisible(point);
		}
	}

	private void onMouseExited(MouseEvent event) {
		final BoundableRenderable oldTarget = this.mousePressTarget;
		if (oldTarget != null) {
			this.mousePressTarget = null;
			oldTarget.onMouseDisarmed(event);
		}
	}

	private void onMouseMoved(MouseEvent event) {
		final RBlock block = this.rblock;
		if (block != null) {
			final Point point = event.getPoint();
			block.onMouseMoved(event, point.x, point.y, false, null);
		}
	}

	private void onMousePressed(MouseEvent event) {
		this.requestFocus();
		final RBlock block = this.rblock;
		if (block != null) {
			final Point point = event.getPoint();
			this.mousePressTarget = block;
			final int rx = point.x;
			final int ry = point.y;
			block.onMousePressed(event, point.x, point.y);
			final RenderableSpot rp = block.getLowestRenderableSpot(rx, ry);
			if (rp != null) {
				this.frameContext.resetSelection(rp);
			} else {
				this.frameContext.resetSelection(null);
			}
		}
	}

	private void onMouseReleased(MouseEvent event) {
		final RBlock block = this.rblock;
		if (block != null) {
			final Point point = event.getPoint();
			final int rx = point.x;
			final int ry = point.y;
			if (event.getButton() == MouseEvent.BUTTON1) {
				// TODO: This will be raised twice on a double-click.
				block.onMouseClick(event, rx, ry);
			}
			block.onMouseReleased(event, rx, ry);
			final BoundableRenderable oldTarget = this.mousePressTarget;
			if (oldTarget != null) {
				this.mousePressTarget = null;
				if (oldTarget != block) {
					oldTarget.onMouseDisarmed(event);
				}
			}
		} else {
			this.mousePressTarget = null;
		}
	}

	private void onMouseWheelMoved(MouseWheelEvent mwe) {
		final RBlock block = this.rblock;
		if (block != null) {
			switch (mwe.getScrollType()) {
			case MouseWheelEvent.WHEEL_UNIT_SCROLL:
				final int units = mwe.getWheelRotation() * mwe.getScrollAmount();
				block.scrollByUnits(JScrollBar.VERTICAL, units);
				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	// protected void paintComponent(Graphics g) {
	@Override
	public void paint(Graphics g) {
		// We go against Sun's advice and override
		// paint() instead of paintComponent(). Scrollbars
		// do not repaint correctly if we use
		// paintComponent.
		if (isOpaque()) {
			// Background not painted by default in JComponent.
			final Rectangle clipBounds = g.getClipBounds();
			g.setColor(getBackground());
			g.fillRect(clipBounds.x, clipBounds.y, clipBounds.width, clipBounds.height);
		}
		if (g instanceof Graphics2D) {
			final Graphics2D g2 = (Graphics2D) g;
            try {
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
            } catch (NoSuchFieldError e) {
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            }
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		final RBlock block = this.rblock;
		if (block != null) {
			block.paint(g);
			final RenderableSpot start = this.startSelection;
			final RenderableSpot end = this.endSelection;
			if (start != null && end != null && !start.equals(end)) {
				block.paintSelection(g, false, start, end);
			}
		}
	}

	@Override
	protected void paintChildren(Graphics g) {
		// Overridding with NOP. For various reasons,
		// the regular mechanism for painting children
		// needs to be handled by Cobra.
	}

	void processDocumentNotifications(DocumentNotification[] notifications) {
		// Called in the GUI thread.
		if (this.processingDocumentNotification) {
			// This should not be possible. Even if
			// Javascript modifies the DOM during
			// parsing, this should be executed in
			// the GUI thread, not the parser thread.
			throw new IllegalStateException("Recursive");
		}
		this.processingDocumentNotification = true;
		try {
			// Note: It may be assumed that usually only generic
			// notifications come in batches. Other types
			// of noitifications probably come one by one.
			boolean topLayout = false;
			List<RElement> repainters = null;
			final int length = notifications.length;
			for (int i = 0; i < length; i++) {
				final DocumentNotification dn = notifications[i];
				final int type = dn.type;
				switch (type) {
				case DocumentNotification.GENERIC:
				case DocumentNotification.SIZE: {
					final NodeImpl node = dn.node;
					if (node == null) {
						this.rblock.invalidateLayoutDeep();
					} else {
						final UINode uiNode = node.findUINode();
						if (uiNode != null) {
							final RElement relement = (RElement) uiNode;
							relement.invalidateLayoutUpTree();
						} 
					}
					topLayout = true;
					break;
				}
				case DocumentNotification.POSITION: {
					// TODO: Could be more efficient.
					final NodeImpl node = dn.node;
					final NodeImpl parent = (NodeImpl) node.getParentNode();
					if (parent != null) {
						final UINode uiNode = parent.findUINode();
						if (uiNode != null) {
							final RElement relement = (RElement) uiNode;
							relement.invalidateLayoutUpTree();
						}
					}
					topLayout = true;
					break;
				}
				case DocumentNotification.LOOK: {
					final NodeImpl node = dn.node;
					final UINode uiNode = node.findUINode();
					if (uiNode != null) {
						if (repainters == null) {
							repainters = new ArrayList<RElement>();
						}
						final RElement relement = (RElement) uiNode;
						// relement.invalidateRenderStyle();
						repainters.add(relement);
					}
					break;
				}
				default:
					break;
				}
			}
			if (topLayout) {
				revalidatePanel();
			} else {
				if (repainters != null) {
					final Iterator<RElement> i = repainters.iterator();
					while (i.hasNext()) {
						final RElement element = (RElement) i.next();
						element.repaint();
					}
				}
			}
		} finally {
			this.processingDocumentNotification = false;
		}
	}

	@Override
	public void relayout() {
		// Expected to be called in the GUI thread.
		// Renderable branch should be invalidated at this
		// point, but this GUI component not necessarily.
		revalidatePanel();
	}

	/**
	 * Implementation of UINode.repaint().
	 */
	public void repaint(ModelNode modelNode) {
		// this.rblock.invalidateRenderStyle();
		this.repaint();
	}

	protected void revalidatePanel() {
		// Called in the GUI thread.
		invalidate();
		validate();
		// TODO: Could be paintImmediately.
		this.repaint();
	}

	/**
	 * Gets an aggregate of the bounds of renderer leaf nodes.
	 */
	private Rectangle scanNodeBounds(RCollection root, Node node, RCollection relativeTo) {
		final Iterator<?> i = root.getRenderables();
		Rectangle resultBounds = null;
		BoundableRenderable prevBoundable = null;
		if (i != null) {
			while (i.hasNext()) {
				final Renderable r = (Renderable) i.next();
				Rectangle subBounds = null;
				if (r instanceof RCollection) {
					final RCollection rc = (RCollection) r;
					prevBoundable = rc;
					subBounds = scanNodeBounds(rc, node, relativeTo);
				} else if (r instanceof BoundableRenderable) {
					final BoundableRenderable br = (BoundableRenderable) r;
					prevBoundable = br;
					if (Nodes.isSameOrAncestorOf(node, (Node) r.getModelNode())) {
						final Point origin = br.getOriginRelativeTo(relativeTo);
						final Dimension size = br.getSize();
						subBounds = new Rectangle(origin, size);
					}
				} else {
					// This would have to be a RStyleChanger. We rely on these
					// when the target node has blank content.
					if (Nodes.isSameOrAncestorOf(node, (Node) r.getModelNode())) {
						final int xInRoot = prevBoundable == null ? 0 : prevBoundable.getX() + prevBoundable.getWidth();
						final Point rootOrigin = root.getOriginRelativeTo(relativeTo);
						subBounds = new Rectangle(rootOrigin.x + xInRoot, rootOrigin.y, 0, root.getHeight());
					}
				}
				if (subBounds != null) {
					if (resultBounds == null) {
						resultBounds = subBounds;
					} else {
						resultBounds = subBounds.union(resultBounds);
					}
				}
			}
		}
		return resultBounds;
	}

	public void scrollBy(int xOffset, int yOffset) {
		final RBlock block = this.rblock;
		if (block != null) {
			if (xOffset != 0) {
				block.scrollBy(JScrollBar.HORIZONTAL, xOffset);
			}
			if (yOffset != 0) {
				block.scrollBy(JScrollBar.VERTICAL, yOffset);
			}
		}
	}

	/**
	 * Scrolls the body area to the node given, if it is part of the current
	 * document.
	 * <p>
	 * This method should be called from the GUI thread.
	 * 
	 * @param node A DOM node.
	 */
	public void scrollTo(Node node) {
		final Rectangle bounds = getNodeBounds(node, true);
		if (bounds == null) {
			return;
		}
		this.scrollTo(bounds, true, false);
	}

	/**
	 * Scrolls the body area to the given location.
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
		final RBlock block = this.rblock;
		if (block != null) {
			block.scrollTo(bounds, xIfNeeded, yIfNeeded);
		}
	}

	/**
	 * Sets the default margin insets. Note that in the root block, the margin
	 * behaves like padding.
	 * 
	 * @param defaultMarginInsets The default margin insets.
	 */
	public void setDefaultMarginInsets(Insets defaultMarginInsets) {
		if (!Objects.equals(this.defaultMarginInsets, defaultMarginInsets)) {
			this.defaultMarginInsets = defaultMarginInsets;
			final RBlock block = this.rblock;
			if (block != null) {
				block.setDefaultMarginInsets(defaultMarginInsets);
				block.relayoutIfValid();
			}
		}
	}

	public void setDefaultOverflowX(int defaultOverflowX) {
		if (defaultOverflowX != this.defaultOverflowX) {
			this.defaultOverflowX = defaultOverflowX;
			final RBlock block = this.rblock;
			if (block != null) {
				block.setDefaultOverflowX(defaultOverflowX);
				block.relayoutIfValid();
			}
		}
	}

	public void setDefaultOverflowY(int defaultOverflowY) {
		if (this.defaultOverflowY != defaultOverflowY) {
			this.defaultOverflowY = defaultOverflowY;
			final RBlock block = this.rblock;
			if (block != null) {
				block.setDefaultOverflowY(defaultOverflowY);
				block.relayoutIfValid();
			}
		}
	}

	/**
	 * Allows {@link #getPreferredSize()} to render the HTML block in order to
	 * determine the preferred size of this component. Note that
	 * <code>getPreferredSize()<code> is a potentially time-consuming
	 * operation if the preferred width is set. 
	 * @param width The preferred blocked width. Use <code>-1</code> to unset.
	 */
	public void setPreferredWidth(int width) {
		this.preferredWidth = width;
	}

	/**
	 * Sets the root node to render. This method should be invoked in the GUI
	 * dispatch thread.
	 */
	@Override
	public void setRootNode(NodeImpl node) {
		if (node != null) {
			final RBlock block = new RBlock(node, 0, this.ucontext, this.rcontext, this.frameContext, this);
			block.setDefaultMarginInsets(this.defaultMarginInsets);
			// block.setDefaultPaddingInsets(this.defaultPaddingInsets);
			block.setDefaultOverflowX(this.defaultOverflowX);
			block.setDefaultOverflowY(this.defaultOverflowY);
			node.setUINode(block);
			this.rblock = block;
		} else {
			this.rblock = null;
		}
		invalidate();
		validateAll();
		this.repaint();
	}

	public void setSelectionEnd(RenderableSpot rpoint) {
		this.endSelection = rpoint;
	}

	public void setSelectionStart(RenderableSpot rpoint) {
		this.startSelection = rpoint;
	}

	@Override
	public void updateAllWidgetBounds() {
		this.rblock.updateWidgetBounds(0, 0);
	}

	private void updateGUIComponents() {
		// We use this method, instead of removing all components and
		// adding them back, because removal of components can cause
		// them to lose focus.

		final Set<Component> c = this.components;
		if (c == null) {
			if (getComponentCount() != 0) {
				removeAll();
			}
		} else {
			// Remove children not in the set.
			final Set<Component> workingSet = new HashSet<Component>();
			workingSet.addAll(c);
			int count = getComponentCount();
			for (int i = 0; i < count;) {
				final Component component = getComponent(i);
				if (!c.contains(component)) {
					this.remove(i);
					count = getComponentCount();
				} else {
					i++;
					workingSet.remove(component);
				}
			}
			// Add components in set that were not previously children.
			final Iterator<Component> wsi = workingSet.iterator();
			while (wsi.hasNext()) {
				final Component component = (Component) wsi.next();
				this.add(component);
			}
		}
	}

	public Insets getInsets(final boolean hscroll, final boolean vscroll) {
		throw new UnsupportedOperationException("Method added while implementing absolute positioned elements inside relative elements. But not implemented yet.");
	}

	@Override
	public Insets getInsetsMarginBorder(final boolean hscroll, final boolean vscroll) {
		throw new UnsupportedOperationException("Method added while fixing #20. Not implemented yet.");
	}

	protected void validateAll() {
		Component toValidate = this;
		for (;;) {
			final Container parent = toValidate.getParent();
			if (parent == null || parent.isValid()) {
				break;
			}
			toValidate = parent;
		}
		toValidate.validate();
	}
}