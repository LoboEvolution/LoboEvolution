package org.lobobrowser.html.w3c;


/**
 * The Interface HTMLCommandElement.
 */
public interface HTMLCommandElement extends HTMLElement {
	// HTMLCommandElement
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType();

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type);

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLElement#getLabel()
	 */
	public String getLabel();

	/**
	 * Sets the label.
	 *
	 * @param label the new label
	 */
	public void setLabel(String label);

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLElement#getIcon()
	 */
	public String getIcon();

	/**
	 * Sets the icon.
	 *
	 * @param icon the new icon
	 */
	public void setIcon(String icon);

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLElement#getDisabled()
	 */
	public boolean getDisabled();

	/**
	 * Sets the disabled.
	 *
	 * @param disabled the new disabled
	 */
	public void setDisabled(boolean disabled);

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLElement#getChecked()
	 */
	public boolean getChecked();

	/**
	 * Sets the checked.
	 *
	 * @param checked the new checked
	 */
	public void setChecked(boolean checked);

	/**
	 * Gets the radiogroup.
	 *
	 * @return the radiogroup
	 */
	public String getRadiogroup();

	/**
	 * Sets the radiogroup.
	 *
	 * @param radiogroup the new radiogroup
	 */
	public void setRadiogroup(String radiogroup);
}
