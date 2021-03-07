package org.loboevolution.html.dom;


import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.events.EventListener;

/**
 * Provides methods to manipulate &lt;marquee&gt; elements.
 */
public interface HTMLMarqueeElement extends HTMLElement {
   

    @Deprecated
    
    String getBehavior();

    
    void setBehavior(String behavior);

    @Deprecated
    
    String getBgColor();

    
    void setBgColor(String bgColor);

    @Deprecated
    
    String getDirection();

    
    void setDirection(String direction);

    @Deprecated
    
    String getHeight();

    
    void setHeight(String height);

    @Deprecated
    
    double getHspace();

    
    void setHspace(double hspace);

    @Deprecated
    
    double getLoop();

    
    void setLoop(double loop);

    @Deprecated
    
    
    EventListener<Event> getOnbounce();

    @Deprecated
    
    void setOnbounce(EventListener<Event> onbounce);


    @Deprecated
    EventListener<Event> getOnfinish();

    @Deprecated
    
    void setOnfinish(EventListener<Event> onfinish);

    @Deprecated
    EventListener<Event> getOnstart();

    @Deprecated
    void setOnstart(EventListener<Event> onstart);


    @Deprecated
    
    double getScrollAmount();

    
    void setScrollAmount(double scrollAmount);

    @Deprecated
    
    double getScrollDelay();

    
    void setScrollDelay(double scrollDelay);

    @Deprecated
    
    boolean isTrueSpeed();

    
    void setTrueSpeed(boolean trueSpeed);

    @Deprecated
    
    double getVspace();

    
    void setVspace(double vspace);

    @Deprecated
    
    String getWidth();

    
    void setWidth(String width);

    @Deprecated
    void start();

    @Deprecated
    void stop();

}
