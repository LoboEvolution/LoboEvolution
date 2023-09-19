/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom;


import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.events.EventListener;

/**
 * Provides methods to manipulate &lt;marquee&gt; elements.
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
