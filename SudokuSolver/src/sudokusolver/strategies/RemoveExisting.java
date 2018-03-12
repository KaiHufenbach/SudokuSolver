package sudokusolver.strategies;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import sudokusolver.riddle.Field;
import sudokusolver.riddle.Tableau;

public class RemoveExisting implements Strategy {

	private Function<Tableau, Field[][]> function;

	public RemoveExisting(Function<Tableau, Field[][]> function) {
		this.function = function;
	}

	@Override
	public boolean resolve(Tableau tableau) {
		boolean changed = false;
		for (Field[] compound : function.apply(tableau)) {

			Set<Integer> illegalNumbers = new HashSet();
			for (Field field : compound) {
				if (field.getValue() != null) {
					illegalNumbers.add(field.getValue());
				}
			}

			for (Field field : compound) {
				for (Integer illegal : illegalNumbers) {
					if (field.removeFromPossibleValue(illegal)) {
						changed = true;
					}
				}
			}

		}

		return changed;

	}

}
