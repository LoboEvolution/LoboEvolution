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
 * 
 */
package org.loboevolution.html.control;

import java.awt.Graphics;
import java.io.File;

import org.loboevolution.html.dombl.InputContext;
import org.loboevolution.html.domimpl.HTMLBaseInputElement;
import org.loboevolution.html.renderer.RElement;
import org.loboevolution.html.renderer.RenderableSpot;
import org.loboevolution.html.style.HtmlLength;

/**
 * The Class BaseInputControl.
 */
public abstract class BaseInputControl extends BaseControl implements InputContext {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The value. */
	protected String value;

	/** The size. */
	protected int size = -1;

	/**
	 * Instantiates a new base input control.
	 *
	 * @param modelNode
	 *            the model node
	 */
	public BaseInputControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		this.setOpaque(false);
		String sizeText = this.controlElement.getAttribute(SIZE);
		if (sizeText != null) {
			setControlSize(new HtmlLength(sizeText).getLength(0));
		}
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		String sizeText = this.controlElement.getAttribute(SIZE);
		if (sizeText != null) {
			try {
				this.size = Integer.parseInt(sizeText);
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}
	}

	@Override
	public int getVAlign() {
		return RElement.VALIGN_ABSBOTTOM;
	}

	@Override
	public void blur() {
		// Method not implemented
	}

	@Override
	public void click() {
		// Method not implemented
	}

	@Override
	public void focus() {
		this.requestFocus();
	}

	@Override
	public boolean getChecked() {
		return false;
	}

	@Override
	public boolean getDisabled() {
		return !this.isEnabled();
	}

	@Override
	public int getMaxLength() {
		return 0;
	}

	@Override
	public boolean getReadOnly() {
		return false;
	}

	@Override
	public int getTabIndex() {
		return 0;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public String[] getValues() {
		return null;
	}

	@Override
	public void select() {
		// Method not implemented
	}

	@Override
	public void setChecked(boolean checked) {
		// Method not implemented
	}

	@Override
	public void setDisabled(boolean disabled) {
		this.setEnabled(!disabled);
	}

	@Override
	public void setMaxLength(int maxLength) {
		// Method not implemented
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		// Method not implemented
	}

	@Override
	public void setControlSize(int size) {
		this.size = size;
		this.invalidate();
	}

	@Override
	public void setTabIndex(int tabIndex) {
		// Method not implemented
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int getControlSize() {
		return this.size;
	}

	@Override
	public int getCols() {
		return 0;
	}

	@Override
	public int getRows() {
		return 0;
	}

	@Override
	public void setCols(int cols) {
		// Method not implemented
	}

	@Override
	public void setRows(int rows) {
		// Method not implemented
	}

	@Override
	public void setMin(int value) {
		// Method not implemented
	}

	@Override
	public int getMin() {
		return 0;
	}

	@Override
	public void setMax(int value) {
		// Method not implemented
	}

	@Override
	public int getMax() {
		return 0;
	}

	@Override
	public void setPattern(String value) {
		// Method not implemented
	}

	@Override
	public String getPattern() {
		return "";
	}

	/**
	 * Paint selection.
	 *
	 * @param g
	 *            the g
	 * @param inSelection
	 *            the in selection
	 * @param startPoint
	 *            the start point
	 * @param endPoint
	 *            the end point
	 * @return true, if successful
	 */
	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		return inSelection;
	}

	/**
	 * Gets the multiple.
	 *
	 * @return the multiple
	 */
	public boolean getMultiple() {
		return false;
	}

	@Override
	public int getSelectedIndex() {
		return -1;
	}

	@Override
	public int getVisibleSize() {
		return 0;
	}

	/**
	 * Sets the multiple.
	 *
	 * @param value
	 *            the new multiple
	 */
	public void setMultiple(boolean value) {
		// Method not implemented
	}

	@Override
	public void setSelectedIndex(int value) {
		// Method not implemented
	}

	@Override
	public void setVisibleSize(int value) {
		// Method not implemented
	}

	@Override
	public File[] getFileValue() {
		// For file inputs
		return null;
	}
}
