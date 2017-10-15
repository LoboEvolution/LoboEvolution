/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.renderstate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.info.BorderInfo;
import org.lobobrowser.html.renderer.RBlockViewport;
import org.lobobrowser.html.style.CSSValuesProperties;
import org.lobobrowser.html.style.HtmlInsets;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.util.Strings;
import org.lobobrowser.util.gui.ColorFactory;
import org.lobobrowser.util.gui.GUITasks;
import org.lobobrowser.util.gui.LAFSettings;
import org.w3c.dom.css.CSS2Properties;

public class BorderRenderState implements CSSValuesProperties {
	
	private static final Logger logger = LogManager.getLogger(BorderRenderState.class.getName());

	/** The Constant DEFAULT_BORDER_WIDTH. */
	public static final int DEFAULT_BORDER_WIDTH = 2;

	/** The Constant BORDER_STYLE_NONE. */
	public static final int BORDER_STYLE_NONE = 0;

	/** The Constant BORDER_STYLE_HIDDEN. */
	public static final int BORDER_STYLE_HIDDEN = 1;

	/** The Constant BORDER_STYLE_DOTTED. */
	public static final int BORDER_STYLE_DOTTED = 2;

	/** The Constant BORDER_STYLE_DASHED. */
	public static final int BORDER_STYLE_DASHED = 3;

	/** The Constant BORDER_STYLE_SOLID. */
	public static final int BORDER_STYLE_SOLID = 4;

	/** The Constant BORDER_STYLE_DOUBLE. */
	public static final int BORDER_STYLE_DOUBLE = 5;

	/** The Constant BORDER_STYLE_GROOVE. */
	public static final int BORDER_STYLE_GROOVE = 6;

	/** The Constant BORDER_STYLE_RIDGE. */
	public static final int BORDER_STYLE_RIDGE = 7;

	/** The Constant BORDER_STYLE_INSET. */
	public static final int BORDER_STYLE_INSET = 8;

	/** The Constant BORDER_STYLE_OUTSET. */
	public static final int BORDER_STYLE_OUTSET = 9;
	
	/** The Constant INVALID_BORDER_INFO. */
	public static final BorderInfo INVALID_BORDER_INFO = new BorderInfo();
	
	public Insets borderInsets(RenderState rs, int availWidth, int availHeight) {
		Insets borderInsets = RBlockViewport.ZERO_INSETS;
		BorderInfo borderInfo = rs.getBorderInfo();
		
		if (borderInfo != null) {
			HtmlInsets binsets = borderInfo.getInsets();
			if(binsets!= null) borderInsets = binsets.getAWTInsets(0, 0, 0, 0, availWidth, availHeight, 0, 0);
		}
		return borderInsets;
	}

