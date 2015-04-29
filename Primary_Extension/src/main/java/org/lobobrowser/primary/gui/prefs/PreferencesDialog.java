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
package org.lobobrowser.primary.gui.prefs;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * The Class PreferencesDialog.
 */
public class PreferencesDialog extends JDialog {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The preferences panel. */
    private final PreferencesPanel preferencesPanel;

    /** The preferences tree. */
    private final PreferencesTree preferencesTree;

    /**
     * Instantiates a new preferences dialog.
     *
     * @param parent
     *            the parent
     * @throws HeadlessException
     *             the headless exception
     */
    public PreferencesDialog(Frame parent) throws HeadlessException {
        super(parent);
        this.preferencesPanel = new PreferencesPanel();
        this.preferencesTree = new PreferencesTree();
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.add(this.createLeftPane());
        contentPane.add(this.createRightPane(this.preferencesPanel));
        this.preferencesTree.initSelection();
    }

    /**
     * Creates the left pane.
     *
     * @return the component
     */
    private Component createLeftPane() {
        PreferencesTree prefsTree = this.preferencesTree;
        prefsTree.addTreeSelectionListener(new LocalTreeSelectionListener());
        JScrollPane scrollPane = new JScrollPane(prefsTree);
        Dimension size = new Dimension(150, 200);
        scrollPane.setPreferredSize(size);
        scrollPane.setMinimumSize(size);
        scrollPane.setMaximumSize(new Dimension(150, Short.MAX_VALUE));
        return scrollPane;
    }

    /**
     * Creates the right pane.
     *
     * @param prefsPanel
     *            the prefs panel
     * @return the component
     */
    private Component createRightPane(Container prefsPanel) {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(prefsPanel);
        rightPanel.add(this.createButtonsPanel());
        return rightPanel;
    }

    /**
     * Creates the buttons panel.
     *
     * @return the component
     */
    private Component createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.add(Box.createHorizontalGlue());
        JButton okButton = new JButton();
        okButton.setAction(new OkAction());
        okButton.setText("OK");
        JButton cancelButton = new JButton();
        cancelButton.setAction(new CancelAction());
        cancelButton.setText("Cancel");
        JButton applyButton = new JButton();
        applyButton.setAction(new ApplyAction());
        applyButton.setText("Apply");
        JButton defaultsButton = new JButton();
        defaultsButton.setAction(new DefaultsAction());
        defaultsButton.setText("Restore Defaults");
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(applyButton);
        buttonsPanel.add(defaultsButton);
        return buttonsPanel;
    }

    /**
     * Update preferences panel.
     *
     * @param settingsInfo
     *            the settings info
     */
    private void updatePreferencesPanel(SettingsInfo settingsInfo) {
        if (settingsInfo != null) {
            AbstractSettingsUI newUI = settingsInfo.createSettingsUI();
            preferencesPanel.setSettingsUI(newUI);
        } else {
            preferencesPanel.setSettingsUI(null);
        }
    }

    /**
     * The Class OkAction.
     */
    private class OkAction extends AbstractAction {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 1L;

        /*
         * (non-Javadoc)
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (preferencesPanel.save()) {
                PreferencesDialog.this.dispose();
            }
        }
    }

    /**
     * The Class CancelAction.
     */
    private class CancelAction extends AbstractAction {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 1L;

        /*
         * (non-Javadoc)
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            PreferencesDialog.this.dispose();
        }
    }

    /**
     * The Class ApplyAction.
     */
    private class ApplyAction extends AbstractAction {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 1L;

        /*
         * (non-Javadoc)
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            preferencesPanel.save();
        }
    }

    /**
     * The Class DefaultsAction.
     */
    private class DefaultsAction extends AbstractAction {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 1L;

        /*
         * (non-Javadoc)
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (JOptionPane.showConfirmDialog(PreferencesDialog.this,
                    "Are you sure you want to restore defaults?", "Confirm",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                preferencesPanel.restoreDefaults();
            }
        }
    }

    /**
     * The listener interface for receiving localTreeSelection events. The class
     * that is interested in processing a localTreeSelection event implements
     * this interface, and the object created with that class is registered with
     * a component using the component's
     * <code>addLocalTreeSelectionListener</code> method. When the
     * localTreeSelection event occurs, that object's appropriate method is
     * invoked.
     *
     * @see LocalTreeSelectionEvent
     */
    private class LocalTreeSelectionListener implements TreeSelectionListener {

        /*
         * (non-Javadoc)
         * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.
         * TreeSelectionEvent)
         */
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            TreePath path = e.getPath();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
                    .getLastPathComponent();
            SettingsInfo si = node == null ? null : (SettingsInfo) node
                    .getUserObject();
            updatePreferencesPanel(si);
        }
    }
}
