package org.loboevolution.html.js;

import org.loboevolution.js.AbstractScriptableDelegate;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Undefined;
import org.w3c.dom.Document;

/**
 * <p>DOMParser class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class DOMParser extends AbstractScriptableDelegate {

	/**
	 * <p>parseFromString.</p>
	 *
	 * @param str a {@link java.lang.String} object.
	 * @param type a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.Document} object.
	 */
	public Document parseFromString(String str, String type) {
		if (type == null || Undefined.instance == type) {
			throw Context.reportRuntimeError("Missing 'type' parameter");
		}
		if (!"text/html".equals(type) && !"text/xml".equals(type) && !"application/xml".equals(type)
				&& !"application/xhtml+xml".equals(type) && !"image/svg+xml".equals(type)) {
			throw Context.reportRuntimeError("Invalid 'type' parameter: " + type);
		}

		XMLDocument document = new XMLDocument();
		document.loadXML(str);
		return document;
	}

}

