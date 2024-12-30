/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
package org.loboevolution.html.dom.canvas;

import org.loboevolution.html.dom.*;
import org.loboevolution.html.node.Element;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.List;

/**
 * The interface CanvasRenderingContext2D.
 */
public interface CanvasRenderingContext2D extends CanvasRenderingContext {

	/**
	 * The CanvasRenderingContext2D.direction property of the Canvas 2D API specifies the current text direction used to draw text.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/direction">CanvasRenderingContext2D.direction - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-direction">CanvasRenderingContext2D.direction - HTML Living Standard</a>
	 */
	String getDirection();

	/**
	 * The CanvasRenderingContext2D.fillStyle property of the Canvas 2D API specifies the color, gradient, or pattern to use inside shapes. The default style is #000 (black).
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/fillStyle">CanvasRenderingContext2D.fillStyle - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-fillstyle">CanvasRenderingContext2D.fillStyle - HTML Living Standard</a>
	 */
	Object getFillStyle();

	/**
	 * The CanvasRenderingContext2D.filter property of the Canvas 2D API provides filter effects such as blurring and grayscaling. It is similar to the CSS filter property and accepts the same values.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/filter">CanvasRenderingContext2D.filter - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#filters">CanvasRenderingContext2D.filter - HTML Living Standard</a>
	 */
	Object getFilter();

	/**
	 * The CanvasRenderingContext2D.font property of the Canvas 2D API specifies the current text style to use when drawing text. This string uses the same syntax as the CSS font specifier.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/font">CanvasRenderingContext2D.font - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-font">CanvasRenderingContext2D.font - HTML Living Standard</a>
	 */
	Font getFont();

	String getFontKerning();

	String getFontStretch();

	String getFontVariantCaps();

	/**
	 * The CanvasRenderingContext2D.globalAlpha property of the Canvas 2D API specifies the alpha (transparency) value that is applied to shapes and images before they are drawn onto the canvas.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/globalAlpha">CanvasRenderingContext2D.globalAlpha - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-globalalpha">CanvasRenderingContext2D.globalAlpha - HTML Living Standard</a>
	 */
	Float getGlobalAlpha();

	/**
	 * The CanvasRenderingContext2D.globalCompositeOperation property of the Canvas 2D API sets the type of compositing operation to apply when drawing new shapes.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/globalCompositeOperation">CanvasRenderingContext2D.globalCompositeOperation - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-globalcompositeoperation">CanvasRenderingContext2D.globalCompositeOperation - HTML Living Standard</a>
	 * @see <a href="https://drafts.fxtf.org/compositing-1/">Compositing and Blending Level 1</a>
	 */
	String getGlobalCompositeOperation();

	/**
	 * The imageSmoothingEnabled property of the CanvasRenderingContext2D interface, part of the Canvas API, determines whether scaled images are smoothed (true, default) or not (false). On getting the imageSmoothingEnabled property, the last value it was set to is returned.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/imageSmoothingEnabled">CanvasRenderingContext2D.imageSmoothingEnabled - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-imagesmoothingenabled">CanvasRenderingContext2D.imageSmoothingEnabled - HTML Living Standard</a>
	 */
	boolean getImageSmoothingEnabled();

	/**
	 * The imageSmoothingQuality property of the CanvasRenderingContext2D interface, part of the Canvas API, lets you set the quality of image smoothing.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/imageSmoothingQuality">CanvasRenderingContext2D.imageSmoothingQuality - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/#imagesmoothingquality">imageSmoothingQuality - HTML Living Standard</a>
	 */
	String getImageSmoothingQuality();

	String getLetterSpacing();

	/**
	 * The CanvasRenderingContext2D.lineCap property of the Canvas 2D API determines the shape used to draw the end points of lines.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineCap">CanvasRenderingContext2D.lineCap - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-linecap">CanvasRenderingContext2D.lineCap - HTML Living Standard</a>
	 */
	String getLineCap();

	/**
	 * The CanvasRenderingContext2D.lineDashOffset property of the Canvas 2D API sets the line dash offset, or &quot();phase.&quot();
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineDashOffset">CanvasRenderingContext2D.lineDashOffset - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-linedashoffset">CanvasRenderingContext2D.lineDashOffset - HTML Living Standard</a>
	 */
	Integer lineDashOffset();

	/**
	 * The CanvasRenderingContext2D.lineJoin property of the Canvas 2D API determines the shape used to join two line segments where they meet.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineJoin">CanvasRenderingContext2D.lineJoin - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-linejoin">CanvasRenderingContext2D.lineJoin - HTML Living Standard</a>
	 */
	String getLineJoin();

