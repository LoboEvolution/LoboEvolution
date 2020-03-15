package org.loboevolution.html.control;

import org.loboevolution.common.WrapperLayout;
import org.loboevolution.html.dom.domimpl.HTMLIFrameElementImpl;

public class FrameControl extends BaseControl {

	private static final long serialVersionUID = 1L;

	/** The element. */
	private final HTMLIFrameElementImpl modelNode;

	public FrameControl(HTMLIFrameElementImpl modelNode) {
		super(modelNode);
		setLayout(WrapperLayout.getInstance());
		this.modelNode = modelNode;
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		modelNode.draw(this);
	}
}
