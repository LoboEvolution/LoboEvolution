package org.lobobrowser.html.w3c;


/**
 * The Interface HTMLTimeElement.
 */
public interface HTMLTimeElement extends HTMLElement {
	// HTMLTimeElement
	/**
	 * Gets the date time.
	 *
	 * @return the date time
	 */
	public String getDateTime();

	/**
	 * Sets the date time.
	 *
	 * @param dateTime the new date time
	 */
	public void setDateTime(String dateTime);

	/**
	 * Gets the pub date.
	 *
	 * @return the pub date
	 */
	public boolean getPubDate();

	/**
	 * Sets the pub date.
	 *
	 * @param pubDate the new pub date
	 */
	public void setPubDate(boolean pubDate);

	/**
	 * Gets the value as date.
	 *
	 * @return the value as date
	 */
	public long getValueAsDate();
}
