/*
 * (c) COPYRIGHT 1999 World Wide Web Consortium
 * (Massachusetts Institute of Technology, Institut National de Recherche
 *  en Informatique et en Automatique, Keio University).
 * All Rights Reserved. http://www.w3.org/Consortium/Legal/
 *
 * $Id: SiblingSelector.java 477010 2006-11-20 02:54:38Z mrglavas $
 */
package org.w3c.css.sac;

/**
 * The Interface SiblingSelector.
 *
 * @author Philippe Le Hegaret
 * @version $Revision: 477010 $
 * @see Selector#SAC_DIRECT_ADJACENT_SELECTOR
 */
public interface SiblingSelector extends Selector {

	/** The Constant ANY_NODE. */
	public static final short ANY_NODE = 201;

	/**
	 * The node type to considered in the siblings list. All DOM node types are
	 * supported. In order to support the "any" node type, the code ANY_NODE is
	 * added to the DOM node types.
	 *
	 * @return the node type
	 */
	public short getNodeType();

	/**
	 * Returns the first selector.
	 *
	 * @return the selector
	 */
	public Selector getSelector();

	/*
	 * Returns the second selector.
	 */
	/**
	 * Gets the sibling selector.
	 *
	 * @return the sibling selector
	 */
	public SimpleSelector getSiblingSelector();
}
