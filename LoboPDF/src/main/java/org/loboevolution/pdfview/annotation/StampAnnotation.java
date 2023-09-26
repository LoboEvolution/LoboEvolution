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

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.loboevolution.pdfview.PDFCmd;
import org.loboevolution.pdfview.PDFImage;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFPage;
import org.loboevolution.pdfview.PDFParseException;
import org.loboevolution.pdfview.PDFParser;

/**
 ***************************************************************************
 * PDF annotation describing a stamp
 *
 * Author Katja Sondermann
 * @since 26.03.2012
 ***************************************************************************
  *
 */
public class StampAnnotation extends PDFAnnotation {
	private String iconName;
	private PDFAnnotation popupAnnotation;
	private PDFObject onAppearance;
	private PDFObject offAppearance;
	private List<PDFCmd> onCmd;
	private List<PDFCmd> offCmd;
	private boolean appearanceStateOn;
	
	/**
	 ***********************************************************************
	 * Constructor
	 *
	 * @param annotObject a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @param type a ANNOTATION_TYPE object.
	 * @throws java.io.IOException if any.
	 */
	public StampAnnotation(final PDFObject annotObject, final ANNOTATION_TYPE type) throws IOException {
		super(annotObject, type);
		
		parsePopupAnnotation(annotObject.getDictRef("Popup"));
		
		parseAP(annotObject.getDictRef("AP"));			
	}

	/**
	 ***********************************************************************
	 * Constructor
	 *
	 * @param annotObject a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @throws java.io.IOException if any.
	 */
	public StampAnnotation(final PDFObject annotObject) throws IOException {
		this(annotObject, ANNOTATION_TYPE.STAMP);
	}
	
	private void parseAP(final PDFObject dictRef) throws IOException {
		if (dictRef == null) {
			return;
		}
		final PDFObject normalAP = dictRef.getDictRef("N");
		if (normalAP == null) {
			return;
		}
		if (normalAP.getType() == PDFObject.DICTIONARY) {
			this.onAppearance = normalAP.getDictRef("On");
			this.offAppearance = normalAP.getDictRef("Off");
			final PDFObject as = dictRef.getDictRef("AS");
			this.appearanceStateOn = (as != null) && ("On".equals(as.getStringValue()));
		} else {
			this.onAppearance = normalAP;
			this.offAppearance = null;
			appearanceStateOn = true;
		}
		parseCommands();
	}

	private void parseCommands() throws IOException {
		if (onAppearance != null) {
			onCmd = parseCommand(onAppearance);
		}
		if (offAppearance != null) {
			offCmd = parseCommand(offAppearance);
		}
	}

	private List<PDFCmd> parseCommand(final PDFObject obj) throws IOException {
        String type = obj.getDictRef("Subtype").getStringValue();
        if (type == null) {
            type = obj.getDictRef ("S").getStringValue ();
        }
        final ArrayList<PDFCmd> result = new ArrayList<>();
        result.add(PDFPage.createPushCmd());
        result.add(PDFPage.createPushCmd());
        if (type.equals("Image")) {
            // stamp annotation transformation
            final AffineTransform rectAt = getPositionTransformation();
            result.add(PDFPage.createXFormCmd(rectAt));
            
        	final PDFImage img = PDFImage.createImage(obj, new HashMap<>() , false);
        	result.add(PDFPage.createImageCmd(img));
        } else if (type.equals("Form")) {
        	
            // rats.  parse it.
            final PDFObject bobj = obj.getDictRef("BBox");
            final float xMin = bobj.getAt(0).getFloatValue();
            final float yMin = bobj.getAt(1).getFloatValue();
			final float xMax = bobj.getAt(2).getFloatValue();
			final float yMax = bobj.getAt(3).getFloatValue();
			final Float bbox = new Rectangle2D.Float(xMin,
                    yMin,
                    xMax - xMin,
                    yMax - yMin);
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
            
            final HashMap<String,PDFObject> r = new HashMap<>(new HashMap<>());
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
	 * @return
	 */
	private AffineTransform getPositionTransformation() {
		final Float rect2 = getRect();
		final double[] f = new double[] {1,
				0,
				0,
				1,
				rect2.getMinX(),
				rect2.getMinY()};
		return new AffineTransform(f);
	}

	private void parsePopupAnnotation(final PDFObject popupObj) throws IOException {
		this.popupAnnotation = (popupObj != null)?createAnnotation(popupObj):null;
	}

	/**
	 * <p>Getter for the field <code>iconName</code>.</p>
	 *
	 * @return the iconName
	 */
	public String getIconName() {
		return iconName;
	}

	/**
	 * <p>Getter for the field <code>popupAnnotation</code>.</p>
	 *
	 * @return the popupAnnotation
	 */
	public PDFAnnotation getPopupAnnotation() {
		return popupAnnotation;
	}

	/**
	 * <p>Getter for the field <code>onAppearance</code>.</p>
	 *
	 * @return the onAppearance
	 */
	public PDFObject getOnAppearance() {
		return onAppearance;
	}

	/**
	 * <p>Getter for the field <code>offAppearance</code>.</p>
	 *
	 * @return the offAppearance
	 */
	public PDFObject getOffAppearance() {
		return offAppearance;
	}

	/**
	 * <p>isAppearanceStateOn.</p>
	 *
	 * @return the appearanceStateOn
	 */
	public boolean isAppearanceStateOn() {
		return appearanceStateOn;
	}

	/**
	 * <p>switchAppearance.</p>
	 */
	public void switchAppearance() {
		this.appearanceStateOn = !this.appearanceStateOn;
	}

	/**
	 * <p>getCurrentAppearance.</p>
	 *
	 * @return a {@link org.loboevolution.pdfview.PDFObject} object.
	 */
	public PDFObject getCurrentAppearance() {
		return appearanceStateOn?onAppearance:offAppearance;
	}

	/**
	 * <p>getCurrentCommand.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<PDFCmd> getCurrentCommand() {
		return appearanceStateOn?onCmd:offCmd;
	}

	/** {@inheritDoc} */
	@Override
	public List<PDFCmd> getPageCommandsForAnnotation() {
		final List<PDFCmd> pageCommandsForAnnotation = super.getPageCommandsForAnnotation();
		pageCommandsForAnnotation.addAll(getCurrentCommand());
		return pageCommandsForAnnotation;
	}
}
