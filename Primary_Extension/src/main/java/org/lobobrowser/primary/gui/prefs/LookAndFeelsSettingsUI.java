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
package org.lobobrowser.primary.gui.prefs;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.lobobrowser.primary.gui.CheckBoxPanel;
import org.lobobrowser.primary.gui.FormPanel;
import org.lobobrowser.primary.gui.ValidationException;
import org.lobobrowser.settings.LAFSettings;

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

	/** The network panel. */
	private final FormPanel lafPanel;

	/** The settings. */
	private final LAFSettings settings = LAFSettings.getInstance();

	public LookAndFeelsSettingsUI() {
		FormPanel lafPanel = new FormPanel();
		this.lafPanel = lafPanel;
		lafPanel.setBorder(new EmptyBorder(1, 8, 8, 0));
		this.acrylPanel = new CheckBoxPanel("Acryl", lafPanel);
		this.aeroPanel = new CheckBoxPanel("Aero", lafPanel);
		this.aluminiumPanel = new CheckBoxPanel("Aluminium", lafPanel);
		this.bernsteinPanel = new CheckBoxPanel("Bernstein", lafPanel);
		this.fastPanel = new CheckBoxPanel("Fast", lafPanel);
		this.graphitePanel = new CheckBoxPanel("Graphite", lafPanel);
		this.hiFiPanel = new CheckBoxPanel("HiFi", lafPanel);
		this.lunaPanel = new CheckBoxPanel("Luna", lafPanel);
		this.mcWinPanel = new CheckBoxPanel("McWin", lafPanel);
		this.mintPanel = new CheckBoxPanel("Mint", lafPanel);
		this.noirePanel = new CheckBoxPanel("Noire", lafPanel);
		this.smartPanel = new CheckBoxPanel("Smart", lafPanel);
		this.texturePanel = new CheckBoxPanel("Texture", lafPanel);

		JPanel groupBox = new JPanel();
		groupBox.setPreferredSize(new Dimension(100, 100));
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.add(this.getAcrylPanel());
		groupBox.add(this.getAeroPanel());
		groupBox.add(this.getAluminiumPanel());
		groupBox.add(this.getBernsteinPanel());
		add(groupBox);

		JPanel group1Box = new JPanel();
		group1Box.setPreferredSize(new Dimension(100, 100));
		group1Box.setLayout(new BoxLayout(group1Box, BoxLayout.Y_AXIS));
		group1Box.add(this.getFastPanel());
		group1Box.add(this.getGraphitePanel());
		group1Box.add(this.getHiFiPanel());
		group1Box.add(this.getLunaPanel());
		add(group1Box);

		JPanel group2Box = new JPanel();
		group2Box.setPreferredSize(new Dimension(100, 130));
		group2Box.setLayout(new BoxLayout(group2Box, BoxLayout.Y_AXIS));
		group2Box.add(this.getMcWinPanel());
		group2Box.add(this.getMintPanel());
		group2Box.add(this.getNoirePanel());
		group2Box.add(this.getSmartPanel());
		group2Box.add(this.getTexturePanel());
		add(group2Box);

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
	 * @return the acrylPanel
	 */
	public Component getAcrylPanel() {
		return acrylPanel;
	}

	/**
	 * @return the aeroPanel
	 */
	public Component getAeroPanel() {
		return aeroPanel;
	}

	/**
	 * @return the aluminiumPanel
	 */
	public Component getAluminiumPanel() {
		return aluminiumPanel;
	}

	/**
	 * @return the bernsteinPanel
	 */
	public Component getBernsteinPanel() {
		return bernsteinPanel;
	}

	/**
	 * @return the fastPanel
	 */
	public Component getFastPanel() {
		return fastPanel;
	}

	/**
	 * @return the graphitePanel
	 */
	public Component getGraphitePanel() {
		return graphitePanel;
	}

	/**
	 * @return the hiFiPanel
	 */
	public Component getHiFiPanel() {
		return hiFiPanel;
	}

	/**
	 * @return the lunaPanel
	 */
	public Component getLunaPanel() {
		return lunaPanel;
	}

	/**
	 * @return the mcWinPanel
	 */
	public Component getMcWinPanel() {
		return mcWinPanel;
	}

	/**
	 * @return the mintPanel
	 */
	public Component getMintPanel() {
		return mintPanel;
	}

	/**
	 * @return the noirePanel
	 */
	public Component getNoirePanel() {
		return noirePanel;
	}

	/**
	 * @return the smartPanel
	 */
	public Component getSmartPanel() {
		return smartPanel;
	}

	/**
	 * @return the texturePanel
	 */
	public Component getTexturePanel() {
		return texturePanel;
	}

}
