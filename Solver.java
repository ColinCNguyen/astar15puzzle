/**
 * Solves of the sliding tile puzzle with A* search.
 *
 * @author	Terry Sergeant
 * @version Spring 2016
 *
*/
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.HashSet;
import java.io.InputStream;

public class Solver
{
	private int rows;        // number of rows for boards we are solving
	private int cols;        // number of cols for boards we are solving
	private Board board;     // current starting board
	private Board solved;    // solved board
	private Scanner source;  // source from which we read next board
	private Timer timer;     // track wall-clock time of solution
	private int count;       // number of states we enqueued during solution


	/**
	 * Initialize the solver by specifying puzzle sizes and input source.
	 *
	 * @param rows number of rows for each puzzle
	 * @param cols columns for each puzzle
	 * @param dataFile input source for board
	 */
	public Solver(int rows, int cols, InputStream dataFile)
	{
		try {
			this.rows= rows;
			this.cols= cols;
			timer= new Timer();
			source= new Scanner(dataFile);
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}


	/**
	 * Creates a new board from the input source.
	 *
	 * @return true if the nextBoard was generated successfully; false otherwise
	 */
	public boolean nextBoard()
	{
		int n= rows*cols;
		char [] tiles= new char[n];

		if (source.hasNextInt()) {
			for (int i= 0; i<n; i++)
				tiles[i]= (char)source.nextInt();
			board= new Board(tiles,rows,cols);
			return true;
		}

		return false;
	}


	/**
	 * Public-facing method to solve current board.
	 */
	public void solve()
	{
		if (board==null)
			System.out.println("No board loaded ... try calling nextBoard() ...");
		else {
			timer.start();
			solved= astar(board);
			timer.stop();
		}
	}


	/**
	 * Gives concise representation if solving multiple boards.
	 * @return board with stats along with solution (if available)
	 */
	@Override
	public String toString()
	{
		if (board==null) return "No current board";
		if (solved==null) return board.toString();
		return board.toString()+" -->\n"+solved.toString()+" ("+timer+"; states: "+count+")";
	}


	/**
	 * Display summary information about solution.
	 */
	public void display()
	{
	}


	/**
	 * Display solution by showing moves from start to finish.
	 */
	public void showSolution()
	{
	}


	/**
	 * Performs A* search.
	 *
	 * @param start Starting board position
	 */
	private Board astar(Board start)
	{
		return null;
	}

}
