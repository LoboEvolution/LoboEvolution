package org.loboevolution.html.style;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.loboevolution.common.Strings;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.laf.ColorFactory;
import org.loboevolution.store.GeneralStore;

public class GradientStyle {
	
	public BufferedImage gradientToImg(AbstractCSSProperties props, RenderState renderState, String backgroundImage) {
		BufferedImage image = null;
		final int idx = backgroundImage.indexOf("(");
		final String quote = backgroundImage.substring(0, idx);
        switch (quote) {
		case "linear-gradient":
			image = linearGadient(props, renderState, backgroundImage);
			break;
		case "radial-gradient":
			image = radialGadient(props, renderState, backgroundImage);
			break;
		case "repeating-linear-gradient":
			image = repeatingLinearGadient(props, renderState, backgroundImage);
			break;
		case "repeating-radial-gradient":
			image = repeatingRadialGadient(props, renderState, backgroundImage);
			break;
		default:
			break;
		}
		return image;
	}

	private BufferedImage linearGadient(AbstractCSSProperties props, RenderState renderState, String backgroundImage) {
		final String start = "linear-gradient(";
		final int startIdx = start.length();
		final int closingIdx = backgroundImage.lastIndexOf(')');
		final String quote = backgroundImage.substring(startIdx, closingIdx);
		final String values = gradientValues(quote);
		final String direction = direction(quote); 
		final float[] fractions = fractions(values);
		final Color[] colors = colors(values);
		final int width = getWidth(props, renderState);
		final int height = getHeight(props, renderState);
		LinearGradientPaint linearGradientPaint = null;
		switch (direction) {
		case "to right":
			linearGradientPaint = new LinearGradientPaint(0, 0, width, 0, fractions, colors);
			break;
		case "to left":
			Collections.reverse(Arrays.asList(colors));
			linearGradientPaint = new LinearGradientPaint(0, 0, width, 0, fractions, colors);
			break;
		case "to top":
			Collections.reverse(Arrays.asList(colors));
			linearGradientPaint = new LinearGradientPaint(0, 0, 0, height, fractions, colors);
			break;
		case "to bottom left":
			Collections.reverse(Arrays.asList(colors));
			linearGradientPaint = new LinearGradientPaint(0, 0, width, height, fractions, colors);
			break;
		case "to bottom right":
			linearGradientPaint = new LinearGradientPaint(0, 0, width, height, fractions, colors);
			break;
		case "to bottom":
		default:
			linearGradientPaint = new LinearGradientPaint(0, 0, 0, height, fractions, colors);
			break;
		}
	
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.white);
        g2.fillRect(0, 0, width, height);
        g2.setPaint(linearGradientPaint);
        g2.fillRect(0, 0,  width, height);
		return image;
	}

	private BufferedImage radialGadient(AbstractCSSProperties props, RenderState renderState, String backgroundImage) {
		// TODO Auto-generated method stub
		return new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
	}

	private BufferedImage repeatingLinearGadient(AbstractCSSProperties props, RenderState renderState, String backgroundImage) {
		// TODO Auto-generated method stub
		return new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
	}

	private BufferedImage repeatingRadialGadient(AbstractCSSProperties props, RenderState renderState, String backgroundImage) {
		// TODO Auto-generated method stub
		return new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
	}

	
	private float[] fractions(String quote) {
		ArrayList<Float> listFractions = new ArrayList<Float>();
		String quoteTmp = quote;
		quoteTmp = quote.replace(" ", "");
		char[] charArray = quoteTmp.toCharArray();
		String color = "";
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if(Strings.isBlank(color) && c == ',') {
				continue;
			} else {
				color += c;
			}
			
			if(color.startsWith("rgb") && c == ')') {
				if(listFractions.size() == 0) {
					listFractions.add(0F);
				}else if(i == charArray.length -1) {
					listFractions.add(1F);
				} else {
					listFractions.add((float)(listFractions.size() * 0.3));
				}
				color = "";
			} else if(!color.startsWith("rgb") && i == charArray.length -1) {
				if(listFractions.size() == 0) {
					listFractions.add(0F);
				}else if(i == charArray.length -1) {
					listFractions.add(1F);
				} else {
					listFractions.add((float)(listFractions.size() * 0.3));
				}
				color = "";
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

	private Color[] colors(String quote) {
		ArrayList<Color> colors = new ArrayList<Color>();
		String quoteTmp = quote;
		quoteTmp = quote.replace(" ", "");
		char[] charArray = quoteTmp.toCharArray();
		String color = "";
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if(Strings.isBlank(color) && c == ',') {
				continue;
			} else {
				color += c;
			}
			
			if(color.startsWith("rgb") && c == ')') {
				colors.add(ColorFactory.getInstance().getColor(color));
				color = "";
			} else if(!color.startsWith("rgb") && i == charArray.length -1) {
				colors.add(ColorFactory.getInstance().getColor(color));
			}
		}
		
		Color[] colorArray = new Color[colors.size()];
		int a = 0;
		for (Color c : colors) {
			colorArray[a++] = c != null ? c : null;
		}
		return colorArray;
	}

	private int getHeight(AbstractCSSProperties props, RenderState renderState) {
		int heightSize = HtmlValues.getPixelSize(props.getHeight(), renderState, -1);
		if(heightSize < 0) {
			final Rectangle initialWindowBounds = GeneralStore.getInitialWindowBounds();
			heightSize = (int)initialWindowBounds.getHeight() * 2;
		}	
		return heightSize;
	}
	
	private int getWidth(AbstractCSSProperties props, RenderState renderState) {
		int widthSize = HtmlValues.getPixelSize(props.getWidth(), renderState, -1);
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
			if (val.contains("to")) {
				values = val;
			}
		}
		return values.trim();
	}

	private String gradientValues(String quote) {
		String values = "";
		String[] split = quote.split(",");
		for (int i = 0; i < split.length; i++) {
			String qut = split[i];
			if (!qut.contains("to")) {
				if (i == split.length - 1) {
					values += qut;
				} else {
					values += qut + ",";
				}
			}
		}
		return values.trim();
	}
}