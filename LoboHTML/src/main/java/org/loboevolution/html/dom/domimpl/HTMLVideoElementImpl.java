package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLVideoElement;

public class HTMLVideoElementImpl extends HTMLElementImpl implements HTMLVideoElement {

    /**
     * <p>Constructor for HTMLElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public HTMLVideoElementImpl(String name) {
        super(name);
    }

    @Override
    public int getClientHeight() {
        int clientHeight = super.getClientHeight();
        return clientHeight == 0 ? 150 : clientHeight;
    }

    @Override
    public Integer getClientWidth() {
        int clientWidth = super.getClientWidth();
        return clientWidth == 0 ? 300 : clientWidth;
    }

    @Override
    public Integer getOffsetWidth() {
        return getClientWidth();
    }
}
