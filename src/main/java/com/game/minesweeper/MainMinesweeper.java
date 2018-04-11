package com.game.minesweeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.fs_latyntseva.task_4.Models.Model;
import ru.cft.fs_latyntseva.task_4.Views.View;

import javax.swing.*;
import java.awt.*;


public class MainMinesweeper {
    // initialise model, which in turn initialises view
    private static final String START_VALUE_DIFFICULTIES = "beginner";
    private static final String NAME_FRAME = "Minesweeper";
    //    private final Logger log = LoggerFactory.getLogger(MainMinesweeper.class);
    private JFrame frame;
    private JScrollPane jScrollPane;

    public MainMinesweeper() {
        //Creating the Model
        Model model = new Model(START_VALUE_DIFFICULTIES);
        //Creating the View
        View view = new View(model);

        frame = new JFrame(NAME_FRAME);

        jScrollPane = new JScrollPane(view);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(jScrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    public static void main(String[] args) {

        MainMinesweeper mainMinesweeper = new MainMinesweeper();

    }

}
