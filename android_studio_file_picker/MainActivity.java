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
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.sql.ResultSet;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";

    Button btnc;
    private String filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init() {
        btnc = (Button) findViewById(R.id.btn1);
        btnc.setOnClickListener(new ButtonClickListener());
    }

    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn1:
                    chooseFile();
                    break;
                default:
                    break;
            }
        }
    }

    public void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);      
        intent.setType("*/*");                                      //不做内容筛选
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        //Caller
        activityResultLauncher.launch(intent);
    }
    //Receive
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
}