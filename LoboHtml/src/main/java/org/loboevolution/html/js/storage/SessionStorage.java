package org.loboevolution.html.js.storage;

import java.util.Map;

import org.loboevolution.store.TabStore;
import org.loboevolution.store.WebStore;

public class SessionStorage implements Storage {
	
	private final int index;
	
	public SessionStorage() {
		this.index = TabStore.getTabs().size();
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
		WebStore.deleteStorage(keyName, 1, index);
		WebStore.insertStorage(keyName, keyValue, 1, index);
	}

	@Override
	public void removeItem(String keyName) {
		WebStore.deleteStorage(keyName, 1, index);

	}

	@Override
	public void getClear() {
		WebStore.deleteStorage(1, index);
	}
}
