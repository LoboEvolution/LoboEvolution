package org.loboevolution.html.control;

import org.loboevolution.common.WrapperLayout;
import org.loboevolution.html.dom.domimpl.HTMLIFrameElementImpl;

/**
 * <p>FrameControl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class FrameControl extends BaseControl {

	private static final long serialVersionUID = 1L;

	/** The element. */
	private final HTMLIFrameElementImpl modelNode;

	/**
	 * <p>Constructor for FrameControl.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLIFrameElementImpl} object.
	 */
	public FrameControl(HTMLIFrameElementImpl modelNode) {
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
