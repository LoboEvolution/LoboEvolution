/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Apr 16, 2005
 */
package org.loboevolution.html.renderstate;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.color.ColorFactory;
import org.loboevolution.font.FontFactory;
import org.loboevolution.font.FontKey;
import org.loboevolution.font.LAFSettings;
import org.loboevolution.html.HtmlAttributeProperties;
import org.loboevolution.html.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.info.BackgroundInfo;
import org.loboevolution.html.info.BorderInfo;
import org.loboevolution.html.info.WordInfo;
import org.loboevolution.html.renderer.LineBreak;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.CSS3Properties;
import org.loboevolution.html.style.CSSValuesProperties;
import org.loboevolution.html.style.FontValues;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.style.RenderThreadState;
import org.loboevolution.util.Strings;
import org.loboevolution.w3c.html.HTMLElement;

/**
 * The Class StyleSheetRenderState.
 *
 * @author J. H. S.
 */
public class StyleSheetRenderState implements RenderState, HtmlAttributeProperties, CSSValuesProperties {

	/** The Constant logger. */
	protected static final Logger logger = LogManager.getLogger(StyleSheetRenderState.class);

	/** The Constant FONT_FACTORY. */
	private static final FontFactory FONT_FACTORY = FontFactory.getInstance();

	/** The Constant DEFAULT_FONT. */
	private static final Font DEFAULT_FONT = FONT_FACTORY.getFont(new FontKey());
	
	/** The Constant DEFAULT_LINE_HEIGHT. */
	protected static final String DEFAULT_LINE_HEIGHT = String.valueOf(1.6f * new LAFSettings().getIstance().getFontSize());

	/** The Constant INVALID_BACKGROUND_INFO. */
	protected static final BackgroundInfo INVALID_BACKGROUND_INFO = new BackgroundInfo();

	/** The Constant INVALID_COLOR. */
	protected static final Color INVALID_COLOR = new Color(100, 0, 100);
	
	/** The element. */
	protected final HTMLElementImpl element;

	/** The document. */
	protected final HTMLDocumentImpl document;

	/** The prev render state. */
	protected final RenderState prevRenderState;

	/** The i background info. */
	protected BackgroundInfo iBackgroundInfo = INVALID_BACKGROUND_INFO;

	/** The border info. */
	protected BorderInfo borderInfo = BorderRenderState.INVALID_BORDER_INFO;
	
	/** The margin insets. */
	protected HtmlInsets marginInsets = MarginRenderState.INVALID_INSETS;
	
	/** The padding insets. */
	protected HtmlInsets paddingInsets = PaddingRenderState.INVALID_INSETS;
	
	/** The i white space. */
	protected Integer iWhiteSpace;

	/** The overflow x. */
	protected int overflowX = -1;

	/** The overflow y. */
	protected int overflowY = -1;

	/** The i font. */
	private Font iFont;

	/** The i font metrics. */
	private FontMetrics iFontMetrics;

	/** The i color. */
	private Color iColor;

	/** The i background color. */
	private Color iBackgroundColor = INVALID_COLOR;

	/** The i text background color. */
	private Color iTextBackgroundColor = INVALID_COLOR;

	/** The i overlay color. */
	private Color iOverlayColor = INVALID_COLOR;
	
	private String iLineHeight;

	/** The cursor. */
	private Optional<Cursor> cursor;

	/** The counters. */
	private Map<String, ArrayList<Object>> counters = null;

	/** The i text decoration. */
	private int iTextDecoration = -1;

	/** The i text transform. */
	private int iTextTransform = -1;

	/** The i blank width. */
	private int iBlankWidth = -1;

	/** The i highlight. */
	private boolean iHighlight;

	/** The i display. */
	private Integer iDisplay;

	/** The i text indent text. */
	private String iTextIndentText = null;

	/** The align x percent. */
	private int alignXPercent = -1;

	/** The cached visibility. */
	private Integer cachedVisibility;

	/** The cached position. */
	private Integer cachedPosition;

	/** The cached float. */
	private Integer cachedFloat;

	/** The i word info map. */
	private Map<String, WordInfo> iWordInfoMap = null;

