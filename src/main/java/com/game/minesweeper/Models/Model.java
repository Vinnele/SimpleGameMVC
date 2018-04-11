package com.game.minesweeper.Models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Model for Minesweeper
 */
public class Model extends Observable {

    //No one frome the outside should manipulate this
    private Field[][] field;
    private int numberCols;
    private int numberRows;
    private int numberMines;
    private int bombsLeft;
    private String state;
    private int revealed;
    private Thread thread;    //Thread for Timer
    private int timer;        //Time for Timer as int
    private boolean running;    //boolean running for terminating the thread

    private static long gamesPlayed = 1;
    private static long gamesWon = 0;
    private static long bestTimeSecondsBeg = 0;
    private static long bestTimeSecondsInter = 0;
    private static long bestTimeSecondsExpert = 0;
    private static long bestTimeSecondsCustom = 0;

    //keeps track of custom game settings the user sets
    private int customMines;
    private int customRows;
    private int customCols;

    private int difficultyIndex; //beginner=0, intermediate=1, expert=2,custom=3

    private final int BEGINNERMINES = 10;
    private final int INTERMEDIATEMINES = 40;
    private final int EXPERTMINES = 99;
    public static final ArrayList<String> DIFFICULTIES = new ArrayList<String>(Arrays.asList("beginner", "intermediate", "expert", "custom"));

    private final Logger log = LoggerFactory.getLogger(Model.class);


    public enum StateGame {
        lose,
        won,
        running

    }


    private String rules;

    /**
     * Constructor
     *
     * @param EnumDiff difficult of the game area
     */
    public Model(String EnumDiff) {

        setDifficulty(EnumDiff);


    }

    public void setDifficulty(String diff) {
        this.revealed = 0;
        this.timer = 0;
        this.state = "running";
        this.running = false;
        this.thread = new Thread();


        int NULL_EXIT_CODE = 0;
        if (diff == null) {
            System.exit(NULL_EXIT_CODE);
        }

        switch (diff) {

            case "beginner":

                this.numberMines = BEGINNERMINES;
                this.numberRows = 9;
                this.numberCols = 9;
                this.field = new Field[numberRows][numberCols];
                this.bombsLeft = BEGINNERMINES;
                difficultyIndex = 0;


                break;
            case "intermediate":
                this.numberMines = INTERMEDIATEMINES;
                this.numberRows = 16;
                this.numberCols = 16;
                this.field = new Field[numberRows][numberCols];
                this.bombsLeft = INTERMEDIATEMINES;

                difficultyIndex = 1;


                break;
            case "expert":
                numberMines = EXPERTMINES;
                numberRows = 16;
                numberCols = 30;
                this.field = new Field[numberRows][numberCols];
                this.bombsLeft = EXPERTMINES;

                difficultyIndex = 2;

                break;
            case "custom":
                numberMines = customMines;
                numberRows = customRows;
                numberCols = customCols;
                this.field = new Field[numberRows][numberCols];
                this.bombsLeft = customMines;
                difficultyIndex = 3;

                break;
            default:
                throw new IllegalArgumentException("Difficulty not correct!");

        }
        buildGameBoard();
    }

    /**
     * Initialize
     */
    public void Init() {
        //initialization as "lost"
        this.state = "lost";
        this.setChanged();
        this.notifyObservers();

        //reset all the fields
        resetAllFields();
        this.state = "running";
        this.bombsLeft = this.numberMines;

        this.revealed = 0;
        resetThread();

        buildGameBoard();
        this.setChanged();
        this.notifyObservers(true);

    }


    public void setCustomRows(int rows) throws Exception {
        if (rows >= 2 && rows <= EXPERTMINES) {
            this.customRows = rows;

        } else {
            throw new Exception("row");
        }
    }

    //user wants to play a custom game, change cols
    public void setCustomColumns(int cols) throws Exception {
        if (cols >= 2 && cols <= EXPERTMINES) {
            this.customCols = cols;
        } else {
            throw new Exception("columns");
        }
    }

    //user wants to play a custom game, change mines
    public void setCustomMines(int mines) throws Exception {
        if (mines >= 1 && mines <= 150) {
            this.customMines = mines;

        } else {
            throw new Exception("mines");
        }

    }


    /**
     * Creates the Thread for the Timer
     */
    private void setThread() {

        this.thread = new Thread() {
            @SuppressWarnings("static-access")
            //if running = true timer increments every second by 1
            @Override
            public void run() {
                while (running) try {

                    addTimer();
                    setChanged();
                    notifyObservers();
                    this.sleep(1000);

                } catch (InterruptedException e) {
                    // log.warn(e.setStackTrace("error"));
                }

            }
        };
        this.thread.start();

    }

    /**
     * Sets the timer
     */
    public void addTimer() {
        this.timer++;
        this.setChanged();
        this.notifyObservers();
    }


    /**
     * Creates the Gameboads
     */
    public void buildGameBoard() {
        //set all the fields
        setAllFields();

        //"random" selection of Bombs
        for (int i = 0; i < numberMines; i++) {
            int width, height;

            do {

                width = (int) (Math.random() * (this.numberCols));
                height = (int) (Math.random() * (this.numberRows));

            } while (this.field[height][width].getField_id() == -1);

            this.field[height][width].setBomb();

            counterMines(getField(height, width));
        }

    }

    /**
     * @return reveal of bombs around
     */
    public void revealbombs() {

        //if(getState()=="lose")
        for (int i = 0; i < this.numberCols; i++) {
            for (int j = 0; j < this.numberRows; j++) {

                if (field[i][j].getField_id() == -1) {
                    //open cells
                }

            }

        }


    }