	/**
	 * The CanvasRenderingContext2D.lineWidth property of the Canvas 2D API sets the thickness of lines.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineWidth">CanvasRenderingContext2D.lineWidth - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-linewidth">CanvasRenderingContext2D.lineWidth - HTML Living Standard</a>
	 */
	Integer getLineWidth();

	/**
	 * The CanvasRenderingContext2D.miterLimit property of the Canvas 2D API sets the miter limit ratio.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/miterLimit">CanvasRenderingContext2D.miterLimit - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-miterlimit">CanvasRenderingContext2D.miterLimit - HTML Living Standard</a>
	 */
	Integer getMiterLimit();

	/**
	 * The CanvasRenderingContext2D.shadowBlur property of the Canvas 2D API specifies the amount of blur applied to shadows. The default is 0 (no blur).
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/shadowBlur">CanvasRenderingContext2D.shadowBlur - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-shadowblur">CanvasRenderingContext2D.shadowBlur - HTML Living Standard</a>
	 */
	Integer getShadowBlur();

	/**
	 * The CanvasRenderingContext2D.shadowColor property of the Canvas 2D API specifies the color of shadows.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/shadowColor">CanvasRenderingContext2D.shadowColor - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-shadowcolor">CanvasRenderingContext2D.shadowColor - HTML Living Standard</a>
	 */
	String getShadowColor();

	/**
	 * The CanvasRenderingContext2D.shadowOffsetX property of the Canvas 2D API specifies the distance that shadows will be offset horizontally.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/shadowOffsetX">CanvasRenderingContext2D.shadowOffsetX - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-shadowoffsetx">CanvasRenderingContext2D.shadowOffsetX - HTML Living Standard</a>
	 */
	Integer getShadowOffsetX();

	/**
	 * The CanvasRenderingContext2D.shadowOffsetY property of the Canvas 2D API specifies the distance that shadows will be offset vertically.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/shadowOffsetY">CanvasRenderingContext2D.shadowOffsetY - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-shadowoffsety">CanvasRenderingContext2D.shadowOffsetY - HTML Living Standard</a>
	 */
	Integer getShadowOffsetY();

	/**
	 * The CanvasRenderingContext2D.strokeStyle property of the Canvas 2D API specifies the color, gradient, or pattern to use for the strokes (outlines) around shapes. The default is #000 (black).
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/strokeStyle">CanvasRenderingContext2D.strokeStyle - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-strokestyle">CanvasRenderingContext2D.strokeStyle - HTML Living Standard</a>
	 */
	Object getStrokeStyle();

	/**
	 * The CanvasRenderingContext2D.textAlign property of the Canvas 2D API specifies the current text alignment used when drawing text.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/textAlign">CanvasRenderingContext2D.textAlign - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-textalign">CanvasRenderingContext2D.textAlign - HTML Living Standard</a>
	 */
	String getTextAlign();

	/**
	 * The CanvasRenderingContext2D.textBaseline property of the Canvas 2D API specifies the current text baseline used when drawing text.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/textBaseline">CanvasRenderingContext2D.textBaseline - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-textbaseline">CanvasRenderingContext2D.textBaseline - HTML Living Standard</a>
	 */
	String getTextBaseline();

	String getTextRendering();

	String getWordSpacing();

	/**
	 * The CanvasRenderingContext2D.canvas property, part of the Canvas API, is a read-only reference to the HTMLCanvasElement object that is associated with a given context. It might be null if there is no associated canvas element.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/canvas">CanvasRenderingContext2D.canvas - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-canvas">CanvasRenderingContext2D.canvas - HTML Living Standard</a>
	 */
	HTMLCanvasElement getCanvas();

	CanvasRenderingContext2DSettings getContextAttributes();

	/**
	 * The CanvasRenderingContext2D.drawImage() method of the Canvas 2D API provides different ways to draw an image onto the canvas.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/drawImage">CanvasRenderingContext2D.drawImage - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/#dom-context-2d-drawimage">CanvasRenderingContext2D: drawImage - HTML Living Standard</a>
	 */
	void drawImage(CanvasImageSource image, Integer dx, Integer dy);

	/**
	 * The CanvasRenderingContext2D.drawImage() method of the Canvas 2D API provides different ways to draw an image onto the canvas.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/drawImage">CanvasRenderingContext2D.drawImage - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/#dom-context-2d-drawimage">CanvasRenderingContext2D: drawImage - HTML Living Standard</a>
	 */
	void drawImage(CanvasImageSource image, Integer dx, Integer dy, Integer dw,
								 Integer dh);

