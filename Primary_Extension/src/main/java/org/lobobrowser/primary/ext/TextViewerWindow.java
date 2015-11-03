/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.ext;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import org.lobobrowser.gui.DefaultWindowFactory;
import org.lobobrowser.primary.action.TextViewerCloseAction;
import org.lobobrowser.primary.action.TextViewerCopyAction;
import org.lobobrowser.primary.action.TextViewerSelectAllAction;
import org.lobobrowser.ua.NavigatorWindow;

/**
 * The Class TextViewerWindow.
 */
public class TextViewerWindow extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The text area. */
	private final JTextArea textArea;
	
	/** The jtf filter. */
	private JTextField jtfFilter;
	
	/** The find Button. */
	private JButton findButton;

	/** The scrolls on appends. */
	private boolean scrollsOnAppends;
		
	private int pos = 0;

	/**
	 * Instantiates a new text viewer window.
	 */
	public TextViewerWindow(NavigatorWindow window) {
		super("Lobo Text Viewer");
		jtfFilter = new JTextField();
		findButton = new JButton("Next word");
		setLayout(new BorderLayout());
		this.setIconImage(DefaultWindowFactory.getInstance().getDefaultImageIcon().getImage());
		JMenuBar menuBar = this.createMenuBar();
		this.setJMenuBar(menuBar);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Specify a word to match:"), BorderLayout.WEST);
		panel.add(jtfFilter, BorderLayout.CENTER);
		panel.add(findButton, BorderLayout.EAST);
		add(panel, BorderLayout.SOUTH);
		final JTextArea textArea = this.createTextArea();
		this.textArea = textArea;
		add(new JScrollPane(textArea), BorderLayout.CENTER);
		
		findButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String find = jtfFilter.getText().toLowerCase();
				textArea.requestFocusInWindow();
				if (find != null && find.length() > 0) {
					Document document = textArea.getDocument();
					int findLength = find.length();
					try {
						boolean found = false;
						if (pos + findLength > document.getLength()) {
							pos = 0;
						}
						while (pos + findLength <= document.getLength()) {
							String match = document.getText(pos, findLength).toLowerCase();
							if (match.equals(find)) {
								found = true;
								break;
							}
							pos++;
						}
						if (found) {
							Rectangle viewRect = textArea.modelToView(pos);
							textArea.scrollRectToVisible(viewRect);
							textArea.setCaretPosition(pos + findLength);
							textArea.moveCaretPosition(pos);
							pos += findLength;
						}

					} catch (Exception exp) {
						exp.printStackTrace();
					}

				}
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				DocumentListener cl = cachedListener;
				if (cl != null) {
					Document prevDocument = textArea.getDocument();
					if (prevDocument != null) {
						prevDocument.removeDocumentListener(cl);
					}
				}
			}
		});
	}

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	public void setText(String text) {
		this.textArea.setText(text);
	}

	/**
	 * Sets the scrolls on appends.
	 *
	 * @param flag
	 *            the new scrolls on appends
	 */
	public void setScrollsOnAppends(boolean flag) {
		this.scrollsOnAppends = flag;
	}

	/** The cached listener. */
	private DocumentListener cachedListener;

	/**
	 * Gets the document listener.
	 *
	 * @return the document listener
	 */
	private DocumentListener getDocumentListener() {
		// Expected in GUI thread.
		DocumentListener cl = this.cachedListener;
		if (cl == null) {
			cl = new LocalDocumentListener();
			this.cachedListener = cl;
		}
		return cl;
	}

	/**
	 * Sets the swing document.
	 *
	 * @param document
	 *            the new swing document
	 */
	public void setSwingDocument(Document document) {
		Document prevDocument = this.textArea.getDocument();
		DocumentListener listener = this.getDocumentListener();
		if (prevDocument != null) {
			prevDocument.removeDocumentListener(listener);
		}
		document.addDocumentListener(listener);
		this.textArea.setDocument(document);
	}

	/**
	 * Creates the text area.
	 *
	 * @return the j text area
	 */
	private JTextArea createTextArea() {
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		return textArea;
	}

	/**
	 * Creates the menu bar.
	 *
	 * @return the j menu bar
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(this.createFileMenu());
		menuBar.add(this.createEditMenu());
		return menuBar;
	}

	/**
	 * Creates the file menu.
	 *
	 * @return the j menu
	 */
	private JMenu createFileMenu() {
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		fileMenu.add(ComponentSource.menuItem("Close", 'C', new TextViewerCloseAction(this)));
		return fileMenu;
	}

	/**
	 * Creates the edit menu.
	 *
	 * @return the j menu
	 */
	private JMenu createEditMenu() {
		JMenu fileMenu = new JMenu("Edit");
		fileMenu.setMnemonic('E');
		fileMenu.add(ComponentSource.menuItem("Copy", 'C', "ctrl c", new TextViewerCopyAction(this)));
		fileMenu.add(ComponentSource.menuItem("Select All", 'A', "ctrl a", new TextViewerSelectAllAction(this)));
		return fileMenu;
	}

	/**
	 * Gets the text area.
	 *
	 * @return the text area
	 */
	public JTextArea getTextArea() {
		return textArea;
	}

	/**
	 * The listener interface for receiving localDocument events. The class that
	 * is interested in processing a localDocument event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's <code>addLocalDocumentListener</code>
	 * method. When the localDocument event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see LocalDocumentEvent
	 */
	private class LocalDocumentListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			// nop
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			EventQueue.invokeLater(new Runnable() {
				// The model is updated outside the GUI thread.
				// Doing this outside the GUI thread can cause a deadlock.
				@Override
				public void run() {
					if (scrollsOnAppends) {
						textArea.scrollRectToVisible(new Rectangle(0, Short.MAX_VALUE, 1, Short.MAX_VALUE));
					}
				}
			});
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// nop
		}
	}
}
