/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.dom.domimpl;

/**
 * An abstract implementation of {@link org.loboevolution.html.dom.domimpl.DocumentNotificationListener} with blank
 * methods, provided for convenience.
 *
 * @author utente
 * @version $Id: $Id
 */
public abstract class DocumentNotificationAdapter implements DocumentNotificationListener {
	/** {@inheritDoc} */
	@Override
	public void allInvalidated() {
	}

	/** {@inheritDoc} */
	@Override
	public void externalScriptLoading(NodeImpl node) {
	}

	/** {@inheritDoc} */
	@Override
	public void invalidated(NodeImpl node) {
	}

	/** {@inheritDoc} */
	@Override
	public void lookInvalidated(NodeImpl node) {
	}

	/** {@inheritDoc} */
	@Override
	public void nodeLoaded(NodeImpl node) {
	}

	/** {@inheritDoc} */
	@Override
	public void positionInvalidated(NodeImpl node) {
	}

	/** {@inheritDoc} */
	@Override
	public void sizeInvalidated(NodeImpl node) {
	}

	/** {@inheritDoc} */
	@Override
	public void structureInvalidated(NodeImpl node) {
	}
}
