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

package org.loboevolution.pdfview.annotation;

import lombok.Getter;
import lombok.Setter;
import org.loboevolution.pdfview.*;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * PDF annotation describing a widget.
 *
 * @since Aug 20, 2010
 */
public class WidgetAnnotation extends PDFAnnotation {

    private final PDFObject fieldValueRef;

    @Getter
    @Setter
    private String fieldValue;

    @Getter
    @Setter
    private FieldType fieldType;

    @Getter
    private String fieldName;
    private List<PDFCmd> cmd;

    /**
     * <p>Constructor for WidgetAnnotation.</p>
     *
     * @param annotObject a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws java.io.IOException if any.
     */
    public WidgetAnnotation(final PDFObject annotObject) throws IOException {
        super(annotObject, ANNOTATION_TYPE.WIDGET);

        // The type of field that this dictionary describes. Field type is
        // present for terminal fields but is inherited from parent if absent
        // (see PDF Reference 1.7 table 8.69)
        PDFObject fieldTypeRef = annotObject.getDictRef("FT");
        if (fieldTypeRef != null) {
            // terminal field
            this.fieldType = FieldType.getByCode(fieldTypeRef.getStringValue());
        } else {
            // must check parent since field type is inherited
            PDFObject parent = annotObject.getDictRef("Parent");
            while (parent != null && parent.isIndirect()) {
                parent = parent.dereference();
            }
            if (parent != null) {
                fieldTypeRef = parent.getDictRef("FT");
                this.fieldType = FieldType.getByCode(fieldTypeRef.getStringValue());
            }
        }

        // Name defined for the field
        final PDFObject fieldNameRef = annotObject.getDictRef("T");
        if (fieldNameRef != null) {
            this.fieldName = fieldNameRef.getTextStringValue();
        }
        this.fieldValueRef = annotObject.getDictRef("V");
        if (this.fieldValueRef != null) {
            this.fieldValue = this.fieldValueRef.getTextStringValue();
        }
        parseAP(annotObject.getDictRef("AP"));
    }

    private void parseAP(final PDFObject dictRef) throws IOException {
        if (dictRef == null) {
            return;
        }
        final PDFObject normalAP = dictRef.getDictRef("N");
        if (normalAP == null) {
            return;
        }
        cmd = parseCommand(normalAP);
    }

