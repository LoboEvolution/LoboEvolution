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

package org.loboevolution.menu.tools.pref;

import com.jtattoo.plaf.lobo.LoboCheckBox;
import com.jtattoo.plaf.lobo.LoboPanel;
import org.loboevolution.store.laf.LAFSettings;
import org.loboevolution.gui.ColorComboBox;
import org.loboevolution.gui.FontLabel;
import org.loboevolution.gui.SwingTasks;
import org.loboevolution.laf.FontType;
import org.loboevolution.laf.LAFType;
import org.loboevolution.menu.tools.AbstractToolsUI;
import org.loboevolution.store.LookAndFeelsStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

import static java.awt.font.TextAttribute.*;

/**
 * The Class LookAndFeelsSettingsUI.
 */
public class LookAndFeelsSettingsUI extends AbstractToolsUI {

	/** The Constant serialVersionUID. */
	@Serial
    private static final long serialVersionUID = 1L;

	/** The Acryl Box. */
	private final LoboCheckBox acrylCheckBox;

	/** The Aero Box. */
	private final LoboCheckBox aeroCheckBox;

	/** The Aluminium Box. */
	private final LoboCheckBox aluminiumCheckBox;

	/** The Bernstein Box. */
	private final LoboCheckBox bernsteinCheckBox;

	/** The Bold Box. */
	private final LoboCheckBox boldCheckBox;

	/** The color Combo Box. */
	private final ColorComboBox colorComboBox;

	/** The Fast Box. */
	private final LoboCheckBox fastCheckBox;

	/** The search engine list control. */
	private final JComboBox<String> fontList;

	/** The Font Size list. */
	private final JComboBox<String> fontSizeList;

	/** The Graphite Box. */
	private final LoboCheckBox graphiteCheckBox;

	/** The HiFi Box. */
	private final LoboCheckBox hiFiCheckBox;

	/** The Italic Box. */
	private final LoboCheckBox italicCheckBox;

	/** The settings. */
	private final LAFSettings laf = new LAFSettings().getInstance();

	/** The Luna Box. */
	private final LoboCheckBox lunaCheckBox;

	/** The McWin Box. */
	private final LoboCheckBox mcWinCheckBox;

	/** The Mint Box. */
	private final LoboCheckBox mintCheckBox;

	/** The Noire Box. */
	private final LoboCheckBox noireCheckBox;

	private final String PREVIEW_TEXT = "Preview Font";

	protected FontLabel previewLabel;

	/** The Smart Box. */
	private final LoboCheckBox smartCheckBox;

	/** The Strikethrough Box. */
	private final LoboCheckBox strikethroughCheckBox;

	/** The Subscript Box. */
	private final LoboCheckBox subscriptCheckBox;

	/** The Superscript Box. */
	private final LoboCheckBox superscriptCheckBox;

	/** The Texture Box. */
	private final LoboCheckBox textureCheckBox;

	/** The Underline Box. */
	private final LoboCheckBox underlineCheckBox;
	
	/** The modern Box. */
	private final LoboCheckBox modernCheckBox;

	/** The blackWhite Box. */
	private final LoboCheckBox blackWhiteCheckBox;

	/** The whiteBlack Box. */
	private final LoboCheckBox whiteBlackCheckBox;


