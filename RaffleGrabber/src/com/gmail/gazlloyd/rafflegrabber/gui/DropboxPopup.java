package com.gmail.gazlloyd.rafflegrabber.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DropboxPopup extends JDialog {

    public static void main(String args[]) {
        new DropboxPopup();
    }

    /**
     * Create the dialog.
     */
    public DropboxPopup() {
        String nl = System.getProperty("line.separator");

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Dropbox Not Found");
        setBounds(100, 100, 342, 258);
        setResizable(false);
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        setContentPane(contentPanel);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JTextPane messageArea = new JTextPane();
        messageArea.setEditable(false);
        messageArea.setOpaque(false);
        messageArea.setText("The Raffle Entries folder in the Dropbox was not found. This may be because:" +nl+nl+
                "1. Dropbox is not installed."+nl+"2. You are not part of the Raffle Entries folder - please contact Gaz." +nl+
                "3. You set the Dropbox folder to a non-default location." +nl+nl+
                "Press OK to open the directory chooser and navigate to the directory you wish to save images to." +nl+
                "Press Cancel to exit the application."+nl);

        contentPanel.add(messageArea);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPane.setBorder(new EmptyBorder(5,5,5,5));

        {
            JButton okButton = new JButton("OK");
            Action okAction = new OKAction();
            okButton.setAction(okAction);
            okButton.setActionCommand("OK");
            buttonPane.add(okButton);
            getRootPane().setDefaultButton(okButton);
        }

        {
            JButton cancelButton = new JButton("Cancel");
            Action cancelAction = new CancelAction();
            cancelButton.setAction(cancelAction);
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }

        contentPanel.add(buttonPane);
        pack();
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
            if(JFileChooser.APPROVE_OPTION == returnVal) {
                File path = directoryChooser.getSelectedFile();
                Main.logger.info("File chosen: " + path.toPath() + ", closing popup");
                DropboxPopup.this.dispose();

                PrintStream out = null;
                try {
                    Main.logger.info("Attempting to save choice...");
                    if (Main.isWindows) {
                        File dir = new File(System.getenv("APPDATA")+File.separator+".rafflegrabber");
                        Main.logger.info("OS is Windows, saving to: "+dir+File.separator+".rafflegrabber");
                        if (!dir.exists())
                            dir.mkdirs();
                        out = new PrintStream(new FileOutputStream(dir + File.separator + ".rafflegrabber"));
                    }
                    else {
                        out = new PrintStream(new FileOutputStream(System.getProperty("user.home") + File.separator + ".rafflegrabber"));
                        Main.logger.info("Non-Windows OS, saving to "+System.getProperty("user.home")+File.separator+".rafflegrabber");
                    }

                    out.print(path.toPath());
                    Main.logger.info("Choice of folder saved!");
                }
                catch (FileNotFoundException exception) {
                    Main.logger.warning("File not found exception");
                    exception.printStackTrace();
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
