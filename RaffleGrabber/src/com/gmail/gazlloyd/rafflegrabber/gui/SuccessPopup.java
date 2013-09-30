package com.gmail.gazlloyd.rafflegrabber.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class SuccessPopup extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final Action okAction = new SwingAction();
	private RaffleImageFrame rif;

	/**
	 * Create the dialog.
	 */
	public SuccessPopup(RaffleImageFrame r) {
		setTitle("Success!");
		setIconImage(Toolkit.getDefaultToolkit().getImage(RaffleErrorPopup.class.getResource("/templates/success.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.rif = r;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel panel = new IconPanel();
			contentPanel.add(panel);
		}
		{
			JTextPane txtpnUserHasSuccessfully = new JTextPane();
			txtpnUserHasSuccessfully.setText("User has successfully been submitted");
			txtpnUserHasSuccessfully.setEditable(false);
			contentPanel.add(txtpnUserHasSuccessfully);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setAction(okAction);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		this.pack();
		this.setVisible(true);
		
	}
	
	private class IconPanel extends JPanel
	{
		BufferedImage img;
		public IconPanel()
		{
			try
			{
				img = ImageIO.read(SuccessPopup.class.getClassLoader().getResourceAsStream("templates/success.png"));
			}
			
			catch (IOException e)
			{
			}
		}
		
		public void paint(Graphics g)
		{
			g.drawImage(img, 0, 0, null);
		}
		
		public Dimension getPreferredSize()
		{
			return new Dimension(30,30);
		}
	}
	

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "OK");
			putValue(SHORT_DESCRIPTION, "OK");
		}
		public void actionPerformed(ActionEvent e) {
			rif.dispose();
			SuccessPopup.this.dispose();
		}
	}
}
