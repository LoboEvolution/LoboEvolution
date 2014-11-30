/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.jweb.javafx;

import java.net.URL;
import java.util.List;

import javax.tools.DiagnosticListener;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.jweb.common.BaseCompiledClientlet;

import com.sun.javafx.api.ToolProvider;

public class CompiledJavaFXClientlet extends BaseCompiledClientlet {
	// private static JavafxCompiler compilerLocator() {
	// Iterator<?> iterator;
	// Class<?> loaderClass;
	// String loadMethodName;
	// boolean usingServiceLoader;
	//
	// try {
	// loaderClass = Class.forName("java.util.ServiceLoader");
	// loadMethodName = "load";
	// usingServiceLoader = true;
	// } catch (ClassNotFoundException cnfe) {
	// try {
	// loaderClass = Class.forName("sun.misc.Service");
	// loadMethodName = "providers";
	// usingServiceLoader = false;
	// } catch (ClassNotFoundException cnfe2) {
	// throw new AssertionError("Failed discovering ServiceLoader");
	// }
	// }
	//
	// try {
	// // java.util.ServiceLoader.load or sun.misc.Service.providers
	// Method loadMethod = loaderClass.getMethod(loadMethodName,
	// Class.class,
	// ClassLoader.class);
	// ClassLoader cl = CompiledJavaFXClientlet.class.getClassLoader();
	// Object result = loadMethod.invoke(null, JavafxCompiler.class, cl);
	//
	// // For java.util.ServiceLoader, we have to call another
	// // method to get the iterator.
	// if (usingServiceLoader) {
	// Method m = loaderClass.getMethod("iterator");
	// result = m.invoke(result); // serviceLoader.iterator();
	// }
	//
	// iterator = (Iterator<?>) result;
	// } catch (Throwable t) {
	// t.printStackTrace();
	// throw new AssertionError(); // not executed
	// }
	//
	// if (!iterator.hasNext()) {
	// throw new AssertionError(); // not executed
	// }
	// return (JavafxCompiler)iterator.next();
	// }

	@Override
	protected void compile(JavaFileManager fileManager,
			DiagnosticListener<? super JavaFileObject> diagnosticListener,
			List<JavaFileObject> compilationUnits) throws ClientletException {
		// TODO: Need to check if compiler API is there?

		// JavaCompiler stdJavaCompiler = ToolProvider.getJavafxCompiler();
		// if(stdJavaCompiler != null) {
		// throw new
		// ClientletException("System Java compiler found. You are apparently running this application with a JDK (not a JRE). For the time being, the JDK compiler needs to be bypassed in order to enable the JavaFX compiler patches. Your Java installation directory is "
		// + System.getProperty("java.home") + ".");
		// }

		com.sun.javafx.api.JavafxCompiler compiler = ToolProvider
				.getJavafxCompiler();
		compiler.getTask(null, fileManager, diagnosticListener, null,
				compilationUnits).call();
	}

	@Override
	protected URL[] getExtraPlatformClassPath() {
		// Return the location of JavaFX runtime classes.
		return new URL[] {
				javafx.lang.FX.class.getProtectionDomain().getCodeSource()
						.getLocation(),
				javafx.ext.swing.SwingButton.class.getProtectionDomain()
						.getCodeSource().getLocation(),
				javafx.scene.Group.class.getProtectionDomain().getCodeSource()
						.getLocation(),
				com.sun.scenario.Settings.class.getProtectionDomain()
						.getCodeSource().getLocation(),
				javafx.io.http.HttpStatus.class.getProtectionDomain()
						.getCodeSource().getLocation() };
	}
}
