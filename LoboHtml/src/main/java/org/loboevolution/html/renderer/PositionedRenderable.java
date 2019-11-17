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
package org.loboevolution.html.renderer;

import java.awt.Graphics;

import org.loboevolution.html.dom.domimpl.ModelNode;

public class PositionedRenderable implements Renderable {
	
	public static final PositionedRenderable[] EMPTY_ARRAY = new PositionedRenderable[0];
	
	private BoundableRenderable renderable;
	
	private boolean verticalAlignable;
	
	private boolean isFloat;
	
	private boolean isFixed;
	
	private int ordinal;

	public PositionedRenderable() {

	}

	@Override
	public void paint(final Graphics g) {
		this.renderable.paintTranslated(g);
	}

	@Override
	public ModelNode getModelNode() {
		return this.renderable.getModelNode();
	}

	/**
	 * @return the renderable
	 */
	public BoundableRenderable getRenderable() {
		return renderable;
	}

	/**
	 * @return the verticalAlignable
	 */
	public boolean isVerticalAlignable() {
		return verticalAlignable;
	}

	/**
	 * @return the isFloat
	 */
	public boolean isFloat() {
		return isFloat;
	}

	/**
	 * @return the isFixed
	 */
	public boolean isFixed() {
		return isFixed;
	}

	/**
	 * @return the ordinal
	 */
	public int getOrdinal() {
		return ordinal;
	}

	/**
	 * @param renderable the renderable to set
	 */
	public void setRenderable(BoundableRenderable renderable) {
		this.renderable = renderable;
	}

	/**
	 * @param verticalAlignable the verticalAlignable to set
	 */
	public void setVerticalAlignable(boolean verticalAlignable) {
		this.verticalAlignable = verticalAlignable;
	}

	/**
	 * @param isFloat the isFloat to set
	 */
	public void setFloat(boolean isFloat) {
		this.isFloat = isFloat;
	}

	/**
	 * @param isFixed the isFixed to set
	 */
	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}

	/**
	 * @param ordinal the ordinal to set
	 */
	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
}