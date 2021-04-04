/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mozilla.javascript.Node;
import org.mozilla.javascript.Token;

/**
 * A JavaScript function declaration or expression.
 * <p>Node type is {@link org.mozilla.javascript.Token#FUNCTION}.</p>
 *
 * <pre><i>FunctionDeclaration</i> :
 *        <b>function</b> Identifier ( FormalParameterListopt ) { FunctionBody }
 * <i>FunctionExpression</i> :
 *        <b>function</b> Identifieropt ( FormalParameterListopt ) { FunctionBody }
 * <i>FormalParameterList</i> :
 *        Identifier
 *        FormalParameterList , Identifier
 * <i>FunctionBody</i> :
 *        SourceElements
 * <i>Program</i> :
 *        SourceElements
 * <i>SourceElements</i> :
 *        SourceElement
 *        SourceElements SourceElement
 * <i>SourceElement</i> :
 *        Statement
 *        FunctionDeclaration</pre>
 *
 * JavaScript 1.8 introduces "function closures" of the form
 *  <pre>function ([params] ) Expression</pre>
 *
 * In this case the FunctionNode node will have no body but will have an
 * expression.
 *
 *
 *
 */
public class FunctionNode extends ScriptNode {

    /**
     * There are three types of functions that can be defined. The first
     * is a function statement. This is a function appearing as a top-level
     * statement (i.e., not nested inside some other statement) in either a
     * script or a function.<p>
     *
     * The second is a function expression, which is a function appearing in
     * an expression except for the third type, which is...<p>
     *
     * The third type is a function expression where the expression is the
     * top-level expression in an expression statement.<p>
     *
     * The three types of functions have different treatment and must be
     * distinguished.
     */
    public static final int FUNCTION_STATEMENT            = 1;
    /** Constant <code>FUNCTION_EXPRESSION=2</code> */
    public static final int FUNCTION_EXPRESSION           = 2;
    /** Constant <code>FUNCTION_EXPRESSION_STATEMENT=3</code> */
    public static final int FUNCTION_EXPRESSION_STATEMENT = 3;
    /** Constant <code>ARROW_FUNCTION=4</code> */
    public static final int ARROW_FUNCTION                = 4;

    public static enum Form { FUNCTION, GETTER, SETTER, METHOD }

    private static final List<AstNode> NO_PARAMS =
        Collections.unmodifiableList(new ArrayList<AstNode>());

    private Name functionName;
    private List<AstNode> params;
    private AstNode body;
    private boolean isExpressionClosure;
    private Form functionForm = Form.FUNCTION;
    private int lp = -1;
    private int rp = -1;

    // codegen variables
    private int functionType;
    private boolean needsActivation;
    private boolean isGenerator;
    private boolean isES6Generator;
    private List<Node> generatorResumePoints;
    private Map<Node,int[]> liveLocals;
    private AstNode memberExprNode;

    {
        type = Token.FUNCTION;
    }

    /**
     * <p>Constructor for FunctionNode.</p>
     */
    public FunctionNode() {
    }

    /**
     * <p>Constructor for FunctionNode.</p>
     *
     * @param pos a int.
     */
    public FunctionNode(int pos) {
        super(pos);
    }

    /**
     * <p>Constructor for FunctionNode.</p>
     *
     * @param pos a int.
     * @param name a {@link org.mozilla.javascript.ast.Name} object.
     */
    public FunctionNode(int pos, Name name) {
        super(pos);
        setFunctionName(name);
    }

    /**
     * Returns function name
     *
     * @return function name, {@code null} for anonymous functions
     */
    public Name getFunctionName() {
        return functionName;
    }

    /**
     * Sets function name, and sets its parent to this node.
     *
     * @param name function name, {@code null} for anonymous functions
     */
    public void setFunctionName(Name name) {
        functionName = name;
        if (name != null)
            name.setParent(this);
    }

    /**
     * Returns the function name as a string
     *
     * @return the function name, {@code ""} if anonymous
     */
    public String getName() {
        return functionName != null ? functionName.getIdentifier() : "";
    }

    /**
     * Returns the function parameter list
     *
     * @return the function parameter list.  Returns an immutable empty
     *         list if there are no parameters.
     */
    public List<AstNode> getParams() {
        return params != null ? params : NO_PARAMS;
    }

    /**
     * Sets the function parameter list, and sets the parent for
     * each element of the list.
     *
     * @param params the function parameter list, or {@code null} if no params
     */
    public void setParams(List<AstNode> params) {
        if (params == null) {
            this.params = null;
        } else {
            if (this.params != null)
                this.params.clear();
            for (AstNode param : params)
                addParam(param);
        }
    }

