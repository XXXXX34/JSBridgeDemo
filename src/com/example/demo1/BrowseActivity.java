package com.example.demo1;

import java.io.File;

import com.example.demo1.MainActivity.BridgeModel;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.webkit.WebSettings;

public class BrowseActivity extends Activity {

	BridgeWebView webViewDemo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		clearWebViewCache();
		
		setContentView(R.layout.activity_browse);
		webViewDemo = (BridgeWebView) findViewById(R.id.webViewDemo);
		webViewDemo.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

		// 加载服务器网页
		webViewDemo.loadUrl("http://ohyewang.com/angular/MobileIndex.html?#/");

		webViewDemo.registerHandler("commandFromWeb", new BridgeHandler() {

			@Override
			public void handler(String data, final CallBackFunction function) {

				Gson gson = new Gson();
				BridgeModel bridgeModel = gson
						.fromJson(data, BridgeModel.class);
				if (bridgeModel.succeed) {
					if (bridgeModel.cmd.equals("alert")) {

					}
					if (bridgeModel.cmd.equals("finishActivity")) {
						finish();
					}
				}
			}

		});
	}
	
	/** 
     * 清除WebView缓存 
     */  
    public void clearWebViewCache(){  
          
        //清理Webview缓存数据库  
        try {  
            deleteDatabase("webview.db");   
            deleteDatabase("webviewCache.db");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
          
        //WebView 缓存文件  
       
          
        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()+"/webviewCache");  
        Log.e(TAG, "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());  
          
        //删除webview 缓存目录  
        if(webviewCacheDir.exists()){  
            deleteFile(webviewCacheDir);  
        }  
        
    }  
     String TAG="Info"; 
    /** 
     * 递归删除 文件/文件夹 
     *  
     * @param file 
     */  
    public void deleteFile(File file) {  
  
        Log.i(TAG, "delete file path=" + file.getAbsolutePath());  
          
        if (file.exists()) {  
            if (file.isFile()) {  
                file.delete();  
            } else if (file.isDirectory()) {  
                File files[] = file.listFiles();  
                for (int i = 0; i < files.length; i++) {  
                    deleteFile(files[i]);  
                }  
            }  
            file.delete();  
        } else {  
            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());  
        }  
    }  

}
