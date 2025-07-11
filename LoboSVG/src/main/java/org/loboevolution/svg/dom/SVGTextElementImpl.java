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

package org.loboevolution.svg.dom;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.svg.*;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.util.StringTokenizer;

/**
 * <p>SVGTextElementImpl class.</p>
 */
public class SVGTextElementImpl extends SVGGraphic implements SVGTextElement {

	/**
	 * <p>Constructor for SVGTextElementImpl.</p>
	 *
	 * @param element a {@link HTMLElement} object.
	 */
	public SVGTextElementImpl(final HTMLElement element) {
		super(element);
	}

	@Override
	public SVGRect getBBox() {

		SVGFontElementImpl font = getFontElement();
		if (font != null) {
			AffineTransform transform = getCTM().getAffineTransform();
			AffineTransform inverseTransform;
			try {
				inverseTransform = transform.createInverse();
			} catch (NoninvertibleTransformException e) {
				inverseTransform = null;
			}
			float fontSize = getFontSize(inverseTransform);
			float xPos = ((SVGLengthImpl) getX().getAnimVal()).getTransformedLength(inverseTransform);
			float yPos = ((SVGLengthImpl) getY().getAnimVal()).getTransformedLength(inverseTransform);
			return new SVGRectImpl(font.getBounds(getText(), xPos, yPos, fontSize));

		} else {
			Shape shape = createShape(getCTM().getAffineTransform());
			return new SVGRectImpl(shape.getBounds2D());
		}
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
	public float getSubStringLength(final int charnum, final int nchars) {
		String text = getText();
		text = text.substring(charnum, nchars);
		return text.length();
	}

	/** {@inheritDoc} */
	@Override
	public SVGPoint getStartPositionOfChar(final int charnum) {
		// TODO Auto-generated method stub
		return new SVGPointImpl();
	}

	/** {@inheritDoc} */
	@Override
	public SVGPoint getEndPositionOfChar(final int charnum) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public SVGRect getExtentOfChar(final int charnum) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public float getRotationOfChar(final int charnum) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public int getCharNumAtPosition(final SVGPoint point) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void selectSubString(final int charnum, final int nchars) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void draw(final Graphics2D graphics) {
		final SVGMatrix ctm = getCTM();
		final Shape shape = createShape(ctm.getAffineTransform());
		animate(this);
		drawable(graphics, shape);
	}

	/** {@inheritDoc} */
	@Override
	public Shape createShape(final AffineTransform transform) {
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

		if (!text.isEmpty() && font != null) {
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
		final StringBuilder text = new StringBuilder();
		final String xmlSpace = getXMLspace();
		if (Strings.isNotBlank(xmlSpace)) {
			setXMLspace("default");
		}
		if (hasChildNodes()) {
			final NodeList children = getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				if (child.getNodeType() == Node.TEXT_NODE) {
					String nodeValue = child.getNodeValue();
					StringBuilder childText = new StringBuilder();
					if ("default".equals(getXMLspace())) {
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
						final StringTokenizer st = new StringTokenizer(nodeValue);
						while (st.hasMoreTokens()) {
							childText.append(st.nextToken()).append(" ");
						}
						childText = new StringBuilder(childText.toString().trim());
					} else {
						nodeValue = nodeValue.replace('\n', ' ');
						nodeValue = nodeValue.replace('\r', ' ');
						nodeValue = nodeValue.replace('\t', ' ');
						childText = new StringBuilder(nodeValue);
					}
					text.append(childText).append(" ");
				}
			};
		}
		if (!text.isEmpty()) {
			return text.substring(0, text.length() - 1);
		} else {
			return text.toString();
		}
	}
}
