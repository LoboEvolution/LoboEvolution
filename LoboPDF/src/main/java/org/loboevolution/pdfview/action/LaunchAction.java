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

package org.loboevolution.pdfview.action;

import lombok.Data;
import lombok.Getter;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

import java.io.IOException;

/**
 * **************************************************************************
 * Action for launching an application, mostly used to open a file.
 * <p>
 * Author  Katja Sondermann
 *
 * @since 08.07.2009
 * **************************************************************************
 */
public class LaunchAction extends PDFAction {
    /**
     * Constant <code>SOLIDUS="/"</code>
     */
    public static final String SOLIDUS = "/";

    /**
     * the file/application to be opened (optional)
     */
    private final FileSpec file;

    @Getter
    private final WinLaunchParam winParam;

    /**
     * Creates a new instance of LaunchAction from an object
     *
     * @param obj  - the PDFObject with the action information
     * @param root - the root object
     * @throws java.io.IOException if any.
     */
    public LaunchAction(final PDFObject obj, final PDFObject root) throws IOException {
        super("Launch");
        // find the file/application and parse it
        final PDFObject fileObj = obj.getDictRef("F");
        this.file = parseFileSpecification(fileObj);

        // find the new window flag and parse it
        final PDFObject newWinObj = obj.getDictRef("NewWindow");
        if (newWinObj != null) {
            newWinObj.getBooleanValue();
        }
        // parse the OS specific launch parameters:
        this.winParam = parseWinDict(obj.getDictRef("Win"));
        // unix and mac dictionaries are not further specified, so can not be parsed yet.
        PDFObject unixParam = obj.getDictRef("Unix");
        PDFObject macParam = obj.getDictRef("Mac");

        // check if at least the file or one of the OS specific launch parameters is set:
        if ((this.file == null)
                && (this.winParam == null)
                && (unixParam == null)
                && (macParam == null)) {
            throw new PDFParseException("Could not parse launch action (file or OS " +
                    "specific launch parameters are missing): " + obj);
        }
    }

    /**
     * **********************************************************************
     * Is the file name absolute (if not, it is relative to the path of the
     * currently opened PDF file).
     * If the file name starts with a "/", it is considered to be absolute.
     *
     * @param fileName a {@link java.lang.String} object.
     * @return boolean
     * **********************************************************************
     */
    public static boolean isAbsolute(final String fileName) {
        return fileName.startsWith(SOLIDUS);
    }

    /*************************************************************************
     * Parse the file specification object
     * @param fileObj a {@link org.loboevolution.pdfview.PDFObject} object.
     * @return FileSpec - might be <code>null</code> in case the passed object is null
     * @throws IOException a {@link java.io.IOException} object.
     * @throws PDFParseException a {@link org.loboevolution.pdfview.PDFParseException} object.
     ************************************************************************/
    private FileSpec parseFileSpecification(final PDFObject fileObj) throws PDFParseException, IOException {
        FileSpec file = null;
        if (fileObj != null) {
            file = new FileSpec();
            if (fileObj.getType() == PDFObject.DICTIONARY) {
                file.setFileSystem(PdfObjectParseUtil.parseStringFromDict("FS", fileObj, false));
                file.setFileName(PdfObjectParseUtil.parseStringFromDict("F", fileObj, false));
                file.setUnicode(PdfObjectParseUtil.parseStringFromDict("UF", fileObj, false));
                file.setDosFileName(PdfObjectParseUtil.parseStringFromDict("DOS", fileObj, false));
                file.setMacFileName(PdfObjectParseUtil.parseStringFromDict("Mac", fileObj, false));
                file.setUnixFileName(PdfObjectParseUtil.parseStringFromDict("Unix", fileObj, false));
                file.setVolatileFile(PdfObjectParseUtil.parseBooleanFromDict("V", fileObj, false));
                file.setDescription(PdfObjectParseUtil.parseStringFromDict("Desc", fileObj, false));
                file.setId(fileObj.getDictRef("ID"));
                file.setEmbeddedFile(fileObj.getDictRef("EF"));
                file.setRelatedFile(fileObj.getDictRef("RF"));
                file.setCollectionItem(fileObj.getDictRef("CI"));
            } else if (fileObj.getType() == PDFObject.STRING) {
                file.setFileName(fileObj.getStringValue());
            } else {
                throw new PDFParseException("File specification could not be parsed " +
                        "(should be of type 'Dictionary' or 'String'): " + fileObj);
            }
        }
        return file;
    }


    /*************************************************************************
     * Parse the windows specific launch parameters
     * @param winDict a {@link org.loboevolution.pdfview.PDFObject} object.
     * @throws IOException - in case of a problem during parsing content
     ************************************************************************/
    private WinLaunchParam parseWinDict(final PDFObject winDict) throws IOException {
        if (winDict == null) {
            return null;
        }
        final WinLaunchParam param = new WinLaunchParam();

        // find and parse the file/application name
        param.setFileName(PdfObjectParseUtil.parseStringFromDict("F", winDict, true));

        // find and parse the directory
        param.setDirectory(PdfObjectParseUtil.parseStringFromDict("D", winDict, false));

        // find and parse the operation to be performed
        param.setOperation(PdfObjectParseUtil.parseStringFromDict("O", winDict, false));

        // find and parse the parameter to be passed to the application
        param.setParameter(PdfObjectParseUtil.parseStringFromDict("P", winDict, false));

        return param;
    }

    /**
     * **********************************************************************
     * The file / application to be opened
     *
     * @return FileSpec
     * **********************************************************************
     */
    public FileSpec getFileSpecification() {
        return this.file;
    }

    /*****************************************************************************
     * Internal class for the windows specific launch parameters
     *
     * @version $Id: LaunchAction.java,v 1.1 2009-07-10 12:47:31 xond Exp $
     * Author  xond
     * @since 08.07.2009
     ****************************************************************************/
    @Data
    public static class WinLaunchParam {
        private String fileName;
        private String directory;
        private String operation = "open";
        private String parameter;

    }

    /*****************************************************************************
     * Inner class for storing a file specification
     *
     * @version $Id: LaunchAction.java,v 1.1 2009-07-10 12:47:31 xond Exp $
     * Author  xond
     * @since 08.07.2009
     ****************************************************************************/
    @Data
    public static class FileSpec {
        private String fileSystem;
        private String fileName;
        private String dosFileName;
        private String unixFileName;
        private String macFileName;
        private String unicode;
        private PDFObject id;
        private boolean volatileFile;
        private PDFObject embeddedFile;
        private PDFObject relatedFile;
        private String description;
        private PDFObject collectionItem;

        /*************************************************************************
         * Get the filename:
         * first try to get the file name for the used OS, if it's not available
         * return the common file name.
         * @return a {@link java.lang.String} object.
         ************************************************************************/
        public String getFileName() {
            final String system = System.getProperty("os.name");
            if (system.startsWith("Windows")) {
                if (this.dosFileName != null) {
                    return this.dosFileName;
                }
            } else if (system.startsWith("mac os x")) {
                if (this.macFileName != null) {
                    return this.macFileName;
                }
            } else {
                if (this.unixFileName != null) {
                    return this.unixFileName;
                }
            }
            return this.fileName;
        }
    }
}
