package org.loboevolution.html.dom.svgimpl;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.loboevolution.common.Nodes;
import org.loboevolution.html.dom.svg.Drawable;
import org.loboevolution.html.dom.svg.SVGAnimatedTransformList;
import org.loboevolution.html.dom.svg.SVGGElement;
import org.loboevolution.html.dom.svg.SVGImageElement;
import org.loboevolution.html.dom.svg.SVGRect;
import org.loboevolution.html.dom.svg.SVGSVGElement;
import org.loboevolution.html.dom.svg.SVGTransformable;
import org.loboevolution.html.dom.svg.SVGUseElement;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>SVGGElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGGElementImpl extends SVGGraphic implements SVGGElement {

	/**
	 * <p>Constructor for SVGGElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGGElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public void draw(Graphics2D graphics) {
		boolean display = getDisplay();
		float opacity = getOpacity();

		if (display && opacity > 0) {
			AffineTransform oldGraphicsTransform = graphics.getTransform();
			Shape oldClip = graphics.getClip();
			if (opacity < 1) {
				float currentScale = 1;
				Shape shape = createShape(null);
				AffineTransform screenCTM = getScreenCTM().getAffineTransform();
				Shape transformedShape = screenCTM.createTransformedShape(shape);
				Rectangle2D bounds = transformedShape.getBounds2D();
				double xInc = bounds.getWidth() / 5;
				double yInc = bounds.getHeight() / 5;
				bounds.setRect(bounds.getX() - xInc, bounds.getY() - yInc, bounds.getWidth() + 2 * xInc, bounds.getHeight() + 2 * yInc);
				int imageWidth = (int) (bounds.getWidth() * currentScale);
				int imageHeight = (int) (bounds.getHeight() * currentScale);
				if (imageWidth > 0 && imageHeight > 0) {
					BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_4BYTE_ABGR);
					Graphics2D offGraphics = (Graphics2D) image.getGraphics();
					RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					offGraphics.setRenderingHints(hints);
					if (currentScale != 1) {
						offGraphics.scale(currentScale, currentScale);
					}

					offGraphics.translate(-bounds.getX(), -bounds.getY());
					offGraphics.transform(screenCTM);
					drawChildren(offGraphics);

					Composite oldComposite = graphics.getComposite();
					AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
					graphics.setComposite(ac);
					AffineTransform imageTransform = AffineTransform.getTranslateInstance(bounds.getX(), bounds.getY());
					imageTransform.scale(1 / currentScale, 1 / currentScale);
					try {
						imageTransform.preConcatenate(screenCTM.createInverse());
					} catch (NoninvertibleTransformException e) {
					}
					graphics.drawImage(image, imageTransform, null);
					graphics.setComposite(oldComposite);
					image.flush();
				}
			} else {
				drawChildren(graphics);
			}

			graphics.setTransform(oldGraphicsTransform);
			graphics.setClip(oldClip);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Shape createShape(AffineTransform transform) {
		GeneralPath path = new GeneralPath();
		if (hasChildNodes()) {
			NodeList children = getChildNodes();
			for (Node child : Nodes.iterable(children)) {
				Shape childShape = null;
				if (child instanceof SVGGElementImpl) {
					childShape = ((SVGGElementImpl) child).createShape(transform);

				} else if (child instanceof SVGAElementImpl) {
					childShape = ((SVGAElementImpl) child).createShape(transform);

				} else if (child instanceof SVGImageElementImpl) {
					SVGRect bbox = ((SVGImageElement) child).getBBox();
					childShape = new Rectangle2D.Float(bbox.getX(), bbox.getY(), bbox.getWidth(), bbox.getHeight());

				} else if (child instanceof SVGUseElementImpl) {
					SVGRect bbox = ((SVGUseElement) child).getBBox();
					childShape = new Rectangle2D.Float(bbox.getX(), bbox.getY(), bbox.getWidth(), bbox.getHeight());

				} else if (child instanceof SVGSVGElementImpl) {
					SVGSVGElement svg = (SVGSVGElement) child;
					AffineTransform ctm = getCTM().getAffineTransform();
					AffineTransform inverseTransform;
					try {
						inverseTransform = ctm.createInverse();
					} catch (NoninvertibleTransformException e) {
						inverseTransform = null;
					}
					float x = ((SVGLengthImpl) svg.getX()).getTransformedLength(inverseTransform);
					float y = ((SVGLengthImpl) svg.getY()).getTransformedLength(inverseTransform);
					float width = ((SVGLengthImpl) svg.getWidth()).getTransformedLength(inverseTransform);
					float height = ((SVGLengthImpl) svg.getHeight()).getTransformedLength(inverseTransform);

					childShape = new Rectangle2D.Float(x, y, width, height);
				}
				if (child instanceof SVGTransformable) {
					SVGAnimatedTransformList childTransformList = ((SVGTransformable) child).getTransform();
					if (childTransformList != null) {
						AffineTransform childTransform = ((SVGTransformListImpl) childTransformList.getAnimVal()).getAffineTransform();
						childShape = childTransform.createTransformedShape(childShape);
					}
				}
				if (childShape != null) {
					path.append(childShape, false);
				}
			}
		}
		return path;
	}
	
	private void drawChildren(Graphics2D graphics) {
		List<Node> drawableChildren = new ArrayList<Node>();
		if (hasChildNodes()) {
			NodeList children = getChildNodes();
			for (Node child : Nodes.iterable(children)) {
				if (child instanceof Drawable) {
					drawableChildren.add(child);
				}
			}
		}

		for (Node node : drawableChildren) {
			drawStyle(node);
			Drawable child = (Drawable) node;
			child.draw(graphics);
		}
	}
}
