package org.loboevolution.pdfview.annotation;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.loboevolution.pdfview.Configuration;
import org.loboevolution.pdfview.PDFCmd;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

/**
 ***************************************************************************
 * Encapsulate a PDF annotation. This is only the super-class of PDF annotations,
 * which has an "unknown" annotation type.
 * Use the createAnnotation() method for getting an annotation of the correct
 * type (if implemented).
 *
 * Author  Katja Sondermann
 * @since 03.07.2009
 ***************************************************************************
  *
 */
public class PDFAnnotation{
	public enum ANNOTATION_TYPE{
		UNKNOWN("-", 0, PDFAnnotation.class),
		LINK("Link", 1, LinkAnnotation.class),
		WIDGET("Widget", 2, WidgetAnnotation.class),
		STAMP("Stamp", 3, StampAnnotation.class),
		FREETEXT("FreeText", 5, FreetextAnnotation.class),
		SIGNATURE("Sig", 6, WidgetAnnotation.class),
		// TODO 28.03.2012: add more annotation types
		;
		
		private final String definition;
		private final int internalId;
		private final Class<?> className;
		ANNOTATION_TYPE(String definition, int typeId, Class<?> className) {
			this.definition = definition;
			this.internalId = typeId;
			this.className = className;
		}
		/**
		 * @return the definition
		 */
		public String getDefinition() {
			return definition;
		}
		/**
		 * @return the internalId
		 */
		public int getInternalId() {
			return internalId;
		}
		
		/**
		 * @return the className
		 */
		public Class<?> getClassName() {
			return className;
		}
		
		/**
		 * Get annotation type by it's type 
		 * @param definition a {@link java.lang.String} object.
		 * @return a {@link org.loboevolution.pdfview.annotation.PDFAnnotation.ANNOTATION_TYPE} object.
		 */
		public static ANNOTATION_TYPE getByDefinition(String definition) {
			for (ANNOTATION_TYPE type : values()) {
				if(type.definition.equals(definition)) {
					return type;
				}
			}
			return UNKNOWN;
		}		
	}
	
	/** Definition of some annotation sub-types*/
	public static final String GOTO = "GoTo";
	/** Constant <code>GOTOE="GoToE"</code> */
	public static final String GOTOE = "GoToE";
	/** Constant <code>GOTOR="GoToR"</code> */
	public static final String GOTOR = "GoToR";
	/** Constant <code>URI="URI"</code> */
	public static final String URI = "URI";
	
	private final PDFObject pdfObj;
	private final ANNOTATION_TYPE type;
	private final Float rect;

	/**
	 ***********************************************************************
	 * Constructor
	 *
	 * @param annotObject - the PDFObject which contains the annotation description
	 * @throws java.io.IOException if any.
	 */
	public PDFAnnotation(PDFObject annotObject) throws IOException{
		this(annotObject, ANNOTATION_TYPE.UNKNOWN);
	}

	/**
	 ***********************************************************************
	 * Constructor
	 *
	 * @param annotObject - the PDFObject which contains the annotation description
	 * @param type a {@link org.loboevolution.pdfview.annotation.PDFAnnotation.ANNOTATION_TYPE} object.
	 * @throws java.io.IOException if any.
	 */
	protected PDFAnnotation(PDFObject annotObject, ANNOTATION_TYPE type) throws IOException{
		this.pdfObj = annotObject;
		// in case a general "PdfAnnotation" is created the type is unknown
		this.type = type;
		this.rect = this.parseRect(annotObject.getDictRef("Rect"));
	}

	/**
	 ***********************************************************************
	 * Create a new PDF annotation object.
	 *
	 * Currently supported annotation types:
	 * Link annotation
	 *
	 * @param parent a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @return PDFAnnotation a {@link org.loboevolution.pdfview.annotation.PDFAnnotation} object.
	 * @throws java.io.IOException if any.
	 */
	public static PDFAnnotation createAnnotation(PDFObject parent) throws IOException{
		PDFObject subtypeValue = parent.getDictRef("Subtype");
		if(subtypeValue == null) {
			return null;
		}
		String subtypeS = subtypeValue.getStringValue();
		ANNOTATION_TYPE annotationType = ANNOTATION_TYPE.getByDefinition(subtypeS);
		
		//if Subtype is Widget than check if it is also a Signature
		if(annotationType == ANNOTATION_TYPE.WIDGET) {
			PDFObject sigType = parent.getDictRef("FT");
			if(sigType != null) {
				String sigTypeS = sigType.getStringValue();
				if(ANNOTATION_TYPE.getByDefinition(sigTypeS) == ANNOTATION_TYPE.SIGNATURE) {
					annotationType = ANNOTATION_TYPE.getByDefinition(sigTypeS);
				}
			}
		}
		
		if(displayAnnotation(annotationType)) {
			Class<?> className = annotationType.getClassName();
			
			Constructor<?> constructor;
			try {
				constructor = className.getConstructor(PDFObject.class);
				return (PDFAnnotation)constructor.newInstance(parent);
			} catch (Exception e) {
				throw new PDFParseException("Could not parse annotation!", e);
			} 
		}
		
		return null;
	}

    private static boolean displayAnnotation(ANNOTATION_TYPE annotationType) {
		switch(annotationType) {
			case STAMP: return Configuration.getInstance().isPrintStampAnnotations();
			case WIDGET: return Configuration.getInstance().isPrintWidgetAnnotations();
			case FREETEXT: return Configuration.getInstance().isPrintFreetextAnnotations();
			case LINK: return Configuration.getInstance().isPrintLinkAnnotations();
			case SIGNATURE: return Configuration.getInstance().isPrintSignatureFields();
			case UNKNOWN: default: return false;
		}
	}

    /**
     * Get a Rectangle2D.Float representation for a PDFObject that is an
     * array of four Numbers.
     *
     * @param obj a PDFObject that represents an Array of exactly four
     * Numbers.
     * @return a {@link java.awt.geom.Rectangle2D.Float} object.
     * @throws java.io.IOException if any.
     */
    public Rectangle2D.Float parseRect(PDFObject obj) throws IOException {
        if (obj.getType() == PDFObject.ARRAY) {
            PDFObject[] bounds = obj.getArray();
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
	 ***********************************************************************
	 * Get the PDF Object which contains the annotation values
	 *
	 * @return PDFObject
	 ***********************************************************************
	 */
	public PDFObject getPdfObj() {
		return this.pdfObj;
	}

	/**
	 ***********************************************************************
	 * Get the annotation type
	 *
	 * @return int
	 ***********************************************************************
	 */
	public ANNOTATION_TYPE getType() {
		return this.type;
	}

	/**
	 ***********************************************************************
	 * Get the rectangle on which the annotation should be applied to
	 *
	 * @return Rectangle2D.Float
	 ***********************************************************************
	 */
	public Float getRect() {
		return this.rect;
	}

	/** {@inheritDoc} */
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
	protected AffineTransform getScalingTransformation(Float bbox) {
		AffineTransform at = new AffineTransform();		
		double scaleHeight = getRect().getHeight()/bbox.getHeight();
        double scaleWidth = getRect().getWidth()/bbox.getWidth();
        at.scale(scaleWidth, scaleHeight);
		return at;
	}

}
