/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.svg;

import lombok.Getter;
import org.loboevolution.common.Strings;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.Node;

import java.awt.*;


/**
 * <p>SVGElementImpl class.</p>
 */
@Getter
public class SVGElementImpl extends HTMLElementImpl implements SVGElement, SVGStylable, Drawable {

    private SVGSVGElement ownerSVGElement;

    /**
     * <p>Constructor for SVGElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public SVGElementImpl(final String name) {
        super(name);
    }

    @Override
    public void draw(Graphics2D graphics){
        if (hasChildNodes()) {
            final NodeListImpl children = (NodeListImpl) getChildNodes();
            children.forEach(node -> {
                if (node instanceof Drawable child) {
                    final SVGElement selem = (SVGElement) node;
                    selem.setOwnerSVGElement(getOwnerSVGElement());
                    if (selem instanceof SVGAnimateTransformElement) {
                        child.animation(selem);
                    }
                    child.draw(graphics);
                }
            });
        }
    }

    public SVGAnimatedBoolean getExternalResourcesRequired() {
        return new SVGAnimatedBooleanImpl(false);
    }

    public SVGStringList getRequiredFeatures() {
        return new SVGStringListImpl();
    }

    public SVGStringList getRequiredExtensions() {
        return new SVGStringListImpl();
    }

    public SVGStringList getSystemLanguage() {
        return new SVGStringListImpl();
    }

    public boolean hasExtension(String extension) {
        return false;
    }

    @Override
    public SVGSVGElement getOwnerSVGElement() {
        Node parent = getParentNode();
        if (parent == getDocumentNode()) {
            return null;
        }
        while (parent != null && !(parent instanceof SVGSVGElement)) {
            parent = parent.getParentNode();
        }
        return (SVGSVGElement) parent;
    }

    @Override
    public void setOwnerSVGElement(SVGSVGElement elem) {
        this.ownerSVGElement = elem;
    }

    @Override
    public SVGElement getViewportElement() {
        if(ownerSVGElement != null) {
            return ownerSVGElement;
        }
        Node parent = getParentNode();
        if (parent == getDocumentNode()) {
            return null;
        }

        while (parent != null && !(parent instanceof SVGSVGElement || parent instanceof SVGSymbolElement)) {
            parent = parent.getParentNode();
        }
        return (SVGElement) parent;
    }


    public String getXMLlang() {
        return getAttribute("xml:lang");
    }


    public void setXMLlang(final String xmllang) {
        setAttribute("xml:lang", xmllang);

    }

    public String getXMLspace() {
        return getAttribute("xml:space");
    }

    public void setXMLspace(final String xmlspace) {
        setAttribute("xml:space", xmlspace);
    }

    /**
     * <p>getVisibility.</p>
     *
     * @return a boolean.
     */
    public boolean getVisibility() {
        final CSSStyleDeclaration style = getStyle();
        final String visibility = Strings.isNotBlank(style.getVisibility()) ? style.getVisibility() : getAttribute("visibility");
        return "visible".equals(visibility);
    }

    /**
     * <p>getDisplay.</p>
     *
     * @return a boolean.
     */
    public boolean getDisplay() {
        final CSSStyleDeclaration style = getStyle();
        final String display = Strings.isNotBlank(style.getDisplay()) ? style.getDisplay() : getAttribute("display");
        return !"none".equals(display);
    }

    /**
     * <p>getOpacity.</p>
     *
     * @return a float.
     */
    public float getOpacity() {
        final CSSStyleDeclaration style = getStyle();
        final String opacityStr = Strings.isNotBlank(style.getOpacity()) ? style.getOpacity() : getAttribute("opacity");
        float opacity = 1;
        if (opacityStr != null) {
            opacity = Float.parseFloat(opacityStr);
        }
        if (opacity > 1) {
            opacity = 1;
        }
        if (opacity < 0) {
            opacity = 0;
        }
        return opacity;
    }
}
