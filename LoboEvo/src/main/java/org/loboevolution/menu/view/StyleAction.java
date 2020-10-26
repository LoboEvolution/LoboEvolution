package org.loboevolution.menu.view;

import org.loboevolution.component.BrowserFrame;
import org.loboevolution.component.ToolBar;
import org.loboevolution.menu.tools.clear.ClearHistoryWindow;
import org.loboevolution.net.HttpNetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>StyleAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class StyleAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(StyleAction.class.getName());

	private final BrowserFrame frame;

	/**
	 * <p>Constructor for SourceAction.</p>
	 *
	 * @param frame a {@link BrowserFrame} object.
	 */
	public StyleAction(BrowserFrame frame) {
		this.frame = frame;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		final StyleWindow clear = new StyleWindow(this.frame);
		clear.setLocationByPlatform(true);
		clear.setResizable(false);
		clear.setSize(new Dimension(200, 190));
		clear.setVisible(true);
	}
}
