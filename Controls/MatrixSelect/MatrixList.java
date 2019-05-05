package Controls.MatrixSelect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Matrices.MatrixString;

public class MatrixList extends JPanel {

	MatrixInput mi;
	JList<MatrixString> list;

	MatrixList() {
		this.list = new JList<>(MatrixString.values());
	}

	private void initComponents() {
		
		this.list.setSelectedIndex(0);
		
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (list.getSelectedValue() == MatrixString.CUSTOM) {
					MatrixList.this.mi.empty();
				} else {
					MatrixList.this.mi.copyMatrix(list.getSelectedValue().getMatrix());
				}
			}
		});

		this.add(list);
	}
	
	public void setMatrixInput(MatrixInput mi) {
		this.mi = mi;
		this.initComponents();
	}

}
