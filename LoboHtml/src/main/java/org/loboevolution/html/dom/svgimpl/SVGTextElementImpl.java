package org.loboevolution.html.dom.svgimpl;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.util.StringTokenizer;

import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.svg.SVGAnimatedEnumeration;
import org.loboevolution.html.dom.svg.SVGAnimatedLength;
import org.loboevolution.html.dom.svg.SVGAnimatedNumberList;
import org.loboevolution.html.dom.svg.SVGMatrix;
import org.loboevolution.html.dom.svg.SVGPoint;
import org.loboevolution.html.dom.svg.SVGRect;
import org.loboevolution.html.dom.svg.SVGTextElement;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>SVGTextElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGTextElementImpl extends SVGGraphic implements SVGTextElement {

	/**
	 * <p>Constructor for SVGTextElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGTextElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getX() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(getAttribute("x")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getY() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(getAttribute("y")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getDx() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(getAttribute("dx")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getDy() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(getAttribute("dy")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedNumberList getRotate() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getTextLength() {
		final String text = getText();
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(text.length()));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedEnumeration getLengthAdjust() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public int getNumberOfChars() {
		final String text = getText();
		return text.toCharArray().length;
	}

	/** {@inheritDoc} */
	@Override
	public float getComputedTextLength() {
		final String text = getText();
		return text.length();
	}

	/** {@inheritDoc} */
	@Override
	public float getSubStringLength(int charnum, int nchars) throws DOMException {
		String text = getText();
		text = text.substring(charnum, nchars);
		return text.length();
	}

	/** {@inheritDoc} */
	@Override
	public SVGPoint getStartPositionOfChar(int charnum) throws DOMException {
		// TODO Auto-generated method stub
		return new SVGPointImpl();
	}

	/** {@inheritDoc} */
	@Override
	public SVGPoint getEndPositionOfChar(int charnum) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public SVGRect getExtentOfChar(int charnum) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public float getRotationOfChar(int charnum) throws DOMException {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public int getCharNumAtPosition(SVGPoint point) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void selectSubString(int charnum, int nchars) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void draw(Graphics2D graphics) {
		final SVGMatrix ctm = getCTM();
		final Shape shape = createShape(ctm.getAffineTransform());
		animate(this);
		drawable(graphics, shape);
	}

	/** {@inheritDoc} */
	@Override
	public Shape createShape(AffineTransform transform) {
		AffineTransform inverseTransform;
		try {
			inverseTransform = transform.createInverse();
		} catch (NoninvertibleTransformException e) {
			inverseTransform = null;
		}

		float x = ((SVGLengthImpl) getX().getAnimVal()).getTransformedLength(inverseTransform);
		float y = ((SVGLengthImpl) getY().getAnimVal()).getTransformedLength(inverseTransform);
		
		GeneralPath path = new GeneralPath();
		path.setWindingRule(Path2D.WIND_NON_ZERO);
		Font font = getFont();
		String text = getText();

		if (text.length() > 0 && font != null) {
			TextLayout tl = new TextLayout(text, font, new FontRenderContext(null, true, true));
			Shape textShape = tl.getOutline(null);
			path.append(textShape, false);
			path.transform(AffineTransform.getTranslateInstance(x, y));
		}

		String textAnchor = getTextAnchor();

		if (textAnchor != null) {
			if (textAnchor.equals("middle")) {
				double swidth = path.getBounds2D().getWidth();
				path.transform(AffineTransform.getTranslateInstance(-swidth / 2.0, 0));
			} else if (textAnchor.equals("end")) {
				double swidth = path.getBounds2D().getWidth();
				path.transform(AffineTransform.getTranslateInstance(-swidth, 0));
			}
		}
		return path;
	}
	
	private String getText() {
		String text = "";
		String xmlSpace = getXMLspace();
		if (Strings.isNotBlank(xmlSpace)) {
			xmlSpace = "default";
		}
		if (hasChildNodes()) {
			NodeList children = getChildNodes();
			for (Node child : Nodes.iterable(children)) {
				if (child.getNodeType() == Node.TEXT_NODE) {
					String nodeValue = child.getNodeValue();
					String childText = "";
					if ("default".equals(xmlSpace)) {
						int newLineIndex = nodeValue.indexOf('\n');
						while (newLineIndex != -1) {
							nodeValue = nodeValue.substring(0, newLineIndex) + nodeValue.substring(newLineIndex + 1);
							newLineIndex = nodeValue.indexOf('\n');
						}
						int returnIndex = nodeValue.indexOf('\r');
						while (returnIndex != -1) {
							nodeValue = nodeValue.substring(0, returnIndex) + nodeValue.substring(returnIndex + 1);
							returnIndex = nodeValue.indexOf('\r');
						}

						nodeValue = nodeValue.replace('\t', ' ');
						StringTokenizer st = new StringTokenizer(nodeValue);
						while (st.hasMoreTokens()) {
							childText += st.nextToken() + " ";
						}
						childText = childText.trim();
					} else {
						nodeValue = nodeValue.replace('\n', ' ');
						nodeValue = nodeValue.replace('\r', ' ');
						nodeValue = nodeValue.replace('\t', ' ');
						childText = nodeValue;
					}
					text += childText + " ";
				}
			}
		}
		if (text.length() > 0) {
			return text.substring(0, text.length() - 1);
		} else {
			return text;
		}
	}
}
