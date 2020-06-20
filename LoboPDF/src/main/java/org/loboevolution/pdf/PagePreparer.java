package org.loboevolution.pdf;

import java.awt.Dimension;

import org.loboevolution.pdfview.PDFFile;
import org.loboevolution.pdfview.PDFPage;

/**
 * A class to pre-cache the next page for better UI response.
 */
class PagePreparer extends Thread {

	/** The waitfor page. */
	private int waitforPage;

	/** The prep page. */
	private int prepPage;
	
	private PDFViewer PDFViewer;

	/**
	 * Creates a new PagePreparer to prepare the page after the current one.
	 *
	 * @param waitforPage
	 *            the current page number, 0 based
	 * @param PDFViewer 
	 */
	public PagePreparer(int waitforPage, PDFViewer PDFViewer) {
		setDaemon(true);
		setName(getClass().getName());
		this.waitforPage = waitforPage;
		this.prepPage = waitforPage + 1;
		this.PDFViewer = PDFViewer;
	}

	/**
	 * Quit.
	 */
	public void quit() {
		waitforPage = -1;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		Dimension size = null;
		final PagePanel fspp = PDFViewer.fspp;
		final PagePanel page = PDFViewer.page;
		final int curpage = PDFViewer.curpage;
		final PDFFile curFile = PDFViewer.curFile;
		
		if (fspp != null) {
			fspp.waitForCurrentPage();
			size = fspp.getCurSize();
		} else if (page != null) {
			page.waitForCurrentPage();
			size = page.getCurSize();
		}
		if (waitforPage == curpage) {
			PDFPage pdfPage = curFile.getPage(prepPage + 1, true);
			if (pdfPage != null && waitforPage == curpage) {
				pdfPage.getImage(size.width, size.height, null, null, true, true);
			}
		}
	}
}
