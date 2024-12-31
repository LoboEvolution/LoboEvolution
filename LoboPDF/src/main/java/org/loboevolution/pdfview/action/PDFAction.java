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

package org.loboevolution.pdfview.action;

import lombok.Data;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

import java.io.IOException;

/**
 * The common super-class of all PDF actions.
 */
@Data
public class PDFAction {
    /**
     * the type of this action
     */
    private final String type;

    /**
     * the next action or array of actions
     */
    private PDFObject next;

    /**
     * Creates a new instance of PDFAction
     *
     * @param type a {@link java.lang.String} object.
     */
    public PDFAction(final String type) {
        this.type = type;
    }

    /**
     * Get an action of the appropriate type from a PDFObject
     *
     * @param obj  the PDF object containing the action to parse
     * @param root the root of the PDF object tree
     * @return a {@link org.loboevolution.pdfview.action.PDFAction} object.
     * @throws java.io.IOException if any.
     */
    public static PDFAction getAction(final PDFObject obj, final PDFObject root)
            throws IOException {
        // figure out the action type
        final PDFObject typeObj = obj.getDictRef("S");
        if (typeObj == null) {
            throw new PDFParseException("No action type in object: " + obj);
        }

        // create the action based on the type
        PDFAction action;
        final String type = typeObj.getStringValue();
        action = switch (type) {
            case "GoTo" -> new GoToAction(obj, root);
            case "GoToE" -> new GoToEAction(obj, root);
            case "GoToR" -> new GoToRAction(obj, root);
            case "URI" -> new UriAction(obj, root);
            case "Launch" -> new LaunchAction(obj, root);
            default ->
            /** [JK FIXME: Implement other action types! ] */
                    throw new PDFParseException("Unknown Action type: " + type);
        };

        // figure out if there is a next action
        final PDFObject nextObj = obj.getDictRef("Next");
        if (nextObj != null) {
            action.setNext(nextObj);
        }

        // return the action
        return action;
    }

}
