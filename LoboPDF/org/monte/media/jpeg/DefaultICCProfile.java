package org.monte.media.jpeg;

import java.awt.color.ICC_Profile;
import java.io.IOException;

public class DefaultICCProfile
{
    public static ICC_Profile getDefaultIccProfile() throws IOException
    {
        return ICC_Profile.getInstance(DefaultICCProfile.class.getResourceAsStream("Generic_CMYK_Profile.icc"));
    }

}
