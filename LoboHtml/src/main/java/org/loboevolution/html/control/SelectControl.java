package org.loboevolution.html.control;

import org.loboevolution.common.WrapperLayout;
import org.loboevolution.html.dom.domimpl.HTMLSelectElementImpl;

/**
 * <p>SelectControl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SelectControl extends BaseControl implements UIControl {

private static final long serialVersionUID = 1L;
	
	private final HTMLSelectElementImpl  modelNode;
	
	/**
	 * <p>Constructor for SelectControl.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLSelectElementImpl} object.
	 */
	public SelectControl(HTMLSelectElementImpl modelNode) {
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
