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
package org.lobobrowser.http;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * The Class DefaultSecurityHandler.
 *
 * @author rbair
 */
public class DefaultSecurityHandler implements SecurityHandler {
    
    /** The message. */
    private String message = "<html><body><p style=\"text-align:justify\">"
            + "<b>Java can''t verify the identity of the website "
            + "\"{0}\".</b></p>" + "<p style=\"text-align:justify;font:11\">"
            + "The certificate for this website was signed by "
            + "an unknown certifying authority, or was self-signed. You might be "
            + "connecting to a website that is pretending to be \"{0}\", which could "
            + "put your confidential information at risk. Would you like to connect to "
            + "the website anyway?</p></body></html>";
    
    /** The accepted. */
    private boolean accepted = false;
    
    /** The message pane. */
    private JEditorPane messagePane;

    private JDialog createDialog() {
        final JDialog dlg = new JDialog();
        dlg.setModal(true);
        // create and configure the components
        messagePane = new JEditorPane();
        messagePane.setContentType("text/html");
        messagePane.setEditable(false);
        messagePane.setOpaque(false);
        messagePane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES,
                Boolean.TRUE);
        Action continueAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                accepted = true;
                dlg.setVisible(false);
            }
        };
        continueAction.putValue(Action.NAME, "Continue");
        Action cancelAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                accepted = false;
                dlg.setVisible(false);
            }
        };
        cancelAction.putValue(Action.NAME, "Cancel");
        Action showCertAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO!!!
            }
        };
        showCertAction.putValue(Action.NAME, "Show Certificate");
        JLabel iconLabel = new JLabel(new ImageIcon(
                getClass().getResource("resources/security-warning.png")));
        // add the components to their containers
        JButton continueButton = new JButton(continueAction);
        JButton cancelButton = new JButton(cancelAction);
        JButton showCertButton = new JButton(showCertAction);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(continueButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(showCertButton);
        dlg.add(iconLabel);
        dlg.add(messagePane);
        dlg.add(buttonPanel);
        // Layout the components
        GridBagLayout gbl = new GridBagLayout();
        gbl.addLayoutComponent(iconLabel,
                new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0,
                        GridBagConstraints.NORTH, GridBagConstraints.BOTH,
                        new Insets(0, 24, 0, 0), 0, 0));
        gbl.addLayoutComponent(messagePane,
                new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                        GridBagConstraints.NORTH, GridBagConstraints.BOTH,
                        new Insets(0, 24, 0, 11), 0, 0));
        gbl.addLayoutComponent(buttonPanel,
                new GridBagConstraints(0, 2, 2, 1, 0.0, 1.0,
                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                        new Insets(24, 12, 11, 11), 0, 0));
        dlg.setLayout(gbl);
        GridBagLayout panelLayout = new GridBagLayout();
        panelLayout.addLayoutComponent(showCertButton,
                new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                        GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        panelLayout.addLayoutComponent(cancelButton,
                new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        panelLayout.addLayoutComponent(continueButton,
                new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        buttonPanel.setLayout(panelLayout);
        dlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dlg.setSize(535, 185);
        return dlg;
    }

    @Override
    public boolean isServerTrusted(String host, X509Certificate cert) {
        accepted = false;
        JDialog dlg = createDialog();
        // populate the fields
        messagePane.setText(MessageFormat.format(message, host));
        // show the dialog
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
        return accepted;
    }
}
