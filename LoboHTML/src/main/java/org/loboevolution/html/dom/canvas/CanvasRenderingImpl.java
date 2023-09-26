/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import org.loboevolution.common.Strings;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.*;
import org.loboevolution.html.dom.domimpl.HTMLCanvasElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLImageElementImpl;
import org.loboevolution.gui.HtmlPanel;
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

/**
 * <p>CanvasRenderingImpl class.</p>
 */
public class CanvasRenderingImpl implements CanvasRenderingContext2D {
	
	/** The canvas. */
	private final HTMLCanvasElementImpl canvas;
	
	/** The image. */
	private final BufferedImage image;
	
    /** The fill style. */
    private Object fillStyle;

    /** The stroke style. */
    private Object strokeStyle;

    /** The line width. */
    private int lineWidth;

    /** The global alpha. */
    private final Float globalAlpha;

    /** The translate x. */
    private int translateX;

    /** The translate y. */
    private int translateY;

    /** The rotate. */
    private Double rotate;

    /** The scale x. */
    private int scaleX;

    /** The scale y. */
    private int scaleY;

    /** The font. */
    private Font font;

    /** The int line cap. */
    private int intLineCap;

    /** The intline join. */
    private int intlineJoin;

    /** The miter limit. */
    private int miterLimit;

    /** The path. */
    private GeneralPath path;

    /** The Affine Transform. */
    private AffineTransform affineTransform;

    /** The global Composite Operation. */
    private String globalCompositeOperation;

    /** The text align */
    private String textAlign;

    /** The baseline. */
    private String baseline;
    
    /** The shadow blur*/
    private Integer shadowBlur;
    
    /** The shadow color*/
    private String shadowColor;
    
    /** The shadow offset x*/
    private Integer shadowOffsetX;
    
    /** The shadow offset y*/
    private Integer shadowOffsetY;

	/**
	 * <p>Constructor for CanvasRenderingImpl.</p>
	 *
	 * @param canvas a {@link org.loboevolution.html.dom.domimpl.HTMLCanvasElementImpl} object.
	 */
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
        rotate = 0.0;
        scaleX = 1;
        scaleY = 1;
        miterLimit = 1;
        globalCompositeOperation = "source-over";
        textAlign = "left";
        baseline = "alphabetic";
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCanvasElement getCanvas() {
		return this.canvas;
	}

	/** {@inheritDoc} */
	@Override
	public Object getFillStyle() {
		return fillStyle;
	}

	/** {@inheritDoc} */
	@Override
	public void setFillStyle(final Object style) {
		if (style instanceof CanvasGradient) {
			final CanvasGradientImpl cgi = (CanvasGradientImpl) style;
			fillStyle = cgi.gradient();
		} else if (style instanceof String) {
			fillStyle = ColorFactory.getInstance().getColor(style.toString());
		} else if (style instanceof CanvasPattern) {
			fillStyle = style;
		}
	}

	/** {@inheritDoc} */
	@Override
	public Font getFont() {
		if (this.font == null) {
			final HtmlRendererConfig config = canvas.getHtmlRendererConfig();
			final FontFactory fontFactory = FontFactory.getInstance();
			final Font font = fontFactory.getFont(FontValues.getDefaultFontKey(config));
			this.font = font;
		}
		return this.font;
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
	public Double getGlobalAlpha() {
		return globalAlpha.doubleValue();
	}

	/** {@inheritDoc} */
	@Override
	public void setGlobalAlpha(final Double globalAlpha) {
		if (globalAlpha >= 0 && globalAlpha <= 1) {
			final Graphics2D graphics = createGraphics();
            final AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, globalAlpha.floatValue());
            graphics.setComposite(composite);
        }
	}

	/** {@inheritDoc} */
	@Override
	public String getGlobalCompositeOperation() {
		return globalCompositeOperation;
	}

	/** {@inheritDoc} */
	@Override
	public void setGlobalCompositeOperation(final String op) {
		globalCompositeOperation = op;

	}