	/**
	 * The CanvasRenderingContext2D.drawImage() method of the Canvas 2D API provides different ways to draw an image onto the canvas.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/drawImage">CanvasRenderingContext2D.drawImage - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/#dom-context-2d-drawimage">CanvasRenderingContext2D: drawImage - HTML Living Standard</a>
	 */
	void drawImage(CanvasImageSource image, Integer getSx, Integer getSy, Integer getSw,
								 Integer getSh, Integer dx, Integer dy, Integer dw, Integer dh);

	/**
	 * The CanvasRenderingContext2D method fillText(), part of the Canvas 2D API, draws a text string at the specified coordinates, filling the string's characters with the current fillStyle. An optional parameter allows specifying a maximum width for the rendered text, which the user agent will achieve by condensing the text or by using a lower font size.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/fillText">CanvasRenderingContext2D.fillText - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-filltext">CanvasRenderingContext2D.fillText - HTML Living Standard</a>
	 */
	void fillText(String text, Integer x, Integer y, Integer maxWidth);

	/**
	 * The CanvasRenderingContext2D method fillText(), part of the Canvas 2D API, draws a text string at the specified coordinates, filling the string's characters with the current fillStyle. An optional parameter allows specifying a maximum width for the rendered text, which the user agent will achieve by condensing the text or by using a lower font size.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/fillText">CanvasRenderingContext2D.fillText - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-filltext">CanvasRenderingContext2D.fillText - HTML Living Standard</a>
	 */
	void fillText(String text, Integer x, Integer y);

	/**
	 * The CanvasRenderingContext2D.measureText() method returns a TextMetrics object that contains information about the measured text (such as its width, for example).
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/measureText">CanvasRenderingContext2D.measureText - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-measuretext">CanvasRenderingContext2D.measureText - HTML Living Standard</a>
	 */
	TextMetrics measureText(String text);

	/**
	 * The CanvasRenderingContext2D method strokeText(), part of the Canvas 2D API, strokes &mdash(); that is, draws the outlines of &mdash(); the characters of a text string at the specified coordinates. An optional parameter allows specifying a maximum width for the rendered text, which the user agent will achieve by condensing the text or by using a lower font size.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/strokeText">CanvasRenderingContext2D.strokeText - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-stroketext">CanvasRenderingContext2D.strokeText - HTML Living Standard</a>
	 */
	void strokeText(String text, Integer x, Integer y, Integer maxWidth);

	/**
	 * The CanvasRenderingContext2D method strokeText(), part of the Canvas 2D API, strokes &mdash(); that is, draws the outlines of &mdash(); the characters of a text string at the specified coordinates. An optional parameter allows specifying a maximum width for the rendered text, which the user agent will achieve by condensing the text or by using a lower font size.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/strokeText">CanvasRenderingContext2D.strokeText - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-stroketext">CanvasRenderingContext2D.strokeText - HTML Living Standard</a>
	 */
	void strokeText(String text, Integer x, Integer y);

	CanvasGradient createConicGradient(Integer startAngle, Integer x, Integer y);

	/**
	 * The CanvasRenderingContext2D.createLinearGradient() method of the Canvas 2D API creates a gradient along the line connecting two given coordinates.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/createLinearGradient">CanvasRenderingContext2D.createLinearGradient - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-createlineargradient">CanvasRenderingContext2D.createLinearGradient - HTML Living Standard</a>
	 */
	CanvasGradient createLinearGradient(Integer x0, Integer y0, Integer x1, Integer y1);

	/**
	 * The CanvasRenderingContext2D.createPattern() method of the Canvas 2D API creates a pattern using the specified image and repetition. This method returns a CanvasPattern.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/createPattern">CanvasRenderingContext2D.createPattern - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-createpattern">CanvasRenderingContext2D.createPattern - HTML Living Standard</a>
	 */
	CanvasPattern createPattern(CanvasImageSource image,
											  String repetition);

	/**
	 * The CanvasRenderingContext2D.createRadialGradient() method of the Canvas 2D API creates a radial gradient using the size and coordinates of two circles.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/createRadialGradient">CanvasRenderingContext2D.createRadialGradient - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-createradialgradient">CanvasRenderingContext2D.createRadialGradient - HTML Living Standard</a>
	 */
	CanvasGradient createRadialGradient(Integer x0, Integer y0, Integer r0, Integer x1,
													  Integer y1, Integer r1);

