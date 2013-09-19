package edu.up.Tactics;

import java.util.Vector;

import edu.up.game.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

/**
 * This class is the extension of the Surface View to display the updated
 * version of the board from the game state
 * 
 * @author Devin Helmgren
 * @author Janel Raab
 * @author Seth Scheider
 * 
 * @version December 2012
 * 
 */
public class TacticsBoardSurfaceView extends SurfaceView implements Runnable {
	// Copy of the tactics game
	TacticsGame tacticsGame;
	// The size of the single tile 50pixels x 50pixels
	public static final int TILE_SIZE = 50;

	// Array of Tile boarders 8x8 Tiles
	protected TacticsCustomRect[][] boardSelectable = new TacticsCustomRect[8][8];
	// Mark a Vectors of Tiles that are legal to select
	protected Vector<TacticsCustomRect> rectsSelect;
	// The selectable highlighted color
	int ColorSelectable = Color.argb(127, 0, 255, 255);
	int CurrrentPlayer = Color.argb(127, 255,0,255);
	// this is a void element that will be set when there is no object selected
	protected TacticsCustomRect notExist = null;
	// holder that holds the currently clicked Tile
	protected TacticsCustomRect currentTile = notExist;
	// holder that holds the tile that has been most recently chosen for an
	// action
	protected TacticsCustomRect chosenTile = notExist;
	// will keep an integer value to indicate what type of tile has been clicked
	protected int clickedTileType;
	// the selected Tactics Piece
	protected TacticsPiece selectedPiece;
	// this is a boolean that allows for a set up
	// if the game is in selection mode from move
	boolean isInSelectionMode = false;
	// if the game is in attack mode
	boolean isInAttackSelect = false;
	// This is the Bitmap that will draw the map
	Bitmap map;
	// This is the Bitmap that will draw the unitImage
	Bitmap unitImage;

	/**
	 * TacticsBoardSurfaceView()
	 * 
	 * This method is the constructor of the Board Surface View and calls the
	 * parent's constructor and the initialize method
	 * 
	 * @param context
	 */
	public TacticsBoardSurfaceView(Context context) {
		super(context);
		initiStuff();
	}// TacticsBoardSurfaceView()

	/**
	 * TacticsBoardSurfaceView()
	 * 
	 * This method is the constructor of the Board Surface View and calls the
	 * parent's constructor and the initialize method
	 * 
	 * @param context
	 * @param set
	 */
	public TacticsBoardSurfaceView(Context context, AttributeSet set) {
		super(context, set);
		initiStuff();
	}// TacticsBoardSurfaceView()

	/**
	 * TacticsBoardSurfaceView()
	 * 
	 * This method is the constructor of the Board Surface View and calls the
	 * parent's constructor and the initialize method
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public TacticsBoardSurfaceView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initiStuff();
	}// TacticsBoardSurfaceView()

	/**
	 * initiStuff()
	 * 
	 * This method initializes the variables
	 * 
	 * @return void
	 */
	private void initiStuff() {
		// set the board to draw
		setWillNotDraw(false);
		// set the current tile to null
		currentTile = null;
		// initialize the Vectors for selectable Tiles
		rectsSelect = new Vector<TacticsCustomRect>();
		// set up the graphics image of the map to be drawn
		map = BitmapFactory
				.decodeResource(getResources(), R.drawable.map_image);

		// Set up the double array of rects
		for (int x = 0; x < 8; x = x + 1) {
			for (int y = 0; y < 8; y = y + 1) {
				TacticsCustomRect temp = new TacticsCustomRect(
						("x" + x + "y" + y), Color.TRANSPARENT, 50 * x, 50 * y,
						(50 + 50 * x), (50 + 50 * y), x, y);
				boardSelectable[x][y] = temp;
			}// for-loop y
		}// for-loop x

	}// initStuff()

