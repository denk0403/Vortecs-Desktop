package Matrices;

import java.awt.geom.AffineTransform;

import Vectors.CartesianVector;
import Vectors.PolarVector;
import Vectors.Vector;

/**
 * Represents a 2x2 Matrix
 * 
 * @author Dennis Kats
 *
 */
public class Matrix2D {

	public static final Matrix2D ZERO_MATRIX = new Matrix2D(0, 0, 0, 0);
	public static final Matrix2D IDENTITY_MATRIX = new Matrix2D(1, 0, 0, 1);
	public static final Matrix2D FLIP_XY_MATRIX = new Matrix2D(0, 1, 1, 0);
	public static final Matrix2D NEGATION_MATRIX = new Matrix2D(-1, 0, 0, -1);
	public static final Matrix2D PROJECT_X_MATRIX = new Matrix2D(1, 0, 0, 0);
	public static final Matrix2D PROJECT_Y_MATRIX = new Matrix2D(0, 0, 0, 1);
	public static final Matrix2D REFLECT_OVER_X_MATRIX = new Matrix2D(1, 0, 0, -1);
	public static final Matrix2D REFLECT_OVER_Y_MATRIX = new Matrix2D(-1, 0, 0, 1);
	public static final Matrix2D ROTATE_90_MATRIX = new Matrix2D(0, 1, -1, 0);

	private final Vector cv1, cv2; // columns vectors
	private final Vector rv1, rv2; // row vectors

	/**
	 * Creates a 2x2 matrix with the given entries
	 * 
	 * <pre>
	 *  [ e11 e12 ]
	 *  [ e21 e22 ]
	 * </pre>
	 * 
	 * @param e11 The top-left entry
	 * @param e21 The bottom-left entry
	 * @param e12 The top-right entry
	 * @param e22 The bottom-right entry
	 */
	public Matrix2D(double e11, double e21, double e12, double e22) {
		this.cv1 = new CartesianVector(e11, e21);
		this.cv2 = new CartesianVector(e12, e22);
		this.rv1 = new CartesianVector(e11, e12);
		this.rv2 = new CartesianVector(e21, e22);
	}

	/**
	 * Creates a 2x2 matrix with the given column vectors
	 * 
	 * <pre>
	 *  [  |  |  ]
	 *  [  v1 v2 ]
	 *  [  |  |  ]
	 * </pre>
	 * 
	 * @param v1 The first (left) column vector
	 * @param v2 The second (right) column vector
	 */
	public Matrix2D(Vector v1, Vector v2) {
		this.cv1 = v1;
		this.cv2 = v2;
		this.rv1 = new CartesianVector(v1.getXComponent(), v2.getXComponent());
		this.rv2 = new CartesianVector(v1.getYComponent(), v2.getYComponent());
	}

	/**
	 * Creates a 2x2 identity matrix
	 * 
	 * <pre>
	 * [ 1 0 ]
	 * [ 0 1 ]
	 * </pre>
	 */
	public Matrix2D() {
		this(Vector.E1, Vector.E2);
	}

	/**
	 * Returns a new vector with the transformation represented by this matrix
	 * applied on the given vector
	 * 
	 * @param v The vector to be transformed
	 * @return The transformed vector
	 */
	public Vector transform(Vector v) {
		return new CartesianVector(this.rv1.dotProduct(v), this.rv2.dotProduct(v), v.getColor());
	}

	/**
	 * Returns a new matrix with the given matrix multiplied on this matrix
	 * <p>
	 * [A].transform([B]) => [B][A]
	 * </p>
	 * 
	 * @param b The matrix that is multiplying
	 * @return The matrix product [B][A]
	 */
	//
	public Matrix2D transform(Matrix2D b) {
		return new Matrix2D(b.transform(this.cv1), b.transform(this.cv2));
	}

