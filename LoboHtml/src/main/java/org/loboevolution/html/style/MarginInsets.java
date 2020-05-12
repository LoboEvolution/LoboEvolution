package org.loboevolution.html.style;

import org.loboevolution.html.renderstate.RenderState;
import org.w3c.dom.css.CSS3Properties;

/**
 * <p>MarginInsets class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class MarginInsets {
	
	/**
	 * <p>getMarginInsets.</p>
	 *
	 * @param cssProperties a {@link org.w3c.dom.css.CSS3Properties} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link org.loboevolution.html.style.HtmlInsets} object.
	 */
	public static HtmlInsets getMarginInsets(CSS3Properties cssProperties, RenderState renderState) {
		HtmlInsets insets = null;
		final String topText = cssProperties.getMarginTop();
		insets = HtmlInsets.updateTopInset(insets, topText, renderState);
		final String leftText = cssProperties.getMarginLeft();
		insets = HtmlInsets.updateLeftInset(insets, leftText, renderState);
		final String bottomText = cssProperties.getMarginBottom();
		insets = HtmlInsets.updateBottomInset(insets, bottomText, renderState);
		final String rightText = cssProperties.getMarginRight();
		insets = HtmlInsets.updateRightInset(insets, rightText, renderState);
		return insets;
	}

	/**
	 * <p>getPaddingInsets.</p>
	 *
	 * @param cssProperties a {@link org.w3c.dom.css.CSS3Properties} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link org.loboevolution.html.style.HtmlInsets} object.
	 */
	public static HtmlInsets getPaddingInsets(CSS3Properties cssProperties, RenderState renderState) {
		HtmlInsets insets = null;
		final String topText = cssProperties.getPaddingTop();
		insets = HtmlInsets.updateTopInset(insets, topText, renderState);
		final String leftText = cssProperties.getPaddingLeft();
		insets = HtmlInsets.updateLeftInset(insets, leftText, renderState);
		final String bottomText = cssProperties.getPaddingBottom();
		insets = HtmlInsets.updateBottomInset(insets, bottomText, renderState);
		final String rightText = cssProperties.getPaddingRight();
		insets = HtmlInsets.updateRightInset(insets, rightText, renderState);
		return insets;
	}

}
