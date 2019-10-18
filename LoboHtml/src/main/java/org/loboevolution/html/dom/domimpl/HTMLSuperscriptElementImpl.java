package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.renderstate.FontStyleRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * Element used for SUB
 */

public class HTMLSuperscriptElementImpl extends HTMLAbstractUIElement {
	private final int superscript;

	public HTMLSuperscriptElementImpl(String name, int superscript) {
		super(name);
		this.superscript = superscript;
	}

	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		prevRenderState = FontStyleRenderState.createSuperscriptFontStyleRenderState(prevRenderState,
				new Integer(this.superscript));
		return super.createRenderState(prevRenderState);
	}
}
