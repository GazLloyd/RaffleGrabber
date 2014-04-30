package com.gmail.gazlloyd.rafflegrabber.gui;

import com.gmail.gazlloyd.rafflegrabber.ResourceType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Gareth Lloyd on 30/04/14.
 */
public class ResourceField extends JPanel {
    ResourceType type;
    JLabel label;
    ResourceTextField field;
    int number;

    public ResourceField(ResourceType type, int number) {
        //make the jpanel
        super();

        //set vars
        this.type = type;
        this.label = new JLabel(type.toString());
        this.field = new ResourceTextField(type, number, this);
        this.number = number;

        //add border and horizontal boxlayout
        this.setBorder(new EmptyBorder(5,5,5,5));
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));

        //add the things with a gap and some glue
        this.add(label);
        this.add(Box.createRigidArea(new Dimension(5,0)));
        this.add(Box.createHorizontalGlue());
        this.add(field);
    }

    public void setEditable(boolean editable) {
        field.setEditable(editable);
    }


    public boolean isOkay() {
        return field.okay;
    }

    public int getValue() {
        return number;
    }

    private void updateValue(int v) {
        this.number = v;
    }



    private class ResourceTextField extends JTextField
    {
        final int X = 58;
        final int Y = 20;
        ResourceType type;
        int value=0;
        boolean okay = true;
        ResourceField container;

        public ResourceTextField(ResourceType type, int valuein, ResourceField con)
        {

            setMaximumSize(new Dimension(X, Y));
            setMinimumSize(new Dimension(X, Y));
            setPreferredSize(new Dimension(X, Y));
            setSize(X,Y);

            this.type = type;
            this.value = valuein;
            this.container = con;

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
                            container.updateValue(value);
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
}
