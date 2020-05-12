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
 * Created on Oct 22, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class LocalErrorHandler implements ErrorHandler {
	private static final Logger logger = Logger.getLogger(LocalErrorHandler.class.getName());

	/**
	 * <p>Constructor for LocalErrorHandler.</p>
	 */
	public LocalErrorHandler() {
	}

	/** {@inheritDoc} */
	@Override
	public void error(SAXParseException exception) throws SAXException {
		logger.log(Level.SEVERE, exception.getMessage(), exception.getCause());
	}

	/** {@inheritDoc} */
	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		logger.log(Level.SEVERE, exception.getMessage(), exception.getCause());
	}

	/** {@inheritDoc} */
	@Override
	public void warning(SAXParseException exception) throws SAXException {
		logger.log(Level.WARNING, exception.getMessage(), exception.getCause());
	}
}
