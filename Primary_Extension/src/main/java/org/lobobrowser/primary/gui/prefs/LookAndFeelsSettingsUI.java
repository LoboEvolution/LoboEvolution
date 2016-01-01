/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.primary.gui.prefs;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.lobobrowser.primary.gui.AbstractSettingsUI;
import org.lobobrowser.primary.gui.CheckBoxPanel;
import org.lobobrowser.primary.gui.FieldType;
import org.lobobrowser.primary.gui.FormField;
import org.lobobrowser.primary.gui.FormPanel;
import org.lobobrowser.primary.gui.ValidationException;
import org.lobobrowser.util.gui.LAFSettings;

/**
 * The Class LookAndFeelsSettingsUI.
 */
public class LookAndFeelsSettingsUI extends AbstractSettingsUI {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Acryl panel. */
	private final CheckBoxPanel acrylPanel;

	/** The Aero panel. */
	private final CheckBoxPanel aeroPanel;

	/** The Aluminium panel. */
	private final CheckBoxPanel aluminiumPanel;

	/** The Bernstein panel. */
	private final CheckBoxPanel bernsteinPanel;

	/** The Fast panel. */
	private final CheckBoxPanel fastPanel;

	/** The Graphite panel. */
	private final CheckBoxPanel graphitePanel;

	/** The HiFi panel. */
	private final CheckBoxPanel hiFiPanel;

	/** The Luna panel. */
	private final CheckBoxPanel lunaPanel;

	/** The McWin panel. */
	private final CheckBoxPanel mcWinPanel;

	/** The Mint panel. */
	private final CheckBoxPanel mintPanel;

	/** The Noire panel. */
	private final CheckBoxPanel noirePanel;

	/** The Smart panel. */
	private final CheckBoxPanel smartPanel;

	/** The Texture panel. */
	private final CheckBoxPanel texturePanel;
	
	/** The font panel. */
	private final FormPanel fontPanel;
	
	/** The search engine list control. */
	private final JComboBox<String> fontList;	
	
	/** The network panel. */
	private final FormPanel lafPanel;
	
	/** The Font Size field. */
	private final FormField fontSizeField;

	/** The settings. */
	private final LAFSettings settings = LAFSettings.getInstance();
	
	public LookAndFeelsSettingsUI() {
		FormPanel lafPanel = new FormPanel();
		this.lafPanel = lafPanel;
		this.fontList = new JComboBox<String>(LAFSettings.FONTS);
		this.fontList.setToolTipText("Choose your favorite font");
		this.fontList.setSelectedItem(LAFSettings.TIMES_NEW_ROMAN);
		lafPanel.setBorder(new EmptyBorder(1, 8, 8, 0));
		this.acrylPanel = new CheckBoxPanel(LAFSettings.ACRYL, lafPanel);
		this.aeroPanel = new CheckBoxPanel(LAFSettings.AERO, lafPanel);
		this.aluminiumPanel = new CheckBoxPanel(LAFSettings.ALUMINIUM, lafPanel);
		this.bernsteinPanel = new CheckBoxPanel(LAFSettings.BERNSTEIN, lafPanel);
		this.fastPanel = new CheckBoxPanel(LAFSettings.FAST, lafPanel);
		this.graphitePanel = new CheckBoxPanel(LAFSettings.GRAPHITE, lafPanel);
		this.hiFiPanel = new CheckBoxPanel(LAFSettings.HIFI, lafPanel);
		this.lunaPanel = new CheckBoxPanel(LAFSettings.LUNA, lafPanel);
		this.mcWinPanel = new CheckBoxPanel(LAFSettings.MCWIN, lafPanel);
		this.mintPanel = new CheckBoxPanel(LAFSettings.MINT, lafPanel);
		this.noirePanel = new CheckBoxPanel(LAFSettings.NOIRE, lafPanel);
		this.smartPanel = new CheckBoxPanel(LAFSettings.SMART, lafPanel);
		this.texturePanel = new CheckBoxPanel(LAFSettings.TEXTURE, lafPanel);
		this.fontPanel = new FormPanel();
		this.fontSizeField = new FormField(FieldType.TEXT, "Font Size:");
		this.fontSizeField.setToolTip("Choose your favorite size");
		this.fontPanel.addField(this.fontSizeField);
		

		JPanel groupBox = new JPanel();
		groupBox.setPreferredSize(new Dimension(100, 150));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.add(this.getAcrylPanel());
		groupBox.add(this.getAeroPanel());
		groupBox.add(this.getAluminiumPanel());
		groupBox.add(this.getBernsteinPanel());
		add(groupBox);

		JPanel group1Box = new JPanel();
		group1Box.setPreferredSize(new Dimension(100, 150));
		group1Box.setLayout(new BoxLayout(group1Box, BoxLayout.Y_AXIS));
		group1Box.add(this.getFastPanel());
		group1Box.add(this.getGraphitePanel());
		group1Box.add(this.getHiFiPanel());
		group1Box.add(this.getLunaPanel());
		add(group1Box);

		JPanel group2Box = new JPanel();
		group2Box.setPreferredSize(new Dimension(100, 150));
		group2Box.setLayout(new BoxLayout(group2Box, BoxLayout.Y_AXIS));
		group2Box.add(this.getMcWinPanel());
		group2Box.add(this.getMintPanel());
		group2Box.add(this.getNoirePanel());
		group2Box.add(this.getSmartPanel());
		group2Box.add(this.getTexturePanel());
		add(group2Box);
		
		add(getFontGroupBox());

		this.loadSettings();
		this.acrylPanel.updateEnabling();
		this.aeroPanel.updateEnabling();
		this.aluminiumPanel.updateEnabling();
		this.bernsteinPanel.updateEnabling();
		this.fastPanel.updateEnabling();
		this.graphitePanel.updateEnabling();
		this.hiFiPanel.updateEnabling();
		this.lunaPanel.updateEnabling();
		this.mcWinPanel.updateEnabling();
		this.mintPanel.updateEnabling();
		this.noirePanel.updateEnabling();
		this.smartPanel.updateEnabling();
		this.texturePanel.updateEnabling();
	}

