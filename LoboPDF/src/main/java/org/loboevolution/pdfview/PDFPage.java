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

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.loboevolution.pdfview.annotation.PDFAnnotation;
import org.loboevolution.pdfview.annotation.PDFAnnotation.ANNOTATION_TYPE;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.*;

/**
 * A PDFPage encapsulates the parsed commands required to render a
 * single page from a PDFFile. The PDFPage is not itself drawable;
 * instead, create a PDFImage to display something on the screen.
 * <p>
 * This file also contains all of the PDFCmd commands that might be a part of the command stream in
 * a PDFPage. They probably should be inner classes of PDFPage instead of separate non-public
 * classes.
 * <p>
 * Author Mike Wessler
 */
@Data
public class PDFPage {
    /**
     * a map from image info to weak references to parsers that are active
     */
    public final Map<ImageInfo, WeakReference<?>> renderers;
    /**
     * the array of commands. The length of this array will always
     * be greater than or equal to the actual number of commands.
     */
    private final List<PDFCmd> commands;
    /**
     * the page number used to find this page
     */
    private final int pageNumber;
    /**
     * the bounding box of the page, in page coordinates
     */
    private final Rectangle2D bbox;
    /**
     * the rotation of this page, in degrees
     */
    private final int rotation;
    /**
     * a map from image info (width, height, clip) to a soft reference to the
     * rendered image
     */
    private final Cache cache;
    /**
     * whether this page has been finished. If true, there will be no
     * more commands added to the cmds list.
     */
    private boolean finished = false;
    /**
     * List of annotations for this page
     */
    @Getter
    @Setter
    private List<PDFAnnotation> annots;

    /**
     * create a PDFPage with dimensions in bbox and rotation.
     *
     * @param bbox     a {@link java.awt.geom.Rectangle2D} object.
     * @param rotation a {@link java.lang.Integer} object.
     */
    public PDFPage(final Rectangle2D bbox, final int rotation) {
        this(-1, bbox, rotation, null);
    }

    /**
     * create a PDFPage with dimensions in bbox and rotation.
     *
     * @param pageNumber {@link java.lang.Integer} object.
     * @param bbox       a {@link java.awt.geom.Rectangle2D} object.
     * @param rote       {@link java.lang.Integer} object.
     * @param cache      a {@link org.loboevolution.pdfview.Cache} object.
     */
    public PDFPage(final int pageNumber, Rectangle2D bbox, final int rote, final Cache cache) {
        int rotation = rote;
        this.pageNumber = pageNumber;
        this.cache = cache;
        if (bbox == null) {
            bbox = new Rectangle2D.Float(0, 0, 1, 1);
        }
        rotation = rotation % 360; // less than a full turn
        if (rotation < 0) {
            rotation += 360;
        }
        rotation = rotation / 90; // for page rotation use only multiples of 90 degrees 
        rotation = rotation * 90; // 0, 90, 180, 270
        this.rotation = rotation;
        if (rotation == 90 || rotation == 270) {
            bbox = new Rectangle2D.Double(bbox.getX(), bbox.getY(), bbox.getHeight(), bbox.getWidth());
        }
        this.bbox = bbox;
        // initialize the cache of images and parsers
        this.renderers = Collections.synchronizedMap(new HashMap<>());
        // initialize the list of commands
        this.commands = Collections.synchronizedList(new ArrayList<>(250));
        // corresponding pop in PDFParser -> setStatus
        this.addPush();
    }

    /**
     * <p>createImageCmd.</p>
     *
     * @param image a {@link org.loboevolution.pdfview.PDFImage} object.
     * @return a {@link org.loboevolution.pdfview.PDFImageCmd} object.
     */
    public static PDFImageCmd createImageCmd(final PDFImage image) {
        return new PDFImageCmd(image);
    }

    /**
     * <p>createPushCmd.</p>
     *
     * @return a {@link org.loboevolution.pdfview.PDFPushCmd} object.
     */
    public static PDFPushCmd createPushCmd() {
        return new PDFPushCmd();
    }

