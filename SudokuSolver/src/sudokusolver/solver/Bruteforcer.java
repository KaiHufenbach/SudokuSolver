package sudokusolver.solver;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import sudokusolver.riddle.Field;
import sudokusolver.riddle.Tableau;

public class Bruteforcer {

	private long noOfBrutes = 0;
	private Stack<Guess> stack = new Stack<>();
	private Tableau start;

	public Bruteforcer(Tableau start) {
		this.start = start;
	}

	public Tableau bruteForce() {
		noOfBrutes++;
		if (noOfBrutes % 1000000 == 0) {
			System.out.println(noOfBrutes);
		}

		Tableau parent = getCurrent();
		if (!parent.isSolveable() || !parent.isValid()) {
			Guess wrongGuess = stack.pop();
			Tableau toChange = wrongGuess.parent;

			Field wrongGuessField = wrongGuess.tableau.getFields()[wrongGuess.guessIndex];
			toChange.getFields()[wrongGuess.guessIndex].removeFromPossibleValue(wrongGuessField.getValue());

			return toChange;
		}

		Tableau base = parent.createCopy();

		Field guessField = getNextFieldCandidate(base);
		guessField.setValue(guessField.getPossibleValues().get(0));

		if (base.isValid()) {
			stack.add(new Guess(base, guessField.getPos(), parent));
			return base;
		} else {
			parent.getFields()[guessField.getPos()].removeFromPossibleValue(guessField.getValue());
			return parent;
		}

	}

	public long getNoOfBrutes() {
		return noOfBrutes;
	}

	private Tableau getCurrent() {
		return stack.isEmpty() ? start : stack.peek().tableau;
	}

	private Field getNextFieldCandidate(Tableau tab) {
		List<Field> fields = Arrays.asList(tab.getFields()).stream().filter(f -> !f.getPossibleValues().isEmpty())
				.collect(Collectors.toList());

		fields.sort((f1, f2) -> f1.getPossibleValues().size() - f2.getPossibleValues().size());

		if (fields.isEmpty()) {
			return null;
		} else {
			return fields.get(0);
		}

	}

	public class Guess {

		public Guess(Tableau tab, int guessIndex, Tableau parent) {
			this.tableau = tab;
			this.guessIndex = guessIndex;
			this.parent = parent;
		}

		private Tableau tableau;
		private int guessIndex;
		private Tableau parent;
	}

}