	/**
	 * Instantiates a new style sheet render state.
	 *
	 * @param prevRenderState
	 *            the prev render state
	 * @param element
	 *            the element
	 */
	public StyleSheetRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		this.prevRenderState = prevRenderState;
		this.element = element;
		this.document = (HTMLDocumentImpl) element.getOwnerDocument();
	}

	/**
	 * Instantiates a new style sheet render state.
	 *
	 * @param document
	 *            the document
	 */
	public StyleSheetRenderState(HTMLDocumentImpl document) {
		this.prevRenderState = null;
		this.element = null;
		this.document = document;
	}

	/**
	 * Gets the default display.
	 *
	 * @return the default display
	 */
	protected int getDefaultDisplay() {
		return DISPLAY_INLINE;
	}

	/**
	 * Gets the default color.
	 *
	 * @return the default color
	 */
	protected Color getDefaultColor() {
		return new LAFSettings().getIstance().getColor();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getClear()
	 */
	@Override
	public int getClear() {
		AbstractCSSProperties props = this.getCssProperties();
		int clear = 0;
		if (props == null || (props!=null && props.getClear() == null)) {
			clear = LineBreak.NONE;
		} else {
			String clearStr = props.getClear();
			switch (clearStr) {
			case "both":
				clear = LineBreak.ALL;
				break;
			case "left":
				clear = LineBreak.LEFT;
				break;
			case "right":
				clear = LineBreak.RIGHT;
				break;
			default:
				clear = LineBreak.NONE;
				break;
			}
		}
		return  clear;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getDisplay()
	 */
	@Override
	public int getDisplay() {
		Integer d = this.iDisplay;
		if (d != null) {
			return d.intValue();
		}
		CSS3Properties props = this.getCssProperties();
		String displayText = props == null ? null : props.getDisplay();
		int displayInt = 0;
		if (displayText != null) {
			String displayTextTL = displayText.toLowerCase();

			switch (displayTextTL) {
			case BLOCK:
				displayInt = DISPLAY_BLOCK;
				break;
			case NONE:
				displayInt = DISPLAY_NONE;
				break;
			case LIST_ITEM:
				displayInt = DISPLAY_LIST_ITEM;
				break;
			case TABLE:
				displayInt = DISPLAY_TABLE;
				break;
			case TABLE_CELL:
				displayInt = DISPLAY_TABLE_CELL;
				break;
			case TABLE_ROW:
				displayInt = DISPLAY_TABLE_ROW;
				break;
			case INLINE:
				displayInt = DISPLAY_INLINE;
				break;
			case INLINE_BLOCK:
				displayInt = DISPLAY_INLINE_BLOCK;
				break;
			case INLINE_TABLE:
				displayInt = DISPLAY_INLINE_TABLE;
				break;
			case INHERIT:
				displayInt = this.getPreviousRenderState().getDisplay();
				break;
			case INITIAL:
			default:
				displayInt = this.getDefaultDisplay();
				break;
			}
		} else {
			displayInt = this.getDefaultDisplay();
		}
		d = Integer.valueOf(displayInt);
		this.iDisplay = d;
		return displayInt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderstate.RenderState#getPreviousRenderState()
	 */
	@Override
	public RenderState getPreviousRenderState() {
		return this.prevRenderState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getFontBase()
	 */
	@Override
	public int getFontBase() {
		RenderState prs = this.prevRenderState;
		return prs == null ? 3 : prs.getFontBase();
	}

	/**
	 * Repaint.
	 */
	public void repaint() {
		// Dummy implementation
	}

	/**
	 * Gets the css properties.
	 *
	 * @return the css properties
	 */
	protected final AbstractCSSProperties getCssProperties() {
		HTMLElementImpl element = this.element;
		return element == null ? null : element.getCurrentStyle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#invalidate()
	 */
	@Override
	public void invalidate() {
		Map<String, WordInfo> map = this.iWordInfoMap;
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
		this.iLineHeight = String.valueOf(1.6f * new LAFSettings().getIstance().getFontSize());
		this.marginInsets = MarginRenderState.INVALID_INSETS;
		this.paddingInsets = PaddingRenderState.INVALID_INSETS;
		this.overflowX = -1;
		this.overflowY = -1;
		this.borderInfo = BorderRenderState.INVALID_BORDER_INFO;
		// Should NOT invalidate parent render state.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getFont()
	 */
	@Override
	public Font getFont() {
		
		AbstractCSSProperties style = this.getCssProperties();
		RenderState prs = this.prevRenderState;
		
		if (this.iFont != null) {
			return this.iFont;
		}
		
		if (style == null) {
			if (prs != null) {
				this.iFont = prs.getFont();
				return this.iFont;
			}
			this.iFont = DEFAULT_FONT;
			return DEFAULT_FONT;
		}
		
		HTMLDocumentImpl document = this.document;
		FontKey key = new FontKey();
		key.setFontFamily(FontValues.getFontFamily(style.getFontFamily(), prs));
		key.setFontStyle(FontValues.getFontStyle(style.getFontStyle()));
		key.setFontVariant(style.getFontVariant());
		key.setFontWeight(FontValues.getFontWeight(style.getFontWeight()));
		key.setFontSize(Float.valueOf(FontValues.getFontSize(style.getFontSize(), prs)));
		key.setLocales(document == null ? null : document.getLocales());
		key.setSuperscript(FontValues.getFontSuperScript(style.getVerticalAlign(), prs));
		key.setLetterSpacing(HtmlValues.getPixelSize(style.getLetterSpacing(), prs, 0));
		key.setStrikethrough(FontValues.getFontStrikeThrough(style.getTextDecoration()));
		key.setUnderline(FontValues.getFontUnderline(style.getTextDecoration()));
		Font f = FONT_FACTORY.getFont(key);
		this.iFont = f;
		return f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getColor()
	 */
	@Override
	public Color getColor() {
		Color c = this.iColor;
		if (c != null) {
			return c;
		}
		AbstractCSSProperties props = this.getCssProperties();
		String colorValue = props == null ? null : props.getColor();

		if (INHERIT.equals(colorValue) && this.getPreviousRenderState() != null) {
			return this.getPreviousRenderState().getColor();
		} else if (INITIAL.equals(colorValue)) {
			return getDefaultColor();
		} else if (colorValue == null || "".equals(colorValue)) {
			RenderState prs = this.prevRenderState;
			if (prs != null) {
				c = prs.getColor();
				this.iColor = c;
				return c;
			} else {
				this.iColor = getDefaultColor();
				return getDefaultColor();
			}
		} else {
			c = colorValue == null ? null : ColorFactory.getInstance().getColor(colorValue);
			this.iColor = c;
		}
		return c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getBackgroundColor()
	 */
	@Override
	public Color getBackgroundColor() {
		Color c = this.iBackgroundColor;
		if (!INVALID_COLOR.equals(c)) {
			return c;
		}
		Color localColor;
		BackgroundInfo binfo = this.getBackgroundInfo();
		localColor = binfo == null ? null : binfo.getBackgroundColor();
		if (localColor == null && this.getDisplay() == DISPLAY_INLINE) {
			RenderState prs = this.prevRenderState;
			if (prs != null) {
				Color ancestorColor = prs.getBackgroundColor();
				if (ancestorColor != null) {
					this.iBackgroundColor = ancestorColor;
					return ancestorColor;
				}
			}
		}
		this.iBackgroundColor = localColor;
		return localColor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderstate.RenderState#getTextBackgroundColor()
	 */
	@Override
	public Color getTextBackgroundColor() {
		Color c = this.iTextBackgroundColor;
		if (!INVALID_COLOR.equals(c)) {
			return c;
		}
		Color localColor;
		if (this.getDisplay() != DISPLAY_INLINE) {
			// Background painted by block.
			localColor = null;
		} else {
			BackgroundInfo binfo = this.getBackgroundInfo();
			localColor = binfo == null ? null : binfo.getBackgroundColor();
			if (localColor == null) {
				RenderState prs = this.prevRenderState;
				if (prs != null) {
					Color ancestorColor = prs.getTextBackgroundColor();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getOverlayColor()
	 */
	@Override
	public Color getOverlayColor() {

		AbstractCSSProperties style = this.getCssProperties();
		RenderState prs = this.prevRenderState;
		if (!INVALID_COLOR.equals(this.iOverlayColor)) {
			return this.iOverlayColor;
		}
		
		if (style == null) {
			if (prs != null) {
				this.iOverlayColor = prs.getOverlayColor();
				return this.iOverlayColor;
			}
			this.iOverlayColor = null;
			return null;
		}
		
		this.iOverlayColor = ColorFactory.getInstance().getColor(style.getOverlayColor());
		return this.iOverlayColor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getTextDecorationMask()
	 */
	@Override
	public int getTextDecorationMask() {
		int td = this.iTextDecoration;
		if (td != -1) {
			return td;
		}
		AbstractCSSProperties props = this.getCssProperties();
		String tdText = props == null ? null : props.getTextDecoration();
		if (tdText == null) {
			RenderState prs = this.prevRenderState;
			if (prs != null) {
				td = prs.getTextDecorationMask();
				this.iTextDecoration = td;
				return td;
			}
		}
		td = 0;
		if (tdText != null) {
			StringTokenizer tok = new StringTokenizer(tdText.toLowerCase(), ", \t\n\r");
			while (tok.hasMoreTokens()) {
				String token = tok.nextToken();
				switch (token) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getTextTransform()
	 */
	@Override
	public int getTextTransform() {
		int tt = this.iTextTransform;
		if (tt != -1) {
			return tt;
		}
		AbstractCSSProperties props = this.getCssProperties();
		String tdText = props == null ? null : props.getTextTransform();
		if (tdText == null) {
			RenderState prs = this.prevRenderState;
			if (prs != null) {
				tt = prs.getTextTransform();
				this.iTextTransform = tt;
				return tt;
			}
		}
		tt = 0;
		if (tdText != null) {

			switch (tdText) {
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
		}
		this.iTextTransform = tt;
		return tt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getFontMetrics()
	 */
	@Override
	public final FontMetrics getFontMetrics() {
		FontMetrics fm = this.iFontMetrics;
		if (fm == null) {
			// TODO getFontMetrics deprecated. How to get text width?
			fm = Toolkit.getDefaultToolkit().getFontMetrics(this.getFont());
			this.iFontMetrics = fm;
		}
		return fm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getBlankWidth()
	 */
	@Override
	public int getBlankWidth() {
		int bw = this.iBlankWidth;
		if (bw == -1) {
			bw = this.getFontMetrics().charWidth(' ');
			this.iBlankWidth = bw;
		}
		return bw;
	}

	/**
	 * Checks if is highlight.
	 *
	 * @return Returns the iHighlight.
	 */
	@Override
	public boolean isHighlight() {
		return this.iHighlight;
	}

	/**
	 * Sets the highlight.
	 *
	 * @param highlight
	 *            The iHighlight to set.
	 */
	@Override
	public void setHighlight(boolean highlight) {
		this.iHighlight = highlight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getWordInfo(java.lang.
	 * String)
	 */
	@Override
	public final WordInfo getWordInfo(String word) {
		// Expected to be called only in the GUI (rendering) thread.
		// No synchronization necessary.
		Map<String, WordInfo> map = this.iWordInfoMap;
		if (map == null) {
			map = new HashMap<String, WordInfo>(1);
			this.iWordInfoMap = map;
		}
		WordInfo wi = map.get(word);
		if (wi != null) {
			return wi;
		}
		wi = new WordInfo();
		FontMetrics fm = this.getFontMetrics();
		wi.setFontMetrics(fm);
		wi.setAscentPlusLeading(fm.getAscent() + fm.getLeading());
		wi.setDescent(fm.getDescent());
		wi.setHeight(fm.getHeight());
		wi.setWidth(fm.stringWidth(word));
		map.put(word, wi);
		return wi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getAlignXPercent()
	 */
	@Override
	public int getAlignXPercent() {
		int axp = this.alignXPercent;
		if (axp != -1) {
			return axp;
		}
		CSS3Properties props = this.getCssProperties();
		String textAlign = props == null ? null : props.getTextAlign();
		if (Strings.isBlank(textAlign)) {
			// Fall back to align attribute.
			HTMLElement element = this.element;
			if (element != null) {
				textAlign = element.getAttribute(HtmlAttributeProperties.ALIGN);
				if (Strings.isBlank(textAlign)) {
					RenderState prs = this.prevRenderState;
					if (prs != null) {
						return prs.getAlignXPercent();
					}
					textAlign = null;
				}
			}
		}
		if (textAlign == null) {
			axp = 0;
		} else if (CENTER.equalsIgnoreCase(textAlign)) {
			axp = 50;
		} else if (RIGHT.equalsIgnoreCase(textAlign)) {
			axp = 100;
		} else if (INHERIT.equalsIgnoreCase(textAlign)) {
			axp = this.getPreviousRenderState().getAlignXPercent();
		} else if (INITIAL.equalsIgnoreCase(textAlign)) {
			axp = 0;
		} else {
			axp = 0;
		}
		this.alignXPercent = axp;
		return axp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getAlignYPercent()
	 */
	@Override
	public int getAlignYPercent() {
		// This is only settable in table cells.
		// TODO: Does it work with display: table-cell?
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderstate.RenderState#getCount(java.lang.String,
	 * int)
	 */
	@Override
	public int getCount(String counter, int nesting) {
		// Expected to be called only in GUI thread.
		RenderState prs = this.prevRenderState;
		if (prs != null) {
			return prs.getCount(counter, nesting);
		}
		Map<String, ArrayList<Object>> counters = this.counters;
		if (counters == null) {
			return 0;
		}
		ArrayList<Object> counterArray = counters.get(counter);
		if (nesting < 0 || nesting >= counterArray.size()) {
			return 0;
		}
		Integer integer = (Integer) counterArray.get(nesting);
		return integer == null ? 0 : integer.intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderstate.RenderState#resetCount(java.lang.String,
	 * int, int)
	 */
	@Override
	public void resetCount(String counter, int nesting, int value) {
		// Expected to be called only in the GUI thread.
		RenderState prs = this.prevRenderState;
		if (prs != null) {
			prs.resetCount(counter, nesting, value);
		} else {
			Map<String, ArrayList<Object>> counters = this.counters;
			if (counters == null) {
				counters = new HashMap<String, ArrayList<Object>>(2);
				this.counters = counters;
				counters.put(counter, new ArrayList<Object>(0));
			}
			ArrayList<Object> counterArray = counters.get(counter);
			while (counterArray.size() <= nesting) {
				counterArray.add(null);
			}
			counterArray.set(nesting, Integer.valueOf(value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderstate.RenderState#incrementCount(java.lang.
	 * String, int)
	 */
	@Override
	public int incrementCount(String counter, int nesting) {
		// Expected to be called only in the GUI thread.
		RenderState prs = this.prevRenderState;
		if (prs != null) {
			return prs.incrementCount(counter, nesting);
		}
		Map<String, ArrayList<Object>> counters = this.counters;
		if (counters == null) {
			counters = new HashMap<String, ArrayList<Object>>(2);
			this.counters = counters;
			counters.put(counter, new ArrayList<Object>(0));
		}
		ArrayList<Object> counterArray = counters.get(counter);
		while (counterArray.size() <= nesting) {
			counterArray.add(null);
		}
		Integer integer = (Integer) counterArray.get(nesting);
		int prevValue = integer == null ? 0 : integer.intValue();
		counterArray.set(nesting, Integer.valueOf(prevValue + 1));
		return prevValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getBackgroundInfo()
	 */
	@Override
	public BackgroundInfo getBackgroundInfo() {

		BackgroundInfo binfo = this.iBackgroundInfo;
		AbstractCSSProperties props = this.getCssProperties();
		BackgroundRenderState bg = new BackgroundRenderState();

		if (!INVALID_BACKGROUND_INFO.equals(binfo)) {
			return binfo;
		}

		if (element != null && !"BODY".equals(element.getNodeName())) {
			binfo = null;
		}

		if (props != null) {

			String backgroundText = props.getBackground();
			List<String> backList = new ArrayList<String>();
			
			if (backgroundText == null) {
				backList.add(props.getBackgroundColor());
				backList.add(props.getBackgroundImage());
				backList.add(props.getBackgroundRepeat());
				backList.add(props.getBackgroundPosition());
			} else{
				BackgroundRenderState brs = new BackgroundRenderState();
				backList = brs.spliBackground(backgroundText);
			}
			
			for (String back : backList) {
				if (!Strings.isBlank(back)) {
					
					if (binfo == null) {
						binfo = new BackgroundInfo();
					}
					
					switch (back.toLowerCase()) {
					case INHERIT:
						binfo.setBackgroundColor(this.getPreviousRenderState().getBackgroundColor());
						break;
					case INITIAL:
						binfo.setBackgroundColor(Color.WHITE);
						break;
					default:
						binfo = bg.applyBackgroundImage(binfo, back, this.document);
						binfo = bg.applyBackgroundPosition(binfo, back, this);
						binfo = bg.applyBackgroundRepeat(binfo, back);
						binfo = bg.applyBackground(binfo, back, this);
						break;
					}
				}
			}
		}
		this.iBackgroundInfo = binfo;
		return binfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getTextIndentText()
	 */
	@Override
	public String getTextIndentText() {
		String tiText = this.iTextIndentText;
		if (tiText != null) {
			return tiText;
		}
		AbstractCSSProperties props = this.getCssProperties();
		tiText = props == null ? null : props.getTextIndent();
		if (tiText == null) {
			RenderState prs = this.prevRenderState;
			if (prs != null) {
				String parentText = prs.getTextIndentText();
				this.iTextIndentText = parentText;
				return parentText;
			} else {
				tiText = "";
			}
		}
		return tiText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getTextIndent(int)
	 */
	@Override
	public int getTextIndent(int availSize) {
		// No caching for this one.
		String tiText = this.getTextIndentText();
		if (Strings.isBlank(tiText)) {
			return 0;
		} else {
			return HtmlValues.getPixelSize(tiText, this, 0, availSize);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getWhiteSpace()
	 */
	@Override
	public int getWhiteSpace() {
		if (RenderThreadState.getState().overrideNoWrap) {
			return WS_NOWRAP;
		}
		Integer ws = this.iWhiteSpace;
		if (ws != null) {
			return ws.intValue();
		}
		AbstractCSSProperties props = this.getCssProperties();
		String whiteSpaceText = props == null ? null : props.getWhiteSpace();
		int wsValue;
		if (whiteSpaceText == null) {
			RenderState prs = this.prevRenderState;
			if (prs != null) {
				wsValue = prs.getWhiteSpace();
			} else {
				wsValue = WS_NORMAL;
			}
		} else {
			String whiteSpaceTextTL = whiteSpaceText.toLowerCase();
			if (HtmlAttributeProperties.NOWRAP.equals(whiteSpaceTextTL)) {
				wsValue = WS_NOWRAP;
			} else if (PRE.equals(whiteSpaceTextTL)) {
				wsValue = WS_PRE;
			} else if (INHERIT.equals(whiteSpaceTextTL)) {
				wsValue = this.getPreviousRenderState().getWhiteSpace();
			} else if (INITIAL.equals(whiteSpaceTextTL)) {
				wsValue = WS_NORMAL;
			} else {
				wsValue = WS_NORMAL;
			}
		}
		this.iWhiteSpace = Integer.valueOf(wsValue);
		return wsValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getMarginInsets()
	 */
	@Override
	public HtmlInsets getMarginInsets() {
		HtmlInsets mi = this.marginInsets;
		if (!MarginRenderState.INVALID_INSETS.equals(mi)) {
			return mi;
		}
		AbstractCSSProperties props = this.getCssProperties();
		if (props == null) {
			mi = null;
		} else {
			mi = MarginRenderState.getMarginInsets(props, this);
		}
		this.marginInsets = mi;
		return mi;
	}
	
    /*
     * (non-Javadoc)
     * 
     * @see org.loboevolution.html.renderstate.RenderState#getPaddingInsets()
     */
    @Override
    public HtmlInsets getPaddingInsets() {
        HtmlInsets mi = this.paddingInsets;
        if (!PaddingRenderState.INVALID_INSETS.equals(mi)) {
            return mi;
        }
        AbstractCSSProperties props = this.getCssProperties();
        if (props == null) {
            mi = null;
        } else {
            mi = PaddingRenderState.getPaddingInsets(props, this);
            this.paddingInsets = mi;
        }
        return mi;
    }



	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getVisibility()
	 */
	@Override
	public int getVisibility() {
		Integer v = this.cachedVisibility;
		if (v != null) {
			return v.intValue();
		}
		AbstractCSSProperties props = this.getCssProperties();
		int visibility;
		if (props == null) {
			visibility = VISIBILITY_VISIBLE;
		} else {
			String visibText = props.getVisibility();
			if (Strings.isBlank(visibText)) {
				visibility = VISIBILITY_VISIBLE;
			} else {
				String visibTextTL = visibText.toLowerCase();

				switch (visibTextTL) {
				case HtmlAttributeProperties.HIDDEN:
					visibility = VISIBILITY_HIDDEN;
					break;
				case VISIBLE:
					visibility = VISIBILITY_VISIBLE;
					break;
				case COLLAPSE:
					visibility = VISIBILITY_COLLAPSE;
					break;
				case INHERIT:
					visibility = this.getPreviousRenderState().getVisibility();
					break;
				case INITIAL:
				default:
					visibility = VISIBILITY_VISIBLE;
					break;
				}
			}
		}
		this.cachedVisibility = Integer.valueOf(visibility);
		return visibility;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getPosition()
	 */
	@Override
	public int getPosition() {
		Integer p = this.cachedPosition;
		if (p != null) {
			return p.intValue();
		}
		AbstractCSSProperties props = this.getCssProperties();
		int position = 0;
		if (props == null) {
			position = POSITION_STATIC;
		} else {
			String positionText = props.getPosition();
			if (Strings.isBlank(positionText)) {
				position = POSITION_STATIC;
			} else {
				String positionTextTL = positionText.toLowerCase();

				switch (positionTextTL) {
				case ABSOLUTE:
					position = POSITION_ABSOLUTE;
					break;
				case RELATIVE:
					position = POSITION_RELATIVE;
					break;
				case FIXED:
					position = POSITION_FIXED;
					break;
				case INHERIT:
					position = this.getPreviousRenderState().getPosition();
					break;
				case STATIC:
				case INITIAL:
				default:
					position = POSITION_STATIC;
					break;
				}
			}
		}
		this.cachedPosition = Integer.valueOf(position);
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getFloat()
	 */
	@Override
	public int getFloat() {
		Integer p = this.cachedFloat;
		if (p != null) {
			return p.intValue();
		}
		AbstractCSSProperties props = this.getCssProperties();
		int floatValue = 0;
		if (props == null) {
			floatValue = FLOAT_NONE;
		} else {
			String floatText = props.getFloat();
			if (Strings.isBlank(floatText)) {
				floatValue = FLOAT_NONE;
			} else {
				String floatTextTL = floatText.toLowerCase();

				switch (floatTextTL) {
				case LEFT:
					floatValue = FLOAT_LEFT;
					break;
				case RIGHT:
					floatValue = FLOAT_RIGHT;
					break;
				case NONE:
					floatValue = FLOAT_NONE;
					break;
				case INHERIT:
					floatValue = this.getPreviousRenderState().getFloat();
					break;
				case INITIAL:
				default:
					floatValue = FLOAT_NONE;
					break;
				}
			}
		}
		this.cachedFloat = Integer.valueOf(floatValue);
		return floatValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StyleSheetRenderState[font=" + this.getFont() + ",textDecoration=" + this.getTextDecorationMask() + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getOverflowX()
	 */
	@Override
	public int getOverflowX() {
		int overflow = this.overflowX;
		if (overflow != -1) {
			return overflow;
		}
		AbstractCSSProperties props = this.getCssProperties();
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

			switch (overflowText) {
			case SCROLL:
				overflow = OVERFLOW_SCROLL;
				break;
			case AUTO:
				overflow = OVERFLOW_AUTO;
				break;
			case HtmlAttributeProperties.HIDDEN:
				overflow = OVERFLOW_HIDDEN;
				break;
			case VISIBLE:
				overflow = OVERFLOW_VISIBLE;
				break;
			case INHERIT:
				overflow = this.getPreviousRenderState().getOverflowX();
				break;
			case INITIAL:
			default:
				overflow = OVERFLOW_NONE;
				break;
			}
		}
		this.overflowX = overflow;
		return overflow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getOverflowY()
	 */
	@Override
	public int getOverflowY() {
		int overflow = this.overflowY;
		if (overflow != -1) {
			return overflow;
		}
		AbstractCSSProperties props = this.getCssProperties();
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

			switch (overflowText) {
			case SCROLL:
				overflow = OVERFLOW_SCROLL;
				break;
			case AUTO:
				overflow = OVERFLOW_AUTO;
				break;
			case HtmlAttributeProperties.HIDDEN:
				overflow = OVERFLOW_HIDDEN;
				break;
			case VISIBLE:
				overflow = OVERFLOW_VISIBLE;
				break;
			case INHERIT:
				overflow = this.getPreviousRenderState().getOverflowY();
				break;
			case INITIAL:
			default:
				overflow = OVERFLOW_NONE;
				break;
			}
		}
		this.overflowY = overflow;
		return overflow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getBorderInfo()
	 */
	@Override
	public BorderInfo getBorderInfo() {
		BorderRenderState brs = new BorderRenderState();
		BorderInfo binfo = this.borderInfo;
		if (!BorderRenderState.INVALID_BORDER_INFO.equals(binfo)) {
			return binfo;
		}
		AbstractCSSProperties props = this.getCssProperties();
		if (props != null) {
			binfo = BorderRenderState.getBorderInfo(props, this);
		} else {
			binfo = null;
		}
		
		this.borderInfo = brs.borderInfo(binfo, this);
		return this.borderInfo;
	}

	@Override
	public void setCursor(Optional<Cursor> cursor) {
		this.cursor = cursor;

	}

	@Override
	public Optional<Cursor> getCursor() {

		Optional<Cursor> prevCursorOpt = Optional.empty();
		AbstractCSSProperties props = this.getCssProperties();

		if (this.cursor != null) {
			prevCursorOpt = this.cursor;
		}

		if (props == null) {
			return prevCursorOpt;
		} else {
			String cursor = props.getPropertyValue("cursor");
			if (cursor == null) {
				return prevCursorOpt;
			} else {
				String cursorTL = cursor.toLowerCase();

				switch (cursorTL) {
				case AUTO:
				case TEXT_CSS:
					prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
					break;
				case CROSSHAIR:
					prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
					break;
				case E_RESIZE:
					prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
					break;
				case MOVE:
					prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
					break;
				case N_RESIZE:
					prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
					break;
				case NE_RESIZE:
					prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
					break;
				case NW_RESIZE:
					prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
					break;
				case GRAB:
				case POINTER:
					prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					break;
				case S_RESIZE:
					prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
					break;
				case SE_RESIZE:
					prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
					break;
				case SW_RESIZE:
					prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
					break;
				case W_RESIZE:
					prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
					break;
				case WAIT:
					prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					break;
				case INHERIT:
					prevCursorOpt = this.getPreviousRenderState().getCursor();
					break;
				case DEFAULT:
				case INITIAL:
				default:
					prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					break;
				}
			}

			return prevCursorOpt;
		}
	}

	@Override
	public String getBoxSizing() {
		AbstractCSSProperties props = this.getCssProperties();
		if (props != null) {
			String boxSizing = props.getBoxSizing();
			switch (boxSizing) {
			case BORDER_BOX:
				return boxSizing;
			case INHERIT:
				return this.getPreviousRenderState().getBoxSizing();
			case INITIAL:
			default:
				return CONTENT_BOX;
			}
		}
		return null;
	}
	
	@Override
	public String getlineHeight() {
		AbstractCSSProperties props = this.getCssProperties();
		RenderState prs = this.prevRenderState;
		String token = props == null ? null : props.getLineHeight();
		
		
		if (this.iLineHeight != null) {
			return this.iLineHeight;
		}
		
		if (token == null) {
			if (prs != null) {
				this.iLineHeight = prs.getlineHeight();
				return this.iLineHeight;
			}
			this.iLineHeight = DEFAULT_LINE_HEIGHT;
			return DEFAULT_LINE_HEIGHT;
		}
		
		switch (token) {
		case INHERIT:
			return this.getPreviousRenderState().getlineHeight();
		case NORMAL:
			return String.valueOf(1.2f * new LAFSettings().getIstance().getFontSize()); 
		case INITIAL:
			return String.valueOf(prs.getFontMetrics().getHeight());
		default:
			if (token.endsWith("px") || token.endsWith("pt") || token.endsWith("pc") || token.endsWith("em")
					|| token.endsWith("mm") || token.endsWith("ex") || token.endsWith("em")) {
				return String.valueOf(HtmlValues.getPixelSize(token, this, 1));
			} else {
				Font f = getFont();
				return String.valueOf(HtmlValues.getPixelSize(token, this, 1) * f.getSize());
			}
		}
	}
}