	/** {@inheritDoc} */
	@Override
	public String getLineCap() {
		switch (intLineCap) {
		case BasicStroke.CAP_ROUND:
			 return "round";
		case BasicStroke.CAP_SQUARE:
			return "square";
		default:
			return "butt";
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
	public String getLineJoin() {
		switch (intlineJoin) {
		case BasicStroke.JOIN_ROUND:
			 return "round";
		case BasicStroke.JOIN_MITER:
			return "miter";
		default:
			return "bevel";
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
	public int getLineWidth() {
		return lineWidth;
	}

	/** {@inheritDoc} */
	@Override
	public void setLineWidth(final int lw) {
		lineWidth = lw;
	}

	/** {@inheritDoc} */
	@Override
	public int getMiterLimit() {
		return miterLimit;
	}

	/** {@inheritDoc} */
	@Override
	public void setMiterLimit(final int ml) {
		miterLimit = ml;
	}

	/** {@inheritDoc} */
	@Override
	public int getShadowBlur() {
		return shadowBlur == null ? 0 : shadowBlur;
	}

	/** {@inheritDoc} */
	@Override
	public void setShadowBlur(final int shadowBlur) {
		this.shadowBlur = shadowBlur;
	}

	/** {@inheritDoc} */
	@Override
	public String getShadowColor() {
		return Strings.isBlank(shadowColor) ? "black" : shadowColor;
	}

	/** {@inheritDoc} */
	@Override
	public void setShadowColor(final String shadowColor) {
		this.shadowColor = shadowColor;
	}

	/** {@inheritDoc} */
	@Override
	public int getShadowOffsetX() {
		return shadowOffsetX == null ? 0 : shadowOffsetX;
	}

	/** {@inheritDoc} */
	@Override
	public void setShadowOffsetX(final int shadowOffsetX) {
		this.shadowOffsetX = shadowOffsetX;
	}

	/** {@inheritDoc} */
	@Override
	public int getShadowOffsetY() {
		return shadowOffsetY == null ? 0 : shadowOffsetY;
	}

	/** {@inheritDoc} */
	@Override
	public void setShadowOffsetY(final int shadowOffsetY) {
		this.shadowOffsetY = shadowOffsetY;
	}

	/** {@inheritDoc} */
	@Override
	public Object getStrokeStyle() {
		return strokeStyle;
	}

	/** {@inheritDoc} */
	@Override
	public void setStrokeStyle(final Object style) {
		if (style instanceof CanvasGradient) {
			final CanvasGradientImpl cgi = (CanvasGradientImpl) style;
			strokeStyle = cgi.gradient();
		} else if (style instanceof String) {
			strokeStyle = ColorFactory.getInstance().getColor(style.toString());
		} else if (style instanceof CanvasPattern) {
			strokeStyle = style;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getTextAlign() {
		return textAlign;
	}

	/** {@inheritDoc} */
	@Override
	public void setTextAlign(final String textAlign) {
		this.textAlign = textAlign;
	}

	/** {@inheritDoc} */
	@Override
	public String getTextBaseline() {
		return baseline;
	}

	/** {@inheritDoc} */
	@Override
	public void setTextBaseline(final String bs) {
		baseline = bs;
	}

	/** {@inheritDoc} */
	@Override
	public void arc(final int x, final int y, final int radius, final int startAngle, final int endAngle) {
		path.append(buildArc(x, y, radius, startAngle, endAngle, false), true);

	}

	/** {@inheritDoc} */
	@Override
	public void arc(final int x, final int y, final int radius, final int startAngle, final int endAngle, final boolean anticlockwise) {
		path.append(buildArc(x, y, radius, startAngle, endAngle, anticlockwise), true);
	}

	/** {@inheritDoc} */
	@Override
	public void arcTo(final int x1, final int y1, final int x2, final int y2, final int radius) {
		// TODO Auto-generated method stub

	}
	
	/** {@inheritDoc} */
	@Override
	public void ellipse(final int x, final int y, final int radiusX, final int radiusY, final int rotation, final int startAngle, final int endAngle) {
		// TODO Auto-generated method stub
	}
	
	/** {@inheritDoc} */
	@Override
	public void ellipse(final int x, final int y, final int radiusX, final int radiusY, final int rotation, final int startAngle, final int endAngle, final boolean anticlockwise) {
		// TODO Auto-generated method stub
	}

	/** {@inheritDoc} */
	@Override
	public void beginPath() {
		path = new GeneralPath();
	}

	/** {@inheritDoc} */
	@Override
	public void bezierCurveTo(final int cp1x, final int cp1y, final int cp2x, final int cp2y, final int x, final int y) {
		final Graphics2D graphics = createGraphics();
		final float[] xy = { cp1x, cp1y, cp2x, cp2y, x, y };
		graphics.getTransform().transform(xy, 0, xy, 0, 3);
		path.curveTo(xy[0], xy[1], xy[2], xy[3], xy[4], xy[5]);
	}

	/** {@inheritDoc} */
	@Override
	public void clearRect(final int x, final int y, final int width, final int height) {
		final Graphics2D graphics = createGraphics();
		graphics.clearRect(x, y, width, height);

	}

	/** {@inheritDoc} */
	@Override
	public void clearShadow() {
		setShadowBlur(0);
	}

	/** {@inheritDoc} */
	@Override
	public void clip() {
		final Graphics2D graphics = createGraphics();
		final AffineTransform t = graphics.getTransform();
		graphics.setTransform(new AffineTransform());
		graphics.setClip(path);
		graphics.setTransform(t);
	}

	/** {@inheritDoc} */
	@Override
	public void closePath() {
		path.closePath();
	}

	/** {@inheritDoc} */
	@Override
	public CanvasGradient createLinearGradient(final Object x0, final Object y0, final Object x1, final Object y1) {
		return new CanvasGradientImpl(x0, y0, x1, y1);
	}

	/** {@inheritDoc} */
	@Override
	public CanvasPattern createPattern(final HTMLCanvasElement canvas, final String repetitionType) {
		return new CanvasPatternImpl(canvas, repetitionType);
	}

	/** {@inheritDoc} */
	@Override
	public CanvasPattern createPattern(final HTMLImageElement image, final String repetitionType) {
		return new CanvasPatternImpl(image, repetitionType);
	}

	/** {@inheritDoc} */
	@Override
	public CanvasGradient createRadialGradient(final Object x0, final Object y0, final Object r0, final Object x1, final Object y1, final Object r1) {
		return new CanvasGradientImpl(x0, y0, x1, y1, r0, r1);
	}

	/** {@inheritDoc} */
	@Override
	public void drawImage(final Object oImage, final Integer x, final Integer y) {
		if (oImage instanceof HTMLImageElementImpl) {
			final HTMLImageElementImpl hImage = (HTMLImageElementImpl)oImage;
			final TimingInfo info = new TimingInfo();
			final Image image = HttpNetwork.getImage(hImage, info, false);
			final Graphics2D graphics = createGraphics();
			final AffineTransform at = new AffineTransform();
			at.setToTranslation(x, y);
			graphics.drawImage(image, at, null);

			final HtmlRendererContext htmlRendererContext = hImage.getHtmlRendererContext();
			final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
			htmlPanel.getBrowserPanel().getTimingList.add(info);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void drawImage(final Object oImage, final Integer x, final Integer y, final Integer width, final Integer height) {
		if (oImage instanceof HTMLImageElementImpl) {
			final HTMLImageElementImpl hImage = (HTMLImageElementImpl)oImage;
			final TimingInfo info = new TimingInfo();
			final Image image = HttpNetwork.getImage(hImage, info, false);
			final Graphics2D graphics = createGraphics();
			final AffineTransform at = new AffineTransform(width / image.getWidth(null), 0, 0, height / image.getHeight(null), x, y);
			graphics.drawImage(image, at, null);

			final HtmlRendererContext htmlRendererContext = hImage.getHtmlRendererContext();
			final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
			htmlPanel.getBrowserPanel().getTimingList.add(info);

		}
	}

	/** {@inheritDoc} */
	@Override
	public void drawImage(final Object oImage, final Integer sx, final Integer sy, final Integer sw, final Integer sh, final Integer dx, final Integer dy, final Integer dw, final Integer dh) {
		if (oImage instanceof HTMLImageElementImpl) {
			final HTMLImageElementImpl hImage = (HTMLImageElementImpl)oImage;
			final TimingInfo info = new TimingInfo();
			final Image image = HttpNetwork.getImage(hImage, info, false);
			final Graphics2D graphics = createGraphics();
			graphics.clip(new Rectangle2D.Float(dx, dy, dw, dh));
			final float scaleX = dw / sw;
			final float scaleY = dh / sh;
			final float x0 = dx - sx * scaleX;
			final float y0 = dy - sy * scaleY;
			final AffineTransform at = new AffineTransform(scaleX, 0, 0, scaleY, x0, y0);
			graphics.drawImage(image, at, null);

			final HtmlRendererContext htmlRendererContext = hImage.getHtmlRendererContext();
			final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
			htmlPanel.getBrowserPanel().getTimingList.add(info);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void fill() {
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
	public void fillRect(final int x, final int y, final int width, final int height) {
		final Graphics2D graphics = createGraphics();
		
		if (getShadowBlur() > 0) {
			shadow(graphics, x, y, width, height, true);
		}

		graphics.setComposite(getComosite());
		graphics.setPaint((Paint)getFillStyle());
		graphics.rotate(rotate);
		graphics.scale(scaleX, scaleY);
		graphics.translate(translateX, translateY);
		graphics.transform(affineTransform);
		graphics.fillRect(x, y, width, height);
	}
	
	/** {@inheritDoc} */
	@Override
	public void fillText(final String text, final int x, final int y) {
		fillText(text, x, y, 0);

	}

	/** {@inheritDoc} */
	@Override
	public void fillText(final String text, final int x, final int y, final int maxWidth) {
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
	public ImageData getImageData(final int sx, final int sy, final int sw, final int sh) {
		return new ImageDataImpl(image, sw, sh);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isPointInPath(final int x, final int y) {
		final Point2D p = new Point2D.Float(x, y);
		return path.contains(p);
	}

	/** {@inheritDoc} */
	@Override
	public void lineTo(final int x, final int y) {
		path.lineTo(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public TextMetrics measureText(final String text) {
		final Graphics2D graphics = createGraphics();
		final FontMetrics metrics = graphics.getFontMetrics(font);
		final Rectangle2D rect = metrics.getStringBounds(text, graphics);
		return new CanvasTextMetricsImpl(rect.getWidth(), rect.getHeight());
	}

	/** {@inheritDoc} */
	@Override
	public void moveTo(final int x, final int y) {
		final Graphics2D graphics = createGraphics();
		final Point2D p = new Point2D.Float(x, y);
		graphics.getTransform().transform(p, p);
		path.moveTo((float) p.getX(), (float) p.getY());
	}

	/** {@inheritDoc} */
	@Override
	public void putImageData(final ImageData imagedata, final int dx, final int dy) {
		putImageData(imagedata, dx, dy, 0, 0, imagedata.getWidth(), imagedata.getHeight());
	}

	/** {@inheritDoc} */
	@Override
	public void putImageData(final ImageData imagedata, final int dx, final int dy, final int dirtyX, final int dirtyY, final int dirtyWidth, final int dirtyHeight) {
		final Graphics2D graphics = createGraphics();
		final BufferedImage image = (BufferedImage)imagedata.getData();
		graphics.drawImage(image, dx, dy, null);
    }

	/** {@inheritDoc} */
	@Override
	public void quadraticCurveTo(final int cpx, final int cpy, final int x, final int y) {
		final Graphics2D graphics = createGraphics();
		final float[] xy = { cpx, cpy, x, y };
		graphics.getTransform().transform(xy, 0, xy, 0, 2);
		path.quadTo(xy[0], xy[1], xy[2], xy[3]);
	}

	/** {@inheritDoc} */
	@Override
	public void rect(final int x, final int y, final int width, final int height) {
		path.append(new Rectangle2D.Double(x, y, width, height), true);
	}

	/** {@inheritDoc} */
	@Override
	public void restore() {
		// TODO Auto-generated method stub
	}

	/** {@inheritDoc} */
	@Override
	public void rotate(final double angle) {
		rotate = angle;
	}

	/** {@inheritDoc} */
	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public void scale(final int sx, final int sy) {
		scaleX = sx;
		scaleY = sy;
	}

	/** {@inheritDoc} */
	@Override
	public void setTransform(final Double m11, final Double m12, final Double m21, final Double m22, final Double dx, final Double dy) {
		transform(m11, m12, m21, m22, dx, dy);
	}

	/** {@inheritDoc} */
	@Override
	public void stroke() {
		final Graphics2D graphics = createGraphics();
		
		if (getShadowBlur() > 0) {
			shadow(graphics, path.getBounds().x, path.getBounds().y, path.getBounds().width, path.getBounds().height, false);
		}
		
		graphics.setStroke(new BasicStroke(lineWidth, intLineCap, intlineJoin, miterLimit));
		graphics.setPaint((Paint)getStrokeStyle());
		graphics.draw(path);
	}

	/** {@inheritDoc} */
	@Override
	public void strokeRect(final int x, final int y, final int width, final int height) {
		strokeRect(x, y, width, height, lineWidth);
	}

	/** {@inheritDoc} */
	@Override
	public void strokeRect(final int x, final int y, final int width, final int height, final int lineWidth) {
		final Graphics2D graphics = createGraphics();
		
		if (getShadowBlur() > 0) {
			shadow(graphics, x, y, width, height, false);
		}

		graphics.setComposite(getComosite());
		graphics.setPaint((Paint)getStrokeStyle());
		graphics.rotate(rotate);
		graphics.setStroke(new BasicStroke(lineWidth));
		graphics.scale(scaleX, scaleY);
		graphics.translate(translateX, translateY);
		graphics.setTransform(affineTransform);
		graphics.drawRect(x, y, width, height);

	}

	/** {@inheritDoc} */
	@Override
	public void strokeText(final String text, final int x, final int y) {
		strokeText(text, x, y, 0);
	}

	/** {@inheritDoc} */
	@Override
	public void strokeText(final String text, final int x, final int y, final int maxWidth) {
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
	public void transform(final Double m11, final Double m12, final Double m21, final Double m22, final Double dx, final Double dy) {
		affineTransform = new AffineTransform(m11, m12, m21, m22, dx, dy);
	}

	/** {@inheritDoc} */
	@Override
	public void translate(final int tx, final int ty) {
		translateX = tx;
		translateY = ty;
	}
	
	/**
	 * <p>Getter for the field image.</p>
	 *
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
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
		final int alphaInt;
		switch (globalCompositeOperation) {
		case "source-atop":
			alphaInt = AlphaComposite.SRC_ATOP;
			break;
		case "source-in":
			alphaInt = AlphaComposite.SRC_IN;
			break;
		case "source-out":
			alphaInt = AlphaComposite.SRC_OUT;
			break;
		case "destination-atop":
			alphaInt = AlphaComposite.DST_ATOP;
			break;
		case "destination-in":
			alphaInt = AlphaComposite.DST_IN;
			break;
		case "destination-out":
			alphaInt = AlphaComposite.DST_OUT;
			break;
		case "destination-over":
			alphaInt = AlphaComposite.DST_OVER;
			break;
		case "xor":
			alphaInt = AlphaComposite.XOR;
			break;
		case "over":
			alphaInt = AlphaComposite.CLEAR;
			break;
		default:
			alphaInt = AlphaComposite.SRC_OVER;
			break;
		}
		return AlphaComposite.getInstance(alphaInt, globalAlpha);
	}
	
	private Point2D.Float calcTextPos(final Graphics2D graphics, final String text, final int xTextPos, final int yTextPos) {
		final FontMetrics metrics = graphics.getFontMetrics();
		int x = xTextPos;
		int y = yTextPos;
		
		if ("center".equals(textAlign)) {
			x = x - metrics.stringWidth(text) / 2;
		} else if ("right".equals(textAlign)) {
			x = x - metrics.stringWidth(text);
		}
		
		switch (baseline) {
		case "baseline":
			y = y - metrics.getLeading() + metrics.getAscent();
			break;
		case "top":
			y = y - metrics.getLeading();
			break;
		case "middle":
			y = y - metrics.getLeading() - metrics.getAscent() / 2;
			break;
		case "bottom":
		case "text-bottom":
			y = y - metrics.getHeight();
			break;
		default:
			y = y + metrics.getLeading() + metrics.getAscent();
			break;
		}		
		return new Point2D.Float(x, y);
	}
	
	private Arc2D.Double buildArc(final int x, final int y, final int radius, final int startAngleArc, final int endAngleArc, final boolean anticlockwise) {
		final boolean clockwise = !anticlockwise;
		final double twopi = 2 * Math.PI;

		int startAngle = startAngleArc;
		int endAngle = endAngleArc;

		while (startAngle < 0) {
			startAngle = (int) (startAngle + twopi);
		}
		while (startAngle > twopi) {
			startAngle = (int) (startAngle - twopi);
		}

		while (endAngle < 0) {
			endAngle = (int) (endAngle + twopi);
		}
		while (endAngle > twopi) {
			endAngle = (int) (endAngle - twopi);
		}

		if (clockwise) {
			if (startAngle > endAngle) {
				endAngle = (int) (endAngle + twopi);
			}

		} else {
			if (startAngle < endAngle) {
				endAngle = (int) (endAngle - twopi);
			}
		}

		double ang = startAngle - endAngle;
		if (ang == 0.0) {
			ang = Math.PI * 2;
		}

		startAngle = -startAngle;
		return new Arc2D.Double(x - radius, y - radius, 2 * radius, 2 * radius, Math.toDegrees(startAngle), Math.toDegrees(ang), Arc2D.OPEN);
	}

	private void shadow(final Graphics2D graphics, final int x1, final int y1, final int width1, final int height1, final boolean isFill) {
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
