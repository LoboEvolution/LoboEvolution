/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.html.w3c;

/**
 * The public interface HTMLMarqueeElement.
 */
public interface HTMLMarqueeElement extends HTMLElement {
    // HTMLMarqueeElement
    /**
     * Gets the behavior.
     *
     * @return the behavior
     */
    String getBehavior();

    /**
     * Sets the behavior.
     *
     * @param behavior
     *            the new behavior
     */
    void setBehavior(String behavior);

    /**
     * Gets the bg color.
     *
     * @return the bg color
     */
    String getBgColor();

    /**
     * Sets the bg color.
     *
     * @param bgColor
     *            the new bg color
     */
    void setBgColor(String bgColor);

    /**
     * Gets the direction.
     *
     * @return the direction
     */
    String getDirection();

    /**
     * Sets the direction.
     *
     * @param direction
     *            the new direction
     */
    void setDirection(String direction);

    /**
     * Gets the height.
     *
     * @return the height
     */
    String getHeight();

    /**
     * Sets the height.
     *
     * @param height
     *            the new height
     */
    void setHeight(String height);

    /**
     * Gets the hspace.
     *
     * @return the hspace
     */
    int getHspace();

    /**
     * Sets the hspace.
     *
     * @param hspace
     *            the new hspace
     */
    void setHspace(int hspace);

    /**
     * Gets the loop.
     *
     * @return the loop
     */
    int getLoop();

    /**
     * Sets the loop.
     *
     * @param loop
     *            the new loop
     */
    void setLoop(int loop);

    /**
     * Gets the scroll amount.
     *
     * @return the scroll amount
     */
    int getScrollAmount();

    /**
     * Sets the scroll amount.
     *
     * @param scrollAmount
     *            the new scroll amount
     */
    void setScrollAmount(int scrollAmount);

    /**
     * Gets the scroll delay.
     *
     * @return the scroll delay
     */
    int getScrollDelay();

    /**
     * Sets the scroll delay.
     *
     * @param scrollDelay
     *            the new scroll delay
     */
    void setScrollDelay(int scrollDelay);

    /**
     * Gets the true speed.
     *
     * @return the true speed
     */
    String getTrueSpeed();

    /**
     * Sets the true speed.
     *
     * @param trueSpeed
     *            the new true speed
     */
    void setTrueSpeed(String trueSpeed);

    /**
     * Gets the vspace.
     *
     * @return the vspace
     */
    int getVspace();

    /**
     * Sets the vspace.
     *
     * @param vspace
     *            the new vspace
     */
    void setVspace(int vspace);

    /**
     * Gets the width.
     *
     * @return the width
     */
    String getWidth();

    /**
     * Sets the width.
     *
     * @param width
     *            the new width
     */
    void setWidth(String width);

    /**
     * Start.
     */
    void start();

    /**
     * Stop.
     */
    void stop();
}
