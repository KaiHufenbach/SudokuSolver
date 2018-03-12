package sudokusolver.strategies;

import java.util.HashSet;
import java.util.Set;

import sudokusolver.riddle.Field;
import sudokusolver.riddle.Tableau;

public class ColumnStrategy implements Strategy {

	@Override
	public boolean resolve(Tableau tableau) {
		boolean changed = false;
		for (Field[] column : tableau.getColumns()) {

			Set<Integer> illegalNumbers = new HashSet();
			for (Field field : column) {
				if (field.getValue() != null) {
					illegalNumbers.add(field.getValue());
				}
			}

			for (Field field : column) {
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
