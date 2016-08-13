package com.sunsoft.zyebiz.b2e.activity;
/**
 * 功能：仿IOS左右滑动功能
 * @author YinGuiChun
 */
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import com.sunsoft.zyebiz.b2e.R;

public class SetMessageActivity extends Activity implements OnClickListener {
	// 消息推送设置界面
	private View messageView;
	// 返回键
	private ImageView backImageButton;
	// 标题
	private TextView titleTv;
	// 是否接受推送消息
	private RelativeLayout isReceiveMesage;
	// 是否开启接受推送的声音
	private RelativeLayout isReceiveMesageSound;
	// 推送消息是否开启的标记，默认是开启
	private String isReceiveMessageFlag;
	// 接受消息推送的声音，默认是开启
	private String isReceiveMessageSoundFlag;
	private ImageView setMessageOn;
	private ImageView setMessageOff;
	private ImageView setMessageSoundOn;
	private ImageView setMessageSoundOff;
	private SharedPreferences sp;
	private Editor editor;
	private TextView titleTvBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_set_message);
		backImageButton = (ImageView) findViewById(R.id.img_title_back);
		titleTvBack = (TextView) findViewById(R.id.tv_title_back);
		titleTv = (TextView) findViewById(R.id.title_main);
		isReceiveMesage = (RelativeLayout) findViewById(R.id.set_message_isOpen_rl);
		isReceiveMesageSound = (RelativeLayout) findViewById(R.id.set_message_sound_isOpen_rl);
		setMessageOn = (ImageView) findViewById(R.id.set_message_iv_on);
		setMessageOff = (ImageView) findViewById(R.id.set_message_iv_off);
		setMessageSoundOn = (ImageView) findViewById(R.id.set_message_sound_tv_on);
		setMessageSoundOff = (ImageView) findViewById(R.id.set_message_sound_tv_off);
		titleTv.setText("消息推送设置");
		isReceiveMesage.setOnClickListener(this);
		isReceiveMesageSound.setOnClickListener(this);
		backImageButton.setClickable(true);
		backImageButton.setOnClickListener(this);
		titleTvBack.setOnClickListener(this);
		sp = getSharedPreferences("setMessage", MODE_PRIVATE);

		editor = sp.edit();
		isReceiveMessageFlag = sp.getString("isReceiveMessageFlag", "on")
				.toString();
		isReceiveMessageSoundFlag = sp.getString("isReceiveMessageSoundFlag",
				"on").toString();

		if ("on".equals(isReceiveMessageFlag)) {
			setMessageOn.setVisibility(View.VISIBLE);
			setMessageOff.setVisibility(View.INVISIBLE);
		} else {
			setMessageOn.setVisibility(View.INVISIBLE);
			setMessageOff.setVisibility(View.VISIBLE);
		}

		if ("on".equals(isReceiveMessageSoundFlag)) {
			setMessageSoundOn.setVisibility(View.VISIBLE);
			setMessageSoundOff.setVisibility(View.INVISIBLE);
		} else {
			setMessageSoundOn.setVisibility(View.INVISIBLE);
			setMessageSoundOff.setVisibility(View.VISIBLE);
		}
	}

	public void onClick(View v) {
		int vId = v.getId();
		switch (vId) {
		case R.id.img_title_back:
		case R.id.tv_title_back:
			finish();
			break;
		case R.id.set_message_isOpen_rl:// 是否接受推送消息
			isReceiveMessageFlag = sp.getString("isReceiveMessageFlag", "ok")
					.toString();
			if ("on".equals(isReceiveMessageFlag)) { // 不接受推送的消息
				setMessageOn.setVisibility(View.INVISIBLE);
				setMessageOff.setVisibility(View.VISIBLE);

				setMessageSoundOn.setVisibility(View.INVISIBLE);
				setMessageSoundOff.setVisibility(View.VISIBLE);
				isReceiveMessageFlag = "off";
				isReceiveMessageSoundFlag = "off";
				editor.putString("isReceiveMessageSoundFlag",
						isReceiveMessageFlag);
				editor.commit();
				JPushInterface.stopPush(getApplicationContext()); // 关闭推送的消息

			} else { // 接受推送的消息
				setMessageOn.setVisibility(View.VISIBLE);
				setMessageOff.setVisibility(View.INVISIBLE);
				isReceiveMessageFlag = "on";
				JPushInterface.resumePush(getApplicationContext()); // 接受推送的消息
			}
			editor.putString("isReceiveMessageFlag", isReceiveMessageFlag);
			editor.commit();
			break;
		case R.id.set_message_sound_isOpen_rl:// 是否开启接受的声音
			System.out.println("--------------声音");
			isReceiveMessageSoundFlag = sp.getString(
					"isReceiveMessageSoundFlag", "ok").toString();
			if ("on".equals(isReceiveMessageSoundFlag)) { // 关闭接受的声音
				setMessageSoundOn.setVisibility(View.INVISIBLE);
				setMessageSoundOff.setVisibility(View.VISIBLE);
				isReceiveMessageSoundFlag = "off";
			} else if ("off".equals(isReceiveMessageSoundFlag)) { // 开启接受的声音
				if ("on".equals(isReceiveMessageFlag)) {
					setMessageSoundOn.setVisibility(View.VISIBLE);
					setMessageSoundOff.setVisibility(View.INVISIBLE);
					isReceiveMessageSoundFlag = "on";
				} else if ("off".equals(isReceiveMessageFlag)) {
					Toast.makeText(SetMessageActivity.this,
							getString(R.string.set_sound), Toast.LENGTH_SHORT)
							.show();
				}

			}
			editor.putString("isReceiveMessageSoundFlag",
					isReceiveMessageSoundFlag);
			editor.commit();
			break;

		default:
			break;
		}
	}

}
