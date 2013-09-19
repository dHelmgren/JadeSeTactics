package edu.up.Tactics;

import java.util.Vector;

import edu.up.game.GameAction;
import edu.up.game.GameConfig;
import edu.up.game.LocalGame;

/**
 * --TacticsGame--
 * 
 * TacticsGame is the copy of the game state used by all of the classes, tracking
 * information about various player, unit and board conditions
 * 
 * @author Devin Helmgren
 * @author Janel Raab
 * @author Seth Scheider
 * 
 * @date 11/19/2012
 * @version 1.3
 * 
 * Changes on 11.19.2012:
 * 
 * Modified the waitAction, because any comparison of two pieces was returning false. 
 * From Now on, use piece's "compareTo()" method
 * 
 * IMPORTANT:
 * 
 */
public class TacticsGame extends LocalGame{

	//Instance Variables
	private static final long serialVersionUID = -6248656195247475778L;
	//GameState Variables
    protected Vector<TacticsPiece> playerOnePieces;
    protected Vector<TacticsPiece> playerTwoPieces;
    protected int playerOnePieceTurn;
    protected int playerTwoPieceTurn;
    protected int[][] boardState;
    protected String lastAction;
    //Final instance variables
    //PLA = plains FOR= forest MON= mountain EMP = empty OCU= occupied
    public static final int PLA_EMP = 0;
    public static final int FOR_EMP = 1;
    public static final int MON_EMP = 2;
    public static final int PLA_OCU = 3;
    public static final int FOR_OCU = 4;
    public static final int MON_OCU = 5;
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    private static final int BONUS_VALUE = 1;
    private static final int PLAYER_WEIGHT = 3;
    private static final double BASE_ACCURACY = .8;
    private static final int HIT_DIE_VALUE = 6-1;// a die of value 6

    /**
     * TacticsGame()
     * 
     * Constructor for the tactics game class, will initialize all data
     * for the game to begin
     * 
     * @param playerTheFirst a reference to the first player of JaDeSe tactics
     * @param playerTheSecond a reference to the second player of JaDeSe tactics
     */
    public TacticsGame(GameConfig initConfig,int whoseTurn) {
    	super(initConfig,whoseTurn);
    	//Initialize the state of the board to match the standard field, W/o units. 
    	int[][] boardInit = { 
    			{PLA_EMP, PLA_EMP, PLA_EMP, PLA_EMP, PLA_EMP, PLA_EMP, PLA_EMP, PLA_EMP},
    			{PLA_EMP, PLA_EMP, FOR_EMP, PLA_EMP, FOR_EMP, FOR_EMP, PLA_EMP, PLA_EMP},
    			{MON_EMP, PLA_EMP, MON_EMP, FOR_EMP, MON_EMP, MON_EMP, PLA_EMP, MON_EMP},
    			{PLA_EMP, PLA_EMP, PLA_EMP, FOR_EMP, MON_EMP, PLA_EMP, PLA_EMP, MON_EMP},
    			{MON_EMP, PLA_EMP, PLA_EMP, MON_EMP, FOR_EMP, PLA_EMP, PLA_EMP, PLA_EMP},
    			{MON_EMP, PLA_EMP, MON_EMP, MON_EMP, FOR_EMP, MON_EMP, PLA_EMP, MON_EMP},
    			{PLA_EMP, PLA_EMP, FOR_EMP, FOR_EMP, PLA_EMP, FOR_EMP, PLA_EMP, PLA_EMP},
    			{PLA_EMP, PLA_EMP, PLA_EMP, PLA_EMP, PLA_EMP, PLA_EMP, PLA_EMP, PLA_EMP}
    			};
    	boardState = boardInit;
    	//create units and vectors to hold them in
    	playerOnePieces = new Vector<TacticsPiece>();
    	playerTwoPieces = new Vector<TacticsPiece>();
    	TacticsPiece t1UnitOne = new TacticsSoldier(0,4,0, "Pork Knight");
    	TacticsPiece t2UnitOne = new TacticsSoldier(7,2,1, "Spork Knight");
    	TacticsPiece t1UnitTwo = new TacticsTank(0,3,2, "Sea Cucumber");
    	TacticsPiece t2UnitTwo = new TacticsTank(7,3,3, "Sea Star");
    	TacticsPiece t1UnitThree = new TacticsScout(0,2,4, "Robo Dracula");
    	TacticsPiece t2UnitThree = new TacticsScout(7,4,5, "Cthulhu");
    	playerOnePieces.add(t1UnitOne);
    	playerTwoPieces.add(t2UnitOne);
    	playerOnePieces.add(t1UnitTwo);
    	playerTwoPieces.add(t2UnitTwo);
    	playerOnePieces.add(t1UnitThree);
    	playerTwoPieces.add(t2UnitThree);
    	playerOnePieceTurn =0;
    	playerTwoPieceTurn =0;
    	lastAction = "";
    	
    	// place units on the board
    	for(TacticsPiece p: playerOnePieces){
    		placeUnit(p.getX(),p.getY());
    	}
    	for(TacticsPiece p: playerTwoPieces){
    		placeUnit(p.getX(),p.getY());
    	}
    	
    	
    
    }//TacticsGame()
    
