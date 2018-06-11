/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.control.input;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import org.apache.logging.log4j.util.Strings;
import org.loboevolution.font.LAFSettings;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.domimpl.DOMElementImpl;
import org.loboevolution.html.domimpl.HTMLBaseInputElement;
import org.loboevolution.html.gui.mouse.GuiMouseImpl;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.util.gui.WrapperLayout;

import com.loboevolution.store.SQLiteCommon;

/**
 * The Class BaseInputTextControl.
 */
public abstract class BaseInputTextControl extends BaseInputControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The max length. */
	private int maxLength = -1;

	/** The widget. */
	protected JAutoTextField widget;

	/** Creates the text field. */
	protected abstract JAutoTextField createTextField();
	
	/** model Node. */
	private HTMLBaseInputElement  modelNode;

	/**
	 * Instantiates a new base input text control.
	 *
	 * @param modelNode
	 *            the model node
	 */
	public BaseInputTextControl(final HTMLBaseInputElement modelNode) {
		super(modelNode);
		this. modelNode = modelNode;
		this.widget = createAndShowGUI(modelNode);
	}
	
	private JAutoTextField createAndShowGUI(final HTMLBaseInputElement modelNode) {
		
		this.setLayout(WrapperLayout.getInstance());
		boolean autocomplete = modelNode.getAutocomplete();
		String id = modelNode.getId();
		String name = modelNode.getName();
		String type = modelNode.getType();
		
		List<String> list = autocomplete(id, name, type);
		widget = new JAutoTextField(list);
		Font font = widget.getFont();
		widget.setFont(font.deriveFont(new LAFSettings().getIstance().getFontSize()));
		widget.setSelectionColor(Color.BLUE);
		DOMElementImpl element = this.controlElement;
		if (!Strings.isBlank(element.getAttribute(VALUE))) {
			widget.setText(element.getAttribute(VALUE));
		}
		
		widget.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent event) {
				GuiMouseImpl.getInstance().onKeyDown(modelNode, event);
				GuiMouseImpl.getInstance().onKeyPress(modelNode, event);
			}

			@Override
			public void keyReleased(KeyEvent event) {
				GuiMouseImpl.getInstance().onKeyUp(modelNode, event);
			}
		});
		
		widget.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent event) {
				if (autocomplete && !"password".equalsIgnoreCase(type)) {
					insertLogin(id, name, type, widget.getText());
				}
			}
		});
		
		this.add(widget);
		return widget;
	}
	
	private List<String> autocomplete(String id, String name, String type) {
        List<String> list = autocomplete(id);
        if(list.size() > 0 ) return list;
        list = autocomplete(name);
        if(list.size() > 0 ) return list;
        list = autocomplete(type);
        if(list.size() > 0 ) return list;
        return new ArrayList<String>();
    }
	
	private List<String> autocomplete(String value) {
        List<String> autoList = new ArrayList<String>();
    	try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INPUT)) {
			pstmt.setString(1, "%"+value+"%");
			ResultSet rs = pstmt.executeQuery();
            while (rs != null && rs.next()) {
            	autoList.add(rs.getString(1));
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return autoList;
    }	
	
	
	/**
	 * Insert a new row into the search selected table
	 *
	 * @param search
	 */
	private void insertLogin(String id, String name, String type, String value) {
		UserAgentContext uac = modelNode.getUserAgentContext();
		if (uac.isNavigationEnabled()) {
			try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
					PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_INPUT)) {
				String nameValue = type;
				if (!Strings.isBlank(id)) {
					nameValue = id;
				} else if (!Strings.isBlank(name)) {
					nameValue = name;
				}

				pstmt.setString(1, nameValue);
				pstmt.setString(2, value);
				pstmt.executeUpdate();
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}
	
	public static void deleteInput() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_INPUT)) {
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
		RUIControl ruiControl = this.ruicontrol;
		String maxLengthText = this.controlElement.getAttribute(MAXLENGTH);
		if (maxLengthText != null) {
			try {
				this.maxLength = Integer.parseInt(maxLengthText);
			} catch (NumberFormatException nfe) {
				logger.error(nfe);
			}
		}

		if (ruiControl.hasBackground()) {
			widget.setBackground(ruiControl.getBackgroundColor());
		}

		widget.setMargin(new Insets(ruiControl.getMarginTop(), ruiControl.getMarginLeft(), ruiControl.getMarginBottom(),
				ruiControl.getMarginRight()));

		if (ruiControl.getBorderInsets().top == 0 && ruiControl.getBorderInsets().left == 0
				&& ruiControl.getBorderInsets().bottom == 0 && ruiControl.getBorderInsets().right == 0) {
			widget.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		} else {
			widget.setBorder(
					BorderFactory.createMatteBorder(ruiControl.getBorderInsets().top, ruiControl.getBorderInsets().left,
							ruiControl.getBorderInsets().bottom, ruiControl.getBorderInsets().right, Color.BLACK));
		}

	}

	@Override
	public int getMaxLength() {
		return this.maxLength;
	}

	@Override
	public boolean getReadOnly() {
		return !this.widget.isEditable();
	}

	@Override
	public String getValue() {
		return this.widget.getText();
	}

	@Override
	public void select() {
		this.widget.selectAll();
	}

	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		this.widget.setEnabled(!disabled);
	}

	@Override
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		this.widget.setEditable(!readOnly);
	}

	@Override
	public void setValue(String value) {
		this.widget.setText(value);
	}

	@Override
	public Dimension getPreferredSize() {
		int size = this.size;
		JAutoTextField widget = this.widget;
		FontMetrics fm = widget.getFontMetrics(widget.getFont());
		Insets insets = widget.getInsets();
		int pw;
		int ph;
		if (size == -1) {
			pw = 200;
		} else {
			pw = insets.left + insets.right + fm.charWidth('0') * size;
		}
		ph = fm.getHeight() + insets.top;
		return new Dimension(pw, ph);
	}

	@Override
	public void resetInput() {
		this.widget.setText("");
	}
}
