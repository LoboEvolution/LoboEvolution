/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.style;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.laf.ColorFactory;
import org.loboevolution.store.GeneralStore;

/**
 * <p>GradientStyle class.</p>
 *
 *
 *
 */
public class GradientStyle {
	
	/**
	 * <p>gradientToImg.</p>
	 * @param document a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
	 * @param props a {@link org.loboevolution.html.style.AbstractCSSProperties} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param backgroundImage a {@link java.lang.String} object.
	 * @return a {@link java.awt.image.BufferedImage} object.
	 */
	public BufferedImage gradientToImg(HTMLDocumentImpl document, AbstractCSSProperties props, RenderState renderState, String backgroundImage) {
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

	private BufferedImage linearGadient(HTMLDocumentImpl document, AbstractCSSProperties props, RenderState renderState, String backgroundImage, String start, CycleMethod cMethod) {
		start = start + "(";
		final int startIdx = start.length();
		final int closingIdx = backgroundImage.lastIndexOf(')');
		final String quote = backgroundImage.substring(startIdx, closingIdx);
		final String values = gradientValues(quote);
		final String direction = direction(quote); 
		final float[] fractions = fractions(values);
		final Color[] colors = colors(values);
		final int width = getWidth(document, props, renderState);
		final int height = getHeight(document, props, renderState);
		LinearGradientPaint linearGradientPaint = null;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		switch (direction) {
		case "to right":
			linearGradientPaint = new LinearGradientPaint(0, 0, width, 0, fractions, colors, cMethod);
			break;
		case "to left":
			Collections.reverse(Arrays.asList(colors));
			linearGradientPaint = new LinearGradientPaint(0, 0, width, 0, fractions, colors, cMethod);
			break;
		case "to top":
			Collections.reverse(Arrays.asList(colors));
			linearGradientPaint = new LinearGradientPaint(0, 0, 0, height, fractions, colors, cMethod);
			break;
		case "to bottom left":
			Collections.reverse(Arrays.asList(colors));
			linearGradientPaint = new LinearGradientPaint(0, 0, width, height, fractions, colors, cMethod);
			break;
		case "to bottom right":
			linearGradientPaint = new LinearGradientPaint(0, 0, width, height, fractions, colors, cMethod);
			break;
		case "to bottom":
		default:
			if (direction.contains("deg")) {
				Collections.reverse(Arrays.asList(colors));
				double rotation = Double.parseDouble(direction.substring(0, direction.lastIndexOf('d')));
				AffineTransform tf = AffineTransform.getTranslateInstance(-width / 2, -height / 2);
		        tf.preConcatenate(AffineTransform.getRotateInstance(Math.toRadians(rotation)));
		        tf.preConcatenate(AffineTransform.getTranslateInstance(width / 2, height / 2));
		        g2.setTransform(tf);
				linearGradientPaint = new LinearGradientPaint(0, 0, width, height, fractions, colors, cMethod);
			} else {
				linearGradientPaint = new LinearGradientPaint(0, 0, 0, height, fractions, colors, cMethod);
			}
			break;
		}
		g2.setColor(Color.white);
        g2.fillRect(0, 0, width, height);
        g2.setPaint(linearGradientPaint);
        g2.fillRect(0, 0,  width, height);
		return image;
	}

	private BufferedImage radialGadient(HTMLDocumentImpl document, AbstractCSSProperties props, RenderState renderState, String backgroundImage, String start, CycleMethod cMethod) {
		start = start + "(";
		final int startIdx = start.length();
		final int closingIdx = backgroundImage.lastIndexOf(')');
		final String quote = backgroundImage.substring(startIdx, closingIdx);
		final String values = gradientValues(quote);
		final int width = getWidth(document, props, renderState);
		final int height = getHeight(document, props, renderState);
		final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2 = image.createGraphics();
		final Point2D center = new Point2D.Float(width/2, height/2);
		final float radius = width/2;
		final float[] fractions = fractions(values.substring(0, values.lastIndexOf(",")));
		Color[] colors = colors(values);
		final Color background = colors[colors.length-1];
		colors = ArrayUtilities.removeColor(colors, colors.length-1);
		RadialGradientPaint p = new RadialGradientPaint(center, radius, fractions, colors, cMethod);
		g2.setColor(background);
		g2.fillRect(0, 0, width, height);
		g2.setPaint(p);
		g2.fillOval(0, 0, width - 1, height - 1);
		return image;
	}

	private static float[] fractions(final String quote) {
		ArrayList<Float> listFractions = new ArrayList<>();
		String quoteTmp = quote;
		quoteTmp = quoteTmp.replace(" ", "");
		char[] charArray = quoteTmp.toCharArray();
		boolean isColored = false;
		String color = "";
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (Strings.isNotBlank(color) &&  !ColorFactory.getInstance().isRgbOrHsl(color)) {
				Color clr = ColorFactory.getInstance().getColor(color);
				if (clr != null) {
					setFractions(listFractions, charArray, i, color);
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
				isColored = true;
			} else if (Strings.isNotBlank(color) &&  !ColorFactory.getInstance().isRgbOrHsl(color) && i == charArray.length - 1) {
				
				Color clr = ColorFactory.getInstance().getColor(color);
				if (clr != null) {
					setFractions(listFractions, charArray, i, color);
					color = "";
				}
			}
		}

		float[] fractions = new float[listFractions.size()];
		int i = 0;

		for (Float f : listFractions) {
			fractions[i++] = f != null ? f : Float.NaN;
		}
		Arrays.sort(fractions);
		return fractions;
	}

	private  Color[] colors(final String quote) {
		ArrayList<Color> colors = new ArrayList<>();
		String quoteTmp = quote;
		quoteTmp = quote.replace(" ", "");
		char[] charArray = quoteTmp.toCharArray();
		boolean isColored = false;
		String color = "";
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (Strings.isNotBlank(color) && !ColorFactory.getInstance().isRgbOrHsl(color)) {
				Color clr = ColorFactory.getInstance().getColor(color);
				if (clr != null) {
					colors.add(ColorFactory.getInstance().getColor(color));
					color = "";
					isColored = true;
				}
			}
			
			if(Strings.isNotBlank(color) || c != ',') {
				color += c;
			}
			
			if (isColored && (c == ',' || c == '%')) {
				isColored = false;
				color = "";
			}
			
			if (ColorFactory.getInstance().isRgbOrHsl(color) && c == ')') {
				colors.add(ColorFactory.getInstance().getColor(color));
				isColored = true;
			} else if (Strings.isNotBlank(color) &&  !ColorFactory.getInstance().isRgbOrHsl(color) && i == charArray.length - 1) {
				Color clr = ColorFactory.getInstance().getColor(color);
				if (clr != null) {
					colors.add(ColorFactory.getInstance().getColor(color));
					color = "";
				}
			}
		}
		
		Color[] colorArray = new Color[colors.size()];
		int a = 0;
		for (Color c : colors) {
			colorArray[a++] = c;
		}
		return colorArray;
	}
	
