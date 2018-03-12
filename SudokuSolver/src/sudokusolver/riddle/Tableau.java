package sudokusolver.riddle;

import java.util.HashSet;
import java.util.Set;

public class Tableau {

	private Field[][] rows = new Field[9][9];
	private Field[][] columns = new Field[9][9];
	private Field[][] blocks = new Field[9][9];
	private Field[] fields = new Field[81];

	public static Tableau createTableau(String description) {
		Tableau tab = new Tableau();
		String[] lines = description.split("\n");
		if (lines.length != 9) {
			throw new IllegalArgumentException("9 rows required!");
		}

		int lineNo = 0;
		for (String line : lines) {

			String[] fields = line.split(",", -1);
			if (fields.length != 9) {
				throw new IllegalArgumentException("9 columns required: row " + lineNo);
			}

			int columnNo = 0;
			for (String fieldValue : fields) {
				Field field = new Field(fieldValue, lineNo * 9 + columnNo);
				tab.rows[lineNo][columnNo] = field;
				tab.columns[columnNo][lineNo] = field;
				tab.fields[lineNo * 9 + columnNo] = field;
				tab.blocks[lineNo / 3 * 3 + columnNo / 3][lineNo % 3 * 3 + columnNo % 3] = field;

				columnNo++;
			}

			lineNo++;
		}

		if (!tab.isValid()) {
			throw new IllegalArgumentException("Tableau invalid");
		}

		return tab;

	}

	public boolean isValid() {
		for (Field field : fields) {
			Integer value = field.getValue();
			if (value != null) {
				if (!isValidNumber(value)) {
					throw new IllegalStateException(value + " is not a valid number");
				}
			}
		}

		return checkCompounds(rows) && checkCompounds(columns) && checkCompounds(blocks);
	}

	public boolean isFilled() {
		for (Field field : fields) {
			if (field.getValue() == null) {
				return false;
			}
		}

		return true;
	}

	private boolean checkCompounds(Field[][] compounds) {
		Set<Integer> set = new HashSet<>();
		for (Field[] compound : compounds) {
			set.clear();
			for (Field field : compound) {
				Integer value = field.getValue();
				if (value != null) {
					if (!set.add(value)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public boolean isValidNumber(int number) {
		return 1 <= number && number <= 9;
	}

	public boolean setValues() {
		boolean set = false;
		for (Field field : fields) {
			if (field.getPossibleValues().size() == 1) {
				field.setValue(field.getPossibleValues().get(0));
				set = true;
			}
		}

		return set;
	}

	public Tableau createCopy() {
		Tableau target = new Tableau();

		for (int lineNo = 0; lineNo < 9; lineNo++) {
			for (int columnNo = 0; columnNo < 9; columnNo++) {
				Field field = rows[lineNo][columnNo].copyOf();
				target.rows[lineNo][columnNo] = field;
				target.columns[columnNo][lineNo] = field;
				target.fields[lineNo * 9 + columnNo] = field;
				target.blocks[lineNo / 3 * 3 + columnNo / 3][lineNo % 3 * 3 + columnNo % 3] = field;
			}
		}

		return target;
	}

	public Field[] getFields() {
		return fields;
	}

	public Field[][] getBlocks() {
		return blocks;
	}

	public Field[][] getRows() {
		return rows;
	}

	public Field[][] getColumns() {
		return columns;
	}

	public boolean isSolveable() {
		for (Field field : fields) {
			if (!field.getPossibleValues().isEmpty()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < fields.length; i++) {
			if (i != 0 && i % 9 == 0) {
				sb.append("\r");
			}
			sb.append(fields[i].toString() + " ");
		}

		return sb.toString();
	}

}
