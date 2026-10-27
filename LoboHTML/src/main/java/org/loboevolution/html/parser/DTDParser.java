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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Slf4j
public class DTDParser {

    private DocumentImpl document;

    public void loadExternalDTD(String systemId) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("org/lobo/xml/" + systemId)) {
            if (is == null) return;
            final StringBuilder content = new StringBuilder();
            final InputStreamReader reader = new InputStreamReader(is);
            final char[] buffer = new char[2048];
            int count;
            while ((count = reader.read(buffer)) != -1) {
                content.append(buffer, 0, count);
            }
            parseDTD(content.toString());
        } catch (Exception e) {
            log.error("LoboEvolution: Unexpected error", e);
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

    private void parseDTD(final String content) {
        int declarationStart = content.indexOf("<!ATTLIST");
        while (declarationStart >= 0) {
            final int bodyStart = declarationStart + "<!ATTLIST".length();
            final int declarationEnd = findDeclarationEnd(content, bodyStart);
            if (declarationEnd < 0) {
                return;
            }
            parseAttributeList(content.substring(bodyStart, declarationEnd));
            declarationStart = content.indexOf("<!ATTLIST", declarationEnd + 1);
        }
    }

    public void parseAttributeList(final String declaration) {
        String body = declaration.trim();
        if (body.endsWith(">")) {
            body = body.substring(0, body.length() - 1);
        }
        final List<String> tokens = tokenize(body);
        if (tokens.size() < 4) {
            return;
        }

        final String element = tokens.get(0).toLowerCase();
        final Map<String, String> defaults = document.getDtdDefaultAttributes()
                .computeIfAbsent(element, key -> new HashMap<>());
        int index = 1;
        while (index + 2 < tokens.size()) {
            final String attributeName = tokens.get(index++);
            index++; // Attribute type.
            String defaultDeclaration = tokens.get(index++);
            if ("#FIXED".equals(defaultDeclaration)) {
                if (index >= tokens.size()) {
                    break;
                }
                defaultDeclaration = tokens.get(index++);
            }
            if (!defaultDeclaration.startsWith("#")) {
                defaults.put(attributeName, Strings.stripQuotes(defaultDeclaration));
            }
        }
    }

    private static List<String> tokenize(final String declaration) {
        final List<String> tokens = new ArrayList<>();
        final StringBuilder token = new StringBuilder();
        char quote = 0;
        int parentheses = 0;
        for (int i = 0; i < declaration.length(); i++) {
            final char current = declaration.charAt(i);
            if (quote != 0) {
                token.append(current);
                if (current == quote) {
                    quote = 0;
                }
            } else if (current == '"' || current == '\'') {
                quote = current;
                token.append(current);
            } else if (current == '(') {
                parentheses++;
                token.append(current);
            } else if (current == ')') {
                parentheses--;
                token.append(current);
            } else if (Character.isWhitespace(current) && parentheses == 0) {
                if (!token.isEmpty()) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
            } else {
                token.append(current);
            }
        }
        if (!token.isEmpty()) {
            tokens.add(token.toString());
        }
        return tokens;
    }

    private static int findDeclarationEnd(final String content, final int start) {
        char quote = 0;
        for (int i = start; i < content.length(); i++) {
            final char current = content.charAt(i);
            if (quote != 0) {
                if (current == quote) {
                    quote = 0;
                }
            } else if (current == '"' || current == '\'') {
                quote = current;
            } else if (current == '>') {
                return i;
            }
        }
        return -1;
    }
}
