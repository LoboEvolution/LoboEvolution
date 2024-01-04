/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

package org.sexydock.tabs.jhrome;

import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.BooleanHolder;
import org.sexydock.InternalTransferableStore;
import org.sexydock.SwingUtils;
import org.sexydock.tabs.*;
import org.sexydock.tabs.event.*;

import javax.swing.Timer;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;
import java.util.*;

/**
 * The Class JhromeTabbedPaneUI.
 */
@Slf4j
public class JhromeTabbedPaneUI extends TabbedPaneUI {
    public static final String TAB_CLOSE_BUTTONS_VISIBLE = "sexydock.tabbedPane.tabCloseButtonsVisible";

    public static final String CONTENT_PANEL_BORDER = "sexydock.tabbedPane.contentPanelBorder";

    public static final String NEW_TAB_BUTTON_VISIBLE = "sexydock.tabbedPane.newTabButtonVisible";

    public static final String TAB_CLOSE_BUTTON_LISTENER = "sexydock.tabbedPane.tabCloseButtonListener";

    public static final String FLOATING_TAB_HANDLER = "sexydock.tabbedPane.floatingTabHandler";

    public static final String TAB_DROP_FAILURE_HANDLER = "sexydock.tabbedPane.tabDropFailureHandler";

    public static final String TAB_FACTORY = "sexydock.tabbedPane.tabFactory";

    public static final String DND_POLICY = "sexydock.tabbedPane.dndPolicy";

    public static final String NEW_TAB_BUTTON_UI = "sexydock.tabbedPane.newTabButtonUI";

    public static final String USE_UNIFORM_WIDTH = "sexydock.tabbedPane.useUniformWidth";

    public static final String ANIMATION_FACTOR = "sexydock.tabbedPane.animationFactor";

    public static final boolean DEFAULT_USE_UNIFORM_WIDTH = true;

    public static final double DEFAULT_ANIMATION_FACTOR = 0.7;
    private static final int MINIMUM = 0;
    private static final int PREFERRED = 1;
    private static final int MAXIMUM = 2;
    private static final boolean LOG_EXCEPTIONS = true;
    private final Object animLock = new Object();
    private final Object updateLock = new Object();
    private final Map<Component, TabInfo> contentMap = new HashMap<>();
    private final List<TabInfo> tabs = new ArrayList<>();
    private JTabbedPane tabbedPane;
    private JLayeredPane tabLayeredPane;
    private final int overlap = 13;
    private double animFactor = DEFAULT_ANIMATION_FACTOR;
    private javax.swing.Timer animTimer;
    private TabLayoutManager layout;
    private boolean useUniformWidth = true;
    private final int maxUniformWidth = 300;
    private boolean mouseOverTopZone = true;
    private MouseManager mouseOverManager;
    private TabInfo selectedTab = null;
    private Component currentContent = null;
    private Border contentPanelBorder = null;
    private final Rectangle contentPanelBounds = new Rectangle();
    private JPanel rightButtonsPanel;
    private JButton newTabButton;
    private DragHandler dragHandler;
    private ITabDropFailureHandler tabDropFailureHandler = null;
    private IFloatingTabHandler floatingTabHandler = null;
    private final InternalTransferableStore transferableStore = InternalTransferableStore.getDefaultInstance();
    private ITabFactory tabFactory = null;
    private final Rectangle topZone = new Rectangle();
    private final Rectangle tabZone = new Rectangle();
    private final Rectangle dropZone = new Rectangle();
    private ITabbedPaneDndPolicy dndPolicy = null;
    private final List<ITabbedPaneListener> tabListeners = new ArrayList<>();
    private Handler handler = new Handler();
    private HashMap<Integer, Integer> mnemonicToIndexMap;
    /**
     * InputMap used for mnemonics. Only non-null if the JTabbedPane has mnemonics associated with it. Lazily created in initMnemonics.
     */
    private InputMap mnemonicInputMap;

    public JhromeTabbedPaneUI() {
    }

    public static JhromeTabbedPaneUI createUI(final JComponent c) {
        return new JhromeTabbedPaneUI();
    }

    public static void insertTab(final JTabbedPane tabbedPane, final int index, final Tab tab) {
        tabbedPane.insertTab(tab.getTitle(), tab.getIcon(), tab.getContent(), tab.getToolTipText(), index);
        tabbedPane.setTabComponentAt(index, tab.getTabComponent());
        tabbedPane.setEnabledAt(index, tab.isEnabled());
        tabbedPane.setMnemonicAt(index, tab.getMnemonic());
        tabbedPane.setDisplayedMnemonicIndexAt(index, tab.getDisplayedMnemonicIndex());
        tabbedPane.setBackgroundAt(index, tab.getBackground());
        tabbedPane.setForegroundAt(index, tab.getForeground());
    }

    private static void checkEDT() {
        if (!SwingUtilities.isEventDispatchThread()) {
            throw new IllegalArgumentException("Must be called on the AWT Event Dispatch Thread!");
        }
    }

    private static void checkNotEDT() {
        if (SwingUtilities.isEventDispatchThread()) {
            throw new IllegalArgumentException("Must not be called on the AWT Event Dispatch Thread!");
        }
    }

    private static void put(final ActionMap map, final Action action) {
        map.put(action.getValue(Action.NAME), action);
    }