	/**
	 * Gets the border info.
	 *
	 * @param properties
	 *            the properties
	 * @param renderState
	 *            the render state
	 * @return the border info
	 */
	public static BorderInfo getBorderInfo(CSS2Properties properties, RenderState renderState) {

		BorderInfo binfo = new BorderInfo();
		ColorFactory cf = ColorFactory.getInstance();

		if (!Strings.isBlank(properties.getBorderColor())) {

			String[] borderColors = Strings.splitUsingTokenizer(properties.getBorderColor(), ")");

			switch (borderColors.length) {
			case 1:
				binfo.setTopColor(cf.getColor(borderColors[0] + ")"));
				binfo.setRightColor(cf.getColor(borderColors[0] + ")"));
				binfo.setBottomColor(cf.getColor(borderColors[0] + ")"));
				binfo.setLeftColor(cf.getColor(borderColors[0] + ")"));
				break;
			case 2:
				binfo.setTopColor(cf.getColor(borderColors[0] + ")"));
				binfo.setBottomColor(cf.getColor(borderColors[0] + ")"));
				binfo.setRightColor(cf.getColor(borderColors[1] + ")"));
				binfo.setLeftColor(cf.getColor(borderColors[1] + ")"));
				break;
			case 3:
				binfo.setTopColor(cf.getColor(borderColors[0] + ")"));
				binfo.setRightColor(cf.getColor(borderColors[1] + ")"));
				binfo.setLeftColor(cf.getColor(borderColors[1] + ")"));
				binfo.setBottomColor(cf.getColor(borderColors[2] + ")"));
				break;
			case 4:
				binfo.setTopColor(cf.getColor(borderColors[0] + ")"));
				binfo.setRightColor(cf.getColor(borderColors[1] + ")"));
				binfo.setBottomColor(cf.getColor(borderColors[2] + ")"));
				binfo.setLeftColor(cf.getColor(borderColors[3] + ")"));
				break;
			}
		}

		if (!Strings.isBlank(properties.getBorderStyle())) {

			String[] borderStyles = Strings.splitUsingTokenizer(properties.getBorderStyle(), " ");

			switch (borderStyles.length) {
			case 1:
				binfo.setTopStyle(getBorderStyle(borderStyles[0]));
				binfo.setRightStyle(getBorderStyle(borderStyles[0]));
				binfo.setBottomStyle(getBorderStyle(borderStyles[0]));
				binfo.setLeftStyle(getBorderStyle(borderStyles[0]));
				break;
			case 2:
				binfo.setTopStyle(getBorderStyle(borderStyles[0]));
				binfo.setBottomStyle(getBorderStyle(borderStyles[0]));
				binfo.setRightStyle(getBorderStyle(borderStyles[1]));
				binfo.setLeftStyle(getBorderStyle(borderStyles[1]));
				break;
			case 3:
				binfo.setTopStyle(getBorderStyle(borderStyles[0]));
				binfo.setRightStyle(getBorderStyle(borderStyles[1]));
				binfo.setLeftStyle(getBorderStyle(borderStyles[1]));
				binfo.setBottomStyle(getBorderStyle(borderStyles[2]));
				break;
			case 4:
				binfo.setTopStyle(getBorderStyle(borderStyles[0]));
				binfo.setRightStyle(getBorderStyle(borderStyles[1]));
				binfo.setBottomStyle(getBorderStyle(borderStyles[2]));
				binfo.setLeftStyle(getBorderStyle(borderStyles[3]));
				break;
			}
		}

		if (INHERIT.equals(properties.getBorderTopStyle())) {
			binfo.setTopStyle(renderState.getPreviousRenderState().getBorderInfo().getTopStyle());
			binfo.setTopColor(renderState.getPreviousRenderState().getBorderInfo().getTopColor());
		} else {
			binfo.setTopStyle(getBorderStyle(properties.getBorderTopStyle()));
			binfo.setTopColor(cf.getColor(properties.getBorderTopColor()));
		}

		if (INHERIT.equals(properties.getBorderBottomStyle())) {
			binfo.setBottomStyle(renderState.getPreviousRenderState().getBorderInfo().getBottomStyle());
			binfo.setBottomColor(renderState.getPreviousRenderState().getBorderInfo().getBottomColor());
		} else {
			binfo.setBottomStyle(getBorderStyle(properties.getBorderBottomStyle()));
			binfo.setBottomColor(cf.getColor(properties.getBorderBottomColor()));
		}
		
		if (INHERIT.equals(properties.getBorderRightStyle())) {
			binfo.setRightStyle(renderState.getPreviousRenderState().getBorderInfo().getRightStyle());
			binfo.setRightColor(renderState.getPreviousRenderState().getBorderInfo().getRightColor());
		} else {
			binfo.setRightStyle(getBorderStyle(properties.getBorderRightStyle()));
			binfo.setRightColor(cf.getColor(properties.getBorderRightColor()));
		}

		if (INHERIT.equals(properties.getBorderLeftStyle())) {
			binfo.setLeftStyle(renderState.getPreviousRenderState().getBorderInfo().getLeftStyle());
			binfo.setLeftColor(renderState.getPreviousRenderState().getBorderInfo().getLeftColor());
		} else {
			binfo.setLeftStyle(getBorderStyle(properties.getBorderLeftStyle()));
			binfo.setLeftColor(cf.getColor(properties.getBorderLeftColor()));
		}

		populateBorderInsets(binfo, properties, renderState);
		return binfo;
	}

