package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.renderstate.FontStyleRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * Element used for SUB
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLSuperscriptElementImpl extends HTMLAbstractUIElement {
	private final int superscript;

	/**
	 * <p>Constructor for HTMLSuperscriptElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param superscript a int.
	 */
	public HTMLSuperscriptElementImpl(String name, int superscript) {
		super(name);
		this.superscript = superscript;
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		prevRenderState = FontStyleRenderState.createSuperscriptFontStyleRenderState(prevRenderState,
                this.superscript);
		return super.createRenderState(prevRenderState);
	}
}
