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

package org.loboevolution.domts.level3;


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.Notation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;

/**
 * The method replaceChild replaces the child node oldChild with newChild in the list of
 * children, and returns the oldChild node.
 * Using replaceChild on this Document node attempt to replace an Entity node with
 * a notation node of retieved from the DTD of another document and verify if a
 * NOT_FOUND_ERR or WRONG_DOCUMENT_ERR or HIERARCHY_REQUEST err is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild10Test extends LoboUnitTest {
@Test
public void runTest(){
        Document doc;
        DocumentType docType;
        NamedNodeMap entitiesMap;
        Node ent;
        Document doc1;
        DocumentType docType1;
        NamedNodeMap notationsMap;
        Notation notation;
        Node replaced;
        doc=sampleXmlFile("hc_staff.xml");
        docType=doc.getDoctype();
        entitiesMap=docType.getEntities();
        ent=entitiesMap.getNamedItem("alpha");
        doc1=sampleXmlFile("hc_staff.xml");
        docType1=doc1.getDoctype();
        notationsMap=docType1.getNotations();
        notation=(Notation)notationsMap.getNamedItem("notation1");

        try{
        replaced=doc.replaceChild(notation,ent);

        }catch(DOMException ex){
        switch(ex.getCode()){
        case 8:
        break;
        case 4:
        break;
        case 3:
        break;
default:
        throw ex;
        }
        }
        }
        }

