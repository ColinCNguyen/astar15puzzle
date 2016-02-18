/**
 * Solves of the sliding tile puzzle with A* search.
 *
 * @author	Terry Sergeant & Colin Nguyen
 * @version Spring 2016
 *
*/
import java.util.HashMap;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Stack;
import java.io.File;

public class Solver
{
	private int rows;        // number of rows for boards we are solving
	private int cols;        // number of cols for boards we are solving
	private Board board;     // current starting board
	private Board solved;    // solved board
	private Scanner source;  // source from which we read next board
	private Timer timer;     // track wall-clock time of solution
	private int count;       // number of states we enqueued during solution
	private PriorityQueue <Board> theMoves = new PriorityQueue <Board>(); 	//Prioty Queue of the moves we can make
	private Stack <Board> theSolutionPath = new Stack <Board>();			//A stack of boards representing the shortest path to solving		
	private int numberOfSteps;		//Number of steps it took to solve.
	private HashMap<Integer, Board> myMap = new HashMap<Integer, Board>(500000);

	/**
	 * Initialize the solver by specifying puzzle sizes and input source.
	 *
	 * @param rows number of rows for each puzzle
	 * @param cols columns for each puzzle
	 * @param dataFile input source for board
	 */
	public Solver(int rows, int cols, String dataFile)
	{
		try {
			this.rows= rows;
			this.cols= cols;
			timer= new Timer();
			try{source= new Scanner(new File(dataFile));}
			catch(Exception e){
				System.out.println("Invalid file name.");
				System.exit(-1);
			}
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
		numberOfSteps = 0;
		count = 0;
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
			solved= astarImproved(board);
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
		System.out.println("Time to solve: "+timer+" | States enqueued: "+count+" | "+"Number of steps it took: "+numberOfSteps);
	}


	/**
	 * Display solution by showing moves from start to finish.
	 */
	public void showSolution()
	{
		
		while(!theSolutionPath.empty()){
			theSolutionPath.pop().display();
			numberOfSteps++;
		}
		solved.display();
	}

	/**
	 * Goes through the array list of already seen states to determine if its a new state or not
	 * @return true if we have, false if we havent
	 */
	public boolean newState(Board a){
		boolean newOrNot = true;
		if(myMap.containsValue(a)){
			newOrNot = false;
		}
		return newOrNot;
	}
	
	/**
	 * Performs A* search.
	 *
	 * @param start Starting board position
	 */
	private Board astar(Board start)
	{
		Board currentBoard;
		Board moveMade = start;
		theMoves.add(start);
		while(theMoves.peek() != null){
			currentBoard = theMoves.poll();
			if(currentBoard.isGoal()){					//Win condition
				Board prevTracker = currentBoard;
				while(prevTracker.getPrev() != null){	//Builds stack to show the path used to solve.
					theSolutionPath.push(prevTracker.getPrev());
					prevTracker = prevTracker.getPrev();
				}
				return currentBoard;
			}
			//Tries all four different moves and if they can be made creates a new board, updates their values, and then adds them to the Priority Queue.
			if(currentBoard.canMove('U')){
				moveMade = new Board(currentBoard.moveUp(), currentBoard.getRows(), currentBoard.getCols());
				moveMade.updateNewBoardValues('U', currentBoard, currentBoard.getSteps());
				theMoves.add(moveMade);
				count++;
			}
			if(currentBoard.canMove('D')){
				moveMade = new Board(currentBoard.moveDown(), currentBoard.getRows(), currentBoard.getCols());
				moveMade.updateNewBoardValues('D', currentBoard, currentBoard.getSteps());
				theMoves.add(moveMade);
				count++;
			}
			if(currentBoard.canMove('L')){
				moveMade = new Board(currentBoard.moveLeft(), currentBoard.getRows(), currentBoard.getCols());
				moveMade.updateNewBoardValues('L', currentBoard, currentBoard.getSteps());
				theMoves.add(moveMade);
				count++;
			}
			if(currentBoard.canMove('R')){
				moveMade = new Board(currentBoard.moveRight(), currentBoard.getRows(), currentBoard.getCols());
				moveMade.updateNewBoardValues('R', currentBoard, currentBoard.getSteps());
				theMoves.add(moveMade);
				count++;
			}
		}
		return moveMade;
	}
	
	/**
	 * Performs A* search with some improvements.
	 *
	 * @param start Starting board position
	 */
	private Board astarImproved(Board start)
	{
		int spotInMap;
		Board currentBoard;
		Board moveMade = start;
		theMoves.add(start);
		spotInMap = start.hashCode();
		myMap.put(spotInMap, start);
		
		while(theMoves.peek() != null){
			currentBoard = theMoves.poll();
			if(currentBoard.isGoal()){					//Win condition
				Board prevTracker = currentBoard;
				System.out.println("Found it");
				while(prevTracker.getPrev() != null){	//Builds stack to show the path used to solve.
					theSolutionPath.push(prevTracker.getPrev());
					prevTracker = prevTracker.getPrev();
				}
				return currentBoard;
			}
			//Tries all four different moves and if they can be made creates a new board, updates their values, and then adds them to the Priority Queue.
			if(currentBoard.canMove('U')){
				moveMade = new Board(currentBoard.moveUp(), currentBoard.getRows(), currentBoard.getCols());
				moveMade.updateNewBoardValues('U', currentBoard, currentBoard.getSteps());
				spotInMap = moveMade.hashCode();
				if(newState(moveMade)){
					theMoves.add(moveMade);
					myMap.put(spotInMap, moveMade);
					count++;
				}
				/*else{
					if(moveMade.getSteps() < myMap.get(moveMade).getSteps()){
						myMap.put(spotInMap, moveMade);
					
					}
				}*/
			}
			if(currentBoard.canMove('D')){
				moveMade = new Board(currentBoard.moveDown(), currentBoard.getRows(), currentBoard.getCols());
				moveMade.updateNewBoardValues('D', currentBoard, currentBoard.getSteps());
				spotInMap = moveMade.hashCode();
				
				if(newState(moveMade)){
					theMoves.add(moveMade);
					myMap.put(spotInMap, moveMade);
					count++;
				}
	
			}
			if(currentBoard.canMove('L')){
				moveMade = new Board(currentBoard.moveLeft(), currentBoard.getRows(), currentBoard.getCols());
				moveMade.updateNewBoardValues('L', currentBoard, currentBoard.getSteps());
				spotInMap = moveMade.hashCode();
				if(newState(moveMade)){
					theMoves.add(moveMade);
					myMap.put(spotInMap, moveMade);
					count++;
				}
				
			}
			if(currentBoard.canMove('R')){
				moveMade = new Board(currentBoard.moveRight(), currentBoard.getRows(), currentBoard.getCols());
				moveMade.updateNewBoardValues('R', currentBoard, currentBoard.getSteps());
				spotInMap = moveMade.hashCode();
				
				if(newState(moveMade)){
					theMoves.add(moveMade);
					myMap.put(spotInMap, moveMade);
					count++;
				}
				
			}
		}
		return moveMade;
	}

}
