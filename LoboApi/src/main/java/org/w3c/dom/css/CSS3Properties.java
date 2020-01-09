package org.w3c.dom.css;

public interface CSS3Properties extends CSS2Properties {

	String getBoxSizing();
	
	String getClipPath();
	
	String getClipRule();
	
	String getFill();

	void setClipPath(String clip);
	
	void setClipRule(String clip);
}
