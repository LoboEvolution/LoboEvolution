/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html;

import java.util.Arrays;
import java.util.List;

/**
 * The Class HtmlCommandMapping.
 */
public class HtmlCommandMapping {

    /**
     * https://developer.mozilla.org/en-US/docs/Web/API/document.execCommand#
     * Commands
     */

    public static final List<String> EXECUTE_CMDS = Arrays.asList(new String[] {
            "backColor", "bold", "contentReadOnly", "copy", "createLink",
            "cut", "decreaseFontSize", "delete", "fontName", "fontSize",
            "foreColor", "formatBlock", "heading", "hiliteColor",
            "increaseFontSize", "indent", "insertHorizontalRule", "insertHTML",
            "insertImage", "insertOrderedList", "insertUnorderedList",
            "insertParagraph", "italic", "justifyCenter", "justifyLeft",
            "justifyRight", "outdent", "paste", "redo", "removeFormat",
            "selectAll", "strikeThrough", "subscript", "superscript",
            "underline", "undo", "unlink", "useCSS", "styleWithCSS"  });

}
