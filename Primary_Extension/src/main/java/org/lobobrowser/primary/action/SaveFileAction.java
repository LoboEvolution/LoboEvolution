/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.action;

import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.lobobrowser.primary.ext.ActionPool;
import org.lobobrowser.primary.ext.ComponentSource;
import org.lobobrowser.ua.NavigatorWindow;

/**
 * The Class SaveFileAction.
 */
public class SaveFileAction extends ActionPool {

    /** The window. */
    private NavigatorWindow window;

    /**
     * Instantiates a new save file action.
     *
     * @param componentSource
     *            the component source
     * @param window
     *            the window
     */
    public SaveFileAction(ComponentSource componentSource,
            NavigatorWindow window) {
        super(componentSource, window);
        this.window = window;
    }

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /*
     * (non-Javadoc)
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileNameExtensionFilter filterHtml = new FileNameExtensionFilter(
                "Hyper Text Markup Language", ".html");
        FileNameExtensionFilter filterTxt = new FileNameExtensionFilter("Text",
                ".txt");

        fileChooser.setFileFilter(filterHtml);
        fileChooser.setFileFilter(filterTxt);
        fileChooser.setAcceptAllFileFilterUsed(true);

        int returnValue = fileChooser.showSaveDialog(window.getTopFrame()
                .getComponent());

        if (returnValue == JFileChooser.APPROVE_OPTION) {

            File selectedFile = getSelectedFileWithExtension(fileChooser);
            if (selectedFile.exists()) {
                int response = JOptionPane.showConfirmDialog(null,
                        "Overwrite existing file?", "Confirm Overwrite",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }
            String source = window.getTopFrame().getSourceCode();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                baos.write(source.getBytes());
                OutputStream ops = new FileOutputStream(selectedFile);
                baos.writeTo(ops);
                baos.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    baos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    /**
     * Returns the selected file from a JFileChooser, including the extension
     * from the file filter.
     *
     * @param c
     *            the c
     * @return the selected file with extension
     */
    private File getSelectedFileWithExtension(JFileChooser c) {
        File file = c.getSelectedFile();
        if (c.getFileFilter() instanceof FileNameExtensionFilter) {
            String[] exts = ((FileNameExtensionFilter) c.getFileFilter())
                    .getExtensions();
            String nameLower = file.getName().toLowerCase();
            for (String ext : exts) { // check if it already has a valid
                // extension
                if (nameLower.endsWith('.' + ext.toLowerCase())) {
                    return file; // if yes, return as-is
                }
            }
            // if not, append the first extension from the selected filter
            file = new File(file.toString() + exts[0]);
        }
        return file;
    }
}
