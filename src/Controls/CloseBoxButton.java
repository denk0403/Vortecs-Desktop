package Controls;
import java.awt.Button;

// responsible for closing and removing input boxes
public class CloseBoxButton extends Button {

	private InputBox associatedBox;

	CloseBoxButton(InputBox associatedBox) {
		this.associatedBox = associatedBox;
	}

	void close() {
		InputBoxContainer.getInstance().remove(associatedBox);
	}

}
