package org.lobobrowser.html.renderstate;

import org.lobobrowser.html.style.RenderStateDelegator;

public class AlignXRenderState extends RenderStateDelegator {
	private final int alignXPercent;

	public AlignXRenderState(final RenderState prevRenderState,
			int alignXPercent) {
		super(prevRenderState);
		this.alignXPercent = alignXPercent;
	}

	public int getAlignXPercent() {
		return this.alignXPercent;
	}
}
