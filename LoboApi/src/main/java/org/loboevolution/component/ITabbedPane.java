package org.loboevolution.component;

import javax.swing.*;
import java.awt.*;

public interface ITabbedPane {
  /**
   * Indicates the relative location of the tab pane within a series of
   * tabbed panes.
   *
   * @return the index, starting from the left-most pane
   */
  int getIndex();

  /**
   * Associates a pop-up menu with the tabbed pane using the given panel
   * as the pop-up menu's parent.
   *
   * @param panel The parent to the pop-up menu to create.
   */
  void setComponentPopupMenu( IBrowserPanel panel );

  /**
   * Returns the actively selected tab in the list of tabs.
   *
   * @return A zero-based index.
   */
  int getSelectedIndex();

  /**
   * Changes the selected tab.
   *
   * @param index A zero-based index.
   */
  void setSelectedIndex( int index );

  /**
   * Removes the tab at the given index from the tabbed panel.
   *
   * @param index Zero-based index.
   */
  void remove( int index );

  /**
   * Inserts a new tab into the panel at the given index.
   *
   * @param title     Descriptive tab title.
   * @param icon      Small identifying icon.
   * @param component Parent component.
   * @param tip       Tooltip.
   * @param index     Zero-based index.
   */
  void insertTab(
    String title, Icon icon, Component component, String tip, int index );
}
