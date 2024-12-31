/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of che, to any person obtaining a copy
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
package org.loboevolution.html.js.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.events.UIEvent;
import org.loboevolution.js.JavaObjectWrapper;
import org.loboevolution.js.Window;
import org.mozilla.javascript.NativeObject;

import java.awt.event.InputEvent;

/**
 * <p>UIEventImpl class.</p>
 */
@Getter
@Setter
@NoArgsConstructor
public class UIEventImpl extends EventImpl implements UIEvent {

	private Window view = null;
	private Double which = 0d;
	private Double detail = 0d;

	public UIEventImpl(InputEvent inputEvent) {
		super(inputEvent);
	}

	/**
	 * <p>Constructor for UIEventImpl.</p>
	 */
	public UIEventImpl(final Object[] params) throws DOMException {
		setParams(params);
		if (params.length < 3) {
			if (params.length > 1 && params[1] != null) {
				NativeObject obj = (NativeObject) params[1];
				if(obj.get("view") == null)
					throw new DOMException(DOMException.NOT_FOUND_ERR, "Failed : Required member is undefined");
				this.view = (Window) ((JavaObjectWrapper) obj.get("view")).getJavaObject();
				setUIEventParams(obj);
			}
		} else {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Failed : 2 argument required, but only " + params.length + " present.");
		}
	}

	protected void setUIEventParams(NativeObject obj) {
		this.detail = getDoubleVal(obj,"v");
		this.which = getDoubleVal(obj,"which");
	}

	@Override
	public void initUIEvent(String type, Boolean bubbles, boolean cancelable, Window view, Double detail) {
		initUIEvent(type, bubbles, cancelable, view);
		this.detail = detail;
	}

	@Override
	public void initUIEvent(String type, Boolean bubbles, boolean cancelable, Window view) {
		super.initEvent(type, bubbles, cancelable);
		this.view = view;
	}

	@Override
	public void initUIEvent(String type, Boolean bubbles, boolean cancelable) {
		super.initEvent(type, bubbles, cancelable);
	}

	@Override
	public void initUIEvent(String type, Boolean bubbles) {
		super.initEvent(type, bubbles);
	}

	@Override
	public void initUIEvent(String type) {
		super.initEvent(type);
	}

	@Override
	public String toString() {
		return "[object UIEvent]";
	}
}
