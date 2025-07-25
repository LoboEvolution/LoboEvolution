/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.html.style;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.common.Strings;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.info.GradientInfo;
import org.loboevolution.laf.ColorFactory;

import java.awt.*;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * <p>GradientStyle class.</p>
 */
public class GradientStyle {
	
	/**
	 * <p>gradientToImg.</p>
	 * @param document a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
	 * @param props a {@link CSSStyleDeclaration} object.
	 * @param renderState a {@link RenderState} object.
	 * @param backgroundImage a {@link java.lang.String} object.
	 * @return a {@link java.awt.image.BufferedImage} object.
	 */
	public BufferedImage gradientToImg(final HTMLDocumentImpl document, final CSSStyleDeclaration props, final RenderState renderState, final String backgroundImage) {
		BufferedImage image = null;
		final int idx = backgroundImage.indexOf("(");
		final String quote = backgroundImage.substring(0, idx);
        switch (quote) {
		case "linear-gradient":
			image = linearGadient(document, props, renderState, backgroundImage, quote, CycleMethod.NO_CYCLE);
			break;
		case "radial-gradient":
			image = radialGadient(document, props, renderState, backgroundImage, quote, CycleMethod.NO_CYCLE);
			break;
		case "repeating-linear-gradient":
			image = linearGadient(document, props, renderState, backgroundImage, quote, CycleMethod.REPEAT);
			break;
		case "repeating-radial-gradient":
			image = radialGadient(document, props, renderState, backgroundImage, quote, CycleMethod.REPEAT);
			break;
		default:
			break;
		}
		return image;
	}

	private BufferedImage linearGadient(final HTMLDocumentImpl document, final CSSStyleDeclaration props, final RenderState renderState, final String backgroundImage,
										final String start, final CycleMethod cMethod) {
		final StringBuilder builder  = new StringBuilder();
		builder.append(start).append("(");
		final int startIdx = start.length();
		final int closingIdx = backgroundImage.lastIndexOf(')');
		final String quote = backgroundImage.substring(startIdx+1, closingIdx);
		final String values = gradientValues(quote);
		final String direction = direction(quote);
		final GradientInfo info = parseGradint(values);
		final Color[] colors = info.getColors();
		final int width = getWidth(document, props, renderState);
		final int height = getHeight(document, props, renderState);
		LinearGradientPaint linearGradientPaint;
		final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2 = image.createGraphics();
		switch (direction) {
		case "to right":
			linearGradientPaint = new LinearGradientPaint(0, 0, width, 0, info.getFractions(), colors, cMethod);
			break;
		case "to left":
			Collections.reverse(Arrays.asList(colors));
			linearGradientPaint = new LinearGradientPaint(0, 0, width, 0, info.getFractions(), colors, cMethod);
			break;
		case "to top":
			Collections.reverse(Arrays.asList(colors));
			linearGradientPaint = new LinearGradientPaint(0, 0, 0, height, info.getFractions(), colors, cMethod);
			break;
		case "to bottom left":
			Collections.reverse(Arrays.asList(colors));
			linearGradientPaint = new LinearGradientPaint(0, 0, width, height, info.getFractions(), colors, cMethod);
			break;
		case "to bottom right":
			linearGradientPaint = new LinearGradientPaint(0, 0, width, height, info.getFractions(), colors, cMethod);
			break;
		case "to bottom":
		default:
			if (direction.contains("deg")) {
				Collections.reverse(Arrays.asList(colors));
				final double rotation = Double.parseDouble(direction.substring(0, direction.lastIndexOf('d')));
				final AffineTransform tf = AffineTransform.getTranslateInstance(-width / 2, -height / 2);
		        tf.preConcatenate(AffineTransform.getRotateInstance(Math.toRadians(rotation)));
		        tf.preConcatenate(AffineTransform.getTranslateInstance(width / 2, height / 2));
		        g2.setTransform(tf);
				linearGradientPaint = new LinearGradientPaint(0, 0, width, height, info.getFractions(), colors, cMethod);
			} else {
				linearGradientPaint = new LinearGradientPaint(0, 0, 0, height, info.getFractions(), colors, cMethod);
			}
			break;
		}
		g2.setColor(Color.white);
        g2.fillRect(0, 0, width, height);
        g2.setPaint(linearGradientPaint);
        g2.fillRect(0, 0,  width, height);
		return image;
	}

	private BufferedImage radialGadient(final HTMLDocumentImpl document, final CSSStyleDeclaration props, final RenderState renderState, final String backgroundImage,
										final String start, final CycleMethod cMethod) {

		final StringBuilder builder  = new StringBuilder();
		builder.append(start).append("(");
		final int startIdx = builder.length();
		final int closingIdx = backgroundImage.lastIndexOf(')');
		final String quote = backgroundImage.substring(startIdx, closingIdx);
		final String values = gradientValues(quote);
		final int width = getWidth(document, props, renderState);
		final int height = getHeight(document, props, renderState);
		final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2 = image.createGraphics();
		final Point2D center = new Point2D.Float(width/2, height/2);
		final float radius = width/2;
		final GradientInfo info = parseGradint(values);
		Color[] colors = info.getColors();
		final float[] fractions = ArrayUtilities.removeFloat(info.getFractions(), info.getFractions().length-1);
		final Color background = colors[colors.length-1];
		colors = ArrayUtilities.removeColor(colors, colors.length-1);
		final RadialGradientPaint p = new RadialGradientPaint(center, radius, fractions, colors, cMethod);
		g2.setColor(background);
		g2.fillRect(0, 0, width, height);
		g2.setPaint(p);

        if (info.getShape().equals("circle")) {
            g2.fillOval(0, 0, width - 1, height - 1);
        } else {
            g2.draw(new Ellipse2D.Double(0, 0, width - 1, height - 1));
        }

		return image;
	}