    private List<PDFCmd> parseCommand(PDFObject obj) throws IOException {
        final PDFObject dictRefSubType = obj.getDictRef("Subtype");
        String type = null;
        if (dictRefSubType != null) {
            type = dictRefSubType.getStringValue();
        }

        if (type == null) {
            final PDFObject dictRefS = obj.getDictRef("S");
            if (dictRefS != null) {
                type = dictRefS.getStringValue();
            }
        }

        //if type is still null, check for AcroForm, if AcroForm is available the PDF could be not compatible
        //with the PDF specification, anyway check if obj is in AcroForm, if so, proceed as for a good PDF
        if (type == null) {
            final PDFObject acroForm = obj.getRoot().getDictRef("AcroForm");
            final PDFObject fields = acroForm.getDictRef("Fields");
            final PDFObject[] arrayFields = fields.getArray();

            for (final PDFObject pdfObject : arrayFields) {
                final PDFObject dictRefAP = pdfObject.getDictRef("AP");
                if (dictRefAP != null) {
                    final PDFObject dictRefN = dictRefAP.getDictRef("N");

                    if (dictRefN.equals(obj)) {
                        final PDFObject dictRefAS = pdfObject.getDictRef("AS");
                        if (dictRefAS != null) {        //this is a combobox
                            obj = dictRefN.getDictRef(dictRefAS.getStringValue());
                        }

                        type = "Form";
                        break;
                    }
                }
            }

            if (type == null) {    //check for radiobutton
                final PDFObject dictRef = obj.getDictRef("Off");
                if (dictRef != null) {
                    for (final PDFObject pdfObject : arrayFields) {
                        final PDFObject dictRefT = pdfObject.getDictRef("T");
                        if (dictRefT != null && dictRefT.getStringValue().contains("Group")) {
                            final PDFObject kids = pdfObject.getDictRef("Kids");
                            final PDFObject[] arrayKids = kids.getArray();
                            for (final PDFObject kid : arrayKids) {
                                final PDFObject kidAP = kid.getDictRef("AP");
                                final PDFObject kidN = kidAP.getDictRef("N");
                                if (kidN.equals(obj)) {
                                    final PDFObject kidAS = kid.getDictRef("AS");
                                    if (kidAS != null) {
                                        obj = kidN.getDictRef(kidAS.getStringValue());
                                    }

                                    type = "Form";
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        final ArrayList<PDFCmd> result = new ArrayList<>();
        result.add(PDFPage.createPushCmd());
        result.add(PDFPage.createPushCmd());
        if ("Image".equals(type)) {
            // stamp annotation transformation
            final AffineTransform rectAt = getPositionTransformation();
            result.add(PDFPage.createXFormCmd(rectAt));

            final PDFImage img = PDFImage.createImage(obj, new HashMap<>(), false);
            result.add(PDFPage.createImageCmd(img));
        } else if ("Form".equals(type)) {
            // rats.  parse it.
            final PDFObject bobj = obj.getDictRef("BBox");
            final Float bbox = new Rectangle2D.Float(bobj.getAt(0).getFloatValue(),
                    bobj.getAt(1).getFloatValue(),
                    bobj.getAt(2).getFloatValue(),
                    bobj.getAt(3).getFloatValue());
            final PDFPage formCmds = new PDFPage(bbox, 0);
            // stamp annotation transformation
            final AffineTransform rectAt = getPositionTransformation();
            formCmds.addXform(rectAt);

            final AffineTransform rectScaled = getScalingTransformation(bbox);
            formCmds.addXform(rectScaled);

            // form transformation
            final AffineTransform at;
            final PDFObject matrix = obj.getDictRef("Matrix");
            if (matrix == null) {
                at = new AffineTransform();
            } else {
                final float[] elts = new float[6];
                for (int i = 0; i < elts.length; i++) {
                    elts[i] = (matrix.getAt(i)).getFloatValue();
                }
                at = new AffineTransform(elts);
            }
            formCmds.addXform(at);

            final HashMap<String, PDFObject> r = new HashMap<>(new HashMap<>());
            final PDFObject rsrc = obj.getDictRef("Resources");
            if (rsrc != null) {
                r.putAll(rsrc.getDictionary());
            }

            final PDFParser form = new PDFParser(formCmds, obj.getStream(), r);
            form.go(true);

            result.addAll(formCmds.getCommands());
        } else {
            throw new PDFParseException("Unknown XObject subtype: " + type);
        }
        result.add(PDFPage.createPopCmd());
        result.add(PDFPage.createPopCmd());
        return result;
    }

    /**
     * Transform to the position of the stamp annotation
     *
     * @return
     */
    private AffineTransform getPositionTransformation() {
        final Float rect2 = getRect();
        final double[] f = new double[]{1,
                0,
                0,
                1,
                rect2.getMinX(),
                rect2.getMinY()};
        return new AffineTransform(f);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PDFCmd> getPageCommandsForAnnotation() {
        final List<PDFCmd> pageCommandsForAnnotation = super.getPageCommandsForAnnotation();
        // cmd might be null if there is no AP (appearance dictionary)
        // AP is optional see PDF Reference 1.7 table 8.15
        if (this.cmd != null) {
            pageCommandsForAnnotation.addAll(this.cmd);
        }
        return pageCommandsForAnnotation;
    }

    /**
     * Type for PDF form elements
     *
     * @version $Id: WidgetAnnotation.java,v 1.2 2010-09-30 10:34:44 xphc Exp $
     * Author  xphc
     * @since Aug 20, 2010
     */
    public enum FieldType {
        /**
         * Button Field
         */
        BUTTON("Btn"),
        /**
         * Text Field
         */
        TEXT("Tx"),
        /**
         * Choice Field
         */
        CHOICE("Ch"),
        /**
         * Signature Field
         */
        SIGNATURE("Sig");

        private final String typeCode;

        FieldType(final String typeCode) {
            this.typeCode = typeCode;
        }

        static FieldType getByCode(final String typeCode) {
            final FieldType[] values = values();
            for (final FieldType value : values) {
                if (value.typeCode.equals(typeCode))
                    return value;
            }
            return null;
        }
    }

}
