package org.lobobrowser.html.w3c;

public interface HTMLCommandElement extends HTMLElement {
	// HTMLCommandElement
	public String getType();

	public void setType(String type);

	public String getLabel();

	public void setLabel(String label);

	public String getIcon();

	public void setIcon(String icon);

	public boolean getDisabled();

	public void setDisabled(boolean disabled);

	public boolean getChecked();

	public void setChecked(boolean checked);

	public String getRadiogroup();

	public void setRadiogroup(String radiogroup);
}
