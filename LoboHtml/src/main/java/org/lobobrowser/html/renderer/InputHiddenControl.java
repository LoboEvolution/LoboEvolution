package org.lobobrowser.html.renderer;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.lobobrowser.html.domimpl.HTMLBaseInputElement;

public class InputHiddenControl extends BaseInputTextControl{

    private static final long serialVersionUID = 1L;

    public InputHiddenControl(HTMLBaseInputElement modelNode) {
        super(modelNode);
    }

    @Override
    protected JTextComponent createTextField() {
        JTextField hidden = new JTextField();
        hidden.setVisible(false);
        return hidden;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(0, 0);
    }
}
