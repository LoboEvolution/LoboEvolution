package org.loboevolution.laf;

import java.awt.Color;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.store.SQLiteCommon;

/**
 * The Class LAFSettings.
 *
 * @author utente
 * @version $Id: $Id
 */
public class LAFSettings implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(LAFSettings.class.getName());
	
	/** The Acryl . */
	private boolean acryl = false;

	/** The Aero . */
	private boolean aero = true;

	/** The Aluminium . */
	private boolean aluminium = false;

	/** The Bernstein . */
	private boolean bernstein = false;

	/** The Bold . */
	private boolean bold = false;

	/** The Color . */
	private Color color = Color.BLACK;

	/** The Fast . */
	private boolean fast = false;

	/** The Font . */
	private String font = FontType.TIMES_NEW_ROMAN.getValue();

	/** The Font Size . */
	private float fontSize = 14.0f;

	/** The Graphite . */
	private boolean graphite = false;

	/** The HiFi . */
	private boolean hiFi = false;

	/** The Italic . */
	private boolean italic = false;

	private final String LOOK_AND_FEEL = " SELECT DISTINCT acryl, aero, aluminium, bernstein, fast, graphite,"
			+ " 	    		 hiFi,luna, mcWin, mint, noire, smart, texture,"
			+ "	 			 subscript, superscript, underline, italic, strikethrough,"
			+ "				 fontSize, font, color, bold" + " FROM LOOK_AND_FEEL";

	/** The Luna . */
	private boolean luna = false;

	/** The McWin . */
	private boolean mcWin = false;

	/** The Mint . */
	private boolean mint = false;

	/** The Noire . */
	private boolean noire = false;

	/** The Smart . */
	private boolean smart = false;

	/** The Strikethrough . */
	private boolean strikethrough = false;

	/** The Subscript . */
	private boolean subscript = false;

	/** The Superscript . */
	private boolean superscript = false;

	/** The Texture . */
	private boolean texture = false;

	/** The Underline . */
	private boolean underline = false;
	
	/**
	 * <p>getFonts.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @return an array of {@link java.lang.String} objects.
	 */
	public static String[] getFonts(String type) {
		final List<String> fonts = new ArrayList<String>();
		final String query = "SELECT name FROM " + type;
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs != null && rs.next()) {
				fonts.add(rs.getString(1));
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return fonts.toArray(new String[fonts.size()]);
	}

	/**
	 * <p>Getter for the field color.</p>
	 *
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Gets the Font .
	 *
	 * @return the Font
	 */
	public String getFont() {
		return this.font;
	}

	/**
	 * Gets the Font Size .
	 *
	 * @return the Font Size
	 */
	public float getFontSize() {
		return this.fontSize;
	}

	/**
	 * <p>getInstance.</p>
	 *
	 * @return the instance
	 */
	public LAFSettings getInstance() {
		LAFSettings laf = retriveFontDate();
		if (laf == null) {
			laf = this;
		}
		return laf;
	}

	/**
	 * Checks if is Acryl .
	 *
	 * @return the Acryl
	 */
	public boolean isAcryl() {
		return this.acryl;
	}

	/**
	 * Checks if is Aero .
	 *
	 * @return the Aero
	 */
	public boolean isAero() {
		return this.aero;
	}

	/**
	 * Checks if is Aluminium .
	 *
	 * @return the Aluminium
	 */
	public boolean isAluminium() {
		return this.aluminium;
	}

	/**
	 * Checks if is Bernstein .
	 *
	 * @return the Bernstein
	 */
	public boolean isBernstein() {
		return this.bernstein;
	}

	/**
	 * <p>isBold.</p>
	 *
	 * @return the bold
	 */
	public boolean isBold() {
		return this.bold;
	}

	/**
	 * Checks if is Fast .
	 *
	 * @return the Fast
	 */
	public boolean isFast() {
		return this.fast;
	}

	/**
	 * Checks if is Graphite .
	 *
	 * @return the Graphite
	 */
	public boolean isGraphite() {
		return this.graphite;
	}

	/**
	 * Checks if is HiFi .
	 *
	 * @return the HiFi
	 */
	public boolean isHiFi() {
		return this.hiFi;
	}

	/**
	 * <p>isItalic.</p>
	 *
	 * @return the italic
	 */
	public boolean isItalic() {
		return this.italic;
	}

	/**
	 * Checks if is Luna .
	 *
	 * @return the Luna
	 */
	public boolean isLuna() {
		return this.luna;
	}

	/**
	 * Checks if is McWin .
	 *
	 * @return the McWin
	 */
	public boolean isMcWin() {
		return this.mcWin;
	}

	/**
	 * Checks if is Mint .
	 *
	 * @return the Mint
	 */
	public boolean isMint() {
		return this.mint;
	}

	/**
	 * Checks if is Noire .
	 *
	 * @return the Noire
	 */
	public boolean isNoire() {
		return this.noire;
	}

	/**
	 * Checks if is Smart .
	 *
	 * @return the Smart
	 */
	public boolean isSmart() {
		return this.smart;
	}

	/**
	 * <p>isStrikethrough.</p>
	 *
	 * @return the strikethrough
	 */
	public boolean isStrikethrough() {
		return this.strikethrough;
	}

	/**
	 * <p>isSubscript.</p>
	 *
	 * @return the subscript
	 */
	public boolean isSubscript() {
		return this.subscript;
	}

	/**
	 * <p>isSuperscript.</p>
	 *
	 * @return the superscript
	 */
	public boolean isSuperscript() {
		return this.superscript;
	}

	/**
	 * Checks if is Texture .
	 *
	 * @return the Texture
	 */
	public boolean isTexture() {
		return this.texture;
	}

	/**
	 * <p>isUnderline.</p>
	 *
	 * @return the underline
	 */
	public boolean isUnderline() {
		return this.underline;
	}

	private LAFSettings retriveFontDate() {
		LAFSettings laf = null;
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(this.LOOK_AND_FEEL)) {
			while (rs != null && rs.next()) {
				laf = new LAFSettings();
				laf.setAcryl(rs.getInt(1) == 1 ? true : false);
				laf.setAero(rs.getInt(2) == 1 ? true : false);
				laf.setAluminium(rs.getInt(3) == 1 ? true : false);
				laf.setBernstein(rs.getInt(4) == 1 ? true : false);
				laf.setFast(rs.getInt(5) == 1 ? true : false);
				laf.setGraphite(rs.getInt(6) == 1 ? true : false);
				laf.setHiFi(rs.getInt(7) == 1 ? true : false);
				laf.setLuna(rs.getInt(8) == 1 ? true : false);
				laf.setMcWin(rs.getInt(9) == 1 ? true : false);
				laf.setMint(rs.getInt(10) == 1 ? true : false);
				laf.setNoire(rs.getInt(11) == 1 ? true : false);
				laf.setSmart(rs.getInt(12) == 1 ? true : false);
				laf.setTexture(rs.getInt(13) == 1 ? true : false);
				laf.setSubscript(rs.getInt(14) == 1 ? true : false);
				laf.setSuperscript(rs.getInt(15) == 1 ? true : false);
				laf.setUnderline(rs.getInt(16) == 1 ? true : false);
				laf.setItalic(rs.getInt(17) == 1 ? true : false);
				laf.setStrikethrough(rs.getInt(18) == 1 ? true : false);
				laf.setFontSize(Float.parseFloat(rs.getString(19)));
				laf.setFont(rs.getString(20));
				laf.setColor(ColorFactory.getInstance().getColor(rs.getString(21)));
				laf.setBold(rs.getInt(22) == 1 ? true : false);
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
		return laf;
	}

	/**
	 * Sets the Acryl .
	 *
	 * @param acryl the new Acryl
	 */
	public void setAcryl(boolean acryl) {
		this.acryl = acryl;
	}

	/**
	 * Sets the Aero .
	 *
	 * @param aero the new Aero
	 */
	public void setAero(boolean aero) {
		this.aero = aero;
	}

	/**
	 * Sets the Aluminium .
	 *
	 * @param aluminium the new Aluminium
	 */
	public void setAluminium(boolean aluminium) {
		this.aluminium = aluminium;
	}

	/**
	 * Sets the Bernstein .
	 *
	 * @param bernstein the new Bernstein
	 */
	public void setBernstein(boolean bernstein) {
		this.bernstein = bernstein;
	}

	/**
	 * <p>Setter for the field bold.</p>
	 *
	 * @param bold the bold to set
	 */
	public void setBold(boolean bold) {
		this.bold = bold;
	}

	/**
	 * <p>Setter for the field color.</p>
	 *
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Sets the Fast .
	 *
	 * @param fast the new Fast
	 */
	public void setFast(boolean fast) {
		this.fast = fast;
	}

	/**
	 * Sets the Font .
	 *
	 * @param font the new Font
	 */
	public void setFont(String font) {
		this.font = font;
	}

	/**
	 * Sets the Font Size .
	 *
	 * @param fontSize the new Font Size
	 */
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * Sets the Graphite .
	 *
	 * @param graphite the new Graphite
	 */
	public void setGraphite(boolean graphite) {
		this.graphite = graphite;
	}

	/**
	 * Sets the HiFi .
	 *
	 * @param hiFi the new HiFi
	 */
	public void setHiFi(boolean hiFi) {
		this.hiFi = hiFi;
	}

	/**
	 * <p>Setter for the field italic.</p>
	 *
	 * @param italic the italic to set
	 */
	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	/**
	 * Sets the Luna .
	 *
	 * @param luna the new Luna
	 */
	public void setLuna(boolean luna) {
		this.luna = luna;
	}

	/**
	 * Sets the McWin .
	 *
	 * @param mcWin the new McWin
	 */
	public void setMcWin(boolean mcWin) {
		this.mcWin = mcWin;
	}

	/**
	 * Sets the Mint .
	 *
	 * @param mint the new Mint
	 */
	public void setMint(boolean mint) {
		this.mint = mint;
	}

	/**
	 * Sets the Noire .
	 *
	 * @param noire the new Noire
	 */
	public void setNoire(boolean noire) {
		this.noire = noire;
	}

	/**
	 * Sets the Smart .
	 *
	 * @param smart the new Smart
	 */
	public void setSmart(boolean smart) {
		this.smart = smart;
	}

	/**
	 * <p>Setter for the field strikethrough.</p>
	 *
	 * @param strikethrough the strikethrough to set
	 */
	public void setStrikethrough(boolean strikethrough) {
		this.strikethrough = strikethrough;
	}

	/**
	 * <p>Setter for the field subscript.</p>
	 *
	 * @param subscript the subscript to set
	 */
	public void setSubscript(boolean subscript) {
		this.subscript = subscript;
	}

	/**
	 * <p>Setter for the field superscript.</p>
	 *
	 * @param superscript the superscript to set
	 */
	public void setSuperscript(boolean superscript) {
		this.superscript = superscript;
	}

	/**
	 * Sets the Texture .
	 *
	 * @param texture the new Texture
	 */
	public void setTexture(boolean texture) {
		this.texture = texture;
	}

	/**
	 * <p>Setter for the field underline.</p>
	 *
	 * @param underline the underline to set
	 */
	public void setUnderline(boolean underline) {
		this.underline = underline;
	}
}
