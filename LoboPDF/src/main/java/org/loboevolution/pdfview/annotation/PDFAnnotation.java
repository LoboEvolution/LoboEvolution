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

package org.loboevolution.pdfview.annotation;

import lombok.Getter;
import org.loboevolution.pdfview.Configuration;
import org.loboevolution.pdfview.PDFCmd;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * **************************************************************************
 * Encapsulate a PDF annotation. This is only the super-class of PDF annotations,
 * which has an "unknown" annotation type.
 * Use the createAnnotation() method for getting an annotation of the correct
 * type (if implemented).
 * <p>
 * Author  Katja Sondermann
 *
 * @since 03.07.2009
 * **************************************************************************
 */
@Getter
public class PDFAnnotation {
    /**
     * Definition of some annotation sub-types
     */
    public static final String GOTO = "GoTo";
    /**
     * Constant <code>GOTOE="GoToE"</code>
     */
    public static final String GOTOE = "GoToE";
    /**
     * Constant <code>GOTOR="GoToR"</code>
     */
    public static final String GOTOR = "GoToR";
    /**
     * Constant <code>URI="URI"</code>
     */
    public static final String URI = "URI";
    private final PDFObject pdfObj;
    private final ANNOTATION_TYPE type;
    private final Float rect;
    /**
     * **********************************************************************
     * Constructor
     *
     * @param annotObject - the PDFObject which contains the annotation description
     * @throws java.io.IOException if any.
     */
    public PDFAnnotation(final PDFObject annotObject) throws IOException {
        this(annotObject, ANNOTATION_TYPE.UNKNOWN);
    }

    /**
     * **********************************************************************
     * Constructor
     *
     * @param annotObject - the PDFObject which contains the annotation description
     * @param type        a {@link org.loboevolution.pdfview.annotation.PDFAnnotation.ANNOTATION_TYPE} object.
     * @throws java.io.IOException if any.
     */
    protected PDFAnnotation(final PDFObject annotObject, final ANNOTATION_TYPE type) throws IOException {
        this.pdfObj = annotObject;
        // in case a general "PdfAnnotation" is created the type is unknown
        this.type = type;
        this.rect = this.parseRect(annotObject.getDictRef("Rect"));
    }

    /**
     * **********************************************************************
     * Create a new PDF annotation object.
     * <p>
     * Currently supported annotation types:
     * Link annotation
     *
     * @param parent a {@link org.loboevolution.pdfview.PDFObject} object.
     * @return PDFAnnotation a {@link org.loboevolution.pdfview.annotation.PDFAnnotation} object.
     * @throws java.io.IOException if any.
     */
    public static PDFAnnotation createAnnotation(final PDFObject parent) throws IOException {
        final PDFObject subtypeValue = parent.getDictRef("Subtype");
        if (subtypeValue == null) {
            return null;
        }
        final String subtypeS = subtypeValue.getStringValue();
        ANNOTATION_TYPE annotationType = ANNOTATION_TYPE.getByDefinition(subtypeS);

        //if Subtype is Widget than check if it is also a Signature
        if (annotationType == ANNOTATION_TYPE.WIDGET) {
            final PDFObject sigType = parent.getDictRef("FT");
            if (sigType != null) {
                final String sigTypeS = sigType.getStringValue();
                if (ANNOTATION_TYPE.getByDefinition(sigTypeS) == ANNOTATION_TYPE.SIGNATURE) {
                    annotationType = ANNOTATION_TYPE.getByDefinition(sigTypeS);
                }
            }
        }

        if (displayAnnotation(annotationType)) {
            final Class<?> className = annotationType.getClassName();

            final Constructor<?> constructor;
            try {
                constructor = className.getConstructor(PDFObject.class);
                return (PDFAnnotation) constructor.newInstance(parent);
            } catch (final Exception e) {
                throw new PDFParseException("Could not parse annotation!", e);
            }
        }

        return null;
    }

    private static boolean displayAnnotation(final ANNOTATION_TYPE annotationType) {
        switch (annotationType) {
            case STAMP:
                return Configuration.getInstance().isPrintStampAnnotations();
            case WIDGET:
                return Configuration.getInstance().isPrintWidgetAnnotations();
            case FREETEXT:
                return Configuration.getInstance().isPrintFreetextAnnotations();
            case LINK:
                return Configuration.getInstance().isPrintLinkAnnotations();
            case SIGNATURE:
                return Configuration.getInstance().isPrintSigantureFields();
            case UNKNOWN:
            default:
                return false;
        }
    }

    /**
     * Get a Rectangle2D.Float representation for a PDFObject that is an
     * array of four Numbers.
     *
     * @param obj a PDFObject that represents an Array of exactly four
     *            Numbers.
     * @return a {@link java.awt.geom.Rectangle2D.Float} object.
     * @throws java.io.IOException if any.
     */
    public Rectangle2D.Float parseRect(final PDFObject obj) throws IOException {
        if (obj.getType() == PDFObject.ARRAY) {
            final PDFObject[] bounds = obj.getArray();
            if (bounds.length == 4) {
                return new Rectangle2D.Float(bounds[0].getFloatValue(),
                        bounds[1].getFloatValue(),
                        bounds[2].getFloatValue() - bounds[0].getFloatValue(),
                        bounds[3].getFloatValue() - bounds[1].getFloatValue());
            } else {
                throw new PDFParseException("Rectangle definition didn't have 4 elements");
            }
        } else {
            throw new PDFParseException("Rectangle definition not an array");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.pdfObj.toString();
    }

    /**
     * Get list of pdf commands for this annotation
     *
     * @return a {@link java.util.List} object.
     */
    public List<PDFCmd> getPageCommandsForAnnotation() {
        return new ArrayList<>();
    }

    /**
     * <p>getScalingTransformation.</p>
     *
     * @param bbox a {@link java.awt.geom.Rectangle2D.Float} object.
     * @return a {@link java.awt.geom.AffineTransform} object.
     */
    protected AffineTransform getScalingTransformation(final Float bbox) {
        final AffineTransform at = new AffineTransform();
        final double scaleHeight = getRect().getHeight() / bbox.getHeight();
        final double scaleWidth = getRect().getWidth() / bbox.getWidth();
        at.scale(scaleWidth, scaleHeight);
        return at;
    }


    @Getter
    public enum ANNOTATION_TYPE {
        UNKNOWN("-", 0, PDFAnnotation.class),
        LINK("Link", 1, LinkAnnotation.class),
        WIDGET("Widget", 2, WidgetAnnotation.class),
        STAMP("Stamp", 3, StampAnnotation.class),
        FREETEXT("FreeText", 5, FreetextAnnotation.class),
        SIGNATURE("Sig", 6, WidgetAnnotation.class);

        private final String definition;
        private final int internalId;
        private final Class<?> className;

        ANNOTATION_TYPE(final String definition, final int typeId, final Class<?> className) {
            this.definition = definition;
            this.internalId = typeId;
            this.className = className;
        }

        /**
         * Get annotation type by it's type
         *
         * @param definition a {@link java.lang.String} object.
         * @return a {@link org.loboevolution.pdfview.annotation.PDFAnnotation.ANNOTATION_TYPE} object.
         */
        public static ANNOTATION_TYPE getByDefinition(final String definition) {
            for (final ANNOTATION_TYPE type : values()) {
                if (type.definition.equals(definition)) {
                    return type;
                }
            }
            return UNKNOWN;
        }

    }

}