    /**
     * Adds a parameter to the function parameter list.
     * Sets the parent of the param node to this node.
     *
     * @param param the parameter
     * @throws java.lang.IllegalArgumentException if param is {@code null}
     */
    public void addParam(AstNode param) {
        assertNotNull(param);
        if (params == null) {
            params = new ArrayList<AstNode>();
        }
        params.add(param);
        param.setParent(this);
    }

    /**
     * Returns true if the specified {@link org.mozilla.javascript.ast.AstNode} node is a parameter
     * of this Function node.  This provides a way during AST traversal
     * to disambiguate the function name node from the parameter nodes.
     *
     * @param node a {@link org.mozilla.javascript.ast.AstNode} object.
     * @return a boolean.
     */
    public boolean isParam(AstNode node) {
        return params == null ? false : params.contains(node);
    }

    /**
     * Returns function body.  Normally a {@link org.mozilla.javascript.ast.Block}, but can be a plain
     * {@link org.mozilla.javascript.ast.AstNode} if it's a function closure.
     *
     * @return the body.  Can be {@code null} only if the AST is malformed.
     */
    public AstNode getBody() {
        return body;
    }

    /**
     * Sets function body, and sets its parent to this node.
     * Also sets the encoded source bounds based on the body bounds.
     * Assumes the function node absolute position has already been set,
     * and the body node's absolute position and length are set.<p>
     *
     * @param body function body.  Its parent is set to this node, and its
     * position is updated to be relative to this node.
     * @throws java.lang.IllegalArgumentException if body is {@code null}
     */
    public void setBody(AstNode body) {
        assertNotNull(body);
        this.body = body;
        if (Boolean.TRUE.equals(body.getProp(Node.EXPRESSION_CLOSURE_PROP))) {
            setIsExpressionClosure(true);
        }
        int absEnd = body.getPosition() + body.getLength();
        body.setParent(this);
        this.setLength(absEnd - this.position);
        setEncodedSourceBounds(this.position, absEnd);
    }

    /**
     * Returns left paren position, -1 if missing
     *
     * @return a int.
     */
    public int getLp() {
        return lp;
    }

    /**
     * Sets left paren position
     *
     * @param lp a int.
     */
    public void setLp(int lp) {
        this.lp = lp;
    }

    /**
     * Returns right paren position, -1 if missing
     *
     * @return a int.
     */
    public int getRp() {
        return rp;
    }

    /**
     * Sets right paren position
     *
     * @param rp a int.
     */
    public void setRp(int rp) {
        this.rp = rp;
    }

    /**
     * Sets both paren positions
     *
     * @param lp a int.
     * @param rp a int.
     */
    public void setParens(int lp, int rp) {
        this.lp = lp;
        this.rp = rp;
    }

    /**
     * Returns whether this is a 1.8 function closure
     *
     * @return a boolean.
     */
    public boolean isExpressionClosure() {
        return isExpressionClosure;
    }

    /**
     * Sets whether this is a 1.8 function closure
     *
     * @param isExpressionClosure a boolean.
     */
    public void setIsExpressionClosure(boolean isExpressionClosure) {
        this.isExpressionClosure = isExpressionClosure;
    }

    /**
     * Return true if this function requires an Ecma-262 Activation object.
     * The Activation object is implemented by
     * {@link org.mozilla.javascript.NativeCall}, and is fairly expensive
     * to create, so when possible, the interpreter attempts to use a plain
     * call frame instead.
     *
     * @return true if this function needs activation.  It could be needed
     * if there is a lexical closure, or in a number of other situations.
     */
    public boolean requiresActivation() {
        return needsActivation;
    }

    /**
     * <p>setRequiresActivation.</p>
     */
    public void setRequiresActivation() {
        needsActivation = true;
    }

    /**
     * <p>isGenerator.</p>
     *
     * @return a boolean.
     */
    public boolean isGenerator() {
      return isGenerator;
    }

    /**
     * <p>Setter for the field <code>isGenerator</code>.</p>
     */
    public void setIsGenerator() {
        isGenerator = true;
    }

    /**
     * <p>isES6Generator.</p>
     *
     * @return a boolean.
     */
    public boolean isES6Generator() {
        return isES6Generator;
    }

    /**
     * <p>Setter for the field <code>isES6Generator</code>.</p>
     */
    public void setIsES6Generator() {
        isES6Generator = true;
        isGenerator = true;
    }

    /**
     * <p>addResumptionPoint.</p>
     *
     * @param target a {@link org.mozilla.javascript.Node} object.
     */
    public void addResumptionPoint(Node target) {
        if (generatorResumePoints == null)
            generatorResumePoints = new ArrayList<Node>();
        generatorResumePoints.add(target);
    }

    /**
     * <p>getResumptionPoints.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Node> getResumptionPoints() {
        return generatorResumePoints;
    }

    /**
     * <p>Getter for the field <code>liveLocals</code>.</p>
     *
     * @return a {@link java.util.Map} object.
     */
    public Map<Node,int[]> getLiveLocals() {
        return liveLocals;
    }