	@Override
	public LocalGame getPlayerState(int playerIndex) {
		// make a copy of the current game state so that the old state can be updated.
		TacticsGame copy = new TacticsGame(getConfig(), playerIndex);
		copy.whoseTurn = this.whoseTurn;
		copy.boardState = this.boardState;
		copy.playerOnePieces = this.playerOnePieces;
		copy.playerTwoPieces = this.playerTwoPieces;
		copy.playerOnePieceTurn = this.playerOnePieceTurn;
		copy.playerTwoPieceTurn = this.playerTwoPieceTurn;
		copy.lastAction = this.lastAction;
		return copy;
	}//getPlayerState()
	 /**
     * facePieceAttacker()
     * 
     * faces the attacking piece towards the defender
     * 
     * @param pieceAttacker a tactics piece that is the attacker
     * @param pieceDefender a tactics piece that is the defender
     */
	public void facePieceAttacker(TacticsPiece pieceAttacker,TacticsPiece pieceDefender){
			// faces the attacker towards the enemy location
			if(pieceDefender.getY() < pieceAttacker.getY()) pieceAttacker.setDirection(NORTH);
			else if(pieceDefender.getY() > pieceAttacker.getY()) pieceAttacker.setDirection(SOUTH);
			else if(pieceDefender.getX() < pieceAttacker.getX()) pieceAttacker.setDirection(EAST);
			else pieceAttacker.setDirection(WEST);
			
	}//facePieceAttacker()
	
