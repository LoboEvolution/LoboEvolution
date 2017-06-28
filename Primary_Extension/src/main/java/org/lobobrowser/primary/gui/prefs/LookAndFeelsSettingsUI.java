/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.gui.prefs;

import static java.awt.font.TextAttribute.FAMILY;
import static java.awt.font.TextAttribute.POSTURE;
import static java.awt.font.TextAttribute.POSTURE_OBLIQUE;
import static java.awt.font.TextAttribute.SIZE;
import static java.awt.font.TextAttribute.STRIKETHROUGH;
import static java.awt.font.TextAttribute.STRIKETHROUGH_ON;
import static java.awt.font.TextAttribute.SUPERSCRIPT;
import static java.awt.font.TextAttribute.SUPERSCRIPT_SUB;
import static java.awt.font.TextAttribute.SUPERSCRIPT_SUPER;
import static java.awt.font.TextAttribute.UNDERLINE;
import static java.awt.font.TextAttribute.UNDERLINE_LOW_ONE_PIXEL;
import static java.awt.font.TextAttribute.WEIGHT;
import static java.awt.font.TextAttribute.WEIGHT_BOLD;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.lobobrowser.primary.gui.AbstractSettingsUI;
import org.lobobrowser.primary.gui.ColorComboBox;
import org.lobobrowser.primary.gui.FontLabel;
import org.lobobrowser.primary.gui.ValidationException;
import org.lobobrowser.util.gui.LAFSettings;

/**
 * The Class LookAndFeelsSettingsUI.
 */
public class LookAndFeelsSettingsUI extends AbstractSettingsUI {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The settings. */
	private LAFSettings settings = LAFSettings.getInstance();

	private String PREVIEW_TEXT = "Preview Font";

	/** The Acryl Box. */
	private JCheckBox acrylCheckBox;

	/** The Aero Box. */
	private JCheckBox aeroCheckBox;

	/** The Aluminium Box. */
	private JCheckBox aluminiumCheckBox;

	/** The Bernstein Box. */
	private JCheckBox bernsteinCheckBox;

	/** The Fast Box. */
	private JCheckBox fastCheckBox;

	/** The Graphite Box. */
	private JCheckBox graphiteCheckBox;

	/** The HiFi Box. */
	private JCheckBox hiFiCheckBox;

	/** The Luna Box. */
	private JCheckBox lunaCheckBox;

	/** The McWin Box. */
	private JCheckBox mcWinCheckBox;

	/** The Mint Box. */
	private JCheckBox mintCheckBox;

	/** The Noire Box. */
	private JCheckBox noireCheckBox;

	/** The Smart Box. */
	private JCheckBox smartCheckBox;

	/** The Texture Box. */
	private JCheckBox textureCheckBox;

	/** The Bold Box. */
	private JCheckBox boldCheckBox;

	/** The Italic Box. */
	private JCheckBox italicCheckBox;

	/** The Underline Box. */
	private JCheckBox underlineCheckBox;

	/** The Strikethrough Box. */
	private JCheckBox strikethroughCheckBox;

	/** The Subscript Box. */
	private JCheckBox subscriptCheckBox;

	/** The Superscript Box. */
	private JCheckBox superscriptCheckBox;

	/** The color Combo Box. */
	private ColorComboBox colorComboBox;

	/** The search engine list control. */
	private JComboBox<String> fontList;

	/** The Font Size list. */
	private JComboBox<String> fontSizeList;

	protected FontLabel previewLabel;

