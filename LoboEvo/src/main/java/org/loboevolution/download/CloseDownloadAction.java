package org.loboevolution.download;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CloseDownloadAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private DownloadWindow action;

    CloseDownloadAction(DownloadWindow action){
        this.action = action;

    }

    public void actionPerformed(ActionEvent e) {
        action.dispose();
    }
}
