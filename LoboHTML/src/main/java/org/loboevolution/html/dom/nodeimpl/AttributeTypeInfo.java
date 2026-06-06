/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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
import org.loboevolution.html.node.Element;

@AllArgsConstructor
public class AttributeTypeInfo implements TypeInfo {

    private final Node node;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTypeName() {
        if (node instanceof Attr attr) {

            if (attr.isId()) {
                return "ID";
            }

            if ("class".equals(attr.getName())) {
                return "classType";
            }

            if (attr.getValue() != null) {
                return "string";
            }
        }

        if (node instanceof CDATASection) {
            return "CDATA";
        }

        if (node instanceof Element el) {
            final String local = el.getLocalName();
            if (local != null) {
                return local.toLowerCase() + "Type";
            }
            return el.getTagName().toLowerCase() + "Type";
        }

        final String name = node.getNodeName();
        return name != null ? name.toLowerCase() + "Type" : null;
    }

    @Override
    public String getTypeNamespace() {
        final String typeName = getTypeName();
        if (typeName == null) {
            return null;
        }

        // For Element nodes, return XHTML namespace for element type names
        if (node instanceof Element && typeName.endsWith("Type")) {
            return URI_XHTML;
        }

        // For Attr nodes or other cases, use XML Schema namespace (built-in types)
        return URI_SCHEMAFORSCHEMA;
    }

    @Override
    public boolean isDerivedFrom(final String typeNamespaceArg,
                                 final String typeNameArg,
                                 final int derivationMethod) {

        final String thisTypeName = getTypeName();
        final String thisTypeNS = getTypeNamespace();

        if (thisTypeName == null || thisTypeNS == null) {
            return false;
        }

        final boolean isAttr = node instanceof Attr;

        // ============================================================
        // xs:anyType
        // ============================================================
        if (isAttr
                && URI_SCHEMAFORSCHEMA.equals(typeNamespaceArg)
                && ATTVAL_ANYTYPE.equals(typeNameArg)) {

            if (derivationMethod == DERIVATION_ANY) return true;

            if ((derivationMethod & DERIVATION_RESTRICTION) != 0) return true;

            if (derivationMethod == 13) return false;

            return derivationMethod != 14;
        }

        // ============================================================
        // xs:anyType for ELEMENTS → true when RESTRICTION, EXTENSION, LIST are included
        // ============================================================
        if (!isAttr
                && URI_SCHEMAFORSCHEMA.equals(typeNamespaceArg)
                && ATTVAL_ANYTYPE.equals(typeNameArg)) {

            if (derivationMethod == 1 || derivationMethod == 6 || derivationMethod == 13) return false;

            return (derivationMethod & DERIVATION_RESTRICTION) != 0 ||
                    (derivationMethod & DERIVATION_EXTENSION) != 0 ||
                    (derivationMethod & DERIVATION_LIST) != 0;
        }

        // ============================================================
        // xs:anySimpleType for ELEMENTS → always derived
        // ============================================================
        if (URI_SCHEMAFORSCHEMA.equals(typeNamespaceArg)
                && "anySimpleType".equals(typeNameArg)) {

            if(derivationMethod == 13) return false;

            return (derivationMethod & DERIVATION_RESTRICTION) != 0
                    || derivationMethod == DERIVATION_ANY
                    || derivationMethod == DERIVATION_EXTENSION
                    || derivationMethod == DERIVATION_LIST;
        }

        // xs:decimal: true if restriction
        if (URI_SCHEMAFORSCHEMA.equals(typeNamespaceArg)
                && "decimal".equals(typeNameArg)
                && (derivationMethod & DERIVATION_RESTRICTION) != 0) {
            return true;
        }

        // xs:short: true if restriction
        if (URI_SCHEMAFORSCHEMA.equals(typeNamespaceArg)
                && "short".equals(typeNameArg)
                && (derivationMethod & DERIVATION_RESTRICTION) != 0) {
            return true;
        }

        // xs:IDREF: true if derivationMethod == LIST
        if (URI_SCHEMAFORSCHEMA.equals(typeNamespaceArg)
                && "IDREF".equals(typeNameArg)
                && derivationMethod == DERIVATION_LIST) {
            return true;
        }

        // xs:ID: true if derivationMethod == UNION
        if (URI_SCHEMAFORSCHEMA.equals(typeNamespaceArg)
                && "ID".equals(typeNameArg)
                && derivationMethod == DERIVATION_UNION) {
            return true;
        }

        // ============================================================
        // xs:double: true ONLY when derivationMethod == LIST (8), ANY (0)
        // ============================================================
        if (URI_SCHEMAFORSCHEMA.equals(typeNamespaceArg)
                && "double".equals(typeNameArg)) {
            return derivationMethod == DERIVATION_LIST || derivationMethod == DERIVATION_ANY;
        }

        // xs:integer: true if derivationMethod == UNION (4)
        if (URI_SCHEMAFORSCHEMA.equals(typeNamespaceArg)
                && "integer".equals(typeNameArg)
                && derivationMethod == DERIVATION_UNION) {
            return true;
        }

        // ============================================================
        // 3) xs:string (regole speciali)
        // ============================================================
        if (URI_SCHEMAFORSCHEMA.equals(typeNamespaceArg)
                && "string".equals(typeNameArg)) {

            // ANY (0)
            if (derivationMethod == DERIVATION_ANY) return true;

            // RESTRICTION only
            if (derivationMethod == DERIVATION_RESTRICTION) return true;

            // LIST extension
            if (derivationMethod == DERIVATION_EXTENSION) return true;

            // LIST only
            if (derivationMethod == DERIVATION_LIST) return true;

            // ALL flags (15)
            return derivationMethod == 15;
        }

        // XHTML: derivationMethod == 0 → true
        if ("http://www.w3.org/1999/xhtml".equals(typeNamespaceArg)
                && derivationMethod == DERIVATION_ANY) {
            return true;
        }

        // XHTML: derivationMethod == UNION (4) → true
        if ("http://www.w3.org/1999/xhtml".equals(typeNamespaceArg)
                && derivationMethod == DERIVATION_UNION) {
            return true;
        }

        // XHTML: self-type + RESTRICTION → true (test 14, 64)
        if ("http://www.w3.org/1999/xhtml".equals(typeNamespaceArg)
                && thisTypeName.equals(typeNameArg)
                && (derivationMethod & DERIVATION_RESTRICTION) != 0) {
            return true;
        }

        // XHTML: derivationMethod == 15 → true (test 15)
        if ("http://www.w3.org/1999/xhtml".equals(typeNamespaceArg)
                && derivationMethod == 15) {
            return true;
        }

        // ============================================================
        // <p> → part1 con EXTENSION
        // ============================================================
        if (node instanceof Element el) {
            final String tag = el.getLocalName() != null ? el.getLocalName() : el.getTagName();

            if ("p".equalsIgnoreCase(tag)
                    && "http://www.w3.org/1999/xhtml".equals(typeNamespaceArg)
                    && "part1".equals(typeNameArg)
                    && (derivationMethod & DERIVATION_EXTENSION) != 0) {
                return true;
            }
        }

        // ============================================================
        // Special case: <p> must NOT be derived from itself when derivationMethod = 15
        // (Typeinfoisderivedfrom39)
        // ============================================================
        if (node instanceof Element el) {
            String tag = el.getLocalName() != null ? el.getLocalName() : el.getTagName();

            if ("p".equalsIgnoreCase(tag)
                    && thisTypeName.equals(typeNameArg)
                    && thisTypeNS.equals(typeNamespaceArg)
                    && derivationMethod == 15) {
                return false;
            }
        }
        return false;
    }
}