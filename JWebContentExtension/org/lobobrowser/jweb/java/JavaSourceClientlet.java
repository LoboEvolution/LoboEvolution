/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.jweb.java;

import java.net.URL;
import java.util.List;

import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.jweb.common.BaseCompiledClientlet;

/**
 * The Class JavaSourceClientlet.
 */
public class JavaSourceClientlet extends BaseCompiledClientlet {

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.jweb.common.BaseCompiledClientlet#compile(javax.tools.
     * JavaFileManager, javax.tools.DiagnosticListener, java.util.List)
     */
    @Override
    protected void compile(JavaFileManager fileManager,
            DiagnosticListener<? super JavaFileObject> diagnosticListener,
            List<JavaFileObject> compilationUnits) throws ClientletException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            Class expectedClass;
            try {
                // Need to load Java compiler like this. The standard way to
                // load
                // it only accepts loading it with the boot class loader.
                expectedClass = Class
                        .forName("com.sun.tools.javac.api.JavacTool");
            } catch (Exception err) {
                throw new ClientletException(
                        "Unable to load Java compiler class from its expected location.",
                        err);
            }
            try {
                compiler = (JavaCompiler) expectedClass.newInstance();
            } catch (Exception err) {
                throw new ClientletException(
                        "Unable to instantiate compiler class.", err);
            }
        }
        compiler.getTask(null, fileManager, diagnosticListener, null, null,
                compilationUnits).call();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.jweb.common.BaseCompiledClientlet#getExtraPlatformClassPath()
     */
    @Override
    protected URL[] getExtraPlatformClassPath() {
        // The standard boot classpath plus clientlets is sufficient.
        return new URL[0];
    }
}
