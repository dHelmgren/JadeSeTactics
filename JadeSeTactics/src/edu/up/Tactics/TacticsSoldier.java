package edu.up.Tactics;

import java.io.Serializable;

/**
 * --TacticsSoldier--
 * 
 * TacticsSoldier provides specific information
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
public class TacticsSoldier extends TacticsPiece implements Serializable 
{

	private static final long serialVersionUID = 8438453269947932412L;

	/**
	 * TacticsSoldier()
	 * 
	 * Constructor for the TacticsSoldier class
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

	public TacticsSoldier(int x, int y, int id, String name) 
	{
		super(x, y, id);
		this.initi = 1;
		this.maxHealth = 10;
		this.health = maxHealth;
		this.accuracy = .8;
		this.attackPower = 2;
		this.defensePower = 1;
		this.speed = 3;
		this.direction = 2;
		this.unitName = name;
		this.range = 1;

	}// TacticsSoldier()
}// TacticsSoldier
