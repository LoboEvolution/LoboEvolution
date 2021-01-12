/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.menu.view;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Document;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.loboevolution.common.Strings;

/**
 * <p>SourceViewerWindow class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SourceViewerWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	/** The jtf filter. */
	private JTextField jtfFilter;

	/** The pos. */
	private int pos = 0;

	/** The text area. */
	private transient RSyntaxTextArea textArea;

	/**
	 * Instantiates a new text viewer window.
	 */
	public SourceViewerWindow() {
		super("Lobo Source Viewer");
		createAndShowGUI();
	}

	private void addTextArea() {
		this.jtfFilter = new JTextField();

		final JButton findButton = new JButton("Next word");
		this.textArea = new RSyntaxTextArea();
		this.textArea.setHighlightCurrentLine(true);
		this.textArea.setAnimateBracketMatching(true);
		this.textArea.setAntiAliasingEnabled(true);
		this.textArea.setEditable(false);
		this.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML);
		this.textArea.setCodeFoldingEnabled(true);

		final RTextScrollPane scrollBar = new RTextScrollPane(this.textArea);
		scrollBar.setBorder(null);
		scrollBar.setLineNumbersEnabled(true);
		scrollBar.setViewportView(this.textArea);

		getContentPane().add(scrollBar);

		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Specify a word to match:"), BorderLayout.WEST);
		panel.add(this.jtfFilter, BorderLayout.CENTER);
		panel.add(findButton, BorderLayout.EAST);
		add(panel, BorderLayout.SOUTH);

		findButton.addActionListener(e -> {
			final String find = this.jtfFilter.getText().toLowerCase();
			this.textArea.requestFocusInWindow();
			if (Strings.isNotBlank(find)) {
				final Document document = this.textArea.getDocument();
				final int findLength = find.length();
				try {
					boolean found = false;
					if (this.pos + findLength > document.getLength()) {
						this.pos = 0;
					}
					while (this.pos + findLength <= document.getLength()) {
						final String match = document.getText(this.pos, findLength).toLowerCase();
						if (match.equals(find)) {
							found = true;
							break;
						}
						this.pos++;
					}
					if (found) {
						final Rectangle viewRect = this.textArea.modelToView(this.pos);
						this.textArea.scrollRectToVisible(viewRect);
						this.textArea.setCaretPosition(this.pos + findLength);
						this.textArea.moveCaretPosition(this.pos);
						this.pos += findLength;
					}

				} catch (final Exception exp) {
					exp.printStackTrace();
				}

			}
		});
	}

	private void createAndShowGUI() {
		setResizable(true);
		setLocationRelativeTo(null);
		addTextArea();
	}

	/** {@inheritDoc} */
	@Override
	public Image getIconImage() {
		return new ImageIcon(getClass().getResource("/org/lobo/image/icon.png")).getImage();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isVisible() {
		return true;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.textArea.setText(text);
	}
}
