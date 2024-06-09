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

package org.loboevolution.html.renderer;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLLinkElementImpl;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.StyleSheetRenderState;
import org.loboevolution.html.style.GradientStyle;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.info.BackgroundInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>StyleSheetRenderState class.</p>
 */
@Slf4j
public class BackgroundRender {

    private final RenderState prevRenderState;
    private final HTMLDocumentImpl document;

    private final HTMLElementImpl element;

    private final HtmlInsets marInsets;

    private final HtmlInsets borInsets;

    public BackgroundRender(final HTMLElementImpl element, final RenderState prevRenderState, final HTMLDocumentImpl document) {
        this.prevRenderState = prevRenderState;
        this.document = document;
        this.element = element;
        this.marInsets = element.getRenderState().getMarginInsets();
        this.borInsets = (HtmlInsets) element.getRenderState().getBorderInfo().getInsets();
    }

    public void applyBackgroundPosition(final BackgroundInfo binfo, final String position, final int width, final int height) {
        binfo.setBackgroundXPositionAbsolute(false);
        binfo.setBackgroundYPositionAbsolute(false);
        binfo.setBackgroundXPosition(0);
        binfo.setBackgroundYPosition(0);
        final String[] tok = HtmlValues.splitCssValue(position);
        switch ((int) Arrays.stream(tok).count()) {
            case 1:
                final String xposition = Arrays.stream(tok).findFirst().get();
                applyBackgroundHorizontalPositon(binfo, xposition, width, height);
                applyBackgroundVerticalPosition(binfo, xposition, width, height);
                break;
            case 2:
                Arrays.stream(tok).forEach(tk -> {
                    final CSSValues pos = CSSValues.get(tk);
                    switch (pos) {
                        case TOP:
                        case BOTTOM:
                        case CENTER:
                            applyBackgroundVerticalPosition(binfo, tk, width, height);
                            break;
                        case LEFT:
                        case RIGHT:
                            applyBackgroundHorizontalPositon(binfo, tk, width, height);
                            break;
                        default:
                            break;
                    }
                });
                break;
            case 3:
            case 4:
                final AtomicReference<CSSValues> ps = new AtomicReference<>(null);
                final AtomicReference<String> lastPos = new AtomicReference<>(null);
                Arrays.stream(tok).forEach(tk -> {
                    lastPos.set(tk);
                    final CSSValues pos = CSSValues.get(tk);
                    switch (pos) {
                        case TOP:
                        case BOTTOM:
                        case CENTER:
                            ps.set(pos);
                            applyBackgroundVerticalPosition(binfo, tk, width, height);
                            break;
                        case LEFT:
                        case RIGHT:
                            ps.set(pos);
                            applyBackgroundHorizontalPositon(binfo, tk, width, height);
                            break;
                        default:
                            int size;
                            int margin;
                            int border;
                            switch (ps.get()) {
                                case TOP:
                                case BOTTOM:
                                case CENTER:
                                    size = HtmlValues.getPixelSize(lastPos.get(), prevRenderState, document.getDefaultView(), 0, height);
                                    margin = CSSValues.TOP.equals(ps.get()) ? marInsets.getTop() : marInsets.getBottom();
                                    border = CSSValues.TOP.equals(ps.get()) ? borInsets.getTop() : borInsets.getBottom();
                                    binfo.setBackgroundYPosition(binfo.getBackgroundYPosition() + size + margin + border);
                                    break;
                                case LEFT:
                                case RIGHT:
                                    size = HtmlValues.getPixelSize(lastPos.get(), prevRenderState, document.getDefaultView(), 0, width);
                                    margin = CSSValues.LEFT.equals(ps.get()) ? marInsets.getLeft() : marInsets.getRight();
                                    border = CSSValues.LEFT.equals(ps.get()) ? borInsets.getLeft() : borInsets.getRight();
                                    binfo.setBackgroundXPosition(binfo.getBackgroundXPosition() + size + margin + border);
                                    break;
                            }
                            break;
                    }
                });
                break;
            default:
                break;
        }
    }

    public void applyBackgroundRepeat(final BackgroundInfo binfo, final String backgroundRepeatText) {
        if (binfo.getBackgroundRepeat() == BackgroundInfo.BR_REPEAT) {
            final CSSValues rep = CSSValues.get(backgroundRepeatText);
            switch (rep) {
                case REPEAT_X:
                    binfo.setBackgroundRepeat(BackgroundInfo.BR_REPEAT_X);
                    break;
                case REPEAT_Y:
                    binfo.setBackgroundRepeat(BackgroundInfo.BR_REPEAT_Y);
                    break;
                case REPEAT_NO:
                    binfo.setBackgroundRepeat(BackgroundInfo.BR_NO_REPEAT);
                    break;
                case INHERIT:
                    final BackgroundInfo bi = prevRenderState.getPreviousRenderState().getBackgroundInfo();
                    if (bi != null) {
                        binfo.setBackgroundRepeat(bi.getBackgroundRepeat());
                    }
                    break;
                case INITIAL:
                case REPEAT:
                default:
                    binfo.setBackgroundRepeat(BackgroundInfo.BR_REPEAT);
                    break;
            }
        }
    }

