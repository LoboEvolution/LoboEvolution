package org.lobo.info;

import java.io.Serializable;

public class TabInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String url;
	
	private String indexTab;
	
	private String title;
	
	
	public String getUrl() {
		return url;
	}

	public String getIndexTab() {
		return indexTab;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setIndexTab(String indexTab) {
		this.indexTab = indexTab;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	

}
