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
package org.loboevolution.pdfview;

import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A cache of PDF pages and images.
 */
public class Cache {

    /**
     * the pages in the cache, mapped by page number
     */
    private final Map<Integer, SoftReference<PageRecord>> pages;

    /**
     * Creates a new instance of a Cache
     */
    public Cache() {
        this.pages = Collections.synchronizedMap(new HashMap<>());
    }

    /**
     * Add a page to the cache.  This method should be used for
     * pages which have already been completely rendered.
     *
     * @param pageNumber the page number of this page
     * @param page       the page to add
     */
    public void addPage(final Integer pageNumber, final PDFPage page) {
        addPageRecord(pageNumber, page, null);
    }

    /**
     * Add a page to the cache.  This method should be used for
     * pages which are still in the process of being rendered.
     *
     * @param pageNumber the page number of this page
     * @param page       the page to add
     * @param parser     the parser which is parsing this page
     */
    public void addPage(final Integer pageNumber, final PDFPage page, final PDFParser parser) {
        addPageRecord(pageNumber, page, parser);
    }

    /**
     * Add an image to the cache.  This method should be used for images
     * which have already been completely rendered
     *
     * @param page  page this image is associated with
     * @param info  the image info associated with this image
     * @param image the image to add
     */
    public void addImage(final PDFPage page, final ImageInfo info, final BufferedImage image) {
        addImageRecord(page, info, image, null);
    }

    /**
     * Add an image to the cache.  This method should be used for images
     * which are still in the process of being rendered.
     *
     * @param page     the page this image is associated with
     * @param info     the image info associated with this image
     * @param image    the image to add
     * @param renderer the renderer which is rendering this page
     */
    public void addImage(final PDFPage page, final ImageInfo info, final BufferedImage image,
                         final PDFRenderer renderer) {
        addImageRecord(page, info, image, renderer);
    }

    /**
     * Get a page from the cache
     *
     * @param pageNumber the number of the page to get
     * @return the page, if it is in the cache, or null if not
     */
    public PDFPage getPage(final Integer pageNumber) {
        final PageRecord rec = getPageRecord(pageNumber);
        if (rec != null) {
            return (PDFPage) rec.value;
        }

        // not found
        return null;
    }

    /**
     * Get a page's parser from the cache
     *
     * @param pageNumber the number of the page to get the parser for
     * @return the parser, or null if it is not in the cache
     */
    public PDFParser getPageParser(final Integer pageNumber) {
        final PageRecord rec = getPageRecord(pageNumber);
        if (rec != null) {
            return (PDFParser) rec.generator;
        }

        // not found
        return null;
    }

    /**
     * Get an image from the cache
     *
     * @param page the page the image is associated with
     * @param info the image info that describes the image
     * @return the image if it is in the cache, or null if not
     */
    public BufferedImage getImage(final PDFPage page, final ImageInfo info) {
        final Record rec = getImageRecord(page, info);
        if (rec != null) {
            return (BufferedImage) rec.value;
        }

        // not found 
        return null;
    }

    /**
     * Get an image's renderer from the cache
     *
     * @param page the page this image was generated from
     * @param info the image info describing the image
     * @return the renderer, or null if it is not in the cache
     */
    public PDFRenderer getImageRenderer(final PDFPage page, final ImageInfo info) {
        final Record rec = getImageRecord(page, info);
        if (rec != null) {
            return (PDFRenderer) rec.generator;
        }

        // not found
        return null;
    }

    /**
     * Remove a page and all its associated images, as well as its parser
     * and renderers, from the cache
     *
     * @param pageNumber the number of the page to remove
     */
    public void removePage(final Integer pageNumber) {
        removePageRecord(pageNumber);
    }

    /**
     * Remove an image and its associated renderer from the cache
     *
     * @param page the page the image is generated from
     * @param info the image info of the image to remove
     */
    public void removeImage(final PDFPage page, final ImageInfo info) {
        removeImageRecord(page, info);
    }

