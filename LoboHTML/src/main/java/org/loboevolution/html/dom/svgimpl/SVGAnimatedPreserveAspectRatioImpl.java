/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

package org.loboevolution.html.dom.svgimpl;

import com.gargoylesoftware.css.dom.DOMException;
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
