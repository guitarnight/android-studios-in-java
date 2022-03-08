package com.example.applicationmanger;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applicationmanger.adapter.IUninstall;
import com.example.applicationmanger.entity.AppInfo;
import com.example.applicationmanger.untils.Utils;
import com.example.applicationmanger.adapter.MyAdapter;

import java.util.List;

//继承Activity则没有顶部的标题框和ActionBar，继承AppCompatActivity的有顶部的标题栏和ActionBar
//顶部标题栏不可以自定义内容，他就是包名
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, IUninstall {

    ListView lv;
    List<AppInfo> list;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //拿到ListView
        lv = (ListView) findViewById(R.id.lv_main);
        //Untils获取数据源
        list = Utils.getAppList(this);
        //适配器
        adapter = new MyAdapter(this);
        adapter.setList(list);
        //关联
        lv.setAdapter(adapter);

        Log.i("spl","list="+list.toString());
        //自己实现onCLick方法
        lv.setOnItemClickListener(this);

        //接口传参
        adapter.setiUninstall(this);
    }

    /**
     * ActionBar的反射和点击后事件
     */
    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sort_date) {
            Toast.makeText(this,"日期序",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.sort_name) {
            Toast.makeText(this,"名称序",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.sort_size) {
            Toast.makeText(this,"大小序",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 对话框充当程序加载动画
     */
    //1
    ProgressDialog pd;

    public void showProgressDialog(){
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("刷新列表");
        pd.setMessage("请耐心等待");
        pd.show();
    }

    //2
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            adapter.setList(list);
            adapter.notifyDataSetChanged();//通知数据更新
            //Log.i("spl", "list=" + list.toString());
            Log.i("spl", "size=" + list.size());
            pd.dismiss();
            return false;
        }
    });

    //3.
    private void updateData(){
        new Thread(){
            @Override
            public void run() {
                list = Utils.getAppList(MainActivity.this);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.sendEmptyMessage(1);
            }
        }.start();// 子线程
        showProgressDialog();// UI线程
    }

    /**
     *点击打开应用程序
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //所有的App信息在list中，一个APP的信息是一个单独的AppInfo实体类，有这个APP的信息，由position确定
        AppInfo app = (AppInfo) parent.getItemAtPosition(position);
        Utils.openPackage(this,app.packageName);
    }

    /**
     * 删除三方APP
     */
    public static final int CODE_UNINSTALL = 0;

    @Override
    public void btnOnClick(int pos, String packageName) {
        Utils.uninstallApk(this,packageName,CODE_UNINSTALL);
    }

}