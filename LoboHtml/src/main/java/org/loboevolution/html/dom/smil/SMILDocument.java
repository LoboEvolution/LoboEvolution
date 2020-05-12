/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

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

package org.loboevolution.html.dom.smil;

import org.w3c.dom.Document;

/**
 * A SMIL document is the root of the SMIL Hierarchy and holds the entire
 * content. Beside providing access to the hierarchy, it also provides some
 * convenience methods for accessing certain sets of information from the
 * document. Cover document timing, document locking?, linking modality and any
 * other document level issues. Are there issues with nested SMIL files? Is it
 * worth talking about different document scenarios, corresponding to differing
 * profiles? E.g. Standalone SMIL, HTML integration, etc.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface SMILDocument extends Document, ElementSequentialTimeContainer {
}
