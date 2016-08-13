package com.sunsoft.zyebiz.b2e.activity;

/**
 * 学生信息查看页面
 * @author YinGuiChun
 */
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.sunsoft.zyebiz.b2e.R;
import com.google.gson.Gson;
import com.sunsoft.zyebiz.b2e.adapter.MessageAdapter;
import com.sunsoft.zyebiz.b2e.common.Constants;
import com.sunsoft.zyebiz.b2e.common.URLInterface;
import com.sunsoft.zyebiz.b2e.http.AsyncHttpUtil;
import com.sunsoft.zyebiz.b2e.http.asynchttp.AsyncHttpResponseHandler;
import com.sunsoft.zyebiz.b2e.http.asynchttp.RequestParams;
import com.sunsoft.zyebiz.b2e.model.Student.ParentResult;
import com.sunsoft.zyebiz.b2e.model.Student.StudentBean;
import com.sunsoft.zyebiz.b2e.model.Student.StudentResult;
import com.sunsoft.zyebiz.b2e.util.EmptyUtil;
import com.sunsoft.zyebiz.b2e.wiget.SharedPreference;
import com.umeng.analytics.MobclickAgent;

public class UserStudentMessageActivity extends Activity implements
		OnClickListener {

	private TextView tvMainText, tvTitleBack;
	private ImageView imgTitleBack;
	private ListView listview;
	private MessageAdapter listAdapter;
	private List<ParentResult> list;
	private TextView stName, stClass, stSex, stXueJi, schName, parentUserName,
			parentName, parentPhone;
	private LinearLayout studentMessage, parentMessage;
	private String classNo, gradeNo;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_message);

		initView();
		initDate();

	}

	/**
	 * 友盟统计
	 */
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/**
	 * 初始化数据
	 */
	private void initDate() {
		System.out.println("SharedPreference.strUserType:"
				+ SharedPreference.strUserType + "SharedPreference.strUserName"
				+ SharedPreference.strUserName);

		if (SharedPreference.strUserType.equals(Constants.LOGIN_PARENT)) {
			studentMessage.setVisibility(View.GONE);
			parentMessage.setVisibility(View.VISIBLE);
			tvMainText
					.setText(getString(R.string.student_message_parent_message));
			parentUserName.setText(SharedPreference.strUserName);
			parentName.setText(SharedPreference.strUserRealName);
			parentPhone.setText(SharedPreference.strUserPhone);

		}
		if (SharedPreference.strUserType.equals(Constants.LOGIN_STUDENT)) {
			studentMessage.setVisibility(View.VISIBLE);
			parentMessage.setVisibility(View.GONE);
			tvMainText
					.setText(getString(R.string.student_message_student_message));
			stXueJi.setText(SharedPreference.strUserName);
			schName.setText(SharedPreference.strSchoolName);

			stName.setText(SharedPreference.strUserRealName);
			if (SharedPreference.strUserSex
					.equals(Constants.CONSTANT_STRING_TWO)) {
				stSex.setText(getString(R.string.student_message_sex_nv));
			} else if (SharedPreference.strUserSex
					.equals(Constants.CONSTANT_STRING_ONE)) {
				stSex.setText(getString(R.string.student_message_sex_nan));
			} else {
				stSex.setText(getString(R.string.student_message_sex_weizhi));
			}
			list = new ArrayList<ParentResult>();
			listAdapter = new MessageAdapter(this, list);
			listview.setAdapter(listAdapter);
			receiveStudentMessage();
			if (EmptyUtil.isNotEmpty(classNo) && EmptyUtil.isNotEmpty(gradeNo)) {
				stClass.setText(gradeNo + classNo);
			}

		}

	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		tvMainText = (TextView) findViewById(R.id.title_main);
		tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
		imgTitleBack = (ImageView) findViewById(R.id.img_title_back);
		listview = (ListView) findViewById(R.id.list_parent_message);
		stXueJi = (TextView) findViewById(R.id.tv_stu_xueji);
		stName = (TextView) findViewById(R.id.tv_stu_name);
		stSex = (TextView) findViewById(R.id.tv_stu_sex);
		schName = (TextView) findViewById(R.id.tv_sch_name);
		stClass = (TextView) findViewById(R.id.tv_class_message);
		studentMessage = (LinearLayout) findViewById(R.id.student_message);
		parentMessage = (LinearLayout) findViewById(R.id.parent_message);
		parentUserName = (TextView) findViewById(R.id.parent_username);
		parentName = (TextView) findViewById(R.id.parent_name);
		parentPhone = (TextView) findViewById(R.id.parent_phone);
		imgTitleBack.setOnClickListener(this);
		tvTitleBack.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_title_back:
		case R.id.img_title_back:
			this.finish();
			break;

		default:
			break;
		}

	}

	/**
	 * 获取学生信息
	 */
	private void receiveStudentMessage() {
		RequestParams params = new RequestParams();
		params.put("token", SharedPreference.strUserToken);

		AsyncHttpUtil.post(URLInterface.PARENT_LOGIN_GET_USER_INFO, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						Gson gson = new Gson();
						if (statusCode == Constants.CONSTANT_TWO_HUNDRED) {
							String resultDate = new String(responseBody);
							System.out.println("resultDate:" + resultDate);
							StudentBean regist = gson.fromJson(resultDate,
									StudentBean.class);
							String title = regist.getTitle();
							if (EmptyUtil.isNotEmpty(title)) {
								if (title
										.equals(Constants.CONSTANT_STRING_ZERO)) {
									StudentResult studentBean = regist
											.getBody();
									classNo = studentBean.getClassNo();
									gradeNo = studentBean.getGradeNo();
									// if(EmptyUtil.isNotEmpty(studentBean.getUserName())){
									// stXueJi.setText(studentBean.getUserName());
									// }
									stClass.setText(gradeNo + classNo);
									if (EmptyUtil.isNotEmpty(studentBean)) {
										list = new ArrayList<ParentResult>();
										list = studentBean.getList();
										listAdapter = new MessageAdapter(
												UserStudentMessageActivity.this,
												list);
										listview.setAdapter(listAdapter);
									}
								}
							}

						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {

					}
				});
	}

}
