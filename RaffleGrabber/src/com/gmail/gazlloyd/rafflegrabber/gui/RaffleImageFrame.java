package com.gmail.gazlloyd.rafflegrabber.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.gmail.gazlloyd.rafflegrabber.Entrant;
import com.gmail.gazlloyd.rafflegrabber.Entrant.ResourceType;
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
		this.entrant = entrant;
		this.img = entrant.getImg().croppedImg;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 740, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel imagePanel = new ImagePanel();
		imagePanel.setBounds(362, 11, 352, 252);
		contentPane.add(imagePanel);

		JLabel lblPlayerName = new JLabel("Player Name:");
		lblPlayerName.setBounds(23, 24, 75, 14);
		contentPane.add(lblPlayerName);

		nameField = new JTextField();
		nameField.setBounds(123, 21, 86, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);
		nameField.setText(entrant.getName());
		nameField.setEditable(false);

		JLabel lblTimber = new JLabel("Timber");
		lblTimber.setBounds(23, 73, 46, 14);
		contentPane.add(lblTimber);

		JLabel lblPreciousOre = new JLabel("Precious Ore");
		lblPreciousOre.setBounds(157, 73, 73, 14);
		contentPane.add(lblPreciousOre);

		JLabel lblStone = new JLabel("Stone");
		lblStone.setBounds(23, 98, 46, 14);
		contentPane.add(lblStone);

		JLabel lblPreciousBars = new JLabel("Precious Bars");
		lblPreciousBars.setBounds(157, 98, 73, 14);
		contentPane.add(lblPreciousBars);

		JLabel lblCoal = new JLabel("Coal");
		lblCoal.setBounds(23, 123, 46, 14);
		contentPane.add(lblCoal);

		JLabel lblCloth = new JLabel("Cloth");
		lblCloth.setBounds(157, 123, 46, 14);
		contentPane.add(lblCloth);

		JLabel lblOre = new JLabel("Ore");
		lblOre.setBounds(23, 148, 46, 14);
		contentPane.add(lblOre);

		JLabel lblRations = new JLabel("Rations");
		lblRations.setBounds(157, 148, 46, 14);
		contentPane.add(lblRations);

		JLabel lblBars = new JLabel("Bars");
		lblBars.setBounds(23, 173, 46, 14);
		contentPane.add(lblBars);

		JLabel lblMinions = new JLabel("Minions");
		lblMinions.setBounds(157, 173, 46, 14);
		contentPane.add(lblMinions);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setAction(submitAction);
		btnSubmit.setToolTipText("Saves the image and submits the data to a text file");
		btnSubmit.setBounds(10, 220, 89, 23);
		contentPane.add(btnSubmit);

		JButton btnAmend = new JButton("Amend");
		btnAmend.setAction(amendAction);
		btnAmend.setToolTipText("Unlocks the fields and allows editing to fix the values");
		btnAmend.setBounds(109, 220, 89, 23);
		contentPane.add(btnAmend);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setAction(cancelAction);
		btnCancel.setToolTipText("Disregards this user and closes the dialog");
		btnCancel.setBounds(208, 220, 89, 23);
		contentPane.add(btnCancel);

		fields = new HashMap();
		for (ResourceType r : ResourceType.values())
		{
			fields.put(r, new ResourceField(r));
			contentPane.add(fields.get(r));
		}

		fields.get(ResourceType.TIMBER).setBounds(70, 70, 58, 20);
		fields.get(ResourceType.STONE).setBounds(70, 98, 58, 20);
		fields.get(ResourceType.CHARCOAL).setBounds(70, 123, 58, 20);
		fields.get(ResourceType.ORE).setBounds(70, 148, 58, 20);
		fields.get(ResourceType.BARS).setBounds(70, 173, 58, 20);
		fields.get(ResourceType.PRECIOUS_ORE).setBounds(228, 70, 58, 20);
		fields.get(ResourceType.PRECIOUS_BARS).setBounds(228, 95, 58, 20);
		fields.get(ResourceType.CLOTH).setBounds(228, 120, 58, 20);
		fields.get(ResourceType.RATIONS).setBounds(228, 145, 58, 20);
		fields.get(ResourceType.MINIONS).setBounds(228, 170, 58, 20);

        Main.logger.info("Created raffle image frame");

	}

	private class ImagePanel extends JPanel
	{
		public void paint(Graphics g)
		{
            Main.logger.info("Drawing image");
			g.drawImage(img, 0, 0, null);
		}
	}

	private class ResourceField extends JTextField
	{
		ResourceType type;
		int value=0;
		boolean okay = true;

		public ResourceField(ResourceType type)
		{
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
