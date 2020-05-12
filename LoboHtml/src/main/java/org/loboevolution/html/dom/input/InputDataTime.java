package org.loboevolution.html.dom.input;

import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;

/**
 * <p>InputDataTime class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class InputDataTime {

	private final static Logger logger = Logger.getLogger(InputDataTime.class.getName());

	/**
	 * <p>Constructor for InputDataTime.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputDataTime(HTMLInputElementImpl modelNode, InputControl ic) {

		try {
			final String type = modelNode.getType();
			JFormattedTextField tf = null;
			MaskFormatter dateMask = null;

			switch (type.toLowerCase()) {
			case "datetime-local":
				tf = new JFormattedTextField(new SimpleDateFormat("dd/mm/yyyy hh:mm"));
				dateMask = new MaskFormatter("##/##/#### ##:##");
				break;
			case "time":
				tf = new JFormattedTextField(new SimpleDateFormat("hh:mm:ss"));
				dateMask = new MaskFormatter("##:##:##");
				break;
			case "month":
				tf = new JFormattedTextField(new SimpleDateFormat("mm/yyyy"));
				dateMask = new MaskFormatter("##/####");
				break;
			case "date":
			default:
				tf = new JFormattedTextField(new SimpleDateFormat("dd/mm/yyyy"));
				dateMask = new MaskFormatter("##/##/####");
				break;
			}

			final Dimension ps = tf.getPreferredSize();
			tf.setPreferredSize(new Dimension(128, ps.height));
			ic.add(tf);
			dateMask.install(tf);
		} catch (ParseException err) {
			logger.log(Level.SEVERE, err.getMessage(), err);
		}
	}
}
