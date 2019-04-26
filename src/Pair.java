public class Pair<E,T> {

	private E elt1;
	private T elt2;
	
	public Pair(E elt1, T elt2) {
		this.elt1 = elt1;
		this.elt2 = elt2;
	}
	
	public E getFirst() {
		return this.elt1;
	}
	
	public T getSecond() {
		return this.elt2;
	}
}
