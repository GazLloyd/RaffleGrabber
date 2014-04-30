package com.gmail.gazlloyd.rafflegrabber.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import javax.swing.*;

public class DropboxPopup extends JDialog {

    private JPanel contentPanel = new JPanel();
	private Action okAction = new OKAction();
	private Action cancelAction = new CancelAction();
	protected File path;


	/**
	 * Create the dialog.
	 */
	public DropboxPopup() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Dropbox Not Found");
		setBounds(100, 100, 342, 258);
        setResizable(false);
        contentPanel.setOpaque(false);
        setContentPane(contentPanel);
		getContentPane().setLayout(new BorderLayout());

		JTextPane messageArea = new JTextPane();
		messageArea.setBounds(10, 11, 306, 165);
        messageArea.setFont(new Font("Ariel", Font.PLAIN, 12));
        messageArea.setEditable(false);
        messageArea.setOpaque(false);
		messageArea.setText("The Raffle Entries folder in the Dropbox was not found. This may be because:" +
                "\r\n\r\n1. Dropbox is not installed.\r\n2. You are not part of the Raffle Entries folder - please contact Gaz." +
                "\r\n3. You set the Dropbox folder to a non-default location." +
                "\r\n\r\nPress OK to open the directory chooser and navigate to the directory you wish to save images to." +
                "\r\nPress Cancel to exit the application.\r\n");

        getContentPane().add(messageArea, BorderLayout.CENTER);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		{
			JButton okButton = new JButton("OK");
			okButton.setAction(okAction);
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setAction(cancelAction);
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
		}

        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        setVisible(true);
        Main.logger.info("Popup constructed and made visible");
	}

	private class OKAction extends AbstractAction {
		public OKAction() {
			putValue(NAME, "OK");
			putValue(SHORT_DESCRIPTION, "Opens the directory chooser to select a new save directory");
		}
		public void actionPerformed(ActionEvent e) {
            Main.logger.info("OK pressed, displaying file chooser");

			JFileChooser directoryChooser = new JFileChooser();
			directoryChooser.setMultiSelectionEnabled(false);
			directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			int returnVal = directoryChooser.showOpenDialog(DropboxPopup.this);
            Main.logger.info("File chooser closed, return val: " + returnVal);
			if(JFileChooser.APPROVE_OPTION == returnVal)
			{
				path = directoryChooser.getSelectedFile();
                Main.logger.info("File chosen: " + path.toPath() + ", closing popup");
				DropboxPopup.this.dispose();
				
				PrintStream out = null;
				try {
					out = new PrintStream(new FileOutputStream(System.getProperty("user.home") + File.separator + ".rafflegrabber"));
					out.print(path.toPath());
				}
				catch (FileNotFoundException exception) {
					
				}
				finally {
					if(out != null) {
						out.close();
					}
				}
				
				
                Main.cont(path);
			}

		}
	}
	private class CancelAction extends AbstractAction {
		public CancelAction() {
			putValue(NAME, "Cancel");
			putValue(SHORT_DESCRIPTION, "Closes the application");
		}
		public void actionPerformed(ActionEvent e) {
            Main.logger.info("Exit pressed, exiting...");
			System.exit(0);
		}
	}

}
