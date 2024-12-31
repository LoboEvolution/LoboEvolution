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

package org.loboevolution.html.dom.canvas;

import lombok.Getter;
import lombok.Setter;
import org.loboevolution.common.Strings;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.*;
import org.loboevolution.html.dom.domimpl.HTMLCanvasElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.style.FontValues;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.info.TimingInfo;
import org.loboevolution.laf.ColorFactory;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;
import org.loboevolution.laf.LAFType;
import org.loboevolution.net.HttpNetwork;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * <p>CanvasRenderingImpl class.</p>
 */
@Getter
@Setter
public class CanvasRenderingImpl implements CanvasRenderingContext2D {

    /** The canvas. */
    private final HTMLCanvasElementImpl canvas;

    /** The image. */
    private final BufferedImage image;

    /** The global alpha.*/
    private final Float globalAlpha;

    /** The fill style.*/
    private Object fillStyle;

    /** The stroke style.*/
    private Object strokeStyle;

    /** The line width.*/
    private Integer lineWidth;

    /** The translate x.*/
    private Integer translateX;

    /** The translate y. */
    private Integer translateY;

    /** The rotate.*/
    private Double rotate;

    /** The scale x.*/
    private Integer scaleX;

    /** The scale y. */
    private Integer scaleY;

    /** The font. */
    private Font font;

    /** The Integer line cap. */
    private Integer intLineCap;

    /** The Integerline join. */
    private Integer intlineJoin;

    /** The miter limit. */
    private Integer miterLimit;

    /** The path. */
    private GeneralPath path;

    /** The Affine Transform. */
    private AffineTransform affineTransform;

    /** The global Composite Operation. */
    private String globalCompositeOperation;

    /** The text align
     */
    private String textAlign;

    /** The textBaseline. */
    private String textBaseline;

    /** The shadow blur
     */
    private Integer shadowBlur;

    /** The shadow color
     */
    private String shadowColor;

    /** The shadow offset x
     */
    private Integer shadowOffsetX;

    /** The shadow offset y
     */
    private Integer shadowOffsetY;

    private String fillRule;

    /**
     * <p>Constructor for CanvasRenderingImpl.</p>
     *
     * @param canvas a {@link org.loboevolution.html.dom.domimpl.HTMLCanvasElementImpl} object. */
    public CanvasRenderingImpl(final HTMLCanvasElementImpl canvas) {
        this.canvas = canvas;
        image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        image.coerceData(true);
        affineTransform = new AffineTransform(1, 0, 0, 1, 0, 0);
        path = new GeneralPath();
        fillStyle = Color.BLACK;
        strokeStyle = Color.BLACK;
        globalAlpha = 1.0f;
        intLineCap = BasicStroke.CAP_BUTT;
        intlineJoin = BasicStroke.JOIN_BEVEL;
        lineWidth = 1;
        translateX = 0;
        translateY = 0;
        shadowBlur = 0;
        rotate = 0.0;
        scaleX = 1;
        scaleY = 1;
        miterLimit = 1;
        globalCompositeOperation = "source-over";
        textAlign = "left";
		textBaseline = "alphabetic";
    }

    /** {@inheritDoc} */
    @Override
    public String getDirection() {
        return "";
    }

    /** {@inheritDoc} */
    @Override
    public Object getFilter() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Font getFont() {
        if (this.font == null) {
            final HtmlRendererConfig config = canvas.getHtmlRendererConfig();
            final FontFactory fontFactory = FontFactory.getInstance();
            this.font = fontFactory.getFont(FontValues.getDefaultFontKey(config));
        }
        return this.font;
    }

    /** {@inheritDoc} */
    @Override
    public String getFontKerning() {
        return "";
    }

    /** {@inheritDoc} */
    @Override
    public String getFontStretch() {
        return "";
    }

    /** {@inheritDoc} */
    @Override
    public String getFontVariantCaps() {
        return "";
    }

    /** {@inheritDoc} */
    @Override
    public Float getGlobalAlpha() {
        return globalAlpha;
    }

    /** {@inheritDoc} */
    @Override
    public boolean getImageSmoothingEnabled() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public String getImageSmoothingQuality() {
        return "";
    }

