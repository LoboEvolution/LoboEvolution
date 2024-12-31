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
package org.loboevolution.pdfview;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.*;

/**
 * This class turns a set of PDF Commands from a PDF page into an image.  It
 * encapsulates the state of drawing in terms of stroke, fill, transform,
 * etc., as well as pushing and popping these states.
 * When the run method is called, this class goes through all remaining commands
 * in the PDF Page and draws them to its buffered image.  It then updates any
 * ImageConsumers with the drawn data.
 */
public class PDFRenderer extends BaseWatchable implements Runnable {

    /**
     * how long (in milliseconds) to wait between image updates
     */
    public static final long UPDATE_DURATION = 200;
    /**
     * Constant <code>NOPHASE=-1000</code>
     */
    public static final float NOPHASE = -1000;
    /**
     * Constant <code>NOWIDTH=-1000</code>
     */
    public static final float NOWIDTH = -1000;
    /**
     * Constant <code>NOLIMIT=-1000</code>
     */
    public static final float NOLIMIT = -1000;
    /**
     * Constant <code>NOCAP=-1000</code>
     */
    public static final int NOCAP = -1000;
    /**
     * Constant <code>NODASH</code>
     */
    public static final float[] NODASH = null;
    /**
     * Constant <code>NOJOIN=-1000</code>
     */
    public static final int NOJOIN = -1000;
    /**
     * the image observers that will be updated when this image changes
     */
    private final List<ImageObserver> observers;
    /**
     * the info about the image, if we need to recreate it
     */
    private final ImageInfo imageinfo;
    /**
     * the page we were generate from
     */
    private PDFPage page;
    /**
     * where we are in the page's command list
     */
    private int currentCommand;
    /**
     * a weak reference to the image we render into.  For the image
     * to remain available, some other code must retain a strong reference to it.
     */
    private WeakReference<BufferedImage> imageRef;
    /**
     * the graphics object for use within an iteration.  Note this must be
     * set to null at the end of each iteration, or the image will not be
     * collected
     */
    private Graphics2D g;
    /**
     * the current graphics state
     */
    private GraphicsState state;
    /**
     * the stack of push()ed graphics states
     */
    private Deque<GraphicsState> stack;
    /**
     * the total region of this image that has been written to
     */
    private Rectangle2D globalDirtyRegion;
    /**
     * the last shape we drew (to check for overlaps)
     */
    @Getter
    @Setter
    private GeneralPath lastShape;
    @Getter
    private AffineTransform lastTransform;
    /**
     * the next time the image should be notified about updates
     */
    private long then = 0;
    /**
     * the sum of all the individual dirty regions since the last update
     */
    private Rectangle2D unupdatedRegion;


    /**
     * create a new PDFGraphics state
     *
     * @param page      the current page
     * @param imageinfo the paramters of the image to render
     * @param bi        a {@link java.awt.image.BufferedImage} object.
     */
    public PDFRenderer(final PDFPage page, final ImageInfo imageinfo, final BufferedImage bi) {
        super();

        this.page = page;
        this.imageinfo = imageinfo;
        this.imageRef = new WeakReference<>(bi);

        // initialize the list of observers
        this.observers = new ArrayList<>();
    }

    /**
     * create a new PDFGraphics state, given a Graphics2D. This version
     * will <b>not</b> create an image, and you will get a NullPointerException
     * if you attempt to call getImage().
     *
     * @param page      the current page
     * @param g         the Graphics2D object to use for drawing
     * @param imgbounds the bounds of the image into which to fit the page
     * @param clip      the portion of the page to draw, in page space, or null
     *                  if the whole page should be drawn
     * @param bgColor   the color to draw the background of the image, or
     *                  null for no color (0 alpha value)
     */
    public PDFRenderer(final PDFPage page, final Graphics2D g, final Rectangle imgbounds,
                       final Rectangle2D clip, final Color bgColor) {
        super();

        this.page = page;
        this.g = g;
        this.imageinfo = new ImageInfo(imgbounds.width, imgbounds.height,
                clip, bgColor);
        g.translate(imgbounds.x, imgbounds.y);

        // initialize the list of observers
        this.observers = new ArrayList<>();
    }

