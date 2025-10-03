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

package com.jtattoo.plaf.base;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.base.action.*;
import com.jtattoo.plaf.base.button.ScrollablePopupMenuTabButton;
import com.jtattoo.plaf.base.button.ScrollableTabButton;
import com.jtattoo.plaf.base.layout.ScrollableTabSupport;
import com.jtattoo.plaf.base.layout.ScrollableTabViewport;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ActionMapUIResource;
import javax.swing.plaf.ComponentInputMapUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.TabbedPaneUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.text.View;

/**
 * This class is a modified copy of the javax.swing.plaf.basic.BasicTabbedPaneUI
 * A Basic L&amp;F implementation of TabbedPaneUI.
 *
 * @version 1.87 06/08/99
 * Author Amy Fowler
 * Author Philip Milne
 * Author Steve Wilson
 * Author Tom Santos
 * Author Dave Moore
 * Author Michael Hagen
 */
@Slf4j
public class BaseTabbedPaneUI extends TabbedPaneUI implements SwingConstants {

	/*
	 * GES 2/3/99: The container listener code was added to support HTML rendering
	 * of tab titles.
	 * 
	 * Ideally, we would be able to listen for property changes when a tab is added
	 * or its text modified. At the moment there are no such events because the
	 * Beans spec doesn't allow 'indexed' property changes (i.e. tab 2's text
	 * changed from A to B).
	 * 
	 * In order to get around this, we listen for tabs to be added or removed by
	 * listening for the container events. we then queue up a runnable (so the
	 * component has a chance to complete the add) which checks the tab title of the
	 * new component to see if it requires HTML rendering.
	 * 
	 * The Views (one per tab title requiring HTML rendering) are stored in the
	 * htmlViews list, which is only allocated after the first time we run into an
	 * HTML tab. Note that this list is kept in step with the number of pages, and
	 * nulls are added for those pages whose tab title do not require HTML
	 * rendering.
	 * 
	 * This makes it easy for the paint and layout code to tell whether to invoke
	 * the HTML engine without having to check the string during time-sensitive
	 * operations.
	 * 
	 * When we have added a way to listen for tab additions and changes to tab text,
	 * this code should be removed and replaced by something which uses that.
	 */
	private final class ContainerHandler implements ContainerListener {

		@Override
		public void componentAdded(final ContainerEvent e) {
			final JTabbedPane tp = (JTabbedPane) e.getContainer();
			final TabbedPaneLayout layout = (TabbedPaneLayout) tp.getLayout();
			layout.layoutContainer(tp);

			final Component child = e.getChild();
			if (child instanceof UIResource) {
				return;
			}
			final int index = tp.indexOfComponent(child);
			final String title = tp.getTitleAt(index);
			final boolean isHTML = BasicHTML.isHTMLString(title);
			if (isHTML) {
				if (htmlViews == null) {
					// Initialize vector
					htmlViews = createHTMLViewList();
				} else {
					// Vector already exists
					final View v = BasicHTML.createHTMLView(tp, title);
					htmlViews.add(index, v);
				}
			} else {
				// Not HTML
				if (htmlViews != null) {
					// Add placeholder
					htmlViews.add(index, null);
				} // else nada!
			}
		}

		@Override
		public void componentRemoved(final ContainerEvent e) {
			final JTabbedPane tp = (JTabbedPane) e.getContainer();
			final Component child = e.getChild();
			if (child instanceof UIResource) {
				return;
			}

			// NOTE 4/15/2002 (joutwate):
			// This fix is implemented using client properties since there is
			// currently no IndexPropertyChangeEvent. Once
			// IndexPropertyChangeEvents have been added this code should be
			// modified to use it.
			final Integer indexObj = (Integer) tp.getClientProperty("__index_to_remove__");
			if (indexObj != null) {
				if (htmlViews != null && htmlViews.size() >= indexObj) {
					htmlViews.remove(indexObj);
				}
			}
		}
	}



	/**
	 * This inner class is marked &quot;public&quot; due to a compiler bug. This
	 * class should be treated as a &quot;protected&quot; inner class. Instantiate
	 * it only within subclasses of BaseTabbedPaneUI.
	 */
	public class FocusHandler extends FocusAdapter {

		@Override
		public void focusGained(final FocusEvent e) {
			final JTabbedPane tabPane = (JTabbedPane) e.getSource();
			final int tabCount = tabPane.getTabCount();
			final int selectedIndex = tabPane.getSelectedIndex();
			if (selectedIndex != -1 && tabCount > 0 && tabCount == rects.length) {
				tabPane.repaint(getTabBounds(tabPane, selectedIndex));
			}
		}

		@Override
		public void focusLost(final FocusEvent e) {
			final JTabbedPane tabPane = (JTabbedPane) e.getSource();
			final int tabCount = tabPane.getTabCount();
			final int selectedIndex = tabPane.getSelectedIndex();
			if (selectedIndex != -1 && tabCount > 0 && tabCount == rects.length) {
				tabPane.repaint(getTabBounds(tabPane, selectedIndex));
			}
		}
	}



	/**
	 * This inner class is marked &quot;public&quot; due to a compiler bug. This
	 * class should be treated as a &quot;protected&quot; inner class. Instantiate
	 * it only within subclasses of BaseTabbedPaneUI.
	 */
	public class MouseHandler extends MouseAdapter {

		@Override
		public void mouseClicked(final MouseEvent e) {
			if (scrollableTabLayoutEnabled()) {
				final MouseListener[] ml = tabPane.getMouseListeners();
				for (final MouseListener ml1 : ml) {
					ml1.mouseClicked(e);
				}
			}
		}

		@Override
		public void mouseEntered(final MouseEvent e) {
			if (scrollableTabLayoutEnabled()) {
				for (final MouseListener ml : tabPane.getMouseListeners()) {
					ml.mouseEntered(e);
				}
			}
		}

		@Override
		public void mouseExited(final MouseEvent e) {
			if (scrollableTabLayoutEnabled()) {
				for (final MouseListener ml : tabPane.getMouseListeners()) {
					ml.mouseExited(e);
				}
			}
			rolloverIndex = -1;
			if (rolloverIndex != oldRolloverIndex) {
				if (oldRolloverIndex >= 0 && oldRolloverIndex < tabPane.getTabCount()) {
					tabPane.repaint(getTabBounds(tabPane, oldRolloverIndex));
				}
				if (rolloverIndex >= 0 && rolloverIndex < tabPane.getTabCount()) {
					tabPane.repaint(getTabBounds(tabPane, rolloverIndex));
				}
				oldRolloverIndex = rolloverIndex;
			}
		}

		@Override
		public void mousePressed(final MouseEvent e) {
			if (scrollableTabLayoutEnabled()) {
				for (final MouseListener ml : tabPane.getMouseListeners()) {
					ml.mousePressed(e);
				}
			}
			if (!tabPane.isEnabled()) {
				return;
			}
			final int tabIndex = getTabAtLocation(e.getX(), e.getY());
			if (tabIndex >= 0 && tabPane.isEnabledAt(tabIndex)) {
				if (tabIndex == tabPane.getSelectedIndex()) {
					if (tabPane.isRequestFocusEnabled()) {
						tabPane.requestFocus();
						tabPane.repaint(getTabBounds(tabPane, tabIndex));
					}
				} else {
					tabPane.setSelectedIndex(tabIndex);
				}
			}
		}

		@Override
		public void mouseReleased(final MouseEvent e) {
			if (scrollableTabLayoutEnabled()) {
				for (final MouseListener ml : tabPane.getMouseListeners()) {
					ml.mouseReleased(e);
				}
			}
		}
	}

	/**
	 * This inner class is marked &quot;public&quot; due to a compiler bug. This
	 * class should be treated as a &quot;protected&quot; inner class. Instantiate
	 * it only within subclasses of BaseTabbedPaneUI.
	 */
	public class MouseMotionHandler extends MouseMotionAdapter {

		@Override
		public void mouseDragged(final MouseEvent e) {
			if (scrollableTabLayoutEnabled()) {
				for (final MouseMotionListener mml : tabPane.getMouseMotionListeners()) {
					mml.mouseDragged(e);
				}
			}
		}

		@Override
		public void mouseMoved(final MouseEvent e) {
			if (scrollableTabLayoutEnabled()) {
				for (final MouseMotionListener mml : tabPane.getMouseMotionListeners()) {
					mml.mouseMoved(e);
				}
			}
			rolloverIndex = getTabAtLocation(e.getX(), e.getY());
			if (rolloverIndex != oldRolloverIndex) {
				if (oldRolloverIndex >= 0 && oldRolloverIndex < tabPane.getTabCount()) {
					tabPane.repaint(getTabBounds(tabPane, oldRolloverIndex));
				}
				if (rolloverIndex >= 0 && rolloverIndex < tabPane.getTabCount()) {
					tabPane.repaint(getTabBounds(tabPane, rolloverIndex));
				}
				oldRolloverIndex = rolloverIndex;
			}
		}
	}

	public class MyTabComponentListener implements PropertyChangeListener {

		@Override
		public void propertyChange(final PropertyChangeEvent evt) {
			if ("font".equals(evt.getPropertyName()) || "text".equals(evt.getPropertyName())) {
				tabPane.revalidate();
				tabPane.repaint();
			}
		}
	}

	/**
	 * This inner class is marked &quot;public&quot; due to a compiler bug. This
	 * class should be treated as a &quot;protected&quot; inner class. Instantiate
	 * it only within subclasses of BaseTabbedPaneUI.
	 */
	public class PropertyChangeHandler implements PropertyChangeListener {

		@Override
		public void propertyChange(final PropertyChangeEvent e) {
			final JTabbedPane pane = (JTabbedPane) e.getSource();
			final String name = e.getPropertyName();
			final boolean isScrollLayout = scrollableTabLayoutEnabled();
			if ("mnemonicAt".equals(name)) {
				updateMnemonics();
				pane.repaint();
			} else if ("displayedMnemonicIndexAt".equals(name)) {
				pane.repaint();
			} else if ("indexForTitle".equals(name)) {
				final int index = (Integer) e.getNewValue();
				final String title = tabPane.getTitleAt(index);
				if (BasicHTML.isHTMLString(title)) {
					if (htmlViews == null) { // Initialize vector
						htmlViews = createHTMLViewList();
					} else { // Vector already exists
						final View v = BasicHTML.createHTMLView(tabPane, title);
						htmlViews.set(index, v);
					}
				} else {
					if (htmlViews != null && htmlViews.get(index) != null) {
						htmlViews.set(index, null);
					}
				}
				updateMnemonics();
			} else if ("tabLayoutPolicy".equals(name)) {
				BaseTabbedPaneUI.this.uninstallUI(pane);
				BaseTabbedPaneUI.this.installUI(pane);
			} else if ("background".equals(name) && isScrollLayout) {
				final Color newVal = (Color) e.getNewValue();
				tabScroller.tabPanel.setBackground(newVal);
				tabScroller.viewport.setBackground(newVal);
				final Color newColor = selectedColor == null ? newVal : selectedColor;
				tabScroller.scrollForwardButton.setBackground(newColor);
				tabScroller.scrollBackwardButton.setBackground(newColor);
			} else if ("indexForTabComponent".equals(name)) {
				if (tabContainer != null) {
					tabContainer.removeUnusedTabComponents();
				}
				try {
					final Component tabComponent = getTabComponentAt((Integer) e.getNewValue());
					if (tabComponent != null) {
						if (tabContainer == null) {
							installTabContainer();
						} else {
							addMyPropertyChangeListeners(tabComponent);
							tabContainer.add(tabComponent);
						}
					}
				} catch (final Exception ex) {
					log.info(ex.getMessage());
				}
				tabPane.revalidate();
				tabPane.repaint();
			} else if ("componentOrientation".equals(name)) {
				pane.revalidate();
				pane.repaint();
			} else if ("tabAreaBackground".equals(name)) {
				pane.revalidate();
				pane.repaint();
			}
		}
	}

	/**
	 * This inner class is marked &quot;public&quot; due to a compiler bug. This
	 * class should be treated as a &quot;protected&quot; inner class. Instantiate
	 * it only within subclasses of BaseTabbedPaneUI.
	 */
	public class TabbedPaneLayout implements LayoutManager {

		@Override
		public void addLayoutComponent(final String name, final Component comp) {
		}

		public void calculateLayoutInfo() {
			final int tc = tabPane.getTabCount();
			assureRectsCreated(tc);
			calculateTabRects(tabPane.getTabPlacement(), tc);
		}

		protected Dimension calculateSize(final boolean minimum) {
			final int tabPlacement = tabPane.getTabPlacement();
			final Insets insets = tabPane.getInsets();
			final Insets contentInsets = getContentBorderInsets(tabPlacement);
			final Insets tabAreaInsets = getTabAreaInsets(tabPlacement);

			// Dimension zeroSize = new Dimension(0, 0);
			int height = contentInsets.top + contentInsets.bottom;
			int width = contentInsets.left + contentInsets.right;
			int cWidth = 0;
			int cHeight = 0;

			// Determine minimum size required to display largest
			// child in each dimension
			//
			for (int i = 0; i < tabPane.getTabCount(); i++) {
				final Component component = tabPane.getComponentAt(i);
				if (component != null) {
					final Dimension size = minimum ? component.getMinimumSize() : component.getPreferredSize();
					if (size != null) {
						cHeight = Math.max(size.height, cHeight);
						cWidth = Math.max(size.width, cWidth);
					}
				}
			}
			// Add content border insets to minimum size
			width += cWidth;
			height += cHeight;
			final int tabExtent;

			// Calculate how much space the tabs will need, based on the
			// minimum size required to display largest child + content border
			//
			switch (tabPlacement) {
			case LEFT:
			case RIGHT:
				height = Math.max(height,
						calculateMaxTabHeight(tabPlacement) + tabAreaInsets.top + tabAreaInsets.bottom);
				tabExtent = preferredTabAreaWidth(tabPlacement, height);
				width += tabExtent;
				break;
			case TOP:
			case BOTTOM:
			default:
				width = Math.max(width, calculateMaxTabWidth(tabPlacement) + tabAreaInsets.left + tabAreaInsets.right);
				tabExtent = preferredTabAreaHeight(tabPlacement, width);
				height += tabExtent;
			}
			return new Dimension(width + insets.left + insets.right, height + insets.bottom + insets.top);
		}

