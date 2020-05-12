package org.loboevolution.menu.view;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.component.BrowserFrame;

/**
 * <p>FullScreenAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class FullScreenAction extends AbstractAction {

	private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	private static DisplayMode newDisplayMode = new DisplayMode(800, 600, 32, 0);

	private static DisplayMode oldDisplayMode = device.getDisplayMode();

	private static final long serialVersionUID = 1L;

	private int countFs = 0;

	private final BrowserFrame frame;

	/**
	 * <p>Constructor for FullScreenAction.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public FullScreenAction(BrowserFrame frame) {
		this.frame = frame;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.countFs == 0) {
			this.countFs = this.countFs + 1;
			device.setFullScreenWindow(this.frame);
			if (device != null && device.isDisplayChangeSupported()) {
				device.setDisplayMode(newDisplayMode);
			}
		} else {
			this.countFs = 0;
			device.setDisplayMode(oldDisplayMode);
			device.setFullScreenWindow(null);
		}

		this.frame.revalidate();

	}

}
