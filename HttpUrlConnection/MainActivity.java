package com.example.httpurlconnection;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

//HttpUrlConnection
//检查 network 选择一项 view resource
//http请求头：请求行：method,url,http版本
//          消息报头：主机地址host，浏览器引擎User-agent,接收参数类型accept，请求语言language，压缩编码类型encoding 长短连接connection
//          请求正文：

//http响应头：状态行：http版本，响应码200 404
//          响应报头：编码，长度，日期
//          响应正文：

//上传文件所需的http请求信息：Connect-Type：表单键  + boundary:分界符，也就是开始符
//                       开始符：-- + "任意字符串"              +  换行
//                       文件说明：请求参数，文件名，文件类型……     +  换行
//                       文件内容：字节流                       +  换行
//                       结束符：开始符 + 两个连字符--           +  换行

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";

    Button btnc;
    Button btns;
    private String upload1 = "http://www.maizitime.com:8081/upload_test";
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (requestReadExternalPermission() == true){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            init();
        }
    }

    void init() {
        btnc = (Button) findViewById(R.id.btn1);
        btns = (Button) findViewById(R.id.btn2);
        btnc.setOnClickListener(new ButtonClickListener());
        btns.setOnClickListener(new ButtonClickListener());
    }

    class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn1:
                    chooseFile();
                    break;
                case R.id.btn2:
                    UploadUtil.uploadFile(new File(filePath),upload1);
                    break;
                default:
                    break;
            }
        }
    }

    //Caller
    public void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);      //
        intent.setType("*/*");  //不做内容筛选
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int result = activityResult.getResultCode();
                            Intent data = activityResult.getData();
                            if (result == RESULT_OK) {
                                Uri uri = data.getData();
                                filePath = uri.getPath();
                                Toast.makeText(getApplicationContext(), filePath, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    );

    @SuppressLint("NewApi")
    private boolean requestReadExternalPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "READ permission IS NOT granted...");
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            return false;
        } else {
            Log.d(TAG, "READ permission is granted...");
            return true;
        }
    }
}