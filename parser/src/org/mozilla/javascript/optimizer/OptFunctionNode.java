/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */


package org.mozilla.javascript.optimizer;

import org.mozilla.javascript.Kit;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.ScriptNode;


/**
 * The Class OptFunctionNode.
 */
public final class OptFunctionNode
{
    
    /**
     * Instantiates a new opt function node.
     *
     * @param fnode the fnode
     */
    OptFunctionNode(FunctionNode fnode)
    {
        this.fnode = fnode;
        fnode.setCompilerData(this);
    }

    /**
     * Gets the.
     *
     * @param scriptOrFn the script or fn
     * @param i the i
     * @return the opt function node
     */
    public static OptFunctionNode get(ScriptNode scriptOrFn, int i)
    {
        FunctionNode fnode = scriptOrFn.getFunctionNode(i);
        return (OptFunctionNode)fnode.getCompilerData();
    }

    /**
     * Gets the.
     *
     * @param scriptOrFn the script or fn
     * @return the opt function node
     */
    public static OptFunctionNode get(ScriptNode scriptOrFn)
    {
        return (OptFunctionNode)scriptOrFn.getCompilerData();
    }

    /**
     * Checks if is target of direct call.
     *
     * @return true, if is target of direct call
     */
    public boolean isTargetOfDirectCall()
    {
        return directTargetIndex >= 0;
    }

    /**
     * Gets the direct target index.
     *
     * @return the direct target index
     */
    public int getDirectTargetIndex()
    {
        return directTargetIndex;
    }

    /**
     * Sets the direct target index.
     *
     * @param directTargetIndex the new direct target index
     */
    void setDirectTargetIndex(int directTargetIndex)
    {
        // One time action
        if (directTargetIndex < 0 || this.directTargetIndex >= 0)
            Kit.codeBug();
        this.directTargetIndex = directTargetIndex;
    }

    /**
     * Sets the parameter number context.
     *
     * @param b the new parameter number context
     */
    void setParameterNumberContext(boolean b)
    {
        itsParameterNumberContext = b;
    }

    /**
     * Gets the parameter number context.
     *
     * @return the parameter number context
     */
    public boolean getParameterNumberContext()
    {
        return itsParameterNumberContext;
    }

    /**
     * Gets the var count.
     *
     * @return the var count
     */
    public int getVarCount()
    {
        return fnode.getParamAndVarCount();
    }

    /**
     * Checks if is parameter.
     *
     * @param varIndex the var index
     * @return true, if is parameter
     */
    public boolean isParameter(int varIndex)
    {
        return varIndex < fnode.getParamCount();
    }

    /**
     * Checks if is number var.
     *
     * @param varIndex the var index
     * @return true, if is number var
     */
    public boolean isNumberVar(int varIndex)
    {
        varIndex -= fnode.getParamCount();
        if (varIndex >= 0 && numberVarFlags != null) {
            return numberVarFlags[varIndex];
        }
        return false;
    }

    /**
     * Sets the checks if is number var.
     *
     * @param varIndex the new checks if is number var
     */
    void setIsNumberVar(int varIndex)
    {
        varIndex -= fnode.getParamCount();
        // Can only be used with non-parameters
        if (varIndex < 0) Kit.codeBug();
        if (numberVarFlags == null) {
            int size = fnode.getParamAndVarCount() - fnode.getParamCount();
            numberVarFlags = new boolean[size];
        }
        numberVarFlags[varIndex] = true;
    }

    /**
     * Gets the var index.
     *
     * @param n the n
     * @return the var index
     */
    public int getVarIndex(Node n)
    {
        int index = n.getIntProp(Node.VARIABLE_PROP, -1);
        if (index == -1) {
            Node node;
            int type = n.getType();
            if (type == Token.GETVAR) {
                node = n;
            } else if (type == Token.SETVAR ||
                       type == Token.SETCONSTVAR) {
                node = n.getFirstChild();
            } else {
                throw Kit.codeBug();
            }
            index = fnode.getIndexForNameNode(node);
            if (index < 0) throw Kit.codeBug();
            n.putIntProp(Node.VARIABLE_PROP, index);
        }
        return index;
    }

    /** The fnode. */
    public final FunctionNode fnode;

    /** The number var flags. */
    private boolean[] numberVarFlags;
    
    /** The direct target index. */
    private int directTargetIndex = -1;
    
    /** The its parameter number context. */
    private boolean itsParameterNumberContext;
    
    /** The its contains calls0. */
    boolean itsContainsCalls0;
    
    /** The its contains calls1. */
    boolean itsContainsCalls1;
}
