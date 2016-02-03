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
	public static void main(String [] args)
	{
		Solver solver= new Solver(4,4,System.in);
		while (solver.nextBoard()) {
			solver.solve();
			System.out.println(solver);
			System.out.println("------------------------------------------------");
		}
	}
}