	/**
	 * Gets the border insets.
	 *
	 * @param borderStyles
	 *            the border styles
	 * @param cssProperties
	 *            the css properties
	 * @param renderState
	 *            the render state
	 * @return the border insets
	 */
	public static HtmlInsets getBorderInsets(Insets borderStyles, CSS2Properties cssProperties,
			RenderState renderState) {
		HtmlInsets insets = null;
		String borderText = cssProperties.getBorder();
		if (borderText != null) {
			String[] br = borderText.split(" ");
			int sizeBorder = br.length;
			switch (sizeBorder) {
			case 4:
				insets = HtmlValues.updateTopInset(insets, br[0], renderState);
				insets = HtmlValues.updateRightInset(insets, br[1], renderState);
				insets = HtmlValues.updateBottomInset(insets, br[2], renderState);
				insets = HtmlValues.updateLeftInset(insets, br[3], renderState);
				break;
			case 3:
				insets = HtmlValues.updateTopInset(insets, br[0], renderState);
				insets = HtmlValues.updateRightInset(insets, br[1], renderState);
				insets = HtmlValues.updateBottomInset(insets, br[2], renderState);
				break;
			case 2:
				insets = HtmlValues.updateTopInset(insets, br[0], renderState);
				insets = HtmlValues.updateRightInset(insets, br[1], renderState);
				break;
			case 1:
				insets = HtmlValues.updateTopInset(insets, br[0], renderState);
				insets = HtmlValues.updateRightInset(insets, br[0], renderState);
				insets = HtmlValues.updateBottomInset(insets, br[0], renderState);
				insets = HtmlValues.updateLeftInset(insets, br[0], renderState);
				break;
			}
		} else {

			if (borderStyles.top != BORDER_STYLE_NONE) {
				String topText = cssProperties.getBorderTopWidth();
				insets = HtmlValues.updateTopInset(insets, topText, renderState);
			}
			if (borderStyles.left != BORDER_STYLE_NONE) {
				String leftText = cssProperties.getBorderLeftWidth();
				insets = HtmlValues.updateLeftInset(insets, leftText, renderState);
			}
			if (borderStyles.bottom != BORDER_STYLE_NONE) {
				String bottomText = cssProperties.getBorderBottomWidth();
				insets = HtmlValues.updateBottomInset(insets, bottomText, renderState);
			}
			if (borderStyles.right != BORDER_STYLE_NONE) {
				String rightText = cssProperties.getBorderRightWidth();
				insets = HtmlValues.updateRightInset(insets, rightText, renderState);
			}
		}
		return insets;
	}

	/**
	 * Populates BorderInfo.insets.
	 *
	 * @param binfo
	 *            A BorderInfo with its styles already populated.
	 * @param cssProperties
	 *            The CSS properties object.
	 * @param renderState
	 *            The current render state.
	 */
	public static void populateBorderInsets(BorderInfo binfo, CSS2Properties cssProperties, RenderState renderState) {
		HtmlInsets insets = null;

		if (binfo.getTopStyle() != BORDER_STYLE_NONE) {
			String topText = cssProperties.getBorderTopWidth();
			insets = HtmlValues.updateTopInset(insets, topText, renderState);
		}
		if (binfo.getLeftStyle() != BORDER_STYLE_NONE) {
			String leftText = cssProperties.getBorderLeftWidth();
			insets = HtmlValues.updateLeftInset(insets, leftText, renderState);
		}
		if (binfo.getBottomStyle() != BORDER_STYLE_NONE) {
			String bottomText = cssProperties.getBorderBottomWidth();
			insets = HtmlValues.updateBottomInset(insets, bottomText, renderState);
		}
		if (binfo.getRightStyle() != BORDER_STYLE_NONE) {
			String rightText = cssProperties.getBorderRightWidth();
			insets = HtmlValues.updateRightInset(insets, rightText, renderState);
		}
		binfo.setInsets(insets);
	}

	/**
	 * Checks if is border style.
	 *
	 * @param token
	 *            the token
	 * @return true, if is border style
	 */
	public static boolean isBorderStyle(String token) {
		String tokenTL = token.toLowerCase();
		return tokenTL.equals(SOLID) || tokenTL.equals(DASHED) || tokenTL.equals(DOTTED) || tokenTL.equals(DOUBLE)
				|| tokenTL.equals(NONE) || tokenTL.equals(HIDDEN) || tokenTL.equals(GROOVE) || tokenTL.equals(RIDGE)
				|| tokenTL.equals(INSET) || tokenTL.equals(OUTSET);
	}
	
	
	public BorderInfo borderInfo(BorderInfo info, StyleSheetRenderState style){
		BorderInfo bi = info;
				
		if (!INVALID_BORDER_INFO.equals(bi)) {
			return bi;
		}
		
		if (bi == null || bi.getTopStyle() == BORDER_STYLE_NONE
				&& bi.getBottomStyle() == BORDER_STYLE_NONE
				&& bi.getLeftStyle() == BORDER_STYLE_NONE
				&& bi.getRightStyle() == BORDER_STYLE_NONE) {
			
			if (bi == null) {
				bi = new BorderInfo();
			}
			
			HTMLElementImpl element = style.element;
			if (element != null) {
				String border = element.getAttribute(HtmlAttributeProperties.FRAMEBORDER);
				int value = 0;
				if (border != null) {
					border = border.trim();
					value = HtmlValues.getPixelSize(border, style, 0);
				}

				HtmlInsets borderInsets = new HtmlInsets();
				borderInsets.top = borderInsets.left = borderInsets.right = borderInsets.bottom = value != 0 ? 1 : 0;
				borderInsets.topType = borderInsets.leftType = borderInsets.rightType = borderInsets.bottomType = HtmlInsets.TYPE_PIXELS;
				bi.setInsets(borderInsets);
				
				if (value != 0) {
					bi.setTopStyle(BORDER_STYLE_SOLID);
					bi.setLeftStyle(BORDER_STYLE_SOLID);
					bi.setRightStyle(BORDER_STYLE_SOLID);
					bi.setBottomStyle(BORDER_STYLE_SOLID);
				}
			}
		}
		
		return bi;
	}
	