    /**
     * <p>createPopCmd.</p>
     *
     * @return a {@link org.loboevolution.pdfview.PDFPopCmd} object.
     */
    public static PDFPopCmd createPopCmd() {
        return new PDFPopCmd();
    }

    /**
     * <p>createXFormCmd.</p>
     *
     * @param at a {@link java.awt.geom.AffineTransform} object.
     * @return a {@link org.loboevolution.pdfview.PDFXformCmd} object.
     */
    public static PDFXformCmd createXFormCmd(final AffineTransform at) {
        return new PDFXformCmd(new AffineTransform(at));
    }

    /**
     * Get the width and height of this image in the correct aspect ratio.
     * The image returned will have at least one of the width and
     * height values identical to those requested. The other
     * dimension may be smaller, so as to keep the aspect ratio
     * the same as in the original page.
     *
     * @param widthSize  the maximum width of the image
     * @param heightSize the maximum height of the image
     * @param clip       the region in <b>page space</b> of the page to
     *                   display. It may be null, in which the page's defined crop box
     *                   will be used.
     * @return a {@link java.awt.Dimension} object.
     */
    public Dimension getUnstretchedSize(final int widthSize, final int heightSize, Rectangle2D clip) {
        int height = heightSize;
        int width = widthSize;
        if (clip == null) {
            clip = this.bbox;
        } else {
            if (getRotation() == 90 || getRotation() == 270) {
                clip = new Rectangle2D.Double(clip.getX(), clip.getY(), clip.getHeight(), clip.getWidth());
            }
        }
        final double ratio = clip.getHeight() / clip.getWidth();
        final double askratio = (double) height / (double) width;
        if (askratio > ratio) {
            // asked for something too high
            height = (int) (width * ratio + 0.5);
        } else {
            // asked for something too wide
            width = (int) (height / ratio + 0.5);
        }
        return new Dimension(width, height);
    }

    /**
     * Get an image producer which can be used to draw the image
     * represented by this PDFPage. The ImageProducer is guaranteed to
     * stay in sync with the PDFPage as commands are added to it.
     * <p>
     * The image will contain the section of the page specified by the clip,
     * scaled to fit in the area given by width and height.
     *
     * @param width    the width of the image to be produced
     * @param height   the height of the image to be produced
     * @param clip     the region in <b>page space</b> of the entire page to
     *                 display
     * @param observer an image observer who will be notified when the
     *                 image changes, or null
     * @return an Image that contains the PDF data
     */
    public Image getImage(final int width, final int height, final Rectangle2D clip, final ImageObserver observer) {
        return getImage(width, height, clip, observer, true, false);
    }

    /**
     * Get an image producer which can be used to draw the image
     * represented by this PDFPage. The ImageProducer is guaranteed to
     * stay in sync with the PDFPage as commands are added to it.
     * <p>
     * The image will contain the section of the page specified by the clip,
     * scaled to fit in the area given by width and height.
     *
     * @param width    the width of the image to be produced
     * @param height   the height of the image to be produced
     * @param clip     the region in <b>page space</b> of the entire page to
     *                 display
     * @param observer an image observer who will be notified when the
     *                 image changes, or null
     * @param drawbg   if true, put a white background on the image. If not,
     *                 draw no color (alpha 0) for the background.
     * @param wait     if true, do not return until this image is fully rendered.
     * @return an Image that contains the PDF data
     */
    public Image getImage(final int width, final int height, final Rectangle2D clip, final ImageObserver observer, final boolean drawbg, final boolean wait) {
        // see if we already have this image
        BufferedImage image = null;
        PDFRenderer renderer = null;
        final ImageInfo info = new ImageInfo(width, height, clip, null);
        if (this.cache != null) {
            image = this.cache.getImage(this, info);
            renderer = this.cache.getImageRenderer(this, info);
        }
        // not in the cache, so create it
        if (image == null) {
            if (drawbg) {
                info.bgColor = Color.WHITE;
            }
            image = new RefImage(info.width, info.height, BufferedImage.TYPE_INT_ARGB);
            renderer = new PDFRenderer(this, info, image);
            if (this.cache != null) {
                this.cache.addImage(this, info, image, renderer);
            }
            this.renderers.put(info, new WeakReference<>(renderer));
        }
        // the renderer may be null if we are getting this image from the
        // cache and rendering has completed.
        if (renderer != null) {
            if (observer != null) {
                renderer.addObserver(observer);
            }

            if (!renderer.isFinished()) {
                renderer.go(wait);
                if (renderer.getStatus() == Watchable.ERROR) {
                    PDFDebugger.debug("Error during reading image!");
                }
            }
        }
        // return the image
        return image;
    }