    /**
     * <p>addLiveLocals.</p>
     *
     * @param node a {@link org.mozilla.javascript.Node} object.
     * @param locals an array of {@link int} objects.
     */
    public void addLiveLocals(Node node, int[] locals) {
        if (liveLocals == null)
            liveLocals = new HashMap<Node,int[]>();
        liveLocals.put(node, locals);
    }

    /** {@inheritDoc} */
    @Override
    public int addFunction(FunctionNode fnNode) {
        int result = super.addFunction(fnNode);
        if (getFunctionCount() > 0) {
            needsActivation = true;
        }
        return result;
    }

    /**
     * Returns the function type (statement, expr, statement expr)
     *
     * @return a int.
     */
    public int getFunctionType() {
        return functionType;
    }

    /**
     * <p>Setter for the field <code>functionType</code>.</p>
     *
     * @param type a int.
     */
    public void setFunctionType(int type) {
        functionType = type;
    }

    /**
     * <p>isMethod.</p>
     *
     * @return a boolean.
     */
    public boolean isMethod() {
        return functionForm == Form.GETTER || functionForm == Form.SETTER || functionForm == Form.METHOD;
    }

    /**
     * <p>isGetterMethod.</p>
     *
     * @return a boolean.
     */
    public boolean isGetterMethod() {
        return functionForm == Form.GETTER;
    }

    /**
     * <p>isSetterMethod.</p>
     *
     * @return a boolean.
     */
    public boolean isSetterMethod() {
        return functionForm == Form.SETTER;
    }

    /**
     * <p>isNormalMethod.</p>
     *
     * @return a boolean.
     */
    public boolean isNormalMethod() {
        return functionForm == Form.METHOD;
    }

    /**
     * <p>setFunctionIsGetterMethod.</p>
     */
    public void setFunctionIsGetterMethod() {
        functionForm = Form.GETTER;
    }

    /**
     * <p>setFunctionIsSetterMethod.</p>
     */
    public void setFunctionIsSetterMethod() {
        functionForm = Form.SETTER;
    }

    /**
     * <p>setFunctionIsNormalMethod.</p>
     */
    public void setFunctionIsNormalMethod() {
        functionForm = Form.METHOD;
    }

    /**
     * Rhino supports a nonstandard Ecma extension that allows you to
     * say, for instance, function a.b.c(arg1, arg) {...}, and it will
     * be rewritten at codegen time to:  a.b.c = function(arg1, arg2) {...}
     * If we detect an expression other than a simple Name in the position
     * where a function name was expected, we record that expression here.
     * <p>
     * This extension is only available by setting the CompilerEnv option
     * "isAllowMemberExprAsFunctionName" in the Parser.
     *
     * @param node a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public void setMemberExprNode(AstNode node) {
        memberExprNode = node;
        if (node != null)
            node.setParent(this);
    }

    /**
     * <p>Getter for the field <code>memberExprNode</code>.</p>
     *
     * @return a {@link org.mozilla.javascript.ast.AstNode} object.
     */
    public AstNode getMemberExprNode() {
        return memberExprNode;
    }

    /** {@inheritDoc} */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        boolean isArrow = functionType == ARROW_FUNCTION;
        if (!isMethod()) {
            sb.append(makeIndent(depth));
            if (!isArrow) {
                sb.append("function");
            }
        }
        if (functionName != null) {
            sb.append(" ");
            sb.append(functionName.toSource(0));
        }
        if (params == null) {
            sb.append("() ");
        } else if (isArrow && lp == -1) {
            // no paren
            printList(params, sb);
            sb.append(" ");
        } else {
            sb.append("(");
            printList(params, sb);
            sb.append(") ");
        }
        if (isArrow) {
            sb.append("=> ");
        }
        if (isExpressionClosure) {
            AstNode body = getBody();
            if (body.getLastChild() instanceof ReturnStatement) {
                // omit "return" keyword, just print the expression
                body = ((ReturnStatement) body.getLastChild()).getReturnValue();
                sb.append(body.toSource(0));
                if (functionType == FUNCTION_STATEMENT) {
                    sb.append(";");
                }
            } else {
                // should never happen
                sb.append(" ");
                sb.append(body.toSource(0));
            }
        } else {
            sb.append(getBody().toSource(depth).trim());
        }
        if (functionType == FUNCTION_STATEMENT || isMethod()) {
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * Visits this node, the function name node if supplied,
     * the parameters, and the body.  If there is a member-expr node,
     * it is visited last.
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            if (functionName != null) {
                functionName.visit(v);
            }
            for (AstNode param : getParams()) {
                param.visit(v);
            }
            getBody().visit(v);
            if (!isExpressionClosure) {
                if (memberExprNode != null) {
                    memberExprNode.visit(v);
                }
            }
        }
    }
}
