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
package org.lobobrowser.html.w3c;

/**
 * The public interface HTMLCommandElement.
 */
public interface HTMLCommandElement extends HTMLElement {
    // HTMLCommandElement
    /**
     * Gets the type.
     *
     * @return the type
     */
    String getType();

    /**
     * Sets the type.
     *
     * @param type
     *            the new type
     */
    void setType(String type);

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLElement#getLabel()
     */
    @Override
    String getLabel();

    /**
     * Sets the label.
     *
     * @param label
     *            the new label
     */
    void setLabel(String label);

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLElement#getIcon()
     */
    @Override
    String getIcon();

    /**
     * Sets the icon.
     *
     * @param icon
     *            the new icon
     */
    void setIcon(String icon);

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLElement#getDisabled()
     */
    @Override
    boolean getDisabled();

    /**
     * Sets the disabled.
     *
     * @param disabled
     *            the new disabled
     */
    void setDisabled(boolean disabled);

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLElement#getChecked()
     */
    @Override
    boolean getChecked();

    /**
     * Sets the checked.
     *
     * @param checked
     *            the new checked
     */
    void setChecked(boolean checked);

    /**
     * Gets the radiogroup.
     *
     * @return the radiogroup
     */
    String getRadiogroup();

    /**
     * Sets the radiogroup.
     *
     * @param radiogroup
     *            the new radiogroup
     */
    void setRadiogroup(String radiogroup);
}
