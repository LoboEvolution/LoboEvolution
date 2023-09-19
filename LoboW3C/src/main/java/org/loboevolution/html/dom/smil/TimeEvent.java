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

package org.loboevolution.html.dom.smil;

import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.views.AbstractView;

/**
 * The TimeEvent interface provides specific contextual information
 * associated with Time events.
 */
public interface TimeEvent extends Event {
    /**
     * The view attribute identifies the AbstractView
     * from which the event was generated.
     *
     * @return a {@link org.loboevolution.html.node.views.AbstractView} object.
     */
    AbstractView getView();

    /**
     * Specifies some detail information about the Event ,
     * depending on the type of event.
     *
     * @return a int.
     */
    int getDetail();

    /**
     * The initTimeEvent method is used to initialize the value of
     * a TimeEvent created through the DocumentEvent
     * interface. This method may only be called before the
     * TimeEvent has been dispatched via the
     * dispatchEvent method, though it may be called multiple times
     * during that phase if necessary. If called multiple times, the final
     * invocation takes precedence.
     *
     * @param typeArg
     *            Specifies the event type.
     * @param viewArg
     *            Specifies the Event 's AbstractView
     *            .
     * @param detailArg
     *            Specifies the Event 's detail.
     */
    void initTimeEvent(String typeArg, AbstractView viewArg, int detailArg);

}
