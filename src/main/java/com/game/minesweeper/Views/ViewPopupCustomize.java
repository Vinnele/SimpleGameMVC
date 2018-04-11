package com.game.minesweeper.Views;

import com.game.minesweeper.Controllers.ControllerCustomize;
import com.game.minesweeper.Models.Model;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;


public class ViewPopupCustomize extends JFrame implements Observer {
    private JTextField textFieldRows;
    private JTextField textFieldColumns;
    private JTextField textFieldMines;
    private JLabel labelRows;
    private  JLabel labelColumns;
    private  JLabel labelMines;
    private  JButton buttonOK;
    private  JButton buttonCancel;

    Model model;
    ControllerCustomize controllerCustomize;
   // int actionc = 0;



    public ViewPopupCustomize(Model model) {
        this.model = model;
        this.controllerCustomize = new ControllerCustomize(this.model, this);
        Init();

    }

    public void Init() {


        setSize(180, 200);
        setResizable(false);
        setLocation(500, 300);

        textFieldRows = new JTextField();
        textFieldColumns = new JTextField();
        textFieldMines = new JTextField();

        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Отмена");

        labelRows = new JLabel("Rows");
        labelColumns = new JLabel("Columns");
        labelMines = new JLabel("Mines");

        getContentPane().setLayout(new GridLayout(0, 2));
        getContentPane().add(labelRows);
        getContentPane().add(textFieldRows);
        getContentPane().add(labelColumns);
        getContentPane().add(textFieldColumns);
        getContentPane().add(labelMines);
        getContentPane().add(textFieldMines);
        getContentPane().add(buttonOK);
        getContentPane().add(buttonCancel);

        buttonOK.addActionListener(controllerCustomize);
        buttonCancel.addActionListener(controllerCustomize);

        show();
    }

 public  boolean setTextFieldRCM(){
     try {
         this.model.setCustomRows(Integer.parseInt(this.textFieldRows.getText()));
         this.model.setCustomColumns(Integer.parseInt(this.textFieldColumns.getText()));
         this.model.setCustomMines(Integer.parseInt(this.textFieldMines.getText()));
         return true;
     } catch (Exception any) {
         JOptionPane.showMessageDialog(this, "Wrong value: "+any.getLocalizedMessage());
         this.textFieldRows.setText("");
         this.textFieldColumns.setText("");
         this.textFieldMines.setText("");
         return false;
     }

 }
    @Override
    public void update(Observable o, Object arg) {

        if (o != null) { // If object is empty, then update all

            setTextFieldRCM();
        }
        this.model.setDifficulty("custom");
        this.model.Init();


    }

}
