package org.lobobrowser.html.test;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.lobobrowser.html.HtmlObject;
import org.lobobrowser.html.w3c.HTMLElement;


/**
 * Simple implementation of {@link org.lobobrowser.html.HtmlObject}.
 */
public class SimpleHtmlObject extends JComponent implements HtmlObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The element. */
	protected final HTMLElement element;

	/**
	 * Instantiates a new simple html object.
	 *
	 * @param element the element
	 */
	public SimpleHtmlObject(HTMLElement element) {
		this.element = element;
		this.setLayout(new FlowLayout());
		this.add(new JLabel("[" + element.getTagName() + "]"));
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlObject#reset(int, int)
	 */
	public void reset(int availWidth, int availHeight) {
		// nop
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlObject#destroy()
	 */
	public void destroy() {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlObject#getComponent()
	 */
	public Component getComponent() {
		return this;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlObject#resume()
	 */
	public void resume() {
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.HtmlObject#suspend()
	 */
	public void suspend() {
	}
}
