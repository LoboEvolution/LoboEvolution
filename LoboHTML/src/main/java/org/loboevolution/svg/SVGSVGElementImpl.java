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

package org.loboevolution.svg;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.events.Event;
import org.loboevolution.html.dom.filter.IdFilter;
import org.loboevolution.html.js.events.EventFactory;
import org.loboevolution.html.node.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.HashMap;

@Slf4j
public class SVGSVGElementImpl extends SVGElementImpl implements SVGSVGElement, Drawable {

    private final HashMap<Long, Long> suspensions = new HashMap<>();

    private SVGRect viewport;
    private boolean animationsPaused = false;
    private float offsetTime = 0;
    private double beginTime;

    @Setter
    private boolean useCurrentView;

    @Setter
    @Getter
    private float currentScale;

    @Getter
    private AffineTransform viewboxToViewportTransform;

    /**
     * <p>Constructor for SVGSVGElementImpl.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public SVGSVGElementImpl(final String name) {
        super(name);
        final float x = getX().getBaseVal().getValue();
        final float y = getY().getBaseVal().getValue();
        final float width = getWidth().getBaseVal().getValue();
        final float height = getHeight().getBaseVal().getValue();
        viewport = new SVGRectImpl(x, y, width, height);
    }

    public void draw(final Graphics2D graphics) {
        Calendar cal = Calendar.getInstance();
        beginTime = cal.getTime().getTime() / 1000.0;
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
                final Shape shape = createShape();
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
                    super.draw(offGraphics);

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
                super.draw(graphics);
            }

            graphics.setTransform(oldGraphicsTransform);
            graphics.setClip(oldClip);
        }
    }


    @Override
    public SVGAnimatedLength getX() {
        return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("x")));
    }

    @Override
    public SVGAnimatedLength getY() {
        return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("y")));
    }

    @Override
    public SVGAnimatedLength getWidth() {
        return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("width")));
    }

    @Override
    public SVGAnimatedLength getHeight() {
        return new SVGAnimatedLengthImpl(new SVGLengthImpl(this.getAttribute("height")));
    }

    @Override
    public SVGRect getViewport() {
        if (viewport == null) {
            viewport = new SVGRectImpl();
            viewport.setX(getX().getBaseVal().getValue());
            viewport.setY(getY().getBaseVal().getValue());
            viewport.setWidth(getWidth().getBaseVal().getValue());
            viewport.setHeight(getHeight().getBaseVal().getValue());
        }
        return viewport;
    }

    @Override
    public float getPixelUnitToMillimeterX() {
        return (float) 0.28;
    }

    @Override
    public float getPixelUnitToMillimeterY() {
        return (float) 0.28;
    }

    @Override
    public float getScreenPixelToMillimeterX() {
        return (float) 0.28;
    }

    @Override
    public float getScreenPixelToMillimeterY() {
        return (float) 0.28;
    }

    @Override
    public boolean getUseCurrentView() {
        return useCurrentView;
    }

    @Override
    public SVGViewSpec getCurrentView() {
        return null;
    }

    @Override
    public SVGPoint getCurrentTranslate() {
        return null;
    }

    @Override
    public long suspendRedraw(long maxWaitMilliseconds) {
        maxWaitMilliseconds = Math.min(maxWaitMilliseconds, 60_000);
        long handle = System.nanoTime();
        suspensions.put(handle, handle + maxWaitMilliseconds * 1_000_000L);
        return handle;
    }

    @Override
    public void unsuspendRedraw(long suspendHandleID) {
        suspensions.remove(suspendHandleID);
    }

    @Override
    public void unsuspendRedrawAll() {
        suspensions.clear();
    }

    @Override
    public void forceRedraw() {
        suspensions.clear();
    }

    @Override
    public void pauseAnimations() {
        animationsPaused = true;
    }

    @Override
    public void unpauseAnimations() {
        animationsPaused = false;
    }

    @Override
    public boolean animationsPaused() {
        return animationsPaused;
    }

    @Override
    public float getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        double now = cal.getTime().getTime() / 1000.0;
        return (float) (now - beginTime + offsetTime);
    }

    @Override
    public void setCurrentTime(float seconds) {
        Calendar cal = Calendar.getInstance();
        float now = (float) (cal.getTime().getTime() / 1000.0);
        offsetTime = seconds - now;
    }

    @Override
    public NodeList getIntersectionList(SVGRect rect, SVGElement referenceElement) {
        return null;
    }

    @Override
    public NodeList getEnclosureList(SVGRect rect, SVGElement referenceElement) {
        return null;
    }

    @Override
    public boolean checkIntersection(SVGElement element, SVGRect rect) {
        return false;
    }

    @Override
    public boolean checkEnclosure(SVGElement element, SVGRect rect) {
        return false;
    }

    @Override
    public void deselectAll() {

    }

    @Override
    public SVGNumber createSVGNumber() {
        return new SVGNumberImpl();
    }

    @Override
    public SVGLength createSVGLength() {
        return new SVGLengthImpl();
    }

    @Override
    public SVGAngle createSVGAngle() {
        return new SVGAngleImpl();
    }

    @Override
    public SVGPoint createSVGPoint() {
        return new SVGPointImpl();
    }

    @Override
    public SVGMatrix createSVGMatrix() {
        return new SVGMatrixImpl(null);
    }

    @Override
    public SVGRect createSVGRect() {
        return new SVGRectImpl();
    }

    @Override
    public SVGTransform createSVGTransform() {
        return new SVGTransformImpl();
    }

    @Override
    public SVGTransform createSVGTransformFromMatrix(SVGMatrix matrix) {
        SVGTransform transform = new SVGTransformImpl();
        transform.setMatrix(matrix);
        return transform;
    }

    @Override
    public Element getElementById(String elementId) {
        final NodeList nodeList = getNodeList(new IdFilter(elementId));
        return nodeList != null && nodeList.getLength() > 0 ? (Element) nodeList.item(0) : null;
    }

    @Override
    public Event createEvent(String eventType) {
        return EventFactory.createEvent(eventType);
    }

    @Override
    public SVGAnimatedBoolean getExternalResourcesRequired() {
        return null;
    }

    @Override
    public SVGAnimatedRect getViewBox() {
        final float width = getWidth().getBaseVal().getValue();
        final float height = getHeight().getBaseVal().getValue();
        return new SVGAnimatedRectImpl(new SVGRectImpl(0, 0, width, height));
    }

    @Override
    public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio() {
        return new SVGAnimatedPreserveAspectRatioImpl(new SVGPreserveAspectRatioImpl(), this);
    }

    @Override
    public String getXMLlang() {
        return "";
    }

    @Override
    public void setXMLlang(String xmllang) {

    }

    @Override
    public String getXMLspace() {
        return "";
    }

    @Override
    public void setXMLspace(String xmlspace) {

    }

    @Override
    public SVGElement getNearestViewportElement() {
        return null;
    }

    @Override
    public SVGElement getFarthestViewportElement() {
        return null;
    }

    @Override
    public SVGRect getBBox() {
        return null;
    }

    @Override
    public SVGMatrix getCTM() {
        return null;
    }

    @Override
    public SVGMatrix getScreenCTM() {
        return null;
    }

    @Override
    public SVGMatrix getTransformToElement(SVGElement element) throws SVGException {
        return null;
    }

    @Override
    public boolean hasExtension(String extension) {
        return false;
    }

    @Override
    public short getZoomAndPan() {
        return 0;
    }

    @Override
    public void setZoomAndPan(short zoomAndPan) {

    }

    @Override
    public Shape createShape() {
        GeneralPath path = new GeneralPath();
        if (hasChildNodes()) {
            NodeList children = getChildNodes();
            int numChildren = children.getLength();
            for (int i = 0; i < numChildren; i++) {
                Node child = children.item(i);
                Shape childShape = null;
                if (child instanceof SVGGElementImpl) {
                    childShape = ((SVGGElementImpl) child).createShape();

                } else if (child instanceof SVGAElementImpl) {
                    childShape = ((SVGAElementImpl) child).createShape();

                } else if (child instanceof SVGImageElementImpl) {
                    SVGRect bbox = ((SVGImageElement) child).getBBox();
                    childShape = new Rectangle2D.Float(bbox.getX(), bbox.getY(), bbox.getWidth(), bbox.getHeight());

                } else if (child instanceof SVGUseElementImpl) {
                    SVGRect bbox = ((SVGUseElement) child).getBBox();
                    childShape = new Rectangle2D.Float(bbox.getX(), bbox.getY(), bbox.getWidth(), bbox.getHeight());

                } else if (child instanceof SVGSVGElementImpl svg) {
                    // just treat the svg element's viewport as it's shape
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
                if (child instanceof SVGTransformable) {
                    SVGAnimatedTransformList childTransformList = ((SVGTransformable) child).getTransform();
                    if (childTransformList != null) {
                        AffineTransform childTransform = ((SVGTransformListImpl) childTransformList.getAnimVal())
                                .getAffineTransform();
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

    private void recalculateViewboxToViewportTransform() {

        viewboxToViewportTransform = new AffineTransform();
        short align = getPreserveAspectRatio().getAnimVal().getAlign();
        short meetOrSlice = getPreserveAspectRatio().getAnimVal().getMeetOrSlice();
        float sx = getViewport().getWidth() / getViewBox().getAnimVal().getWidth();
        float sy = getViewport().getHeight() / getViewBox().getAnimVal().getHeight();

        if (align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_NONE) {
            viewboxToViewportTransform.scale(sx, sy);

            float tx = -getViewBox().getAnimVal().getX();
            float ty = -getViewBox().getAnimVal().getY();
            viewboxToViewportTransform.translate(tx, ty);

        } else { // are doing some sort of uniform scaling

            float scale;
            if (meetOrSlice == SVGPreserveAspectRatio.SVG_MEETORSLICE_MEET) {
                scale = Math.min(sx, sy);
            } else {
                scale = Math.max(sx, sy);
            }

            float vpX = 0;
            float vpY = 0;
            float vpWidth = getViewport().getWidth();
            float vpHeight = getViewport().getHeight();

            float vbX = getViewBox().getAnimVal().getX();
            float vbY = getViewBox().getAnimVal().getY();
            float vbWidth = getViewBox().getAnimVal().getWidth();
            float vbHeight = getViewBox().getAnimVal().getHeight();

            float tx;
            float ty;

            float ty1 = (vpY + vpHeight / 2) / scale - (vbY + vbHeight / 2);
            float ty2 = (vpY + vpHeight) / scale - (vbY + vbHeight);
            float tx1 = (vpX + vpWidth / 2) / scale - (vbX + vbWidth / 2);
            if (meetOrSlice == SVGPreserveAspectRatio.SVG_MEETORSLICE_MEET) {
                if (sy < sx) {
                    ty = vpY / scale - vbY;
                    if (align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMIN
                            || align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMID
                            || align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMAX) {
                        tx = vpX / scale - vbX;

                    } else if (align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMIN
                            || align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMID
                            || align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMAX) {
                        tx = tx1;

                    } else {
                        tx = vpX + vpWidth / scale - (vbX + vbWidth);
                    }
                } else {
                    tx = vpX / scale - vbX;

                    if (align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMIN
                            || align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMIN
                            || align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMAXYMIN) {
                        ty = vpY / scale - vbY;

                    } else if (align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMID
                            || align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMID
                            || align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMAXYMID) {
                        ty = ty1;

                    } else {
                        ty = ty2;
                    }
                }
            } else { // SLICE

                if (sy > sx) {
                    ty = vpY / scale - vbY;

                    if (align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMIN
                            || align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMID
                            || align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMAX) {
                        tx = vpX / scale - vbX;

                    } else if (align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMIN
                            || align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMID
                            || align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMAX) {
                        tx = tx1;

                    } else {
                        tx = (vpX + vpWidth) / scale - (vbX + vbWidth);
                    }

                } else {
                    tx = vpX - vbX * scale;

                    if (align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMIN
                            || align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMIN
                            || align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMAXYMIN) {
                        ty = vpY / scale - vbY;

                    } else if (align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMINYMID
                            || align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMIDYMID
                            || align == SVGPreserveAspectRatio.SVG_PRESERVEASPECTRATIO_XMAXYMID) {
                        ty = ty1;

                    } else {
                        ty = ty2;
                    }
                }
            }

            viewboxToViewportTransform.scale(scale, scale);
            viewboxToViewportTransform.translate(tx, ty);
        }
    }
}
