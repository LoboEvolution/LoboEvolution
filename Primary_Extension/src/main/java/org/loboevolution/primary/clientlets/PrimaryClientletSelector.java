/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Sep 18, 2005
 */
package org.loboevolution.primary.clientlets;

import org.loboevolution.clientlet.Clientlet;
import org.loboevolution.clientlet.ClientletRequest;
import org.loboevolution.clientlet.ClientletResponse;
import org.loboevolution.clientlet.ClientletSelector;
import org.loboevolution.primary.clientlets.download.DownloadClientlet;
import org.loboevolution.primary.clientlets.html.HtmlClientlet;
import org.loboevolution.primary.clientlets.img.ImageClientlet;
import org.loboevolution.primary.clientlets.pdf.PdfClientlet;

/**
 * The Class PrimaryClientletSelector.
 */
public class PrimaryClientletSelector implements ClientletSelector {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ClientletSelector#select(org.loboevolution.
	 * clientlet .ClientletRequest, org.loboevolution.clientlet.ClientletResponse)
	 */
	@Override
	public Clientlet select(ClientletRequest request, ClientletResponse response) {
		String mimeType = response.getMimeType();
		String mimeTypeTL = mimeType == null ? null : mimeType.toLowerCase();
		switch (mimeTypeTL) {
		case "text/html":
		case "image/svg+xml":
			return new HtmlClientlet();
		case "image/jpeg":
		case "image/jpg":
		case "image/gif":
		case "image/png":
			return new ImageClientlet();
		case "application/octet-stream":
		case "content/unknown":
		default:
			String path = response.getResponseURL().getPath();
			int lastDotIdx = path.lastIndexOf('.');
			String extension = lastDotIdx == -1 ? "" : path.substring(lastDotIdx + 1);
			String extensionTL = extension.toLowerCase();
			switch (extensionTL) {
			case "html":
			case "htm":
			case "svg":
				return new HtmlClientlet();
			case "gif":
			case "jpg":
			case "png":
				return new ImageClientlet();
			default:
				return new DownloadClientlet();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.clientlet.ClientletSelector#lastResortSelect(org.
	 * loboevolution .clientlet.ClientletRequest,
	 * org.loboevolution.clientlet.ClientletResponse)
	 */
	@Override
	public Clientlet lastResortSelect(ClientletRequest request, ClientletResponse response) {
		String mimeType = response.getMimeType();
		String mimeTypeTL = mimeType == null ? null : mimeType.toLowerCase();
		
		if (mimeType == null) {
			return new HtmlClientlet();
		}
		
		switch (mimeTypeTL) {
		case "application/xml":
			return new HtmlClientlet();
		case "application/json":
			return new TextClientlet("json");
		default:
			String path = response.getResponseURL().getPath();
			int lastDotIdx = path.lastIndexOf('.');
			String extension = lastDotIdx == -1 ? "" : path.substring(lastDotIdx + 1);
			String extensionTL = extension.toLowerCase();
			switch (extensionTL) {
			case "xhtml":
				return new HtmlClientlet();
			case "java":
			case "php":
			case "bash":
			case "ruby":
			case "javascript":
			case "css":
			case "html":
			case "csharp":
			case "sql":
			case "xml":
			case "c":
			case "objc":
			case "python":
			case "perl":
			case "js":
			case "xaml":
			case "json":
				return new TextClientlet(extensionTL);
			case "pdf":
				return new PdfClientlet();
			default:
				return new DownloadClientlet();
			}
		}
	}
}