    private static void log(final Exception e) {
        if (LOG_EXCEPTIONS) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void installUI(final JComponent c) {
        checkEDT();
        tabbedPane = (JTabbedPane) c;
        init();
    }

    @Override
    public void uninstallUI(final JComponent c) {
        if (c != tabbedPane) {
            throw new IllegalArgumentException("Cannot uninstall from a different component than this was installed on");
        }
        checkEDT();
        dispose();
    }

    private void init() {
        synchronized (animLock) {
            animTimer = new Timer(15, e -> {
                if (tabbedPane != null) {
                    tabbedPane.invalidate();
                    tabbedPane.validate();
                }
            });
        }
        layout = new TabLayoutManager();
        tabbedPane.setLayout(layout);
        tabLayeredPane = new TabLayeredPane();
        tabLayeredPane.setName("Tab Layered Pane");
        tabLayeredPane.setLayout(null);
        tabbedPane.add(tabLayeredPane);

        tabbedPane.invalidate();
        tabbedPane.validate();

        mouseOverManager = new MouseManager();
        mouseOverManager.addExcludedComponent(tabLayeredPane);
        mouseOverManager.install(tabbedPane);

        newTabButton = new JButton();

        final ActionListener newTabButtonListener = e -> {
            if (tabFactory == null) {
                return;
            }
            final Tab newTab = tabFactory.createTabWithContent();
            if (newTab != null) {
                tabbedPane.addTab(newTab.getTitle(), newTab.getContent());
                tabbedPane.setSelectedComponent(newTab.getContent());
            }
        };

        newTabButton.addActionListener(newTabButtonListener);
        newTabButton.setUI(PropertyGetter.get(ButtonUI.class, tabbedPane, NEW_TAB_BUTTON_UI, null, new JhromeNewTabButtonUI()));
        newTabButton.setVisible(PropertyGetter.get(Boolean.class, tabbedPane, NEW_TAB_BUTTON_VISIBLE, false));
        newTabButton.setEnabled(tabbedPane.isEnabled());
        contentPanelBorder = PropertyGetter.get(Border.class, tabbedPane, CONTENT_PANEL_BORDER, null, new JhromeContentPanelBorder());

        rightButtonsPanel = new JPanel();
        rightButtonsPanel.setLayout(new GridBagLayout());
        rightButtonsPanel.setOpaque(false);

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(1, 2, 0, 0);
        rightButtonsPanel.add(newTabButton, gbc);
        tabLayeredPane.add(rightButtonsPanel);

        dragHandler = new DragHandler(tabbedPane, DnDConstants.ACTION_MOVE);
        new DropHandler(tabbedPane);

        handler = new Handler();
        tabbedPane.addContainerListener(handler);
        tabbedPane.addChangeListener(handler);
        tabbedPane.addPropertyChangeListener(handler);
        tabbedPane.addFocusListener(handler);

        tabFactory = PropertyGetter.get(ITabFactory.class, tabbedPane, TAB_FACTORY, new DefaultTabFactory());
        dndPolicy = PropertyGetter.get(ITabbedPaneDndPolicy.class, tabbedPane, DND_POLICY);
        tabDropFailureHandler = PropertyGetter.get(ITabDropFailureHandler.class, tabbedPane, TAB_DROP_FAILURE_HANDLER);
        floatingTabHandler = PropertyGetter.get(IFloatingTabHandler.class, tabbedPane, FLOATING_TAB_HANDLER, new DefaultFloatingTabHandler());
        useUniformWidth = PropertyGetter.get(Boolean.class, tabbedPane, USE_UNIFORM_WIDTH, DEFAULT_USE_UNIFORM_WIDTH);
        animFactor = PropertyGetter.get(Double.class, tabbedPane, ANIMATION_FACTOR, DEFAULT_ANIMATION_FACTOR);
        if (tabbedPane.getClientProperty(TAB_CLOSE_BUTTON_LISTENER) == null) {
            tabbedPane.putClientProperty(TAB_CLOSE_BUTTON_LISTENER, PropertyGetter.get(ITabCloseButtonListener.class, TAB_CLOSE_BUTTON_LISTENER, new DefaultTabCloseButtonListener()));
        }
        if (tabbedPane.getClientProperty(TAB_CLOSE_BUTTONS_VISIBLE) == null) {
            tabbedPane.putClientProperty(TAB_CLOSE_BUTTONS_VISIBLE, PropertyGetter.get(Boolean.class, TAB_CLOSE_BUTTONS_VISIBLE, false));
        }

        installKeyboardActions();
    }

    /**
     * @param p a {@link Point} in this tabbed pane's coordinate system.
     * @return the hoverable tab under {@code p}, or {@code null} if no such tab exists.
     * @see Tab#isHoverableAt(Point)
     */
    public Tab getHoverableTabAt(final Point p) {
        checkEDT();

        for (int i = -1; i < tabs.size(); i++) {
            final TabInfo info = i < 0 ? selectedTab : tabs.get(i);
            if (info == null || info.isBeingRemoved) {
                continue;
            }
            if (info == selectedTab && i >= 0) {
                continue;
            }
            final Tab tab = info.tab;
            final Point converted = SwingUtilities.convertPoint(tabbedPane, p, tab);
            if (tab.contains(converted)) {
                if (tab.isHoverableAt(converted)) {
                    return tab;
                }
            }
        }
        return null;
    }

    /**
     * @param p a {@link Point} in this tabbed pane's coordinate system.
     * @return the draggable tab under {@code p}, or {@code null} if no such tab exists.
     * @see Tab#isDraggableAt(Point)
     */
    public Tab getDraggableTabAt(final Point p) {
        checkEDT();

        for (int i = -1; i < tabs.size(); i++) {
            final TabInfo info = i < 0 ? selectedTab : tabs.get(i);
            if (info == null || info.isBeingRemoved) {
                continue;
            }
            if (info == selectedTab && i >= 0) {
                continue;
            }
            final Tab tab = info.tab;
            final Point converted = SwingUtilities.convertPoint(tabbedPane, p, tab);
            if (tab.contains(converted)) {
                if (tab.isDraggableAt(converted)) {
                    return tab;
                }
            }
        }
        return null;
    }

    /**
     * @param p a {@link Point} in this tabbed pane's coordinate system.
     * @return the selectable tab under {@code p}, or {@code null} if no such tab exists.
     * @see Tab#isSelectableAt(Point)
     */
    public Tab getSelectableTabAt(final Point p) {
        checkEDT();

        for (int i = -1; i < tabs.size(); i++) {
            final TabInfo info = i < 0 ? selectedTab : tabs.get(i);
            if (info == null || info.isBeingRemoved) {
                continue;
            }
            if (info == selectedTab && i >= 0) {
                continue;
            }
            final Tab tab = info.tab;
            final Point converted = SwingUtilities.convertPoint(tabbedPane, p, tab);
            if (tab.contains(converted)) {
                if (tab.isSelectableAt(converted)) {
                    return tab;
                }
            }
        }
        return null;
    }

    private int getInfoIndex(final Tab tab) {
        checkEDT();
        for (int i = 0; i < tabs.size(); i++) {
            final TabInfo info = tabs.get(i);
            if (info.tab == tab) {
                return i;
            }
        }
        return -1;
    }

    private TabInfo getInfo(final Tab tab) {
        checkEDT();

        for (final TabInfo info : tabs) {
            if (info.tab == tab) {
                return info;
            }
        }
        return null;
    }

    private int actualizeIndex(final int index) {
        checkEDT();

        int virtual = 0;

        int actual;
        for (actual = 0; actual < tabs.size(); actual++) {
            if (virtual == index) {
                break;
            }
            if (!tabs.get(actual).isBeingRemoved) {
                virtual++;
            }
        }
        return actual;
    }

    private int virtualizeIndex(final int index) {
        checkEDT();

        int virtual = 0;

        for (int actual = 0; actual < index; actual++) {
            if (!tabs.get(actual).isBeingRemoved) {
                virtual++;
            }
        }
        return virtual;
    }

    /**
     * @return the number of tabs in this tabbed pane.
     */
    public int getTabCount() {
        checkEDT();

        int count = 0;
        for (final TabInfo tab : tabs) {
            if (!tab.isBeingRemoved) {
                count++;
            }
        }
        return count;
    }

    /**
     * @return a newly-constructed {@link List} of the tabs in this tabbed pane.
     */
    public List<Tab> getTabs() {
        checkEDT();

        final List<Tab> result = new ArrayList<>();
        for (final TabInfo info : tabs) {
            if (!info.isBeingRemoved) {
                result.add(info.tab);
            }
        }
        return result;
    }

    /**
     * Inserts a tab at a specific index in the tabbed pane. The new tab will not be selected.
     *
     * @param vIndex the index to add the tab at. Like {@link List#add(int, Object)}, this method will insert the new tab before the tab at {@code index}.
     * @param tab    the tab to add.
     * @throws IllegalArgumentException if {@code index} is less than 0 or greater than the tab count, or {@code tab} is already a member of this tabbed pane.
     */
    private void addTabInternal(final int vIndex, final Tab tab) {
        addTabInternal(vIndex, tab, true);
    }

    /**
     * Inserts a tab at a specific index in the tabbed pane.
     *
     * @param vIndex the index to add the tab at. Like {@link List#add(int, Object)}, this method will insert the new tab before the tab at {@code index}.
     * @param tab    the tab to add.
     * @param expand whether to animate the new tab expansion. If {@code false}, the new tab will immediately appear at full size.
     * @throws IllegalArgumentException if {@code index} is less than 0 or greater than the tab count, or {@code tab} is already a member of this tabbed pane.
     */
    private void addTabInternal(final int vIndex, final Tab tab, final boolean expand) {
        checkEDT();

        final int tabCount = getTabCount();
        if (vIndex < 0 || vIndex > tabCount) {
            throw new IndexOutOfBoundsException(String.format("Invalid insertion index: %d (tab count: %d)", vIndex, tabCount));
        }
        final TabInfo existing = getInfo(tab);

        if (existing != null) {
            if (existing.isBeingRemoved) {
                actuallyRemoveTab(tab);
            } else {
                throw new IllegalArgumentException("Tab has already been added: " + tab);
            }
        }

        final TabAddedEvent event = new TabAddedEvent(tabbedPane, System.currentTimeMillis(), tab, vIndex);

        final int index = actualizeIndex(vIndex);

        final boolean transpose = tabbedPane.getTabPlacement() == SwingConstants.LEFT || tabbedPane.getTabPlacement() == SwingConstants.RIGHT;

        final TabInfo info = new TabInfo();
        info.tab = tab;
        info.prefSize = tab.getPreferredSize();
        final int prefWidth = transpose ? info.prefSize.height : info.prefSize.width;
        info.vCurrentWidth = expand ? 0 : prefWidth;
        info.isBeingRemoved = false;

        if (index > 0) {
            final TabInfo prev = tabs.get(index - 1);
            info.vCurrentX = prev.vCurrentX + prev.vCurrentWidth - overlap;
        }
        tabs.add(index, info);
        contentMap.put(tab.getContent(), info);

        tabLayeredPane.add(tab);

        tabbedPane.invalidate();
        tabbedPane.validate();

        notifyTabbedPaneListeners(event);
    }

    public void addTab(final int vIndex, final Tab tab, final boolean expand) {
        addTabInternal(vIndex, tab, expand);
        handler.disable = true;
        try {
            insertTab(tabbedPane, vIndex, tab);
        } finally {
            handler.disable = false;
        }
        setSelectedIndexInternal(tabbedPane.getSelectedIndex());
    }

    /**
     * Moves a tab from its current index to a new index.
     *
     * @param tab       the tab to move.
     * @param vNewIndex the index to move the tab to. Like {@link List#add(int, Object)}, this method will insert {@code tab} before the tab at {@code newIndex}.
     * @throws IllegalArgumentException if {@code newIndex} is less than 0, greater than the tab count, or {@code tab} is not a member of this tabbed pane.
     */
    private void moveTabInternal(final Tab tab, final int vNewIndex) {
        final int tabCount = getTabCount();
        if (vNewIndex < 0 || vNewIndex > tabCount) {
            throw new IndexOutOfBoundsException(String.format("Invalid new index: %d (tab count: %d)", vNewIndex, tabCount));
        }

        int actualNewIndex = actualizeIndex(vNewIndex);
        final int currentIndex = getInfoIndex(tab);

        if (currentIndex < 0) {
            throw new IllegalArgumentException("Tab is not a member of this tabbed pane: " + tab);
        }

        final TabInfo info = tabs.get(currentIndex);

        if (info.isBeingRemoved) {
            throw new IllegalArgumentException("Tab is not a member of this tabbed pane: " + tab);
        }

        if (actualNewIndex != currentIndex) {
            final int vCurrentIndex = virtualizeIndex(currentIndex);

            final TabMovedEvent event = new TabMovedEvent(tabbedPane, System.currentTimeMillis(), info.tab, vCurrentIndex, vNewIndex);

            actualNewIndex = Math.min(actualNewIndex, tabs.size() - 1);
            tabs.remove(info);
            tabs.add(actualNewIndex, info);

            tabbedPane.invalidate();
            tabbedPane.validate();

            notifyTabbedPaneListeners(event);
        }

    }

    /**
     * Moves the given tab with animation (if enabled). Attempting to do this by removing / adding back the tab in the {@link JTabbedPane} won't produce the
     * same effect.
     *
     * @param vCurrentIndex the current index of the tab to move.
     * @param vNewIndex     the index to move the tab to.
     */
    public void moveTab(final int vCurrentIndex, final int vNewIndex) {
        final TabInfo info = tabs.get(actualizeIndex(vCurrentIndex));

        handler.disable = true;
        try {
            final int index = tabbedPane.indexOfComponent(info.tab.getContent());
            if (index >= 0 && index != vNewIndex) {
                tabbedPane.removeTabAt(index);
                insertTab(tabbedPane, vNewIndex, info.tab);
            }
        } finally {
            handler.disable = false;
        }
        moveTabInternal(info.tab, vNewIndex);
        updateMnemonics();
    }

    /**
     * Removes a tab from this tabbed pane with animation. The layout will animate the tab renderer shrinking before it is actually removed from the component
     * hierarchy. However, the tab will be immediately removed from the list of tabs in this tabbed pane, so {@link #getTabs()}, addTabInternal,
     * {@link #moveTabInternal(Tab, int)}, etc. will all behave as if it is no longer a member of this tabbed pane. If you want to remove the tab and its
     * renderer component immediately, use {@link #removeTabImmediatelyInternal(Tab)}.
     * <p>
     * If the selected tab is removed, one of the adjacent tabs will be selected before it is removed. If it is the only tab, the selection will be cleared
     * before it is removed.
     * <p>
     * {@code tab} the tab to remove. If {@code tab} is not a member of this tabbed pane, this method has no effect.
     */
    private void removeTabInternal(final Tab tab) {
        removeTabInternal(tab, true);
    }

    private void removeTabInternal(final Tab tab, final boolean startTimer) {
        checkEDT();

        final int removedIndex = getInfoIndex(tab);

        if (removedIndex >= 0) {
            final TabInfo info = tabs.get(removedIndex);
            if (info.isBeingRemoved) {
                return;
            }

            final TabRemovedEvent event = new TabRemovedEvent(tabbedPane, System.currentTimeMillis(), tab, virtualizeIndex(removedIndex));

            if (info == selectedTab) {
                if (tabs.size() == 1) {
                    setSelectedTabInternal(null);
                } else {
                    final int index = tabs.indexOf(info);

                    // select the closest tab that is not being removed
                    TabInfo newSelectedTab = null;

                    for (int i = index + 1; i < tabs.size(); i++) {
                        final TabInfo adjTab = tabs.get(i);
                        if (!adjTab.isBeingRemoved) {
                            newSelectedTab = adjTab;
                            break;
                        }
                    }
                    if (newSelectedTab == null) {
                        for (int i = index - 1; i >= 0; i--) {
                            final TabInfo adjTab = tabs.get(i);
                            if (!adjTab.isBeingRemoved) {
                                newSelectedTab = adjTab;
                                break;
                            }
                        }
                    }

                    setSelectedTabInternal(newSelectedTab);
                }
            }
            info.isBeingDragged = false;
            info.isBeingRemoved = true;
            tabbedPane.invalidate();
            tabbedPane.validate();

            notifyTabbedPaneListeners(event);
        }
    }

    /**
     * Removes a tab from this tabbed pane without animation. This means the tab renderer will be removed from the component hierarchy immediately, unlike
     * {@link #removeTabInternal(Tab)}.
     * <p>
     * If the selected tab is removed, one of the adjacent tabs will be selected before it is removed. If it is the only tab, the selection will be cleared
     * before it is removed.
     *
     * @param tab the tab to remove. If {@code tab} is not a member of this tabbed pane, this method has no effect.
     */
    private void removeTabImmediatelyInternal(final Tab tab) {
        removeTabInternal(tab, false);
        actuallyRemoveTab(tab);
    }

    private void actuallyRemoveTab(final Tab tab) {
        checkEDT();

        final TabInfo info = getInfo(tab);
        if (info != null) {
            tabLayeredPane.remove(tab);
            tabs.remove(info);
            contentMap.remove(info.tab.getContent());
        }
    }

    /**
     * Removes all tabs from this tabbed pane without animation. This method is equivalent to setting the selected tab to {@code null} and then removing all
     * tabs one by one.
     */
    private void removeAllTabsInternal() {
        checkEDT();

        final long time = System.currentTimeMillis();

        setSelectedTabInternal(null);

        final List<Tab> removedTabs = new ArrayList<>();

        while (!tabs.isEmpty()) {
            final TabInfo info = tabs.get(0);
            removedTabs.add(info.tab);
            removeTabImmediatelyInternal(info.tab);
        }

        final TabsClearedEvent event = new TabsClearedEvent(tabbedPane, time, Collections.unmodifiableList(removedTabs));

        notifyTabbedPaneListeners(event);
    }

    private void setSelectedIndexInternal(final int vIndex) {
        if (vIndex < 0) {
            setSelectedTabInternal(null);
        } else {
            setSelectedTabInternal(tabs.get(actualizeIndex(vIndex)));
        }
    }

    private void setSelectedTabInternal(final TabInfo info) {
        checkEDT();

        if (selectedTab != info) {
            final int prevIndex = selectedTab != null ? virtualizeIndex(tabs.indexOf(selectedTab)) : -1;
            final int newIndex = info != null ? virtualizeIndex(tabs.indexOf(info)) : -1;

            final Tab prevTab = selectedTab != null ? selectedTab.tab : null;
            final Tab newTab = info != null ? info.tab : null;

            final TabSelectedEvent event = new TabSelectedEvent(tabbedPane, System.currentTimeMillis(), prevTab, prevIndex, newTab, newIndex);

            if (selectedTab != null) {
                selectedTab.tab.setSelected(false);
            }

            selectedTab = info;

            if (selectedTab != null) {
                selectedTab.tab.setSelected(true);
                setContentInternal(selectedTab.tab.getContent());
            } else {
                setContentInternal(null);
            }

            tabbedPane.invalidate();
            tabbedPane.validate();

            notifyTabbedPaneListeners(event);
        }
    }

    private void setContentInternal(final Component newContent) {
        checkEDT();

        if (currentContent != newContent) {
            if (currentContent != null && currentContent.getParent() == tabbedPane && currentContent.isVisible()) {
                currentContent.setVisible(false);
            }
            currentContent = newContent;
            if (newContent != null && !newContent.isVisible()) {
                newContent.setVisible(true);
            }
            if (SwingUtilities.findFocusOwner(currentContent) != null) {
                if (!requestFocusForVisibleComponent()) {
                    tabbedPane.requestFocus();
                }
            }
        }
    }

    /**
     * Immediately updates the tabs' bounds to their eventual position where animation is complete.
     */
    public void finishAnimation() {
        checkEDT();

        layout.finishAnimation = true;
        tabbedPane.invalidate();
        tabbedPane.validate();
    }

    private void setDragState(final Tab draggedTab, final double grabX, final int dragX) {
        checkEDT();

        boolean validate = false;

        for (final TabInfo info : tabs) {
            if (info.tab == draggedTab) {
                if (!info.isBeingDragged || info.grabX != grabX || info.dragX != dragX) {
                    info.isBeingDragged = true;
                    info.grabX = grabX;
                    info.dragX = dragX;

                    validate = true;
                }
            } else {
                if (info.isBeingDragged) {
                    info.isBeingDragged = false;
                    validate = true;
                }
            }
        }

        if (validate) {
            tabbedPane.invalidate();
            tabbedPane.validate();
        }
    }

    /**
     * Removes all tabs, stops animation and attempts to unregister all listeners that could prevent this TabbedPane from being finalized. However, I am not yet
     * certain if this method eliminates all potential memory leaks (excluding references apart from internal TabbedPane code.
     */
    public void dispose() {
        checkEDT();

        synchronized (animLock) {
            animTimer.stop();
            animLock.notifyAll();
        }

        uninstallKeyboardActions();

        tabbedPane.remove(tabLayeredPane);
        mouseOverManager.uninstall(tabbedPane);
        mouseOverManager.removeExcludedComponent(tabLayeredPane);

        tabbedPane.removeContainerListener(handler);
        tabbedPane.removeChangeListener(handler);
        tabbedPane.removePropertyChangeListener(handler);
        tabbedPane.removeFocusListener(handler);

        try {
            removeAllTabsInternal();
        } catch (final Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        dragHandler.dispose();
        tabbedPane.setLayout(null);
        tabbedPane.setDropTarget(null);

        dragHandler = null;
        handler = null;
        mouseOverManager = null;
        layout = null;
        synchronized (animLock) {
            animTimer = null;
        }
        tabLayeredPane = null;
        tabbedPane = null;
    }

    private int animate(final int value, final int target, final double animFactor) {
        int d = value - target;
        d = (int) (d * animFactor);
        return d == 0 ? target : target + d;
    }

    private int animate(final int value, final int target, final double animFactor, final BooleanHolder animNeeded) {
        final int newValue = animate(value, target, animFactor);
        if (newValue != value) {
            animNeeded.value = true;
        }
        return newValue;
    }

    private int animateShrinkingOnly(final int value, final int target, final double animFactor, final BooleanHolder animNeeded) {
        final int newValue = value < target ? target : animate(value, target, animFactor);
        if (newValue != value) {
            animNeeded.value = true;
        }
        return newValue;
    }

    private TabDragInfo getDragInfo(final Transferable t) {
        Object it = null;
        try {
            it = transferableStore.getTransferableData(t);
        } catch (final UnsupportedFlavorException | IOException e) {
            log(e);
        }
        if (it instanceof TabDragInfo) {
            return (TabDragInfo) it;
        }
        return null;
    }

    private boolean isTearAwayAllowed(final Tab tab) {
        return tabbedPane.isEnabled() && dndPolicy != null && dndPolicy.isTearAwayAllowed(tabbedPane, tab);
    }

    private boolean isSnapInAllowed(final Tab tab) {
        return tabbedPane.isEnabled() && dndPolicy != null && dndPolicy.isSnapInAllowed(tabbedPane, tab);
    }

    private void dragOut(final Component dragComponent, final TabDragInfo dragInfo) {
        if (dragInfo != null) {
            final JhromeTabbedPaneUI draggedParent = SwingUtils.getJTabbedPaneAncestorUI(dragInfo.tab);
            if (draggedParent != null && dragComponent == draggedParent.tabbedPane && draggedParent.isTearAwayAllowed(dragInfo.tab)) {
                if (dragInfo.floatingTabHandler != null) {
                    dragInfo.floatingTabHandler.onFloatingBegin(dragInfo.tab, dragInfo.getGrabPoint());
                }
                removeDraggedTabFromParent(dragInfo);
                // draggedParent.mouseOverTopZone = false;
            }
        }
    }

    private void removeDraggedTabFromParent(final TabDragInfo dragInfo) {
        final JhromeTabbedPaneUI draggedParent = SwingUtils.getJTabbedPaneAncestorUI(dragInfo.tab);
        if (draggedParent != null) {
            draggedParent.setDragState(null, 0, 0);
            final Component draggedTabComponent = dragInfo.tab.getTabComponent();
            draggedParent.removeTabImmediatelyInternal(dragInfo.tab);
            final int tabIndex = draggedParent.tabbedPane.indexOfComponent(dragInfo.tab.getContent());
            if (tabIndex < 0) {
                return;
            }
            draggedParent.tabbedPane.removeTabAt(tabIndex);
            dragInfo.tab.setTabComponent(draggedTabComponent);
        }
    }

    private void dragOver(final DropTargetDragEvent dtde) {
        final TabDragInfo dragInfo = getDragInfo(dtde.getTransferable());
        if (dragInfo != null) {
            final JhromeTabbedPaneUI tabbedPaneUI = SwingUtils.getJTabbedPaneAncestorUI(dtde.getDropTargetContext().getComponent());

            final Point p = dtde.getLocation();
            dragInfo.setGrabPoint(SwingUtilities.convertPoint(dtde.getDropTargetContext().getComponent(), p, tabbedPaneUI.tabbedPane));

            if (!Utils.contains(tabbedPaneUI.dropZone, p)) {
                dragOut(dtde.getDropTargetContext().getComponent(), dragInfo);
                return;
            }

            JhromeTabbedPaneUI sourceUI = SwingUtils.getJTabbedPaneAncestorUI(dragInfo.tab);

            if (sourceUI != tabbedPaneUI && (sourceUI == null || sourceUI.isTearAwayAllowed(dragInfo.tab)) && tabbedPaneUI.isSnapInAllowed(dragInfo.tab)) {
                if (dragInfo.floatingTabHandler != null) {
                    dragInfo.floatingTabHandler.onFloatingEnd();
                }

                if (sourceUI != null) {
                    removeDraggedTabFromParent(dragInfo);
                }

                sourceUI = tabbedPaneUI;

                // tabbedPane.mouseOverTopZone = true;

                final Window ancestor = SwingUtilities.getWindowAncestor(tabbedPaneUI.tabbedPane);
                if (ancestor != null) {
                    ancestor.toFront();
                    ancestor.requestFocus();
                }

                final int dragX = dtde.getLocation().x;

                final int newIndex = tabbedPaneUI.layout.getInsertIndex(dragInfo.tab, dragInfo.grabX, dragX);
                tabbedPaneUI.addTab(newIndex, dragInfo.tab, false);
                if (dragInfo.tab.isEnabled()) {
                    tabbedPaneUI.tabbedPane.setSelectedIndex(newIndex);
                }

                tabbedPaneUI.setDragState(dragInfo.tab, dragInfo.grabX, dragX);
            } else {
                final TabInfo info = tabbedPaneUI.getInfo(dragInfo.tab);
                if (info != null) {
                    final int dragX = dtde.getLocation().x;
                    tabbedPaneUI.setDragState(dragInfo.tab, dragInfo.grabX, dragX);

                    final int newIndex = tabbedPaneUI.layout.getInsertIndex(dragInfo.tab, dragInfo.grabX, dragX);
                    final int currentIndex = tabbedPaneUI.getInfoIndex(dragInfo.tab);
                    tabbedPaneUI.moveTab(currentIndex, newIndex);
                    if (dragInfo.tab.isEnabled()) {
                        tabbedPaneUI.tabbedPane.setSelectedComponent(dragInfo.tab.getContent());
                    }
                }
            }
        }
    }

    public Image createDragImage(final Tab tab) {
        final Component rend = tab;

        final int width = tabbedPane.getWidth();
        final int height = tabbedPane.getHeight();

        if (width == 0 || height == 0) {
            return null;
        }

        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = (Graphics2D) image.getGraphics();

        final AffineTransform origXform = g2.getTransform();

        if (contentPanelBorder != null) {
            contentPanelBorder.paintBorder(tabbedPane, g2, contentPanelBounds.x, contentPanelBounds.y, contentPanelBounds.width, contentPanelBounds.height);
        }
        if (tab.getContent() != null) {
            final Insets contentInsets = contentPanelBorder == null ? new Insets(0, 0, 0, 0) : contentPanelBorder.getBorderInsets(tabbedPane);
            g2.translate(contentPanelBounds.x + contentInsets.left, contentPanelBounds.y + contentInsets.top);
            tab.getContent().paint(g2);
        }

        g2.setTransform(origXform);

        final int tabX = tabLayeredPane.getX() + tab.getX();
        final int tabY = tabLayeredPane.getY() + tab.getY();

        g2.translate(tabX, tabY);
        rend.paint(g2);

        final BufferedImage rescaled = new BufferedImage(width * 3 / 4, height * 3 / 4, BufferedImage.TYPE_INT_ARGB);

        g2 = (Graphics2D) rescaled.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));

        g2.drawImage(image, 0, 0, rescaled.getWidth(), rescaled.getHeight(), 0, 0, width, height, null);

        return rescaled;
    }