	/**
     * calculateAccuracy()
     * 
     * calculates the accuracy of an attacking piece
     * 
     * @param pieceAttacker a tactics piece that is the attacker
     * @param pieceDefender a tactics piece that is the defender
     * 
     */
	public void calculateAccuracy(TacticsPiece pieceAttacker, TacticsPiece pieceDefender){
			// if this statement is true, the attacker is behind the defender
			if(Math.abs(pieceDefender.getDirection()- pieceAttacker.getDirection()) == 0){
				pieceAttacker.setAccuracy(1.0);
			}
			//if this is true, the attacker is on the defenders side, this is the only way to fight a dragon
			//because innately, a dragon has a tail whip
			else if(Math.abs(pieceDefender.getDirection()- pieceAttacker.getDirection()) == 1){
				pieceAttacker.setAccuracy(.9);
			}
	}//calculateAccuracy()

	
    /**
     * calculateDamage()
     * 
     * calculates the damage of an attacking piece and defending piece
     * 
     * @param pieceAttacker a tactics piece that is the attacker
     * @param pieceDefender a tactics piece that is the defender
     * 
     * @return returns and int that is the final damage that will be dealt to the defender
     */
	public int calculateDamage(TacticsPiece pieceAttacker, TacticsPiece pieceDefender){
		int finalDamage =0;
    	double isHit = Math.random();
    	// check for a miss
    	if (isHit < (1-(pieceAttacker.getAccuracy())))
    	{
    		finalDamage = 0;
    		return finalDamage;
    	}
    	
    	//Determine the bonuses that apply to attack actions
    	int attackerTileType = this.boardState[pieceAttacker.getX()][pieceAttacker.getY()] - 3;
    	int defenderTileType = this.boardState[pieceDefender.getX()][pieceDefender.getY()] - 3;
    	int atkMod = 0;
    	int defMod =0;
    	if (attackerTileType == FOR_EMP)
    	{
    		atkMod = BONUS_VALUE;
    	}
    	if (defenderTileType == MON_EMP)
    	{
    		defMod = BONUS_VALUE;
    	}
    	//gets the number of D6 hit dice for the unit, include a delightfully roundabout accessor!
    	int hitDice = pieceAttacker.getAttack() + atkMod;
    	for(int i =0; i< hitDice; i++)
    	{
    		//roll damage b/t 0 and 5
    		finalDamage += HIT_DIE_VALUE * Math.random();
    		//add to between 1 and 6 per hit die
    		finalDamage++;
    	}
    	//Figures out how much to subtract from the damage
    	finalDamage -= (pieceDefender.getDefense() + defMod);
    	//Negative damage? now, lets not heal anyone
    	if(finalDamage < 0)
    	{
    		finalDamage = 0;
    	}
    	return finalDamage;
	}//calculateDamage()
	
	
	@Override
	public void applyAction(GameAction action) {
		
		if( action instanceof TacticsWaitAction){
			//variables
			TacticsPiece waitPiece;
			int waiterIndex;
			int waitDirrection = ((TacticsWaitAction) action).direction;
			
			// Get references to actual pieces and switch turn
			if(this.whoseTurn == 0){
				waiterIndex = pieceIndexFinder(((TacticsWaitAction)action).waitor, playerOnePieces);
				waitPiece = playerOnePieces.elementAt(waiterIndex);
				this.playerOnePieceTurn = (this.playerOnePieceTurn + 1)% playerOnePieces.size();
				this.whoseTurn = 1;
			}//if
			else{
				waiterIndex = pieceIndexFinder(((TacticsWaitAction)action).waitor, playerTwoPieces);
				waitPiece = playerTwoPieces.elementAt(waiterIndex);
				this.playerTwoPieceTurn = (this.playerTwoPieceTurn + 1)% playerTwoPieces.size();
				this.whoseTurn = 0;
			}//else
			
			//set the direction of the wait and set moved and attacked back to false
			waitPiece.setDirection(waitDirrection);
			waitPiece.setMoved(false);
			waitPiece.setAttacked(false);
			System.out.println(waitPiece +  " waits" + whoseTurn());
			
		}//TacticsWaitAction
		
		if(action instanceof TacticsMoveAction){
			//variables
			int placeX= ((TacticsMoveAction) action).destinationX;
			int placeY = ((TacticsMoveAction) action).destinationY;
			int moverIndex;
			TacticsPiece movePiece; 
			// get references to actual pieces
			if(this.whoseTurn() == 0){
				moverIndex = pieceIndexFinder(((TacticsMoveAction)action).mover, playerOnePieces);
				movePiece = playerOnePieces.elementAt(moverIndex);
			}
			else{
				moverIndex = pieceIndexFinder(((TacticsMoveAction)action).mover, playerTwoPieces);
				movePiece = playerTwoPieces.elementAt(moverIndex);
			}
			// lift piece off Board
			boardState[movePiece.xPos][movePiece.yPos] -= PLAYER_WEIGHT;
			// Set piece in new location
			boardState[placeX][placeY] += PLAYER_WEIGHT;
			//move actual piece and set moved to true
			movePiece.setX(placeX);
			movePiece.setY(placeY);
			movePiece.setMoved(true);
			
		}//TacticsMoveAction
		
		if(action instanceof TacticsAttackAction){
			int defenderIndex;
			int attackerIndex;
			int damageDone;
			TacticsPiece aUnit;
			TacticsPiece dUnit;
			
			//if the source was player 1
			if(this.whoseTurn == 0){
				// if defender is dead remove it from the vector
				defenderIndex = pieceIndexFinder(((TacticsAttackAction)action).defender, playerTwoPieces);
				attackerIndex = pieceIndexFinder(((TacticsAttackAction)action).attacker,playerOnePieces);
				dUnit = playerTwoPieces.elementAt(defenderIndex);
				aUnit = playerOnePieces.elementAt(attackerIndex);
			}
			else{
				defenderIndex = pieceIndexFinder(((TacticsAttackAction)action).defender, playerOnePieces);
				attackerIndex = pieceIndexFinder(((TacticsAttackAction)action).attacker,playerTwoPieces);
				dUnit = playerOnePieces.elementAt(defenderIndex);
				aUnit = playerTwoPieces.elementAt(attackerIndex);
			}
			//face attacking piece towards defender		
			facePieceAttacker(aUnit,dUnit);
			//if attacking piece is facing the back or side of the opponent, give an accuracy bonus
			calculateAccuracy(aUnit,dUnit);
			//calculate the damage that will be done
			damageDone = calculateDamage(aUnit,dUnit);
			//return the accuracy of the attacking piece back to the base accuracy
			aUnit.setAccuracy(BASE_ACCURACY);
			aUnit.setAttacked(true);
			//DEAL THE DAMAGE
			dUnit.setHealth(dUnit.getHealth() - damageDone);
			// if the unit is killed
			if(dUnit.getHealth() <= 0){		
				// give the resulting message if the target dies
				this.lastAction = aUnit.unitName +" did " + damageDone + " damage to " + dUnit.unitName + " and has killed them";
				//remove that unit from the board
				this.boardState[dUnit.getX()][dUnit.getY()] -= 3;
				
				if(this.whoseTurn() == 0){
					
					playerTwoPieces.removeElement(dUnit);
					
					if(!playerTwoPieces.isEmpty()){
						playerTwoPieceTurn = playerTwoPieceTurn % playerTwoPieces.size();
					}
					
				}
				else{
					
					playerOnePieces.removeElement(dUnit);
					
					if(!playerOnePieces.isEmpty()){
						playerOnePieceTurn = playerOnePieceTurn % playerOnePieces.size();
					}
	
				}
			}//if dead
			else{
					// gives resulting message if the target is dealt damage but is not killed
				this.lastAction = aUnit.unitName +" did " + damageDone + " damage to " + dUnit.unitName;
			}
			
		}//AttackAction
	}//Apply Action
			
		

