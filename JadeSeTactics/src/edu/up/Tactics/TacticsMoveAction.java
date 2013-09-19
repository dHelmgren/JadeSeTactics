package edu.up.Tactics;

import edu.up.game.GameAction;

/**
 * -- MoveAction --
 * 
 * Containts the instructions for completeing a move action
 * 
 * @author Seth Schneider
 * @author Janel Rab
 * @author Devin Helmgren
 * 
 * @date 11/16/2012
 * @version 1.2
 */
public class TacticsMoveAction extends GameAction 
{

	private static final long serialVersionUID = 8115086952136183879L;
	// setup Variables
	public TacticsPiece mover;
	public int destinationX;
	public int destinationY;

	/**
	 * MoveAction()
	 * 
	 * consructor for a move action
	 * 
	 * @param source
	 *            the player id of the move action
	 * @param pieceToMove
	 *            the piece in which will be moved
	 * @param newX
	 *            the new X location of the piece
	 * @param newY
	 *            the new Y location of the piece
	 */
	public TacticsMoveAction(int source, TacticsPiece pieceToMove, int newX,
			int newY)
	{
		super(source);
		destinationX = newX;
		destinationY = newY;
		mover = pieceToMove;

	}//TacticsMoveAction()

}//TacticsMoveAction
