/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