    /**
     * Set up the graphics transform to match the clip region
     * to the image size.
     */
    private void setupRendering(final Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

        if (this.imageinfo.bgColor != null) {
            g.setColor(this.imageinfo.bgColor);
            g.fillRect(0, 0, this.imageinfo.width, this.imageinfo.height);
        }

        g.setColor(Color.BLACK);

        // set the initial clip and transform on the graphics
        final AffineTransform at = getInitialTransform();
        g.transform(at);

        // set up the initial graphics state
        this.state = new GraphicsState();
        this.state.cliprgn = null;
        this.state.stroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
        this.state.strokePaint = PDFPaint.getColorPaint(Color.black);
        this.state.fillPaint = this.state.strokePaint;
        this.state.fillAlpha = AlphaComposite.getInstance(AlphaComposite.SRC);
        this.state.strokeAlpha = AlphaComposite.getInstance(AlphaComposite.SRC);
        this.state.xform = g.getTransform();

        // initialize the stack
        this.stack = new ArrayDeque<>();

        // initialize the current command
        this.currentCommand = 0;
    }

    /**
     * push the current graphics state onto the stack.  Continue working
     * with the current object; calling pop() restores the state of this
     * object to its state when push() was called.
     */
    public void push() {
        this.state.cliprgn = this.g.getClip();
        this.stack.push(this.state);

        this.state = (GraphicsState) this.state.clone();
    }

    /**
     * restore the state of this object to what it was when the previous
     * push() was called.
     */
    public void pop() {
        if (!this.stack.isEmpty()) {
            this.state = this.stack.pop();
        }

        setTransform(this.state.xform);
        setClip(this.state.cliprgn);
    }

    /**
     * draw an outline using the current stroke and draw paint
     *
     * @param s                the path to stroke
     * @param autoAdjustStroke a boolean.
     * @return a Rectangle2D to which the current region being
     * drawn will be added.  May also be null, in which case no dirty
     * region will be recorded.
     */
    public Rectangle2D stroke(GeneralPath s, final boolean autoAdjustStroke) {
        // TODO: consider autoAdjustStroke here instead of during parsing
        //      PDF specification p. 130 / > 10.6.5 
        this.g.setComposite(this.state.strokeAlpha);
        s = new GeneralPath(autoAdjustStrokeWidth(this.g, this.state.stroke).createStrokedShape(s));
        return this.state.strokePaint.fill(this, this.g, s);
    }

    /**
     * auto adjust the stroke width, according to 6.5.4, which presumes that
     * the device characteristics (an image) require a single pixel wide
     * line, even if the width is set to less. We determine the scaling to
     * see if we would produce a line that was too small, and if so, scale
     * it up to produce a graphics line of 1 pixel, or so. This matches our
     * output with Adobe Reader.
     *
     * @param g a {@link Graphics2D} object.
     * @param bs a {@link BasicStroke} object.
     * @return a {@link BasicStroke} object.
     */
    private BasicStroke autoAdjustStrokeWidth(final Graphics2D g, final BasicStroke bs) {
        final AffineTransform bt = new AffineTransform(g.getTransform());
        float width = bs.getLineWidth() * (float) bt.getScaleX();
        BasicStroke stroke = bs;
        if (width < 1f) {
            if (bt.getScaleX() > 0.01) {
                width = 1.0f / (float) bt.getScaleX();
            } else {
                // prevent division by a really small number
                width = Math.max(stroke.getLineWidth(), 1f);
            }
            stroke = new BasicStroke(width, bs.getEndCap(), bs.getLineJoin(), bs.getMiterLimit(), bs.getDashArray(), bs.getDashPhase());
        }
        return stroke;
    }

