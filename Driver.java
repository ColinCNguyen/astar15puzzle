
/**
 * Driver for 15-puzzle solver.
 *
 * @author	Colin Nguyen
 * @version Spring 2016
 *
 * To test this, compile and then: java Driver < quickcases.in
*/

public class Driver
{
	public static void main(String [] args)
	{
		//
		if (args.length==0) {
			System.err.println("Must provide file name");
			System.exit(-1);
		}
		Solver solver= new Solver(4,4,args[0]);
		
		//Multiple boards
		while (solver.nextBoard()) {
			solver.solve();
			System.out.println(solver);
			//solver.showSolution();
			solver.display();
			System.out.println("------------------------------------------------");
		}
	}
}
