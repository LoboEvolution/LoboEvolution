package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.renderstate.FontStyleRenderState;
import org.lobobrowser.html.renderstate.RenderState;

/**
 * Element used for SUB
 */

public class HTMLSuperscriptElementImpl extends HTMLAbstractUIElement {
	private int superscript;

	public HTMLSuperscriptElementImpl(String name, int superscript) {
		super(name);
		this.superscript = superscript;
	}

	protected RenderState createRenderState(RenderState prevRenderState) {
		prevRenderState = FontStyleRenderState
				.createSuperscriptFontStyleRenderState(prevRenderState,
						new Integer(this.superscript));
		return super.createRenderState(prevRenderState);
	}
}
