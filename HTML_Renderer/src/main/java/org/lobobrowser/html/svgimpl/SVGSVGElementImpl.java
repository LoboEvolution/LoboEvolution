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
import org.lobobrowser.html.domimpl.HTMLAbstractUIElement;
import org.lobobrowser.html.style.CSSProperties;
import org.lobobrowser.w3c.svg.SVGAngle;
import org.lobobrowser.w3c.svg.SVGAnimatedBoolean;
import org.lobobrowser.w3c.svg.SVGAnimatedLength;
import org.lobobrowser.w3c.svg.SVGAnimatedPreserveAspectRatio;
import org.lobobrowser.w3c.svg.SVGAnimatedRect;
import org.lobobrowser.w3c.svg.SVGElement;
import org.lobobrowser.w3c.svg.SVGException;
import org.lobobrowser.w3c.svg.SVGLength;
import org.lobobrowser.w3c.svg.SVGMatrix;
import org.lobobrowser.w3c.svg.SVGNumber;
import org.lobobrowser.w3c.svg.SVGPoint;
import org.lobobrowser.w3c.svg.SVGRect;
import org.lobobrowser.w3c.svg.SVGSVGElement;
import org.lobobrowser.w3c.svg.SVGStringList;
import org.lobobrowser.w3c.svg.SVGTransform;
import org.lobobrowser.w3c.svg.SVGViewSpec;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventException;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.stylesheets.StyleSheetList;
import org.w3c.dom.views.DocumentView;

public class SVGSVGElementImpl extends HTMLAbstractUIElement implements SVGSVGElement, CSSProperties {

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
            d.replaceAll("\\r|\\n", "");
        }
        return d;
    }

	@Override
	public String getXMLbase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setXMLbase(String xmlbase) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SVGSVGElement getOwnerSVGElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGElement getViewportElement() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
	public void addEventListener(String type, EventListener listener, boolean useCapture) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeEventListener(String type, EventListener listener, boolean useCapture) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean dispatchEvent(Event evt) throws EventException {
		// TODO Auto-generated method stub
		return false;
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
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute(HtmlAttributeProperties.X)));
	}

	@Override
	public SVGAnimatedLength getY() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute(HtmlAttributeProperties.Y)));
	}

	@Override
	public SVGAnimatedLength getWidth() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute(HtmlAttributeProperties.WIDTH)));
	}

	@Override
	public SVGAnimatedLength getHeight() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute(HtmlAttributeProperties.HEIGHT)));
	}

	@Override
	public String getContentScriptType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setContentScriptType(String contentScriptType) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getContentStyleType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setContentStyleType(String contentStyleType) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SVGRect getViewport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getPixelUnitToMillimeterX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getPixelUnitToMillimeterY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getScreenPixelToMillimeterX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getScreenPixelToMillimeterY() {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGLength createSVGLength() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGAngle createSVGAngle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGPoint createSVGPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGMatrix createSVGMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGRect createSVGRect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGTransform createSVGTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGTransform createSVGTransformFromMatrix(SVGMatrix matrix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element getElementById(String elementId) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
