/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.node.js.WindowEventHandlers;
import org.mozilla.javascript.Function;

/**
 * <p>WindowEventHandlersImpl class.</p>
 */
public class WindowEventHandlersImpl extends GlobalEventHandlersImpl implements WindowEventHandlers {
	
    private Function onunload, onafterprint, onbeforeprint, onlanguagechange, onoffline, ononline;

	/** {@inheritDoc} */
	@Override
	public Function getOnafterprint() {
		return getEventFunction(this.onafterprint, "onafterprint");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnbeforeprint() {
		return getEventFunction(this.onbeforeprint, "onafterprint");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnlanguagechange() {
		return getEventFunction(this.onlanguagechange, "onlanguagechange");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnoffline() {
		return getEventFunction(this.onoffline, "onoffline");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnonline() {
		return getEventFunction(this.ononline, "ononline");
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnunload() {
		return getEventFunction(this.onunload, "onunload");
	}

	/** {@inheritDoc} */
	@Override
	public void setOnunload(final Function onunload) {
		this.onunload = onunload;
		
	}

	/** {@inheritDoc} */
	@Override
	public void setOnonline(final Function ononline) {
		this.ononline = ononline;
		
	}

	/** {@inheritDoc} */
	@Override
	public void setOnoffline(final Function onoffline) {
		this.onoffline = onoffline;
		
	}

	/** {@inheritDoc} */
	@Override
	public void setOnlanguagechange(final Function onlanguagechange) {
		this.onlanguagechange = onlanguagechange;
		
	}

	/** {@inheritDoc} */
	@Override
	public void setOnbeforeprint(final Function onbeforeprint) {
		this.onbeforeprint = onbeforeprint;
		
	}

	/** {@inheritDoc} */
	@Override
	public void setOnafterprint(final Function onafterprint) {
		this.onafterprint = onafterprint;
		
	}
}
