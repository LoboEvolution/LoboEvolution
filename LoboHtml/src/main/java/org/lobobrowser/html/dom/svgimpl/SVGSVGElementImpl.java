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
package org.lobobrowser.html.dom.svgimpl;

import org.lobo.common.Nodes;
import org.lobobrowser.html.dom.svg.SVGAngle;
import org.lobobrowser.html.dom.svg.SVGAnimatedBoolean;
import org.lobobrowser.html.dom.svg.SVGAnimatedLength;
import org.lobobrowser.html.dom.svg.SVGAnimatedPreserveAspectRatio;
import org.lobobrowser.html.dom.svg.SVGAnimatedRect;
import org.lobobrowser.html.dom.svg.SVGAnimatedTransformList;
import org.lobobrowser.html.dom.svg.SVGElement;
import org.lobobrowser.html.dom.svg.SVGException;
import org.lobobrowser.html.dom.svg.SVGLength;
import org.lobobrowser.html.dom.svg.SVGMatrix;
import org.lobobrowser.html.dom.svg.SVGNumber;
import org.lobobrowser.html.dom.svg.SVGPoint;
import org.lobobrowser.html.dom.svg.SVGRect;
import org.lobobrowser.html.dom.svg.SVGSVGElement;
import org.lobobrowser.html.dom.svg.SVGStringList;
import org.lobobrowser.html.dom.svg.SVGTransform;
import org.lobobrowser.html.dom.svg.SVGViewSpec;
import org.lobobrowser.html.style.AbstractCSSProperties;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.events.Event;
import org.w3c.dom.stylesheets.StyleSheetList;
import org.w3c.dom.views.DocumentView;

import com.gargoylesoftware.css.util.CSSProperties;

public class SVGSVGElementImpl extends SVGElementImpl implements SVGSVGElement, CSSProperties {

	public SVGSVGElementImpl(String name) {
		super(name);
	}

	public String getFill() {
		return this.getAttribute(FILL);
	}

	public String getStroke() {
		return this.getAttribute(STROKE);
	}

	public String getStrokeDashArray() {
		return this.getAttribute(STROKE_DASHARRAY);
	}

	public String getStrokeLineCap() {
		return this.getAttribute(STROKE_LINE_CAP);
	}

	public String getStrokeMiterLimit() {
		return this.getAttribute(STROKE_MITERLIMIT);
	}

	public String getStrokeOpacity() {
		return this.getAttribute(STROKE_OPACITY);
	}

	public String getStrokeWidth() {
		return this.getAttribute(STROKE_WIDTH);
	}

	public String getD() {
		String d = this.getAttribute(D);
		if (d != null) {
			d = d.replaceAll("\\r|\\n", "");
		}
		return d;
	}

	@Override
	public SVGStringList getRequiredFeatures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGStringList getRequiredExtensions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGStringList getSystemLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasExtension(String extension) {
		return "svg".equalsIgnoreCase(extension);
	}

	@Override
	public String getXMLlang() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setXMLlang(String xmllang) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getXMLspace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setXMLspace(String xmlspace) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public SVGAnimatedBoolean getExternalResourcesRequired() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CSSValue getPresentationAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGElement getNearestViewportElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGElement getFarthestViewportElement() {
		SVGSVGElement root = getOwnerSVGElement();
		if (root != this) {
			return root;
		} else {
			return null;
		}
	}

	@Override
	public SVGRect getBBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGMatrix getCTM() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGMatrix getScreenCTM() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGMatrix getTransformToElement(SVGElement element) throws SVGException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGAnimatedRect getViewBox() {
		String viewBox = this.getAttribute("viewBox");

		if (viewBox == null) {
			SVGRectImpl rect = (SVGRectImpl) getViewport();
			return new SVGAnimatedRectImpl(rect);
		}

		String[] result = viewBox.split(" ");
		float x = Float.parseFloat(result[0]);
		float y = Float.parseFloat(result[1]);
		float width = Float.parseFloat(result[2]);
		float height = Float.parseFloat(result[3]);
		SVGRectImpl rect = new SVGRectImpl(x, y, width, height);
		return new SVGAnimatedRectImpl(rect);
	}