	/**
	 * The CanvasRenderingContext2D.beginPath() method of the Canvas 2D API starts a new path by emptying the list of sub-paths. Call this method when you want to create a new path.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/beginPath">CanvasRenderingContext2D.beginPath - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-beginpath">CanvasRenderingContext2D.beginPath - HTML Living Standard</a>
	 */
	void beginPath();

	/**
	 * The CanvasRenderingContext2D.clip() method of the Canvas 2D API turns the current or given path into the current clipping region. The previous clipping region, if any, is intersected with the current or given path to create the new clipping region.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/clip">CanvasRenderingContext2D.clip - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-clip">CanvasRenderingContext2D.clip - HTML Living Standard</a>
	 */
	void clip(String fillRule);

	/**
	 * The CanvasRenderingContext2D.clip() method of the Canvas 2D API turns the current or given path into the current clipping region. The previous clipping region, if any, is intersected with the current or given path to create the new clipping region.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/clip">CanvasRenderingContext2D.clip - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-clip">CanvasRenderingContext2D.clip - HTML Living Standard</a>
	 */
	void clip();

	/**
	 * The CanvasRenderingContext2D.clip() method of the Canvas 2D API turns the current or given path into the current clipping region. The previous clipping region, if any, is intersected with the current or given path to create the new clipping region.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/clip">CanvasRenderingContext2D.clip - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-clip">CanvasRenderingContext2D.clip - HTML Living Standard</a>
	 */
	void clip(Path2D path, String fillRule);

	/**
	 * The CanvasRenderingContext2D.clip() method of the Canvas 2D API turns the current or given path into the current clipping region. The previous clipping region, if any, is intersected with the current or given path to create the new clipping region.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/clip">CanvasRenderingContext2D.clip - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-clip">CanvasRenderingContext2D.clip - HTML Living Standard</a>
	 */
	void clip(Path2D path);

	/**
	 * The CanvasRenderingContext2D.fill() method of the Canvas 2D API fills the current or given path with the current fillStyle.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/fill">CanvasRenderingContext2D.fill - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-fill">CanvasRenderingContext2D.fill - HTML Living Standard</a>
	 */
	void fill(String fillRule);

	/**
	 * The CanvasRenderingContext2D.fill() method of the Canvas 2D API fills the current or given path with the current fillStyle.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/fill">CanvasRenderingContext2D.fill - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-fill">CanvasRenderingContext2D.fill - HTML Living Standard</a>
	 */
	void fill();

	/**
	 * The CanvasRenderingContext2D.fill() method of the Canvas 2D API fills the current or given path with the current fillStyle.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/fill">CanvasRenderingContext2D.fill - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-fill">CanvasRenderingContext2D.fill - HTML Living Standard</a>
	 */
	void fill(Path2D path, String fillRule);

	/**
	 * The CanvasRenderingContext2D.fill() method of the Canvas 2D API fills the current or given path with the current fillStyle.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/fill">CanvasRenderingContext2D.fill - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-fill">CanvasRenderingContext2D.fill - HTML Living Standard</a>
	 */
	void fill(Path2D path);

	/**
	 * The CanvasRenderingContext2D.isPointInPath() method of the Canvas 2D API reports whether or not the specified poInteger is contained in the current path.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/isPointInPath">CanvasRenderingContext2D.isPointInPath - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-ispointinpath">CanvasRenderingContext2D.isPointInPath - HTML Living Standard</a>
	 */
	boolean isPointInPath(Integer x, Integer y, String fillRule);

	/**
	 * The CanvasRenderingContext2D.isPointInPath() method of the Canvas 2D API reports whether or not the specified poInteger is contained in the current path.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/isPointInPath">CanvasRenderingContext2D.isPointInPath - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-ispointinpath">CanvasRenderingContext2D.isPointInPath - HTML Living Standard</a>
	 */
	boolean isPointInPath(Integer x, Integer y);

	/**
	 * The CanvasRenderingContext2D.isPointInPath() method of the Canvas 2D API reports whether or not the specified poInteger is contained in the current path.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/isPointInPath">CanvasRenderingContext2D.isPointInPath - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-ispointinpath">CanvasRenderingContext2D.isPointInPath - HTML Living Standard</a>
	 */
	boolean isPointInPath(Path2D path, Integer x, Integer y,
										String fillRule);

