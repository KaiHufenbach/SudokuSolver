package sudokusolver.riddle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Field {

	private Integer value = null;
	private List<Integer> possibleValues = new ArrayList<>();
	private int pos = 0;

	public Field(String value, int pos) {
		if (value != null && !value.equals("")) {
			setValue(Integer.valueOf(value));
		} else {
			for (int i = 1; i < 10; i++) {
				possibleValues.add(i);
			}
		}

		this.pos = pos;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		this.possibleValues = Collections.EMPTY_LIST;
	}

	public List<Integer> getPossibleValues() {
		return possibleValues;
	}

	public int getPos() {
		return pos;
	}

	public boolean removeFromPossibleValue(Integer value) {
		return possibleValues.remove(value);
	}

	public Field copyOf() {
		Field field = new Field("", this.pos);
		field.value = value;
		field.possibleValues.clear();

		for (Integer i : possibleValues) {
			field.possibleValues.add(i);
		}

		return field;
	}

	@Override
	public String toString() {
		return value == null ? "-" : (value + "");
	}

}
