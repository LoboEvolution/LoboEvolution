package org.loboevolution.html.parser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.nodeimpl.AttrImpl;
import org.loboevolution.html.dom.nodeimpl.DocumentImpl;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.NamedNodeMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Slf4j
public class DTDParser {

    private  DocumentImpl document;

    public void loadExternalDTD(String systemId) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("org/lobo/xml/"+systemId)) {
            if (is == null) return;
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
            parseDTD(reader);
        } catch (Exception e) {
           log.error(e.getMessage(),e);
        }
    }

    public void applyDefaultAttributes(Element element) {
        final DocumentImpl doc = this.document;
        Map<String, String> defaults =
                doc.getDtdDefaultAttributes().get(element.getTagName().toLowerCase());

        if (defaults == null) return;

        NamedNodeMap map = element.getAttributes();

        for (Map.Entry<String, String> e : defaults.entrySet()) {

            String name = e.getKey();
            String value = e.getValue();

            if (value == null) continue;

            // NON duplicare
            if (map.getNamedItemNS("*", name) != null) continue;
            AttrImpl attr;

            if ("xmlns".equals(name) || name.startsWith("xmlns:")) {
                attr = (AttrImpl) document.createAttribute(name);
            } else if (name.startsWith("xml:")) {
                attr = (AttrImpl) document.createAttribute(name);
            } else if (Strings.isNotBlank(element.getNamespaceURI())) {
                attr = (AttrImpl) document.createAttributeNS(element.getNamespaceURI(), name);
            } else {
                attr = (AttrImpl) document.createAttribute(name);
            }

            attr.setValue(value);
            attr.setOwnerElement(element);

            map.setNamedItem(attr);
        }
    }

    private void parseDTD(LineNumberReader reader) throws IOException {
        final DocumentImpl doc = this.document;
        String line;

        while ((line = reader.readLine()) != null) {

            line = line.trim();

            if (!line.startsWith("<!ATTLIST")) continue;

            line = line
                    .replace("<!ATTLIST", "")
                    .replace(">", "")
                    .trim()
                    .replaceAll("\\s+", " ");

            String[] tokens = line.split(" ");

            if (tokens.length < 4) continue;

            String element = tokens[0];

            int i = 1;

            while (i + 2 < tokens.length) {

                String attrName = tokens[i++];
                String attrType = tokens[i++];
                String defaultDecl = tokens[i++];

                String value = null;

                if (!"#IMPLIED".equals(defaultDecl) &&
                        !"#REQUIRED".equals(defaultDecl)) {
                    value = Strings.stripQuotes(defaultDecl);
                }

                doc.getDtdDefaultAttributes()
                        .computeIfAbsent(element.toLowerCase(), k -> new HashMap<>())
                        .put(attrName, value);
            }
        }
    }
}
