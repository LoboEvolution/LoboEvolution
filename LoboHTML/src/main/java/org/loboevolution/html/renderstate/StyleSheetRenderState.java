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
/*
 * Created on Apr 16, 2005
 */
package org.loboevolution.html.renderstate;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Strings;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderer.BackgroundRender;
import org.loboevolution.html.renderer.LineBreak;
import org.loboevolution.html.renderer.RFlex;
import org.loboevolution.html.style.*;
import org.loboevolution.info.BackgroundInfo;
import org.loboevolution.info.BorderInfo;
import org.loboevolution.info.WordInfo;
import org.loboevolution.laf.ColorFactory;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * <p>StyleSheetRenderState class.</p>
 */
@Slf4j
public class StyleSheetRenderState implements RenderState {
	
	private static final FontFactory FONT_FACTORY = FontFactory.getInstance();

	/** Constant INVALID_BACKGROUND_INFO */
	protected static final BackgroundInfo INVALID_BACKGROUND_INFO = BackgroundInfo.builder().build();
	
	/** Constant INVALID_BORDER_INFO */
	protected static final BorderInfo INVALID_BORDER_INFO = new BorderInfo();
	
	/** Constant INVALID_COLOR */
	protected static final Color INVALID_COLOR = new Color(100, 0, 100);
	
	/** Constant INVALID_INSETS */
	protected static final HtmlInsets INVALID_INSETS = new HtmlInsets();
	
	protected HtmlInsets paddingInsets = INVALID_INSETS;

	protected HtmlInsets marginInsets = INVALID_INSETS;

	protected final HTMLDocumentImpl document;
	
	protected final HTMLElementImpl element;
	
	protected BorderInfo borderInfo = INVALID_BORDER_INFO;

	protected BackgroundInfo iBackgroundInfo = INVALID_BACKGROUND_INFO;
	
	private Color iOverlayColor = INVALID_COLOR;

	private Color iTextBackgroundColor = INVALID_COLOR;
	
	private Color iBackgroundColor = INVALID_COLOR;

	private Integer cachedFloat;
	
	private Integer cachedPosition;
	
	private Integer cachedVisibility;
	
	private Integer iDisplay;

	protected Integer iWhiteSpace;

	private Integer cachedClear = null;
    
    private int iBlankWidth = -1;

	private int alignXPercent = -1;
	
	private int iTextDecoration = -1;

	private int iTextTransform = -1;
	
	protected int overflowX = -1;

	protected int overflowY = -1;

	private Map<String, ArrayList<Integer>> counters = null;
	
	private Color iColor;

	private Font iFont;

	private FontMetrics iFontMetrics;

	private boolean iHighlight;
	
	private String iTextIndentText = null;

	protected final RenderState prevRenderState;
	    
    private Cursor cursor;

	Map<String, WordInfo> iWordInfoMap = null;

	/**
	 * <p>Constructor for StyleSheetRenderState.</p>
	 *
	 * @param document a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
	 */
	public StyleSheetRenderState(final HTMLDocumentImpl document) {
		this.prevRenderState = null;
		this.element = null;
		this.document = document;
	}

	/**
	 * <p>Constructor for StyleSheetRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public StyleSheetRenderState(final RenderState prevRenderState,final HTMLElementImpl element) {
		this.prevRenderState = prevRenderState;
		this.element = element;
		this.document = (HTMLDocumentImpl) element.getOwnerDocument();
	}
	
	/** {@inheritDoc} */
	@Override
	public int getAlignXPercent() {
		int axp = this.alignXPercent;
		if (axp != -1) {
			return axp;
		}
		final CSSStyleDeclaration props = getCssProperties();
		String textAlign = props == null ? null : props.getTextAlign();
		if (Strings.isBlank(textAlign)) {
			final HTMLElement element = this.element;
			if (element != null) {
				textAlign = element.getAttribute("align");
				if (Strings.isBlank(textAlign)) {
					final RenderState prs = this.prevRenderState;
					if (prs != null) {
						return prs.getAlignXPercent();
					}
					textAlign = null;
				}
			}
		}

		final CSSValues aling = CSSValues.get(textAlign);
        axp = switch (aling) {
            case CENTER -> 50;
            case RIGHT -> 100;
            case INHERIT -> this.getPreviousRenderState().getAlignXPercent();
            default -> 0;
        };
		this.alignXPercent = axp;
		return axp;
	}

