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

import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.html.domimpl.InputContext;

abstract class BaseInputControl extends BaseControl implements InputContext {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int size = -1;

	protected String value;

	public BaseInputControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		setOpaque(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#blur()
	 */
	@Override
	public void blur() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#click()
	 */
	@Override
	public void click() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#focus()
	 */
	@Override
	public void focus() {
		this.requestFocus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#getChecked()
	 */
	@Override
	public boolean getChecked() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#getCols()
	 */
	@Override
	public int getCols() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#getTextSize()
	 */
	@Override
	public int getControlSize() {
		return this.size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#getDisabled()
	 */
	@Override
	public boolean getDisabled() {
		return !isEnabled();
	}

	@Override
	public File getFileValue() {
		// For file inputs
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#getMaxLength()
	 */
	@Override
	public int getMaxLength() {
		return 0;
	}

	public boolean getMultiple() {
		// For selects
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#getReadOnly()
	 */
	@Override
	public boolean getReadOnly() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#getRows()
	 */
	@Override
	public int getRows() {
		return 0;
	}

	@Override
	public int getSelectedIndex() {
		// For selects
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#getTabIndex()
	 */
	@Override
	public int getTabIndex() {
		return 0;
	}

	@Override
	public int getVAlign() {
		return RElement.VALIGN_ABSBOTTOM;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#getValue()
	 */
	@Override
	public String getValue() {
		return this.value;
	}

	/**
	 * Returns <code>null</code>. It should be overridden by controls that support
	 * multiple values.
	 */
	@Override
	public String[] getValues() {
		return null;
	}

	@Override
	public int getVisibleSize() {
		// For selects
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.UIControl#paintSelection(java.awt.Graphics,
	 * boolean, org.xamjwg.html.renderer.RenderablePoint,
	 * org.xamjwg.html.renderer.RenderablePoint)
	 */
	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		return inSelection;
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		final String sizeText = this.controlElement.getAttribute("size");
		if (sizeText != null) {
			try {
				this.size = Integer.parseInt(sizeText);
			} catch (final NumberFormatException nfe) {
				// ignore
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#select()
	 */
	@Override
	public void select() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#setChecked(boolean)
	 */
	@Override
	public void setChecked(boolean checked) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#setCols(int)
	 */
	@Override
	public void setCols(int cols) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#setSize(int)
	 */
	@Override
	public void setControlSize(int size) {
		this.size = size;
		invalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#setDisabled(boolean)
	 */
	@Override
	public void setDisabled(boolean disabled) {
		setEnabled(!disabled);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#setMaxLength(int)
	 */
	@Override
	public void setMaxLength(int maxLength) {
	}

	public void setMultiple(boolean value) {
		// For selects
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#setRows(int)
	 */
	@Override
	public void setRows(int rows) {
	}

	@Override
	public void setSelectedIndex(int value) {
		// For selects
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tabIndex) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.InputContext#setValue(java.lang.String)
	 */
	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void setVisibleSize(int value) {
		// For selects
	}
}
