/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.ext;

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.loboevolution.info.MetaInfo;

/**
 * The Class SourceViewerWindow.
 */
public class InfoPageWindow extends JFrame {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(InfoPageWindow.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new text viewer window.
	 */
	public InfoPageWindow(List<MetaInfo> infoList) {
		super("Lobo Info Page");
		setResizable(true);
		setLocationRelativeTo(null);
		addContent(infoList);
	}

	@Override
	public Image getIconImage() {
		return new ImageIcon(getClass().getResource("/org/loboevolution/images/info.png")).getImage();
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	private void addContent(List<MetaInfo> infoList) {
		try {
			Object columnNames[] = {"", ""};
			List<String[]> values = new ArrayList<String[]>();
			for (MetaInfo info : infoList) {				
				if (!Strings.isBlank(info.getName())) {
					values.add(new String[] { info.getName(), info.getContent()});
				} else if (!Strings.isBlank(info.getItemprop())) {
					values.add(new String[] { info.getItemprop(), info.getContent()});
				} else if (!Strings.isBlank(info.getProperty())) {
					values.add(new String[] { info.getProperty(), info.getContent()});
				} else if (!Strings.isBlank(info.getHttpEqui())) {
					values.add(new String[] { info.getHttpEqui(), info.getContent()});
				}
			}
			
			DefaultTableModel model = new DefaultTableModel(values.toArray(new Object[][] {}), columnNames);
			JTable jtable = new JTable(model);
			jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
	        jtable.setTableHeader(null);
			jtable.setShowGrid(false);
			JScrollPane scrollPane = new JScrollPane(jtable);
			add(scrollPane, BorderLayout.CENTER);
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
