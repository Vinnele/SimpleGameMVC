package com.game.minesweeper.Views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.game.minesweeper.Controllers.Controller;
import com.game.minesweeper.Models.Model;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;


public class View extends JPanel implements Observer {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(View.class);
    private JPanel viewPanel;
    private JLabel bombs;
    private JLabel gameState;
    private JLabel timer;
    private ViewButtonField[][] buttonViewFields;
    private Model model;
    private Controller controller;


    /**
     * Constructor
     *
     * @param model sets the standard values for the view
     */
    public View(Model model) {
        Init(model);
    }

    public void Init(Model model) {
        this.model = model;
        this.controller = new Controller(model);
        this.setLayout(new BorderLayout());

        this.viewPanel = new JPanel();

        this.bombs = setLabel(this.bombs, "Bombs:  " + Integer.toString(model.remainingNumberMines()));
        this.gameState = setLabel(this.gameState, "Status:  " + model.getState());
        this.timer = setLabel(this.timer, " Time: "+ new SimpleDateFormat(" mm:ss").format(new Date(TimeUnit.SECONDS.toMillis(model.getTimer()))) );

        this.add(SetMenu(), BorderLayout.NORTH);
        this.add(this.bombs, BorderLayout.WEST);
        this.add(this.gameState, BorderLayout.CENTER);
        this.add(this.timer, BorderLayout.EAST);

        buildButtonsField();

        this.add(viewPanel, BorderLayout.SOUTH);
    }

    /**
     * Set  main menu //the view
     */
    private JMenuBar SetMenu() {

        JMenuBar menuBarMain = new JMenuBar();
        JMenu menuGame = new JMenu("Игра");

        JMenuItem newGame = new JMenuItem("Новая игра");

        final JCheckBoxMenuItem beginner = new JCheckBoxMenuItem("Новичок");
        final JCheckBoxMenuItem intermediate = new JCheckBoxMenuItem("Опытный");
        final JCheckBoxMenuItem expert = new JCheckBoxMenuItem("Эксперт");
        final JCheckBoxMenuItem custom = new JCheckBoxMenuItem("Особый");

        final JMenuItem exit = new JMenuItem("Выход");

        final JMenu menuHelp = new JMenu("Справка");

        final JMenuItem help = new JMenuItem("Помощь");

        final JMenuItem tableHighscore = new JMenuItem("Статистика");

        ButtonGroup status = new ButtonGroup();

        status.add(beginner);

        menuHelp.add(help);
        status.add(intermediate);
        status.add(expert);
        status.add(custom);

        menuGame.add(newGame);
        menuGame.add(tableHighscore);
        menuGame.addSeparator();
        menuGame.add(beginner);
        menuGame.add(intermediate);
        menuGame.add(expert);
        menuGame.add(custom);
        menuGame.addSeparator();
        menuGame.add(exit);

        menuBarMain.add(menuGame);
        menuBarMain.add(menuHelp);

        help.addActionListener(controller);
        newGame.addActionListener(controller); // action item menu
        beginner.addActionListener(controller);
        intermediate.addActionListener(controller);
        expert.addActionListener(controller);
        custom.addActionListener(controller);
        tableHighscore.addActionListener(controller);
        exit.addActionListener(controller);

        return menuBarMain;
    }
    /**
     * @param label
     * @param string sets the text
     * @return returns the label wit the string setted
     */
    private JLabel setLabel(JLabel label, String string) {
        if (!(label instanceof JLabel)) {
            label = new JLabel("");
        }
        label.setText(string);
        label.setPreferredSize(new Dimension(100, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;

    }

    /**
     * updates the view
     */
    @Override
    public void update(Observable obs, Object o) {

        if (o != null) { // If object is empty, then update all
            updateButtons();
        }

        this.bombs = setLabel(this.bombs, "Bombs:  " + Integer.toString(model.remainingNumberMines()));
        this.gameState = setLabel(this.gameState, "Status:  " + model.getState());
        this.timer = setLabel(this.timer, "Time:  " + new SimpleDateFormat(" mm:ss").format(new Date(TimeUnit.SECONDS.toMillis(this.model.getTimer()))));

    }




    /**
     * creates restart button
     *
     * @return the restart button
     */


    /**
     * update Buttons
     */
    public void updateButtons() {
        try {

            removeButtons();
            buildButtonsField();
        } catch (ArrayIndexOutOfBoundsException e) {

            log.warn( e.fillInStackTrace().toString());
        }

    }

    /**
     * delete buttons
     */
    private void removeButtons() {

        this.viewPanel.removeAll();

    }

    /**
     * create buttons
     */
    private void buildButtonsField() {


        this.buttonViewFields = new ViewButtonField[model.getHeight()][model.getWidth()];
        this.model.addObserver(this);
        this.viewPanel.setLayout(new GridLayout(model.getHeight(), model.getWidth()));

        for (int i = 0; i < this.model.getHeight(); i++) {
            for (int j = 0; j < this.model.getWidth(); j++) {

                ViewButtonField button = new ViewButtonField(this.model.getField(i, j));

                buttonViewFields[i][j] = button;
                this.viewPanel.add(button.getButton());

            }

        }
    }
}