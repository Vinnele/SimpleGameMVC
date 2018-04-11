package com.game.minesweeper.Controllers;


import com.game.minesweeper.Models.Field;
import com.game.minesweeper.Models.Model;
import com.game.minesweeper.Views.ViewPopupCustomize;
import com.game.minesweeper.Views.ViewPopupHelp;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Controller Menu and Field Buttons
 */
public class Controller extends MouseAdapter implements AncestorListener, ActionListener {

    private Field field;
    private Model model;


    /**
     * Construcor
     *
     * @param field field of the game
     */
    public Controller(Field field) {
        this.field = field;

    }


    /**
     * Constructor
     *
     * @param model
     */
    public Controller(Model model) {
        this.model = model;

    }


    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getActionCommand().equals("Новая игра")) {
            this.model.Init();

        }
        if (ae.getActionCommand().equals("Статистика")) {
            ///

        }
        if (ae.getActionCommand().equals("Новичок")) {

            this.model.setDifficulty(Model.DIFFICULTIES.get(0));
            this.model.Init();

        }
        if (ae.getActionCommand().equals("Опытный")) {
            this.model.setDifficulty(Model.DIFFICULTIES.get(1));
            this.model.Init();

        }
        if (ae.getActionCommand().equals("Эксперт")) {

            this.model.setDifficulty(Model.DIFFICULTIES.get(2));
            this.model.Init();

        }
        if (ae.getActionCommand().equals("Особый")) {

            ViewPopupCustomize viewPopupCustomize = new ViewPopupCustomize(this.model);


        }
        if (ae.getActionCommand().equals("Выход")) {
            model.setDifficulty(null);

        }
        if (ae.getActionCommand().equals("Помощь")) {

            ViewPopupHelp viewPopupHelp = new ViewPopupHelp(model.getRules(), 450, 500);

        }
    }

    /**
     * Checks for Mouseclicks and class the Method in the model
     *
     * @param e the MouseEvent that just happened
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                if (this.model == null) {
                    if (!field.isFlag()) {
                        field.reveal();

                    }
                } else {
                    model.Init();
                }
                break;
            case MouseEvent.BUTTON2:
                if (this.model == null) {
                    if (field.isFlag()) {
                        field.reveal();

                    }
                } else {
                    model.Init();
                }
                break;
            case MouseEvent.BUTTON3:
                if (this.model == null) {
                    field.changeState();
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void ancestorAdded(AncestorEvent event) {

    }

    @Override
    public void ancestorRemoved(AncestorEvent event) {

    }

    @Override
    public void ancestorMoved(AncestorEvent event) {

    }
}