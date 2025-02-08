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

package org.loboevolution.html.dom.smil;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.svg.SVGElementImpl;

import static org.loboevolution.svg.SVGTransform.*;

/**
 * <p>SMILAnimationImpl class.</p>
 */
public class SMILAnimationImpl extends SVGElementImpl implements SMILAnimation {

    /**
     * <p>Constructor for SMILAnimationImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public SMILAnimationImpl(String name) {
        super(name);
    }

    /**
     * <p>getType.</p>
     *
     * @return a short.
     */
    public short getType() {
        final String type = this.getAttribute("type");
        return switch (type) {
            case "translate" -> SVG_TRANSFORM_TRANSLATE;
            case "scale" -> SVG_TRANSFORM_SCALE;
            case "rotate" -> SVG_TRANSFORM_ROTATE;
            case "skewX" -> SVG_TRANSFORM_SKEWX;
            case "skewY" -> SVG_TRANSFORM_SKEWY;
            default -> SVG_TRANSFORM_UNKNOWN;
        };
    }

    @Override
    public String getValues() {
        return this.getAttribute("values");
    }

    @Override
    public void setValues(String values) throws DOMException {
        this.setAttribute("values", values);
    }

    @Override
    public String getFrom() {
        return this.getAttribute("from");
    }

    @Override
    public void setFrom(String from) throws DOMException {
        this.setAttribute("from", from);
    }

    @Override
    public String getTo() {
        return this.getAttribute("to");
    }

    @Override
    public void setTo(String to) throws DOMException {
        this.setAttribute("to", to);
    }

    @Override
    public String getBy() {
        return this.getAttribute("by");
    }

    @Override
    public void setBy(String by) throws DOMException {
        this.setAttribute("by", by);
    }

    @Override
    public String getAttributeName() {
        return "";
    }

    @Override
    public void setAttributeName(String attributeName) {

    }

    @Override
    public short getAttributeType() {
        return 0;
    }

    @Override
    public void setAttributeType(short attributeType) {

    }

    @Override
    public TimeList getBegin() {
        return null;
    }

    @Override
    public TimeList getEnd() {
        return null;
    }

    @Override
    public float getDur() {
        final String duration = this.getAttribute("dur");
        return TimeImpl.parseClockValue(duration);
    }

    @Override
    public void setDur(float dur) throws DOMException {
        this.setAttribute("dur", String.valueOf(dur));
    }

    @Override
    public short getFill() {
        return (short) this.getAttributeAsInt("fill", 0);
    }

    @Override
    public void setFill(short fill) throws DOMException {
        this.setAttribute("fill", String.valueOf(fill));
    }

    @Override
    public float getRepeatCount() {
        final String rc = this.getAttribute("repeatCount");
        if (rc == null) return 0;
        if ("indefinite".equals(rc)) return Float.MAX_VALUE;
        return Float.parseFloat(rc);
    }

    @Override
    public void setRepeatCount(float repeatCount) throws DOMException {
        this.setAttribute("repeatCount", String.valueOf(repeatCount));
    }

    @Override
    public float getRepeatDur() {
        final String rd = this.getAttribute("repeatDur");
        if (rd == null) return 5000;
        if ("indefinite".equals(rd)) return Float.MAX_VALUE;
        return TimeImpl.parseClockValue(rd);
    }

    @Override
    public void setRepeatDur(float repeatDur) throws DOMException {
        this.setAttribute("repeatDur", String.valueOf(repeatDur));
    }

    @Override
    public boolean beginElement() {
        final String restart = getAttribute("restart");
        if (!("never").equalsIgnoreCase(restart)) {
          //  final SVGAnimateImpl anime = getAnimate();
           // anime.restart();
            return true;
        }
        return false;
    }

    @Override
    public boolean beginElementAt(float offset) throws DOMException {
        return false;
    }

    @Override
    public boolean endElement() {
        return false;
    }

    @Override
    public boolean endElementAt(float offset) throws DOMException {
        setDur(offset);
        return beginElement();
    }
}
