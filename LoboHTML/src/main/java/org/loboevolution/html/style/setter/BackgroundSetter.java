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

package org.loboevolution.html.style.setter;

import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.js.css.CSSStyleDeclarationImpl;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.laf.ColorFactory;

/**
 * <p>BackgroundSetter class.</p>
 */
public class BackgroundSetter implements SubPropertySetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeValue(final CSSStyleDeclaration declaration, final String newValue) {
        final CSSStyleDeclarationImpl properties = (CSSStyleDeclarationImpl) declaration;
        properties.setProperty(BACKGROUND, newValue);
        if (Strings.isNotBlank(newValue)) {
            final String[] tokens = HtmlValues.splitCssValue(newValue);
            boolean hasXPosition = false;
            boolean hasYPosition = false;
            boolean lenghtPosition = false;
            String attachment = null;
            String color = null;
            String image = null;
            String backgroundRepeat = null;
            StringBuilder position = new StringBuilder();
            for (final String token : tokens) {
                if (ColorFactory.getInstance().isColor(token) || CSSValues.INITIAL.equals(CSSValues.get(token))) {
                    color = token;
                } else if (HtmlValues.isUrl(token) || HtmlValues.isGradient(token)) {
                    image = token;
                } else if (HtmlValues.isBackgroundRepeat(token)) {
                    backgroundRepeat = token;
                } else if (HtmlValues.isBackgroundPosition(token)) {

                    hasXPosition = CSSValues.get(token).equals(CSSValues.LEFT) ||
                            CSSValues.get(token).equals(CSSValues.RIGHT) ||
                            CSSValues.get(token).equals(CSSValues.CENTER);
                    hasYPosition = !hasXPosition;
                    lenghtPosition = lenghtPosition || HtmlValues.isUnits(token);

                    if (hasXPosition && !hasYPosition) {
                        if (!lenghtPosition) {
                            position.insert(0, token + " ");
                        } else {
                            position.append(" ").append(token);
                        }
                    } else {
                        position.append(token).append(" ");
                    }
                } else if (HtmlValues.isBackgroundAttachment(token)) {
                    attachment = token;
                }
            }

            if (color != null) {
                properties.setProperty(BACKGROUND_COLOR, color);
            } else {
                properties.setProperty(BACKGROUND_COLOR, CSSValues.INITIAL.toString());
            }

            if (image != null) {
                properties.setPropertyValueProcessed(BACKGROUND_IMAGE, image, false);
            } else {
                properties.setPropertyValueProcessed(BACKGROUND_IMAGE, CSSValues.INITIAL.toString(), false);
            }

            if (backgroundRepeat != null) {
                properties.setProperty(BACKGROUND_REPEAT, backgroundRepeat);
            } else {
                properties.setProperty(BACKGROUND_REPEAT, CSSValues.INITIAL.toString());
            }

            if (!position.isEmpty()) {
                properties.setProperty(BACKGROUND_POSITION, position.toString());
            } else {
                properties.setProperty(BACKGROUND_POSITION, CSSValues.INITIAL.toString());
            }

            if (attachment != null) {
                properties.setProperty(BACKGROUND_ATTACHMENT, attachment.toString());
            } else {
                properties.setProperty(BACKGROUND_ATTACHMENT, CSSValues.INITIAL.toString());
            }
        }
    }
}