	public LookAndFeelsSettingsUI() {

		JPanel groupBox = new JPanel();
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));

		boldCheckBox = new JCheckBox(LAFSettings.BOLD);
		italicCheckBox = new JCheckBox(LAFSettings.ITALIC);
		underlineCheckBox = new JCheckBox(LAFSettings.UNDERLINE);
		strikethroughCheckBox = new JCheckBox(LAFSettings.STRIKETHROUGH);
		subscriptCheckBox = new JCheckBox(LAFSettings.SUBSCRIPT);
		superscriptCheckBox = new JCheckBox(LAFSettings.SUPERSCRIPT);

		acrylCheckBox = new JCheckBox(LAFSettings.ACRYL);
		aeroCheckBox = new JCheckBox(LAFSettings.AERO);
		aluminiumCheckBox = new JCheckBox(LAFSettings.ALUMINIUM);
		bernsteinCheckBox = new JCheckBox(LAFSettings.BERNSTEIN);
		fastCheckBox = new JCheckBox(LAFSettings.FAST);
		graphiteCheckBox = new JCheckBox(LAFSettings.GRAPHITE);
		hiFiCheckBox = new JCheckBox(LAFSettings.HIFI);
		lunaCheckBox = new JCheckBox(LAFSettings.LUNA);
		mcWinCheckBox = new JCheckBox(LAFSettings.MCWIN);
		mintCheckBox = new JCheckBox(LAFSettings.MINT);
		noireCheckBox = new JCheckBox(LAFSettings.NOIRE);
		smartCheckBox = new JCheckBox(LAFSettings.SMART);
		textureCheckBox = new JCheckBox(LAFSettings.TEXTURE);
		colorComboBox = new ColorComboBox();
		colorComboBox.setPreferredSize(new Dimension(400, 20));
		fontList = new JComboBox<String>(LAFSettings.FONTS);
		fontSizeList = new JComboBox<String>(LAFSettings.FONTS_SIZE);

		ActionListener actionListener = e -> updatePreview();

		this.loadSettings();

		boldCheckBox.addActionListener(actionListener);
		italicCheckBox.addActionListener(actionListener);
		underlineCheckBox.addActionListener(actionListener);
		strikethroughCheckBox.addActionListener(actionListener);
		subscriptCheckBox.addActionListener(actionListener);
		superscriptCheckBox.addActionListener(actionListener);
		colorComboBox.addActionListener(actionListener);
		fontList.addActionListener(actionListener);
		fontSizeList.addActionListener(actionListener);

		groupBox.add(lookAndFeelsPanel());
		groupBox.add(fontPanel());
		groupBox.add(effectsPanel());
		groupBox.add(fontColorPanel());
		groupBox.add(previewPanel());

		add(groupBox);
	}

	@Override
	public void save() throws ValidationException {
		LAFSettings settings = this.settings;
		settings.setAcryl(this.acrylCheckBox.isSelected());
		settings.setAero(this.aeroCheckBox.isSelected());
		settings.setAluminium(this.aluminiumCheckBox.isSelected());
		settings.setBernstein(this.bernsteinCheckBox.isSelected());
		settings.setFast(this.fastCheckBox.isSelected());
		settings.setGraphite(this.graphiteCheckBox.isSelected());
		settings.setHiFi(this.hiFiCheckBox.isSelected());
		settings.setLuna(this.lunaCheckBox.isSelected());
		settings.setMcWin(this.mcWinCheckBox.isSelected());
		settings.setMint(this.mintCheckBox.isSelected());
		settings.setNoire(this.noireCheckBox.isSelected());
		settings.setSmart(this.smartCheckBox.isSelected());
		settings.setTexture(this.textureCheckBox.isSelected());
		settings.setBold(this.boldCheckBox.isSelected());
		settings.setItalic(this.italicCheckBox.isSelected());
		settings.setUnderline(this.underlineCheckBox.isSelected());
		settings.setStrikethrough(this.strikethroughCheckBox.isSelected());
		settings.setSubscript(this.subscriptCheckBox.isSelected());
		settings.setSuperscript(this.superscriptCheckBox.isSelected());
		settings.setFontSize(new Float(this.fontSizeList.getSelectedItem().toString()));
		settings.setFont(this.fontList.getSelectedItem().toString());
		settings.setColor((Color) colorComboBox.getSelectedItem());

		if (validate(settings)) {
			settings.save();
		}
	}

	@Override
	public void restoreDefaults() {
		acrylCheckBox.setSelected(false);
		aeroCheckBox.setSelected(true);
		aluminiumCheckBox.setSelected(false);
		bernsteinCheckBox.setSelected(false);
		fastCheckBox.setSelected(false);
		graphiteCheckBox.setSelected(false);
		hiFiCheckBox.setSelected(false);
		lunaCheckBox.setSelected(false);
		mcWinCheckBox.setSelected(false);
		mintCheckBox.setSelected(false);
		noireCheckBox.setSelected(false);
		smartCheckBox.setSelected(false);
		textureCheckBox.setSelected(false);
		boldCheckBox.setSelected(false);
		italicCheckBox.setSelected(false);
		underlineCheckBox.setSelected(false);
		strikethroughCheckBox.setSelected(false);
		subscriptCheckBox.setSelected(false);
		superscriptCheckBox.setSelected(false);
		fontSizeList.setSelectedItem("14");
		fontList.setSelectedItem(LAFSettings.TIMES_NEW_ROMAN);
		acrylCheckBox.revalidate();
		aeroCheckBox.revalidate();
		aluminiumCheckBox.revalidate();
		bernsteinCheckBox.revalidate();
		fastCheckBox.revalidate();
		graphiteCheckBox.revalidate();
		hiFiCheckBox.revalidate();
		lunaCheckBox.revalidate();
		mcWinCheckBox.revalidate();
		mintCheckBox.revalidate();
		noireCheckBox.revalidate();
		smartCheckBox.revalidate();
		textureCheckBox.revalidate();
		boldCheckBox.revalidate();
		italicCheckBox.revalidate();
		underlineCheckBox.revalidate();
		strikethroughCheckBox.revalidate();
		subscriptCheckBox.revalidate();
		superscriptCheckBox.revalidate();
		colorComboBox.setSelectedItem(Color.BLACK);

		previewLabel.setText(PREVIEW_TEXT);
		Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
		attributes.put(FAMILY, LAFSettings.TIMES_NEW_ROMAN);
		attributes.put(SIZE, (float) 14.0);
		previewLabel.setFont(new Font(attributes));
		previewLabel.setForeground(Color.BLACK);// TODO

	}

	private Component lookAndFeelsPanel() {
		JPanel p = new JPanel(new GridLayout(4, 3, 10, 5));
		p.setBorder(new TitledBorder(new EtchedBorder(), "Look And Feels"));
		p.add(acrylCheckBox);
		p.add(aeroCheckBox);
		p.add(aluminiumCheckBox);
		p.add(bernsteinCheckBox);
		p.add(fastCheckBox);
		p.add(graphiteCheckBox);
		p.add(hiFiCheckBox);
		p.add(lunaCheckBox);
		p.add(mcWinCheckBox);
		p.add(mintCheckBox);
		p.add(noireCheckBox);
		p.add(smartCheckBox);
		p.add(textureCheckBox);
		return p;
	}

	private Component fontPanel() {
		JPanel p = new JPanel(new GridLayout(1, 2, 10, 5));
		p.setBorder(new TitledBorder(new EtchedBorder(), "Font"));
		p.add(fontList);
		p.add(fontSizeList);
		return p;
	}

	private Component effectsPanel() {
		JPanel p = new JPanel(new GridLayout(2, 3, 10, 5));
		p.setBorder(new TitledBorder(new EtchedBorder(), "Font Effect"));
		p.add(boldCheckBox);
		p.add(italicCheckBox);
		p.add(underlineCheckBox);
		p.add(strikethroughCheckBox);
		p.add(subscriptCheckBox);
		p.add(superscriptCheckBox);
		return p;
	}

	private Component fontColorPanel() {
		JPanel p = new JPanel();
		p.setBorder(new TitledBorder(new EtchedBorder(), "Font Color"));
		p.add(colorComboBox);
		return p;
	}

	private Component previewPanel() {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new TitledBorder(new EtchedBorder(), "Preview"));
		previewLabel = new FontLabel(PREVIEW_TEXT);
		p.add(previewLabel, BorderLayout.CENTER);
		return p;
	}

	private void updatePreview() {
		StringBuilder previewText = new StringBuilder(PREVIEW_TEXT);
		String name = (String) fontList.getSelectedItem();
		int size = Integer.valueOf((String) fontSizeList.getSelectedItem());

		if (size <= 0) {
			return;
		}

		Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();

		attributes.put(FAMILY, name);
		attributes.put(SIZE, (float) size);

		// Using HTML to force JLabel manage natively unsupported attributes
		if (underlineCheckBox.isSelected() || strikethroughCheckBox.isSelected()) {
			previewText.insert(0, "<html>");
			previewText.append("</html>");
		}

		if (underlineCheckBox.isSelected()) {
			attributes.put(UNDERLINE, UNDERLINE_LOW_ONE_PIXEL);
			previewText.insert(6, "<u>");
			previewText.insert(previewText.length() - 7, "</u>");
		}
		if (strikethroughCheckBox.isSelected()) {
			attributes.put(STRIKETHROUGH, STRIKETHROUGH_ON);
			previewText.insert(6, "<strike>");
			previewText.insert(previewText.length() - 7, "</strike>");
		}

		if (boldCheckBox.isSelected()) {
			attributes.put(WEIGHT, WEIGHT_BOLD);
		}
		if (italicCheckBox.isSelected()) {
			attributes.put(POSTURE, POSTURE_OBLIQUE);
		}

		if (subscriptCheckBox.isSelected()) {
			attributes.put(SUPERSCRIPT, SUPERSCRIPT_SUB);
		}
		if (superscriptCheckBox.isSelected()) {
			attributes.put(SUPERSCRIPT, SUPERSCRIPT_SUPER);
		}

		superscriptCheckBox.setEnabled(!subscriptCheckBox.isSelected());
		subscriptCheckBox.setEnabled(!superscriptCheckBox.isSelected());

		Font fn = new Font(attributes);

		previewLabel.setText(previewText.toString());
		previewLabel.setFont(fn);

		Color c = (Color) colorComboBox.getSelectedItem();
		previewLabel.setForeground(c);
		previewLabel.repaint();
	}

	private void loadSettings() {
		LAFSettings settings = this.settings;
		acrylCheckBox.setSelected(settings.isAcryl());
		aeroCheckBox.setSelected(settings.isAero());
		aluminiumCheckBox.setSelected(settings.isAluminium());
		bernsteinCheckBox.setSelected(settings.isBernstein());
		fastCheckBox.setSelected(settings.isFast());
		graphiteCheckBox.setSelected(settings.isGraphite());
		hiFiCheckBox.setSelected(settings.isHiFi());
		lunaCheckBox.setSelected(settings.isLuna());
		mcWinCheckBox.setSelected(settings.isMcWin());
		mintCheckBox.setSelected(settings.isMint());
		noireCheckBox.setSelected(settings.isNoire());
		smartCheckBox.setSelected(settings.isSmart());
		textureCheckBox.setSelected(settings.isTexture());
		boldCheckBox.setSelected(settings.isBold());
		italicCheckBox.setSelected(settings.isItalic());
		underlineCheckBox.setSelected(settings.isUnderline());
		strikethroughCheckBox.setSelected(settings.isStrikethrough());
		subscriptCheckBox.setSelected(settings.isSubscript());
		superscriptCheckBox.setSelected(settings.isSuperscript());
		fontSizeList.setSelectedItem(String.valueOf(Math.round(settings.getFontSize())));
		fontList.setSelectedItem(settings.getFont());
		colorComboBox.setSelectedItem(settings.getColor());
		acrylCheckBox.revalidate();
		acrylCheckBox.revalidate();
		aeroCheckBox.revalidate();
		aluminiumCheckBox.revalidate();
		bernsteinCheckBox.revalidate();
		fastCheckBox.revalidate();
		graphiteCheckBox.revalidate();
		hiFiCheckBox.revalidate();
		lunaCheckBox.revalidate();
		mcWinCheckBox.revalidate();
		mintCheckBox.revalidate();
		noireCheckBox.revalidate();
		smartCheckBox.revalidate();
		textureCheckBox.revalidate();
		boldCheckBox.revalidate();
		italicCheckBox.revalidate();
		underlineCheckBox.revalidate();
		strikethroughCheckBox.revalidate();
		subscriptCheckBox.revalidate();
		superscriptCheckBox.revalidate();
	}

	private boolean validate(LAFSettings settings) {

		int check = 0;
		if (settings.isAcryl()) {
			check = check + 1;
		}
		if (settings.isAero()) {
			check = check + 1;
		}
		if (settings.isAluminium()) {
			check = check + 1;
		}
		if (settings.isBernstein()) {
			check = check + 1;
		}
		if (settings.isFast()) {
			check = check + 1;
		}
		if (settings.isGraphite()) {
			check = check + 1;
		}
		if (settings.isHiFi()) {
			check = check + 1;
		}
		if (settings.isLuna()) {
			check = check + 1;
		}
		if (settings.isMcWin()) {
			check = check + 1;
		}
		if (settings.isMint()) {
			check = check + 1;
		}
		if (settings.isNoire()) {
			check = check + 1;
		}
		if (settings.isSmart()) {
			check = check + 1;
		}
		if (settings.isTexture()) {
			check = check + 1;
		}

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

}
