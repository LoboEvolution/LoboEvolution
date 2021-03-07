package org.loboevolution.html.node;

public interface DOMConfiguration {

	boolean canSetParameter(String name, Object value);

	Object getParameter(String name);

	DOMStringList getParameterNames();

	public void setParameter(String name, Object value);
}