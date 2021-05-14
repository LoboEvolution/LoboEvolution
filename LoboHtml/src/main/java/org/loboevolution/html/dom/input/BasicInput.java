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

package org.loboevolution.html.dom.input;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLInputElement;
import org.loboevolution.html.dom.domimpl.HTMLBasicInputElement;
import org.loboevolution.html.js.Executor;
import org.loboevolution.store.InputStore;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>BasicInput class.</p>
 */
public class BasicInput implements FocusListener, KeyListener, CaretListener, MouseListener {

    private HTMLBasicInputElement element;

    private JTextComponent jComponent;

    @Override
    public void focusGained(FocusEvent e) {
        if (element.getOnfocus() != null) {
            Executor.executeFunction(element, element.getOnfocus(), null, new Object[]{});
        }

        if (element.getOnfocusin() != null) {
            Executor.executeFunction(element, element.getOnfocusin(), null, new Object[]{});
        }
    }

    @Override
    public void focusLost(FocusEvent event) {
        final boolean autocomplete = element.isAutocomplete();
        final String baseUrl = element.getBaseURI();
        final String type = element instanceof HTMLInputElement ? ((HTMLInputElement)element).getType() : "";

        String selectedText = jComponent.getSelectedText();
        if (Strings.isNotBlank(selectedText) && Strings.isNotBlank(element.getValue())) {
            Pattern word = Pattern.compile(selectedText);
            Matcher match = word.matcher(element.getValue());

            while (match.find()) {
                element.setSelectionRange(match.start(), match.end() - 1);
            }
        }

        if (autocomplete || "password".equals(type)) {
            final String text = jComponent.getText();
            final boolean isNavigation = element.getUserAgentContext().isNavigationEnabled();
            InputStore.deleteInput(text, baseUrl);
            InputStore.insertLogin(type, text, baseUrl, isNavigation);
        }

        if (element.getOnblur() != null) {
            Executor.executeFunction(element, element.getOnblur(), null, new Object[]{});
        }

        if (element.getOnfocusout() != null) {
            jComponent.setText(element.getValue());
            Executor.executeFunction(element, element.getOnfocusout(), null, new Object[]{});
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (element.getOnkeydown() != null) {
            Executor.executeFunction(element, element.getOnkeydown(), null, new Object[]{});
        }

        if (element.getOnkeypress() != null) {
            Executor.executeFunction(element, element.getOnkeypress(), null, new Object[]{});
        }

        if (element.getOninput() != null) {
            element.setValue(Strings.isBlank(element.getValue()) ? String.valueOf(e.getKeyChar()) : element.getValue() + e.getKeyChar());
            Executor.executeFunction(element, element.getOninput(), null, new Object[]{});
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (element.getOnkeyup() != null) {
            Executor.executeFunction(element, element.getOnkeyup(), null, new Object[]{});
        }
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        final int dot = e.getDot();
        final int mark = e.getMark();

        if (dot != mark && element.getOnselect() != null) {
            Executor.executeFunction(element, element.getOnselect(), null, new Object[]{});
        }
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
        if (element.getOnmouseover() != null) {
            Executor.executeFunction(element, element.getOnmouseover(), null, new Object[]{});
        }
    }

    public void mousePressed(MouseEvent e) {
        if (element.getOnkeypress() != null) {
            Executor.executeFunction(element, element.getOnkeypress(), null, new Object[]{});
        }

        if (element.getOnkeydown() != null) {
            Executor.executeFunction(element, element.getOnkeydown(), null, new Object[]{});
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (element.getOnkeyup() != null) {
            Executor.executeFunction(element, element.getOnkeyup(), null, new Object[]{});
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    public HTMLBasicInputElement getElement() {
        return element;
    }

    public void setElement(HTMLBasicInputElement element) {
        this.element = element;
    }

    public JTextComponent getjComponent() {
        return jComponent;
    }

    public void setjComponent(JTextComponent jComponent) {
        this.jComponent = jComponent;
    }
}
