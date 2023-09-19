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

package org.loboevolution.net;

import org.loboevolution.common.Strings;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>MimeType enum.</p>
 */
public enum MimeType {

    ABW("application/x-abiword"),
    ARC("application/x-freearc"),
    AZW("application/vnd.amazon.ebook"),
    BIN("application/octet-stream"),
    BZ("application/x-bzip"),
    BZ2("application/x-bzip2"),
    CDA("application/x-cdf"),
    CSH("application/x-csh"),
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    EOT("application/vnd.ms-fontobject"),
    EPUB("application/epub+zip"),
    GZ("application/gzip"),
    DOC("application/msword"),
    JSON("application/json"),
    JSONLD("application/ld+json"),
    MPKG("application/vnd.apple.installer+xml"),
    ODP("application/vnd.oasis.opendocument.presentation"),
    ODS("application/vnd.oasis.opendocument.spreadsheet"),
    ODT("application/vnd.oasis.opendocument.text"),
    PDF("application/pdf"),
    PHP("application/x-httpd-php"),
    PPT("application/vnd.ms-powerpoint"),
    PPTX("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    RAR("application/vnd.rar"),
    RTF("application/rtf"),
    SH("application/x-sh"),
    SWF("application/x-shockwave-flash"),
    TAR("application/x-tar"),
    VSD("application/vnd.visio"),
    XHTML("application/xhtml+xml"),
    XLS("application/vnd.ms-excel"),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    XML("application/xml"),
    XUL("application/vnd.mozilla.xul+xml"),
    ZIP("application/zip"),
    SEVENZ("application/x-7z-compressed"),
    OGX("application/ogg"),

    BMP("image/bmp"),
    GIF("image/gif"),
    ICO("image/vnd.microsoft.icon"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    TIF("image/tiff"),
    WEBP("image/webp"),
    SVG("image/svg+xml"),

    CSS("text/css"),
    CSV("text/csv"),
    HTML("text/html"),
    ICS("text/calendar"),
    JS("text/javascript"),
    TXT("text/plain"),

    AAC("audio/aac"),
    MID("audio/midi"),
    MP3("audio/mpeg"),
    OGA("audio/ogg"),
    OPUS("audio/opus"),
    WAV("audio/wav"),
    WEBA("audio/webm"),

    AVI("video/x-msvideo"),
    MP4("video/mp4"),
    MPEg("video/mpeg"),
    OGV("video/ogg"),
    TS("video/mp2t"),
    WEBM("video/webm"),

    OTF("font/otf"),
    TTF("font/ttf"),
    WOFF("font/woff"),
    WOFf2("font/woff2"),

    UNKNOWN("content/unknown");

    private final String value;
    private static final Map<String, MimeType> ENUM_MAP;

    MimeType(String value) {
        this.value = value;
    }

    static {
        Map<String, MimeType> map = new HashMap<>();
        for (MimeType instance : MimeType.values()) {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * <p> Getter for the field value </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getValue() {
        return value;
    }

    /**
     * <p>get.</p>*
     * @param actionName a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.net.MimeType} object.
     */
    public static MimeType get(String actionName) {
        if(Strings.isNotBlank(actionName) && actionName.contains(";")){
            actionName = actionName.split(";")[0];
        }
        return ENUM_MAP.get(actionName);
    }
}
