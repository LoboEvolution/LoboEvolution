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

public class SVGGElementImpl extends SVGElementImpl implements SVGGElement {

    /**
     * <p>Constructor for SVGGElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public SVGGElementImpl(String name) {
        super(name);
    }

    @Override
    public String getXMLlang() {
        return "";
    }

    @Override
    public void setXMLlang(String xmllang) {

    }

    @Override
    public String getXMLspace() {
        return "";
    }

    @Override
    public void setXMLspace(String xmlspace) {

    }

    @Override
    public SVGAnimatedTransformList getTransform() {
        return null;
    }

    @Override
    public SVGElement getNearestViewportElement() {
        return null;
    }

    @Override
    public SVGElement getFarthestViewportElement() {
        return null;
    }

    @Override
    public SVGRect getBBox() {
        return null;
    }

    @Override
    public SVGMatrix getCTM() {
        return null;
    }

    @Override
    public SVGMatrix getScreenCTM() {
        return null;
    }

    @Override
    public SVGMatrix getTransformToElement(SVGElement element) throws SVGException {
        return null;
    }
}
