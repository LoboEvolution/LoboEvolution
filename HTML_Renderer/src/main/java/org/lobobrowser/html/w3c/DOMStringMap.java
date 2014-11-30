package org.lobobrowser.html.w3c;

public interface DOMStringMap {
	// DOMStringMap
	public String getElement(String name);

	public void setElement(String name, String value);

	public void createElement(String name, String value);

	public void deleteElement(String name);
}
