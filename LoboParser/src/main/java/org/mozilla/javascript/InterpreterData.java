/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.io.Serializable;
import java.util.Arrays;

import org.mozilla.javascript.debug.DebuggableScript;

final class InterpreterData implements Serializable, DebuggableScript
{
    private static final long serialVersionUID = 5067677351589230234L;

    static final int INITIAL_MAX_ICODE_LENGTH = 1024;
    static final int INITIAL_STRINGTABLE_SIZE = 64;
    static final int INITIAL_NUMBERTABLE_SIZE = 64;

    InterpreterData(int languageVersion, String sourceFile,
                    String encodedSource, boolean isStrict)
    {
        this.languageVersion = languageVersion;
        this.itsSourceFile = sourceFile;
        this.encodedSource = encodedSource;
        this.isStrict = isStrict;
        init();
    }

    InterpreterData(InterpreterData parent)
    {
        this.parentData = parent;
        this.languageVersion = parent.languageVersion;
        this.itsSourceFile = parent.itsSourceFile;
        this.encodedSource = parent.encodedSource;
        this.isStrict = parent.isStrict;
        init();
    }

    private void init()
    {
        itsICode = new byte[INITIAL_MAX_ICODE_LENGTH];
        itsStringTable = new String[INITIAL_STRINGTABLE_SIZE];
    }

    String itsName;
    String itsSourceFile;
    boolean itsNeedsActivation;
    int itsFunctionType;

    String[] itsStringTable;
    double[] itsDoubleTable;
    InterpreterData[] itsNestedFunctions;
    Object[] itsRegExpLiterals;

    byte[] itsICode;

    int[] itsExceptionTable;

    int itsMaxVars;
    int itsMaxLocals;
    int itsMaxStack;
    int itsMaxFrameArray;

    // see comments in NativeFuncion for definition of argNames and argCount
    String[] argNames;
    boolean[] argIsConst;
    int argCount;

    int itsMaxCalleeArgs;

    String encodedSource;
    int encodedSourceStart;
    int encodedSourceEnd;

    int languageVersion;

    boolean isStrict;
    boolean topLevel;
    boolean isES6Generator;

    Object[] literalIds;

    UintMap longJumps;

    int firstLinePC = -1; // PC for the first LINE icode

    InterpreterData parentData;

    boolean evalScriptFlag; // true if script corresponds to eval() code

    private int icodeHashCode = 0;

    /** true if the function has been declared like "var foo = function() {...}" */
    boolean declaredAsVar;

    /** true if the function has been declared like "!function() {}". */
    boolean declaredAsFunctionExpression;

    /** {@inheritDoc} */
    @Override
    public boolean isTopLevel()
    {
        return topLevel;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isFunction()
    {
        return itsFunctionType != 0;
    }

    /** {@inheritDoc} */
    @Override
    public String getFunctionName()
    {
        return itsName;
    }

    /** {@inheritDoc} */
    @Override
    public int getParamCount()
    {
        return argCount;
    }

    /** {@inheritDoc} */
    @Override
    public int getParamAndVarCount()
    {
        return argNames.length;
    }

    /** {@inheritDoc} */
    @Override
    public String getParamOrVarName(int index)
    {
        return argNames[index];
    }

    /**
     * <p>getParamOrVarConst.</p>
     *
     * @param index a int.
     * @return a boolean.
     */
    public boolean getParamOrVarConst(int index)
    {
        return argIsConst[index];
    }

    /** {@inheritDoc} */
    @Override
    public String getSourceName()
    {
        return itsSourceFile;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isGeneratedScript()
    {
        return ScriptRuntime.isGeneratedScript(itsSourceFile);
    }

    /** {@inheritDoc} */
    @Override
    public int[] getLineNumbers()
    {
        return Interpreter.getLineNumbers(this);
    }

    /** {@inheritDoc} */
    @Override
    public int getFunctionCount()
    {
        return (itsNestedFunctions == null) ? 0 : itsNestedFunctions.length;
    }

    /** {@inheritDoc} */
    @Override
    public DebuggableScript getFunction(int index)
    {
        return itsNestedFunctions[index];
    }

    /** {@inheritDoc} */
    @Override
    public DebuggableScript getParent()
    {
         return parentData;
    }

    /**
     * <p>icodeHashCode.</p>
     *
     * @return a int.
     */
    public int icodeHashCode()
    {
        int h = icodeHashCode;
        if (h == 0) {
            icodeHashCode = h = Arrays.hashCode(itsICode);
        }
        return h;
    }
}
