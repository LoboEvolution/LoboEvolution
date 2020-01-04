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

public class SVGTextElementImpl extends SVGGraphic implements SVGTextElement {

	public SVGTextElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedLength getX() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(getAttribute("x")));
	}

	@Override
	public SVGAnimatedLength getY() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(getAttribute("y")));
	}

	@Override
	public SVGAnimatedLength getDx() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(getAttribute("dx")));
	}

	@Override
	public SVGAnimatedLength getDy() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(getAttribute("dy")));
	}

	@Override
	public SVGAnimatedNumberList getRotate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGAnimatedLength getTextLength() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGAnimatedEnumeration getLengthAdjust() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfChars() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getComputedTextLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getSubStringLength(int charnum, int nchars) throws DOMException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SVGPoint getStartPositionOfChar(int charnum) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGPoint getEndPositionOfChar(int charnum) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGRect getExtentOfChar(int charnum) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getRotationOfChar(int charnum) throws DOMException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCharNumAtPosition(SVGPoint point) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void selectSubString(int charnum, int nchars) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D graphics) {
		final SVGMatrix ctm = getCTM();
		final Shape shape = createShape(ctm.getAffineTransform());
		animate(this);
		drawable(graphics, shape);
	}

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