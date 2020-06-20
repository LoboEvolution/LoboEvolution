package org.loboevolution.menu.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.ToolBar;
import org.loboevolution.net.HttpNetwork;

/**
 * <p>SourceAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SourceAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(SourceAction.class.getName());

	private final BrowserFrame frame;

	/**
	 * <p>Constructor for SourceAction.</p>
	 *
	 * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
	 */
	public SourceAction(BrowserFrame frame) {
		this.frame = frame;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			final ToolBar bar = this.frame.getToolbar();
			final SourceViewerWindow textViewer = new SourceViewerWindow();
			textViewer.setText(HttpNetwork.getSource(bar.getAddressBar().getText()));
			textViewer.setSize(new Dimension(600, 400));
			textViewer.setLocationByPlatform(true);
			textViewer.setVisible(true);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
