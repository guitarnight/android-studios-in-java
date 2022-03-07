package com.example.arrayadapter;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//加载布局文件

        //1. 获取ListView对象
        ListView listview = (ListView) findViewById(R.id.lv_main);

        //2. 准备数据源
        String[] data = {
                "初识Android",
                "开发环境搭建",
                "基础控件I",
                "基础控件II",
                "线性布局",
                "相对布局"};

        //3. 准备适配器Adapter
        ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(
                this,                           //context 上下文
                android.R.layout.simple_list_item_1,    //系统自带的简单行布局
                data);                                  // 数据源

        //4. 将适配器关联到ListView
        listview.setAdapter(adapter);

    }



}
