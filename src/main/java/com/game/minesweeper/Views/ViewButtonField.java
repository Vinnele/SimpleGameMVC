package com.game.minesweeper.Views;

import com.game.minesweeper.Controllers.Controller;
import com.game.minesweeper.Models.Field;

import javax.swing.*;
import java.awt.*;

import java.util.Observable;
import java.util.Observer;

/**
 *
 */
public class ViewButtonField extends JButton implements Observer {
    public static final int BUTTON_WIDTH = 25;
    public static final int BUTTON_HEIGHT = 25;
    private static final long serialVersionUID = 1L;
    private JButton button;
    private Field field;
    private Controller controller;
    private ImageIcon[] imageIcons = new ImageIcon[14];

    /**
     * Creates the neccassary Buttons
     *
     * @param field
     */
    ViewButtonField(Field field) {
        this.field = field;
        this.button = new JButton("");
        this.button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        setIcons();
        this.controller = new Controller(field);
        this.button.addMouseListener(controller);
        this.field.addObserver(this);
    }

    JButton getButton() {
        return this.button;
    }


    /**
     * Makes a Button Invisible
     */
    public void setUnvisible() {
        this.button.setVisible(false);
        this.field.reveal();
    }

    /**
     * SetIcons button  icons image from resource
     */
    private void setIcons() {
        String name;
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            for (int i = 1; i <= 8; i++) {
                name = i + ".gif";
                imageIcons[i] = new ImageIcon(classLoader.getResource("icons/" + name).getFile());
            }

            imageIcons[9] = new ImageIcon(classLoader.getResource("icons/mine.gif").getFile());
            imageIcons[10] = new ImageIcon(classLoader.getResource("icons/flag.gif").getFile());
        } catch (NullPointerException e) {
            //log.
        }

    }

    /**
     * Update button view
     *
     * @param obs standard  Observable
     * @param o
     */
    @Override
    public void update(Observable obs, Object o) {

        if (field.getRevealed()) {

            if (this.field.getField_id() == -1) {

                this.button.setBackground(Color.RED);
                this.button.setIcon(imageIcons[9]);

            } else {

                this.button.setBackground(Color.LIGHT_GRAY);
                //if
                if (this.field.getField_id() == 0) {
                    this.button.setText("");
                } else {
                    this.button.setIcon(imageIcons[this.field.getField_id()]);
                }
            }
        }

        if (field.isFlag()) {                    // flag set in field

            this.button.setIcon(imageIcons[10]);

        }
        if (!field.isFlag() && !field.getRevealed()) {
            // flag remove in field
            this.button.setIcon(null);
            this.button.setBackground(new JButton().getBackground());
        }
    }

}