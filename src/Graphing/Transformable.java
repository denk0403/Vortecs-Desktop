package Graphing;

import Matrices.Matrix2D;

public interface Transformable extends Displayable {

	// returns the Matrix of this transformable
	public Matrix2D getMatrix();
	
	// transforms this transformable by the given matrix
	public void transform(Matrix2D m);
	
	// sets this transformable's matrix to the given one
	public void setMatrix(Matrix2D m);
}
