package org.loboevolution.tab;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * <p>DragSourceAdapterImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class DragSourceAdapterImpl extends DragSourceAdapter {

	private final DnDTabbedPane tab;

	/**
	 * <p>Constructor for DragSourceAdapterImpl.</p>
	 *
	 * @param tab a {@link org.loboevolution.tab.DnDTabbedPane} object.
	 */
	public DragSourceAdapterImpl(DnDTabbedPane tab) {
		this.tab = tab;
	}

	/** {@inheritDoc} */
	@Override
	public void dragDropEnd(DragSourceDropEvent e) {
		this.tab.getGlass().setVisible(false);
	}

	/** {@inheritDoc} */
	@Override
	public void dragEnter(DragSourceDragEvent e) {
		e.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
	}

	/** {@inheritDoc} */
	@Override
	public void dragExit(DragSourceEvent e) {
		final Point location = e.getLocation();
		try {
			if (this.tab.dragTabIdx > -1) {
				final Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_ESCAPE);
				robot.keyRelease(KeyEvent.VK_ESCAPE);

				final OuterFrame frame = new OuterFrame(this.tab);

				frame.tab = this.tab.getTabComponentAt(this.tab.dragTabIdx);
				frame.comp = this.tab.getComponentAt(this.tab.dragTabIdx);
				frame.tabTitle = this.tab.getTitleAt(this.tab.dragTabIdx);
				frame.icon = this.tab.getIconAt(this.tab.dragTabIdx);
				frame.tip = this.tab.getToolTipTextAt(this.tab.dragTabIdx);
				this.tab.removeTabAt(this.tab.dragTabIdx);
				frame.setTitle(frame.tabTitle);
				frame.getContentPane().add(frame.comp);
				frame.setSize(frame.comp.getSize());
				frame.validate();
				frame.setLocation(location.x - frame.getSize().width / 2, location.y - 5);
				frame.setVisible(true);

				// keep dragging the frame while mouse button is pressed
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.mouseMove(location.x, location.y);
				robot.mousePress(InputEvent.BUTTON1_MASK);
			}
		} catch (final AWTException e1) {
			e1.printStackTrace();
		}
	}

}
