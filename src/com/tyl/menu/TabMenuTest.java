package com.tyl.menu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.tyl.R;

public class TabMenuTest extends Activity {
	TabMenu.MenuBodyAdapter[] bodyAdapter = new TabMenu.MenuBodyAdapter[3];
	TabMenu.MenuTitleAdapter titleAdapter;
	TabMenu tabMenu;
	int selTitle = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_menu_test);
		// 设置分页栏的标题
		titleAdapter = new TabMenu.MenuTitleAdapter(this, new String[] { "常用", "设置", "工具" }, 16, 0xFF222222, Color.LTGRAY, Color.WHITE);
		// 定义每项分页栏的内容
		bodyAdapter[0] = new TabMenu.MenuBodyAdapter(this, new String[] { "常用1", "常用2", }, new int[] { R.drawable.png1, R.drawable.png2 }, 13, 0xFFFFFFFF);

		bodyAdapter[1] = new TabMenu.MenuBodyAdapter(this, new String[] { "设置1", "设置2", "设置3" }, new int[] { R.drawable.png3, R.drawable.png4, R.drawable.png5 }, 13, 0xFFFFFFFF);

		bodyAdapter[2] = new TabMenu.MenuBodyAdapter(this, new String[] { "工具1", "工具2", "工具3", "工具4" }, new int[] { R.drawable.png6, R.drawable.png1, R.drawable.png2, R.drawable.png3 }, 13, 0xFFFFFFFF);

		tabMenu = new TabMenu(this, new TitleClickEvent(), new BodyClickEvent(), titleAdapter, 0x55123456,// TabMenu的背景颜色
		R.style.PopupAnimation);// 出现与消失的动画

		tabMenu.update();
		tabMenu.setTitleSelect(0);
		tabMenu.setBodyAdapter(bodyAdapter[0]);
	}

	class TitleClickEvent implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			selTitle = arg2;
			tabMenu.setTitleSelect(arg2);
			tabMenu.setBodyAdapter(bodyAdapter[arg2]);
		}
	}

	class BodyClickEvent implements AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			tabMenu.setBodySelect(arg2, Color.GRAY);
			String str = "第" + String.valueOf(selTitle) + "栏\n\r" + "第" + String.valueOf(arg2) + "项";
			Toast.makeText(TabMenuTest.this, str, 500).show();
		}
	}

	/**
	 * 创建MENU
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");// 必须创建一项
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * 拦截MENU
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (tabMenu != null) {
			if (tabMenu.isShowing()) {
				tabMenu.dismiss();
			} else {
				tabMenu.showAtLocation(findViewById(R.id.LinearLayout01), Gravity.BOTTOM, 0, 0);
			}
		}
		return false;// 返回为true 则显示系统menu
	}
}

class TabMenu extends PopupWindow {
	private GridView gvBody, gvTitle;
	private LinearLayout mLayout;
	private MenuTitleAdapter titleAdapter;

	public TabMenu(Context context, OnItemClickListener titleClick, OnItemClickListener bodyClick, MenuTitleAdapter titleAdapter, int colorBgTabMenu, int aniTabMenu) {
		super(context);

		mLayout = new LinearLayout(context);
		mLayout.setOrientation(LinearLayout.VERTICAL);
		// 标题选项栏
		gvTitle = new GridView(context);
		gvTitle.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		gvTitle.setNumColumns(titleAdapter.getCount());
		gvTitle.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gvTitle.setVerticalSpacing(1);
		gvTitle.setHorizontalSpacing(1);
		gvTitle.setGravity(Gravity.CENTER);
		gvTitle.setOnItemClickListener(titleClick);
		gvTitle.setAdapter(titleAdapter);
		gvTitle.setSelector(new ColorDrawable(Color.TRANSPARENT));// 选中的时候为透明色
		this.titleAdapter = titleAdapter;
		// 子选项栏
		gvBody = new GridView(context);
		gvBody.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		gvBody.setSelector(new ColorDrawable(Color.TRANSPARENT));// 选中的时候为透明色
		gvBody.setNumColumns(4);
		gvBody.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gvBody.setVerticalSpacing(10);
		gvBody.setHorizontalSpacing(10);
		gvBody.setPadding(10, 10, 10, 10);
		gvBody.setGravity(Gravity.CENTER);
		gvBody.setOnItemClickListener(bodyClick);
		mLayout.addView(gvTitle);
		mLayout.addView(gvBody);

		// 设置默认项
		this.setContentView(mLayout);
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setBackgroundDrawable(new ColorDrawable(colorBgTabMenu));// 设置TabMenu菜单背景
		this.setAnimationStyle(aniTabMenu);
		this.setFocusable(true);// menu菜单获得焦点 如果没有获得焦点menu菜单中的控件事件无法响应
	}

	public void setTitleSelect(int index) {
		gvTitle.setSelection(index);
		this.titleAdapter.SetFocus(index);
	}

	public void setBodySelect(int index, int colorSelBody) {
		int count = gvBody.getChildCount();
		for (int i = 0; i < count; i++) {
			if (i != index) {
				((LinearLayout) gvBody.getChildAt(i)).setBackgroundColor(Color.TRANSPARENT);
			}
		}
		((LinearLayout) gvBody.getChildAt(index)).setBackgroundColor(colorSelBody);
	}

	public void setBodyAdapter(MenuBodyAdapter bodyAdapter) {
		gvBody.setAdapter(bodyAdapter);
	}

	/**
	 * 自定义Adapter，TabMenu的每个分页的主体
	 * 
	 */
	static public class MenuBodyAdapter extends BaseAdapter {
		private Context mContext;
		private int fontColor, fontSize;
		private String[] texts;
		private int[] resID;

		/**
		 * 设置TabMenu的分页主体
		 * 
		 * @param context
		 *            调用方的上下文
		 * @param texts
		 *            按钮集合的字符串数组
		 * @param resID
		 *            按钮集合的图标资源数组
		 * @param fontSize
		 *            按钮字体大小
		 * @param color
		 *            按钮字体颜色
		 */
		public MenuBodyAdapter(Context context, String[] texts, int[] resID, int fontSize, int fontColor) {
			this.mContext = context;
			this.fontColor = fontColor;
			this.texts = texts;
			this.fontSize = fontSize;
			this.resID = resID;
		}

		public int getCount() {
			return texts.length;
		}

		public Object getItem(int position) {
			return makeMenuBody(position);
		}

		public long getItemId(int position) {
			return position;
		}

		private LinearLayout makeMenuBody(int position) {
			LinearLayout result = new LinearLayout(this.mContext);
			result.setOrientation(LinearLayout.VERTICAL);
			result.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
			result.setPadding(10, 10, 10, 10);

			TextView text = new TextView(this.mContext);
			text.setText(texts[position]);
			text.setTextSize(fontSize);
			text.setTextColor(fontColor);
			text.setGravity(Gravity.CENTER);
			text.setPadding(5, 5, 5, 5);
			ImageView img = new ImageView(this.mContext);
			img.setBackgroundResource(resID[position]);
			result.addView(img, new LinearLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)));
			result.addView(text);
			return result;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			return makeMenuBody(position);
		}
	}

	/**
	 * 自定义Adapter,TabMenu的分页标签部分
	 * 
	 */
	static public class MenuTitleAdapter extends BaseAdapter {
		private Context mContext;
		private int fontColor, unselcolor, selcolor;
		private TextView[] title;

		/**
		 * 设置TabMenu的title
		 * 
		 * @param context
		 *            调用方的上下文
		 * @param titles
		 *            分页标签的字符串数组
		 * @param fontSize
		 *            字体大小
		 * @param fontcolor
		 *            字体颜色
		 * @param unselcolor
		 *            未选中项的背景色
		 * @param selcolor
		 *            选中项的背景色
		 */
		public MenuTitleAdapter(Context context, String[] titles, int fontSize, int fontcolor, int unselcolor, int selcolor) {
			this.mContext = context;
			this.fontColor = fontcolor;
			this.unselcolor = unselcolor;
			this.selcolor = selcolor;
			this.title = new TextView[titles.length];
			for (int i = 0; i < titles.length; i++) {
				title[i] = new TextView(mContext);
				title[i].setText(titles[i]);
				title[i].setTextSize(fontSize);
				title[i].setTextColor(fontColor);
				title[i].setGravity(Gravity.CENTER);
				title[i].setPadding(10, 10, 10, 10);
			}
		}

		public int getCount() {
			return title.length;
		}

		public Object getItem(int position) {
			return title[position];
		}

		public long getItemId(int position) {
			return title[position].getId();
		}

		/**
		 * 设置选中的效果
		 */
		private void SetFocus(int index) {
			for (int i = 0; i < title.length; i++) {
				if (i != index) {
					title[i].setBackgroundDrawable(new ColorDrawable(unselcolor));// 设置没选中的颜色
					title[i].setTextColor(fontColor);// 设置没选中项的字体颜色
				}
			}
			title[index].setBackgroundColor(0x00);// 设置选中项的颜色
			title[index].setTextColor(selcolor);// 设置选中项的字体颜色
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View v;
			if (convertView == null) {
				v = title[position];
			} else {
				v = convertView;
			}
			return v;
		}
	}
}