/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import org.loboevolution.js.Window;

/**
 * KeyboardEvent objects describe a user interaction with the keyboard; each event describes a single
 * interaction between the user and a key (or combination of a key with modifier keys) on the keyboard.
 * The event type (keydown, keypress, or keyup) identifies what kind of keyboard activity occurred.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent">KeyboardEvent - MDN</a>
 * @see <a href="https://w3c.github.io/uievents/#interface-keyboardevent"># interface-keyboardevent</a>
 */

public interface KeyboardEvent extends UIEvent {

    /**
     * The KeyboardEvent.altKey read-only property is a Boolean that indicates if the alt key (Option or ⌥ on OS X) was pressed (true) or not (false) when the event occured.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/altKey">KeyboardEvent.altKey - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-KeyboardEvent-altKey">KeyboardEvent.altkey - Document Object Model (DOM) Level 3 Events Specification</a>
     */

    Boolean getAltKey();

    /**
     * The charCode read-only property of the KeyboardEvent interface returns the Unicode value of a character key pressed during a keypress event.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/charCode">KeyboardEvent.charCode - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-KeyboardEvent-charCode">KeyboardEvent.charCode - Document Object Model (DOM) Level 3 Events Specification</a>
     */
    Double getCharCode();

    /**
     * The KeyboardEvent.code property represents a physical key on the keyboard (as opposed to the character generated by pressing the key). In other words, this property returns a value that isn't altered by keyboard layout or the state of the modifier keys.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/code">KeyboardEvent.code - MDN</a>
     */
    String getCode();

    /**
     * The KeyboardEvent.ctrlKey read-only property returns a Boolean that indicates if the control key was pressed (true) or not (false) when the event occured.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/ctrlKey">KeyboardEvent.ctrlKey - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-KeyboardEvent-ctrlKey">KeyboardEvent.ctrlKey - Document Object Model (DOM) Level 3 Events Specification</a>
     */
    Boolean getCtrlKey();

    /**
     * The KeyboardEvent.isComposing read-only property returns a Boolean value indicating if the event is fired within a composition session, i.e. after compositionstart and before compositionend.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/isComposing">KeyboardEvent.isComposing - MDN</a>
     * @see <a href="https://w3c.github.io/uievents/#dom-keyboardevent-iscomposing">KeyboardEvent.prototype.isComposing - UI Events</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-KeyboardEvent-isComposing">KeyboardEvent.prototype.isComposing - Document Object Model (DOM) Level 3 Events Specification</a>
     */
    Boolean getIsComposing();

    /**
     * The KeyboardEvent interface's key read-only property returns the value of the key pressed by the user, taking into consideration the state of modifier keys such as Shift as well as the keyboard locale and layout.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/key">KeyboardEvent.key - MDN</a>
     */
    String getKey();

    /**
     * The deprecated KeyboardEvent.keyCode read-only property represents a system and implementation dependent numerical code identifying the unmodified value of the pressed key.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/keyCode">KeyboardEvent.keyCode - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-KeyboardEvent-keyCode">KeyboardEvent.keyCode - Document Object Model (DOM) Level 3 Events Specification</a>
     */
    int getKeyCode();

    /**
     * The KeyboardEvent.location read-only property returns an unsigned long representing the location of the key on the keyboard or other input device.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/location">KeyboardEvent.location - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-KeyboardEvent-location">KeyboardEvent.location - Document Object Model (DOM) Level 3 Events Specification</a>
     */
    Double getLocation();

    /**
     * The KeyboardEvent.metaKey read-only property returning a Boolean that indicates if the Meta key was pressed (true) or not (false) when the event occurred. Some operating systems may intercept the key so it is never detected.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/metaKey">KeyboardEvent.metaKey - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-KeyboardEvent-metaKey">KeyboardEvent.metaKey - Document Object Model (DOM) Level 3 Events Specification</a>
     */
    Boolean getMetaKey();

    /**
     * The repeat read-only property of the KeyboardEvent interface returns a Boolean that is true if the given key is being held down such that it is automatically repeating.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/repeat">KeyboardEvent.repeat - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-KeyboardEvent-repeat">KeyboardEvent.repeat - Document Object Model (DOM) Level 3 Events Specification</a>
     */
    Boolean getRepeat();

    /**
     * The KeyboardEvent.shiftKey read-only property is a Boolean that indicates if the shift key was pressed (true) or not (false) when the event occurred.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/shiftKey">KeyboardEvent.shiftKey - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-KeyboardEvent-shiftKey">KeyboardEvent.shiftKey - Document Object Model (DOM) Level 3 Events Specification</a>
     */
    Boolean getShiftKey();

    /**
     * The KeyboardEvent.getModifierState() method returns the current state of the specified modifier key: true if the modifier is active (that is the modifier key is pressed or locked), otherwise, false.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/getModifierState">KeyboardEvent.getModifierState - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-KeyboardEvent-getModifierState">getModifierState() - Document Object Model (DOM) Level 3 Events Specification</a>
     */
    Boolean getModifierState(String modifierState);

    /**
     * The KeyboardEvent.initKeyboardEvent() method initializes the attributes of a keyboard event object. This method was introduced in draft of DOM Level 3 Events, but deprecated in newer draft. Gecko won't support this feature since implementing this method as experimental broke existing web apps (see bug 999645). Web applications should use constructor instead of this if it's available.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/initKeyboardEvent">KeyboardEvent.initKeyboardEvent - MDN</a>
     */
    void initKeyboardEvent(String typeArg, Boolean bubblesArg,
                           boolean cancelableArg, Window viewArg, String keyArg, Double locationArg,
                           boolean ctrlKey, boolean altKey, boolean shiftKey, boolean metaKey);

