package com.tyl.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.tyl.R;
import com.tyl.commom.table.TableAdapter;
import com.tyl.commom.table.TableCell;
import com.tyl.commom.table.TableRow;

public class TableActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);
		ListView dataListView = (ListView) findViewById(R.id.dataListView);
		List<TableRow> table = new ArrayList<TableRow>();
		for (int i = 1; i < 21; i++) {
			List<TableCell> cells = new ArrayList<TableCell>();
			for (int j = 1; j < 21; j++) {
				cells.add(new TableCell("Row:" + i + ",Cell:" + j, 200, 50, TableCell.STRING));
			}
			TableRow tr = new TableRow(cells);
			table.add(tr);
		}
		TableAdapter adapter = new TableAdapter(this, table);
		dataListView.setAdapter(adapter);
	}

}
