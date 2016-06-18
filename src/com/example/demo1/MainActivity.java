package com.example.demo1;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
 

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
 

import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.Button;
 

public class MainActivity extends Activity implements OnClickListener {

	private final String TAG = "MainActivity";

	BridgeWebView webView;

	Button button;

	int RESULT_CODE = 0;

	ValueCallback<Uri> mUploadMessage;

	 
	static class BridgeModel {
		String cmd;
		Boolean succeed;
		String message;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		webView = (BridgeWebView) findViewById(R.id.webView);

		button = (Button) findViewById(R.id.button);

		button.setOnClickListener(this);

		webView.setDefaultHandler(new DefaultHandler());

		webView.setWebChromeClient(new WebChromeClient() {

			@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploadMsg,
					String AcceptType, String capture) {
				this.openFileChooser(uploadMsg);
			}

			@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploadMsg,
					String AcceptType) {
				this.openFileChooser(uploadMsg);
			}

			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
				mUploadMessage = uploadMsg;
				pickFile();
			}
		});
		// 加载本地网页

		// webView.loadUrl("file:///android_asset/demo.html");

		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

		// 加载服务器网页
		webView.loadUrl("http://ohyewang.com/angular/JsbridgeIndex.html");

		// 必须和js同名函数，注册具体执行函数，类似java实现类。
		webView.registerHandler("commandFromWeb", new BridgeHandler() {

			@Override
			public void handler(String data, final CallBackFunction function) {

				Gson gson = new Gson();
				BridgeModel bridgeModel = gson
						.fromJson(data, BridgeModel.class);
				if (bridgeModel.succeed) {
					if (bridgeModel.cmd.equals("alert")) {
						new AlertDialog.Builder(MainActivity.this)
								.setTitle("成功提示")// 设置对话框标题

								.setMessage(bridgeModel.message)// 设置显示的内容

								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												// stub
												BridgeModel bridgeModel = new BridgeModel();
												bridgeModel.cmd = "alert";
												bridgeModel.succeed = true;
												bridgeModel.message = "Java说：网页妹妹，我也想和你结婚，就明天吧？";
												Gson gson = new Gson();
												String brideJson = gson
														.toJson(bridgeModel);
												function.onCallBack(brideJson);

											}// 添加确定按钮

										}).show();

					}
					if ( bridgeModel.cmd.equals("openDemo")) {

						Intent intent = new Intent(MainActivity.this,
								BrowseActivity.class);
						startActivity(intent);
					}

				} else {

				}

			}

		});

	}

	public void pickFile() {
		Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
		chooserIntent.setType("image/*");
		startActivityForResult(chooserIntent, RESULT_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == RESULT_CODE) {
			if (null == mUploadMessage) {
				return;
			}
			Uri result = intent == null || resultCode != RESULT_OK ? null
					: intent.getData();
			mUploadMessage.onReceiveValue(result);
			mUploadMessage = null;
		}
	}

	@Override
	public void onClick(View v) {
		if (button.equals(v)) {
			BridgeModel brideModel = new BridgeModel();
			brideModel.cmd = "alert";
			brideModel.succeed = true;
			brideModel.message = "Java说：网页妹妹，我喜欢你";
			Gson gson = new Gson();
			String brideJson = gson.toJson(brideModel);

			webView.callHandler("commandFromJava", brideJson,
					new CallBackFunction() {

						@Override
						public void onCallBack(String data) {
							// TODO Auto-generated method stub
							Gson gson = new Gson();
							BridgeModel bridgeModel = gson.fromJson(data,
									BridgeModel.class);
							if (bridgeModel.succeed) {
								new AlertDialog.Builder(MainActivity.this)
										.setTitle("成功提示")// 设置对话框标题

										.setMessage(bridgeModel.message)// 设置显示的内容

										.setPositiveButton(
												"确定",
												new DialogInterface.OnClickListener() {

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
														// TODO Auto-generated
														// method stub

													}// 添加确定按钮

												}).show();

							} else {
								new AlertDialog.Builder(MainActivity.this)
										.setTitle("失败提示")// 设置对话框标题

										.setMessage(bridgeModel.message)// 设置显示的内容

										.setPositiveButton(
												"确定",
												new DialogInterface.OnClickListener() {

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
														// TODO Auto-generated
														// method stub

													}// 添加确定按钮

												}).show();
							}

						}

					});
		}

	}
}