		protected void calculateTabRects(final int tabPlacement, final int tabCount) {
			final FontMetrics fm = getFontMetrics();
			final Dimension size = tabPane.getSize();
			final Insets insets = tabPane.getInsets();
			final Insets tabAreaInsets = getTabAreaInsets(tabPlacement);
			final int fontHeight = fm.getHeight();
			final int selectedIndex = tabPane.getSelectedIndex();
			final int tabRunOverlay;
			int i, j;
			int x, y;
			final int returnAt;
			final boolean verticalTabRuns = tabPlacement == LEFT || tabPlacement == RIGHT;
			final boolean leftToRight = JTattooUtilities.isLeftToRight(tabPane);

			//
			// Calculate bounds within which a tab run must fit
			//
			switch (tabPlacement) {
			case LEFT:
				maxTabWidth = calculateMaxTabWidth(tabPlacement);
				x = insets.left + tabAreaInsets.left;
				y = insets.top + tabAreaInsets.top;
				returnAt = size.height - (insets.bottom + tabAreaInsets.bottom);
				break;
			case RIGHT:
				maxTabWidth = calculateMaxTabWidth(tabPlacement);
				x = size.width - insets.right - tabAreaInsets.right - maxTabWidth;
				y = insets.top + tabAreaInsets.top;
				returnAt = size.height - (insets.bottom + tabAreaInsets.bottom);
				break;
			case BOTTOM:
				maxTabHeight = calculateMaxTabHeight(tabPlacement);
				x = insets.left + tabAreaInsets.left;
				y = size.height - insets.bottom - tabAreaInsets.bottom - maxTabHeight;
				returnAt = size.width - (insets.right + tabAreaInsets.right);
				break;
			case TOP:
			default:
				maxTabHeight = calculateMaxTabHeight(tabPlacement);
				x = insets.left + tabAreaInsets.left;
				y = insets.top + tabAreaInsets.top;
				returnAt = size.width - (insets.right + tabAreaInsets.right);
				break;
			}

			tabRunOverlay = getTabRunOverlay(tabPlacement);

			runCount = 0;
			selectedRun = -1;

			if (tabCount == 0) {
				return;
			}

			// Run through tabs and partition them into runs
			Rectangle rect;
			for (i = 0; i < tabCount; i++) {
				rect = rects[i];

				if (!verticalTabRuns) {
					// Tabs on TOP or BOTTOM....
					if (i > 0) {
						rect.x = rects[i - 1].x + rects[i - 1].width;
					} else {
						tabRuns[0] = 0;
						runCount = 1;
						maxTabWidth = 0;
						rect.x = x;
					}
					rect.width = calculateTabWidth(tabPlacement, i, fm);
					maxTabWidth = Math.max(maxTabWidth, rect.width);

					// Never move a TAB down a run if it is in the first column.
					// Even if there isn't enough room, moving it to a fresh
					// line won't help.
					if (rect.x != 2 + insets.left && rect.x + rect.width > returnAt) {
						if (runCount > tabRuns.length - 1) {
							expandTabRunsArray();
						}
						tabRuns[runCount] = i;
						runCount++;
						rect.x = x;
					}
					// Initialize y position in case there's just one run
					rect.y = y;
					rect.height = maxTabHeight/* - 2 */;

				} else {
					// Tabs on LEFT or RIGHT...
					if (i > 0) {
						rect.y = rects[i - 1].y + rects[i - 1].height;
					} else {
						tabRuns[0] = 0;
						runCount = 1;
						maxTabHeight = 0;
						rect.y = y;
					}
					rect.height = calculateTabHeight(tabPlacement, i, fontHeight);
					maxTabHeight = Math.max(maxTabHeight, rect.height);

					// Never move a TAB over a run if it is in the first run.
					// Even if there isn't enough room, moving it to a fresh
					// column won't help.
					if (rect.y != 2 + insets.top && rect.y + rect.height > returnAt) {
						if (runCount > tabRuns.length - 1) {
							expandTabRunsArray();
						}
						tabRuns[runCount] = i;
						runCount++;
						rect.y = y;
					}
					// Initialize x position in case there's just one column
					rect.x = x;
					rect.width = maxTabWidth/* - 2 */;

				}
				if (i == selectedIndex) {
					selectedRun = runCount - 1;
				}
			}

			if (runCount > 1) {
				// Re-distribute tabs in case last run has leftover space
				normalizeTabRuns(tabPlacement, tabCount, verticalTabRuns ? y : x, returnAt);

				selectedRun = getRunForTab(tabCount, selectedIndex);

				// Rotate run array so that selected run is first
				if (shouldRotateTabRuns(tabPlacement)) {
					rotateTabRuns(tabPlacement, selectedRun);
				}
			}

			// Step through runs from back to front to calculate
			// tab y locations and to pad runs appropriately
			for (i = runCount - 1; i >= 0; i--) {
				final int start = tabRuns[i];
				final int next = tabRuns[i == runCount - 1 ? 0 : i + 1];
				final int end = next != 0 ? next - 1 : tabCount - 1;
				if (!verticalTabRuns) {
					for (j = start; j <= end; j++) {
						rect = rects[j];
						rect.y = y;
						rect.x += getTabRunIndent(tabPlacement, i);
					}
					if (shouldPadTabRun(tabPlacement, i)) {
						padTabRun(tabPlacement, start, end, returnAt);
					}
					if (tabPlacement == BOTTOM) {
						y -= maxTabHeight - tabRunOverlay;
					} else {
						y += maxTabHeight - tabRunOverlay;
					}
				} else {
					for (j = start; j <= end; j++) {
						rect = rects[j];
						rect.x = x;
						rect.y += getTabRunIndent(tabPlacement, i);
					}
					if (shouldPadTabRun(tabPlacement, i)) {
						padTabRun(tabPlacement, start, end, returnAt);
					}
					if (tabPlacement == RIGHT) {
						x -= maxTabWidth - tabRunOverlay;
					} else {
						x += maxTabWidth - tabRunOverlay;
					}
				}
			}

			// Pad the selected tab so that it appears raised in front
			padSelectedTab(tabPlacement, selectedIndex);

			// if right to left and tab placement on the top or
			// the bottom, flip x positions and adjust by widths
			if (!leftToRight && !verticalTabRuns) {
				final int rightMargin = size.width - (insets.right + tabAreaInsets.right);
				for (i = 0; i < tabCount; i++) {
					rects[i].x = rightMargin - rects[i].x - rects[i].width;
				}
			}
		}

		@Override
		public void layoutContainer(final Container parent) {
			/*
			 * Some of the code in this method deals with changing the visibility of
			 * components to hide and show the contents for the selected tab. This is older
			 * code that has since been duplicated in JTabbedPane.fireStateChanged(), so as
			 * to allow visibility changes to happen sooner (see the note there). This code
			 * remains for backward compatibility as there are some cases, such as
			 * subclasses that don't fireStateChanged() where it may be used. Any changes
			 * here need to be kept in synch with JTabbedPane.fireStateChanged().
			 */

			final int tabPlacement = tabPane.getTabPlacement();
			final Insets insets = tabPane.getInsets();
			final int selectedIndex = tabPane.getSelectedIndex();
			final Component visibleComponent = getVisibleComponent();

			calculateLayoutInfo();

			Component selectedComponent = null;
			if (selectedIndex < 0) {
				if (visibleComponent != null) {
					// The last tab was removed, so remove the component
					setVisibleComponent(null);
				}
			} else {
				try {
					selectedComponent = tabPane.getComponentAt(selectedIndex);
				} catch (final Exception e) {
					log.info(e.getMessage());
				}
			}
			final int cx;
            int cy;
            int cw;
            final int ch;
            int totalTabWidth = 0;
			int totalTabHeight = 0;
			final Insets contentInsets = getContentBorderInsets(tabPlacement);

			boolean shouldChangeFocus = false;

			// In order to allow programs to use a single component
			// as the display for multiple tabs, we will not change
			// the visible compnent if the currently selected tab
			// has a null component. This is a bit dicey, as we don't
			// explicitly state we support this in the spec, but since
			// programs are now depending on this, we're making it work.
			//
			if (selectedComponent != null) {
				if (selectedComponent != visibleComponent && visibleComponent != null) {
					Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

					if (focusOwner != null && focusOwner == visibleComponent) {
						shouldChangeFocus = true;
					}
				}
				setVisibleComponent(selectedComponent);
			}

			final Rectangle bounds = tabPane.getBounds();
			final int numChildren = tabPane.getComponentCount();

			if (numChildren > 0) {

				switch (tabPlacement) {
				case LEFT:
					totalTabWidth = calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
					cx = insets.left + totalTabWidth + contentInsets.left;
					cy = insets.top + contentInsets.top;
					break;
				case RIGHT:
					totalTabWidth = calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
					cx = insets.left + contentInsets.left;
					cy = insets.top + contentInsets.top;
					break;
				case BOTTOM:
					totalTabHeight = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
					cx = insets.left + contentInsets.left;
					cy = insets.top + contentInsets.top;
					break;
				case TOP:
				default:
					totalTabHeight = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
					cx = insets.left + contentInsets.left;
					cy = insets.top + totalTabHeight + contentInsets.top;
				}

				cw = bounds.width - totalTabWidth - insets.left - insets.right - contentInsets.left
						- contentInsets.right;
				ch = bounds.height - totalTabHeight - insets.top - insets.bottom - contentInsets.top
						- contentInsets.bottom;

				for (int i = 0; i < numChildren; i++) {
					try {
						final Component child = tabPane.getComponent(i);
						if (tabContainer == child) {

							final int tabContainerWidth = totalTabWidth == 0 ? cw : totalTabWidth;
							final int tabContainerHeight = totalTabHeight == 0 ? ch : totalTabHeight;

							int tabContainerX = 0;
							int tabContainerY = 0;
							if (tabPlacement == BOTTOM) {
								tabContainerY = bounds.height - tabContainerHeight;
							} else if (tabPlacement == RIGHT) {
								tabContainerX = bounds.width - tabContainerWidth;
							}
							child.setBounds(tabContainerX, tabContainerY, tabContainerWidth, tabContainerHeight);
						} else {
							child.setBounds(cx, cy, cw, ch);
						}
					} catch (final Exception e) {
						log.info(e.getMessage());
					}
				}
			}
			layoutTabComponents();
			if (shouldChangeFocus) {
				if (!requestFocusForVisibleComponent()) {
					tabPane.requestFocus();
				}
			}
		}

		private void layoutTabComponents() {
			if (tabContainer == null) {
				return;
			}
			final Rectangle rect = new Rectangle();
			final Point delta = new Point(-tabContainer.getX(), -tabContainer.getY());
			if (scrollableTabLayoutEnabled()) {
				translatePointToTabPanel(0, 0, delta);
			}
			for (int i = 0; i < tabPane.getTabCount(); i++) {
				final Component tabComponent = getTabComponentAt(i);
				if (tabComponent == null) {
					continue;
				}
				getTabBounds(i, rect);
				final Dimension preferredSize = tabComponent.getPreferredSize();
				final Insets insets = getTabInsets(tabPane.getTabPlacement(), i);
				final int outerX = rect.x + insets.left + delta.x;
				final int outerY = rect.y + insets.top + delta.y;
				final int outerWidth = rect.width - insets.left - insets.right;
				final int outerHeight = rect.height - insets.top - insets.bottom;
				// centralize component
				final int x = outerX + (outerWidth - preferredSize.width) / 2;
				final int y = outerY + (outerHeight - preferredSize.height) / 2;
				final int tabPlacement = tabPane.getTabPlacement();
				final boolean isSeleceted = i == tabPane.getSelectedIndex();
				tabComponent.setBounds(x + getTabLabelShiftX(tabPlacement, i, isSeleceted),
						y + getTabLabelShiftY(tabPlacement, i, isSeleceted), preferredSize.width, preferredSize.height);
			}
		}

		@Override
		public Dimension minimumLayoutSize(final Container parent) {
			return calculateSize(true);
		}

		protected void normalizeTabRuns(final int tabPlacement, final int tabCount, final int start, final int max) {
			// Only normalize the runs for top & bottom; normalizing
			// doesn't look right for Metal's vertical tabs
			// because the last run isn't padded and it looks odd to have
			// fat tabs in the first vertical runs, but slimmer ones in the
			// last (this effect isn't noticeable for horizontal tabs).
			if (tabPlacement == TOP || tabPlacement == BOTTOM) {
				int run = runCount - 1;
				boolean keepAdjusting = true;
				double weight = 1.25;

				// At this point the tab runs are packed to fit as many
				// tabs as possible, which can leave the last run with a lot
				// of extra space (resulting in very fat tabs on the last run).
				// So we'll attempt to distribute this extra space more evenly
				// across the runs in order to make the runs look more consistent.
				//
				// Starting with the last run, determine whether the last tab in
				// the previous run would fit (generously) in this run; if so,
				// move tab to current run and shift tabs accordingly. Cycle
				// through remaining runs using the same algorithm.
				//
				while (keepAdjusting) {
					final int last = lastTabInRun(tabCount, run);
					final int prevLast = lastTabInRun(tabCount, run - 1);
					final int end;
					final int prevLastLen;

					end = rects[last].x + rects[last].width;
					prevLastLen = (int) (maxTabWidth * weight);

					// Check if the run has enough extra space to fit the last tab
					// from the previous row...
					if (max - end > prevLastLen) {

						// Insert tab from previous row and shift rest over
						tabRuns[run] = prevLast;
						rects[prevLast].x = start;
						for ( int i = prevLast + 1; i <= last; i++) {
							rects[i].x = rects[i - 1].x + rects[i - 1].width;
						}

					} else if (run == runCount - 1) {
						// no more room left in last run, so we're done!
						keepAdjusting = false;
					}
					if (run - 1 > 0) {
						// check previous run next...
						run -= 1;
					} else {
						// check last run again...but require a higher ratio
						// of extraspace-to-tabsize because we don't want to
						// end up with too many tabs on the last run!
						run = runCount - 1;
						weight += .25;
					}
				}
			}
		}

		protected void padSelectedTab(final int tabPlacement, final int selectedIndex) {
//            if ((selectedIndex >= 0) && (selectedIndex < rects.length)) {
//                Rectangle selRect = rects[selectedIndex];
//                Insets padInsets = getSelectedTabPadInsets(tabPlacement);
//                selRect.x -= padInsets.left;
//                selRect.width += (padInsets.left + padInsets.right);
//                selRect.y -= padInsets.top;
//                selRect.height += (padInsets.top + padInsets.bottom);
//            }
		}

		protected void padTabRun(final int tabPlacement, final int start, final int end, final int max) {
			final Rectangle lastRect = rects[end];
			if (tabPlacement == TOP || tabPlacement == BOTTOM) {
				final int runWidth = lastRect.x + lastRect.width - rects[start].x;
				final int deltaWidth = max - (lastRect.x + lastRect.width);
				final float factor = (float) deltaWidth / (float) runWidth;

				for ( int j = start; j <= end; j++) {
					final Rectangle pastRect = rects[j];
					if (j > start) {
						pastRect.x = rects[j - 1].x + rects[j - 1].width;
					}
					pastRect.width += Math.round(pastRect.width * factor);
				}
				lastRect.width = max - lastRect.x;
			} else {
				final int runHeight = lastRect.y + lastRect.height - rects[start].y;
				final int deltaHeight = max - (lastRect.y + lastRect.height);
				final float factor = (float) deltaHeight / (float) runHeight;

				for ( int j = start; j <= end; j++) {
					final Rectangle pastRect = rects[j];
					if (j > start) {
						pastRect.y = rects[j - 1].y + rects[j - 1].height;
					}
					pastRect.height += Math.round(pastRect.height * factor);
				}
				lastRect.height = max - lastRect.y;
			}
		}

		@Override
		public Dimension preferredLayoutSize(final Container parent) {
			return calculateSize(false);
		}

		protected int preferredTabAreaHeight(final int tabPlacement, final int width) {
			final FontMetrics fm = getFontMetrics();
			final int tc = tabPane.getTabCount();
			int total = 0;
			if (tc > 0) {
				int rows = 1;
				int x = 0;
				final int maxTabHeight = calculateMaxTabHeight(tabPlacement);

				for ( int i = 0; i < tc; i++) {
					final int tabWidth = calculateTabWidth(tabPlacement, i, fm);

					if (x != 0 && x + tabWidth > width) {
						rows++;
						x = 0;
					}
					x += tabWidth;
				}
				total = calculateTabAreaHeight(tabPlacement, rows, maxTabHeight);
			}
			return total;
		}

		protected int preferredTabAreaWidth(final int tabPlacement, final int height) {
			final FontMetrics fm = getFontMetrics();
			final int tc = tabPane.getTabCount();
			int total = 0;
			if (tc > 0) {
				int columns = 1;
				int y = 0;
				final int fontHeight = fm.getHeight();

				maxTabWidth = calculateMaxTabWidth(tabPlacement);

				for ( int i = 0; i < tc; i++) {
					final int tabHeight = calculateTabHeight(tabPlacement, i, fontHeight);

					if (y != 0 && y + tabHeight > height) {
						columns++;
						y = 0;
					}
					y += tabHeight;
				}
				total = calculateTabAreaWidth(tabPlacement, columns, maxTabWidth);
			}
			return total;
		}

		@Override
		public void removeLayoutComponent(final Component comp) {
		}

		/*
		 * Rotates the run-index array so that the selected run is run[0]
		 */
		protected void rotateTabRuns(final int tabPlacement, final int selectedRun) {
			for ( int i = 0; i < selectedRun; i++) {
				final int save = tabRuns[0];
                if (runCount - 1 >= 0) System.arraycopy(tabRuns, 1, tabRuns, 0, runCount - 1);
				tabRuns[runCount - 1] = save;
			}
		}
	}

	private final class TabbedPaneScrollLayout extends TabbedPaneLayout {

		@Override
		protected void calculateTabRects(final int tabPlacement, final int tabCount) {
			final FontMetrics fm = getFontMetrics();
			final Dimension size = tabPane.getSize();
			final Insets insets = tabPane.getInsets();
			final Insets tabAreaInsets = getTabAreaInsets(tabPlacement);
			final int fontHeight = fm.getHeight();
			final boolean verticalTabRuns = tabPlacement == LEFT || tabPlacement == RIGHT;
			final boolean leftToRight = JTattooUtilities.isLeftToRight(tabPane);
			final int x = tabAreaInsets.left;
			final int y = tabAreaInsets.top;
			int totalWidth = 0;
			int totalHeight = 0;

			//
			// Calculate bounds within which a tab run must fit
			//
			switch (tabPlacement) {
			case LEFT:
			case RIGHT:
				maxTabWidth = calculateMaxTabWidth(tabPlacement);
				break;
			case BOTTOM:
			case TOP:
			default:
				maxTabHeight = calculateMaxTabHeight(tabPlacement);
			}

			runCount = 0;
			selectedRun = -1;

			if (tabCount == 0) {
				return;
			}

			selectedRun = 0;
			runCount = 1;

			// Run through tabs and lay them out in a single run
			Rectangle rect;
			for ( int i = 0; i < tabCount; i++) {
				rect = rects[i];

				if (!verticalTabRuns) {
					// Tabs on TOP or BOTTOM....
					if (i > 0) {
						rect.x = rects[i - 1].x + rects[i - 1].width;
					} else {
						tabRuns[0] = 0;
						maxTabWidth = 0;
						totalHeight += maxTabHeight;
						rect.x = x;
					}
					rect.width = calculateTabWidth(tabPlacement, i, fm);
					totalWidth = rect.x + rect.width;
					maxTabWidth = Math.max(maxTabWidth, rect.width);

					rect.y = y;
					rect.height = maxTabHeight/* - 2 */;

				} else {
					// Tabs on LEFT or RIGHT...
					if (i > 0) {
						rect.y = rects[i - 1].y + rects[i - 1].height;
					} else {
						tabRuns[0] = 0;
						maxTabHeight = 0;
						totalWidth = maxTabWidth;
						rect.y = y;
					}
					rect.height = calculateTabHeight(tabPlacement, i, fontHeight);
					totalHeight = rect.y + rect.height;
					maxTabHeight = Math.max(maxTabHeight, rect.height);

					rect.x = x;
					rect.width = maxTabWidth/* - 2 */;

				}
			}

			// if right to left and tab placement on the top or
			// the bottom, flip x positions and adjust by widths
			if (!leftToRight && !verticalTabRuns) {
				final int rightMargin = size.width - (insets.right + tabAreaInsets.right);
				for ( int i = 0; i < tabCount; i++) {
					rects[i].x = rightMargin - rects[i].x - rects[i].width;
				}
			}
			// tabPanel.setSize(totalWidth, totalHeight);
			tabScroller.tabPanel.setPreferredSize(new Dimension(totalWidth, totalHeight));
		}

