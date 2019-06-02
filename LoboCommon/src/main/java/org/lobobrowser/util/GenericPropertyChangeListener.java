/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Mar 19, 2005
 */
package org.lobobrowser.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;

/**
 * @author J. H. S.
 */
public class GenericPropertyChangeListener implements GenericEventListener {
	private final PropertyChangeListener delegate;

	public GenericPropertyChangeListener(PropertyChangeListener delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof GenericPropertyChangeListener
				&& ((GenericPropertyChangeListener) other).delegate.equals(this.delegate);
	}

	@Override
	public int hashCode() {
		return this.delegate.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sourceforge.xamj.util.GenericEventListener#processEvent(java.util.
	 * EventObject)
	 */
	@Override
	public void processEvent(EventObject event) {
		this.delegate.propertyChange((PropertyChangeEvent) event);
	}
}
