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

import org.loboevolution.html.node.TypeInfo;

public class AttributeTypeInfo implements TypeInfo {

    private boolean isId;

    public AttributeTypeInfo(boolean isId){
        this.isId = isId;
    }

    /** {@inheritDoc} */
    @Override
    public String getTypeName() {
        if (isId) {
            return "ID";
        }
        return "CDATA";
    }

    /** {@inheritDoc} */
    @Override
    public String getTypeNamespace() {
        return "http://www.w3.org/TR/REC-xml";
    }

    /** {@inheritDoc} */
    @Override
    public boolean isDerivedFrom(String typeNamespaceArg, String typeNameArg, int derivationMethod) {
        return false;
    }
}
