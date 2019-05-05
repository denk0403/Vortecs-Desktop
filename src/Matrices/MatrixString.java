package Matrices;

public enum MatrixString {
	
	CUSTOM("Custom Matrix", new Matrix2D()),
	IDENTITY("Identity Matrix", Matrix2D.IDENTITY_MATRIX),
	NEGATION("Negation Matrix", Matrix2D.NEGATION_MATRIX),
	PROJECTX("Project Onto X-axis", Matrix2D.PROJECT_X_MATRIX),
	PROJECTY("Project Onto Y-axis", Matrix2D.PROJECT_Y_MATRIX),
	PROJECTXY("Project Onto Y = X", Matrix2D.PROJECT_XY_MATRIX),
	REFLECTX("Reflect Over X-Axis", Matrix2D.REFLECT_OVER_X_MATRIX),
	REFLECTY("Reflect Over Y-Axis", Matrix2D.REFLECT_OVER_Y_MATRIX),
	FLIPXY("Reflect Over Y = X", Matrix2D.FLIP_XY_MATRIX),
	ROTATECCW("Rotate 90º", Matrix2D.ROTATE_90_MATRIX),
	ROTATECW("Rotate -90º", Matrix2D.ROTATE_90_CW_MATRIX),
	ZERO("Zero Matrix", Matrix2D.ZERO_MATRIX);
	
	private String name;
	private Matrix2D matrix;
	
	MatrixString(String name, Matrix2D matrix) {
		this.name = name;
		this.matrix = matrix;
	}
	
	public Matrix2D getMatrix() {
		return this.matrix;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
