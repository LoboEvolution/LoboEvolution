/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

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
 * The listener interface for receiving genericPropertyChange events. The class
 * that is interested in processing a genericPropertyChange event implements
 * this interface, and the object created with that class is registered with a
 * component using the component's <code>addGenericPropertyChangeListener</code>
 * method. When the genericPropertyChange event occurs, that object's
 * appropriate method is invoked.
 *
 * @author J. H. S.
 */
public class GenericPropertyChangeListener implements GenericEventListener {
	/** The delegate. */
	private final PropertyChangeListener delegate;

	/**
	 * Instantiates a new generic property change listener.
	 *
	 * @param delegate
	 *            the delegate
	 */
	public GenericPropertyChangeListener(PropertyChangeListener delegate) {
		this.delegate = delegate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.xamj.util.GenericEventListener#processEvent(java.util
	 * .EventObject)
	 */
	@Override
	public void processEvent(EventObject event) {
		this.delegate.propertyChange((PropertyChangeEvent) event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return other instanceof GenericPropertyChangeListener
				&& ((GenericPropertyChangeListener) other).delegate.equals(this.delegate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.delegate.hashCode();
	}
}
