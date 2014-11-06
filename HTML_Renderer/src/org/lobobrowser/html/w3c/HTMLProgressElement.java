package org.lobobrowser.html.w3c;

import org.w3c.dom.NodeList;

public interface HTMLProgressElement extends HTMLElement {
	// HTMLProgressElement
	public float getValue();

	public void setValue(float value);

	public float getMax();

	public void setMax(float max);

	public float getPosition();

	public HTMLFormElement getForm();

	public NodeList getLabels();
}
