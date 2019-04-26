import java.util.EmptyStackException;
import java.util.Stack;

public class Undo {

	public static Stack<Pair<Undoable, Visitor<Undoable, Undoable>>> operations;
	private static Undo instance;

	static {
		instance = new Undo();
		operations = new Stack<>();
	}
	
	private Undo() {
	}
	
	public void undo() {
		System.out.println("Here");
		try {
			Pair<Undoable, Visitor<Undoable, Undoable>> p = operations.pop();
			p.getSecond().visit(p.getFirst());
		}
		catch (EmptyStackException e) {
			e.printStackTrace();
		}	
	}
	
	public void addOperation(Pair<Undoable, Visitor<Undoable, Undoable>> pair) {
		operations.push(pair);
	}

	public static Undo getInstance() {
		return instance;
	}
	
}
