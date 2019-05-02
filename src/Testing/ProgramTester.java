package Testing;

/**
 * Represents a collection of tests for this program
 * 
 * @author Dennis Kats
 *
 */
public class ProgramTester extends Tester {

	/**
	 * Creates a collection of tests for this program
	 */
	public ProgramTester() {
		new VectorTest();
		new MatrixTest();
		System.out.println("\033[1;32m" + "All " + getTestCounter() + " Tests Passed!" + "\033[0m");
	}

}
