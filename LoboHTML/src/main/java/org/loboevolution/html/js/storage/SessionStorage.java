/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.js.storage;

import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.html.node.js.webstorage.Storage;

import java.util.Map;

/**
 * <p>SessionStorage class.</p>
 */
public class SessionStorage implements Storage {
	
	private int index;

	private HtmlRendererConfig config;
	
	/**
	 * <p>Constructor for SessionStorage.</p>
	 */
	public SessionStorage(HtmlRendererConfig config) {
		this.config = config;
		this.index = config.getTabs().size();
		if(this.index > 0) this.index = this.index -1;
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		return config.countStorage(index);
	}
	
	/** {@inheritDoc} */
	@Override
	public Object key(int index) {
        int counter = 0;
        Map<String, String> store = config.getMapStorage(index, 1);
        for (final String key : store.keySet()) {
            if (counter++ == index) {
                return key;
            }
        }
        return null;
    }
	
	/** {@inheritDoc} */
	@Override
	public Object getItem(String key) {
		return config.getValue(key, 1, index);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setItem(String keyName, String keyValue) {
		config.deleteStorage(keyName, 1, index);
		config.insertStorage(keyName, keyValue, 1, index);
	}

	/** {@inheritDoc} */
	@Override
	public void removeItem(String keyName) {
		config.deleteStorage(keyName, 1, index);

	}
	
	/** {@inheritDoc} */
	@Override
	public void clear() {
		config.deleteStorage(1, index);
	}

	@Override
	public String toString() {
		return "[object Storage]";
	}
}