	/**
	 * The CanvasRenderingContext2D.isPointInPath() method of the Canvas 2D API reports whether or not the specified poInteger is contained in the current path.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/isPointInPath">CanvasRenderingContext2D.isPointInPath - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-ispointinpath">CanvasRenderingContext2D.isPointInPath - HTML Living Standard</a>
	 */
	boolean isPointInPath(Path2D path, Integer x, Integer y);

	/**
	 * The CanvasRenderingContext2D.isPointInStroke() method of the Canvas 2D API reports whether or not the specified poInteger is inside the area contained by the stroking of a path.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/isPointInStroke">CanvasRenderingContext2D.isPointInStroke - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-ispointinstroke">CanvasRenderingContext2D.isPointInStroke - HTML Living Standard</a>
	 */
	boolean isPointInStroke(Integer x, Integer y);

	/**
	 * The CanvasRenderingContext2D.isPointInStroke() method of the Canvas 2D API reports whether or not the specified poInteger is inside the area contained by the stroking of a path.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/isPointInStroke">CanvasRenderingContext2D.isPointInStroke - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-ispointinstroke">CanvasRenderingContext2D.isPointInStroke - HTML Living Standard</a>
	 */
	boolean isPointInStroke(Path2D path, Integer x, Integer y);

	/**
	 * The CanvasRenderingContext2D.stroke() method of the Canvas 2D API strokes (outlines) the current or given path with the current stroke style.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/stroke">CanvasRenderingContext2D.stroke - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-stroke">CanvasRenderingContext2D.stroke - HTML Living Standard</a>
	 */
	void stroke();

	/**
	 * The CanvasRenderingContext2D.stroke() method of the Canvas 2D API strokes (outlines) the current or given path with the current stroke style.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/stroke">CanvasRenderingContext2D.stroke - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-stroke">CanvasRenderingContext2D.stroke - HTML Living Standard</a>
	 */
	void stroke(Path2D path);

	/**
	 * The CanvasRenderingContext2D.createImageData() method of the Canvas 2D API creates a new, blank ImageData object with the specified dimensions. All of the pixels in the new object are transparent black.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/createImageData">CanvasRenderingContext2D.createImageData - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-createimagedata">CanvasRenderingContext2D.createImageData - HTML Living Standard</a>
	 */
	ImageData createImageData(Integer sw, Integer sh, ImageDataSettings settings);

	/**
	 * The CanvasRenderingContext2D.createImageData() method of the Canvas 2D API creates a new, blank ImageData object with the specified dimensions. All of the pixels in the new object are transparent black.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/createImageData">CanvasRenderingContext2D.createImageData - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-createimagedata">CanvasRenderingContext2D.createImageData - HTML Living Standard</a>
	 */
	ImageData createImageData(Integer sw, Integer sh);

	/**
	 * The CanvasRenderingContext2D.createImageData() method of the Canvas 2D API creates a new, blank ImageData object with the specified dimensions. All of the pixels in the new object are transparent black.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/createImageData">CanvasRenderingContext2D.createImageData - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-createimagedata">CanvasRenderingContext2D.createImageData - HTML Living Standard</a>
	 */
	ImageData createImageData(ImageData imagedata);

	/**
	 * The CanvasRenderingContext2D method getImageData() of the Canvas 2D API returns an ImageData object representing the underlying pixel data for a specified portion of the canvas.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/getImageData">CanvasRenderingContext2D.getImageData - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-getimagedata">CanvasRenderingContext2D.getImageData - HTML Living Standard</a>
	 */
	ImageData getImageData(Integer sx, Integer sy, Integer sw, Integer sh,
										 ImageDataSettings settings);

	/**
	 * The CanvasRenderingContext2D method getImageData() of the Canvas 2D API returns an ImageData object representing the underlying pixel data for a specified portion of the canvas.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/getImageData">CanvasRenderingContext2D.getImageData - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-getimagedata">CanvasRenderingContext2D.getImageData - HTML Living Standard</a>
	 */
	ImageData getImageData(Integer sx, Integer sy, Integer sw, Integer sh);

	/**
	 * The CanvasRenderingContext2D.putImageData() method of the Canvas 2D API paints data from the given ImageData object onto the canvas. If a dirty rectangle is provided, only the pixels from that rectangle are painted. This method is not affected by the canvas transformation matrix.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/putImageData">CanvasRenderingContext2D.putImageData - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-putimagedata">CanvasRenderingContext2D.putImageData - HTML Living Standard</a>
	 */
	void putImageData(ImageData imagedata, Integer dx, Integer dy);

