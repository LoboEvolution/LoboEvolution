/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.w3c.events;

import org.w3c.dom.Node;


/**
 * The public interface MutationEvent.
 */
public interface MutationEvent extends Event {
	// MutationEvent
	/** The Constant MODIFICATION. */
	short MODIFICATION = 1;

	/** The Constant ADDITION. */
	short ADDITION = 2;

	/** The Constant REMOVAL. */
	short REMOVAL = 3;

	/**
	 * Gets the related node.
	 *
	 * @return the related node
	 */
	Node getRelatedNode();

	/**
	 * Gets the prev value.
	 *
	 * @return the prev value
	 */
	String getPrevValue();

	/**
	 * Gets the new value.
	 *
	 * @return the new value
	 */
	String getNewValue();

	/**
	 * Gets the attr name.
	 *
	 * @return the attr name
	 */
	String getAttrName();

	/**
	 * Gets the attr change.
	 *
	 * @return the attr change
	 */
	short getAttrChange();

	/**
	 * Inits the mutation event.
	 *
	 * @param typeArg
	 *            the type arg
	 * @param canBubbleArg
	 *            the can bubble arg
	 * @param cancelableArg
	 *            the cancelable arg
	 * @param relatedNodeArg
	 *            the related node arg
	 * @param prevValueArg
	 *            the prev value arg
	 * @param newValueArg
	 *            the new value arg
	 * @param attrNameArg
	 *            the attr name arg
	 * @param attrChangeArg
	 *            the attr change arg
	 */
	void initMutationEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, Node relatedNodeArg,
			String prevValueArg, String newValueArg, String attrNameArg, short attrChangeArg);
}
