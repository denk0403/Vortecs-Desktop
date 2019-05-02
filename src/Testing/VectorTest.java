package Testing;

import Matrices.Matrix2D;
import Vectors.CartesianVector;
import Vectors.PolarVector;
import Vectors.Vector;

/**
 * Represents a Collection of tests on Vectors
 * 
 * @author Dennis Kats
 *
 */
public class VectorTest extends Tester {

	private static final Vector cv0 = new CartesianVector(0, 0);
	private static final Vector pv0 = new PolarVector(0, 0);
	private static final Vector cv1 = new CartesianVector(1, 1);
	private static final Vector pv1 = new PolarVector(Math.sqrt(2), Math.PI / 4);

	/**
	 * Creates Vector Tests
	 */
	public VectorTest() {
		testAll();
	}

	/**
	 * Tests all methods on vectors
	 */
	public static void testAll() {
		testNegate();
		testOrthogonalize();
		testScale();
		testDotProduct();
		testAdd();
		try {
			testGetUnitVector();
			testProjection();
			testReflection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests the negate method of vectors
	 */
	public static void testNegate() {
		test(cv0.negate(), cv0);
		test(cv0.negate(), pv0);
		test(cv0.negate(), pv0.negate());
		test(cv1.negate(), new CartesianVector(-1, -1));
		test(cv1.negate(), new PolarVector(Math.sqrt(2), -3 * Math.PI / 4));
		test(cv1.negate(), pv1.negate());
	}

	/**
	 * Tests the orthogonalize method of vectors
	 */
	public static void testOrthogonalize() {
		test(cv0.orthogonalize(), cv0);
		test(cv0.orthogonalize(), pv0);
		test(cv0.orthogonalize(), pv0.orthogonalize());
		test(cv1.orthogonalize(), new CartesianVector(-1, 1));
		test(cv1.orthogonalize(), new PolarVector(Math.sqrt(2), 3 * Math.PI / 4));
		test(cv1.orthogonalize(), pv1.orthogonalize());
	}

	/**
	 * Tests the getUnitVector method of vectors
	 * 
	 * @throws Exception If one of the tests fails
	 */
	public static void testGetUnitVector() throws Exception {
		testException(cv0, new Exception("The Zero Vector has no unit vector"), "getUnitVector");
		testException(pv0, new Exception("The Zero Vector has no unit vector"), "getUnitVector");
		test(cv1.getUnitVector(), new CartesianVector(1 / Math.sqrt(2), 1 / Math.sqrt(2)));
		test(cv1.getUnitVector(), new PolarVector(1, Math.PI / 4));
		test(cv1.getUnitVector(), pv1.getUnitVector());
		test(pv1.getUnitVector(), new PolarVector(1, Math.PI / 4));
		test(pv1.getUnitVector(), new CartesianVector(1 / Math.sqrt(2), 1 / Math.sqrt(2)));
	}

	/**
	 * Tests the scale method vectors
	 */
	public static void testScale() {
		test(cv0.scale(0), Vector.ZERO_VECTOR);
		test(cv0.scale(1), Vector.ZERO_VECTOR);
		test(cv0.scale(100), Vector.ZERO_VECTOR);
		test(cv0.scale(Math.random() * 100), cv0);
		test(cv0.scale(Math.random() * 100), pv0);
		test(pv0.scale(0), Vector.ZERO_VECTOR);
		test(pv0.scale(1), Vector.ZERO_VECTOR);
		test(pv0.scale(100), Vector.ZERO_VECTOR);
		test(pv0.scale(Math.random() * 100), cv0);
		test(pv0.scale(Math.random() * 100), pv0);
		test(cv1.scale(0), Vector.ZERO_VECTOR);
		test(pv1.scale(0), Vector.ZERO_VECTOR);
		test(cv1.scale(1), cv1);
		test(cv1.scale(1), pv1);
		test(cv1.scale(10), pv1.scale(10));
		test(cv1.scale(1000), pv1.scale(1000));
		test(cv1.scale(-1), new CartesianVector(-1, -1));
		test(cv1.scale(-1), new PolarVector(Math.sqrt(2), -3 * Math.PI / 4));
		test(cv1.scale(-10), pv1.scale(-10));
		test(cv1.scale(-1000), new PolarVector(1000*Math.sqrt(2), -3 * Math.PI / 4));
		test(pv1.scale(1), cv1);
		test(pv1.scale(1), pv1);
		test(pv1.scale(10), pv1.scale(10));
		test(pv1.scale(1000), pv1.scale(1000));
		test(pv1.scale(-1), new CartesianVector(-1, -1));
		test(pv1.scale(-1), new PolarVector(Math.sqrt(2), -3 * Math.PI / 4));
		test(pv1.scale(-1000), new PolarVector(1000*Math.sqrt(2), -3 * Math.PI / 4));
	}

	/**
	 * Tests the dot product method of vectors
	 */
	public static void testDotProduct() {
		test(cv0.dotProduct(cv0), 0.0);
		test(cv0.dotProduct(pv0), 0.0);
		test(cv0.dotProduct(cv1), 0.0);
		test(cv0.dotProduct(pv1), 0.0);
		test(pv0.dotProduct(cv0), 0.0);
		test(pv0.dotProduct(pv0), 0.0);
		test(pv0.dotProduct(cv1), 0.0);
		test(pv0.dotProduct(pv1), 0.0);
		test(cv1.dotProduct(cv0), 0.0);
		test(cv1.dotProduct(pv0), 0.0);
		testInexact(cv1.dotProduct(cv1), 2.0, 0.00000001);
		testInexact(cv1.dotProduct(pv1), 2.0, 0.00000001);
		test(pv1.dotProduct(cv0), 0.0);
		test(pv1.dotProduct(pv0), 0.0);
		testInexact(pv1.dotProduct(cv1), 2.0, 0.00000001);
		testInexact(pv1.dotProduct(pv1), 2.0, 0.00000001);
		testInexact(new CartesianVector(5, 8).dotProduct(new PolarVector(10, Math.PI / 3)),
				25 + 40 * Math.sqrt(3), 0.00000001);
	}

	/**
	 * Tests the add method of vectors
	 */
	public static void testAdd() {
		test(Vector.ZERO_VECTOR.add(cv0), Vector.ZERO_VECTOR);
		test(Vector.ZERO_VECTOR.add(cv0), cv0);
		test(Vector.ZERO_VECTOR.add(cv0), pv0);
		test(Vector.ZERO_VECTOR.add(pv0), Vector.ZERO_VECTOR);
		test(Vector.ZERO_VECTOR.add(pv0), pv0);
		test(Vector.ZERO_VECTOR.add(pv0), cv0);
		test(cv0.add(cv0), Vector.ZERO_VECTOR);
		test(cv0.add(cv0), cv0);
		test(cv0.add(pv0), Vector.ZERO_VECTOR);
		test(cv0.add(pv0), pv0);
		test(pv0.add(cv0), Vector.ZERO_VECTOR);
		test(pv0.add(cv0), cv0);
		test(pv0.add(pv0), Vector.ZERO_VECTOR);
		test(pv0.add(pv0), pv0);
		test(cv1.add(cv0), cv1);
		test(cv1.add(pv0), cv1);
		test(cv1.add(cv0), pv1);
		test(cv1.add(pv0), pv1);
		test(pv1.add(cv0), cv1);
		test(pv1.add(pv0), cv1);
		test(pv1.add(cv0), pv1);
		test(pv1.add(pv0), pv1);
		test(cv1.add(Vector.ZERO_VECTOR), cv1);
		test(cv1.add(Vector.ZERO_VECTOR), pv1);
		test(pv1.add(Vector.ZERO_VECTOR), cv1);
		test(pv1.add(Vector.ZERO_VECTOR), pv1);
		test(cv1.add(cv1), cv1.scale(2));
		test(cv1.add(pv1), cv1.scale(2));
		test(pv1.add(cv1), cv1.scale(2));
		test(pv1.add(pv1), cv1.scale(2));
		test(cv1.add(cv1), pv1.scale(2));
		test(cv1.add(pv1), pv1.scale(2));
		test(pv1.add(cv1), pv1.scale(2));
		test(pv1.add(pv1), pv1.scale(2));
		test(cv1.add(new CartesianVector(5, 23)), new CartesianVector(6, 24));
		test(pv1.add(new CartesianVector(5, 23)), new CartesianVector(6, 24));
		test(new CartesianVector(3, 4).add(new CartesianVector(5, 9)), new CartesianVector(8, 13));
		test(new PolarVector(3, Math.PI / 2).add(new PolarVector(5, Math.PI / 2)),
				new PolarVector(8, Math.PI / 2));
		test(new PolarVector(3, Math.PI / 2).add(new PolarVector(5, Math.PI / 2)),
				new CartesianVector(0, 8));
		test(new PolarVector(3, Math.PI / 2).add(new PolarVector(5, Math.PI / 2)),
				new CartesianVector(0, 1).scale(8));
		test(new PolarVector(3, Math.PI / 2).add(new PolarVector(5, Math.PI / 2)),
				new CartesianVector(1, 0).scale(8).orthogonalize());
	}

	public static void testProjection() throws Exception {
		testException(Vector.ZERO_VECTOR, new Exception("Can't project onto a zero vector"),
				"getProjectionMatrix");
		testException(cv0, new Exception("Can't project onto a zero vector"),
				"getProjectionMatrix");
		testException(pv0, new Exception("Can't project onto a zero vector"),
				"getProjectionMatrix");
		test(cv1.getProjectionMatrix(), new Matrix2D(1, 1, 1, 1).scale(1 / 2.0));
		test(pv1.getProjectionMatrix(), new Matrix2D(1, 1, 1, 1).scale(1 / 2.0));
		test(new PolarVector(8, Math.PI / 2).getProjectionMatrix(), new Matrix2D(0, 0, 0, 1));
		test(new CartesianVector(-1, -2).getProjectionMatrix(),
				new Matrix2D(1, 2, 2, 4).scale(1 / 5.0));
	}

	public static void testReflection() throws Exception {
		testException(Vector.ZERO_VECTOR, new Exception("Can't reflect over a zero vector"),
				"getReflectionMatrix");
		testException(cv0, new Exception("Can't reflect over a zero vector"),
				"getReflectionMatrix");
		testException(pv0, new Exception("Can't reflect over a zero vector"),
				"getReflectionMatrix");
		test(cv1.getReflectionMatrix(), new Matrix2D(0, 1, 1, 0));
		test(pv1.getReflectionMatrix(), new Matrix2D(0, 1, 1, 0));
		test(new PolarVector(8, Math.PI / 2).getReflectionMatrix(), new Matrix2D(-1, 0, 0, 1));
		test(new CartesianVector(-1, -2).getReflectionMatrix(),
				new Matrix2D(-3, 4, 4, 3).scale(1 / 5.0));
	}
}
