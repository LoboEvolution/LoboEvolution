/*
* Copyright (c) 2002 and later by MH Software-Entwicklung. All Rights Reserved.
*
* JTattoo is multiple licensed. If your are an open source developer you can use
* it under the terms and conditions of the GNU General Public License version 2.0
* or later as published by the Free Software Foundation.
*
* see: gpl-2.0.txt
*
* If you pay for a license you will become a registered user who could use the
* software under the terms and conditions of the GNU Lesser General Public License
* version 2.0 or later with classpath exception as published by the Free Software
* Foundation.
*
* see: lgpl-2.0.txt
* see: classpath-exception.txt
*
* Registered users could also use JTattoo under the terms and conditions of the
* Apache License, Version 2.0 as published by the Apache Software Foundation.
*
* see: APACHE-LICENSE-2.0.txt
 */
package com.jtattoo.plaf.texture;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.BaseScrollPaneUI;

/**
 * <p>TextureScrollPaneUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TextureScrollPaneUI extends BaseScrollPaneUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new TextureScrollPaneUI();
	}

	/** {@inheritDoc} */
	@Override
	public void installDefaults(JScrollPane p) {
		super.installDefaults(p);
		p.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
	}

	/** {@inheritDoc} */
	@Override
	public void update(Graphics g, JComponent c) {
		if (c.getBackground() instanceof ColorUIResource) {
			if (c.isOpaque()) {
				TextureUtils.fillComponent(g, c, TextureUtils.getTextureType(c));
			}
		} else {
			super.update(g, c);
		}
	}

} // end of class TextureScrollPaneUI
