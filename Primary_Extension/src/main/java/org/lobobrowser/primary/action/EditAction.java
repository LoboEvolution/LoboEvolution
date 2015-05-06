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

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.lobobrowser.primary.gui.SimpleTextEditDialog;
import org.lobobrowser.primary.gui.StringListControl;
import org.lobobrowser.primary.gui.SwingTasks;

/**
 * The Class EditAction.
 */
public class EditAction extends AbstractAction {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The control. */
    private StringListControl control;

    public EditAction(StringListControl control) {
        this.control = control;
    }

    /*
     * (non-Javadoc)
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Frame parentFrame = SwingTasks.getFrame(control);
        SimpleTextEditDialog dialog;
        if (parentFrame != null) {
            dialog = new SimpleTextEditDialog(parentFrame);
        } else {
            Dialog parentDialog = SwingTasks.getDialog(control);
            dialog = new SimpleTextEditDialog(parentDialog);
        }
        dialog.setModal(true);
        dialog.setTitle("Edit List");
        dialog.setCaption(control.getEditListCaption());
        dialog.setSize(new Dimension(400, 300));
        dialog.setLocationByPlatform(true);
        dialog.setText(control.getStringsAsText());
        dialog.setVisible(true);
        String text = dialog.getResultingText();
        if (text != null) {
            control.setStringsFromText(text);
        }
    }
}
