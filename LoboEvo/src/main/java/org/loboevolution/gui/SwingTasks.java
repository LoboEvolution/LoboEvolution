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

package org.loboevolution.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * The Class SwingTasks. */
public class SwingTasks {

	/** The Constant NESTED_ENABLING. */
	private static final String NESTED_ENABLING = "$nested.enabling";

	/**
	 * Creates the group box.
	 *
	 * @param orientation the orientation
	 * @param title       the title
	 * @return the box
	 */
	public static Box createGroupBox(final int orientation, final String title) {
		final Box box = new Box(orientation);
		box.setBorder(new TitledBorder(new EtchedBorder(), title));
		return box;
	}

	/**
	 * Creates the horizontal fill.
	 *
	 * @return the component
	 */
	public static Component createHorizontalFill() {
		final Dimension min = new Dimension(0, 0);
		final Dimension pref = new Dimension(Short.MAX_VALUE, 0);
        return new Box.Filler(min, pref, pref);
	}

	/**
	 * Creates the vertical fill.
	 *
	 * @return the component
	 */
	public static Component createVerticalFill() {
		final Dimension min = new Dimension(0, 0);
		final Dimension pref = new Dimension(0, Short.MAX_VALUE);
        return new Box.Filler(min, pref, pref);
	}

	/**
	 * Sets the enabled recursive.
	 *
	 * @param component the component
	 * @param enabled   the enabled
	 */
	private static void setEnabledRecursive(final JComponent component, final boolean enabled) {
		component.setEnabled(enabled);
		final int count = component.getComponentCount();
		for (int i = 0; i < count; i++) {
			final Component child = component.getComponent(i);
			if (child instanceof JComponent jchild) {
                if (enabled) {
					final Boolean nestedEnabling = (Boolean) jchild.getClientProperty(NESTED_ENABLING);
					if (nestedEnabling == null || nestedEnabling) {
						setEnabledRecursive(jchild, true);
					}
				} else {
					setEnabledRecursive(jchild, false);
				}
			}
		}
	}

	/**
	 * Sets the nested enabled.
	 *
	 * @param component the component
	 * @param enabled   the enabled
	 */
	public static void setNestedEnabled(final JComponent component, final boolean enabled) {
		final Boolean nestedEnabling = (Boolean) component.getClientProperty(NESTED_ENABLING);
		if (nestedEnabling == null || nestedEnabling != enabled) {
			component.putClientProperty(NESTED_ENABLING, enabled);
			final Container parent = component.getParent();
			if (parent == null || !enabled || parent.isEnabled()) {
				setEnabledRecursive(component, enabled);
			}
		}
	}
}