    /**
     * get the aspect ratio of the correctly oriented page.
     *
     * @return the width/height aspect ratio of the page
     */
    public float getAspectRatio() {
        return getWidth() / getHeight();
    }

    /**
     * get the width of this page, after rotation
     *
     * @return a float.
     */
    public float getWidth() {
        return (float) this.bbox.getWidth();
    }

    /**
     * get the height of this page, after rotation
     *
     * @return a float.
     */
    public float getHeight() {
        return (float) this.bbox.getHeight();
    }

    /**
     * Get the initial transform to map from a specified clip rectangle in
     * pdf coordinates to an image of the specfied width and
     * height in device coordinates
     *
     * @param width  the width of the image
     * @param height the height of the image
     * @param clip   the desired clip rectangle (in PDF space) or null to use
     *               the page's bounding box
     * @return a {@link java.awt.geom.AffineTransform} object.
     */
    public AffineTransform getInitialTransform(int width, int height, Rectangle2D clip) {
        AffineTransform at = new AffineTransform();
        switch (getRotation()) {
            case 0:
                at = new AffineTransform(1, 0, 0, -1, 0, height);
                break;
            case 90:
                at = new AffineTransform(0, 1, 1, 0, 0, 0);
                break;
            case 180:
                at = new AffineTransform(-1, 0, 0, 1, width, 0);
                break;
            case 270:
                at = new AffineTransform(0, -1, -1, 0, width, height);
                break;
            default:
                break;
        }
        final double clipW;
        final double clipH;
        if (clip == null) {
            clip = getBbox();
            clipW = clip.getWidth();
            clipH = clip.getHeight();
        } else if (getRotation() == 90 || getRotation() == 270) {
            final int tmp = width;
            width = height;
            height = tmp;
            clipW = clip.getHeight();
            clipH = clip.getWidth();
        } else {
            clipW = clip.getWidth();
            clipH = clip.getHeight();
        }
        // now scale the image to be the size of the clip
        final double scaleX = width / clipW;
        final double scaleY = height / clipH;
        at.scale(scaleX, scaleY);
        // create a transform that moves the top left corner of the clip region
        // (minX, minY) to (0,0) in the image
        at.translate(-clip.getMinX(), -clip.getMinY());
        return at;
    }

    /*
     * get the commands in the page within the given start and end indices
     */

    /**
     * get the current number of commands for this page
     *
     * @return a {@link java.lang.Integer} object.
     */
    public int getCommandCount() {
        return this.commands.size();
    }

    /**
     * get the command at a given index
     *
     * @param index a {@link java.lang.Integer} object.
     * @return a {@link org.loboevolution.pdfview.PDFCmd} object.
     */
    public PDFCmd getCommand(final int index) {
        return this.commands.get(index);
    }

    /**
     * get all the commands in the current page starting at the given index
     *
     * @param startIndex a {@link java.lang.Integer} object.
     * @return a {@link java.util.List} object.
     */
    public List<PDFCmd> getCommands(final int startIndex) {
        return getCommands(startIndex, getCommandCount());
    }

