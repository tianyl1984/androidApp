package com.tyl.commom.table;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TableAdapter extends BaseAdapter {
	private Context context;
	private List<TableRow> table;

	public TableAdapter(Context context, List<TableRow> table) {
		this.context = context;
		this.table = table;
	}

	@Override
	public int getCount() {
		return table.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public TableRow getItem(int position) {
		return table.get(position);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TableRow tableRow = table.get(position);
		return new TableRowView(this.context, tableRow);
	}

	/**
	 * TableRowView 实现表格行的样式
	 * 
	 */
	public class TableRowView extends LinearLayout {
		public TableRowView(Context context, TableRow tableRow) {
			super(context);

			this.setOrientation(LinearLayout.HORIZONTAL);
			for (int i = 0; i < tableRow.getSize(); i++) {// 逐个格单元添加到行
				final TableCell tableCell = tableRow.getCellValue(i);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(tableCell.getWidth(), tableCell.getHeight());// 按照格单元指定的大小设置空间
				layoutParams.setMargins(0, 0, 1, 1);// 预留空隙制造边框
				if (tableCell.getType() == TableCell.STRING) {// 如果格单元是文本内容
					TextView textCell = new TextView(context);
					textCell.setLines(1);
					textCell.setGravity(Gravity.CENTER);
					textCell.setBackgroundColor(Color.BLACK);// 背景黑色
					textCell.setText(String.valueOf(tableCell.getValue()));
					addView(textCell, layoutParams);
					textCell.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(getContext(), String.valueOf(tableCell.getValue()) + "", Toast.LENGTH_SHORT).show();
						}
					});
				} else if (tableCell.getType() == TableCell.IMAGE) {// 如果格单元是图像内容
					ImageView imgCell = new ImageView(context);
					imgCell.setBackgroundColor(Color.BLACK);// 背景黑色
					imgCell.setImageResource((Integer) tableCell.getValue());
					addView(imgCell, layoutParams);
				}
			}
			this.setBackgroundColor(Color.WHITE);// 背景白色，利用空隙来实现边框
		}
	}

}