    /** {@inheritDoc} */
    @Override
    public String getLetterSpacing() {
        return "";
    }

    /** {@inheritDoc} */
    @Override
    public String getLineCap() {
		return switch (intLineCap) {
			case BasicStroke.CAP_ROUND -> "round";
			case BasicStroke.CAP_SQUARE -> "square";
			default -> "butt";
		};
    }

    /** {@inheritDoc} */
    @Override
    public Integer lineDashOffset() {
        return 0;
    }

	/** {@inheritDoc} */
	@Override
	public String getLineJoin() {
		return switch (intlineJoin) {
			case BasicStroke.JOIN_ROUND -> "round";
			case BasicStroke.JOIN_MITER -> "miter";
			default -> "bevel";
		};
	}

    /** {@inheritDoc} */
    @Override
    public String getShadowColor() {
		return Strings.isBlank(shadowColor) ? "black" : shadowColor;
    }

    /** {@inheritDoc} */
    @Override
    public String getTextRendering() {
        return "";
    }

    /** {@inheritDoc} */
    @Override
    public String getWordSpacing() {
        return "";
    }

    /** {@inheritDoc} */
    @Override
    public CanvasRenderingContext2DSettings getContextAttributes() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public void drawImage(CanvasImageSource image, Integer dx, Integer dy) {
		if (image instanceof HTMLImageElementImpl hImage) {
			final TimingInfo info = new TimingInfo();
			final Image img = HttpNetwork.getImage(hImage, info, true);
			final Graphics2D graphics = createGraphics();
			final AffineTransform at = new AffineTransform();
			at.setToTranslation(dx, dy);
			graphics.drawImage(img, at, null);

			final HtmlRendererContext htmlRendererContext = hImage.getHtmlRendererContext();
			final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
			htmlPanel.getBrowserPanel().getTimingList.add(info);
		}
    }

