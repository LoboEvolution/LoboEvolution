package org.loboevolution.info;

import java.io.Serializable;

/**
 * <p>TabInfo class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class TabInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String url;
	
	private String indexTab;
	
	private String title;
	
	
	/**
	 * <p>Getter for the field url.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * <p>Getter for the field indexTab.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getIndexTab() {
		return indexTab;
	}

	/**
	 * <p>Setter for the field url.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * <p>Setter for the field indexTab.</p>
	 *
	 * @param indexTab a {@link java.lang.String} object.
	 */
	public void setIndexTab(String indexTab) {
		this.indexTab = indexTab;
	}

	/**
	 * <p>Getter for the field title.</p>
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * <p>Setter for the field title.</p>
	 *
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	

}
