/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import org.mozilla.javascript.Node;
import org.mozilla.javascript.Token;

/**
 * Used for code generation.  During codegen, the AST is transformed
 * into an Intermediate Representation (IR) in which loops, ifs, switches
 * and other control-flow statements are rewritten as labeled jumps.
 * If the parser is set to IDE-mode, the resulting AST will not contain
 * any instances of this class.
 *
 *
 *
 */
public class Jump extends AstNode {

    public Node target;
    private Node target2;
    private Jump jumpNode;

    /**
     * <p>Constructor for Jump.</p>
     */
    public Jump() {
        type = Token.ERROR;
    }

    /**
     * <p>Constructor for Jump.</p>
     *
     * @param nodeType a int.
     */
    public Jump(int nodeType) {
        type = nodeType;
    }

    /**
     * <p>Constructor for Jump.</p>
     *
     * @param type a int.
     * @param lineno a int.
     */
    public Jump(int type, int lineno) {
        this(type);
        setLineno(lineno);
    }

    /**
     * <p>Constructor for Jump.</p>
     *
     * @param type a int.
     * @param child a {@link org.mozilla.javascript.Node} object.
     */
    public Jump(int type, Node child) {
        this(type);
        addChildToBack(child);
    }

    /**
     * <p>Constructor for Jump.</p>
     *
     * @param type a int.
     * @param child a {@link org.mozilla.javascript.Node} object.
     * @param lineno a int.
     */
    public Jump(int type, Node child, int lineno) {
        this(type, child);
        setLineno(lineno);
    }

    /**
     * <p>getJumpStatement.</p>
     *
     * @return a {@link org.mozilla.javascript.ast.Jump} object.
     */
    public Jump getJumpStatement()
    {
        if (type != Token.BREAK && type != Token.CONTINUE) codeBug();
        return jumpNode;
    }

    /**
     * <p>setJumpStatement.</p>
     *
     * @param jumpStatement a {@link org.mozilla.javascript.ast.Jump} object.
     */
    public void setJumpStatement(Jump jumpStatement)
    {
        if (type != Token.BREAK && type != Token.CONTINUE) codeBug();
        if (jumpStatement == null) codeBug();
        if (this.jumpNode != null) codeBug(); //only once
        this.jumpNode = jumpStatement;
    }

    /**
     * <p>getDefault.</p>
     *
     * @return a {@link org.mozilla.javascript.Node} object.
     */
    public Node getDefault()
    {
        if (type != Token.SWITCH) codeBug();
        return target2;
    }

    /**
     * <p>setDefault.</p>
     *
     * @param defaultTarget a {@link org.mozilla.javascript.Node} object.
     */
    public void setDefault(Node defaultTarget)
    {
        if (type != Token.SWITCH) codeBug();
        if (defaultTarget.getType() != Token.TARGET) codeBug();
        if (target2 != null) codeBug(); //only once
        target2 = defaultTarget;
    }

    /**
     * <p>getFinally.</p>
     *
     * @return a {@link org.mozilla.javascript.Node} object.
     */
    public Node getFinally()
    {
        if (type != Token.TRY) codeBug();
        return target2;
    }

    /**
     * <p>setFinally.</p>
     *
     * @param finallyTarget a {@link org.mozilla.javascript.Node} object.
     */
    public void setFinally(Node finallyTarget)
    {
        if (type != Token.TRY) codeBug();
        if (finallyTarget.getType() != Token.TARGET) codeBug();
        if (target2 != null) codeBug(); //only once
        target2 = finallyTarget;
    }

    /**
     * <p>getLoop.</p>
     *
     * @return a {@link org.mozilla.javascript.ast.Jump} object.
     */
    public Jump getLoop()
    {
        if (type != Token.LABEL) codeBug();
        return jumpNode;
    }

    /**
     * <p>setLoop.</p>
     *
     * @param loop a {@link org.mozilla.javascript.ast.Jump} object.
     */
    public void setLoop(Jump loop)
    {
        if (type != Token.LABEL) codeBug();
        if (loop == null) codeBug();
        if (jumpNode != null) codeBug(); //only once
        jumpNode = loop;
    }

    /**
     * <p>getContinue.</p>
     *
     * @return a {@link org.mozilla.javascript.Node} object.
     */
    public Node getContinue()
    {
        if (type != Token.LOOP) codeBug();
        return target2;
    }

    /**
     * <p>setContinue.</p>
     *
     * @param continueTarget a {@link org.mozilla.javascript.Node} object.
     */
    public void setContinue(Node continueTarget)
    {
        if (type != Token.LOOP) codeBug();
        if (continueTarget.getType() != Token.TARGET) codeBug();
        if (target2 != null) codeBug(); //only once
        target2 = continueTarget;
    }

    /**
     * {@inheritDoc}
     *
     * Jumps are only used directly during code generation, and do
     * not support this interface.
     */
    @Override
    public void visit(NodeVisitor visitor) {
        throw new UnsupportedOperationException(this.toString());
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        throw new UnsupportedOperationException(this.toString());
    }
}
