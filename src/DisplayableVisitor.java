public interface DisplayableVisitor<T> extends Visitor<T, Displayable>{
	
	public T visit(TransformationPlane tp);
	
}
