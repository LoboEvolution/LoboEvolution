/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jul 9, 2005
 */
package org.lobobrowser.request;

import java.awt.Frame;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

import org.lobobrowser.gui.AuthenticationDialog;
import org.lobobrowser.settings.AssociatedSettings;
import org.lobobrowser.settings.ConnectionSettings;
import org.lobobrowser.util.gui.GUITasks;

/**
 * The Class AuthenticatorImpl.
 */
public class AuthenticatorImpl extends Authenticator {

    /** The connection settings. */
    private final ConnectionSettings connectionSettings;

    /** The associated settings. */
    private final AssociatedSettings associatedSettings;

    /**
     * Instantiates a new authenticator impl.
     */
    public AuthenticatorImpl() {
        super();
        // This is one way to avoid potential security exceptions.
        this.connectionSettings = ConnectionSettings.getInstance();
        this.associatedSettings = AssociatedSettings.getInstance();
    }

    /*
     * (non-Javadoc)
     * @see java.net.Authenticator#getPasswordAuthentication()
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        RequestorType requestorType = this.getRequestorType();
        if (requestorType == RequestorType.PROXY) {
            try {
                PasswordAuthentication pa = this.connectionSettings
                        .getPasswordAuthentication();
                if (pa != null) {
                    // This could get it into an infinite loop if the
                    // credentials are wrong?
                    // Apparently there's a limit of 20 for retries. See bug
                    // #4848752 in
                    // the bug parade.
                    return pa;
                }
            } catch (Exception err) {
                throw new IllegalStateException(err);
            }
        }

        AssociatedSettings settings = this.associatedSettings;
        String userName = settings.getUserNameForHost(this.getRequestingHost());

        Frame frame = GUITasks.getTopFrame();
        AuthenticationDialog dialog = new AuthenticationDialog(frame);
        if (userName != null) {
            dialog.setUserName(userName);
        }
        dialog.setModal(true);
        dialog.setTitle("Authentication Required");
        dialog.pack();
        dialog.setLocationByPlatform(true);
        dialog.setVisible(true);
        PasswordAuthentication pa = dialog.getAuthentication();
        if (pa != null) {
            settings.setUserNameForHost(this.getRequestingHost(),
                    pa.getUserName());
            settings.save();
        }
        return pa;
    }
}
