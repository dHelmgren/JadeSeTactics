package edu.up.Tactics;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import edu.up.game.GameAction;
import edu.up.game.GameHumanPlayer;
import edu.up.game.R;

/**
 * --TacticsHumanPlayer--
 * 
 * TacticsHumanPlayer controls all of the layout functions associated with
 * creating and implementing actions
 * 
 * @author Devin Helmgren
 * @author Janel Raab
 * @author Seth Scheider
 * 
 * @date December 2012
 * @version 1.3
 * 
 *          IMPORTANT:
 * 
 *          Currently, areThereAjacentEnemies only checks to see if the nearby
 *          spaces are occupied, not whether or not those units are friendly.
 * 
 *          NEW ERRORS:
 * 
 *          Attacks are somehow invalid.
 * 
 */

public class TacticsHumanPlayer extends GameHumanPlayer implements
		View.OnTouchListener, View.OnClickListener {

	/** a reference to my copy of the current game state */
	protected TacticsGame tacticsGame;

	boolean inMoveMode;
	boolean inAttackMode;
	// Reference to the human player information.
	protected String message;
	protected TacticsPiece[] team;
	public boolean hasMoved;
	public boolean hasAttacked;
	public boolean hasWaited;
	protected TacticsPiece currentPiece;
	protected TacticsPiece oppPiece;
	protected int myId;
	private int[] unitBonus = new int[2];
	private int[] oppBonus = new int[2];
	private static final int FOREST_TILE = 1;
	private static final int MOUNTAIN_TILE = 2;
	private static final int BONUS_VALUE = 1;

	// Information associated with actions
	protected GameAction currentAction;
	protected int waitDirection;

	// Buttons
	protected Button moveButton;
	protected Button attackButton;
	protected Button waitButton;
	protected Button northButton;
	protected Button eastButton;
	protected Button westButton;
	protected Button southButton;
	protected Button confirmButton;
	protected Button quitButton;

	// SurfaceViews
	protected TacticsBoardSurfaceView gameBoard;
	protected TacticsUnitDisplaySurfaceView playerUnit;
	protected TacticsUnitDisplaySurfaceView opponentUnit;

	// Images
	Bitmap map;
	Bitmap porknightImage;
	Bitmap sporknightImage;

	// TextViews
	protected TextView messageTextDisplay;
	protected TextView unitTerrianTextDisplay;
	protected TextView unitName;
	protected TextView unitAttackValueTextDisplay;
	protected TextView unitAttackBonusTextDisplay;
	protected TextView unitDefenseValueTextDisplay;
	protected TextView unitDefenseBonusTextDisplay;
	protected TextView oppName;
	protected TextView oppTerrianTextDisplay;
	protected TextView oppAttackValueTextDisplay;
	protected TextView oppAttackBonusTextDisplay;
	protected TextView oppDefenseValueTextDisplay;
	protected TextView oppDefenseBonusTextDisplay;

	// Progress Bar
	protected ProgressBar unitHealth;
	protected ProgressBar oppHealth;

	// Confirm Button animation
	protected Animation confirmFlash;

	/**
	 * initializeGUI
	 * 
	 * Sets up the user interface.
	 */
	@Override
	protected void initializeGUI() {
		setContentView(R.layout.tactics_human_player);
		// initialize instance variables
		this.tacticsGame = (TacticsGame) super.game;
		this.myId = super.game.whoseTurn();
		message = tacticsGame.lastAction;
		// Buttons
		moveButton = (Button) findViewById(R.id.move_button);
		attackButton = (Button) findViewById(R.id.attack_button);
		waitButton = (Button) findViewById(R.id.wait_button);
		northButton = (Button) findViewById(R.id.north_button);
		eastButton = (Button) findViewById(R.id.east_button);
		westButton = (Button) findViewById(R.id.west_button);
		southButton = (Button) findViewById(R.id.south_button);
		confirmButton = (Button) findViewById(R.id.confirm_button);
		quitButton = (Button) findViewById(R.id.rage_Quite_button);

		// SurfaceViews
		gameBoard = (TacticsBoardSurfaceView) findViewById(R.id.GameField);
		// immediately pass the copy of the game to the gameboard
		gameBoard.setGameState(tacticsGame);
		// set it to be an onTouchListener to the board
		gameBoard.setOnTouchListener(this);

		playerUnit = (TacticsUnitDisplaySurfaceView) findViewById(R.id.currentUnitImage);
		opponentUnit = (TacticsUnitDisplaySurfaceView) findViewById(R.id.ClickedUnitImage);

		// textviews
		messageTextDisplay = (TextView) findViewById(R.id.message_Text);
		unitName = (TextView) findViewById(R.id.unit_type_name);
		unitTerrianTextDisplay = (TextView) findViewById(R.id.unit_terrian_type);
		unitAttackValueTextDisplay = (TextView) findViewById(R.id.attack_points_value);
		unitAttackBonusTextDisplay = (TextView) findViewById(R.id.unit_plus_attack_points);
		unitDefenseValueTextDisplay = (TextView) findViewById(R.id.defense_points_value);
		unitDefenseBonusTextDisplay = (TextView) findViewById(R.id.unit_plus_defense_points);
		oppName = (TextView) findViewById(R.id.oponent_unit_type_name);
		oppTerrianTextDisplay = (TextView) findViewById(R.id.oponent_terrian_type);
		oppAttackValueTextDisplay = (TextView) findViewById(R.id.oponent_attack_value);
		oppAttackBonusTextDisplay = (TextView) findViewById(R.id.oponent_plus_attack_point);
		oppDefenseValueTextDisplay = (TextView) findViewById(R.id.oponent_defense_value);
		oppDefenseBonusTextDisplay = (TextView) findViewById(R.id.oponent_plus_defense_point);

		// Progress Bar
		unitHealth = (ProgressBar) findViewById(R.id.health_value);
		oppHealth = (ProgressBar) findViewById(R.id.oponent_health);
		inMoveMode = false;
		inAttackMode = false;

		// Create button animation
		confirmFlash = new AlphaAnimation(1, 0);
		confirmFlash.setDuration(500);
		confirmFlash.setInterpolator(new LinearInterpolator()); // do not alter
																// animation
																// rate
		confirmFlash.setRepeatCount(Animation.INFINITE); // Repeat animation
															// infinitely
		confirmFlash.setRepeatMode(Animation.REVERSE);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		// show the user the game state
		updateDisplay();
	}// initializeGUI()

	/** sets the counter value in the text view */
	@Override
	protected void updateDisplay() {

		// initalizes whose turn
		if (myId == 0) {
			currentPiece = tacticsGame.playerOnePieces
					.elementAt(tacticsGame.playerOnePieceTurn);
		} else {
			currentPiece = tacticsGame.playerTwoPieces
					.elementAt(tacticsGame.playerTwoPieceTurn);
		}
		// set up the values on the field
		hasAttacked = currentPiece.hasAttacked;
		hasMoved = currentPiece.hasMoved;
		// get the opponent Piece
		oppPiece = gameBoard.selectedPiece;
		// set the Bonus Values
		setBonus(oppPiece);
		// set the opponent Field
		setOppField(oppPiece);
		// set all the unit Display information
		unitAttackValueTextDisplay.setText(" " + currentPiece.attackPower);
		unitAttackBonusTextDisplay.setText("+" + unitBonus[0]);
		unitDefenseValueTextDisplay.setText(" " + currentPiece.defensePower);
		unitDefenseBonusTextDisplay.setText("+" + unitBonus[1]);
		unitName.setText(currentPiece.unitName);
		unitHealth.setMax(currentPiece.maxHealth);
		unitHealth.setProgress(currentPiece.health);
		// set the message about the final attack action that might have taken
		// place
		messageTextDisplay.setText(message);

		// change it back to the original set up
		this.originalSetUp();
		// redraw the board
		gameBoard.invalidate();

	}// updateDisplay()

	/**
	 * onClick()
	 * 
	 * listener for Click events
	 * 
	 * @param button
	 *            is the Click view
	 */
	public void onClick(View button) {

	}// onClick()

	/**
	 * onMoveClick()
	 * 
	 * calls the move action
	 * 
	 * @param moveButton
	 *            is the moveClick view
	 */
	public void onMoveClick(View moveButton) {
		inMoveMode();
		// set up the board to move a Piece
		gameBoard.moveSelect();
		// set the Move Mode
		inMoveMode = true;
		// enable the confirmButton
		this.confirmButton.setEnabled(true);
		// set the confirmButton to flash
		confirmButton.startAnimation(confirmFlash);
		// this.moveButton.setEnabled(true);

	}// onMoveClick()

	/**
	 * onAttackClick()
	 * 
	 * calls the attack action
	 * 
	 * @param attackButton
	 *            is that attack button
	 */
	public void onAttackClick(View attackButton) {
		this.attackButton.setEnabled(false);
		// If there are no valid targets, do not continue
		if (!areThereAdjacentEnemies())
			return;

		inAttackMode();
		gameBoard.attackSelect();
		inAttackMode = true;

		this.confirmButton.setEnabled(true);
		this.confirmButton.startAnimation(confirmFlash);

		// is this valid?? are there enemies??

		// Using information from the player, draw selection boxes over adjacent
		// enemies

		// When an enemy has been selected, enable the confirm button (thread
		// while we wait?)
		// and set the enemy to oppPiece

		// currentAction = new
		// TacticsAttackAction(myId,currentPiece,oppPiece,tacticsGame);
	}// onAttackClick

	/**
	 * onWaitClick()
	 * 
	 * calls the wait action
	 * 
	 * @param waitButton
	 *            is the wait button
	 */
	public void onWaitClick(View waitButton) {
		inWaitMode();
	}// onWaitClick

	/**
	 * onDirrectionClick()
	 * 
	 * does the direction action
	 * 
	 * @param dirrectionButton
	 *            is the direction button
	 */
	public void onDirectionClick(View directionButton) {

		if (directionButton == northButton) {
			currentAction = new TacticsWaitAction(myId, currentPiece, 0);
		} else if (directionButton == westButton) {
			currentAction = new TacticsWaitAction(myId, currentPiece, 1);
		} else if (directionButton == southButton) {
			currentAction = new TacticsWaitAction(myId, currentPiece, 2);
		} else if (directionButton == eastButton) {
			currentAction = new TacticsWaitAction(myId, currentPiece, 3);
		}

		this.confirmButton.setEnabled(true);
		this.confirmButton.startAnimation(confirmFlash);

	}// onDirectionClick()

	/**
	 * onConfirmClick()
	 * 
	 * does the confirm action
	 * 
	 * @param confirmButton
	 *            is the confirm button
	 */
	public void onConfirmClick(View confirmButton) {
		confirmButton.clearAnimation();
		if (inMoveMode && (gameBoard.chosenTile != null)) {
			TacticsCustomRect selectedSpace = gameBoard.chosenTile;
			currentAction = new TacticsMoveAction(myId, currentPiece,
					selectedSpace.boardX, selectedSpace.boardY);
		} 
		else if (inAttackMode && (gameBoard.chosenTile != null)) {
			TacticsCustomRect selectedSpace = gameBoard.chosenTile;
			TacticsPiece target;
			if (myId == 0) {
				try {
					target = TacticsRandoCalrissian.enemyFinder(currentPiece,
							selectedSpace.boardX, selectedSpace.boardY,
							((TacticsGame) game).playerTwoPieces);
				} catch (NullPointerException e) {
					System.out.println("invalid Target");
					target = null;
				}
			} else {
				try {
					target = TacticsRandoCalrissian.enemyFinder(currentPiece,
							selectedSpace.boardX, selectedSpace.boardY,
							((TacticsGame) game).playerOnePieces);
				} catch (NullPointerException e) {
					System.out.println("invalid target");
					target = null;
				}
			}
			if(target!=null)
				currentAction = new TacticsAttackAction(myId, currentPiece, target);
		}
		if (currentAction != null) {
			takeAction(currentAction);
		}
		else
		{
			originalSetUp();
		}
		
	}// onConfirmClick()

	/**
	 * onQuitClick()
	 * 
	 * does the Quit action
	 * 
	 * @param quitButton
	 *            is the quit button
	 * @return void
	 */
	public void onQuitClick(View quitButton) {
		takeAction(null);
	}// onQuiteClick()

	/**
	 * setMessage()
	 * 
	 * This sets the message displayed for attack Action
	 * 
	 * @param newMessage
	 * @return void
	 */
	public void setMessage(String newMessage) {
		message = newMessage;

	}// setMessage()

	/**
	 * OriginalSetUP()
	 * 
	 * This method sets up the GUI to it's original set up if the player is not
	 * making a move.
	 * 
	 * @return void
	 */
	private void originalSetUp() {
		if (this.hasAttacked) {
			attackButton.setEnabled(false);
		} else {
			attackButton.setEnabled(true);
		}

		if (this.hasMoved) {
			moveButton.setEnabled(false);
		} else {
			moveButton.setEnabled(true);
		}
		
		waitButton.setEnabled(true);
		northButton.setEnabled(false);
		southButton.setEnabled(false);
		eastButton.setEnabled(false);
		westButton.setEnabled(false);

		confirmButton.setEnabled(false);
	}// OriginalSetUP()

	/**
	 * inAttackMode()
	 * 
	 * Enables buttons accordingly to the game being in attack mode
	 * 
	 * @return void
	 */
	private void inAttackMode() {
		attackButton.setEnabled(false);
		moveButton.setEnabled(false);
		northButton.setEnabled(false);
		southButton.setEnabled(false);
		eastButton.setEnabled(false);
		westButton.setEnabled(false);
		waitButton.setEnabled(false);
		confirmButton.setEnabled(false);
	}// inAttackeMode()

	/**
	 * inWaitMode()
	 * 
	 * Enables buttons accordingly to the game being in move mode
	 * 
	 * @return void
	 */
	private void inWaitMode() {
		this.moveButton.setEnabled(false);
		this.attackButton.setEnabled(false);
		this.waitButton.setEnabled(false);
		this.eastButton.setEnabled(true);
		this.northButton.setEnabled(true);
		this.southButton.setEnabled(true);
		this.westButton.setEnabled(true);
	}// inWaitMode()

	/**
	 * inMoveMode()
	 * 
	 * Sets up the GUI as if the game was in moved mode
	 * 
	 * @return void
	 */
	private void inMoveMode() {
		attackButton.setEnabled(false);
		moveButton.setEnabled(false);
		northButton.setEnabled(false);
		southButton.setEnabled(false);
		eastButton.setEnabled(false);
		westButton.setEnabled(false);
		waitButton.setEnabled(false);
		confirmButton.setEnabled(false);
	}// inMoveMode()

	/**
	 * areThereAdjacentEnemies()
	 * 
	 * checks the surrounding tiles to see if the current unit is adjacent to
	 * enemies.
	 * 
	 * @return returns true if there are enemies next to the unit.
	 */
	private boolean areThereAdjacentEnemies() 
	{
		// Check to North
		if (currentPiece.yPos != 0) {
			if (((TacticsGame) this.game).boardState[currentPiece.xPos][currentPiece.yPos - 1] >= 3) 
			{
				return true;
			}
		}
		// Check to South
		if (currentPiece.yPos != 7) 
		{
			if (((TacticsGame) this.game).boardState[currentPiece.xPos][currentPiece.yPos + 1] >= 3) 
			{
				return true;
			}
		}
		// Check to West
		if (currentPiece.xPos != 7) 
		{
			if (((TacticsGame) this.game).boardState[currentPiece.xPos + 1][currentPiece.yPos] >= 3) 
			{
				return true;
			}
		}
		// Check to East
		if (currentPiece.xPos != 0) 
		{
			if (((TacticsGame) this.game).boardState[currentPiece.xPos - 1][currentPiece.yPos] >= 3) 
			{
				return true;
			}
		}

		return false;
	}// areThereAdjacentEnemies()

	/**
	 * setOppField()
	 * 
	 * This method set the display field accordingly to whether or no the piece
	 * passed in is null or not
	 * 
	 * @param opp
	 * @return void
	 */
	private void setOppField(TacticsPiece opp) {

		if (opp != null) 
		{
			// set up the values of the current piece.

			oppAttackValueTextDisplay.setText(" " + opp.attackPower);
			oppDefenseValueTextDisplay.setText(" " + opp.defensePower);
			oppName.setText(opp.unitName);
			oppHealth.setMax(opp.maxHealth);
			oppHealth.setProgress(opp.health);
		} 
		else 
		{
			oppAttackValueTextDisplay.setText(" ");
			oppDefenseValueTextDisplay.setText(" ");
			oppName.setText(" ");
			oppHealth.setMax(0);
			oppHealth.setProgress(0);
		}

	}// setOppField()

	/**
	 * setBonus()
	 * 
	 * This method sets the bonus value displays for the TacticsPiece passed in
	 * and sets the values for the current piece
	 * 
	 * @param opp
	 * @return void
	 */
	private void setBonus(TacticsPiece opp) {

		// initialize the vectors to be zeros
		unitBonus[0] = 0;
		unitBonus[1] = 0;
		oppBonus[0] = 0;
		oppBonus[1] = 0;

		int pieceUnitTileType = (((TacticsGame) this.game).boardState[currentPiece
				.getX()][currentPiece.getY()]) - 3;

		playerUnit.setUnitImageType(currentPiece, pieceUnitTileType);
		
		
		if (pieceUnitTileType == FOREST_TILE) 
		{
			unitBonus[0] = BONUS_VALUE;
			unitBonus[1] = 0;
			unitTerrianTextDisplay.setText("Forest");

		} 
		else if (pieceUnitTileType == MOUNTAIN_TILE) 
		{
			unitBonus[1] = BONUS_VALUE;
			unitBonus[0] = 0;
			unitTerrianTextDisplay.setText("Mountain");
		} 
		else 
		{
			unitBonus[0] = 0;
			unitBonus[1] = 0;
			unitTerrianTextDisplay.setText("Plain");
		}

		// if the opp piece is not null
		if (opp != null) 
		{
			// then it is a piece on a tile so get the tile value
			int pieceOppTileType = ((TacticsGame) this.game).boardState[opp
					.getX()][opp.getY()] - 3;
			// set the UnitImage with the given values so they can be displayed
			// in the UnitDisplaySurfaceView
			opponentUnit.setUnitImageType(opp, pieceOppTileType);
			// redraw the UnitDisplay
			opponentUnit.invalidate();
			// set the bonus text values
			if (pieceOppTileType == FOREST_TILE) {
				oppAttackBonusTextDisplay.setText("+1");
				oppDefenseBonusTextDisplay.setText("+0");
				oppTerrianTextDisplay.setText("Forest");
			}
			else if (pieceOppTileType == MOUNTAIN_TILE) 
			{
				oppAttackBonusTextDisplay.setText("+0");
				oppDefenseBonusTextDisplay.setText("+1");
				oppTerrianTextDisplay.setText("Mountain");
			}
			else 
			{
				oppAttackBonusTextDisplay.setText("+0");
				oppDefenseBonusTextDisplay.setText("+0");
				oppTerrianTextDisplay.setText("Plain");
			}
		}// if opp !null
		else {
			// then it must be a tile so get the tile type
			int clickedTile = gameBoard.clickedTileType;
			// set the Image with a null
			opponentUnit.setUnitImageType(null, clickedTile);
			// redraw the display
			opponentUnit.invalidate();
			// set the bonus fields
			if (clickedTile == FOREST_TILE) 
			{
				oppTerrianTextDisplay.setText("Forest");
				oppDefenseBonusTextDisplay.setText("+0");
				oppAttackBonusTextDisplay.setText("+1");
			}
			else if (clickedTile == MOUNTAIN_TILE) 
			{
				oppTerrianTextDisplay.setText("Mountain");
				oppDefenseBonusTextDisplay.setText("+1");
				oppAttackBonusTextDisplay.setText("+0");

			} 
			else 
			{
				oppTerrianTextDisplay.setText("Plain");
				oppDefenseBonusTextDisplay.setText("+0");
				oppAttackBonusTextDisplay.setText("+0");
			}
		}// end oppPiece is null
	}// setBonus()

	/**
	 * onTouch()
	 * 
	 * This is when everytime the board is touched the game behaves accordingly,
	 * such as selecting a Tile
	 * 
	 * @param Veiw
	 * @param MotionEvent
	 * @return boolean
	 */
	public boolean onTouch(View v, MotionEvent event) 
	{
		// get the location of clicked object
		int clickedX = (int) event.getX();
		int clickedY = (int) event.getY();
		// call the gameBoard's onTouch() method
		gameBoard.touched(v, event);
		// Refresh the unit's information
		// set up the values of the current piece and opp Piece
		oppPiece = gameBoard.pieceOnTile(clickedX, clickedY);
		setBonus(oppPiece);
		setOppField(oppPiece);
		messageTextDisplay.setText(message);
		unitAttackValueTextDisplay.setText(" " + currentPiece.attackPower);
		unitAttackBonusTextDisplay.setText("+" + unitBonus[0]);
		unitDefenseValueTextDisplay.setText(" " + currentPiece.defensePower);
		unitDefenseBonusTextDisplay.setText("+" + unitBonus[1]);
		unitName.setText(currentPiece.unitName);
		unitHealth.setProgress(currentPiece.health);
		return true;
	}// onTouch()

}// class TacticsHumanPlayer
