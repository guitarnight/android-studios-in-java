package com.example.simpleadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //override表示重写所继承的父类方法，这个注释可有可无
    @Override
    //onCreate方法底层调用main方法
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);              //将界面前端xml布局  与  后端java程序  关联

        //Simpleadapter 简易资源适配器4步配置在onCreate()方法中实现

        //1，拿到listview控件，不是整个xml布局
        ListView listView = findViewById(R.id.lv_main);

        //2. 准备数据源，数据结构都需要导包
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String, Object>();

        map.put("logo", R.drawable.ic_10);          //键是查找值的索引类是String，值是R.的资源类是Object
        map.put("title", "千千静听");
        map.put("version", "版本: 8.4.0");
        map.put("size", "大小: 32.81M");
        list.add(map);                              //一个map中有很多对 键值对， 作为一个item所需节点插入list

        map = new HashMap<String, Object>();
        map.put("logo", R.drawable.ic_2);
        map.put("title", "时空猎人");
        map.put("version", "版本: 2.4.1");
        map.put("size", "大小: 84.24M");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("logo", R.drawable.ic_4);
        map.put("title", "360新闻");
        map.put("version", "版本: 6.2.0");
        map.put("size", "大小: 11.74M");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("logo", R.drawable.ic_15);
        map.put("title", "捕鱼达人2");
        map.put("version", "版本: 2.3.0");
        map.put("size", "大小: 45.53M");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("logo", R.drawable.ic_2);
        map.put("title", "时空猎人");
        map.put("version", "版本: 2.4.1");
        map.put("size", "大小: 84.24M");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("logo", R.drawable.ic_4);
        map.put("title", "360新闻");
        map.put("version", "版本: 6.2.0");
        map.put("size", "大小: 11.74M");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("logo", R.drawable.ic_15);
        map.put("title", "捕鱼达人2");
        map.put("version", "版本: 2.3.0");
        map.put("size", "大小: 45.53M");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("logo", R.drawable.ic_2);
        map.put("title", "时空猎人");
        map.put("version", "版本: 2.4.1");
        map.put("size", "大小: 84.24M");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("logo", R.drawable.ic_4);
        map.put("title", "360新闻");
        map.put("version", "版本: 6.2.0");
        map.put("size", "大小: 11.74M");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("logo", R.drawable.ic_15);
        map.put("title", "捕鱼达人2");
        map.put("version", "版本: 2.3.0");
        map.put("size", "大小: 45.53M");
        list.add(map);

        //3. item资源装载到适配器Adapter
        SimpleAdapter adapter = new SimpleAdapter(
                this,                        // 上下文
                list,                               // 数据源，其中有各项item的资源
                R.layout.item,                      // 自定义的item行布局
                new String[]{"logo","title",
                        "version","size"},          // 资源存储Map中的keys

                new int[]{R.id.logo,R.id.title,
                        R.id.version,R.id.size});   // 行布局中的id

        //4. 适配器将item资源关联到ListView控件
        listView.setAdapter(adapter);
    }
}