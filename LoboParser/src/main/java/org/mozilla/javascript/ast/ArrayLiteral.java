/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mozilla.javascript.Token;

/**
 * AST node for an Array literal.  The elements list will always be
 * non-{@code null}, although the list will have no elements if the Array literal
 * is empty.
 *
 * <p>Node type is {@link org.mozilla.javascript.Token#ARRAYLIT}.
 *
 * <pre><i>ArrayLiteral</i> :
 *        <b>[</b> Elisionopt <b>]</b>
 *        <b>[</b> ElementList <b>]</b>
 *        <b>[</b> ElementList , Elisionopt <b>]</b>
 * <i>ElementList</i> :
 *        Elisionopt AssignmentExpression
 *        ElementList , Elisionopt AssignmentExpression
 * <i>Elision</i> :
 *        <b>,</b>
 *        Elision <b>,</b></pre>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ArrayLiteral extends AstNode implements DestructuringForm {

    private static final List<AstNode> NO_ELEMS =
        Collections.unmodifiableList(new ArrayList<AstNode>());

    private List<AstNode> elements;
    private int destructuringLength;
    private int skipCount;
    private boolean isDestructuring;

    {
        type = Token.ARRAYLIT;
    }

    /**
     * <p>Constructor for ArrayLiteral.</p>
     */
    public ArrayLiteral() {
    }

    /**
     * <p>Constructor for ArrayLiteral.</p>
     *
     * @param pos a int.
     */
    public ArrayLiteral(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for ArrayLiteral.</p>
     *
     * @param pos a int.
     * @param len a int.
     */
    public ArrayLiteral(int pos, int len) {
        super(pos, len);
    }

    /**
     * Returns the element list
     *
     * @return the element list.  If there are no elements, returns an immutable
     *         empty list.  Elisions are represented as {@link org.mozilla.javascript.ast.EmptyExpression}
     *         nodes.
     */
    public List<AstNode> getElements() {
        return elements != null ? elements : NO_ELEMS;
    }

    /**
     * Sets the element list, and sets each element's parent to this node.
     *
     * @param elements the element list.  Can be {@code null}.
     */
    public void setElements(List<AstNode> elements) {
        if (elements == null) {
            this.elements = null;
        } else {
            if (this.elements != null)
                this.elements.clear();
            for (AstNode e : elements)
                addElement(e);
        }
    }

    /**
     * Adds an element to the list, and sets its parent to this node.
     *
     * @param element the element to add
     * @throws java.lang.IllegalArgumentException if element is {@code null}.  To indicate
     *         an empty element, use an {@link org.mozilla.javascript.ast.EmptyExpression} node.
     */
    public void addElement(AstNode element) {
        assertNotNull(element);
        if (elements == null)
            elements = new ArrayList<AstNode>();
        elements.add(element);
        element.setParent(this);
    }

    /**
     * Returns the number of elements in this {@code Array} literal,
     * including empty elements.
     *
     * @return a int.
     */
    public int getSize() {
        return elements == null ? 0 : elements.size();
    }

    /**
     * Returns element at specified index.
     *
     * @param index the index of the element to retrieve
     * @return the element
     * @throws java.lang.IndexOutOfBoundsException if the index is invalid
     */
    public AstNode getElement(int index) {
        if (elements == null)
            throw new IndexOutOfBoundsException("no elements");
        return elements.get(index);
    }

    /**
     * Returns destructuring length
     *
     * @return a int.
     */
    public int getDestructuringLength() {
      return destructuringLength;
    }

    /**
     * Sets destructuring length.  This is set by the parser and used
     * by the code generator.  {@code for ([a,] in obj)} is legal,
     * but {@code for ([a] in obj)} is not since we have both key and
     * value supplied.  The difference is only meaningful in array literals
     * used in destructuring-assignment contexts.
     *
     * @param destructuringLength a int.
     */
    public void setDestructuringLength(int destructuringLength) {
      this.destructuringLength = destructuringLength;
    }

    /**
     * Used by code generator.
     *
     * @return the number of empty elements
     */
    public int getSkipCount() {
        return skipCount;
    }

    /**
     * Used by code generator.
     *
     * @param count the count of empty elements
     */
    public void setSkipCount(int count) {
        skipCount = count;
    }

    /**
     * {@inheritDoc}
     *
     * Marks this node as being a destructuring form - that is, appearing
     * in a context such as {@code for ([a, b] in ...)} where it's the
     * target of a destructuring assignment.
     */
    @Override
    public void setIsDestructuring(boolean destructuring) {
        isDestructuring = destructuring;
    }

    /**
     * {@inheritDoc}
     *
     * Returns true if this node is in a destructuring position:
     * a function parameter, the target of a variable initializer, the
     * iterator of a for..in loop, etc.
     */
    @Override
    public boolean isDestructuring() {
        return isDestructuring;
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append("[");
        if (elements != null) {
            printList(elements, sb);
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * Visits this node, then visits its element expressions in order.
     * Any empty elements are represented by {@link EmptyExpression}
     * objects, so the callback will never be passed {@code null}.
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            for (AstNode e : getElements()) {
                e.visit(v);
            }
        }
    }
}
