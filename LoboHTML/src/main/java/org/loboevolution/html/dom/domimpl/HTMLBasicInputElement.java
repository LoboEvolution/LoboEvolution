/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ValidityState;
import org.loboevolution.jsenum.Direction;

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
