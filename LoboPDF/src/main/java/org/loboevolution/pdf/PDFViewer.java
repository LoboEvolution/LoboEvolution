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
package org.loboevolution.pdf;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.BytesUtilities;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.net.UserAgent;
import org.loboevolution.pdfview.*;
import org.loboevolution.pdfview.action.*;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * The Class PDFViewer.
 */
@Slf4j
public class PDFViewer extends JFrame implements KeyListener, PageChangeListener, TreeSelectionListener {

    /**
     * The Constant TITLE.
     */
    public static final String TITLE = "LoboEvolution PDF Viewer";
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    private final float ZOOM_FACTOR = 1.2f;
    /**
     * The current PDFFile.
     */
    protected transient PDFFile curFile;
    /**
     * The page display.
     */
    protected transient PagePanel page;
    /**
     * The full screen page display, or null if not in full screen mode.
     */
    protected transient PagePanel fspp;
    /**
     * The current page number (starts at 0), or -1 if no page.
     */
    protected int curpage = -1;
    /**
     * The scroll page.
     */
    JScrollPane scrollPage;
    /**
     * the name of the current document.
     */
    private String docName;
    /**
     * The split between thumbs and page.
     */
    private JSplitPane split;
    /**
     * The thumbnail scroll pane.
     */
    private JScrollPane thumbscroll;
    /**
     * The thumbnail display.
     */
    private transient ThumbPanel thumbs;
    /**
     * The page format for printing.
     */
    private transient PageFormat pformat = PrinterJob.getPrinterJob().defaultPage();
    /**
     * true if the thumb panel should exist at all.
     */
    private boolean doThumb = true;
    /**
     * a thread that pre-loads the next page for faster response.
     */
    private transient PagePreparer pagePrep;
    /**
     * the window containing the pdf outline, or null if one doesn't exist.
     */
    private JDialog olf;
    /**
     * The nav tool bar.
     */
    private NavigationToolbar navToolbar;

    /**
     * The pb.
     */
    private PageBuilder pb;

    /**
     * The setup action.
     */
    private SetupAction pageSetupAction;

    /**
     * The print action.
     */
    private PrintAction printAction;

    /**
     * The zoom tool action.
     */
    private FitHeightAction fitHeightAction;

    /**
     * The fit in window action.
     */
    private FitAction fitAction;

    /**
     * The thumb action.
     */
    private ThumbAction thumbAction;

    /**
     * The outline action.
     */
    private OutlineAction outAction;

    /**
     * The close outline action.
     */
    private CloseOutlineAction closeOutline;

    /**
     * The zoom in action.
     */
    private ZoomInAction zoomInAction;

    /**
     * The zoom out action.
     */
    private ZoomOutAction zoomOutAction;

    /**
     * Create a new PdfDialog based on a user, with or without a thumbnail
     * panel.
     *
     * @param useThumbs true if the thumb panel should exist, false if not.
     */
    public PDFViewer(final boolean useThumbs) {
        super(TITLE);
        createAndShowGUI(useThumbs);
    }

    private void createAndShowGUI(final boolean useThumbs) {
        doThumb = useThumbs;
        pageSetupAction = new SetupAction(this);
        printAction = new PrintAction(this);
        fitHeightAction = new FitHeightAction(this);
        fitAction = new FitAction(this);
        pb = new PageBuilder(this);
        outAction = new OutlineAction(this);
        closeOutline = new CloseOutlineAction(this);
        zoomInAction = new ZoomInAction(this, ZOOM_FACTOR);
        zoomOutAction = new ZoomOutAction(this, 1f / ZOOM_FACTOR);
        init();
    }

