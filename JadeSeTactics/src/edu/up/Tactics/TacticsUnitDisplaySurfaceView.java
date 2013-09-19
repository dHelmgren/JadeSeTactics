package edu.up.Tactics;

import edu.up.game.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * This Class controls the Unit's Surface View Display. It will Draw the avatar
 * of the unit on the display
 * 
 * 
 * @author Devin Helmgren
 * @author Janel Raab
 * @author Seth Scheider
 * @version December 2012
 * 
 * 
 */

public class TacticsUnitDisplaySurfaceView extends SurfaceView 
{
	// Images used to draw the Unit's Display
	Bitmap unitImage;
	Bitmap backgroundImage;

	/**
	 * TacticsUnitDisplaySurfaceView()
	 * 
	 * This is the constructor of the SurfaceView It calls its parent class and
	 * the initialize function to set up Display
	 * 
	 * @param context
	 */
	public TacticsUnitDisplaySurfaceView(Context context) 
	{
		super(context);
		initiStuff();
	}// TacticsUnitDisplaySurfaceView()

	/**
	 * TacticsUnitDisplaySurfaceView()
	 * 
	 * This is the constructor of the SurfaceView It calls its parent class and
	 * the initialize function to set up Display
	 * 
	 * @param context
	 * @param set
	 */
	public TacticsUnitDisplaySurfaceView(Context context, AttributeSet set) 
	{
		super(context, set);
		initiStuff();
	}// TacticsUnitDisplaySurfacceView

	/**
	 * TacticsUnitDisplaySurfaceView()
	 * 
	 * This is the constructor of the SurfaceView It calls its parent class and
	 * the initialize function to set up Display
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public TacticsUnitDisplaySurfaceView(Context context, AttributeSet attrs,
			int defStyle) 
	{
		super(context, attrs, defStyle);
		initiStuff();
	}// TacticsUnitDisplaySurfaceView()

	/**
	 * initiStuff()
	 * 
	 * This method initialized the Surface View to draw
	 * 
	 * @return void
	 */
	private void initiStuff() 
	{
		setWillNotDraw(false);
	}// initiStuff()

	/**
	 * draw()
	 * 
	 * This method will draw the unit avatar and background on the Unit Display
	 * Surface View
	 * 
	 * @param Canvas
	 * @return void
	 */
	@Override
	public void draw(Canvas canvas) 
	{
		// Draw the board

		// as long as the background image is not null
		// then draw it in a 3x3 square
		if (backgroundImage != null) 
		{
			for (int x = 0; x < 3; x = x + 1) 
			{
				for (int y = 0; y < 3; y = y + 1) 
				{
					canvas.drawBitmap(backgroundImage, (x * 50), (y * 50), null);
				}// y value for-loop
			}// x value for-loop
		}// if !null

		// as long as the unitImage is not null
		// then draw it onto the Canvas
		if (unitImage != null) 
		{
			canvas.drawBitmap(unitImage, 0, 0, null);
		}
	}// draw()

	/**
	 * setUnitImageType
	 * 
	 * This method is give a TacticsPiece an int value that represents the type
	 * of terrian that is to be printed as the background image
	 * 
	 * @param tempUnit
	 * @param terrianType
	 * @return void
	 */
	public void setUnitImageType(TacticsPiece tempUnit, int terrianType) {
		// Check to see what type of terrian is passed into the function
		if (terrianType == 0) {
			// set the background image to the plains
			backgroundImage = BitmapFactory.decodeResource(getResources(),
					R.drawable.plain_image);
		} else if (terrianType == 1) {
			// set the background image to forest
			backgroundImage = BitmapFactory.decodeResource(getResources(),
					R.drawable.forest_image);
		} else if (terrianType == 2) {
			// set the background image to the mountains
			backgroundImage = BitmapFactory.decodeResource(getResources(),
					R.drawable.mountain_image);
		}// ends the check for setting the background image

		// as long as the TacticsPieces passed in is not null
		if (tempUnit != null) {
			// if the unit is a pork Knight
			if ((tempUnit.unitName).equals("Pork Knight")) {
				// then print the Pork Knight Avatar
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.pork_knight_avatar);
			}
			// if the Unit is a spork Knight
			else if ((tempUnit.unitName).equals("Spork Knight")) 
			{
				// then print the Spork Knight Avatar
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.spork_knight_avatar);
			}
			//if the unit is sea star
			else if ((tempUnit.unitName).equals("Sea Star")) 
			{
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.sea_star_avatar);
			} 
			//if the unit is sea cucumber
			else if ((tempUnit.unitName).equals("Sea Cucumber")) 
			{
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.cucumber_avatar);
			}
			//if the unit is Robo Dracula
			else if ((tempUnit.unitName).equals("Robo Dracula")) 
			{
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.robodracula_avatar);
			}
			//if the unit is Cthulhu
			else if ((tempUnit.unitName).equals("Cthulhu")) 
			{
				unitImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.cthulhu_avatar);
			}
			//if it is anything else then set to null
			else 
			{
				unitImage = null;
			}
		}// if tempImage !null
		else 
		{
			unitImage = null;
		}
	}// setUnitImageType()
}// TacticsUnitDisplaySurfaceView()
