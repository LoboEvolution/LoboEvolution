/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.dom.svg;


import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.events.Event;

/**
 * <p>SVGSVGElement interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SVGSVGElement extends SVGElement, SVGTests, SVGLangSpace, SVGExternalResourcesRequired, SVGStylable,
		SVGLocatable, SVGFitToViewBox, SVGZoomAndPan {
	/**
	 * <p>getX.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getX();

	/**
	 * <p>getY.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getY();

	/**
	 * <p>getWidth.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getWidth();

	/**
	 * <p>getHeight.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAnimatedLength} object.
	 */
	SVGAnimatedLength getHeight();

	/**
	 * <p>getViewport.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGRect} object.
	 */
	SVGRect getViewport();

	/**
	 * <p>getPixelUnitToMillimeterX.</p>
	 *
	 * @return a float.
	 */
	float getPixelUnitToMillimeterX();

	/**
	 * <p>getPixelUnitToMillimeterY.</p>
	 *
	 * @return a float.
	 */
	float getPixelUnitToMillimeterY();

	/**
	 * <p>getScreenPixelToMillimeterX.</p>
	 *
	 * @return a float.
	 */
	float getScreenPixelToMillimeterX();

	/**
	 * <p>getScreenPixelToMillimeterY.</p>
	 *
	 * @return a float.
	 */
	float getScreenPixelToMillimeterY();

	/**
	 * <p>getUseCurrentView.</p>
	 *
	 * @return a boolean.
	 */
	boolean getUseCurrentView();

	/**
	 * <p>setUseCurrentView.</p>
	 *
	 * @param useCurrentView a boolean.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setUseCurrentView(boolean useCurrentView);

	/**
	 * <p>getCurrentView.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGViewSpec} object.
	 */
	SVGViewSpec getCurrentView();

	/**
	 * <p>getCurrentScale.</p>
	 *
	 * @return a float.
	 */
	float getCurrentScale();

	/**
	 * <p>setCurrentScale.</p>
	 *
	 * @param currentScale a float.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setCurrentScale(float currentScale);

	/**
	 * <p>getCurrentTranslate.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 */
	SVGPoint getCurrentTranslate();

	/**
	 * <p>suspendRedraw.</p>
	 *
	 * @param max_wait_milliseconds a int.
	 * @return a int.
	 */
	int suspendRedraw(int max_wait_milliseconds);

	/**
	 * <p>unsuspendRedraw.</p>
	 *
	 * @param suspend_handle_id a int.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void unsuspendRedraw(int suspend_handle_id);

	/**
	 * <p>unsuspendRedrawAll.</p>
	 */
	void unsuspendRedrawAll();

	/**
	 * <p>forceRedraw.</p>
	 */
	void forceRedraw();

	/**
	 * <p>pauseAnimations.</p>
	 */
	void pauseAnimations();

	/**
	 * <p>unpauseAnimations.</p>
	 */
	void unpauseAnimations();

	/**
	 * <p>animationsPaused.</p>
	 *
	 * @return a boolean.
	 */
	boolean animationsPaused();

	/**
	 * <p>getCurrentTime.</p>
	 *
	 * @return a float.
	 */
	float getCurrentTime();

	/**
	 * <p>setCurrentTime.</p>
	 *
	 * @param seconds a float.
	 */
	void setCurrentTime(float seconds);

	/**
	 * <p>getIntersectionList.</p>
	 *
	 * @param rect a {@link org.loboevolution.html.dom.svg.SVGRect} object.
	 * @param referenceElement a {@link org.loboevolution.html.dom.svg.SVGElement} object.
	 * @return a {@link org.w3c.dom.NodeList} object.
	 */
    NodeList getIntersectionList(SVGRect rect, SVGElement referenceElement);

	/**
	 * <p>getEnclosureList.</p>
	 *
	 * @param rect a {@link org.loboevolution.html.dom.svg.SVGRect} object.
	 * @param referenceElement a {@link org.loboevolution.html.dom.svg.SVGElement} object.
	 * @return a {@link org.w3c.dom.NodeList} object.
	 */
    NodeList getEnclosureList(SVGRect rect, SVGElement referenceElement);

	/**
	 * <p>checkIntersection.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.svg.SVGElement} object.
	 * @param rect a {@link org.loboevolution.html.dom.svg.SVGRect} object.
	 * @return a boolean.
	 */
	boolean checkIntersection(SVGElement element, SVGRect rect);

	/**
	 * <p>checkEnclosure.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.svg.SVGElement} object.
	 * @param rect a {@link org.loboevolution.html.dom.svg.SVGRect} object.
	 * @return a boolean.
	 */
	boolean checkEnclosure(SVGElement element, SVGRect rect);

	/**
	 * <p>deselectAll.</p>
	 */
	void deselectAll();

	/**
	 * <p>createSVGNumber.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGNumber} object.
	 */
	SVGNumber createSVGNumber();

	/**
	 * <p>createSVGLength.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGLength} object.
	 */
	SVGLength createSVGLength();

	/**
	 * <p>createSVGAngle.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGAngle} object.
	 */
	SVGAngle createSVGAngle();

	/**
	 * <p>createSVGPoint.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 */
	SVGPoint createSVGPoint();

	/**
	 * <p>createSVGMatrix.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 */
	SVGMatrix createSVGMatrix();

	/**
	 * <p>createSVGRect.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGRect} object.
	 */
	SVGRect createSVGRect();

	/**
	 * <p>createSVGTransform.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.svg.SVGTransform} object.
	 */
	SVGTransform createSVGTransform();

	/**
	 * <p>createSVGTransformFromMatrix.</p>
	 *
	 * @param matrix a {@link org.loboevolution.html.dom.svg.SVGMatrix} object.
	 * @return a {@link org.loboevolution.html.dom.svg.SVGTransform} object.
	 */
	SVGTransform createSVGTransformFromMatrix(SVGMatrix matrix);

	/**
	 * <p>getElementById.</p>
	 *
	 * @param elementId a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.Element} object.
	 */
    Element getElementById(String elementId);
    
    Event createEvent(String eventType);
}
