/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

import java.util.ArrayList;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.html.dom.smil.ElementTargetAttributes;
import org.loboevolution.html.dom.smil.SMILAnimation;
import org.loboevolution.html.dom.smil.Time;
import org.loboevolution.html.dom.smil.TimeList;
import org.loboevolution.html.dom.svg.SVGTransform;


/**
 * <p>SVGAnimationImpl class.</p>
 *
 *
 *
 */
public class SVGAnimationImpl extends SVGElementImpl implements SMILAnimation {
	
	private SVGAnimateImpl animate;

	/**
	 * <p>Constructor for SVGAnimationImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGAnimationImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public String getAttributeName() {
		return this.getAttribute("attributeName");
	}

	/** {@inheritDoc} */
	@Override
	public void setAttributeName(String attributeName) {
		this.setAttribute("attributeName", attributeName);		
	}
	
	/**
	 * <p>getType.</p>
	 *
	 * @return a short.
	 */
	public short getType() {
		String type = this.getAttribute("type");
			
		switch (type) {
		case "translate":
			return SVGTransform.SVG_TRANSFORM_TRANSLATE;
		case "scale":
			return SVGTransform.SVG_TRANSFORM_SCALE;
		case "rotate":
			return SVGTransform.SVG_TRANSFORM_ROTATE;
		case "skewX":
			return SVGTransform.SVG_TRANSFORM_SKEWX;
		case "skewY":
			return SVGTransform.SVG_TRANSFORM_SKEWY;
		default:
			return SVGTransform.SVG_TRANSFORM_UNKNOWN;
		}
	}

	/** {@inheritDoc} */
	@Override
	public short getAttributeType() {
		String type = this.getAttribute("attributeType");
		if (type == null)
			return ElementTargetAttributes.ATTRIBUTE_TYPE_AUTO;

		switch (type) {
		case "xml":
		case "XML":
			return ElementTargetAttributes.ATTRIBUTE_TYPE_XML;
		case "css":
		case "CSS":
			return ElementTargetAttributes.ATTRIBUTE_TYPE_CSS;
		default:
			return ElementTargetAttributes.ATTRIBUTE_TYPE_AUTO;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setAttributeType(short attributeType) {
		this.setAttribute("attributeType", String.valueOf(attributeType));
	}

	/** {@inheritDoc} */
	@Override
	public TimeList getBegin() {
		ArrayList<Time> beginTimeList = new ArrayList<>();
		String begin = this.getAttribute("begin");

		if (begin != null) {
			String[] beginTimeStringList = begin.split(";");

			for (String beginTimeString : beginTimeStringList) {
				try {
					beginTimeList.add(new TimeImpl(beginTimeString));
				} catch (IllegalArgumentException e) {
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
		ArrayList<Time> beginTimeList = new ArrayList<>();
		String begin = this.getAttribute("end");

		if (begin != null) {
			String[] beginTimeStringList = begin.split(";");

			for (String beginTimeString : beginTimeStringList) {
				try {
					beginTimeList.add(new TimeImpl(beginTimeString));
				} catch (IllegalArgumentException e) {
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
		String duration = this.getAttribute("dur");
		return TimeImpl.getClockMilliSecs(duration);
	}

	/** {@inheritDoc} */
	@Override
	public void setDur(float dur) {
		this.setAttribute("dur", String.valueOf(dur));
	}

	/** {@inheritDoc} */
	@Override
	public String getFill() {
		return this.getAttribute("fill");
	}

	/** {@inheritDoc} */
	@Override
	public void setFill(String fill) {
		this.setAttribute("fill", fill);
	}

	/** {@inheritDoc} */
	@Override
	public float getRepeatCount() {
		String rc = this.getAttribute("repeatCount");
		if(rc == null) return 0;
		if("indefinite".equals(rc)) return Float.MAX_VALUE;
		return Float.parseFloat(rc);
	}

	/** {@inheritDoc} */
	@Override
	public void setRepeatCount(float repeatCount) {
		this.setAttribute("repeatCount", String.valueOf(repeatCount));
	}

	/** {@inheritDoc} */
	@Override
	public float getRepeatDur() {
		String rd = this.getAttribute("repeatDur");
		if(rd == null) return 5000;
		if("indefinite".equals(rd)) return Float.MAX_VALUE;
		return TimeImpl.getClockMilliSecs(rd);
	}

	/** {@inheritDoc} */
	@Override
	public void setRepeatDur(float repeatDur) {
		this.setAttribute("repeatDur", String.valueOf(repeatDur));
	}

	/** {@inheritDoc} */
	@Override
	public boolean beginElement() {
		String restart = getAttribute("restart");
		if (!("never").equalsIgnoreCase(restart)) {
			SVGAnimateImpl anime = getAnimate();
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
	public boolean beginElementAt(float offset) {
		setDur(offset);
		return beginElement();
	}

	/** {@inheritDoc} */
	@Override
	public boolean endElementAt(float offset) {
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
	public void setValues(String values) {
		this.setAttribute("values", values);
	}

	/** {@inheritDoc} */
	@Override
	public String getFrom() {
		return this.getAttribute("from");
	}

	/** {@inheritDoc} */
	@Override
	public void setFrom(String from) {
		this.setAttribute("from", from);	
	}

	/** {@inheritDoc} */
	@Override
	public String getTo() {
		return this.getAttribute("to");
	}

	/** {@inheritDoc} */
	@Override
	public void setTo(String to) {
		this.setAttribute("to", to);
	}

	/** {@inheritDoc} */
	@Override
	public String getBy() {
		return this.getAttribute("by");
	}

	/** {@inheritDoc} */
	@Override
	public void setBy(String by) {
		this.setAttribute("by", by);
	}
	
	
	/**
	 * <p>Getter for the field animate.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svgimpl.SVGAnimateImpl} object.
	 */
	protected SVGAnimateImpl getAnimate() {
		return animate;
	}
	
	/**
	 * <p>Setter for the field animate.</p>
	 *
	 * @param animate a {@link org.loboevolution.html.dom.svgimpl.SVGAnimateImpl} object.
	 */
	protected void setAnimate(SVGAnimateImpl animate) {
		this.animate = animate;
	}
}
