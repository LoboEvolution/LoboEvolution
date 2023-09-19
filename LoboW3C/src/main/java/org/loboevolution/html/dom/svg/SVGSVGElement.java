/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.svg;


import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.events.Event;

/**
 * <p>SVGSVGElement interface.</p>
 *
 *
 *
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
	 * @throws DOMException if any.
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
	 * @throws DOMException if any.
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
	 * @throws DOMException if any.
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
     * @return a {@link org.loboevolution.html.node.NodeList} object.
     */
    NodeList getIntersectionList(SVGRect rect, SVGElement referenceElement);

    /**
     * <p>getEnclosureList.</p>
     *
     * @param rect a {@link org.loboevolution.html.dom.svg.SVGRect} object.
     * @param referenceElement a {@link org.loboevolution.html.dom.svg.SVGElement} object.
     * @return a {@link org.loboevolution.html.node.NodeList} object.
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
     * @return a {@link org.loboevolution.html.node.Element} object.
     */
    Element getElementById(String elementId);
    
    /**
     * <p>createEvent.</p>
     *
     * @param eventType a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.node.events.Event} object.
     */
    Event createEvent(String eventType);
}
