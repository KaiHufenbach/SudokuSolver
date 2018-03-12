package sudokusolver.strategies;

import java.util.HashSet;
import java.util.Set;

import sudokusolver.riddle.Field;
import sudokusolver.riddle.Tableau;

public class RowStrategy implements Strategy {

	@Override
	public boolean resolve(Tableau tableau) {
		boolean changed = false;
		for (Field[] row : tableau.getRows()) {

			Set<Integer> illegalNumbers = new HashSet();
			for (Field field : row) {
				if (field.getValue() != null) {
					illegalNumbers.add(field.getValue());
				}
			}

			for (Field field : row) {
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
