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
 * Created on Oct 23, 2005
 */
package org.loboevolution.html.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.html.HtmlAttributeProperties;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderer.RElement;
import org.loboevolution.html.renderer.UIControl;

/**
 * The Class BaseControl.
 */
public abstract class BaseControl extends JComponent implements UIControl, HtmlAttributeProperties {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	protected static final Logger logger = LogManager.getLogger(BaseControl.class.getName());

	/** The Constant ZERO_DIMENSION. */
	protected static final Dimension ZERO_DIMENSION = new Dimension(0, 0);

	/** The control element. */
	protected final HTMLElementImpl controlElement;

	/** The ruicontrol. */
	protected RUIControl ruicontrol;

	/**
	 * Instantiates a new base control.
	 *
	 * @param modelNode
	 *            the model node
	 */
	public BaseControl(HTMLElementImpl modelNode) {
		this.controlElement = modelNode;
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public void setRUIControl(RUIControl ruicontrol) {
		this.ruicontrol = ruicontrol;
	}

	@Override
	public int getVAlign() {
		return RElement.VALIGN_BASELINE;
	}

	/**
	 * Method invoked when image changes size. It's expected to be called
	 * outside the GUI thread.
	 */
	protected void invalidateAndRepaint() {
		RUIControl rc = this.ruicontrol;
		if (rc == null) {
			logger.error("invalidateAndPaint(): RUIControl not set.");
			return;
		}
		if (rc.isValid()) {
			rc.relayout();
		}
	}

	@Override
	public Color getBackgroundColor() {
		return this.getBackground();
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		// Method not implemented
	}
}
