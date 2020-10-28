package org.loboevolution.html.renderstate;

import java.awt.Color;
import java.util.Base64;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLTableElement;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLTableCellElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLTableRowElementImpl;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.info.BackgroundInfo;
import org.loboevolution.laf.ColorFactory;
import org.w3c.dom.css.CSS3Properties;

/**
 * <p>TableCellRenderState class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class TableCellRenderState extends DisplayRenderState {
	private int alignXPercent = -1;

	private int alignYPercent = -1;
	private BackgroundInfo backgroundInfo = INVALID_BACKGROUND_INFO;
	private HtmlInsets paddingInsets = INVALID_INSETS;

	/**
	 * <p>Constructor for TableCellRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public TableCellRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		super(prevRenderState, element, RenderState.DISPLAY_TABLE_CELL);
	}

	/** {@inheritDoc} */
	@Override
	public int getAlignXPercent() {
		int axp = this.alignXPercent;
		if (axp != -1) {
			return axp;
		}
		final CSS3Properties props = getCssProperties();
		if (props != null) {
			final String textAlign = props.getTextAlign();
			if (textAlign != null && textAlign.length() != 0) {
				return super.getAlignXPercent();
			}
		}
		// Parent already knows about "align" attribute, but override because of TH.
		String align = this.element.getAttribute("align");
		final HTMLElement element = this.element;
		HTMLElement rowElement = null;
		final Object parent = element.getParentNode();
		if (parent instanceof HTMLElement) {
			rowElement = (HTMLElement) parent;
		}
		if (align == null || align.length() == 0) {
			if (rowElement != null) {
				align = rowElement.getAttribute("align");
				if (align != null && align.length() == 0) {
					align = null;
				}
			} else {
				align = null;
			}
		}
		if (align == null) {
			if ("TH".equalsIgnoreCase(element.getNodeName())) {
				axp = 50;
			} else {
				axp = 0;
			}
		} else if ("center".equalsIgnoreCase(align) || "middle".equalsIgnoreCase(align)) {
			axp = 50;
		} else if ("left".equalsIgnoreCase(align)) {
			axp = 0;
		} else if ("right".equalsIgnoreCase(align)) {
			axp = 100;
		} else {
			// TODO: justify, etc.
			axp = 0;
		}
		this.alignXPercent = axp;
		return axp;
	}

	/** {@inheritDoc} */
	@Override
	public int getAlignYPercent() {
		int ayp = this.alignYPercent;
		if (ayp != -1) {
			return ayp;
		}
		final CSS3Properties props = getCssProperties();
		if (props != null) {
			final String textAlign = props.getVerticalAlign();
			if (textAlign != null && textAlign.length() != 0) {
				return super.getAlignYPercent();
			}
		}
		String valign = this.element.getAttribute("valign");
		final HTMLElement element = this.element;
		HTMLElement rowElement = null;
		final Object parent = element.getParentNode();
		if (parent instanceof HTMLElement) {
			rowElement = (HTMLElement) parent;
		}
		if (valign == null || valign.length() == 0) {
			if (rowElement != null) {
				valign = rowElement.getAttribute("valign");
				if (valign != null && valign.length() == 0) {
					valign = null;
				}
			} else {
				valign = null;
			}
		}
		if (valign == null) {
			ayp = 50;
		} else if ("top".equalsIgnoreCase(valign)) {
			ayp = 0;
		} else if ("middle".equalsIgnoreCase(valign) || "center".equalsIgnoreCase(valign)) {
			ayp = 50;
		} else if ("bottom".equalsIgnoreCase(valign)) {
			ayp = 100;
		} else {
			// TODO: baseline, etc.
			ayp = 50;
		}
		this.alignYPercent = ayp;
		return ayp;
	}

	/** {@inheritDoc} */
	@Override
	public BackgroundInfo getBackgroundInfo() {
		BackgroundInfo binfo = this.backgroundInfo;
		if (binfo != INVALID_BACKGROUND_INFO) {
			return binfo;
		}
		// Apply style based on deprecated attributes.
		binfo = super.getBackgroundInfo();
		final HTMLTableCellElementImpl element = (HTMLTableCellElementImpl) this.element;
		HTMLTableRowElementImpl rowElement = null;
		final Object parentNode = element.getParentNode();
		if (parentNode instanceof HTMLTableRowElementImpl) {
			rowElement = (HTMLTableRowElementImpl) parentNode;
		}
		if (binfo == null || binfo.getBackgroundColor() == null) {
			String bgColor = element.getBgColor();
			if (Strings.isNotBlank(bgColor)) {
				if (rowElement != null) {
					bgColor = rowElement.getBgColor();
				}
			}
			if (Strings.isNotBlank(bgColor)) {
				final Color bgc = ColorFactory.getInstance().getColor(bgColor);
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				binfo.setBackgroundColor(bgc);
			}
		}
		if (binfo == null || binfo.getBackgroundImage() == null) {
			String background = element.getAttribute("background");
			if (Strings.isNotBlank(background)) {
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				
				if (background.contains(";base64,")) {
                    final String base64 = background.split(";base64,")[1];
                    final byte[] decodedBytes = Base64.getDecoder().decode(Strings.linearize(base64));
                    background = String.valueOf(decodedBytes);
                }
				binfo.setBackgroundImage(this.document.getFullURL(background));
			}
		}
		this.backgroundInfo = binfo;
		return binfo;
	}

	/** {@inheritDoc} */
	@Override
	public HtmlInsets getPaddingInsets() {
		HtmlInsets insets = this.paddingInsets;
		if (insets != INVALID_INSETS) {
			return insets;
		} else {

			final HTMLTableElement tableElement = getTableElement();
			if (tableElement == null) {
				// Return without caching
				return null;
			}
			String cellPaddingText = tableElement.getAttribute("cellpadding");
			if (Strings.isNotBlank(cellPaddingText)) {
				cellPaddingText = cellPaddingText.trim();
				int cellPadding = HtmlValues.getPixelSize(cellPaddingText, this, 0);
				int cellPaddingType = HtmlInsets.TYPE_PIXELS;

				if (cellPaddingText.endsWith("%")) {
					cellPaddingType = HtmlInsets.TYPE_PERCENT;
				}
				insets = new HtmlInsets();
				insets.top = insets.left = insets.right = insets.bottom = cellPadding;
				insets.topType = insets.leftType = insets.rightType = insets.bottomType = cellPaddingType;
			} else {
				insets = super.getPaddingInsets();
			}
		}
		this.paddingInsets = insets;
		return insets;
	}

	private HTMLTableElement getTableElement() {
		org.w3c.dom.Node ancestor = this.element.getParentNode();
		while (ancestor != null && !(ancestor instanceof HTMLTableElement)) {
			ancestor = ancestor.getParentNode();
		}
		return (HTMLTableElement) ancestor;
	}

	/** {@inheritDoc} */
	@Override
	public int getWhiteSpace() {
		// Overrides super.
		if (RenderThreadState.getState().overrideNoWrap) {
			return WS_NOWRAP;
		}
		final Integer ws = this.iWhiteSpace;
		if (ws != null) {
			return ws;
		}
		final AbstractCSSProperties props = getCssProperties();
		final String whiteSpaceText = props == null ? null : props.getWhiteSpace();
		int wsValue;
		if (whiteSpaceText == null) {
			final HTMLElementImpl element = this.element;
			if (element != null && element.getAttributeAsBoolean("nowrap")) {
				wsValue = WS_NOWRAP;
			} else {
				final RenderState prs = this.prevRenderState;
				if (prs != null) {
					wsValue = prs.getWhiteSpace();
				} else {
					wsValue = WS_NORMAL;
				}
			}
		} else {
			final String whiteSpaceTextTL = whiteSpaceText.toLowerCase();
			if ("nowrap".equals(whiteSpaceTextTL)) {
				wsValue = WS_NOWRAP;
			} else if ("pre".equals(whiteSpaceTextTL)) {
				wsValue = WS_PRE;
			} else {
				wsValue = WS_NORMAL;
			}
		}
		if (wsValue == WS_NOWRAP) {
			// In table cells, if the width is defined as an absolute value,
			// nowrap has no effect (IE and FireFox behavior).
			final HTMLElementImpl element = this.element;
			String width = props == null ? null : props.getWidth();
			if (width == null) {
				width = element.getAttribute("width");
				if (width != null && width.length() > 0 && !width.endsWith("%")) {
					wsValue = WS_NORMAL;
				}
			} else {
				if (!width.trim().endsWith("%")) {
					wsValue = WS_NORMAL;
				}
			}
		}
		this.iWhiteSpace = wsValue;
		return wsValue;
	}

	/** {@inheritDoc} */
	@Override
	public void invalidate() {
		super.invalidate();
		this.alignXPercent = -1;
		this.alignYPercent = -1;
		this.backgroundInfo = INVALID_BACKGROUND_INFO;
		this.paddingInsets = INVALID_INSETS;
	}

}
