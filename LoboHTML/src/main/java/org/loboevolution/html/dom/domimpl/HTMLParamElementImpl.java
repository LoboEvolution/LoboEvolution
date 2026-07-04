package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLParamElement;
import org.loboevolution.html.renderstate.DisplayRenderState;
import org.loboevolution.html.renderstate.RenderState;

public class HTMLParamElementImpl extends HTMLElementImpl implements HTMLParamElement {

    /**
     * <p>Constructor for HTMLParamElementImpl.</p>
     */
    public HTMLParamElementImpl() {
        super("PARAM");
    }

    /**
     * <p>Constructor for HTMLParamElementImpl.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public HTMLParamElementImpl(final String name) {
        super(name);
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getType() {
        return "";
    }

    @Override
    public void setType(String type) {

    }

    @Override
    public String getValue() {
        return "";
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public String getValueType() {
        return "";
    }

    @Override
    public void setValueType(String valueType) {

    }

    /** {@inheritDoc} */
    @Override
    protected RenderState createRenderState(final RenderState prevRenderState) {
        return new DisplayRenderState(prevRenderState, this, RenderState.DISPLAY_NONE);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "[object HTMLParamElement]";
    }
}
