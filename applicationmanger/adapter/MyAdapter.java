package com.example.applicationmanger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applicationmanger.R;
import com.example.applicationmanger.untils.Utils;
import com.example.applicationmanger.entity.AppInfo;

import java.util.List;

//Adapter只管前后端交互，效果渲染   拿的Urtils整理到AppInfo list的现成后台资源，进行控件id 和 @resource匹配
public class MyAdapter extends BaseAdapter {

    List<AppInfo> list;
    LayoutInflater inflater;
    IUninstall iUninstall;

    //外部传入接口,此时的iUninstall的点击方法已经由MainActivity落实，到控件监听点击事件即可
    public void setiUninstall(IUninstall iUninstall) {
        this.iUninstall = iUninstall;
    }

    public MyAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setList(List<AppInfo> list) {
        this.list = list;
    }

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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.logo = (ImageView) convertView.findViewById(R.id.logo);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.version = (TextView) convertView.findViewById(R.id.version);
            holder.size = (TextView) convertView.findViewById(R.id.size);
            holder.btn = (Button) convertView.findViewById(R.id.btn);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        AppInfo app = list.get(position);
        holder.logo.setImageDrawable(app.icon);
        holder.title.setText(app.appName);
        holder.version.setText(app.versionName + " "+ Utils.getTime(app.insTime));
        holder.size.setText(app.size + "M" + " "+ Utils.getTime(app.updTime));
        //holder本来应该也是从这设置btn点击，但是这样做太拥挤了，利用接口
        final int pos = position;
        final String packName = app.packageName;
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iUninstall.btnOnClick(pos,packName);
            }
        });

        return convertView;
    }

    public class ViewHolder{
        ImageView logo;
        TextView title;
        TextView version;
        TextView size;
        Button btn;
    }
}
