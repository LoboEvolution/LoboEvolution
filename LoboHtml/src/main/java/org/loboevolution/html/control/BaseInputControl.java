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
package org.loboevolution.html.control;

import java.awt.ComponentOrientation;
import java.awt.Graphics;
import java.io.File;

import org.loboevolution.html.dom.domimpl.HTMLBaseInputElement;
import org.loboevolution.html.dom.domimpl.InputContext;
import org.loboevolution.html.renderer.RElement;
import org.loboevolution.html.renderer.RenderableSpot;
import org.loboevolution.html.style.HtmlValues;

public abstract class BaseInputControl extends BaseControl implements InputContext {

	private static final long serialVersionUID = 1L;
	protected int size = -1;

	protected String value;

	public BaseInputControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		setOpaque(false);
	}

	@Override
	public void blur() {
	}

	@Override
	public void click() {
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
	public int getCols() {
		return 0;
	}

	@Override
	public int getControlSize() {
		return this.size;
	}

	@Override
	public boolean getDisabled() {
		return !isEnabled();
	}

	@Override
	public File getFileValue() {
		// For file inputs
		return null;
	}

	@Override
	public int getMaxLength() {
		return 0;
	}

	public boolean getMultiple() {
		// For selects
		return false;
	}

	@Override
	public boolean getReadOnly() {
		return false;
	}

	@Override
	public int getRows() {
		return 0;
	}

	@Override
	public int getSelectedIndex() {
		// For selects
		return -1;
	}

	@Override
	public int getTabIndex() {
		return 0;
	}

	@Override
	public int getVAlign() {
		return RElement.VALIGN_ABSBOTTOM;
	}

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

	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		return inSelection;
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		final String sizeText = this.controlElement.getAttribute("size");
		this.size = HtmlValues.getPixelSize(sizeText, null, 0);
	}

	@Override
	public void select() {
	}

	@Override
	public void setChecked(boolean checked) {
	}

	@Override
	public void setCols(int cols) {
	}

	@Override
	public void setControlSize(int size) {
		this.size = size;
		invalidate();
	}

	@Override
	public void setDisabled(boolean disabled) {
		setEnabled(!disabled);
	}

	@Override
	public void setMaxLength(int maxLength) {
	}

	public void setMultiple(boolean value) {
		// For selects
	}

	@Override
	public void setReadOnly(boolean readOnly) {
	}

	@Override
	public void setRows(int rows) {
	}

	@Override
	public void setSelectedIndex(int value) {
		// For selects
	}

	@Override
	public void setTabIndex(int tabIndex) {
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void setVisibleSize(int value) {
		// For selects
	}
	
	@Override
	public void resetInput() {
	}
	
    protected ComponentOrientation direction(String dir) {
        if ("ltr".equalsIgnoreCase(dir)) {
            return ComponentOrientation.LEFT_TO_RIGHT;
        } else if ("rtl".equalsIgnoreCase(dir)) {
            return ComponentOrientation.RIGHT_TO_LEFT;
        } else {
            return ComponentOrientation.UNKNOWN;
        }
    }
}
