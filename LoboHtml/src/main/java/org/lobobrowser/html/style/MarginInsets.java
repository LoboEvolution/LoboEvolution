package org.lobobrowser.html.style;

import org.lobobrowser.html.renderstate.RenderState;
import org.w3c.dom.css.CSS3Properties;

public class MarginInsets {
	
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
