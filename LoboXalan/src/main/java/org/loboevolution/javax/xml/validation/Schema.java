// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Schema.java

package org.loboevolution.javax.xml.validation;


// Referenced classes of package org.loboevolution.javax.xml.validation:
//            Validator, ValidatorHandler

public abstract class Schema
{

    protected Schema()
    {
        throw new RuntimeException("Stub!");
    }

    public abstract Validator newValidator();

    public abstract ValidatorHandler newValidatorHandler();
}