    /**
     * draw an outline.
     *
     * @param p  the path to draw
     * @param bs the stroke with which to draw the path
     */
    public void draw(final GeneralPath p, final BasicStroke bs) {
        this.g.setComposite(this.state.fillAlpha);
        this.g.setPaint(this.state.fillPaint.getPaint());
        this.g.setStroke(autoAdjustStrokeWidth(this.g, bs));
        this.g.draw(p);
    }

    /**
     * fill an outline using the current fill paint
     *
     * @param s the path to fill
     * @return a {@link java.awt.geom.Rectangle2D} object.
     */
    public Rectangle2D fill(GeneralPath s) {
        this.g.setComposite(this.state.fillAlpha);
        if (s == null) {
            final GraphicsState gs = stack.peek();
            if (gs.cliprgn != null) {
                s = new GeneralPath(gs.cliprgn);
            }
        }
        return this.state.fillPaint.fill(this, this.g, s);
    }

    /**
     * draw an image.
     *
     * @param image the image to draw
     * @return a {@link java.awt.geom.Rectangle2D} object.
     */
    public Rectangle2D drawImage(final PDFImage image) {

        BufferedImage bi;
        try {
            bi = image.getImage();
        } catch (final PDFImageParseException e) {
            // maybe it was an unsupported format, or something.
            // Nothing to draw, anyway!
            return new Rectangle2D.Double();
        }

        // transform must use bitmap size
        final AffineTransform at = new AffineTransform(1f / bi.getWidth(), 0,
                0, -1f / bi.getHeight(),
                0, 1);

        if (image.isImageMask()) {
            bi = getMaskedImage(bi);
        }

        final Rectangle r = g.getTransform().createTransformedShape(new Rectangle(0, 0, 1, 1)).getBounds();
        final boolean isBlured = false;

        if (Configuration.getInstance().isUseBlurResizingForImages() &&
                bi.getType() != BufferedImage.TYPE_CUSTOM &&
                bi.getWidth() >= 1.75 * r.getWidth() && bi.getHeight() >= 1.75 * r.getHeight()) {
            try {
                return smartDrawImage(image, bi, r, at);
            } catch (final Exception e) {
                // do nothing, just go on with the "default" processing
            }
        }

        this.g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

        //Image quality is better when using texturepaint instead of drawimage
        //but it is also slower :(
        this.g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        // banded rendering may lead to lower memory consumption for e.g. scanned PDFs with large images
        final int bandSize = Configuration.getInstance().getThresholdForBandedImageRendering();
        if (bandSize > 0 && bi.getHeight() > bandSize) {
            // draw in bands
            final int tempMax = bi.getHeight();
            for (int offset = 0; offset < tempMax; offset += bandSize) {
                final int h = Math.min(tempMax - offset, bandSize);
                final AffineTransform translated = AffineTransform.getTranslateInstance(0, -(double) offset / tempMax);
                translated.concatenate(at);

                if (!g.drawImage(bi.getSubimage(0, offset, bi.getWidth(), h), translated, null)) {
                    PDFDebugger.debug("Image part not completed!", 10);
                }
            }
        } else {
            if (!g.drawImage(bi, at, null)) {
                PDFDebugger.debug("Image not completed!", 10);
            }
        }

        if (isBlured) bi.flush();

        // get the total transform that was executed
        final AffineTransform bt = new AffineTransform(this.g.getTransform());
        bt.concatenate(at);

        final double minx = bi.getMinX();
        final double miny = bi.getMinY();

        final double[] points = new double[]{
                minx, miny, minx + bi.getWidth(), miny + bi.getHeight()
        };
        bt.transform(points, 0, points, 0, 2);

        return new Rectangle2D.Double(points[0], points[1],
                points[2] - points[0],
                points[3] - points[1]);

    }

