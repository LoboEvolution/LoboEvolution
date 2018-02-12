/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dombl;

import org.loboevolution.html.domimpl.DOMNodeImpl;

/**
 * An abstract implementation of {@link DocumentNotificationListener} with blank
 * methods, provided for convenience.
 */
public abstract class DocumentNotificationAdapter implements DocumentNotificationListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.dombl.DocumentNotificationListener#allInvalidated()
	 */
	@Override
	public void allInvalidated() {
		// Method not implemented
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.DocumentNotificationListener#
	 * externalScriptLoading (org.loboevolution.html.domimpl.DOMNodeImpl)
	 */
	@Override
	public void externalScriptLoading(DOMNodeImpl node) {
		// Method not implemented
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.dombl.DocumentNotificationListener#invalidated(org.
	 * loboevolution.html.domimpl.DOMNodeImpl)
	 */
	@Override
	public void invalidated(DOMNodeImpl node) {
		// Method not implemented
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.dombl.DocumentNotificationListener#lookInvalidated(
	 * org .loboevolution.html.domimpl.DOMNodeImpl)
	 */
	@Override
	public void lookInvalidated(DOMNodeImpl node) {
		// Method not implemented
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.dombl.DocumentNotificationListener#nodeLoaded(org.
	 * loboevolution.html.domimpl.DOMNodeImpl)
	 */
	@Override
	public void nodeLoaded(DOMNodeImpl node) {
		// Method not implemented
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.DocumentNotificationListener#
	 * positionInvalidated (org.loboevolution.html.domimpl.DOMNodeImpl)
	 */
	@Override
	public void positionInvalidated(DOMNodeImpl node) {
		// Method not implemented
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.dombl.DocumentNotificationListener#sizeInvalidated(
	 * org .loboevolution.html.domimpl.DOMNodeImpl)
	 */
	@Override
	public void sizeInvalidated(DOMNodeImpl node) {
		// Method not implemented
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.DocumentNotificationListener#
	 * structureInvalidated (org.loboevolution.html.domimpl.DOMNodeImpl)
	 */
	@Override
	public void structureInvalidated(DOMNodeImpl node) {
		// Method not implemented
	}
}
