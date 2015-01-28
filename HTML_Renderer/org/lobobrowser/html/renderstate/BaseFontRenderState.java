package org.lobobrowser.html.renderstate;

import org.lobobrowser.html.style.RenderStateDelegator;

public class BaseFontRenderState extends RenderStateDelegator {
	private final int fontBase;

	public BaseFontRenderState(final RenderState prevRenderState, int fontBase) {
		super(prevRenderState);
		this.fontBase = fontBase;
	}

	public int getFontBase() {
		return this.fontBase;
	}
}
