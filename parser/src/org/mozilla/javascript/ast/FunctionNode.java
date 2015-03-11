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
 * A JavaScript function declaration or expression.<p>
 * Node type is {@link Token#FUNCTION}.
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
     * */
    public static final int FUNCTION_STATEMENT            = 1;
    
    /** The Constant FUNCTION_EXPRESSION. */
    public static final int FUNCTION_EXPRESSION           = 2;
    
    /** The Constant FUNCTION_EXPRESSION_STATEMENT. */
    public static final int FUNCTION_EXPRESSION_STATEMENT = 3;

    /**
     * The Enum Form.
     */
    public static enum Form { /** The function. */
 FUNCTION, /** The getter. */
 GETTER, /** The setter. */
 SETTER }

    /** The Constant NO_PARAMS. */
    private static final List<AstNode> NO_PARAMS =
        Collections.unmodifiableList(new ArrayList<AstNode>());

    /** The function name. */
    private Name functionName;
    
    /** The params. */
    private List<AstNode> params;
    
    /** The body. */
    private AstNode body;
    
    /** The is expression closure. */
    private boolean isExpressionClosure;
    
    /** The function form. */
    private Form functionForm = Form.FUNCTION;
    
    /** The lp. */
    private int lp = -1;
    
    /** The rp. */
    private int rp = -1;

    // codegen variables
    /** The function type. */
    private int functionType;
    
    /** The needs activation. */
    private boolean needsActivation;
    
    /** The is generator. */
    private boolean isGenerator;
    
    /** The generator resume points. */
    private List<Node> generatorResumePoints;
    
    /** The live locals. */
    private Map<Node,int[]> liveLocals;
    
    /** The member expr node. */
    private AstNode memberExprNode;

    {
        type = Token.FUNCTION;
    }

    /**
     * Instantiates a new function node.
     */
    public FunctionNode() {
    }

    /**
     * Instantiates a new function node.
     *
     * @param pos the pos
     */
    public FunctionNode(int pos) {
        super(pos);
    }

    /**
     * Instantiates a new function node.
     *
     * @param pos the pos
     * @param name the name
     */
    public FunctionNode(int pos, Name name) {
        super(pos);
        setFunctionName(name);
    }

    /**
     * Returns function name.
     *
     * @return function name, {@code null} for anonymous functions
     */
    public Name getFunctionName() {
        return functionName;
    }

    /**
     * Sets function name, and sets its parent to this node.
     * @param name function name, {@code null} for anonymous functions
     */
    public void setFunctionName(Name name) {
        functionName = name;
        if (name != null)
            name.setParent(this);
    }

    /**
     * Returns the function name as a string.
     *
     * @return the function name, {@code ""} if anonymous
     */
    public String getName() {
        return functionName != null ? functionName.getIdentifier() : "";
    }

    /**
     * Returns the function parameter list.
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
     * Returns true if the specified {@link AstNode} node is a parameter
     * of this Function node.  This provides a way during AST traversal
     * to disambiguate the function name node from the parameter nodes.
     *
     * @param node the node
     * @return true, if is param
     */
    public boolean isParam(AstNode node) {
        return params == null ? false : params.contains(node);
    }

    /**
     * Returns function body.  Normally a {@link Block}, but can be a plain
     * {@link AstNode} if it's a function closure.
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
     * Returns left paren position, -1 if missing.
     *
     * @return the lp
     */
    public int getLp() {
        return lp;
    }

    /**
     * Sets left paren position.
     *
     * @param lp the new lp
     */
    public void setLp(int lp) {
        this.lp = lp;
    }

    /**
     * Returns right paren position, -1 if missing.
     *
     * @return the rp
     */
    public int getRp() {
        return rp;
    }

    /**
     * Sets right paren position.
     *
     * @param rp the new rp
     */
    public void setRp(int rp) {
        this.rp = rp;
    }

    /**
     * Sets both paren positions.
     *
     * @param lp the lp
     * @param rp the rp
     */
    public void setParens(int lp, int rp) {
        this.lp = lp;
        this.rp = rp;
    }

    /**
     * Returns whether this is a 1.8 function closure
     *
     * @return true, if is expression closure
     */
    public boolean isExpressionClosure() {
        return isExpressionClosure;
    }

    /**
     * Sets whether this is a 1.8 function closure
     *
     * @param isExpressionClosure the new checks if is expression closure
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
     * Sets the requires activation.
     */
    public void setRequiresActivation() {
        needsActivation = true;
    }

    /**
     * Checks if is generator.
     *
     * @return true, if is generator
     */
    public boolean isGenerator() {
      return isGenerator;
    }

    /**
     * Sets the is generator.
     */
    public void setIsGenerator() {
        isGenerator = true;
    }

    /**
     * Adds the resumption point.
     *
     * @param target the target
     */
    public void addResumptionPoint(Node target) {
        if (generatorResumePoints == null)
            generatorResumePoints = new ArrayList<Node>();
        generatorResumePoints.add(target);
    }

    /**
     * Gets the resumption points.
     *
     * @return the resumption points
     */
    public List<Node> getResumptionPoints() {
        return generatorResumePoints;
    }

    /**
     * Gets the live locals.
     *
     * @return the live locals
     */
    public Map<Node,int[]> getLiveLocals() {
        return liveLocals;
    }

    /**
     * Adds the live locals.
     *
     * @param node the node
     * @param locals the locals
     */
    public void addLiveLocals(Node node, int[] locals) {
        if (liveLocals == null)
            liveLocals = new HashMap<Node,int[]>();
        liveLocals.put(node, locals);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.ScriptNode#addFunction(org.mozilla.javascript.ast.FunctionNode)
     */
    @Override
    public int addFunction(FunctionNode fnNode) {
        int result = super.addFunction(fnNode);
        if (getFunctionCount() > 0) {
            needsActivation = true;
        }
        return result;
    }

    /**
     * Returns the function type (statement, expr, statement expr).
     *
     * @return the function type
     */
    public int getFunctionType() {
        return functionType;
    }

    /**
     * Sets the function type.
     *
     * @param type the new function type
     */
    public void setFunctionType(int type) {
        functionType = type;
    }

    /**
     * Checks if is getter or setter.
     *
     * @return true, if is getter or setter
     */
    public boolean isGetterOrSetter() {
        return functionForm == Form.GETTER || functionForm == Form.SETTER;
    }

    /**
     * Checks if is getter.
     *
     * @return true, if is getter
     */
    public boolean isGetter() {
        return functionForm == Form.GETTER;
    }

    /**
     * Checks if is setter.
     *
     * @return true, if is setter
     */
    public boolean isSetter() {
        return functionForm == Form.SETTER;
    }

    /**
     * Sets the function is getter.
     */
    public void setFunctionIsGetter() {
        functionForm = Form.GETTER;
    }

    /**
     * Sets the function is setter.
     */
    public void setFunctionIsSetter() {
        functionForm = Form.SETTER;
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
     * @param node the new member expr node
     */
    public void setMemberExprNode(AstNode node) {
        memberExprNode = node;
        if (node != null)
            node.setParent(this);
    }

    /**
     * Gets the member expr node.
     *
     * @return the member expr node
     */
    public AstNode getMemberExprNode() {
        return memberExprNode;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.Scope#toSource(int)
     */
    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(depth));
        sb.append("function");
        if (functionName != null) {
            sb.append(" ");
            sb.append(functionName.toSource(0));
        }
        if (params == null) {
            sb.append("() ");
        } else {
            sb.append("(");
            printList(params, sb);
            sb.append(") ");
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
        if (functionType == FUNCTION_STATEMENT) {
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Visits this node, the function name node if supplied,
     * the parameters, and the body.  If there is a member-expr node,
     * it is visited last.
     *
     * @param v the v
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
