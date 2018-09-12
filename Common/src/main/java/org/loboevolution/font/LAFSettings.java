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
package org.loboevolution.font;

import java.awt.Color;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.color.ColorFactory;

import com.loboevolution.store.SQLiteCommon;

/**
 * The Class LAFSettings.
 */
public class LAFSettings implements FontCommon, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(LAFSettings.class);
	
	/** The Acryl . */
	private boolean acryl = false;

	/** The Aero . */
	private boolean aero = true;

	/** The Aluminium . */
	private boolean aluminium = false;

	/** The Bernstein . */
	private boolean bernstein = false;

	/** The Fast . */
	private boolean fast = false;

	/** The Graphite . */
	private boolean graphite = false;

	/** The HiFi . */
	private boolean hiFi = false;

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

	/** The Texture . */
	private boolean texture = false;

	/** The Bold . */
	private boolean bold = false;

	/** The Italic . */
	private boolean italic = false;

	/** The Underline . */
	private boolean underline = false;

	/** The Strikethrough . */
	private boolean strikethrough = false;

	/** The Subscript . */
	private boolean subscript = false;

	/** The Superscript . */
	private boolean superscript = false;

	/** The Font Size . */
	private float fontSize = 14.0f;

	/** The Color . */
	private Color color = Color.BLACK;

	/** The Font . */
	private String font = TIMES_NEW_ROMAN;

	/**
	 * @return the istance
	 */
	public LAFSettings getIstance() {
		LAFSettings laf = retriveFontDate(); 
		if(laf == null ) { laf = this; }
		return laf;
	}
	
	private LAFSettings retriveFontDate() {
		LAFSettings laf = null;
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory())) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQLiteCommon.FONTS);

			while (rs!= null && rs.next()) {
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
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
		return laf;
	}
	
	public static String[] getFonts(String type) {
		List<String> fonts = new ArrayList<String>();
		String query = "SELECT name FROM "+type;
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory())) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs != null && rs.next()) {
				fonts.add(rs.getString(1));
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return fonts.toArray(new String[fonts.size()]);
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
