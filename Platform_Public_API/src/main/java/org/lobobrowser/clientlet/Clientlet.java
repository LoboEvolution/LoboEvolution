/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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

/**
 * A clientlet processes a URL response and creates content renderable by a user
 * agent.
 *
 * @see ClientletSelector#select(ClientletRequest, ClientletResponse)
 */
public interface Clientlet {
    /**
     * This method is invoked by a clientlet engine to process a URL response.
     * After the response is processed, the clientlet should either set
     * displayable content via
     * {@link ClientletContext#setResultingContent(ComponentContent)} or
     * redirect navigation elsewhere.
     *
     * @param context
     *            An instance of ClientletContext which represents the current
     *            request context.
     * @throws ClientletException
     *             This may be thrown by a clientlet when there are document
     *             errors.
     */
    void process(ClientletContext context) throws ClientletException;
}
