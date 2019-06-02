package org.lobo.menu.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.lobo.component.BrowserFrame;
import org.lobo.component.ToolBar;
import org.lobo.net.HttpNetwork;

public class SourceAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	public SourceAction(BrowserFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		final ToolBar bar = this.frame.getToolbar();
		final SourceViewerWindow textViewer = new SourceViewerWindow();
		textViewer.setText(HttpNetwork.getSource(bar.getAddressBar().getText()));
		textViewer.setSize(new Dimension(600, 400));
		textViewer.setLocationByPlatform(true);
		textViewer.setVisible(true);
	}
}