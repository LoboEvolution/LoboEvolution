/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.svgimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.w3c.smil.ElementTargetAttributes;
import org.lobobrowser.w3c.smil.SMILAnimation;
import org.lobobrowser.w3c.smil.TimeList;
import org.lobobrowser.w3c.svg.SVGElement;
import org.lobobrowser.w3c.svg.SVGTransform;
import org.w3c.dom.DOMException;

public class SVGAnimationImpl extends SVGSVGElementImpl implements SMILAnimation {

	public SVGAnimationImpl(String name) {
		super(name);
	}

	@Override
	public String getAttributeName() {
		return this.getAttribute(HtmlAttributeProperties.ATTRIBUTE_NAME);
	}

	@Override
	public void setAttributeName(String attributeName) {
		this.setAttribute(HtmlAttributeProperties.ATTRIBUTE_NAME, attributeName);

	}

	@Override
	public short getAttributeType() {
		String type = this.getAttribute(HtmlAttributeProperties.ATTRIBUTE_TYPE);
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
		this.setAttribute(HtmlAttributeProperties.ATTRIBUTE_TYPE, String.valueOf(attributeType));

	}

	@Override
	public TimeList getBegin() {
		// TODO Auto-generated method stub
		return null;
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
		String duration = this.getAttribute(HtmlAttributeProperties.DUR);
		return SVGUtility.getClockSecs(duration);
	}

	@Override
	public void setDur(float dur) throws DOMException {
		this.setAttribute(HtmlAttributeProperties.DUR, String.valueOf(dur));
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
		return this.getAttribute(HtmlAttributeProperties.FILL);
	}

	@Override
	public void setFill(String fill) throws DOMException {
		this.setAttribute(HtmlAttributeProperties.FILL, fill);

	}

	@Override
	public float getRepeatCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRepeatCount(float repeatCount) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public float getRepeatDur() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRepeatDur(float repeatDur) throws DOMException {
		// TODO Auto-generated method stub

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
		return this.getAttribute(HtmlAttributeProperties.FROM);
	}

	@Override
	public void setFrom(String from) throws DOMException {
		this.setAttribute(HtmlAttributeProperties.FROM, from);

	}

	@Override
	public String getTo() {
		return this.getAttribute(HtmlAttributeProperties.TO);
	}

	@Override
	public void setTo(String to) throws DOMException {
		this.setAttribute(HtmlAttributeProperties.TO, to);

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
		String type = this.getAttribute(HtmlAttributeProperties.TYPE);
			
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

	public SVGElement getTargetElement() {
		String href = this.getAttribute(HtmlAttributeProperties.XLINK_HREF);
		if (href == null) {
			href = this.getAttribute(HtmlAttributeProperties.HREF);
		}
		
		if (href != null) {
			return (SVGElement) getOwnerDocument().getElementById(href.split("#")[1]);
		}
		
		return null;
	}
}