	/**
	 * Returns a new matrix with this matrix multiplied on the given matrix
	 * <p>
	 * [A].applyTransformOn([B]) => [A][B]
	 * </p>
	 * 
	 * @param b The matrix being multiplied on
	 * @return The matrix product [A][B]
	 */
	public Matrix2D applyTransformOn(Matrix2D b) {
		return b.transform(this);
	}

	/**
	 * Returns the sum of this matrix and the given matrix Specifically, given the
	 * following two matrices
	 * 
	 * <pre>
	 * [ a b ]  [ e f ]
	 * [ c d ]  [ g h ]
	 * </pre>
	 * 
	 * then their sum is given by
	 * 
	 * <pre>
	 * [ a + e  b + f ]
	 * [ c + g  d + h ]
	 * </pre>
	 * 
	 * @param m The matrix to add with
	 * @return The matrix sum
	 */
	public Matrix2D add(Matrix2D m) {
		return new Matrix2D(this.getFirstColumn().add(m.getFirstColumn()),
				this.getSecondColumn().add(m.getSecondColumn()));
	}

	/**
	 * Returns the new matrix of this matrix multiplied by the given scalar.
	 * Specifically, given the following matrix
	 * 
	 * <pre>
	 * [ a b ]
	 * [ c d ]
	 * </pre>
	 * 
	 * Then scaling it by a factor of <i>k</i> will return the matrix
	 * 
	 * <pre>
	 * [ <i>k</i>a <i>k</i>b ]
	 * [ <i>k</i>c <i>k</i>d ]
	 * </pre>
	 * 
	 * @param scalar The scaling factor
	 * @return The scaled matrix
	 */
	public Matrix2D scale(double scalar) {
		return new Matrix2D(this.getFirstColumn().scale(scalar),
				this.getSecondColumn().scale(scalar));
	}

	/**
	 * Returns the transpose of this matrix. Specifically, given the following
	 * matrix
	 * 
	 * <pre>
	 * [ a b ]
	 * [ c d ]
	 * </pre>
	 * 
	 * then its transpose will be
	 * 
	 * <pre>
	 * [ a c ]
	 * [ b d ]
	 * </pre>
	 * <p>
	 * Note that this method satisfies the property that (A<sup>T</sup>)<sup>T</sup>
	 * = A, where A is some matrix
	 * </p>
	 * 
	 * @return The transpose of this matrix
	 */
	public Matrix2D getTranspose() {
		return new Matrix2D(this.getFirstRow(), this.getSecondRow());
	}

	/**
	 * Returns the determinant of this matrix. Specifically, given the following
	 * matrix
	 * 
	 * <pre>
	 * [ a b ]
	 * [ c d ]
	 * </pre>
	 * 
	 * then its determinant is given by ad-bc.
	 * <p>
	 * </p>
	 * 
	 * @return The determinant of this matrix
	 */
	public double getDeterminant() {
		return this.getFirstColumn().getXComponent() * this.getSecondColumn().getYComponent()
				- this.getFirstColumn().getYComponent() * this.getSecondColumn().getXComponent();
	}

	/**
	 * Returns the inverse of this matrix. Specifically, given the following matrix
	 * 
	 * <pre>
	 * [ a b ]
	 * [ c d ]
	 * </pre>
	 * 
	 * then its inverse will be
	 * 
	 * <pre>
	 * _1_ [ d -b ]
	 * <i>det</i> [ -c a ]
	 * </pre>
	 * 
	 * where <i>det</i> is the determinant of the matrix
	 * <p>
	 * </p>
	 * 
	 * @return The inverse of this matrix, if one exists
	 * @throws Exception If this matrix is non-invertible
	 */
	public Matrix2D getInverse() throws Exception {
		if (this.getDeterminant() == 0) {
			throw new Exception("Cannot find inverse of non-invertible matrix");
		}
		return (new Matrix2D(
				new CartesianVector(this.getSecondColumn().getYComponent(),
						-this.getFirstColumn().getYComponent()),
				new CartesianVector(-this.getSecondColumn().getXComponent(),
						this.getFirstColumn().getXComponent()))).scale(1 / this.getDeterminant());
	}

