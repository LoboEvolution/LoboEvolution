/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import lombok.Getter;
import org.loboevolution.component.input.Autocomplete;
import org.loboevolution.config.DesktopConfig;
import org.loboevolution.info.BookmarkInfo;
import org.loboevolution.store.BookmarksStore;

/**
 * <p>ToolBar class.</p>
 */
@Getter
public class ToolBar extends JToolBar implements IToolBar {

	@Serial
    private static final long serialVersionUID = 1L;

	private JTextField addressBar;

	/**
	 * <p>Constructor for ToolBar.</p>
	 *
	 * @param panel a {@link org.loboevolution.component.BrowserPanel} object.
	 */
	public ToolBar(final BrowserPanel panel) {
		init(panel);
	}

	private void init(final BrowserPanel panel) {
		setLayout(new BorderLayout());
		setBorderPainted(true);
		setOpaque(true);
		setEnabled(false);
		setFloatable(false);

		this.addressBar = new JTextField() {
			@Serial
            private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(final Graphics g) {
				if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
					final Graphics2D g2 = (Graphics2D) g.create();
					g2.setColor(Color.LIGHT_GRAY);
					g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(0, 0, getWidth() - 1, getHeight() - 1));
					g2.dispose();
				}
				super.paintComponent(g);
			}

			@Override
			public void updateUI() {
				super.updateUI();
				setOpaque(false);
				setBorder(new RoundedCornerBorder());
			}
		};
		
		final BookmarksStore book = new BookmarksStore();
		final List<BookmarkInfo> bookmarks = book.getBookmarks(100);
		final List<String> hosts = new ArrayList<>();
		bookmarks.forEach(bookmark -> hosts.add(bookmark.getUrl()));
		Autocomplete.setupAutoComplete(addressBar, hosts);
		addressBar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					final GoAction go = new GoAction(panel, addressBar);
					go.actionPerformed(null);
				}	
			}
		});

		ImageIcon icon = new ImageIcon(DesktopConfig.getResourceFile("back.png",DesktopConfig.PATH_IMAGE));
		final JButton back = new JButton();
		back.setIcon(new ImageIcon(icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		back.setToolTipText("Back");
		back.setBorder(BorderFactory.createEmptyBorder());
		back.addActionListener(new BackAction(panel, this.addressBar));

		icon = new ImageIcon(DesktopConfig.getResourceFile("forward.png",DesktopConfig.PATH_IMAGE));
		final JButton forward = new JButton();
		forward.setIcon(new ImageIcon(icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		forward.setToolTipText("Forward");
		forward.setBorder(BorderFactory.createEmptyBorder());
		forward.addActionListener(new ForwardAction(panel, this.addressBar));

		icon = new ImageIcon(DesktopConfig.getResourceFile("reload.png",DesktopConfig.PATH_IMAGE));
		final JButton refresh = new JButton();
		refresh.setIcon(new ImageIcon(icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		refresh.setToolTipText("Refresh");
		refresh.setBorder(BorderFactory.createEmptyBorder());
		refresh.addActionListener(new GoAction(panel, this.addressBar));

		this.addressBar.setEditable(true);
		this.addressBar.setBackground(Color.LIGHT_GRAY);

		icon = new ImageIcon(DesktopConfig.getResourceFile("go.png",DesktopConfig.PATH_IMAGE));
		final JButton go = new JButton();
		go.setIcon(new ImageIcon(icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		go.setToolTipText("Go");
		go.setBorder(BorderFactory.createEmptyBorder());
		go.addActionListener(new GoAction(panel, this.addressBar));

		icon = new ImageIcon(DesktopConfig.getResourceFile("home.png",DesktopConfig.PATH_IMAGE));
		final JButton home = new JButton();
		home.setIcon(new ImageIcon(icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		home.setToolTipText("Home");
		home.setBorder(BorderFactory.createEmptyBorder());
		home.addActionListener(new HomeAction(panel));

		final JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		panel1.add(back, BorderLayout.WEST);
		panel1.add(forward, BorderLayout.CENTER);
		panel1.add(refresh, BorderLayout.EAST);

		final JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		panel2.add(go, BorderLayout.WEST);
		panel2.add(home, BorderLayout.EAST);

		add(panel1, BorderLayout.WEST);
		add(this.addressBar, BorderLayout.CENTER);
		add(panel2, BorderLayout.EAST);

	}
}
