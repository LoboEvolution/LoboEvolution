package org.loboevolution.menu.tools.pref;

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

import org.loboevolution.gui.ColorComboBox;
import org.loboevolution.gui.FontLabel;
import org.loboevolution.laf.LAFType;
import org.loboevolution.store.LookAndFeelsStore;
import org.loboevolution.laf.FontType;
import org.loboevolution.laf.LAFSettings;

/**
 * The Class LookAndFeelsSettingsUI.
 *
 * @author utente
 * @version $Id: $Id
 */
public class LookAndFeelsSettingsUI extends AbstractSettingsUI {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Acryl Box. */
	private final JCheckBox acrylCheckBox;

	/** The Aero Box. */
	private final JCheckBox aeroCheckBox;

	/** The Aluminium Box. */
	private final JCheckBox aluminiumCheckBox;

	/** The Bernstein Box. */
	private final JCheckBox bernsteinCheckBox;

	/** The Bold Box. */
	private final JCheckBox boldCheckBox;

	/** The color Combo Box. */
	private final ColorComboBox colorComboBox;

	/** The Fast Box. */
	private final JCheckBox fastCheckBox;

	/** The search engine list control. */
	private final JComboBox<String> fontList;

	/** The Font Size list. */
	private final JComboBox<String> fontSizeList;

	/** The Graphite Box. */
	private final JCheckBox graphiteCheckBox;

	/** The HiFi Box. */
	private final JCheckBox hiFiCheckBox;

	/** The Italic Box. */
	private final JCheckBox italicCheckBox;

	/** The settings. */
	private final LAFSettings laf = new LAFSettings().getInstance();

	/** The Luna Box. */
	private final JCheckBox lunaCheckBox;

	/** The McWin Box. */
	private final JCheckBox mcWinCheckBox;

	/** The Mint Box. */
	private final JCheckBox mintCheckBox;

	/** The Noire Box. */
	private final JCheckBox noireCheckBox;

	private final String PREVIEW_TEXT = "Preview Font";

	protected FontLabel previewLabel;

	/** The Smart Box. */
	private final JCheckBox smartCheckBox;

	/** The Strikethrough Box. */
	private final JCheckBox strikethroughCheckBox;

	/** The Subscript Box. */
	private final JCheckBox subscriptCheckBox;

	/** The Superscript Box. */
	private final JCheckBox superscriptCheckBox;

	/** The Texture Box. */
	private final JCheckBox textureCheckBox;

	/** The Underline Box. */
	private final JCheckBox underlineCheckBox;