    private Rectangle2D smartDrawImage(final PDFImage image, BufferedImage bi, final Rectangle r, AffineTransform at) {
        boolean isBlured = false;

        if (Configuration.getInstance().isUseBlurResizingForImages() &&
                bi.getType() != BufferedImage.TYPE_CUSTOM &&
                bi.getWidth() >= 1.75 * r.getWidth() && bi.getHeight() >= 1.75 * r.getHeight()) {

            final BufferedImageOp op;
            // indexed colored images need to be converted for the convolveOp
            final boolean colorConversion = (bi.getColorModel() instanceof IndexColorModel);
            final float maxFactor = 3.5f;
            if (bi.getWidth() > maxFactor * r.getWidth() && bi.getHeight() > maxFactor * r.getHeight()) {
                //First resize, otherwise we risk that we get out of heapspace
                final int newHeight = (int) Math.round(maxFactor * r.getHeight());
                final int newWidth = (int) Math.round(maxFactor * r.getWidth());
                final BufferedImage resized = new BufferedImage(newWidth,
                        newHeight, colorConversion ? BufferedImage.TYPE_INT_ARGB : bi.getType());
                final Graphics2D bg = (Graphics2D) resized.getGraphics();
                bg.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                bg.drawImage(bi, 0, 0, newWidth, newHeight, null);
                bi = resized;
                at = new AffineTransform(1f / bi.getWidth(), 0,
                        0, -1f / bi.getHeight(),
                        0, 1);

                final float weight = 1.0f / 16.0f;
                final float[] blurKernel = {
                        weight, weight, weight, weight,
                        weight, weight, weight, weight,
                        weight, weight, weight, weight,
                        weight, weight, weight, weight,
                };
                op = new ConvolveOp(new Kernel(4, 4, blurKernel), ConvolveOp.EDGE_NO_OP, null);
            } else {
                final float weight = 1.0f / 18.0f;
                final float[] blurKernel = {
                        1 * weight, 2 * weight, 1 * weight,
                        2 * weight, 6 * weight, 2 * weight,
                        1 * weight, 2 * weight, 1 * weight
                };
                if (colorConversion) {
                    final BufferedImage colored = new BufferedImage(bi.getWidth(),
                            bi.getHeight(), colorConversion ? BufferedImage.TYPE_INT_ARGB : bi.getType());
                    final Graphics2D bg = (Graphics2D) colored.getGraphics();
                    bg.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    bg.drawImage(bi, 0, 0, bi.getWidth(), bi.getHeight(), null);
                    bi = colored;
                }
                op = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, null);
            }

            final BufferedImage blured = op.createCompatibleDestImage(bi,
                    colorConversion ? ColorModel.getRGBdefault() : bi.getColorModel());

            op.filter(bi, blured);
            bi = blured;
            isBlured = true;
        }

        this.g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

        //Image quality is better when using texturepaint instead of drawimage
        //but it is also slower :(
        this.g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        // banded rendering may lead to lower memory consumption for e.g. scanned PDFs with large images
        final int bandSize = Configuration.getInstance().getThresholdForBandedImageRendering();
        if (bandSize > 0 && bi.getHeight() > bandSize) {
            // draw in bands
            final int tempMax = bi.getHeight();
            for (int offset = 0; offset < tempMax; offset += bandSize) {
                final int h = Math.min(tempMax - offset, bandSize);
                final AffineTransform translated = AffineTransform.getTranslateInstance(0, -(double) offset / tempMax);
                translated.concatenate(at);

                if (!g.drawImage(bi.getSubimage(0, offset, bi.getWidth(), h), translated, null)) {
                    PDFDebugger.debug("Image part not completed!", 10);
                }
            }
        } else {
            if (!g.drawImage(bi, at, null)) {
                PDFDebugger.debug("Image not completed!", 10);
            }
        }

        if (isBlured) bi.flush();

        // get the total transform that was executed
        final AffineTransform bt = new AffineTransform(this.g.getTransform());
        bt.concatenate(at);

        final double minx = bi.getMinX();
        final double miny = bi.getMinY();

        final double[] points = new double[]{
                minx, miny, minx + bi.getWidth(), miny + bi.getHeight()
        };
        bt.transform(points, 0, points, 0, 2);

