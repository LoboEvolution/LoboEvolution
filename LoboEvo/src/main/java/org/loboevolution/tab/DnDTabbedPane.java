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

package org.loboevolution.tab;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.loboevolution.common.Strings;
import org.loboevolution.component.IBrowserFrame;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.component.ITabbedPane;
import org.loboevolution.store.TabStore;
import org.sexydock.tabs.jhrome.JhromeTabbedPaneUI;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * <p>DnDTabbedPane class.</p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DnDTabbedPane extends JTabbedPane implements ITabbedPane {

	private static final long serialVersionUID = 1L;

	private IBrowserPanel browserPanel;

	private int index;
	
	/**
	 * <p>Constructor for DnDTabbedPane.</p>
	 *
	 * @param browserPanel a {@link org.loboevolution.component.IBrowserPanel} object.
	 */
	public DnDTabbedPane(final IBrowserPanel browserPanel) {
		init(browserPanel);
	}

	private void init(final IBrowserPanel browserPanel) {
		setUI(new JhromeTabbedPaneUI());
		putClientProperty(JhromeTabbedPaneUI.NEW_TAB_BUTTON_VISIBLE, false);
		putClientProperty(JhromeTabbedPaneUI.TAB_CLOSE_BUTTONS_VISIBLE, true);
		putClientProperty(JhromeTabbedPaneUI.ANIMATION_FACTOR, 0.95);

		this.browserPanel = browserPanel;
		final ChangeListener changeListener = changeEvent -> {
			final JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
			final IBrowserFrame browserFrame = browserPanel.getBrowserFrame();
			final String uri = TabStore.getTab(sourceTabbedPane.getSelectedIndex());
			if (browserFrame.getToolbar() != null && Strings.isNotBlank(uri)) {
				browserFrame.getToolbar().getAddressBar().setText(uri);
			}
			setIndex(sourceTabbedPane.getSelectedIndex());
		};
		addChangeListener(changeListener);
	}

	@Override
	public void setComponentPopupMenu(final IBrowserPanel panel) {
		super.setComponentPopupMenu(new TabbedPanePopupMenu(panel));
	}

	@Override
	public void insertTab(
			final String title,
			final Icon icon,
			final Component component,
			final String tip,
			final int index) {
		super.insertTab(title, icon, component, tip, index);
	}
}
