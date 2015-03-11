package org.lobobrowser.html.renderstate;

import org.lobobrowser.html.style.RenderStateDelegator;


/**
 * The Class AlignXRenderState.
 */
public class AlignXRenderState extends RenderStateDelegator {
	
	/** The align x percent. */
	private final int alignXPercent;

	/**
	 * Instantiates a new align x render state.
	 *
	 * @param prevRenderState the prev render state
	 * @param alignXPercent the align x percent
	 */
	public AlignXRenderState(final RenderState prevRenderState,
			int alignXPercent) {
		super(prevRenderState);
		this.alignXPercent = alignXPercent;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.style.RenderStateDelegator#getAlignXPercent()
	 */
	public int getAlignXPercent() {
		return this.alignXPercent;
	}
}
