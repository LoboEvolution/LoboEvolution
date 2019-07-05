package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;

import org.lobo.common.Strings;
import org.lobo.common.WrapperLayout;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.html.domimpl.HTMLInputElementImpl;

public class InputColorPickerControl extends BaseInputControl{

	private static final long serialVersionUID = 1L;

	private JButton widget = new JButton("Choose Color");

    public InputColorPickerControl(final HTMLBaseInputElement modelNode) {
        super(modelNode);
        this.setLayout(WrapperLayout.getInstance());
        widget.setContentAreaFilled(false);

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
				Color c = JColorChooser.showDialog(null, "Choose a Color", null);
				String value = "#" + Integer.toHexString(c.getRGB()).substring(2);
				modelNode.setValue(value);
				widget.setToolTipText(value);
				widget.setBackground(c);

			}
		});
    }
  
    @Override
    public void reset(int availWidth, int availHeight) {
        super.reset(availWidth, availHeight);
        RUIControl ruiControl = this.ruicontrol;
        JButton button = this.widget;
        button.setContentAreaFilled(!ruiControl.hasBackground());
        Color foregroundColor = ruiControl.getForegroundColor();
        if (foregroundColor != null) {
            button.setForeground(foregroundColor);
        }
        HTMLInputElementImpl element = (HTMLInputElementImpl) this.controlElement;
        String text = element.getValue();
        if (Strings.isBlank(text)) {
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

    @Override
    public void click() {
        this.widget.doClick();
    }

    @Override
    public String getValue() {
        return this.widget.getText();
    }

    @Override
    public void setDisabled(boolean disabled) {
        super.setDisabled(disabled);
        this.widget.setEnabled(!disabled);
    }
    @Override
    public void setValue(String value) {
        this.widget.setText(value);
    }
}
