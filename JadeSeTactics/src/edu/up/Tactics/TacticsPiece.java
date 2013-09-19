package edu.up.Tactics;

import java.io.Serializable;

/**
 * --Piece--
 * 
 * Piece provides a generic set of information that units will use to act and 
 * be drawn in the game
 * 
 * @author Devin Helmgren
 * @author Janel Raab
 * @author Seth Scheider
 * 
 * @date 11/19/2012
 * @version 1.2
 * 
 * IMPORTANT CHANGES: 
 * added an ID number and compareTo functions so that we can accuarately compare two pieces.
 * 
 */

public class TacticsPiece implements Serializable{

 
	private static final long serialVersionUID = 859078811766866790L;
	protected int xPos;
    protected int yPos;
    protected int initi;
    protected int attackPower;
    protected int defensePower;
    protected int speed;
    protected double accuracy; // percent chance of hit
    protected int health;
    protected int maxHealth;
    protected int range;
    protected int direction;
    protected boolean hasAttacked;
    protected boolean hasMoved;
    protected int id;
    protected String unitName;

    /**
     * Piece()
     * 
     * constructor for pieces
     * 
     * @param x starting column
     * @param y starting row
     */
    public TacticsPiece(int x, int y, int idNum) {
    	xPos = x;
    	yPos = y;
    	id = idNum;
    	
    	
    }//TacticsPiece()

    /**
     * getX()
     * 
     * returns current column of piece
     * 
     * @return current column of piece
     */
    public int getX() {
		return this.xPos;
    
    }//getX()
    
    /**
     * getY()
     * 
     * returns current row of piece
     * 
     * @return current row of piece
     */
    public int getY() {
		return this.yPos;
    
    }//getY()

    /**
     * getAtack()
     * 
     * returns attack statistics
     * 
     * @return attack stat
     */
    public int getAttack() {
		return this.attackPower;
    
    }//getAttack()

    /**
     * getDefense()
     * 
     * returns defense statistics
     * 
     * @return defense stat
     */
    public int getDefense() {
		return this.defensePower;
    
    }//getDefense()

    /**
     * getSpeed()
     * 
     * returns speed statistics
     * 
     * @return speed stat
     */
    public int getSpeed() {
		return this.speed;
    
    }//getSpeed()
    
    /**
     * getDirrection()
     * 
     * returns dirrection
     * 
     * @return dirrection the dirrection in which the piece is facing
     */
    public int getDirection() {
		return this.direction;
    
    }//getDirection()

    /**
     * getAccur()
     * 
     * returns accuracy statistics
     * 
     * @return accuracy stat
     */
    public double getAccuracy() {
		return this.accuracy;
    
    }//getAccur()

    /**
     * getHealth()
     * 
     * returns hit point statistics
     * 
     * @return HP stat
     */
    public int getHealth() {
		return this.health;
    
    }//getHealth()
    
    
    /**
     * setHealth()
     * 
     * sets health of piece
     * 
     * @param hp hp to be changed
     */
    public void setHealth(int hp){
    	this.health = hp;
    }//setHealth()
    
    /**
     * setX()
     * 
     * sets X position of piece
     * 
     * @param x xPos to be changed
     */
    public void setX(int x){
    	this.xPos =x;
    }//setX()
    
    /**
     * setY()
     * 
     * sets Y position of piece
     * 
     * @param y y to be changed
     */
    public void setY(int y){
    	this.yPos =y;
    }//setY()
    
    /**
     * setDirrection()
     * 
     * sets direction of piece
     * 
     * @param direction xPos to be changed
     */
    public void setDirection(int dir){
    	this.direction =dir;
    }//setDirection()
    
    /**
     * setAttack()
     * 
     * the piece has attacked this turn
     * 
     */
    public void setAccuracy(double accur){
    	this.accuracy = accur;
    }//setAccuracy()
    
    /**
     * setAttack()
     * 
     * the piece has attacked this turn
     * 
     */
    public void setAttacked(boolean set)
    {
    	this.hasAttacked = set;
    }//setAttack()
    
    /**
     * setMoved()
     * 
     * the piece has attacked this turn
     * 
     */
    public void setMoved(boolean set){
    	this.hasMoved = set;
    }//setMoved()
    
    /**
     * compareTo()
     * 
     * compares a piece to another piece
     * 
     * @param p the piece to compare to
     */
    public boolean compareTo(TacticsPiece p)
    {
    	if(this.id != p.id)
    		return false;
    	
    	return true;
    }//compareTo()
    
}//TacticsPiece