		@Override
		public void layoutContainer(final Container parent) {
			final int tabPlacement = tabPane.getTabPlacement();
			final int tc = tabPane.getTabCount();
			final Insets insets = tabPane.getInsets();
			final int selectedIndex = tabPane.getSelectedIndex();
			final Component visibleComponent = getVisibleComponent();

			calculateLayoutInfo();

			Component selectedComponent = null;
			if (selectedIndex < 0) {
				if (visibleComponent != null) {
					// The last tab was removed, so remove the component
					setVisibleComponent(null);
				}
			} else {
				try {
					selectedComponent = tabPane.getComponentAt(selectedIndex);
				} catch (final Exception ex) {
					log.info(ex.getMessage());
				}
			}
			boolean shouldChangeFocus = false;

			// In order to allow programs to use a single component
			// as the display for multiple tabs, we will not change
			// the visible compnent if the currently selected tab
			// has a null component. This is a bit dicey, as we don't
			// explicitly state we support this in the spec, but since
			// programs are now depending on this, we're making it work.
			//
			if (selectedComponent != null) {
				if (selectedComponent != visibleComponent && visibleComponent != null) {
					Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

					if (focusOwner != null && focusOwner == visibleComponent) {
						shouldChangeFocus = true;
					}
				}
				setVisibleComponent(selectedComponent);
			}
			final int tx;  // tab area bounds
            int ty;
            int tw;
            final int th;
            final int cx;  // content area bounds
            int cy;
            int cw;
            final int ch;
            final Insets contentInsets = getContentBorderInsets(tabPlacement);
			final Rectangle bounds = tabPane.getBounds();
			final int numChildren = tabPane.getComponentCount();

			final int space = 60;
			if (numChildren > 0 && tc > 0) {
				switch (tabPlacement) {
				case LEFT:
					// calculate tab area bounds
					tw = calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
					th = bounds.height - insets.top - insets.bottom;
					tx = insets.left;
					ty = insets.top;

					// calculate content area bounds
					cx = tx + tw + contentInsets.left;
					cy = ty + contentInsets.top;
					cw = bounds.width - insets.left - insets.right - tw - contentInsets.left - contentInsets.right;
					ch = bounds.height - insets.top - insets.bottom - contentInsets.top - contentInsets.bottom;
					break;
				case RIGHT:
					// calculate tab area bounds
					tw = calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
					th = bounds.height - insets.top - insets.bottom;
					tx = bounds.width - insets.right - tw;
					ty = insets.top;

					// calculate content area bounds
					cx = insets.left + contentInsets.left;
					cy = insets.top + contentInsets.top;
					cw = bounds.width - insets.left - insets.right - tw - contentInsets.left - contentInsets.right;
					ch = bounds.height - insets.top - insets.bottom - contentInsets.top - contentInsets.bottom;
					break;
				case BOTTOM:
					// calculate tab area bounds
					tw = bounds.width - insets.left - insets.right;
					th = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
					tx = insets.left;
					ty = bounds.height - insets.bottom - th;

					// calculate content area bounds
					cx = insets.left + contentInsets.left;
					cy = insets.top + contentInsets.top;
					cw = bounds.width - insets.left - insets.right - contentInsets.left - contentInsets.right;
					ch = bounds.height - insets.top - insets.bottom - th - contentInsets.top - contentInsets.bottom;
					break;
				case TOP:
				default:
					// calculate tab area bounds
					tw = bounds.width - insets.left - insets.right;
					th = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
					tx = insets.left;
					ty = insets.top;

					// calculate content area bounds
					cx = tx + contentInsets.left;
					cy = ty + th + contentInsets.top;
					cw = bounds.width - insets.left - insets.right - contentInsets.left - contentInsets.right;
					ch = bounds.height - insets.top - insets.bottom - th - contentInsets.top - contentInsets.bottom;
				}
				for ( int i = 0; i < numChildren; i++) {
					final Component child = tabPane.getComponent(i);
					if (child instanceof ScrollableTabViewport) {
						final JViewport viewport = (JViewport) child;
						final Rectangle viewRect = viewport.getViewRect();
						int vw = tw;
						int vh = th;
						switch (tabPlacement) {
						case LEFT:
						case RIGHT:
							final int totalTabHeight = rects[tc - 1].y + rects[tc - 1].height;
							if (totalTabHeight > th) {
								// Allow space for scrollbuttons
								vh = Math.max(th - space, space);
								if (totalTabHeight - viewRect.y <= vh) {
									// Scrolled to the end, so ensure the viewport size is
									// such that the scroll offset aligns with a tab
									vh = totalTabHeight - viewRect.y;
								}
							}
							break;
						case BOTTOM:
						case TOP:
						default:
							final int totalTabWidth = rects[tc - 1].x + rects[tc - 1].width;
							if (totalTabWidth > tw) {
								// Allow space for scrollbuttons
								vw = Math.max(tw - space, space);
								if (totalTabWidth - viewRect.x <= vw) {
									// Scrolled to the end, so ensure the viewport size is
									// such that the scroll offset aligns with a tab
									vw = totalTabWidth - viewRect.x;
								}
							}
						}

						child.setBounds(tx, ty, vw, vh);

					} else if (child instanceof ScrollableTabButton) {
						final ScrollableTabButton scrollbutton = (ScrollableTabButton) child;
						final Dimension bsize = scrollbutton.getPreferredSize();
						int bx = 0;
						int by = 0;
						final int bw = bsize.width;
						final int bh = bsize.height;
						boolean visible = false;

						switch (tabPlacement) {
						case LEFT:
						case RIGHT:
							final int totalTabHeight = rects[tc - 1].y + rects[tc - 1].height;
							if (totalTabHeight > th) {
								final int dir = scrollbutton.scrollsForward() ? SOUTH : NORTH;
								scrollbutton.setDirection(dir);
								visible = true;
								bx = tabPlacement == LEFT ? tw - insets.left - tabAreaInsets.bottom - bsize.width
										: bounds.width - insets.left - bsize.width;
								by = dir == SOUTH ? bounds.height - insets.bottom - 2 * bsize.height - 2
										: bounds.height - insets.bottom - 3 * bsize.height - 2;
							}
							break;

						case BOTTOM:
						case TOP:
						default:
							final int totalTabWidth = rects[tc - 1].x + rects[tc - 1].width;
							if (totalTabWidth > tw) {
								final int dir = scrollbutton.scrollsForward() ? EAST : WEST;
								scrollbutton.setDirection(dir);
								visible = true;
								bx = dir == EAST ? bounds.width - insets.left - 2 * bsize.width - 2
										: bounds.width - insets.left - 3 * bsize.width - 2;
								by = ty + (th - bsize.height - tabAreaInsets.bottom) / 2;
								if (tabPlacement == BOTTOM) {
									by += tabAreaInsets.bottom;
								} else {
									by++;
								}
							}
						}

						child.setVisible(visible);
						if (visible) {
							child.setBounds(bx, by, bw, bh);
						}

					} else if (child instanceof ScrollablePopupMenuTabButton button) {
                        final Dimension bsize = button.getPreferredSize();
						int bx = 0;
						int by = 0;
						final int bw = bsize.width;
						final int bh = bsize.height;
						boolean visible = false;

						switch (tabPlacement) {
						case LEFT:
						case RIGHT:
							final int totalTabHeight = rects[tc - 1].y + rects[tc - 1].height;
							if (totalTabHeight > th) {
								visible = true;
								bx = tabPlacement == LEFT ? tw - insets.left - tabAreaInsets.bottom - bsize.width
										: bounds.width - insets.left - bsize.width;
								by = bounds.height - insets.bottom - bsize.height;
							}
							break;

						case BOTTOM:
						case TOP:
						default:
							final int totalTabWidth = rects[tc - 1].x + rects[tc - 1].width;
							if (totalTabWidth > tw) {
								visible = true;
								bx = bounds.width - insets.left - bsize.width;
								by = ty + (th - bsize.height - tabAreaInsets.bottom) / 2;
								if (tabPlacement == BOTTOM) {
									by += tabAreaInsets.bottom;
								} else {
									by++;
								}
							}
						}

						child.setVisible(visible);
						if (visible) {
							child.setBounds(bx, by, bw, bh);
						}
					} else {
						// All content children...
						child.setBounds(cx, cy, cw, ch);
					}
				}
				super.layoutTabComponents();
				if (shouldChangeFocus) {
					if (!requestFocusForVisibleComponent()) {
						tabPane.requestFocus();
					}
				}
			}
		}

		@Override
		protected int preferredTabAreaHeight(final int tabPlacement, final int width) {
			return calculateMaxTabHeight(tabPlacement);
		}

		@Override
		protected int preferredTabAreaWidth(final int tabPlacement, final int height) {
			return calculateMaxTabWidth(tabPlacement);
		}
	}

	public class TabComponentHandler implements ComponentListener {

		@Override
		public void componentHidden(final ComponentEvent ce) {
		}

		@Override
		public void componentMoved(final ComponentEvent ce) {
		}

		@Override
		public void componentResized(final ComponentEvent ce) {
			SwingUtilities.invokeLater(() -> {
				if (tabPane != null) {
					tabPane.doLayout();
				}
			});
		}

		@Override
		public void componentShown(final ComponentEvent ce) {

		}
	}

	private final class TabContainer extends JPanel implements UIResource {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private boolean notifyTabbedPane = true;

		public TabContainer() {
			super(null);
			setOpaque(false);
		}

		@Override
		public void remove(final Component comp) {
			final int index = tabPane.indexOfTabComponent(comp);
			final PropertyChangeListener[] listeners = comp.getPropertyChangeListeners();
			for (final PropertyChangeListener listener : listeners) {
				if (listener instanceof MyTabComponentListener) {
					comp.removePropertyChangeListener(listener);
				}
			}
			super.remove(comp);
			if (notifyTabbedPane && index != -1) {
				tabPane.setTabComponentAt(index, null);
			}
		}

		private void removeUnusedTabComponents() {
			for ( int i = 0; i < getComponentCount(); i++) {
				final Component c = getComponent(i);
				if (!(c instanceof UIResource)) {
					final int index = tabPane.indexOfTabComponent(c);
					if (index == -1) {
						final PropertyChangeListener[] listeners = c.getPropertyChangeListeners();
						for (final PropertyChangeListener listener : listeners) {
							if (listener instanceof MyTabComponentListener) {
								c.removePropertyChangeListener(listener);
							}
						}
						super.remove(c);
					}
				}
			}
		}

	} // end of class ContainerHandler

	/**
	 * This inner class is marked &quot;public&quot; due to a compiler bug. This
	 * class should be treated as a &quot;protected&quot; inner class. Instantiate
	 * it only within subclasses of BaseTabbedPaneUI.
	 */
	public static class TabSelectionHandler implements ChangeListener {

		@Override
		public void stateChanged(final ChangeEvent e) {
			final JTabbedPane tabPane = (JTabbedPane) e.getSource();
			if (tabPane.getTabLayoutPolicy() == JTabbedPane.SCROLL_TAB_LAYOUT) {
				final int index = tabPane.getSelectedIndex();
				if (index >= 0) {
					final BaseTabbedPaneUI ui = (BaseTabbedPaneUI) tabPane.getUI();
					ui.tabScroller.scrollTabToVisible(tabPane.getTabPlacement(), index);
				}
			}
			tabPane.revalidate();
			tabPane.repaint();
		}
	}