    /**
     * <p>Getter for the field <code>commands</code>.</p>
     *
     * @param startIndex a {@link java.lang.Integer} object.
     * @param endIndex   a {@link java.lang.Integer} object.
     * @return a {@link java.util.List} object.
     */
    public List<PDFCmd> getCommands(final int startIndex, final int endIndex) {
        return this.commands.subList(startIndex, endIndex);
    }

    /**
     * <p>findLastCommand.</p>
     *
     * @param cls a {@link java.lang.Class} object.
     * @return a {@link org.loboevolution.pdfview.PDFCmd} object.
     */
    public PDFCmd findLastCommand(final Class<?> cls) {
        int index = this.commands.size();
        while (index-- > 0) {
            final PDFCmd cmd = this.commands.get(index);
            if (cmd.getClass().isAssignableFrom(cls)) {
                return cmd;
            }
        }
        return null;
    }

    /**
     * Add a single command to the page list.
     *
     * @param cmd a {@link org.loboevolution.pdfview.PDFCmd} object.
     */
    public void addCommand(final PDFCmd cmd) {
        synchronized (this.commands) {
            this.commands.add(cmd);
        }
        // notify any outstanding images
        updateImages();
    }

    /**
     * add a collection of commands to the page list. This is probably
     * invoked as the result of an XObject 'do' command, or through a
     * type 3 font.
     *
     * @param page a {@link org.loboevolution.pdfview.PDFPage} object.
     */
    public void addCommands(final PDFPage page) {
        addCommands(page, null);
    }

    /**
     * add a collection of commands to the page list. This is probably
     * invoked as the result of an XObject 'do' command, or through a
     * type 3 font.
     *
     * @param page  the source of other commands. It MUST be finished.
     * @param extra a transform to perform before adding the commands.
     *              If null, no extra transform will be added.
     */
    public void addCommands(final PDFPage page, final AffineTransform extra) {
        synchronized (this.commands) {
            addPush();
            if (extra != null) {
                addXform(extra);
            }
            // addXform(page.getTransform());
            this.commands.addAll(page.getCommands());
            addPop();
        }
        // notify any outstanding images
        updateImages();
    }

    /**
     * Clear all commands off the current page
     */
    public void clearCommands() {
        synchronized (this.commands) {
            this.commands.clear();
        }
        // notify any outstanding images
        updateImages();
    }

    /**
     * wait for finish
     *
     * @throws java.lang.InterruptedException if any.
     */
    public synchronized void waitForFinish() throws InterruptedException {
        if (!this.finished) {
            wait();
        }
    }

    /**
     * Stop the rendering of a particular image on this page
     *
     * @param width  a {@link java.lang.Integer} object.
     * @param height a {@link java.lang.Integer} object.
     * @param clip   a {@link java.awt.geom.Rectangle2D} object.
     */
    public void stop(final int width, final int height, final Rectangle2D clip) {
        final ImageInfo info = new ImageInfo(width, height, clip);
        synchronized (this.renderers) {
            // find our renderer
            final WeakReference<?> rendererRef = this.renderers.get(info);
            if (rendererRef != null) {
                final PDFRenderer renderer = (PDFRenderer) rendererRef.get();
                if (renderer != null) {
                    // stop it
                    renderer.stop();
                }
            }
        }
    }

    /**
     * The entire page is done. This must only be invoked once. All
     * observers will be notified.
     */
    public synchronized void finish() {
        PDFDebugger.debug("Page finished!", 1000);
        this.finished = true;
        notifyAll();
        // notify any outstanding images
        updateImages();
    }

    /**
     * push the graphics state
     */
    public void addPush() {
        addCommand(new PDFPushCmd());
    }

    /**
     * pop the graphics state
     */
    public void addPop() {
        addCommand(new PDFPopCmd());
    }

    /**
     * concatenate a transform to the graphics state
     *
     * @param at a {@link java.awt.geom.AffineTransform} object.
     */
    public void addXform(final AffineTransform at) {
        addCommand(new PDFXformCmd(new AffineTransform(at)));
    }

