package com.gmail.gazlloyd.rafflegrabber.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class ErrorPopup extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final Action okAction = new OKAction();
	private final Action cancelAction = new CancelAction();
	protected File path;


	/**
	 * Create the dialog.
	 */
	public ErrorPopup() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Dropbox Not Found");
		setBounds(100, 100, 342, 258);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JTextPane txtpnMsg = new JTextPane();
			txtpnMsg.setBounds(10, 11, 306, 165);
			txtpnMsg.setBackground(new Color(255,255,255,0));
			txtpnMsg.setText("The Raffle Entries folder in the Dropbox was not found. This may be because:\r\n\r\n1. Dropbox is not installed.\r\n2. You are not part of the Raffle Entries folder - please contact Gaz.\r\n3. You set the Dropbox folder to a non-default location.\r\n\r\nPress OK to open the directory chooser and navigate to the directory you wish to save images to.\r\nPress Cancel to exit the application.\r\n");
			contentPanel.add(txtpnMsg);
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
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
		}
		setVisible(true);
	}

	private class OKAction extends AbstractAction {
		public OKAction() {
			putValue(NAME, "OK");
			putValue(SHORT_DESCRIPTION, "Opens the directory chooser to select a new save directory");
		}
		public void actionPerformed(ActionEvent e) {
			JFileChooser directoryChooser = new JFileChooser();
			directoryChooser.setMultiSelectionEnabled(false);
			directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			int returnVal = directoryChooser.showOpenDialog(ErrorPopup.this);
			if(JFileChooser.APPROVE_OPTION == returnVal)
			{
				Main.cont(directoryChooser.getSelectedFile());
				ErrorPopup.this.dispose();
			}

		}
	}
	private class CancelAction extends AbstractAction {
		public CancelAction() {
			putValue(NAME, "Cancel");
			putValue(SHORT_DESCRIPTION, "Closes the application");
		}
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

}
