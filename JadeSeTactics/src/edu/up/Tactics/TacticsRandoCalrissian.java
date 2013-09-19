package edu.up.Tactics;

import java.util.Vector;

import edu.up.game.GameAction;
import edu.up.game.GameComputerPlayer;

/**
 * --RandoCalrissian--
 * 
 * RandoCalrissian is a basic AI player, capable of playing JaDeSe tactics
 * 
 * @author Devin Helmgren
 * @author Janel Raab
 * @author Seth Scheider
 * 
 * @date 11/18/2012
 * @version 1.2
 * 
 */


public class TacticsRandoCalrissian extends GameComputerPlayer {

    
	/**
     * RandoCalrissian()
     * 
     * constructor for RandoCalrissian
     * Has no return values or parameters
     */
    public TacticsRandoCalrissian() {super();}

	
    /**
     * calculateMove() 
     * 
     * 	the method that will determine RandoCalrissian's next move
     *  Has no return values or parameters
     * 
     */
    
    @Override
	protected GameAction calculateMove() {
    	
    	int myPlayerNumber = this.game.whoseTurn();
    	Vector<TacticsPiece> myPieces;
    	Vector<TacticsPiece> enemyPieces;
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
    	
    	TacticsPiece currentPiece = myPieces.elementAt(myPieceNumber);
    	
    	//Am I adjacent to another piece?
    	int myX = currentPiece.getX();
    	int myY = currentPiece.getY();
    	//Boolean values say if there is a piece to N E S W in that order
    	boolean adjacentEnemies[] = {false, false, false, false};
    	
    	//Check to North
    	if(myY != 0)
    	{
    		if((((TacticsGame) this.game).boardState[myX][myY-1] >= 3) && 
    				areYouMyEnemy(currentPiece, currentPiece.getX(), currentPiece.getY()-1, myPieces))
    		{
    			adjacentEnemies[0] = true;
    		}
    	}
    	//Check to South
    	if(myY != 7)
    	{
    		if((((TacticsGame) this.game).boardState[myX][myY+1] >= 3) && 
    				areYouMyEnemy(currentPiece, currentPiece.getX(), currentPiece.getY()+1, myPieces))
    		{
    			adjacentEnemies[2] = true;
    		}
    	}
    	//Check to West
    	if(myX != 7)
    	{
    		if((((TacticsGame) this.game).boardState[myX+1][myY] >= 3)&& 
    				areYouMyEnemy(currentPiece, currentPiece.getX()+1, currentPiece.getY(), myPieces))
    		{
    			adjacentEnemies[1] = true;
    		}
    	}
    	//Check to East
    	if(myX != 0)
    	{
    		if((((TacticsGame) this.game).boardState[myX-1][myY] >= 3)&& 
    				areYouMyEnemy(currentPiece, currentPiece.getX()-1, currentPiece.getY(), myPieces))
    		{
    			adjacentEnemies[3] = true;
    		}
    	}
    	
    	//(I AM!) I shall strike with great vengeance.
    	if((adjacentEnemies[0] || adjacentEnemies[1] || adjacentEnemies[2] || adjacentEnemies[3])
    			&& !(currentPiece.hasAttacked))
    	{
    		TacticsPiece enemyPiece;
    		//STRIKE TO THE NORTH?
    		if(adjacentEnemies[0])
    		{
    			try
    			{
    				enemyPiece = enemyFinder(currentPiece, 
    						currentPiece.getX(), currentPiece.getY()-1, 
    						enemyPieces);
    				return new TacticsAttackAction(this.game.whoseTurn(),currentPiece, enemyPiece);
    			}
    			catch(NullPointerException e)
    			{
    				System.out.println("enemy not found");
    			}	
    		}//no, TO THE SOUTH?
    		else if(adjacentEnemies[2])
    		{
    			try
    			{
    				enemyPiece = enemyFinder(currentPiece, 
    						currentPiece.getX(), currentPiece.getY()+1, 
    						enemyPieces);
    				return new TacticsAttackAction(this.game.whoseTurn(),currentPiece, enemyPiece);
    			}
    			catch(NullPointerException e)
    			{
    				System.out.println("enemy not found");
    			}	
    		}//Perhaps the west?
    		else if(adjacentEnemies[1])
    		{
    			try
    			{
    				enemyPiece = enemyFinder(currentPiece, 
    						currentPiece.getX()+1, currentPiece.getY(), 
    						enemyPieces);
    				return new TacticsAttackAction(this.game.whoseTurn(),currentPiece, enemyPiece);
    			}
    			catch(NullPointerException e)
    			{
    				System.out.println("enemy not found");
    			}	
    		}//Well, now I just feel foolish...
    		else if(adjacentEnemies[3])
    		{
    			try
    			{
    				enemyPiece = enemyFinder(currentPiece, 
    						currentPiece.getX()-1, currentPiece.getY(), 
    						enemyPieces);
    				return new TacticsAttackAction(this.game.whoseTurn(),currentPiece, enemyPiece);
    			}
    			catch(NullPointerException e)
    			{
    				System.out.println("enemy not found");
    			}	
    		}
    	}
    	
    	//(I am not) I shall move randomly
    	else if(!(currentPiece.hasMoved)&& !(currentPiece.hasAttacked))
    	{
    		int moveX;
    		int moveY;
    		do{
    			moveX = makeAMove(currentPiece);
    			moveY = currentPiece.speed - moveX;
    			if(Math.random()>.5)
    			{
    				moveX *= -1;
    			}
    			if(Math.random()>.5)
    			{
    				moveY *= -1;
    			}
    		}while(!(isThisEvenLegal(currentPiece,moveX,moveY)));
    		//calculate an x move distance,
    		
    		//is that legal?
    		return new TacticsMoveAction(myPlayerNumber, currentPiece, moveX+currentPiece.getX(), moveY+currentPiece.getY());
    		//if yes, move there.
    	}

    	//I have made the appropriate moves, and shall wait until my next turn
    	
    	return new TacticsWaitAction(myPlayerNumber, currentPiece, currentPiece.direction);
    
    }

    private int makeAMove(TacticsPiece currentPiece) {
    	int xDist = (int) (currentPiece.speed * Math.random());
		return xDist;
	}

	private boolean isThisEvenLegal(TacticsPiece myUnit, int moveX, int moveY) {
		
    	if(((moveX+ myUnit.getX()) < 0) || ((moveY+ myUnit.getY()) < 0) || ((moveY+myUnit.getY()) > 7)||((moveX+myUnit.getX()) > 7)||
    			(((TacticsGame)this.game).boardState[moveX+myUnit.getX()][moveY+myUnit.getY()] >=3))
    	{
    		return false;
    	}
    	return true;
	}

    
    public static TacticsPiece enemyFinder(TacticsPiece thisUnit, 
    		int yourX, int yourY, 
    		Vector<TacticsPiece> myEnemies) throws NullPointerException{
    	for(TacticsPiece p : myEnemies)
    	{
    		if((p.xPos==yourX)&&(p.yPos==yourY))
    		{
    			return p;
    		}
    	}
    	throw new NullPointerException();
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
    	
    }

}
