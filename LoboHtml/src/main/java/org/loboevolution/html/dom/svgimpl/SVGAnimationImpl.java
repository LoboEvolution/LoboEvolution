/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.svgimpl;

import java.util.ArrayList;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.smil.ElementTargetAttributes;
import org.loboevolution.html.dom.smil.SMILAnimation;
import org.loboevolution.html.dom.smil.Time;
import org.loboevolution.html.dom.smil.TimeList;
import org.loboevolution.html.dom.svg.SVGElement;
import org.loboevolution.html.dom.svg.SVGTransform;
import org.w3c.dom.DOMException;

public class SVGAnimationImpl extends SVGSVGElementImpl implements SMILAnimation {

	public SVGAnimationImpl(String name) {
		super(name);
	}

	@Override
	public String getAttributeName() {
		return this.getAttribute("attributeName");
	}

	@Override
	public void setAttributeName(String attributeName) {
		this.setAttribute("attributeName", attributeName);

	}

	@Override
	public short getAttributeType() {
		String type = this.getAttribute("attributeType");
		if(type == null) return ElementTargetAttributes.ATTRIBUTE_TYPE_XML;
		
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

	@Override
	public void setAttributeType(short attributeType) {
		this.setAttribute("attributeType", String.valueOf(attributeType));

	}

	@Override
	public TimeList getBegin() {
		ArrayList<Time> beginTimeList = new ArrayList<Time>();
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
		
		if(ArrayUtilities.isNotBlank(beginTimeList)) {
			beginTimeList.add(new TimeImpl("0"));
		}
		return new TimeListImpl(beginTimeList);
	}
	
	
	public float getStartTime() {
		String beginTime = this.getAttribute("begin");
		if (Strings.isNotBlank(beginTime) && !beginTime.equalsIgnoreCase("indefinite")) {
			return SVGUtility.getClockMilliSecs(beginTime);
		} else {
			return 0;
		}
	}

	@Override
	public void setBegin(TimeList begin) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public TimeList getEnd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEnd(TimeList end) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public float getDur() {
		String duration = this.getAttribute("dur");
		return SVGUtility.getClockMilliSecs(duration);
	}

	@Override
	public void setDur(float dur) throws DOMException {
		this.setAttribute("dur", String.valueOf(dur));
	}

	@Override
	public short getRestart() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRestart(short restart) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getFill() {
		return this.getAttribute(FILL);
	}

	@Override
	public void setFill(String fill) throws DOMException {
		this.setAttribute(FILL, fill);

	}

	@Override
	public float getRepeatCount() {
		String rc = this.getAttribute("repeatCount");
		if(rc == null) return 0;
		if("indefinite".equals(rc)) return Float.MAX_VALUE;
		return Float.parseFloat(rc);
	}

	@Override
	public void setRepeatCount(float repeatCount) throws DOMException {
		this.setAttribute("repeatCount", String.valueOf(repeatCount));

	}

	@Override
	public float getRepeatDur() {
		String rd = this.getAttribute("repeatDur");
		if(rd == null) return 5000;
		if("indefinite".equals(rd)) return Float.MAX_VALUE;
		return SVGUtility.getClockMilliSecs(rd);
	}

	@Override
	public void setRepeatDur(float repeatDur) throws DOMException {
		this.setAttribute("repeatDur", String.valueOf(repeatDur));

	}

	@Override
	public boolean beginElement() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean endElement() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void pauseElement() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resumeElement() {
		// TODO Auto-generated method stub

	}

	@Override
	public void seekElement(float seekTo) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean beginElementAt(float arg0) throws DOMException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean endElementAt(float arg0) throws DOMException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public short getAdditive() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAdditive(short additive) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public short getAccumulate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAccumulate(short accumulate) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public short getCalcMode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCalcMode(short calcMode) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getKeySplines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKeySplines(String keySplines) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public TimeList getKeyTimes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKeyTimes(TimeList keyTimes) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValues(String values) throws DOMException {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBy(String by) throws DOMException {
		// TODO Auto-generated method stub

	}

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
			return 0;
		}
	}
	
	public float getSimpleDuration() throws DOMException {
		return getDur();
	}
	
	public SVGElement getTargetElement() {
		String href = this.getAttribute("xlink:href");
		if (href == null) {
			href = this.getAttribute("href");
		}
		
		if (href != null) {
			return (SVGElement) getOwnerDocument().getElementById(href.split("#")[1]);
		}
		
		return null;
	}
}
