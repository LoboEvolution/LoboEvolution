/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ValidityState;
import org.loboevolution.type.Direction;

/**
 * <p>HTMLBasicInputElement class.</p>
 */
public abstract class HTMLBasicInputElement extends HTMLElementImpl {

    private boolean focusable = false;

    private int selectionStart = 0;

    private int selectionEnd = 0;

    private boolean isMaxSet = false;

    private boolean isMinSet = false;

    private int maxlength = 0;

    private int minlength = 0;

    public HTMLBasicInputElement(String name) {
        super(name);
    }

    public abstract String getValue();

    public abstract void setValue(String value);

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
        String autocomplete = this.getAttribute("autocomplete");
        return "on".equalsIgnoreCase(autocomplete);
    }

    public boolean isReadOnly() {
        final String disabled = getAttribute("readonly");
        return disabled != null;
    }

    public boolean isFocusable() {
        return focusable;
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            return -1;
        }
    }

    public int getTextLength() {
        return getValue().length();
    }


    public void setDisabled(boolean disabled) {
        setAttribute("disabled", String.valueOf(disabled));
    }

    public void setAutocomplete(String autocomplete) {
        this.setAttribute("autocomplete", String.valueOf(autocomplete));
    }

    public void setAutofocus(boolean autofocus) {
        this.setAttribute("autofocus", String.valueOf(autofocus));
    }

    public void setRequired(boolean required) {
        this.setAttribute("required", String.valueOf(required));
    }

    public void setDefaultValue(String defaultValue) {
        // TODO Auto-generated method stub

    }

    public void setDirName(String dirName) {
        // TODO Auto-generated method stub

    }

    public void setPlaceholder(String placeholder) {
        this.setAttribute("placeholder", placeholder);

    }

    public void setSelectionDirection(String selectionDirection) {
        // TODO Auto-generated method stub

    }

    public void setCustomValidity(String error) {
        // TODO Auto-generated method stub

    }

    public void setRangeText(String replacement) {
        // TODO Auto-generated method stub
    }

    public void setRangeText(String replacement, int start, int end) {
        // TODO Auto-generated method stub
    }

    public void setSelectionRange(int start, int end, Direction direction) {
        // TODO Auto-generated method stub
    }

    public void setReadOnly(boolean readOnly) {
        setAttribute("readonly", String.valueOf(readOnly));

    }

    public void setFocusable(boolean focusable) {
        this.focusable = focusable;
    }

    public void setSelectionStart(int start) {
        this.selectionStart = start;
    }

    public void setSelectionEnd(int end) {
        this.selectionEnd = end;
    }

    public void setSelectionRange(int start, int end) {
        setSelectionStart(start);
        setSelectionEnd(end);
    }

    public void setMaxLength(int value) {
        this.maxlength = value;
        this.isMaxSet = true;
    }

    public void setMinLength(int value) {
        this.minlength = value;
        this.isMinSet = true;
    }
}
