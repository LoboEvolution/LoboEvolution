package org.lobobrowser.html.w3c;

public interface HTMLTimeElement extends HTMLElement {
	// HTMLTimeElement
	public String getDateTime();

	public void setDateTime(String dateTime);

	public boolean getPubDate();

	public void setPubDate(boolean pubDate);

	public long getValueAsDate();
}
