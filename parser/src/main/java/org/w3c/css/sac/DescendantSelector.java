/*
 * (c) COPYRIGHT 1999 World Wide Web Consortium
 * (Massachusetts Institute of Technology, Institut National de Recherche
 *  en Informatique et en Automatique, Keio University).
 * All Rights Reserved. http://www.w3.org/Consortium/Legal/
 *
 * $Id: DescendantSelector.java 477010 2006-11-20 02:54:38Z mrglavas $
 */
package org.w3c.css.sac;

/**
 * The Interface DescendantSelector.
 *
 * @author Philippe Le Hegaret
 * @version $Revision: 477010 $
 * @see Selector#SAC_DESCENDANT_SELECTOR
 * @see Selector#SAC_CHILD_SELECTOR
 */
public interface DescendantSelector extends Selector {

	/**
	 * Returns the parent selector.
	 *
	 * @return the ancestor selector
	 */
	public Selector getAncestorSelector();

	/*
	 * Returns the simple selector.
	 */
	/**
	 * Gets the simple selector.
	 *
	 * @return the simple selector
	 */
	public SimpleSelector getSimpleSelector();
}
