/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
package org.lobobrowser.html.gui;

import java.util.EventListener;
import java.util.EventObject;

import org.lobobrowser.util.EventDispatch2;

/**
 * The Class SelectionDispatch.
 */
public class SelectionDispatch extends EventDispatch2 {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xamjwg.util.EventDispatch2#dispatchEvent(java.util.EventListener,
	 * java.util.EventObject)
	 */
	@Override
	protected void dispatchEvent(EventListener listener, EventObject event) {
		((SelectionChangeListener) listener).selectionChanged((SelectionChangeEvent) event);
	}
}