	/**
	 * The CanvasRenderingContext2D.putImageData() method of the Canvas 2D API paints data from the given ImageData object onto the canvas. If a dirty rectangle is provided, only the pixels from that rectangle are painted. This method is not affected by the canvas transformation matrix.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/putImageData">CanvasRenderingContext2D.putImageData - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-putimagedata">CanvasRenderingContext2D.putImageData - HTML Living Standard</a>
	 */
	void putImageData(ImageData imagedata, Integer dx, Integer dy, Integer dirtyX,
									Integer dirtyY, Integer dirtyWidth, Integer dirtyHeight);

	/**
	 * The CanvasRenderingContext2D.arc() method of the Canvas 2D API adds a circular arc to the current sub-path.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/arc">CanvasRenderingContext2D.arc - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-arc">CanvasRenderingContext2D.arc - HTML Living Standard</a>
	 */
	void arc(Double x, Double y, Double radius, Integer startAngle, Double endAngle, boolean counterclockwise);

	/**
	 * The CanvasRenderingContext2D.arc() method of the Canvas 2D API adds a circular arc to the current sub-path.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/arc">CanvasRenderingContext2D.arc - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-arc">CanvasRenderingContext2D.arc - HTML Living Standard</a>
	 */
	void arc(Double x, Double y, Double radius, Integer startAngle, Double endAngle);

	/**
	 * The CanvasRenderingContext2D.arcTo() method of the Canvas 2D API adds a circular arc to the current sub-path, using the given control points and radius. The arc is automatically connected to the path's latest poInteger with a straight line, if necessary for the specified parameters.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/arcTo">CanvasRenderingContext2D.arcTo - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-arcto">CanvasRenderingContext2D.arcTo - HTML Living Standard</a>
	 */
	void arcTo(Integer x1, Integer y1, Integer x2, Integer y2, Integer radius);

	/**
	 * The CanvasRenderingContext2D.bezierCurveTo() method of the Canvas 2D API adds a cubic B&eacute();zier curve to the current sub-path. It requires three points: the first two are control points and the third one is the end point. The starting poInteger is the latest poInteger in the current path, which can be changed using moveTo() before creating the B&eacute();zier curve.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/bezierCurveTo">CanvasRenderingContext2D.bezierCurveTo - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-beziercurveto">CanvasRenderingContext2D.beziercurveto - HTML Living Standard</a>
	 */
	void bezierCurveTo(Integer cp1x, Integer cp1y, Integer cp2x, Integer cp2y, Integer x,
									 Integer y);

	/**
	 * The CanvasRenderingContext2D.closePath() method of the Canvas 2D API attempts to add a straight line from the current poInteger to the start of the current sub-path. If the shape has already been closed or has only one point, this function does nothing.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/closePath">CanvasRenderingContext2D.closePath - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-closepath">CanvasRenderingContext2D.closePath - HTML Living Standard</a>
	 */
	void closePath();

	/**
	 * The CanvasRenderingContext2D.ellipse() method of the Canvas 2D API adds an elliptical arc to the current sub-path.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/ellipse">CanvasRenderingContext2D.ellipse - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-ellipse">CanvasRenderingContext2D.ellipse - HTML Living Standard</a>
	 */
	void ellipse(Integer x, Integer y, Integer radiusX, Integer radiusY, Integer rotation,
							   Integer startAngle, Integer endAngle, boolean counterclockwise);

	/**
	 * The CanvasRenderingContext2D.ellipse() method of the Canvas 2D API adds an elliptical arc to the current sub-path.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/ellipse">CanvasRenderingContext2D.ellipse - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-ellipse">CanvasRenderingContext2D.ellipse - HTML Living Standard</a>
	 */
	void ellipse(Integer x, Integer y, Integer radiusX, Integer radiusY, Integer rotation,
							   Integer startAngle, Integer endAngle);

	/**
	 * The CanvasRenderingContext2D method lineTo(), part of the Canvas 2D API, adds a straight line to the current sub-path by connecting the sub-path's last poInteger to the specified (x, y) coordinates.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineTo">CanvasRenderingContext2D.lineTo - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-lineto">CanvasRenderingContext2D.lineTo - HTML Living Standard</a>
	 */
	void lineTo(Integer x, Integer y);

	/**
	 * The CanvasRenderingContext2D.moveTo() method of the Canvas 2D API begins a new sub-path at the poInteger specified by the given (x, y) coordinates.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/moveTo">CanvasRenderingContext2D.moveTo - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-moveto">CanvasRenderingContext2D.moveTo - HTML Living Standard</a>
	 */
	void moveTo(Integer x, Integer y);

