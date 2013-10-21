package com.tyl.orm;

import java.util.List;

import org.orman.mapper.Model;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tyl.R;
import com.tyl.commom.StringUtil;

public class OrmanTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orman_demo);
		findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FooOrman foo = new FooOrman();
				foo.setEmail(StringUtil.getRandomStr());
				foo.setName(StringUtil.getRandomStr());
				ZooOrman zooOrman = new ZooOrman();
				zooOrman.setName(StringUtil.getRandomStr());
				zooOrman.insert();
				foo.setZooOrman(zooOrman);
				foo.insert();
				Toast.makeText(getApplicationContext(), "完成", Toast.LENGTH_SHORT).show();
			}
		});
		findViewById(R.id.readBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				List<FooOrman> foos = Model.fetchAll(FooOrman.class);
				for (FooOrman foo : foos) {
					Toast.makeText(getApplicationContext(), foo.getName() + ":" + foo.getEmail() + ":" + (foo.getZooOrman() != null ? foo.getZooOrman().getName() : "null"), Toast.LENGTH_SHORT).show();
				}
				Toast.makeText(getApplicationContext(), "完成", Toast.LENGTH_SHORT).show();
			}
		});
		findViewById(R.id.delBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				List<FooOrman> foos = Model.fetchAll(FooOrman.class);
				for (FooOrman foo : foos) {
					foo.delete();
				}
				Toast.makeText(getApplicationContext(), "完成", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
