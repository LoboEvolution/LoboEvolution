package org.loboevolution.html.dom.svgimpl;

import java.util.ArrayList;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.html.dom.smil.ElementTargetAttributes;
import org.loboevolution.html.dom.smil.SMILAnimation;
import org.loboevolution.html.dom.smil.Time;
import org.loboevolution.html.dom.smil.TimeList;
import org.w3c.dom.DOMException;

public class SVGAnimationImpl extends SVGElementImpl implements SMILAnimation {

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
		if (type == null)
			return ElementTargetAttributes.ATTRIBUTE_TYPE_XML;

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
	public void setBegin(TimeList begin) throws DOMException {
		// TODO Auto-generated method stub
		
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
	public void setEnd(TimeList end) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getDur() {
		String duration = this.getAttribute("dur");
		return TimeImpl.getClockMilliSecs(duration);
	}

	@Override
	public void setDur(float dur) throws DOMException {
		// TODO Auto-generated method stub
		
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
		return this.getAttribute("fill");
	}

	@Override
	public void setFill(String fill) throws DOMException {
		// TODO Auto-generated method stub
		
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
	public boolean beginElementAt(float offset) throws DOMException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean endElementAt(float offset) throws DOMException {
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
}