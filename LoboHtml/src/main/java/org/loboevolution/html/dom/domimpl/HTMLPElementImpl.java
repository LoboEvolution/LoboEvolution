package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLParagraphElement;
import org.loboevolution.html.renderstate.ParagraphRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>HTMLPElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLPElementImpl extends HTMLAbstractUIElement implements HTMLParagraphElement {
	/**
	 * <p>Constructor for HTMLPElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLPElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	protected void appendInnerTextImpl(StringBuilder buffer) {
		final int length = buffer.length();
		int lineBreaks;
		if (length == 0) {
			lineBreaks = 2;
		} else {
			int start = length - 4;
			if (start < 0) {
				start = 0;
			}
			lineBreaks = 0;
			for (int i = start; i < length; i++) {
				final char ch = buffer.charAt(i);
				if (ch == '\n') {
					lineBreaks++;
				}
			}
		}
		for (int i = 0; i < 2 - lineBreaks; i++) {
			buffer.append("\r\n");
		}
		super.appendInnerTextImpl(buffer);
		buffer.append("\r\n\r\n");
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new ParagraphRenderState(prevRenderState, this);
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}
}
