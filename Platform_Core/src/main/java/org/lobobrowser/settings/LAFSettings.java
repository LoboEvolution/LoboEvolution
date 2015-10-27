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
package org.lobobrowser.settings;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.store.StorageManager;

public class LAFSettings implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** The Constant logger. */
    private static final Logger logger = Logger.getLogger(LAFSettings.class
            .getName());

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
	
	/** The Constant instance. */
    private static final LAFSettings instance;
	
	static {
		LAFSettings ins = null;
        try {
            ins = (LAFSettings) StorageManager.getInstance()
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
    }

    /** Gets the Constant instance.
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
        StorageManager.getInstance().saveSettings(
                this.getClass().getSimpleName(), this);
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

}
