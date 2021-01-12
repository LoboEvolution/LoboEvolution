/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.dom.canvas;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.CanvasGradient;
import org.loboevolution.html.dom.CanvasPattern;
import org.loboevolution.html.dom.CanvasRenderingContext2D;
import org.loboevolution.html.dom.HTMLCanvasElement;
import org.loboevolution.html.dom.HTMLImageElement;
import org.loboevolution.html.dom.ImageData;
import org.loboevolution.html.dom.TextMetrics;
import org.loboevolution.html.dom.domimpl.HTMLCanvasElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.laf.ColorFactory;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;
import org.loboevolution.laf.LAFSettings;
import org.loboevolution.laf.LAFType;
import org.loboevolution.net.HttpNetwork;

/**
 * <p>CanvasRenderingImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
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
	public CanvasRenderingImpl(HTMLCanvasElementImpl canvas) {
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
	public void setFillStyle(Object style) {
		if (style instanceof CanvasGradient) {
			CanvasGradientImpl cgi = (CanvasGradientImpl) style;
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
			final FontFactory fontFactory = FontFactory.getInstance();
			final Font font = fontFactory.getFont(new FontKey());
			this.font = font;
		}
		return this.font;
	}

	/** {@inheritDoc} */
	@Override
	public void setFont(String fontSpec) {
		FontKey key = new FontKey();
		key.setFontFamily(Font.SANS_SERIF);
		key.setFontStyle(LAFType.ITALIC.getValue());
		key.setFontVariant(CSSValues.SMALL_CAPS.getValue());
		key.setFontWeight(LAFType.BOLD.getValue());
		key.setFontSize(new LAFSettings().getInstance().getFontSize());

		final String[] tokens = HtmlValues.splitCssValue(fontSpec);
		String token = null;
		final int length = tokens.length;
		int i;
		for (i = 0; i < length; i++) {
			token = tokens[i];
			if (HtmlValues.isFontStyle(token)) {
				key.setFontStyle(token);
				continue;
			}
			if (HtmlValues.isFontVariant(token)) {
				key.setFontVariant(token);
				continue;
			}
			if (HtmlValues.isFontWeight(token)) {
				key.setFontWeight(token);
				continue;
			}
			break;
		}
		if (token != null) {
			final int slashIdx = token.indexOf('/');
			final String fontSizeText = slashIdx == -1 ? token : token.substring(0, slashIdx);
			int errorValue = Float.valueOf(new LAFSettings().getInstance().getFontSize()).intValue();
			key.setFontSize(HtmlValues.getPixelSize(fontSizeText, null, errorValue));
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
	public void setGlobalAlpha(Double globalAlpha) {
		if (globalAlpha >= 0 && globalAlpha <= 1) {
			Graphics2D graphics = createGraphics();
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
	public void setGlobalCompositeOperation(String op) {
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
	public void setLineCap(String lineCap) {
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
	public void setLineJoin(String lineJoin) {
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
	public void setLineWidth(int lw) {
		lineWidth = lw;
	}

	/** {@inheritDoc} */
	@Override
	public int getMiterLimit() {
		return miterLimit;
	}

	/** {@inheritDoc} */
	@Override
	public void setMiterLimit(int ml) {
		miterLimit = ml;
	}

	/** {@inheritDoc} */
	@Override
	public int getShadowBlur() {
		return shadowBlur == null ? 0 : shadowBlur;
	}

	/** {@inheritDoc} */
	@Override
	public void setShadowBlur(int shadowBlur) {
		this.shadowBlur = shadowBlur;
	}

	/** {@inheritDoc} */
	@Override
	public String getShadowColor() {
		return Strings.isBlank(shadowColor) ? "black" : shadowColor;
	}

	/** {@inheritDoc} */
	@Override
	public void setShadowColor(String shadowColor) {
		this.shadowColor = shadowColor;
	}

	/** {@inheritDoc} */
	@Override
	public int getShadowOffsetX() {
		return shadowOffsetX == null ? 0 : shadowOffsetX;
	}

	/** {@inheritDoc} */
	@Override
	public void setShadowOffsetX(int shadowOffsetX) {
		this.shadowOffsetX = shadowOffsetX;
	}

	/** {@inheritDoc} */
	@Override
	public int getShadowOffsetY() {
		return shadowOffsetY == null ? 0 : shadowOffsetY;
	}

	/** {@inheritDoc} */
	@Override
	public void setShadowOffsetY(int shadowOffsetY) {
		this.shadowOffsetY = shadowOffsetY;
	}

	/** {@inheritDoc} */
	@Override
	public Object getStrokeStyle() {
		return strokeStyle;
	}

	/** {@inheritDoc} */
	@Override
	public void setStrokeStyle(Object style) {
		if (style instanceof CanvasGradient) {
			CanvasGradientImpl cgi = (CanvasGradientImpl) style;
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
	public void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}

	/** {@inheritDoc} */
	@Override
	public String getTextBaseline() {
		return baseline;
	}

	/** {@inheritDoc} */
	@Override
	public void setTextBaseline(String bs) {
		baseline = bs;
	}

	/** {@inheritDoc} */
	@Override
	public void arc(int x, int y, int radius, int startAngle, int endAngle) {
		path.append(buildArc(x, y, radius, startAngle, endAngle, false), true);

	}

	/** {@inheritDoc} */
	@Override
	public void arc(int x, int y, int radius, int startAngle, int endAngle, boolean anticlockwise) {
		path.append(buildArc(x, y, radius, startAngle, endAngle, anticlockwise), true);
	}

	/** {@inheritDoc} */
	@Override
	public void arcTo(int x1, int y1, int x2, int y2, int radius) {
		// TODO Auto-generated method stub

	}
	
	/** {@inheritDoc} */
	@Override
	public void ellipse(int x, int y, int radiusX, int radiusY, int rotation, int startAngle, int endAngle) {
		// TODO Auto-generated method stub
	}
	
	/** {@inheritDoc} */
	@Override
	public void ellipse(int x, int y, int radiusX, int radiusY, int rotation, int startAngle, int endAngle, boolean anticlockwise) {
		// TODO Auto-generated method stub
	}

	/** {@inheritDoc} */
	@Override
	public void beginPath() {
		path = new GeneralPath();
	}

	/** {@inheritDoc} */
	@Override
	public void bezierCurveTo(int cp1x, int cp1y, int cp2x, int cp2y, int x, int y) {
		Graphics2D graphics = createGraphics();
		float[] xy = { cp1x, cp1y, cp2x, cp2y, x, y };
		graphics.getTransform().transform(xy, 0, xy, 0, 3);
		path.curveTo(xy[0], xy[1], xy[2], xy[3], xy[4], xy[5]);
	}

	/** {@inheritDoc} */
	@Override
	public void clearRect(int x, int y, int width, int height) {
		Graphics2D graphics = createGraphics();
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
		Graphics2D graphics = createGraphics();
		AffineTransform t = graphics.getTransform();
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
	public CanvasGradient createLinearGradient(Object x0, Object y0, Object x1, Object y1) {
		return new CanvasGradientImpl(x0, y0, x1, y1);
	}

	/** {@inheritDoc} */
	@Override
	public CanvasPattern createPattern(HTMLCanvasElement canvas, String repetitionType) {
		return new CanvasPatternImpl(canvas, repetitionType);
	}

	/** {@inheritDoc} */
	@Override
	public CanvasPattern createPattern(HTMLImageElement image, String repetitionType) {
		return new CanvasPatternImpl(image, repetitionType);
	}

	/** {@inheritDoc} */
	@Override
	public CanvasGradient createRadialGradient(Object x0, Object y0, Object r0, Object x1, Object y1, Object r1) {
		return new CanvasGradientImpl(x0, y0, x1, y1, r0, r1);
	}

	/** {@inheritDoc} */
	@Override
	public void drawImage(Object oImage, Integer x, Integer y) {
		if (oImage instanceof HTMLImageElementImpl) {
			HTMLImageElementImpl hImage = (HTMLImageElementImpl)oImage;
			Image image = HttpNetwork.getImage(hImage.getSrc(), null);
			Graphics2D graphics = createGraphics();
			AffineTransform at = new AffineTransform();
			at.setToTranslation(x, y);
			graphics.drawImage(image, at, null);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void drawImage(Object oImage, Integer x, Integer y, Integer width, Integer height) {
		if (oImage instanceof HTMLImageElementImpl) {
			HTMLImageElementImpl hImage = (HTMLImageElementImpl)oImage;
			Image image = HttpNetwork.getImage(hImage.getSrc(), null);
			Graphics2D graphics = createGraphics();
			AffineTransform at = new AffineTransform(width / image.getWidth(null), 0, 0, height / image.getHeight(null), x, y);
			graphics.drawImage(image, at, null);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void drawImage(Object oImage, Integer sx, Integer sy, Integer sw, Integer sh, Integer dx, Integer dy, Integer dw, Integer dh) {
		if (oImage instanceof HTMLImageElementImpl) {
			HTMLImageElementImpl hImage = (HTMLImageElementImpl)oImage;
			Image image = HttpNetwork.getImage(hImage.getSrc(), null);
			Graphics2D graphics = createGraphics();
			graphics.clip(new Rectangle2D.Float(dx, dy, dw, dh));
			float scaleX = dw / sw;
			float scaleY = dh / sh;
			float x0 = dx - sx * scaleX;
			float y0 = dy - sy * scaleY;
			AffineTransform at = new AffineTransform(scaleX, 0, 0, scaleY, x0, y0);
			graphics.drawImage(image, at, null);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void fill() {
		Graphics2D graphics = createGraphics();
		
		if (getShadowBlur() > 0) {
			shadow(graphics, path.getBounds().x, path.getBounds().y, path.getBounds().width, path.getBounds().height, true);
		}

		graphics.setPaint((Paint)getFillStyle());
		graphics.setTransform(affineTransform);
		graphics.fill(path);	
	}

	/** {@inheritDoc} */
	@Override
	public void fillRect(int x, int y, int width, int height) {
		Graphics2D graphics = createGraphics();
		
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
	public void fillText(String text, int x, int y) {
		fillText(text, x, y, 0);

	}

	/** {@inheritDoc} */
	@Override
	public void fillText(String text, int x, int y, int maxWidth) {
		Graphics2D graphics = createGraphics();
		graphics.setPaint((Paint)getFillStyle());
		graphics.setFont(getFont());
		graphics.rotate(rotate);
		graphics.scale(scaleX, scaleY);
		graphics.translate(translateX, translateY);
		Point2D.Float f = calcTextPos(graphics, text, x, y);
		graphics.drawString(text, f.x, f.y);
	}

	/** {@inheritDoc} */
	@Override
	public ImageData getImageData(int sx, int sy, int sw, int sh) {
		return new ImageDataImpl(image, sw, sh);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isPointInPath(int x, int y) {
		Point2D p = new Point2D.Float(x, y);
		return path.contains(p);
	}

	/** {@inheritDoc} */
	@Override
	public void lineTo(int x, int y) {
		path.lineTo(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public TextMetrics measureText(String text) {
		Graphics2D graphics = createGraphics();
		FontMetrics metrics = graphics.getFontMetrics(font);
		Rectangle2D rect = metrics.getStringBounds(text, graphics);
		return new CanvasTextMetricsImpl(rect.getWidth(), rect.getHeight());
	}

	/** {@inheritDoc} */
	@Override
	public void moveTo(int x, int y) {
		Graphics2D graphics = createGraphics();
		Point2D p = new Point2D.Float(x, y);
		graphics.getTransform().transform(p, p);
		path.moveTo((float) p.getX(), (float) p.getY());
	}

	/** {@inheritDoc} */
	@Override
	public void putImageData(ImageData imagedata, int dx, int dy) {
		putImageData(imagedata, dx, dy, 0, 0, imagedata.getWidth(), imagedata.getHeight());
	}

	/** {@inheritDoc} */
	@Override
	public void putImageData(ImageData imagedata, int dx, int dy, int dirtyX, int dirtyY, int dirtyWidth, int dirtyHeight) {
		Graphics2D graphics = createGraphics();
		BufferedImage image = (BufferedImage)imagedata.getData();
		graphics.drawImage(image, dx, dy, null);
    }

	/** {@inheritDoc} */
	@Override
	public void quadraticCurveTo(int cpx, int cpy, int x, int y) {
		Graphics2D graphics = createGraphics();
		float[] xy = { cpx, cpy, x, y };
		graphics.getTransform().transform(xy, 0, xy, 0, 2);
		path.quadTo(xy[0], xy[1], xy[2], xy[3]);
	}

	/** {@inheritDoc} */
	@Override
	public void rect(int x, int y, int width, int height) {
		path.append(new Rectangle2D.Double(x, y, width, height), true);
	}

	/** {@inheritDoc} */
	@Override
	public void restore() {
		// TODO Auto-generated method stub
	}

	/** {@inheritDoc} */
	@Override
	public void rotate(double angle) {
		rotate = angle;
	}

	/** {@inheritDoc} */
	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public void scale(int sx, int sy) {
		scaleX = sx;
		scaleY = sy;
	}

	/** {@inheritDoc} */
	@Override
	public void setTransform(Double m11, Double m12, Double m21, Double m22, Double dx, Double dy) {
		transform(m11, m12, m21, m22, dx, dy);
	}

	/** {@inheritDoc} */
	@Override
	public void stroke() {
		Graphics2D graphics = createGraphics();
		
		if (getShadowBlur() > 0) {
			shadow(graphics, path.getBounds().x, path.getBounds().y, path.getBounds().width, path.getBounds().height, false);
		}
		
		graphics.setStroke(new BasicStroke(lineWidth, intLineCap, intlineJoin, miterLimit));
		graphics.setPaint((Paint)getStrokeStyle());
		graphics.draw(path);
	}

	/** {@inheritDoc} */
	@Override
	public void strokeRect(int x, int y, int width, int height) {
		strokeRect(x, y, width, height, lineWidth);
	}

	/** {@inheritDoc} */
	@Override
	public void strokeRect(int x, int y, int width, int height, int lineWidth) {
		Graphics2D graphics = createGraphics();
		
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
	public void strokeText(String text, int x, int y) {
		strokeText(text, x, y, 0);
	}

	/** {@inheritDoc} */
	@Override
	public void strokeText(String text, int x, int y, int maxWidth) {
		Graphics2D graphics = createGraphics();
		FontRenderContext frc = new FontRenderContext(null, false, false);
		TextLayout tl = new TextLayout(text, font, frc);
		Point2D.Float pos = calcTextPos(graphics, text, x, y);
		AffineTransform textAt = AffineTransform.getTranslateInstance(pos.x, pos.y);
		textAt.translate(x, y);
		Shape outline = tl.getOutline(textAt);
		graphics.setPaint((Paint)getStrokeStyle());
		graphics.rotate(rotate);
		graphics.setStroke(new BasicStroke(2));
		graphics.scale(scaleX, scaleY);
		graphics.translate(translateX, translateY);
		graphics.draw(outline);
	}

	/** {@inheritDoc} */
	@Override
	public void transform(Double m11, Double m12, Double m21, Double m22, Double dx, Double dy) {
		affineTransform = new AffineTransform(m11, m12, m21, m22, dx, dy);
	}

	/** {@inheritDoc} */
	@Override
	public void translate(int tx, int ty) {
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
		Graphics2D createGraphics = image.createGraphics();
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
		int alphaInt;
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
	
	private Point2D.Float calcTextPos(Graphics2D graphics, String text, int x, int y) {
		FontMetrics metrics = graphics.getFontMetrics();
		
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
	
	private Arc2D.Double buildArc(int x, int y, int radius, int startAngle, int endAngle, boolean anticlockwise) {
		boolean clockwise = !anticlockwise;
		double twopi = 2 * Math.PI;

		while (startAngle < 0) {
			startAngle += twopi;
		}
		while (startAngle > twopi) {
			startAngle -= twopi;
		}

		while (endAngle < 0) {
			endAngle += twopi;
		}
		while (endAngle > twopi) {
			endAngle -= twopi;
		}

		if (clockwise) {
			if (startAngle > endAngle) {
				endAngle += twopi;
			}

		} else {
			if (startAngle < endAngle) {
				endAngle -= twopi;
			}
		}

		double ang = startAngle - endAngle;
		if (ang == 0.0) {
			ang = Math.PI * 2;
		}

		startAngle = -startAngle;
		return new Arc2D.Double(x - radius, y - radius, 2 * radius, 2 * radius, Math.toDegrees(startAngle), Math.toDegrees(ang), Arc2D.OPEN);
	}

	private void shadow(Graphics2D graphics, int x1, int y1, int width1, int height1, boolean isFill) {
		Color shadowColor = ColorFactory.getInstance().getColor(getShadowColor());
		Color shadowColorA = new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), 150);
		int x = x1 + getShadowOffsetX();
		int y = y1 + getShadowOffsetY();
		int strokeSize = getShadowBlur();
		int width = width1 + strokeSize;
		int height = height1 + strokeSize;
		graphics.setColor(shadowColorA);
		if (isFill) {
			graphics.fillRoundRect(x, y, width, height, 0, 0);
		} else {
			graphics.drawRect(x, y, width, height);
		}
	}
}
