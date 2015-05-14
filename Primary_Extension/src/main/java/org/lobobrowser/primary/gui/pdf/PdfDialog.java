/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.gui.pdf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;

import org.lobobrowser.http.SSLCertificate;

import com.sun.pdfview.Flag;
import com.sun.pdfview.FullScreenWindow;
import com.sun.pdfview.OutlineNode;
import com.sun.pdfview.PDFDestination;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFObject;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFPrintPage;
import com.sun.pdfview.PageChangeListener;
import com.sun.pdfview.PagePanel;
import com.sun.pdfview.ThumbPanel;
import com.sun.pdfview.action.GoToAction;
import com.sun.pdfview.action.PDFAction;

/**
 * The Class PdfDialog.
 */
public class PdfDialog extends JFrame implements KeyListener,
TreeSelectionListener, PageChangeListener {
	
	 /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(PdfDialog.class
            .getName());

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant TITLE. */
    public final static String TITLE = "SwingLabs PDF Viewer";

    /** The current PDFFile. */
    PDFFile curFile;

    /** the name of the current document. */
    String docName;

    /** The split between thumbs and page. */
    JSplitPane split;

    /** The thumbnail scroll pane. */
    JScrollPane thumbscroll;

    /** The thumbnail display. */
    ThumbPanel thumbs;

    /** The page display. */
    PagePanel page;

    /** The full screen page display, or null if not in full screen mode. */
    PagePanel fspp;
    // Thread anim;
    /** The current page number (starts at 0), or -1 if no page. */
    int curpage = -1;

    /** the full screen button. */
    JToggleButton fullScreenButton;

    /** the current page number text field. */
    JTextField pageField;

    /** the full screen window, or null if not in full screen mode. */
    FullScreenWindow fullScreen;

    /** the root of the outline, or null if there is no outline. */
    OutlineNode outline = null;

    /** The page format for printing. */
    PageFormat pformat = PrinterJob.getPrinterJob().defaultPage();

    /** true if the thumb panel should exist at all. */
    boolean doThumb = true;

    /** flag to indicate when a newly added document has been announced. */
    Flag docWaiter;

    /** a thread that pre-loads the next page for faster response. */
    PagePreparer pagePrep;

    /** the window containing the pdf outline, or null if one doesn't exist. */
    JDialog olf;

    /** the document menu. */
    JMenu docMenu;

    /**
     * utility method to get an icon from the resources of this class.
     *
     * @param name
     *            the name of the icon
     * @return the icon, or null if the icon wasn't found.
     */
    public Icon getIcon(String name) {
        Icon icon = null;
        URL url = null;
        try {
            url = getClass().getResource(name);
            icon = new ImageIcon(url);
        } catch (Exception e) {
            System.out.println("Couldn't find " + getClass().getName() + "/"
                    + name);
            logger.severe(e.getMessage());
        }
        return icon;
    }

    // / FILE MENU

    /** The page setup action. */
    Action pageSetupAction = new AbstractAction("Page setup...") {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent evt) {
            doPageSetup();
        }
    };

    /** The print action. */
    Action printAction = new AbstractAction("Print...",
            getIcon("/toolbarButtonGraphics/general/Print16.gif")) {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent evt) {
            doPrint();
        }
    };

    /** The close action. */
    Action closeAction = new AbstractAction("Close") {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent evt) {
            doClose();
        }
    };

    /** The quit action. */
    Action quitAction = new AbstractAction("Quit") {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent evt) {
            doQuit();
        }
    };

    /** The zoom tool action. */
    Action zoomToolAction = new AbstractAction("",
            getIcon("/toolbarButtonGraphics/general/ZoomIn16.gif")) {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent evt) {
            doZoomTool();
        }
    };

    /** The fit in window action. */
    Action fitInWindowAction = new AbstractAction("Fit in window",
            getIcon("/toolbarButtonGraphics/general/ZoomOut16.gif")) {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent evt) {
            doFitInWindow();
        }
    };

    /**
     * The Class ThumbAction.
     */
    class ThumbAction extends AbstractAction implements PropertyChangeListener {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 1L;

        /** The is open. */
        boolean isOpen = true;

        /**
         * Instantiates a new thumb action.
         */
        public ThumbAction() {
            super("Hide thumbnails");
        }

        /*
         * (non-Javadoc)
         * @see
         * java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent
         * )
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            int v = ((Integer) evt.getNewValue()).intValue();
            if (v <= 1) {
                isOpen = false;
                putValue(ACTION_COMMAND_KEY, "Show thumbnails");
                putValue(NAME, "Show thumbnails");
            } else {
                isOpen = true;
                putValue(ACTION_COMMAND_KEY, "Hide thumbnails");
                putValue(NAME, "Hide thumbnails");
            }
        }

        /*
         * (non-Javadoc)
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent evt) {
            doThumbs(!isOpen);
        }
    }

    /** The thumb action. */
    ThumbAction thumbAction = new ThumbAction();

    /** The full screen action. */
    Action fullScreenAction = new AbstractAction("Full screen",
            getIcon("/toolbarButtonGraphics/media/Play16.gif")) {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent evt) {
            doFullScreen((evt.getModifiers() & ActionEvent.SHIFT_MASK) != 0);
        }
    };

    /** The next action. */
    Action nextAction = new AbstractAction("Next",
            getIcon("/toolbarButtonGraphics/navigation/Forward16.gif")) {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent evt) {
            doNext();
        }
    };

    /** The prev action. */
    Action prevAction = new AbstractAction("Prev",
            getIcon("/toolbarButtonGraphics/navigation/Back16.gif")) {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent evt) {
            doPrev();
        }
    };

    /**
     * Create a new PdfDialog based on a user, with or without a thumbnail
     * panel.
     *
     * @param useThumbs
     *            true if the thumb panel should exist, false if not.
     */
    public PdfDialog(boolean useThumbs) {
        super(TITLE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                doQuit();
            }
        });
        doThumb = useThumbs;
        init();
    }

    /**
     * Initialize this PdfDialog by creating the GUI.
     */
    @SuppressWarnings("deprecation")
    protected void init() {
        page = new PagePanel();
        page.addKeyListener(this);
        if (doThumb) {
            split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            split.addPropertyChangeListener(
                    JSplitPane.DIVIDER_LOCATION_PROPERTY, thumbAction);
            split.setOneTouchExpandable(true);
            thumbs = new ThumbPanel(null);
            thumbscroll = new JScrollPane(thumbs,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            split.setLeftComponent(thumbscroll);
            split.setRightComponent(page);
            getContentPane().add(split, BorderLayout.CENTER);
        } else {
            getContentPane().add(page, BorderLayout.CENTER);
        }
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        JButton jb;
        jb = new JButton(prevAction);
        jb.setText("");
        toolbar.add(jb);
        pageField = new JTextField("-", 3);
        // pageField.setEnabled(false);
        pageField.setMaximumSize(new Dimension(45, 32));
        pageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                doPageTyped();
            }
        });
        toolbar.add(pageField);
        jb = new JButton(nextAction);
        jb.setText("");
        toolbar.add(jb);
        toolbar.add(Box.createHorizontalGlue());
        fullScreenButton = new JToggleButton(fullScreenAction);
        fullScreenButton.setText("");
        toolbar.add(fullScreenButton);
        fullScreenButton.setEnabled(true);
        toolbar.add(Box.createHorizontalGlue());
        JToggleButton jtb;
        ButtonGroup bg = new ButtonGroup();
        jtb = new JToggleButton(zoomToolAction);
        jtb.setText("");
        bg.add(jtb);
        toolbar.add(jtb);
        jtb = new JToggleButton(fitInWindowAction);
        jtb.setText("");
        bg.add(jtb);
        jtb.setSelected(true);
        toolbar.add(jtb);
        toolbar.add(Box.createHorizontalGlue());
        jb = new JButton(printAction);
        jb.setText("");
        toolbar.add(jb);
        getContentPane().add(toolbar, BorderLayout.NORTH);
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        file.add(closeAction);
        file.addSeparator();
        file.add(pageSetupAction);
        file.add(printAction);
        file.addSeparator();
        file.add(quitAction);
        mb.add(file);
        setJMenuBar(mb);
        setEnabling();
        pack();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - getWidth()) / 2;
        int y = (screen.height - getHeight()) / 2;
        setLocation(x, y);
        if (SwingUtilities.isEventDispatchThread()) {
            show();
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        show();
                    }
                });
            } catch (InvocationTargetException ie) {
                // ignore
            } catch (InterruptedException ie) {
                // ignore
            }
        }
    }

    /**
     * Changes the displayed page, desyncing if we're not on the same page as a
     * presenter.
     *
     * @param pagenum
     *            the page to display
     */
    @Override
    public void gotoPage(int pagenum) {
        if (pagenum < 0) {
            pagenum = 0;
        } else if (pagenum >= curFile.getNumPages()) {
            pagenum = curFile.getNumPages() - 1;
        }
        forceGotoPage(pagenum);
    }

    /**
     * Changes the displayed page.
     *
     * @param pagenum
     *            the page to display
     */
    public void forceGotoPage(int pagenum) {
        if (pagenum <= 0) {
            pagenum = 0;
        } else if (pagenum >= curFile.getNumPages()) {
            pagenum = curFile.getNumPages() - 1;
        }
        // System.out.println("Going to page " + pagenum);
        curpage = pagenum;
        // update the page text field
        pageField.setText(String.valueOf(curpage + 1));
        // fetch the page and show it in the appropriate place
        PDFPage pg = curFile.getPage(pagenum + 1);
        if (fspp != null) {
            fspp.showPage(pg);
            fspp.requestFocus();
        } else {
            page.showPage(pg);
            page.requestFocus();
        }
        // update the thumb panel
        if (doThumb) {
            thumbs.pageShown(pagenum);
        }
        // stop any previous page prepper, and start a new one
        if (pagePrep != null) {
            pagePrep.quit();
        }
        pagePrep = new PagePreparer(pagenum);
        pagePrep.start();
        setEnabling();
    }

    /**
     * A class to pre-cache the next page for better UI response.
     */
    class PagePreparer extends Thread {

        /** The waitfor page. */
        int waitforPage;

        /** The prep page. */
        int prepPage;

        /**
         * Creates a new PagePreparer to prepare the page after the current one.
         *
         * @param waitforPage
         *            the current page number, 0 based
         */
        public PagePreparer(int waitforPage) {
            setDaemon(true);
            setName(getClass().getName());
            this.waitforPage = waitforPage;
            this.prepPage = waitforPage + 1;
        }

        /**
         * Quit.
         */
        public void quit() {
            waitforPage = -1;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            Dimension size = null;
            Rectangle2D clip = null;
            // wait for the current page
            // System.out.println("Preparer waiting for page " + (waitforPage +
            // 1));
            if (fspp != null) {
                fspp.waitForCurrentPage();
                size = fspp.getCurSize();
                clip = fspp.getCurClip();
            } else if (page != null) {
                page.waitForCurrentPage();
                size = page.getCurSize();
                clip = page.getCurClip();
            }
            if (waitforPage == curpage) {
                // don't go any further if the user changed pages.
                // System.out.println("Preparer generating page " + (prepPage +
                // 2));
                PDFPage pdfPage = curFile.getPage(prepPage + 1, true);
                if ((pdfPage != null) && (waitforPage == curpage)) {
                    // don't go any further if the user changed pages
                    // System.out.println("Generating image for page " +
                    // (prepPage + 2));
                    pdfPage.getImage(size.width, size.height, clip, null, true,
                            true);
                    // System.out.println("Generated image for page "+
                    // (prepPage+2));
                }
            }
        }
    }

    /**
     * Enable or disable all of the actions based on the current state.
     */
    public void setEnabling() {
        boolean fileavailable = curFile != null;
        boolean pageshown = ((fspp != null) ? fspp.getPage() != null : page
                .getPage() != null);
        boolean printable = fileavailable && curFile.isPrintable();
        pageField.setEnabled(fileavailable);
        printAction.setEnabled(printable);
        closeAction.setEnabled(fileavailable);
        fullScreenAction.setEnabled(pageshown);
        prevAction.setEnabled(pageshown);
        nextAction.setEnabled(pageshown);
        zoomToolAction.setEnabled(pageshown);
        fitInWindowAction.setEnabled(pageshown);
    }

    /**
     * open a URL to a PDF file. The file is read in and processed with an
     * in-memory buffer.
     *
     * @param url
     *            the url
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void openFile(URL url) throws IOException {
        SSLCertificate.setCertificate();
        URLConnection urlConnection = url.openConnection();
        int contentLength = urlConnection.getContentLength();
        InputStream istr = urlConnection.getInputStream();
        byte[] byteBuf = new byte[contentLength];
        int offset = 0;
        int read = 0;
        while (read >= 0) {
            read = istr.read(byteBuf, offset, contentLength - offset);
            if (read > 0) {
                offset += read;
            }
        }
        if (offset != contentLength) {
            throw new IOException("Could not read all of URL file.");
        }
        ByteBuffer buf = ByteBuffer.allocate(contentLength);
        buf.put(byteBuf);
        openPDFByteBuffer(buf, url.toString(), url.getFile());
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
     * @param file
     *            the file to open
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void openFile(File file) throws IOException {
        // first open the file for random access
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        // extract a file channel
        FileChannel channel = raf.getChannel();
        // now memory-map a byte-buffer
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,
                channel.size());
        openPDFByteBuffer(buf, file.getPath(), file.getName());
        raf.close();
    }

    /**
     * <p>
     * Open a specific pdf file. Creates a DocumentInfo from the file, and opens
     * that.
     * </p>
     *
     * <p>
     * <b>Note:</b> By not memory mapping the file its contents are not locked
     * down while PDFFile is open.
     * </p>
     *
     * @param file
     *            the file to open
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void openFileUnMapped(File file) throws IOException {
        DataInputStream istr = null;
        try {
            // load a pdf from a byte buffer
            // avoid using a RandomAccessFile but fill a ByteBuffer directly
            istr = new DataInputStream(new FileInputStream(file));
            long len = file.length();
            if (len > Integer.MAX_VALUE) {
                throw new IOException("File too long to decode: "
                        + file.getName());
            }
            int contentLength = (int) len;
            byte[] byteBuf = new byte[contentLength];
            int offset = 0;
            int read = 0;
            while (read >= 0) {
                read = istr.read(byteBuf, offset, contentLength - offset);
                if (read > 0) {
                    offset += read;
                }
            }
            ByteBuffer buf = ByteBuffer.allocate(contentLength);
            buf.put(byteBuf);
            openPDFByteBuffer(buf, file.getPath(), file.getName());
        } catch (FileNotFoundException e) {
            logger.severe(e.getMessage());
        } catch (IOException e) {
            logger.severe(e.getMessage());
        } finally {
            if (istr != null) {
                try {
                    istr.close();
                } catch (Exception e) {
                    // ignore error on close
                }
            }
        }
    }

    /**
     * open the ByteBuffer data as a PDFFile and start to process it.
     *
     * @param buf
     *            the buf
     * @param path
     *            the path
     * @param name
     *            the name
     */
    private void openPDFByteBuffer(ByteBuffer buf, String path, String name) {
        // create a PDFFile from the data
        PDFFile newfile = null;
        try {
            newfile = new PDFFile(buf);
        } catch (IOException ioe) {
            openError(path + " doesn't appear to be a PDF file.");
            return;
        }
        // Now that we're reasonably sure this document is real, close the
        // old one.
        doClose();
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
        // if the PDF has an outline, display it.
        try {
            outline = curFile.getOutline();
        } catch (IOException ioe) {
        }
        if (outline != null) {
            if (outline.getChildCount() > 0) {
                olf = new JDialog(this, "Outline");
                olf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                olf.setLocation(this.getLocation());
                JTree jt = new JTree(outline);
                jt.setRootVisible(false);
                jt.addTreeSelectionListener(this);
                JScrollPane jsp = new JScrollPane(jt);
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
     * Display a dialog indicating an error.
     *
     * @param message
     *            the message
     */
    public void openError(String message) {
        JOptionPane.showMessageDialog(split, message, "Error opening file",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * A file filter for PDF files.
     */
    FileFilter pdfFilter = new FileFilter() {
        @Override
        public boolean accept(File f) {
            return f.isDirectory() || f.getName().endsWith(".pdf");
        }

        @Override
        public String getDescription() {
            return "Choose a PDF file";
        }
    };

    /** The prev dir choice. */
    private File prevDirChoice;

    /**
     * Ask the user for a PDF file to open from the local file system.
     */
    public void doOpen() {
        try {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(prevDirChoice);
            fc.setFileFilter(pdfFilter);
            fc.setMultiSelectionEnabled(false);
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    prevDirChoice = fc.getSelectedFile();
                    openFile(fc.getSelectedFile());
                } catch (IOException e) {
                    logger.severe(e.getMessage());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(split,
                    "Opening files from your local "
                            + "disk is not available\nfrom the "
                            + "Java Web Start version of this " + "program.\n",
                            "Error opening directory", JOptionPane.ERROR_MESSAGE);
            logger.severe(e.getMessage());
        }
    }

    /**
     * Open a local file, given a string filename.
     *
     * @param name
     *            the name of the file to open
     */
    public void doOpen(String name) {
        try {
            openFile(new URL(name));
        } catch (IOException ioe) {
            try {
                openFile(new File(name));
            } catch (IOException ex) {
                Logger.getLogger(PdfDialog.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }

    /**
     * Posts the Page Setup dialog.
     */
    public void doPageSetup() {
        PrinterJob pjob = PrinterJob.getPrinterJob();
        pformat = pjob.pageDialog(pformat);
    }

    /**
     * A thread for printing in.
     */
    class PrintThread extends Thread {

        /** The pt pages. */
        PDFPrintPage ptPages;

        /** The pt pjob. */
        PrinterJob ptPjob;

        /**
         * Instantiates a new prints the thread.
         *
         * @param pages
         *            the pages
         * @param pjob
         *            the pjob
         */
        public PrintThread(PDFPrintPage pages, PrinterJob pjob) {
            ptPages = pages;
            ptPjob = pjob;
            setName(getClass().getName());
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            try {
                ptPages.show(ptPjob);
                ptPjob.print();
            } catch (PrinterException pe) {
                JOptionPane.showMessageDialog(PdfDialog.this,
                        "Printing Error: " + pe.getMessage(), "Print Aborted",
                        JOptionPane.ERROR_MESSAGE);
            }
            ptPages.hide();
        }
    }

    /**
     * Print the current document.
     */
    public void doPrint() {
        PrinterJob pjob = PrinterJob.getPrinterJob();
        pjob.setJobName(docName);
        Book book = new Book();
        PDFPrintPage pages = new PDFPrintPage(curFile);
        book.append(pages, pformat, curFile.getNumPages());
        pjob.setPageable(book);
        if (pjob.printDialog()) {
            new PrintThread(pages, pjob).start();
        }
    }

    /**
     * Close the current document.
     */
    public void doClose() {
        if (thumbs != null) {
            thumbs.stop();
        }
        if (olf != null) {
            olf.setVisible(false);
            olf = null;
        }
        if (doThumb) {
            thumbs = new ThumbPanel(null);
            thumbscroll.getViewport().setView(thumbs);
        }
        setFullScreenMode(false, false);
        page.showPage(null);
        curFile = null;
        setTitle(TITLE);
        setEnabling();
    }

    /**
     * Shuts down all known threads. This ought to cause the JVM to quit if the
     * PdfDialog is the only application running.
     */
    public void doQuit() {
        doClose();
        dispose();
    }

    /**
     * Turns on zooming.
     */
    public void doZoomTool() {
        if (fspp == null) {
            page.useZoomTool(true);
        }
    }

    /**
     * Turns off zooming; makes the page fit in the window.
     */
    public void doFitInWindow() {
        if (fspp == null) {
            page.useZoomTool(false);
            page.setClip(null);
        }
    }

    /**
     * Shows or hides the thumbnails by moving the split pane divider.
     *
     * @param show
     *            the show
     */
    public void doThumbs(boolean show) {
        if (show) {
            split.setDividerLocation(thumbs.getPreferredSize().width
                    + thumbscroll.getVerticalScrollBar().getWidth() + 4);
        } else {
            split.setDividerLocation(0);
        }
    }

    /**
     * Enter full screen mode.
     *
     * @param force
     *            true if the user should be prompted for a screen to use in a
     *            multiple-monitor setup. If false, the user will only be
     *            prompted once.
     */
    public void doFullScreen(boolean force) {
        setFullScreenMode(fullScreen == null, force);
    }

    /**
     * Do zoom.
     *
     * @param factor
     *            the factor
     */
    public void doZoom(double factor) {
    }

    // public void doOpenMeetingDoc(DocumentInfo doc) {
    //}
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
     * Goes to the page that was typed in the page number text field.
     */
    public void doPageTyped() {
        int pagenum = -1;
        try {
            pagenum = Integer.parseInt(pageField.getText()) - 1;
        } catch (NumberFormatException nfe) {
        }
        if (pagenum >= curFile.getNumPages()) {
            pagenum = curFile.getNumPages() - 1;
        }
        if (pagenum >= 0) {
            if (pagenum != curpage) {
                gotoPage(pagenum);
            }
        } else {
            pageField.setText(String.valueOf(curpage));
        }
    }

    /**
     * Runs the FullScreenMode change in another thread.
     */
    class PerformFullScreenMode implements Runnable {

        /** The force. */
        boolean force;

        /**
         * Instantiates a new perform full screen mode.
         *
         * @param forcechoice
         *            the forcechoice
         */
        public PerformFullScreenMode(boolean forcechoice) {
            force = forcechoice;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            fspp = new PagePanel();
            fspp.setBackground(Color.black);
            page.showPage(null);
            fullScreen = new FullScreenWindow(fspp, force);
            fspp.addKeyListener(PdfDialog.this);
            gotoPage(curpage);
            fullScreenAction.setEnabled(true);
        }
    }

    /**
     * Starts or ends full screen mode.
     *
     * @param full
     *            true to enter full screen mode, false to leave
     * @param force
     *            true if the user should be prompted for a screen to use the
     *            second time full screen mode is entered.
     */
    public void setFullScreenMode(boolean full, boolean force) {
        // curpage= -1;
        if (full && (fullScreen == null)) {
            fullScreenAction.setEnabled(false);
            new Thread(new PerformFullScreenMode(force), getClass().getName()
                    + ".setFullScreenMode").start();
            fullScreenButton.setSelected(true);
        } else if (!full && (fullScreen != null)) {
            fullScreen.close();
            fspp = null;
            fullScreen = null;
            gotoPage(curpage);
            fullScreenButton.setSelected(false);
        }
    }

    /**
     * Handle a key press for navigation.
     *
     * @param evt
     *            the evt
     */
    @Override
    public void keyPressed(KeyEvent evt) {
        int code = evt.getKeyCode();
        if (code == KeyEvent.VK_LEFT) {
            doPrev();
        } else if (code == KeyEvent.VK_RIGHT) {
            doNext();
        } else if (code == KeyEvent.VK_UP) {
            doPrev();
        } else if (code == KeyEvent.VK_DOWN) {
            doNext();
        } else if (code == KeyEvent.VK_HOME) {
            doFirst();
        } else if (code == KeyEvent.VK_END) {
            doLast();
        } else if (code == KeyEvent.VK_PAGE_UP) {
            doPrev();
        } else if (code == KeyEvent.VK_PAGE_DOWN) {
            doNext();
        } else if (code == KeyEvent.VK_SPACE) {
            doNext();
        } else if (code == KeyEvent.VK_ESCAPE) {
            setFullScreenMode(false, false);
        }
    }

    /**
     * Combines numeric key presses to build a multi-digit page number.
     */
    class PageBuilder implements Runnable {

        /** The value. */
        int value = 0;

        /** The timeout. */
        long timeout;

        /** The anim. */
        Thread anim;

        /** The Constant TIMEOUT. */
        static final long TIMEOUT = 500;

        /**
         * add the digit to the page number and start the timeout thread.
         *
         * @param keyval
         *            the keyval
         */
        public synchronized void keyTyped(int keyval) {
            value = (value * 10) + keyval;
            timeout = System.currentTimeMillis() + TIMEOUT;
            if (anim == null) {
                anim = new Thread(this);
                anim.setName(getClass().getName());
                anim.start();
            }
        }

        /**
         * waits for the timeout, and if time expires, go to the specified page
         * number.
         */
        @Override
        public void run() {
            long now, then;
            synchronized (this) {
                now = System.currentTimeMillis();
                then = timeout;
            }
            while (now < then) {
                try {
                    Thread.sleep(timeout - now);
                } catch (InterruptedException ie) {
                }
                synchronized (this) {
                    now = System.currentTimeMillis();
                    then = timeout;
                }
            }
            synchronized (this) {
                gotoPage(value - 1);
                anim = null;
                value = 0;
            }
        }
    }

    /** The pb. */
    PageBuilder pb = new PageBuilder();

    /*
     * (non-Javadoc)
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    @Override
    public void keyReleased(KeyEvent evt) {
    }

    /**
     * gets key presses and tries to build a page if they're numeric.
     *
     * @param evt
     *            the evt
     */
    @Override
    public void keyTyped(KeyEvent evt) {
        char key = evt.getKeyChar();
        if ((key >= '0') && (key <= '9')) {
            int val = key - '0';
            pb.keyTyped(val);
        }
    }

    /**
     * Someone changed the selection of the outline tree. Go to the new page.
     *
     * @param e
     *            the e
     */
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        if (e.isAddedPath()) {
            OutlineNode node = (OutlineNode) e.getPath().getLastPathComponent();
            if (node == null) {
                return;
            }
            try {
                PDFAction action = node.getAction();
                if (action == null) {
                    return;
                }
                if (action instanceof GoToAction) {
                    PDFDestination dest = ((GoToAction) action)
                            .getDestination();
                    if (dest == null) {
                        return;
                    }
                    PDFObject page = dest.getPage();
                    if (page == null) {
                        return;
                    }
                    int pageNum = curFile.getPageNumber(page);
                    if (pageNum >= 0) {
                        gotoPage(pageNum);
                    }
                }
            } catch (IOException ioe) {
                logger.severe(ioe.getMessage());
            }
        }
    }
}