    /** {@inheritDoc} */
    @Override
    public void drawImage(CanvasImageSource image, Integer dx, Integer dy, Integer dw, Integer dh) {
        if (image instanceof HTMLImageElementImpl hImage) {
            final TimingInfo info = new TimingInfo();
            final Image img = HttpNetwork.getImage(hImage, info, true);
            if (img != null) {
                final Graphics2D graphics = createGraphics();
                final AffineTransform at = new AffineTransform((float) dw / img.getWidth(null), 0, 0, (float) dh / img.getHeight(null), dx, dy);
                graphics.drawImage(img, at, null);

                final HtmlRendererContext htmlRendererContext = hImage.getHtmlRendererContext();
                final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
                htmlPanel.getBrowserPanel().getTimingList.add(info);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void drawImage(CanvasImageSource image, Integer sx, Integer sy, Integer sw, Integer sh, Integer dx, Integer dy, Integer dw, Integer dh) {
		if (image instanceof HTMLImageElementImpl hImage) {
			final TimingInfo info = new TimingInfo();
			final Image img = HttpNetwork.getImage(hImage, info, true);
			final Graphics2D graphics = createGraphics();
			graphics.clip(new Rectangle2D.Float(dx.floatValue(), dy.floatValue(), dw.floatValue(), dh.floatValue()));
			final float scaleX = dw.floatValue() / sw.floatValue();
			final float scaleY = dh.floatValue() / sh.floatValue();
			final float x0 = dx.floatValue() - sx.floatValue() * scaleX;
			final float y0 = dy.floatValue() - sy.floatValue() * scaleY;
			final AffineTransform at = new AffineTransform(scaleX, 0, 0, scaleY, x0, y0);
			graphics.drawImage(img, at, null);

			final HtmlRendererContext htmlRendererContext = hImage.getHtmlRendererContext();
			final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
			htmlPanel.getBrowserPanel().getTimingList.add(info);
		}
    }

    /** {@inheritDoc} */
    @Override
    public void fillText(String text, Integer x, Integer y, Integer maxWidth) {
		final Graphics2D graphics = createGraphics();
		graphics.setPaint((Paint)getFillStyle());
		graphics.setFont(getFont());
		graphics.rotate(rotate);
		graphics.scale(scaleX, scaleY);
		graphics.translate(translateX, translateY);
		final Point2D.Float f = calcTextPos(graphics, text, x, y);
		graphics.drawString(text, f.x, f.y);
    }

    /** {@inheritDoc} */
    @Override
    public void fillText(String text, Integer x, Integer y) {
		fillText(text, x, y, 0);
    }

    /** {@inheritDoc} */
    @Override
    public TextMetrics measureText(String text) {
		final Graphics2D graphics = createGraphics();
		final FontMetrics metrics = graphics.getFontMetrics(font);
		final Rectangle2D rect = metrics.getStringBounds(text, graphics);
		return new CanvasTextMetricsImpl(rect.getWidth(), rect.getHeight());
    }

    /** {@inheritDoc} */
    @Override
    public void strokeText(String text, Integer x, Integer y, Integer maxWidth) {
		final Graphics2D graphics = createGraphics();
		final FontRenderContext frc = new FontRenderContext(null, false, false);
		final TextLayout tl = new TextLayout(text, font, frc);
		final Point2D.Float pos = calcTextPos(graphics, text, x, y);
		final AffineTransform textAt = AffineTransform.getTranslateInstance(pos.x, pos.y);
		textAt.translate(x, y);
		final Shape outline = tl.getOutline(textAt);
		graphics.setPaint((Paint)getStrokeStyle());
		graphics.rotate(rotate);
		graphics.setStroke(new BasicStroke(2));
		graphics.scale(scaleX, scaleY);
		graphics.translate(translateX, translateY);
		graphics.draw(outline);
    }

    /** {@inheritDoc} */
    @Override
    public void strokeText(String text, Integer x, Integer y) {
		strokeText(text, x, y, 0);
    }

    /** {@inheritDoc} */
    @Override
    public CanvasGradient createConicGradient(Integer startAngle, Integer x, Integer y) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public CanvasGradient createLinearGradient(Integer x0, Integer y0, Integer x1, Integer y1) {
		return new CanvasGradientImpl(x0, y0, x1, y1);
    }

    /** {@inheritDoc} */
    @Override
    public CanvasPattern createPattern(CanvasImageSource image, String repetition) {
		return new CanvasPatternImpl(image, repetition);
    }

    /** {@inheritDoc} */
    @Override
    public CanvasGradient createRadialGradient(Integer x0, Integer y0, Integer r0, Integer x1, Integer y1, Integer r1) {
		return new CanvasGradientImpl(x0, y0, x1, y1, r0, r1);
    }

    /** {@inheritDoc} */
    @Override
    public void beginPath() {
		path = new GeneralPath();
    }

    /** {@inheritDoc} */
    @Override
    public void clip(String fillRule) {
        clip(path, fillRule);
    }

    /** {@inheritDoc} */
    @Override
    public void clip() {
        clip(path);
    }

    /** {@inheritDoc} */
    @Override
    public void clip(Path2D path, String fillRule) {
        setFillRule(fillRule);
        clip(path);
    }

    /** {@inheritDoc} */
    @Override
    public void clip(Path2D path) {
        final Graphics2D graphics = createGraphics();
        final AffineTransform t = graphics.getTransform();
        graphics.setTransform(new AffineTransform());
        graphics.setClip(path);
        graphics.setTransform(t);
    }

    /** {@inheritDoc} */
    @Override
    public void fill(String fillRule) {
		fill(path, fillRule);
	}

    /** {@inheritDoc} */
    @Override
    public void fill() {
        fill(path);
	}

    /** {@inheritDoc} */
    @Override
    public void fill(Path2D path, String fillRule) {
        setFillRule(fillRule);
        fill(path);
    }

    /** {@inheritDoc} */
    @Override
    public void fill(Path2D path) {
        final Graphics2D graphics = createGraphics();

        if (getShadowBlur() > 0) {
            shadow(graphics, path.getBounds().x, path.getBounds().y, path.getBounds().width, path.getBounds().height, true);
        }

        graphics.setPaint((Paint)getFillStyle());
        graphics.setTransform(affineTransform);
        graphics.fill(path);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPointInPath(Integer x, Integer y, String fillRule) {
        setFillRule(fillRule);
        return isPointInPath(x, y);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPointInPath(Integer x, Integer y) {
		return isPointInPath(path, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPointInPath(Path2D path, Integer x, Integer y, String fillRule) {
        setFillRule(fillRule);
        return isPointInPath(path, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPointInPath(Path2D path, Integer x, Integer y) {
        final Point2D p = new Point2D.Float(x.floatValue(), y.floatValue());
        return path.contains(p);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPointInStroke(Integer x, Integer y) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPointInStroke(Path2D path, Integer x, Integer y) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void stroke() {
        stroke(path);
    }

    /** {@inheritDoc} */
    @Override
    public void stroke(Path2D path) {
        final Graphics2D graphics = createGraphics();

        if (getShadowBlur() > 0) {
            shadow(graphics, path.getBounds().x, path.getBounds().y, path.getBounds().width, path.getBounds().height, false);
        }

        graphics.setStroke(new BasicStroke(lineWidth.floatValue(), intLineCap, intlineJoin, miterLimit.floatValue()));
        graphics.setPaint((Paint)getStrokeStyle());
        graphics.draw(path);
    }

    /** {@inheritDoc} */
    @Override
    public ImageData createImageData(Integer sw, Integer sh, ImageDataSettings settings) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public ImageData createImageData(Integer sw, Integer sh) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public ImageData createImageData(ImageData imagedata) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public ImageData getImageData(Integer sx, Integer sy, Integer sw, Integer sh, ImageDataSettings settings) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public ImageData getImageData(Integer sx, Integer sy, Integer sw, Integer sh) {
		return new ImageDataImpl(image, sw, sh);
    }

    /** {@inheritDoc} */
    @Override
    public void putImageData(ImageData imagedata, Integer dx, Integer dy) {
		putImageData(imagedata, dx, dy, 0, 0, imagedata.getWidth(), imagedata.getHeight());
    }

    /** {@inheritDoc} */
    @Override
    public void putImageData(ImageData imagedata, Integer dx, Integer dy, Integer dirtyX, Integer dirtyY, Integer dirtyWidth, Integer dirtyHeight) {
		final Graphics2D graphics = createGraphics();
		final BufferedImage image = (BufferedImage)imagedata.getData();
		graphics.drawImage(image, dx, dy, null);
    }

    /** {@inheritDoc} */
    @Override
    public void arc(Double x, Double y, Double radius, Integer startAngle, Double endAngle, boolean counterclockwise) {
		path.append(buildArc(x, y, radius, Double.valueOf(startAngle), endAngle, counterclockwise), true);
    }

    /** {@inheritDoc} */
    @Override
    public void arc(Double x, Double y, Double radius, Integer startAngle, Double endAngle) {
		path.append(buildArc(x, y, radius, (double)startAngle, endAngle, false), true);
    }

    /** {@inheritDoc} */
    @Override
    public void arcTo(Integer x1, Integer y1, Integer x2, Integer y2, Integer radius) {

    }

    /** {@inheritDoc} */
    @Override
    public void bezierCurveTo(Integer cp1x, Integer cp1y, Integer cp2x, Integer cp2y, Integer x, Integer y) {
		final Graphics2D graphics = createGraphics();
		final float[] xy = { cp1x.floatValue(), cp1y.floatValue(), cp2x.floatValue(), cp2y.floatValue(), x.floatValue(), y.floatValue() };
		graphics.getTransform().transform(xy, 0, xy, 0, 3);
		path.curveTo(xy[0], xy[1], xy[2], xy[3], xy[4], xy[5]);
    }

    /** {@inheritDoc} */
    @Override
    public void closePath() {
		path.closePath();
    }

    /** {@inheritDoc} */
    @Override
    public void ellipse(Integer x, Integer y, Integer radiusX, Integer radiusY, Integer rotation, Integer startAngle, Integer endAngle, boolean counterclockwise) {

    }

    /** {@inheritDoc} */
    @Override
    public void ellipse(Integer x, Integer y, Integer radiusX, Integer radiusY, Integer rotation, Integer startAngle, Integer endAngle) {

    }

    /** {@inheritDoc} */
    @Override
    public void lineTo(Integer x, Integer y) {
		path.lineTo(x, y);
    }

    /** {@inheritDoc} */
    @Override
    public void moveTo(Integer x, Integer y) {
		final Graphics2D graphics = createGraphics();
		final Point2D p = new Point2D.Float(x.floatValue(), y.floatValue());
		graphics.getTransform().transform(p, p);
		path.moveTo((float) p.getX(), (float) p.getY());
    }

    /** {@inheritDoc} */
    @Override
    public void quadraticCurveTo(Integer cpx, Integer cpy, Integer x, Integer y) {
		final Graphics2D graphics = createGraphics();
		final float[] xy = { cpx.floatValue(), cpy.floatValue(), x.floatValue(), y.floatValue() };
		graphics.getTransform().transform(xy, 0, xy, 0, 2);
		path.quadTo(xy[0], xy[1], xy[2], xy[3]);
    }

    /** {@inheritDoc} */
    @Override
    public void rect(Integer x, Integer y, Integer w, Integer h) {
		path.append(new Rectangle2D.Float(x, y, w, h), true);
    }

    /** {@inheritDoc} */
    @Override
    public void roundRect(Integer x, Integer y, Integer w, Integer h, Integer radii) {

    }

    /** {@inheritDoc} */
    @Override
    public void clearRect(Integer x, Integer y, Integer w, Integer h) {
		final Graphics2D graphics = createGraphics();
		graphics.clearRect(x, y, w, h);
    }

    /** {@inheritDoc} */
    @Override
    public void fillRect(Integer x, Integer y, Integer w, Integer h) {
		final Graphics2D graphics = createGraphics();

		if (getShadowBlur() > 0) {
			shadow(graphics, x, y, w, h, true);
		}

		graphics.setComposite(getComosite());
		graphics.setPaint((Paint)getFillStyle());
		graphics.rotate(rotate);
		graphics.scale(scaleX, scaleY);
		graphics.translate(translateX, translateY);
		graphics.transform(affineTransform);
		graphics.fillRect(x, y, w, h);
    }

    /** {@inheritDoc} */
    @Override
    public void strokeRect(Integer x, Integer y, Integer w, Integer h) {
		final Graphics2D graphics = createGraphics();

		if (getShadowBlur() > 0) {
			shadow(graphics, x, y, w, h, false);
		}

		graphics.setComposite(getComosite());
		graphics.setPaint((Paint)getStrokeStyle());
		graphics.rotate(rotate);
		graphics.setStroke(new BasicStroke(lineWidth.floatValue()));
		graphics.scale(scaleX, scaleY);
		graphics.translate(translateX, translateY);
		graphics.setTransform(affineTransform);
		graphics.drawRect(x, y, w, h);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isContextLost() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void reset() {

    }

    /** {@inheritDoc} */
    @Override
    public void restore() {

    }

    /** {@inheritDoc} */
    @Override
    public void save() {

    }

    /** {@inheritDoc} */
    @Override
    public List<Integer> getLineDash() {
        return List.of();
    }

    /** {@inheritDoc} */
    @Override
    public void setLineDash(List<Integer> segments) {

    }


	/** {@inheritDoc} */
	@Override
    public void setFillStyle(final Object style) {
        if (style instanceof CanvasGradient) {
            final CanvasGradientImpl cgi = (CanvasGradientImpl) style;
            fillStyle = cgi.gradient();
        } else if (style instanceof String) {
            Color clr = ColorFactory.getInstance().getColor(style.toString());
            fillStyle = clr != null ? clr : fillStyle;
        } else if (style instanceof CanvasPattern) {
            fillStyle = style;
        }
    }

	/** {@inheritDoc} */
	@Override
	public void setFont(final String fontSpec) {
		final HtmlRendererConfig config = canvas.getHtmlRendererConfig();
		final FontKey key = FontValues.getDefaultFontKey(config);
		key.setFontStyle(LAFType.ITALIC.getValue());
		key.setFontVariant(CSSValues.SMALL_CAPS.getValue());
		key.setFontWeight(LAFType.BOLD.getValue());

		final String[] tokens = HtmlValues.splitCssValue(fontSpec);
		String token = null;
		final int length = tokens.length;
		int i;
		for (i = 0; i < length; i++) {
			token = tokens[i];
			if (FontValues.isFontStyle(token)) {
				key.setFontStyle(token);
				continue;
			}
			if (FontValues.isFontVariant(token)) {
				key.setFontVariant(token);
				continue;
			}
			if (FontValues.isFontWeight(token)) {
				key.setFontWeight(token);
				continue;
			}
			break;
		}
		if (token != null) {
			final int slashIdx = token.indexOf('/');
			final String fontSizeText = slashIdx == -1 ? token : token.substring(0, slashIdx);
			final HTMLDocumentImpl doc =  (HTMLDocumentImpl)this.canvas.getDocumentNode();
			final int errorValue = Float.valueOf(doc.getConfig().getFontSize()).intValue();
			key.setFontSize(HtmlValues.getPixelSize(fontSizeText, null, doc.getDefaultView(), errorValue));
			if (++i < length) {
				final StringBuilder fontFamilyBuff = new StringBuilder();
				do {
					token = tokens[i];
					fontFamilyBuff.append(token);
					fontFamilyBuff.append(' ');
				} while (++i < length);
				key.setFontFamily(fontFamilyBuff.toString());
			}
		}
		this.font = FontFactory.getInstance().getFont(key);
	}

	/** {@inheritDoc} */
	@Override
	public void setGlobalAlpha(final Float globalAlpha) {
		if (globalAlpha != null && globalAlpha >= 0 && globalAlpha <= 1) {
			final Graphics2D graphics = createGraphics();
			final AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, globalAlpha);
			graphics.setComposite(composite);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setLineCap(final String lineCap) {
		switch (lineCap) {
			case "round":
				intLineCap = BasicStroke.CAP_ROUND;
				break;
			case "square":
				intLineCap = BasicStroke.CAP_SQUARE;
				break;
			default:
				intLineCap = BasicStroke.CAP_BUTT;
				break;
		}
	}


	/** {@inheritDoc} */
	@Override
	public void setLineJoin(final String lineJoin) {
		switch (lineJoin) {
			case "round":
				intlineJoin = BasicStroke.JOIN_ROUND;
				break;
			case "miter":
				intlineJoin = BasicStroke.JOIN_MITER;
				break;
			default:
				intlineJoin = BasicStroke.JOIN_BEVEL;
				break;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setStrokeStyle(final Object style) {
		if (style instanceof CanvasGradient) {
			final CanvasGradientImpl cgi = (CanvasGradientImpl) style;
			strokeStyle = cgi.gradient();
		} else if (style instanceof String) {
            Color clr = ColorFactory.getInstance().getColor(style.toString());
            strokeStyle = clr != null ? clr : fillStyle;
		} else if (style instanceof CanvasPattern) {
			strokeStyle = style;
		}
	}

	/** {@inheritDoc} */
    @Override
    public void drawFocusIfNeeded(Element element) {

    }

    /** {@inheritDoc} */
    @Override
    public void drawFocusIfNeeded(Path2D path, Element element) {

    }

    /** {@inheritDoc} */
    @Override
    public void scrollPathIntoView() {

    }

    /** {@inheritDoc} */
    @Override
    public void scrollPathIntoView(Path2D path) {

    }

    /** {@inheritDoc} */
    @Override
    public DOMMatrix getTransform() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public void setTransform(DOMMatrix2DInit transform) {

    }

    /** {@inheritDoc} */
    @Override
    public void resetTransform() {
        affineTransform = new AffineTransform(1, 0, 0, 1, 0, 0);
    }

    /** {@inheritDoc} */
    @Override
    public void rotate(Double angle) {
        setRotate(angle);
    }

    /** {@inheritDoc} */
    @Override
    public void scale(Integer x, Integer y) {
		scaleX = x;
		scaleY = y;
    }

    /** {@inheritDoc} */
    @Override
    public void setTransform(Integer a, Integer b, Integer c, Integer d, Integer e, Integer f) {
		transform(a, b, c, d, e, f);
    }

    /** {@inheritDoc} */
    @Override
    public void transform(Integer a, Integer b, Integer c, Integer d, Integer e, Integer f) {
		affineTransform = new AffineTransform(a, b, c, d, e, f);
    }

    /** {@inheritDoc} */
    @Override
	public void translate(final Integer tx, final Integer ty) {
		translateX = tx;
		translateY = ty;
	}

	private Graphics2D createGraphics() {
		final Graphics2D createGraphics = image.createGraphics();
		createGraphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		createGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		createGraphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		createGraphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		createGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		createGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		createGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		createGraphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		return createGraphics;
	}

	private AlphaComposite getComosite() {
		final Integer alphaInteger = switch (globalCompositeOperation) {
			case "source-atop" -> AlphaComposite.SRC_ATOP;
			case "source-in" -> AlphaComposite.SRC_IN;
			case "source-out" -> AlphaComposite.SRC_OUT;
			case "destination-atop" -> AlphaComposite.DST_ATOP;
			case "destination-in" -> AlphaComposite.DST_IN;
			case "destination-out" -> AlphaComposite.DST_OUT;
			case "destination-over" -> AlphaComposite.DST_OVER;
			case "xor" -> AlphaComposite.XOR;
			case "over" -> AlphaComposite.CLEAR;
			default -> AlphaComposite.SRC_OVER;
		};
		return AlphaComposite.getInstance(alphaInteger, globalAlpha);
	}

	private Point2D.Float calcTextPos(final Graphics2D graphics, final String text, final Integer xTextPos, final Integer yTextPos) {
		final FontMetrics metrics = graphics.getFontMetrics();
		Integer x = xTextPos;
		Integer y = yTextPos;

		if ("center".equals(textAlign)) {
			x = x - metrics.stringWidth(text) / 2;
		} else if ("right".equals(textAlign)) {
			x = x - metrics.stringWidth(text);
		}

		y = switch (textBaseline) {
			case "baseline" -> y - metrics.getLeading() + metrics.getAscent();
			case "top" -> y - metrics.getLeading();
			case "middle" -> y - metrics.getLeading() - metrics.getAscent() / 2;
			case "bottom", "text-bottom" -> y - metrics.getHeight();
			default -> y + metrics.getLeading() + metrics.getAscent();
		};
		return new Point2D.Float(x, y);
	}

	private Arc2D.Double buildArc(final Double x, final Double y, final Double radius, final Double startAngleArc, final Double endAngleArc, final boolean anticlockwise) {
		final boolean clockwise = !anticlockwise;
		final Double twopi = 2 * Math.PI;

        Double startAngle = startAngleArc;
        Double endAngle = endAngleArc;

		while (startAngle < 0) {
			startAngle = startAngle + twopi;
		}
		while (startAngle > twopi) {
			startAngle = startAngle - twopi;
		}

		while (endAngle < 0) {
			endAngle = endAngle + twopi;
		}
		while (endAngle > twopi) {
			endAngle = endAngle - twopi;
		}

		if (clockwise) {
			if (startAngle > endAngle) {
				endAngle = endAngle + twopi;
			}

		} else {
			if (startAngle < endAngle) {
				endAngle = endAngle - twopi;
			}
		}

        double ang = startAngle - endAngle;
		if (ang == 0.0) {
			ang = Math.PI * 2;
		}

		startAngle = -startAngle;
		return new Arc2D.Double(x - radius, y - radius, 2 * radius, 2 * radius, Math.toDegrees(startAngle), Math.toDegrees(ang), Arc2D.OPEN);
	}

	private void shadow(final Graphics2D graphics, final Integer x1, final Integer y1, final Integer width1, final Integer height1, final boolean isFill) {
		final Color shadowColor = ColorFactory.getInstance().getColor(getShadowColor());
		final Color shadowColorA = new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), 150);
		final int x = x1 + getShadowOffsetX();
		final int y = y1 + getShadowOffsetY();
		final int strokeSize = getShadowBlur();
		final int width = width1 + strokeSize;
		final int height = height1 + strokeSize;
		graphics.setColor(shadowColorA);
		if (isFill) {
			graphics.fillRoundRect(x, y, width, height, 0, 0);
		} else {
			graphics.drawRect(x, y, width, height);
		}
	}
}
