package edu.up.Tactics;

import java.util.Vector;

import edu.up.game.GameAction;
import edu.up.game.GameComputerPlayer;
import edu.up.game.LocalGame;

/**
 * --AdmiralHackbar--
 * 
 * AdmiralHackbar is a more advanced AI player, capable of playing JaDeSe tactics
 * 
 * @author Devin Helmgren
 * @author Janel Raab
 * @author Seth Scheider
 * 
 * @date 11/9/2012
 * @version 1.1
 * 
 */


public class TacticsAdmiralHackbar extends GameComputerPlayer {
	
	//These are here to make better use of the constructor
	int myPlayerNumber, myX, myY;
	Vector<TacticsPiece> myPieces;
	Vector<TacticsPiece> enemyPieces;
	TacticsPiece currentPiece;

	/**
     * AdmiralHackbar()
     * 
     * constructor for AdmiralHackbar
     * Has no return values or parameters
     */
    public TacticsAdmiralHackbar() 
    {
    	super();
    	
    }
    
    /**
     * calculateMove() 
     * 
     * 	the method that will determine AdmiralHackbar's next move
     *  Has no return values or parameters
     * 
     */

    @Override
	protected GameAction calculateMove() {
    	
    	myPlayerNumber = this.game.whoseTurn();
        
    	if (myPlayerNumber == 0)
    	{
    		myPieces = ((TacticsGame) this.game).playerOnePieces;
    		enemyPieces = ((TacticsGame) this.game).playerTwoPieces;
    	}
    	else
    	{
    		myPieces = ((TacticsGame) this.game).playerTwoPieces;
    		enemyPieces = ((TacticsGame) this.game).playerOnePieces;
    	}
    	int myPieceNumber;
    	if(myPlayerNumber == 0){
    		myPieceNumber = ((TacticsGame) this.game).playerOnePieceTurn;
    	}
    	else
    	{
    		myPieceNumber = ((TacticsGame) this.game).playerTwoPieceTurn;
    	}
    	
    	currentPiece = myPieces.elementAt(myPieceNumber);
    	myX = currentPiece.getX();
    	myY = currentPiece.getY();
    	
    	
    
    	
    	if(!(currentPiece.hasMoved))
    	{
    		
    		
    		TacticsPiece myFoe= enemyPieces.elementAt(0);
    		int foeDistance = ((Math.abs(myFoe.getX()-currentPiece.getX()))+(Math.abs(myFoe.getY()-currentPiece.getY())));
    		
    		int foeX = (Math.abs(myFoe.getX()-currentPiece.getX()));
    		int foeY = (Math.abs(myFoe.getY()-currentPiece.getY()));
    		
    		for(TacticsPiece p : enemyPieces)
    		{
    			int checkDistance = ((Math.abs(p.getX()-currentPiece.getX()))+(Math.abs(p.getY()-currentPiece.getY())));
    			if ((foeDistance>checkDistance)&&(myFoe.getHealth()>p.getHealth()))
    			{
    				foeDistance = checkDistance;
    				myFoe = p;
    				foeX = (Math.abs(myFoe.getX()-currentPiece.getX()));
    	    		foeY = (Math.abs(myFoe.getY()-currentPiece.getY()));
    			}
    		}
    			
    		//GET AS CLOSE AS POSSIBLE
    		int bestX = 0;
    		int bestY = 0;
    		int[] lastMove = new int[currentPiece.speed];
    		int i =0;
    		while ((Math.abs(bestX)+Math.abs(bestY))<currentPiece.speed && (i < currentPiece.speed))
    		{
    			
    			if((myX+bestX)<myFoe.getX())
    			{
    				++bestX;
    				lastMove[i] =0;
    			}
    			else if((myX+bestX)>myFoe.getX())
    			{
    				--bestX;
    				lastMove[i]=1;
    			}
    			else if((myY+bestY)<myFoe.getY())
    			{
    				++bestY;
    				lastMove[i]=2;
    			}
    			else if((myY+bestY)>myFoe.getY())
    			{
    				--bestY;
    				lastMove[i]=3;
    			}
    			else
    			{
    				lastMove[i]=4;
    			}
    			
    			i++;
    		}
    		while( i > 0){
    			i--;
	    		if(((TacticsGame)this.game).boardState[bestX+currentPiece.getX()][bestY+currentPiece.getY()]>2)
	    		{
	    			switch(lastMove[i])
	    			{
	    			case 0: bestX--; break;
	    			case 1: bestX++; break;
	    			case 2: bestY--; break;
	    			case 3: bestY++; break;
	    			case 4: break;
	    			}
	    		}
	    		
    		}
    		//Is this the best move? maybe not. Is it valid? I sure as heck hope so.
    		return new TacticsMoveAction(this.game.whoseTurn(),currentPiece,bestX+currentPiece.getX(),bestY+currentPiece.getY());
    	
    	}
    	else if(!currentPiece.hasAttacked){
    	//attack if next to an enemy
    		
    		//find local enemies, add to vector
    		//Am I adjacent to another piece?
        	
        	//Boolean values say if there is a piece to N E S W in that order
        	Vector<TacticsPiece> adjacentEnemies = new Vector<TacticsPiece>();
        	//Check to North
        	if(myY != 0)
        	{
        		if((((TacticsGame) this.game).boardState[myX][myY-1] >= 3) && 
        				areYouMyEnemy(currentPiece, currentPiece.getX(), currentPiece.getY()-1, myPieces))
        		{
        			adjacentEnemies.add(TacticsRandoCalrissian.enemyFinder(currentPiece, 
        					myX, myY-1, enemyPieces));
        		}
        	}
        	//Check to South
        	if(myY != 7)
        	{
        		if((((TacticsGame) this.game).boardState[myX][myY+1] >= 3) && 
        				areYouMyEnemy(currentPiece, currentPiece.getX(), currentPiece.getY()+1, myPieces))
        		{
        			adjacentEnemies.add(TacticsRandoCalrissian.enemyFinder(currentPiece, 
        					myX, myY+1, enemyPieces));
        		}
        	}
        	//Check to West
        	if(myX != 7)
        	{
        		if((((TacticsGame) this.game).boardState[myX+1][myY] >= 3)&& 
        				areYouMyEnemy(currentPiece, currentPiece.getX()+1, currentPiece.getY(), myPieces))
        		{
        			adjacentEnemies.add(TacticsRandoCalrissian.enemyFinder(currentPiece, 
        					myX+1, myY, enemyPieces));
        		}
        	}
        	//Check to East
        	if(myX != 0)
        	{
        		if((((TacticsGame) this.game).boardState[myX-1][myY] >= 3)&& 
        				areYouMyEnemy(currentPiece, currentPiece.getX()-1, currentPiece.getY(), myPieces))
        		{
        			adjacentEnemies.add(TacticsRandoCalrissian.enemyFinder(currentPiece, 
        					myX-1, myY, enemyPieces));
        		}
        	}
        	
        	//if there were no enemies next to me, I shall wait.
        	if(adjacentEnemies.isEmpty())
        	{
        		return new TacticsWaitAction(this.game.whoseTurn(), 
        				currentPiece, currentPiece.direction);
        	}
    		
    		//attack the weakest
    		TacticsPiece weakestEnemy = adjacentEnemies.elementAt(0);
    		for(TacticsPiece p : adjacentEnemies)
    		{
    			if(weakestEnemy.health > p.health)
    			{
    				weakestEnemy = p;
    			}
    		}
    		
    		return new TacticsAttackAction(this.game.whoseTurn(), 
    				currentPiece, weakestEnemy);
    		
    	}
		return new TacticsWaitAction(this.game.whoseTurn(), currentPiece, currentPiece.getDirection());
    
    }
    
    private boolean areYouMyEnemy(TacticsPiece thisUnit, 
    		int yourX, int yourY, 
    		Vector<TacticsPiece> myFriends)
    {
    	for(TacticsPiece p : myFriends)
    	{
    		if((p.xPos==yourX)&&(p.yPos==yourY))
    		{
    			return false;
    		}
    	}
		return true;
    	
    }//end areYouMyEnemy

}