	/**
	 * The CanvasRenderingContext2D.quadraticCurveTo() method of the Canvas 2D API adds a quadratic B&eacute();zier curve to the current sub-path. It requires two points: the first one is a control poInteger and the second one is the end point. The starting poInteger is the latest poInteger in the current path, which can be changed using moveTo() before creating the quadratic B&eacute();zier curve.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/quadraticCurveTo">CanvasRenderingContext2D.quadraticCurveTo - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-quadraticcurveto">CanvasRenderingContext2D.quadraticCurveTo - HTML Living Standard</a>
	 */
	void quadraticCurveTo(Integer cpx, Integer cpy, Integer x, Integer y);

	/**
	 * The CanvasRenderingContext2D.rect() method of the Canvas 2D API adds a rectangle to the current path.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/rect">CanvasRenderingContext2D.rect - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-rect">CanvasRenderingContext2D.rect - HTML Living Standard</a>
	 */
	void rect(Integer x, Integer y, Integer w, Integer h);

	void roundRect(Integer x, Integer y, Integer w, Integer h, Integer radii);

	/**
	 * The CanvasRenderingContext2D.clearRect() method of the Canvas 2D API erases the pixels in a rectangular area by setting them to transparent black.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/clearRect">CanvasRenderingContext2D.clearRect - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-clearrect">CanvasRenderingContext2D.clearRect - HTML Living Standard</a>
	 */
	void clearRect(Integer x, Integer y, Integer w, Integer h);

	/**
	 * The CanvasRenderingContext2D.fillRect() method of the Canvas 2D API draws a rectangle that is filled according to the current fillStyle.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/fillRect">CanvasRenderingContext2D.fillRect - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-fillrect">CanvasRenderingContext2D.fillRect - HTML Living Standard</a>
	 */
	void fillRect(Integer x, Integer y, Integer w, Integer h);

	/**
	 * The CanvasRenderingContext2D.strokeRect() method of the Canvas 2D API draws a rectangle that is stroked (outlined) according to the current strokeStyle and other context settings.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/strokeRect">CanvasRenderingContext2D.strokeRect - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-strokerect">CanvasRenderingContext2D.strokeRect - HTML Living Standard</a>
	 */
	void strokeRect(Integer x, Integer y, Integer w, Integer h);

	boolean isContextLost();

	void reset();

	/**
	 * The CanvasRenderingContext2D.restore() method of the Canvas 2D API restores the most recently saved canvas state by popping the top entry in the drawing state stack. If there is no saved state, this method does nothing.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/restore">CanvasRenderingContext2D.restore - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-restore">CanvasRenderingContext2D.restore - HTML Living Standard</a>
	 */
	void restore();

	/**
	 * The CanvasRenderingContext2D.save() method of the Canvas 2D API saves the entire state of the canvas by pushing the current state onto a stack.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/save">CanvasRenderingContext2D.save - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-save">CanvasRenderingContext2D.save - HTML Living Standard</a>
	 */
	void save();

	/**
	 * The getLineDash() method of the Canvas 2D API's CanvasRenderingContext2D interface gets the current line dash pattern.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/getLineDash">CanvasRenderingContext2D.getLineDash - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-getlinedash">CanvasRenderingContext2D.getLineDash - HTML Living Standard</a>
	 */
	List<Integer> getLineDash();

	/**
	 * The setLineDash() method of the Canvas 2D API's CanvasRenderingContext2D interface sets the line dash pattern used when stroking lines. It uses an array of values that specify alternating lengths of lines and gaps which describe the pattern.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/setLineDash">CanvasRenderingContext2D.setLineDash - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-setlinedash">CanvasRenderingContext2D.setLineDash - HTML Living Standard</a>
	 */
	void setLineDash(List<Integer> segments);

	void setFillStyle(Object style);

	void setFont(String fontSpec);

	void setGlobalAlpha(Float globalAlpha);

	void setLineCap(String lineCap);

	void setLineJoin(String lineJoin);

	void setStrokeStyle(Object style);

	/**
	 * The CanvasRenderingContext2D.drawFocusIfNeeded() method of the Canvas 2D API draws a focus ring around the current or given path, if the specified element is focused.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/drawFocusIfNeeded">CanvasRenderingContext2D.drawFocusIfNeeded - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-drawfocusifneeded">CanvasRenderingContext2D.drawFocusIfNeeded - HTML Living Standard</a>
	 */
	void drawFocusIfNeeded(Element element);

