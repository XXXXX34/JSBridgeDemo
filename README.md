﻿# JSBridgeDemo
使用WebView，Android(JsBridge) ，IOS（WebViewJavascriptBridge）开发同时开发Android和IOS版本App应用

<h1>Html/Javascript代码</h1>
 
<pre>
 


    function marryClick() {
        var bridgeModel = {};
        bridgeModel.cmd = "alert";
        bridgeModel.succeed = true;
        bridgeModel.message = "网页说：Java哥哥，我想和你结婚";
        var responseData = JSON.stringify(bridgeModel);
        triggerJavaMethod(responseData);

    }

    function demoClick() {
        var bridgeModel = {};
        bridgeModel.cmd = "openDemo";
        bridgeModel.succeed = true;
        bridgeModel.message = "";
        var responseData = JSON.stringify(bridgeModel);

        triggerJavaMethod(responseData);
    }

    function triggerJavaMethod(responseData) {
        //调用本地java方法
        window.WebViewJavascriptBridge.callHandler(
            'commandFromWeb'
            , responseData
            , function (responseData) {

                var bridgeModel = $.parseJSON(responseData);

                if (bridgeModel.succeed) {
                    alert(bridgeModel.message);
                }
                else {

                }
            }
        );
    }


    //注册事件监听
    function connectWebViewJavascriptBridge(callback) {
        if (window.WebViewJavascriptBridge) {
            callback(WebViewJavascriptBridge)
        } else {
            document.addEventListener(
                'WebViewJavascriptBridgeReady'
                , function () {
                    callback(WebViewJavascriptBridge)
                },
                false
            );
        }
    }
    //注册回调函数，初始化函数
    connectWebViewJavascriptBridge(function (bridge) {
        bridge.init(function (message, responseCallback) {
            //console.log('JS got a message', message);
            //var data = {
            //    'Javascript Responds': 'Wee!'
            //};
            //console.log('JS responding with', data);
            //responseCallback(data);
        });

        bridge.registerHandler("commandFromJava", function (data, responseCallback) {

            var bridgeModel = $.parseJSON(data);
            alert(bridgeModel.message);

            var bridgeModel = {};
            bridgeModel.cmd = "alert";
            bridgeModel.succeed = true;
            bridgeModel.message = "网页说：Java哥哥，我也喜欢你";

            var responseData = JSON.stringify(bridgeModel);
            responseCallback(responseData);
        });
    })
 
 </pre>
 


 <h1>安装Demo</h1>
    <p>
        <img src="http://7xp8v4.com1.z0.glb.clouddn.com/DoloadJSBridgeDemo2.png" />
    </p>
    
  <h1>首页</h1>
        <p>
            <img src="http://7xp8v4.com1.z0.glb.clouddn.com/JSBridgeDemo1.png" />
        </p>
          <h1>登录页面</h1>
        <p>
            <img src="http://7xp8v4.com1.z0.glb.clouddn.com/JSBridgeDemo2.png" />
        </p>
          <h1>列表页面</h1>
         <p>
            <img src="http://7xp8v4.com1.z0.glb.clouddn.com/JSBridgeDemo3.png" />
        </p>
          <h1>JS调用Java退出Activity</h1>
         <p>
            <img src="http://7xp8v4.com1.z0.glb.clouddn.com/JSBridgeDemo4.png" />
 </p>

