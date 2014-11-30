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
 * Created on Apr 15, 2005
 */
package org.lobobrowser.util;

import java.util.EventObject;

/**
 * @author J. H. S.
 */
public class InputProgressEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4768022316827973086L;
	private final int progress;

	/**
	 * @param arg0
	 */
	public InputProgressEvent(Object arg0, int progress) {
		super(arg0);
		this.progress = progress;
	}

	public int getProgress() {
		return this.progress;
	}
}
