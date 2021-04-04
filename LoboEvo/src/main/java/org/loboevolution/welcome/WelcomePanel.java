/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.welcome;

import javax.swing.JPanel;

import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.component.IWelcomePanel;

import com.jtattoo.plaf.lobo.LoboLookAndFeel;

/**
 * <p>WelcomePanel class.</p>
 *
 *
 *
 */
public class WelcomePanel extends JPanel implements IWelcomePanel, LoboLookAndFeel {

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
		setBackground(background());
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
