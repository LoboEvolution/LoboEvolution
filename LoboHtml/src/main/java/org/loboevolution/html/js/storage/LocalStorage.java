package org.loboevolution.html.js.storage;

import java.util.Map;
import org.loboevolution.store.WebStore;

public class LocalStorage implements Storage {
	
	private final int index;
	
	public LocalStorage() {
		index = -1;
	}
	
	@Override
	public int getLength() {
		return WebStore.countStorage(index);
	}
	
	@Override
	public Object key(int index) {
        int counter = 0;
        Map<String, String> store = WebStore.getMapStorage(index);
        for (final String key : store.keySet()) {
            if (counter++ == index) {
                return key;
            }
        }
        return null;
    }
	
	@Override
	public Object getItem(String key) {
		return WebStore.getValue(key, index);
	}
	
	@Override
	public void setItem(String keyName, String keyValue) {
		WebStore.deleteStorage(keyName, 0, index);
		WebStore.insertStorage(keyName, keyValue, 0, index);
	}

	@Override
	public void removeItem(String keyName) {
		WebStore.deleteStorage(keyName, 0, index);

	}
	
	@Override
	public void getClear() {
		WebStore.deleteStorage(0, index);
	}
}