package com.gmail.gazlloyd.rafflegrabber.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.gmail.gazlloyd.rafflegrabber.Entrant;
import com.gmail.gazlloyd.rafflegrabber.ResourceType;
import com.gmail.gazlloyd.rafflegrabber.RaffleImage;
import com.gmail.gazlloyd.rafflegrabber.TimeTools;

public class RaffleImageFrame extends JFrame {

	private JPanel contentPane;
	private BufferedImage img;
	protected Entrant entrant;
	protected HashMap<ResourceType, ResourceField> fields;
	protected JTextField nameField;
	private final Action cancelAction = new CancelAction();
	private final Action amendAction = new AmendAction();
	private final Action submitAction = new SubmitAction();
	protected boolean amended = false;


	public RaffleImageFrame(Entrant entrant) {
        /*
        Panel is subdivided many times
        first division is to two, one on the right with the image (imagePanel) and one on the left with
            the resources (resourcePanel)
            (image panel is not subdivided further)

        second division is into three stacked panels, one for name label and field (namePanel),
            one for the resource labels and fields (valuesPanel),
            and one for the three buttons (buttonsPanel)

        third division is for the two columns in the resource (valuesPanelLeft and valuesPanelRight)

        each resource has its own panel, containing its label and field (a ResourceField)

         */

        Main.logger.info("Creating raffle image frame");
		setTitle("Entrant verification");
        setResizable(false);
		this.entrant = entrant;
		this.img = entrant.getImg().croppedImg;

        //setup content pane
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout());
        contentPane.setAlignmentY(TOP_ALIGNMENT);

