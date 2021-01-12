/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

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