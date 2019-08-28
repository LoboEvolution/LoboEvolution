package org.lobobrowser.html.control;


import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import org.lobobrowser.html.dom.domimpl.HTMLBaseInputElement;

/**
 * The Class InputUrlControl.
 */
public class InputUrlControl extends BaseInputTextControl {

    private static final long serialVersionUID = 1L;
    
    private static final String URL_PATTERN = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    
    private JTextField url;
    
    private String strPattern;

    public InputUrlControl(final HTMLBaseInputElement modelNode) {
        super(modelNode);
        url = (JTextField) this.widget;
        String value = modelNode.getValue();
        strPattern = modelNode.getAttribute("pattern");
        if (!isUrl(value)) {
            url.setBorder(BorderFactory.createLineBorder(Color.RED));
        } else {
            url.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        url.addKeyListener(addKeyListener());
    }

    @Override
    protected JTextComponent createTextField() {
        return new JTextField();
    }

    private KeyListener addKeyListener() {
        KeyListener keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent keyEvent) {
                JTextField url = (JTextField) keyEvent.getSource();
                if (!isUrl(url.getText())) {
                    url.setBorder(BorderFactory.createLineBorder(Color.RED));
                } else {
                    url.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }
            }
        };
        return keyListener;
    }

    private boolean isUrl(String keyCode) {
        if (keyCode != null && keyCode.length() > 0) {
            Pattern pattern = Pattern.compile((strPattern!= null && strPattern.length()>0) ? strPattern : URL_PATTERN);
            Matcher matcher = pattern.matcher(keyCode);
            return matcher.matches();
        } else {
            return true;
        }
    }
}
