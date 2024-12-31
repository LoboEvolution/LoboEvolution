/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.menu.crono;

import com.jtattoo.plaf.lobo.*;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.component.BrowserFrame;
import org.loboevolution.download.DownloadWindow;
import org.loboevolution.config.DesktopConfig;
import org.loboevolution.store.DownloadStore;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.List;

/**
 * <p>ShowDowlaodWindow class.</p>
 */
@Slf4j
public class ShowDowlaodWindow extends JFrame {

    /** The Constant serialVersionUID. */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for ShowDowlaodWindow.</p>
     *
     * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
     */
    public ShowDowlaodWindow(final BrowserFrame frame) {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        final LoboBackground lb = new LoboBackground();
        final Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(lb.getBackground());
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        final ImageIcon ico = new ImageIcon(DesktopConfig.getResourceFile("download.png",DesktopConfig.PATH_IMAGE));
        setIconImage(ico.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

        final LoboLabel label_6 = new LoboLabel("");
        label_6.setOpaque(true);
        label_6.setBounds(0, 11, 592, 8);
        contentPane.add(label_6);

        listHost(contentPane);
    }

    private void listHost(final Container contentPane) {
        final LoboSeparator separator_7 = new LoboSeparator();
        separator_7.setBounds(0, 98, 550, 12);
        contentPane.add(separator_7);

        final LoboPanel panel = new LoboPanel();
        panel.setLayout(null);
        panel.setBounds(0, 40, 500, 50);
        contentPane.add(panel);

        final LoboLabel url = new LoboLabel("Download");
        url.setFont(new Font("Tahoma", Font.BOLD, 14));
        url.setBounds(100, 18, 160, 17);
        panel.add(url);

        final LoboPanel panelGeneralViewAllItems = new LoboPanel();
        panelGeneralViewAllItems.setBounds(10, 100, 555, 313);
        final JScrollPane spViewallItems = new JScrollPane();
        spViewallItems.setBorder(null);
        spViewallItems.setViewportView(createItemPanel());

        panelGeneralViewAllItems.setLayout(new BorderLayout());
        panelGeneralViewAllItems.add(spViewallItems, BorderLayout.CENTER);

        contentPane.add(panelGeneralViewAllItems);

    }

    private LoboPanel createItemPanel() {

        final LoboPanel panel_3 = new LoboPanel();
        panel_3.setLayout(null);
        panel_3.setBounds(0, 191, 300, 70);

        int debutCpDesc = 15;
        int debutCpTitle = 15;
        int debutCpUrl = 15;
        int debutCpSeparator = 58;
        final int incrementNouvelleLigne = 67;

        try {
            final DownloadStore store = new DownloadStore();
            final List<String> hostEntries = store.getDownload();
            for (final String host : hostEntries) {
                final LoboTextField url = new LoboTextField();
                url.setText(host);
                url.setToolTipText(host);
                url.setFont(new Font("Tahoma", Font.BOLD, 12));
                url.setEditable(false);
                url.setColumns(10);
                url.setBorder(null);
                url.setBounds(12, debutCpDesc, 420, 22);
                panel_3.add(url);

                final LoboSeparator separatorItem = new LoboSeparator();
                separatorItem.setBounds(0, debutCpSeparator, 600, 7);
                panel_3.add(separatorItem);
                
                final LoboButton dwnl = new LoboButton();
                dwnl.setText("Download");
                dwnl.setActionCommand("dwnlButton");
                dwnl.setBounds(440, debutCpUrl, 80, 40);
                dwnl.addActionListener(e -> new DownloadWindow().downloadFile(host));
				panel_3.add(dwnl);

                debutCpDesc = debutCpDesc + incrementNouvelleLigne;
                debutCpTitle = debutCpTitle + incrementNouvelleLigne;
                debutCpUrl = debutCpUrl + incrementNouvelleLigne;
                debutCpSeparator = debutCpSeparator + incrementNouvelleLigne;
            }

        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }

        panel_3.setPreferredSize(new Dimension(0, 700));
        panel_3.revalidate();
        panel_3.repaint();

        return panel_3;
    }
}
