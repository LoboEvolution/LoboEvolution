/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.dnd.DropTarget;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serial;

import javax.swing.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.loboevolution.common.Strings;
import org.loboevolution.download.DownloadWindow;
import org.loboevolution.menu.MenuBar;
import org.loboevolution.store.GeneralStore;
import org.loboevolution.store.StyleStore;
import org.loboevolution.store.TabStore;
import org.loboevolution.store.WebStore;

/**
 * <p>BrowserFrame class.</p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BrowserFrame extends JFrame implements IBrowserFrame {

	@Serial
    private static final long serialVersionUID = 1L;

	private BrowserPanel panel;

	private ToolBar toolbar;

	/**
	 * <p>Constructor for BrowserFrame.</p>
	 *
	 * @param title a {@link java.lang.String} object.
	 */
	public BrowserFrame(final String title) {
		super(title);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		this.panel = new BrowserPanel(this);
		final IWelcomePanel welcome = this.panel.getWelcome();
		if (welcome != null) {
			final Rectangle initialWindowBounds = GeneralStore.getInitialWindowBounds();
			final int width = Double.valueOf(initialWindowBounds.getWidth()).intValue();
			final int height = Double.valueOf(initialWindowBounds.getHeight()).intValue();
			final Dimension dim = new Dimension(width, height);
			welcome.setSize(dim);
			welcome.setPreferredSize(dim);
		}
		this.toolbar = new ToolBar(this.panel);

		setJMenuBar(new MenuBar(this));
		new DropTarget(welcome.getWelocome(), new DragDropListener(panel));

		final JPanel basicPanel = new JPanel();
		basicPanel.setLayout(new BorderLayout());
		basicPanel.add(this.panel, BorderLayout.CENTER);
		basicPanel.add(this.toolbar, BorderLayout.NORTH);
		contentPane.add(basicPanel, BorderLayout.CENTER);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(final ComponentEvent e) {
				final BrowserPanel bpanel = BrowserFrame.this.panel;
				final IBrowserFrame browserFrame = bpanel.getBrowserFrame();
				final JTextField addressBar = browserFrame.getToolbar().getAddressBar();
				if (Strings.isNotBlank(addressBar.getText())) {
					final GoAction go = new GoAction(panel, browserFrame.getToolbar().getAddressBar());
					go.actionPerformed(null);
				} else {
					final IWelcomePanel welcome = bpanel.getWelcome();
					final Dimension dim = new Dimension(getWidth(), getHeight());
					welcome.setSize(dim);
					welcome.setPreferredSize(dim);
					welcome.repaint();
				}
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				TabStore.deleteAll();
				WebStore.deleteSessionStorage();
				final StyleStore style = new StyleStore();
				style.deleteStyle();
			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public IDownload getDownload() {
		return new DownloadWindow();
	}
}
