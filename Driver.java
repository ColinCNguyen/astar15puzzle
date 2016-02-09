import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.PriorityQueue;

/**
 * Driver for 15-puzzle solver.
 *
 * @author	Terry Sergeant
 * @version Spring 2016
 *
 * To test this, compile and then: java Driver < quickcases.in
*/

public class Driver
{
	//Test comment.
	public static void main(String [] args)
	{
		/*PriorityQueue<Integer> temp = new PriorityQueue<Integer>(12);
		temp.add(10);
		temp.add(7);
		temp.add(1);
		temp.add(3);
		temp.add(5);
		while(temp.peek() != null)
			System.out.println(temp.poll());*/
		if (args.length==0) {
			System.err.println("Must provide file name");
			System.exit(-1);
		}
		Solver solver= new Solver(4,4,args[0]);
		
		//One Board
		solver.nextBoard();
		solver.nextBoard();
		solver.solve();
		solver.display();
		System.out.println(solver);
		
		//Multiple boards
		/*while (solver.nextBoard()) {
			solver.solve();
			solver.display();
			System.out.println(solver);
			System.out.println("------------------------------------------------");
		}*/
	}
}
