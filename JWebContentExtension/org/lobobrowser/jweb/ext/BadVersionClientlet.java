/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.jweb.ext;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.clientlet.ComponentContent;
import org.lobobrowser.clientlet.SimpleComponentContent;
import org.lobobrowser.util.gui.CenterLayout;

public class BadVersionClientlet implements Clientlet {
	private final String feature;
	private final String requiredVersion;

	public BadVersionClientlet(String feature, String requiredVersion) {
		this.feature = feature;
		this.requiredVersion = requiredVersion;
	}

	public void process(ClientletContext context) throws ClientletException {
		JTextArea textArea = new JTextArea();
		Border etchedBorder = new EtchedBorder(EtchedBorder.RAISED);
		Border cborder1 = new CompoundBorder(new EmptyBorder(10, 10, 10, 10),
				etchedBorder);
		Border cborder2 = new CompoundBorder(cborder1, new EmptyBorder(10, 10,
				10, 10));
		textArea.setBorder(cborder2);
		textArea.setBackground(new Color(255, 200, 180));
		textArea.setEditable(false);
		textArea.setWrapStyleWord(false);
		textArea.setFont(new Font("Arial", Font.BOLD, 16));
		textArea.setText("ERROR: A JRE supporting "
				+ feature
				+ " is required to process this document"
				+ (this.requiredVersion != null ? ", that is, JRE "
						+ this.requiredVersion + ".\r\n" : ".\r\n")
				+ "The current Java version is "
				+ System.getProperty("java.version") + ".\r\n");
		JScrollPane spane = new JScrollPane(textArea);
		JPanel resultingPanel = new JPanel();
		resultingPanel.setLayout(CenterLayout.getInstance());
		resultingPanel.add(spane);
		ComponentContent content = new SimpleComponentContent(resultingPanel,
				"Version Error", "");
		context.setResultingContent(content);
	}
}
