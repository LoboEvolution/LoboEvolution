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

import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.css.CSS3Properties;
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
	 * @param properties a {@link org.loboevolution.html.node.css.CSS3Properties} object.
	 * @param element a {@link  org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link org.loboevolution.info.BorderInfo} object.
	 */
	public static BorderInfo getBorderInfo(CSS3Properties properties, HTMLElementImpl element, RenderState renderState) {
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
	 * @param cssProperties a {@link org.loboevolution.html.node.css.CSS3Properties} object.
	 * @param element a {@link  org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 */
	private static void populateBorderInsets(BorderInfo binfo, CSS3Properties cssProperties, HTMLElementImpl element, RenderState renderState) {
		final AbstractCSSProperties props = element.getParentStyle();
		if (props == null) {
			final HtmlInsets insets = HtmlInsets.getInsets("0px", "0px", "0px", "0px", element, renderState);
			binfo.setInsets(insets);
		} else {
			final String topText = borderInsets(props.getBorderTopWidth(), binfo.getTopStyle(), cssProperties.getBorderTopWidth());
			final String leftText =  borderInsets(props.getBorderLeftWidth(), binfo.getLeftStyle(), cssProperties.getBorderLeftWidth());
			final String bottomText = borderInsets(props.getBorderBottomWidth(), binfo.getBottomStyle(), cssProperties.getBorderBottomWidth());
			final String rightText = borderInsets(props.getBorderRightWidth(), binfo.getRightStyle(), cssProperties.getBorderRightWidth());
			final HtmlInsets insets = HtmlInsets.getInsets(topText, leftText, bottomText, rightText, element, renderState);
			binfo.setInsets(insets);
		}
	}

	private static String borderInsets(String parentStyle, int style, String value){
		if(isNone(style)) return "0px";
		if(isInherit(value)) return parentStyle;
		return value;
	}

	private static boolean isNone(int value){
		return BORDER_STYLE_NONE == value;
	}

	private static boolean isInherit(String value){
		return CSSValues.INHERIT.equals(CSSValues.get(value));
	}

	private static Color getBorderColor(HTMLElementImpl element, final String colorSpec, final RenderState renderState) {
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
	
	private static int getBorderStyle(HTMLElementImpl element, String styleText) {
		if (Strings.isBlank(styleText)) {
			return BorderInsets.BORDER_STYLE_NONE;
		}
		final String stl = styleText.toLowerCase();
		switch (CSSValues.get(stl)) {
		case SOLID:
			return BORDER_STYLE_SOLID;
		case DASHED:
			return BORDER_STYLE_DASHED;
		case DOTTED:
			return BORDER_STYLE_DOTTED;
		case HIDDEN:
			return BORDER_STYLE_HIDDEN;
		case DOUBLE:
			return BORDER_STYLE_DOUBLE;
		case GROOVE:
			return BORDER_STYLE_GROOVE;
		case RIDGE:
			return BORDER_STYLE_RIDGE;
		case INSET:
			return BORDER_STYLE_INSET;
		case OUTSET:
			return BORDER_STYLE_OUTSET;
		case INHERIT:
			return getBorderStyle(element, element.getParentStyle().getBorderStyle());
		case NONE:
			default:
			return BORDER_STYLE_NONE;
		}
	}
}