    /**
     * The internal routine to add a page to the cache, and return the
     * page record which was generated
     */
    PageRecord addPageRecord(final Integer pageNumber, final PDFPage page,
                             final PDFParser parser) {
        final PageRecord rec = new PageRecord();
        rec.value = page;
        rec.generator = parser;

        this.pages.put(pageNumber, new SoftReference<>(rec));

        return rec;
    }

    /**
     * Get a page's record from the cache
     *
     * @return the record, or null if it's not in the cache
     */
    PageRecord getPageRecord(final Integer pageNumber) {
        PDFDebugger.debug("Request for page " + pageNumber, 1000);
        final SoftReference<PageRecord> ref = this.pages.get(pageNumber);
        if (ref != null) {
            final String val = (ref.get() == null) ? " not in " : " in ";
            PDFDebugger.debug("Page " + pageNumber + val + "cache", 1000);
            return ref.get();
        }

        PDFDebugger.debug("Page " + pageNumber + " not in cache", 1000);
        // not in cache
        return null;
    }

    /**
     * Remove a page's record from the cache
     */
    PageRecord removePageRecord(final Integer pageNumber) {
        final SoftReference<PageRecord> ref = this.pages.remove(pageNumber);
        if (ref != null) {
            return ref.get();
        }

        // not in cache
        return null;
    }

    /**
     * The internal routine to add an image to the cache and return the
     * record that was generated.
     */
    Record addImageRecord(final PDFPage page, final ImageInfo info,
                          final BufferedImage image, final PDFRenderer renderer) {
        // first, find or create the relevant page record
        final Integer pageNumber = page.getPageNumber();
        PageRecord pageRec = getPageRecord(pageNumber);
        if (pageRec == null) {
            pageRec = addPageRecord(pageNumber, page, null);
        }

        // next, create the image record
        final Record rec = new Record();
        rec.value = image;
        rec.generator = renderer;

        // add it to the cache
        pageRec.images.put(info, new SoftReference<>(rec));

        return rec;
    }

    /**
     * Get an image's record from the cache
     *
     * @return the record, or null if it's not in the cache
     */
    Record getImageRecord(final PDFPage page, final ImageInfo info) {
        // first find the relevant page record
        final Integer pageNumber = page.getPageNumber();

        PDFDebugger.debug("Request for image on page " + pageNumber, 1000);

        final PageRecord pageRec = getPageRecord(pageNumber);
        if (pageRec != null) {
            final SoftReference<Record> ref = pageRec.images.get(info);
            if (ref != null) {
                final String val = (ref.get() == null) ? " not in " : " in ";
                PDFDebugger.debug("Image on page " + pageNumber + val + " cache", 1000);
                return ref.get();
            }
        }

        PDFDebugger.debug("Image on page " + pageNumber + " not in cache", 1000);
        // not found
        return null;
    }

    /**
     * Remove an image's record from the cache
     */
    Record removeImageRecord(final PDFPage page, final ImageInfo info) {
        // first find the relevant page record
        final Integer pageNumber = page.getPageNumber();
        final PageRecord pageRec = getPageRecord(pageNumber);
        if (pageRec != null) {
            final SoftReference<Record> ref = pageRec.images.remove(info);
            if (ref != null) {
                return ref.get();
            }

        }

        return null;
    }

    /**
     * the basic information about a page or image
     */
    static class Record {

        /**
         * the page or image itself
         */
        Object value;
        /**
         * the thing generating the page, or null if done/not provided
         */
        BaseWatchable generator;
    }

    /**
     * the record stored for each page in the cache
     */
    static class PageRecord extends Record {

        /**
         * any images associated with the page
         */
        final Map<ImageInfo, SoftReference<Record>> images;

        /**
         * create a new page record
         */
        public PageRecord() {
            this.images = Collections.synchronizedMap(new HashMap<>());
        }
    }
}
