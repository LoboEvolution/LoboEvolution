/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
/*
 * Created on Apr 16, 2005
 */
package org.lobobrowser.html.renderstate;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.font.TextAttribute;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.info.BackgroundInfo;
import org.lobobrowser.html.info.BorderInfo;
import org.lobobrowser.html.info.WordInfo;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.HtmlInsets;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.html.style.RenderThreadState;
import org.lobobrowser.util.gui.ColorFactory;
import org.lobobrowser.util.gui.FontFactory;
import org.lobobrowser.util.gui.LAFSettings;
import org.lobobrowser.w3c.html.HTMLElement;
import org.w3c.dom.css.CSS2Properties;

/**
 * The Class StyleSheetRenderState.
 *
 * @author J. H. S.
 */
public class StyleSheetRenderState implements RenderState {
	
	 /** The Constant logger. */
    protected static final Logger logger = LogManager
            .getLogger(StyleSheetRenderState.class.getName());

	/** The Constant FONT_FACTORY. */
	private static final FontFactory FONT_FACTORY = FontFactory.getInstance();

	/** The Constant DEFAULT_FONT. */
	private static final Font DEFAULT_FONT = FONT_FACTORY.getFont(LAFSettings.getInstance().getFont(), null, null, null,
			LAFSettings.getInstance().getFontSize(), null, null);

	/** The Constant INVALID_INSETS. */
	protected static final HtmlInsets INVALID_INSETS = new HtmlInsets();

	/** The Constant INVALID_BACKGROUND_INFO. */
	protected static final BackgroundInfo INVALID_BACKGROUND_INFO = new BackgroundInfo();

	/** The Constant INVALID_BORDER_INFO. */
	protected static final BorderInfo INVALID_BORDER_INFO = new BorderInfo();

	/** The Constant INVALID_COLOR. */
	protected static final Color INVALID_COLOR = new Color(100, 0, 100);

	/** The element. */
	protected final HTMLElementImpl element;

	/** The document. */
	protected final HTMLDocumentImpl document;

	/** The prev render state. */
	protected final RenderState prevRenderState;

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

	/** The i text decoration. */
	private int iTextDecoration = -1;

	/** The i text transform. */
	private int iTextTransform = -1;

	/** The i blank width. */
	private int iBlankWidth = -1;

	/** The i highlight. */
	private boolean iHighlight;

	/** The cursor. */
	private Optional<Cursor> cursor;

	/** The i background info. */
	protected BackgroundInfo iBackgroundInfo = INVALID_BACKGROUND_INFO;

	static {
	}

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

	// public TextRenderState(RenderState prevRenderState) {
	// this.css2properties = new CSS2PropertiesImpl(this);
	// this.prevRenderState = prevRenderState;
	// }

	/**
	 * Gets the default display.
	 *
	 * @return the default display
	 */
	protected int getDefaultDisplay() {
		return DISPLAY_INLINE;
	}

