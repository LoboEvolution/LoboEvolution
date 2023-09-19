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

package org.loboevolution.html.dom.input;

import org.loboevolution.common.Strings;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.html.dom.HTMLInputElement;
import org.loboevolution.html.dom.domimpl.HTMLBasicInputElement;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.js.WindowImpl;

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
            HTMLInputElementImpl im =  (HTMLInputElementImpl)element;
            WindowImpl win = (WindowImpl) im.getDocumentNode().getDefaultView();
            HtmlRendererConfig config = win.getConfig();
            final String text = jComponent.getText();
            final boolean isNavigation = element.getUserAgentContext().isNavigationEnabled();
            config.deleteInput(text, baseUrl);
            config.insertLogin(type, text, baseUrl, isNavigation);
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

    public void mousePressed(final MouseEvent e) {
        if (element.getOnkeypress() != null) {
            Executor.executeFunction(element, element.getOnkeypress(), null, new Object[]{});
        }

        if (element.getOnkeydown() != null) {
            Executor.executeFunction(element, element.getOnkeydown(), null, new Object[]{});
        }
    }

    public void mouseReleased(final MouseEvent e) {
        if (element.getOnkeyup() != null) {
            Executor.executeFunction(element, element.getOnkeyup(), null, new Object[]{});
        }
    }

    @Override
    public void mouseExited(final MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
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