    public void applyBackgroundVerticalPosition(final BackgroundInfo binfo, final String yposition, final int width, final int height) {
        final CSSValues ypos = CSSValues.get(yposition);
        switch (ypos) {
            case BOTTOM:
                binfo.setBackgroundYPositionAbsolute(false);
                binfo.setBackgroundYPosition(element.getBoundingClientRect().getHeight() - (height - marInsets.getBottom() - borInsets.getBottom()));
                break;
            case LEFT:
            case RIGHT:
            case CENTER:
                final int parentSize = HtmlValues.getPixelSize("50%", prevRenderState, document.getDefaultView(), 0, element.getClientHeight());
                binfo.setBackgroundYPositionAbsolute(false);
                final int margin = CSSValues.LEFT.isEqual(yposition) ? marInsets.getLeft() : marInsets.getRight();
                final int border = CSSValues.LEFT.isEqual(yposition) ? borInsets.getLeft() : borInsets.getRight();
                binfo.setBackgroundYPosition(parentSize - (height / 2));
                binfo.setBackgroundYPosition(binfo.getBackgroundYPosition() + (margin + border));
                break;
            case TOP:
                binfo.setBackgroundYPositionAbsolute(false);
                binfo.setBackgroundYPosition(0);
                break;
            case INHERIT:
                final BackgroundInfo bi = prevRenderState.getPreviousRenderState().getBackgroundInfo();
                if (bi != null) {
                    binfo.setBackgroundYPositionAbsolute(bi.isBackgroundYPositionAbsolute());
                    binfo.setBackgroundYPosition(bi.getBackgroundYPosition());
                }
                break;
            case INITIAL:
            default:
                binfo.setBackgroundYPositionAbsolute(true);
                binfo.setBackgroundYPosition(HtmlValues.getPixelSize(yposition, prevRenderState, document.getDefaultView(), 0, element.getClientHeight()));
                break;
        }
    }

    public void applyBackgroundHorizontalPositon(final BackgroundInfo binfo, final String xposition, final int width, final int height) {
        final CSSValues xpos = CSSValues.get(xposition);
        switch (xpos) {
            case RIGHT:
                binfo.setBackgroundXPositionAbsolute(false);
                binfo.setBackgroundXPosition(element.getClientWidth() - (width - marInsets.getRight() - borInsets.getRight()));
                break;
            case LEFT:
                binfo.setBackgroundXPositionAbsolute(false);
                binfo.setBackgroundXPosition(0);
                break;
            case BOTTOM:
            case TOP:
            case CENTER:
                final int parentSize = HtmlValues.getPixelSize("50%", prevRenderState, document.getDefaultView(), 0, element.getClientWidth());
                binfo.setBackgroundXPositionAbsolute(false);
                binfo.setBackgroundXPosition(parentSize - (width / 2));
                final int margin = CSSValues.TOP.isEqual(xposition) ? marInsets.getTop() : marInsets.getBottom();
                final int border = CSSValues.TOP.isEqual(xposition) ? borInsets.getTop() : borInsets.getBottom();
                binfo.setBackgroundXPosition(binfo.getBackgroundXPosition() - (margin + border));
                break;
            case INHERIT:
                final BackgroundInfo bi = prevRenderState.getPreviousRenderState().getBackgroundInfo();
                if (bi != null) {
                    binfo.setBackgroundXPositionAbsolute(bi.isBackgroundXPositionAbsolute());
                    binfo.setBackgroundXPosition(bi.getBackgroundXPosition());
                }
                break;
            case INITIAL:
            default:
                binfo.setBackgroundXPositionAbsolute(true);
                binfo.setBackgroundXPosition(HtmlValues.getPixelSize(xposition, prevRenderState, document.getDefaultView(), 0, element.getClientWidth()));
                break;
        }
    }

    public void applyBackgroundImage(final BackgroundInfo binfo, String backgroundImageText, final StyleSheetRenderState renderState, final CSSStyleDeclaration props) {
        if (HtmlValues.isUrl(backgroundImageText)) {
            final String start = "url(";
            final int startIdx = start.length() + 1;
            final int closingIdx = backgroundImageText.lastIndexOf(')') - 1;
            String quotedUri = backgroundImageText.substring(startIdx, closingIdx);
            final String[] items = {"http", "https", "file"};
            if (Strings.containsWords(quotedUri, items)) {
                try {
                    binfo.setBackgroundImage(linkUri(document, backgroundImageText));
                } catch (final Exception e) {
                    binfo.setBackgroundImage(null);
                }
            } else {
                if (quotedUri.contains(";base64,")) {
                    final String base64 = backgroundImageText.split(";base64,")[1];
                    final byte[] decodedBytes = Base64.getDecoder().decode(Strings.linearize(base64));
                    backgroundImageText = Arrays.toString(decodedBytes);
                }
                binfo.setBackgroundImage(linkUri(document, backgroundImageText));
            }
        } else if (HtmlValues.isGradient(backgroundImageText)) {
            try {
                final GradientStyle style = new GradientStyle();
                final BufferedImage img = style.gradientToImg(document, props, renderState, backgroundImageText);
                if (img != null) {
                    final File f = File.createTempFile("temp", null);
                    ImageIO.write(img, "png", f);
                    binfo.setBackgroundImage(f.toURI().toURL());
                }
            } catch (final Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public URL linkUri(final HTMLDocumentImpl document, final String backgroundImageText) {
        if (element instanceof HTMLLinkElementImpl elm) {
            final String rel = elm.getAttribute("rel");
            if (rel != null) {
                final String cleanRel = rel.trim().toLowerCase();
                final boolean isStyleSheet = cleanRel.equals("stylesheet");
                final boolean isAltStyleSheet = cleanRel.equals("alternate stylesheet");

                if ((isStyleSheet || isAltStyleSheet)) {
                    return document.getFullURL(backgroundImageText, elm.getHref());
                }
            }
        }

        return document.getFullURL(backgroundImageText, document.getBaseURI());
    }
}
