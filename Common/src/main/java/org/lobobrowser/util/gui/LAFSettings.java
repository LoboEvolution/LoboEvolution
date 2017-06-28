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
package org.lobobrowser.util.gui;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class LAFSettings.
 */
public class LAFSettings implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(LAFSettings.class);

	/** The Constant instance. */
	private static final LAFSettings instance;

	/** The Acryl . */
	private volatile boolean acryl;

	/** The Aero . */
	private volatile boolean aero;

	/** The Aluminium . */
	private volatile boolean aluminium;

	/** The Bernstein . */
	private volatile boolean bernstein;

	/** The Fast . */
	private volatile boolean fast;

	/** The Graphite . */
	private volatile boolean graphite;

	/** The HiFi . */
	private volatile boolean hiFi;

	/** The Luna . */
	private volatile boolean luna;

	/** The McWin . */
	private volatile boolean mcWin;

	/** The Mint . */
	private volatile boolean mint;

	/** The Noire . */
	private volatile boolean noire;

	/** The Smart . */
	private volatile boolean smart;

	/** The Texture . */
	private volatile boolean texture;

	/** The Bold . */
	private volatile boolean bold;

	/** The Italic . */
	private volatile boolean italic;

	/** The Underline . */
	private volatile boolean underline;

	/** The Strikethrough . */
	private volatile boolean strikethrough;

	/** The Subscript . */
	private volatile boolean subscript;

	/** The Superscript . */
	private volatile boolean superscript;

	/** The Font Size . */
	private volatile float fontSize;

	/** The Color . */
	private volatile Color color;

	/** The Font . */
	private volatile String font;

	/** The Constant ACRYL. */
	public static String ACRYL = "Acryl";

	/** The Constant AERO. */
	public static String AERO = "Aero";

	/** The Constant ALUMINIUM. */
	public static String ALUMINIUM = "Aluminium";

	/** The Constant BERNSTEIN. */
	public static String BERNSTEIN = "Bernstein";

	/** The Constant FAST. */
	public static String FAST = "Fast";

	/** The Constant GRAPHITE. */
	public static String GRAPHITE = "Graphite";

	/** The Constant HIFI. */
	public static String HIFI = "HiFi";

	/** The Constant LUNA. */
	public static String LUNA = "Luna";

	/** The Constant MCWIN. */
	public static String MCWIN = "McWin";

	/** The Constant MINT. */
	public static String MINT = "Mint";

	/** The Constant NOIRE. */
	public static String NOIRE = "Noire";

	/** The Constant SMART. */
	public static String SMART = "Smart";

	/** The Constant TEXTURE. */
	public static String TEXTURE = "Texture";

	public static String BOLD = "Bold";
	public static String ITALIC = "Italic";
	public static String UNDERLINE = "Underline";
	public static String STRIKETHROUGH = "Strikethrough";
	public static String SUBSCRIPT = "Subscript";
	public static String SUPERSCRIPT = "Superscript";

	/** The Constant TIMES_NEW_ROMAN. */
	public static String TIMES_NEW_ROMAN = "TimesNewRoman";

	/** The FONTS . */
	public static String[] FONTS = { "Aharoni", "Andalus", "AngsanaNew", "AngsanaUPC", "AngsanaUPC", "Aparajita",
			"ArabicTypesetting", "Arial", "ArialBlack", "Batang", "BatangChe", "BrowalliaNew", "BrowalliaUPC",
			"Caladea", "Calibri", "CalibriLight", "Cambria", "CambriaMath", "Candara", "Carlito", "ComicSansMS",
			"Consolas", "Constantia", "Corbel", "CordiaNew", "CordiaUPC", "CourierNew", "DaunPenh", "David",
			"DejaVuSans", "DejaVuSansCondensed", "DejaVuSansLight", "DejaVuSansMono", "DejaVuSerif",
			"DejaVuSerifCondensed", "DFKai-SB", "Dialog", "DialogInput", "DilleniaUPC", "DokChampa", "Dotum",
			"DotumChe", "Ebrima", "EstrangeloEdessa", "EucrosiaUPC", "Euphemia", "FangSong", "FranklinGothicMedium",
			"FrankRuehl", "FreesiaUPC", "Gabriola", "Gautami", "GentiumBasic", "GentiumBookBasic", "Georgia", "Gisha",
			"Gulim", "GulimChe", "Gungsuh", "GungsuhChe", "Impact", "IrisUPC", "IskoolaPota", "JasmineUPC", "KaiTi",
			"Kalinga", "Kartika", "KhmerUI", "KodchiangUPC", "Kokila", "LaoUI", "Latha", "Leelawadee", "LevenimMT",
			"LiberationMono", "LiberationSans", "LiberationSansNarrow", "LiberationSerif", "LilyUPC", "LinuxBiolinumG",
			"LinuxLibertineDisplayG", "LinuxLibertineG", "LucidaBright", "LucidaConsole", "LucidaSans",
			"LucidaSansTypewriter", "LucidaSansUnicode", "MalgunGothic", "Mangal", "Marlett", "Meiryo", "MeiryoUI",
			"MicrosoftHimalaya", "MicrosoftJhengHei", "MicrosoftNewTaiLue", "MicrosoftPhagsPa", "MicrosoftSansSerif",
			"MicrosoftTaiLe", "MicrosoftUighur", "MicrosoftYaHei", "MicrosoftYiBaiti", "MingLiU", "MingLiU-ExtB",
			"MingLiU_HKSCS", "MingLiU_HKSCS-ExtB", "Miriam", "MiriamFixed", "MongolianBaiti", "Monospaced", "MoolBoran",
			"MSGothic", "MSMincho", "MSPGothic", "MSPMincho", "MSUIGothic", "MVBoli", "Narkisim", "NSimSun", "Nyala",
			"OpenSans", "OpenSymbol", "PalatinoLinotype", "PlantagenetCherokee", "PMingLiU", "PMingLiU-ExtB", "PTSerif",
			"Raavi", "Rod", "SakkalMajalla", "SansSerif", "SegoePrint", "SegoeScript", "SegoeUI", "SegoeUILight",
			"SegoeUISemibold", "SegoeUISymbol", "Serif", "ShonarBangla", "Shruti", "SimHei", "SimplifiedArabic",
			"SimplifiedArabicFixed", "SimSun", "SimSun-ExtB", "SourceCodePro", "SourceSansPro", "SourceSansProBlack",
			"SourceSansProExtraLight", "SourceSansProLight", "SourceSansProSemibold", "Sylfaen", "Symbol", "Tahoma",
			"TimesNewRoman", "TraditionalArabic", "TrebuchetMS", "Tunga", "Utsaah", "Vani", "Verdana", "Vijaya",
			"Vrinda", "Webdings", "Wingdings" };

	public static String[] FONTS_SIZE = { "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28",
			"36", "48", "72" };

	static {
		LAFSettings ins = null;
		try {
			ins = (LAFSettings) StorageManagerCommon.getInstance().retrieveSettings(LAFSettings.class.getSimpleName(),
					LAFSettings.class.getClassLoader());
		} catch (Exception err) {
			logger.error("getInstance(): Unable to retrieve settings.", err);
		}
		if (ins == null) {
			ins = new LAFSettings();
		}
		instance = ins;
	}

	/**
	 * Instantiates a new general settings.
	 */
	private LAFSettings() {
		this.acryl = false;
		this.aero = true;
		this.aluminium = false;
		this.bernstein = false;
		this.fast = false;
		this.graphite = false;
		this.hiFi = false;
		this.luna = false;
		this.mcWin = false;
		this.mint = false;
		this.noire = false;
		this.smart = false;
		this.texture = false;
		this.bold = false;
		this.italic = false;
		this.underline = false;
		this.strikethrough = false;
		this.subscript = false;
		this.superscript = false;
		this.fontSize = 14.0f;
		this.font = TIMES_NEW_ROMAN;
		this.color = Color.BLACK;
	}

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
	public static LAFSettings getInstance() {
		return instance;
	}

	/**
	 * Save.
	 */
	public void save() {
		try {
			this.saveChecked();
		} catch (IOException ioe) {
			logger.error("save(): Unable to save settings", ioe);
		}
	}

	/**
	 * Save checked.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void saveChecked() throws IOException {
		StorageManagerCommon.getInstance().saveSettings(this.getClass().getSimpleName(), this);
	}

	/**
	 * Checks if is Acryl .
	 *
	 * @return the Acryl
	 */
	public boolean isAcryl() {
		return acryl;
	}

	/**
	 * Checks if is Aero .
	 *
	 * @return the Aero
	 */
	public boolean isAero() {
		return aero;
	}

	/**
	 * Checks if is Aluminium .
	 *
	 * @return the Aluminium
	 */
	public boolean isAluminium() {
		return aluminium;
	}

	/**
	 * Checks if is Bernstein .
	 *
	 * @return the Bernstein
	 */
	public boolean isBernstein() {
		return bernstein;
	}

	/**
	 * Checks if is Fast .
	 *
	 * @return the Fast
	 */
	public boolean isFast() {
		return fast;
	}

	/**
	 * Checks if is Graphite .
	 *
	 * @return the Graphite
	 */
	public boolean isGraphite() {
		return graphite;
	}

	/**
	 * Checks if is HiFi .
	 *
	 * @return the HiFi
	 */
	public boolean isHiFi() {
		return hiFi;
	}

	/**
	 * Checks if is Luna .
	 *
	 * @return the Luna
	 */
	public boolean isLuna() {
		return luna;
	}

	/**
	 * Checks if is McWin .
	 *
	 * @return the McWin
	 */
	public boolean isMcWin() {
		return mcWin;
	}

	/**
	 * Checks if is Mint .
	 *
	 * @return the Mint
	 */
	public boolean isMint() {
		return mint;
	}

	/**
	 * Checks if is Noire .
	 *
	 * @return the Noire
	 */
	public boolean isNoire() {
		return noire;
	}

	/**
	 * Checks if is Smart .
	 *
	 * @return the Smart
	 */
	public boolean isSmart() {
		return smart;
	}

	/**
	 * Checks if is Texture .
	 *
	 * @return the Texture
	 */
	public boolean isTexture() {
		return texture;
	}

	/**
	 * Sets the Acryl .
	 *
	 * @param acryl
	 *            the new Acryl
	 */
	public void setAcryl(boolean acryl) {
		this.acryl = acryl;
	}

	/**
	 * Sets the Aero .
	 *
	 * @param aero
	 *            the new Aero
	 */
	public void setAero(boolean aero) {
		this.aero = aero;
	}

	/**
	 * Sets the Aluminium .
	 *
	 * @param aluminium
	 *            the new Aluminium
	 */
	public void setAluminium(boolean aluminium) {
		this.aluminium = aluminium;
	}

	/**
	 * Sets the Bernstein .
	 *
	 * @param bernstein
	 *            the new Bernstein
	 */
	public void setBernstein(boolean bernstein) {
		this.bernstein = bernstein;
	}

	/**
	 * Sets the Fast .
	 *
	 * @param fast
	 *            the new Fast
	 */
	public void setFast(boolean fast) {
		this.fast = fast;
	}

	/**
	 * Sets the Graphite .
	 *
	 * @param graphite
	 *            the new Graphite
	 */
	public void setGraphite(boolean graphite) {
		this.graphite = graphite;
	}

	/**
	 * Sets the HiFi .
	 *
	 * @param hiFi
	 *            the new HiFi
	 */
	public void setHiFi(boolean hiFi) {
		this.hiFi = hiFi;
	}

	/**
	 * Sets the Luna .
	 *
	 * @param luna
	 *            the new Luna
	 */
	public void setLuna(boolean luna) {
		this.luna = luna;
	}

	/**
	 * Sets the McWin .
	 *
	 * @param mcWin
	 *            the new McWin
	 */
	public void setMcWin(boolean mcWin) {
		this.mcWin = mcWin;
	}

	/**
	 * Sets the Mint .
	 *
	 * @param mint
	 *            the new Mint
	 */
	public void setMint(boolean mint) {
		this.mint = mint;
	}

	/**
	 * Sets the Noire .
	 *
	 * @param noire
	 *            the new Noire
	 */
	public void setNoire(boolean noire) {
		this.noire = noire;
	}

	/**
	 * Sets the Smart .
	 *
	 * @param smart
	 *            the new Smart
	 */
	public void setSmart(boolean smart) {
		this.smart = smart;
	}

	/**
	 * Sets the Texture .
	 *
	 * @param texture
	 *            the new Texture
	 */
	public void setTexture(boolean texture) {
		this.texture = texture;
	}

	/**
	 * @return the bold
	 */
	public boolean isBold() {
		return bold;
	}

	/**
	 * @param bold
	 *            the bold to set
	 */
	public void setBold(boolean bold) {
		this.bold = bold;
	}

	/**
	 * @return the italic
	 */
	public boolean isItalic() {
		return italic;
	}

	/**
	 * @param italic
	 *            the italic to set
	 */
	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	/**
	 * @return the underline
	 */
	public boolean isUnderline() {
		return underline;
	}

	/**
	 * @param underline
	 *            the underline to set
	 */
	public void setUnderline(boolean underline) {
		this.underline = underline;
	}

	/**
	 * @return the strikethrough
	 */
	public boolean isStrikethrough() {
		return strikethrough;
	}

	/**
	 * @param strikethrough
	 *            the strikethrough to set
	 */
	public void setStrikethrough(boolean strikethrough) {
		this.strikethrough = strikethrough;
	}

	/**
	 * @return the subscript
	 */
	public boolean isSubscript() {
		return subscript;
	}

	/**
	 * @param subscript
	 *            the subscript to set
	 */
	public void setSubscript(boolean subscript) {
		this.subscript = subscript;
	}

	/**
	 * @return the superscript
	 */
	public boolean isSuperscript() {
		return superscript;
	}

	/**
	 * @param superscript
	 *            the superscript to set
	 */
	public void setSuperscript(boolean superscript) {
		this.superscript = superscript;
	}

	/**
	 * Gets the Font Size .
	 *
	 * @return the Font Size
	 */
	public float getFontSize() {
		return fontSize;
	}

	/**
	 * Gets the Font .
	 *
	 * @return the Font
	 */
	public String getFont() {
		return font;
	}

	/**
	 * Sets the Font Size .
	 *
	 * @param fontSize
	 *            the new Font Size
	 */
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Sets the Font .
	 *
	 * @param font
	 *            the new Font
	 */
	public void setFont(String font) {
		this.font = font;
	}
}
