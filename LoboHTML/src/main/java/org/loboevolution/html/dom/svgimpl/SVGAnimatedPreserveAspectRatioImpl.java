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

package org.loboevolution.html.dom.svgimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.svg.SVGAnimatedPreserveAspectRatio;
import org.loboevolution.html.dom.svg.SVGPreserveAspectRatio;

/**
 * <p>SVGAnimatedPreserveAspectRatioImpl class.</p>
 */
public class SVGAnimatedPreserveAspectRatioImpl extends SVGAnimatedValue implements SVGAnimatedPreserveAspectRatio {

    private SVGPreserveAspectRatio baseVal;

    public SVGAnimatedPreserveAspectRatioImpl(SVGPreserveAspectRatio baseVal, SVGElementImpl owner) {
        this.owner = owner;
        this.baseVal = baseVal;
    }

    @Override
    public SVGPreserveAspectRatio getBaseVal() {
        return baseVal;
    }

    void setBaseVal(SVGPreserveAspectRatio baseVal) throws DOMException {
        this.baseVal = baseVal;
    }

    @Override
    public SVGPreserveAspectRatio getAnimVal() {
        if (animations == null) {
            return baseVal;
        } else {
            int numAnimations = animations.size();
            SVGPreserveAspectRatio result = null;
            for (int i = 0; i < numAnimations; i++) {
                SVGAnimationElementImpl animation = (SVGAnimationElementImpl) animations.elementAt(i);
                SVGPreserveAspectRatio animVal = (SVGPreserveAspectRatio) animation.getCurrentValue(ANIMTYPE_PRESERVEASPECTRATIO);
                if (animVal != null) {
                    result = animVal;
                    break;
                }
            }
            if (result != null) {
                return result;
            } else {
                return baseVal;
            }
        }
    }
}
