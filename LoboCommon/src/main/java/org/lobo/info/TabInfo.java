package org.lobo.info;

import java.io.Serializable;

public class TabInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String url;
	
	private String indexTab;
	
	
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
	

}
