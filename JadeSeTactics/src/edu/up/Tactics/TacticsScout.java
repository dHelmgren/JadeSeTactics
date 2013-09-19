package edu.up.Tactics;

import java.io.Serializable;

/**
 * --TacticsScout--
 * 
 * UnitOne provides specific information
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
public class TacticsScout extends TacticsPiece implements Serializable 
{

	private static final long serialVersionUID = -8303310698025169877L;

	/**
	 * TacticsScout()
	 * 
	 * Constructor for the TacticsScout class
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

	public TacticsScout(int x, int y, int id, String name) 
	{
		super(x, y, id);
		this.initi = 1;
		this.maxHealth = 5;
		this.health = maxHealth;
		this.accuracy = .8;
		this.attackPower = 3;
		this.defensePower = 0;
		this.speed = 4;
		this.direction = 2;
		this.unitName = name;
		this.range = 1;

	}// TacticsScout()
}// TacticsScout
