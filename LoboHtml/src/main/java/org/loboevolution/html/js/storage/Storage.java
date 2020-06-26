package org.loboevolution.html.js.storage;

public interface Storage {
	
	int getLength();
	
	Object key(int index);
	
	Object getItem(String key);

	void setItem(String keyName, String keyValue);
	
	void removeItem(String keyName);
	
	void getClear();

}