    /**
     * Prints the field to console (for Debugging Stuff)
     */
    public void printArray() {


        for (int i = 0; i < numberCols; i++) {

            for (int j = 0; j < numberRows; j++) {
                System.out.print(field[i][j].getField_id() + " ");


            }
            //System.out.println();

        }
    }


    /**
     * Add 1 to all fields around
     *
     * @param field
     */
    public void counterMines(Field field) {
        int ax;
        int ay;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                ay = field.getPosy() + i;
                ax = field.getPosx() + j;

                if (ay >= 0 && ay < this.numberRows && ax >= 0 && ax < this.numberCols) {
                    if (this.field[ay][ax].getField_id() != -1) {
                        this.field[ay][ax].addFieldId();
                    }
                }

            }
        }
    }

    /**
     * Opens all zeros and reveals them
     *
     * @param field
     */
    public void revealZeros(Field field) {
        int x = field.getPosx();
        int y = field.getPosy();

        int ax;
        int ay;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                ay = y + i;
                ax = x + j;

                if (ay >= 0 && ay < this.numberRows && ax >= 0 && ax < this.numberCols) {
                    if (this.field[ay][ax].isFlag()) {
                        continue;
                    }
                    this.field[ay][ax].reveal();
                }

            }
        }

    }


    /**
     * Sets the State
     *
     * @param state Status
     */
    public void setState(String state) {
        this.state = state;
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Sets all the feilds
     */
    private void setAllFields() {
        for (int i = 0; i < this.numberRows; i++) {
            for (int j = 0; j < this.numberCols; j++) {
                this.field[i][j] = new Field(this, j, i, 0);
            }
        }
    }

    /**
     * Resets all fields to 0
     */
    private void resetAllFields() {


        for (int i = 0; i < this.numberRows; i++) {
            for (int j = 0; j < this.numberCols; j++) {
                this.field[i][j] = new Field(this, j, i, 0);
                this.field[i][j].Init(this, j, i, 0);
            }
        }


    }

    /**
     * Adds one to the number of revelead bombs and changes the state to won
     * if it was won.
     */
    public void addToRevealed() {

        this.revealed++;

        if (this.revealed >= ((this.numberRows * this.numberCols) - this.numberMines)) {
            setState("won");

        }
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Adds 1 to the remaining bombs
     */
    public boolean addRemainingBombs() {
        if (this.bombsLeft <= this.numberMines) {
            this.bombsLeft++;
            this.setChanged();
            this.notifyObservers();
            return false;
        } else {
            return true;
        }

    }

    /**
     * Removes 1 of the remaining bombs
     */
    public boolean subRemainingBombs() {
        if (this.bombsLeft > 0) {
            this.bombsLeft--;
            this.setChanged();
            this.notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Starts Thread
     */
    public void startThread() {
        this.running = true;
        this.setThread();
    }

    /**
     * Resetzts the Threads timer
     */
    public void resetThread() {
        this.running = false;
        this.timer = 0;
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Stops the Thread
     */
    public void stopThread() {
        this.running = false;

    }

    /**
     * @return Timer Thread
     */
    public Thread getThread() {
        return this.thread;
    }

    /**
     * @return Heigth of the Game
     */
    public int getHeight() {
        return this.numberRows;
    }

    /**
     * @return Width of The Game
     */
    public int getWidth() {
        return this.numberCols;
    }

    /**
     * @return State of the Game
     */
    public String getState() {
        return this.state;
    }

    /**
     * @param height
     * @param width
     * @return Field at this position
     */
    public Field getField(int height, int width) {
        return this.field[height][width];
    }

    /**
     * @return the field as an Array
     */
    public Field[][] getFields() {
        return this.field;
    }

    /**
     * @return number of Bombs
     */
    public int getNumberMines() {
        return this.numberMines;
    }

    /**
     * @return number of remaining Bombs
     */
    public int remainingNumberMines() {
        return this.bombsLeft;
    }

    /**
     * @return the Timer
     */
    public int getTimer() {
        return this.timer;
    }

    /**
     * @return the Rules  of the Game
     */
    public String getRules() {
        rules = "Rules for Minesweeper:\n\n"
                + "In Minesweeper, you are given a board of tiles. "
                + "These tiles may contain mines (" + this.numberMines + ") or numbers, "
                + "or be empty . "
                + "If you click on a tile with a mine, and you do not "
                + "have any more lives, you lose. "
                + "Tiles with numbers (1-8) indicate how many bombs are "
                + "immediately adjacent to that tile (touching a side "
                + "or a corner of that tile). "
                + "If you click on a tile and an empty square appears, "
                + "it indicates that no bombs are immediately adjacent "
                + "to that tile, and so all surrounding tiles "
                + "will be displayed. "
                + "The objective is to fill in all non-mine tiles by "
                + "clicking them (and optionally flagging the mine tiles). "
                + "To flag a tile you think is a mine, point and right-click. "
                + "To unflag a tile you previously flagged, point and right-click again. "
                + "You can click a numbered tile after it is initially opened "
                + "to display all adjacent tiles if the correct number of "
                + "flags have been placed (and/or mines hit in extra-lives "
                + "mode) on surrounding tiles. "
                + "Tiles on the edge of the board have fewer adjacent tiles "
                + "(the board does not wrap around the edges). "
                + "The number of mines minus the number of flags used, the number "
                + "of lives left (if applicable), and the time passed are "
                + "displayed at the top of the game.\n"
                + "Good luck and have fun!";
        return rules;
    }

}