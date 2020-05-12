package org.loboevolution.html.dom.input;

import java.awt.Dimension;

import javax.swing.JSlider;
import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.style.HtmlValues;

/**
 * <p>InputRange class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class InputRange {

	/**
	 * <p>Constructor for InputRange.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputRange(HTMLInputElementImpl modelNode, InputControl ic) {
		final int min = HtmlValues.getPixelSize(modelNode.getAttribute("min"), null, 0);
		final int max = HtmlValues.getPixelSize(modelNode.getAttribute("max"), null, 0);
		final int value = HtmlValues.getPixelSize(modelNode.getAttribute("value"), null, 0);

		JSlider rangeSlider = new JSlider();
		rangeSlider.setPreferredSize(new Dimension(240, rangeSlider.getPreferredSize().height));
		rangeSlider.setMinimum(min);
		rangeSlider.setMaximum(max);
		rangeSlider.setValue(value);
		ic.add(rangeSlider);
	}
}
