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
 * Created on Jan 15, 2006
 */
package org.lobobrowser.html.renderer;

import java.awt.Graphics;
import java.io.File;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.dombl.InputContext;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;

public abstract class BaseInputControl extends BaseControl implements InputContext {

	private static final long serialVersionUID = 1L;
	protected String value;

	public BaseInputControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		this.setOpaque(false);
	}

	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		String sizeText = this.controlElement.getAttribute(HtmlAttributeProperties.SIZE);
		if (sizeText != null) {
			try {
				this.size = Integer.parseInt(sizeText);
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}
	}

	public int getVAlign() {
		return RElement.VALIGN_ABSBOTTOM;
	}

	protected int size = -1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#blur()
	 */
	public void blur() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#click()
	 */
	public void click() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#focus()
	 */
	public void focus() {
		this.requestFocus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#getChecked()
	 */
	public boolean getChecked() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#getDisabled()
	 */
	public boolean getDisabled() {
		return !this.isEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#getMaxLength()
	 */
	public int getMaxLength() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#getReadOnly()
	 */
	public boolean getReadOnly() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#getTabIndex()
	 */
	public int getTabIndex() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#getValue()
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Returns <code>null</code>. It should be overridden by controls that
	 * support multiple values.
	 */
	public String[] getValues() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#select()
	 */
	public void select() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#setChecked(boolean)
	 */
	public void setChecked(boolean checked) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#setDisabled(boolean)
	 */
	public void setDisabled(boolean disabled) {
		this.setEnabled(!disabled);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#setMaxLength(int)
	 */
	public void setMaxLength(int maxLength) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#setReadOnly(boolean)
	 */
	public void setReadOnly(boolean readOnly) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#setSize(int)
	 */
	public void setControlSize(int size) {
		this.size = size;
		this.invalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#setTabIndex(int)
	 */
	public void setTabIndex(int tabIndex) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#setValue(java.lang.String)
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#getTextSize()
	 */
	public int getControlSize() {
		return this.size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#getCols()
	 */
	public int getCols() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#getRows()
	 */
	public int getRows() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#setCols(int)
	 */
	public void setCols(int cols) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.dombl.InputContext#setRows(int)
	 */
	public void setRows(int rows) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.render.UIControl#paintSelection(java.awt.Graphics,
	 * boolean, org.lobobrowser.html.render.RenderablePoint,
	 * org.lobobrowser.html.render.RenderablePoint)
	 */
	public boolean paintSelection(Graphics g, boolean inSelection,
			RenderableSpot startPoint, RenderableSpot endPoint) {
		return inSelection;
	}

	public boolean getMultiple() {
		// For selects
		return false;
	}

	public int getSelectedIndex() {
		// For selects
		return -1;
	}

	public int getVisibleSize() {
		// For selects
		return 0;
	}

	public void setMultiple(boolean value) {
		// For selects
	}

	public void setSelectedIndex(int value) {
		// For selects
	}

	public void setVisibleSize(int value) {
		// For selects
	}

	public File[] getFileValue() {
		// For file inputs
		return null;
	}
}
