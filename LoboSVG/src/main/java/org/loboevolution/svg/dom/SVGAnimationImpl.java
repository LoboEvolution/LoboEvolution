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

package org.loboevolution.svg.dom;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.smil.ElementTargetAttributes;
import org.loboevolution.html.dom.smil.SMILAnimation;
import org.loboevolution.html.dom.smil.Time;
import org.loboevolution.html.dom.smil.TimeList;
import org.loboevolution.svg.SVGTransform;
import org.loboevolution.svg.smil.TimeImpl;
import org.loboevolution.svg.smil.TimeListImpl;

import java.util.ArrayList;


/**
 * <p>SVGAnimationImpl class.</p>
 */
@Slf4j
public class SVGAnimationImpl extends SVGElementImpl implements SMILAnimation {
	
	private SVGAnimateImpl animate;

	/**
	 * <p>Constructor for SVGAnimationImpl.</p>
	 *
	 * @param element a {@link HTMLElement} object.
	 */
	public SVGAnimationImpl(final HTMLElement element) {
		super(element);
	}

	/** {@inheritDoc} */
	@Override
	public String getAttributeName() {
		return this.getAttribute("attributeName");
	}

	/** {@inheritDoc} */
	@Override
	public void setAttributeName(final String attributeName) {
		this.setAttribute("attributeName", attributeName);		
	}
	
	/**
	 * <p>getType.</p>
	 *
	 * @return a short.
	 */
	public short getType() {
		final String type = this.getAttribute("type");

        return switch (type) {
            case "translate" -> SVGTransform.SVG_TRANSFORM_TRANSLATE;
            case "scale" -> SVGTransform.SVG_TRANSFORM_SCALE;
            case "rotate" -> SVGTransform.SVG_TRANSFORM_ROTATE;
            case "skewX" -> SVGTransform.SVG_TRANSFORM_SKEWX;
            case "skewY" -> SVGTransform.SVG_TRANSFORM_SKEWY;
            default -> SVGTransform.SVG_TRANSFORM_UNKNOWN;
        };
	}

	/** {@inheritDoc} */
	@Override
	public short getAttributeType() {
		final String type = this.getAttribute("attributeType");
		if (type == null)
			return ElementTargetAttributes.ATTRIBUTE_TYPE_AUTO;

        return switch (type) {
            case "xml", "XML" -> ElementTargetAttributes.ATTRIBUTE_TYPE_XML;
            case "css", "CSS" -> ElementTargetAttributes.ATTRIBUTE_TYPE_CSS;
            default -> ElementTargetAttributes.ATTRIBUTE_TYPE_AUTO;
        };
	}

	/** {@inheritDoc} */
	@Override
	public void setAttributeType(final short attributeType) {
		this.setAttribute("attributeType", String.valueOf(attributeType));
	}

	/** {@inheritDoc} */
	@Override
	public TimeList getBegin() {
		final ArrayList<Time> beginTimeList = new ArrayList<>();
		final String begin = this.getAttribute("begin");

		if (begin != null) {
			final String[] beginTimeStringList = begin.split(";");

			for (final String beginTimeString : beginTimeStringList) {
				try {
					beginTimeList.add(new TimeImpl(beginTimeString));
				} catch (final IllegalArgumentException e) {
					log.info(e.getMessage());
				}
			}
		}

		if (ArrayUtilities.isBlank(beginTimeList)) {
			beginTimeList.add(new TimeImpl("0"));
		}
		return new TimeListImpl(beginTimeList);
	}

	/** {@inheritDoc} */
	@Override
	public TimeList getEnd() {
		final ArrayList<Time> beginTimeList = new ArrayList<>();
		final String begin = this.getAttribute("end");

		if (begin != null) {
			final String[] beginTimeStringList = begin.split(";");

			for (final String beginTimeString : beginTimeStringList) {
				try {
					beginTimeList.add(new TimeImpl(beginTimeString));
				} catch (final IllegalArgumentException e) {
					log.info(e.getMessage());
				}
			}
		}

		if (ArrayUtilities.isBlank(beginTimeList)) {
			beginTimeList.add(new TimeImpl("0"));
		}
		return new TimeListImpl(beginTimeList);
	}

	/** {@inheritDoc} */
	@Override
	public float getDur() {
		final String duration = this.getAttribute("dur");
		return TimeImpl.getClockMilliSecs(duration);
	}

	/** {@inheritDoc} */
	@Override
	public void setDur(final float dur) {
		this.setAttribute("dur", String.valueOf(dur));
	}

	/** {@inheritDoc} */
	@Override
	public String getFill() {
		return this.getAttribute("fill");
	}

	/** {@inheritDoc} */
	@Override
	public void setFill(final String fill) {
		this.setAttribute("fill", fill);
	}

	/** {@inheritDoc} */
	@Override
	public float getRepeatCount() {
		final String rc = this.getAttribute("repeatCount");
		if (rc == null) return 0;
		if ("indefinite".equals(rc)) return Float.MAX_VALUE;
		return Float.parseFloat(rc);
	}

	/** {@inheritDoc} */
	@Override
	public void setRepeatCount(final float repeatCount) {
		this.setAttribute("repeatCount", String.valueOf(repeatCount));
	}

	/** {@inheritDoc} */
	@Override
	public float getRepeatDur() {
		final String rd = this.getAttribute("repeatDur");
		if (rd == null) return 5000;
		if ("indefinite".equals(rd)) return Float.MAX_VALUE;
		return TimeImpl.getClockMilliSecs(rd);
	}

	/** {@inheritDoc} */
	@Override
	public void setRepeatDur(final float repeatDur) {
		this.setAttribute("repeatDur", String.valueOf(repeatDur));
	}

	/** {@inheritDoc} */
	@Override
	public boolean beginElement() {
		final String restart = getAttribute("restart");
		if (!("never").equalsIgnoreCase(restart)) {
			final SVGAnimateImpl anime = getAnimate();
			anime.restart();
			return true;
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean endElement() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean beginElementAt(final float offset) {
		setDur(offset);
		return beginElement();
	}

	/** {@inheritDoc} */
	@Override
	public boolean endElementAt(final float offset) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String getValues() {
		return this.getAttribute("values");
	}

	/** {@inheritDoc} */
	@Override
	public void setValues(final String values) {
		this.setAttribute("values", values);
	}

	/** {@inheritDoc} */
	@Override
	public String getFrom() {
		return this.getAttribute("from");
	}

	/** {@inheritDoc} */
	@Override
	public void setFrom(final String from) {
		this.setAttribute("from", from);	
	}

	/** {@inheritDoc} */
	@Override
	public String getTo() {
		return this.getAttribute("to");
	}

	/** {@inheritDoc} */
	@Override
	public void setTo(final String to) {
		this.setAttribute("to", to);
	}

	/** {@inheritDoc} */
	@Override
	public String getBy() {
		return this.getAttribute("by");
	}

	/** {@inheritDoc} */
	@Override
	public void setBy(final String by) {
		this.setAttribute("by", by);
	}
	
	
	/**
	 * <p>Getter for the field animate.</p>
	 *
	 * @return a {@link SVGAnimateImpl} object.
	 */
	protected SVGAnimateImpl getAnimate() {
		return animate;
	}
	
	/**
	 * <p>Setter for the field animate.</p>
	 *
	 * @param animate a {@link SVGAnimateImpl} object.
	 */
	protected void setAnimate(final SVGAnimateImpl animate) {
		this.animate = animate;
	}
}
