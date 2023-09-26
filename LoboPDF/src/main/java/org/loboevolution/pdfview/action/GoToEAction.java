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

import java.io.IOException;
import java.util.ArrayList;

import org.loboevolution.pdfview.PDFDestination;
import org.loboevolution.pdfview.PDFObject;
import org.loboevolution.pdfview.PDFParseException;

/**
 ***************************************************************************
 * Action directing to a location within an embedded PDF document
 *
 * Author  Katja Sondermann
 * @since 07.07.2009
 ***************************************************************************
  *
 */
public class GoToEAction extends PDFAction {

	/** the destination within the remote PDF file */
	private final PDFDestination destination;
	/** the remote file this action refers to (optional)*/
	private String file = null;
	/** Should the remote file be opened in a new window? (optional)*/
	private boolean newWindow = false;
	/** The target dictionary*/
	private GoToETarget target;

	/**
	 * Creates a new instance of GoToEAction from an object
	 *
	 * @param obj the PDFObject with the action information
	 * @param root a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @throws java.io.IOException if any.
	 */
	public GoToEAction(final PDFObject obj, final PDFObject root) throws IOException {
		super("GoToE");
		// find the destination and parse it
		this.destination = PdfObjectParseUtil.parseDestination("D", obj, root, true);
		
		// find the remote file and parse it
		this.file = PdfObjectParseUtil.parseStringFromDict("F", obj, false);

		// find the new window attribute and parse it if available
		this.newWindow = PdfObjectParseUtil.parseBooleanFromDict("NewWindow", obj, false);

		// parse the target dictionary
		final PDFObject targetObj = obj.getDictRef("T");
		final ArrayList<GoToETarget> list = new ArrayList<>();
		this.target = parseTargetDistionary(targetObj, list);
	}

	/*************************************************************************
	 * Parse a target dictionary if available 
	 * @param targetObj a {@link org.loboevolution.pdfview.PDFObject} object.
	 * @param list - a list of all already parsed targets, for not getting in an endless loop 
	 * 					(if a target is found which is already contained, the recursive calling
	 * 					of this method will stop).
	 * @throws IOException - in case a value can not be parsed 
	 ************************************************************************/
	private GoToETarget parseTargetDistionary(final PDFObject targetObj, final ArrayList<GoToETarget> list) throws IOException {
		GoToETarget target = null;
		if (targetObj != null) {
			target = new GoToETarget();
			
			// find the relation and parse it
			target.setRelation(PdfObjectParseUtil.parseStringFromDict("R", targetObj, true));
			
			// find the name of the embedded file and parse it
			target.setNameInTree(PdfObjectParseUtil.parseStringFromDict("N", targetObj, false));
			
			// find the page number and parse it
			String page = PdfObjectParseUtil.parseStringFromDict("P", targetObj, false);
			if (page == null) {
				page = ""+PdfObjectParseUtil.parseIntegerFromDict("P", targetObj, false);
			}
			target.setPageNo(page);
			
			// find the annotation index and parse it
			String annot = PdfObjectParseUtil.parseStringFromDict("A", targetObj, false);
			if (annot == null) {
				annot = ""+PdfObjectParseUtil.parseIntegerFromDict("A", targetObj, false);
			}
			target.setAnnotNo(annot);
			
			//find target dictionary and parse it
			final PDFObject subTargetObj = targetObj.getDictRef("T");
			if (subTargetObj != null) {
				// call this method recursive, in case the target was not already contained in the 
				// list (this is checked for not getting into an infinite loop)
				if (!list.contains(target)) {
					list.add(target);
					final GoToETarget subTargetDictionary = parseTargetDistionary(subTargetObj, list);
					target.setTargetDictionary(subTargetDictionary);
				}
			}
		} else {
			if (this.file == null) {
				throw new PDFParseException("No target dictionary in GoToE action " + targetObj);
			}
		}
		return target;
	}

	/**
	 ***********************************************************************
	 * Create a new GoToEAction from the given attributes
	 *
	 * @param dest a {@link org.loboevolution.pdfview.PDFDestination} object.
	 * @param file a {@link java.lang.String} object.
	 * @param newWindow a {@link java.lang.Boolean} object.
	 ***********************************************************************
	 */
	public GoToEAction(final PDFDestination dest, final String file, final boolean newWindow) {
		super("GoToR");
		this.file = file;
		this.destination = dest;
		this.newWindow = newWindow;
	}

	/**
	 ***********************************************************************
	 * Get the destination this action refers to
	 *
	 * @return PDFDestination
	 ***********************************************************************
	 */
	public PDFDestination getDestination() {
		return this.destination;
	}

