/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.html;

import org.mozilla.javascript.Function;


/**
 * The Interface HTMLMarqueeElement.
 */
public interface HTMLMarqueeElement extends HTMLElement {
	
	/**
	 * Gets the behavior.
	 *
	 * @return the behavior
	 */
	// HTMLMarqueeElement
	public String getBehavior();

	/**
	 * Sets the behavior.
	 *
	 * @param behavior
	 *            the new behavior
	 */
	public void setBehavior(String behavior);

	/**
	 * Gets the bg color.
	 *
	 * @return the bg color
	 */
	public String getBgColor();

	/**
	 * Sets the bg color.
	 *
	 * @param bgColor
	 *            the new bg color
	 */
	public void setBgColor(String bgColor);

	/**
	 * Gets the direction.
	 *
	 * @return the direction
	 */
	public String getDirection();

	/**
	 * Sets the direction.
	 *
	 * @param direction
	 *            the new direction
	 */
	public void setDirection(String direction);

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public String getHeight();

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the new height
	 */
	public void setHeight(String height);

	/**
	 * Gets the hspace.
	 *
	 * @return the hspace
	 */
	public int getHspace();

	/**
	 * Sets the hspace.
	 *
	 * @param hspace
	 *            the new hspace
	 */
	public void setHspace(int hspace);

	/**
	 * Gets the loop.
	 *
	 * @return the loop
	 */
	public int getLoop();

	/**
	 * Sets the loop.
	 *
	 * @param loop
	 *            the new loop
	 */
	public void setLoop(int loop);

	/**
	 * Gets the scroll amount.
	 *
	 * @return the scroll amount
	 */
	public int getScrollAmount();

	/**
	 * Sets the scroll amount.
	 *
	 * @param scrollAmount
	 *            the new scroll amount
	 */
	public void setScrollAmount(int scrollAmount);

	/**
	 * Gets the scroll delay.
	 *
	 * @return the scroll delay
	 */
	public int getScrollDelay();

	/**
	 * Sets the scroll delay.
	 *
	 * @param scrollDelay
	 *            the new scroll delay
	 */
	public void setScrollDelay(int scrollDelay);

	/**
	 * Gets the true speed.
	 *
	 * @return the true speed
	 */
	public boolean getTrueSpeed();

	/**
	 * Sets the true speed.
	 *
	 * @param trueSpeed
	 *            the new true speed
	 */
	public void setTrueSpeed(boolean trueSpeed);

	/**
	 * Gets the vspace.
	 *
	 * @return the vspace
	 */
	public int getVspace();

	/**
	 * Sets the vspace.
	 *
	 * @param vspace
	 *            the new vspace
	 */
	public void setVspace(int vspace);

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public String getWidth();

	/**
	 * Sets the width.
	 *
	 * @param width
	 *            the new width
	 */
	public void setWidth(String width);

	/**
	 * Gets the onbounce.
	 *
	 * @return the onbounce
	 */
	public Function getOnbounce();

	/**
	 * Sets the onbounce.
	 *
	 * @param onbounce
	 *            the new onbounce
	 */
	public void setOnbounce(Function onbounce);

	/**
	 * Gets the onfinish.
	 *
	 * @return the onfinish
	 */
	public Function getOnfinish();

	/**
	 * Sets the onfinish.
	 *
	 * @param onfinish
	 *            the new onfinish
	 */
	public void setOnfinish(Function onfinish);

	/**
	 * Gets the onstart.
	 *
	 * @return the onstart
	 */
	public Function getOnstart();

	/**
	 * Sets the onstart.
	 *
	 * @param onstart
	 *            the new onstart
	 */
	public void setOnstart(Function onstart);

	/**
	 * Start.
	 */
	public void start();

	/**
	 * Stop.
	 */
	public void stop();
}
