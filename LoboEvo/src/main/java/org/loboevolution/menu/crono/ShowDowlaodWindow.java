/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.menu.crono;

import com.jtattoo.plaf.lobo.*;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.download.DownloadWindow;
import org.loboevolution.store.DownloadStore;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * <p>ShowDowlaodWindow class.</p>
 *
 *
 *
 */
public class ShowDowlaodWindow extends JFrame implements LoboLookAndFeel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for ShowDowlaodWindow.</p>
     *
     * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
     */
    public ShowDowlaodWindow(BrowserFrame frame) {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        final Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(background());
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        final ImageIcon ico = new ImageIcon(getClass().getResource("/org/lobo/image/download.png"));
        setIconImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

        LoboLabel label_6 = new LoboLabel("");
        label_6.setOpaque(true);
        label_6.setBounds(0, 11, 592, 8);
        contentPane.add(label_6);

        listHost(contentPane);
    }

    private void listHost(Container contentPane) {
        LoboSeparator separator_7 = new LoboSeparator();
        separator_7.setBounds(0, 98, 550, 12);
        contentPane.add(separator_7);

        LoboPanel panel = new LoboPanel();
        panel.setLayout(null);
        panel.setBounds(0, 40, 500, 50);
        contentPane.add(panel);

        LoboLabel url = new LoboLabel("Download");
        url.setFont(new Font("Tahoma", Font.BOLD, 14));
        url.setBounds(100, 18, 160, 17);
        panel.add(url);

        LoboPanel panelGeneralViewAllItems = new LoboPanel();
        panelGeneralViewAllItems.setBounds(10, 100, 555, 313);
        JScrollPane spViewallItems = new JScrollPane();
        spViewallItems.setBorder(null);
        spViewallItems.setViewportView(createItemPanel());

        panelGeneralViewAllItems.setLayout(new BorderLayout());
        panelGeneralViewAllItems.add(spViewallItems, BorderLayout.CENTER);

        contentPane.add(panelGeneralViewAllItems);

    }

    private LoboPanel createItemPanel() {

        LoboPanel panel_3 = new LoboPanel();
        panel_3.setLayout(null);
        panel_3.setBounds(0, 191, 300, 70);

        int debutCpDesc = 15;
        int debutCpTitle = 15;
        int debutCpUrl = 15;
        int debutCpSeparator = 58;
        int incrementNouvelleLigne = 67;

        try {
            final DownloadStore store = new DownloadStore();
            final List<String> hostEntries = store.getDownload();
            for (final String host : hostEntries) {
                LoboTextField url = new LoboTextField();
                url.setText(host);
                url.setToolTipText(host);
                url.setFont(new Font("Tahoma", Font.BOLD, 12));
                url.setEditable(false);
                url.setColumns(10);
                url.setBorder(null);
                url.setBounds(12, debutCpDesc, 420, 22);
                panel_3.add(url);

                LoboSeparator separatorItem = new LoboSeparator();
                separatorItem.setBounds(0, debutCpSeparator, 600, 7);
                panel_3.add(separatorItem);
                
                LoboButton dwnl = new LoboButton();
                dwnl.setText("Download");
                dwnl.setActionCommand("dwnlButton");
                dwnl.setBounds(440, debutCpUrl, 80, 40);
                dwnl.addActionListener(e -> {
					new DownloadWindow().downloadFile(host);

				});
				panel_3.add(dwnl);

                debutCpDesc = debutCpDesc + incrementNouvelleLigne;
                debutCpTitle = debutCpTitle + incrementNouvelleLigne;
                debutCpUrl = debutCpUrl + incrementNouvelleLigne;
                debutCpSeparator = debutCpSeparator + incrementNouvelleLigne;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        panel_3.setPreferredSize(new Dimension(0, 700));
        panel_3.revalidate();
        panel_3.repaint();

        return panel_3;
    }
}
