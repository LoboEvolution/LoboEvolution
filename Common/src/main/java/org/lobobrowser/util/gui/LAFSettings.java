/*
 * GNU GENERAL LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 -
 * 2015 Lobo Evolution This program is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either verion 2 of the License, or
 * (at your option) any later version. This program is distributed in the hope
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General License for more details. You should have received a copy of the GNU
 * General Public License along with this library; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301
 * USA Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.util.gui;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LAFSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    /** The Constant logger. */
    private static final Logger logger = Logger
            .getLogger(LAFSettings.class.getName());
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
    /** The Font Size . */
    private volatile float fontSize;
    /** The Font . */
    private volatile String font;
    /** The Constant instance. */
    private static final LAFSettings instance;
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
    /** The Constant TIMES_NEW_ROMAN. */
    public static String TIMES_NEW_ROMAN = "TimesNewRoman";
    /** The FONTS . */
    public static String[] FONTS = { "Aharoni", "Andalus", "AngsanaNew",
            "AngsanaUPC", "AngsanaUPC", "Aparajita", "ArabicTypesetting",
            "Arial", "ArialBlack", "Batang", "BatangChe", "BrowalliaNew",
            "BrowalliaUPC", "Caladea", "Calibri", "CalibriLight", "Cambria",
            "CambriaMath", "Candara", "Carlito", "ComicSansMS", "Consolas",
            "Constantia", "Corbel", "CordiaNew", "CordiaUPC", "CourierNew",
            "DaunPenh", "David", "DejaVuSans", "DejaVuSansCondensed",
            "DejaVuSansLight", "DejaVuSansMono", "DejaVuSerif",
            "DejaVuSerifCondensed", "DFKai-SB", "Dialog", "DialogInput",
            "DilleniaUPC", "DokChampa", "Dotum", "DotumChe", "Ebrima",
            "EstrangeloEdessa", "EucrosiaUPC", "Euphemia", "FangSong",
            "FranklinGothicMedium", "FrankRuehl", "FreesiaUPC", "Gabriola",
            "Gautami", "GentiumBasic", "GentiumBookBasic", "Georgia", "Gisha",
            "Gulim", "GulimChe", "Gungsuh", "GungsuhChe", "Impact", "IrisUPC",
            "IskoolaPota", "JasmineUPC", "KaiTi", "Kalinga", "Kartika",
            "KhmerUI", "KodchiangUPC", "Kokila", "LaoUI", "Latha", "Leelawadee",
            "LevenimMT", "LiberationMono", "LiberationSans",
            "LiberationSansNarrow", "LiberationSerif", "LilyUPC",
            "LinuxBiolinumG", "LinuxLibertineDisplayG", "LinuxLibertineG",
            "LucidaBright", "LucidaConsole", "LucidaSans",
            "LucidaSansTypewriter", "LucidaSansUnicode", "MalgunGothic",
            "Mangal", "Marlett", "Meiryo", "MeiryoUI", "MicrosoftHimalaya",
            "MicrosoftJhengHei", "MicrosoftNewTaiLue", "MicrosoftPhagsPa",
            "MicrosoftSansSerif", "MicrosoftTaiLe", "MicrosoftUighur",
            "MicrosoftYaHei", "MicrosoftYiBaiti", "MingLiU", "MingLiU-ExtB",
            "MingLiU_HKSCS", "MingLiU_HKSCS-ExtB", "Miriam", "MiriamFixed",
            "MongolianBaiti", "Monospaced", "MoolBoran", "MSGothic", "MSMincho",
            "MSPGothic", "MSPMincho", "MSUIGothic", "MVBoli", "Narkisim",
            "NSimSun", "Nyala", "OpenSans", "OpenSymbol", "PalatinoLinotype",
            "PlantagenetCherokee", "PMingLiU", "PMingLiU-ExtB", "PTSerif",
            "Raavi", "Rod", "SakkalMajalla", "SansSerif", "SegoePrint",
            "SegoeScript", "SegoeUI", "SegoeUILight", "SegoeUISemibold",
            "SegoeUISymbol", "Serif", "ShonarBangla", "Shruti", "SimHei",
            "SimplifiedArabic", "SimplifiedArabicFixed", "SimSun",
            "SimSun-ExtB", "SourceCodePro", "SourceSansPro",
            "SourceSansProBlack", "SourceSansProExtraLight",
            "SourceSansProLight", "SourceSansProSemibold", "Sylfaen", "Symbol",
            "Tahoma", "TimesNewRoman", "TraditionalArabic", "TrebuchetMS",
            "Tunga", "Utsaah", "Vani", "Verdana", "Vijaya", "Vrinda",
            "Webdings", "Wingdings" };
            
    static {
        LAFSettings ins = null;
        try {
            ins = (LAFSettings) StorageManagerCommon.getInstance()
                    .retrieveSettings(LAFSettings.class.getSimpleName(),
                            LAFSettings.class.getClassLoader());
        } catch (Exception err) {
            logger.log(Level.WARNING,
                    "getInstance(): Unable to retrieve settings.", err);
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
        this.fontSize = 14.0f;
        this.font = TIMES_NEW_ROMAN;
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
            logger.log(Level.WARNING, "save(): Unable to save settings", ioe);
        }
    }
    
    /**
     * Save checked.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void saveChecked() throws IOException {
        StorageManagerCommon.getInstance()
                .saveSettings(this.getClass().getSimpleName(), this);
    }
    
    /**
     * @return the acryl
     */
    public boolean isAcryl() {
        return acryl;
    }
    
    /**
     * @return the aero
     */
    public boolean isAero() {
        return aero;
    }
    
    /**
     * @return the aluminium
     */
    public boolean isAluminium() {
        return aluminium;
    }
    
    /**
     * @return the bernstein
     */
    public boolean isBernstein() {
        return bernstein;
    }
    
    /**
     * @return the fast
     */
    public boolean isFast() {
        return fast;
    }
    
    /**
     * @return the graphite
     */
    public boolean isGraphite() {
        return graphite;
    }
    
    /**
     * @return the hiFi
     */
    public boolean isHiFi() {
        return hiFi;
    }
    
    /**
     * @return the luna
     */
    public boolean isLuna() {
        return luna;
    }
    
    /**
     * @return the mcWin
     */
    public boolean isMcWin() {
        return mcWin;
    }
    
    /**
     * @return the mint
     */
    public boolean isMint() {
        return mint;
    }
    
    /**
     * @return the noire
     */
    public boolean isNoire() {
        return noire;
    }
    
    /**
     * @return the smart
     */
    public boolean isSmart() {
        return smart;
    }
    
    /**
     * @return the texture
     */
    public boolean isTexture() {
        return texture;
    }
    
    /**
     * @param acryl
     *            the acryl to set
     */
    public void setAcryl(boolean acryl) {
        this.acryl = acryl;
    }
    
    /**
     * @param aero
     *            the aero to set
     */
    public void setAero(boolean aero) {
        this.aero = aero;
    }
    
    /**
     * @param aluminium
     *            the aluminium to set
     */
    public void setAluminium(boolean aluminium) {
        this.aluminium = aluminium;
    }
    
    /**
     * @param bernstein
     *            the bernstein to set
     */
    public void setBernstein(boolean bernstein) {
        this.bernstein = bernstein;
    }
    
    /**
     * @param fast
     *            the fast to set
     */
    public void setFast(boolean fast) {
        this.fast = fast;
    }
    
    /**
     * @param graphite
     *            the graphite to set
     */
    public void setGraphite(boolean graphite) {
        this.graphite = graphite;
    }
    
    /**
     * @param hiFi
     *            the hiFi to set
     */
    public void setHiFi(boolean hiFi) {
        this.hiFi = hiFi;
    }
    
    /**
     * @param luna
     *            the luna to set
     */
    public void setLuna(boolean luna) {
        this.luna = luna;
    }
    
    /**
     * @param mcWin
     *            the mcWin to set
     */
    public void setMcWin(boolean mcWin) {
        this.mcWin = mcWin;
    }
    
    /**
     * @param mint
     *            the mint to set
     */
    public void setMint(boolean mint) {
        this.mint = mint;
    }
    
    /**
     * @param noire
     *            the noire to set
     */
    public void setNoire(boolean noire) {
        this.noire = noire;
    }
    
    /**
     * @param smart
     *            the smart to set
     */
    public void setSmart(boolean smart) {
        this.smart = smart;
    }
    
    /**
     * @param texture
     *            the texture to set
     */
    public void setTexture(boolean texture) {
        this.texture = texture;
    }
    
    /**
     * @return the fontSize
     */
    public float getFontSize() {
        return fontSize;
    }
    
    /**
     * @return the font
     */
    public String getFont() {
        return font;
    }
    
    /**
     * @param fontSize
     *            the fontSize to set
     */
    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }
    
    /**
     * @param font
     *            the font to set
     */
    public void setFont(String font) {
        this.font = font;
    }
}
