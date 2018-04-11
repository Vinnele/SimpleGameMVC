package com.game.minesweeper.Models;

import java.util.Observable;

/**
 * Model ButtontsField
 */
public class Field extends Observable {

    //No one should change this stuff from the Outside
    private int fieldId;
    private int posx;
    private int posy;
    private Model model;
    private boolean isRevealed;        //If the field is Revealed
    private boolean isFlag;            //If the field is selected


    /**
     * Constructor
     *
     * @param model
     * @param x       x position of the Field
     * @param y       y position of the Field
     * @param fieldId
     */
    public Field(Model model, int x, int y, int fieldId) {
        this.fieldId = fieldId;
        this.posx = x;
        this.posy = y;
        this.model = model;
        this.isRevealed = false;
        this.isFlag = false;


    }

    /**
     * Initialises the Field
     *
     * @param model
     * @param x
     * @param y
     * @param fieldId
     */
    public void Init(Model model, int x, int y, int fieldId) {
        this.fieldId = fieldId;
        this.posx = x;
        this.posy = y;
        this.model = model;
        this.isRevealed = false;
        this.isFlag = false;



    }

    /**
     * Reveal the field
     */
    public void reveal() {

        if (model.getState().equals("running")) {
//
            if (!this.model.getThread().isAlive()) {

                this.model.startThread();
            }
             //if cell closet

            if (this.isRevealed == false) {

                model.addToRevealed();
                this.isRevealed = true;

                if (this.fieldId == 0) {
                    this.model.revealZeros(this);
                }
                this.setChanged();
                this.notifyObservers();

            }

            // if bombed
                if (this.fieldId == -1) {

                    this.isRevealed = true;
                    this.model.setState("lost");

                    this.setChanged();
                    this.notifyObservers();
                }
        }

        if (!model.getState().equals("running")) {
            this.model.stopThread();
        }


    }

    /**
     * Changing the State
     */
    public void changeState() {

        if (!this.isFlag && !this.getRevealed() && this.model.getState().equals("running")) {

            if (!this.model.getThread().isAlive()) {
                this.model.startThread();
            }

            this.isFlag = this.model.subRemainingBombs();

            this.setChanged();
            this.notifyObservers();

        } else if (!this.getRevealed() && model.getState().equals("running")) {

            this.isFlag = this.model.addRemainingBombs();

            this.setChanged();
            this.notifyObservers();
        }

        if (!model.getState().equals("running")) {
            this.model.stopThread();
        }
    }

    /**
     * @return if the field is selected
     */
    public boolean isFlag() {
        return this.isFlag;
    }

    /**
     * @return id of the field
     */
    public int getField_id() {
        return fieldId;
    }

    /**
     * @return x Position
     */
    public int  getPosx() {
        return posx;
    }

    /**
     * @return y Position
     */
    public int getPosy() {
        return posy;
    }

    /**
     * @return is the field revealed
     */
    public boolean getRevealed() {
        return this.isRevealed;
    }

    /**
     * Adds one to the field_id
     */
    void addFieldId() {
        this.fieldId++;
    }

    /**
     * Makes the Field to a Bomb
     */
    int getBomb() {
        return -1;
    }

    void setBomb() {
        this.fieldId = getBomb();
    }

    /**
     * @return the Model of the field
     */
    public Model getModel() {
        return this.model;
    }


}