package org.loboevolution.html.node;

/**
 * The validity states that an element can be in, with respect to constraint validation. Together, they help explain why an element's value fails to validate, if it's not valid.
 */
public interface ValidityState {

    boolean isBadInput();

    
    boolean isCustomError();

    
    boolean isPatternMismatch();

    
    boolean isRangeOverflow();

    
    boolean isRangeUnderflow();

    
    boolean isStepMismatch();

    
    boolean isTooLong();

    
    boolean isTooShort();

    
    boolean isTypeMismatch();

    
    boolean isValid();

    
    boolean isValueMissing();


}