	/** {@inheritDoc} */
	@Override
	public int getAlignYPercent() {
		// This is only settable in table cells.
		// TODO: Does it work with display: table-cell?
		return 0;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getAlignItems() {
		final CSSStyleDeclaration props = this.getCssProperties();
		return props == null ? null : props.getAlignItems();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getAlignContent() {
		final CSSStyleDeclaration props = this.getCssProperties();
		return props == null ? null : props.getAlignContent();
	}
	
	/** {@inheritDoc} */
	@Override
	public Color getBackgroundColor() {
		final Color c = this.iBackgroundColor;
		if (c != INVALID_COLOR) {
			return c;
		}
		final Color localColor;
		final BackgroundInfo binfo = getBackgroundInfo();
		localColor = binfo == null ? null : binfo.getBackgroundColor();
		if (localColor == null && getDisplay() == DISPLAY_INLINE) {
			final RenderState prs = this.prevRenderState;
			if (prs != null) {
				final Color ancestorColor = prs.getBackgroundColor();
				if (ancestorColor != null) {
					this.iBackgroundColor = ancestorColor;
					return ancestorColor;
				}
			}
		}
		this.iBackgroundColor = localColor;
		return localColor;
	}

	/** {@inheritDoc} */
	@Override
	public BackgroundInfo getBackgroundInfo() {

		BackgroundInfo binfo = this.iBackgroundInfo;
		final CSSStyleDeclaration props = getCssProperties();

		if (binfo != INVALID_BACKGROUND_INFO) {
			return binfo;
		}

		if (element != null && !"BODY".equals(element.getNodeName())) {
			binfo = null;
		}

		if (props != null) {
			final String backgroundColorText = props.getBackgroundColor();
			final String backgroundImageText = props.getBackgroundImage();
			final String backgroundRepeatText = props.getBackgroundRepeat();
			final String backgroundPositionText = props.getBackgroundPosition();
						
			if (Strings.isNotBlank(backgroundColorText)  ||
				Strings.isNotBlank(backgroundImageText)  ||
				Strings.isNotBlank(backgroundRepeatText) ||
				Strings.isNotBlank(backgroundPositionText)) {
				binfo = BackgroundInfo.builder().build();
			}

			if (Strings.isNotBlank(backgroundColorText)) {
				final CSSValues bc = CSSValues.get(backgroundColorText);
				if (bc.equals(CSSValues.INHERIT)) {
					binfo.setBackgroundColor(this.getPreviousRenderState().getBackgroundColor());
				} else {
					binfo.setBackgroundColor(ColorFactory.getInstance().getColor(backgroundColorText));
				}
			}
			
			if (Strings.isNotBlank(backgroundRepeatText)) {
				final BackgroundRender backgroundImageRender = new BackgroundRender(element, prevRenderState, document);
				backgroundImageRender.applyBackgroundRepeat(binfo, backgroundRepeatText);
			}

			if (Strings.isNotBlank(backgroundImageText)) {
				final BackgroundRender backgroundImageRender = new BackgroundRender(element, prevRenderState, document);
				backgroundImageRender.applyBackgroundImage(binfo, backgroundImageText, this, props);
			}
		}
		this.iBackgroundInfo = binfo;		
		return binfo;
	}

	/** {@inheritDoc} */
	@Override
	public BackgroundInfo getBackgroundImageInfo(final int width, final int height){
		final BackgroundInfo binfo = iBackgroundInfo;
		final CSSStyleDeclaration props = getCssProperties();
		final String backgroundPositionText = props != null ? props.getBackgroundPosition() : "";

		if (Strings.isNotBlank(backgroundPositionText)) {
			final BackgroundRender backgroundImageRender = new BackgroundRender(element, prevRenderState, document);
			backgroundImageRender.applyBackgroundPosition(binfo, backgroundPositionText, width, height);
		}
		this.iBackgroundInfo = binfo;
		return binfo;

	}

	/** {@inheritDoc} */
	@Override
	public int getBlankWidth() {
		int bw = this.iBlankWidth;
		if (bw == -1) {
			bw = getFontMetrics().charWidth(' ');
			this.iBlankWidth = bw;
		}
		return bw;
	}

	/** {@inheritDoc} */
	@Override
	public BorderInfo getBorderInfo() {
		BorderInfo binfo = this.borderInfo;
		if (binfo != INVALID_BORDER_INFO) {
			return binfo;
		}
		final CSSStyleDeclaration props = getCssProperties();
		if (props != null) {
			binfo = BorderInsets.getBorderInfo(props, element, this);
		} else {
			binfo = null;
		}
		this.borderInfo = binfo;
		return binfo;
	}

	/**
	 * <p>getColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getColor() {
		Color c = this.iColor;
		if (c != null) {
			return c;
		}
		final CSSStyleDeclaration props = this.getCssProperties();
		final String colorValue = props == null ? null : props.getColor();
		final CSSValues color = CSSValues.get(colorValue);
		switch (color) {
		case INHERIT:
			if (this.getPreviousRenderState() != null) {
				return this.getPreviousRenderState().getColor();
			} else {
				return Color.BLACK;
			}
		case INITIAL:
			return Color.BLACK;
		default:
			if (Strings.isBlank(colorValue)) {
				final RenderState prs = this.prevRenderState;
				if (prs != null) {
					c = prs.getColor();
					this.iColor = c;
					return c;
				} else {
					this.iColor = Color.BLACK;
					return Color.BLACK;
				}
			} else {
				c = ColorFactory.getInstance().getColor(colorValue);
				this.iColor = c;
			}
			break;
		}
		return c;
	}

	/** {@inheritDoc} */
	@Override
	public int getCount(final String counter, final int nesting) {
		// Expected to be called only in GUI thread.
		final RenderState prs = this.prevRenderState;
		if (prs != null) {
			return prs.getCount(counter, nesting);
		}
		final Map<String, ArrayList<Integer>> counters = this.counters;
		if (counters == null) {
			return 0;
		}
		final ArrayList<Integer> counterArray = counters.get(counter);
		if (nesting < 0 || nesting >= counterArray.size()) {
			return 0;
		}
		final Integer integer = counterArray.get(nesting);
		return integer == null ? 0 : integer;
	}

	/**
	 * <p>getCssProperties.</p>
	 *
	 * @return a {@link CSSStyleDeclaration} object.
	 */
	protected final CSSStyleDeclaration getCssProperties() {
		final HTMLElementImpl element = this.element;
		return element == null ? null : element.getCurrentStyle();
	}

	/** {@inheritDoc} */
	@Override
	public int getDefaultDisplay() {
		return DISPLAY_INLINE;
	}

	/** {@inheritDoc} */
	@Override
	public int getDisplay() {
		final Integer d = this.iDisplay;
		if (d != null) {
			return d;
		}
		CSSValues display;
		int displayInt;
		final RenderState previous = this.getPreviousRenderState();

		if (previous != null && previous.getDisplay() == DISPLAY_FLEX_BOX) {
			final RFlex flex = new RFlex(previous);
			displayInt = flex.isFlexTable() ? DISPLAY_TABLE_CELL : DISPLAY_FLEX_CHILD;
			this.iDisplay = displayInt;
			return displayInt;
		} else {
			final CSSStyleDeclaration props = this.getCssProperties();
			final String displayText = props == null ? null : props.getDisplay();
			final String displayTextTL = Strings.isNotBlank(displayText) ? displayText : "";
			display = CSSValues.get(displayTextTL);
		}

        displayInt = switch (display) {
            case BLOCK -> DISPLAY_BLOCK;
            case NONE -> DISPLAY_NONE;
            case LIST_ITEM -> DISPLAY_LIST_ITEM;
            case TABLE -> DISPLAY_TABLE;
            case TABLE_CELL -> DISPLAY_TABLE_CELL;
            case TABLE_ROW -> DISPLAY_TABLE_ROW;
            case TABLE_CAPTION -> DISPLAY_TABLE_CAPTION;
            case TABLE_COLUMN -> DISPLAY_TABLE_COLUMN;
            case TABLE_COLUMN_GROUP -> DISPLAY_TABLE_COLUMN_GROUP;
            case INLINE -> DISPLAY_INLINE;
            case INLINE_BLOCK -> DISPLAY_INLINE_BLOCK;
            case INLINE_TABLE -> DISPLAY_INLINE_TABLE;
            case FLEX -> DISPLAY_FLEX_BOX;
            case INHERIT -> this.getPreviousRenderState().getDisplay();
            default -> this.getDefaultDisplay();
        };
		this.iDisplay = displayInt;
		return displayInt;
	}

	/** {@inheritDoc} */
	@Override
	public int getFloat() {
		final Integer p = this.cachedFloat;
		if (p != null) {
			return p;
		}
		final CSSStyleDeclaration props = this.getCssProperties();
		final String floatText = props == null ? null : props.getFloat();
		final String floatTextTL = Strings.isBlank(floatText) ? "" : floatText;
		final CSSValues flt = CSSValues.get(floatTextTL);
		int floatValue = switch (flt) {
            case LEFT -> FLOAT_LEFT;
            case RIGHT -> FLOAT_RIGHT;
            case INHERIT -> this.getPreviousRenderState().getFloat();
            default -> FLOAT_NONE;
        };
        this.cachedFloat = floatValue;
		return floatValue;
	}

	/** {@inheritDoc} */
	@Override
	public String getFlexDirection() {
		final CSSStyleDeclaration props = this.getCssProperties();
		final String flexDir = props == null ? null : props.getFlexDirection();
		final String flexDirText = Strings.isBlank(flexDir) ? "" : flexDir;
		final CSSValues flt = CSSValues.get(flexDirText);
        return switch (flt) {
            case COLUMN, COLUMN_REVERSE, ROW_REVERSE, ROW -> flexDirText;
            default -> CSSValues.ROW.getValue();
        };
	}
	
	/** {@inheritDoc} */
	@Override
	public String getFlexWrap() {
		final CSSStyleDeclaration props = this.getCssProperties();
		return props == null ? null : props.getFlexWrap();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getFlexFlow() {
		final CSSStyleDeclaration props = this.getCssProperties();
		return props == null ? null : props.getFlexFlow();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getJustifyContent() {
		final CSSStyleDeclaration props = this.getCssProperties();
		return props == null ? null : props.getJustifyContent();
	}

	/** {@inheritDoc} */
	@Override
	public Font getFont() {
		final CSSStyleDeclaration style = this.getCssProperties();
		final RenderState prs = this.prevRenderState;
		final HtmlRendererConfig config = element != null ? element.getHtmlRendererConfig() : new LocalHtmlRendererConfig();
		final FontKey key = FontValues.getDefaultFontKey(config);

		if (this.iFont != null) {
			return this.iFont;
		}

		if (style == null || Strings.isBlank(style.getFont())) {
			if (prs != null) {
				this.iFont = prs.getFont();
				return this.iFont;
			}
			this.iFont = FONT_FACTORY.getFont(key);
			return this.iFont;
		}

		final Font f = FONT_FACTORY.getFont(FontValues.getFontKey(key, element, style, prs));
		this.iFont = f;
		return f;
	}

	/** {@inheritDoc} */
	@Override
	public int getFontBase() {
		final RenderState prs = this.prevRenderState;
		return prs == null ? 3 : prs.getFontBase();
	}

	/** {@inheritDoc} */
	@Override
	public final FontMetrics getFontMetrics() {
		FontMetrics fm = this.iFontMetrics;
		if (fm == null) {
			// TODO getFontMetrics deprecated. How to get text width?
			fm = Toolkit.getDefaultToolkit().getFontMetrics(getFont());
			this.iFontMetrics = fm;
		}
		return fm;
	}

	/** {@inheritDoc} */
	@Override
	public HtmlInsets getMarginInsets() {
		HtmlInsets mi = this.marginInsets;
		if (mi != INVALID_INSETS) {
			return mi;
		}

		if("border-box".equals(getBoxSizing())){
			return INVALID_INSETS;
		}

		final CSSStyleDeclaration props = getCssProperties();
		if (props == null) {
			mi = null;
		} else {
			mi = MarginInsets.getMarginInsets(props, element, this);
		}
		this.marginInsets = mi;
		return mi;
	}

	/** {@inheritDoc} */
	@Override
	public int getOverflowX() {
		int overflow = this.overflowX;
		if (overflow != -1) {
			return overflow;
		}
		final CSSStyleDeclaration props = this.getCssProperties();
		if (props == null) {
			overflow = OVERFLOW_NONE;
		} else {
			String overflowText = props.getPropertyValue("overflow-x");
			if (overflowText == null) {
				overflowText = props.getOverflow();

				if (overflowText == null) {
					return OVERFLOW_NONE;
				}
			}
			final CSSValues overx = CSSValues.get(overflowText);
            overflow = switch (overx) {
                case SCROLL -> OVERFLOW_SCROLL;
                case AUTO -> OVERFLOW_AUTO;
                case HIDDEN -> OVERFLOW_HIDDEN;
                case VISIBLE -> OVERFLOW_VISIBLE;
                case INHERIT -> this.getPreviousRenderState().getOverflowX();
                default -> OVERFLOW_NONE;
            };
		}
		this.overflowX = overflow;
		return overflow;
	}

	/** {@inheritDoc} */
	@Override
	public int getOverflowY() {
		int overflow = this.overflowY;
		if (overflow != -1) {
			return overflow;
		}
		final CSSStyleDeclaration props = this.getCssProperties();
		if (props == null) {
			overflow = OVERFLOW_NONE;
		} else {
			String overflowText = props.getPropertyValue("overflow-y");
			if (overflowText == null) {
				overflowText = props.getOverflow();

				if (overflowText == null) {
					return OVERFLOW_NONE;
				}
			}
			final CSSValues overy = CSSValues.get(overflowText);
            overflow = switch (overy) {
                case SCROLL -> OVERFLOW_SCROLL;
                case AUTO -> OVERFLOW_AUTO;
                case HIDDEN -> OVERFLOW_HIDDEN;
                case VISIBLE -> OVERFLOW_VISIBLE;
                case INHERIT -> this.getPreviousRenderState().getOverflowY();
                default -> OVERFLOW_NONE;
            };
		}
		this.overflowY = overflow;
		return overflow;
	}

	/** {@inheritDoc} */
	@Override
	public Color getOverlayColor() {
		Color c = this.iOverlayColor;
		if (c != INVALID_COLOR) {
			return c;
		}
		final CSSStyleDeclaration props = getCssProperties();
		String colorValue = props == null ? null : props.getOverlayColor();
		if (Strings.isBlank(colorValue)) {
			final RenderState prs = this.prevRenderState;
			if (prs != null) {
				c = prs.getOverlayColor();
				this.iOverlayColor = c;
				return c;
			} else {
				colorValue = null;
			}
		}
		c = colorValue == null ? null : ColorFactory.getInstance().getColor(colorValue);
		this.iOverlayColor = c;
		return c;
	}

	/** {@inheritDoc} */
	@Override
	public HtmlInsets getPaddingInsets() {
		HtmlInsets mi = this.paddingInsets;
		if (mi != INVALID_INSETS) {
			return mi;
		}
		final CSSStyleDeclaration props = getCssProperties();
		if (props == null) {
			mi = null;
		} else {
			mi = MarginInsets.getPaddingInsets(props, element,this);
			this.paddingInsets = mi;
		}
		return mi;
	}

	/** {@inheritDoc} */
	@Override
	public int getPosition() {
		final Integer p = this.cachedPosition;
		if (p != null) {
			return p;
		}
		final CSSStyleDeclaration props = this.getCssProperties();
		int position;
		final String positionText = props == null ? null : props.getPosition();
		final String positionTextTL = Strings.isBlank(positionText) ? "" : positionText;
		final CSSValues pos = CSSValues.get(positionTextTL);
        position = switch (pos) {
            case ABSOLUTE -> POSITION_ABSOLUTE;
            case RELATIVE -> POSITION_RELATIVE;
            case FIXED -> POSITION_FIXED;
            case INHERIT -> this.getPreviousRenderState().getPosition();
            default -> POSITION_STATIC;
        };

		this.cachedPosition = position;
		return position;
	}

	/** {@inheritDoc} */
	@Override
	public RenderState getPreviousRenderState() {
		return this.prevRenderState;
	}

	/** {@inheritDoc} */
	@Override
	public Color getTextBackgroundColor() {
		final Color c = this.iTextBackgroundColor;
		if (c != INVALID_COLOR) {
			return c;
		}
		final Color localColor;
		if (getDisplay() != DISPLAY_INLINE) {
			// Background painted by block.
			localColor = null;
		} else {
			final BackgroundInfo binfo = getBackgroundInfo();
			localColor = binfo == null ? null : binfo.getBackgroundColor();
			if (localColor == null) {
				final RenderState prs = this.prevRenderState;
				if (prs != null) {
					final Color ancestorColor = prs.getTextBackgroundColor();
					if (ancestorColor != null) {
						this.iTextBackgroundColor = ancestorColor;
						return ancestorColor;
					}
				}
			}
		}
		this.iTextBackgroundColor = localColor;
		return localColor;
	}

	/** {@inheritDoc} */
	@Override
	public int getTextDecorationMask() {
		int td = this.iTextDecoration;
		if (td != -1) {
			return td;
		}
		final CSSStyleDeclaration props = this.getCssProperties();
		final String tdText = props == null ? null : props.getTextDecoration();
		if (tdText == null) {
			final RenderState prs = this.prevRenderState;
			if (prs != null) {
				td = prs.getTextDecorationMask();
				this.iTextDecoration = td;
				return td;
			}
		}
		td = 0;
		if (tdText != null) {
			final StringTokenizer tok = new StringTokenizer(tdText.toLowerCase(), ", \t\n\r");
			while (tok.hasMoreTokens()) {
				final String token = tok.nextToken();
				final CSSValues tkn = CSSValues.get(token);
				switch (tkn) {
				case UNDERLINE:
					td |= MASK_TEXTDECORATION_UNDERLINE;
					break;
				case LINE_THROUGH:
					td |= MASK_TEXTDECORATION_LINE_THROUGH;
					break;
				case BLINK:
					td |= MASK_TEXTDECORATION_BLINK;
					break;
				case OVERLINE:
					td |= MASK_TEXTDECORATION_OVERLINE;
					break;
				case INHERIT:
					td |= this.getPreviousRenderState().getTextDecorationMask();
					break;
				case NONE:
				case INITIAL:
				default:
					td |= MASK_TEXTDECORATION_NONE;
					break;
				}
			}
		}
		this.iTextDecoration = td;
		return td;
	}

	/** {@inheritDoc} */
	@Override
	public int getTextIndent(final int availSize) {
		// No caching for this one.
		final String tiText = getTextIndentText();
		if (Strings.isCssBlank(tiText)) {
			return 0;
		} else {
			return HtmlValues.getPixelSize(tiText, this, document.getDefaultView(), 0, availSize);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getTextIndentText() {
		String tiText = this.iTextIndentText;
		if (tiText != null) {
			return tiText;
		}
		final CSSStyleDeclaration props = getCssProperties();
		tiText = props == null ? null : props.getTextIndent();
		if (tiText == null) {
			final RenderState prs = this.prevRenderState;
			if (prs != null) {
				final String parentText = prs.getTextIndentText();
				this.iTextIndentText = parentText;
				return parentText;
			} else {
				tiText = "";
			}
		}
		return tiText;
	}

	/** {@inheritDoc} */
	@Override
	public int getTextTransform() {
		int tt = this.iTextTransform;
		if (tt != -1) {
			return tt;
		}
		final CSSStyleDeclaration props = this.getCssProperties();
		final String tdText = props == null ? null : props.getTextTransform();
		if (tdText == null) {
			final RenderState prs = this.prevRenderState;
			if (prs != null) {
				tt = prs.getTextTransform();
				this.iTextTransform = tt;
				return tt;
			}
		}
		tt = 0;
		final CSSValues text = CSSValues.get(tdText);
		switch (text) {
		case CAPITALIZE:
			tt |= TEXTTRANSFORM_CAPITALIZE;
			break;
		case UPPERCASE:
			tt |= TEXTTRANSFORM_UPPERCASE;
			break;
		case LOWERCASE:
			tt |= TEXTTRANSFORM_LOWERCASE;
			break;
		case INHERIT:
			tt |= this.getPreviousRenderState().getTextTransform();
			break;
		case NONE:
		case INITIAL:
		default:
			tt |= TEXTTRANSFORM_NONE;
			break;
		}
		this.iTextTransform = tt;
		return tt;
	}

	/** {@inheritDoc} */
	@Override
	public int getVisibility() {
		final Integer v = this.cachedVisibility;
		if (v != null) {
			return v;
		}
		final CSSStyleDeclaration props = this.getCssProperties();
		final int visibility;
		final String visibText = props == null ? null : props.getVisibility();
		final String visibTextTL = Strings.isBlank(visibText) ? "" : visibText;
		final CSSValues visy = CSSValues.get(visibTextTL);
        visibility = switch (visy) {
            case HIDDEN -> VISIBILITY_HIDDEN;
            case COLLAPSE -> VISIBILITY_COLLAPSE;
            case INHERIT -> this.getPreviousRenderState().getVisibility();
            default -> VISIBILITY_VISIBLE;
        };
		this.cachedVisibility = visibility;
		return visibility;
	}
	
	/** {@inheritDoc} */
	@Override
	public int getWhiteSpace() {
		if (RenderThreadState.getState().overrideNoWrap) {
			return WS_NOWRAP;
		}
		final Integer ws = this.iWhiteSpace;
		if (ws != null) {
			return ws;
		}
		final CSSStyleDeclaration props = getCssProperties();
		final String whiteSpaceText = props == null ? null : props.getWhiteSpace();
		final int wsValue;
		final String whiteSpaceTextTL = Strings.isBlank(whiteSpaceText) ? "" : whiteSpaceText;
		final CSSValues white = CSSValues.get(whiteSpaceTextTL);
		switch (white) {
		case NOWRAP:
			wsValue = WS_NOWRAP;
			break;
		case INHERIT:
			wsValue = this.getPreviousRenderState().getWhiteSpace();
			break;
		case INITIAL:
		default:
			if (whiteSpaceText == null) {
				final RenderState prs = this.prevRenderState;
				if (prs != null) {
					wsValue = prs.getWhiteSpace();
				} else {
					wsValue = WS_NORMAL;
				}
			} else {
				wsValue = WS_NORMAL;
			}
			break;
		}
		this.iWhiteSpace = wsValue;
		return wsValue;
	}

	/** {@inheritDoc} */
	@Override
	public final WordInfo getWordInfo(final String word) {
		// Expected to be called only in the GUI (rendering) thread.
		// No synchronization necessary.
		Map<String, WordInfo> map = this.iWordInfoMap;
		if (map == null) {
			map = new HashMap<>(1);
			this.iWordInfoMap = map;
		}
		WordInfo wi = map.get(word);
		if (wi != null) {
			return wi;
		}

		final FontMetrics fm = getFontMetrics();
		wi = WordInfo.builder()
				.fontMetrics(fm)
				.ascentPlusLeading(fm.getAscent() + fm.getLeading())
				.descent(fm.getDescent())
				.height(fm.getHeight())
				.width(fm.stringWidth(word))
				.build();
		map.put(word, wi);
		return wi;
	}

	/** {@inheritDoc} */
	@Override
	public int incrementCount(final String counter, final int nesting) {
		// Expected to be called only in the GUI thread.
		final RenderState prs = this.prevRenderState;
		if (prs != null) {
			return prs.incrementCount(counter, nesting);
		}
		Map<String, ArrayList<Integer>> counters = this.counters;
		if (counters == null) {
			counters = new HashMap<>();
			this.counters = counters;
			counters.put(counter, new ArrayList<>());
		}
		final ArrayList<Integer> counterArray = counters.get(counter);
		while (counterArray.size() <= nesting) {
			counterArray.add(null);
		}
		final Integer integer = counterArray.get(nesting);
		final int prevValue = (integer == null || integer == 0) ? 1 : integer;
		counterArray.set(nesting, prevValue + 1);
		return prevValue;
	}

	/** {@inheritDoc} */
	@Override
	public void invalidate() {
		final Map<String, WordInfo> map = this.iWordInfoMap;
		if (map != null) {
			map.clear();
		}
		this.iFont = null;
		this.iFontMetrics = null;
		this.iColor = null;
		this.iTextDecoration = -1;
		this.iBlankWidth = -1;
		this.alignXPercent = -1;
		this.iBackgroundColor = INVALID_COLOR;
		this.iTextBackgroundColor = INVALID_COLOR;
		this.iOverlayColor = INVALID_COLOR;
		this.iBackgroundInfo = INVALID_BACKGROUND_INFO;
		this.iDisplay = null;
		this.iTextIndentText = null;
		this.iWhiteSpace = null;
		this.marginInsets = INVALID_INSETS;
		this.paddingInsets = INVALID_INSETS;
		this.overflowX = -1;
		this.overflowY = -1;
		this.borderInfo = INVALID_BORDER_INFO;
		// Should NOT invalidate parent render state.
	}

	/** {@inheritDoc} */
	@Override
	public boolean isHighlight() {
		return this.iHighlight;
	}

	/** {@inheritDoc} */
	@Override
	public void resetCount(final String counter, final int nesting, final int value) {
		// Expected to be called only in the GUI thread.
		final RenderState prs = this.prevRenderState;
		if (prs != null) {
			prs.resetCount(counter, nesting, value);
		} else {
			Map<String, ArrayList<Integer>> counters = this.counters;
			if (counters == null) {
				counters = new HashMap<>();
				this.counters = counters;
				counters.put(counter, new ArrayList<>());
			}
			final ArrayList<Integer> counterArray = counters.get(counter);
			while (counterArray.size() <= nesting) {
				counterArray.add(null);
			}
			counterArray.set(nesting, value);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setHighlight(final boolean highlight) {
		this.iHighlight = highlight;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "StyleSheetRenderState[font=" + getFont() + ",textDecoration=" + getTextDecorationMask() + "]";
	}
	
	/** {@inheritDoc} */
	@Override
	public int getClear() {
		if (cachedClear == null) {
			final CSSStyleDeclaration props = this.getCssProperties();
			final String clearStr = props != null ? props.getClear() : "";
			final CSSValues clear = CSSValues.get(clearStr);
			switch (clear) {
			case RIGHT:
				cachedClear = LineBreak.RIGHT;
				break;
			case LEFT:
				cachedClear = LineBreak.LEFT;
				break;
			default:
				cachedClear = LineBreak.NONE;
				break;
			}
		}
		return cachedClear;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getBoxSizing() {
		final CSSStyleDeclaration props = this.getCssProperties();
		final String boxSizing = props != null ? props.getBoxSizing() : "";
		final String visibTextTL = Strings.isBlank(boxSizing) ? "" : boxSizing;
		final CSSValues box = CSSValues.get(visibTextTL);
        return switch (box) {
            case INHERIT -> this.getPreviousRenderState().getBoxSizing();
            default -> boxSizing;
        };

	}

    /** {@inheritDoc} */
    @Override
    public String getLeft() {
        final CSSStyleDeclaration props = this.getCssProperties();
        return props == null ? null : props.getLeft();
    }

    /** {@inheritDoc} */
    @Override
    public String getTop() {
        final CSSStyleDeclaration props = this.getCssProperties();
        return props == null ? null : props.getTop();
    }

    /** {@inheritDoc} */
    @Override
    public String getRight() {
        final CSSStyleDeclaration props = this.getCssProperties();
        return props == null ? null : props.getRight();
    }

    /** {@inheritDoc} */
    @Override
    public String getBottom() {
        final CSSStyleDeclaration props = this.getCssProperties();
        return props == null ? null : props.getBottom();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setCursor(final Cursor cursor) {
       this.cursor = cursor;
        
    }

	/** {@inheritDoc} */
	@Override
	public Cursor getCursor() {
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor prevCursorOpt = null;
		if(element == null) return null;
		final CSSStyleDeclaration props = element.getStyle();
		final HtmlRendererConfig config = element.getHtmlRendererConfig();

		if (this.cursor != null) {
			prevCursorOpt = this.cursor;
		}

		if (props != null) {
			final String cursor = props.getPropertyValue("cursor");
			final CSSValues key = CSSValues.get(cursor);

            prevCursorOpt = switch (key) {
                case AUTO, TEXT_CSS -> Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
                case CROSSHAIR -> Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
                case E_RESIZE, EW_RESIZE -> Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
                case MOVE -> Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
                case N_RESIZE -> Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
                case NE_RESIZE, NESW_RESIZE -> Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
                case NW_RESIZE, NWSE_RESIZE -> Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
                case GRAB, POINTER -> Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
                case S_RESIZE -> Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
                case SE_RESIZE -> Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
                case SW_RESIZE -> Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
                case W_RESIZE -> Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
                case WAIT, PROGRESS -> Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
                case ZOOM_IN ->
                        toolkit.createCustomCursor(new ImageIcon(config.getResourceFile("zoomin.png")).getImage(), new Point(5, 5), "zoomin");
                case ZOOM_OUT ->
                        toolkit.createCustomCursor(new ImageIcon(config.getResourceFile("zoomout.png")).getImage(), new Point(5, 5), "zoomout");
                case INHERIT -> this.getPreviousRenderState().getCursor();
                default -> Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
            };
		}
		return prevCursorOpt;
	}
   
   /** {@inheritDoc} */
   @Override
   public String getVerticalAlign() {
	   final CSSStyleDeclaration props = this.getCssProperties();
       return props == null ? null : props.getVerticalAlign();
   }
}