	private static void setFractions(ArrayList<Float> listFractions, char[] charArray, int index, String color) {
		final boolean isPercent = color.contains("%");
		final float numberOnly = isPercent ? Float.parseFloat(color.replaceAll("[^0-9]", "")) /100 : 0f;
		
		if (listFractions.size() == 0) {
			listFractions.add(isPercent ? numberOnly : 0F);
		} else if (index == charArray.length - 1) {
			listFractions.add(isPercent ? numberOnly : 1F);
		} else {
			listFractions.add(isPercent ? numberOnly : (float) (listFractions.size() * 0.3));
		}
	}

	private int getHeight(HTMLDocumentImpl document, AbstractCSSProperties props, RenderState renderState) {
		int heightSize = HtmlValues.getPixelSize(props.getHeight(), renderState, document.getWindow(), -1);
		if(heightSize < 0) {
			final Rectangle initialWindowBounds = GeneralStore.getInitialWindowBounds();
			heightSize = (int)initialWindowBounds.getHeight() * 2;
		}	
		return heightSize;
	}
	
	private int getWidth(HTMLDocumentImpl document, AbstractCSSProperties props, RenderState renderState) {
		int widthSize = HtmlValues.getPixelSize(props.getWidth(), renderState, document.getWindow(),-1);
		if(widthSize < 0) {
			final Rectangle initialWindowBounds = GeneralStore.getInitialWindowBounds();
			widthSize = (int)initialWindowBounds.getWidth() * 2;
		}	
		return widthSize;
	}
	
	private String direction(String quote) {
		String values = "";
		String[] split = quote.split(",");
		for (String val : split) {
			if (val.contains("to") || val.contains("deg")) {
				values = val;
			}
		}
		return values.trim();
	}

	private String gradientValues(String quote) {
		StringBuilder values = new StringBuilder();
		String[] split = quote.split(",");
		for (int i = 0; i < split.length; i++) {
			String qut = split[i];
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