	@Override
	public void save() throws ValidationException {
		LAFSettings settings = this.settings;
		settings.setAcryl(this.acrylPanel.isSelected());
		settings.setAero(this.aeroPanel.isSelected());
		settings.setAluminium(this.aluminiumPanel.isSelected());
		settings.setBernstein(this.bernsteinPanel.isSelected());
		settings.setFast(this.fastPanel.isSelected());
		settings.setGraphite(this.graphitePanel.isSelected());
		settings.setHiFi(this.hiFiPanel.isSelected());
		settings.setLuna(this.lunaPanel.isSelected());
		settings.setMcWin(this.mcWinPanel.isSelected());
		settings.setMint(this.mintPanel.isSelected());
		settings.setNoire(this.noirePanel.isSelected());
		settings.setSmart(this.smartPanel.isSelected());
		settings.setTexture(this.texturePanel.isSelected());
		settings.setFontSize(new Float(this.fontSizeField.getValue()));
		settings.setFont(this.getFontList().getSelectedItem().toString());
		if (validate(settings)) {
			settings.save();
		}
	}

	private void loadSettings() {
		LAFSettings settings = this.settings;
		acrylPanel.setSelected(settings.isAcryl());
		aeroPanel.setSelected(settings.isAero());
		aluminiumPanel.setSelected(settings.isAluminium());
		bernsteinPanel.setSelected(settings.isBernstein());
		fastPanel.setSelected(settings.isFast());
		graphitePanel.setSelected(settings.isGraphite());
		hiFiPanel.setSelected(settings.isHiFi());
		lunaPanel.setSelected(settings.isLuna());
		mcWinPanel.setSelected(settings.isMcWin());
		mintPanel.setSelected(settings.isMint());
		noirePanel.setSelected(settings.isNoire());
		smartPanel.setSelected(settings.isSmart());
		texturePanel.setSelected(settings.isTexture());
		fontSizeField.setValue(String.valueOf(settings.getFontSize()));
		getFontList().setSelectedItem(settings.getFont());
		acrylPanel.revalidate();
		acrylPanel.revalidate();
		aeroPanel.revalidate();
		aluminiumPanel.revalidate();
		bernsteinPanel.revalidate();
		fastPanel.revalidate();
		graphitePanel.revalidate();
		hiFiPanel.revalidate();
		lunaPanel.revalidate();
		mcWinPanel.revalidate();
		mintPanel.revalidate();
		noirePanel.revalidate();
		smartPanel.revalidate();
		texturePanel.revalidate();
		lafPanel.revalidate();
		fontPanel.revalidate();

	}

	@Override
	public void restoreDefaults() {
		acrylPanel.setSelected(false);
		aeroPanel.setSelected(true);
		aluminiumPanel.setSelected(false);
		bernsteinPanel.setSelected(false);
		fastPanel.setSelected(false);
		graphitePanel.setSelected(false);
		hiFiPanel.setSelected(false);
		lunaPanel.setSelected(false);
		mcWinPanel.setSelected(false);
		mintPanel.setSelected(false);
		noirePanel.setSelected(false);
		smartPanel.setSelected(false);
		texturePanel.setSelected(false);
		fontSizeField.setValue("14.0");
		getFontList().setSelectedItem("TimesNewRoman");
		acrylPanel.revalidate();
		acrylPanel.revalidate();
		aeroPanel.revalidate();
		aluminiumPanel.revalidate();
		bernsteinPanel.revalidate();
		fastPanel.revalidate();
		graphitePanel.revalidate();
		hiFiPanel.revalidate();
		lunaPanel.revalidate();
		mcWinPanel.revalidate();
		mintPanel.revalidate();
		noirePanel.revalidate();
		smartPanel.revalidate();
		texturePanel.revalidate();
		fontPanel.revalidate();
		lafPanel.revalidate();

	}

