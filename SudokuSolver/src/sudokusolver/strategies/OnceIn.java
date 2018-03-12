package sudokusolver.strategies;

import java.util.function.Function;

import sudokusolver.riddle.Field;
import sudokusolver.riddle.Tableau;

public class OnceIn implements Strategy {

	private Function<Tableau, Field[][]> function;

	public OnceIn(Function<Tableau, Field[][]> function) {
		this.function = function;
	}

	@Override
	public boolean resolve(Tableau tableau) {
		boolean found = false;

		for (Field[] compound : function.apply(tableau)) {

			for (int i = 1; i <= 9; i++) {
				int count = 0;
				Field last = null;

				for (Field field : compound) {
					if (field.getPossibleValues().contains(i)) {
						count++;
						last = field;
					}
				}

				if (count == 1) {
					last.getPossibleValues().clear();
					last.getPossibleValues().add(i);
					found = true;
				}
			}

		}

		return found;

	}

}
