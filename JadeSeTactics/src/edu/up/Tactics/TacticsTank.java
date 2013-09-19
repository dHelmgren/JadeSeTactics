package edu.up.Tactics;

import java.io.Serializable;

/**
 * --TacticsTank--
 * 
 * TacticsTank provides specific information
 * 
 * @author Devin Helmgren
 * @author Janel Raab
 * @author Seth Scheider
 * 
 * @date 11/19/2012
 * @version 1.3
 * 
 *          11/19/2012: Updated to match TacticsPiece,
 * 
 * @see TacticsPiece
 * 
 */
public class TacticsTank extends TacticsPiece implements Serializable 
{

	private static final long serialVersionUID = -6558155266275627147L;

	/**
	 * TacticsTank()
	 * 
	 * Constructor for the Tank class
	 * 
	 * 
	 * @param x
	 *            starting column
	 * @param y
	 *            starting row
	 * @param owner
	 *            player who controls this UnitOne
	 * @param name
	 *            the name of this unit
	 */

	public TacticsTank(int x, int y, int id, String name) 
	{
		super(x, y, id);
		this.initi = 1;
		this.maxHealth = 15;
		this.health = maxHealth;
		this.accuracy = .8;
		this.attackPower = 1;
		this.defensePower = 2;
		this.speed = 2;
		this.direction = 2;
		this.unitName = name;
		this.range = 1;

	}// TacticsTank()
}// TacticsTank
