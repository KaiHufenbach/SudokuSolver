package sudokusolver.strategies;

import sudokusolver.riddle.Field;
import sudokusolver.riddle.Tableau;

public abstract class AStrategy {
	protected Field myLastSolvedField;

	public abstract Field solveOneField(Tableau tab, Field lastSolvedField);
}
