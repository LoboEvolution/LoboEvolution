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
import org.loboevolution.html.dom.filter.IdFilter;
import org.loboevolution.html.dom.svg.Drawable;
import org.loboevolution.html.dom.svg.SVGAngle;
import org.loboevolution.html.dom.svg.SVGAnimatedLength;
import org.loboevolution.html.dom.svg.SVGAnimatedPreserveAspectRatio;
import org.loboevolution.html.dom.svg.SVGAnimatedRect;
import org.loboevolution.html.dom.svg.SVGAnimatedTransformList;
import org.loboevolution.html.dom.svg.SVGElement;
import org.loboevolution.html.dom.svg.SVGImageElement;
import org.loboevolution.html.dom.svg.SVGLength;
import org.loboevolution.html.dom.svg.SVGMatrix;
import org.loboevolution.html.dom.svg.SVGNumber;
import org.loboevolution.html.dom.svg.SVGPoint;
import org.loboevolution.html.dom.svg.SVGRect;
import org.loboevolution.html.dom.svg.SVGSVGElement;
import org.loboevolution.html.dom.svg.SVGTransform;
import org.loboevolution.html.dom.svg.SVGTransformable;
import org.loboevolution.html.dom.svg.SVGUseElement;
import org.loboevolution.html.dom.svg.SVGViewSpec;
import org.loboevolution.html.js.events.EventFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.events.Event;
import org.w3c.dom.stylesheets.StyleSheetList;
import org.w3c.dom.views.DocumentView;

