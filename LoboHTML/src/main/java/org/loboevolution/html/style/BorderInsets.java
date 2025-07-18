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

import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.HTMLHtmlElement;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.info.BorderInfo;
import org.loboevolution.laf.ColorFactory;

import java.awt.*;

/**
 * <p>BorderInsets class.</p>
 */
public class BorderInsets {
	
	/** Constant BORDER_STYLE_NONE=0 */
	public static final int BORDER_STYLE_NONE = 0;
	
	/** Constant BORDER_STYLE_HIDDEN=1 */
	public static final int BORDER_STYLE_HIDDEN = 1;
	
	/** Constant BORDER_STYLE_DOTTED=2 */
	public static final int BORDER_STYLE_DOTTED = 2;
	
	/** Constant BORDER_STYLE_DASHED=3 */
	public static final int BORDER_STYLE_DASHED = 3;
	
	/** Constant BORDER_STYLE_SOLID=4 */
	public static final int BORDER_STYLE_SOLID = 4;
			
	/** Constant BORDER_STYLE_DOUBLE=5 */
	public static final int BORDER_STYLE_DOUBLE = 5;

	/** Constant BORDER_STYLE_GROOVE=6 */
	public static final int BORDER_STYLE_GROOVE = 6;
	
	/** Constant BORDER_STYLE_RIDGE=7 */
	public static final int BORDER_STYLE_RIDGE = 7;
	
	/** Constant BORDER_STYLE_INSET=8 */
	public static final int BORDER_STYLE_INSET = 8;
		
	/** Constant BORDER_STYLE_OUTSET=9 */
	public static final int BORDER_STYLE_OUTSET = 9;
	
	/** Constant DEFAULT_BORDER_WIDTH=2 */
	public static final int DEFAULT_BORDER_WIDTH = 2;
	
	/**
	 * <p>getBorderInfo.</p>
	 *
	 * @param properties a {@link CSSStyleDeclaration} object.
	 * @param element a {@link  org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param renderState a {@link RenderState} object.
	 * @return a {@link org.loboevolution.info.BorderInfo} object.
	 */
	public static BorderInfo getBorderInfo(final CSSStyleDeclaration properties, final HTMLElementImpl element, final RenderState renderState) {
		final BorderInfo binfo = new BorderInfo();
		
		final int topStyle = getBorderStyle(element, properties.getBorderTopStyle());
		final int rightStyle = getBorderStyle(element, properties.getBorderRightStyle());
		final int bottomStyle = getBorderStyle(element, properties.getBorderBottomStyle());
		final int leftStyle = getBorderStyle(element, properties.getBorderLeftStyle());
		
		final Color topColor = getBorderColor(element, properties.getBorderTopColor(), renderState);
		final Color rightColor = getBorderColor(element, properties.getBorderRightColor(), renderState);
		final Color bottomColor = getBorderColor(element, properties.getBorderBottomColor(), renderState);
		final Color leftColor = getBorderColor(element, properties.getBorderLeftColor(), renderState);

		binfo.setTopStyle(topStyle);
		binfo.setRightStyle(rightStyle);
		binfo.setBottomStyle(bottomStyle);
		binfo.setLeftStyle(leftStyle);
		
		binfo.setTopColor(topColor);
	    binfo.setRightColor(rightColor);
	    binfo.setBottomColor(bottomColor);
	    binfo.setLeftColor(leftColor);

		populateBorderInsets(binfo, properties, element ,renderState);

		return binfo;
	}

