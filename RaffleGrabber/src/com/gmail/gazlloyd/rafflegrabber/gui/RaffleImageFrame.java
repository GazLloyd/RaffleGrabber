package com.gmail.gazlloyd.rafflegrabber.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.gmail.gazlloyd.rafflegrabber.Entrant;
import com.gmail.gazlloyd.rafflegrabber.Entrant.ResourceType;
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

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RaffleImageFrame frame = new RaffleImageFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public RaffleImageFrame(Entrant entrant) {
        Main.logger.info("Creating raffle image frame");
		setTitle("Entrant verification");
        setResizable(true);
		this.entrant = entrant;
		this.img = entrant.getImg().croppedImg;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setBounds(100, 100, 740, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout());
        contentPane.setAlignmentY(TOP_ALIGNMENT);

        JPanel resourcePane = new JPanel();
        resourcePane.setLayout(new BoxLayout(resourcePane, BoxLayout.Y_AXIS));
        resourcePane.setBorder(new EmptyBorder(5,5,5,5));

        JPanel namePane = new JPanel();
        namePane.setLayout(new BoxLayout(namePane, BoxLayout.X_AXIS));
        namePane.setBorder(new EmptyBorder(5,5,5,5));

        JPanel valuesPane = new JPanel();
        valuesPane.setLayout(new BoxLayout(valuesPane, BoxLayout.X_AXIS));
        valuesPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
        buttonPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel valuesPane1 = new JPanel();
        valuesPane1.setLayout(new BoxLayout(valuesPane1, BoxLayout.Y_AXIS));
        valuesPane1.setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel valuesPane2 = new JPanel();
        valuesPane2.setLayout(new BoxLayout(valuesPane2, BoxLayout.Y_AXIS));
        valuesPane2.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel timberPanel = new JPanel();
        timberPanel.setLayout(new BoxLayout(timberPanel, BoxLayout.X_AXIS));
        timberPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JPanel stonePanel = new JPanel();
        stonePanel.setLayout(new BoxLayout(stonePanel, BoxLayout.X_AXIS));
        stonePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel coalPanel = new JPanel();
        coalPanel.setLayout(new BoxLayout(coalPanel, BoxLayout.X_AXIS));
        coalPanel.setBorder(new EmptyBorder(5,5,5,5));

        JPanel orePanel = new JPanel();
        orePanel.setLayout(new BoxLayout(orePanel, BoxLayout.X_AXIS));
        orePanel.setBorder(new EmptyBorder(5,5,5,5));

        JPanel barsPanel = new JPanel();
        barsPanel.setLayout(new BoxLayout(barsPanel, BoxLayout.X_AXIS));
        barsPanel.setBorder(new EmptyBorder(5,5,5,5));

        JPanel porePanel = new JPanel();
        porePanel.setLayout(new BoxLayout(porePanel, BoxLayout.X_AXIS));
        porePanel.setBorder(new EmptyBorder(5,5,5,5));

        JPanel pbarsPanel = new JPanel();
        pbarsPanel.setLayout(new BoxLayout(pbarsPanel, BoxLayout.X_AXIS));
        pbarsPanel.setBorder(new EmptyBorder(5,5,5,5));

        JPanel clothPanel = new JPanel();
        clothPanel.setLayout(new BoxLayout(clothPanel, BoxLayout.X_AXIS));
        clothPanel.setBorder(new EmptyBorder(5,5,5,5));

        JPanel rationsPanel = new JPanel();
        rationsPanel.setLayout(new BoxLayout(rationsPanel, BoxLayout.X_AXIS));
        rationsPanel.setBorder(new EmptyBorder(5,5,5,5));

        JPanel minionsPanel = new JPanel();
        minionsPanel.setLayout(new BoxLayout(minionsPanel, BoxLayout.X_AXIS));
        minionsPanel.setBorder(new EmptyBorder(5,5,5,5));


        JLabel lblPlayerName = new JLabel("Player Name:");
		//lblPlayerName.setBounds(23, 24, 75, 14);
		namePane.add(lblPlayerName);

		nameField = new JTextField();
		//nameField.setBounds(123, 21, 86, 20);
		namePane.add(nameField);
		nameField.setColumns(10);
		nameField.setText(entrant.getName());
		nameField.setEditable(false);

		JLabel lblTimber = new JLabel("Timber");
		//lblTimber.setBounds(23, 73, 46, 14);
		timberPanel.add(lblTimber);
        timberPanel.add(Box.createRigidArea(new Dimension(5,0)));
        timberPanel.add(Box.createHorizontalGlue());

		JLabel lblPreciousOre = new JLabel("Precious Ore");
		//lblPreciousOre.setBounds(157, 73, 73, 14);
		porePanel.add(lblPreciousOre);
        porePanel.add(Box.createRigidArea(new Dimension(5,0)));
        porePanel.add(Box.createHorizontalGlue());

		JLabel lblStone = new JLabel("Stone");
		//lblStone.setBounds(23, 98, 46, 14);
		stonePanel.add(lblStone);
        stonePanel.add(Box.createRigidArea(new Dimension(5,0)));
        stonePanel.add(Box.createHorizontalGlue());

		JLabel lblPreciousBars = new JLabel("Precious Bars");
		//lblPreciousBars.setBounds(157, 98, 73, 14);
		pbarsPanel.add(lblPreciousBars);
        pbarsPanel.add(Box.createRigidArea(new Dimension(5,0)));
        pbarsPanel.add(Box.createHorizontalGlue());

		JLabel lblCoal = new JLabel("Coal");
		//lblCoal.setBounds(23, 123, 46, 14);
		coalPanel.add(lblCoal);
        coalPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        coalPanel.add(Box.createHorizontalGlue());

		JLabel lblCloth = new JLabel("Cloth");
		//lblCloth.setBounds(157, 123, 46, 14);
		clothPanel.add(lblCloth);
        clothPanel.add(Box.createRigidArea(new Dimension(5,0)));
        clothPanel.add(Box.createHorizontalGlue());

		JLabel lblOre = new JLabel("Ore");
		//lblOre.setBounds(23, 148, 46, 14);
		orePanel.add(lblOre);
        orePanel.add(Box.createRigidArea(new Dimension(5,0)));
        orePanel.add(Box.createHorizontalGlue());

		JLabel lblRations = new JLabel("Rations");
		//lblRations.setBounds(157, 148, 46, 14);
		rationsPanel.add(lblRations);
        rationsPanel.add(Box.createRigidArea(new Dimension(5,0)));
        rationsPanel.add(Box.createHorizontalGlue());

		JLabel lblBars = new JLabel("Bars");
		//lblBars.setBounds(23, 173, 46, 14);
		barsPanel.add(lblBars);
        barsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        barsPanel.add(Box.createHorizontalGlue());

		JLabel lblMinions = new JLabel("Minions");
		//lblMinions.setBounds(157, 173, 46, 14);
		minionsPanel.add(lblMinions);
        minionsPanel.add(Box.createRigidArea(new Dimension(5,0)));
        minionsPanel.add(Box.createHorizontalGlue());

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setAction(submitAction);
		btnSubmit.setToolTipText("Saves the image and submits the data to a text file");
		//btnSubmit.setBounds(10, 220, 89, 23);
		buttonPane.add(btnSubmit);

        buttonPane.add(Box.createRigidArea(new Dimension(10,0)));

        JButton btnAmend = new JButton("Amend");
		btnAmend.setAction(amendAction);
		btnAmend.setToolTipText("Unlocks the fields and allows editing to fix the values");
		//btnAmend.setBounds(109, 220, 89, 23);
		buttonPane.add(btnAmend);

        buttonPane.add(Box.createRigidArea(new Dimension(10,0)));

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setAction(cancelAction);
		btnCancel.setToolTipText("Disregards this user and closes the dialog");
		//btnCancel.setBounds(208, 220, 89, 23);
		buttonPane.add(btnCancel);

		fields = new HashMap();
		for (ResourceType r : ResourceType.values())
		{
            ResourceField f = new ResourceField(r);
			fields.put(r, f);

		}

        timberPanel.add(fields.get(ResourceType.TIMBER));
        valuesPane1.add(timberPanel);

        stonePanel.add(fields.get(ResourceType.STONE));
        valuesPane1.add(stonePanel);

        coalPanel.add(fields.get(ResourceType.CHARCOAL));
        valuesPane1.add(coalPanel);

        orePanel.add(fields.get(ResourceType.ORE));
        valuesPane1.add(orePanel);

        barsPanel.add(fields.get(ResourceType.BARS));
        valuesPane1.add(barsPanel);

        porePanel.add(fields.get(ResourceType.PRECIOUS_ORE));
        valuesPane2.add(porePanel);

        pbarsPanel.add(fields.get(ResourceType.PRECIOUS_BARS));
        valuesPane2.add(pbarsPanel);

        clothPanel.add(fields.get(ResourceType.CLOTH));
        valuesPane2.add(clothPanel);

        rationsPanel.add(fields.get(ResourceType.RATIONS));
        valuesPane2.add(rationsPanel);

        minionsPanel.add(fields.get(ResourceType.MINIONS));
        valuesPane2.add(minionsPanel);

        valuesPane.add(valuesPane1);
        valuesPane.add(Box.createRigidArea(new Dimension(0,10)));
        valuesPane.add(valuesPane2);

        resourcePane.add(valuesPane);
        resourcePane.add(buttonPane);


        resourcePane.add(namePane);
        resourcePane.add(valuesPane);
        resourcePane.add(buttonPane);

        contentPane.add(resourcePane);

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
            this.setSize(RaffleImage.X, RaffleImage.Y);
            g.setClip(0,0,RaffleImage.X,RaffleImage.Y);
			g.drawImage(img, 0, 0, RaffleImage.X, RaffleImage.Y, null);
            Main.logger.info("Image drawn, clip: "+g.getClipBounds()+", size: "+this.getSize());
		}
	}

	private class ResourceField extends JTextField
	{
        final int X = 58;
        final int Y = 20;
		ResourceType type;
		int value=0;
		boolean okay = true;

		public ResourceField(ResourceType type)
		{

            setMaximumSize(new Dimension(X, Y));
            setMinimumSize(new Dimension(X, Y));
            setPreferredSize(new Dimension(X, Y));
            setSize(X,Y);

			this.type = type;
			this.value = entrant.getResources().get(type);

            Main.logger.info("Creating resource field for " + type + " with value " + value);

			setText(Integer.toString(value));
			setHorizontalAlignment(SwingConstants.RIGHT);
			setEditable(false);
			addKeyListener(new KeyAdapter(){
				public void keyReleased(KeyEvent e) {
					int i;
					try
					{
						i = Integer.parseInt(getText());
						if (i > 2700)
						{
                            Main.logger.info("Detected that field is greater than 2700, setting to red");
							setBackground(Color.RED);
							okay = false;
						}

						else
						{

                            Main.logger.info("Resource value is less than 2700, setting to white");
							setBackground(Color.WHITE);
							value = i;
							okay = true;
						}

					}

					catch (NumberFormatException ex)
					{
                        Main.logger.warning("The value inserted was an invalid number, setting to red");
						setBackground(Color.RED);
						okay = false;
					}



				}
			});
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
				HashMap<ResourceType, Integer> readFields = new HashMap();
				int total = 0;
				for (ResourceType rt : ResourceType.values())
				{
					ResourceField rf = RaffleImageFrame.this.fields.get(rt);
					if (rf.okay)
					{
						readFields.put(rt, rf.value);
						total += rf.value;
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
