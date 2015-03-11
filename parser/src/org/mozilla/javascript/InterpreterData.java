/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.io.Serializable;

import org.mozilla.javascript.debug.DebuggableScript;


/**
 * The Class InterpreterData.
 */
final class InterpreterData implements Serializable, DebuggableScript
{
    
    /** The Constant serialVersionUID. */
    static final long serialVersionUID = 5067677351589230234L;

    /** The Constant INITIAL_MAX_ICODE_LENGTH. */
    static final int INITIAL_MAX_ICODE_LENGTH = 1024;
    
    /** The Constant INITIAL_STRINGTABLE_SIZE. */
    static final int INITIAL_STRINGTABLE_SIZE = 64;
    
    /** The Constant INITIAL_NUMBERTABLE_SIZE. */
    static final int INITIAL_NUMBERTABLE_SIZE = 64;

    /**
     * Instantiates a new interpreter data.
     *
     * @param languageVersion the language version
     * @param sourceFile the source file
     * @param encodedSource the encoded source
     * @param isStrict the is strict
     */
    InterpreterData(int languageVersion, String sourceFile,
                    String encodedSource, boolean isStrict)
    {
        this.languageVersion = languageVersion;
        this.itsSourceFile = sourceFile;
        this.encodedSource = encodedSource;
        this.isStrict = isStrict;
        init();
    }

    /**
     * Instantiates a new interpreter data.
     *
     * @param parent the parent
     */
    InterpreterData(InterpreterData parent)
    {
        this.parentData = parent;
        this.languageVersion = parent.languageVersion;
        this.itsSourceFile = parent.itsSourceFile;
        this.encodedSource = parent.encodedSource;

        init();
    }

    /**
     * Inits the.
     */
    private void init()
    {
        itsICode = new byte[INITIAL_MAX_ICODE_LENGTH];
        itsStringTable = new String[INITIAL_STRINGTABLE_SIZE];
    }

    /** The its name. */
    String itsName;
    
    /** The its source file. */
    String itsSourceFile;
    
    /** The its needs activation. */
    boolean itsNeedsActivation;
    
    /** The its function type. */
    int itsFunctionType;

    /** The its string table. */
    String[] itsStringTable;
    
    /** The its double table. */
    double[] itsDoubleTable;
    
    /** The its nested functions. */
    InterpreterData[] itsNestedFunctions;
    
    /** The its reg exp literals. */
    Object[] itsRegExpLiterals;

    /** The its i code. */
    byte[] itsICode;

    /** The its exception table. */
    int[] itsExceptionTable;

    /** The its max vars. */
    int itsMaxVars;
    
    /** The its max locals. */
    int itsMaxLocals;
    
    /** The its max stack. */
    int itsMaxStack;
    
    /** The its max frame array. */
    int itsMaxFrameArray;

    // see comments in NativeFuncion for definition of argNames and argCount
    /** The arg names. */
    String[] argNames;
    
    /** The arg is const. */
    boolean[] argIsConst;
    
    /** The arg count. */
    int argCount;

    /** The its max callee args. */
    int itsMaxCalleeArgs;

    /** The encoded source. */
    String encodedSource;
    
    /** The encoded source start. */
    int encodedSourceStart;
    
    /** The encoded source end. */
    int encodedSourceEnd;

    /** The language version. */
    int languageVersion;

    /** The is strict. */
    boolean isStrict;
    
    /** The top level. */
    boolean topLevel;

    /** The literal ids. */
    Object[] literalIds;

    /** The long jumps. */
    UintMap longJumps;

    /** The first line pc. */
    int firstLinePC = -1; // PC for the first LINE icode

    /** The parent data. */
    InterpreterData parentData;

    /** The eval script flag. */
    boolean evalScriptFlag; // true if script corresponds to eval() code

    /* (non-Javadoc)
     * @see org.mozilla.javascript.debug.DebuggableScript#isTopLevel()
     */
    public boolean isTopLevel()
    {
        return topLevel;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.debug.DebuggableScript#isFunction()
     */
    public boolean isFunction()
    {
        return itsFunctionType != 0;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.debug.DebuggableScript#getFunctionName()
     */
    public String getFunctionName()
    {
        return itsName;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.debug.DebuggableScript#getParamCount()
     */
    public int getParamCount()
    {
        return argCount;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.debug.DebuggableScript#getParamAndVarCount()
     */
    public int getParamAndVarCount()
    {
        return argNames.length;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.debug.DebuggableScript#getParamOrVarName(int)
     */
    public String getParamOrVarName(int index)
    {
        return argNames[index];
    }

    /**
     * Gets the param or var const.
     *
     * @param index the index
     * @return the param or var const
     */
    public boolean getParamOrVarConst(int index)
    {
        return argIsConst[index];
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.debug.DebuggableScript#getSourceName()
     */
    public String getSourceName()
    {
        return itsSourceFile;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.debug.DebuggableScript#isGeneratedScript()
     */
    public boolean isGeneratedScript()
    {
        return ScriptRuntime.isGeneratedScript(itsSourceFile);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.debug.DebuggableScript#getLineNumbers()
     */
    public int[] getLineNumbers()
    {
        return Interpreter.getLineNumbers(this);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.debug.DebuggableScript#getFunctionCount()
     */
    public int getFunctionCount()
    {
        return (itsNestedFunctions == null) ? 0 : itsNestedFunctions.length;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.debug.DebuggableScript#getFunction(int)
     */
    public DebuggableScript getFunction(int index)
    {
        return itsNestedFunctions[index];
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.debug.DebuggableScript#getParent()
     */
    public DebuggableScript getParent()
    {
         return parentData;
    }
}