    /**
     * The KeyboardEvent.initKeyboardEvent() method initializes the attributes of a keyboard event object. This method was introduced in draft of DOM Level 3 Events, but deprecated in newer draft. Gecko won't support this feature since implementing this method as experimental broke existing web apps (see bug 999645). Web applications should use constructor instead of this if it's available.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/initKeyboardEvent">KeyboardEvent.initKeyboardEvent - MDN</a>
     */
    void initKeyboardEvent(String typeArg, Boolean bubblesArg,
                           boolean cancelableArg, Window viewArg, String keyArg, Double locationArg,
                           boolean ctrlKey, boolean altKey, boolean shiftKey);

    /**
     * The KeyboardEvent.initKeyboardEvent() method initializes the attributes of a keyboard event object. This method was introduced in draft of DOM Level 3 Events, but deprecated in newer draft. Gecko won't support this feature since implementing this method as experimental broke existing web apps (see bug 999645). Web applications should use constructor instead of this if it's available.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/initKeyboardEvent">KeyboardEvent.initKeyboardEvent - MDN</a>
     */
    void initKeyboardEvent(String typeArg, Boolean bubblesArg,
                           boolean cancelableArg, Window viewArg, String keyArg, Double locationArg,
                           boolean ctrlKey, boolean altKey);

    /**
     * The KeyboardEvent.initKeyboardEvent() method initializes the attributes of a keyboard event object. This method was introduced in draft of DOM Level 3 Events, but deprecated in newer draft. Gecko won't support this feature since implementing this method as experimental broke existing web apps (see bug 999645). Web applications should use constructor instead of this if it's available.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/initKeyboardEvent">KeyboardEvent.initKeyboardEvent - MDN</a>
     */
    void initKeyboardEvent(String typeArg, Boolean bubblesArg,
                           boolean cancelableArg, Window viewArg, String keyArg, Double locationArg,
                           boolean ctrlKey);

    /**
     * The KeyboardEvent.initKeyboardEvent() method initializes the attributes of a keyboard event object. This method was introduced in draft of DOM Level 3 Events, but deprecated in newer draft. Gecko won't support this feature since implementing this method as experimental broke existing web apps (see bug 999645). Web applications should use constructor instead of this if it's available.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/initKeyboardEvent">KeyboardEvent.initKeyboardEvent - MDN</a>
     */
    void initKeyboardEvent(String typeArg, Boolean bubblesArg,
                           boolean cancelableArg, Window viewArg, String keyArg, Double locationArg);

    /**
     * The KeyboardEvent.initKeyboardEvent() method initializes the attributes of a keyboard event object. This method was introduced in draft of DOM Level 3 Events, but deprecated in newer draft. Gecko won't support this feature since implementing this method as experimental broke existing web apps (see bug 999645). Web applications should use constructor instead of this if it's available.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/initKeyboardEvent">KeyboardEvent.initKeyboardEvent - MDN</a>
     */
    void initKeyboardEvent(String typeArg, Boolean bubblesArg,
                           boolean cancelableArg, Window viewArg, String keyArg);

    /**
     * The KeyboardEvent.initKeyboardEvent() method initializes the attributes of a keyboard event object. This method was introduced in draft of DOM Level 3 Events, but deprecated in newer draft. Gecko won't support this feature since implementing this method as experimental broke existing web apps (see bug 999645). Web applications should use constructor instead of this if it's available.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/initKeyboardEvent">KeyboardEvent.initKeyboardEvent - MDN</a>
     */
    void initKeyboardEvent(String typeArg, Boolean bubblesArg,
                           boolean cancelableArg, Window viewArg);

    /**
     * The KeyboardEvent.initKeyboardEvent() method initializes the attributes of a keyboard event object. This method was introduced in draft of DOM Level 3 Events, but deprecated in newer draft. Gecko won't support this feature since implementing this method as experimental broke existing web apps (see bug 999645). Web applications should use constructor instead of this if it's available.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/initKeyboardEvent">KeyboardEvent.initKeyboardEvent - MDN</a>
     */
    void initKeyboardEvent(String typeArg, Boolean bubblesArg,
                           boolean cancelableArg);

    /**
     * The KeyboardEvent.initKeyboardEvent() method initializes the attributes of a keyboard event object. This method was introduced in draft of DOM Level 3 Events, but deprecated in newer draft. Gecko won't support this feature since implementing this method as experimental broke existing web apps (see bug 999645). Web applications should use constructor instead of this if it's available.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/initKeyboardEvent">KeyboardEvent.initKeyboardEvent - MDN</a>
     */
    void initKeyboardEvent(String typeArg, Boolean bubblesArg);

    /**
     * The KeyboardEvent.initKeyboardEvent() method initializes the attributes of a keyboard event object. This method was introduced in draft of DOM Level 3 Events, but deprecated in newer draft. Gecko won't support this feature since implementing this method as experimental broke existing web apps (see bug 999645). Web applications should use constructor instead of this if it's available.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/initKeyboardEvent">KeyboardEvent.initKeyboardEvent - MDN</a>
     */
    void initKeyboardEvent(String typeArg);
}