	/**
	 * The CanvasRenderingContext2D.drawFocusIfNeeded() method of the Canvas 2D API draws a focus ring around the current or given path, if the specified element is focused.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/drawFocusIfNeeded">CanvasRenderingContext2D.drawFocusIfNeeded - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-drawfocusifneeded">CanvasRenderingContext2D.drawFocusIfNeeded - HTML Living Standard</a>
	 */
	void drawFocusIfNeeded(Path2D path, Element element);

	/**
	 * The CanvasRenderingContext2D.scrollPathIntoView() method of the Canvas 2D API scrolls the current or given path into view. It is similar to Element.scrollIntoView().
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/scrollPathIntoView">CanvasRenderingContext2D.scrollPathIntoView - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-scrollpathintoview">CanvasRenderingContext2D.scrollPathIntoView - HTML Living Standard</a>
	 */
	void scrollPathIntoView();

	/**
	 * The CanvasRenderingContext2D.scrollPathIntoView() method of the Canvas 2D API scrolls the current or given path into view. It is similar to Element.scrollIntoView().
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/scrollPathIntoView">CanvasRenderingContext2D.scrollPathIntoView - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-scrollpathintoview">CanvasRenderingContext2D.scrollPathIntoView - HTML Living Standard</a>
	 */
	void scrollPathIntoView(Path2D path);

	/**
	 * The CanvasRenderingContext2D.getTransform() method of the Canvas 2D API retrieves the current transformation matrix being applied to the context.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/getTransform">CanvasRenderingContext2D.getTransform - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-gettransform">CanvasRenderingContext2D.getTransform - HTML Living Standard</a>
	 */
	DOMMatrix getTransform();

	/**
	 * The CanvasRenderingContext2D.resetTransform() method of the Canvas 2D API resets the current transform to the identity matrix.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/resetTransform">CanvasRenderingContext2D.resetTransform - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-resettransform">CanvasRenderingContext2D.resetTransform - HTML Living Standard</a>
	 */
	void resetTransform();

	/**
	 * The CanvasRenderingContext2D.rotate() method of the Canvas 2D API adds a rotation to the transformation matrix.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/rotate">CanvasRenderingContext2D.rotate - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-rotate">CanvasRenderingContext2D.rotate - HTML Living Standard</a>
	 */
	void rotate(Double angle);

	/**
	 * The CanvasRenderingContext2D.scale() method of the Canvas 2D API adds a scaling transformation to the canvas units horizontally and/or vertically.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/scale">CanvasRenderingContext2D.scale - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-scale">CanvasRenderingContext2D.scale - HTML Living Standard</a>
	 */
	void scale(Integer x, Integer y);

	/**
	 * The CanvasRenderingContext2D.setTransform() method of the Canvas 2D API resets (overrides) the current transformation to the identity matrix, and then invokes a transformation described by the arguments of this method. This lets you scale, rotate, translate (move), and skew the context.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/setTransform">CanvasRenderingContext2D.setTransform - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-settransform">CanvasRenderingContext2D.setTransform - HTML Living Standard</a>
	 */
	void setTransform(Integer a, Integer b, Integer c, Integer d, Integer e, Integer f);

	/**
	 * The CanvasRenderingContext2D.setTransform() method of the Canvas 2D API resets (overrides) the current transformation to the identity matrix, and then invokes a transformation described by the arguments of this method. This lets you scale, rotate, translate (move), and skew the context.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/setTransform">CanvasRenderingContext2D.setTransform - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-settransform">CanvasRenderingContext2D.setTransform - HTML Living Standard</a>
	 */
	void setTransform(DOMMatrix2DInit transform);

	/**
	 * The CanvasRenderingContext2D.transform() method of the Canvas 2D API multiplies the current transformation with the matrix described by the arguments of this method. This lets you scale, rotate, translate (move), and skew the context.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/transform">CanvasRenderingContext2D.transform - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-transform">CanvasRenderingContext2D.transform - HTML Living Standard</a>
	 */
	void transform(Integer a, Integer b, Integer c, Integer d, Integer e, Integer f);

	/**
	 * The CanvasRenderingContext2D.translate() method of the Canvas 2D API adds a translation transformation to the current matrix.
	 *
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/translate">CanvasRenderingContext2D.translate - MDN</a>
	 * @see <a href="https://html.spec.whatwg.org/multipage/scripting.html#dom-context-2d-translate">CanvasRenderingContext2D.translate - HTML Living Standard</a>
	 */
	void translate(Integer x, Integer y);
}
