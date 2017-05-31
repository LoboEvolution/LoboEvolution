package org.lobobrowser.html.svgimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.svgimpl.SVGSVGElementImpl;
import org.lobobrowser.w3c.svg.SVGAnimatedEnumeration;
import org.lobobrowser.w3c.svg.SVGAnimatedLength;
import org.lobobrowser.w3c.svg.SVGAnimatedLengthList;
import org.lobobrowser.w3c.svg.SVGAnimatedNumberList;
import org.lobobrowser.w3c.svg.SVGAnimatedTransformList;
import org.lobobrowser.w3c.svg.SVGPoint;
import org.lobobrowser.w3c.svg.SVGRect;
import org.lobobrowser.w3c.svg.SVGTextElement;
import org.w3c.dom.DOMException;

public class SVGTextElementImpl extends SVGSVGElementImpl implements SVGTextElement {

	public SVGTextElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedLengthList getDx() {
		return new SVGAnimatedLengthListImpl(SVGUtility.constructLengthList(this.getAttribute(HtmlAttributeProperties.DX)));
	}

	@Override
	public SVGAnimatedLengthList getDy() {
		return new SVGAnimatedLengthListImpl(SVGUtility.constructLengthList(this.getAttribute(HtmlAttributeProperties.DY)));
	}

	@Override
	public SVGAnimatedNumberList getRotate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGAnimatedLength getTextLength() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGAnimatedEnumeration getLengthAdjust() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfChars() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getComputedTextLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getSubStringLength(int charnum, int nchars) throws DOMException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SVGPoint getStartPositionOfChar(int charnum) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGPoint getEndPositionOfChar(int charnum) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGRect getExtentOfChar(int charnum) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getRotationOfChar(int charnum) throws DOMException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCharNumAtPosition(SVGPoint point) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void selectSubString(int charnum, int nchars) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SVGAnimatedTransformList getTransform() {
		return new SVGAnimatedTransformListImpl(this.getAttribute(HtmlAttributeProperties.TRANSFORM));
	}
	
	public String getTextAnchor() {
		return this.getAttribute(HtmlAttributeProperties.TEXTANCHOR);
	}

	@Override
	public AbstractCSS2Properties getSVGStyle() {
		
		AbstractCSS2Properties style = this.getStyle();
		
		if(style.getStroke() == null){
			style.setStroke(this.getStroke());
		}
		
		if(style.getStrokeDashArray() == null){
			style.setStrokeDashArray(this.getStrokeDashArray());
		}
		
		if(style.getStrokeLineCap() == null){
			style.setStrokeLineCap(this.getStrokeLineCap());
		}
		
		if(style.getStrokeMiterLimit() == null){
			style.setStrokeMiterLimit(this.getStrokeMiterLimit());
		}
		
		if(style.getStrokeOpacity() == null){
			style.setStrokeOpacity(this.getStrokeOpacity());
		}
		
		if(style.getStrokeWidth() == null){
			style.setStrokeWidth(this.getStrokeWidth());
		}
		
		if(style.getFill() == null){
			style.setFill(this.getFill());
		}
		
		return style;
	}
}
