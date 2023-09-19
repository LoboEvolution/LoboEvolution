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
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.nodeimpl.event.GlobalEventHandlersImpl;
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
