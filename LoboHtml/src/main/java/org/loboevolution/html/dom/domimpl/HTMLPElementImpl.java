package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLParagraphElement;
import org.loboevolution.html.renderstate.ParagraphRenderState;
import org.loboevolution.html.renderstate.RenderState;

public class HTMLPElementImpl extends HTMLAbstractUIElement implements HTMLParagraphElement {
	public HTMLPElementImpl(String name) {
		super(name);
	}

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

	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new ParagraphRenderState(prevRenderState, this);
	}

	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}
}
