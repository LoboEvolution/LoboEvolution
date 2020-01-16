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
package org.loboevolution.html.dom.svg;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.DocumentCSS;
import org.w3c.dom.css.ViewCSS;
import org.w3c.dom.events.DocumentEvent;

public interface SVGSVGElement extends SVGElement, SVGTests, SVGLangSpace, SVGExternalResourcesRequired, SVGStylable,
		SVGLocatable, SVGFitToViewBox, SVGZoomAndPan, DocumentEvent, ViewCSS, DocumentCSS {
	SVGAnimatedLength getX();

	SVGAnimatedLength getY();

	SVGAnimatedLength getWidth();

	SVGAnimatedLength getHeight();

	SVGRect getViewport();

	float getPixelUnitToMillimeterX();

	float getPixelUnitToMillimeterY();

	float getScreenPixelToMillimeterX();

	float getScreenPixelToMillimeterY();

	boolean getUseCurrentView();

	void setUseCurrentView(boolean useCurrentView) throws DOMException;

	SVGViewSpec getCurrentView();

	float getCurrentScale();

	void setCurrentScale(float currentScale) throws DOMException;

	SVGPoint getCurrentTranslate();

	int suspendRedraw(int max_wait_milliseconds);

	void unsuspendRedraw(int suspend_handle_id) throws DOMException;

	void unsuspendRedrawAll();

	void forceRedraw();

	void pauseAnimations();

	void unpauseAnimations();

	boolean animationsPaused();

	float getCurrentTime();

	void setCurrentTime(float seconds);

	public NodeList getIntersectionList(SVGRect rect, SVGElement referenceElement);

	public NodeList getEnclosureList(SVGRect rect, SVGElement referenceElement);

	boolean checkIntersection(SVGElement element, SVGRect rect);

	boolean checkEnclosure(SVGElement element, SVGRect rect);

	void deselectAll();

	SVGNumber createSVGNumber();

	SVGLength createSVGLength();

	SVGAngle createSVGAngle();

	SVGPoint createSVGPoint();

	SVGMatrix createSVGMatrix();

	SVGRect createSVGRect();

	SVGTransform createSVGTransform();

	SVGTransform createSVGTransformFromMatrix(SVGMatrix matrix);

	public Element getElementById(String elementId);
}
