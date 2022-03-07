package com.example.myadapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class MyAdapter extends BaseAdapter {

    /** 数据集合 */
    List<Map<String,Object>> list;
    /**
     * 传入数据集合
     * @param list
     */
    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    /** 反射器 */
    LayoutInflater inflater;
    /**
     * 构造器
     * @param context 上下文
     */
    public MyAdapter(Context context){
        inflater = LayoutInflater.from(context);
    }

    /**
     *  getCount getItemID getItem getView  四大方法继承并落实自父类BaseAdapter的抽象方法
     */
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //反射器   反射自己定义好的item布局
        View view = inflater.inflate(R.layout.item,null);

        //逐个查找item布局中的控件ID
        ImageView logo = (ImageView) view.findViewById(R.id.logo);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView version = (TextView) view.findViewById(R.id.version);
        TextView size = (TextView) view.findViewById(R.id.size);

        //逐个set填充item布局中的控件
        //list . get(position) 返回值是map，存储当前position的item的资源
        Map<String,Object>map = list.get(position);
        logo.setImageResource((Integer) map.get("logo"));
        title.setText((String) map.get("title"));
        version.setText((String) map.get("version"));
        size.setText((String) map.get("size"));

        Button btn = (Button) view.findViewById(R.id.btn);
        //点击监听器setOnClickListener，负责点击按钮后的事件，如Toast 跳转Activity 弹窗 对话框
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("spl", "点击");
            }
        });

        return view;
    }
    */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.logo = (ImageView) convertView.findViewById(R.id.logo);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.version = (TextView) convertView.findViewById(R.id.version);
            holder.size = (TextView) convertView.findViewById(R.id.size);
            holder.button = (Button) convertView.findViewById(R.id.btn);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Map map = list.get(position);
        holder.logo.setImageResource((Integer) map.get("logo"));
        holder.title.setText((String) map.get("title"));
        holder.version.setText((String) map.get("version"));
        holder.size.setText((String) map.get("size"));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("spl", "点击");
            }
        });
        return convertView;
    }

    public class ViewHolder{
        ImageView logo;
        TextView title;
        TextView version;
        TextView size;
        Button button;
    }
}
