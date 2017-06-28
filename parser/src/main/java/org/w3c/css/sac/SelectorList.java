/*
 * Copyright (c) 1999 World Wide Web Consortium
 * (Massachusetts Institute of Technology, Institut National de Recherche
 *  en Informatique et en Automatique, Keio University).
 * All Rights Reserved. http://www.w3.org/Consortium/Legal/
 *
 * $Id: SelectorList.java 477010 2006-11-20 02:54:38Z mrglavas $
 */
package org.w3c.css.sac;

/**
 * The SelectorList interface provides the abstraction of an ordered collection
 * of selectors, without defining or constraining how this collection is
 * implemented.
 *
 * @author Philippe Le Hegaret
 * @version $Revision: 477010 $
 */
public interface SelectorList {

	/**
	 * Returns the length of this selector list.
	 *
	 * @return the length
	 */
	public int getLength();

	/**
	 * Returns the selector at the specified index, or <code>null</code> if this
	 * is not a valid index.
	 *
	 * @param index
	 *            the index
	 * @return the selector
	 */
	public Selector item(int index);
}