	/**
	 * <p>populateBorderInsets.</p>
	 *
	 * @param binfo a {@link org.loboevolution.info.BorderInfo} object.
	 * @param cssProperties a {@link CSSStyleDeclaration} object.
	 * @param element a {@link  org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param renderState a {@link RenderState} object.
	 */
	private static void populateBorderInsets(final BorderInfo binfo, final CSSStyleDeclaration cssProperties, final HTMLElementImpl element, final RenderState renderState) {
		final CSSStyleDeclaration parentStyle = element.getParentStyle();
		String borderTopWidth = "";
		String borderLeftWidth = "";
		String borderBottomWidth = "";
		String borderRightWidth = "";

		if (parentStyle == null) {
			if (!(element instanceof HTMLHtmlElement)) {
				binfo.setTopStyle(BORDER_STYLE_NONE);
				binfo.setLeftStyle(BORDER_STYLE_NONE);
				binfo.setBottomStyle(BORDER_STYLE_NONE);
				binfo.setRightStyle(BORDER_STYLE_NONE);
			}
		} else {
			borderTopWidth = parentStyle.getBorderTopWidth();
			borderLeftWidth = parentStyle.getBorderLeftWidth();
			borderBottomWidth = parentStyle.getBorderBottomWidth();
			borderRightWidth = parentStyle.getBorderRightWidth();
		}

		final String topText = borderInsets(borderTopWidth, binfo.getTopStyle(), cssProperties.getBorderTopWidth());
		final String leftText = borderInsets(borderLeftWidth, binfo.getLeftStyle(), cssProperties.getBorderLeftWidth());
		final String bottomText = borderInsets(borderBottomWidth, binfo.getBottomStyle(), cssProperties.getBorderBottomWidth());
		final String rightText = borderInsets(borderRightWidth, binfo.getRightStyle(), cssProperties.getBorderRightWidth());
		binfo.setInsets(HtmlInsets.getInsets(topText, leftText, bottomText, rightText, element, renderState));
	}

	private static String borderInsets(final String parentStyle, final int style, String value) {
		if (isNone(style)) return "0px";
        if (isInherit(value)) return getBorderWidthValue(parentStyle);
        return getBorderWidthValue(value);
	}

	private static boolean isNone(final int value) {
		return BORDER_STYLE_NONE == value;
	}

	private static boolean isInherit(final String value) {
		return CSSValues.INHERIT.equals(CSSValues.get(value));
	}

	private static String getBorderWidthValue(String value) {
		String width = value != null ? value.toLowerCase() : "";
		if (CSSValues.THIN.isEqual(width)) {
			width = "1px";
		}

		if (CSSValues.INITIAL.isEqual(width) || CSSValues.MEDIUM.isEqual(width)) {
			width = "3px";
		}

		if (CSSValues.THICK.isEqual(width)) {
			width = "5px";
		}

		return width;
	}

	private static Color getBorderColor(final HTMLElementImpl element, final String colorSpec, final RenderState renderState) {
		final ColorFactory cf = ColorFactory.getInstance();
		if (Strings.isNotBlank(colorSpec)) {
	      if ("currentColor".equalsIgnoreCase(colorSpec)) {
	        return renderState.getColor();
	      } else  if ("inherit".equalsIgnoreCase(colorSpec)) {
			  return cf.getColor(element.getParentStyle().getBorderColor());
		  } else {
	        return cf.getColor(colorSpec);
	      }
	    } else {
	      return renderState.getColor();
	    }
	  }
	
	private static int getBorderStyle(final HTMLElementImpl element, final String styleText) {
		if (Strings.isBlank(styleText)) {
			return BorderInsets.BORDER_STYLE_NONE;
		}
		final String stl = styleText.toLowerCase();
        return switch (CSSValues.get(stl)) {
            case SOLID -> BORDER_STYLE_SOLID;
            case DASHED -> BORDER_STYLE_DASHED;
            case DOTTED -> BORDER_STYLE_DOTTED;
            case HIDDEN -> BORDER_STYLE_HIDDEN;
            case DOUBLE -> BORDER_STYLE_DOUBLE;
            case GROOVE -> BORDER_STYLE_GROOVE;
            case RIDGE -> BORDER_STYLE_RIDGE;
            case INSET -> BORDER_STYLE_INSET;
            case OUTSET -> BORDER_STYLE_OUTSET;
            case INHERIT -> getBorderStyle(element, element.getParentStyle().getBorderStyle());
            default -> BORDER_STYLE_NONE;
        };
	}
}