	@Override
	public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getZoomAndPan() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setZoomAndPan(short zoomAndPan) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public Event createEvent(String eventType) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CSSStyleDeclaration getComputedStyle(Element elt, String pseudoElt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentView getDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CSSStyleDeclaration getOverrideStyle(Element elt, String pseudoElt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StyleSheetList getStyleSheets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGAnimatedLength getX() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("x")));
	}

	@Override
	public SVGAnimatedLength getY() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("y")));
	}

	@Override
	public SVGAnimatedLength getWidth() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute(WIDTH)));
	}

	@Override
	public SVGAnimatedLength getHeight() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute(HEIGHT)));
	}

	@Override
	public String getContentScriptType() {
		return this.getAttribute("contentScriptType");
	}

	@Override
	public void setContentScriptType(String contentScriptType) throws DOMException {
		this.setAttribute("contentScriptType", contentScriptType);

	}

	@Override
	public String getContentStyleType() {
		return this.getAttribute("contentStyleType");
	}

	@Override
	public void setContentStyleType(String contentStyleType) throws DOMException {
		this.setAttribute("contentStyleType", contentStyleType);
	}

	@Override
	public SVGRect getViewport() {
		float x = getX().getBaseVal().getValue();
		float y = getY().getBaseVal().getValue();
		float width = getWidth().getBaseVal().getValue();
		float height = getHeight().getBaseVal().getValue();
		return new SVGRectImpl(x, y, width, height);
	}

	@Override
	public float getPixelUnitToMillimeterX() {
		return (float) 0.28;
	}

	@Override
	public float getPixelUnitToMillimeterY() {
		return (float) 0.28;
	}

	@Override
	public float getScreenPixelToMillimeterX() {
		return (float) 0.28;
	}

	@Override
	public float getScreenPixelToMillimeterY() {
		return (float) 0.28;
	}

	@Override
	public boolean getUseCurrentView() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setUseCurrentView(boolean useCurrentView) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public SVGViewSpec getCurrentView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getCurrentScale() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCurrentScale(float currentScale) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public SVGPoint getCurrentTranslate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int suspendRedraw(int max_wait_milliseconds) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void unsuspendRedraw(int suspend_handle_id) throws DOMException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unsuspendRedrawAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void forceRedraw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pauseAnimations() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unpauseAnimations() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean animationsPaused() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getCurrentTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCurrentTime(float seconds) {
		// TODO Auto-generated method stub

	}

	@Override
	public NodeList getIntersectionList(SVGRect rect, SVGElement referenceElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeList getEnclosureList(SVGRect rect, SVGElement referenceElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkIntersection(SVGElement element, SVGRect rect) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkEnclosure(SVGElement element, SVGRect rect) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deselectAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public SVGNumber createSVGNumber() {
		return new SVGNumberImpl("0");
	}

	@Override
	public SVGLength createSVGLength() {
		return new SVGLengthImpl();
	}

	@Override
	public SVGAngle createSVGAngle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGPoint createSVGPoint() {
		return new SVGPointImpl(0, 0);
	}

	@Override
	public SVGMatrix createSVGMatrix() {
		return new SVGMatrixImpl();
	}

	@Override
	public SVGRect createSVGRect() {
		return new SVGRectImpl(0, 0, 0, 0);
	}

	@Override
	public SVGTransform createSVGTransform() {
		return new SVGTransformImpl();
	}

	@Override
	public SVGTransform createSVGTransformFromMatrix(SVGMatrix matrix) {
		SVGTransform transform = new SVGTransformImpl();
		transform.setMatrix(matrix);
		return transform;
	}

	@Override
	public Element getElementById(String elementId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFontSize() {
		return this.getAttribute("font-size");
	}

	public String getFontFamily() {
		return this.getAttribute("font-family");
	}

	public String getClipPath() {
		return this.getAttribute("clip-path");
	}
	
	public SVGAnimatedTransformList getTransform() {
		return new SVGAnimatedTransformListImpl(this.getAttribute("transform"));
	}

	public SVGAnimationImpl getAnimateElement() {
		NodeList gChildNodes = this.getChildNodes();
		for (Node n : Nodes.iterable(gChildNodes)) {
			if (n instanceof SVGAnimateElementImpl) {
				return (SVGAnimateElementImpl) n;
			}
			
			if (n instanceof SVGAnimateColorElementImpl) {
				return (SVGAnimateColorElementImpl) n;
			}
			if (n instanceof SVGAnimateTransformElementImpl) {
				return (SVGAnimateTransformElementImpl) n;
			} 
		}
		return null;
	}
	
	public AbstractCSSProperties getSVGStyle() {

		AbstractCSSProperties style = this.getStyle();

		if (style.getStroke() == null) {
			style.setStroke(this.getStroke());
		}

		if (style.getStrokeDashArray() == null) {
			style.setStrokeDashArray(this.getStrokeDashArray());
		}

		if (style.getStrokeLineCap() == null) {
			style.setStrokeLineCap(this.getStrokeLineCap());
		}

		if (style.getStrokeMiterLimit() == null) {
			style.setStrokeMiterLimit(this.getStrokeMiterLimit());
		}

		if (style.getStrokeOpacity() == null) {
			style.setStrokeOpacity(this.getStrokeOpacity());
		}

		if (style.getStrokeWidth() == null) {
			style.setStrokeWidth(this.getStrokeWidth());
		}

		if (style.getFill() == null) {
			style.setFill(this.getFill());
		}

		return style;
	}
}
