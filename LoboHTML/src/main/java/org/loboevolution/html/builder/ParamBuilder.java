package org.loboevolution.html.builder;

import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.domimpl.HTMLParamElementImpl;

public class ParamBuilder implements HTMLElementBuilder {

    @Override
    public HTMLElement build(final String name) {
        return new HTMLParamElementImpl(name);
    }
}