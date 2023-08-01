package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLMarqueeElement;
import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.events.EventListener;
import org.loboevolution.html.renderstate.BlockRenderState;
import org.loboevolution.html.renderstate.RenderState;

public class HTMLMarqueeElementImpl extends HTMLElementImpl implements HTMLMarqueeElement {

    /**
     * <p>Constructor for HTMLElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public HTMLMarqueeElementImpl(String name) {
        super(name);
    }

    @Override
    protected RenderState createRenderState(RenderState prevRenderState) {
        return new BlockRenderState(prevRenderState, this);
    }

    @Override
    public String getBehavior() {
        return null;
    }

    @Override
    public void setBehavior(String behavior) {

    }

    @Override
    public String getBgColor() {
        return null;
    }

    @Override
    public void setBgColor(String bgColor) {

    }

    @Override
    public String getDirection() {
        return null;
    }

    @Override
    public void setDirection(String direction) {

    }

    @Override
    public String getHeight() {
        return null;
    }

    @Override
    public void setHeight(String height) {

    }

    @Override
    public double getHspace() {
        return 0;
    }

    @Override
    public void setHspace(double hspace) {

    }

    @Override
    public double getLoop() {
        return 0;
    }

    @Override
    public void setLoop(double loop) {

    }

    @Override
    public EventListener<Event> getOnbounce() {
        return null;
    }

    @Override
    public void setOnbounce(EventListener<Event> onbounce) {

    }

    @Override
    public EventListener<Event> getOnfinish() {
        return null;
    }

    @Override
    public void setOnfinish(EventListener<Event> onfinish) {

    }

    @Override
    public EventListener<Event> getOnstart() {
        return null;
    }

    @Override
    public void setOnstart(EventListener<Event> onstart) {

    }

    @Override
    public double getScrollAmount() {
        return 0;
    }

    @Override
    public void setScrollAmount(double scrollAmount) {

    }

    @Override
    public double getScrollDelay() {
        return 0;
    }

    @Override
    public void setScrollDelay(double scrollDelay) {

    }

    @Override
    public boolean isTrueSpeed() {
        return false;
    }

    @Override
    public void setTrueSpeed(boolean trueSpeed) {

    }

    @Override
    public double getVspace() {
        return 0;
    }

    @Override
    public void setVspace(double vspace) {

    }

    @Override
    public String getWidth() {
        return null;
    }

    @Override
    public void setWidth(String width) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
