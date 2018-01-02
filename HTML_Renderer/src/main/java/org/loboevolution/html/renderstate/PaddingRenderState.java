package org.loboevolution.html.renderstate;

import org.loboevolution.html.style.HtmlInsets;
import org.w3c.dom.css.CSS2Properties;

public class PaddingRenderState {

	/** The Constant INVALID_INSETS. */
	protected static final HtmlInsets INVALID_INSETS = new HtmlInsets();

	/** The padding insets. */
	protected static HtmlInsets paddingInsets = INVALID_INSETS;

	/**
	 * Gets the padding insets.
	 *
	 * @param cssProperties
	 *            the css properties
	 * @param renderState
	 *            the render state
	 * @return the padding insets
	 */
	public static HtmlInsets getPaddingInsets(CSS2Properties props, RenderState renderState) {

		HtmlInsets insets = paddingInsets;
		if (!INVALID_INSETS.equals(insets)) {
			return insets;
		}

		if (props == null) {
			return null;
		} else {

			String paddingText = props.getPadding();
			if (paddingText != null) {
				String[] pd = paddingText.split(" ");
				int sizePadding = pd.length;
				switch (sizePadding) {
				case 4:
					insets = HtmlInsets.updateTopInset(insets, pd[0], renderState);
					insets = HtmlInsets.updateRightInset(insets, pd[1], renderState);
					insets = HtmlInsets.updateBottomInset(insets, pd[2], renderState);
					insets = HtmlInsets.updateLeftInset(insets, pd[3], renderState);
					break;
				case 3:
					insets = HtmlInsets.updateTopInset(insets, pd[0], renderState);
					insets = HtmlInsets.updateRightInset(insets, pd[1], renderState);
					insets = HtmlInsets.updateBottomInset(insets, pd[2], renderState);
					break;
				case 2:
					insets = HtmlInsets.updateTopInset(insets, pd[0], renderState);
					insets = HtmlInsets.updateRightInset(insets, pd[1], renderState);
					break;
				case 1:
					insets = HtmlInsets.updateTopInset(insets, pd[0], renderState);
					insets = HtmlInsets.updateRightInset(insets, pd[0], renderState);
					insets = HtmlInsets.updateBottomInset(insets, pd[0], renderState);
					insets = HtmlInsets.updateLeftInset(insets, pd[0], renderState);
					break;
				default:
					break;
				}
			} else {
				String topText = props.getPaddingTop();
				insets = HtmlInsets.updateTopInset(insets, topText, renderState);
				String leftText = props.getPaddingLeft();
				insets = HtmlInsets.updateLeftInset(insets, leftText, renderState);
				String bottomText = props.getPaddingBottom();
				insets = HtmlInsets.updateBottomInset(insets, bottomText, renderState);
				String rightText = props.getPaddingRight();
				insets = HtmlInsets.updateRightInset(insets, rightText, renderState);
			}
			paddingInsets = insets;
			return insets;
		}
	}
}