	/** Constant NULL_BORDER_INSETS */
	protected static final Insets NULL_BORDER_INSETS = new Insets(0, 0, 0, 0);
	/** Constant GAP=5 */
	protected static final int GAP = 5;
	private static final int CROP_SEGMENT = 12;

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BaseTabbedPaneUI();
	}

	/**
	 * <p>rotateInsets.</p>
	 *
	 * @param topInsets a {@link java.awt.Insets} object.
	 * @param targetInsets a {@link java.awt.Insets} object.
	 * @param targetPlacement a {@link java.lang.Integer} object.
	 */
	protected static void rotateInsets(final Insets topInsets, final Insets targetInsets, final int targetPlacement) {
		switch (targetPlacement) {
		case LEFT:
			targetInsets.top = topInsets.left;
			targetInsets.left = topInsets.top;
			targetInsets.bottom = topInsets.right;
			targetInsets.right = topInsets.bottom;
			break;
		case BOTTOM:
			targetInsets.top = topInsets.bottom;
			targetInsets.left = topInsets.left;
			targetInsets.bottom = topInsets.top;
			targetInsets.right = topInsets.right;
			break;
		case RIGHT:
			targetInsets.top = topInsets.left;
			targetInsets.left = topInsets.bottom;
			targetInsets.bottom = topInsets.right;
			targetInsets.right = topInsets.top;
			break;
		case TOP:
		default:
			targetInsets.top = topInsets.top;
			targetInsets.left = topInsets.left;
			targetInsets.bottom = topInsets.bottom;
			targetInsets.right = topInsets.right;
		}
	}

	// Instance variables initialized at installation
	protected JTabbedPane tabPane;

	protected Color tabAreaBackground;

	protected Color selectedColor;

	protected int textIconGap;

	protected int tabRunOverlay;

	protected Insets tabInsets;

	protected Insets selectedTabPadInsets;

	protected Insets tabAreaInsets;

	protected Insets contentBorderInsets;

	// Transient variables (recalculated each time TabbedPane is layed out)
	protected int[] tabRuns = new int[10];

	protected int runCount = 0;

	protected int selectedRun = -1;

	protected Rectangle[] rects = new Rectangle[0];

	protected int maxTabHeight;

	protected int maxTabWidth;

	// Listeners
	protected ChangeListener tabChangeListener;

	protected ComponentListener tabComponentListener;

	protected PropertyChangeListener propertyChangeListener;

	protected MouseListener mouseListener;

	protected MouseMotionListener mouseMotionListener;

	protected FocusListener focusListener;

	// PENDING(api): See comment for ContainerHandler
	private ContainerListener containerListener;

	// Private instance data
	private final Insets currentPadInsets = new Insets(0, 0, 0, 0);

	private final Insets currentTabAreaInsets = new Insets(0, 0, 0, 0);

	private Component visibleComponent;

	// PENDING(api): See comment for ContainerHandler
	private List<View> htmlViews;

	public Map<Integer, Integer> mnemonicToIndexMap;

	/**
	 * InputMap used for mnemonics. Only non-null if the JTabbedPane has mnemonics
	 * associated with it. Lazily created in initMnemonics.
	 */
	private InputMap mnemonicInputMap;

	// For use when tabLayoutPolicy = SCROLL_TAB_LAYOUT
    public ScrollableTabSupport tabScroller;

	private TabContainer tabContainer;

	/**
	 * A rectangle used for general layout calculations in order to avoid
	 * constructing many new Rectangles on the fly.
	 */
	protected transient Rectangle calcRect = new Rectangle(0, 0, 0, 0);

	/**
	 * Number of tabs. When the count differs, the mnemonics are updated.
	 */
	// PENDING: This wouldn't be necessary if JTabbedPane had a better
	// way of notifying listeners when the count changed.
	private int tabCount;

	protected int oldRolloverIndex = -1;

	protected int rolloverIndex = -1;

	protected boolean roundedTabs = true;

	public boolean simpleButtonBorder = false;

	/*
	 * This method will create and return a polygon shape for the given tab
	 * rectangle which has been cropped at the specified cropline with a torn edge
	 * visual. e.g. A "File" tab which has cropped been cropped just after the "i":
	 * ------------- | ..... | | . | | ... . | | . . | | . . | | . . |
	 * --------------
	 *
	 * The x, y arrays below define the pattern used to create a "torn" edge segment
	 * which is repeated to fill the edge of the tab. For tabs placed on TOP and
	 * BOTTOM, this righthand torn edge is created by line segments which are
	 * defined by coordinates obtained by subtracting xCropLen[i] from (tab.x +
	 * tab.width) and adding yCroplen[i] to (tab.y). For tabs placed on LEFT or
	 * RIGHT, the bottom torn edge is created by subtracting xCropLen[i] from (tab.y
	 * + tab.height) and adding yCropLen[i] to (tab.x).
	 */
	private final int[] xCropLen = { 1, 1, 0, 0, 1, 1, 2, 2 };

	private final int[] yCropLen = { 0, 3, 3, 6, 6, 9, 9, 12 };

	/**
	 * Adds the specified mnemonic at the specified index.
	 */
	private void addMnemonic(final int index, final int mnemonic) {
		if (mnemonicToIndexMap == null) {
			initMnemonics();
		}
		mnemonicInputMap.put(KeyStroke.getKeyStroke(mnemonic, InputEvent.ALT_DOWN_MASK), "setSelectedIndex");
		mnemonicToIndexMap.put(mnemonic, index);
	}

	private void addMyPropertyChangeListeners(final Component component) {
		component.addPropertyChangeListener(new MyTabComponentListener());
		if (component instanceof Container) {
			final Container container = (Container) component;
			for ( int i = 0; i < container.getComponentCount(); i++) {
				final Component c = container.getComponent(i);
				addMyPropertyChangeListeners(c);
			}
		}
	}

	/**
	 * <p>assureRectsCreated.</p>
	 *
	 * @param tabCount a {@link java.lang.Integer} object.
	 */
	protected void assureRectsCreated(final int tabCount) {
		final int rectArrayLen = rects.length;
		if (tabCount != rectArrayLen) {
			final Rectangle[] tempRectArray = new Rectangle[tabCount];
			System.arraycopy(rects, 0, tempRectArray, 0, Math.min(rectArrayLen, tabCount));
			rects = tempRectArray;
			for ( int rectIndex = rectArrayLen; rectIndex < tabCount; rectIndex++) {
				rects[rectIndex] = new Rectangle();
			}
		}
	}

	/**
	 * <p>calculateMaxTabHeight.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int calculateMaxTabHeight(final int tabPlacement) {
		final int tc = tabPane.getTabCount();
		int result = 0;
		final int fontHeight = getFontMetrics().getHeight();
		for ( int i = 0; i < tc; i++) {
			result = Math.max(calculateTabHeight(tabPlacement, i, fontHeight), result);
		}
		return result;
	}

	/**
	 * <p>calculateMaxTabWidth.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int calculateMaxTabWidth(final int tabPlacement) {
		final int tc = tabPane.getTabCount();
		int result = 0;
		for ( int i = 0; i < tc; i++) {
			result = Math.max(calculateTabWidth(tabPlacement, i, getFontMetrics()), result);
		}
		return result;
	}

	/**
	 * <p>calculateTabAreaHeight.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param horizRunCount a {@link java.lang.Integer} object.
	 * @param maxTabHeight a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int calculateTabAreaHeight(final int tabPlacement, final int horizRunCount, final int maxTabHeight) {
		if (tabPlacement == SwingConstants.TOP || tabPlacement == SwingConstants.BOTTOM) {
			final Insets insets = getTabAreaInsets(tabPlacement);
			final int overlay = getTabRunOverlay(tabPlacement);
			return horizRunCount > 0 ? horizRunCount * (maxTabHeight - overlay) + overlay + insets.top + insets.bottom
					: 0;
		} else {
			return tabPane.getHeight();
		}
	}

	/**
	 * <p>calculateTabAreaWidth.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param vertRunCount a {@link java.lang.Integer} object.
	 * @param maxTabWidth a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int calculateTabAreaWidth(final int tabPlacement, final int vertRunCount, final int maxTabWidth) {
		if (tabPlacement == SwingConstants.LEFT || tabPlacement == SwingConstants.RIGHT) {
			final Insets insets = getTabAreaInsets(tabPlacement);
			final int overlay = getTabRunOverlay(tabPlacement);
			return vertRunCount > 0 ? vertRunCount * (maxTabWidth - overlay) + overlay + insets.left + insets.right : 0;
		} else {
			return tabPane.getWidth();
		}
	}

	/**
	 * <p>calculateTabHeight.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param fontHeight a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int calculateTabHeight(final int tabPlacement, final int tabIndex, final int fontHeight) {
		int height = 0;
		final Component tabComponent = getTabComponentAt(tabIndex);
		if (tabComponent != null) {
			height = tabComponent.getPreferredSize().height;
		} else {
			final View v = getTextViewForTab(tabIndex);
			if (v != null) {
				// html
				height += (int) v.getPreferredSpan(View.Y_AXIS);
			} else {
				// plain text
				height += fontHeight;
			}
			final Icon icon = getIconForTab(tabIndex);
			if (icon != null) {
				height = Math.max(height, icon.getIconHeight());
			}
		}
		final Insets ti = getTabInsets(tabPlacement, tabIndex);
		height += ti.top + ti.bottom + 2;
		return height;
	}

	/**
	 * <p>calculateTabWidth.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param metrics a {@link java.awt.FontMetrics} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int calculateTabWidth(final int tabPlacement, final int tabIndex, final FontMetrics metrics) {
		final Insets insets = getTabInsets(tabPlacement, tabIndex);
		int width = insets.left + insets.right + 3;
		final Component tabComponent = getTabComponentAt(tabIndex);
		if (tabComponent != null) {
			width += tabComponent.getPreferredSize().width;
		} else {
			final Icon icon = getIconForTab(tabIndex);
			if (icon != null) {
				width += icon.getIconWidth() + textIconGap;
			}
			final View v = getTextViewForTab(tabIndex);
			if (v != null) {
				// html
				width += (int) v.getPreferredSpan(View.X_AXIS);
			} else {
				// plain text
				final String title = tabPane.getTitleAt(tabIndex);
				width += SwingUtilities.computeStringWidth(metrics, title);
			}
		}

		return width;
	}

	ActionMap createActionMap() {
		final ActionMap map = new ActionMapUIResource();
		map.put("navigateNext", new NextAction());
		map.put("navigatePrevious", new PreviousAction());
		map.put("navigateRight", new RightAction());
		map.put("navigateLeft", new LeftAction());
		map.put("navigateUp", new UpAction());
		map.put("navigateDown", new DownAction());
		map.put("navigatePageUp", new PageUpAction());
		map.put("navigatePageDown", new PageDownAction());
		map.put("requestFocus", new RequestFocusAction());
		map.put("requestFocusForVisibleComponent", new RequestFocusForVisibleAction());
		map.put("setSelectedIndex", new SetSelectedIndexAction());
		map.put("scrollTabsForwardAction", new ScrollTabsForwardAction());
		map.put("scrollTabsBackwardAction", new ScrollTabsBackwardAction());
		map.put("scrollTabsPopupMenuAction", new ScrollTabsPopupMenuAction());
		return map;
	}

	/**
	 * <p>createChangeListener.</p>
	 *
	 * @return a {@link javax.swing.event.ChangeListener} object.
	 */
	protected ChangeListener createChangeListener() {
		return new TabSelectionHandler();
	}

	/**
	 * <p>createComponentListener.</p>
	 *
	 * @return a {@link java.awt.event.ComponentListener} object.
	 */
	protected ComponentListener createComponentListener() {
		return new TabComponentHandler();
	}

	private Polygon createCroppedTabClip(final int tabPlacement, final Rectangle tabRect, final int cropline) {
		final int rlen;
		final int start;
		final int end;
		final int ostart;

		switch (tabPlacement) {
		case LEFT:
		case RIGHT:
			rlen = tabRect.width;
			start = tabRect.x;
			end = tabRect.x + tabRect.width;
			ostart = tabRect.y;
			break;
		case TOP:
		case BOTTOM:
		default:
			rlen = tabRect.height;
			start = tabRect.y;
			end = tabRect.y + tabRect.height;
			ostart = tabRect.x;
		}
		int rcnt = rlen / CROP_SEGMENT;
		if (rlen % CROP_SEGMENT > 0) {
			rcnt++;
		}
		final int npts = 2 + rcnt * 8;
		final int[] xp = new int[npts];
		final int[] yp = new int[npts];
		int pcnt = 0;

		xp[pcnt] = ostart;
		yp[pcnt++] = end;
		xp[pcnt] = ostart;
		yp[pcnt++] = start;
		for ( int i = 0; i < rcnt; i++) {
			for ( int j = 0; j < xCropLen.length; j++) {
				xp[pcnt] = cropline - xCropLen[j];
				yp[pcnt] = start + i * CROP_SEGMENT + yCropLen[j];
				if (yp[pcnt] >= end) {
					yp[pcnt] = end;
					pcnt++;
					break;
				}
				pcnt++;
			}
		}
		if (tabPlacement == SwingConstants.TOP || tabPlacement == SwingConstants.BOTTOM) {
			return new Polygon(xp, yp, pcnt);
		} else {
			// LEFT or RIGHT
			return new Polygon(yp, xp, pcnt);
		}
	}

	/**
	 * <p>createFocusListener.</p>
	 *
	 * @return a {@link java.awt.event.FocusListener} object.
	 */
	protected FocusListener createFocusListener() {
		return new FocusHandler();
	}

	private List<View> createHTMLViewList() {
		final ArrayList<View> viewList = new ArrayList<>();
		final int count = tabPane.getTabCount();
		for ( int i = 0; i < count; i++) {
			final String title = tabPane.getTitleAt(i);
			if (BasicHTML.isHTMLString(title)) {
				viewList.add(BasicHTML.createHTMLView(tabPane, title));
			} else {
				viewList.add(null);
			}
		}
		return viewList;
	}

	/**
	 * Invoked by installUI to create a layout manager object to manage
	 * the JTabbedPane.
	 *
	 * @return a layout manager object
	 * @see TabbedPaneLayout
	 * @see javax.swing.JTabbedPane#getTabLayoutPolicy
	 */
	protected LayoutManager createLayoutManager() {
		if (tabPane.getTabLayoutPolicy() == JTabbedPane.SCROLL_TAB_LAYOUT) {
			return new TabbedPaneScrollLayout();
		}
		/* WRAP_TAB_LAYOUT */
		return new TabbedPaneLayout();
	}

	/**
	 * <p>createMouseListener.</p>
	 *
	 * @return a {@link java.awt.event.MouseListener} object.
	 */
	protected MouseListener createMouseListener() {
		return new MouseHandler();
	}

	/**
	 * <p>createMouseMotionListener.</p>
	 *
	 * @return a {@link java.awt.event.MouseMotionListener} object.
	 */
	protected MouseMotionListener createMouseMotionListener() {
		return new MouseMotionHandler();
	}

	/**
	 * <p>createPropertyChangeListener.</p>
	 *
	 * @return a {@link java.beans.PropertyChangeListener} object.
	 */
	protected PropertyChangeListener createPropertyChangeListener() {
		return new PropertyChangeHandler();
	}

	// TabbedPaneUI methods
	private void ensureCurrentLayout() {
		// TabPane maybe still invalid. See bug 4237677.
		((TabbedPaneLayout) tabPane.getLayout()).calculateLayoutInfo();
	}

	/**
	 * <p>expandTabRunsArray.</p>
	 */
	protected void expandTabRunsArray() {
		final int rectLen = tabRuns.length;
		final int[] newArray = new int[rectLen + 10];
		System.arraycopy(tabRuns, 0, newArray, 0, runCount);
		tabRuns = newArray;
	}

	ActionMap getActionMap() {
		ActionMap map = (ActionMap) UIManager.get("TabbedPane.actionMap");

		if (map == null) {
			map = createActionMap();
			if (map != null) {
				UIManager.getLookAndFeelDefaults().put("TabbedPane.actionMap", map);
			}
		}
		return map;
	}

	/*
	 * Returns the index of the tab closest to the passed in location, note that the
	 * returned tab may not contain the location x,y.
	 */
	/**
	 * <p>getClosestTab.</p>
	 *
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getClosestTab(final int x, final int y) {
		int min = 0;
		final int tc = Math.min(rects.length, tabPane.getTabCount());
		int max = tc;
		final int tabPlacement = tabPane.getTabPlacement();
		final boolean useX = tabPlacement == TOP || tabPlacement == BOTTOM;
		final int want = useX ? x : y;

		while (min != max) {
			final int current = (max + min) / 2;
			final int minLoc;
			final int maxLoc;

			if (useX) {
				minLoc = rects[current].x;
				maxLoc = minLoc + rects[current].width;
			} else {
				minLoc = rects[current].y;
				maxLoc = minLoc + rects[current].height;
			}
			if (want < minLoc) {
				max = current;
				if (min == max) {
					return Math.max(0, current - 1);
				}
			} else if (want >= maxLoc) {
				min = current;
				if (max - min <= 1) {
					return Math.max(current + 1, tc - 1);
				}
			} else {
				return current;
			}
		}
		return min;
	}

	/**
	 * <p>getContentBorderColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	protected Color getContentBorderColor() {
		return AbstractLookAndFeel.getFrameColor();
	}

	/**
	 * <p>getContentBorderColors.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @return an array of {@link java.awt.Color} objects.
	 */
	protected Color[] getContentBorderColors(final int tabPlacement) {
		final int sepHeight = tabAreaInsets.bottom;
		final Color[] selColors = AbstractLookAndFeel.getTheme().getSelectedColors();
		final Color loColor = selColors[selColors.length - 1];
		final Color darkLoColor = ColorHelper.darker(loColor, 20);
		return ColorHelper.createColorArr(loColor, darkLoColor, sepHeight);
	}

	/**
	 * <p>Getter for the field contentBorderInsets.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @return a {@link java.awt.Insets} object.
	 */
	public Insets getContentBorderInsets(final int tabPlacement) {
		if (tabPane.getBorder() == null) {
			return NULL_BORDER_INSETS;
		}
		return contentBorderInsets;
	}

	/**
	 * <p>getFontMetrics.</p>
	 *
	 * @return a {@link java.awt.FontMetrics} object.
	 */
	protected FontMetrics getFontMetrics() {
		final Font font = tabPane.getFont().deriveFont(Font.BOLD);
		return JTattooUtilities.getFontMetrics(tabPane, null, font);
	}

	/**
	 * <p>getGapColor.</p>
	 *
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @return a {@link java.awt.Color} object.
	 */
	protected Color getGapColor(final int tabIndex) {
		if (isTabOpaque() || tabIndex == tabPane.getSelectedIndex()) {
			if (tabIndex >= 0 && tabIndex < tabCount) {
				final Color[] tabColors = getTabColors(tabIndex, tabIndex == tabPane.getSelectedIndex(), false);
				if (tabColors != null && tabColors.length > 0) {
					return tabColors[tabColors.length - 1];
				} else {
					return tabPane.getBackgroundAt(tabIndex);
				}
			}
		}
		if (!tabPane.isOpaque()) {
			Container parent = tabPane.getParent();
			while (parent != null) {
				if (parent.isOpaque()) {
					return parent.getBackground();
				}
				parent = parent.getParent();
			}
		}
		return tabAreaBackground;
	}

	/**
	 * <p>getHiBorderColor.</p>
	 *
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @return a {@link java.awt.Color} object.
	 */
	protected Color getHiBorderColor(final int tabIndex) {
		final Color backColor = tabPane.getBackgroundAt(tabIndex);
		if (tabIndex == tabPane.getSelectedIndex()) {
			if (backColor instanceof UIResource) {
				return MetalLookAndFeel.getControlHighlight();
			} else {
				return ColorHelper.brighter(backColor, 40);
			}
		}
		if (tabIndex >= 0 && tabIndex <= tabCount) {
			if (!isTabOpaque() || backColor instanceof UIResource) {
				return MetalLookAndFeel.getControlHighlight();
			} else {
				return ColorHelper.brighter(backColor, 40);
			}
		}
		return MetalLookAndFeel.getControlHighlight();
	}

	/**
	 * <p>getIconForTab.</p>
	 *
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @return a {@link javax.swing.Icon} object.
	 */
	protected Icon getIconForTab(final int tabIndex) {
		if (tabIndex >= 0 && tabIndex < tabCount) {
			return !tabPane.isEnabled() || !tabPane.isEnabledAt(tabIndex) ? tabPane.getDisabledIconAt(tabIndex)
					: tabPane.getIconAt(tabIndex);
		}
		return null;
	}

	InputMap getInputMap(final int condition) {
		if (condition == JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT) {
			return (InputMap) UIManager.get("TabbedPane.ancestorInputMap");
		} else if (condition == JComponent.WHEN_FOCUSED) {
			return (InputMap) UIManager.get("TabbedPane.focusInputMap");
		}
		return null;
	}

	/**
	 * <p>getLoBorderColor.</p>
	 *
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @return a {@link java.awt.Color} object.
	 */
	protected Color getLoBorderColor(final int tabIndex) {
		return MetalLookAndFeel.getControlDarkShadow();
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getMaximumSize(final JComponent c) {
		// Default to LayoutManager's maximumLayoutSize
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getMinimumSize(final JComponent c) {
		// Default to LayoutManager's minimumLayoutSize
		return null;
	}

	/**
	 * <p>getNextTabIndex.</p>
	 *
	 * @param base a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getNextTabIndex(final int base) {
		return (base + 1) % tabPane.getTabCount();
	}

	/**
	 * <p>getNextTabIndexInRun.</p>
	 *
	 * @param tabCount a {@link java.lang.Integer} object.
	 * @param base a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getNextTabIndexInRun(final int tabCount, final int base) {
		if (runCount < 2) {
			return getNextTabIndex(base);
		}
		final int currentRun = getRunForTab(tabCount, base);
		final int next = getNextTabIndex(base);
		if (next == tabRuns[getNextTabRun(currentRun)]) {
			return tabRuns[currentRun];
		}
		return next;
	}

	/**
	 * <p>getNextTabRun.</p>
	 *
	 * @param baseRun a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getNextTabRun(final int baseRun) {
		return (baseRun + 1) % runCount;
	}

	// Geometry
	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize(final JComponent c) {
		// Default to LayoutManager's preferredLayoutSize
		return null;
	}

	/**
	 * <p>getPreviousTabIndex.</p>
	 *
	 * @param base a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getPreviousTabIndex(final int base) {
		final int tabIndex = base - 1 >= 0 ? base - 1 : tabPane.getTabCount() - 1;
		return Math.max(tabIndex, 0);
	}

	/**
	 * <p>getPreviousTabIndexInRun.</p>
	 *
	 * @param tabCount a {@link java.lang.Integer} object.
	 * @param base a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getPreviousTabIndexInRun(final int tabCount, final int base) {
		if (runCount < 2) {
			return getPreviousTabIndex(base);
		}
		final int currentRun = getRunForTab(tabCount, base);
		if (base == tabRuns[currentRun]) {
			final int previous = tabRuns[getNextTabRun(currentRun)] - 1;
			return previous != -1 ? previous : tabCount - 1;
		}
		return getPreviousTabIndex(base);
	}

	/**
	 * <p>getPreviousTabRun.</p>
	 *
	 * @param baseRun a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getPreviousTabRun(final int baseRun) {
		final int runIndex = baseRun - 1 >= 0 ? baseRun - 1 : runCount - 1;
		return Math.max(runIndex, 0);
	}

	/**
	 * <p>getRunForTab.</p>
	 *
	 * @param tabCount a {@link java.lang.Integer} object.
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getRunForTab(final int tabCount, final int tabIndex) {
		for ( int i = 0; i < runCount; i++) {
			final int first = tabRuns[i];
			final int last = lastTabInRun(tabCount, i);
			if (tabIndex >= first && tabIndex <= last) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * <p>Getter for the field selectedTabPadInsets.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @return a {@link java.awt.Insets} object.
	 */
	protected Insets getSelectedTabPadInsets(final int tabPlacement) {
		rotateInsets(selectedTabPadInsets, currentPadInsets, tabPlacement);
		return currentPadInsets;
	}

	/**
	 * <p>Getter for the field tabAreaInsets.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @return a {@link java.awt.Insets} object.
	 */
	protected Insets getTabAreaInsets(final int tabPlacement) {
		rotateInsets(tabAreaInsets, currentTabAreaInsets, tabPlacement);
		return currentTabAreaInsets;
	}

	/*
	 * Returns the tab index which intersects the specified point in the coordinate
	 * space of the component where the tabs are actually rendered, which could be
	 * the JTabbedPane (for WRAP_TAB_LAYOUT) or a ScrollableTabPanel
	 * (SCROLL_TAB_LAYOUT).
	 */
	/**
	 * <p>getTabAtLocation.</p>
	 *
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getTabAtLocation(final int x, final int y) {
		ensureCurrentLayout();
		final int tc = tabPane.getTabCount();
		for ( int i = 0; i < tc; i++) {
			if (rects[i].contains(x, y)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the bounds of the specified tab in the coordinate space of the
	 * JTabbedPane component. This is required because the tab rects are by default
	 * defined in the coordinate space of the component where they are rendered,
	 * which could be the JTabbedPane (for WRAP_TAB_LAYOUT) or a ScrollableTabPanel
	 * (SCROLL_TAB_LAYOUT). This method should be used whenever the tab rectangle
	 * must be relative to the JTabbedPane itself and the result should be placed in
	 * a designated Rectangle object (rather than instantiating and returning a new
	 * Rectangle each time). The tab index parameter must be a valid tabbed pane tab
	 * index (0 to tab count - 1, inclusive). The destination rectangle parameter
	 * must be a valid Rectangle instance. The handling of invalid
	 * parameters is unspecified.
	 *
	 * @param tabIndex the index of the tab
	 * @param dest     the rectangle where the result should be placed
	 * @return the resulting rectangle
	 * @since 1.4
	 */
	protected Rectangle getTabBounds(final int tabIndex, final Rectangle dest) {
		dest.width = rects[tabIndex].width;
		dest.height = rects[tabIndex].height;

		if (scrollableTabLayoutEnabled()) { // SCROLL_TAB_LAYOUT
			// Need to translate coordinates based on viewport location &
			// view position
			final Point vpp = tabScroller.viewport.getLocation();
			final Point viewp = tabScroller.viewport.getViewPosition();
			dest.x = rects[tabIndex].x + vpp.x - viewp.x;
			dest.y = rects[tabIndex].y + vpp.y - viewp.y;

		} else { // WRAP_TAB_LAYOUT
			dest.x = rects[tabIndex].x;
			dest.y = rects[tabIndex].y;
		}
		return dest;
	}

	/*
	 * Returns the bounds of the specified tab index. The bounds are with respect to
	 * the JTabbedPane's coordinate space.
	 */
	/** {@inheritDoc} */
	@Override
	public Rectangle getTabBounds(final JTabbedPane pane, final int i) {
		ensureCurrentLayout();
		final Rectangle tabRect = new Rectangle();
		return getTabBounds(i, tabRect);
	}

	// colors
	/**
	 * <p>getTabColors.</p>
	 *
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param isSelected a boolean.
	 * @param isRollover a boolean.
	 * @return an array of {@link java.awt.Color} objects.
	 */
	protected Color[] getTabColors(final int tabIndex, final boolean isSelected, final boolean isRollover) {
		Color[] colorArr = AbstractLookAndFeel.getTheme().getTabColors();
		if (tabIndex >= 0 && tabIndex < tabPane.getTabCount()) {
			final boolean isEnabled = tabPane.isEnabledAt(tabIndex);
			final Color backColor = tabPane.getBackgroundAt(tabIndex);
			if (backColor instanceof UIResource) {
				if (isSelected) {
					colorArr = AbstractLookAndFeel.getTheme().getSelectedColors();
				} else if (isRollover && isEnabled) {
					colorArr = AbstractLookAndFeel.getTheme().getRolloverColors();
				} else {
					if (JTattooUtilities.isFrameActive(tabPane)) {
						colorArr = AbstractLookAndFeel.getTheme().getTabColors();
					} else {
						colorArr = AbstractLookAndFeel.getTheme().getInactiveColors();
					}
				}
			} else if (backColor != null) {
				if (isSelected) {
					colorArr = ColorHelper.createColorArr(ColorHelper.brighter(backColor, 60), backColor, 20);
				} else if (isRollover && isEnabled) {
					colorArr = ColorHelper.createColorArr(ColorHelper.brighter(backColor, 80),
							ColorHelper.brighter(backColor, 20), 20);
				} else {
					colorArr = ColorHelper.createColorArr(ColorHelper.brighter(backColor, 40),
							ColorHelper.darker(backColor, 10), 20);
				}
			}
		}
		return colorArr;
	}

	private Component getTabComponentAt(final int index) {
		return tabPane.getTabComponentAt(index);
	}

	/**
	 * <p>getTabFont.</p>
	 *
	 * @param isSelected a boolean.
	 * @return a {@link java.awt.Font} object.
	 */
	protected Font getTabFont(final boolean isSelected) {
		return tabPane.getFont();
	}

	/**
	 * <p>Getter for the field tabInsets.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @return a {@link java.awt.Insets} object.
	 */
	protected Insets getTabInsets(final int tabPlacement, final int tabIndex) {
		return tabInsets;
	}

	/**
	 * <p>getTabLabelShiftX.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param isSelected a boolean.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getTabLabelShiftX(final int tabPlacement, final int tabIndex, final boolean isSelected) {
		return 0;
	}

	/**
	 * <p>getTabLabelShiftY.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param isSelected a boolean.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getTabLabelShiftY(final int tabPlacement, final int tabIndex, final boolean isSelected) {
		if (!isSelected) {
			if (tabPlacement == TOP) {
				return 1;
			} else if (tabPlacement == BOTTOM) {
				return -1;
			}
		}
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public int getTabRunCount(final JTabbedPane pane) {
		ensureCurrentLayout();
		return runCount;
	}

	/**
	 * <p>getTabRunIndent.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param run a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getTabRunIndent(final int tabPlacement, final int run) {
		return 0;
	}

	/**
	 * <p>getTabRunOffset.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param tabCount a {@link java.lang.Integer} object.
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param forward a boolean.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getTabRunOffset(final int tabPlacement, final int tabCount, final int tabIndex, final boolean forward) {
		final int run = getRunForTab(tabCount, tabIndex);
		final int offset;
		switch (tabPlacement) {
		case LEFT: {
			if (run == 0) {
				offset = forward ? -(calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth) - maxTabWidth)
						: -maxTabWidth;

			} else if (run == runCount - 1) {
				offset = forward ? maxTabWidth
						: calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth) - maxTabWidth;
			} else {
				offset = forward ? maxTabWidth : -maxTabWidth;
			}
			break;
		}
		case RIGHT: {
			if (run == 0) {
				offset = forward ? maxTabWidth
						: calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth) - maxTabWidth;
			} else if (run == runCount - 1) {
				offset = forward ? -(calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth) - maxTabWidth)
						: -maxTabWidth;
			} else {
				offset = forward ? maxTabWidth : -maxTabWidth;
			}
			break;
		}
		case BOTTOM: {
			if (run == 0) {
				offset = forward ? maxTabHeight
						: calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight) - maxTabHeight;
			} else if (run == runCount - 1) {
				offset = forward ? -(calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight) - maxTabHeight)
						: -maxTabHeight;
			} else {
				offset = forward ? maxTabHeight : -maxTabHeight;
			}
			break;
		}
		case TOP:
		default: {
			if (run == 0) {
				offset = forward ? -(calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight) - maxTabHeight)
						: -maxTabHeight;
			} else if (run == runCount - 1) {
				offset = forward ? maxTabHeight
						: calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight) - maxTabHeight;
			} else {
				offset = forward ? maxTabHeight : -maxTabHeight;
			}
		}
		}
		return offset;
	}

	/**
	 * <p>Getter for the field tabRunOverlay.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int getTabRunOverlay(final int tabPlacement) {
		return tabRunOverlay;
	}

	/**
	 * Returns the text View object required to render stylized text (HTML) for the
	 * specified tab or null if no specialized text rendering is needed for this
	 * tab. This is provided to support html rendering inside tabs.
	 *
	 * @param tabIndex the index of the tab
	 * @return the text view to render the tab's text or null if no specialized
	 *         rendering is required
	 * @since 1.4
	 */
	protected View getTextViewForTab(final int tabIndex) {
		if (htmlViews != null && htmlViews.size() > tabIndex) {
			return htmlViews.get(tabIndex);
		}
		return null;
	}

	// BaseTabbedPaneUI methods
	/**
	 * <p>Getter for the field visibleComponent.</p>
	 *
	 * @return a {@link java.awt.Component} object.
	 */
	protected Component getVisibleComponent() {
		return visibleComponent;
	}

	/**
	 * <p>hasInnerBorder.</p>
	 *
	 * @return a boolean.
	 */
	protected boolean hasInnerBorder() {
		return false;
	}

	/**
	 * Installs the state needed for mnemonics.
	 */
	private void initMnemonics() {
		mnemonicToIndexMap = new HashMap<>();
		mnemonicInputMap = new ComponentInputMapUIResource(tabPane);
		mnemonicInputMap.setParent(SwingUtilities.getUIInputMap(tabPane, JComponent.WHEN_IN_FOCUSED_WINDOW));
		SwingUtilities.replaceUIInputMap(tabPane, JComponent.WHEN_IN_FOCUSED_WINDOW, mnemonicInputMap);
	}

	/**
	 * Creates and installs any required subcomponents for the JTabbedPane. Invoked
	 * by installUI.
	 *
	 * @since 1.4
	 */
	protected void installComponents() {
		if (scrollableTabLayoutEnabled()) {
			if (tabScroller == null) {
				tabScroller = new ScrollableTabSupport(this, tabPane, rects);
				tabPane.add(tabScroller.viewport);
				tabPane.add(tabScroller.scrollForwardButton);
				tabPane.add(tabScroller.scrollBackwardButton);
				tabPane.add(tabScroller.popupMenuButton);
				tabScroller.tabPanel.setBackground(tabAreaBackground);
			}
		}
		installTabContainer();
	}

	/**
	 * <p>installDefaults.</p>
	 */
	protected void installDefaults() {
		LookAndFeel.installColorsAndFont(tabPane, "TabbedPane.background", "TabbedPane.foreground", "TabbedPane.font");
		tabAreaBackground = UIManager.getColor("TabbedPane.tabAreaBackground");
		selectedColor = UIManager.getColor("TabbedPane.selected");
		textIconGap = UIManager.getInt("TabbedPane.textIconGap");
		tabInsets = UIManager.getInsets("TabbedPane.tabInsets");
		selectedTabPadInsets = UIManager.getInsets("TabbedPane.selectedTabPadInsets");
		tabAreaInsets = UIManager.getInsets("TabbedPane.tabAreaInsets");
		contentBorderInsets = UIManager.getInsets("TabbedPane.contentBorderInsets");
		tabRunOverlay = UIManager.getInt("TabbedPane.tabRunOverlay");
		tabPane.setBorder(UIManager.getBorder("TabbedPane.boder"));
	}

	/**
	 * <p>installKeyboardActions.</p>
	 */
	protected void installKeyboardActions() {
		InputMap km = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		SwingUtilities.replaceUIInputMap(tabPane, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, km);
		km = getInputMap(JComponent.WHEN_FOCUSED);
		SwingUtilities.replaceUIInputMap(tabPane, JComponent.WHEN_FOCUSED, km);
		final ActionMap am = getActionMap();
		SwingUtilities.replaceUIActionMap(tabPane, am);
		if (scrollableTabLayoutEnabled()) {
			tabScroller.scrollForwardButton.setAction(am.get("scrollTabsForwardAction"));
			tabScroller.scrollBackwardButton.setAction(am.get("scrollTabsBackwardAction"));
			tabScroller.popupMenuButton.setAction(am.get("scrollTabsPopupMenuAction"));
		}
	}

	/**
	 * <p>installListeners.</p>
	 */
	protected void installListeners() {
		if ((propertyChangeListener = createPropertyChangeListener()) != null) {
			tabPane.addPropertyChangeListener(propertyChangeListener);
		}
		if ((tabChangeListener = createChangeListener()) != null) {
			tabPane.addChangeListener(tabChangeListener);
		}
		if ((tabComponentListener = createComponentListener()) != null) {
			tabPane.addComponentListener(tabComponentListener);
		}
		if ((mouseListener = createMouseListener()) != null) {
			if (scrollableTabLayoutEnabled()) {
				tabScroller.tabPanel.addMouseListener(mouseListener);

			} else { // WRAP_TAB_LAYOUT
				tabPane.addMouseListener(mouseListener);
			}
		}
		if ((mouseMotionListener = createMouseMotionListener()) != null) {
			if (scrollableTabLayoutEnabled()) {
				tabScroller.tabPanel.addMouseMotionListener(mouseMotionListener);

			} else { // WRAP_TAB_LAYOUT
				tabPane.addMouseMotionListener(mouseMotionListener);
			}
		}
		if ((focusListener = createFocusListener()) != null) {
			tabPane.addFocusListener(focusListener);
		}
		// PENDING(api) : See comment for ContainerHandler
		containerListener = new ContainerHandler();
		tabPane.addContainerListener(containerListener);
		if (tabPane.getTabCount() > 0) {
			htmlViews = createHTMLViewList();
		}
	}

	private void installTabContainer() {
		for ( int i = 0; i < tabPane.getTabCount(); i++) {
			final Component tabComponent = getTabComponentAt(i);
			if (tabComponent != null) {
				if (tabContainer == null) {
					tabContainer = new TabContainer();
				}
				tabContainer.add(tabComponent);
				addMyPropertyChangeListeners(tabComponent);
			}
		}
		if (tabContainer == null) {
			return;
		}
		if (scrollableTabLayoutEnabled()) {
			tabScroller.tabPanel.add(tabContainer);
		} else {
			tabPane.add(tabContainer);
		}
	}

	// UI Installation/De-installation
	/** {@inheritDoc} */
	@Override
	public void installUI(final JComponent c) {
		this.tabPane = (JTabbedPane) c;
		c.setLayout(createLayoutManager());
		installComponents();
		installDefaults();
		installListeners();
		installKeyboardActions();
	}

	/**
	 * <p>isContentOpaque.</p>
	 *
	 * @return a boolean.
	 */
	protected boolean isContentOpaque() {
		if (!tabPane.isOpaque()) {
			if (UIManager.get("TabbedPane.contentOpaque") != null) {
				return UIManager.getBoolean("TabbedPane.contentOpaque");
			}
		}
		return true;
	}

	/**
	 * <p>isTabOpaque.</p>
	 *
	 * @return a boolean.
	 */
	protected boolean isTabOpaque() {
		if (!tabPane.isOpaque()) {
			if (UIManager.get("TabbedPane.tabsOpaque") != null) {
				return UIManager.getBoolean("TabbedPane.tabsOpaque");
			}
		}
		return true;
	}

	/**
	 * <p>lastTabInRun.</p>
	 *
	 * @param tabCount a {@link java.lang.Integer} object.
	 * @param run a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected int lastTabInRun(final int tabCount, final int run) {
		if (runCount == 1) {
			return tabCount - 1;
		}
		final int nextRun = run == runCount - 1 ? 0 : run + 1;
		if (tabRuns[nextRun] == 0) {
			return tabCount - 1;
		}
		return tabRuns[nextRun] - 1;
	}

	/**
	 * <p>layoutLabel.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param metrics a {@link java.awt.FontMetrics} object.
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param title a {@link java.lang.String} object.
	 * @param icon a {@link javax.swing.Icon} object.
	 * @param tabRect a {@link java.awt.Rectangle} object.
	 * @param iconRect a {@link java.awt.Rectangle} object.
	 * @param textRect a {@link java.awt.Rectangle} object.
	 * @param isSelected a boolean.
	 */
	protected void layoutLabel(final int tabPlacement, final FontMetrics metrics, final int tabIndex, final String title, final Icon icon,
							   final Rectangle tabRect, final Rectangle iconRect, final Rectangle textRect, final boolean isSelected) {
		textRect.x = textRect.y = iconRect.x = iconRect.y = 0;
		final View v = getTextViewForTab(tabIndex);
		if (v != null) {
			tabPane.putClientProperty("html", v);
		}

		SwingUtilities.layoutCompoundLabel(tabPane, metrics, title, icon, SwingConstants.CENTER, SwingConstants.CENTER,
				SwingConstants.CENTER, SwingConstants.TRAILING, tabRect, iconRect, textRect, textIconGap);

		tabPane.putClientProperty("html", null);

		final int xNudge = getTabLabelShiftX(tabPlacement, tabIndex, isSelected);
		final int yNudge = getTabLabelShiftY(tabPlacement, tabIndex, isSelected);
		iconRect.x += xNudge;
		iconRect.y += yNudge;
		textRect.x += xNudge;
		textRect.y += yNudge;
	}

	// Tab Navigation methods
	/**
	 * <p>navigateSelectedTab.</p>
	 *
	 * @param direction a {@link java.lang.Integer} object.
	 */
	public void navigateSelectedTab(final int direction) {
		final int tabPlacement = tabPane.getTabPlacement();
		final int current = tabPane.getSelectedIndex();
		final int tc = tabPane.getTabCount();
		final boolean leftToRight = JTattooUtilities.isLeftToRight(tabPane);

		// If we have no tabs then don't navigate.
		if (tc <= 0) {
			return;
		}

		final int offset;
		switch (tabPlacement) {
		case NEXT:
			selectNextTab(current);
			break;
		case PREVIOUS:
			selectPreviousTab(current);
			break;
		case LEFT:
		case RIGHT:
			switch (direction) {
			case NORTH:
				selectPreviousTabInRun(current);
				break;
			case SOUTH:
				selectNextTabInRun(current);
				break;
			case WEST:
				offset = getTabRunOffset(tabPlacement, tc, current, false);
				selectAdjacentRunTab(tabPlacement, current, offset);
				break;
			case EAST:
				offset = getTabRunOffset(tabPlacement, tc, current, true);
				selectAdjacentRunTab(tabPlacement, current, offset);
				break;
			default:
			}
			break;
		case BOTTOM:
		case TOP:
		default:
			switch (direction) {
			case NORTH:
				offset = getTabRunOffset(tabPlacement, tc, current, false);
				selectAdjacentRunTab(tabPlacement, current, offset);
				break;
			case SOUTH:
				offset = getTabRunOffset(tabPlacement, tc, current, true);
				selectAdjacentRunTab(tabPlacement, current, offset);
				break;
			case EAST:
				if (leftToRight) {
					selectNextTabInRun(current);
				} else {
					selectPreviousTabInRun(current);
				}
				break;
			case WEST:
				if (leftToRight) {
					selectPreviousTabInRun(current);
				} else {
					selectNextTabInRun(current);
				}
				break;
			default:
			}
		}
	}

	// UI Rendering
	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		final int tc = tabPane.getTabCount();
		if (tabCount != tc) {
			tabCount = tc;
			updateMnemonics();
		}

		final int selectedIndex = tabPane.getSelectedIndex();
		final int tabPlacement = tabPane.getTabPlacement();

		ensureCurrentLayout();

		// Paint content border
		paintContentBorder(g, tabPlacement, selectedIndex, 0, 0, c.getWidth(), c.getHeight());

		// Paint tab area
		// If scrollable tabs are enabled, the tab area will be
		// painted by the scrollable tab panel instead.
		//
		if (!scrollableTabLayoutEnabled()) {
			// WRAP_TAB_LAYOUT
			paintTabArea(g, tabPlacement, selectedIndex);
		}
	}

	/**
	 * <p>paintBottomTabBorder.</p>
	 *
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param g a {@link java.awt.Graphics} object.
	 * @param x1 a {@link java.lang.Integer} object.
	 * @param y1 a {@link java.lang.Integer} object.
	 * @param x2 a {@link java.lang.Integer} object.
	 * @param y2 a {@link java.lang.Integer} object.
	 * @param isSelected a boolean.
	 */
	protected void paintBottomTabBorder(final int tabIndex, final Graphics g, final int x1, final int y1, final int x2, final int y2, final boolean isSelected) {
		final int tc = tabPane.getTabCount();
		final int currentRun = getRunForTab(tc, tabIndex);
		final int lastIndex = lastTabInRun(tc, currentRun);
		final int firstIndex = tabRuns[currentRun];
		final boolean leftToRight = JTattooUtilities.isLeftToRight(tabPane);

		final Color loColor = getLoBorderColor(tabIndex);
		final Color hiColor = getHiBorderColor(tabIndex);

		g.setColor(loColor);
		g.drawLine(x1, y1, x1, y2 - GAP);
		g.drawLine(x1, y2 - GAP, x1 + GAP, y2);
		g.drawLine(x1 + GAP, y2, x2, y2);
		g.drawLine(x2, y2, x2, y1);
		g.setColor(hiColor);
		g.drawLine(x1 + 1, y1, x1 + 1, y2 - GAP - 1);
		g.drawLine(x1 + 1, y2 - GAP, x1 + GAP, y2 - 1);

		// paint gap
		final int gapTabIndex = getTabAtLocation(x1 + 2, y2 + 2);
		final Color gapColor = getGapColor(gapTabIndex);

		g.setColor(gapColor);
		for ( int i = 0; i < GAP; i++) {
			g.drawLine(x1, y2 - i, x1 + GAP - i - 1, y2 - i);
		}
		if (leftToRight) {
			if (tabIndex != firstIndex || currentRun != runCount - 1) {
				g.setColor(loColor);
				g.drawLine(x1, y2 - GAP, x1, y2);
			}
		} else {
			if (tabIndex != lastIndex || currentRun != runCount - 1) {
				g.setColor(loColor);
				g.drawLine(x1, y2 - GAP, x1, y2);
			}
		}
	}

	/**
	 * <p>paintContentBorder.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param selectedIndex a {@link java.lang.Integer} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param w a {@link java.lang.Integer} object.
	 * @param h a {@link java.lang.Integer} object.
	 */
	protected void paintContentBorder(final Graphics g, final int tabPlacement, final int selectedIndex, final int x, final int y, final int w, final int h) {
		final int tabAreaHeight = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
		final int tabAreaWidth = calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);

		// paint the background
		if (tabPane.isOpaque()) {
			final int xt = tabPlacement == RIGHT ? w - tabAreaWidth : 0;
			final int yt = tabPlacement == BOTTOM ? h - tabAreaHeight : 0;
			final int wt = tabPlacement == TOP || tabPlacement == BOTTOM ? w : tabAreaWidth;
			final int ht = tabPlacement == LEFT || tabPlacement == RIGHT ? h : tabAreaHeight;
			g.setColor(tabAreaBackground);
			g.fillRect(xt, yt, wt, ht);
		}
		if (isContentOpaque()) {
			final int xt = tabPlacement == LEFT ? tabAreaWidth : 0;
			final int yt = tabPlacement == TOP ? tabAreaHeight : 0;
			final int wt = tabPlacement == LEFT || tabPlacement == RIGHT ? w - tabAreaWidth : w;
			final int ht = tabPlacement == TOP || tabPlacement == BOTTOM ? h - tabAreaHeight : h;
			g.setColor(tabPane.getBackground());
			g.fillRect(xt, yt, wt, ht);
		}

		Insets bi = new Insets(0, 0, 0, 0);
		if (tabPane.getBorder() != null) {
			bi = tabPane.getBorder().getBorderInsets(tabPane);
		}
		if (hasInnerBorder()) {
			final Color loColor = MetalLookAndFeel.getControlDarkShadow();
			final Color hiColor = MetalLookAndFeel.getControlHighlight();
			g.setColor(loColor);
			switch (tabPlacement) {
			case TOP: {
				final int x1 = x + bi.left - 1;
				final int y1 = y + tabAreaHeight + bi.top - 2;
				final int x2 = x1 + w - bi.left - bi.right + 1;
				final int ws = w - bi.left - bi.right + 1;
				final int hs = h - tabAreaHeight - bi.top - bi.bottom + 2;

				if (tabPane.getBorder() == null) {
					g.drawLine(x1, y1, x2, y1);
					g.setColor(hiColor);
					g.drawLine(x1, y1 + 1, x2, y1 + 1);
				} else {
					g.drawRect(x1, y1, ws, hs);
					g.setColor(hiColor);
					g.drawLine(x1 + 1, y1 + 1, x2 - 1, y1 + 1);
				}
				break;
			}
			case LEFT: {
				final int x1 = x + tabAreaWidth + bi.left - 2;
				final int y1 = y + bi.top - 1;
				// int x2 = w - bi.right;
				final int y2 = y1 + h - bi.top - bi.bottom + 1;
				final int ws = w - tabAreaWidth - bi.left - bi.right + 2;
				final int hs = h - bi.top - bi.bottom + 1;

				if (tabPane.getBorder() == null) {
					g.drawLine(x1, y1, x1, y2);
					g.setColor(hiColor);
					g.drawLine(x1 + 1, y1, x1 + 1, y2);
				} else {
					g.drawRect(x1, y1, ws, hs);
					g.setColor(hiColor);
					g.drawLine(x1 + 1, y1 + 1, x1 + 1, y2 - 1);
				}
				break;
			}
			case BOTTOM: {
				final int x1 = x + bi.left - 1;
				final int y1 = y + bi.top - 1;
				final int x2 = x1 + w - bi.left - bi.right + 1;
				final int y2 = h - tabAreaHeight - bi.bottom;
				final int ws = w - bi.left - bi.right + 1;
				final int hs = h - tabAreaHeight - bi.top - bi.bottom + 2;

				if (tabPane.getBorder() == null) {
					g.drawLine(x1, y2, x2, y2);
				} else {
					g.drawRect(x1, y1, ws, hs);
				}
				break;
			}
			case RIGHT: {
				final int x1 = x + bi.left - 1;
				final int y1 = y + bi.top - 1;
				final int x2 = w - tabAreaWidth - bi.right + 1;
				final int y2 = y1 + h - bi.top - bi.bottom + 1;
				final int ws = w - tabAreaWidth - bi.left - bi.right + 2;
				final int hs = h - bi.top - bi.bottom + 1;

				if (tabPane.getBorder() == null) {
					g.drawLine(x2, y1, x2, y2);
				} else {
					g.drawRect(x1, y1, ws, hs);
				}
				break;
			}
			}
		} else {
			final int sepHeight = tabAreaInsets.bottom;
			if (sepHeight > 0) {
				switch (tabPlacement) {
				case TOP: {
					final Color[] colors = getContentBorderColors(tabPlacement);
					final int ys = y + tabAreaHeight - sepHeight + bi.top;
					for ( int i = 0; i < colors.length; i++) {
						g.setColor(colors[i]);
						g.drawLine(x, ys + i, x + w, ys + i);
					}
					break;
				}
				case LEFT: {
					final Color[] colors = getContentBorderColors(tabPlacement);
					final int xs = x + tabAreaWidth - sepHeight + bi.left;
					for ( int i = 0; i < colors.length; i++) {
						g.setColor(colors[i]);
						g.drawLine(xs + i, y, xs + i, y + h);
					}
					break;
				}
				case BOTTOM: {
					final Color[] colors = getContentBorderColors(tabPlacement);
					final int ys = y + h - tabAreaHeight - bi.bottom;
					for (int i = 0; i < colors.length; i++) {
						g.setColor(colors[i]);
						g.drawLine(x, ys + i, x + w, ys + i);
					}
					break;
				}
				case RIGHT: {
					final Color[] colors = getContentBorderColors(tabPlacement);
					final int xs = x + w - tabAreaWidth - bi.right;
					for ( int i = 0; i < colors.length; i++) {
						g.setColor(colors[i]);
						g.drawLine(xs + i, y, xs + i, y + h);
					}
					break;
				}
				}
			}
		}
	}

	/*
	 * If tabLayoutPolicy == SCROLL_TAB_LAYOUT, this method will paint an edge
	 * indicating the tab is cropped in the viewport display
	 */
	private void paintCroppedTabEdge(final Graphics g, final int tabPlacement, final int tabIndex, final int x, final int y) {
		g.setColor(Color.gray);
		switch (tabPlacement) {
		case LEFT:
		case RIGHT:
			int xx = x;
			while (xx <= x + rects[tabIndex].width) {
				for ( int i = 0; i < xCropLen.length; i += 2) {
					g.drawLine(xx + yCropLen[i], y - xCropLen[i], xx + yCropLen[i + 1] - 1, y - xCropLen[i + 1]);
				}
				xx += CROP_SEGMENT;
			}
			break;
		case TOP:
		case BOTTOM:
		default:
			int yy = y;
			while (yy <= y + rects[tabIndex].height) {
				for ( int i = 0; i < xCropLen.length; i += 2) {
					g.drawLine(x - xCropLen[i], yy + yCropLen[i], x - xCropLen[i + 1], yy + yCropLen[i + 1] - 1);
				}
				yy += CROP_SEGMENT;
			}
		}
	}

	/**
	 * <p>paintFocusIndicator.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param rects an array of {@link java.awt.Rectangle} objects.
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param iconRect a {@link java.awt.Rectangle} object.
	 * @param textRect a {@link java.awt.Rectangle} object.
	 * @param isSelected a boolean.
	 */
	protected void paintFocusIndicator(final Graphics g, final int tabPlacement, final Rectangle[] rects, final int tabIndex,
									   final Rectangle iconRect, final Rectangle textRect, final boolean isSelected) {
		if (tabPane.isRequestFocusEnabled() && tabPane.hasFocus() && isSelected && tabIndex >= 0
				&& textRect.width > 8) {
			g.setColor(AbstractLookAndFeel.getTheme().getFocusColor());
			BasicGraphicsUtils.drawDashedRect(g, textRect.x - 4, textRect.y + 1, textRect.width + 8, textRect.height);
		}
	}

	/**
	 * <p>paintIcon.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param icon a {@link javax.swing.Icon} object.
	 * @param iconRect a {@link java.awt.Rectangle} object.
	 * @param isSelected a boolean.
	 */
	protected void paintIcon(final Graphics g, final int tabPlacement, final int tabIndex, final Icon icon, final Rectangle iconRect,
							 final boolean isSelected) {
		if (icon != null) {
			icon.paintIcon(tabPane, g, iconRect.x, iconRect.y);
		}
	}

	/**
	 * <p>paintLeftTabBorder.</p>
	 *
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param g a {@link java.awt.Graphics} object.
	 * @param x1 a {@link java.lang.Integer} object.
	 * @param y1 a {@link java.lang.Integer} object.
	 * @param x2 a {@link java.lang.Integer} object.
	 * @param y2 a {@link java.lang.Integer} object.
	 * @param isSelected a boolean.
	 */
	protected void paintLeftTabBorder(final int tabIndex, final Graphics g, final int x1, final int y1, final int x2, final int y2, final boolean isSelected) {
		final Graphics2D g2D = (Graphics2D) g;

		final int tc = tabPane.getTabCount();
		final int currentRun = getRunForTab(tc, tabIndex);
		final int lastIndex = lastTabInRun(tc, currentRun);
		final int firstIndex = tabRuns[currentRun];

		Color loColor = getLoBorderColor(tabIndex);
		Color hiColor = getHiBorderColor(tabIndex);

		g.setColor(hiColor);
		final Composite savedComposite = g2D.getComposite();
		final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
		g2D.setComposite(alpha);
		g.drawLine(x1 + GAP + 1, y1 + 1, x2 - 1, y1 + 1);
		g.drawLine(x1 + GAP, y1 + 1, x1 + 1, y1 + GAP);
		g.drawLine(x1 + 1, y1 + GAP + 1, x1 + 1, y2 - 1);
		g2D.setComposite(savedComposite);

		g.setColor(loColor);
		g.drawLine(x1 + GAP, y1, x2 - 1, y1);
		g.drawLine(x1 + GAP, y1, x1, y1 + GAP);
		g.drawLine(x1, y1 + GAP, x1, y2);
		g.drawLine(x1 + GAP, y2, x2 - 1, y2);
		if (tabIndex == lastIndex) {
			g.drawLine(x1, y2, x1 + GAP, y2);
		}
		// paint gap
		final int gapTabIndex = getTabAtLocation(x1 + 2, y1 - 2);
		final Color gapColor = getGapColor(gapTabIndex);
		g.setColor(gapColor);
		for ( int i = 0; i < GAP; i++) {
			g.drawLine(x1, y1 + i, x1 + GAP - i - 1, y1 + i);
		}

		if (tabIndex != firstIndex || currentRun != runCount - 1) {
			loColor = getLoBorderColor(gapTabIndex);
			g.setColor(loColor);
			g.drawLine(x1, y1, x1, y1 + GAP - 1);
			if (tabIndex != firstIndex) {
				g2D.setComposite(alpha);
				hiColor = getHiBorderColor(gapTabIndex);
				g.setColor(hiColor);
				g.drawLine(x1 + 1, y1, x1 + 1, y1 + GAP - 2);
				g2D.setComposite(savedComposite);
			}
		}
	}

	/**
	 * <p>paintRightTabBorder.</p>
	 *
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param g a {@link java.awt.Graphics} object.
	 * @param x1 a {@link java.lang.Integer} object.
	 * @param y1 a {@link java.lang.Integer} object.
	 * @param x2 a {@link java.lang.Integer} object.
	 * @param y2 a {@link java.lang.Integer} object.
	 * @param isSelected a boolean.
	 */
	protected void paintRightTabBorder(final int tabIndex, final Graphics g, final int x1, final int y1, final int x2, final int y2, final boolean isSelected) {
		final Graphics2D g2D = (Graphics2D) g;

		final int tc = tabPane.getTabCount();
		final int currentRun = getRunForTab(tc, tabIndex);
		final int lastIndex = lastTabInRun(tc, currentRun);
		final int firstIndex = tabRuns[currentRun];

		Color loColor = getLoBorderColor(tabIndex);
		final Color hiColor = getHiBorderColor(tabIndex);

		final Composite savedComposite = g2D.getComposite();
		final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
		g2D.setComposite(alpha);
		g.setColor(hiColor);
		g.drawLine(x1, y1 + 1, x2 - GAP - 1, y1 + 1);
		g.drawLine(x2 - GAP, y1 + 1, x2 - 1, y1 + GAP);
		g2D.setComposite(savedComposite);

		g.setColor(loColor);
		g.drawLine(x1, y1, x2 - GAP, y1);
		g.drawLine(x2 - GAP, y1, x2, y1 + GAP);
		g.drawLine(x2, y1 + GAP, x2, y2);
		if (tabIndex == lastIndex) {
			g.drawLine(x2, y2, x1, y2);
		}

		// paint gap
		final int gapTabIndex = getTabAtLocation(x1 + 2, y1 - 2);
		final Color gapColor = getGapColor(gapTabIndex);
		g.setColor(gapColor);
		for ( int i = 0; i < GAP; i++) {
			g.drawLine(x2 - GAP + i + 1, y1 + i, x2, y1 + i);
		}

		if (tabIndex != firstIndex || currentRun != runCount - 1) {
			loColor = getLoBorderColor(gapTabIndex);
			g.setColor(loColor);
			g.drawLine(x2, y1, x2, y1 + GAP - 1);
		}
	}

	/**
	 * <p>paintRoundedBottomTabBorder.</p>
	 *
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param g a {@link java.awt.Graphics} object.
	 * @param x1 a {@link java.lang.Integer} object.
	 * @param y1 a {@link java.lang.Integer} object.
	 * @param x2 a {@link java.lang.Integer} object.
	 * @param y2 a {@link java.lang.Integer} object.
	 * @param isSelected a boolean.
	 */
	protected void paintRoundedBottomTabBorder(final int tabIndex, final Graphics g, final int x1, final int y1, final int x2, final int y2,
											   final boolean isSelected) {
		final Graphics2D g2D = (Graphics2D) g;
		final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		final Color loColor = getLoBorderColor(tabIndex);
		final int d = 2 * GAP;
		g.setColor(loColor);
		g.drawLine(x1 + GAP, y2, x2 - GAP, y2);
		g.drawArc(x1, y2 - d, d, d, 180, 90);
		g.drawArc(x2 - d, y2 - d, d, d, -90, 90);
		g.drawLine(x1, y1, x1, y2 - GAP - 1);
		g.drawLine(x2, y1, x2, y2 - GAP - 1);

		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
	}

	/**
	 * <p>paintRoundedTopTabBorder.</p>
	 *
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param g a {@link java.awt.Graphics} object.
	 * @param x1 a {@link java.lang.Integer} object.
	 * @param y1 a {@link java.lang.Integer} object.
	 * @param x2 a {@link java.lang.Integer} object.
	 * @param y2 a {@link java.lang.Integer} object.
	 * @param isSelected a boolean.
	 */
	protected void paintRoundedTopTabBorder(final int tabIndex, final Graphics g, final int x1, final int y1, final int x2, final int y2,
											final boolean isSelected) {
		final Graphics2D g2D = (Graphics2D) g;
		final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		final Color borderColor = getLoBorderColor(tabIndex);
		g.setColor(borderColor);
		final int d = 2 * GAP;
		if (isSelected) {
			g.drawLine(x1 + GAP, y1, x2 - GAP, y1);
			g.drawArc(x1, y1, d, d, 90, 90);
			g.drawArc(x2 - d, y1, d, d, 0, 90);
			g.drawLine(x1, y1 + GAP + 1, x1, y2);
			g.drawLine(x2, y1 + GAP + 1, x2, y2);
		} else {
			g.drawLine(x1 + GAP, y1, x2 - GAP, y1);
			g.drawArc(x1, y1, d, d, 90, 90);
			g.drawArc(x2 - d, y1, d, d, 0, 90);
			g.drawLine(x1, y1 + GAP + 1, x1, y2 - 1);
			g.drawLine(x2, y1 + GAP + 1, x2, y2 - 1);
		}
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
	}

	/**
	 * <p>paintScrollContentBorder.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param selectedIndex a {@link java.lang.Integer} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param w a {@link java.lang.Integer} object.
	 * @param h a {@link java.lang.Integer} object.
	 */
	public void paintScrollContentBorder(final Graphics g, final int tabPlacement, final int selectedIndex, final int x, final int y, final int w,
                                            final int h) {
		Insets bi = new Insets(0, 0, 0, 0);
		if (tabPane.getBorder() != null) {
			bi = tabPane.getBorder().getBorderInsets(tabPane);
		}
		switch (tabPane.getTabPlacement()) {
		case TOP:
			paintContentBorder(g, tabPane.getTabPlacement(), tabPane.getSelectedIndex(), x, y - bi.top, w, h);
			break;
		case BOTTOM:
			paintContentBorder(g, tabPane.getTabPlacement(), tabPane.getSelectedIndex(), x, y + bi.bottom, w, h);
			break;
		case LEFT:
			paintContentBorder(g, tabPane.getTabPlacement(), tabPane.getSelectedIndex(), x - bi.left, y, w, h);
			break;
		case RIGHT:
			paintContentBorder(g, tabPane.getTabPlacement(), tabPane.getSelectedIndex(), x + bi.right, y, w, h);
			break;
		default:
			break;
		}
	}

	/**
	 * <p>paintTab.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param rects an array of {@link java.awt.Rectangle} objects.
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param iconRect a {@link java.awt.Rectangle} object.
	 * @param textRect a {@link java.awt.Rectangle} object.
	 */
	protected void paintTab(final Graphics g, final int tabPlacement, final Rectangle[] rects, final int tabIndex, final Rectangle iconRect,
							final Rectangle textRect) {
		final Rectangle tabRect = rects[tabIndex];
		final int selectedIndex = tabPane.getSelectedIndex();
		final boolean isSelected = selectedIndex == tabIndex;
		Graphics2D g2D = null;
		Polygon cropShape = null;
		Shape savedClip = null;
		int cropx = 0;
		int cropy = 0;

		if (scrollableTabLayoutEnabled()) {
			if (g instanceof Graphics2D) {
				g2D = (Graphics2D) g;

				// Render visual for cropped tab edge...
				final Rectangle viewRect = tabScroller.viewport.getViewRect();
				final int cropline;
				switch (tabPlacement) {
				case LEFT:
				case RIGHT:
					cropline = viewRect.y + viewRect.height;
					if (tabRect.y < cropline && tabRect.y + tabRect.height > cropline) {
						cropShape = createCroppedTabClip(tabPlacement, tabRect, cropline);
						cropx = tabRect.x;
						cropy = cropline - 1;
					}
					break;
				case TOP:
				case BOTTOM:
				default:
					cropline = viewRect.x + viewRect.width;
					if (tabRect.x < cropline && tabRect.x + tabRect.width > cropline) {
						cropShape = createCroppedTabClip(tabPlacement, tabRect, cropline);
						cropx = cropline - 1;
						cropy = tabRect.y;
					}
				}
				if (cropShape != null) {
					savedClip = g2D.getClip();
					g2D.clip(cropShape);
				}
			}
		}

		paintTabBackground(g, tabPlacement, tabIndex, tabRect.x, tabRect.y, tabRect.width, tabRect.height, isSelected);
		paintTabBorder(g, tabPlacement, tabIndex, tabRect.x, tabRect.y, tabRect.width, tabRect.height, isSelected);

		try {
			final boolean doPaintContent = getTabComponentAt(tabIndex) == null;
			if (doPaintContent) {
				final String title = tabPane.getTitleAt(tabIndex);
				final Font font = getTabFont(isSelected);
				final FontMetrics fm = JTattooUtilities.getFontMetrics(tabPane, g, font);
				final Icon icon = getIconForTab(tabIndex);

				layoutLabel(tabPlacement, fm, tabIndex, title, icon, tabRect, iconRect, textRect, isSelected);
				paintText(g, tabPlacement, font, fm, tabIndex, title, textRect, isSelected);
				paintIcon(g, tabPlacement, tabIndex, icon, iconRect, isSelected);
			}
			paintFocusIndicator(g, tabPlacement, rects, tabIndex, iconRect, textRect, isSelected);
		} catch (final Exception e) {
			log.info(e.getMessage());
		}

		if (cropShape != null) {
			paintCroppedTabEdge(g, tabPlacement, tabIndex, cropx, cropy);
			if (g2D != null && savedClip != null) {
				g2D.setClip(savedClip);
			}
		}
	}

	/**
	 * Paints the tabs in the tab area. Invoked by paint(). The graphics parameter
	 * must be a valid Graphics object. Tab placement may be either:
	 * JTabbedPane.TOP, JTabbedPane.BOTTOM,
	 * JTabbedPane.LEFT, or JTabbedPane.RIGHT. The
	 * selected index must be a valid tabbed pane tab index (0 to tab count - 1,
	 * inclusive) or -1 if no tab is currently selected. The handling of invalid
	 * parameters is unspecified.
	 *
	 * @param g             the graphics object to use for rendering
	 * @param tabPlacement  the placement for the tabs within the JTabbedPane
	 * @param selectedIndex the tab index of the selected component
	 * @since 1.4
	 */
	public void paintTabArea(final Graphics g, final int tabPlacement, final int selectedIndex) {
		final int tc = tabPane.getTabCount();
		final Rectangle iconRect = new Rectangle();
        final Rectangle textRect = new Rectangle();
        final Shape savedClip = g.getClip();
		final Rectangle clipRect = g.getClipBounds();
		// Dirty trick to fix clipping problem
		if (scrollableTabLayoutEnabled() && tabScroller.scrollBackwardButton.isVisible()) {
			if (tabPlacement == TOP || tabPlacement == BOTTOM) {
				g.setClip(clipRect.x, clipRect.y, clipRect.width + 1, clipRect.height);
			} else {
				g.setClip(clipRect.x, clipRect.y, clipRect.width, clipRect.height + 1);
			}
		}
		// Paint tabRuns of tabs from back to front
		for ( int i = runCount - 1; i >= 0; i--) {
			final int start = tabRuns[i];
			final int next = tabRuns[i == runCount - 1 ? 0 : i + 1];
			final int end = next != 0 ? next - 1 : tc - 1;
			for ( int j = start; j <= end; j++) {
				if (rects[j].intersects(clipRect)) {
					paintTab(g, tabPlacement, rects, j, iconRect, textRect);
				}
			}
		}

		// Paint selected tab if its in the front run
		// since it may overlap other tabs
		if (selectedIndex >= 0 && selectedIndex < rects.length && getRunForTab(tc, selectedIndex) == 0) {
			if (rects[selectedIndex].intersects(clipRect)) {
				paintTab(g, tabPlacement, rects, selectedIndex, iconRect, textRect);
			}
		}
		g.setClip(savedClip);
	}

	/**
	 * <p>paintTabBackground.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param w a {@link java.lang.Integer} object.
	 * @param h a {@link java.lang.Integer} object.
	 * @param isSelected a boolean.
	 */
	protected void paintTabBackground(final Graphics g, final int tabPlacement, final int tabIndex, final int x, final int y, final int w, final int h,
			final boolean isSelected) {
		if (isTabOpaque() || isSelected) {
			final Graphics2D g2D = (Graphics2D) g;
			final Shape savedClip = g.getClip();
			Area orgClipArea = new Area(new Rectangle2D.Double(x, y, w, h));
			if (savedClip != null) {
				orgClipArea = new Area(savedClip);
			}
			final Color[] colorArr = getTabColors(tabIndex, isSelected, tabIndex == rolloverIndex);
			final int d = 2 * GAP;
			switch (tabPlacement) {
			case TOP:
			default:
                final Area clipArea = new Area(new RoundRectangle2D.Double(x, y, w, h + 4, d, d));
                final Area rectArea;
                if (isSelected) {
                    rectArea = new Area(new Rectangle2D.Double(x, y, w, h + 2));
                } else {
                    rectArea = new Area(new Rectangle2D.Double(x, y, w, h));
                }
                clipArea.intersect(rectArea);
                clipArea.intersect(orgClipArea);
                g2D.setClip(clipArea);
                JTattooUtilities.fillHorGradient(g, colorArr, x, y, w, h + 4);
                g2D.setClip(savedClip);
                break;
			case LEFT:
				if (isSelected) {
					JTattooUtilities.fillHorGradient(g, colorArr, x + 1, y + 1, w + 1, h - 1);
				} else {
					JTattooUtilities.fillHorGradient(g, colorArr, x + 1, y + 1, w - 1, h - 1);
				}
				break;
			case BOTTOM:
                final Area clipA = new Area(new RoundRectangle2D.Double(x, y - 4, w, h + 4, d, d));
                final Area rectA;
                if (isSelected) {
					rectA = new Area(new Rectangle2D.Double(x, y - 2, w, h + 1));
                } else {
					rectA = new Area(new Rectangle2D.Double(x, y, w, h));
                }
				clipA.intersect(rectA);
				clipA.intersect(orgClipArea);
                g2D.setClip(clipA);
                JTattooUtilities.fillHorGradient(g, colorArr, x, y - 4, w, h + 4);
                g2D.setClip(savedClip);
                break;
			case RIGHT:
				if (isSelected) {
					JTattooUtilities.fillHorGradient(g, colorArr, x - 2, y + 1, w + 2, h - 1);
				} else {
					JTattooUtilities.fillHorGradient(g, colorArr, x, y + 1, w + 1, h - 1);
				}
				break;
			}
		}
	}

    /*
	 * this function draws the border around each tab note that this function does
	 * now draw the background of the tab. that is done elsewhere
	 */
	/**
	 * <p>paintTabBorder.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param w a {@link java.lang.Integer} object.
	 * @param h a {@link java.lang.Integer} object.
	 * @param isSelected a boolean.
	 */
	protected void paintTabBorder(final Graphics g, final int tabPlacement, final int tabIndex, final int x, final int y, final int w, final int h,
								  final boolean isSelected) {
		final int x2 = x + w;
		final int y2 = y + h;
		switch (tabPlacement) {
		case LEFT:
			paintLeftTabBorder(tabIndex, g, x, y, x2, y2, isSelected);
			break;
		case RIGHT:
			paintRightTabBorder(tabIndex, g, x, y, x2, y2, isSelected);
			break;
		case BOTTOM:
			if (roundedTabs) {
				paintRoundedBottomTabBorder(tabIndex, g, x, y, x2, y2 - 1, isSelected);
			} else {
				paintBottomTabBorder(tabIndex, g, x, y, x2, y2 - 1, isSelected);
			}
			break;
		case TOP:
		default:
			if (roundedTabs) {
				paintRoundedTopTabBorder(tabIndex, g, x, y, x2, y2, isSelected);
			} else {
				paintTopTabBorder(tabIndex, g, x, y, x2, y2, isSelected);
			}
		}
	}

	/**
	 * <p>paintText.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param font a {@link java.awt.Font} object.
	 * @param metrics a {@link java.awt.FontMetrics} object.
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param title a {@link java.lang.String} object.
	 * @param textRect a {@link java.awt.Rectangle} object.
	 * @param isSelected a boolean.
	 */
	protected void paintText(final Graphics g, final int tabPlacement, final Font font, final FontMetrics metrics, final int tabIndex, final String title,
			final Rectangle textRect, final boolean isSelected) {
		g.setFont(font);
		final View v = getTextViewForTab(tabIndex);
		if (v != null) {
			// html
			final Graphics2D g2D = (Graphics2D) g;
			Object savedRenderingHint = null;
			if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
				savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
				g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						AbstractLookAndFeel.getTheme().getTextAntiAliasingHint());
			}
			v.paint(g, textRect);
			if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
				g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, savedRenderingHint);
			}
		} else {
			// plain text
			final int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);

			if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) {
				if (isSelected) {
					final Color backColor = tabPane.getBackgroundAt(tabIndex);
					if (backColor instanceof UIResource) {
						g.setColor(AbstractLookAndFeel.getTabSelectionForegroundColor());
					} else {
						g.setColor(tabPane.getForegroundAt(tabIndex));
					}
				} else {
					if (tabIndex == rolloverIndex) {
						g.setColor(AbstractLookAndFeel.getTheme().getRolloverForegroundColor());
					} else {
						g.setColor(tabPane.getForegroundAt(tabIndex));
					}
				}
				JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,
						textRect.y + metrics.getAscent());

			} else { // tab disabled
				g.setColor(tabPane.getBackgroundAt(tabIndex).brighter());
				JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,
						textRect.y + metrics.getAscent());
				g.setColor(tabPane.getBackgroundAt(tabIndex).darker());
				JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x - 1,
						textRect.y + metrics.getAscent() - 1);
			}
		}
	}

	/**
	 * <p>paintTopTabBorder.</p>
	 *
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param g a {@link java.awt.Graphics} object.
	 * @param x1 a {@link java.lang.Integer} object.
	 * @param y1 a {@link java.lang.Integer} object.
	 * @param x2 a {@link java.lang.Integer} object.
	 * @param y2 a {@link java.lang.Integer} object.
	 * @param isSelected a boolean.
	 */
	protected void paintTopTabBorder(final int tabIndex, final Graphics g, final int x1, final int y1, final int x2, final int y2, final boolean isSelected) {
		final int tc = tabPane.getTabCount();
		final int currentRun = getRunForTab(tc, tabIndex);
		final int lastIndex = lastTabInRun(tc, currentRun);
		final int firstIndex = tabRuns[currentRun];
		final boolean leftToRight = JTattooUtilities.isLeftToRight(tabPane);

		final Color loColor = getLoBorderColor(tabIndex);
		final Color hiColor = getHiBorderColor(tabIndex);

		g.setColor(loColor);
		g.drawLine(x1 + GAP, y1, x2, y1);
		g.drawLine(x1 + GAP, y1, x1, y1 + GAP);
		g.drawLine(x1, y1 + GAP + 1, x1, y2);
		g.drawLine(x2, y1, x2, y2);
		g.setColor(hiColor);
		g.drawLine(x1 + GAP + 1, y1 + 1, x2 - 1, y1 + 1);
		g.drawLine(x1 + GAP + 1, y1 + 1, x1 + 1, y1 + GAP + 1);
		g.drawLine(x1 + 1, y1 + GAP + 1, x1 + 1, y2 - 1);

		// paint gap
		final int gapTabIndex = getTabAtLocation(x1 + 2, y1 - 2);
		final Color gapColor = getGapColor(gapTabIndex);
		g.setColor(gapColor);
		for ( int i = 0; i < GAP; i++) {
			g.drawLine(x1, y1 + i, x1 + GAP - i - 1, y1 + i);
		}

		if (leftToRight) {
			if (tabIndex != firstIndex || currentRun != runCount - 1) {
				g.setColor(loColor);
				g.drawLine(x1, y1, x1, y1 + GAP);
			}
			if (!isSelected && tabIndex == firstIndex && currentRun != runCount - 1) {
				g.setColor(hiColor);
				g.drawLine(x1 + 1, y1, x1 + 1, y1 + GAP - 2);
			}
		} else {
			if (tabIndex != lastIndex || currentRun != runCount - 1) {
				g.setColor(loColor);
				g.drawLine(x1, y1, x1, y1 + GAP);
			}
		}
	}

	private void removeMyPropertyChangeListeners(final Component component) {
		final PropertyChangeListener[] listeners = component.getPropertyChangeListeners();
		for (final PropertyChangeListener listener : listeners) {
			if (listener instanceof MyTabComponentListener) {
				component.removePropertyChangeListener(listener);
			}
		}
		if (component instanceof Container) {
			final Container container = (Container) component;
			for ( int i = 0; i < container.getComponentCount(); i++) {
				final Component c = container.getComponent(i);
				removeMyPropertyChangeListeners(c);
			}
		}
	}

	/**
	 * <p>requestFocusForVisibleComponent.</p>
	 *
	 * @return a boolean.
	 */
	public boolean requestFocusForVisibleComponent() {
		final Component vc = getVisibleComponent();
		if (vc.isFocusable()) {
			vc.requestFocus();
			return true;
		} else if (vc instanceof JComponent) {
			return vc.requestFocusInWindow();
		}
		return false;
	}

	/**
	 * Resets the mnemonics bindings to an empty state.
	 */
	private void resetMnemonics() {
		if (mnemonicToIndexMap != null) {
			mnemonicToIndexMap.clear();
			mnemonicInputMap.clear();
		}
	}

	/*
	 * In an attempt to preserve backward compatibility for programs which have
	 * extended BaseTabbedPaneUI to do their own layout, the UI uses the installed
	 * layoutManager (and not tabLayoutPolicy) to determine if scrollTabLayout is
	 * enabled.
	 */
	/**
	 * <p>scrollableTabLayoutEnabled.</p>
	 *
	 * @return a boolean.
	 */
	public boolean scrollableTabLayoutEnabled() {
		return tabPane.getLayout() instanceof TabbedPaneScrollLayout;
	}

	/**
	 * <p>selectAdjacentRunTab.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param tabIndex a {@link java.lang.Integer} object.
	 * @param offset a {@link java.lang.Integer} object.
	 */
	protected void selectAdjacentRunTab(final int tabPlacement, final int tabIndex, final int offset) {
		if (runCount < 2) {
			return;
		}
		int newIndex;
		final Rectangle r = rects[tabIndex];
		switch (tabPlacement) {
		case LEFT:
		case RIGHT:
			newIndex = getTabAtLocation(r.x + r.width / 2 + offset, r.y + r.height / 2);
			break;
		case BOTTOM:
		case TOP:
		default:
			newIndex = getTabAtLocation(r.x + r.width / 2, r.y + r.height / 2 + offset);
		}
		if (newIndex != -1) {
			while (!tabPane.isEnabledAt(newIndex) && newIndex != tabIndex) {
				newIndex = getNextTabIndex(newIndex);
			}
			tabPane.setSelectedIndex(newIndex);
		}
	}

	/**
	 * <p>selectNextTab.</p>
	 *
	 * @param current a {@link java.lang.Integer} object.
	 */
	protected void selectNextTab(final int current) {
		int tabIndex = getNextTabIndex(current);
		while (tabIndex != current && !tabPane.isEnabledAt(tabIndex)) {
			tabIndex = getNextTabIndex(tabIndex);
		}
		tabPane.setSelectedIndex(tabIndex);
	}

	/**
	 * <p>selectNextTabInRun.</p>
	 *
	 * @param current a {@link java.lang.Integer} object.
	 */
	protected void selectNextTabInRun(final int current) {
		final int tc = tabPane.getTabCount();
		int tabIndex = getNextTabIndexInRun(tc, current);
		while (tabIndex != current && !tabPane.isEnabledAt(tabIndex)) {
			tabIndex = getNextTabIndexInRun(tc, tabIndex);
		}
		tabPane.setSelectedIndex(tabIndex);
	}

	/**
	 * <p>selectPreviousTab.</p>
	 *
	 * @param current a {@link java.lang.Integer} object.
	 */
	protected void selectPreviousTab(final int current) {
		int tabIndex = getPreviousTabIndex(current);
		while (tabIndex != current && !tabPane.isEnabledAt(tabIndex)) {
			tabIndex = getPreviousTabIndex(tabIndex);
		}
		tabPane.setSelectedIndex(tabIndex);
	}

	/**
	 * <p>selectPreviousTabInRun.</p>
	 *
	 * @param current a {@link java.lang.Integer} object.
	 */
	protected void selectPreviousTabInRun(final int current) {
		final int tc = tabPane.getTabCount();
		int tabIndex = getPreviousTabIndexInRun(tc, current);
		while (tabIndex != current && !tabPane.isEnabledAt(tabIndex)) {
			tabIndex = getPreviousTabIndexInRun(tc, tabIndex);
		}
		tabPane.setSelectedIndex(tabIndex);
	}

	/**
	 * <p>Setter for the field visibleComponent.</p>
	 *
	 * @param component a {@link java.awt.Component} object.
	 */
	protected void setVisibleComponent(final Component component) {
		if (visibleComponent != null && visibleComponent != component && visibleComponent.getParent() == tabPane) {
			visibleComponent.setVisible(false);
		}
		if (component != null && !component.isVisible()) {
			component.setVisible(true);
		}
		visibleComponent = component;
	}

	/**
	 * <p>shouldPadTabRun.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @param run a {@link java.lang.Integer} object.
	 * @return a boolean.
	 */
	protected boolean shouldPadTabRun(final int tabPlacement, final int run) {
		return runCount > 1;
	}

	/**
	 * <p>shouldRotateTabRuns.</p>
	 *
	 * @param tabPlacement a {@link java.lang.Integer} object.
	 * @return a boolean.
	 */
	protected boolean shouldRotateTabRuns(final int tabPlacement) {
		return true;
	}

	/*
	 * Returns the tab index which intersects the specified point in the
	 * JTabbedPane's coordinate space.
	 */
	/** {@inheritDoc} */
	@Override
	public int tabForCoordinate(final JTabbedPane pane, final int x, final int y) {
		ensureCurrentLayout();
		final Point p = new Point(x, y);

		if (scrollableTabLayoutEnabled()) {
			translatePointToTabPanel(x, y, p);
		}
		final int tc = tabPane.getTabCount();
		for ( int i = 0; i < tc; i++) {
			if (rects[i].contains(p.x, p.y)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns a point which is translated from the specified point in the
	 * JTabbedPane's coordinate space to the coordinate space of the
	 * ScrollableTabPanel. This is used for SCROLL_TAB_LAYOUT ONLY.
	 */
	private Point translatePointToTabPanel(final int srcx, final int srcy, final Point dest) {
		final Point vpp = tabScroller.viewport.getLocation();
		final Point viewp = tabScroller.viewport.getViewPosition();
		dest.x = srcx - vpp.x + viewp.x;
		dest.y = srcy - vpp.y + viewp.y;
		return dest;
	}

	/**
	 * Removes any installed subcomponents from the JTabbedPane. Invoked by
	 * uninstallUI.
	 *
	 * @since 1.4
	 */
	protected void uninstallComponents() {
		uninstallTabContainer();
		if (scrollableTabLayoutEnabled()) {
			tabPane.remove(tabScroller.viewport);
			tabPane.remove(tabScroller.scrollForwardButton);
			tabPane.remove(tabScroller.scrollBackwardButton);
			tabPane.remove(tabScroller.popupMenuButton);
			tabScroller = null;
		}
	}

	/**
	 * <p>uninstallDefaults.</p>
	 */
	protected void uninstallDefaults() {
		tabInsets = null;
		selectedTabPadInsets = null;
		tabAreaInsets = null;
		contentBorderInsets = null;
	}

	/**
	 * <p>uninstallKeyboardActions.</p>
	 */
	protected void uninstallKeyboardActions() {
		SwingUtilities.replaceUIActionMap(tabPane, null);
		SwingUtilities.replaceUIInputMap(tabPane, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, null);
		SwingUtilities.replaceUIInputMap(tabPane, JComponent.WHEN_FOCUSED, null);
		SwingUtilities.replaceUIInputMap(tabPane, JComponent.WHEN_IN_FOCUSED_WINDOW, null);
	}

	/**
	 * <p>uninstallListeners.</p>
	 */
	protected void uninstallListeners() {
		if (mouseListener != null) {
			if (scrollableTabLayoutEnabled()) {
				// SCROLL_TAB_LAYOUT
				tabScroller.tabPanel.removeMouseListener(mouseListener);
			} else {
				// WRAP_TAB_LAYOUT
				tabPane.removeMouseListener(mouseListener);
			}
			mouseListener = null;
		}
		if (mouseMotionListener != null) {
			if (scrollableTabLayoutEnabled()) {
				// SCROLL_TAB_LAYOUT
				tabScroller.tabPanel.removeMouseMotionListener(mouseMotionListener);
			} else {
				// WRAP_TAB_LAYOUT
				tabPane.removeMouseMotionListener(mouseMotionListener);
			}
			mouseMotionListener = null;
		}
		if (focusListener != null) {
			tabPane.removeFocusListener(focusListener);
			focusListener = null;
		}

		// PENDING(api): See comment for ContainerHandler
		if (containerListener != null) {
			tabPane.removeContainerListener(containerListener);
			containerListener = null;
			if (htmlViews != null) {
				htmlViews.clear();
				htmlViews = null;
			}
		}
		if (tabChangeListener != null) {
			tabPane.removeChangeListener(tabChangeListener);
			tabChangeListener = null;
		}
		if (tabComponentListener != null) {
			tabPane.removeComponentListener(tabComponentListener);
			tabChangeListener = null;
		}
		if (propertyChangeListener != null) {
			tabPane.removePropertyChangeListener(propertyChangeListener);
			propertyChangeListener = null;
		}
	}

	private void uninstallTabContainer() {
		if (tabContainer == null) {
			return;
		}
		// Remove all the tabComponents, making sure not to notify the tabbedpane.
		tabContainer.notifyTabbedPane = false;
		for ( int i = 0; i < tabContainer.getComponentCount(); i++) {
			final Component c = tabContainer.getComponent(i);
			removeMyPropertyChangeListeners(c);
		}
		tabContainer.removeAll();
		if (scrollableTabLayoutEnabled()) {
			tabScroller.tabPanel.remove(tabContainer);
		} else {
			tabPane.remove(tabContainer);
		}
		tabContainer = null;
	}

	/** {@inheritDoc} */
	@Override
	public void uninstallUI(final JComponent c) {
		uninstallKeyboardActions();
		uninstallListeners();
		uninstallDefaults();
		uninstallComponents();
		c.setLayout(null);

		this.tabPane = null;
	}

	/**
	 * Reloads the mnemonics. This should be invoked when a memonic changes, when
	 * the title of a mnemonic changes, or when tabs are added/removed.
	 */
	private void updateMnemonics() {
		resetMnemonics();
		for ( int counter = tabPane.getTabCount() - 1; counter >= 0; counter--) {
			final int mnemonic = tabPane.getMnemonicAt(counter);
			if (mnemonic > 0) {
				addMnemonic(counter, mnemonic);
			}
		}
	}

} // end of class BaseTabbedPaneUI