    private void notifyTabbedPaneListeners(final TabbedPaneEvent event) {
        for (final ITabbedPaneListener listener : tabListeners) {
            listener.onEvent(event);
        }
    }

    @Override
    public void paint(final Graphics g, final JComponent c) {
        if (contentPanelBorder != null) {
            contentPanelBorder.paintBorder(c, g, contentPanelBounds.x, contentPanelBounds.y, contentPanelBounds.width, contentPanelBounds.height);
        }
    }

    @Override
    public void update(final Graphics g, final JComponent c) {
        updateTabs();
        super.update(g, c);

        synchronized (updateLock) {
            updateLock.notifyAll();
        }
    }

    public int tabForCoordinate(final JTabbedPane pane, final int x, final int y) {
        final Tab tab = getHoverableTabAt(new Point(x, y));
        return virtualizeIndex(getInfoIndex(tab));
    }

    @Override
    public Rectangle getTabBounds(final JTabbedPane pane, final int vIndex) {
        final TabInfo info = tabs.get(actualizeIndex(vIndex));
        return info != null ? info.tab.getBounds() : null;
    }

    @Override
    public int getTabRunCount(final JTabbedPane pane) {
        return 1;
    }

    private void updateTabs() {
        final List<Tab> toRemove = new LinkedList<>();
        for (final TabInfo info : tabs) {
            if (tabbedPane.indexOfComponent(info.tab.getContent()) < 0) {
                toRemove.add(info.tab);
            }
        }

        for (final Tab tab : toRemove) {
            removeTabInternal(tab);
        }

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            updateTab(i, true);
        }
    }

    private void updateTab(final int vIndex, final boolean createIfNecessary) {
        final String title = tabbedPane.getTitleAt(vIndex);
        final Icon icon = tabbedPane.getIconAt(vIndex);
        final Component content = tabbedPane.getComponentAt(vIndex);
        final Component tabComponent = tabbedPane.getTabComponentAt(vIndex);
        final int mnemonic = tabbedPane.getMnemonicAt(vIndex);
        final int displayedMnemonicIndex = tabbedPane.getDisplayedMnemonicIndexAt(vIndex);
        final Color background = tabbedPane.getBackgroundAt(vIndex);
        final Color foreground = tabbedPane.getForegroundAt(vIndex);
        final boolean selected = tabbedPane.getSelectedIndex() == vIndex;
        final boolean enabled = tabbedPane.isEnabledAt(vIndex);

        Tab tab = null;
        final TabInfo info = contentMap.get(content);
        if (info != null) {
            tab = info.tab;
        } else if (createIfNecessary) {
            tab = tabFactory.createTab();
        }

        if (tab != null) {
            tab.setTitle(title);
            tab.setIcon(icon);
            tab.setMnemonic(mnemonic);
            tab.setDisplayedMnemonicIndex(displayedMnemonicIndex);
            tab.setContent(content);
            tab.setTabComponent(tabComponent);
            tab.setSelected(selected);
            tab.setEnabled(enabled);
            tab.setBackground(background);
            tab.setForeground(foreground);

            if (info != null) {
                if (tabs.indexOf(info) != vIndex) {
                    moveTabInternal(info.tab, vIndex);
                }
            } else if (createIfNecessary) {
                addTabInternal(vIndex, tab);
            }
        }
    }

   protected void installKeyboardActions() {
        InputMap km = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        SwingUtilities.replaceUIInputMap(tabbedPane, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, km);
        km = getInputMap(JComponent.WHEN_FOCUSED);
        SwingUtilities.replaceUIInputMap(tabbedPane, JComponent.WHEN_FOCUSED, km);
        SwingUtilities.replaceUIActionMap(tabbedPane, loadActionMap());
        updateMnemonics();
    }

    InputMap getInputMap(final int condition) {
        if (condition == JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT) {
            return (InputMap) UIManager.get("TabbedPane.ancestorInputMap", tabbedPane.getLocale());
        } else if (condition == JComponent.WHEN_FOCUSED) {
            return (InputMap) UIManager.get("TabbedPane.focusInputMap", tabbedPane.getLocale());
        }
        return null;
    }

    protected void uninstallKeyboardActions() {
        SwingUtilities.replaceUIActionMap(tabbedPane, null);
        SwingUtilities.replaceUIInputMap(tabbedPane, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, null);
        SwingUtilities.replaceUIInputMap(tabbedPane, JComponent.WHEN_FOCUSED, null);
        SwingUtilities.replaceUIInputMap(tabbedPane, JComponent.WHEN_IN_FOCUSED_WINDOW, null);
        mnemonicToIndexMap = null;
        mnemonicInputMap = null;
    }

    /**
     * Reloads the mnemonics. This should be invoked when a memonic changes, when the title of a mnemonic changes, or when tabs are added/removed.
     */
    private void updateMnemonics() {
        resetMnemonics();
        for (int counter = tabbedPane.getTabCount() - 1; counter >= 0; counter--) {
            final int mnemonic = tabbedPane.getMnemonicAt(counter);

            if (mnemonic > 0) {
                addMnemonic(counter, mnemonic);
            }
        }
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

    /**
     * Adds the specified mnemonic at the specified index.
     */
    private void addMnemonic(final int index, final int mnemonic) {
        if (mnemonicToIndexMap == null) {
            initMnemonics();
        }
        mnemonicInputMap.put(KeyStroke.getKeyStroke(mnemonic, InputEvent.ALT_MASK), "setSelectedIndex");
        mnemonicToIndexMap.put(mnemonic, index);
    }

    /**
     * Installs the state needed for mnemonics.
     */
    private void initMnemonics() {
        mnemonicToIndexMap = new HashMap<>();
        mnemonicInputMap = new ComponentInputMapUIResource(tabbedPane);
        mnemonicInputMap.setParent(SwingUtilities.getUIInputMap(tabbedPane, JComponent.WHEN_IN_FOCUSED_WINDOW));
        SwingUtilities.replaceUIInputMap(tabbedPane, JComponent.WHEN_IN_FOCUSED_WINDOW, mnemonicInputMap);
    }

    ActionMap loadActionMap() {
        final ActionMap map = new ActionMapUIResource();
        put(map, new Actions(Actions.NEXT));
        put(map, new Actions(Actions.PREVIOUS));
        put(map, new Actions(Actions.RIGHT));
        put(map, new Actions(Actions.LEFT));
        put(map, new Actions(Actions.UP));
        put(map, new Actions(Actions.DOWN));
        put(map, new Actions(Actions.PAGE_UP));
        put(map, new Actions(Actions.PAGE_DOWN));
        put(map, new Actions(Actions.REQUEST_FOCUS));
        put(map, new Actions(Actions.REQUEST_FOCUS_FOR_VISIBLE));
        put(map, new Actions(Actions.SET_SELECTED));
        put(map, new Actions(Actions.SELECT_FOCUSED));
        put(map, new Actions(Actions.SCROLL_FORWARD));
        put(map, new Actions(Actions.SCROLL_BACKWARD));
        return map;
    }

    // REMIND(aim,7/29/98): This method should be made
    // protected in the next release where
    // API changes are allowed
    boolean requestFocusForVisibleComponent() {
        if (currentContent != null) {
            if (currentContent.isFocusable()) {
                compositeRequestFocus(currentContent);
                return true;
            } else if (currentContent instanceof JComponent
                    && ((JComponent) currentContent).requestDefaultFocus()) {

                return true;
            }
        }
        return false;
    }

    private Component compositeRequestFocus(final Component component) {
        if (component instanceof Container) {
            final Container container = (Container)component;
            if (container.isFocusCycleRoot()) {
                final FocusTraversalPolicy policy = container.getFocusTraversalPolicy();
                final Component comp = policy.getDefaultComponent(container);
                if (comp!=null) {
                    comp.requestFocus();
                    return comp;
                }
            }
            final Container rootAncestor = container.getFocusCycleRootAncestor();
            if (rootAncestor!=null) {
                final FocusTraversalPolicy policy = rootAncestor.getFocusTraversalPolicy();
                final Component comp = policy.getComponentAfter(rootAncestor, container);

                if (comp!=null && SwingUtilities.isDescendingFrom(comp, container)) {
                    comp.requestFocus();
                    return comp;
                }
            }
        }
        if (component.isFocusable()) {
            component.requestFocus();
            return component;
        }
        return null;
    }

    private static class TabInfo {
        Tab tab;
        Dimension prefSize;

        /**
         * Whether the tab is being removed (contracting until its width reaches zero, when it will be completely removed)
         */
        boolean isBeingRemoved;

        /**
         * The tab's target x position in virtual coordinate space. It will be scaled down to produce the actual target x position.
         */
        int vTargetX;
        /**
         * The tab's target width in virtual coordinate space. This does not include the overlap area -- it is the distance to the virtual target x position of
         * the next tab. To get the actual target width, this value will be scaled down and the overlap amount will be added.
         */
        int vTargetWidth;

        /**
         * The tab's target bounds in actual coordinate space. Not valid for tabs that are being removed.
         */
        final Rectangle targetBounds = new Rectangle();

        /**
         * The tab's current x position in virtual coordinate space. It will be scaled down to produce the actual current x position.
         */
        int vCurrentX;
        /**
         * The tab's current width in virtual coordinate space. This does not include the overlap area -- it is the distance to the virtual current x position
         * of the next tab. To get the actual current width, this value will be scaled down and the overlap amount will be added.
         */
        int vCurrentWidth;

        /**
         * Whether the tab is being dragged.
         */
        boolean isBeingDragged;

        /**
         * The x position of the dragging mouse cursor in actual coordinate space.
         */
        int dragX;
        /**
         * The relative x position at which the tab was grabbed, as a proportion of its width (0.0 = left side, 0.5 = middle, 1.0 = right side). This way if the
         * tab width changes while it's being dragged, the layout manager can still give it a reasonable position relative to the mouse cursor.
         */
        double grabX;
    }

    private final class MouseManager extends RecursiveListener {
        final MouseAdapter adapter = new MouseAdapter() {
            private void updateHoldTabScale(final MouseEvent e) {
                Point p = e.getPoint();
                p = SwingUtilities.convertPoint((Component) e.getSource(), p, tabbedPane);
                final boolean newMouseOver = Utils.contains(topZone, p);
                if (newMouseOver != mouseOverTopZone) {
                    mouseOverTopZone = newMouseOver;
                    tabbedPane.invalidate();
                    tabbedPane.validate();
                }
            }

            @Override
            public void mouseEntered(final MouseEvent e) {
                updateHoldTabScale(e);
            }

            @Override
            public void mouseExited(final MouseEvent e) {
                updateHoldTabScale(e);
            }

            @Override
            public void mouseMoved(final MouseEvent e) {
                updateHoldTabScale(e);

                if (!tabbedPane.isEnabled()) {
                    return;
                }

                final Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), tabbedPane);
                final Tab tab = getHoverableTabAt(p);
                for (final TabInfo info : tabs) {
                    info.tab.setRollover(info.tab.isEnabled() && info.tab == tab);
                }
            }

            @Override
            public void mousePressed(final MouseEvent e) {
                if (!tabbedPane.isEnabled()) {
                    return;
                }

                final Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), tabbedPane);
                final Tab tab = getSelectableTabAt(p);
                if (tab != null) {
                    final int vIndex = virtualizeIndex(getInfoIndex(tab));
                    if (tabbedPane.getSelectedComponent() != tab.getContent() && tabbedPane.isEnabledAt(vIndex)) {
                        tabbedPane.setSelectedComponent(tab.getContent());
                    } else if (tabbedPane.isRequestFocusEnabled()) {
                        tabbedPane.requestFocus();
                    }
                }
            }
        };

        @Override
        protected void install(final Component c) {
            c.addMouseListener(adapter);
            c.addMouseMotionListener(adapter);
        }

        @Override
        protected void uninstall(final Component c) {
            c.removeMouseListener(adapter);
            c.removeMouseMotionListener(adapter);
        }
    }

    private static class TabLayeredPane extends JLayeredPane implements UIResource {

    }

    private final class TabLayoutManager implements LayoutManager {
        /**
         * The sustained total width of the tab zone in virtual coordinate space. This does not include the overlap area of the last tab.
         */
        private int vSustainedTabZoneWidth = 0;

        /**
         * The current width scale.
         */
        private double widthScale = 1.0;

        private boolean finishAnimation = false;

        private int getInsertIndex(final Tab tab, final double grabX, final int dragX) {
            checkEDT();

            int targetWidth = useUniformWidth ? maxUniformWidth : tab.getPreferredSize().width;
            targetWidth = (int) (targetWidth * widthScale);
            int vX = dragX - (int) (grabX * targetWidth);
            vX = (int) ((vX - tabZone.x) / widthScale);

            int vIndex = 0;

            int vTargetX = 0;

            for (final TabInfo info : tabs) {
                if (info.tab == tab || info.isBeingRemoved) {
                    continue;
                }

                if (vX < vTargetX + info.vTargetWidth / 2) {
                    break;
                }

                vTargetX += info.vTargetWidth;
                vIndex++;
            }

            return vIndex;
        }

        @Override
        public void addLayoutComponent(final String name, final Component comp) {
        }

        @Override
        public void removeLayoutComponent(final Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(final Container parent) {
            return layoutSize(parent, PREFERRED);
        }

        @Override
        public Dimension minimumLayoutSize(final Container parent) {
            return layoutSize(parent, MINIMUM);
        }

        private Dimension layoutSize(final Container parent, final int sizeType) {
            final boolean transpose = tabbedPane.getTabPlacement() == SwingConstants.LEFT || tabbedPane.getTabPlacement() == SwingConstants.RIGHT;

            int contentWidth = 0;
            int contentHeight = 0;
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                final Component content = tabbedPane.getComponentAt(i);
                final Dimension size = getSize(content, sizeType);
                contentWidth = Math.max(contentWidth, size.width);
                contentHeight = Math.max(contentHeight, size.height);
            }

            int tabsHeight = 0;

            for (final TabInfo info : tabs) {
                info.prefSize = info.tab.getPreferredSize();
                tabsHeight = Math.max(tabsHeight, transpose ? info.prefSize.width : info.prefSize.height);
            }

            final Insets insets = tabbedPane.getInsets();
            int width = insets.left + insets.right + contentWidth;
            int height = insets.top + insets.bottom + contentHeight;

            if (transpose) {
                width += tabsHeight;
            } else {
                height += tabsHeight;
            }
            if (contentPanelBorder != null) {
                final Insets contentInsets = contentPanelBorder.getBorderInsets(tabbedPane);
                width += contentInsets.left + contentInsets.right;
                height += contentInsets.top + contentInsets.bottom;
            }

            return new Dimension(width, height);
        }

        private Dimension getSize(final Component comp, final int sizeType) {
            switch (sizeType) {
                case MINIMUM:
                    return comp.getMinimumSize();
                case PREFERRED:
                    return comp.getPreferredSize();
                case MAXIMUM:
                    return comp.getMaximumSize();
                default:
                    return null;
            }
        }

        @Override
        public void layoutContainer(final Container parent) {
            checkEDT();

            final boolean reset = finishAnimation;
            finishAnimation = false;

            final double animFactor = reset ? 0.0 : JhromeTabbedPaneUI.this.animFactor;

            final boolean transpose = tabbedPane.getTabPlacement() == SwingConstants.LEFT || tabbedPane.getTabPlacement() == SwingConstants.RIGHT;

            final int parentWidth = parent.getWidth();
            final int parentHeight = parent.getHeight();

            final Insets insets = parent.getInsets();

            final Dimension rightButtonsPanelPrefSize = rightButtonsPanel.getPreferredSize();

            final int availWidth = parentWidth - insets.left - insets.right;
            final int availHeight = parentHeight - insets.top - insets.bottom;
            final int tabMargin = 2;
            final int availTopZoneWidth = (transpose ? availHeight : availWidth) - tabMargin * 2;
            final int availTabZoneWidth = availTopZoneWidth - rightButtonsPanelPrefSize.width;
            final BooleanHolder animNeeded = new BooleanHolder(false);
            int vTargetX = 0;
            int vTargetTabZoneWidth = 0;
            int vCurrentTabZoneWidth = 0;

            int targetTabHeight = rightButtonsPanelPrefSize.height;

            boolean anyDragging = false;

            for (int i = 0; i < tabs.size(); i++) {
                final TabInfo info = tabs.get(i);
                info.vTargetX = vTargetX;

                info.prefSize = info.tab.getPreferredSize();

                info.vTargetWidth = info.isBeingRemoved ? 0 : Math.max(0, useUniformWidth ? maxUniformWidth - overlap : info.prefSize.width - overlap);

                // animate the tab x position
                info.vCurrentX = animate(info.vCurrentX, vTargetX, animFactor, animNeeded);

                // animate the tab width
                info.vCurrentWidth = animate(info.vCurrentWidth, info.vTargetWidth, animFactor, animNeeded);

                if (info.isBeingDragged) {
                    anyDragging = true;
                }

                if (!info.isBeingRemoved) {
                    vTargetX += info.vTargetWidth;
                    vTargetTabZoneWidth += info.vTargetWidth;
                    targetTabHeight = Math.max(targetTabHeight, info.prefSize.height);
                }
                vCurrentTabZoneWidth += info.vCurrentWidth;

                info.tab.getContent().setVisible(tabbedPane.getSelectedIndex() == virtualizeIndex(i));
            }

            final TabInfo lastInfo = tabs.isEmpty() ? null : tabs.get(tabs.size() - 1);

            final int vTargetRightButtonsPanelX = lastInfo != null ? lastInfo.vCurrentX == lastInfo.vTargetX ? lastInfo.vCurrentX + lastInfo.vCurrentWidth : lastInfo.vTargetX + lastInfo.vCurrentWidth : 0;
            if (reset || vCurrentTabZoneWidth > vSustainedTabZoneWidth) {
                vSustainedTabZoneWidth = vCurrentTabZoneWidth;
            } else if (!mouseOverTopZone && !anyDragging && vSustainedTabZoneWidth > vTargetTabZoneWidth) {
                animNeeded.value = true;
                vSustainedTabZoneWidth = animate(vSustainedTabZoneWidth, vTargetTabZoneWidth, animFactor);
            }

            // Compute necessary width scale to fit all tabs on screen
            widthScale = vSustainedTabZoneWidth > availTabZoneWidth - overlap ? (availTabZoneWidth - overlap) / (double) vSustainedTabZoneWidth : 1.0;

            // Adjust width scale as necessary so that no tab (except those being removed) is narrower than its minimum width
            double adjWidthScale = widthScale;
            for (final TabInfo info : tabs) {
                if (info.isBeingRemoved) {
                    continue;
                }
                final Dimension minSize = info.tab.getMinimumSize();
                if (minSize != null && info.vCurrentWidth >= minSize.width) {
                    final int prefWidth = transpose ? info.prefSize.height : info.prefSize.width;
                    final int targetWidth = useUniformWidth ? maxUniformWidth : prefWidth;
                    adjWidthScale = Math.max(adjWidthScale, minSize.width / (double) targetWidth);
                }
            }
            widthScale = adjWidthScale;

            final int currentTabHeight = transpose ? tabZone.width : tabZone.height;
            int newTabHeight = targetTabHeight;
            if (currentTabHeight > 0) {
                newTabHeight = animate(currentTabHeight, targetTabHeight, animFactor, animNeeded);
            }
            final int extraDropZoneSpace = 25;
            /*
             * How many pixels the content panel overlaps the tabs. This is necessary with the Google Chrome appearance to make the selected tab and the content panel
             * look like a contiguous object
             */
            final int contentPanelOverlap = 1;
            if (transpose) {
                topZone.setFrame(insets.left, insets.top + tabMargin, newTabHeight, availTopZoneWidth);
                tabZone.setFrame(insets.left, insets.top + tabMargin, topZone.height, availTabZoneWidth);
                dropZone.setFrame(insets.left, insets.top + tabMargin, Math.min(availWidth, currentTabHeight + extraDropZoneSpace), availTopZoneWidth);

                if (tabbedPane.getTabPlacement() == SwingConstants.RIGHT) {
                    topZone.x = insets.left + availWidth - topZone.width - 1;
                    tabZone.x = insets.left + availWidth - tabZone.width - 1;
                    dropZone.x = insets.left + availWidth - dropZone.width - 1;
                    tabLayeredPane.setBounds(tabZone.x, 0, tabZone.width + insets.right, tabbedPane.getHeight());
                    contentPanelBounds.setBounds(insets.left, insets.top, availWidth - tabZone.width + contentPanelOverlap, availHeight);
                } else {
                    tabLayeredPane.setBounds(0, 0, tabZone.width + insets.left, tabbedPane.getHeight());
                    contentPanelBounds.setBounds(tabZone.x + tabZone.width - contentPanelOverlap, insets.top, availWidth - tabZone.width + contentPanelOverlap, availHeight);
                }
            } else {
                topZone.setFrame(insets.left + tabMargin, insets.top, availTopZoneWidth, newTabHeight);
                tabZone.setFrame(insets.left + tabMargin, insets.top, availTabZoneWidth, topZone.height);
                dropZone.setFrame(insets.left + tabMargin, insets.top, availTopZoneWidth, Math.min(availHeight, tabZone.height + extraDropZoneSpace));

                if (tabbedPane.getTabPlacement() == SwingConstants.BOTTOM) {
                    topZone.y = insets.top + availHeight - topZone.height - 1;
                    tabZone.y = insets.top + availHeight - tabZone.height - 1;
                    dropZone.y = insets.top + availHeight - dropZone.height - 1;
                    tabLayeredPane.setBounds(0, tabZone.y, tabbedPane.getWidth(), tabZone.height + insets.bottom);
                    contentPanelBounds.setBounds(insets.left, insets.top, availWidth, availHeight - tabZone.height + contentPanelOverlap);
                } else {
                    tabLayeredPane.setBounds(0, 0, tabbedPane.getWidth(), tabZone.height + insets.top);
                    contentPanelBounds.setBounds(insets.left, tabZone.y + tabZone.height - contentPanelOverlap, availWidth, availHeight - tabZone.height + contentPanelOverlap);
                }
            }

            final int tabZoneX = transpose ? tabZone.y : tabZone.x;
            final int topZoneX = transpose ? topZone.y : topZone.x;
            final int topZoneY = transpose ? topZone.x : topZone.y;
            final int topZoneWidth = transpose ? topZone.height : topZone.width;
            final int topZoneHeight = transpose ? topZone.width : topZone.height;
            final int tabZoneHeight = transpose ? tabZone.width : tabZone.height;
            final int tabLayeredPaneY = transpose ? tabLayeredPane.getX() : tabLayeredPane.getY();

            // now, lay out the tabs
            for (final TabInfo info : tabs) {
                int x = topZoneX + (int) (info.vCurrentX * widthScale);
                final int targetX = topZoneX + (int) (info.vTargetX * widthScale);
                final int width = (int) (info.vCurrentWidth * widthScale) + overlap;
                final int targetWidth = (int) (info.vTargetWidth * widthScale) + overlap;

                if (info.isBeingDragged) {
                    x = info.dragX - (int) (info.grabX * width);
                    x = Math.max(topZoneX, Math.min(topZoneX + topZoneWidth - width, x));
                    info.vCurrentX = (int) ((x - topZoneX) / widthScale);
                }
                info.tab.setBounds(x, topZoneY - tabLayeredPaneY, width, tabZoneHeight);
                info.targetBounds.setFrame(targetX, topZoneY, targetWidth, tabZoneHeight);
            }

            // lay out the content panel and right button panel
            final Insets contentInsets = contentPanelBorder == null ? new Insets(0, 0, 0, 0) : contentPanelBorder.getBorderInsets(tabbedPane);

            final int contentX = contentPanelBounds.x + contentInsets.left;
            final int contentW = contentPanelBounds.width - contentInsets.left - contentInsets.right;
            final int contentY = contentPanelBounds.y + contentInsets.top;
            final int contentH = contentPanelBounds.height - contentInsets.top - contentInsets.bottom;

            for (final Component comp : tabbedPane.getComponents()) {
                if (comp != tabLayeredPane) {
                    comp.setBounds(contentX, contentY, contentW, contentH);
                }
            }

            // animate the right buttons panel x position. If it must increase to reach the target, do it immediately, without animation;
            // If it must decrease, do it with animation.
            int vCurrentRightButtonsPanelX = (int) ((rightButtonsPanel.getX() - overlap / 2 - tabZoneX) / widthScale);
            vCurrentRightButtonsPanelX = animateShrinkingOnly(vCurrentRightButtonsPanelX, vTargetRightButtonsPanelX, animFactor, animNeeded);
            int rightButtonsPanelX = tabZoneX + (int) (vCurrentRightButtonsPanelX * widthScale) + overlap / 2;

            // keep right buttons panel from getting pushed off the edge of the tabbed pane when minimum tab width is reached
            rightButtonsPanelX = Math.min(rightButtonsPanelX, topZoneX + topZoneWidth - rightButtonsPanelPrefSize.width);
            rightButtonsPanel.setBounds(rightButtonsPanelX, topZoneY - tabLayeredPaneY, rightButtonsPanelPrefSize.width, topZoneHeight);

            for (int i = tabs.size() - 1; i >= 0; i--) {
                final TabInfo info = tabs.get(i);
                if (info.isBeingRemoved && info.vCurrentWidth == 0) {
                    actuallyRemoveTab(info.tab);
                }
            }

            int layer = JLayeredPane.DEFAULT_LAYER;

            tabLayeredPane.setComponentZOrder(rightButtonsPanel, layer++);

            if (selectedTab != null) {
                try {
                    tabLayeredPane.setComponentZOrder(selectedTab.tab, layer++);
                } catch (final Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            }

            // setComponentZOrder( contentPanel , layer++ );

            for (final TabInfo info : tabs) {
                if (info == selectedTab) {
                    continue;
                }
                tabLayeredPane.setComponentZOrder(info.tab, layer++);
            }

            tabLayeredPane.repaint();

            synchronized (animLock) {
                if (animTimer != null) {
                    if (animNeeded.value) {
                        animTimer.start();
                    } else {
                        animTimer.stop();
                    }
                    animLock.notifyAll();
                }
            }
        }
    }

    private final class DragHandler implements DragSourceListener, DragSourceMotionListener, DragGestureListener {
        final DragSource source;

        final DragGestureRecognizer dragGestureRecognizer;

        Point dragOrigin;

        TabDragInfo dragInfo;

        public DragHandler(final Component comp, final int actions) {
            source = new DragSource();
            dragGestureRecognizer = source.createDefaultDragGestureRecognizer(comp, actions, this);
            source.addDragSourceMotionListener(this);
        }

        public void dispose() {
            source.removeDragSourceListener(this);
            source.removeDragSourceMotionListener(this);
            dragGestureRecognizer.removeDragGestureListener(this);
            dragGestureRecognizer.setComponent(null);
        }

        @Override
        public void dragGestureRecognized(final DragGestureEvent dge) {
            if (!tabbedPane.isEnabled()) {
                return;
            }

            dragOrigin = dge.getDragOrigin();

            final Tab draggedTab = getDraggableTabAt(dragOrigin);
            if (draggedTab != null) {
                final Window window = SwingUtilities.getWindowAncestor(tabbedPane);
                Dimension sourceWindowSize = null;
                if (window != null) {
                    sourceWindowSize = window.getSize();
                }
                final Point p = SwingUtilities.convertPoint(tabbedPane, dragOrigin, draggedTab);
                final double grabX = p.x / (double) draggedTab.getWidth();

                dragInfo = new TabDragInfo(draggedTab, dragOrigin, grabX, floatingTabHandler, sourceWindowSize);
                source.startDrag(dge, Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR), transferableStore.createTransferable(dragInfo), this);
            }
        }

        @Override
        public void dragEnter(final DragSourceDragEvent dsde) {
        }

        @Override
        public void dragExit(final DragSourceEvent dse) {
        }

        @Override
        public void dragOver(final DragSourceDragEvent dsde) {
        }

        @Override
        public void dragMouseMoved(final DragSourceDragEvent dsde) {
            if (dragInfo != null) {
                final JhromeTabbedPaneUI draggedParent = SwingUtils.getJTabbedPaneAncestorUI(dragInfo.tab);
                if (draggedParent != null) {
                    final Point p = dsde.getLocation();
                    SwingUtilities.convertPointFromScreen(p, draggedParent.tabbedPane);
                    if (!Utils.contains(draggedParent.dropZone, p)) {
                        dragOut(dsde.getDragSourceContext().getComponent(), dragInfo);
                    }
                }

                if (dragInfo.floatingTabHandler != null) {
                    dragInfo.floatingTabHandler.onFloatingTabDragged(dsde, dragInfo.tab, dragInfo.grabX);
                }
            }
        }

        @Override
        public void dropActionChanged(final DragSourceDragEvent dsde) {
        }

        @Override
        public void dragDropEnd(final DragSourceDropEvent dsde) {
            if (dragInfo != null) {
                if (dragInfo.floatingTabHandler != null) {
                    dragInfo.floatingTabHandler.onFloatingEnd();
                }

                final JhromeTabbedPaneUI draggedParent = SwingUtils.getJTabbedPaneAncestorUI(dragInfo.tab);

                if (dragInfo.tab != null && draggedParent == null && tabDropFailureHandler != null) {
                    tabDropFailureHandler.onDropFailure(dsde, dragInfo.tab, dragInfo.sourceWindowSize);
                }

                if (draggedParent != null) {
                    draggedParent.setDragState(null, 0, 0);
                }

                dragInfo = null;
            }
            transferableStore.cleanUp(dsde.getDragSourceContext().getTransferable());
        }
    }

    private final class DropHandler implements DropTargetListener {
        private TabDragInfo dragInfo = null;

        public DropHandler(final Component comp) {
            new DropTarget(comp, this);
        }

        @Override
        public void dragEnter(final DropTargetDragEvent dtde) {
            dragInfo = getDragInfo(dtde.getTransferable());
        }

        @Override
        public void dragOver(final DropTargetDragEvent dtde) {
            JhromeTabbedPaneUI.this.dragOver(dtde);
        }

        @Override
        public void dragExit(final DropTargetEvent dte) {
            dragOut(dte.getDropTargetContext().getComponent(), dragInfo);
            dragInfo = null;
        }

        @Override
        public void dropActionChanged(final DropTargetDragEvent dtde) {

        }

        @Override
        public void drop(final DropTargetDropEvent dtde) {
            setDragState(null, 0, 0);

            final TabDragInfo dragInfo = getDragInfo(dtde.getTransferable());
            if (dragInfo != null) {
                if (dragInfo.tab != null && Utils.contains(dropZone, dtde.getLocation()) && isSnapInAllowed(dragInfo.tab)) {
                    dtde.acceptDrop(dtde.getDropAction());
                } else {
                    dtde.rejectDrop();
                }
            } else {
                dtde.rejectDrop();
            }

            dtde.dropComplete(true);
        }
    }

    private final class Handler implements PropertyChangeListener, ContainerListener, ChangeListener, FocusListener {
        boolean disable = false;

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            if (disable) {
                return;
            }
            if ("indexForTabComponent".equals(evt.getPropertyName())) {
                updateTabs();
            } else if ("mnemonicAt".equals(evt.getPropertyName())) {
                updateTabs();
                updateMnemonics();
            } else if ("indexForTitle".equals(evt.getPropertyName())) {
                updateTab((Integer) evt.getNewValue(), false);
            } else if ("enabled".equals(evt.getPropertyName())) {
                newTabButton.setEnabled(tabbedPane.isEnabled());
                tabbedPane.repaint();
            } else if (NEW_TAB_BUTTON_VISIBLE.equals(evt.getPropertyName())) {
                newTabButton.setVisible(PropertyGetter.get(Boolean.class, tabbedPane, NEW_TAB_BUTTON_VISIBLE, null, false));
            } else if (NEW_TAB_BUTTON_UI.equals(evt.getPropertyName())) {
                final ButtonUI ui = PropertyGetter.get(ButtonUI.class, tabbedPane, NEW_TAB_BUTTON_UI, (String) null);
                if (ui != null) {
                    newTabButton.setUI(ui);
                }
            } else if (TAB_CLOSE_BUTTONS_VISIBLE.equals(evt.getPropertyName())) {
                tabbedPane.repaint();
            } else if (CONTENT_PANEL_BORDER.equals(evt.getPropertyName())) {
                contentPanelBorder = PropertyGetter.get(Border.class, tabbedPane, CONTENT_PANEL_BORDER, (String) null);
                tabbedPane.repaint();
            } else if (TAB_FACTORY.equals(evt.getPropertyName())) {
                tabFactory = PropertyGetter.get(ITabFactory.class, tabbedPane, TAB_FACTORY);
            } else if (DND_POLICY.equals(evt.getPropertyName())) {
                dndPolicy = PropertyGetter.get(ITabbedPaneDndPolicy.class, tabbedPane, DND_POLICY);
            } else if (TAB_DROP_FAILURE_HANDLER.equals(evt.getPropertyName())) {
                tabDropFailureHandler = PropertyGetter.get(ITabDropFailureHandler.class, tabbedPane, TAB_DROP_FAILURE_HANDLER);
            } else if (FLOATING_TAB_HANDLER.equals(evt.getPropertyName())) {
                floatingTabHandler = PropertyGetter.get(IFloatingTabHandler.class, tabbedPane, FLOATING_TAB_HANDLER);
            } else if (ANIMATION_FACTOR.equals(evt.getPropertyName())) {
                animFactor = PropertyGetter.get(Double.class, tabbedPane, ANIMATION_FACTOR, DEFAULT_ANIMATION_FACTOR);
            } else if (USE_UNIFORM_WIDTH.equals(evt.getPropertyName())) {
                useUniformWidth = PropertyGetter.get(Boolean.class, tabbedPane, USE_UNIFORM_WIDTH, DEFAULT_USE_UNIFORM_WIDTH);
                tabbedPane.invalidate();
                tabbedPane.validate();
            }
        }

        @Override
        public void stateChanged(final ChangeEvent e) {
            if (disable) {
                return;
            }
            setSelectedTabInternal(contentMap.get(tabbedPane.getSelectedComponent()));
        }

        @Override
        public void componentAdded(final ContainerEvent e) {
            if (disable) {
                return;
            }
            updateTabs();
        }

        @Override
        public void componentRemoved(final ContainerEvent e) {
            if (disable) {
                return;
            }
            updateMnemonics();
            updateTabs();
        }

        @Override
        public void focusGained(final FocusEvent e) {
            tabbedPane.repaint();
        }

        @Override
        public void focusLost(final FocusEvent e) {
            tabbedPane.repaint();
        }
    }

    private final class Actions extends AbstractAction {
        final static String NEXT = "navigateNext";
        final static String PREVIOUS = "navigatePrevious";
        final static String RIGHT = "navigateRight";
        final static String LEFT = "navigateLeft";
        final static String UP = "navigateUp";
        final static String DOWN = "navigateDown";
        final static String PAGE_UP = "navigatePageUp";
        final static String PAGE_DOWN = "navigatePageDown";
        final static String REQUEST_FOCUS = "requestFocus";
        final static String REQUEST_FOCUS_FOR_VISIBLE = "requestFocusForVisibleComponent";
        final static String SET_SELECTED = "setSelectedIndex";
        final static String SELECT_FOCUSED = "selectTabWithFocus";
        final static String SCROLL_FORWARD = "scrollTabsForwardAction";
        final static String SCROLL_BACKWARD = "scrollTabsBackwardAction";

        private final String key;

        Actions(final String key){
            this.key = key;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            final JTabbedPane pane = (JTabbedPane) e.getSource();

            switch (key) {
                case REQUEST_FOCUS:
                    pane.requestFocus();
                    break;
                case REQUEST_FOCUS_FOR_VISIBLE:
                    requestFocusForVisibleComponent();
                    break;
                case SET_SELECTED:
                    final String command = e.getActionCommand();

                    if (command != null && command.length() > 0) {
                        int mnemonic = e.getActionCommand().charAt(0);
                        if (mnemonic >= 'a' && mnemonic <= 'z') {
                            mnemonic -= ('a' - 'A');
                        }
                        final Integer index = mnemonicToIndexMap.get(mnemonic);
                        if (index != null && pane.isEnabledAt(index)) {
                            pane.setSelectedIndex(index);
                        }
                    }
            }
        }
    }
}
