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

package org.loboevolution.html.dom.nodeimpl;

import lombok.AllArgsConstructor;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.CDATASection;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.TypeInfo;

@AllArgsConstructor
public class AttributeTypeInfo implements TypeInfo {

    private static final String URI_SCHEMAFORSCHEMA = "http://www.w3.org/2001/XMLSchema";

    private static final String ATTVAL_ANYTYPE = "anyType";

    private static final int DERIVATION_ANY = 0;

    private static final int DERIVATION_RESTRICTION = 1;
    private static final int DERIVATION_EXTENSION = 2;
    private static final int DERIVATION_UNION = 4;
    private static final int DERIVATION_LIST = 8;


    private Node node;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTypeName() {
        if (node instanceof Attr && ((Attr) node).isId()) {
            return "ID";
        }

        if (node instanceof Attr && node.getNodeValue() != null) {
            return "string";
        }

        if (node instanceof CDATASection) {
            return "CDATA";
        }

        return node.getNodeName().toLowerCase() + "Type";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTypeNamespace() {
        return "http://www.w3.org/2001/XMLSchema";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDerivedFrom(String typeNamespaceArg, String typeNameArg, int derivationMethod) {

        if (typeNameArg == null)
            return false;

        if (URI_SCHEMAFORSCHEMA.equals(typeNamespaceArg)
                && ATTVAL_ANYTYPE.equals(typeNameArg)
                && (((derivationMethod & DERIVATION_RESTRICTION) != 0)
                || (derivationMethod == DERIVATION_ANY))) {
            return true;
        }

        if ((derivationMethod & DERIVATION_RESTRICTION) != 0) {
            return true;
        }

        // list
        if ((derivationMethod & DERIVATION_LIST) != 0) {
            return true;
        }

        // union
        if ((derivationMethod & DERIVATION_UNION) != 0) {
            return true;
        }

        // extension
        if (((derivationMethod & DERIVATION_EXTENSION) != 0)
                && (((derivationMethod & DERIVATION_RESTRICTION) == 0)
                && ((derivationMethod & DERIVATION_LIST) == 0)
                && ((derivationMethod & DERIVATION_UNION) == 0))) {
            return false;
        }

        if (((derivationMethod & DERIVATION_EXTENSION) == 0)
                && (((derivationMethod & DERIVATION_RESTRICTION) == 0)
                && ((derivationMethod & DERIVATION_LIST) == 0)
                && ((derivationMethod & DERIVATION_UNION) == 0))) {
            return true;
        }

        return false;
    }
}
