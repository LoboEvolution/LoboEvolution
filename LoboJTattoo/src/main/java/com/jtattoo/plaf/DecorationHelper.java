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

package com.jtattoo.plaf;

import java.awt.Color;
import java.awt.Window;

/**
 * <p>DecorationHelper class.</p>
 *
 * Author Michael Hagen
 *
 */
public class DecorationHelper {

	/**
	 * <p>isTranslucentWindowSupported.</p>
	 *
	 * @return a boolean.
	 */
	public static boolean isTranslucentWindowSupported() {
		return JTattooUtilities.getJavaVersion() >= 1.7 && (JTattooUtilities.isMac() || JTattooUtilities.isWindows());
	}

	/**
	 * <p>setTranslucentWindow.</p>
	 *
	 * @param wnd a {@link java.awt.Window} object.
	 * @param translucent a boolean.
	 */
	public static void setTranslucentWindow(Window wnd, boolean translucent) {
		if (isTranslucentWindowSupported()) {
			if (JTattooUtilities.getJavaVersion() >= 1.7) {
				if (translucent) {
					if (wnd.getBackground() == null || !wnd.getBackground().equals(new Color(0, 0, 0, 0))) {
						wnd.setBackground(new Color(0, 0, 0, 0));
					}
				} else {
					if (wnd.getBackground() == null || !wnd.getBackground().equals(new Color(0, 0, 0, 0xff))) {
						wnd.setBackground(new Color(0, 0, 0, 0xff));
					}
				}
			}
		}
	}

	private DecorationHelper() {
	}

} // end of class DecorationHelper
