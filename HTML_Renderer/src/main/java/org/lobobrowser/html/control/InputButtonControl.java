/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jan 15, 2006
 */
package org.lobobrowser.html.control;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.html.domimpl.HTMLInputElementImpl;
import org.lobobrowser.html.renderer.HtmlController;
import org.lobobrowser.util.gui.WrapperLayout;

/**
 * The Class InputButtonControl.
 */
public class InputButtonControl extends BaseInputControl {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The widget. */
    private final JButton widget;

    /**
     * Instantiates a new input button control.
     *
     * @param modelNode
     *            the model node
     */
    public InputButtonControl(final HTMLBaseInputElement modelNode) {
        super(modelNode);
        this.setLayout(WrapperLayout.getInstance());
        JButton widget = new JButton();
        widget.setContentAreaFilled(false);
        this.widget = widget;

        if (modelNode.getTitle() != null) {
            widget.setToolTipText(modelNode.getTitle());
        }
        widget.setVisible(modelNode.getHidden());
        widget.applyComponentOrientation(direction(modelNode.getDir()));
        widget.setEnabled(!modelNode.getDisabled());
        this.add(widget);
        widget.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                HtmlController.getInstance().onPressed(
                        InputButtonControl.this.controlElement, null, 0, 0);
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.control.BaseInputControl#reset(int, int)
     */
    @Override
    public void reset(int availWidth, int availHeight) {
        super.reset(availWidth, availHeight);
        RUIControl ruiControl = this.ruicontrol;
        JButton button = this.widget;
        button.setContentAreaFilled(!ruiControl.hasBackground());
        java.awt.Color foregroundColor = ruiControl.getForegroundColor();
        if (foregroundColor != null) {
            button.setForeground(foregroundColor);
        }
        HTMLInputElementImpl element = (HTMLInputElementImpl) this.controlElement;
        String text = element.getAttribute(HtmlAttributeProperties.VALUE);
        if ((text == null) || (text.length() == 0)) {
            String type = element.getType();
            if ("submit".equalsIgnoreCase(type)) {
                text = "Submit Query";
            } else if ("reset".equalsIgnoreCase(type)) {
                text = "Reset";
            } else {
                text = "";
            }
        }
        button.setText(text);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.dombl.InputContext#click()
     */
    @Override
    public void click() {
        this.widget.doClick();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.dombl.InputContext#getValue()
     */
    @Override
    public String getValue() {
        return this.widget.getText();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.control.BaseInputControl#setDisabled(boolean)
     */
    @Override
    public void setDisabled(boolean disabled) {
        super.setDisabled(disabled);
        this.widget.setEnabled(!disabled);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.dombl.InputContext#setValue(String)
     */
    @Override
    public void setValue(String value) {
        this.widget.setText(value);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.dombl.InputContext#resetInput()
     */
    @Override
    public void resetInput() {
        // nop
    }

    /**
     * Direction.
     *
     * @param dir
     *            the dir
     * @return the component orientation
     */
    private ComponentOrientation direction(String dir) {

        if ("ltr".equalsIgnoreCase(dir)) {
            return ComponentOrientation.LEFT_TO_RIGHT;
        } else if ("rtl".equalsIgnoreCase(dir)) {
            return ComponentOrientation.RIGHT_TO_LEFT;
        } else {
            return ComponentOrientation.UNKNOWN;
        }
    }
}
