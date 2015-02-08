/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

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

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.reflect.FXClassType;
import javafx.reflect.FXContext;
import javafx.reflect.FXLocal;
import javafx.reflect.FXObjectValue;
import javafx.reflect.FXValue;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.lobobrowser.clientlet.ClientletContext;

import com.sun.javafx.stage.StageDelegate;

public class ClientletStageDelegate extends StageDelegate {
	private static final Logger logger = Logger
			.getLogger(ClientletStageDelegate.class.getName());
	private static final FXClassType PANEL_SCENE_TYPE;
	static {
		String helperName = "com.sun.javafx.scene.JSGPanelSceneImpl";
		FXClassType type = FXContext.getInstance().findClass(helperName);
		PANEL_SCENE_TYPE = type;
	}

	public ClientletStageDelegate(ClientletContext context) {
	}

	public Component createComponent() {
		FXClassType stageDelegateType = FXContext.getInstance().findClass(
				this.getClass().getName());
		FXObjectValue thisValue = FXLocal.getContext().mirrorOf(this);
		FXValue stageValue = stageDelegateType.getVariable("stage").getValue(
				thisValue);
		if (stageValue == null) {
			throw new IllegalStateException("Delegate has no stage!");
		}
		FXClassType stageType = FXContext.getInstance().findClass(
				Stage.class.getName());
		FXValue sceneValue = stageType.getVariable("scene").getValue(
				(FXObjectValue) stageValue);
		if (sceneValue == null) {
			throw new IllegalStateException("Stage has no scene.");
		}
		return getSceneComponent(sceneValue);
	}

	public static java.awt.Component getSceneComponent(Scene scene) {
		return getSceneComponent(FXLocal.getContext().mirrorOf(scene));
	}

	public static java.awt.Component getSceneComponent(FXValue sceneMirror) {
		FXClassType type = PANEL_SCENE_TYPE;
		if (type == null) {
			throw new IllegalStateException("JSGPanelSceneImpl type not set.");
		}
		FXObjectValue panelSceneImpl = type.allocate();
		panelSceneImpl.initVar("scene", sceneMirror);
		panelSceneImpl.initialize();
		FXValue jsgPanelV = type.getVariable("jsgPanel").getValue(
				panelSceneImpl);
		return (java.awt.Component) ((FXLocal.ObjectValue) jsgPanelV)
				.asObject();
	}

	@Override
	public void close() {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("close(): Ignored.");
		}
	}

	@Override
	public void toBack() {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("toBack(): Ignored.");
		}
	}

	@Override
	public void toFront() {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("toFront(): Ignored.");
		}
	}
}