    /**
     * set the stroke width
     *
     * @param w the width of the stroke
     * @return a {@link org.loboevolution.pdfview.PDFChangeStrokeCmd} object.
     */
    public PDFChangeStrokeCmd addStrokeWidth(final float w) {
        final PDFChangeStrokeCmd sc = new PDFChangeStrokeCmd();
        sc.setWidth(w);
        addCommand(sc);
        return sc;
    }

    /**
     * set the end cap style
     *
     * @param capstyle the cap style: 0 = BUTT, 1 = ROUND, 2 = SQUARE
     */
    public void addEndCap(final int capstyle) {
        final PDFChangeStrokeCmd sc = new PDFChangeStrokeCmd();
        final int cap = switch (capstyle) {
            case 1 -> BasicStroke.CAP_ROUND;
            case 2 -> BasicStroke.CAP_SQUARE;
            default -> BasicStroke.CAP_BUTT;
        };
        sc.setEndCap(cap);
        addCommand(sc);
    }

    /**
     * set the line join style
     *
     * @param joinstyle the join style: 0 = MITER, 1 = ROUND, 2 = BEVEL
     */
    public void addLineJoin(final int joinstyle) {
        final PDFChangeStrokeCmd sc = new PDFChangeStrokeCmd();
        final int join = switch (joinstyle) {
            case 1 -> BasicStroke.JOIN_ROUND;
            case 2 -> BasicStroke.JOIN_BEVEL;
            default -> BasicStroke.JOIN_MITER;
        };
        sc.setLineJoin(join);
        addCommand(sc);
    }

    /**
     * set the miter limit
     *
     * @param limit a float.
     */
    public void addMiterLimit(final float limit) {
        final PDFChangeStrokeCmd sc = new PDFChangeStrokeCmd();
        sc.setMiterLimit(limit);
        addCommand(sc);
    }

    /**
     * set the dash style
     *
     * @param dashary the array of on-off lengths
     * @param phase   offset of the array at the start of the line drawing
     */
    public void addDash(final float[] dashary, final float phase) {
        final PDFChangeStrokeCmd sc = new PDFChangeStrokeCmd();
        sc.setDash(dashary, phase);
        addCommand(sc);
    }

    /**
     * set the current path
     *
     * @param path             the path
     * @param style            the style: PDFShapeCmd.STROKE, PDFShapeCmd.FILL,
     * @param autoAdjustStroke PDFShapeCmd.BOTH, PDFShapeCmd.CLIP, or some combination.
     */
    public void addPath(final GeneralPath path, final int style, final boolean autoAdjustStroke) {
        addCommand(new PDFShapeCmd(path, style, autoAdjustStroke));
    }

    /**
     * <p>addShadeCommand.</p>
     *
     * @param p   a {@link org.loboevolution.pdfview.PDFPaint} object.
     * @param box a {@link java.awt.geom.Rectangle2D} object.
     */
    public void addShadeCommand(final PDFPaint p, final Rectangle2D box) {
        addCommand(new PDFShadeCommand(p, box));
    }

    /**
     * set the fill paint
     *
     * @param p a {@link org.loboevolution.pdfview.PDFPaint} object.
     */
    public void addFillPaint(final PDFPaint p) {
        addCommand(new PDFFillPaintCmd(p));
    }

    /**
     * set the stroke paint
     *
     * @param p a {@link org.loboevolution.pdfview.PDFPaint} object.
     */
    public void addStrokePaint(final PDFPaint p) {
        addCommand(new PDFStrokePaintCmd(p));
    }

    /**
     * set the fill alpha
     *
     * @param a a float.
     */
    public void addFillAlpha(final float a) {
        addCommand(new PDFFillAlphaCmd(a));
    }

    /**
     * set the stroke alpha
     *
     * @param a a float.
     */
    public void addStrokeAlpha(final float a) {
        addCommand(new PDFStrokeAlphaCmd(a));
    }

