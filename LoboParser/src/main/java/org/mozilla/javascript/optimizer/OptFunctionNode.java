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
 * <p>OptFunctionNode class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public final class OptFunctionNode
{
    OptFunctionNode(FunctionNode fnode)
    {
        this.fnode = fnode;
        fnode.setCompilerData(this);
    }

    /**
     * <p>get.</p>
     *
     * @param scriptOrFn a {@link org.mozilla.javascript.ast.ScriptNode} object.
     * @param i a int.
     * @return a {@link org.mozilla.javascript.optimizer.OptFunctionNode} object.
     */
    public static OptFunctionNode get(ScriptNode scriptOrFn, int i)
    {
        FunctionNode fnode = scriptOrFn.getFunctionNode(i);
        return (OptFunctionNode)fnode.getCompilerData();
    }

    /**
     * <p>get.</p>
     *
     * @param scriptOrFn a {@link org.mozilla.javascript.ast.ScriptNode} object.
     * @return a {@link org.mozilla.javascript.optimizer.OptFunctionNode} object.
     */
    public static OptFunctionNode get(ScriptNode scriptOrFn)
    {
        return (OptFunctionNode)scriptOrFn.getCompilerData();
    }

    /**
     * <p>isTargetOfDirectCall.</p>
     *
     * @return a boolean.
     */
    public boolean isTargetOfDirectCall()
    {
        return directTargetIndex >= 0;
    }

    /**
     * <p>Getter for the field directTargetIndex.</p>
     *
     * @return a int.
     */
    public int getDirectTargetIndex()
    {
        return directTargetIndex;
    }

    void setDirectTargetIndex(int directTargetIndex)
    {
        // One time action
        if (directTargetIndex < 0 || this.directTargetIndex >= 0)
            Kit.codeBug();
        this.directTargetIndex = directTargetIndex;
    }

    void setParameterNumberContext(boolean b)
    {
        itsParameterNumberContext = b;
    }

    /**
     * <p>getParameterNumberContext.</p>
     *
     * @return a boolean.
     */
    public boolean getParameterNumberContext()
    {
        return itsParameterNumberContext;
    }

    /**
     * <p>getVarCount.</p>
     *
     * @return a int.
     */
    public int getVarCount()
    {
        return fnode.getParamAndVarCount();
    }

    /**
     * <p>isParameter.</p>
     *
     * @param varIndex a int.
     * @return a boolean.
     */
    public boolean isParameter(int varIndex)
    {
        return varIndex < fnode.getParamCount();
    }

    /**
     * <p>isNumberVar.</p>
     *
     * @param varIndex a int.
     * @return a boolean.
     */
    public boolean isNumberVar(int varIndex)
    {
        varIndex -= fnode.getParamCount();
        if (varIndex >= 0 && numberVarFlags != null) {
            return numberVarFlags[varIndex];
        }
        return false;
    }

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
     * <p>getVarIndex.</p>
     *
     * @param n a {@link org.mozilla.javascript.Node} object.
     * @return a int.
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

    public final FunctionNode fnode;

    private boolean[] numberVarFlags;
    private int directTargetIndex = -1;
    private boolean itsParameterNumberContext;
    boolean itsContainsCalls0;
    boolean itsContainsCalls1;
}