	/** The i display. */
	private Integer iDisplay;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getDisplay()
	 */
	@Override
	public int getDisplay() {
		Integer d = this.iDisplay;
		if (d != null) {
			return d.intValue();
		}
		CSS2Properties props = this.getCssProperties();
		String displayText = props == null ? null : props.getDisplay();
		int displayInt;
		if (displayText != null) {
			String displayTextTL = displayText.toLowerCase();
			if ("block".equals(displayTextTL)) {
				displayInt = DISPLAY_BLOCK;
			} else if ("inline".equals(displayTextTL)) {
				displayInt = DISPLAY_INLINE;
			} else if ("none".equals(displayTextTL)) {
				displayInt = DISPLAY_NONE;
			} else if ("list-item".equals(displayTextTL)) {
				displayInt = DISPLAY_LIST_ITEM;
			} else if ("table".equals(displayTextTL)) {
				displayInt = DISPLAY_TABLE;
			} else if ("table-cell".equals(displayTextTL)) {
				displayInt = DISPLAY_TABLE_CELL;
			} else if ("table-row".equals(displayTextTL)) {
				displayInt = DISPLAY_TABLE_ROW;
			} else if ("inline-block".equals(displayTextTL)) {
				displayInt = DISPLAY_INLINE_BLOCK;
			} else {
				displayInt = this.getDefaultDisplay();
			}
		} else {
			displayInt = this.getDefaultDisplay();
		}
		d = new Integer(displayInt);
		this.iDisplay = d;
		return displayInt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderstate.RenderState#getPreviousRenderState()
	 */
	@Override
	public RenderState getPreviousRenderState() {
		return this.prevRenderState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getFontBase()
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
	protected final AbstractCSS2Properties getCssProperties() {
		HTMLElementImpl element = this.element;
		return element == null ? null : element.getCurrentStyle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#invalidate()
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
		this.marginInsets = INVALID_INSETS;
		this.paddingInsets = INVALID_INSETS;
		this.overflowX = -1;
		this.overflowY = -1;
		this.borderInfo = INVALID_BORDER_INFO;
		// Should NOT invalidate parent render state.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getFont()
	 */
	@Override
	public Font getFont() {
		Font f = this.iFont;
		if (f != null) {
			return f;
		}
		AbstractCSS2Properties style = this.getCssProperties();
		RenderState prs = this.prevRenderState;
		if (style == null) {
			if (prs != null) {
				f = prs.getFont();
				this.iFont = f;
				return f;
			}
			f = DEFAULT_FONT;
			this.iFont = f;
			return f;
		}
		Float fontSize = null;
		String fontStyle = null;
		String fontVariant = null;
		String fontWeight = null;
		String fontFamily = null;

		String newFontSize = style == null ? null : style.getFontSize();
		String newFontFamily = style == null ? null : style.getFontFamily();
		String newFontStyle = style == null ? null : style.getFontStyle();
		String newFontVariant = style == null ? null : style.getFontVariant();
		String newFontWeight = style == null ? null : style.getFontWeight();
		String verticalAlign = style == null ? null : style.getVerticalAlign();
		boolean isSuper = (verticalAlign != null) && verticalAlign.equalsIgnoreCase("super");
		boolean isSub = (verticalAlign != null) && verticalAlign.equalsIgnoreCase("sub");
		if ((newFontSize == null) && (newFontWeight == null) && (newFontStyle == null) && (newFontFamily == null)
				&& (newFontVariant == null)) {
			if (!isSuper && !isSub) {
				if (prs != null) {
					f = prs.getFont();
					this.iFont = f;
					return f;
				}
				f = DEFAULT_FONT;
				this.iFont = f;
				return f;
			}
		}
		if (newFontSize != null) {
			try {
				fontSize = new Float(HtmlValues.getFontSize(newFontSize, prs));
			} catch (Exception err) {
				fontSize = LAFSettings.getInstance().getFontSize();
			}
		} else if (fontSize == null) {
			if (prs != null) {
				fontSize = new Float(prs.getFont().getSize());
			} else {
				fontSize = LAFSettings.getInstance().getFontSize();
			}
		}
		if (newFontFamily != null) {
			fontFamily = newFontFamily;
		} else if ((fontFamily == null) && (prs != null)) {
			fontFamily = prs.getFont().getFamily();
		}
		if (fontFamily == null) {
			fontFamily = Font.SANS_SERIF;
		}
		if (newFontStyle != null) {
			fontStyle = newFontStyle;
		} else if ((fontStyle == null) && (prs != null)) {
			int fstyle = prs.getFont().getStyle();
			if ((fstyle & Font.ITALIC) != 0) {
				fontStyle = "italic";
			}
		}
		if (newFontVariant != null) {
			fontVariant = newFontVariant;
		} else if (prs != null) {
			// TODO: smallcaps?
		}
		if (newFontWeight != null) {
			fontWeight = newFontWeight;
		} else if ((fontWeight == null) && (prs != null)) {
			int fstyle = prs.getFont().getStyle();
			if ((fstyle & Font.BOLD) != 0) {
				fontWeight = "bold";
			}
		}
		HTMLDocumentImpl document = this.document;
		Set locales = document == null ? null : document.getLocales();

		Integer superscript = null;
		if (isSuper) {
			superscript = new Integer(1);
		} else if (isSub) {
			superscript = new Integer(-1);
		}
		if ((superscript == null) && (prs != null)) {
			superscript = (Integer) prs.getFont().getAttributes().get(TextAttribute.SUPERSCRIPT);
		}
		f = FONT_FACTORY.getFont(fontFamily, fontStyle, fontVariant, fontWeight, fontSize.floatValue(), locales,
				superscript);
		this.iFont = f;
		return f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getColor()
	 */
	@Override
	public Color getColor() {
		Color c = this.iColor;
		if (c != null) {
			return c;
		}
		AbstractCSS2Properties props = this.getCssProperties();
		String colorValue = props == null ? null : props.getColor();
		if ((colorValue == null) || "".equals(colorValue)) {
			RenderState prs = this.prevRenderState;
			if (prs != null) {
				c = prs.getColor();
				this.iColor = c;
				return c;
			} else {
				colorValue = "black";
			}
		}
		c = ColorFactory.getInstance().getColor(colorValue);
		this.iColor = c;
		return c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getBackgroundColor()
	 */
	@Override
	public Color getBackgroundColor() {
		Color c = this.iBackgroundColor;
		if (c != INVALID_COLOR) {
			return c;
		}
		Color localColor;
		BackgroundInfo binfo = this.getBackgroundInfo();
		localColor = binfo == null ? null : binfo.getBackgroundColor();
		if ((localColor == null) && (this.getDisplay() == DISPLAY_INLINE)) {
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
	 * org.lobobrowser.html.renderstate.RenderState#getTextBackgroundColor()
	 */
	@Override
	public Color getTextBackgroundColor() {
		Color c = this.iTextBackgroundColor;
		if (c != INVALID_COLOR) {
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
	 * @see org.lobobrowser.html.renderstate.RenderState#getOverlayColor()
	 */
	@Override
	public Color getOverlayColor() {
		Color c = this.iOverlayColor;
		if (c != INVALID_COLOR) {
			return c;
		}
		AbstractCSS2Properties props = this.getCssProperties();
		String colorValue = props == null ? null : props.getOverlayColor();
		if ((colorValue == null) || (colorValue.length() == 0)) {
			RenderState prs = this.prevRenderState;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getTextDecorationMask()
	 */
	@Override
	public int getTextDecorationMask() {
		int td = this.iTextDecoration;
		if (td != -1) {
			return td;
		}
		AbstractCSS2Properties props = this.getCssProperties();
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
				if ("none".equals(token)) {
					// continue
				} else if ("underline".equals(token)) {
					td |= RenderState.MASK_TEXTDECORATION_UNDERLINE;
				} else if ("line-through".equals(token)) {
					td |= RenderState.MASK_TEXTDECORATION_LINE_THROUGH;
				} else if ("blink".equals(token)) {
					td |= RenderState.MASK_TEXTDECORATION_BLINK;
				} else if ("overline".equals(token)) {
					td |= RenderState.MASK_TEXTDECORATION_OVERLINE;
				}
			}
		}
		this.iTextDecoration = td;
		return td;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getTextTransform()
	 */
	@Override
	public int getTextTransform() {
		int tt = this.iTextTransform;
		if (tt != -1) {
			return tt;
		}
		AbstractCSS2Properties props = this.getCssProperties();
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
			if ("none".equals(tdText)) {
				// continue
			} else if ("capitalize".equals(tdText)) {
				tt = TEXTTRANSFORM_CAPITALIZE;
			} else if ("uppercase".equals(tdText)) {
				tt = TEXTTRANSFORM_UPPERCASE;
			} else if ("lowercase".equals(tdText)) {
				tt = TEXTTRANSFORM_LOWERCASE;
			}
			// TODO how the explicit "inherit" value is to be handled?
			// Who is responsible for CSS cascading?
			// ... painting code? prevRenderState?
			//
			// else if("inherit".equals(tdText)) {
			// tt = TEXTTRANSFORM_INHERIT;
			// }
		}
		this.iTextTransform = tt;
		return tt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getFontMetrics()
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
	 * @see org.lobobrowser.html.renderstate.RenderState#getBlankWidth()
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

	/** The i word info map. */
	Map<String, WordInfo> iWordInfoMap = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getWordInfo(java.lang.
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

	/** The align x percent. */
	private int alignXPercent = -1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getAlignXPercent()
	 */
	@Override
	public int getAlignXPercent() {
		int axp = this.alignXPercent;
		if (axp != -1) {
			return axp;
		}
		CSS2Properties props = this.getCssProperties();
		String textAlign = props == null ? null : props.getTextAlign();
		if ((textAlign == null) || (textAlign.length() == 0)) {
			// Fall back to align attribute.
			HTMLElement element = this.element;
			if (element != null) {
				textAlign = element.getAttribute(HtmlAttributeProperties.ALIGN);
				if ((textAlign == null) || (textAlign.length() == 0)) {
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
		} else if ("center".equalsIgnoreCase(textAlign)) {
			axp = 50;
		} else if ("right".equalsIgnoreCase(textAlign)) {
			axp = 100;
		} else {
			// TODO: justify, <string>
			axp = 0;
		}
		this.alignXPercent = axp;
		return axp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getAlignYPercent()
	 */
	@Override
	public int getAlignYPercent() {
		// This is only settable in table cells.
		// TODO: Does it work with display: table-cell?
		return 0;
	}

	/** The counters. */
	private Map<String, ArrayList<Object>> counters = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderstate.RenderState#getCount(java.lang.String,
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
		if ((nesting < 0) || (nesting >= counterArray.size())) {
			return 0;
		}
		Integer integer = (Integer) counterArray.get(nesting);
		return integer == null ? 0 : integer.intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderstate.RenderState#resetCount(java.lang.String,
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
			counterArray.set(nesting, new Integer(value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderstate.RenderState#incrementCount(java.lang.
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
		counterArray.set(nesting, new Integer(prevValue + 1));
		return prevValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getBackgroundInfo()
	 */
	@Override
	public BackgroundInfo getBackgroundInfo() {
		BackgroundInfo binfo = this.iBackgroundInfo;
		if (binfo != INVALID_BACKGROUND_INFO) {
			return binfo;
		}
		
		if(element != null && !"BODY".equals(element.getNodeName())){
			binfo = null;
		}
		
		AbstractCSS2Properties props = this.getCssProperties();
		if (props != null) {
			String backgroundColorText = props.getBackgroundColor();
			if (backgroundColorText != null) {
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				binfo.setBackgroundColor(ColorFactory.getInstance().getColor(backgroundColorText));
			}
			String backgroundImageText = props.getBackgroundImage();
			if ((backgroundImageText != null) && (backgroundImageText.length() > 0)) {
				URL backgroundImage = HtmlValues.getURIFromStyleValue(backgroundImageText);
				if (backgroundImage != null) {
					if (binfo == null) {
						binfo = new BackgroundInfo();
					}
					binfo.setBackgroundImage(backgroundImage);
				}
			}
			String backgroundRepeatText = props.getBackgroundRepeat();
			if (backgroundRepeatText != null) {
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				this.applyBackgroundRepeat(binfo, backgroundRepeatText);
			}
			String backgroundPositionText = props.getBackgroundPosition();
			if (backgroundPositionText != null) {
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				this.applyBackgroundPosition(binfo, backgroundPositionText);
			}
		}
		this.iBackgroundInfo = binfo;
		return binfo;
	}

	/** The i text indent text. */
	private String iTextIndentText = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getTextIndentText()
	 */
	@Override
	public String getTextIndentText() {
		String tiText = this.iTextIndentText;
		if (tiText != null) {
			return tiText;
		}
		AbstractCSS2Properties props = this.getCssProperties();
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
	 * @see org.lobobrowser.html.renderstate.RenderState#getTextIndent(int)
	 */
	@Override
	public int getTextIndent(int availSize) {
		// No caching for this one.
		String tiText = this.getTextIndentText();
		if (tiText.length() == 0) {
			return 0;
		} else {
			return HtmlValues.getPixelSize(tiText, this, 0, availSize);
		}
	}

	/** The i white space. */
	protected Integer iWhiteSpace;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getWhiteSpace()
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
		AbstractCSS2Properties props = this.getCssProperties();
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
			if ("nowrap".equals(whiteSpaceTextTL)) {
				wsValue = WS_NOWRAP;
			} else if ("pre".equals(whiteSpaceTextTL)) {
				wsValue = WS_PRE;
			} else {
				wsValue = WS_NORMAL;
			}
		}
		this.iWhiteSpace = new Integer(wsValue);
		return wsValue;
	}

	/** The margin insets. */
	protected HtmlInsets marginInsets = INVALID_INSETS;

	/** The padding insets. */
	protected HtmlInsets paddingInsets = INVALID_INSETS;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getMarginInsets()
	 */
	@Override
	public HtmlInsets getMarginInsets() {
		HtmlInsets mi = this.marginInsets;
		if (mi != INVALID_INSETS) {
			return mi;
		}
		AbstractCSS2Properties props = this.getCssProperties();
		if (props == null) {
			mi = null;
		} else {
			mi = HtmlValues.getMarginInsets(props, this);
		}
		this.marginInsets = mi;
		return mi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getPaddingInsets()
	 */
	@Override
	public HtmlInsets getPaddingInsets() {
		HtmlInsets mi = this.paddingInsets;
		if (mi != INVALID_INSETS) {
			return mi;
		}
		AbstractCSS2Properties props = this.getCssProperties();
		if (props == null) {
			mi = null;
		} else {
			mi = HtmlValues.getPaddingInsets(props, this);
			this.paddingInsets = mi;
		}
		return mi;
	}

	/**
	 * Apply background horizontal positon.
	 *
	 * @param binfo
	 *            the binfo
	 * @param xposition
	 *            the xposition
	 */
	private void applyBackgroundHorizontalPositon(BackgroundInfo binfo, String xposition) {
		if (xposition.endsWith("%")) {
			binfo.setBackgroundXPositionAbsolute(false);
			try {
				binfo.setBackgroundXPosition(
						(int) Double.parseDouble(xposition.substring(0, xposition.length() - 1).trim()));
			} catch (NumberFormatException nfe) {
				binfo.setBackgroundXPosition(0);
			}
		} else if ("center".equalsIgnoreCase(xposition)) {
			binfo.setBackgroundXPositionAbsolute(false);
			binfo.setBackgroundXPosition(50);
		} else if ("right".equalsIgnoreCase(xposition)) {
			binfo.setBackgroundXPositionAbsolute(false);
			binfo.setBackgroundXPosition(100);
		} else if ("left".equalsIgnoreCase(xposition)) {
			binfo.setBackgroundXPositionAbsolute(false);
			binfo.setBackgroundXPosition(0);
		} else if ("bottom".equalsIgnoreCase(xposition)) {
			// Can happen
			binfo.setBackgroundYPositionAbsolute(false);
			binfo.setBackgroundYPosition(100);
		} else if ("top".equalsIgnoreCase(xposition)) {
			// Can happen
			binfo.setBackgroundYPositionAbsolute(false);
			binfo.setBackgroundYPosition(0);
		} else {
			binfo.setBackgroundXPositionAbsolute(true);
			binfo.setBackgroundXPosition(HtmlValues.getPixelSize(xposition, this, 0));
		}
	}

	/**
	 * Apply background vertical position.
	 *
	 * @param binfo
	 *            the binfo
	 * @param yposition
	 *            the yposition
	 */
	private void applyBackgroundVerticalPosition(BackgroundInfo binfo, String yposition) {
		if (yposition.endsWith("%")) {
			binfo.setBackgroundYPositionAbsolute(false);
			try {
				binfo.setBackgroundYPosition(
						(int) Double.parseDouble(yposition.substring(0, yposition.length() - 1).trim()));
			} catch (NumberFormatException nfe) {
				binfo.setBackgroundYPosition(0);
			}
		} else if ("center".equalsIgnoreCase(yposition)) {
			binfo.setBackgroundYPositionAbsolute(false);
			binfo.setBackgroundYPosition(50);
		} else if ("bottom".equalsIgnoreCase(yposition)) {
			binfo.setBackgroundYPositionAbsolute(false);
			binfo.setBackgroundYPosition(100);
		} else if ("top".equalsIgnoreCase(yposition)) {
			binfo.setBackgroundYPositionAbsolute(false);
			binfo.setBackgroundYPosition(0);
		} else if ("right".equalsIgnoreCase(yposition)) {
			// Can happen
			binfo.setBackgroundXPositionAbsolute(false);
			binfo.setBackgroundXPosition(100);
		} else if ("left".equalsIgnoreCase(yposition)) {
			// Can happen
			binfo.setBackgroundXPositionAbsolute(false);
			binfo.setBackgroundXPosition(0);
		} else {
			binfo.setBackgroundYPositionAbsolute(true);
			binfo.setBackgroundYPosition(HtmlValues.getPixelSize(yposition, this, 0));
		}
	}

	/**
	 * Apply background position.
	 *
	 * @param binfo
	 *            the binfo
	 * @param position
	 *            the position
	 */
	private void applyBackgroundPosition(BackgroundInfo binfo, String position) {
		binfo.setBackgroundXPositionAbsolute(false);
		binfo.setBackgroundYPositionAbsolute(false);
		binfo.setBackgroundXPosition(50);
		binfo.setBackgroundYPosition(50);
		StringTokenizer tok = new StringTokenizer(position, " \t\r\n");
		if (tok.hasMoreTokens()) {
			String xposition = tok.nextToken();
			this.applyBackgroundHorizontalPositon(binfo, xposition);
			if (tok.hasMoreTokens()) {
				String yposition = tok.nextToken();
				this.applyBackgroundVerticalPosition(binfo, yposition);
			}
		}
	}

	/**
	 * Apply background repeat.
	 *
	 * @param binfo
	 *            the binfo
	 * @param backgroundRepeatText
	 *            the background repeat text
	 */
	private void applyBackgroundRepeat(BackgroundInfo binfo, String backgroundRepeatText) {
		String brtl = backgroundRepeatText.toLowerCase();
		if ("repeat".equals(brtl)) {
			binfo.backgroundRepeat = BackgroundInfo.BR_REPEAT;
		} else if ("repeat-x".equals(brtl)) {
			binfo.backgroundRepeat = BackgroundInfo.BR_REPEAT_X;
		} else if ("repeat-y".equals(brtl)) {
			binfo.backgroundRepeat = BackgroundInfo.BR_REPEAT_Y;
		} else if ("no-repeat".equals(brtl)) {
			binfo.backgroundRepeat = BackgroundInfo.BR_NO_REPEAT;
		}
	}

	/** The cached visibility. */
	private Integer cachedVisibility;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getVisibility()
	 */
	@Override
	public int getVisibility() {
		Integer v = this.cachedVisibility;
		if (v != null) {
			return v.intValue();
		}
		AbstractCSS2Properties props = this.getCssProperties();
		int visibility;
		if (props == null) {
			visibility = VISIBILITY_VISIBLE;
		} else {
			String visibText = props.getVisibility();
			if ((visibText == null) || (visibText.length() == 0)) {
				visibility = VISIBILITY_VISIBLE;
			} else {
				String visibTextTL = visibText.toLowerCase();
				if (visibTextTL.equals("hidden")) {
					visibility = VISIBILITY_HIDDEN;
				} else if (visibTextTL.equals("visible")) {
					visibility = VISIBILITY_VISIBLE;
				} else if (visibTextTL.equals("collapse")) {
					visibility = VISIBILITY_COLLAPSE;
				} else {
					visibility = VISIBILITY_VISIBLE;
				}
			}
		}
		this.cachedVisibility = new Integer(visibility);
		return visibility;
	}

	/** The cached position. */
	private Integer cachedPosition;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getPosition()
	 */
	@Override
	public int getPosition() {
		Integer p = this.cachedPosition;
		if (p != null) {
			return p.intValue();
		}
		AbstractCSS2Properties props = this.getCssProperties();
		int position;
		if (props == null) {
			position = POSITION_STATIC;
		} else {
			String positionText = props.getPosition();
			if ((positionText == null) || (positionText.length() == 0)) {
				position = POSITION_STATIC;
			} else {
				String positionTextTL = positionText.toLowerCase();
				if (positionTextTL.equals("absolute")) {
					position = POSITION_ABSOLUTE;
				} else if (positionTextTL.equals("static")) {
					position = POSITION_STATIC;
				} else if (positionTextTL.equals("relative")) {
					position = POSITION_RELATIVE;
				} else if (positionTextTL.equals("fixed")) {
					position = POSITION_FIXED;
				} else {
					position = POSITION_STATIC;
				}
			}
		}
		this.cachedPosition = new Integer(position);
		return position;
	}

	/** The cached float. */
	private Integer cachedFloat;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getFloat()
	 */
	@Override
	public int getFloat() {
		Integer p = this.cachedFloat;
		if (p != null) {
			return p.intValue();
		}
		AbstractCSS2Properties props = this.getCssProperties();
		int floatValue;
		if (props == null) {
			floatValue = FLOAT_NONE;
		} else {
			String floatText = props.getFloat();
			if ((floatText == null) || (floatText.length() == 0)) {
				floatValue = FLOAT_NONE;
			} else {
				String floatTextTL = floatText.toLowerCase();
				if (floatTextTL.equals("left")) {
					floatValue = FLOAT_LEFT;
				} else if (floatTextTL.equals("right")) {
					floatValue = FLOAT_RIGHT;
				} else {
					floatValue = FLOAT_NONE;
				}
			}
		}
		this.cachedFloat = new Integer(floatValue);
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

	/** The overflow x. */
	protected int overflowX = -1;

	/** The overflow y. */
	protected int overflowY = -1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getOverflowX()
	 */
	@Override
	public int getOverflowX() {
		int overflow = this.overflowX;
		if (overflow != -1) {
			return overflow;
		}
		AbstractCSS2Properties props = this.getCssProperties();
		if (props == null) {
			overflow = OVERFLOW_NONE;
		} else {
			String overflowText = props.getPropertyValue("overflow-x");
			if (overflowText == null) {
				overflowText = props.getOverflow();
			}
			if (overflowText == null) {
				overflow = OVERFLOW_NONE;
			} else {
				String overflowTextTL = overflowText.toLowerCase();
				if ("scroll".equals(overflowTextTL)) {
					overflow = OVERFLOW_SCROLL;
				} else if ("auto".equals(overflowTextTL)) {
					overflow = OVERFLOW_AUTO;
				} else if ("hidden".equals(overflowTextTL)) {
					overflow = OVERFLOW_HIDDEN;
				} else if ("visible".equals(overflowTextTL)) {
					overflow = OVERFLOW_VISIBLE;
				} else {
					overflow = OVERFLOW_NONE;
				}
			}
		}
		this.overflowX = overflow;
		return overflow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getOverflowY()
	 */
	@Override
	public int getOverflowY() {
		int overflow = this.overflowY;
		if (overflow != -1) {
			return overflow;
		}
		AbstractCSS2Properties props = this.getCssProperties();
		if (props == null) {
			overflow = OVERFLOW_NONE;
		} else {
			String overflowText = props.getPropertyValue("overflow-y");
			if (overflowText == null) {
				overflowText = props.getOverflow();
			}
			if (overflowText == null) {
				overflow = OVERFLOW_NONE;
			} else {
				String overflowTextTL = overflowText.toLowerCase();
				if ("scroll".equals(overflowTextTL)) {
					overflow = OVERFLOW_SCROLL;
				} else if ("auto".equals(overflowTextTL)) {
					overflow = OVERFLOW_AUTO;
				} else if ("hidden".equals(overflowTextTL)) {
					overflow = OVERFLOW_HIDDEN;
				} else if ("visible".equals(overflowTextTL)) {
					overflow = OVERFLOW_VISIBLE;
				} else {
					overflow = OVERFLOW_NONE;
				}
			}
		}
		this.overflowY = overflow;
		return overflow;
	}

	/** The border info. */
	protected BorderInfo borderInfo = INVALID_BORDER_INFO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderstate.RenderState#getBorderInfo()
	 */
	@Override
	public BorderInfo getBorderInfo() {
		BorderInfo binfo = this.borderInfo;
		if (binfo != INVALID_BORDER_INFO) {
			return binfo;
		}
		AbstractCSS2Properties props = this.getCssProperties();
		if (props != null) {
			binfo = HtmlValues.getBorderInfo(props, this);
		} else {
			binfo = null;
		}
		this.borderInfo = binfo;
		return binfo;
	}

	@Override
	public void setCursor(Optional<Cursor> cursor) {
		this.cursor = cursor;

	}

	@Override
	public Optional<Cursor> getCursor() {

		Optional<Cursor> prevCursorOpt = Optional.empty();
		AbstractCSS2Properties props = this.getCssProperties();

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
				if ("default".equals(cursorTL)) {
					return Optional.of(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				} else if ("pointer".equals(cursorTL)) {
					return Optional.of(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else if ("crosshair".equals(cursorTL)) {
					return Optional.of(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				} else if ("move".equals(cursorTL)) {
					return Optional.of(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				} else if ("text".equals(cursorTL)) {
					return Optional.of(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
				} else if ("wait".equals(cursorTL)) {
					return Optional.of(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				} else {
					return prevCursorOpt;
				}
			}
		}
	}
}