    /**
     * draw an image
     *
     * @param image the image to draw
     */
    public void addImage(final PDFImage image) {
        addCommand(new PDFImageCmd(image));
    }

    /**
     * Notify all images we know about that a command has been added
     */
    public void updateImages() {
        for (final WeakReference<?> ref : this.renderers.values()) {
            final PDFRenderer renderer = (PDFRenderer) ref.get();
            if (renderer != null) {
                if (renderer.getStatus() == Watchable.NEEDS_DATA) {
                    renderer.setStatus(Watchable.PAUSED);
                }
            }
        }
    }

    /**
     * Get a list of all annotations of the given type for this PDF page
     *
     * @param type a {@link org.loboevolution.pdfview.annotation.PDFAnnotation.ANNOTATION_TYPE} object.
     * @return List of {@link org.loboevolution.pdfview.annotation.PDFAnnotation} object.
     */
    public List<PDFAnnotation> getAnnots(final ANNOTATION_TYPE type) {
        final List<PDFAnnotation> list = new ArrayList<>();
        if (this.annots != null) {
            for (final PDFAnnotation annot : this.annots) {
                if (annot.getType() == type) {
                    list.add(annot);
                }
            }
        }
        return list;
    }

    /**
     * <p>addAnnotations.</p>
     */
    public void addAnnotations() {
        if (this.annots != null) {
            for (final PDFAnnotation pdfAnnotation : this.annots) {
                // add command to the page if needed
                this.commands.addAll(pdfAnnotation.getPageCommandsForAnnotation());
            }
        }
    }
}

/**
 * draw an image
 */
class PDFImageCmd extends PDFCmd {
    final PDFImage image;

    /**
     * {@inheritDoc}
     */
    public PDFImageCmd(final PDFImage image) {
        this.image = image;
    }

    @Override
    public Rectangle2D execute(final PDFRenderer state) {
        return state.drawImage(this.image);
    }
}

/**
 * set the fill paint
 */
class PDFFillPaintCmd extends PDFCmd {
    final PDFPaint p;

    public PDFFillPaintCmd(final PDFPaint p) {
        this.p = p;
    }

    @Override
    public Rectangle2D execute(final PDFRenderer state) {
        state.setFillPaint(this.p);
        return null;
    }
}

/**
 * set the stroke paint
 */
class PDFStrokePaintCmd extends PDFCmd {

    final PDFPaint p;

    public PDFStrokePaintCmd(final PDFPaint p) {
        this.p = p;
    }

    @Override
    public Rectangle2D execute(final PDFRenderer state) {
        state.setStrokePaint(this.p);
        return null;
    }
}

/**
 * set the fill paint
 */
class PDFFillAlphaCmd extends PDFCmd {
    final float a;

    public PDFFillAlphaCmd(final float a) {
        this.a = a;
    }

    @Override
    public Rectangle2D execute(final PDFRenderer state) {
        state.setFillAlpha(this.a);
        return null;
    }
}

/**
 * set the stroke paint
 */
class PDFStrokeAlphaCmd extends PDFCmd {
    final float a;

    public PDFStrokeAlphaCmd(final float a) {
        this.a = a;
    }

    @Override
    public Rectangle2D execute(final PDFRenderer state) {
        state.setStrokeAlpha(this.a);
        return null;
    }
}

/**
 * set the shade paint
 */
class PDFShadeCommand extends PDFCmd {
    final PDFPaint p;
    final Rectangle2D box;

    PDFShadeCommand(final PDFPaint p, final Rectangle2D box) {
        this.p = p;
        this.box = box;
    }

    PDFShadeCommand(final PDFPaint p) {
        this.p = p;
        this.box = null;
    }