	public void borderStyleTop(Graphics g, BorderInfo borderInfo, int btop, int bleft, int bright, int startX, int startY, int totalWidth) {
		if (btop > 0) {
			g.setColor(borderInfo.getTopColor());
			int borderStyle = borderInfo == null ? BORDER_STYLE_SOLID : borderInfo.getTopStyle();
			for (int i = 0; i < btop; i++) {
				int leftOffset = i * bleft / btop;
				int rightOffset = i * bright / btop;
				if (borderStyle == BORDER_STYLE_DASHED) {
					GUITasks.drawDashed(g, startX + leftOffset, startY + i, startX + totalWidth - rightOffset - 1,
							startY + i, 10 + btop, 6);
				} else {
					g.drawLine(startX + leftOffset, startY + i, startX + totalWidth - rightOffset - 1, startY + i);
				}
			}
		}
	}
	
	public void borderStyleRight(Graphics g, BorderInfo borderInfo, int bright, int btop, int bbottom, int startX, int startY, int totalWidth, int totalHeight) {
		if (bright > 0) {
			int borderStyle = borderInfo == null ? BORDER_STYLE_SOLID : borderInfo.getRightStyle();
			g.setColor(borderInfo.getRightColor());
			int lastX = startX + totalWidth - 1;
			for (int i = 0; i < bright; i++) {
				int topOffset = i * btop / bright;
				int bottomOffset = i * bbottom / bright;
				if (borderStyle == BORDER_STYLE_DASHED) {
					GUITasks.drawDashed(g, lastX - i, startY + topOffset, lastX - i,
							startY + totalHeight - bottomOffset - 1, 10 + bright, 6);
				} else {
					g.drawLine(lastX - i, startY + topOffset, lastX - i,
							startY + totalHeight - bottomOffset - 1);
				}
			}
		}
		
	}

	public void borderStyleBottom(Graphics g, BorderInfo borderInfo, int bright, int bleft, int bbottom, int startX, int startY, int totalWidth, int totalHeight) {
		if (bbottom > 0) {
			int borderStyle = borderInfo == null ? BORDER_STYLE_SOLID : borderInfo.getBottomStyle();
			g.setColor(borderInfo.getBottomColor());
			int lastY = startY + totalHeight - 1;
			for (int i = 0; i < bbottom; i++) {
				int leftOffset = i * bleft / bbottom;
				int rightOffset = i * bright / bbottom;
				if (borderStyle == BORDER_STYLE_DASHED) {
					GUITasks.drawDashed(g, startX + leftOffset, lastY - i,
							startX + totalWidth - rightOffset - 1, lastY - i, 10 + bbottom, 6);
				} else {
					g.drawLine(startX + leftOffset, lastY - i, startX + totalWidth - rightOffset - 1,
							lastY - i);
				}
			}
		}	
	}

	public void borderStyleLeft(Graphics g, BorderInfo borderInfo, int bleft, int btop, int bbottom, int startX, int startY, int totalHeight) {
		if (bleft > 0) {
			int borderStyle = borderInfo == null ? BORDER_STYLE_SOLID : borderInfo.getLeftStyle();
			g.setColor(borderInfo.getLeftColor());
			for (int i = 0; i < bleft; i++) {
				int topOffset = i * btop / bleft;
				int bottomOffset = i * bbottom / bleft;
				if (borderStyle == BORDER_STYLE_DASHED) {
					GUITasks.drawDashed(g, startX + i, startY + topOffset, startX + i,
							startY + totalHeight - bottomOffset - 1, 10 + bleft, 6);
				} else {
					g.drawLine(startX + i, startY + topOffset, startX + i, startY + totalHeight - bottomOffset - 1);
				}
			}
		}
	}

	/**
	 * Gets the border style.
	 *
	 * @param styleText
	 *            the style text
	 * @return the border style
	 */
	private static int getBorderStyle(String styleText) {

		if (styleText == null || styleText.length() == 0) {
			return BORDER_STYLE_NONE;
		}

		String stl = styleText.toLowerCase();

		switch (stl) {
		case SOLID:
			return BORDER_STYLE_SOLID;
		case DASHED:
			return BORDER_STYLE_DASHED;
		case DOTTED:
			return BORDER_STYLE_DOTTED;
		case NONE:
			return BORDER_STYLE_NONE;
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
		default:
			return BORDER_STYLE_NONE;
		}
	}
}
