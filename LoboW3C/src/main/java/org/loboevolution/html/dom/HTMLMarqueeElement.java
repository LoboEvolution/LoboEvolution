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

package org.loboevolution.html.dom;


import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.events.EventListener;

/**
 * Provides methods to manipulate &lt;marquee&gt; elements.
 *
 *
 *
 */
public interface HTMLMarqueeElement extends HTMLElement {
   

    /**
     * <p>getBehavior.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getBehavior();

    
    /**
     * <p>setBehavior.</p>
     *
     * @param behavior a {@link java.lang.String} object.
     */
    void setBehavior(String behavior);

    /**
     * <p>getBgColor.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getBgColor();

    
    /**
     * <p>setBgColor.</p>
     *
     * @param bgColor a {@link java.lang.String} object.
     */
    void setBgColor(String bgColor);

    /**
     * <p>getDirection.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getDirection();

    
    /**
     * <p>setDirection.</p>
     *
     * @param direction a {@link java.lang.String} object.
     */
    void setDirection(String direction);

    /**
     * <p>getHeight.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getHeight();

    
    /**
     * <p>setHeight.</p>
     *
     * @param height a {@link java.lang.String} object.
     */
    void setHeight(String height);

    /**
     * <p>getHspace.</p>
     *
     * @return a double.
     */
    @Deprecated
    double getHspace();

    
    /**
     * <p>setHspace.</p>
     *
     * @param hspace a double.
     */
    void setHspace(double hspace);

    /**
     * <p>getLoop.</p>
     *
     * @return a double.
     */
    @Deprecated
    double getLoop();

    
    /**
     * <p>setLoop.</p>
     *
     * @param loop a double.
     */
    void setLoop(double loop);

    /**
     * <p>getOnbounce.</p>
     *
     * @return a {@link org.loboevolution.html.node.events.EventListener} object.
     */
    @Deprecated
    EventListener<Event> getOnbounce();

    /**
     * <p>setOnbounce.</p>
     *
     * @param onbounce a {@link org.loboevolution.html.node.events.EventListener} object.
     */
    @Deprecated
    void setOnbounce(EventListener<Event> onbounce);


    /**
     * <p>getOnfinish.</p>
     *
     * @return a {@link org.loboevolution.html.node.events.EventListener} object.
     */
    @Deprecated
    EventListener<Event> getOnfinish();

    /**
     * <p>setOnfinish.</p>
     *
     * @param onfinish a {@link org.loboevolution.html.node.events.EventListener} object.
     */
    @Deprecated
    void setOnfinish(EventListener<Event> onfinish);

    /**
     * <p>getOnstart.</p>
     *
     * @return a {@link org.loboevolution.html.node.events.EventListener} object.
     */
    @Deprecated
    EventListener<Event> getOnstart();

    /**
     * <p>setOnstart.</p>
     *
     * @param onstart a {@link org.loboevolution.html.node.events.EventListener} object.
     */
    @Deprecated
    void setOnstart(EventListener<Event> onstart);


    /**
     * <p>getScrollAmount.</p>
     *
     * @return a double.
     */
    @Deprecated
    double getScrollAmount();

    
    /**
     * <p>setScrollAmount.</p>
     *
     * @param scrollAmount a double.
     */
    void setScrollAmount(double scrollAmount);

    /**
     * <p>getScrollDelay.</p>
     *
     * @return a double.
     */
    @Deprecated
    double getScrollDelay();

    
    /**
     * <p>setScrollDelay.</p>
     *
     * @param scrollDelay a double.
     */
    void setScrollDelay(double scrollDelay);

    /**
     * <p>isTrueSpeed.</p>
     *
     * @return a boolean.
     */
    @Deprecated
    boolean isTrueSpeed();

    
    /**
     * <p>setTrueSpeed.</p>
     *
     * @param trueSpeed a boolean.
     */
    void setTrueSpeed(boolean trueSpeed);

    /**
     * <p>getVspace.</p>
     *
     * @return a double.
     */
    @Deprecated
    double getVspace();

    
    /**
     * <p>setVspace.</p>
     *
     * @param vspace a double.
     */
    void setVspace(double vspace);

    /**
     * <p>getWidth.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    @Deprecated
    String getWidth();

    
    /**
     * <p>setWidth.</p>
     *
     * @param width a {@link java.lang.String} object.
     */
    void setWidth(String width);

    /**
     * <p>start.</p>
     */
    @Deprecated
    void start();

    /**
     * <p>stop.</p>
     */
    @Deprecated
    void stop();

}
