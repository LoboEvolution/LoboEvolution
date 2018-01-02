/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.control;

import org.loboevolution.html.dombl.ModelNode;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.RenderableContainer;
import org.loboevolution.html.renderer.UIControl;
import org.loboevolution.http.UserAgentContext;

/**
 * The Class RCanvasControl.
 */
public class RSVGControl extends RUIControl {

	public RSVGControl(ModelNode me, UIControl widget, RenderableContainer container, FrameContext frameContext,
			UserAgentContext ucontext) {
		super(me, widget, container, frameContext, ucontext);
	}

	@Override
	protected void applyStyle(int availWidth, int availHeight) {
		super.applyStyle(availWidth, availHeight);
	}
}
