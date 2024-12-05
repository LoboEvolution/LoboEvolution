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

import org.loboevolution.js.Window;

/**
 * The MouseEvent interface represents events that occur due to the user interacting with a pointing device (such as a mouse). Common events using this interface include click, dblclick, mouseup, mousedown.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent">MouseEvent - MDN</a>
 * @see <a href="https://w3c.github.io/uievents/#interface-mouseevent"># interface-mouseevent</a>
 * @see <a href="https://drafts.csswg.org/cssom-view/#extensions-to-the-mouseevent-interface"># extensions-to-the-mouseevent-interface</a>
 * @see <a href="https://w3c.github.io/pointerlock/#extensions-to-the-mouseevent-interface"># extensions-to-the-mouseevent-interface</a>
 */
public interface MouseEvent extends UIEvent {

    /**
     * The MouseEvent.altKey read-only property is a Boolean that indicates whether the alt key was pressed or not when a given mouse event occurs.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/altKey">MouseEvent.altKey - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-MouseEvent-altKey">MouseEvent.altkey - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-MouseEvent">MouseEvent.altkey - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    Boolean getAltKey();

    /**
     * The MouseEvent.button read-only property indicates which button was pressed on the mouse to trigger the event.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/button">MouseEvent.button - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-MouseEvent-button">MouseEvent.button - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-MouseEvent">MouseEvent.button - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    Long getButton();

    /**
     * The MouseEvent.buttons read-only property indicates which buttons are pressed on the mouse (or other input device) when a mouse event is triggered.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/buttons">MouseEvent.buttons - MDN</a>
     * @see <a href="https://w3c.github.io/uievents/#dom-mouseevent-buttons">MouseEvent.buttons - UI Events</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-MouseEvent-buttons">MouseEvent.buttons - Document Object Model (DOM) Level 3 Events Specification</a>
     */
    Long getButtons();

    /**
     * The clientX read-only property of the MouseEvent interface provides the horizontal coordinate within the application's viewport at which the event occurred (as opposed to the coordinate within the page).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/clientX">MouseEvent.clientX - MDN</a>
     * @see <a href="https://drafts.csswg.org/cssom-view/#dom-mouseevent-clientx">clientX - CSS Object Model (CSSOM) View Module</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-MouseEvent-clientX">MouseEvent.clientX - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-MouseEvent">MouseEvent.clientX - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    Long getClientX();

    /**
     * The clientY read-only property of the MouseEvent interface provides the vertical coordinate within the application's viewport at which the event occurred (as opposed to the coordinate within the page).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/clientY">MouseEvent.clientY - MDN</a>
     * @see <a href="https://drafts.csswg.org/cssom-view/#dom-mouseevent-clienty">clientY - CSS Object Model (CSSOM) View Module</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-MouseEvent-clientY">MouseEvent.clientY - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-MouseEvent">MouseEvent.clientY - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    Long getClientY();

    /**
     * The MouseEvent.ctrlKey read-only property is a Boolean that indicates whether the ctrl key was pressed or not when a given mouse event occurs.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/ctrlKey">MouseEvent.ctrlKey - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-MouseEvent-ctrlKey">MouseEvent.ctrlKey - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-MouseEvent">MouseEvent.ctrlKey - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    Boolean getCtrlKey();

    /**
     * The MouseEvent.metaKey read-only property is a Boolean that indicates whether the meta key was pressed or not when a given mouse event occurs.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/metaKey">MouseEvent.metaKey - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-MouseEvent-metaKey">MouseEvent.metaKey - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-MouseEvent">MouseEvent.metaKey - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    Boolean getMetaKey();

    /**
     * The movementX read-only property of the MouseEvent interface provides the difference in the X coordinate of the mouse pointer between the given event and the previous mousemove event. In other words, the value of the property is computed like this: currentEvent.movementX = currentEvent.screenX - previousEvent.screenX.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/movementX">MouseEvent.movementX - MDN</a>
     * @see <a href="https://w3c.github.io/pointerlock/#dom-mouseevent-movementx">MouseEvent.movementX - Pointer Lock</a>
     */
    Long getMovementX();