        return new Rectangle2D.Double(points[0], points[1],
                points[2] - points[0],
                points[3] - points[1]);
    }

    /**
     * add the path to the current clip.  The new clip will be the intersection
     * of the old clip and given path.
     *
     * @param s a {@link java.awt.geom.GeneralPath} object.
     */
    public void clip(final GeneralPath s) {
        this.g.clip(s);
    }

    /**
     * set the clip to be the given shape.  The current clip is not taken
     * into account.
     */
    private void setClip(final Shape s) {
        this.state.cliprgn = s;
        this.g.setClip(null);
        this.g.clip(s);
    }

    /**
     * get the current affinetransform
     *
     * @return a {@link java.awt.geom.AffineTransform} object.
     */
    public AffineTransform getTransform() {
        return this.state.xform;
    }

    /**
     * replace the current transform with the given one.
     *
     * @param at a {@link java.awt.geom.AffineTransform} object.
     */
    public void setTransform(final AffineTransform at) {
        this.state.xform = at;
        this.g.setTransform(this.state.xform);
    }

    /**
     * concatenate the given transform with the current transform
     *
     * @param at a {@link java.awt.geom.AffineTransform} object.
     */
    public void transform(final AffineTransform at) {
        this.state.xform.concatenate(at);
        this.g.setTransform(this.state.xform);
    }

    /**
     * get the initial transform from page space to Java space
     *
     * @return a {@link java.awt.geom.AffineTransform} object.
     */
    public AffineTransform getInitialTransform() {
        return this.page.getInitialTransform(this.imageinfo.width,
                this.imageinfo.height,
                this.imageinfo.clip);
    }

    /**
     * Set some or all aspects of the current stroke.
     *
     * @param wStroke     the width of the stroke, or NOWIDTH to leave it unchanged
     * @param capStroke   the end cap style, or NOCAP to leave it unchanged
     * @param joinStroke  the join style, or NOJOIN to leave it unchanged
     * @param limitStroke the miter limit, or NOLIMIT to leave it unchanged
     * @param aryStroke   the dash array, or null to leave it unchanged.  phase
     *                    and ary must both be valid, or phase must be NOPHASE while ary is null.
     * @param phaseStroke the phase of the dash array, or NOPHASE to leave it unchanged
     */
    public void setStrokeParts(final float wStroke, final int capStroke, final int joinStroke,
                               final float limitStroke, final float[] aryStroke, final float phaseStroke) {
        float w = wStroke;
        int cap = capStroke;
        int join = joinStroke;
        float limit = limitStroke;
        float[] ary = aryStroke;
        float phase = phaseStroke;
        if (w == NOWIDTH) {
            w = this.state.stroke.getLineWidth();
        }
        if (cap == NOCAP) {
            cap = this.state.stroke.getEndCap();
        }
        if (join == NOJOIN) {
            join = this.state.stroke.getLineJoin();
        }
        if (limit == NOLIMIT) {
            limit = this.state.stroke.getMiterLimit();
        }
        if (phase == NOPHASE) {
            ary = this.state.stroke.getDashArray();
            phase = this.state.stroke.getDashPhase();
        }
        if (ary != null && ary.length == 0) {
            ary = null;
        }
        if (phase == NOPHASE) {
            this.state.stroke = new BasicStroke(w, cap, join, limit);
        } else {
            this.state.stroke = new BasicStroke(w, cap, join, limit, ary, phase);
        }
    }

    /**
     * get the current stroke as a BasicStroke
     *
     * @return a {@link java.awt.BasicStroke} object.
     */
    public BasicStroke getStroke() {
        return this.state.stroke;
    }

    /**
     * set the current stroke as a BasicStroke
     *
     * @param bs a {@link java.awt.BasicStroke} object.
     */
    public void setStroke(final BasicStroke bs) {
        this.state.stroke = bs;
    }

    /**
     * set the stroke color
     *
     * @param paint a {@link org.loboevolution.pdfview.PDFPaint} object.
     */
    public void setStrokePaint(final PDFPaint paint) {
        this.state.strokePaint = paint;
    }

    /**
     * set the fill color
     *
     * @param paint a {@link org.loboevolution.pdfview.PDFPaint} object.
     */
    public void setFillPaint(final PDFPaint paint) {
        this.state.fillPaint = paint;
    }

    /**
     * set the stroke alpha
     *
     * @param alpha a float.
     */
    public void setStrokeAlpha(final float alpha) {
        this.state.strokeAlpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                alpha);
    }

    /**
     * set the stroke alpha
     *
     * @param alpha a float.
     */
    public void setFillAlpha(final float alpha) {
        this.state.fillAlpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                alpha);
    }

    /**
     * Add an image observer
     *
     * @param observer a {@link java.awt.image.ImageObserver} object.
     */
    public void addObserver(final ImageObserver observer) {
        if (observer == null) {
            return;
        }

        // update the new observer to the current state
        final Image i = this.imageRef.get();
        if (rendererFinished()) {
            // if we're finished, just send a finished notification, don't
            // add to the list of observers
            observer.imageUpdate(i, ImageObserver.ALLBITS, 0, 0,
                    this.imageinfo.width, this.imageinfo.height);
        } else {
            // if we're not yet finished, add to the list of observers and
            // notify of the current dirty region
            synchronized (this.observers) {
                this.observers.add(observer);
            }

            if (this.globalDirtyRegion != null) {
                observer.imageUpdate(i, ImageObserver.SOMEBITS,
                        (int) this.globalDirtyRegion.getMinX(),
                        (int) this.globalDirtyRegion.getMinY(),
                        (int) this.globalDirtyRegion.getWidth(),
                        (int) this.globalDirtyRegion.getHeight());
            }
        }
    }

    /**
     * Remove an image observer
     *
     * @param observer a {@link java.awt.image.ImageObserver} object.
     */
    public void removeObserver(final ImageObserver observer) {
        synchronized (this.observers) {
            this.observers.remove(observer);
        }
    }

    /**
     * If exists, returns the image which is used by the renderer.
     *
     * @return a BufferedImage or null
     */
    public BufferedImage getImage() {
        if (this.imageRef == null) return null;
        return this.imageRef.get();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Setup rendering.  Called before iteration begins
     */
    @Override
    public void setup() {
        Graphics2D graphics = null;

        if (this.imageRef != null) {
            final BufferedImage bi = this.imageRef.get();
            if (bi != null) {
                graphics = bi.createGraphics();
            }
        } else {
            graphics = this.g;
        }


        if (graphics != null) {
            setupRendering(graphics);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Draws the next command in the PDFPage to the buffered image.
     * The image will be notified about changes no less than every
     * UPDATE_DURATION milliseconds.
     */
    @Override
    public int iterate() throws Exception {
        // make sure we have a page to render
        if (this.page == null) {
            return Watchable.COMPLETED;
        }

        // check if this renderer is based on a weak reference to a graphics
        // object.  If it is, and the graphics is no longer valid, then just quit
        BufferedImage bi = null;
        if (this.imageRef != null) {
            bi = this.imageRef.get();
            if (bi == null) {
                PDFDebugger.debug("Image went away.  Stopping");
                return Watchable.STOPPED;
            }

            this.g = bi.createGraphics();
        }

        // check if there are any commands to parse.  If there aren't,
        // just return, but check if we'return really finished or not
        if (this.currentCommand >= this.page.getCommandCount()) {
            if (this.page.isFinished()) {
                return Watchable.COMPLETED;
            } else {
                return Watchable.NEEDS_DATA;
            }
        }

        // find the current command
        final PDFCmd cmd = this.page.getCommand(this.currentCommand++);
        if (cmd == null) {
            // uh oh.  Synchronization problem!
            throw new PDFParseException("Command not found!");
        }

        // execute the command
        final Rectangle2D dirtyRegion = cmd.execute(this);

        // append to the global dirty region
        this.globalDirtyRegion = addDirtyRegion(dirtyRegion, this.globalDirtyRegion);
        this.unupdatedRegion = addDirtyRegion(dirtyRegion, this.unupdatedRegion);

        final long now = System.currentTimeMillis();
        if (now > this.then || rendererFinished()) {
            // now tell any observers, so they can repaint
            notifyObservers(bi, this.unupdatedRegion);
            this.unupdatedRegion = null;
            this.then = now + UPDATE_DURATION;
        }

        // if we are based on a reference to a graphics, don't hold on to it
        // since that will prevent the image from being collected.
        if (this.imageRef != null) {
            this.g = null;
        }

        // if we need to stop, it will be caught at the start of the next
        // iteration.
        return Watchable.RUNNING;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Called when iteration has stopped
     */
    @Override
    public void cleanup() {
        this.page = null;
        this.state = null;
        this.stack = null;
        this.globalDirtyRegion = null;
        this.lastShape = null;

        this.observers.clear();

        // keep around the image ref and image info for use in
        // late addObserver() call
    }

    /**
     * Append a rectangle to the total dirty region of this shape
     */
    private Rectangle2D addDirtyRegion(final Rectangle2D region, final Rectangle2D glob) {
        if (region == null) {
            return glob;
        } else if (glob == null) {
            return region;
        } else {
            Rectangle2D.union(glob, region, glob);
            return glob;
        }
    }

    /**
     * Determine if we are finished
     */
    private boolean rendererFinished() {
        if (this.page == null) {
            return true;
        }

        return (this.page.isFinished() && this.currentCommand == this.page.getCommandCount());
    }

    /**
     * Notify the observer that a region of the image has changed
     */
    private void notifyObservers(final BufferedImage bi, final Rectangle2D region) {
        if (bi == null) {
            return;
        }

        int startx, starty, width, height;
        int flags = 0;

        // don't do anything if nothing is there or no one is listening
        if ((region == null && !rendererFinished()) || this.observers == null ||
                this.observers.isEmpty()) {
            return;
        }

        if (region != null) {
            // get the image data for the total dirty region
            startx = (int) Math.floor(region.getMinX());
            starty = (int) Math.floor(region.getMinY());
            width = (int) Math.ceil(region.getWidth());
            height = (int) Math.ceil(region.getHeight());

            // sometimes width or height is negative.  Grrr...
            if (width < 0) {
                startx += width;
                width = -width;
            }
            if (height < 0) {
                starty += height;
                height = -height;
            }

            flags = 0;
        } else {
            startx = 0;
            starty = 0;
            width = this.imageinfo.width;
            height = this.imageinfo.height;
        }
        if (rendererFinished()) {
            flags |= ImageObserver.ALLBITS;
            // forget about the Graphics -- allows the image to be
            // garbage collected.
            this.g = null;
        } else {
            flags |= ImageObserver.SOMEBITS;
        }

        synchronized (this.observers) {
            for (final Iterator<ImageObserver> i = this.observers.iterator(); i.hasNext(); ) {
                final ImageObserver observer = i.next();

                final boolean result = observer.imageUpdate(bi, flags,
                        startx, starty,
                        width, height);

                // if result is false, the observer no longer wants to
                // be notified of changes
                if (!result) {
                    i.remove();
                }
            }
        }
    }

    /**
     * Convert an image mask into an image by painting over any pixels
     * that have a value in the image with the current paint
     */
    private BufferedImage getMaskedImage(final BufferedImage bi) {

        // get the color of the current paint
        final Paint paint = state.fillPaint.getPaint();
        if (!(paint instanceof Color col)) {
            // TODO - support other types of Paint
            return bi;
        }

        final ColorModel colorModel = bi.getColorModel();
        if (colorModel instanceof IndexColorModel) {
            final int mapSize = ((IndexColorModel) colorModel).getMapSize();
            final int pixelSize = colorModel.getPixelSize();
            if (mapSize == 2 && pixelSize == 1) {
                // we have a monochrome image mask with 1 bit per pixel
                // swap out the standard color with the current paint color
                final int[] rgbValues = new int[2];
                ((IndexColorModel) colorModel).getRGBs(rgbValues);
                byte[] colorComponents = null;
                if (rgbValues[0] == 0xff000000) {
                    // normal case color at 0
                    colorComponents = new byte[]{
                            (byte) col.getRed(),
                            (byte) col.getGreen(),
                            (byte) col.getBlue(),
                            (byte) col.getAlpha(),
                            0, 0, 0, 0 // the background is transparent
                    };
                } else if (rgbValues[1] == 0xff000000) {
                    // alternate case color at 1
                    colorComponents = new byte[]{
                            0, 0, 0, 0, // the background is transparent
                            (byte) col.getRed(),
                            (byte) col.getGreen(),
                            (byte) col.getBlue(),
                            (byte) col.getAlpha()
                    };
                }

                if (colorComponents != null) {
                    // replace mapped colors
                    final int startIndex = 0;
                    final boolean hasAlpha = true;
                    final ColorModel replacementColorModel = new IndexColorModel(pixelSize, mapSize, colorComponents, startIndex, hasAlpha);
                    final WritableRaster raster = bi.getRaster();
                    return new BufferedImage(replacementColorModel, raster, false, null);
                } else {
                    return bi; // no color replacement
                }
            }
        }

        // format as 8 bits each of ARGB
        int paintColor = col.getAlpha() << 24;
        paintColor |= col.getRed() << 16;
        paintColor |= col.getGreen() << 8;
        paintColor |= col.getBlue();

        // transparent (alpha = 1)
        final int noColor = 0;

        // get the coordinates of the source image
        final int startX = bi.getMinX();
        final int startY = bi.getMinY();
        final int width = bi.getWidth();
        final int height = bi.getHeight();

        // create a destion image of the same size
        final BufferedImage dstImage =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // copy the pixels row by row
        for (int i = 0; i < height; i++) {
            final int[] srcPixels = new int[width];
            final int[] dstPixels = new int[srcPixels.length];

            // read a row of pixels from the source
            bi.getRGB(startX, startY + i, width, 1, srcPixels, 0, height);

            // figure out which ones should get painted
            for (int j = 0; j < srcPixels.length; j++) {
                if (srcPixels[j] == 0xff000000) {
                    dstPixels[j] = paintColor;
                } else {
                    dstPixels[j] = noColor;
                }
            }

            // write the destination image
            dstImage.setRGB(startX, startY + i, width, 1, dstPixels, 0, height);
        }

        return dstImage;
    }

    /**
     * **********************************************************************
     * Remember the current transformation
     * **********************************************************************
     */
    public void rememberTransformation() {
        this.lastTransform = this.state.xform;
    }

    static class GraphicsState implements Cloneable {

        /**
         * the clip region
         */
        Shape cliprgn;
        /**
         * the current stroke
         */
        BasicStroke stroke;
        /**
         * the current paint for drawing strokes
         */
        PDFPaint strokePaint;
        /**
         * the current paint for filling shapes
         */
        PDFPaint fillPaint;
        /**
         * the current compositing alpha for stroking
         */
        AlphaComposite strokeAlpha;
        /**
         * the current compositing alpha for filling
         */
        AlphaComposite fillAlpha;
        /**
         * the current transform
         */
        AffineTransform xform;

        /**
         * Clone this Graphics state.
         * Note that cliprgn is not cloned.  It must be set manually from
         * the current graphics object's clip
         */
        @Override
        public Object clone() {
            final GraphicsState cState = new GraphicsState();
            cState.cliprgn = null;

            // copy immutable fields
            cState.strokePaint = this.strokePaint;
            cState.fillPaint = this.fillPaint;
            cState.strokeAlpha = this.strokeAlpha;
            cState.fillAlpha = this.fillAlpha;

            // clone mutable fields
            cState.stroke = new BasicStroke(this.stroke.getLineWidth(),
                    this.stroke.getEndCap(),
                    this.stroke.getLineJoin(),
                    this.stroke.getMiterLimit(),
                    this.stroke.getDashArray(),
                    this.stroke.getDashPhase());
            cState.xform = (AffineTransform) this.xform.clone();

            return cState;
        }
    }
}
