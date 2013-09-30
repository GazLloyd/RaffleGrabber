package com.gmail.gazlloyd.rafflegrabber.gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import com.gmail.gazlloyd.rafflegrabber.Entrant;
import com.gmail.gazlloyd.rafflegrabber.ImageHandler;
import com.gmail.gazlloyd.rafflegrabber.RaffleImageException;

public class RaffleFrame extends JFrame {

	private JPanel contentPane;
	private final Action loadAction = new LoadAction();
	private final Action exitAction = new ExitAction();
	private JFileChooser fc;
	private ImageHandler ih;
	private final Action captureAction = new CaptureAction();


	/**
	 * Create the frame.
	 */
	public RaffleFrame(ImageHandler ih, File path) {
		this.ih = ih;

		setTitle("Raffle Image Grabber");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 275, 251);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton loadButton = new JButton("Load an image");
		loadButton.setAction(loadAction);
		loadButton.setBounds(65, 111, 113, 23);
		contentPane.add(loadButton);

		JButton exitButton = new JButton("Exit");
		exitButton.setAction(exitAction);
		exitButton.setBounds(75, 179, 89, 23);
		contentPane.add(exitButton);

		JButton btnNewButton_2 = new JButton("Capture an image");
		btnNewButton_2.setAction(captureAction);
		btnNewButton_2.setBounds(51, 145, 141, 23);
		contentPane.add(btnNewButton_2);

		JTextPane txtpnWelcomeToThe = new JTextPane();
		txtpnWelcomeToThe.setEditable(false);
		txtpnWelcomeToThe.setBackground(new Color(255,255,255,0));
		txtpnWelcomeToThe.setText("Welcome to the raffle image grabber!\r\n\r\nPress 'Load Image' to load in an already captured image.\r\nPress 'Capture Image' to obtain an image from the screen.");
		txtpnWelcomeToThe.setBounds(10, 11, 239, 88);
		contentPane.add(txtpnWelcomeToThe);

		fc = new JFileChooser(path);
		fc.setMultiSelectionEnabled(false);
		ImageFilter filter = new ImageFilter();
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setAccessory(new ImagePreview(fc));


	}
	private class LoadAction extends AbstractAction {
		public LoadAction() {
			putValue(NAME, "Load an image");
			putValue(SHORT_DESCRIPTION, "Load an already-saved image");
		}
		public void actionPerformed(ActionEvent e) {

			int returnVal = fc.showOpenDialog(RaffleFrame.this);

			if(JFileChooser.APPROVE_OPTION == returnVal)
			{
				File file = fc.getSelectedFile();
				BufferedImage img = ih.loadImage(file);
				try
				{
					Entrant entrant = new Entrant(img);
					System.out.println(entrant);
					RaffleImageFrame rif = new RaffleImageFrame(entrant);
					rif.go();
				}
				catch (RaffleImageException rie)
				{
					new RaffleErrorPopup(rie.msg);
				}

			}
		}
	}
	private class ExitAction extends AbstractAction {
		public ExitAction() {
			putValue(NAME, "Exit");
			putValue(SHORT_DESCRIPTION, "Exit the raffle grabber");
		}
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	private class CaptureAction extends AbstractAction {
		public CaptureAction() {
			putValue(NAME, "Capture an image");
			putValue(SHORT_DESCRIPTION, "Capture an image from the screen");
		}
		public void actionPerformed(ActionEvent e) {
			BufferedImage img;
			try
			{
				Robot r = new Robot();
				img = r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
				Entrant entrant = new Entrant(img);
				System.out.println(entrant);
				RaffleImageFrame rif = new RaffleImageFrame(entrant);
				rif.go();
			}
			catch (RaffleImageException rie)
			{
				new RaffleErrorPopup(rie.msg);
			}

			catch (AWTException ex)
			{
				ex.printStackTrace();
			}

		}
	}
}
