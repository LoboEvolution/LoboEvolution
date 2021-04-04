/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Oct 23, 2005
 */
package org.loboevolution.html.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.JComponent;

import org.loboevolution.html.AlignValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

/**
 * <p>Abstract BaseControl class.</p>
 *
 *
 *
 */
public abstract class BaseControl extends JComponent implements UIControl {
	private static final Logger logger = Logger.getLogger(BaseControl.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Constant ZERO_DIMENSION */
	protected static final Dimension ZERO_DIMENSION = new Dimension(0, 0);
	protected final HTMLElementImpl controlElement;
	protected RUIControl ruicontrol;

	/**
	 * <p>Constructor for BaseControl.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public BaseControl(HTMLElementImpl modelNode) {
		this.controlElement = modelNode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.UIControl#getBackgroundColor()
	 */
	/** {@inheritDoc} */
	@Override
	public Color getBackgroundColor() {
		return getBackground();
	}

	/** {@inheritDoc} */
	@Override
	public Component getComponent() {
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public int getVAlign() {
		return AlignValues.BASELINE.getValue();
	}

	/**
	 * Method invoked when image changes size. It's expected to be called outside
	 * the GUI thread.
	 */
	protected void invalidateAndRepaint() {
		final RUIControl rc = this.ruicontrol;
		if (rc == null) {
			logger.severe("invalidateAndPaint(): RUIControl not set.");
			return;
		}
		if (rc.isValid()) {
			rc.relayout();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void reset(int availWidth, int availHeight) {
	}

	/** {@inheritDoc} */
	@Override
	public void setRUIControl(RUIControl ruicontrol) {
		this.ruicontrol = ruicontrol;
	}
	
	/**
	 * <p>getRUIControl.</p>
	 *
	 * @return a {@link org.loboevolution.html.control.RUIControl} object.
	 */
	public RUIControl getRUIControl() {
		return ruicontrol;
	}
}
