/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Node;
import org.mozilla.javascript.Token;

/**
 * Represents a symbol-table entry.
 *
 * @author utente
 * @version $Id: $Id
 */
public class Symbol {

    // One of Token.FUNCTION, Token.LP (for parameters), Token.VAR,
    // Token.LET, or Token.CONST
    private int declType;
    private int index = -1;
    private String name;
    private Node node;
    private Scope containingTable;

    /**
     * <p>Constructor for Symbol.</p>
     */
    public Symbol() {
    }

    /**
     * Constructs a new Symbol with a specific name and declaration type
     *
     * @param declType {@link org.mozilla.javascript.Token#FUNCTION}, {@link org.mozilla.javascript.Token#LP}
     * (for params), {@link org.mozilla.javascript.Token#VAR}, {@link org.mozilla.javascript.Token#LET} or {@link org.mozilla.javascript.Token#CONST}
     * @param name a {@link java.lang.String} object.
     */
    public Symbol(int declType, String name) {
        setName(name);
        setDeclType(declType);
    }

    /**
     * Returns symbol declaration type
     *
     * @return a int.
     */
    public int getDeclType() {
        return declType;
    }

    /**
     * Sets symbol declaration type
     *
     * @param declType a int.
     */
    public void setDeclType(int declType) {
        if (!(declType == Token.FUNCTION
              || declType == Token.LP
              || declType == Token.VAR
              || declType == Token.LET
              || declType == Token.CONST))
            throw new IllegalArgumentException("Invalid declType: " + declType);
        this.declType = declType;
    }

    /**
     * Returns symbol name
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets symbol name
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the node associated with this identifier
     *
     * @return a {@link org.mozilla.javascript.Node} object.
     */
    public Node getNode() {
        return node;
    }

    /**
     * Returns symbol's index in its scope
     *
     * @return a int.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets symbol's index in its scope
     *
     * @param index a int.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Sets the node associated with this identifier
     *
     * @param node a {@link org.mozilla.javascript.Node} object.
     */
    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * Returns the Scope in which this symbol is entered
     *
     * @return a {@link org.mozilla.javascript.ast.Scope} object.
     */
    public Scope getContainingTable() {
        return containingTable;
    }

    /**
     * Sets this symbol's Scope
     *
     * @param containingTable a {@link org.mozilla.javascript.ast.Scope} object.
     */
    public void setContainingTable(Scope containingTable) {
        this.containingTable = containingTable;
    }

    /**
     * <p>getDeclTypeName.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDeclTypeName() {
        return Token.typeToName(declType);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Symbol (");
        result.append(getDeclTypeName());
        result.append(") name=");
        result.append(name);
        if (node != null) {
            result.append(" line=");
            result.append(node.getLineno());
        }
        return result.toString();
    }
}