	/**
	 * draw()
	 * 
	 * This method draws the current state of the board
	 * 
	 * @param canvas
	 * @return void
	 */
	@Override
	public void draw(Canvas canvas) {
		// Draw the board image
		canvas.drawBitmap(map, 0, 0, null);
		TacticsCustomRect curPlayerTile = this.getCurPlayerTile();
		
		// draw the boards for the tiles
		for (int x = 0; x < 8; x = x + 1) {
			for (int y = 0; y < 8; y = y + 1) {

				// If the certain tile is legal to select then highlight it
				if (rectsSelect.contains(boardSelectable[x][y]) == true) {
					// set the color to the Selectable color
					boardSelectable[x][y].setColor(ColorSelectable);
				}
				//if this is the tile the currentPiece is on
				else {
					// set the color to Transparent
					boardSelectable[x][y].setColor(Color.TRANSPARENT);
				}

				// if the current tile is selected then draw the highlight
				if ((currentTile == boardSelectable[x][y])) {
					boardSelectable[x][y].drawHighlight(canvas);
				}
				// Draw the square
				boardSelectable[x][y].onDraw(canvas);

			}// for-loop y
		}// for-loop x
		
		curPlayerTile.setColor(CurrrentPlayer);
		curPlayerTile.onDraw(canvas);

		// Get the teamOne Vector of pieces
		Vector<TacticsPiece> teamOne = this.tacticsGame.playerOnePieces;
		// get the teamTwo Vector of the piece
		Vector<TacticsPiece> teamTwo = this.tacticsGame.playerTwoPieces;

		// Draw all the pieces for team one
		for (TacticsPiece unitOne : teamOne) {
			// set the unit image so that it can be drawn from the game state
			this.setUnitImages(unitOne, unitOne.direction);
			// draw it on the canvas
			canvas.drawBitmap(unitImage, (unitOne.getX() * 50) + 5,
					(unitOne.getY() * 50) + 5, null);
		}// for-loop for team one

		// Draw all the pieces for team two
		for (TacticsPiece unitTwo : teamTwo) {
			// set the unit image so that is can be drawn from the game state
			this.setUnitImages(unitTwo, unitTwo.direction);
			// draw it on the canvas
			canvas.drawBitmap(unitImage, (unitTwo.getX() * 50) + 5,
					(unitTwo.getY() * 50) + 5, null);

		}// for-loop for team two

	}// draw()

	/**
	 * touched()
	 * 
	 * This method is the onTouch method for the Surface View It should be
	 * called during the TacticsHumanPlayer's OnTouch method. It sets what mode
	 * the board is in, and it sets the currentClicked Tile
	 * 
	 * @param v
	 * @param event
	 */
	public void touched(View v, MotionEvent event) {
		// get the location of clicked object
		int clickedX = (int) event.getX();
		int clickedY = (int) event.getY();
		// set the Tile that is at the clicked location
		getElement(clickedX, clickedY);
		// if it is in a special mode, set the board accordingly
		if (isInSelectionMode) {
			setChosenTile();
			if (isInAttackSelect) {
				setAttackTile();
			}
		}// if isInSelectionMode

		// get the type of tile that was clicked on
		clickedTileType = this.tacticsGame.boardState[currentTile.boardX][currentTile.boardY];
		// if the value is greater than, or equal to 3 than subtract 3 to get
		// the base value to determine the type of the tile
		if (clickedTileType >= 3) {
			clickedTileType = clickedTileType - 3;
		}
		// get the Piece if it is on the tile clicked
		selectedPiece = pieceOnTile(clickedX, clickedY);
		// redraw the board
		this.invalidate();
	}// touched()

	/**
	 * getElement()
	 * 
	 * This method returns the element in the Vector if it contains the x and y
	 * coordinates passed into the function
	 * 
	 * @param int xinit
	 * @param int yinit
	 * 
	 * @return CustomElement
	 * */
	public void getElement(int xinit, int yinit) {
		for (int x = 0; x < 8; x = x + 1) {
			for (int y = 0; y < 8; y = y + 1) {
				// if the tile contains the x and y location
				if (boardSelectable[x][y].containsPoint(xinit, yinit)) {
					// then return that element
					currentTile = boardSelectable[x][y];
				}// if equals name
			}// for-loop y
		}// for-loop x
	}// getElement()
	
	public TacticsCustomRect getCurPlayerTile()
	{
		//get current piece
				// get whose turn it is
						int myId = tacticsGame.whoseTurn();
						TacticsPiece currentPieceMove;
						// get the piece that is currently moving
						if (myId == 0) {
							currentPieceMove = tacticsGame.playerOnePieces // Should read one
									.elementAt(this.tacticsGame.playerOnePieceTurn);
						} else {
							currentPieceMove = this.tacticsGame.playerTwoPieces
									.elementAt(this.tacticsGame.playerTwoPieceTurn);
						}
						int xLoc = (currentPieceMove.xPos+1)*50;
						int yLoc = (currentPieceMove.yPos+1)*50;
						for (int x = 0; x < 8; x = x + 1) {
							for (int y = 0; y < 8; y = y + 1) {
								// if the tile contains the x and y location
								if (boardSelectable[x][y].containsPoint(xLoc, yLoc)) {
									// then return that element
									return boardSelectable[x][y];
								}// if equals name
							}// for-loop y
						}// for-loop x
						return null;
	}

