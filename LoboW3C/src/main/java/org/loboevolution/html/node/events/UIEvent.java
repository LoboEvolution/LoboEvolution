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
