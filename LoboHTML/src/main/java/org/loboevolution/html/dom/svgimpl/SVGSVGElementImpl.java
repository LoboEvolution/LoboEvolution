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

package org.loboevolution.html.dom.svgimpl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.filter.IdFilter;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.dom.svg.*;
import org.loboevolution.html.js.events.EventFactory;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.events.Event;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>SVGSVGElementImpl class.</p>
 */
@Slf4j
public class SVGSVGElementImpl extends SVGLocatableImpl implements SVGSVGElement, Drawable {
	
	private final SVGRect viewport;

	@Getter
	private SVGViewSpec currentView;
	
	private SVGAnimatedRect viewBox;

	@Getter
	@Setter
	private SVGPoint currentTranslate;
	
	@Getter
	private AffineTransform viewboxToViewportTransform;

	private float currentScale;

	private boolean useCurrentView = false;

	@Getter
	@Setter
	private boolean painted;

	/**
	 * <p>Constructor for SVGSVGElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public SVGSVGElementImpl(final String name) {
		super(name);
		currentTranslate = new SVGPointImpl();
		final float x = getX().getBaseVal().getValue();
		final float y = getY().getBaseVal().getValue();
		final float width = getWidth().getBaseVal().getValue();
		final float height = getHeight().getBaseVal().getValue();
		viewport = new SVGRectImpl(x, y, width, height);
		recalculateViewboxToViewportTransform();
	}

	@Override
	public SVGRect getBBox() {
		final Shape shape = createShape(null);
		return new SVGRectImpl(shape.getBounds2D());
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
		return new SVGAnimatedPreserveAspectRatioImpl(new SVGPreserveAspectRatioImpl(), this);
	}

	/** {@inheritDoc} */
	@Override
	public short getZoomAndPan() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void setZoomAndPan(final short zoomAndPan) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public Event createEvent(final String eventType) throws DOMException {
		return EventFactory.createEvent(eventType);
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
		final float x = getX().getBaseVal().getValue();
		final float y = getY().getBaseVal().getValue();
		final float width = getWidth().getBaseVal().getValue();
		final float height = getHeight().getBaseVal().getValue();
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
	public void setUseCurrentView(final boolean useCurrentView) {
		this.useCurrentView = useCurrentView;
	}

	/** {@inheritDoc} */
	@Override
	public float getCurrentScale() {
		return currentScale;
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentScale(final float currentScale) {
		this.currentScale = currentScale;
	}

	/** {@inheritDoc} */
	@Override
	public int suspendRedraw(final int max_wait_milliseconds) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void unsuspendRedraw(final int suspend_handle_id) {
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
	public void setCurrentTime(final float seconds) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public NodeList getIntersectionList(final SVGRect rect, final SVGElement referenceElement) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public NodeList getEnclosureList(final SVGRect rect, final SVGElement referenceElement) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean checkIntersection(final SVGElement element, final SVGRect rect) {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean checkEnclosure(final SVGElement element, final SVGRect rect) {
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
		final float x = getX().getBaseVal().getValue();
		final float y = getY().getBaseVal().getValue();
		final float width = getWidth().getBaseVal().getValue();
		final float height = getHeight().getBaseVal().getValue();
		return new SVGRectImpl(x, y, width, height);
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform createSVGTransform() {
		return new SVGTransformImpl();
	}

	/** {@inheritDoc} */
	@Override
	public SVGTransform createSVGTransformFromMatrix(final SVGMatrix matrix) {
		final SVGTransform transform = new SVGTransformImpl();
		transform.setMatrix(matrix);
		return transform;
	}

	/** {@inheritDoc} */
	@Override
	public Element getElementById(final String elementId) {
		final NodeList nodeList = getNodeList(new IdFilter(elementId));
		return nodeList != null && nodeList.getLength() > 0 ? (Element)nodeList.item(0) : null;
	}

	/** {@inheritDoc} */
	@Override
	public void draw(final Graphics2D graphics) {
		recalculateViewboxToViewportTransform();
		final boolean display = getDisplay();
		final float opacity = getOpacity();

		if (display && opacity > 0) {
			final AffineTransform oldGraphicsTransform = graphics.getTransform();
			final Shape oldClip = graphics.getClip();
			graphics.translate(viewport.getX(), viewport.getY());
			if (opacity < 1) {
				final SVGSVGElement root = this;
				final float currentScale = root.getCurrentScale();
				// create buffer to draw on
				final Shape shape = createShape(null);
				final AffineTransform screenCTM = getScreenCTM().getAffineTransform();
				final Shape transformedShape = screenCTM.createTransformedShape(shape);
				final Rectangle2D bounds = transformedShape.getBounds2D();
				final double xInc = bounds.getWidth() / 5;
				final double yInc = bounds.getHeight() / 5;
				bounds.setRect(bounds.getX() - xInc, bounds.getY() - yInc, bounds.getWidth() + 2 * xInc, bounds.getHeight() + 2 * yInc);
				final int imageWidth = (int) (bounds.getWidth() * currentScale);
				final int imageHeight = (int) (bounds.getHeight() * currentScale);
				if (imageWidth > 0 && imageHeight > 0) {
					final BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_4BYTE_ABGR);
					final Graphics2D offGraphics = (Graphics2D) image.getGraphics();
					final RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					offGraphics.setRenderingHints(hints);
					if (currentScale != 1) {
						offGraphics.scale(currentScale, currentScale);
					}

					offGraphics.translate(-bounds.getX(), -bounds.getY());
					offGraphics.transform(screenCTM);
					drawChildren(offGraphics);
					
					final Composite oldComposite = graphics.getComposite();
					final AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
					graphics.setComposite(ac);
					final AffineTransform imageTransform = AffineTransform.getTranslateInstance(bounds.getX(), bounds.getY());
					imageTransform.scale(1 / currentScale, 1 / currentScale);
					try {
						imageTransform.preConcatenate(screenCTM.createInverse());
					} catch (final NoninvertibleTransformException e) {
						log.info(e.getMessage());
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

	private void recalculateViewboxToViewportTransform() {

		viewboxToViewportTransform = new AffineTransform();

		final short align = getPreserveAspectRatio().getAnimVal().getAlign();
		final short meetOrSlice = getPreserveAspectRatio().getAnimVal().getMeetOrSlice();

		final float sx = getViewport().getWidth() / getViewBox().getAnimVal().getWidth();
		final float sy = getViewport().getHeight() / getViewBox().getAnimVal().getHeight();

		if (align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_NONE) {
			viewboxToViewportTransform.scale(sx, sy);

			final float tx = -getViewBox().getAnimVal().getX();
			final float ty = -getViewBox().getAnimVal().getY();
			viewboxToViewportTransform.translate(tx, ty);

		} else {

			final float scale;
			if (meetOrSlice == SVGPreserveAspectRatio.SVG_MEETORSLICE_MEET) {
				scale = Math.min(sx, sy);
			} else {
				scale = Math.max(sx, sy);
			}

			final float vpX = 0;
			final float vpY = 0;
			final float vpWidth = getViewport().getWidth();
			final float vpHeight = getViewport().getHeight();

			final float vbX = getViewBox().getAnimVal().getX();
			final float vbY = getViewBox().getAnimVal().getY();
			final float vbWidth = getViewBox().getAnimVal().getWidth();
			final float vbHeight = getViewBox().getAnimVal().getHeight();

			final float tx;
			final float ty;

			if (meetOrSlice == SVGPreserveAspectRatio.SVG_MEETORSLICE_MEET) {
				if (sy < sx) {
					ty = vpY / scale - vbY;
                    tx = switch (align) {
                        case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMIN,
                             SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMID,
                             SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMAX -> vpX / scale - vbX;
                        case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMIN,
                             SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMID,
                             SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMAX ->
                                (vpX + vpWidth / 2) / scale - (vbX + vbWidth / 2);
                        default -> vpX + vpWidth / scale - (vbX + vbWidth);
                    };
				} else {
					tx = vpX / scale - vbX;

                    ty = switch (align) {
                        case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMIN,
                             SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMIN,
                             SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMAXYMIN -> vpY / scale - vbY;
                        case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMID,
                             SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMID,
                             SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMAXYMID ->
                                (vpY + vpHeight / 2) / scale - (vbY + vbHeight / 2);
                        default -> (vpY + vpHeight) / scale - (vbY + vbHeight);
                    };
				}
			} else { // SLICE

				if (sy > sx) {
					ty = vpY / scale - vbY;

                    tx = switch (align) {
                        case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMIN,
                             SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMID,
                             SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMAX -> vpX / scale - vbX;
                        case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMIN,
                             SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMID,
                             SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMAX ->
                                (vpX + vpWidth / 2) / scale - (vbX + vbWidth / 2);
                        default -> (vpX + vpWidth) / scale - (vbX + vbWidth);
                    };


				} else {
					tx = vpX - vbX * scale;

                    ty = switch (align) {
                        case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMIN,
                             SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMIN,
                             SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMAXYMIN -> vpY / scale - vbY;
                        case SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMID,
                             SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMID,
                             SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMAXYMID ->
                                (vpY + vpHeight / 2) / scale - (vbY + vbHeight / 2);
                        default -> (vpY + vpHeight) / scale - (vbY + vbHeight);
                    };
				}
			}

			viewboxToViewportTransform.scale(scale, scale);
			viewboxToViewportTransform.translate(tx, ty);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Shape createShape(final AffineTransform transform) {
		final GeneralPath path = new GeneralPath();
		if (hasChildNodes()) {
			final NodeListImpl children = (NodeListImpl)getChildNodes();
			children.forEach(child -> {
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
				} else if (child instanceof SVGSVGElementImpl svg) {
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
			});
		}
		return path;
	}

	@Override
	public int getClientHeight() {
		final int clientHeight = super.getClientHeight();
		return clientHeight == 0 ? 150 : clientHeight;
	}

	@Override
	public Integer getClientWidth() {
		final int clientWidth = super.getClientWidth();
		return clientWidth == 0 ? 300 : clientWidth;
	}

	@Override
	public Integer getOffsetWidth() {
		return getClientWidth();
	}
	
	private void drawChildren(final Graphics2D graphics) {
		final List<Node> drawableChildren = new ArrayList<>();
		if (hasChildNodes()) {
			final NodeListImpl children = (NodeListImpl)getChildNodes();
			children.forEach(child -> {
				if (child instanceof Drawable) {
					drawableChildren.add(child);
				}
			});
		}
		for (final Node node : drawableChildren) {
			final SVGElement selem = ( SVGElement)node;
			selem.setOwnerSVGElement(this);
			drawStyle(node);
			final Drawable child = (Drawable) node;
			child.draw(graphics);
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object SVGSVGElement]";
	}
}
