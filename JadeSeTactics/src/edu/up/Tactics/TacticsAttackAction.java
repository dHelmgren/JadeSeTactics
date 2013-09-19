package edu.up.Tactics;

import edu.up.game.GameAction;

/**
 *  -- AttackAction --
 *  
 *  Containts the instructions for completeing an attack action
 *  
 *  @author Seth Schneider
 *  @author Janel Rab
 *  @author Devin Helmgren
 *  
 *  @date 11/16/2012
 *  @version 1.2
 */
public class TacticsAttackAction extends GameAction {

	private static final long serialVersionUID = -3801264712357639099L;
	//set up the Variables
	protected TacticsPiece attacker;
	protected TacticsPiece defender;
	
	/**
	 * AttackAction()
	 * 
	 * constructor for a attack action
	 * 
	 * @param source the player id of the move action
	 * @param pieceAttacker  the piece in which will be attacking
	 * @param pieceDefender the piece in which will be defending
	 * @param gameInfo a copy of the game state to determine bonuses
	 */
    public TacticsAttackAction(int source, TacticsPiece pieceAttacker, TacticsPiece pieceDefender) {
    	super(source);
    	defender = pieceDefender;
    	attacker = pieceAttacker;
    }//TacticsAttackAction()



}//TacticsAttackAction
