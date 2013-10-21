package com.tyl.commom.table;

import java.util.List;

public class TableRow {

	private List<TableCell> cells;

	public TableRow(List<TableCell> cells) {
		this.cells = cells;
	}

	public int getSize() {
		return cells.size();
	}

	public TableCell getCellValue(int index) {
		if (cells == null) {
			return null;
		}
		if (index >= cells.size()) {
			return null;
		}
		return cells.get(index);
	}
}
