package org.loboevolution.html.node.events;

import org.loboevolution.html.node.js.Window;

/**
 * Simple user interface events.
 */
public interface UIEvent extends Event {
   
    double getDetail();

    
    Window getView();
    
    void initUIEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, Window viewArg, double detailArg);

   

    interface UIEventInit extends EventInit {
        
        int getDetail();

        
        void setDetail(int detail);

       
        
        Window getView();

        
        void setView(Window view);

    }



	
}
