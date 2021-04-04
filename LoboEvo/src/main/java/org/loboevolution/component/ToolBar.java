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

package org.loboevolution.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.loboevolution.component.input.Autocomplete;
import org.loboevolution.info.BookmarkInfo;
import org.loboevolution.store.BookmarksStore;

/**
 * <p>ToolBar class.</p>
 *
 *
 *
 */
public class ToolBar extends JToolBar implements IToolBar {

	private static final long serialVersionUID = 1L;

	private JTextField addressBar;

	/**
	 * <p>Constructor for ToolBar.</p>
	 *
	 * @param panel a {@link org.loboevolution.component.BrowserPanel} object.
	 */
	public ToolBar(BrowserPanel panel) {
		init(panel);
	}

	/**
	 * <p>Getter for the field addressBar.</p>
	 *
	 * @return the addressBar
	 */
	public JTextField getAddressBar() {
		return this.addressBar;
	}

	private void init(BrowserPanel panel) {
		setLayout(new BorderLayout());
		setBorderPainted(true);
		setOpaque(true);
		setEnabled(false);
		setFloatable(false);

		this.addressBar = new JTextField() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
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
		
		BookmarksStore book = new BookmarksStore();
		List<BookmarkInfo> bookmarks = book.getBookmarks(100);
		List<String> hosts = new ArrayList<>();
		bookmarks.forEach(bookmark -> hosts.add(bookmark.getUrl()));
		Autocomplete.setupAutoComplete(addressBar, hosts);
		addressBar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					GoAction go = new GoAction(panel, addressBar);
					go.actionPerformed(null);
				}	
			}
		});
		
		final ClassLoader classLoader = getClass().getClassLoader();

		ImageIcon icon = new ImageIcon(classLoader.getResource("org/lobo/image/back.png"));
		final JButton back = new JButton();
		back.setIcon(new ImageIcon(icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		back.setToolTipText("Back");
		back.setBorder(BorderFactory.createEmptyBorder());
		back.addActionListener(new BackAction(panel, this.addressBar));

		icon = new ImageIcon(classLoader.getResource("org/lobo/image/forward.png"));
		final JButton forward = new JButton();
		forward.setIcon(new ImageIcon(icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		forward.setToolTipText("Forward");
		forward.setBorder(BorderFactory.createEmptyBorder());
		forward.addActionListener(new ForwardAction(panel, this.addressBar));

		icon = new ImageIcon(classLoader.getResource("org/lobo/image/reload.png"));
		final JButton refresh = new JButton();
		refresh.setIcon(new ImageIcon(icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		refresh.setToolTipText("Refresh");
		refresh.setBorder(BorderFactory.createEmptyBorder());
		refresh.addActionListener(new GoAction(panel, this.addressBar));

		this.addressBar.setEditable(true);
		this.addressBar.setBackground(Color.LIGHT_GRAY);

		icon = new ImageIcon(classLoader.getResource("org/lobo/image/go.png"));
		final JButton go = new JButton();
		go.setIcon(new ImageIcon(icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		go.setToolTipText("Go");
		go.setBorder(BorderFactory.createEmptyBorder());
		go.addActionListener(new GoAction(panel, this.addressBar));

		icon = new ImageIcon(classLoader.getResource("org/lobo/image/home.png"));
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