    /**
     * The movementY read-only property of the MouseEvent interface provides the difference in the Y coordinate of the mouse pointer between the given event and the previous mousemove event. In other words, the value of the property is computed like this: currentEvent.movementY = currentEvent.screenY - previousEvent.screenY.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/movementY">MouseEvent.movementY - MDN</a>
     * @see <a href="https://w3c.github.io/pointerlock/#dom-mouseevent-movementy">MouseEvent.movementY - Pointer Lock</a>
     */
    Long getMovementY();

    /**
     * The offsetX read-only property of the MouseEvent interface provides the offset in the X coordinate of the mouse pointer between that event and the padding edge of the target node.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/offsetX">MouseEvent.offsetX - MDN</a>
     * @see <a href="https://drafts.csswg.org/cssom-view/#dom-mouseevent-offsetx">MouseEvent - CSS Object Model (CSSOM) View Module</a>
     */
    Long getOffsetX();

    /**
     * The offsetY read-only property of the MouseEvent interface provides the offset in the Y coordinate of the mouse pointer between that event and the padding edge of the target node.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/offsetY">MouseEvent.offsetY - MDN</a>
     * @see <a href="https://drafts.csswg.org/cssom-view/#dom-mouseevent-offsety">MouseEvent - CSS Object Model (CSSOM) View Module</a>
     */
    Long getOffsetY();

    /**
     * The pageX read-only property of the MouseEvent interface returns the X (horizontal) coordinate (in pixels) at which the mouse was clicked, relative to the left edge of the entire document.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/pageX">MouseEvent.pageX - MDN</a>
     * @see <a href="https://drafts.csswg.org/cssom-view/#dom-mouseevent-pagex">pageX - CSS Object Model (CSSOM) View Module</a>
     * @see <a href="https://www.w3.org/TR/touch-events/#widl-Touch-pageX">pageX - Touch Events</a>
     */
    Long getPageX();

    /**
     * The pageY read-only property of the MouseEvent interface returns the Y (vertical) coordinate in pixels of the event relative to the whole document. This property takes into account any vertical scrolling of the page.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/pageY">MouseEvent.pageY - MDN</a>
     * @see <a href="https://drafts.csswg.org/cssom-view/#dom-mouseevent-pagey">pageY - CSS Object Model (CSSOM) View Module</a>
     * @see <a href="https://www.w3.org/TR/touch-events/#widl-Touch-pageY">pageY - Touch Events</a>
     */
    Long getPageY();

    /**
     * The MouseEvent.relatedTarget read-only property is the secondary target for the mouse event, if there is one. That is:
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/relatedTarget">MouseEvent.relatedTarget - MDN</a>
     * @see <a href="https://w3c.github.io/uievents/#dom-mouseevent-relatedtarget">MouseEvent.relatedTarget - UI Events</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-MouseEvent-relatedTarget">MouseEvent.relatedTarget - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-MouseEvent">MouseEvent.altkey - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    EventTarget relatedTarget();

    /**
     * The screenX read-only property of the MouseEvent interface provides the horizontal coordinate (offset) of the mouse pointer in global (screen) coordinates.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/screenX">MouseEvent.screenX - MDN</a>
     * @see <a href="https://drafts.csswg.org/cssom-view/#dom-mouseevent-screenx">screenX - CSS Object Model (CSSOM) View Module</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-MouseEvent-screenX">MouseEvent.screenX - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-MouseEvent">MouseEvent.sceenX - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    Long getScreenX();

    /**
     * The screenY read-only property of the MouseEvent interface provides the vertical coordinate (offset) of the mouse pointer in global (screen) coordinates.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/screenY">MouseEvent.screenY - MDN</a>
     * @see <a href="https://drafts.csswg.org/cssom-view/#dom-mouseevent-screeny">screenY - CSS Object Model (CSSOM) View Module</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-MouseEvent-screenY">MouseEvent.screenY - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-MouseEvent">MouseEvent.sceenY - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    Long getScreenY();

    /**
     * The MouseEvent.shiftKey read-only property is a Boolean that indicates whether the shift key was pressed or not when a given mouse event occurs.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/shiftKey">MouseEvent.shiftKey - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-MouseEvent-shiftKey">MouseEvent.shiftKey - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-MouseEvent">MouseEvent.shiftKey - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    Boolean getShiftKey();

    /**
     * The MouseEvent.x property is an alias for the MouseEvent.clientX property.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/x">MouseEvent.x - MDN</a>
     * @see <a href="https://drafts.csswg.org/cssom-view/#dom-mouseevent-x">MouseEvent.x - CSS Object Model (CSSOM) View Module</a>
     */
    Long getX();

