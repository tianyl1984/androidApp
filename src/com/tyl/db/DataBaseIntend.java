package com.tyl.db;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tyl.R;

public class DataBaseIntend extends Activity {

	EditText nameText, authorText;

	Button addBtn, editBtn, delBtn;

	ListView bookList;

	BookDB bookDb;

	int bookId;

	Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.database);

		bookDb = new BookDB(this);
		cursor = bookDb.select();

		nameText = (EditText) findViewById(R.id.nameText);
		authorText = (EditText) findViewById(R.id.authorText);

		addBtn = (Button) findViewById(R.id.addBtn);
		editBtn = (Button) findViewById(R.id.editBtn);
		delBtn = (Button) findViewById(R.id.delBtn);

		bookList = (ListView) findViewById(R.id.bookList);

		bookList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				cursor.moveToPosition(position);
				bookId = cursor.getInt(0);
				nameText.setText(cursor.getString(1));
				authorText.setText(cursor.getString(2));
				editBtn.setEnabled(true);
				delBtn.setEnabled(true);
				addBtn.setEnabled(false);
				return true;
			}

		});

		bookList.setAdapter(new BooksListAdapter(this, cursor));

		addBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (nameText.getText().toString().trim().equals("") || authorText.getText().toString().trim().equals("")) {
					Toast.makeText(DataBaseIntend.this, "名称和作者不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
				bookDb.insert(nameText.getText().toString().trim(), authorText.getText().toString().trim());
				nameText.setText("");
				authorText.setText("");
				addBtn.setEnabled(true);
				editBtn.setEnabled(false);
				delBtn.setEnabled(false);

				cursor.requery();
				bookList.invalidateViews();

				Toast.makeText(DataBaseIntend.this, "添加成功！", Toast.LENGTH_SHORT).show();
			}
		});

		findViewById(R.id.addBatchBtn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				for (int i = 0; i < 10; i++) {
					bookDb.insert("name" + i, "author" + i);
				}
				cursor.requery();
				bookList.invalidateViews();
				Toast.makeText(DataBaseIntend.this, "添加成功！", Toast.LENGTH_SHORT).show();
			}
		});

		editBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (nameText.getText().toString().trim().equals("") || authorText.getText().toString().trim().equals("")) {
					Toast.makeText(DataBaseIntend.this, "名称和作者不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
				bookDb.update(nameText.getText().toString(), authorText.getText().toString(), bookId);

				nameText.setText("");
				authorText.setText("");

				editBtn.setEnabled(false);
				delBtn.setEnabled(false);
				addBtn.setEnabled(true);

				cursor.requery();
				bookList.invalidateViews();

				Toast.makeText(DataBaseIntend.this, "修改成功！", Toast.LENGTH_SHORT).show();
			}
		});

		delBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				bookDb.delete(bookId);

				nameText.setText("");
				authorText.setText("");

				editBtn.setEnabled(false);
				delBtn.setEnabled(false);
				addBtn.setEnabled(true);

				cursor.requery();
				bookList.invalidateViews();

				Toast.makeText(DataBaseIntend.this, "删除成功！", Toast.LENGTH_SHORT).show();
			}
		});

		findViewById(R.id.delAllBtn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cursor.moveToFirst()) {
					do {
						bookDb.delete(Long.valueOf(cursor.getLong(0)).intValue());
					} while (cursor.moveToNext());
				}
				cursor.requery();
				bookList.invalidateViews();
				Toast.makeText(DataBaseIntend.this, "完成清空！", Toast.LENGTH_SHORT).show();
			}
		});

		findViewById(R.id.contentProviderBtn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ContentResolver cr = DataBaseIntend.this.getContentResolver();
				final Cursor c = cr.query(BookContentProvider.CONTENT_URI, null, null, null, null);

				// if (1 == 1) {
				// String aa = "" + c.getCount();
				//
				// Toast.makeText(DataBaseIntend.this, aa,
				// Toast.LENGTH_SHORT).show();
				// c.close();
				// return;
				// }

				AlertDialog.Builder builder = new AlertDialog.Builder(DataBaseIntend.this);
				builder.setCursor(c, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						c.close();
					}
				}, BookContentProvider.BOOK_NAME);
				builder.setTitle("书籍");
				AlertDialog ad = builder.create();
				ad.setButton(AlertDialog.BUTTON_NEGATIVE, "关闭", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						c.close();
					}
				});
				ad.show();
				// c.close();
			}
		});

	}

	public class BooksListAdapter extends BaseAdapter {

		private Context ctx;

		private Cursor cursor;

		public BooksListAdapter(Context ctx, Cursor cursor) {
			this.ctx = ctx;
			this.cursor = cursor;
		}

		public int getCount() {
			return cursor.getCount();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater li = LayoutInflater.from(ctx);
				convertView = li.inflate(R.layout.bookitem, null);
			}
			cursor.moveToPosition(position);
			((TextView) convertView.findViewById(R.id.nameTextView)).setText("名称：" + cursor.getString(1));
			((TextView) convertView.findViewById(R.id.authorTextView)).setText("作者：" + cursor.getString(2));
			return convertView;
		}
	}
}
