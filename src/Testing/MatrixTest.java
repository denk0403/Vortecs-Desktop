package Testing;

import Matrices.Matrix2D;
import Vectors.CartesianVector;
import Vectors.PolarVector;
import Vectors.Vector;

/**
 * Represents a Collection of tests on Matrices
 * 
 * @author Dennis Kats
 *
 */
public class MatrixTest extends Tester {

	/**
	 * Creates Matrix Tests
	 */
	public MatrixTest() {
		this.testAll();
	}

	/**
	 * Tests all methods of Matrices
	 */
	public void testAll() {
		testTransformVector();
		testTransformMatrix();
		testGetDeterminant();
		testRotation();
		testRREF();
		// testLinearlyIndependent();
		try {
			testGetInverse();
			testProjection();
			testReflection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testTransformVector() {
		Vector cv0 = new CartesianVector(0, 0);
		Vector pv0 = new PolarVector(0, 0);
		Vector cv1 = new CartesianVector(1, 1);
		Vector pv1 = new PolarVector(Math.sqrt(2), Math.PI / 4);

		test(Matrix2D.IDENTITY_MATRIX.transform(Vector.ZERO_VECTOR), Vector.ZERO_VECTOR);
		test(Matrix2D.IDENTITY_MATRIX.transform(Vector.ZERO_VECTOR), cv0);
		test(Matrix2D.IDENTITY_MATRIX.transform(Vector.ZERO_VECTOR), pv0);
		test(Matrix2D.IDENTITY_MATRIX.transform(cv0), Vector.ZERO_VECTOR);
		test(Matrix2D.IDENTITY_MATRIX.transform(cv0), cv0);
		test(Matrix2D.IDENTITY_MATRIX.transform(cv0), pv0);
		test(Matrix2D.IDENTITY_MATRIX.transform(pv0), Vector.ZERO_VECTOR);
		test(Matrix2D.IDENTITY_MATRIX.transform(pv0), cv0);
		test(Matrix2D.IDENTITY_MATRIX.transform(pv0), pv0);
		test(Matrix2D.IDENTITY_MATRIX.transform(cv1), cv1);
		test(Matrix2D.IDENTITY_MATRIX.transform(cv1), pv1);
		test(Matrix2D.IDENTITY_MATRIX.transform(pv1), cv1);
		test(Matrix2D.IDENTITY_MATRIX.transform(pv1), pv1);

		test(Matrix2D.FLIP_XY_MATRIX.transform(Vector.ZERO_VECTOR), Vector.ZERO_VECTOR);
		test(Matrix2D.FLIP_XY_MATRIX.transform(Vector.ZERO_VECTOR), cv0);
		test(Matrix2D.FLIP_XY_MATRIX.transform(Vector.ZERO_VECTOR), pv0);
		test(Matrix2D.FLIP_XY_MATRIX.transform(cv0), Vector.ZERO_VECTOR);
		test(Matrix2D.FLIP_XY_MATRIX.transform(cv0), cv0);
		test(Matrix2D.FLIP_XY_MATRIX.transform(cv0), pv0);
		test(Matrix2D.FLIP_XY_MATRIX.transform(pv0), Vector.ZERO_VECTOR);
		test(Matrix2D.FLIP_XY_MATRIX.transform(pv0), cv0);
		test(Matrix2D.FLIP_XY_MATRIX.transform(pv0), pv0);
		test(Matrix2D.FLIP_XY_MATRIX.transform(cv1), cv1);
		test(Matrix2D.FLIP_XY_MATRIX.transform(cv1), pv1);
		test(Matrix2D.FLIP_XY_MATRIX.transform(pv1), cv1);
		test(Matrix2D.FLIP_XY_MATRIX.transform(pv1), pv1);
		test(Matrix2D.FLIP_XY_MATRIX.transform(cv1), cv1.flipXY());
		test(Matrix2D.FLIP_XY_MATRIX.transform(cv1), pv1.flipXY());
		test(Matrix2D.FLIP_XY_MATRIX.transform(pv1), cv1.flipXY());
		test(Matrix2D.FLIP_XY_MATRIX.transform(pv1), pv1.flipXY());
		test(Matrix2D.FLIP_XY_MATRIX.transform(new CartesianVector(5, 23)),
				new CartesianVector(23, 5));
		test(Matrix2D.FLIP_XY_MATRIX.transform(new PolarVector(5, Math.PI / 6)),
				new PolarVector(5, Math.PI / 3));

		test(Matrix2D.NEGATION_MATRIX.transform(Vector.ZERO_VECTOR), Vector.ZERO_VECTOR);
		test(Matrix2D.NEGATION_MATRIX.transform(Vector.ZERO_VECTOR), cv0);
		test(Matrix2D.NEGATION_MATRIX.transform(Vector.ZERO_VECTOR), pv0);
		test(Matrix2D.NEGATION_MATRIX.transform(cv0), Vector.ZERO_VECTOR);
		test(Matrix2D.NEGATION_MATRIX.transform(cv0), cv0);
		test(Matrix2D.NEGATION_MATRIX.transform(cv0), pv0);
		test(Matrix2D.NEGATION_MATRIX.transform(pv0), Vector.ZERO_VECTOR);
		test(Matrix2D.NEGATION_MATRIX.transform(pv0), cv0);
		test(Matrix2D.NEGATION_MATRIX.transform(pv0), pv0);
		test(Matrix2D.NEGATION_MATRIX.transform(cv1), cv1.negate());
		test(Matrix2D.NEGATION_MATRIX.transform(cv1), pv1.negate());
		test(Matrix2D.NEGATION_MATRIX.transform(pv1), cv1.negate());
		test(Matrix2D.NEGATION_MATRIX.transform(pv1), pv1.negate());
		test(Matrix2D.NEGATION_MATRIX.transform(new CartesianVector(5, 23)),
				new CartesianVector(-5, -23));
		test(Matrix2D.NEGATION_MATRIX.transform(new PolarVector(5, Math.PI / 6)),
				new PolarVector(5, -Math.PI + Math.PI / 6));

		test(Matrix2D.PROJECT_X_MATRIX.transform(Vector.ZERO_VECTOR), Vector.ZERO_VECTOR);
		test(Matrix2D.PROJECT_X_MATRIX.transform(Vector.ZERO_VECTOR), cv0);
		test(Matrix2D.PROJECT_X_MATRIX.transform(Vector.ZERO_VECTOR), pv0);
		test(Matrix2D.PROJECT_X_MATRIX.transform(cv0), Vector.ZERO_VECTOR);
		test(Matrix2D.PROJECT_X_MATRIX.transform(cv0), cv0);
		test(Matrix2D.PROJECT_X_MATRIX.transform(cv0), pv0);
		test(Matrix2D.PROJECT_X_MATRIX.transform(pv0), Vector.ZERO_VECTOR);
		test(Matrix2D.PROJECT_X_MATRIX.transform(pv0), cv0);
		test(Matrix2D.PROJECT_X_MATRIX.transform(pv0), pv0);
		test(Matrix2D.PROJECT_X_MATRIX.transform(cv1), new CartesianVector(1, 0));
		test(Matrix2D.PROJECT_X_MATRIX.transform(cv1), new PolarVector(1, 0));
		test(Matrix2D.PROJECT_X_MATRIX.transform(pv1), new CartesianVector(1, 0));
		test(Matrix2D.PROJECT_X_MATRIX.transform(pv1), new PolarVector(1, 0));
		test(Matrix2D.PROJECT_X_MATRIX.transform(new CartesianVector(5, 23)),
				new CartesianVector(5, 0));
		test(Matrix2D.PROJECT_X_MATRIX.transform(new PolarVector(5, Math.PI / 6)),
				new PolarVector(5 * Math.cos(Math.PI / 6), 0));

		test(Matrix2D.PROJECT_Y_MATRIX.transform(Vector.ZERO_VECTOR), Vector.ZERO_VECTOR);
		test(Matrix2D.PROJECT_Y_MATRIX.transform(Vector.ZERO_VECTOR), cv0);
		test(Matrix2D.PROJECT_Y_MATRIX.transform(Vector.ZERO_VECTOR), pv0);
		test(Matrix2D.PROJECT_Y_MATRIX.transform(cv0), Vector.ZERO_VECTOR);
		test(Matrix2D.PROJECT_Y_MATRIX.transform(cv0), cv0);
		test(Matrix2D.PROJECT_Y_MATRIX.transform(cv0), pv0);
		test(Matrix2D.PROJECT_Y_MATRIX.transform(pv0), Vector.ZERO_VECTOR);
		test(Matrix2D.PROJECT_Y_MATRIX.transform(pv0), cv0);
		test(Matrix2D.PROJECT_Y_MATRIX.transform(pv0), pv0);
		test(Matrix2D.PROJECT_Y_MATRIX.transform(cv1), new CartesianVector(0, 1));
		test(Matrix2D.PROJECT_Y_MATRIX.transform(cv1), new PolarVector(1, Math.PI / 2));
		test(Matrix2D.PROJECT_Y_MATRIX.transform(pv1), new CartesianVector(0, 1));
		test(Matrix2D.PROJECT_Y_MATRIX.transform(pv1), new PolarVector(1, Math.PI / 2));
		test(Matrix2D.PROJECT_Y_MATRIX.transform(new CartesianVector(5, 23)),
				new CartesianVector(0, 23));
		test(Matrix2D.PROJECT_Y_MATRIX.transform(new PolarVector(5, Math.PI / 6)),
				new PolarVector(5 * Math.sin(Math.PI / 6), Math.PI / 2));

		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(Vector.ZERO_VECTOR), Vector.ZERO_VECTOR);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(Vector.ZERO_VECTOR), cv0);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(Vector.ZERO_VECTOR), pv0);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(cv0), Vector.ZERO_VECTOR);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(cv0), cv0);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(cv0), pv0);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(pv0), Vector.ZERO_VECTOR);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(pv0), cv0);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(pv0), pv0);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(cv1), new CartesianVector(1, -1));
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(cv1),
				new PolarVector(Math.sqrt(2), -Math.PI / 4));
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(pv1), new CartesianVector(1, -1));
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(pv1),
				new PolarVector(Math.sqrt(2), -Math.PI / 4));
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(new CartesianVector(5, 23)),
				new CartesianVector(5, -23));
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(new PolarVector(5, Math.PI / 6)),
				new PolarVector(5, -Math.PI / 6));

		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(Vector.ZERO_VECTOR), Vector.ZERO_VECTOR);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(Vector.ZERO_VECTOR), cv0);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(Vector.ZERO_VECTOR), pv0);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(cv0), Vector.ZERO_VECTOR);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(cv0), cv0);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(cv0), pv0);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(pv0), Vector.ZERO_VECTOR);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(pv0), cv0);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(pv0), pv0);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(cv1), new CartesianVector(-1, 1));
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(cv1),
				new PolarVector(Math.sqrt(2), 3 * Math.PI / 4));
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(pv1), new CartesianVector(-1, 1));
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(pv1),
				new PolarVector(Math.sqrt(2), 3 * Math.PI / 4));
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(new CartesianVector(5, 23)),
				new CartesianVector(-5, 23));
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(new PolarVector(5, Math.PI / 6)),
				new PolarVector(5, 5 * Math.PI / 6));

		test(Matrix2D.ZERO_MATRIX.transform(Vector.ZERO_VECTOR), Vector.ZERO_VECTOR);
		test(Matrix2D.ZERO_MATRIX.transform(Vector.ZERO_VECTOR), cv0);
		test(Matrix2D.ZERO_MATRIX.transform(Vector.ZERO_VECTOR), pv0);
		test(Matrix2D.ZERO_MATRIX.transform(cv0), Vector.ZERO_VECTOR);
		test(Matrix2D.ZERO_MATRIX.transform(cv0), cv0);
		test(Matrix2D.ZERO_MATRIX.transform(cv0), pv0);
		test(Matrix2D.ZERO_MATRIX.transform(pv0), Vector.ZERO_VECTOR);
		test(Matrix2D.ZERO_MATRIX.transform(pv0), cv0);
		test(Matrix2D.ZERO_MATRIX.transform(pv0), pv0);
		test(Matrix2D.ZERO_MATRIX.transform(cv1), Vector.ZERO_VECTOR);
		test(Matrix2D.ZERO_MATRIX.transform(cv1), Vector.ZERO_VECTOR);
		test(Matrix2D.ZERO_MATRIX.transform(pv1), Vector.ZERO_VECTOR);
		test(Matrix2D.ZERO_MATRIX.transform(pv1), pv0);
		test(Matrix2D.ZERO_MATRIX.transform(new CartesianVector(5, 23)), Vector.ZERO_VECTOR);
		test(Matrix2D.ZERO_MATRIX.transform(new PolarVector(5, Math.PI / 6)), cv0);

		test(Matrix2D.ROTATE_90_MATRIX.transform(Vector.ZERO_VECTOR), Vector.ZERO_VECTOR);
		test(Matrix2D.ROTATE_90_MATRIX.transform(Vector.ZERO_VECTOR), cv0);
		test(Matrix2D.ROTATE_90_MATRIX.transform(Vector.ZERO_VECTOR), pv0);
		test(Matrix2D.ROTATE_90_MATRIX.transform(cv0), Vector.ZERO_VECTOR);
		test(Matrix2D.ROTATE_90_MATRIX.transform(cv0), cv0);
		test(Matrix2D.ROTATE_90_MATRIX.transform(cv0), pv0);
		test(Matrix2D.ROTATE_90_MATRIX.transform(pv0), Vector.ZERO_VECTOR);
		test(Matrix2D.ROTATE_90_MATRIX.transform(pv0), cv0);
		test(Matrix2D.ROTATE_90_MATRIX.transform(pv0), pv0);
		test(Matrix2D.ROTATE_90_MATRIX.transform(cv1), new CartesianVector(-1, 1));
		test(Matrix2D.ROTATE_90_MATRIX.transform(cv1),
				new PolarVector(Math.sqrt(2), 3 * Math.PI / 4));
		test(Matrix2D.ROTATE_90_MATRIX.transform(pv1), new CartesianVector(-1, 1));
		test(Matrix2D.ROTATE_90_MATRIX.transform(pv1),
				new PolarVector(Math.sqrt(2), 3 * Math.PI / 4));
		test(Matrix2D.ROTATE_90_MATRIX.transform(new CartesianVector(5, 23)),
				new CartesianVector(-23, 5));
		test(Matrix2D.ROTATE_90_MATRIX.transform(new PolarVector(5, Math.PI / 6)),
				new PolarVector(5, 4 * Math.PI / 6));
		test(new Matrix2D(0, -1, 1, 0)
				.transform(Matrix2D.ROTATE_90_MATRIX.transform(new PolarVector(5, Math.PI / 6))),
				new PolarVector(5, Math.PI / 6));
	}

	public static void testTransformMatrix() {
		test(Matrix2D.IDENTITY_MATRIX.transform(Matrix2D.IDENTITY_MATRIX),
				Matrix2D.IDENTITY_MATRIX);
		test(Matrix2D.IDENTITY_MATRIX.transform(Matrix2D.FLIP_XY_MATRIX), Matrix2D.FLIP_XY_MATRIX);
		test(Matrix2D.IDENTITY_MATRIX.transform(Matrix2D.NEGATION_MATRIX),
				Matrix2D.NEGATION_MATRIX);
		test(Matrix2D.IDENTITY_MATRIX.transform(Matrix2D.PROJECT_X_MATRIX),
				Matrix2D.PROJECT_X_MATRIX);
		test(Matrix2D.IDENTITY_MATRIX.transform(Matrix2D.PROJECT_Y_MATRIX),
				Matrix2D.PROJECT_Y_MATRIX);
		test(Matrix2D.IDENTITY_MATRIX.transform(Matrix2D.REFLECT_OVER_X_MATRIX),
				Matrix2D.REFLECT_OVER_X_MATRIX);
		test(Matrix2D.IDENTITY_MATRIX.transform(Matrix2D.REFLECT_OVER_Y_MATRIX),
				Matrix2D.REFLECT_OVER_Y_MATRIX);
		test(Matrix2D.IDENTITY_MATRIX.transform(Matrix2D.ROTATE_90_MATRIX),
				Matrix2D.ROTATE_90_MATRIX);
		test(Matrix2D.IDENTITY_MATRIX.transform(Matrix2D.ZERO_MATRIX), Matrix2D.ZERO_MATRIX);

		test(Matrix2D.NEGATION_MATRIX.transform(Matrix2D.IDENTITY_MATRIX),
				Matrix2D.IDENTITY_MATRIX.scale(-1));
		test(Matrix2D.NEGATION_MATRIX.transform(Matrix2D.FLIP_XY_MATRIX),
				Matrix2D.FLIP_XY_MATRIX.scale(-1));
		test(Matrix2D.NEGATION_MATRIX.transform(Matrix2D.NEGATION_MATRIX),
				Matrix2D.NEGATION_MATRIX.scale(-1));
		test(Matrix2D.NEGATION_MATRIX.transform(Matrix2D.PROJECT_X_MATRIX),
				Matrix2D.PROJECT_X_MATRIX.scale(-1));
		test(Matrix2D.NEGATION_MATRIX.transform(Matrix2D.PROJECT_Y_MATRIX),
				Matrix2D.PROJECT_Y_MATRIX.scale(-1));
		test(Matrix2D.NEGATION_MATRIX.transform(Matrix2D.REFLECT_OVER_X_MATRIX),
				Matrix2D.REFLECT_OVER_X_MATRIX.scale(-1));
		test(Matrix2D.NEGATION_MATRIX.transform(Matrix2D.REFLECT_OVER_Y_MATRIX),
				Matrix2D.REFLECT_OVER_Y_MATRIX.scale(-1));
		test(Matrix2D.NEGATION_MATRIX.transform(Matrix2D.ROTATE_90_MATRIX),
				Matrix2D.ROTATE_90_MATRIX.scale(-1));
		test(Matrix2D.NEGATION_MATRIX.transform(Matrix2D.ZERO_MATRIX),
				Matrix2D.ZERO_MATRIX.scale(-1));

		test(Matrix2D.FLIP_XY_MATRIX.transform(Matrix2D.IDENTITY_MATRIX), Matrix2D.FLIP_XY_MATRIX);
		test(Matrix2D.FLIP_XY_MATRIX.transform(Matrix2D.FLIP_XY_MATRIX), Matrix2D.IDENTITY_MATRIX);
		test(Matrix2D.FLIP_XY_MATRIX.transform(Matrix2D.NEGATION_MATRIX),
				Matrix2D.FLIP_XY_MATRIX.scale(-1));
		test(Matrix2D.FLIP_XY_MATRIX.transform(Matrix2D.PROJECT_X_MATRIX),
				new Matrix2D(Vector.ZERO_VECTOR, Vector.E1));
		test(Matrix2D.FLIP_XY_MATRIX.transform(Matrix2D.PROJECT_Y_MATRIX),
				new Matrix2D(Vector.E2, Vector.ZERO_VECTOR));
		test(Matrix2D.FLIP_XY_MATRIX.transform(Matrix2D.REFLECT_OVER_X_MATRIX),
				new Matrix2D(Vector.E2.scale(-1), Vector.E1));
		test(Matrix2D.FLIP_XY_MATRIX.transform(Matrix2D.REFLECT_OVER_Y_MATRIX),
				new Matrix2D(Vector.E2, Vector.E1.scale(-1)));
		test(Matrix2D.FLIP_XY_MATRIX.transform(Matrix2D.ROTATE_90_MATRIX),
				new Matrix2D(Vector.E1.scale(-1), Vector.E2));
		test(Matrix2D.FLIP_XY_MATRIX.transform(Matrix2D.ZERO_MATRIX), Matrix2D.ZERO_MATRIX);

		test(Matrix2D.ZERO_MATRIX.transform(Matrix2D.IDENTITY_MATRIX), Matrix2D.ZERO_MATRIX);
		test(Matrix2D.ZERO_MATRIX.transform(Matrix2D.FLIP_XY_MATRIX), Matrix2D.ZERO_MATRIX);
		test(Matrix2D.ZERO_MATRIX.transform(Matrix2D.NEGATION_MATRIX), Matrix2D.ZERO_MATRIX);
		test(Matrix2D.ZERO_MATRIX.transform(Matrix2D.PROJECT_X_MATRIX), Matrix2D.ZERO_MATRIX);
		test(Matrix2D.ZERO_MATRIX.transform(Matrix2D.PROJECT_Y_MATRIX), Matrix2D.ZERO_MATRIX);
		test(Matrix2D.ZERO_MATRIX.transform(Matrix2D.REFLECT_OVER_X_MATRIX), Matrix2D.ZERO_MATRIX);
		test(Matrix2D.ZERO_MATRIX.transform(Matrix2D.REFLECT_OVER_Y_MATRIX), Matrix2D.ZERO_MATRIX);
		test(Matrix2D.ZERO_MATRIX.transform(Matrix2D.ROTATE_90_MATRIX), Matrix2D.ZERO_MATRIX);
		test(Matrix2D.ZERO_MATRIX.transform(Matrix2D.ZERO_MATRIX), Matrix2D.ZERO_MATRIX);

		test(Matrix2D.PROJECT_X_MATRIX.transform(Matrix2D.IDENTITY_MATRIX),
				Matrix2D.PROJECT_X_MATRIX);
		test(Matrix2D.PROJECT_X_MATRIX.transform(Matrix2D.FLIP_XY_MATRIX),
				new Matrix2D(Vector.E2, Vector.ZERO_VECTOR));
		test(Matrix2D.PROJECT_X_MATRIX.transform(Matrix2D.NEGATION_MATRIX),
				new Matrix2D(Vector.E1.scale(-1), Vector.ZERO_VECTOR));
		test(Matrix2D.PROJECT_X_MATRIX.transform(Matrix2D.PROJECT_X_MATRIX),
				Matrix2D.PROJECT_X_MATRIX);
		test(Matrix2D.PROJECT_X_MATRIX.transform(Matrix2D.PROJECT_Y_MATRIX), Matrix2D.ZERO_MATRIX);
		test(Matrix2D.PROJECT_X_MATRIX.transform(Matrix2D.REFLECT_OVER_X_MATRIX),
				Matrix2D.PROJECT_X_MATRIX);
		test(Matrix2D.PROJECT_X_MATRIX.transform(Matrix2D.REFLECT_OVER_Y_MATRIX),
				new Matrix2D(Vector.E1.scale(-1), Vector.ZERO_VECTOR));
		test(Matrix2D.PROJECT_X_MATRIX.transform(Matrix2D.ROTATE_90_MATRIX),
				new Matrix2D(Vector.E2, Vector.ZERO_VECTOR));
		test(Matrix2D.PROJECT_X_MATRIX.transform(Matrix2D.ZERO_MATRIX), Matrix2D.ZERO_MATRIX);

		test(Matrix2D.PROJECT_Y_MATRIX.transform(Matrix2D.IDENTITY_MATRIX),
				Matrix2D.PROJECT_Y_MATRIX);
		test(Matrix2D.PROJECT_Y_MATRIX.transform(Matrix2D.FLIP_XY_MATRIX),
				new Matrix2D(Vector.ZERO_VECTOR, Vector.E1));
		test(Matrix2D.PROJECT_Y_MATRIX.transform(Matrix2D.NEGATION_MATRIX),
				new Matrix2D(Vector.ZERO_VECTOR, Vector.E2.scale(-1)));
		test(Matrix2D.PROJECT_Y_MATRIX.transform(Matrix2D.PROJECT_X_MATRIX), Matrix2D.ZERO_MATRIX);
		test(Matrix2D.PROJECT_Y_MATRIX.transform(Matrix2D.PROJECT_Y_MATRIX),
				Matrix2D.PROJECT_Y_MATRIX);
		test(Matrix2D.PROJECT_Y_MATRIX.transform(Matrix2D.REFLECT_OVER_X_MATRIX),
				new Matrix2D(Vector.ZERO_VECTOR, Vector.E2.scale(-1)));
		test(Matrix2D.PROJECT_Y_MATRIX.transform(Matrix2D.REFLECT_OVER_Y_MATRIX),
				Matrix2D.PROJECT_Y_MATRIX);
		test(Matrix2D.PROJECT_Y_MATRIX.transform(Matrix2D.ROTATE_90_MATRIX),
				new Matrix2D(Vector.ZERO_VECTOR, Vector.E1.scale(-1)));
		test(Matrix2D.PROJECT_Y_MATRIX.transform(Matrix2D.ZERO_MATRIX), Matrix2D.ZERO_MATRIX);

		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(Matrix2D.IDENTITY_MATRIX),
				Matrix2D.REFLECT_OVER_X_MATRIX);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(Matrix2D.FLIP_XY_MATRIX),
				new Matrix2D(Vector.E2, Vector.E1.scale(-1)));
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(Matrix2D.NEGATION_MATRIX),
				Matrix2D.REFLECT_OVER_Y_MATRIX);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(Matrix2D.PROJECT_X_MATRIX),
				Matrix2D.PROJECT_X_MATRIX);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(Matrix2D.PROJECT_Y_MATRIX),
				Matrix2D.PROJECT_Y_MATRIX.scale(-1));
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(Matrix2D.REFLECT_OVER_X_MATRIX),
				Matrix2D.IDENTITY_MATRIX);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(Matrix2D.REFLECT_OVER_Y_MATRIX),
				Matrix2D.NEGATION_MATRIX);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(Matrix2D.ROTATE_90_MATRIX),
				Matrix2D.FLIP_XY_MATRIX);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(Matrix2D.ZERO_MATRIX), Matrix2D.ZERO_MATRIX);

		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(Matrix2D.IDENTITY_MATRIX),
				Matrix2D.REFLECT_OVER_Y_MATRIX);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(Matrix2D.FLIP_XY_MATRIX),
				new Matrix2D(Vector.E2.scale(-1), Vector.E1));
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(Matrix2D.NEGATION_MATRIX),
				Matrix2D.REFLECT_OVER_X_MATRIX);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(Matrix2D.PROJECT_X_MATRIX),
				Matrix2D.PROJECT_X_MATRIX.scale(-1));
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(Matrix2D.PROJECT_Y_MATRIX),
				Matrix2D.PROJECT_Y_MATRIX);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(Matrix2D.REFLECT_OVER_X_MATRIX),
				Matrix2D.NEGATION_MATRIX);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(Matrix2D.REFLECT_OVER_Y_MATRIX),
				Matrix2D.IDENTITY_MATRIX);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(Matrix2D.ROTATE_90_MATRIX),
				Matrix2D.FLIP_XY_MATRIX.scale(-1));
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(Matrix2D.ZERO_MATRIX), Matrix2D.ZERO_MATRIX);

		test(Matrix2D.ROTATE_90_MATRIX.transform(Matrix2D.IDENTITY_MATRIX),
				Matrix2D.ROTATE_90_MATRIX);
		test(Matrix2D.ROTATE_90_MATRIX.transform(Matrix2D.FLIP_XY_MATRIX),
				Matrix2D.REFLECT_OVER_X_MATRIX);
		test(Matrix2D.ROTATE_90_MATRIX.transform(Matrix2D.NEGATION_MATRIX),
				new Matrix2D(Vector.E2.scale(-1), Vector.E1));
		test(Matrix2D.ROTATE_90_MATRIX.transform(Matrix2D.PROJECT_X_MATRIX),
				new Matrix2D(Vector.ZERO_VECTOR, Vector.E1.scale(-1)));
		test(Matrix2D.ROTATE_90_MATRIX.transform(Matrix2D.PROJECT_Y_MATRIX),
				new Matrix2D(Vector.E2, Vector.ZERO_VECTOR));
		test(Matrix2D.ROTATE_90_MATRIX.transform(Matrix2D.REFLECT_OVER_X_MATRIX),
				new Matrix2D(Vector.E2.scale(-1), Vector.E1.scale(-1)));
		test(Matrix2D.ROTATE_90_MATRIX.transform(Matrix2D.REFLECT_OVER_Y_MATRIX),
				Matrix2D.FLIP_XY_MATRIX);
		test(Matrix2D.ROTATE_90_MATRIX.transform(Matrix2D.ROTATE_90_MATRIX),
				Matrix2D.NEGATION_MATRIX);
		test(Matrix2D.ROTATE_90_MATRIX.transform(Matrix2D.ZERO_MATRIX), Matrix2D.ZERO_MATRIX);

	}

	/**
	 * Tests the getInverse method of matrices
	 * 
	 * @throws Exception If any of the tests fail
	 */
	public static void testGetInverse() throws Exception {
		test(Matrix2D.IDENTITY_MATRIX.getInverse(), Matrix2D.IDENTITY_MATRIX);
		test(Matrix2D.NEGATION_MATRIX.getInverse(), Matrix2D.NEGATION_MATRIX);
		test(Matrix2D.FLIP_XY_MATRIX.getInverse(), Matrix2D.FLIP_XY_MATRIX);
		testException(Matrix2D.PROJECT_X_MATRIX,
				new Exception("Cannot find inverse of non-invertible matrix"), "getInverse");
		testException(Matrix2D.PROJECT_Y_MATRIX,
				new Exception("Cannot find inverse of non-invertible matrix"), "getInverse");
		test(Matrix2D.REFLECT_OVER_X_MATRIX.getInverse(), Matrix2D.REFLECT_OVER_X_MATRIX);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.getInverse(), Matrix2D.REFLECT_OVER_Y_MATRIX);
		test(Matrix2D.ROTATE_90_MATRIX.getInverse(), Matrix2D.ROTATE_90_MATRIX.scale(-1));
		testException(Matrix2D.ZERO_MATRIX,
				new Exception("Cannot find inverse of non-invertible matrix"), "getInverse");
		test(Matrix2D.ROTATE_90_MATRIX.transform(Matrix2D.ROTATE_90_MATRIX.getInverse()),
				Matrix2D.IDENTITY_MATRIX);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.transform(Matrix2D.REFLECT_OVER_X_MATRIX.getInverse()),
				Matrix2D.IDENTITY_MATRIX);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.transform(Matrix2D.REFLECT_OVER_Y_MATRIX.getInverse()),
				Matrix2D.IDENTITY_MATRIX);
	}

	/**
	 * Tests the getDeterminant method of matrices
	 */
	public static void testGetDeterminant() {
		test(Matrix2D.IDENTITY_MATRIX.getDeterminant(), 1.0);
		test(Matrix2D.NEGATION_MATRIX.getDeterminant(), 1.0);
		test(Matrix2D.FLIP_XY_MATRIX.getDeterminant(), -1.0);
		test(Matrix2D.PROJECT_X_MATRIX.getDeterminant(), 0.0);
		test(Matrix2D.PROJECT_Y_MATRIX.getDeterminant(), 0.0);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.getDeterminant(), -1.0);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.getDeterminant(), -1.0);
		test(Matrix2D.ROTATE_90_MATRIX.getDeterminant(), 1.0);
		test(Matrix2D.ZERO_MATRIX.getDeterminant(), 0.0);
	}

	/**
	 * Tests the generateOrthogonalProjectionOnto method of matrices
	 * 
	 * @throws Exception If any of the tests fail
	 */
	public static void testProjection() throws Exception {
		test(Matrix2D.generateOrthogonalProjectionOnto(1, 0), Matrix2D.PROJECT_X_MATRIX);
		test(Matrix2D.generateOrthogonalProjectionOnto(5, 0), Matrix2D.PROJECT_X_MATRIX);
		test(Matrix2D.generateOrthogonalProjectionOnto(0, 1), Matrix2D.PROJECT_Y_MATRIX);
		test(Matrix2D.generateOrthogonalProjectionOnto(0, 9), Matrix2D.PROJECT_Y_MATRIX);
		test(Matrix2D.generateOrthogonalProjectionOnto(1, 1),
				new Matrix2D(1, 1, 1, 1).scale(1 / 2.0));
		test(Matrix2D.generateOrthogonalProjectionOnto(4, 4),
				new Matrix2D(1, 1, 1, 1).scale(1 / 2.0));
		test(Matrix2D.generateOrthogonalProjectionOnto(1, 2),
				new Matrix2D(1, 2, 2, 4).scale(1 / 5.0));
		test(Matrix2D.generateOrthogonalProjectionOnto(0), Matrix2D.PROJECT_X_MATRIX);
		test(Matrix2D.generateOrthogonalProjectionOnto(Math.PI / 2), Matrix2D.PROJECT_Y_MATRIX);
		test(Matrix2D.generateOrthogonalProjectionOnto(Math.PI / 4),
				new Matrix2D(1, 1, 1, 1).scale(0.5));
	}

	/**
	 * Tests the generateReflectionOver method of matrices
	 * 
	 * @throws Exception If any of the tests fail
	 */
	public static void testReflection() throws Exception {
		test(Matrix2D.generateReflectionOver(1, 0), Matrix2D.REFLECT_OVER_X_MATRIX);
		test(Matrix2D.generateReflectionOver(5, 0), Matrix2D.REFLECT_OVER_X_MATRIX);
		test(Matrix2D.generateReflectionOver(0, 1), Matrix2D.REFLECT_OVER_Y_MATRIX);
		test(Matrix2D.generateReflectionOver(0, 9), Matrix2D.REFLECT_OVER_Y_MATRIX);
		test(Matrix2D.generateReflectionOver(1, 1), Matrix2D.FLIP_XY_MATRIX);
		test(Matrix2D.generateReflectionOver(4, 4), Matrix2D.FLIP_XY_MATRIX);
		test(Matrix2D.generateReflectionOver(1, 2), new Matrix2D(-3, 4, 4, 3).scale(1 / 5.0));
		test(Matrix2D.generateReflectionOver(0), Matrix2D.REFLECT_OVER_X_MATRIX);
		test(Matrix2D.generateReflectionOver(Math.PI / 2), Matrix2D.REFLECT_OVER_Y_MATRIX);
		test(Matrix2D.generateReflectionOver(Math.PI / 4), Matrix2D.FLIP_XY_MATRIX);
	}

	/**
	 * Tests the generateRotationOf method of matrices
	 */
	public static void testRotation() {
		test(Matrix2D.generateRotationOf(Math.toRadians(-180)), Matrix2D.NEGATION_MATRIX);
		test(Matrix2D.generateRotationOf(Math.toRadians(-150)),
				new Matrix2D(-Math.sqrt(3), -1, 1, -Math.sqrt(3)).scale(0.5));
		test(Matrix2D.generateRotationOf(Math.toRadians(-135)),
				new Matrix2D(-Math.sqrt(2), -Math.sqrt(2), Math.sqrt(2), -Math.sqrt(2)).scale(0.5));
		test(Matrix2D.generateRotationOf(Math.toRadians(-120)),
				new Matrix2D(-1, -Math.sqrt(3), Math.sqrt(3), -1).scale(0.5));
		test(Matrix2D.generateRotationOf(Math.toRadians(-90)), new Matrix2D(0, -1, 1, 0));
		test(Matrix2D.generateRotationOf(Math.toRadians(-60)),
				new Matrix2D(1, -Math.sqrt(3), Math.sqrt(3), 1).scale(0.5));
		test(Matrix2D.generateRotationOf(Math.toRadians(-45)),
				new Matrix2D(Math.sqrt(2), -Math.sqrt(2), Math.sqrt(2), Math.sqrt(2)).scale(0.5));
		test(Matrix2D.generateRotationOf(Math.toRadians(-30)),
				new Matrix2D(Math.sqrt(3), -1, 1, Math.sqrt(3)).scale(0.5));
		test(Matrix2D.generateRotationOf(Math.toRadians(0)), Matrix2D.IDENTITY_MATRIX);
		test(Matrix2D.generateRotationOf(Math.toRadians(30)),
				new Matrix2D(Math.sqrt(3), 1, -1, Math.sqrt(3)).scale(0.5));
		test(Matrix2D.generateRotationOf(Math.toRadians(45)),
				new Matrix2D(Math.sqrt(2), Math.sqrt(2), -Math.sqrt(2), Math.sqrt(2)).scale(0.5));
		test(Matrix2D.generateRotationOf(Math.toRadians(60)),
				new Matrix2D(1, Math.sqrt(3), -Math.sqrt(3), 1).scale(0.5));
		test(Matrix2D.generateRotationOf(Math.toRadians(90)), new Matrix2D(0, 1, -1, 0));
		test(Matrix2D.generateRotationOf(Math.toRadians(120)),
				new Matrix2D(-1, Math.sqrt(3), -Math.sqrt(3), -1).scale(0.5));
		test(Matrix2D.generateRotationOf(Math.toRadians(135)),
				new Matrix2D(-Math.sqrt(2), Math.sqrt(2), -Math.sqrt(2), -Math.sqrt(2)).scale(0.5));
		test(Matrix2D.generateRotationOf(Math.toRadians(150)),
				new Matrix2D(-Math.sqrt(3), 1, -1, -Math.sqrt(3)).scale(0.5));
		test(Matrix2D.generateRotationOf(Math.toRadians(180)), Matrix2D.NEGATION_MATRIX);

	}

	/**
	 * Tests the rref method of matrices
	 */
	public static void testRREF() {
		test(Matrix2D.IDENTITY_MATRIX.rref(), Matrix2D.IDENTITY_MATRIX);
		test(Matrix2D.NEGATION_MATRIX.rref(), Matrix2D.IDENTITY_MATRIX);
		test(Matrix2D.FLIP_XY_MATRIX.rref(), Matrix2D.IDENTITY_MATRIX);
		test(Matrix2D.PROJECT_X_MATRIX.rref(), Matrix2D.PROJECT_X_MATRIX);
		test(Matrix2D.PROJECT_Y_MATRIX.rref(), Matrix2D.PROJECT_X_MATRIX.swapColumns());
		test(Matrix2D.REFLECT_OVER_X_MATRIX.rref(), Matrix2D.IDENTITY_MATRIX);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.rref(), Matrix2D.IDENTITY_MATRIX);
		test(Matrix2D.ROTATE_90_MATRIX.rref(), Matrix2D.IDENTITY_MATRIX);
		test(Matrix2D.ZERO_MATRIX.rref(), Matrix2D.ZERO_MATRIX);
	}

	
	public static void testLinearlyIndependent() {
		test(Matrix2D.IDENTITY_MATRIX.linearlyIndependent(Vector.ZERO_VECTOR), false);
		test(Matrix2D.NEGATION_MATRIX.linearlyIndependent(Vector.ZERO_VECTOR), false);
		test(Matrix2D.FLIP_XY_MATRIX.linearlyIndependent(Vector.ZERO_VECTOR), false);
		test(Matrix2D.PROJECT_X_MATRIX.linearlyIndependent(Vector.ZERO_VECTOR), false);
		test(Matrix2D.PROJECT_Y_MATRIX.linearlyIndependent(Vector.ZERO_VECTOR), false);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.linearlyIndependent(Vector.ZERO_VECTOR), false);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.linearlyIndependent(Vector.ZERO_VECTOR), false);
		test(Matrix2D.ROTATE_90_MATRIX.linearlyIndependent(Vector.ZERO_VECTOR), false);
		test(Matrix2D.ZERO_MATRIX.linearlyIndependent(Vector.ZERO_VECTOR), false);

		test(Matrix2D.IDENTITY_MATRIX.linearlyIndependent(Vector.E2), false);
		test(Matrix2D.NEGATION_MATRIX.linearlyIndependent(Vector.E2), false);
		test(Matrix2D.FLIP_XY_MATRIX.linearlyIndependent(Vector.E2), false);
		test(Matrix2D.PROJECT_X_MATRIX.linearlyIndependent(Vector.E1), false);
		test(Matrix2D.PROJECT_Y_MATRIX.linearlyIndependent(Vector.E1), false);
		test(Matrix2D.PROJECT_X_MATRIX.linearlyIndependent(Vector.E2), false);
		test(Matrix2D.PROJECT_Y_MATRIX.linearlyIndependent(Vector.E2), false);
		test(Matrix2D.REFLECT_OVER_X_MATRIX.linearlyIndependent(Vector.E2), false);
		test(Matrix2D.REFLECT_OVER_Y_MATRIX.linearlyIndependent(Vector.E2), false);
		test(Matrix2D.ROTATE_90_MATRIX.linearlyIndependent(Vector.E2), false);
		test(Matrix2D.ZERO_MATRIX.linearlyIndependent(Vector.E2), false);

		test(new Matrix2D(1,1,1,1).linearlyIndependent(new CartesianVector(1, 1)), false);
		test(new Matrix2D(1, 1, 1, 1).linearlyIndependent(Vector.E1), true);
		test(new Matrix2D(1, 1, -1, -1).linearlyIndependent(Vector.E1), true);
		test(new Matrix2D(1, 1, 1, 1).linearlyIndependent(Vector.E2), true);
		test(new Matrix2D(1, 1, -1, -1).linearlyIndependent(Vector.E2), true);
		test(new Matrix2D(1,1,1,1).linearlyIndependent(new CartesianVector(3, 3)), false);
		test(new Matrix2D(1,1,-2,-2).linearlyIndependent(new CartesianVector(3, 3)), false);
	}

}