	@Override
	public boolean isGameOver() {
		// if either one of the players has a vector of empty pieces, their pieces are dead
		if((playerOnePieces.isEmpty()) || playerTwoPieces.isEmpty()){
			return true;
		}//if
		else{
		return false;
		}//else
	}//isGameOver()

	@Override
	public int getWinnerId() {
		if(playerOnePieces.isEmpty()){
			return 1;
		}
		if(playerTwoPieces.isEmpty()){
		return 0;
		}
		//something went wrong
		return -1;
	}//getWinnerId()
	/**
     * pieceIndexFinder()
     * 
     * finds the index of the piece in the vector of pieces
     * 
     * @param tempPiece a tactics piece that is the piece you want to find the index for
     * @param playerPieces a vector of pieces in which the piece is in
     * 
     * @return returns the index of the piece 
     */
	private int pieceIndexFinder(TacticsPiece tempPiece, Vector<TacticsPiece> playerPieces) {
		for(TacticsPiece p : playerPieces)
		{
			// compare the temp piece to the pieces in the vector, we use this to get a reference to the 
			// actual piece from the serialized piece data.
			if (p.compareTo(tempPiece))
			{
				return playerPieces.indexOf(p);
			}
		}
		
		return -1;
	}//pieceIndexFinder()
	/**
     * placeUnit()
     * 
     * places a piece on the tactics board.
     * 
     * @param x is x location
     * @param y is y location
     */
	public void placeUnit(int x, int y){
		
		this.boardState[x][y]+=PLAYER_WEIGHT;
	}//placeUnit()
	
}
