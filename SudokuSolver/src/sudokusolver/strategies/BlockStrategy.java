package sudokusolver.strategies;

import java.util.HashSet;
import java.util.Set;

import sudokusolver.riddle.Field;
import sudokusolver.riddle.Tableau;

public class BlockStrategy implements Strategy {

	@Override
	public boolean resolve(Tableau tableau) {
		boolean changed = false;
		for (Field[] block : tableau.getBlocks()) {

			Set<Integer> illegalNumbers = new HashSet();
			for (Field field : block) {
				if (field.getValue() != null) {
					illegalNumbers.add(field.getValue());
				}
			}

			for (Field field : block) {
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
