
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

package org.loboevolution.domts.level1;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Retrieve the entire DOM document and invoke its
 * "getElementsByTagName(tagName)" method with tagName
 * equal to "*".  The method should return a NodeList
 * that contains all the elements of the document.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-A6C9094">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-A6C9094</a>
 * @see <a href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=251">http://www.w3.org/Bugs/Public/show_bug.cgi?id=251</a>
 */
public class hc_documentgetelementsbytagnametotallengthTest extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection nameList;
        List<String> expectedNames = new ArrayList<>();
        expectedNames.add("HTML");
        expectedNames.add("HEAD");
        expectedNames.add("META");
        expectedNames.add("TITLE");
        expectedNames.add("BODY");
        expectedNames.add("P");
        expectedNames.add("EM");
        expectedNames.add("STRONG");
        expectedNames.add("CODE");
        expectedNames.add("SUP");
        expectedNames.add("VAR");
        expectedNames.add("ACRONYM");
        expectedNames.add("P");
        expectedNames.add("EM");
        expectedNames.add("STRONG");
        expectedNames.add("CODE");
        expectedNames.add("SUP");
        expectedNames.add("VAR");
        expectedNames.add("ACRONYM");
        expectedNames.add("P");
        expectedNames.add("EM");
        expectedNames.add("STRONG");
        expectedNames.add("CODE");
        expectedNames.add("SUP");
        expectedNames.add("VAR");
        expectedNames.add("ACRONYM");
        expectedNames.add("P");
        expectedNames.add("EM");
        expectedNames.add("STRONG");
        expectedNames.add("CODE");
        expectedNames.add("SUP");
        expectedNames.add("VAR");
        expectedNames.add("ACRONYM");
        expectedNames.add("P");
        expectedNames.add("EM");
        expectedNames.add("STRONG");
        expectedNames.add("CODE");
        expectedNames.add("SUP");
        expectedNames.add("VAR");
        expectedNames.add("ACRONYM");

        List<String> svgExpectedNames = new ArrayList<>();
        svgExpectedNames.add("SVG");
        svgExpectedNames.add("RECT");
        svgExpectedNames.add("SCRIPT");
        svgExpectedNames.add("HEAD");
        svgExpectedNames.add("META");
        svgExpectedNames.add("TITLE");
        svgExpectedNames.add("BODY");
        svgExpectedNames.add("P");
        svgExpectedNames.add("EM");
        svgExpectedNames.add("STRONG");
        svgExpectedNames.add("CODE");
        svgExpectedNames.add("SUP");
        svgExpectedNames.add("VAR");
        svgExpectedNames.add("ACRONYM");
        svgExpectedNames.add("P");
        svgExpectedNames.add("EM");
        svgExpectedNames.add("STRONG");
        svgExpectedNames.add("CODE");
        svgExpectedNames.add("SUP");
        svgExpectedNames.add("VAR");
        svgExpectedNames.add("ACRONYM");
        svgExpectedNames.add("P");
        svgExpectedNames.add("EM");
        svgExpectedNames.add("STRONG");
        svgExpectedNames.add("CODE");
        svgExpectedNames.add("SUP");
        svgExpectedNames.add("VAR");
        svgExpectedNames.add("ACRONYM");
        svgExpectedNames.add("P");
        svgExpectedNames.add("EM");
        svgExpectedNames.add("STRONG");
        svgExpectedNames.add("CODE");
        svgExpectedNames.add("SUP");
        svgExpectedNames.add("VAR");
        svgExpectedNames.add("ACRONYM");
        svgExpectedNames.add("P");
        svgExpectedNames.add("EM");
        svgExpectedNames.add("STRONG");
        svgExpectedNames.add("CODE");
        svgExpectedNames.add("SUP");
        svgExpectedNames.add("VAR");
        svgExpectedNames.add("ACRONYM");

        java.util.List<String> actualNames = new java.util.ArrayList<String>();

        Element thisElement;
        String thisTag;
        doc = sampleXmlFile("hc_staff.xml");
        nameList = doc.getElementsByTagName("*");
        for (int indexN10148 = 0; indexN10148 < nameList.getLength(); indexN10148++) {
            thisElement = (Element) nameList.item(indexN10148);
            thisTag = thisElement.getTagName();
            actualNames.add(thisTag);
        }

        assertEquals("tagNames", expectedNames, actualNames);

    }

}

