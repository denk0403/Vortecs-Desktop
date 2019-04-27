package Matrices;

import java.awt.geom.AffineTransform;

import Vectors.CartesianVector;
import Vectors.Vector;

// represents a 2x2 Matrix
public class Matrix2D {

//	 This Matrix2D represents the matrix given by:
//	 [ e11 e12 ]
//	 [ e21 e22 ]

	private final Vector cv1, cv2; // columns vectors
	private final Vector rv1, rv2; // row vectors

	// creates a 2x2 matrix with the given entries
	public Matrix2D(double e11, double e21, double e12, double e22) {
		this.cv1 = new CartesianVector(e11, e21);
		this.cv2 = new CartesianVector(e12, e22);
		this.rv1 = new CartesianVector(e11, e12);
		this.rv2 = new CartesianVector(e21, e22);
	}

	// creates a 2x2 matrix with the given column vectors
	public Matrix2D(Vector v1, Vector v2) {
		this.cv1 = v1;
		this.cv2 = v2;
		this.rv1 = new CartesianVector(v1.getXComponent(), v2.getXComponent());
		this.rv2 = new CartesianVector(v1.getYComponent(), v2.getYComponent());
	}

	// returns a new vector with the transformation represented by this
	// matrix applied on the given vector
	public Vector transform(Vector v) {
		return new CartesianVector(this.rv1.dotProduct(v), this.rv2.dotProduct(v), v.getColor());
	}

	// returns a new matrix with the given matrix multiplied on this matrix
	// [A].transform([B]) => [B][A]
	public Matrix2D transform(Matrix2D b) {
		System.out.println("Deter: " + this.getDeterminant());
		return new Matrix2D(b.transform(this.cv1), b.transform(this.cv2));
	}

	// returns a new matrix with this matrix multiplied on the given matrix
	// [A].applyTransformOn([B]) => [A][B]
	public Matrix2D applyTransformOn(Matrix2D b) {
		return b.transform(this);
	}

	// returns the sum of this matrix and the given matrix
	public Matrix2D add(Matrix2D m) {
		return new Matrix2D(this.getFirstColumn().add(m.getFirstColumn()),
				this.getSecondColumn().add(m.getSecondColumn()));
	}

	// returns the new matrix of this matrix multiplied by the given scalar
	public Matrix2D scale(double scalar) {
		return new Matrix2D(this.getFirstColumn().scale(scalar),
				this.getSecondColumn().scale(scalar));
	}

	// returns the transpose of this matrix
	public Matrix2D getTranspose() {
		return new Matrix2D(this.getFirstRow(), this.getSecondRow());
	}

	// returns the determinant of this matrix
	public double getDeterminant() {
		return this.getFirstColumn().getXComponent() * this.getSecondColumn().getYComponent()
				- this.getFirstColumn().getYComponent() * this.getSecondColumn().getXComponent();
	}

	// returns the inverse of this matrix
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

	// returns the first column vector
	public Vector getFirstColumn() {
		return this.cv1;
	}

	// returns the second column vector
	public Vector getSecondColumn() {
		return this.cv2;
	}

	// returns the first row vector
	public Vector getFirstRow() {
		return this.rv1;
	}

	// returns the second row vector
	public Vector getSecondRow() {
		return this.rv2;
	}

	// returns a string representation of this matrix
	public String toString() {
		return "Matrix:" + "\n\t[ " + this.getFirstRow().getXComponent() + " "
				+ this.getFirstRow().getYComponent() + " ]\n\t[ "
				+ this.getSecondRow().getXComponent() + " " + this.getSecondRow().getYComponent()
				+ " ]";
	}

	@Deprecated
	// returns the AffineTransform represented by this matrix
	public AffineTransform getAsAffineTransform() {
		return new AffineTransform(Math.sqrt(this.getFirstColumn().getXComponent()),
				this.getFirstColumn().getYComponent(), this.getSecondColumn().getXComponent(),
				Math.sqrt(this.getSecondColumn().getYComponent()), 0, 0);
	}

}
