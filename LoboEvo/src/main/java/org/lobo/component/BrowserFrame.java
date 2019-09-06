package org.lobo.component;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lobo.menu.MenuBar;
import org.lobo.store.GeneralStore;
import org.lobo.store.TabStore;
import org.lobo.welcome.WelcomePanel;

public class BrowserFrame extends JFrame implements IBrowserFrame {

	private static final long serialVersionUID = 1L;

	private BrowserPanel panel;

	private ToolBar toolbar;

	public BrowserFrame(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		this.panel = new BrowserPanel(this);
		final WelcomePanel welcome = this.panel.getWelcome();
		if (welcome != null) {
			final Rectangle initialWindowBounds = GeneralStore.getInitialWindowBounds();
			final int width = new Double(initialWindowBounds.getWidth()).intValue();
			final int height = new Double(initialWindowBounds.getHeight()).intValue();
			final Dimension dim = new Dimension(width, height);
			welcome.setSize(dim);
			welcome.setPreferredSize(dim);
		}
		this.toolbar = new ToolBar(this.panel);

		setJMenuBar(new MenuBar(this));

		final JPanel basicPanel = new JPanel();
		basicPanel.setLayout(new BorderLayout());
		basicPanel.add(this.panel, BorderLayout.CENTER);
		basicPanel.add(this.toolbar, BorderLayout.NORTH);
		contentPane.add(basicPanel, BorderLayout.CENTER);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				final WelcomePanel welcome = BrowserFrame.this.panel.getWelcome();
				final Dimension dim = new Dimension(getWidth(), getHeight());
				welcome.setSize(dim);
				welcome.setPreferredSize(dim);
				welcome.repaint();
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				TabStore.deleteAll();
			}
		});
	}

	/**
	 * @return the panel
	 */
	public BrowserPanel getPanel() {
		return this.panel;
	}

	/**
	 * @return the toolbar
	 */
	public ToolBar getToolbar() {
		return this.toolbar;
	}

	/**
	 * @param panel the panel to set
	 */
	public void setPanel(BrowserPanel panel) {
		this.panel = panel;
	}

	/**
	 * @param toolbar the toolbar to set
	 */
	public void setToolbar(ToolBar toolbar) {
		this.toolbar = toolbar;
	}
}