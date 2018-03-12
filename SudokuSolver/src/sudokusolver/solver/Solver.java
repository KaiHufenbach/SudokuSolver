package sudokusolver.solver;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sudokusolver.riddle.Tableau;
import sudokusolver.strategies.OnceIn;
import sudokusolver.strategies.RemoveExisting;
import sudokusolver.strategies.Strategy;

public class Solver {

	private Bruteforcer brutus;
	private Tableau currentState;
	private List<Strategy> strategies = new ArrayList();

	public Solver(Tableau toSolve) {
		brutus = new Bruteforcer(toSolve);
		currentState = toSolve;

		// What Set of strategies are needed, such that no Brute is required???

		strategies.add(new RemoveExisting(t -> t.getBlocks()));
		strategies.add(new RemoveExisting(t -> t.getColumns()));
		strategies.add(new RemoveExisting(t -> t.getRows()));
		strategies.add(new OnceIn(t -> t.getBlocks()));
		strategies.add(new OnceIn(t -> t.getColumns()));
		strategies.add(new OnceIn(t -> t.getRows()));
	}

	public void solveStep() {

		currentState.setValues();

		int currentStrategy = 0;

		while (currentStrategy < strategies.size()) {

			if (strategies.get(currentStrategy).resolve(currentState)) {
				currentState.setValues();
				currentStrategy = 0;
			} else {
				currentStrategy++;
			}

		}

		if (!currentState.isFilled() || !currentState.isValid()) {
			currentState = brutus.bruteForce();
		}
	}

	public void solve() {
		while (!currentState.isFilled()) {
			solveStep();
		}
		System.out.println("Solved! (" + brutus.getNoOfBrutes() + " brutes)");
		System.out.println(currentState);
	}

	public static void main(String[] args) {
		InputStream in = Tableau.class.getClassLoader().getResourceAsStream("riddles/testriddle");
		Scanner s = new Scanner(in).useDelimiter("\\A");
		Tableau tab = Tableau.createTableau(s.next());
		System.out.println("solving: ");
		System.out.println(tab);
		new Solver(tab).solve();
	}

}
