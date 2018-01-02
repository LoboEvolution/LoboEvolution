/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * The Class SwingTasks.
 */
public class SwingTasks {

	/** The Constant NESTED_ENABLING. */
	private static final String NESTED_ENABLING = "$nested.enabling";

	/**
	 * Sets the nested enabled.
	 *
	 * @param component
	 *            the component
	 * @param enabled
	 *            the enabled
	 */
	public static void setNestedEnabled(JComponent component, boolean enabled) {
		Boolean nestedEnabling = (Boolean) component.getClientProperty(NESTED_ENABLING);
		if (nestedEnabling == null || nestedEnabling.booleanValue() != enabled) {
			component.putClientProperty(NESTED_ENABLING, enabled);
			Container parent = component.getParent();
			if (parent == null || !enabled || parent.isEnabled()) {
				setEnabledRecursive(component, enabled);
			}
		}
	}

	/**
	 * Sets the enabled recursive.
	 *
	 * @param component
	 *            the component
	 * @param enabled
	 *            the enabled
	 */
	private static void setEnabledRecursive(JComponent component, boolean enabled) {
		component.setEnabled(enabled);
		int count = component.getComponentCount();
		for (int i = 0; i < count; i++) {
			Component child = component.getComponent(i);
			if (child instanceof JComponent) {
				JComponent jchild = (JComponent) child;
				if (enabled) {
					Boolean nestedEnabling = (Boolean) jchild.getClientProperty(NESTED_ENABLING);
					if (nestedEnabling == null || nestedEnabling.booleanValue()) {
						setEnabledRecursive(jchild, true);
					}
				} else {
					setEnabledRecursive(jchild, false);
				}
			}
		}
	}

	/**
	 * Creates the vertical fill.
	 *
	 * @return the component
	 */
	public static Component createVerticalFill() {
		Dimension min = new Dimension(0, 0);
		Dimension pref = new Dimension(0, Short.MAX_VALUE);
		Dimension max = pref;
		return new Box.Filler(min, pref, max);
	}

	/**
	 * Creates the horizontal fill.
	 *
	 * @return the component
	 */
	public static Component createHorizontalFill() {
		Dimension min = new Dimension(0, 0);
		Dimension pref = new Dimension(Short.MAX_VALUE, 0);
		Dimension max = pref;
		return new Box.Filler(min, pref, max);
	}

	/**
	 * Gets the frame.
	 *
	 * @param component
	 *            the component
	 * @return the frame
	 */
	public static Frame getFrame(Component component) {
		Container ancestor = component.getParent();
		while (ancestor != null && !(ancestor instanceof Frame)) {
			ancestor = ancestor.getParent();
		}
		return (Frame) ancestor;
	}

	/**
	 * Gets the dialog.
	 *
	 * @param component
	 *            the component
	 * @return the dialog
	 */
	public static Dialog getDialog(Component component) {
		Container ancestor = component.getParent();
		while (ancestor != null && !(ancestor instanceof Dialog)) {
			ancestor = ancestor.getParent();
		}
		return (Dialog) ancestor;
	}

	/**
	 * Creates the group box.
	 *
	 * @param orientation
	 *            the orientation
	 * @param title
	 *            the title
	 * @return the box
	 */
	public static Box createGroupBox(int orientation, String title) {
		Box box = new Box(orientation);
		box.setBorder(new TitledBorder(new EtchedBorder(), title));
		return box;
	}
}
