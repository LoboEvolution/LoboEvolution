package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import org.lobobrowser.html.dom.domimpl.HTMLBaseInputElement;

public class InputEmailControl extends BaseInputTextControl {

    private static final long serialVersionUID = 1L;
    private JTextField email;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private String strPattern;

    public InputEmailControl(HTMLBaseInputElement modelNode) {
        super(modelNode);
        email = (JTextField) this.widget;
        String value = modelNode.getValue();
        strPattern = modelNode.getAttribute("pattern");
        if (!isEmail(value)) {
            email.setBorder(BorderFactory.createLineBorder(Color.RED));
        } else {
            email.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        email.addKeyListener(addKeyListener());
    }

    @Override
    protected JTextComponent createTextField() {
        return new JTextField();
    }

    private KeyListener addKeyListener() {
        KeyListener keyListener = new KeyListener() {
            public void keyPressed(KeyEvent keyEvent) {

                JTextField email = (JTextField) keyEvent.getSource();
                if (!isEmail(email.getText())) {
                    email.setBorder(BorderFactory.createLineBorder(Color.RED));
                } else {
                    email.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }
            }

            public void keyReleased(KeyEvent keyEvent) {
            }

            public void keyTyped(KeyEvent keyEvent) {
            }

        };
        return keyListener;
    }

    private boolean isEmail(String keyCode) {
        if (keyCode != null && keyCode.length() > 0) {
            Pattern pattern = Pattern.compile((strPattern!= null && strPattern.length()>0) ? strPattern : EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(keyCode);
            return matcher.matches();
        } else {
            return true;
        }
    }
}