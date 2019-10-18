package org.loboevolution.html.style;

import java.awt.Color;
import java.awt.Insets;

import org.loboevolution.common.Strings;
import org.loboevolution.info.BorderInfo;
import org.loboevolution.laf.ColorFactory;
import org.loboevolution.html.renderstate.RenderState;
import org.w3c.dom.css.CSS3Properties;

public class BorderInsets {
	
	public static final int BORDER_STYLE_DASHED = 3;
	public static final int BORDER_STYLE_DOTTED = 2;
	public static final int BORDER_STYLE_DOUBLE = 5;

	public static final int BORDER_STYLE_GROOVE = 6;
	public static final int BORDER_STYLE_HIDDEN = 1;
	public static final int BORDER_STYLE_INSET = 8;
	public static final int BORDER_STYLE_NONE = 0;
	public static final int BORDER_STYLE_OUTSET = 9;
	public static final int BORDER_STYLE_RIDGE = 7;
	public static final int BORDER_STYLE_SOLID = 4;
	public static final int DEFAULT_BORDER_WIDTH = 2;
	
	public static BorderInfo getBorderInfo(CSS3Properties properties, RenderState renderState) {
		final BorderInfo binfo = new BorderInfo();

		binfo.topStyle = getBorderStyle(properties.getBorderTopStyle());
		binfo.rightStyle = getBorderStyle(properties.getBorderRightStyle());
		binfo.bottomStyle = getBorderStyle(properties.getBorderBottomStyle());
		binfo.leftStyle = getBorderStyle(properties.getBorderLeftStyle());
		
		binfo.topColor = getBorderColor(properties.getBorderTopColor(), renderState);
	    binfo.rightColor = getBorderColor(properties.getBorderRightColor(), renderState);
	    binfo.bottomColor = getBorderColor(properties.getBorderBottomColor(), renderState);
	    binfo.leftColor = getBorderColor(properties.getBorderLeftColor(), renderState);

		populateBorderInsets(binfo, properties, renderState);

		return binfo;
	}

	public static HtmlInsets getBorderInsets(Insets borderStyles, CSS3Properties cssProperties, RenderState renderState) {
		HtmlInsets insets = null;
		if (borderStyles.top != BORDER_STYLE_NONE) {
			final String topText = cssProperties.getBorderTopWidth();
			insets = HtmlInsets.updateTopInset(insets, topText, renderState);
		}
		if (borderStyles.left != BORDER_STYLE_NONE) {
			final String leftText = cssProperties.getBorderLeftWidth();
			insets = HtmlInsets.updateLeftInset(insets, leftText, renderState);
		}
		if (borderStyles.bottom != BORDER_STYLE_NONE) {
			final String bottomText = cssProperties.getBorderBottomWidth();
			insets = HtmlInsets.updateBottomInset(insets, bottomText, renderState);
		}
		if (borderStyles.right != BORDER_STYLE_NONE) {
			final String rightText = cssProperties.getBorderRightWidth();
			insets = HtmlInsets.updateRightInset(insets, rightText, renderState);
		}
		return insets;
	}
	
	public static void populateBorderInsets(BorderInfo binfo, CSS3Properties cssProperties, RenderState renderState) {
		HtmlInsets insets = null;
		if (binfo.topStyle != BORDER_STYLE_NONE) {
			final String topText = cssProperties.getBorderTopWidth();
			insets = HtmlInsets.updateTopInset(insets, topText, renderState);
		}
		if (binfo.leftStyle != BORDER_STYLE_NONE) {
			final String leftText = cssProperties.getBorderLeftWidth();
			insets = HtmlInsets.updateLeftInset(insets, leftText, renderState);
		}
		if (binfo.bottomStyle != BORDER_STYLE_NONE) {
			final String bottomText = cssProperties.getBorderBottomWidth();
			insets = HtmlInsets.updateBottomInset(insets, bottomText, renderState);
		}
		if (binfo.rightStyle != BORDER_STYLE_NONE) {
			final String rightText = cssProperties.getBorderRightWidth();
			insets = HtmlInsets.updateRightInset(insets, rightText, renderState);
		}
		binfo.insets = insets;
	}
	
	private static Color getBorderColor(final String colorSpec, final RenderState renderState) {
		final ColorFactory cf = ColorFactory.getInstance();
		if (Strings.isNotBlank(colorSpec)) {
	      if ("currentColor".equalsIgnoreCase(colorSpec)) {
	        return renderState.getColor();
	      } else {
	        return cf.getColor(colorSpec);
	      }
	    } else {
	      return renderState.getColor();
	    }
	  }
	
	private static int getBorderStyle(String styleText) {
		if (styleText == null || styleText.length() == 0) {
			return BorderInsets.BORDER_STYLE_NONE;
		}
		final String stl = styleText.toLowerCase();
		switch (stl) {
		case "solid":
			return BORDER_STYLE_SOLID;
		case "dashed":
			return BORDER_STYLE_DASHED;
		case "dotted":
			return BORDER_STYLE_DOTTED;
		case "hidden":
			return BORDER_STYLE_HIDDEN;
		case "double":
			return BORDER_STYLE_DOUBLE;
		case "groove":
			return BORDER_STYLE_GROOVE;
		case "ridge":
			return BORDER_STYLE_RIDGE;
		case "inset":
			return BORDER_STYLE_INSET;
		case "outset":
			return BORDER_STYLE_OUTSET;
		case "none":
			default:
			return BORDER_STYLE_NONE;
		}
	}
}
