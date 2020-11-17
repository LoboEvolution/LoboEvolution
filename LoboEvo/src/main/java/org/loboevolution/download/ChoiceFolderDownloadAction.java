package org.loboevolution.download;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class ChoiceFolderDownloadAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private final DownloadWindow action;

    ChoiceFolderDownloadAction(DownloadWindow action){
        this.action = action;
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showSaveDialog(action) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            action.setDestinationFile(chooser.getSelectedFile());
            action.destinationField.setValue(file.getAbsolutePath());
            action.destinationField.setToolTip(file.getAbsolutePath());
        }
    }
}