/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Oct 8, 2005
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.dom.HTMLDocument;
import org.lobobrowser.html.dom.HTMLElement;

public abstract class HTMLElementBuilder {
	public static class Anchor extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLLinkElementImpl(name);
		}
	}

	public static class Base extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLBaseElementImpl(name);
		}
	}
	
	public static class Head extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLHeadElementImpl(name);
		}
	}

	public static class Big extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLFontSizeChangeElementImpl(name, +1);
		}
	}

	public static class Blockquote extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLBlockQuoteElementImpl(name);
		}
	}

	public static class Body extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLBodyElementImpl(name);
		}
	}

	public static class Br extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLBRElementImpl(name);
		}
	}

	public static class Button extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLButtonElementImpl(name);
		}
	}

	public static class Center extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLCenterElementImpl(name);
		}
	}

	public static class Code extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLMonospacedElementImpl(name);
		}
	}

	public static class Div extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLDivElementImpl(name);
		}
	}

	public static class Em extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLEmElementImpl(name);
		}
	}

	public static class Font extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLFontElementImpl(name);
		}
	}

	public static class Form extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLFormElementImpl(name);
		}
	}

	public static class Frame extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLFrameElementImpl(name);
		}
	}

	public static class Frameset extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLFrameSetElementImpl(name);
		}
	}

	public static class GenericMarkup extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLGenericMarkupElement(name);
		}
	}

	public static class Heading extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLHeadingElementImpl(name);
		}
	}

	public static class Hr extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLHRElementImpl(name);
		}
	}

	public static class Html extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLHtmlElementImpl(name);
		}
	}

	public static class HtmlObject extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLObjectElementImpl(name);
		}
	}

	public static class IFrame extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLIFrameElementImpl(name);
		}
	}

	public static class Img extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLImageElementImpl(name);
		}
	}

	public static class Input extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLInputElementImpl(name);
		}
	}

	public static class Li extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLLIElementImpl(name);
		}
	}

	public static class Link extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLLinkElementImpl(name);
		}
	}

	public static class NonStandard extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLNonStandardElement(name);
		}
	}

	public static class Ol extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLOListElementImpl(name);
		}
	}

	public static class Option extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLOptionElementImpl(name);
		}
	}

	public static class P extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLPElementImpl(name);
		}
	}

	public static class Pre extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLPreElementImpl(name);
		}
	}

	public static class Script extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLScriptElementImpl(name);
		}
	}

	public static class Select extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLSelectElementImpl(name);
		}
	}

	public static class Small extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLFontSizeChangeElementImpl(name, -1);
		}
	}

	public static class Span extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLSpanElementImpl(name);
		}
	}

	public static class Strike extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLStrikeElementImpl(name);
		}
	}

	public static class Strong extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLStrongElementImpl(name);
		}
	}

	public static class Style extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLStyleElementImpl(name);
		}
	}

	public static class Sub extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLSuperscriptElementImpl(name, -1);
		}
	}

	public static class Sup extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLSuperscriptElementImpl(name, 1);
		}
	}

	public static class Table extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLTableElementImpl(name);
		}
	}

	public static class Td extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLTableCellElementImpl(name);
		}
	}

	public static class Textarea extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLTextAreaElementImpl(name);
		}
	}

	public static class Th extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLTableHeadElementImpl(name);
		}
	}

	public static class Title extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLTitleElementImpl(name);
		}
	}

	public static class Tr extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLTableRowElementImpl(name);
		}
	}

	public static class Tt extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLMonospacedElementImpl(name);
		}
	}

	public static class Ul extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLUListElementImpl(name);
		}
	}

	public static class Underline extends HTMLElementBuilder {
		@Override
		public HTMLElementImpl build(String name) {
			return new HTMLUnderlineElementImpl(name);
		}
	}

	protected abstract HTMLElementImpl build(String name);

	public final HTMLElement create(HTMLDocument document, String name) {
		final HTMLElementImpl element = build(name);
		element.setOwnerDocument(document);
		return element;
	}

}
