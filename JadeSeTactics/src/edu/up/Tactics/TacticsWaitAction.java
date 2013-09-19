package edu.up.Tactics;

import edu.up.game.GameAction;

/**
 * -- WaitAction --
 * 
 * Contains the instructions for completing a wait Action
 * 
 * @author Seth Schneider
 * @author Janel Rab
 * @author Devin Helmgren
 * 
 * @date 11/16/2012
 * @version 1.2
 */
public class TacticsWaitAction extends GameAction 
{
	private static final long serialVersionUID = -6533512485541813417L;
	// set up the Variables
	public TacticsPiece waitor;
	public int direction;

	/**
	 * WaitAction()
	 * 
	 * constructor for a wait action
	 * 
	 * @param source
	 *            the player id of the move action
	 * @param pieceWaiting
	 *            the piece in which will be waiting
	 * @param newDirection
	 *            the new direction that the piece is facing
	 * 
	 *            for newDirection, 0 is north, 1 is west, 2 is south 3 is east
	 */
	public TacticsWaitAction(int source, TacticsPiece pieceWaiting,
			int newDirection) 
	{
		super(source);
		waitor = pieceWaiting;
		direction = newDirection;
	}// TacticsWaitAction()

}// TacticsWaitAction()
