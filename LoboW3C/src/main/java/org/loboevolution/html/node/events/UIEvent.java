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

package org.loboevolution.html.node.events;

import org.loboevolution.html.node.js.Window;

/**
 * Simple user interface events.
 *
 *
 *
 */
public interface UIEvent extends Event {
   
    /**
     * <p>getDetail.</p>
     *
     * @return a double.
     */
    double getDetail();

    
    /**
     * <p>getView.</p>
     *
     * @return a {@link org.loboevolution.html.node.js.Window} object.
     */
    Window getView();
    
    /**
     * <p>initUIEvent.</p>
     *
     * @param typeArg a {@link java.lang.String} object.
     * @param canBubbleArg a boolean.
     * @param cancelableArg a boolean.
     * @param viewArg a {@link org.loboevolution.html.node.js.Window} object.
     * @param detailArg a double.
     */
    void initUIEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, Window viewArg, double detailArg);

   

    interface UIEventInit extends EventInit {
        
        int getDetail();

        
        void setDetail(int detail);

       
        
        Window getView();

        
        void setView(Window view);

    }



	
}