	/**
	 * 
	 * @param x The x-component of the vector on the line to project onto
	 * @param y The y-component of the vector on the line to project onto
	 * @return The orthogonal projection matrix onto the line spanned by (x, y)
	 * @throws Exception If the given vector components make a zero vector
	 */
	public static Matrix2D generateOrthogonalProjectionOnto(double x, double y) throws Exception {
		return new CartesianVector(x, y).getProjectionMatrix();
	}

	/**
	 * 
	 * @param radians The angle in radians of the line from the positive x-axis
	 * @return The orthogonal projection matrix onto the line in the given direction
	 */
	public static Matrix2D generateOrthogonalProjectionOnto(double radians) {
		try {
			return new PolarVector(1, radians).getProjectionMatrix();
		} catch (Exception e) {
			// SHOULD NEVER REACH THIS POINT
			e.printStackTrace();
			return new Matrix2D();
		}
	}

	/**
	 * 
	 * @param x The x-component of the vector on the line to reflect over
	 * @param y The y-component of the vector on the line to reflect over
	 * @return The reflection matrix over the line spanned by (x, y)
	 * @throws Exception If the given vector components make a zero vector
	 */
	public static Matrix2D generateReflectionOver(double x, double y) throws Exception {
		return new CartesianVector(x, y).getReflectionMatrix();
	}
	
	/**
	 * 
	 * @param radians The angle in radians of the line from the positive x-axis
	 * @return The reflection matrix over the line in the given direction
	 */
	public static Matrix2D generateReflectionOver(double radians) {
		try {
			return new PolarVector(1, radians).getReflectionMatrix();
		} catch (Exception e) {
			// SHOULD NEVER REACH THIS POINT
			e.printStackTrace();
			return new Matrix2D();
		}
	}

	/**
	 * 
	 * @param radians The amount in radians that matrix should rotate
	 * @return The rotation matrix for the given amount of radians
	 */
	public static Matrix2D generateRotationOf(double radians) {
		return new Matrix2D(Math.cos(radians), Math.sin(radians), -Math.sin(radians),
				Math.cos(radians));
	}

	/**
	 * @return The first column vector
	 */
	public Vector getFirstColumn() {
		return this.cv1;
	}

	/**
	 * @return The second column vector
	 */
	public Vector getSecondColumn() {
		return this.cv2;
	}

	/**
	 * @return The first row vector
	 */
	public Vector getFirstRow() {
		return this.rv1;
	}

	/**
	 * @return The second row vector
	 */
	public Vector getSecondRow() {
		return this.rv2;
	}

	@Override
	public String toString() {
		return "Matrix:" + "\n\t[ " + this.getFirstRow().getXComponent() + " "
				+ this.getFirstRow().getYComponent() + " ]\n\t[ "
				+ this.getSecondRow().getXComponent() + " " + this.getSecondRow().getYComponent()
				+ " ]";
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Matrix2D)) {
			return false;
		}
		Matrix2D mat = (Matrix2D) obj;
		return this.getFirstColumn().equals(mat.getFirstColumn())
				&& this.getSecondColumn().equals(mat.getSecondColumn())
				&& this.getFirstRow().equals(mat.getFirstRow())
				&& this.getSecondRow().equals(mat.getSecondRow())
				&& this.hashCode() == mat.hashCode();
	}

	@Override
	public int hashCode() {
		return this.getFirstColumn().hashCode() + this.getSecondColumn().hashCode()
				+ this.getFirstColumn().hashCode() + this.getSecondRow().hashCode();
	}

	/**
	 * @return The AffineTransform represented by this matrix
	 */
	@Deprecated
	public AffineTransform getAsAffineTransform() {
		return new AffineTransform(Math.sqrt(this.getFirstColumn().getXComponent()),
				this.getFirstColumn().getYComponent(), this.getSecondColumn().getXComponent(),
				Math.sqrt(this.getSecondColumn().getYComponent()), 0, 0);
	}

}