	/**
	 ***********************************************************************
	 * Get the file this action refers to
	 *
	 * @return PDFDestination
	 ***********************************************************************
	 */
	public String getFile() {
		return this.file;
	}

	/**
	 ***********************************************************************
	 * Should the remote file be opened in a new window?
	 *
	 * @return boolean
	 ***********************************************************************
	 */
	public boolean isNewWindow() {
		return this.newWindow;
	}
	
	/**
	 ***********************************************************************
	 * Get the target dictionary
	 *
	 * @return GoToETarget
	 ***********************************************************************
	 */
	public GoToETarget getTarget() {
		return this.target;
	}

	
	/*****************************************************************************
	 * Inner class for holding the target dictionary's information
	 *
	 * @version $Id: GoToEAction.java,v 1.1 2009-07-10 12:47:31 xond Exp $ 
	 * Author  xond
	 * @since 07.07.2009
	 ****************************************************************************/
	public static class GoToETarget {
		private String relation;
		private String nameInTree;
		private String pageNo;
		private String annotNo;
		private GoToETarget targetDictionary;

		/*************************************************************************
		 * Relation between current document and the target. Can either be "P" or "C"
		 * @return a {@link java.lang.String} object.
		 ************************************************************************/
		public String getRelation() {
			return this.relation;
		}

		/*************************************************************************
		 * Relation between current document and the target. Can either be "P" or "C"
		 * @param relation a {@link java.lang.String} object.
		 ************************************************************************/
		public void setRelation(final String relation) {
			this.relation = relation;
		}

		/*************************************************************************
		 * The file name in the embedded files tree
		 * @return a {@link java.lang.String} object.
		 ************************************************************************/
		public String getNameInTree() {
			return this.nameInTree;
		}

		/*************************************************************************
		 * The file name in the embedded files tree
		 * @param nameInTree a {@link java.lang.String} object.
		 ************************************************************************/
		public void setNameInTree(final String nameInTree) {
			this.nameInTree = nameInTree;
		}

		/*************************************************************************
		 * Page Number:
		 * If the value can be parsed as Integer, it specifies the page number in 
		 * the current document containing the file attachment annotation. If the 
		 * value is a string, it defines a named destination in the current document 
		 * that provides the page number of the file attachment annotation.
		 * 
		 * @return String 
		 ************************************************************************/
		public String getPageNo() {
			return this.pageNo;
		}

		/*************************************************************************
		 * Page Number:
		 * If the value can be parsed as Integer, it specifies the page number in 
		 * the current document containing the file attachment annotation. If the 
		 * value is a string, it defines a named destination in the current document 
		 * that provides the page number of the file attachment annotation.
		 * 
		 * @param pageNo a {@link java.lang.String} object.
		 ************************************************************************/
		public void setPageNo(final String pageNo) {
			this.pageNo = pageNo;
		}

		/*************************************************************************
		 * The index of the according annotation in the annotations array
		 * @return a {@link java.lang.String} object.
		 ************************************************************************/
		public String getAnnotNo() {
			return this.annotNo;
		}

		/*************************************************************************
		 * The index of the according annotation in the annotations array
		 * @param annotNo a {@link java.lang.String} object.
		 ************************************************************************/
		public void setAnnotNo(final String annotNo) {
			this.annotNo = annotNo;
		}

		/*************************************************************************
		 * A target dictionary specifying additional target information. If missing, 
		 * the current document is the target file containing the destination.
		 * @return GoToETarget
		 ************************************************************************/
		public GoToETarget getTargetDictionary() {
			return this.targetDictionary;
		}

		/*************************************************************************
		 * A target dictionary specifying additional target information. If missing, 
		 * the current document is the target file containing the destination.
		 * @param targetDictionary a {@link GoToETarget} object.
		 ************************************************************************/
		public void setTargetDictionary(final GoToETarget targetDictionary) {
			this.targetDictionary = targetDictionary;
		}

		@Override
		public boolean equals(final Object obj) {
			if (!(obj instanceof GoToETarget)) {
				return false;
			}
			if (super.equals(obj)) {
				return true;
			}			
			final GoToETarget that = (GoToETarget)obj;
			// compare the strng values, as the attributes may also be null
			return String.valueOf(this.annotNo).equals(String.valueOf(that.annotNo))
				&& String.valueOf(this.nameInTree).equals(String.valueOf(that.nameInTree))
				&& String.valueOf(this.pageNo).equals(String.valueOf(that.pageNo))
				&& String.valueOf(this.relation).equals(String.valueOf(that.relation));
		}
	}
}
