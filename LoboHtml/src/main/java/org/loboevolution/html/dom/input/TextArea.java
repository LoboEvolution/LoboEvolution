package org.loboevolution.html.dom.input;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.control.TextAreaControl;
import org.loboevolution.html.dom.domimpl.HTMLTextAreaElementImpl;
import org.loboevolution.html.js.Executor;

public class TextArea {
	
	
	private static final float DEFAULT_FONT_SIZE = 14.0f;
	
	private JTextArea  jtArea = new JTextArea();
	
	private HTMLTextAreaElementImpl modelNode;
	
	
	/**
	 * <p>Constructor for InputText.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLTextAreaElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.TextAreaControl} object.
	 */
	public TextArea(HTMLTextAreaElementImpl modelNode, TextAreaControl ic) {
		this.modelNode = modelNode;
		this.modelNode = modelNode;
		
		final Font font = jtArea.getFont();
		jtArea.setFont(font.deriveFont(DEFAULT_FONT_SIZE));
		jtArea.setDocument(new LimitedDocument());
		jtArea.setText(modelNode.getValue());
		jtArea.setSelectionColor(Color.BLUE);
		jtArea.setPreferredSize(getPreferredSize());
		
		jtArea.setEnabled(!modelNode.getDisabled());
		jtArea.setEditable(!modelNode.getReadOnly());

		MouseInputAdapter mouseHandler = new MouseInputAdapter() {

			@Override
			public void mouseEntered(final MouseEvent e) {
				if (modelNode.getOnmouseover() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnmouseover(), null, new Object[] {});
				}
			}
		};
		
		jtArea.addMouseListener(mouseHandler);
		
		RUIControl ruiControl = ic.getRUIControl();
		final Insets borderInsets = ruiControl.getBorderInsets();
		
		jtArea.setMargin(new Insets(ruiControl.getMarginTop(), ruiControl.getMarginLeft(), ruiControl.getMarginBottom(), ruiControl.getMarginRight()));
		
		if (borderInsets.top == 0 && borderInsets.left == 0 && borderInsets.bottom == 0 && borderInsets.right == 0) {
			jtArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		} else {
			jtArea.setBorder(BorderFactory.createMatteBorder(borderInsets.top, borderInsets.left, borderInsets.bottom, borderInsets.right, Color.BLACK));
		}

		ic.add(jtArea);
	}
	
	/**
	 * <p>selectAll.</p>
	 */
	public void selectAll() {
		jtArea.selectAll();
		jtArea.requestFocus();
	}
	
	
	private Dimension getPreferredSize() {
		int pw;
		int cols =  modelNode.getCols();
		if (cols == -1) {
			pw = 200;
		} else {
			Font f = this.jtArea.getFont();
			FontMetrics fm = this.jtArea.getFontMetrics(f);
			Insets insets = this.jtArea.getInsets();
			pw = insets.left + insets.right + fm.charWidth('*') * cols;
		}
		int ph;
		int rows = modelNode.getRows();
		if (rows == -1) {
			ph = 100;
		} else {
			Font f = this.jtArea.getFont();
			FontMetrics fm = this.jtArea.getFontMetrics(f);
			Insets insets = this.jtArea.getInsets();
			ph = insets.top + insets.bottom + fm.getHeight() * rows;
		}
		return new Dimension(pw, ph);

	}

	
	private class LimitedDocument extends PlainDocument {

		private static final long serialVersionUID = 1L;

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			int max = modelNode.getMaxLength();

			final int docLength = getLength();
			if (docLength >= max) {
				return;
			}
			final int strLen = str.length();
			if (docLength + strLen > max) {
				final String shorterStr = str.substring(0, max - docLength);
				super.insertString(offs, shorterStr, a);
			} else {
				super.insertString(offs, str, a);
			}
		}
	}
}