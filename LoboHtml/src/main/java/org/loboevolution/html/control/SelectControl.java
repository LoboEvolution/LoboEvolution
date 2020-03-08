package org.loboevolution.html.control;

import org.loboevolution.common.WrapperLayout;
import org.loboevolution.html.dom.domimpl.HTMLSelectElementImpl;

public class SelectControl extends BaseControl implements UIControl {

private static final long serialVersionUID = 1L;
	
	private HTMLSelectElementImpl  modelNode;
	
	public SelectControl(HTMLSelectElementImpl modelNode) {
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