/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.domimpl;

import lombok.Getter;
import lombok.Setter;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ValidityState;
import org.loboevolution.type.Direction;

/**
 * <p>HTMLBasicInputElement class.</p>
 */
public abstract class HTMLBasicInputElement extends HTMLElementImpl {

    @Getter
    @Setter
    private boolean focusable = false;

    @Setter
    private int selectionStart = 0;

    @Setter
    private int selectionEnd = 0;

    private boolean isMaxSet = false;

    private boolean isMinSet = false;

    private int maxlength = 0;

    private int minlength = 0;

    public HTMLBasicInputElement(final String name) {
        super(name);
    }

    public abstract String getValue();

    public abstract void setValue(final String value);

    public boolean isDisabled() {
        final String disabled = getAttribute("disabled");
        return disabled != null;
    }

    public boolean isAutofocus() {
        final String disabled = getAttribute("autofocus");
        return disabled != null;
    }

    public boolean isRequired() {
        final String disabled = getAttribute("required");
        return disabled != null;
    }

    public boolean isAutocomplete() {
        final String autocomplete = this.getAttribute("autocomplete");
        return "on".equalsIgnoreCase(autocomplete);
    }

    public boolean isReadOnly() {
        final String disabled = getAttribute("readonly");
        return disabled != null;
    }

    public String getDefaultValue() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getDirName() {
        // TODO Auto-generated method stub
        return null;
    }

    public NodeList getLabels() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getPlaceholder() {
        return this.getAttribute("placeholder");
    }

    public String getSelectionDirection() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getValidationMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    public ValidityState getValidity() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isWillValidate() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean checkValidity() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean reportValidity() {
        // TODO Auto-generated method stub
        return false;
    }


    public int getSelectionStart() {
        final int textLenght = getTextLength();
        return (selectionStart > textLenght || selectionStart < 0) ? textLenght : selectionStart;
    }

    public int getSelectionEnd() {
        final int textLenght = getTextLength();
        return (selectionEnd > textLenght || selectionEnd < 0) ? textLenght : selectionEnd;
    }

    public int getMaxLength() {
        if (isMaxSet) {
            return this.maxlength;
        }

        try {
            final String maxLength = getAttribute("maxlength");
            return Integer.parseInt(maxLength.trim());
        } catch (final Exception e) {
            return Integer.MAX_VALUE;
        }
    }

    public int getMinLength() {
        if (isMinSet) {
            return this.minlength;
        }

        try {
            final String maxLength = getAttribute("minlength");
            return Integer.parseInt(maxLength.trim());
        } catch (final Exception e) {
            return -1;
        }
    }

    public int getTextLength() {
        return getValue().length();
    }


    public void setDisabled(final boolean disabled) {
        setAttribute("disabled", String.valueOf(disabled));
    }

    public void setAutocomplete(final String autocomplete) {
        this.setAttribute("autocomplete", String.valueOf(autocomplete));
    }

    public void setAutofocus(final boolean autofocus) {
        this.setAttribute("autofocus", String.valueOf(autofocus));
    }

    public void setRequired(final boolean required) {
        this.setAttribute("required", String.valueOf(required));
    }

    public void setDefaultValue(final String defaultValue) {
        // TODO Auto-generated method stub

    }

    public void setDirName(final String dirName) {
        // TODO Auto-generated method stub

    }

    public void setPlaceholder(final String placeholder) {
        this.setAttribute("placeholder", placeholder);

    }

    public void setSelectionDirection(final String selectionDirection) {
        // TODO Auto-generated method stub

    }

    public void setCustomValidity(final String error) {
        // TODO Auto-generated method stub

    }

    public void setRangeText(final String replacement) {
        // TODO Auto-generated method stub
    }

    public void setRangeText(final String replacement, final int start, final int end) {
        // TODO Auto-generated method stub
    }

    public void setSelectionRange(final int start, final int end, final Direction direction) {
        // TODO Auto-generated method stub
    }

    public void setReadOnly(final boolean readOnly) {
        setAttribute("readonly", String.valueOf(readOnly));

    }

    public void setSelectionRange(final int start, final int end) {
        setSelectionStart(start);
        setSelectionEnd(end);
    }

    public void setMaxLength(final int value) {
        this.maxlength = value;
        this.isMaxSet = true;
    }

    public void setMinLength(final int value) {
        this.minlength = value;
        this.isMinSet = true;
    }
}
