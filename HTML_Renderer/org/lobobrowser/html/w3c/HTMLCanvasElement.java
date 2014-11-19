package org.lobobrowser.html.w3c;

public interface HTMLCanvasElement extends HTMLElement {
	
	public int getWidth();

	public void setWidth(int width);

	public int getHeight();

	public void setHeight(int height);

	public String toDataURL();

	public String toDataURL(String type, Object... args);

	public Object getContext(String contextId);
}
