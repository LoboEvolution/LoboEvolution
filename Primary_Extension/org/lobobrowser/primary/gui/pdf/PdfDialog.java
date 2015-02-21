package org.lobobrowser.primary.gui.pdf;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.primary.ext.IconFactory;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PagePanel;

public class PdfDialog extends JFrame {

	private static final long serialVersionUID = 2275364034336356595L;
	private static final Logger logger = Logger.getLogger(PdfDialog.class
			.getName());
	private JButton back = new JButton();
	private JButton next = new JButton();
	private JButton closeButton = new JButton();
	private int pg;
	private int tot_pages;
	private PagePanel panel;
	private PDFFile pdffile;
	private String path;

	public PdfDialog(ClientletResponse response, URL url, int pg) {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.closeButton = new JButton("Close");
		this.pg = pg;
		this.panel = new PagePanel();
		this.path = url.toString();

		this.getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);
		JPanel northPanel = new JPanel(new GridLayout(1, 2));
		northPanel.add(back);
		northPanel.add(next);
		northPanel.add(closeButton);
		this.getContentPane().add(northPanel, BorderLayout.NORTH);
		this.setSize(800, 800);
		this.setVisible(true);

		try {
			this.setup(path);
			this.showPage(pg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		back.addActionListener(new ButtonListener());
		back.setIcon(IconFactory.getInstance().getIcon(
				"/toolbarButtonGraphics/navigation/Back16.gif"));
		back.setToolTipText("Back");
		next.addActionListener(new ButtonListener());
		next.setIcon(IconFactory.getInstance().getIcon(
				"/toolbarButtonGraphics/navigation/Forward16.gif"));
		next.setToolTipText("Next");
		closeButton.setAction(new CloseAction());
		closeButton.setText("Close");
	}

	private class ButtonListener implements ActionListener {

		public ButtonListener() {
			if (pg == 1) {
				back.setEnabled(false);
			}
			if (pg == tot_pages) {
				next.setEnabled(false);
			}
		}

		public void actionPerformed(ActionEvent ae) {
			JButton src = (JButton) ae.getSource();
			if (src.equals(back)) {
				pg--;
				if (pg <= 1) {
					back.setEnabled(false);
				}
				next.setEnabled(true);
				showPage(pg);
			} else {
				pg++;
				if (pg >= tot_pages) {
					next.setEnabled(false);
				}
				back.setEnabled(true);
				showPage(pg);
			}
		}
	}

	protected void showPage(int pg) {
		PDFPage page = pdffile.getPage(pg);
		panel.showPage(page);
		panel.validate();
	}

	protected void setup(String path) throws IOException {

		System.out.println("path: " + path);
		File file = fileToUrl(path);
		System.out.println(file.getName());
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		FileChannel channel = raf.getChannel();
		ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,
				channel.size());
		pdffile = new PDFFile(buf);
		tot_pages = pdffile.getNumPages();
	}

	protected File fileToUrl(String str) throws IOException {
		URL url = new URL(str);
		InputStream is = url.openStream();
		FileOutputStream fos = null;

		String localFile = null;

		StringTokenizer st = new StringTokenizer(url.getFile(), "/");
		File userHome = new File(System.getProperty("user.home"));
		File loboHome = new File(userHome, ".lobo");
		while (st.hasMoreTokens()) {
			localFile = loboHome.getPath() + "\\" + st.nextToken();
		}
				
		fos = new FileOutputStream(localFile);

		int oneChar, count = 0;
		while ((oneChar = is.read()) != -1) {
			fos.write(oneChar);
			count++;
		}
		
		is.close();
		fos.close();

		return new File(localFile);

	}
	
	private class CloseAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			PdfDialog.this.dispose();
		}
	}
}
