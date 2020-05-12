package org.loboevolution.welcome;

import java.awt.Color;

import javax.swing.JPanel;

import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.component.IWelcomePanel;

/**
 * <p>WelcomePanel class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class WelcomePanel extends JPanel implements IWelcomePanel {

	private static final long serialVersionUID = 1L;

	private final LoginButton button;

	private final TextFieldUsername text;

	/**
	 * <p>Constructor for WelcomePanel.</p>
	 *
	 * @param panel a {@link org.loboevolution.component.IBrowserPanel} object.
	 */
	public WelcomePanel(IBrowserPanel panel) {
		this.text = new TextFieldUsername();
		this.button = new LoginButton(panel, this.text);
		setBackground(new Color(37, 51, 61));
		setLayout(null);
		add(this.text);
		add(this.button);
	}

	/** {@inheritDoc} */
	@Override
	public void repaint() {
		if (this.text != null) {
			final int x = (int) (getSize().getWidth() * 0.07);
			final int x1 = (int) (getSize().getWidth() * 0.27);
			final int y = 109;
			final int width = (int) (getSize().getWidth() * 0.80);
			final int height = 44;
			this.text.setBounds(x, y, width, height);
			this.button.setBounds(x1, 170, width / 2, height);
		}
	}

	/** {@inheritDoc} */
	@Override
	public JPanel getWelocome() {
		return this;
	}
}
