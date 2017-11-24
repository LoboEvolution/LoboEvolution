package org.lobobrowser.html.test;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * A basic File Browser. Requires 1.6+ for the Desktop & SwingWorker classes,
 * amongst other minor things.
 * 
 * Includes support classes FileTableModel & FileTreeCellRenderer.
 * 
 * @TODO Bugs
 *       <li>Fix keyboard focus issues - especially when functions like
 *       rename/delete etc. are called that update nodes & file lists.
 *       <li>Needs more testing in general.
 * 
 * @TODO Functionality
 *       <li>Double clicking a directory in the table, should update the tree
 *       <li>Move progress bar?
 *       <li>Add other file display modes (besides table) in CardLayout?
 *       <li>Menus + other cruft?
 *       <li>Implement history/back
 *       <li>Allow multiple selection
 *       <li>Add file search
 * 
 * @author Andrew Thompson
 * @version 2011-06-08
 * @see http://stackoverflow.com/questions/6182110
 * @license LGPL
 */
class FileBrowser {

	/** Title of the application */
	public static final String APP_TITLE = "FileBro";
	/** Used to open/edit/print files. */
	private Desktop desktop;
	/** Provides nice icons and names for files. */
	private FileSystemView fileSystemView;

	/** currently selected File. */
	private File currentFile;

	/** Main GUI container */
	private JPanel gui;

	/** File-system tree. Built Lazily */
	private JTree tree;
	private DefaultTreeModel treeModel;

	/** Directory listing */
	private JTable table;
	private JProgressBar progressBar;
	/** Table model for File[]. */
	private FileTableModel fileTableModel;
	private ListSelectionListener listSelectionListener;
	private boolean cellSizesSet = false;
	private int rowIconPadding = 6;

	/* File controls. */
	private JButton openFile;
	private JButton printFile;
	private JButton editFile;

	/* File details. */
	private JLabel fileName;
	private JTextField path;
	private JLabel date;
	private JLabel size;
	private JCheckBox readable;
	private JCheckBox writable;
	private JCheckBox executable;
	private JRadioButton isDirectory;
	private JRadioButton isFile;

