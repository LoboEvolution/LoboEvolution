package org.loboevolution.html.control;

import org.loboevolution.common.WrapperLayout;
import org.loboevolution.html.dom.domimpl.HTMLButtonElementImpl;

/**
 * <p>ButtonControl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ButtonControl extends BaseControl implements UIControl {

private static final long serialVersionUID = 1L;
	
	private HTMLButtonElementImpl  modelNode;
	
	/**
	 * <p>Constructor for ButtonControl.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLButtonElementImpl} object.
	 */
	public ButtonControl(HTMLButtonElementImpl modelNode) {
		super(modelNode);
		setLayout(WrapperLayout.getInstance());
		this.modelNode = modelNode;
	}

	/** {@inheritDoc} */
	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		modelNode.draw(this);
	}
}