	public boolean validate(LAFSettings settings) {

		int check = 0;
		if (settings.isAcryl())
			check = check + 1;
		if (settings.isAero())
			check = check + 1;
		if (settings.isAluminium())
			check = check + 1;
		if (settings.isBernstein())
			check = check + 1;
		if (settings.isFast())
			check = check + 1;
		if (settings.isGraphite())
			check = check + 1;
		if (settings.isHiFi())
			check = check + 1;
		if (settings.isLuna())
			check = check + 1;
		if (settings.isMcWin())
			check = check + 1;
		if (settings.isMint())
			check = check + 1;
		if (settings.isNoire())
			check = check + 1;
		if (settings.isSmart())
			check = check + 1;
		if (settings.isTexture())
			check = check + 1;

		if (check == 0) {
			JOptionPane.showMessageDialog(this, "Checks a Look & Feels");
			return false;
		} else if (check > 1) {
			JOptionPane.showMessageDialog(this, "Checks only one Look & Feels");
			return false;
		} else {
			JOptionPane.showMessageDialog(this, "Please restart Lobo Evolution");
			return true;
		}
	}
	
	
	/**
	 * Gets the font group box.
	 *
	 * @return the font group box
	 */
	private Component getFontGroupBox() {
		JPanel groupBox = new JPanel();
		groupBox.setPreferredSize(new Dimension(400, 65));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.setBorder(new TitledBorder(new EtchedBorder(), "Font"));
		groupBox.add(this.getFontPanel());
		groupBox.add(this.getFontList());
		return groupBox;
	}

	/**
	 * Gets the Acryl panel.
	 *
	 * @return the Acryl panel
	 */
	public Component getAcrylPanel() {
		return acrylPanel;
	}

	/**
	 * Gets the Aero panel.
	 *
	 * @return the Aero panel
	 */
	public Component getAeroPanel() {
		return aeroPanel;
	}

	/**
	 * Gets the Aluminium panel.
	 *
	 * @return the Aluminium panel
	 */
	public Component getAluminiumPanel() {
		return aluminiumPanel;
	}

	/**
	 * Gets the Bernstein panel.
	 *
	 * @return the Bernstein panel
	 */
	public Component getBernsteinPanel() {
		return bernsteinPanel;
	}

	/**
	 * Gets the Fast panel.
	 *
	 * @return the Fast panel
	 */
	public Component getFastPanel() {
		return fastPanel;
	}

	/**
	 * Gets the Graphite panel.
	 *
	 * @return the Graphite panel
	 */
	public Component getGraphitePanel() {
		return graphitePanel;
	}

	/**
	 * Gets the HiFi panel.
	 *
	 * @return the HiFi panel
	 */
	public Component getHiFiPanel() {
		return hiFiPanel;
	}

	/**
	 * Gets the Luna panel.
	 *
	 * @return the Luna panel
	 */
	public Component getLunaPanel() {
		return lunaPanel;
	}

	/**
	 * Gets the McWin panel.
	 *
	 * @return the McWin panel
	 */
	public Component getMcWinPanel() {
		return mcWinPanel;
	}

	/**
	 * Gets the Mint panel.
	 *
	 * @return the Mint panel
	 */
	public Component getMintPanel() {
		return mintPanel;
	}

	/**
	 * Gets the Noire panel.
	 *
	 * @return the Noire panel
	 */
	public Component getNoirePanel() {
		return noirePanel;
	}

	/**
	 * Gets the Smart panel.
	 *
	 * @return the Smart panel
	 */
	public Component getSmartPanel() {
		return smartPanel;
	}

	/**
	 * Gets the Texture panel.
	 *
	 * @return the Texture panel
	 */
	public Component getTexturePanel() {
		return texturePanel;
	}

	/**
	 * Gets the Font Size field.
	 *
	 * @return the Font Size field
	 */
	public FormField getFontSizeField() {
		return fontSizeField;
	}

	/**
	 * Gets the font panel.
	 *
	 * @return the font panel
	 */
	public FormPanel getFontPanel() {
		return fontPanel;
	}

	/**
	 * Gets the search engine list control.
	 *
	 * @return the search engine list control
	 */
	public JComboBox<String> getFontList() {
		return fontList;
	}

}
