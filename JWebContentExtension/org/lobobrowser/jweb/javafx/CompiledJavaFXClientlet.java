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
package org.lobobrowser.jweb.javafx;

import java.net.URL;
import java.util.List;

import javafx.ext.swing.SwingButton;
import javafx.io.http.HttpStatus;
import javafx.lang.FX;
import javafx.scene.Group;

import javax.tools.DiagnosticListener;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.jweb.common.BaseCompiledClientlet;

import com.sun.javafx.api.JavafxCompiler;
import com.sun.javafx.api.ToolProvider;
import com.sun.scenario.Settings;

/**
 * The Class CompiledJavaFXClientlet.
 */
public class CompiledJavaFXClientlet extends BaseCompiledClientlet {

    @Override
    protected void compile(JavaFileManager fileManager,
            DiagnosticListener<? super JavaFileObject> diagnosticListener,
            List<JavaFileObject> compilationUnits) throws ClientletException {

        JavafxCompiler compiler = ToolProvider.getJavafxCompiler();
        compiler.getTask(null, fileManager, diagnosticListener, null,
                compilationUnits).call();
    }

    @Override
    protected URL[] getExtraPlatformClassPath() {
        // Return the location of JavaFX runtime classes.
        return new URL[] {
                FX.class.getProtectionDomain().getCodeSource().getLocation(),
                SwingButton.class.getProtectionDomain().getCodeSource().getLocation(),
                Group.class.getProtectionDomain().getCodeSource().getLocation(),
                Settings.class.getProtectionDomain().getCodeSource().getLocation(),
                HttpStatus.class.getProtectionDomain().getCodeSource().getLocation()};
    }
}
