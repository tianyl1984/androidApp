package com.tyl.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tyl.commom.AlertDialogUtil;
import com.tyl.commom.JsonUtil;
import com.tyl.model.Student;
import com.tyl.model.User;

public class JsonActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button button = new Button(this);
		button.setText("测试");
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				User aa = JsonUtil.json2JavaPojo("{\"email\":\"zhangsan@126.com\",\"age\":\"12\",\"id\":\"id123\",\"name\":\"张三\",\"teacher\":{\"id\":\"t1\",\"name\":\"t1Name\",\"students\":[{\"address\":{\"name\":\"s1NameAddress\"},\"id\":\"s1\",\"name\":\"s1Name\"},{\"address\":{\"name\":\"s2NameAddress\"},\"id\":\"s2\",\"name\":\"s2Name\"},{\"address\":{\"name\":\"s3NameAddress\"},\"id\":\"s3\",\"name\":\"s3Name\"},{\"address\":{\"name\":\"s4NameAddress\"},\"id\":\"s4\",\"name\":\"s4Name\"},{\"address\":{\"name\":\"s5NameAddress\"},\"id\":\"s5\",\"name\":\"s5Name\"},{\"address\":{\"name\":\"s6NameAddress\"},\"id\":\"s6\",\"name\":\"s6Name\"}]}}", User.class);
				AlertDialogUtil.showMessage(aa.toString(), "User", JsonActivity.this);
				AlertDialogUtil.showMessage(aa.getTeacher().toString(), "Teacher", JsonActivity.this);
				if (aa.getTeacher().getStudents() != null && aa.getTeacher().getStudents().size() > 0) {
					AlertDialogUtil.showMessage(aa.getTeacher().getStudents().get(0).toString(), "Student", JsonActivity.this);
				}
				List<Student> stus = JsonUtil.json2List("[{\"address\":{\"name\":\"s1NameAddress\"},\"id\":\"s1\",\"name\":\"s1Name\"},{\"address\":{\"name\":\"s2NameAddress\"},\"id\":\"s2\",\"name\":\"s2Name\"},{\"address\":{\"name\":\"s3NameAddress\"},\"id\":\"s3\",\"name\":\"s3Name\"},{\"address\":{\"name\":\"s4NameAddress\"},\"id\":\"s4\",\"name\":\"s4Name\"},{\"address\":{\"name\":\"s5NameAddress\"},\"id\":\"s5\",\"name\":\"s5Name\"},{\"address\":{\"name\":\"s6NameAddress\"},\"id\":\"s6\",\"name\":\"s6Name\"}]", Student.class);
				if (stus.size() > 0) {
					AlertDialogUtil.showMessage(stus.get(0).toString(), "Student2", JsonActivity.this);
				}
			}
		});
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		addContentView(button, params);
	}
}
