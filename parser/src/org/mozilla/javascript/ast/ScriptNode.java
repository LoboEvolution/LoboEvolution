/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mozilla.javascript.Node;
import org.mozilla.javascript.Token;


/**
 * Base type for {@link AstRoot} and {@link FunctionNode} nodes, which need to
 * collect much of the same information.
 */
public class ScriptNode extends Scope {

    /** The encoded source start. */
    private int encodedSourceStart = -1;
    
    /** The encoded source end. */
    private int encodedSourceEnd = -1;
    
    /** The source name. */
    private String sourceName;
    
    /** The encoded source. */
    private String encodedSource;
    
    /** The end lineno. */
    private int endLineno = -1;

    /** The functions. */
    private List<FunctionNode> functions;
    
    /** The regexps. */
    private List<RegExpLiteral> regexps;
    
    /** The empty list. */
    private List<FunctionNode> EMPTY_LIST = Collections.emptyList();

    /** The symbols. */
    private List<Symbol> symbols = new ArrayList<Symbol>(4);
    
    /** The param count. */
    private int paramCount = 0;
    
    /** The variable names. */
    private String[] variableNames;
    
    /** The is consts. */
    private boolean[] isConsts;

    /** The compiler data. */
    private Object compilerData;
    
    /** The temp number. */
    private int tempNumber = 0;

    {
        // during parsing, a ScriptNode or FunctionNode's top scope is itself
        this.top = this;
        this.type = Token.SCRIPT;
    }

    /**
     * Instantiates a new script node.
     */
    public ScriptNode() {
    }

    /**
     * Instantiates a new script node.
     *
     * @param pos the pos
     */
    public ScriptNode(int pos) {
        super(pos);
    }

    /**
     * Returns the URI, path or descriptive text indicating the origin
     * of this script's source code.
     *
     * @return the source name
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * Sets the URI, path or descriptive text indicating the origin
     * of this script's source code.
     *
     * @param sourceName the new source name
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    /**
     * Returns the start offset of the encoded source.
     * Only valid if {@link #getEncodedSource} returns non-{@code null}.
     *
     * @return the encoded source start
     */
    public int getEncodedSourceStart() {
        return encodedSourceStart;
    }

    /**
     * Used by code generator.
     *
     * @param start the new encoded source start
     * @see #getEncodedSource
     */
    public void setEncodedSourceStart(int start) {
        this.encodedSourceStart = start;
    }

    /**
     * Returns the end offset of the encoded source.
     * Only valid if {@link #getEncodedSource} returns non-{@code null}.
     *
     * @return the encoded source end
     */
    public int getEncodedSourceEnd() {
        return encodedSourceEnd;
    }

    /**
     * Used by code generator.
     *
     * @param end the new encoded source end
     * @see #getEncodedSource
     */
    public void setEncodedSourceEnd(int end) {
        this.encodedSourceEnd = end;
    }

    /**
     * Used by code generator.
     *
     * @param start the start
     * @param end the end
     * @see #getEncodedSource
     */
    public void setEncodedSourceBounds(int start, int end) {
        this.encodedSourceStart = start;
        this.encodedSourceEnd = end;
    }

    /**
     * Used by the code generator.
     *
     * @param encodedSource the new encoded source
     * @see #getEncodedSource
     */
    public void setEncodedSource(String encodedSource) {
        this.encodedSource = encodedSource;
    }

    /**
     * Returns a canonical version of the source for this script or function,
     * for use in implementing the {@code Object.toSource} method of
     * JavaScript objects.  This source encoding is only recorded during code
     * generation.  It must be passed back to
     * {@link org.mozilla.javascript.Decompiler#decompile} to construct the
     * human-readable source string.<p>
     *
     * Given a parsed AST, you can always convert it to source code using the
     * {@link AstNode#toSource} method, although it's not guaranteed to produce
     * exactly the same results as {@code Object.toSource} with respect to
     * formatting, parenthesization and other details.
     *
     * @return the encoded source, or {@code null} if it was not recorded.
     */
    public String getEncodedSource() {
        return encodedSource;
    }

    /**
     * Gets the base lineno.
     *
     * @return the base lineno
     */
    public int getBaseLineno() {
        return lineno;
    }

    /**
     * Sets base (starting) line number for this script or function.
     * This is a one-time operation, and throws an exception if the
     * line number has already been set.
     *
     * @param lineno the new base lineno
     */
    public void setBaseLineno(int lineno) {
        if (lineno < 0 || this.lineno >= 0) codeBug();
        this.lineno = lineno;
    }

    /**
     * Gets the end lineno.
     *
     * @return the end lineno
     */
    public int getEndLineno() {
        return endLineno;
    }

    /**
     * Sets the end lineno.
     *
     * @param lineno the new end lineno
     */
    public void setEndLineno(int lineno) {
        // One time action
        if (lineno < 0 || endLineno >= 0) codeBug();
        endLineno = lineno;
    }

    /**
     * Gets the function count.
     *
     * @return the function count
     */
    public int getFunctionCount() {
        return functions == null ? 0 : functions.size();
    }

    /**
     * Gets the function node.
     *
     * @param i the i
     * @return the function node
     */
    public FunctionNode getFunctionNode(int i) {
        return functions.get(i);
    }

    /**
     * Gets the functions.
     *
     * @return the functions
     */
    public List<FunctionNode> getFunctions() {
        return functions == null ? EMPTY_LIST : functions;
    }

