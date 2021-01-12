/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
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
 * The Class SwingTasks.
 *
 * @author utente
 * @version $Id: $Id
 */
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
	public static Box createGroupBox(int orientation, String title) {
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
		final Dimension max = pref;
		return new Box.Filler(min, pref, max);
	}

	/**
	 * Creates the vertical fill.
	 *
	 * @return the component
	 */
	public static Component createVerticalFill() {
		final Dimension min = new Dimension(0, 0);
		final Dimension pref = new Dimension(0, Short.MAX_VALUE);
		final Dimension max = pref;
		return new Box.Filler(min, pref, max);
	}

	/**
	 * Sets the enabled recursive.
	 *
	 * @param component the component
	 * @param enabled   the enabled
	 */
	private static void setEnabledRecursive(JComponent component, boolean enabled) {
		component.setEnabled(enabled);
		final int count = component.getComponentCount();
		for (int i = 0; i < count; i++) {
			final Component child = component.getComponent(i);
			if (child instanceof JComponent) {
				final JComponent jchild = (JComponent) child;
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
	public static void setNestedEnabled(JComponent component, boolean enabled) {
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
