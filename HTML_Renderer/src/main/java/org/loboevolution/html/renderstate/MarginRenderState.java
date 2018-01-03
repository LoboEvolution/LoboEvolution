package org.loboevolution.html.renderstate;

import org.loboevolution.html.style.CSS3Properties;
import org.loboevolution.html.style.HtmlInsets;

public class MarginRenderState {
	
	/** The Constant INVALID_INSETS. */
	protected static final HtmlInsets INVALID_INSETS = new HtmlInsets();
		
	/**
	 * Gets the margin insets.
	 *
	 * @param cssProperties
	 *            the css properties
	 * @param renderState
	 *            the render state
	 * @return the margin insets
	 */
	public static HtmlInsets getMarginInsets(CSS3Properties cssProperties, RenderState renderState) {
		HtmlInsets insets = null;
		String marginText = cssProperties.getMargin();
		if (marginText != null) {
			String[] mg = marginText.split(" ");
			int sizeMargin = mg.length;
			switch (sizeMargin) {
			case 4:
				insets = HtmlInsets.updateTopInset(insets, mg[0], renderState); 
				insets = HtmlInsets.updateRightInset(insets, mg[1], renderState);
				insets = HtmlInsets.updateBottomInset(insets, mg[2], renderState);
				insets = HtmlInsets.updateLeftInset(insets, mg[3], renderState);
				break;
			case 3:
				insets = HtmlInsets.updateTopInset(insets, mg[0], renderState);
				insets = HtmlInsets.updateRightInset(insets, mg[1], renderState);
				insets = HtmlInsets.updateBottomInset(insets, mg[2], renderState);
				break;
			case 2:
				insets = HtmlInsets.updateTopInset(insets, mg[0], renderState);
				insets = HtmlInsets.updateRightInset(insets, mg[1], renderState);
				break;
			case 1:
				insets = HtmlInsets.updateTopInset(insets, mg[0], renderState);
				insets = HtmlInsets.updateRightInset(insets, mg[0], renderState);
				insets = HtmlInsets.updateBottomInset(insets, mg[0], renderState);
				insets = HtmlInsets.updateLeftInset(insets, mg[0], renderState);
				break;
			default:
				break;
			}
		} else {

			String topText = cssProperties.getMarginTop();
			insets = HtmlInsets.updateTopInset(insets, topText, renderState);
			String leftText = cssProperties.getMarginLeft();
			insets = HtmlInsets.updateLeftInset(insets, leftText, renderState);
			String bottomText = cssProperties.getMarginBottom();
			insets = HtmlInsets.updateBottomInset(insets, bottomText, renderState);
			String rightText = cssProperties.getMarginRight();
			insets = HtmlInsets.updateRightInset(insets, rightText, renderState);
		}
		return insets;
	}

}
