package sudokusolver.strategies;

import sudokusolver.riddle.Tableau;

public interface Strategy {

	// schränkt die Wertemenge der Tabellen ein
	public boolean resolve(Tableau tableau);

}