	/**
	 * pieceOnTile()
	 * 
	 * This method returns the piece on the x and y location passed into it. If
	 * there is no piece then it passes a null Piece
	 * 
	 * @param x
	 * @param y
	 * @return TacticsPiece
	 */
	public TacticsPiece pieceOnTile(int x, int y) {
		// set the x and y values so that it matches the board[][] indexs
		x = x / 50;
		y = y / 50;
		// get the vector of the two team's pieces
		Vector<TacticsPiece> teamOne = this.tacticsGame.playerOnePieces;
		Vector<TacticsPiece> teamTwo = this.tacticsGame.playerTwoPieces;
		// loop through the first team to see if the piece is in there
		for (TacticsPiece unitOne : teamOne) {
			// if the piece has the x and y value
			if (((unitOne).getX() == x) && ((unitOne.getY() == y))) {
				// return the piece
				return unitOne;
			}
		}// for-loop teamOne
			// do the same for team two
		for (TacticsPiece unitTwo : teamTwo) {
			if ((unitTwo.getX() == x) && ((unitTwo.getY() == y))) {
				return unitTwo;
			}
		}// for-loop teamTwo

		// if there is no piece on that tile, then return null
		return null;
	}// pieceOnTile()

	/**
	 * attackSelect()
	 * 
	 * This methods this sets up the board for attack Selection By going through
	 * the board and seeing if each Tile meets the requirements for attack
	 * selection, then if so stores the tile as a Selectable tile in the Vector
	 * So that when the board is drawn, it draws the tiles as selectable.
	 * 
	 * @return void
	 */
	public void attackSelect() {
		// get the ID of whose's Turn it is.
		int myId = tacticsGame.whoseTurn();

		TacticsPiece currentPieceMove;

		// get the piece that is currently the pieces moving
		if (myId == 0) {
			currentPieceMove = tacticsGame.playerOnePieces // Should read one
					.elementAt(this.tacticsGame.playerOnePieceTurn);
		} else {
			currentPieceMove = this.tacticsGame.playerTwoPieces
					.elementAt(this.tacticsGame.playerTwoPieceTurn);
		}
		// get the piece's x and y Location
		int xLoc = currentPieceMove.xPos;
		int yLoc = currentPieceMove.yPos;

		// get the pieces around it that are able to select to attack.
		for (int i = -1; i <= 1; i = i + 1) {
			for (int j = -1; j <= 1; j = j + 1) {
				if ((((Math.abs(i) + Math.abs(j)) <= 1) && (0 <= (i + xLoc))
						&& ((i + xLoc) <= 7) && (0 <= (j + yLoc)) && ((j + yLoc) <= 7))
						&& (i != j)) {
					// if it is a legal selectable tile, then push it into the
					// Vector
					rectsSelect.add(boardSelectable[xLoc + i][yLoc + j]);
				}// if legal check

			}// for-loop j
		}// for-loop i

		// Redraw the board
		this.invalidate();
		// set the Special modes to true
		isInSelectionMode = true;
		isInAttackSelect = true;
	}// attackSelect()

	/**
	 * moveSelect()
	 * 
	 * This method goes through the board and selects the Tiles that are legal
	 * moves for the piece, and pushes them to the selectable Vector
	 * 
	 * @return void
	 */
	public void moveSelect() {
		// Re-draw the image so the user sees which moves are legal

		// once one is selected (if it is a legal move) re-draw so that only
		// selected space is high-lit

		// return the remaining rectangle.

		// get whose turn it is
		int myId = tacticsGame.whoseTurn();
		TacticsPiece currentPieceMove;
		// get the piece that is currently moving
		if (myId == 0) {
			currentPieceMove = tacticsGame.playerOnePieces // Should read one
					.elementAt(this.tacticsGame.playerOnePieceTurn);
		} else {
			currentPieceMove = this.tacticsGame.playerTwoPieces
					.elementAt(this.tacticsGame.playerTwoPieceTurn);
		}
		// get the pieces speed and x and y location
		int speed = currentPieceMove.speed;
		int xLoc = currentPieceMove.xPos;
		int yLoc = currentPieceMove.yPos;

		// go through the board and determine which tiles are legal moves
		for (int i = -speed; i <= speed; i = i + 1) {
			for (int j = -speed; j <= speed; j = j + 1) {
				// check to see if the tile is a legal move
				if ((((Math.abs(i) + Math.abs(j)) <= speed)
						&& (0 <= (i + xLoc)) && ((i + xLoc) <= 7)
						&& (0 <= (j + yLoc)) && ((j + yLoc) <= 7))) {
					// if it is legal then push it onto the Selectable Vector
					rectsSelect.add(boardSelectable[xLoc + i][yLoc + j]);
				}// if legal check

			}// for-loop j
		}// for-loop i

		// redraw the board
		this.invalidate();
		// change the board into selction mode
		isInSelectionMode = true;
	}// moveSelect()