	public Container getGui() {
		if (gui == null) {
			gui = new JPanel(new BorderLayout(3, 3));
			gui.setBorder(new EmptyBorder(5, 5, 5, 5));

			fileSystemView = FileSystemView.getFileSystemView();
			desktop = Desktop.getDesktop();

			JPanel detailView = new JPanel(new BorderLayout(3, 3));

			table = new JTable();
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setAutoCreateRowSorter(true);
			table.setShowVerticalLines(false);

			listSelectionListener = lse -> {
				int row = table.getSelectionModel().getLeadSelectionIndex();
				setFileDetails(((FileTableModel) table.getModel()).getFile(row));
			};
			table.getSelectionModel().addListSelectionListener(listSelectionListener);
			JScrollPane tableScroll = new JScrollPane(table);
			Dimension d = tableScroll.getPreferredSize();
			tableScroll.setPreferredSize(new Dimension((int) d.getWidth(), (int) d.getHeight() / 2));
			detailView.add(tableScroll, BorderLayout.CENTER);

			// the File tree
			DefaultMutableTreeNode root = new DefaultMutableTreeNode();
			treeModel = new DefaultTreeModel(root);

			TreeSelectionListener treeSelectionListener = tse -> {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tse.getPath().getLastPathComponent();
				showChildren(node);
				setFileDetails((File) node.getUserObject());
			};

			// show the file system roots.
			File[] roots = fileSystemView.getRoots();
			for (File fileSystemRoot : roots) {
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
				root.add(node);
				File[] files = fileSystemView.getFiles(fileSystemRoot, true);
				for (File file : files) {
					if (file.isDirectory()) {
						node.add(new DefaultMutableTreeNode(file));
					}
				}
				//
			}

			tree = new JTree(treeModel);
			tree.setRootVisible(false);
			tree.addTreeSelectionListener(treeSelectionListener);
			tree.setCellRenderer(new FileTreeCellRenderer());
			tree.expandRow(0);
			tree.setSelectionPath(new TreePath("C:\\tmp\\lobo-build"));
			JScrollPane treeScroll = new JScrollPane(tree);

			// as per trashgod tip
			tree.setVisibleRowCount(15);

			Dimension preferredSize = treeScroll.getPreferredSize();
			Dimension widePreferred = new Dimension(200, (int) preferredSize.getHeight());
			treeScroll.setPreferredSize(widePreferred);

			// details for a File
			JPanel fileMainDetails = new JPanel(new BorderLayout(4, 2));
			fileMainDetails.setBorder(new EmptyBorder(0, 6, 0, 6));

			JPanel fileDetailsLabels = new JPanel(new GridLayout(0, 1, 2, 2));
			fileMainDetails.add(fileDetailsLabels, BorderLayout.WEST);

			JPanel fileDetailsValues = new JPanel(new GridLayout(0, 1, 2, 2));
			fileMainDetails.add(fileDetailsValues, BorderLayout.CENTER);

			fileDetailsLabels.add(new JLabel("File", SwingConstants.TRAILING));
			fileName = new JLabel();
			fileDetailsValues.add(fileName);
			fileDetailsLabels.add(new JLabel("Path/name", SwingConstants.TRAILING));
			path = new JTextField(5);
			path.setEditable(false);
			fileDetailsValues.add(path);
			fileDetailsLabels.add(new JLabel("Last Modified", SwingConstants.TRAILING));
			date = new JLabel();
			fileDetailsValues.add(date);
			fileDetailsLabels.add(new JLabel("File size", SwingConstants.TRAILING));
			size = new JLabel();
			fileDetailsValues.add(size);
			fileDetailsLabels.add(new JLabel("Type", SwingConstants.TRAILING));

			JPanel flags = new JPanel(new FlowLayout(FlowLayout.LEADING, 4, 0));

			isDirectory = new JRadioButton("Directory");
			flags.add(isDirectory);

			isFile = new JRadioButton("File");
			flags.add(isFile);
			fileDetailsValues.add(flags);

			JToolBar toolBar = new JToolBar();
			// mnemonics stop working in a floated toolbar
			toolBar.setFloatable(false);

			JButton locateFile = new JButton("Locate");
			locateFile.setMnemonic('l');

			locateFile.addActionListener(ae -> {
				try {
					System.out.println("Locate: " + currentFile.getParentFile());
					desktop.open(currentFile.getParentFile());
				} catch (Throwable t) {
					showThrowable(t);
				}
				gui.repaint();
			});
			toolBar.add(locateFile);

			openFile = new JButton("Open");
			openFile.setMnemonic('o');

			openFile.addActionListener(ae -> {
				try {
					System.out.println("Open: " + currentFile);
					desktop.open(currentFile);
				} catch (Throwable t) {
					showThrowable(t);
				}
				gui.repaint();
			});
			toolBar.add(openFile);

			editFile = new JButton("Edit");
			editFile.setMnemonic('e');
			editFile.addActionListener(ae -> {
				try {
					desktop.edit(currentFile);
				} catch (Throwable t) {
					showThrowable(t);
				}
			});
			toolBar.add(editFile);

			printFile = new JButton("Print");
			printFile.setMnemonic('p');
			printFile.addActionListener(ae -> {
				try {
					desktop.print(currentFile);
				} catch (Throwable t) {
					showThrowable(t);
				}
			});
			toolBar.add(printFile);

			// Check the actions are supported on this platform!
			openFile.setEnabled(desktop.isSupported(Desktop.Action.OPEN));
			editFile.setEnabled(desktop.isSupported(Desktop.Action.EDIT));
			printFile.setEnabled(desktop.isSupported(Desktop.Action.PRINT));

			flags.add(new JLabel("::  Flags"));
			readable = new JCheckBox("Read  ");
			readable.setMnemonic('a');
			flags.add(readable);

			writable = new JCheckBox("Write  ");
			writable.setMnemonic('w');
			flags.add(writable);

			executable = new JCheckBox("Execute");
			executable.setMnemonic('x');
			flags.add(executable);

			int count = fileDetailsLabels.getComponentCount();
			for (int ii = 0; ii < count; ii++) {
				fileDetailsLabels.getComponent(ii).setEnabled(false);
			}

			count = flags.getComponentCount();
			for (int ii = 0; ii < count; ii++) {
				flags.getComponent(ii).setEnabled(false);
			}

			JPanel fileView = new JPanel(new BorderLayout(3, 3));

			fileView.add(toolBar, BorderLayout.NORTH);
			fileView.add(fileMainDetails, BorderLayout.CENTER);

			detailView.add(fileView, BorderLayout.SOUTH);

			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroll, detailView);
			gui.add(splitPane, BorderLayout.CENTER);

			JPanel simpleOutput = new JPanel(new BorderLayout(3, 3));
			progressBar = new JProgressBar();
			simpleOutput.add(progressBar, BorderLayout.EAST);
			progressBar.setVisible(false);

			gui.add(simpleOutput, BorderLayout.SOUTH);

		}
		return gui;
	}

	public void showRootFile() {
		// ensure the main files are displayed
		tree.setSelectionInterval(0, 0);
	}

	private void showThrowable(Throwable t) {
		t.printStackTrace();
		JOptionPane.showMessageDialog(gui, t.toString(), t.getMessage(), JOptionPane.ERROR_MESSAGE);
		gui.repaint();
	}

	/** Update the table on the EDT */
	private void setTableData(final File[] files) {
		SwingUtilities.invokeLater(() -> {
			if (fileTableModel == null) {
				fileTableModel = new FileTableModel();
				table.setModel(fileTableModel);
			}
			table.getSelectionModel().removeListSelectionListener(listSelectionListener);
			fileTableModel.setFiles(files);
			table.getSelectionModel().addListSelectionListener(listSelectionListener);
			if (!cellSizesSet) {
				Icon icon = fileSystemView.getSystemIcon(files[0]);

				// size adjustment to better account for icons
				table.setRowHeight(icon.getIconHeight() + rowIconPadding);

				setColumnWidth(0, -1);
				setColumnWidth(3, 60);
				table.getColumnModel().getColumn(3).setMaxWidth(120);
				setColumnWidth(4, -1);
				setColumnWidth(5, -1);
				setColumnWidth(6, -1);
				setColumnWidth(7, -1);
				setColumnWidth(8, -1);
				setColumnWidth(9, -1);

				cellSizesSet = true;
			}
		});
	}

	private void setColumnWidth(int column, int width) {
		TableColumn tableColumn = table.getColumnModel().getColumn(column);
		if (width < 0) {
			// use the preferred width of the header..
			JLabel label = new JLabel((String) tableColumn.getHeaderValue());
			Dimension preferred = label.getPreferredSize();
			// altered 10->14 as per camickr comment.
			width = (int) preferred.getWidth() + 14;
		}
		tableColumn.setPreferredWidth(width);
		tableColumn.setMaxWidth(width);
		tableColumn.setMinWidth(width);
	}

	/**
	 * Add the files that are contained within the directory of this node.
	 * Thanks to Hovercraft Full Of Eels for the SwingWorker fix.
	 */
	private void showChildren(final DefaultMutableTreeNode node) {
		tree.setEnabled(false);
		progressBar.setVisible(true);
		progressBar.setIndeterminate(true);

		SwingWorker<Void, File> worker = new SwingWorker<Void, File>() {
			@Override
			public Void doInBackground() {
				File file = (File) node.getUserObject();
				if (file.isDirectory()) {
					File[] files = fileSystemView.getFiles(file, true); // !!
					if (node.isLeaf()) {
						for (File child : files) {
							if (child.isDirectory()) {
								publish(child);
							}
						}
					}
					setTableData(files);
				}
				return null;
			}

			@Override
			protected void process(List<File> chunks) {
				for (File child : chunks) {
					node.add(new DefaultMutableTreeNode(child));
				}
			}

			@Override
			protected void done() {
				progressBar.setIndeterminate(false);
				progressBar.setVisible(false);
				tree.setEnabled(true);
			}
		};
		worker.execute();
	}

	/** Update the File details view with the details of this File. */
	private void setFileDetails(File file) {
		currentFile = file;
		Icon icon = fileSystemView.getSystemIcon(file);
		fileName.setIcon(icon);
		fileName.setText(fileSystemView.getSystemDisplayName(file));
		path.setText("C:\\tmp\\lobo-build");
		date.setText(new Date(file.lastModified()).toString());
		size.setText(file.length() + " bytes");
		readable.setSelected(file.canRead());
		writable.setSelected(file.canWrite());
		executable.setSelected(file.canExecute());
		isDirectory.setSelected(file.isDirectory());

		isFile.setSelected(file.isFile());

		JFrame f = (JFrame) gui.getTopLevelAncestor();
		if (f != null) {
			f.setTitle(APP_TITLE + " :: " + fileSystemView.getSystemDisplayName(file));
		}

		gui.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				// Significantly improves the look of the output in
				// terms of the file names returned by FileSystemView!
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception weTried1) {
			}
			JFrame f = new JFrame(APP_TITLE);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			FileBrowser FileBrowser = new FileBrowser();
			f.setContentPane(FileBrowser.getGui());

			try {
				URL urlBig = FileBrowser.getClass().getResource("fb-icon-32x32.png");
				URL urlSmall = FileBrowser.getClass().getResource("fb-icon-16x16.png");
				ArrayList<Image> images = new ArrayList<Image>();
				images.add(ImageIO.read(urlBig));
				images.add(ImageIO.read(urlSmall));
				f.setIconImages(images);
			} catch (Exception weTried2) {
			}

			f.pack();
			f.setLocationByPlatform(true);
			f.setMinimumSize(f.getSize());
			f.setVisible(true);

			FileBrowser.showRootFile();
		});
	}
}

