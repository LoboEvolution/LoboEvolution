package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.renderstate.FontStyleRenderState;
import org.lobobrowser.html.renderstate.RenderState;


/**
 * Element used for SUB.
 */

public class HTMLSuperscriptElementImpl extends HTMLAbstractUIElement {
	
	/** The superscript. */
	private int superscript;

	/**
	 * Instantiates a new HTML superscript element impl.
	 *
	 * @param name the name
	 * @param superscript the superscript
	 */
	public HTMLSuperscriptElementImpl(String name, int superscript) {
		super(name);
		this.superscript = superscript;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.lobobrowser.html.renderstate.RenderState)
	 */
	protected RenderState createRenderState(RenderState prevRenderState) {
		prevRenderState = FontStyleRenderState
				.createSuperscriptFontStyleRenderState(prevRenderState,
						new Integer(this.superscript));
		return super.createRenderState(prevRenderState);
	}
}