    /**
     * Adds a {@link FunctionNode} to the functions table for codegen.
     * Does not set the parent of the node.
     *
     * @param fnNode the fn node
     * @return the index of the function within its parent
     */
    public int addFunction(FunctionNode fnNode) {
        if (fnNode == null) codeBug();
        if (functions == null)
            functions = new ArrayList<FunctionNode>();
        functions.add(fnNode);
        return functions.size() - 1;
    }

    /**
     * Gets the regexp count.
     *
     * @return the regexp count
     */
    public int getRegexpCount() {
        return regexps == null ? 0 : regexps.size();
    }

    /**
     * Gets the regexp string.
     *
     * @param index the index
     * @return the regexp string
     */
    public String getRegexpString(int index) {
        return regexps.get(index).getValue();
    }

    /**
     * Gets the regexp flags.
     *
     * @param index the index
     * @return the regexp flags
     */
    public String getRegexpFlags(int index) {
        return regexps.get(index).getFlags();
    }

    /**
     * Called by IRFactory to add a RegExp to the regexp table.
     *
     * @param re the re
     */
    public void addRegExp(RegExpLiteral re) {
        if (re == null) codeBug();
        if (regexps == null)
            regexps = new ArrayList<RegExpLiteral>();
        regexps.add(re);
        re.putIntProp(REGEXP_PROP, regexps.size() - 1);
    }

    /**
     * Gets the index for name node.
     *
     * @param nameNode the name node
     * @return the index for name node
     */
    public int getIndexForNameNode(Node nameNode) {
        if (variableNames == null) codeBug();
        Scope node = nameNode.getScope();
        Symbol symbol = node == null
            ? null
            : node.getSymbol(((Name)nameNode).getIdentifier());
        return (symbol == null) ? -1 : symbol.getIndex();
    }

    /**
     * Gets the param or var name.
     *
     * @param index the index
     * @return the param or var name
     */
    public String getParamOrVarName(int index) {
        if (variableNames == null) codeBug();
        return variableNames[index];
    }

    /**
     * Gets the param count.
     *
     * @return the param count
     */
    public int getParamCount() {
        return paramCount;
    }

    /**
     * Gets the param and var count.
     *
     * @return the param and var count
     */
    public int getParamAndVarCount() {
        if (variableNames == null) codeBug();
        return symbols.size();
    }

    /**
     * Gets the param and var names.
     *
     * @return the param and var names
     */
    public String[] getParamAndVarNames() {
        if (variableNames == null) codeBug();
        return variableNames;
    }

    /**
     * Gets the param and var const.
     *
     * @return the param and var const
     */
    public boolean[] getParamAndVarConst() {
        if (variableNames == null) codeBug();
        return isConsts;
    }

    /**
     * Adds the symbol.
     *
     * @param symbol the symbol
     */
    void addSymbol(Symbol symbol) {
        if (variableNames != null) codeBug();
        if (symbol.getDeclType() == Token.LP) {
            paramCount++;
        }
        symbols.add(symbol);
    }

    /**
     * Gets the symbols.
     *
     * @return the symbols
     */
    public List<Symbol> getSymbols() {
        return symbols;
    }

    /**
     * Sets the symbols.
     *
     * @param symbols the new symbols
     */
    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    /**
     * Assign every symbol a unique integer index. Generate arrays of variable
     * names and constness that can be indexed by those indices.
     *
     * @param flattenAllTables if true, flatten all symbol tables,
     * included nested block scope symbol tables. If false, just flatten the
     * script's or function's symbol table.
     */
    public void flattenSymbolTable(boolean flattenAllTables) {
        if (!flattenAllTables) {
            List<Symbol> newSymbols = new ArrayList<Symbol>();
            if (this.symbolTable != null) {
                // Just replace "symbols" with the symbols in this object's
                // symbol table. Can't just work from symbolTable map since
                // we need to retain duplicate parameters.
                for (int i = 0; i < symbols.size(); i++) {
                    Symbol symbol = symbols.get(i);
                    if (symbol.getContainingTable() == this) {
                        newSymbols.add(symbol);
                    }
                }
            }
            symbols = newSymbols;
        }
        variableNames = new String[symbols.size()];
        isConsts = new boolean[symbols.size()];
        for (int i = 0; i < symbols.size(); i++) {
            Symbol symbol = symbols.get(i);
            variableNames[i] = symbol.getName();
            isConsts[i] = symbol.getDeclType() == Token.CONST;
            symbol.setIndex(i);
        }
    }

    /**
     * Gets the compiler data.
     *
     * @return the compiler data
     */
    public Object getCompilerData() {
        return compilerData;
    }

    /**
     * Sets the compiler data.
     *
     * @param data the new compiler data
     */
    public void setCompilerData(Object data) {
        assertNotNull(data);
        // Can only call once
        if (compilerData != null)
            throw new IllegalStateException();
        compilerData = data;
    }

    /**
     * Gets the next temp name.
     *
     * @return the next temp name
     */
    public String getNextTempName() {
        return "$" + tempNumber++;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ast.Scope#visit(org.mozilla.javascript.ast.NodeVisitor)
     */
    @Override
    public void visit(NodeVisitor v) {
        if (v.visit(this)) {
            for (Node kid : this) {
                ((AstNode)kid).visit(v);
            }
        }
    }
}
