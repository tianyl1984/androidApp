package com.tyl.ioc.afinal;

import java.util.List;

import net.tsz.afinal.FinaActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.ajax.AjaxCallBack;
import net.tsz.afinal.ajax.AjaxStatus;
import net.tsz.afinal.annotation.view.ViewInject;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tyl.R;
import com.tyl.commom.StringUtil;

public class AfinalDemo extends FinaActivity {

	FinalDb db;

	@ViewInject(id = R.id.saveBtn, click = "save")
	Button saveBtn;
	@ViewInject(id = R.id.readBtn, click = "list")
	Button readBtn;
	@ViewInject(id = R.id.delBtn, click = "del")
	Button delBtn;
	@ViewInject(id = R.id.netBtn, click = "ajax")
	Button netBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.afinal_demo);
		db = FinalDb.creat(this, Environment.getExternalStorageDirectory().getPath() + "/foo.db");
	}

	public void save(View v) {
		Foo foo = new Foo();
		foo.setName(StringUtil.getRandomStr());
		foo.setEmail(StringUtil.getRandomStr());
		Gee gee = new Gee();
		gee.setName(StringUtil.getRandomStr());
		db.save(gee);
		foo.setGee(gee);
		db.save(foo);
		Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
	}

	public void list(View v) {
		List<Foo> foos = db.findAll(Foo.class);
		for (Foo f : foos) {
			Toast.makeText(this, f.toString(), Toast.LENGTH_SHORT).show();
		}
		Toast.makeText(this, "完成读取", Toast.LENGTH_SHORT).show();
	}

	public void del(View v) {
		db.deleteByWhere(Foo.class);
		Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
	}

	public void ajax(View v) {
		FinalHttp.ajax("http://www.baidu.com", new AjaxCallBack() {
			@Override
			public void callBack(AjaxStatus status) {

			}
		});
	}
}