	/**
	 * <p>Constructor for LookAndFeelsSettingsUI.</p>
	 */
	public LookAndFeelsSettingsUI() {

		final JPanel groupBox = new JPanel();
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));

		this.boldCheckBox = new JCheckBox(LAFType.BOLD.getValue());
		this.italicCheckBox = new JCheckBox(LAFType.ITALIC.getValue());
		this.underlineCheckBox = new JCheckBox(FontType.UNDERLINE.getValue());
		this.strikethroughCheckBox = new JCheckBox(FontType.STRIKETHROUGH.getValue());
		this.subscriptCheckBox = new JCheckBox(FontType.SUBSCRIPT.getValue());
		this.superscriptCheckBox = new JCheckBox(FontType.SUPERSCRIPT.getValue());

		this.acrylCheckBox = new JCheckBox(LAFType.ACRYL.getValue());
		this.aeroCheckBox = new JCheckBox(LAFType.AERO.getValue());
		this.aluminiumCheckBox = new JCheckBox(LAFType.ALUMINIUM.getValue());
		this.bernsteinCheckBox = new JCheckBox(LAFType.BERNSTEIN.getValue());
		this.fastCheckBox = new JCheckBox(LAFType.FAST.getValue());
		this.graphiteCheckBox = new JCheckBox(LAFType.GRAPHITE.getValue());
		this.hiFiCheckBox = new JCheckBox(LAFType.HIFI.getValue());
		this.lunaCheckBox = new JCheckBox(LAFType.LUNA.getValue());
		this.mcWinCheckBox = new JCheckBox(LAFType.MCWIN.getValue());
		this.mintCheckBox = new JCheckBox(LAFType.MINT.getValue());
		this.noireCheckBox = new JCheckBox(LAFType.NOIRE.getValue());
		this.smartCheckBox = new JCheckBox(LAFType.SMART.getValue());
		this.textureCheckBox = new JCheckBox(FontType.TEXTURE.getValue());
		this.colorComboBox = new ColorComboBox();
		this.colorComboBox.setPreferredSize(new Dimension(400, 20));
		this.fontList = new JComboBox<String>(LAFSettings.getFonts("FONT"));
		this.fontSizeList = new JComboBox<String>(LAFSettings.getFonts("FONT_SIZE"));

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
	}

	private Component effectsPanel() {
		final JPanel p = new JPanel(new GridLayout(2, 3, 10, 5));
		p.setBorder(new TitledBorder(new EtchedBorder(), "Font Effect"));
		p.add(this.boldCheckBox);
		p.add(this.italicCheckBox);
		p.add(this.underlineCheckBox);
		p.add(this.strikethroughCheckBox);
		p.add(this.subscriptCheckBox);
		p.add(this.superscriptCheckBox);
		return p;
	}

	private Component fontColorPanel() {
		final JPanel p = new JPanel();
		p.setBorder(new TitledBorder(new EtchedBorder(), "Font Color"));
		p.add(this.colorComboBox);
		return p;
	}

	private Component fontPanel() {
		final JPanel p = new JPanel(new GridLayout(1, 2, 10, 5));
		p.setBorder(new TitledBorder(new EtchedBorder(), "Font"));
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
		this.textureCheckBox.revalidate();
		this.boldCheckBox.revalidate();
		this.italicCheckBox.revalidate();
		this.underlineCheckBox.revalidate();
		this.strikethroughCheckBox.revalidate();
		this.subscriptCheckBox.revalidate();
		this.superscriptCheckBox.revalidate();
	}

	private Component lookAndFeelsPanel() {
		final JPanel p = new JPanel(new GridLayout(4, 3, 10, 5));
		p.setBorder(new TitledBorder(new EtchedBorder(), "Look And Feels"));
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
		p.add(this.textureCheckBox);
		return p;
	}

	private Component previewPanel() {
		final JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new TitledBorder(new EtchedBorder(), "Preview"));
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
		this.textureCheckBox.revalidate();
		this.boldCheckBox.revalidate();
		this.italicCheckBox.revalidate();
		this.underlineCheckBox.revalidate();
		this.strikethroughCheckBox.revalidate();
		this.subscriptCheckBox.revalidate();
		this.superscriptCheckBox.revalidate();
		this.colorComboBox.setSelectedItem(Color.BLACK);

		this.previewLabel.setText(this.PREVIEW_TEXT);
		final Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
		attributes.put(FAMILY, FontType.TIMES_NEW_ROMAN.getValue());
		attributes.put(SIZE, (float) 14.0);
		this.previewLabel.setFont(new Font(attributes));
		this.previewLabel.setForeground(Color.BLACK);

	}

	/** {@inheritDoc} */
	@Override
	public void save() {
		final LAFSettings laf = new LAFSettings();
		laf.setAcryl(this.acrylCheckBox.isSelected());
		laf.setAero(this.aeroCheckBox.isSelected());
		laf.setAluminium(this.aluminiumCheckBox.isSelected());
		laf.setBernstein(this.bernsteinCheckBox.isSelected());
		laf.setFast(this.fastCheckBox.isSelected());
		laf.setGraphite(this.graphiteCheckBox.isSelected());
		laf.setHiFi(this.hiFiCheckBox.isSelected());
		laf.setLuna(this.lunaCheckBox.isSelected());
		laf.setMcWin(this.mcWinCheckBox.isSelected());
		laf.setMint(this.mintCheckBox.isSelected());
		laf.setNoire(this.noireCheckBox.isSelected());
		laf.setSmart(this.smartCheckBox.isSelected());
		laf.setTexture(this.textureCheckBox.isSelected());
		laf.setSubscript(this.subscriptCheckBox.isSelected());
		laf.setSuperscript(this.superscriptCheckBox.isSelected());
		laf.setUnderline(this.underlineCheckBox.isSelected());
		laf.setItalic(this.italicCheckBox.isSelected());
		laf.setStrikethrough(this.strikethroughCheckBox.isSelected());
		laf.setFontSize(Float.valueOf(this.fontSizeList.getSelectedItem().toString()));
		laf.setFont(this.fontList.getSelectedItem().toString());
		laf.setColor((Color) this.colorComboBox.getSelectedItem());
		laf.setBold(this.boldCheckBox.isSelected());

		if (validate(laf)) {
			final LookAndFeelsStore sql = new LookAndFeelsStore();
			sql.deleteLAF();
			sql.insertLAF(laf);
		}
	}

	private void updatePreview() {
		final StringBuilder previewText = new StringBuilder(this.PREVIEW_TEXT);
		final String name = (String) this.fontList.getSelectedItem();
		final int size = Integer.valueOf((String) this.fontSizeList.getSelectedItem());

		if (size <= 0) {
			return;
		}

		final Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();

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

	private boolean validate(LAFSettings laf) {

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
