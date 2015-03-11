/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.util.Set;

import org.mozilla.javascript.ast.ErrorCollector;


/**
 * The Class CompilerEnvirons.
 */
public class CompilerEnvirons
{
    
    /**
     * Instantiates a new compiler environs.
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
     * Inits the from context.
     *
     * @param cx the cx
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
     * Gets the error reporter.
     *
     * @return the error reporter
     */
    public final ErrorReporter getErrorReporter()
    {
        return errorReporter;
    }

    /**
     * Sets the error reporter.
     *
     * @param errorReporter the new error reporter
     */
    public void setErrorReporter(ErrorReporter errorReporter)
    {
        if (errorReporter == null) throw new IllegalArgumentException();
        this.errorReporter = errorReporter;
    }

    /**
     * Gets the language version.
     *
     * @return the language version
     */
    public final int getLanguageVersion()
    {
        return languageVersion;
    }

    /**
     * Sets the language version.
     *
     * @param languageVersion the new language version
     */
    public void setLanguageVersion(int languageVersion)
    {
        Context.checkLanguageVersion(languageVersion);
        this.languageVersion = languageVersion;
    }

    /**
     * Checks if is generate debug info.
     *
     * @return true, if is generate debug info
     */
    public final boolean isGenerateDebugInfo()
    {
        return generateDebugInfo;
    }

    /**
     * Sets the generate debug info.
     *
     * @param flag the new generate debug info
     */
    public void setGenerateDebugInfo(boolean flag)
    {
        this.generateDebugInfo = flag;
    }

    /**
     * Checks if is reserved keyword as identifier.
     *
     * @return true, if is reserved keyword as identifier
     */
    public final boolean isReservedKeywordAsIdentifier()
    {
        return reservedKeywordAsIdentifier;
    }

    /**
     * Sets the reserved keyword as identifier.
     *
     * @param flag the new reserved keyword as identifier
     */
    public void setReservedKeywordAsIdentifier(boolean flag)
    {
        reservedKeywordAsIdentifier = flag;
    }

    /**
     * Extension to ECMA: if 'function &lt;name&gt;' is not followed
     * by '(', assume &lt;name&gt; starts a {@code memberExpr}.
     *
     * @return true, if is allow member expr as function name
     */
    public final boolean isAllowMemberExprAsFunctionName()
    {
        return allowMemberExprAsFunctionName;
    }

    /**
     * Sets the allow member expr as function name.
     *
     * @param flag the new allow member expr as function name
     */
    public void setAllowMemberExprAsFunctionName(boolean flag)
    {
        allowMemberExprAsFunctionName = flag;
    }

    /**
     * Checks if is xml available.
     *
     * @return true, if is xml available
     */
    public final boolean isXmlAvailable()
    {
        return xmlAvailable;
    }

    /**
     * Sets the xml available.
     *
     * @param flag the new xml available
     */
    public void setXmlAvailable(boolean flag)
    {
        xmlAvailable = flag;
    }

    /**
     * Gets the optimization level.
     *
     * @return the optimization level
     */
    public final int getOptimizationLevel()
    {
        return optimizationLevel;
    }

    /**
     * Sets the optimization level.
     *
     * @param level the new optimization level
     */
    public void setOptimizationLevel(int level)
    {
        Context.checkOptimizationLevel(level);
        this.optimizationLevel = level;
    }

    /**
     * Checks if is generating source.
     *
     * @return true, if is generating source
     */
    public final boolean isGeneratingSource()
    {
        return generatingSource;
    }

    /**
     * Gets the warn trailing comma.
     *
     * @return the warn trailing comma
     */
    public boolean getWarnTrailingComma() {
        return warnTrailingComma;
    }

    /**
     * Sets the warn trailing comma.
     *
     * @param warn the new warn trailing comma
     */
    public void setWarnTrailingComma(boolean warn) {
        warnTrailingComma = warn;
    }

    /**
     * Checks if is strict mode.
     *
     * @return true, if is strict mode
     */
    public final boolean isStrictMode()
    {
        return strictMode;
    }

    /**
     * Sets the strict mode.
     *
     * @param strict the new strict mode
     */
    public void setStrictMode(boolean strict)
    {
        strictMode = strict;
    }

    /**
     * Report warning as error.
     *
     * @return true, if successful
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
     * @param generatingSource the new generating source
     */
    public void setGeneratingSource(boolean generatingSource)
    {
        this.generatingSource = generatingSource;
    }

