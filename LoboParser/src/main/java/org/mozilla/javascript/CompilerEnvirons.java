/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.util.Set;

import org.mozilla.javascript.ast.ErrorCollector;

/**
 * <p>CompilerEnvirons class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class CompilerEnvirons
{
    /**
     * <p>Constructor for CompilerEnvirons.</p>
     */
    public CompilerEnvirons()
    {
        errorReporter = DefaultErrorReporter.instance;
        languageVersion = Context.VERSION_DEFAULT;
        generateDebugInfo = true;
        reservedKeywordAsIdentifier = true;
        allowMemberExprAsFunctionName = false;
        xmlAvailable = true;
        optimizationLevel = 0;
        generatingSource = true;
        strictMode = false;
        warningAsError = false;
        generateObserverCount = false;
        allowSharpComments = false;
    }

    /**
     * <p>initFromContext.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     */
    public void initFromContext(Context cx)
    {
        setErrorReporter(cx.getErrorReporter());
        languageVersion = cx.getLanguageVersion();
        generateDebugInfo = (!cx.isGeneratingDebugChanged()
                             || cx.isGeneratingDebug());
        reservedKeywordAsIdentifier
            = cx.hasFeature(Context.FEATURE_RESERVED_KEYWORD_AS_IDENTIFIER);
        allowMemberExprAsFunctionName
            = cx.hasFeature(Context.FEATURE_MEMBER_EXPR_AS_FUNCTION_NAME);
        strictMode
            = cx.hasFeature(Context.FEATURE_STRICT_MODE);
        warningAsError = cx.hasFeature(Context.FEATURE_WARNING_AS_ERROR);
        xmlAvailable
            = cx.hasFeature(Context.FEATURE_E4X);

        optimizationLevel = cx.getOptimizationLevel();

        generatingSource = cx.isGeneratingSource();
        activationNames = cx.activationNames;

        // Observer code generation in compiled code :
        generateObserverCount = cx.generateObserverCount;
    }

    /**
     * <p>Getter for the field errorReporter.</p>
     *
     * @return a {@link org.mozilla.javascript.ErrorReporter} object.
     */
    public final ErrorReporter getErrorReporter()
    {
        return errorReporter;
    }

    /**
     * <p>Setter for the field errorReporter.</p>
     *
     * @param errorReporter a {@link org.mozilla.javascript.ErrorReporter} object.
     */
    public void setErrorReporter(ErrorReporter errorReporter)
    {
        if (errorReporter == null) throw new IllegalArgumentException();
        this.errorReporter = errorReporter;
    }

    /**
     * <p>Getter for the field languageVersion.</p>
     *
     * @return a int.
     */
    public final int getLanguageVersion()
    {
        return languageVersion;
    }

    /**
     * <p>Setter for the field languageVersion.</p>
     *
     * @param languageVersion a int.
     */
    public void setLanguageVersion(int languageVersion)
    {
        Context.checkLanguageVersion(languageVersion);
        this.languageVersion = languageVersion;
    }

    /**
     * <p>isGenerateDebugInfo.</p>
     *
     * @return a boolean.
     */
    public final boolean isGenerateDebugInfo()
    {
        return generateDebugInfo;
    }

    /**
     * <p>Setter for the field generateDebugInfo.</p>
     *
     * @param flag a boolean.
     */
    public void setGenerateDebugInfo(boolean flag)
    {
        this.generateDebugInfo = flag;
    }

    /**
     * <p>isReservedKeywordAsIdentifier.</p>
     *
     * @return a boolean.
     */
    public final boolean isReservedKeywordAsIdentifier()
    {
        return reservedKeywordAsIdentifier;
    }

    /**
     * <p>Setter for the field reservedKeywordAsIdentifier.</p>
     *
     * @param flag a boolean.
     */
    public void setReservedKeywordAsIdentifier(boolean flag)
    {
        reservedKeywordAsIdentifier = flag;
    }

    /**
     * Extension to ECMA: if 'function &lt;name&gt;' is not followed
     * by '(', assume &lt;name&gt; starts a {@code memberExpr}
     *
     * @return a boolean.
     */
    public final boolean isAllowMemberExprAsFunctionName()
    {
        return allowMemberExprAsFunctionName;
    }

    /**
     * <p>Setter for the field allowMemberExprAsFunctionName.</p>
     *
     * @param flag a boolean.
     */
    public void setAllowMemberExprAsFunctionName(boolean flag)
    {
        allowMemberExprAsFunctionName = flag;
    }

    /**
     * <p>isXmlAvailable.</p>
     *
     * @return a boolean.
     */
    public final boolean isXmlAvailable()
    {
        return xmlAvailable;
    }

    /**
     * <p>Setter for the field xmlAvailable.</p>
     *
     * @param flag a boolean.
     */
    public void setXmlAvailable(boolean flag)
    {
        xmlAvailable = flag;
    }

    /**
     * <p>Getter for the field optimizationLevel.</p>
     *
     * @return a int.
     */
    public final int getOptimizationLevel()
    {
        return optimizationLevel;
    }

    /**
     * <p>Setter for the field optimizationLevel.</p>
     *
     * @param level a int.
     */
    public void setOptimizationLevel(int level)
    {
        Context.checkOptimizationLevel(level);
        this.optimizationLevel = level;
    }

    /**
     * <p>isGeneratingSource.</p>
     *
     * @return a boolean.
     */
    public final boolean isGeneratingSource()
    {
        return generatingSource;
    }

    /**
     * <p>Getter for the field warnTrailingComma.</p>
     *
     * @return a boolean.
     */
    public boolean getWarnTrailingComma() {
        return warnTrailingComma;
    }

    /**
     * <p>Setter for the field warnTrailingComma.</p>
     *
     * @param warn a boolean.
     */
    public void setWarnTrailingComma(boolean warn) {
        warnTrailingComma = warn;
    }

    /**
     * <p>isStrictMode.</p>
     *
     * @return a boolean.
     */
    public final boolean isStrictMode()
    {
        return strictMode;
    }

    /**
     * <p>Setter for the field strictMode.</p>
     *
     * @param strict a boolean.
     */
    public void setStrictMode(boolean strict)
    {
        strictMode = strict;
    }

    /**
     * <p>reportWarningAsError.</p>
     *
     * @return a boolean.
     */
    public final boolean reportWarningAsError()
    {
        return warningAsError;
    }

    /**
     * Specify whether or not source information should be generated.
     * <p>
     * Without source information, evaluating the "toString" method
     * on JavaScript functions produces only "[native code]" for
     * the body of the function.
     * Note that code generated without source is not fully ECMA
     * conformant.
     *
     * @param generatingSource a boolean.
     */
    public void setGeneratingSource(boolean generatingSource)
    {
        this.generatingSource = generatingSource;
    }

    /**
     * <p>isGenerateObserverCount.</p>
     *
     * @return true iff code will be generated with callbacks to enable
     * instruction thresholds
     */
    public boolean isGenerateObserverCount() {
        return generateObserverCount;
    }

    /**
     * Turn on or off generation of code with callbacks to
     * track the count of executed instructions.
     * Currently only affects JVM byte code generation: this slows down the
     * generated code, but code generated without the callbacks will not
     * be counted toward instruction thresholds. Rhino's interpretive
     * mode does instruction counting without inserting callbacks, so
     * there is no requirement to compile code differently.
     *
     * @param generateObserverCount if true, generated code will contain
     * calls to accumulate an estimate of the instructions executed.
     */
    public void setGenerateObserverCount(boolean generateObserverCount) {
        this.generateObserverCount = generateObserverCount;
    }

    /**
     * <p>isRecordingComments.</p>
     *
     * @return a boolean.
     */
    public boolean isRecordingComments() {
        return recordingComments;
    }

    /**
     * <p>Setter for the field recordingComments.</p>
     *
     * @param record a boolean.
     */
    public void setRecordingComments(boolean record) {
        recordingComments = record;
    }

    /**
     * <p>isRecordingLocalJsDocComments.</p>
     *
     * @return a boolean.
     */
    public boolean isRecordingLocalJsDocComments() {
        return recordingLocalJsDocComments;
    }

    /**
     * <p>Setter for the field recordingLocalJsDocComments.</p>
     *
     * @param record a boolean.
     */
    public void setRecordingLocalJsDocComments(boolean record) {
        recordingLocalJsDocComments = record;
    }

    /**
     * Turn on or off full error recovery.  In this mode, parse errors do not
     * throw an exception, and the parser attempts to build a full syntax tree
     * from the input.  Useful for IDEs and other frontends.
     *
     * @param recover a boolean.
     */
    public void setRecoverFromErrors(boolean recover) {
        recoverFromErrors = recover;
    }

    /**
     * <p>recoverFromErrors.</p>
     *
     * @return a boolean.
     */
    public boolean recoverFromErrors() {
        return recoverFromErrors;
    }

    /**
     * Puts the parser in "IDE" mode.  This enables some slightly more expensive
     * computations, such as figuring out helpful error bounds.
     *
     * @param ide a boolean.
     */
    public void setIdeMode(boolean ide) {
        ideMode = ide;
    }

    /**
     * <p>isIdeMode.</p>
     *
     * @return a boolean.
     */
    public boolean isIdeMode() {
        return ideMode;
    }

    /**
     * <p>Getter for the field activationNames.</p>
     *
     * @return a {@link java.util.Set} object.
     */
    public Set<String> getActivationNames() {
        return activationNames;
    }

    /**
     * <p>Setter for the field activationNames.</p>
     *
     * @param activationNames a {@link java.util.Set} object.
     */
    public void setActivationNames(Set<String> activationNames) {
        this.activationNames = activationNames;
    }

    /**
     * Mozilla sources use the C preprocessor.
     *
     * @param allow a boolean.
     */
    public void setAllowSharpComments(boolean allow) {
        allowSharpComments = allow;
    }

    /**
     * <p>Getter for the field allowSharpComments.</p>
     *
     * @return a boolean.
     */
    public boolean getAllowSharpComments() {
        return allowSharpComments;
    }

    /**
     * Returns a {@code CompilerEnvirons} suitable for using Rhino
     * in an IDE environment.  Most features are enabled by default.
     * The {@link org.mozilla.javascript.ErrorReporter} is set to an {@link org.mozilla.javascript.ast.ErrorCollector}.
     *
     * @return a {@link org.mozilla.javascript.CompilerEnvirons} object.
     */
    public static CompilerEnvirons ideEnvirons() {
        CompilerEnvirons env = new CompilerEnvirons();
        env.setRecoverFromErrors(true);
        env.setRecordingComments(true);
        env.setStrictMode(true);
        env.setWarnTrailingComma(true);
        env.setLanguageVersion(Context.VERSION_1_7);
        env.setReservedKeywordAsIdentifier(true);
        env.setIdeMode(true);
        env.setErrorReporter(new ErrorCollector());
        return env;
    }

    private ErrorReporter errorReporter;

    private int languageVersion;
    private boolean generateDebugInfo;
    private boolean reservedKeywordAsIdentifier;
    private boolean allowMemberExprAsFunctionName;
    private boolean xmlAvailable;
    private int optimizationLevel;
    private boolean generatingSource;
    private boolean strictMode;
    private boolean warningAsError;
    private boolean generateObserverCount;
    private boolean recordingComments;
    private boolean recordingLocalJsDocComments;
    private boolean recoverFromErrors;
    private boolean warnTrailingComma;
    private boolean ideMode;
    private boolean allowSharpComments;
    Set<String> activationNames;
}