        JPanel resourcePanel = new JPanel();
        resourcePanel.setLayout(new BoxLayout(resourcePanel, BoxLayout.Y_AXIS));
        resourcePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel valuesPanel = new JPanel();
        valuesPanel.setLayout(new BoxLayout(valuesPanel, BoxLayout.X_AXIS));
        valuesPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));


        JPanel valuesPanelLeft = new JPanel();
        valuesPanelLeft.setLayout(new BoxLayout(valuesPanelLeft, BoxLayout.Y_AXIS));
        valuesPanelLeft.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel valuesPanelRight = new JPanel();
        valuesPanelRight.setLayout(new BoxLayout(valuesPanelRight, BoxLayout.Y_AXIS));
        valuesPanelRight.setBorder(new EmptyBorder(5, 5, 5, 5));


        JLabel lblPlayerName = new JLabel("Player Name:");
		namePanel.add(lblPlayerName);

		nameField = new JTextField();
        namePanel.add(Box.createRigidArea(new Dimension(10,0)));
		namePanel.add(nameField);
		nameField.setColumns(15);
		nameField.setText(entrant.getName());
		nameField.setEditable(false);
        namePanel.add(Box.createRigidArea(new Dimension(10,0)));


		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setAction(submitAction);
		btnSubmit.setToolTipText("Saves the image and submits the data to a text file");
		buttonPanel.add(btnSubmit);

        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton btnAmend = new JButton("Amend");
		btnAmend.setAction(amendAction);
		btnAmend.setToolTipText("Unlocks the fields and allows editing to fix the values");
		buttonPanel.add(btnAmend);

        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setAction(cancelAction);
		btnCancel.setToolTipText("Disregards this user and closes the dialog");
		buttonPanel.add(btnCancel);

		fields = new HashMap<ResourceType, ResourceField>();
		for (ResourceType r : ResourceType.values())
		{
            ResourceField f = new ResourceField(r, entrant.getResources().get(r));
			fields.put(r, f);

		}

        valuesPanelLeft.add(fields.get(ResourceType.TIMBER));
        valuesPanelLeft.add(fields.get(ResourceType.STONE));
        valuesPanelLeft.add(fields.get(ResourceType.CHARCOAL));
        valuesPanelLeft.add(fields.get(ResourceType.ORE));
        valuesPanelLeft.add(fields.get(ResourceType.BARS));

        valuesPanelRight.add(fields.get(ResourceType.PRECIOUS_ORE));
        valuesPanelRight.add(fields.get(ResourceType.PRECIOUS_BARS));
        valuesPanelRight.add(fields.get(ResourceType.CLOTH));
        valuesPanelRight.add(fields.get(ResourceType.RATIONS));
        valuesPanelRight.add(fields.get(ResourceType.MINIONS));

        valuesPanel.add(valuesPanelLeft);
        valuesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        valuesPanel.add(valuesPanelRight);

        resourcePanel.add(valuesPanel);
        resourcePanel.add(buttonPanel);


        resourcePanel.add(namePanel);
        resourcePanel.add(valuesPanel);
        resourcePanel.add(buttonPanel);

        contentPane.add(resourcePanel);

        JPanel imagePanel = new ImagePanel();
        imagePanel.setPreferredSize(new Dimension(352, 252));
        imagePanel.setSize(new Dimension(352, 252));
        imagePanel.setAlignmentX(LEFT_ALIGNMENT);
        imagePanel.setAlignmentY(TOP_ALIGNMENT);

        contentPane.add(imagePanel);

        pack();

        Main.logger.info("Created raffle image frame");

	}

	private class ImagePanel extends JPanel
	{

		public void paintComponent(Graphics g)
		{
            Main.logger.info("Drawing image");
			g.drawImage(img, 0, 0, RaffleImage.X, RaffleImage.Y, null);
		}
	}


	public void go()
	{
        Main.logger.info("Setting raffle image frame visible");
		setVisible(true);
	}
	private class CancelAction extends AbstractAction {
		public CancelAction() {
			putValue(NAME, "Cancel");
			putValue(SHORT_DESCRIPTION, "Disregards this user and closes the dialog");
		}
		public void actionPerformed(ActionEvent e) {
            Main.logger.info("Cancel button clicked, closing window");
			RaffleImageFrame.this.dispose();
		}
	}
	private class AmendAction extends AbstractAction {
		public AmendAction() {
			putValue(NAME, "Amend");
			putValue(SHORT_DESCRIPTION, "Unlocks the fields and allows editing to fix the values");
		}
		public void actionPerformed(ActionEvent e) {
            Main.logger.info("Amend button clicked, unlocking fields");

			RaffleImageFrame.this.amended = true;
			for (ResourceField r : RaffleImageFrame.this.fields.values())
			{
				r.setEditable(true);
			}

			RaffleImageFrame.this.nameField.setEditable(true);
		}
	}
	private class SubmitAction extends AbstractAction {
		public SubmitAction() {
			putValue(NAME, "Submit");
			putValue(SHORT_DESCRIPTION, "Submits the data in the form");
		}
		public void actionPerformed(ActionEvent e) {
            Main.logger.info("Submit button clicked");

			if (RaffleImageFrame.this.amended)
			{
                Main.logger.info("Frame was amended, double checking total");
				HashMap<ResourceType, Integer> readFields = new HashMap<ResourceType, Integer>();
				int total = 0;
				for (ResourceType rt : ResourceType.values())
				{
					ResourceField rf = RaffleImageFrame.this.fields.get(rt);
					if (rf.isOkay())
					{
						readFields.put(rt, rf.getValue());
						total += rf.getValue();
					}
					else
					{
                        Main.logger.warning("One of the fields contained an invalid value, stopping submit");
						new RaffleErrorPopup("One of more of the fields contains an invalid value");
						return;
					}
				}

				if (total > 2701)
				{
                    Main.logger.warning("The total resources is too large");
					new RaffleErrorPopup("The total resources is too large");
					return;
				}

                Main.logger.info("Values are fine, copying back to entrant object");
				RaffleImageFrame.this.entrant.amend(RaffleImageFrame.this.nameField.getText(), readFields);
			}

			try
			{
                Main.logger.info("Attempting to write to log...");
				BufferedWriter logWriter = new BufferedWriter(new FileWriter(Main.getLog(), true));
				logWriter.newLine();
				logWriter.write(TimeTools.nowL()+","+RaffleImageFrame.this.entrant.printString());
				logWriter.close();

                Main.logger.info("Attempting to save image...");
				RaffleImageFrame.this.entrant.saveImg();
				new SuccessPopup(RaffleImageFrame.this);
				RaffleImageFrame.this.setEnabled(false);

			}

			catch (Exception ex)
			{
                Main.logger.severe("There was an error submitting the data");
				new RaffleErrorPopup("There was an error submitting the data");
				ex.printStackTrace();
			}
		}
	}
}
