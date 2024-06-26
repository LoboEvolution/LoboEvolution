/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

package org.loboevolution.events;

/**
 * <p>EventModifierInit interface.</p>
 *
 *
 *
 */
public interface EventModifierInit extends UIEvent.UIEventInit {

	/**
	 * <p>isAltKey.</p>
	 *
	 * @return a boolean.
	 */
	boolean isAltKey();

	/**
	 * <p>setAltKey.</p>
	 *
	 * @param altKey a boolean.
	 */
	void setAltKey(boolean altKey);

	/**
	 * <p>isCtrlKey.</p>
	 *
	 * @return a boolean.
	 */
	boolean isCtrlKey();

	/**
	 * <p>setCtrlKey.</p>
	 *
	 * @param ctrlKey a boolean.
	 */
	void setCtrlKey(boolean ctrlKey);

	/**
	 * <p>isMetaKey.</p>
	 *
	 * @return a boolean.
	 */
	boolean isMetaKey();

	/**
	 * <p>setMetaKey.</p>
	 *
	 * @param metaKey a boolean.
	 */
	void setMetaKey(boolean metaKey);

	/**
	 * <p>isModifierAltGraph.</p>
	 *
	 * @return a boolean.
	 */
	boolean isModifierAltGraph();

	/**
	 * <p>setModifierAltGraph.</p>
	 *
	 * @param modifierAltGraph a boolean.
	 */
	void setModifierAltGraph(boolean modifierAltGraph);

	/**
	 * <p>isModifierCapsLock.</p>
	 *
	 * @return a boolean.
	 */
	boolean isModifierCapsLock();

	/**
	 * <p>setModifierCapsLock.</p>
	 *
	 * @param modifierCapsLock a boolean.
	 */
	void setModifierCapsLock(boolean modifierCapsLock);

	/**
	 * <p>isModifierFn.</p>
	 *
	 * @return a boolean.
	 */
	boolean isModifierFn();

	/**
	 * <p>setModifierFn.</p>
	 *
	 * @param modifierFn a boolean.
	 */
	void setModifierFn(boolean modifierFn);

	/**
	 * <p>isModifierFnLock.</p>
	 *
	 * @return a boolean.
	 */
	boolean isModifierFnLock();

	/**
	 * <p>setModifierFnLock.</p>
	 *
	 * @param modifierFnLock a boolean.
	 */
	void setModifierFnLock(boolean modifierFnLock);

	/**
	 * <p>isModifierHyper.</p>
	 *
	 * @return a boolean.
	 */
	boolean isModifierHyper();

	/**
	 * <p>setModifierHyper.</p>
	 *
	 * @param modifierHyper a boolean.
	 */
	void setModifierHyper(boolean modifierHyper);

	/**
	 * <p>isModifierNumLock.</p>
	 *
	 * @return a boolean.
	 */
	boolean isModifierNumLock();

	/**
	 * <p>setModifierNumLock.</p>
	 *
	 * @param modifierNumLock a boolean.
	 */
	void setModifierNumLock(boolean modifierNumLock);

	/**
	 * <p>isModifierScrollLock.</p>
	 *
	 * @return a boolean.
	 */
	boolean isModifierScrollLock();

	/**
	 * <p>setModifierScrollLock.</p>
	 *
	 * @param modifierScrollLock a boolean.
	 */
	void setModifierScrollLock(boolean modifierScrollLock);

	/**
	 * <p>isModifierSuper.</p>
	 *
	 * @return a boolean.
	 */
	boolean isModifierSuper();

	/**
	 * <p>setModifierSuper.</p>
	 *
	 * @param modifierSuper a boolean.
	 */
	void setModifierSuper(boolean modifierSuper);

	/**
	 * <p>isModifierSymbol.</p>
	 *
	 * @return a boolean.
	 */
	boolean isModifierSymbol();

	/**
	 * <p>setModifierSymbol.</p>
	 *
	 * @param modifierSymbol a boolean.
	 */
	void setModifierSymbol(boolean modifierSymbol);

	/**
	 * <p>isModifierSymbolLock.</p>
	 *
	 * @return a boolean.
	 */
	boolean isModifierSymbolLock();

	/**
	 * <p>setModifierSymbolLock.</p>
	 *
	 * @param modifierSymbolLock a boolean.
	 */
	void setModifierSymbolLock(boolean modifierSymbolLock);

	/**
	 * <p>isShiftKey.</p>
	 *
	 * @return a boolean.
	 */
	boolean isShiftKey();

	/**
	 * <p>setShiftKey.</p>
	 *
	 * @param shiftKey a boolean.
	 */
	void setShiftKey(boolean shiftKey);

}