	private GradientInfo parseGradint(final String quote){
		String shape = null;
		String size = null;
		final ArrayList<Float> listFractions = new ArrayList<>();
		final ArrayList<Color> colors = new ArrayList<>();
		final String quoteRepalced = quote.replace(" ", "");
		final char[] charArray = quoteRepalced.toCharArray();
		boolean isColored = false;
		String color = "";
		for (int i = 0; i < charArray.length; i++) {
			final char c = charArray[i];
			if (Strings.isNotBlank(color) && !ColorFactory.getInstance().isRgbOrHsl(color)) {
				final Color clr = ColorFactory.getInstance().getColor(color);
				if (clr != null) {
					setFractions(listFractions, charArray, i, color);
					colors.add(ColorFactory.getInstance().getColor(color));
					color = "";
					isColored = true;
				}
			}

			if (Strings.isNotBlank(color) || c != ',') {
				color += c;
			}

			if (isColored && (c == ',' || c == '%')) {
				isColored = false;
				color = "";
			}

			if (ColorFactory.getInstance().isRgbOrHsl(color) && c == ')') {
				setFractions(listFractions, charArray, i, color);
				colors.add(ColorFactory.getInstance().getColor(color));
				isColored = true;
			} else if (Strings.isNotBlank(color) &&  !ColorFactory.getInstance().isRgbOrHsl(color) && i == charArray.length - 1) {
				final Color clr = ColorFactory.getInstance().getColor(color);
				if (clr != null) {
					setFractions(listFractions, charArray, i, color);
					colors.add(ColorFactory.getInstance().getColor(color));
					color = "";
				}
			}

			if("circle".equals(color) || "ellipse".equals(color)){
				shape = color;
				color = "";
			}

			if(("farthest-corner".equals(color) || "closest-corner".equals(color) ||
					"farthest-side".equals(color) || "closest-side".equals(color)) && (c == 'e' || c == 'r')) {
				size = color;
				color = "";
			}
		}

		final Color[] colorArray = new Color[colors.size()];
		int a = 0;
		for (final Color c : colors) {
			colorArray[a++] = c;
		}

		final float[] fractions = new float[listFractions.size()];
		int i = 0;

		for (final Float f : listFractions) {
			fractions[i++] = f != null ? f : Float.NaN;
		}
		Arrays.sort(fractions);

		return GradientInfo.builder().
				colors(colorArray).
				fractions(fractions).
				shape(shape).
				sizeAtPosition(size).
				build();
	}

	private static void setFractions(final List<Float> listFractions, final char[] charArray, final int index, final String color) {
		final boolean isPercent = color.contains("%");
		final float numberOnly = isPercent ? Float.parseFloat(color.replaceAll("[^0-9]", "")) /100 : 0f;
		
		if (listFractions.isEmpty()) {
			listFractions.add(isPercent ? numberOnly : 0F);
		} else if (index == charArray.length - 1) {
			listFractions.add(isPercent ? numberOnly : 1F);
		} else {
			listFractions.add(isPercent ? numberOnly : (float) (listFractions.size() * 0.3));
		}
	}

	private int getHeight(final HTMLDocumentImpl document, final CSSStyleDeclaration props, final RenderState renderState) {
		int heightSize = HtmlValues.getPixelSize(props.getHeight(), renderState, document.getDefaultView(), -1);
		if (heightSize < 0) {
			final HtmlRendererConfig config = document.getConfig();
			final Rectangle initialWindowBounds = config.getInitialWindowBounds();
			heightSize = (int)initialWindowBounds.getHeight() * 2;
		}	
		return heightSize;
	}
	
	private int getWidth(final HTMLDocumentImpl document, final CSSStyleDeclaration props, final RenderState renderState) {
		int widthSize = HtmlValues.getPixelSize(props.getWidth(), renderState, document.getDefaultView(),-1);
		if (widthSize < 0) {
			final HtmlRendererConfig config = document.getConfig();
			final Rectangle initialWindowBounds = config.getInitialWindowBounds();
			widthSize = (int)initialWindowBounds.getWidth() * 2;
		}	
		return widthSize;
	}
	
	private String direction(final String quote) {
		String values = "";
		final String[] split = quote.split(",");
		for (final String val : split) {
			if (val.contains("to") || val.contains("deg")) {
				values = val;
			}
		}
		return values.trim();
	}

	private String gradientValues(final String quote) {
		final StringBuilder values = new StringBuilder();
		final String[] split = quote.split(",");
		for (int i = 0; i < split.length; i++) {
			final String qut = split[i];
			if (!qut.contains("to") && !qut.contains("deg")) {
				if (i == split.length - 1) {
					values.append(qut);
				} else {
					values.append(qut).append(",");
				}
			}
		}
		return values.toString().trim();
	}
}