/** A TableModel to hold File[]. */
class FileTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File[] files;
	private FileSystemView fileSystemView = FileSystemView.getFileSystemView();
	private String[] columns = { "Icon", "File", "Path/name", "Size", "Last Modified", "R", "W", "E", "D", "F", };

	FileTableModel() {
		this(new File[0]);
	}

	FileTableModel(File[] files) {
		this.files = files;
	}

	@Override
	public Object getValueAt(int row, int column) {
		File file = files[row];
		Object obj = new Object();
		switch (column) {
		case 0:
			obj = fileSystemView.getSystemIcon(file);
			break;
		case 1:
			obj = fileSystemView.getSystemDisplayName(file);
			break;
		case 2:
			obj = file.getPath();
			break;
		case 3:
			obj = file.length();
			break;
		case 4:
			obj = file.lastModified();
			break;
		case 5:
			obj = file.canRead();
			break;
		case 6:
			obj = file.canWrite();
			break;
		case 7:
			obj = file.canExecute();
			break;
		case 8:
			obj = file.isDirectory();
			break;
		case 9:
			obj = file.isFile();
			break;
		default:
			obj = null;
			break;
		}
		return obj;

	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public Class<?> getColumnClass(int column) {
		switch (column) {
		case 0:
			return ImageIcon.class;
		case 3:
			return Long.class;
		case 4:
			return Date.class;
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			return Boolean.class;
		default:
			return String.class;
		}
		
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public int getRowCount() {
		return files.length;
	}

	public File getFile(int row) {
		return files[row];
	}

	public void setFiles(File[] files) {
		this.files = files;
		fireTableDataChanged();
	}
}

/** A TreeCellRenderer for a File. */
class FileTreeCellRenderer extends DefaultTreeCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FileSystemView fileSystemView;

	private JLabel label;

	FileTreeCellRenderer() {
		label = new JLabel();
		label.setOpaque(true);
		fileSystemView = FileSystemView.getFileSystemView();
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		File file = (File) node.getUserObject();
		label.setIcon(fileSystemView.getSystemIcon(file));
		label.setText(fileSystemView.getSystemDisplayName(file));
		label.setToolTipText(file.getPath());

		if (selected) {
			label.setBackground(backgroundSelectionColor);
			label.setForeground(textSelectionColor);
		} else {
			label.setBackground(backgroundNonSelectionColor);
			label.setForeground(textNonSelectionColor);
		}

		return label;
	}
}