/**
 * <p>SVGSVGElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class SVGSVGElementImpl extends SVGLocatableImpl implements SVGSVGElement, Drawable {
	
	private final SVGRect viewport;

	private SVGViewSpec currentView;
	
	private SVGAnimatedRect viewBox;
	
	private SVGPoint currentTranslate;
	
	private AffineTransform viewboxToViewportTransform;

	private float currentScale;

	private boolean useCurrentView = false;
	
	private boolean painted;

	/**
	 * <p>isPainted.</p>
	 *
	 * @return the painted
	 */
	public boolean isPainted() {
		return painted;
	}

	/**
	 * <p>Setter for the field painted.</p>
	 *
	 * @param painted the painted to set
	 */
	public void setPainted(boolean painted) {
		this.painted = painted;
	}

	/**
	 * <p>Constructor for SVGSVGElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGSVGElementImpl(String name) {
		super(name);
		currentTranslate = new SVGPointImpl();
		float x = getX().getBaseVal().getValue();
		float y = getY().getBaseVal().getValue();
		float width = getWidth().getBaseVal().getValue();
		float height = getHeight().getBaseVal().getValue();
		viewport = new SVGRectImpl(x, y, width, height);
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedRect getViewBox() {
		if (viewBox == null) {
			final float width = getWidth().getBaseVal().getValue();
			final float height = getHeight().getBaseVal().getValue();
			viewBox = new SVGAnimatedRectImpl(new SVGRectImpl(0, 0, width, height));
		}
		return viewBox;
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public short getZoomAndPan() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void setZoomAndPan(short zoomAndPan) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public Event createEvent(String eventType) throws DOMException {
		return EventFactory.createEvent(eventType);
	}

	/** {@inheritDoc} */
	@Override
	public CSSStyleDeclaration getComputedStyle(Element elt, String pseudoElt) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public DocumentView getDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public CSSStyleDeclaration getOverrideStyle(Element elt, String pseudoElt) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public StyleSheetList getStyleSheets() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getX() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("x")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getY() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("y")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getWidth() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("width")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGAnimatedLength getHeight() {
		return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("height")));
	}

	/** {@inheritDoc} */
	@Override
	public SVGRect getViewport() {
		float x = getX().getBaseVal().getValue();
		float y = getY().getBaseVal().getValue();
		float width = getWidth().getBaseVal().getValue();
		float height = getHeight().getBaseVal().getValue();
		return new SVGRectImpl(x, y, width, height);
	}

	/** {@inheritDoc} */
	@Override
	public float getPixelUnitToMillimeterX() {
		return (float) 0.28; 
	}

	/** {@inheritDoc} */
	@Override
	public float getPixelUnitToMillimeterY() {
		return (float) 0.28; 
	}

	/** {@inheritDoc} */
	@Override
	public float getScreenPixelToMillimeterX() {
		return (float) 0.28; 
	}

	/** {@inheritDoc} */
	@Override
	public float getScreenPixelToMillimeterY() {
		return (float) 0.28; 
	}

	/** {@inheritDoc} */
	@Override
	public boolean getUseCurrentView() {
		return useCurrentView;
	}

	/** {@inheritDoc} */
	@Override
	public void setUseCurrentView(boolean useCurrentView) throws DOMException {
		this.useCurrentView = useCurrentView;
	}

	/** {@inheritDoc} */
	@Override
	public SVGViewSpec getCurrentView() {
		return currentView;
	}

	/** {@inheritDoc} */
	@Override
	public float getCurrentScale() {
		return currentScale;
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentScale(float currentScale) throws DOMException {
		this.currentScale = currentScale;
	}

	/** {@inheritDoc} */
	@Override
	public SVGPoint getCurrentTranslate() {
		return currentTranslate;
	}

	/**
	 * <p>Setter for the field currentTranslate.</p>
	 *
	 * @param currentTranslate a {@link org.loboevolution.html.dom.svg.SVGPoint} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public void setCurrentTranslate(SVGPoint currentTranslate) throws DOMException {
		this.currentTranslate = currentTranslate;
	}

	/** {@inheritDoc} */
	@Override
	public int suspendRedraw(int max_wait_milliseconds) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void unsuspendRedraw(int suspend_handle_id) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void unsuspendRedrawAll() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void forceRedraw() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void pauseAnimations() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void unpauseAnimations() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean animationsPaused() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public float getCurrentTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentTime(float seconds) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public NodeList getIntersectionList(SVGRect rect, SVGElement referenceElement) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public NodeList getEnclosureList(SVGRect rect, SVGElement referenceElement) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean checkIntersection(SVGElement element, SVGRect rect) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean checkEnclosure(SVGElement element, SVGRect rect) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void deselectAll() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public SVGLength createSVGLength() {
		return new SVGLengthImpl();
	}

	/** {@inheritDoc} */
	@Override
	public SVGAngle createSVGAngle() {
		return new SVGAngleImpl();
	}

	/** {@inheritDoc} */
	@Override
	public SVGPoint createSVGPoint() {
		return new SVGPointImpl();
	}

	/** {@inheritDoc} */
	@Override
	public SVGMatrix createSVGMatrix() {
		return new SVGMatrixImpl();
	}

	/** {@inheritDoc} */
	@Override
	public SVGNumber createSVGNumber() {
		return new SVGNumberImpl();
	}

	/** {@inheritDoc} */
	@Override
	public SVGRect createSVGRect() {
		float x = getX().getBaseVal().getValue();
		float y = getY().getBaseVal().getValue();
		float width = getWidth().getBaseVal().getValue();
		float height = getHeight().getBaseVal().getValue();
		return new SVGRectImpl(x, y, width, height);
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform createSVGTransform() {
		return new SVGTransformImpl();
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform createSVGTransformFromMatrix(SVGMatrix matrix) {
		SVGTransform transform = new SVGTransformImpl();
		transform.setMatrix(matrix);
		return transform;
	}

	/** {@inheritDoc} */
	@Override
	public Element getElementById(String elementId) {
		NodeList nodeList = getNodeList(new IdFilter(elementId));
		return nodeList != null && nodeList.getLength() > 0 ? (Element)nodeList.item(0) : null;
	}

	/** {@inheritDoc} */
	@Override
	public void draw(Graphics2D graphics) {
		boolean display = getDisplay();
		float opacity = getOpacity();

		if (display && opacity > 0) {
			AffineTransform oldGraphicsTransform = graphics.getTransform();
			Shape oldClip = graphics.getClip();
			graphics.translate(viewport.getX(), viewport.getY());
			if (opacity < 1) {
				SVGSVGElement root = this;
				float currentScale = root.getCurrentScale();
				SVGPoint currentTranslate = root.getCurrentTranslate();
				if (currentTranslate == null) {
					currentTranslate = new SVGPointImpl();
				}

				// create buffer to draw on
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
					AffineTransform ctm = AffineTransform.getTranslateInstance(viewport.getX(), viewport.getY());
					if (viewboxToViewportTransform != null) {
						ctm.concatenate(viewboxToViewportTransform);
					}
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
				// transform the child shapae
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
		List<Node> drawableChildren = new ArrayList<>();
		if (hasChildNodes()) {
			NodeList children = getChildNodes();
			for (Node child : Nodes.iterable(children)) {
				if (child instanceof Drawable) {
					drawableChildren.add(child);
				}
			}
		}
		for (Node node : drawableChildren) {
			SVGElement selem = (SVGElement)node;
			selem.setOwnerSVGElement(this);
			drawStyle(node);
			Drawable child = (Drawable) node;
			child.draw(graphics);
		}
	}
}
