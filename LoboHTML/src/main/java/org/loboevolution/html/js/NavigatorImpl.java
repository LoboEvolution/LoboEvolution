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

package org.loboevolution.html.js;

import org.loboevolution.html.js.geolocation.Geolocation;
import org.loboevolution.html.node.js.Navigator;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.net.UserAgent;

/**
 * <p>Navigator class.</p>
 */
public class NavigatorImpl extends AbstractScriptableDelegate implements Navigator {
	
	private final WindowImpl window;
	
	/**
	 * <p>Constructor for Navigator.</p>
	 *
	 * @param window a {@link org.loboevolution.html.js.WindowImpl} object.
	 */
	public NavigatorImpl(WindowImpl window) {
		this.window = window;
	}

	/**
	 * <p>getGeolocation.</p>
	 *
	 * @return a {@link org.loboevolution.html.js.geolocation.Geolocation} object.
	 */
	public Geolocation getGeolocation() {
		return new Geolocation(window);
	}

	/**
	 * <p>isOnLine.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isOnLine() {
		return true;
	}

	/**
	 * <p>getLanguage.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLanguage() {
		return "EN";
	}

	/**
	 * <p>getAppName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getAppName() {
		return "Lobo Evolution";
	}

	/**
	 * <p>getAppVersion.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getAppVersion() {
		return "1.0";
	}

	/**
	 * <p>getPlatform.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPlatform() {
		return System.getProperty("os.name");
	}

	/**
	 * <p>getProduct.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getProduct() {
		return this.getAppName();
	}

	/**
	 * <p>getUserAgent.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUserAgent() {
		return UserAgent.getUserAgent();
	}

	/** {@inheritDoc} */
	@Override
	public int getMaxTouchPoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMsManipulationViewsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public int getMsMaxTouchPoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMsPointerEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isPointerEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean vibrate(int pattern) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean vibrate(int... pattern) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return "[object Navigator]";
	}
}