    /**
     * Checks if is generate observer count.
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
     * @param generateObserverCount if true, generated code will contain
     * calls to accumulate an estimate of the instructions executed.
     */
    public void setGenerateObserverCount(boolean generateObserverCount) {
        this.generateObserverCount = generateObserverCount;
    }

    /**
     * Checks if is recording comments.
     *
     * @return true, if is recording comments
     */
    public boolean isRecordingComments() {
        return recordingComments;
    }

    /**
     * Sets the recording comments.
     *
     * @param record the new recording comments
     */
    public void setRecordingComments(boolean record) {
        recordingComments = record;
    }

    /**
     * Checks if is recording local js doc comments.
     *
     * @return true, if is recording local js doc comments
     */
    public boolean isRecordingLocalJsDocComments() {
        return recordingLocalJsDocComments;
    }

    /**
     * Sets the recording local js doc comments.
     *
     * @param record the new recording local js doc comments
     */
    public void setRecordingLocalJsDocComments(boolean record) {
        recordingLocalJsDocComments = record;
    }

    /**
     * Turn on or off full error recovery.  In this mode, parse errors do not
     * throw an exception, and the parser attempts to build a full syntax tree
     * from the input.  Useful for IDEs and other frontends.
     *
     * @param recover the new recover from errors
     */
    public void setRecoverFromErrors(boolean recover) {
        recoverFromErrors = recover;
    }

    /**
     * Recover from errors.
     *
     * @return true, if successful
     */
    public boolean recoverFromErrors() {
        return recoverFromErrors;
    }

    /**
     * Puts the parser in "IDE" mode.  This enables some slightly more expensive
     * computations, such as figuring out helpful error bounds.
     *
     * @param ide the new ide mode
     */
    public void setIdeMode(boolean ide) {
        ideMode = ide;
    }

    /**
     * Checks if is ide mode.
     *
     * @return true, if is ide mode
     */
    public boolean isIdeMode() {
        return ideMode;
    }

    /**
     * Gets the activation names.
     *
     * @return the activation names
     */
    public Set<String> getActivationNames() {
        return activationNames;
    }

    /**
     * Sets the activation names.
     *
     * @param activationNames the new activation names
     */
    public void setActivationNames(Set<String> activationNames) {
        this.activationNames = activationNames;
    }

    /**
     * Mozilla sources use the C preprocessor.
     *
     * @param allow the new allow sharp comments
     */
    public void setAllowSharpComments(boolean allow) {
        allowSharpComments = allow;
    }

    /**
     * Gets the allow sharp comments.
     *
     * @return the allow sharp comments
     */
    public boolean getAllowSharpComments() {
        return allowSharpComments;
    }

    /**
     * Returns a {@code CompilerEnvirons} suitable for using Rhino
     * in an IDE environment.  Most features are enabled by default.
     * The {@link ErrorReporter} is set to an {@link ErrorCollector}.
     *
     * @return the compiler environs
     */
    public static CompilerEnvirons ideEnvirons() {
        CompilerEnvirons env = new CompilerEnvirons();
        env.setRecoverFromErrors(true);
        env.setRecordingComments(true);
        env.setStrictMode(true);
        env.setWarnTrailingComma(true);
        env.setLanguageVersion(170);
        env.setReservedKeywordAsIdentifier(true);
        env.setIdeMode(true);
        env.setErrorReporter(new ErrorCollector());
        return env;
    }

    /** The error reporter. */
    private ErrorReporter errorReporter;

    /** The language version. */
    private int languageVersion;
    
    /** The generate debug info. */
    private boolean generateDebugInfo;
    
    /** The reserved keyword as identifier. */
    private boolean reservedKeywordAsIdentifier;
    
    /** The allow member expr as function name. */
    private boolean allowMemberExprAsFunctionName;
    
    /** The xml available. */
    private boolean xmlAvailable;
    
    /** The optimization level. */
    private int optimizationLevel;
    
    /** The generating source. */
    private boolean generatingSource;
    
    /** The strict mode. */
    private boolean strictMode;
    
    /** The warning as error. */
    private boolean warningAsError;
    
    /** The generate observer count. */
    private boolean generateObserverCount;
    
    /** The recording comments. */
    private boolean recordingComments;
    
    /** The recording local js doc comments. */
    private boolean recordingLocalJsDocComments;
    
    /** The recover from errors. */
    private boolean recoverFromErrors;
    
    /** The warn trailing comma. */
    private boolean warnTrailingComma;
    
    /** The ide mode. */
    private boolean ideMode;
    
    /** The allow sharp comments. */
    private boolean allowSharpComments;
    
    /** The activation names. */
    Set<String> activationNames;
}
