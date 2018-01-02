/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.lobobrowser.w3c.svg;

import org.lobobrowser.w3c.events.DocumentEvent;
import org.lobobrowser.w3c.events.EventTarget;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.DocumentCSS;
import org.w3c.dom.css.ViewCSS;

public interface SVGSVGElement extends SVGElement, SVGTests, SVGLangSpace, SVGExternalResourcesRequired, SVGStylable,
		SVGLocatable, SVGFitToViewBox, SVGZoomAndPan, EventTarget, DocumentEvent, ViewCSS, DocumentCSS {
	public SVGAnimatedLength getX();

	public SVGAnimatedLength getY();

	public SVGAnimatedLength getWidth();

	public SVGAnimatedLength getHeight();

	public String getContentScriptType();

	public void setContentScriptType(String contentScriptType) throws DOMException;

	public String getContentStyleType();

	public void setContentStyleType(String contentStyleType) throws DOMException;

	public SVGRect getViewport();

	public float getPixelUnitToMillimeterX();

	public float getPixelUnitToMillimeterY();

	public float getScreenPixelToMillimeterX();

	public float getScreenPixelToMillimeterY();

	public boolean getUseCurrentView();

	public void setUseCurrentView(boolean useCurrentView) throws DOMException;

	public SVGViewSpec getCurrentView();

	public float getCurrentScale();

	public void setCurrentScale(float currentScale) throws DOMException;

	public SVGPoint getCurrentTranslate();

	public int suspendRedraw(int max_wait_milliseconds);

	public void unsuspendRedraw(int suspend_handle_id) throws DOMException;

	public void unsuspendRedrawAll();

	public void forceRedraw();

	public void pauseAnimations();

	public void unpauseAnimations();

	public boolean animationsPaused();

	public float getCurrentTime();

	public void setCurrentTime(float seconds);

	public NodeList getIntersectionList(SVGRect rect, SVGElement referenceElement);

	public NodeList getEnclosureList(SVGRect rect, SVGElement referenceElement);

	public boolean checkIntersection(SVGElement element, SVGRect rect);

	public boolean checkEnclosure(SVGElement element, SVGRect rect);

	public void deselectAll();

	public SVGNumber createSVGNumber();

	public SVGLength createSVGLength();

	public SVGAngle createSVGAngle();

	public SVGPoint createSVGPoint();

	public SVGMatrix createSVGMatrix();

	public SVGRect createSVGRect();

	public SVGTransform createSVGTransform();

	public SVGTransform createSVGTransformFromMatrix(SVGMatrix matrix);

	public Element getElementById(String elementId);
}
