package org.loboevolution.html.dom.svgimpl;

import java.util.ArrayList;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.html.dom.smil.ElementTargetAttributes;
import org.loboevolution.html.dom.smil.SMILAnimation;
import org.loboevolution.html.dom.smil.Time;
import org.loboevolution.html.dom.smil.TimeList;
import org.loboevolution.html.dom.svg.SVGTransform;
import org.w3c.dom.DOMException;

public class SVGAnimationImpl extends SVGElementImpl implements SMILAnimation {
	
	private SVGAnimateImpl animate;

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

		if (ArrayUtilities.isBlank(beginTimeList)) {
			beginTimeList.add(new TimeImpl("0"));
		}
		return new TimeListImpl(beginTimeList);
	}

	@Override
	public TimeList getEnd() {
		ArrayList<Time> beginTimeList = new ArrayList<Time>();
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

	@Override
	public float getDur() {
		String duration = this.getAttribute("dur");
		return TimeImpl.getClockMilliSecs(duration);
	}

	@Override
	public void setDur(float dur) throws DOMException {
		this.setAttribute("dur", String.valueOf(dur));
	}

	@Override
	public String getFill() {
		return this.getAttribute("fill");
	}

	@Override
	public void setFill(String fill) throws DOMException {
		this.setAttribute("fill", fill);
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
		return TimeImpl.getClockMilliSecs(rd);
	}

	@Override
	public void setRepeatDur(float repeatDur) throws DOMException {
		this.setAttribute("repeatDur", String.valueOf(repeatDur));
	}

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

	@Override
	public boolean endElement() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean beginElementAt(float offset) throws DOMException {
		setDur(offset);
		return beginElement();
	}

	@Override
	public boolean endElementAt(float offset) throws DOMException {
		// TODO Auto-generated method stub
		return false;
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
	
	
	protected SVGAnimateImpl getAnimate() {
		return animate;
	}
	
	protected void setAnimate(SVGAnimateImpl animate) {
		this.animate = animate;
	}
}