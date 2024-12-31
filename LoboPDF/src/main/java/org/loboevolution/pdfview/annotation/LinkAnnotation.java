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

package org.loboevolution.pdfview.annotation;

import lombok.Getter;
import org.loboevolution.pdfview.PDFDestination;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;
import org.loboevolution.pdfview.action.GoToAction;
import org.loboevolution.pdfview.action.PDFAction;

import java.io.IOException;

/**
 * **************************************************************************
 * PDF annotation describing a link to either a location within the current
 * document, a location in another PDF file, an application/file to be opened
 * or a web site.
 * In the PDF structure a link can be a destination ("DEST") or an action ("A").
 * Both ways are handled as actions internally, i.e. for getting the links
 * destination, you should get the action from this annotation object. It can be
 * one of the following actions:
 * <p>GotoAction - for a file internal destination</p>
 * <p>GoToRAction - for a destination in a remote PDF file</p>
 * <p>GoToEAction - for a destination in an embedded PDF file</p>
 * <p>UriAction - for a web link</p>
 * <p>LaunchAction - for launching an application/opening a file</p>
 * <p>
 * Author Katja Sondermann
 *
 * @since 06.07.2009
 * **************************************************************************
 */
@Getter
public class LinkAnnotation extends PDFAnnotation {

    private final PDFAction action;

    /**
     * **********************************************************************
     * Constructor
     *
     * @param annotObject a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    public LinkAnnotation(final PDFObject annotObject) throws IOException {
        super(annotObject, ANNOTATION_TYPE.LINK);
        // a link annotation can either have an action (GoTo or URI) or a destination (DEST)
        final PDFObject actionObj = annotObject.getDictRef("A");
        if (actionObj != null) {
            this.action = PDFAction.getAction(actionObj, annotObject.getRoot());
        } else {
            // if a destination is given, create a GoToAction from it
            PDFObject dest = annotObject.getDictRef("Dest");
            if (dest == null) {
                dest = annotObject.getDictRef("DEST");
            }
            if (dest != null) {
                this.action = new GoToAction(PDFDestination.getDestination(dest, annotObject.getRoot()));
            } else {
                throw new PDFParseException(
                        "Could not parse link annotation (no Action or Destination found): "
                                + annotObject);
            }
        }
    }
}
