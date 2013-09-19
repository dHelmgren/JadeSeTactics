package edu.up.Tactics;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * <!-- class CustomRect -->
 * 
 * This class defines a custom drawing element that is a rectangle.
 * 
 * @author Andrew Nuxoll
 * Adapted By:
 * @author Devin Helmgren
 * @version November 2012
 * @see CustomElement
 * 
 * IMPORTANT DETAILS:
 * 
 * Each of these rectangles will have a set color and highlight color
 * Each of these rect's will have an X and Y location as relates to the TacticsBoard
 * They will also NO LONGER HAVE A TAP MARGIN. This is to prevent difficulties when
 * 	selecting from several adjacent squares.
 *
 */

public class TacticsCustomRect extends TacticsCustomElement {


	/** the position and size of the rectangle is stored here */
	protected Rect myRect;
	//Board position, between 0 and 7
	protected int boardX;
	protected int boardY;
	
	/** the rectangles dimensions must be defined at construction */
	public TacticsCustomRect(String name, int color, 
			int left, int top, int right, int bottom,
			int x, int y)
	{
		super(name, color);
		boardX = x;
		boardY = y;
		
		this.myRect = new Rect(left, top, right, bottom);
	}//TacticsCustomRect()
	
	
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawRect(myRect, myPaint);  //main rectangle
		canvas.drawRect(myRect, outlinePaint);  //outline around rectangle
	}//onDraw()
	
	@Override
	public boolean containsPoint(int x, int y) {
		
		//Want to check for a tap within a slightly larger rectangle
		int left = this.myRect.left - TAP_MARGIN;
		int top = this.myRect.top - TAP_MARGIN;
		int right = this.myRect.right + TAP_MARGIN;
		int bottom = this.myRect.bottom + TAP_MARGIN;
		Rect r = new Rect(left, top, right, bottom);
		
		return r.contains(x, y);
	}//contaisPoint()


	@Override
	public int getSize() {
		return this.myRect.width() * this.myRect.height();
	}//getSize()

	@Override
	public void drawHighlight(Canvas canvas) {
		canvas.drawRect(myRect, highlightPaint);
		canvas.drawRect(myRect, outlinePaint);  //keep outline so it stands out
	}//drawHighlight()

	
	
	
	
}//class CustomRect
