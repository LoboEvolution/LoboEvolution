package org.loboevolution.html.control;

import org.loboevolution.common.WrapperLayout;
import org.loboevolution.html.dom.domimpl.HTMLButtonElementImpl;

public class ButtonControl extends BaseControl implements UIControl {

private static final long serialVersionUID = 1L;
	
	private HTMLButtonElementImpl  modelNode;
	
	public ButtonControl(HTMLButtonElementImpl modelNode) {
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