	/**
	 * endSelection()
	 * 
	 * This method clears the Selectable Vector of all its Tiles, resets the
	 * mode the board is in, and resets the chosenTile
	 * 
	 * @return void
	 */
	public void endSelection() {
		// empty the vector
		for (TacticsCustomRect temp : rectsSelect) {
			rectsSelect.remove(temp);
		}
		// reset the choseTiel
		chosenTile = null;
		// turn off the selectionMode
		isInSelectionMode = false;
	}// endSelection()

	/**
	 * setGameState()
	 * 
	 * This method gives the BoardSurfaceView a copy of the game. It is
	 * immediately called by the TacticsHumanPlayer when initalizig the board.
	 * This is to help making coding easier for the Board who will now have
	 * access to the state of the game
	 * 
	 * @param copyGame
	 * @return void
	 */
	public void setGameState(TacticsGame copyGame) {
		tacticsGame = copyGame;
	}// setGameState

	/**
	 * setChoseTile()
	 * 
	 * This methods sets the current Tile if it is a legal move to the chosen
	 * tile
	 * 
	 * @return void
	 */
	public void setChosenTile() {
		// if the tile is in Rect Select and is unoccupied
		if (rectsSelect.contains(currentTile)
				&& (this.tacticsGame.boardState[currentTile.boardX][currentTile.boardY] < 3)) {
			// then set the currentTile to the chosenTile
			chosenTile = currentTile;

		}
	}// setChosenTile()

	/**
	 * setAttackTile()
	 * 
	 * This method sets the current Tile if it is a legal attack to the chosen
	 * tile
	 */
	public void setAttackTile() {
		if (rectsSelect.contains(currentTile)
				&& (this.tacticsGame.boardState[currentTile.boardX][currentTile.boardY] >= 3)) {

			chosenTile = currentTile;
		}
	}// setAttackTile

	public void run() {
		this.moveSelect();
	}

	/**
	 * setUnitImages()
	 * 
	 * This method given a TacticsPiece and a direction will set a Bitmap Image
	 * to the correct sprite image
	 * 
	 * @param tempUnit
	 * @param direction
	 * @return void
	 */
	private void setUnitImages(TacticsPiece tempUnit, int direction) {
		// 0 is north, 1 is west, 2 is south 3 is east

		if ((tempUnit.unitName).equals("Pork Knight")) {
			if (direction == 0) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.pork_knight_north);
			} else if (direction == 1) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.pork_knight_west);
			} else if (direction == 2) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.pork_knight_south);
			} else if (direction == 3) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.pork_knight_east);
			}
		}//end if PorkKight
		else if ((tempUnit.unitName).equals("Spork Knight")) {
			if (direction == 0) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.spork_knight_north);
			} else if (direction == 1) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.spork_knight_west);
			} else if (direction == 2) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.spork_knight_south);
			} else if (direction == 3) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.spork_knight_east);
			}
		}//end if Spork Knight
		else if ((tempUnit.unitName).equals("Sea Star")) {
			if (direction == 0) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.blue_pirate_north);
			} else if (direction == 1) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.blue_pirate_west);
			} else if (direction == 2) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.blue_pirate_south);
			} else if (direction == 3) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.blue_pirate_east);
			}
		}//end if Sea Cucumber 
		else if ((tempUnit.unitName).equals("Sea Cucumber")) {
			if (direction == 0) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.red_pirate_north);
			} else if (direction == 1) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.red_pirate_west);
			} else if (direction == 2) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.red_pirate_south);
			} else if (direction == 3) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.red_pirate_east);
			}
		}//ends if sea Star
		else if ((tempUnit.unitName).equals("Robo Dracula")) {
			if (direction == 0) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.red_ninja_north);
			} else if (direction == 1) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.red_ninja_west);
			} else if (direction == 2) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.red_ninja_south);
			} else if (direction == 3) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.red_ninja_east);
			}
		}//ends if Robo Dracula 
		else if ((tempUnit.unitName).equals("Cthulhu")) {
			if (direction == 0) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.blue_ninja_north);
			} else if (direction == 1) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.blue_ninja_west);
			} else if (direction == 2) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.blue_ninja_south);
			} else if (direction == 3) {
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.blue_ninja_east);
			}
		}//ends if Cthulhu

	}//setUnitImages()

}//TacticsBoardSurfaceView
