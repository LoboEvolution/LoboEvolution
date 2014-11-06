package org.lobobrowser.html.w3c;

import org.w3c.dom.NodeList;

public interface HTMLMeterElement extends HTMLElement {
	// HTMLMeterElement
	public float getValue();

	public void setValue(float value);

	public float getMin();

	public void setMin(float min);

	public float getMax();

	public void setMax(float max);

	public float getLow();

	public void setLow(float low);

	public float getHigh();

	public void setHigh(float high);

	public float getOptimum();

	public void setOptimum(float optimum);

	public HTMLFormElement getForm();

	public NodeList getLabels();
}
