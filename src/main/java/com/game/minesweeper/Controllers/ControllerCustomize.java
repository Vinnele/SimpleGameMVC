package com.game.minesweeper.Controllers;

import com.game.minesweeper.Models.Model;
import com.game.minesweeper.Views.ViewPopupCustomize;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Objects;


public class ControllerCustomize implements  ActionListener {

    Model model;
    ViewPopupCustomize viewPopupCustomize;


    public ControllerCustomize(Model model, ViewPopupCustomize viewPopupCustomize) {

        this.model = model;
        this.viewPopupCustomize = viewPopupCustomize;
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (Objects.equals(e.getActionCommand(), "OK")) {

            if (!this.viewPopupCustomize.setTextFieldRCM())
            {
                //do
            } else {
                this.viewPopupCustomize.setTextFieldRCM();
                this.model.setDifficulty("custom");
                this.model.Init();
            }


        }
        if (e.getActionCommand().equals("Отмена")) {

            this.viewPopupCustomize.dispose();
        }
    }



}
