/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.w3c.html.HTMLMarqueeElement;
import org.mozilla.javascript.Function;

/**
 * The Class HTMLMarqueeElementImpl.
 */
public class HTMLMarqueeElementImpl extends HTMLElementImpl implements HTMLMarqueeElement {

	/**
	 * Instantiates a new HTML marquee element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLMarqueeElementImpl(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#getBehavior()
	 */
	@Override
	public String getBehavior() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLMarqueeElement#setBehavior(java.lang.String)
	 */
	@Override
	public void setBehavior(String behavior) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#getBgColor()
	 */
	@Override
	public String getBgColor() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLMarqueeElement#setBgColor(java.lang.String)
	 */
	@Override
	public void setBgColor(String bgColor) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#getDirection()
	 */
	@Override
	public String getDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#setDirection(java.lang.
	 * String)
	 */
	@Override
	public void setDirection(String direction) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#getHeight()
	 */
	@Override
	public String getHeight() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLMarqueeElement#setHeight(java.lang.String)
	 */
	@Override
	public void setHeight(String height) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#getHspace()
	 */
	@Override
	public int getHspace() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#setHspace(int)
	 */
	@Override
	public void setHspace(int hspace) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#getLoop()
	 */
	@Override
	public int getLoop() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#setLoop(int)
	 */
	@Override
	public void setLoop(int loop) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#getScrollAmount()
	 */
	@Override
	public int getScrollAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#setScrollAmount(int)
	 */
	@Override
	public void setScrollAmount(int scrollAmount) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#getScrollDelay()
	 */
	@Override
	public int getScrollDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#setScrollDelay(int)
	 */
	@Override
	public void setScrollDelay(int scrollDelay) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#getTrueSpeed()
	 */
	@Override
	public boolean getTrueSpeed() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#setTrueSpeed(java.lang.
	 * String)
	 */
	@Override
	public void setTrueSpeed(boolean trueSpeed) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#getVspace()
	 */
	@Override
	public int getVspace() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#setVspace(int)
	 */
	@Override
	public void setVspace(int vspace) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#getWidth()
	 */
	@Override
	public String getWidth() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLMarqueeElement#setWidth(java.lang.String)
	 */
	@Override
	public void setWidth(String width) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#start()
	 */
	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLMarqueeElement#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnbounce() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnbounce(Function onbounce) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnfinish() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnfinish(Function onfinish) {
		// TODO Auto-generated method stub

	}

	@Override
	public Function getOnstart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnstart(Function onstart) {
		// TODO Auto-generated method stub

	}
}
