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
package org.lobobrowser.clientlet;

import org.lobobrowser.ua.NavigatorExtensionContext;

/**
 * Interface implemented in order to handle web responses, typically by checking
 * the content types.
 *
 * @see NavigatorExtensionContext#addClientletSelector(ClientletSelector)
 */
public interface ClientletSelector {

	/**
	 * This method is invoked after a URL response has been received by the user
	 * agent. It obtains a <code>Clientlet</code> instance that can handle the
	 * given request and response. It <i>must</i> return <code>null</code> if it
	 * does not know how to handle the response. Generally a selector should
	 * only attempt to handle a specific response MIME type. If the MIME type is
	 * missing or is generic (e.g. application/octet-stream), then the selector
	 * should check the file extension. It is recommended that implementors use
	 * the convenience method,
	 * {@link ClientletResponse#matches(String, String[])}, to determine if they
	 * should handle a response.
	 * <p>
	 * The <code>select</code> method is invoked on all extensions that have
	 * registered one or more clientlet selectors. Extensions are invoked in
	 * descending order of priority. If a extension returns a non-null
	 * clientlet, the rest of the extensions are not invoked.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return A new <code>Clientlet</code> instance, or <code>null</code> if
	 *         the clientlet selector does not know how to handle the response.
	 * @see ClientletResponse#matches(String, String[])
	 */
	Clientlet select(ClientletRequest request, ClientletResponse response);

	/**
	 * This method is meant for the primary extension to handle content that was
	 * not handled by any other extension. Invocation of this method proceeds in
	 * <i>ascending</i> order of extension priority. Implementors will generally
	 * have this method return <code>null</code> unless they would like to allow
	 * extensions with lower priority to override the selection.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the clientlet
	 */
	Clientlet lastResortSelect(ClientletRequest request, ClientletResponse response);
}
