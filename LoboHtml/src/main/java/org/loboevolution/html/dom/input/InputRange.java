package org.loboevolution.html.dom.input;

import java.awt.Dimension;

import javax.swing.JSlider;
import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.style.HtmlValues;

public class InputRange {

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