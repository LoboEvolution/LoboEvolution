package org.lobobrowser.html.renderstate;

import org.lobobrowser.html.style.RenderStateDelegator;


/**
 * The Class BaseFontRenderState.
 */
public class BaseFontRenderState extends RenderStateDelegator {
	
	/** The font base. */
	private final int fontBase;

	/**
	 * Instantiates a new base font render state.
	 *
	 * @param prevRenderState the prev render state
	 * @param fontBase the font base
	 */
	public BaseFontRenderState(final RenderState prevRenderState, int fontBase) {
		super(prevRenderState);
		this.fontBase = fontBase;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.style.RenderStateDelegator#getFontBase()
	 */
	public int getFontBase() {
		return this.fontBase;
	}
}
