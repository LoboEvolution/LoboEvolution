
package org.lobobrowser.w3c.dom.html;

public interface ValidityState
{
    // ValidityState
    public boolean getValueMissing();
    public boolean getTypeMismatch();
    public boolean getPatternMismatch();
    public boolean getTooLong();
    public boolean getRangeUnderflow();
    public boolean getRangeOverflow();
    public boolean getStepMismatch();
    public boolean getCustomError();
    public boolean getValid();
}