	/**
	 * <p>Constructor for LookAndFeelsSettingsUI.</p>
	 */
	public LookAndFeelsSettingsUI() {

		final JPanel groupBox = new JPanel();
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));

		this.boldCheckBox = new LoboCheckBox(LAFType.BOLD.getValue());
		this.italicCheckBox = new LoboCheckBox(LAFType.ITALIC.getValue());
		this.acrylCheckBox = new LoboCheckBox(LAFType.ACRYL.getValue());
		this.aeroCheckBox = new LoboCheckBox(LAFType.AERO.getValue());
		this.aluminiumCheckBox = new LoboCheckBox(LAFType.ALUMINIUM.getValue());
		this.bernsteinCheckBox = new LoboCheckBox(LAFType.BERNSTEIN.getValue());
		this.fastCheckBox = new LoboCheckBox(LAFType.FAST.getValue());
		this.graphiteCheckBox = new LoboCheckBox(LAFType.GRAPHITE.getValue());
		this.hiFiCheckBox = new LoboCheckBox(LAFType.HIFI.getValue());
		this.lunaCheckBox = new LoboCheckBox(LAFType.LUNA.getValue());
		this.mcWinCheckBox = new LoboCheckBox(LAFType.MCWIN.getValue());
		this.mintCheckBox = new LoboCheckBox(LAFType.MINT.getValue());
		this.noireCheckBox = new LoboCheckBox(LAFType.NOIRE.getValue());
		this.smartCheckBox = new LoboCheckBox(LAFType.SMART.getValue());
		this.modernCheckBox = new LoboCheckBox(LAFType.MODERN.getValue());
		this.blackWhiteCheckBox = new LoboCheckBox(LAFType.BLACK_WHITE.getValue());
		this.whiteBlackCheckBox = new LoboCheckBox(LAFType.WHITE_BLACK.getValue());
		this.underlineCheckBox = new LoboCheckBox(FontType.UNDERLINE.getValue());
		this.strikethroughCheckBox = new LoboCheckBox(FontType.STRIKETHROUGH.getValue());
		this.subscriptCheckBox = new LoboCheckBox(FontType.SUBSCRIPT.getValue());
		this.superscriptCheckBox = new LoboCheckBox(FontType.SUPERSCRIPT.getValue());
		this.textureCheckBox = new LoboCheckBox(FontType.TEXTURE.getValue());
		
		this.colorComboBox = new ColorComboBox();
		this.colorComboBox.setPreferredSize(new Dimension(400, 20));
		this.fontList = new JComboBox<>(LAFSettings.getFonts("FONT"));
		this.fontSizeList = new JComboBox<>(LAFSettings.getFonts("FONT_SIZE"));

		final ActionListener actionListener = e -> updatePreview();

		loadSettings();

		this.boldCheckBox.addActionListener(actionListener);
		this.italicCheckBox.addActionListener(actionListener);
		this.underlineCheckBox.addActionListener(actionListener);
		this.strikethroughCheckBox.addActionListener(actionListener);
		this.subscriptCheckBox.addActionListener(actionListener);
		this.superscriptCheckBox.addActionListener(actionListener);
		this.colorComboBox.addActionListener(actionListener);
		this.fontList.addActionListener(actionListener);
		this.fontSizeList.addActionListener(actionListener);

		groupBox.add(lookAndFeelsPanel());
		groupBox.add(fontPanel());
		groupBox.add(effectsPanel());
		groupBox.add(fontColorPanel());
		groupBox.add(previewPanel());
		add(groupBox);
		add(SwingTasks.createVerticalFill());
	}

	private Component effectsPanel() {
		final LoboPanel p = new LoboPanel(new GridLayout(2, 3, 10, 5), "Font Effect");
		p.add(this.boldCheckBox);
		p.add(this.italicCheckBox);
		p.add(this.underlineCheckBox);
		p.add(this.strikethroughCheckBox);
		p.add(this.subscriptCheckBox);
		p.add(this.superscriptCheckBox);
		return p;
	}

	private Component fontColorPanel() {
		final LoboPanel p = new LoboPanel("Font Color");
		p.add(this.colorComboBox);
		return p;
	}

	private Component fontPanel() {
		final LoboPanel p = new LoboPanel(new GridLayout(1, 2, 10, 5),"Font");
		p.add(this.fontList);
		p.add(this.fontSizeList);
		return p;
	}

	private void loadSettings() {
		final LAFSettings laf = this.laf;
		this.acrylCheckBox.setSelected(laf.isAcryl());
		this.aeroCheckBox.setSelected(laf.isAero());
		this.aluminiumCheckBox.setSelected(laf.isAluminium());
		this.bernsteinCheckBox.setSelected(laf.isBernstein());
		this.fastCheckBox.setSelected(laf.isFast());
		this.graphiteCheckBox.setSelected(laf.isGraphite());
		this.hiFiCheckBox.setSelected(laf.isHiFi());
		this.lunaCheckBox.setSelected(laf.isLuna());
		this.mcWinCheckBox.setSelected(laf.isMcWin());
		this.mintCheckBox.setSelected(laf.isMint());
		this.noireCheckBox.setSelected(laf.isNoire());
		this.smartCheckBox.setSelected(laf.isSmart());
		this.modernCheckBox.setSelected(laf.isModern());
		this.blackWhiteCheckBox.setSelected(laf.isBlackWhite());
		this.whiteBlackCheckBox.setSelected(laf.isWhiteBlack());
		this.textureCheckBox.setSelected(laf.isTexture());
		this.boldCheckBox.setSelected(laf.isBold());
		this.italicCheckBox.setSelected(laf.isItalic());
		this.underlineCheckBox.setSelected(laf.isUnderline());
		this.strikethroughCheckBox.setSelected(laf.isStrikethrough());
		this.subscriptCheckBox.setSelected(laf.isSubscript());
		this.superscriptCheckBox.setSelected(laf.isSuperscript());
		this.fontSizeList.setSelectedItem(String.valueOf(Math.round(laf.getFontSize())));
		this.fontList.setSelectedItem(laf.getFont());
		this.colorComboBox.setSelectedItem(laf.getColor());
		this.acrylCheckBox.revalidate();
		this.acrylCheckBox.revalidate();
		this.aeroCheckBox.revalidate();
		this.aluminiumCheckBox.revalidate();
		this.bernsteinCheckBox.revalidate();
		this.fastCheckBox.revalidate();
		this.graphiteCheckBox.revalidate();
		this.hiFiCheckBox.revalidate();
		this.lunaCheckBox.revalidate();
		this.mcWinCheckBox.revalidate();
		this.mintCheckBox.revalidate();
		this.noireCheckBox.revalidate();
		this.smartCheckBox.revalidate();
		this.modernCheckBox.revalidate();
		this.blackWhiteCheckBox.revalidate();
		this.whiteBlackCheckBox.revalidate();
		this.textureCheckBox.revalidate();
		this.boldCheckBox.revalidate();
		this.italicCheckBox.revalidate();
		this.underlineCheckBox.revalidate();
		this.strikethroughCheckBox.revalidate();
		this.subscriptCheckBox.revalidate();
		this.superscriptCheckBox.revalidate();
	}

	private Component lookAndFeelsPanel() {
		final LoboPanel p = new LoboPanel(new GridLayout(4, 3, 10, 5), "Look And Feels");
		p.add(this.acrylCheckBox);
		p.add(this.aeroCheckBox);
		p.add(this.aluminiumCheckBox);
		p.add(this.bernsteinCheckBox);
		p.add(this.fastCheckBox);
		p.add(this.graphiteCheckBox);
		p.add(this.hiFiCheckBox);
		p.add(this.lunaCheckBox);
		p.add(this.mcWinCheckBox);
		p.add(this.mintCheckBox);
		p.add(this.noireCheckBox);
		p.add(this.smartCheckBox);
		p.add(this.modernCheckBox);
		p.add(this.blackWhiteCheckBox);
		p.add(this.whiteBlackCheckBox);
		p.add(this.textureCheckBox);
		return p;
	}

	private Component previewPanel() {
		final LoboPanel p = new LoboPanel(new BorderLayout(), "Preview");
		this.previewLabel = new FontLabel(this.PREVIEW_TEXT);
		p.add(this.previewLabel, BorderLayout.CENTER);
		return p;
	}

	/** {@inheritDoc} */
	@Override
	public void restoreDefaults() {
		this.acrylCheckBox.setSelected(false);
		this.aeroCheckBox.setSelected(true);
		this.aluminiumCheckBox.setSelected(false);
		this.bernsteinCheckBox.setSelected(false);
		this.fastCheckBox.setSelected(false);
		this.graphiteCheckBox.setSelected(false);
		this.hiFiCheckBox.setSelected(false);
		this.lunaCheckBox.setSelected(false);
		this.mcWinCheckBox.setSelected(false);
		this.mintCheckBox.setSelected(false);
		this.noireCheckBox.setSelected(false);
		this.smartCheckBox.setSelected(false);
		this.modernCheckBox.setSelected(false);
		this.blackWhiteCheckBox.setSelected(false);
		this.whiteBlackCheckBox.setSelected(false);
		this.textureCheckBox.setSelected(false);
		this.boldCheckBox.setSelected(false);
		this.italicCheckBox.setSelected(false);
		this.underlineCheckBox.setSelected(false);
		this.strikethroughCheckBox.setSelected(false);
		this.subscriptCheckBox.setSelected(false);
		this.superscriptCheckBox.setSelected(false);
		this.fontSizeList.setSelectedItem("14");
		this.fontList.setSelectedItem(FontType.TIMES_NEW_ROMAN.getValue());
		this.acrylCheckBox.revalidate();
		this.aeroCheckBox.revalidate();
		this.aluminiumCheckBox.revalidate();
		this.bernsteinCheckBox.revalidate();
		this.fastCheckBox.revalidate();
		this.graphiteCheckBox.revalidate();
		this.hiFiCheckBox.revalidate();
		this.lunaCheckBox.revalidate();
		this.mcWinCheckBox.revalidate();
		this.mintCheckBox.revalidate();
		this.noireCheckBox.revalidate();
		this.smartCheckBox.revalidate();
		this.modernCheckBox.revalidate();
		this.blackWhiteCheckBox.revalidate();
		this.whiteBlackCheckBox.revalidate();
		this.textureCheckBox.revalidate();
		this.boldCheckBox.revalidate();
		this.italicCheckBox.revalidate();
		this.underlineCheckBox.revalidate();
		this.strikethroughCheckBox.revalidate();
		this.subscriptCheckBox.revalidate();
		this.superscriptCheckBox.revalidate();
		this.colorComboBox.setSelectedItem(Color.BLACK);

		this.previewLabel.setText(this.PREVIEW_TEXT);
		final Map<TextAttribute, Object> attributes = new HashMap<>();
		attributes.put(FAMILY, FontType.TIMES_NEW_ROMAN.getValue());
		attributes.put(SIZE, (float) 14.0);
		this.previewLabel.setFont(new Font(attributes));
		this.previewLabel.setForeground(Color.BLACK);

	}

	/** {@inheritDoc} */
	@Override
	public void save() {

		final LAFSettings laf = LAFSettings.builder().
				acryl(this.acrylCheckBox.isSelected()).
				aero(this.aeroCheckBox.isSelected()).
				aluminium(this.aluminiumCheckBox.isSelected()).
				bernstein(this.bernsteinCheckBox.isSelected()).
				fast(this.fastCheckBox.isSelected()).
				graphite(this.graphiteCheckBox.isSelected()).
				hiFi(this.hiFiCheckBox.isSelected()).
				luna(this.lunaCheckBox.isSelected()).
				mcWin(this.mcWinCheckBox.isSelected()).
				mint(this.mintCheckBox.isSelected()).
				noire(this.noireCheckBox.isSelected()).
				smart(this.smartCheckBox.isSelected()).
				texture(this.textureCheckBox.isSelected()).
				subscript(this.subscriptCheckBox.isSelected()).
				superscript(this.superscriptCheckBox.isSelected()).
				underline(this.underlineCheckBox.isSelected()).
				italic(this.italicCheckBox.isSelected()).
				strikethrough(this.strikethroughCheckBox.isSelected()).
				fontSize(Float.parseFloat(this.fontSizeList.getSelectedItem().toString())).
				font(this.fontList.getSelectedItem().toString()).
				color((Color) this.colorComboBox.getSelectedItem()).
				bold(this.boldCheckBox.isSelected()).
				modern(modernCheckBox.isSelected()).
				blackWhite(blackWhiteCheckBox.isSelected()).
				whiteBlack(whiteBlackCheckBox.isSelected()).build();

		if (validate(laf)) {
			final LookAndFeelsStore sql = new LookAndFeelsStore();
			sql.deleteLAF();
			sql.insertLAF(laf);
		}
	}

	private void updatePreview() {
		final StringBuilder previewText = new StringBuilder(this.PREVIEW_TEXT);
		final String name = (String) this.fontList.getSelectedItem();
		final int size = Integer.parseInt((String) this.fontSizeList.getSelectedItem());

		if (size <= 0) {
			return;
		}

		final Map<TextAttribute, Object> attributes = new HashMap<>();

		attributes.put(FAMILY, name);
		attributes.put(SIZE, (float) size);

		// Using HTML to force JLabel manage natively unsupported attributes
		if (this.underlineCheckBox.isSelected() || this.strikethroughCheckBox.isSelected()) {
			previewText.insert(0, "<html>");
			previewText.append("</html>");
		}

		if (this.underlineCheckBox.isSelected()) {
			attributes.put(UNDERLINE, UNDERLINE_LOW_ONE_PIXEL);
			previewText.insert(6, "<u>");
			previewText.insert(previewText.length() - 7, "</u>");
		}
		if (this.strikethroughCheckBox.isSelected()) {
			attributes.put(STRIKETHROUGH, STRIKETHROUGH_ON);
			previewText.insert(6, "<strike>");
			previewText.insert(previewText.length() - 7, "</strike>");
		}

		if (this.boldCheckBox.isSelected()) {
			attributes.put(WEIGHT, WEIGHT_BOLD);
		}
		if (this.italicCheckBox.isSelected()) {
			attributes.put(POSTURE, POSTURE_OBLIQUE);
		}

		if (this.subscriptCheckBox.isSelected()) {
			attributes.put(SUPERSCRIPT, SUPERSCRIPT_SUB);
		}
		if (this.superscriptCheckBox.isSelected()) {
			attributes.put(SUPERSCRIPT, SUPERSCRIPT_SUPER);
		}

		this.superscriptCheckBox.setEnabled(!this.subscriptCheckBox.isSelected());
		this.subscriptCheckBox.setEnabled(!this.superscriptCheckBox.isSelected());

		final Font fn = new Font(attributes);

		this.previewLabel.setText(previewText.toString());
		this.previewLabel.setFont(fn);

		final Color c = (Color) this.colorComboBox.getSelectedItem();
		this.previewLabel.setForeground(c);
		this.previewLabel.repaint();
	}

	private boolean validate(final LAFSettings laf) {

		int check = 0;
		if (laf.isAcryl()) {
			check = check + 1;
		}
		if (laf.isAero()) {
			check = check + 1;
		}
		if (laf.isAluminium()) {
			check = check + 1;
		}
		if (laf.isBernstein()) {
			check = check + 1;
		}
		if (laf.isFast()) {
			check = check + 1;
		}
		if (laf.isGraphite()) {
			check = check + 1;
		}
		if (laf.isHiFi()) {
			check = check + 1;
		}
		if (laf.isLuna()) {
			check = check + 1;
		}
		if (laf.isMcWin()) {
			check = check + 1;
		}
		if (laf.isMint()) {
			check = check + 1;
		}
		if (laf.isNoire()) {
			check = check + 1;
		}
		if (laf.isSmart()) {
			check = check + 1;
		}
		if (laf.isTexture()) {
			check = check + 1;
		}
		
		if (laf.isModern()) {
			check = check + 1;
		}
		
		if (laf.isBlackWhite()) {
			check = check + 1;
		}
		
		if (laf.isWhiteBlack()) {
			check = check + 1;
		}

		if (check == 0) {
			JOptionPane.showMessageDialog(this, "Checks a Look & Feels");
			return false;
		} else if (check > 1) {
			JOptionPane.showMessageDialog(this, "Checks only one Look & Feels");
			return false;
		}
		return true;
	}

}