    /**
     * Initialize this PdfDialog by creating the GUI.
     */
    protected void init() {
        page = new PagePanel();
        page.addKeyListener(this);
        scrollPage = new JScrollPane(page);
        if (doThumb) {
            split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            split.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, thumbAction);
            split.setOneTouchExpandable(true);
            thumbs = new ThumbPanel(null);
            thumbscroll = new JScrollPane(thumbs, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            split.setLeftComponent(thumbscroll);
            split.setRightComponent(scrollPage);
            add(split, BorderLayout.CENTER);
        } else {
            add(scrollPage, BorderLayout.CENTER);
        }

        navToolbar = new NavigationToolbar(this);
        navToolbar.setFloatable(false);
        add(navToolbar, BorderLayout.SOUTH);

        final JMenuBar mb = new JMenuBar();
        final JMenu file = new JMenu("File");
        file.add(pageSetupAction);
        file.addSeparator();
        file.add(printAction);
        mb.add(file);

        final JMenu jmZoom = new JMenu("Zoom");
        jmZoom.add(fitHeightAction);
        jmZoom.addSeparator();
        jmZoom.add(fitAction);
        jmZoom.addSeparator();
        jmZoom.add(zoomInAction);
        jmZoom.addSeparator();
        jmZoom.add(zoomOutAction);
        mb.add(jmZoom);

        final JMenu nav = new JMenu("Navigation");
        nav.add(outAction);
        nav.addSeparator();
        nav.add(closeOutline);
        mb.add(nav);

        setJMenuBar(mb);

        setEnabling();
        pack();

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int x = (screen.width - getWidth()) / 2;
        final int y = (screen.height - getHeight()) / 2;
        setLocation(x, y);
        if (SwingUtilities.isEventDispatchThread()) {
            setVisible(true);
        } else {
            try {
                SwingUtilities.invokeAndWait(() -> setVisible(true));
            } catch (final InvocationTargetException e) {
                log.error(e.getMessage(), e);
            } catch (final InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Changes the displayed page, desyncing if we're not on the same page as a
     * presenter.
     */
    @Override
    public void gotoPage(final int pagenum) {
        int tmpPagenum = pagenum;
        if (tmpPagenum < 0) {
            tmpPagenum = 0;
        } else if (tmpPagenum >= curFile.getNumPages()) {
            tmpPagenum = curFile.getNumPages() - 1;
        }
        forceGotoPage(tmpPagenum);
    }

    /**
     * Changes the displayed page.
     *
     * @param pagenum the page to display
     */
    public void forceGotoPage(final int pagenum) {
        int tmpPagenum = pagenum;
        if (tmpPagenum <= 0) {
            tmpPagenum = 0;
        } else if (tmpPagenum >= curFile.getNumPages()) {
            tmpPagenum = curFile.getNumPages() - 1;
        }

        curpage = tmpPagenum;
        // update the page text field
        // fetch the page and show it in the appropriate place
        final PDFPage pg = curFile.getPage(tmpPagenum + 1);
        if (fspp != null) {
            fspp.showPage(pg);
            fspp.requestFocus();
        } else {
            page.showPage(pg);
            page.requestFocus();
        }
        // update the thumb panel
        if (doThumb) {
            thumbs.pageShown(tmpPagenum);
        }
        // stop any previous page prepper, and start a new one
        if (pagePrep != null) {
            pagePrep.quit();
        }
        pagePrep = new PagePreparer(tmpPagenum, this);
        pagePrep.start();
        setEnabling();
    }

    /**
     * Enable or disable all of the actions based on the current state.
     */
    public void setEnabling() {
        final boolean fileavailable = curFile != null;
        final boolean pageshown = fspp != null ? fspp.getPage() != null : page.getPage() != null;
        final boolean printable = fileavailable && curFile.isPrintable();

        printAction.setEnabled(printable);

        fitHeightAction.setEnabled(pageshown);
        fitAction.setEnabled(pageshown);

        zoomInAction.setEnabled(pageshown);
        zoomOutAction.setEnabled(pageshown);

    }

    /**
     * open a URL to a PDF file. The file is read in and processed with an
     * in-memory buffer.
     *
     * @param url the url
     * @throws java.io.IOException if any.
     */
    public void openFile(final URL url) throws IOException {
        final URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", UserAgent.getUserAgent());
        connection.getHeaderField("Set-Cookie");
        try (final InputStream inputStream = HttpNetwork.openConnectionCheckRedirects(connection)) {
            final ByteBuffer byteBuffer = BytesUtilities.readStream(inputStream);
            openPDFByteBuffer(byteBuffer, url.toString(), url.getFile());
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * open a URL to a PDF file. The file is read in and processed with an
     * in-memory buffer.
     *
     * @param url the url
     * @throws java.io.IOException if any.
     */
    public void openFile(final URL url, final URLConnection httpcon) throws IOException {
        final ByteBuffer byteBuffer = BytesUtilities.readStream(httpcon.getInputStream());
        openPDFByteBuffer(byteBuffer, url.toString(), url.getFile());
    }

    /**
     * <p>
     * Open a specific pdf file. Creates a DocumentInfo from the file, and opens
     * that.
     * </p>
     *
     * <p>
     * <b>Note:</b> Mapping the file locks the file until the PDFFile is closed.
     * </p>
     *
     * @param file the file to open
     * @throws java.io.IOException if any.
     */
    public void openFile(final File file) throws IOException {
        // first open the file for random access
        try (final RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            // extract a file channel
            final FileChannel channel = raf.getChannel();
            // now memory-map a byte-buffer
            final ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            openPDFByteBuffer(buf, file.getPath(), file.getName());
        }
    }

    /**
     * open the ByteBuffer data as a PDFFile and start to process it.
     *
     * @param buf  the buf
     * @param path the path
     * @param name the name
     */
    private void openPDFByteBuffer(final ByteBuffer buf, final String path, final String name) {
        // create a PDFFile from the data
        final PDFFile newfile;

        try {
            newfile = new PDFFile(buf);
        } catch (final IOException e) {
            e.printStackTrace();
            openError(path + " doesn't appear to be a PDF file.");
            return;
        }

        // set up our document
        this.curFile = newfile;
        docName = name;
        setTitle(TITLE + ": " + docName);
        // set up the thumbnails
        if (doThumb) {
            thumbs = new ThumbPanel(curFile);
            thumbs.addPageChangeListener(this);
            thumbscroll.getViewport().setView(thumbs);
            thumbscroll.getViewport().setBackground(Color.gray);
        }
        setEnabling();
        // display page 1.
        forceGotoPage(0);
        navToolbar.setTotalNoOfPages(curFile.getNumPages());
    }

    /**
     * Display a dialog indicating an error.
     *
     * @param message the message
     */
    public void openError(final String message) {
        JOptionPane.showMessageDialog(split, message, "Error opening file", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Open a local file, given a string filename.
     *
     * @param name the name of the file to open
     */
    public void doOpen(final String name) {
        try {
            openFile(new URL(name));
        } catch (final IOException ioe) {
            try {
                openFile(new File(name));
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Open a local file, given a string filename.
     *
     * @param name    the name of the file to open
     * @param url
     * @param httpcon
     */
    public void doOpen(final String name, final URL url, final URLConnection httpcon) {
        try {
            openFile(url, httpcon);
        } catch (final IOException ioe) {
            try {
                openFile(new File(name));
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Posts the Page Setup dialog.
     */
    public void doPageSetup() {
        final PrinterJob pjob = PrinterJob.getPrinterJob();
        pformat = pjob.pageDialog(pformat);
    }


    /**
     * Print the current document.
     */
    public void doPrint() {
        final PrinterJob pjob = PrinterJob.getPrinterJob();
        pjob.setJobName(docName);
        final Book book = new Book();
        final PDFPrintPage pages = new PDFPrintPage(curFile);
        book.append(pages, pformat, curFile.getNumPages());
        pjob.setPageable(book);
        if (pjob.printDialog()) {
            new PrintThread(pages, pjob, this).start();
        }
    }

    /**
     * makes the page fit in the window
     *
     * @param width  a boolean.
     * @param height a boolean.
     */
    public void doFit(final boolean width, final boolean height) {
        assert width || height;

        final Dimension viewSize = scrollPage.getViewport().getSize();

        // calculate viewSize without scrollbars
        if (scrollPage.getHorizontalScrollBar().isVisible()) {
            viewSize.height += scrollPage.getHorizontalScrollBar().getHeight();
        }
        if (scrollPage.getVerticalScrollBar().isVisible()) {
            viewSize.width += scrollPage.getVerticalScrollBar().getWidth();
        }

        final Dimension imageSize = page.getPage().getUnstretchedSize(width ? viewSize.width : 1000000, height ? viewSize.height : 1000000, null);
        final Dimension realImageSize = (Dimension) imageSize.clone();

        // avoid horizontal scrollbar if vertical scrollbar expected
        // and vice versa
        if (imageSize.width > viewSize.width) {
            realImageSize.height -= scrollPage.getHorizontalScrollBar().getHeight();
        }
        if (imageSize.height > viewSize.height) {
            realImageSize.width -= scrollPage.getVerticalScrollBar().getWidth();
        }

        page.setPreferredSize(realImageSize);
        page.revalidate();
    }

    /**
     * Shows or hides the thumbnails by moving the split pane divider.
     *
     * @param show the show
     */
    public void doThumbs(final boolean show) {
        if (show) {
            final int divLoc = thumbs.getPreferredSize().width + thumbscroll.getVerticalScrollBar().getWidth() + 4;
            split.setDividerLocation(divLoc);
        } else {
            split.setDividerLocation(0);
        }
    }

    /**
     * Do zoom.
     *
     * @param factor the factor
     */
    public void doZoom(final float factor) {
        final int MIN_ZOOM_SIZE = 100;
        final int MAX_ZOOM_SIZE = 4000;
        page.setPreferredSize(new Dimension(
                Math.max(MIN_ZOOM_SIZE, Math.min(MAX_ZOOM_SIZE, Math.round(page.getPreferredSize().width * factor))),
                Math.max(MIN_ZOOM_SIZE, Math.min(MAX_ZOOM_SIZE, Math.round(page.getPreferredSize().height * factor)))
        ));
        page.revalidate();
    }

    /**
     * Goes to the next page.
     */
    public void doNext() {
        gotoPage(curpage + 1);
    }

    /**
     * Goes to the previous page.
     */
    public void doPrev() {
        gotoPage(curpage - 1);
    }

    /**
     * Goes to the first page.
     */
    public void doFirst() {
        gotoPage(0);
    }

    /**
     * Goes to the last page.
     */
    public void doLast() {
        gotoPage(curFile.getNumPages() - 1);
    }

    /**
     * Open outline.
     * the root of the outline, or null if there is no outline./
     */
    public void doOutline() {
        OutlineNode outline = null;

        // if the PDF has an outline, display it.
        try {
            outline = curFile.getOutline();
        } catch (final IOException ioe) {
        }
        if (outline != null && olf == null) {
            if (outline.getChildCount() > 0) {
                olf = new JDialog(this, "Outline");
                olf.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                olf.setLocation(this.getLocation());
                final JTree jt = new JTree(outline);
                jt.setRootVisible(false);
                jt.addTreeSelectionListener(this);
                final JScrollPane jsp = new JScrollPane(jt);
                olf.getContentPane().add(jsp);
                olf.pack();
                olf.setVisible(true);
            } else {
                if (olf != null) {
                    olf.setVisible(false);
                    olf = null;
                }
            }
        }
    }

    /**
     * <p>doCloseOutline.</p>
     */
    public void doCloseOutline() {
        if (olf != null) {
            olf.setVisible(false);
            olf.dispose();
            olf = null;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Handle a key press for navigation.
     */
    @Override
    public void keyPressed(final KeyEvent evt) {
        final int code = evt.getKeyCode();
        final char keyChar = evt.getKeyChar();
        switch (code) {
            case KeyEvent.VK_PAGE_UP:
                doPrev();
                break;
            case KeyEvent.VK_PAGE_DOWN:
                doNext();
                break;
            case KeyEvent.VK_HOME:
                doFirst();
                break;
            case KeyEvent.VK_END:
                doLast();
                break;
            default:
                break;
        }

        switch (keyChar) {
            case '+':
                doZoom(ZOOM_FACTOR);
                return;
            case '-':
                doZoom(1f / ZOOM_FACTOR);
                return;
            default:
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyReleased(final KeyEvent evt) {
        // Method not implemented
    }

    /**
     * {@inheritDoc}
     * <p>
     * gets key presses and tries to build a page if they're numeric.
     */
    @Override
    public void keyTyped(final KeyEvent evt) {
        final char key = evt.getKeyChar();
        if (key >= '0' && key <= '9') {
            final int val = key - '0';
            pb.keyTyped(val);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Someone changed the selection of the outline tree. Go to the new page.
     */
    @Override
    public void valueChanged(final TreeSelectionEvent e) {
        if (e.isAddedPath()) {
            final OutlineNode node = (OutlineNode) e.getPath().getLastPathComponent();
            if (node == null) {
                return;
            }
            try {
                final PDFAction action = node.getAction();
                if (action == null) {
                    return;
                }
                if (action instanceof GoToAction) {
                    final PDFDestination dest = ((GoToAction) action).getDestination();
                    if (dest == null) {
                        return;
                    }
                    final PDFObject page = dest.getPage();
                    if (page == null) {
                        return;
                    }
                    final int pageNum = curFile.getPageNumber(page);
                    if (pageNum >= 0) {
                        gotoPage(pageNum);
                    }
                }
            } catch (final IOException eio) {
                log.error(eio.getMessage(), eio);
            }
        }
    }

    /**
     * utility method to get an icon from the resources of this class.
     *
     * @param name the name of the icon
     * @return the icon, or null if the icon wasn't found.
     */
    public Icon getIcon(final String name) {
        Icon icon = null;
        final URL url;
        try {
            url = getClass().getResource(name);
            icon = new ImageIcon(url);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
        return icon;
    }
}
