/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.sexydock.tabs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.sexydock.tabs.jhrome.JhromeTabUI;

import javax.swing.*;
import java.awt.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class Tab extends JComponent {
    private static final String uiClassId = "TabUI";

    private static final long serialVersionUID = 5209596149902613716L;

    static {
        UIManager.getDefaults().put(uiClassId, JhromeTabUI.class.getName());
    }

    private String title;
    private Icon icon;
    private Component tabComponent;
    private int mnemonic = '\0';
    private int displayedMnemonicIndex = -1;
    private boolean rollover;
    private boolean selected;
    private Component content;

    public Tab() {
        this(null, null);
    }

    public Tab(final String title) {
        this(title, null);
    }

    public Tab(final String title, final Component content) {
        setTitle(title);
        setContent(content);

        updateUI();
    }

    private static boolean equals(final Object o1, final Object o2) {
        return (o1 == o2) || (o1 != null && o1.equals(o2)) || (o2 != null && o2.equals(o1));
    }

    public void setRollover(final boolean rollover) {
        if (this.rollover != rollover) {
            this.rollover = rollover;
            firePropertyChange("rollover", !rollover, rollover);
        }
    }

    public void setSelected(final boolean selected) {
        if (this.selected != selected) {
            this.selected = selected;
            firePropertyChange("selected", !selected, selected);
        }
    }

    public TabUI getUI() {
        return (TabUI) ui;
    }

    public void setUI(final TabUI ui) {
        super.setUI(ui);
    }

    public void setTitle(final String title) {
        if (!equals(title, this.title)) {
            final String oldValue = this.title;
            this.title = title;
            firePropertyChange("title", oldValue, title);
        }
    }

    public void setTabComponent(final Component overrideTitle) {
        if (this.tabComponent != overrideTitle) {
            final Component oldValue = this.tabComponent;
            this.tabComponent = overrideTitle;
            firePropertyChange("overrideTitle", oldValue, overrideTitle);
        }
    }

    public void setIcon(final Icon icon) {
        if (this.icon != icon) {
            final Icon oldValue = this.icon;
            this.icon = icon;
            firePropertyChange("icon", oldValue, icon);
        }
    }

    public void setMnemonic(final int mnemonic) {
        if (this.mnemonic != mnemonic) {
            final int oldValue = this.mnemonic;
            this.mnemonic = mnemonic;
            firePropertyChange("mnemonic", oldValue, mnemonic);
        }
    }

    public void setDisplayedMnemonicIndex(final int displayedMnemonic) {
        if (this.displayedMnemonicIndex != displayedMnemonic) {
            final int oldValue = this.displayedMnemonicIndex;
            this.displayedMnemonicIndex = displayedMnemonic;
            firePropertyChange("displayedMnemonic", oldValue, displayedMnemonic);
        }
    }

    public Component getRenderer() {
        return this;
    }

    /**
     * Specifies where the user can click and drag this jhromeTab. This way the jhromeTab component can behave as though it has a non-rectangular shape.
     *
     * @param p the point at which the user pressed the mouse, in the renderer's coordinate system.
     * @return {@code true} if the user can drag the jhromeTab after dragging from {@code p}. NOTE: must return {@code false} for points over the close button
     * and other operable components, or the user will be able to drag the jhromeTab from these points!
     */
    public boolean isDraggableAt(final Point p) {
        return getUI().isDraggableAt(this, p);
    }

    /**
     * Specifies where the user can click and select this jhromeTab. This way the jhromeTab component can behave as though it has a non-rectangular shape.
     *
     * @param p the point at which the user pressed the mouse, in the renderer's coordinate system.
     * @return {@code true} if the user can select the jhromeTab by pressing the mouse at {@code p}. NOTE: must return {@code false} for points over the close
     * button and other operable components, or the user will be able to select the jhromeTab by clicking these components!
     */
    public boolean isSelectableAt(final Point p) {
        return getUI().isSelectableAt(this, p);
    }

    /**
     * Specifies where the jhromeTab is "hoverable." If the user moves the mouse over the hoverable areas of this jhromeTab,
     * {@link JTabbedPane} will set it to
     * the rollover state.
     *
     * @param p the point the user moved the mouse over, in the renderer's coordinate system.
     * @return {@code true} if the jhromeTab should be set to the rollover state when the user moves the mouse over {@code p}.
     */
    public boolean isHoverableAt(final Point p) {
        return getUI().isHoverableAt(this, p);
    }

    @Override
    public String getUIClassID() {
        return uiClassId;
    }

    @Override
    public void updateUI() {
        setUI((TabUI) UIManager.getUI(this));
    }
}