    /**
     * The MouseEvent.y property is an alias for the MouseEvent.clientY property.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/y">MouseEvent.y - MDN</a>
     * @see <a href="https://drafts.csswg.org/cssom-view/#dom-mouseevent-y">MouseEvent.y - CSS Object Model (CSSOM) View Module</a>
     */
    Long getY();

    /**
     * The MouseEvent.getModifierState() method returns the current state of the specified modifier key: true if the modifier is active (i.e., the modifier key is pressed or locked), otherwise, false.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/getModifierState">MouseEvent.getModifierState - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#widl-MouseEvent-getModifierState">getModifierState() - Document Object Model (DOM) Level 3 Events Specification</a>
     */
    Boolean getModifierState(String keyArg);

    /**
     * The MouseEvent.initMouseEvent() method initializes the value of a mouse event once it's been created (normally using the Document.createEvent() method).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/initMouseEvent">MouseEvent.initMouseEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#idl-interface-MouseEvent-initializers">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-Event-initMouseEvent">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initMouseEvent(String typeArg, Boolean bubblesArg,
                        Boolean cancelableArg, Window viewArg, Double detailArg, Double screenXArg,
                        Double screenYArg, Double clientXArg, Double clientYArg, Boolean ctrlKeyArg, Boolean altKeyArg,
                        Boolean shiftKeyArg, Boolean metaKeyArg, Double buttonArg,
                        EventTarget relatedTargetArg);

    /**
     * The MouseEvent.initMouseEvent() method initializes the value of a mouse event once it's been created (normally using the Document.createEvent() method).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/initMouseEvent">MouseEvent.initMouseEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#idl-interface-MouseEvent-initializers">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-Event-initMouseEvent">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initMouseEvent(String typeArg, Boolean bubblesArg,
                        Boolean cancelableArg, Window viewArg, Double detailArg, Double screenXArg,
                        Double screenYArg, Double clientXArg, Double clientYArg, Boolean ctrlKeyArg, Boolean altKeyArg,
                        Boolean shiftKeyArg, Boolean metaKeyArg, Double buttonArg);

    /**
     * The MouseEvent.initMouseEvent() method initializes the value of a mouse event once it's been created (normally using the Document.createEvent() method).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/initMouseEvent">MouseEvent.initMouseEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#idl-interface-MouseEvent-initializers">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-Event-initMouseEvent">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initMouseEvent(String typeArg, Boolean bubblesArg,
                        Boolean cancelableArg, Window viewArg, Double detailArg, Double screenXArg,
                        Double screenYArg, Double clientXArg, Double clientYArg, Boolean ctrlKeyArg, Boolean altKeyArg,
                        Boolean shiftKeyArg, Boolean metaKeyArg);

    /**
     * The MouseEvent.initMouseEvent() method initializes the value of a mouse event once it's been created (normally using the Document.createEvent() method).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/initMouseEvent">MouseEvent.initMouseEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#idl-interface-MouseEvent-initializers">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-Event-initMouseEvent">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initMouseEvent(String typeArg, Boolean bubblesArg,
                        Boolean cancelableArg, Window viewArg, Double detailArg, Double screenXArg,
                        Double screenYArg, Double clientXArg, Double clientYArg, Boolean ctrlKeyArg, Boolean altKeyArg,
                        Boolean shiftKeyArg);

    /**
     * The MouseEvent.initMouseEvent() method initializes the value of a mouse event once it's been created (normally using the Document.createEvent() method).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/initMouseEvent">MouseEvent.initMouseEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#idl-interface-MouseEvent-initializers">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-Event-initMouseEvent">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initMouseEvent(String typeArg, Boolean bubblesArg,
                        Boolean cancelableArg, Window viewArg, Double detailArg, Double screenXArg,
                        Double screenYArg, Double clientXArg, Double clientYArg, Boolean ctrlKeyArg, Boolean altKeyArg);

    /**
     * The MouseEvent.initMouseEvent() method initializes the value of a mouse event once it's been created (normally using the Document.createEvent() method).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/initMouseEvent">MouseEvent.initMouseEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#idl-interface-MouseEvent-initializers">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-Event-initMouseEvent">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initMouseEvent(String typeArg, Boolean bubblesArg,
                        Boolean cancelableArg, Window viewArg, Double detailArg, Double screenXArg,
                        Double screenYArg, Double clientXArg, Double clientYArg, Boolean ctrlKeyArg);

    /**
     * The MouseEvent.initMouseEvent() method initializes the value of a mouse event once it's been created (normally using the Document.createEvent() method).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/initMouseEvent">MouseEvent.initMouseEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#idl-interface-MouseEvent-initializers">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-Event-initMouseEvent">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initMouseEvent(String typeArg, Boolean bubblesArg,
                        Boolean cancelableArg, Window viewArg, Double detailArg, Double screenXArg,
                        Double screenYArg, Double clientXArg, Double clientYArg);

    /**
     * The MouseEvent.initMouseEvent() method initializes the value of a mouse event once it's been created (normally using the Document.createEvent() method).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/initMouseEvent">MouseEvent.initMouseEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#idl-interface-MouseEvent-initializers">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-Event-initMouseEvent">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initMouseEvent(String typeArg, Boolean bubblesArg,
                        Boolean cancelableArg, Window viewArg, Double detailArg, Double screenXArg,
                        Double screenYArg, Double clientXArg);

    /**
     * The MouseEvent.initMouseEvent() method initializes the value of a mouse event once it's been created (normally using the Document.createEvent() method).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/initMouseEvent">MouseEvent.initMouseEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#idl-interface-MouseEvent-initializers">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-Event-initMouseEvent">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initMouseEvent(String typeArg, Boolean bubblesArg,
                        Boolean cancelableArg, Window viewArg, Double detailArg, Double screenXArg,
                        Double screenYArg);

    /**
     * The MouseEvent.initMouseEvent() method initializes the value of a mouse event once it's been created (normally using the Document.createEvent() method).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/initMouseEvent">MouseEvent.initMouseEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#idl-interface-MouseEvent-initializers">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-Event-initMouseEvent">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initMouseEvent(String typeArg, Boolean bubblesArg,
                        Boolean cancelableArg, Window viewArg, Double detailArg, Double screenXArg);

    /**
     * The MouseEvent.initMouseEvent() method initializes the value of a mouse event once it's been created (normally using the Document.createEvent() method).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/initMouseEvent">MouseEvent.initMouseEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#idl-interface-MouseEvent-initializers">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-Event-initMouseEvent">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initMouseEvent(String typeArg, Boolean bubblesArg,
                        Boolean cancelableArg, Window viewArg, Double detailArg);

    /**
     * The MouseEvent.initMouseEvent() method initializes the value of a mouse event once it's been created (normally using the Document.createEvent() method).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/initMouseEvent">MouseEvent.initMouseEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#idl-interface-MouseEvent-initializers">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-Event-initMouseEvent">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initMouseEvent(String typeArg, Boolean bubblesArg,
                        Boolean cancelableArg, Window viewArg);

    /**
     * The MouseEvent.initMouseEvent() method initializes the value of a mouse event once it's been created (normally using the Document.createEvent() method).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/initMouseEvent">MouseEvent.initMouseEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#idl-interface-MouseEvent-initializers">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-Event-initMouseEvent">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initMouseEvent(String typeArg, Boolean bubblesArg,
                        Boolean cancelableArg);

    /**
     * The MouseEvent.initMouseEvent() method initializes the value of a mouse event once it's been created (normally using the Document.createEvent() method).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/initMouseEvent">MouseEvent.initMouseEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#idl-interface-MouseEvent-initializers">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-Event-initMouseEvent">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initMouseEvent(String typeArg, Boolean bubblesArg);

    /**
     * The MouseEvent.initMouseEvent() method initializes the value of a mouse event once it's been created (normally using the Document.createEvent() method).
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent/initMouseEvent">MouseEvent.initMouseEvent - MDN</a>
     * @see <a href="https://www.w3.org/TR/2014/WD-DOM-Level-3-Events-20140925/#idl-interface-MouseEvent-initializers">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 3 Events Specification</a>
     * @see <a href="https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-Event-initMouseEvent">MouseEvent.initMouseEvent() - Document Object Model (DOM) Level 2 Events Specification</a>
     */
    void initMouseEvent(String typeArg);
}
