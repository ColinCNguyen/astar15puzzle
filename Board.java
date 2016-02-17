/**
 * Implements a text-based 15-puzzle board.
 *
 * @author  Terry Sergeant & Colin Nguyen
 * @version Spring 2016
 *
 */

public class Board implements Comparable<Board>
{
	private int rows,cols; 		// board size in rows/cols
	private char [] tiles; 		// the actual tiles and their locations
	private char dir;      		// direction prev used to get here
	private int  bpos;     		// position of blank element
	private Board prev;    		// pointer to previous state
	private int	h;         		// heuristic value (manhattan distance)
	private int	g;         		// cost so far
	private char[]changeThis; 	//new tile array after a move


	/**
	 * Constructor based on starting tile positions and board dimensions.
	 *
	 * @param tiles an array of tile positions
	 * @param rows number of rows on the board
	 * @param cols number of columns on the board
	 *
	 * <p>We do not make sure the tile list is valid. It should contain
	 * exactly the int/char values 0 - rows*cols-1. The tile numbered
	 * rows*cols-1 is interpreted as the blank tile.</p>
	 */
	public Board(char [] tiles, int rows, int cols)
	{
		this.rows= rows;
		this.cols= cols;
		this.tiles= tiles.clone();
		this.bpos= new String(tiles).indexOf((char) rows*cols-1);
		this.dir= 'X';       // initially direction is not valid
		this.prev= null;     // and no previous board
		this.h= manhattan(); // use manhattan distance to estimate position
		this.g= 0;           // no steps taken with a new board
	}


	/**
	 * Overrides compareTo to provide proper order in priority queue.
	 *
	 * @param other board we are comparing this one to
	 * @return your documentation here
	 */
	@Override
	public int compareTo(Board other)
	{
		int thisCurrentValue = this.manhattan() + this.g;
		int otherCurrentValue = other.manhattan() + other.g;
		if(thisCurrentValue > otherCurrentValue)
			return 1;
		if(thisCurrentValue < otherCurrentValue)
			return -1;
		return 0;
	}


	/**
	 * Calculate classic manhattan distance for a board.
	 *
	 * @return manhattan distance
	 *
	 * <p>We calculate distance for all tiles and then subtract back out the
	 * distance added by the blank tile.</p>
	 */
	private int manhattan()
	{
		int i,n,h= 0;
		n= rows*cols;
		for (i=0; i<n; i++)
				h+= Math.abs(i/cols-tiles[i]/cols) + Math.abs(i%cols-tiles[i]%cols);
		h-= (n-1)/cols-bpos/cols + (n-1)%cols-bpos%cols;

		return h;
	}


	/**
	 * Determine whether a move in the specified direction is possible.
	 *
	 * @param trydir the direction we may try to move: U, D, R, or L
	 * @return true if the move is possible, false otherwise
	 *
	 * <p>Moves are from the perspective of the blank tile.</p>
	 * <p>A move is considered not possible if it "undoes" the move
	 * the got us in this state. Also, if it moves the blank tile
	 * off the board it is not possible.</p>
	 */
	public boolean canMove(char trydir)
	{
		switch (trydir) {
			case 'U': return dir!='D' && bpos-cols >= 0;
			case 'D': return dir!='U' && bpos+cols < rows*cols;
			case 'R': return dir!='L' && (bpos+1)%cols!=0; //(bpos+1)/rows==(bpos/rows);
			case 'L': return dir!='R' && bpos%cols!=0; //bpos>0 && (bpos-1)/rows==(bpos/rows);
			default: return false;
		}
	}


	/**
	 * Getter for reference to previous board.
	 */
	public Board getPrev()
	{
		return prev;
	}
	
	/**
	 * Getter for bpos
	 */
	public int getBpos()
	{
		return bpos;
	}

	/**
	 * Getter for steps traveled so far.
	 */
	public int getSteps()
	{
		return g;
	}
	/**
	 * Getter for column number
	 * @return
	 */
	public int getCols(){
		return cols;
	}
	/**
	 * Getter for row number
	 * @return
	 */
	public int getRows(){
		return rows;
	}


	/**
	 * Determine whether we are at goal state or not.
	 *
	 * @return true if this board is at goal state; false otherwise
	 */
	public boolean isGoal()
	{
		return h==0;
	}
	/**
	 * Updates a new boards values of prev, dir, and g before adding to Priority Queue
	 */
	public void updateNewBoardValues(char directionMoved, Board prev, int prevG){
		this.dir = directionMoved;
		this.prev = prev;
		this.g = prevG + 1;
	}

	/**
	 * Alters the current boards char array to match the the blank piece being moved up
	 * @return: the new char array for the move that is being made
	 */
	public char[] moveUp(){
		changeThis = tiles.clone();
		char temp = tiles[bpos - 4];
		changeThis[bpos-4] = (char) ((rows*cols) - 1);
		changeThis[bpos] = temp;
		return changeThis;
	}
	/**
	 * Alters the current boards char array to match the blank piece being moved down
	 * @return: the new char array for the move being made
	 */
	public char[] moveDown(){
		changeThis = tiles.clone();
		char temp = tiles[bpos+4];
		changeThis[bpos+4] = (char) ((rows*cols)-1);
		changeThis[bpos] = temp;
		return changeThis;
	}
	/**
	 * Alters the current boards char array to match the blank piece being moved left
	 * @return: the new char array for the move being made
	 */
	public char[] moveLeft(){
		changeThis = tiles.clone();
		char temp = tiles[bpos-1];
		changeThis[bpos-1] = (char) ((rows*cols)-1);
		changeThis[bpos] = temp;
		return changeThis;
	}
	/**
	 * Alters the current boards char array to match the blank piece being moved right
	 * @return: the new char array for the move being made
	 */
	public char[] moveRight(){
		changeThis = tiles.clone();
		char temp = tiles[bpos+1];
		changeThis[bpos+1] = (char) ((rows*cols)-1);
		changeThis[bpos] = temp;
		return changeThis;
	}
	
	/**
	 * Compares two board states to see if they are the same
	 * @return true or false depending on if they are the same.
	 */
	public boolean compareBoards(Board otherBoard){
		boolean stillEqual = true;
		int counter = 0;
		while(stillEqual && counter < this.tiles.length){
			if(this.tiles[counter] == otherBoard.tiles[counter])
				counter++;
			else
				stillEqual = false;
		}
		return stillEqual;
	}
	
	/**
	 * Concise representation of the board.
	 *
	 * @return list of tiles enclosed in ()'s
	 */
	@Override
	public String toString()
	{
		String str="("+(int)tiles[0];
		for (int i=1; i<tiles.length; i++)
			str+= " "+(int)tiles[i];
		return String.format(str+" | f:%2d, g:%2d, h:%2d)",g+h,g,h);
	}


	/**
	 * Displays ASCII board along with f(), g(), and h() values.
	 */
	public void display()
	{
		int i,j;
		System.out.println("+----+----+----+----+");
		System.out.printf("| f=%3d g=%3d h=%3d |\n",h+g,g,h);
		for (i=0; i<rows; i++) {
			System.out.println("+----+----+----+----+");
			System.out.printf("| ");
			for (j=0; j<cols; j++)
				if (tiles[i*cols+j]==rows*cols-1)  // if blank
					System.out.printf("%2s | ",' ');
				else
					System.out.printf("%2d | ",(int)tiles[i*cols+j]);
			System.out.println();
		}
		System.out.println("+----+----+----+----+\n");
	}
}