    @Override
    public Rectangle2D execute(final PDFRenderer state) {
        // TODO: Not sure this is the right way to get the area for the sh cmd
        Shape s = box;
        final Shape clip = state.getImage().getGraphics().getClipBounds();
        if (clip != null) {
            s = clip;
        }
        if (s == null) {
            s = state.getImage().getData().getBounds();
            try {
                s = state.getLastTransform().createInverse().createTransformedShape(s);
            } catch (final NoninvertibleTransformException e) {
                BaseWatchable.getErrorHandler().publishException(e);
            }
        }
        state.setFillAlpha(1);
        state.setFillPaint(p);
        return (new PDFShapeCmd(new GeneralPath(s), PDFShapeCmd.FILL, false)).execute(state);
    }
}

/**
 * push the graphics state
 */
class PDFPushCmd extends PDFCmd {
    @Override
    public Rectangle2D execute(final PDFRenderer state) {
        state.push();
        return null;
    }
}

/**
 * pop the graphics state
 */
class PDFPopCmd extends PDFCmd {
    @Override
    public Rectangle2D execute(final PDFRenderer state) {
        state.pop();
        return null;
    }
}

/**
 * concatenate a transform to the graphics state
 */
class PDFXformCmd extends PDFCmd {
    AffineTransform at;

    /**
     * <p>Constructor for PDFXformCmd.</p>
     *
     * @param at a {@link java.awt.geom.AffineTransform} object.
     */
    public PDFXformCmd(final AffineTransform at) {
        if (at == null) {
            throw new RuntimeException("Null transform in PDFXformCmd");
        }
        this.at = at;
    }

    @Override
    public Rectangle2D execute(final PDFRenderer state) {
        state.transform(this.at);
        return null;
    }

    /**
     * <p>toString.</p>
     *
     * @param state a {@link org.loboevolution.pdfview.PDFRenderer} object.
     * @return a {@link java.lang.String} object.
     */
    public String toString(final PDFRenderer state) {
        return "PDFXformCmd: " + this.at;
    }

    @Override
    public String getDetails() {
        return "PDFXformCommand: \n" +
                this.at.toString();
    }
}

/**
 * change the stroke style
 */
class PDFChangeStrokeCmd extends PDFCmd {
    float w, limit, phase;
    int cap, join;
    float[] ary;

    /**
     * <p>Constructor for PDFChangeStrokeCmd.</p>
     */
    public PDFChangeStrokeCmd() {
        this.w = PDFRenderer.NOWIDTH;
        this.cap = PDFRenderer.NOCAP;
        this.join = PDFRenderer.NOJOIN;
        this.limit = PDFRenderer.NOLIMIT;
        this.ary = PDFRenderer.NODASH;
        this.phase = PDFRenderer.NOPHASE;
    }

    /**
     * set the width of the stroke. Rendering needs to account for a minimum
     * stroke width in creating the output.
     *
     * @param w float
     */
    public void setWidth(final float w) {
        this.w = w;
    }

    public void setEndCap(final int cap) {
        this.cap = cap;
    }

    public void setLineJoin(final int join) {
        this.join = join;
    }

    /**
     * <p>setMiterLimit.</p>
     *
     * @param limit a float.
     */
    public void setMiterLimit(final float limit) {
        this.limit = limit;
    }

    /**
     * <p>setDash.</p>
     *
     * @param ary   an array of {@link float} objects.
     * @param phase a float.
     */
    public void setDash(final float[] ary, final float phase) {
        if (ary != null) {
            // make sure no pairs start with 0, since having no opaque
            // region doesn't make any sense.
            for (int i = 0; i < ary.length - 1; i += 2) {
                if (ary[i] == 0) {
                    /* Give a very small value, since 0 messes java up */
                    ary[i] = 0.00001f;
                    break;
                }
            }
        }
        this.ary = ary;
        this.phase = phase;
    }

    @Override
    public Rectangle2D execute(final PDFRenderer state) {
        state.setStrokeParts(this.w, this.cap, this.join, this.limit, this.ary, this.phase);
        return null;
    }

    public String toString(final PDFRenderer state) {
        return "STROKE: w=" + this.w + " cap=" + this.cap + " join=" + this.join + " limit=" + this.limit + " ary=" + Arrays.toString(this.ary) + " phase=" + this.phase;
    }
}
