package com.example.applicationmanger.untils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.example.applicationmanger.entity.AppInfo;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Utils {

    /**
     * 判断应用是否是第三方应用
     * @param appInfo
     * @return
     */
    public static boolean isThirdPartyApp(ApplicationInfo appInfo) {
        boolean flag = false;
        if ((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            // 可更新的系统应用
            flag = true;
        } else if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            // 非系统应用
            flag = true;
        }
        return flag;
    }

    public static List<AppInfo> getAppList(Context context){
        // 返回值集合
        List<AppInfo> list = new ArrayList<AppInfo>();
        // 实例化包管理器
        PackageManager pm = context.getPackageManager();
        // 获取所有已经安装的应用信息，参数传递0最可靠   PackageManager.GET_ACTIVITIES不一定可靠
        List<PackageInfo> pList = pm.getInstalledPackages(0);

        // 遍历集合
        for(int i = 0; i<pList.size(); i++){
            // 拿到元素
            PackageInfo packageInfo = pList.get(i);
            //判断是否第三方应用
            if(isThirdPartyApp(packageInfo.applicationInfo)
                    && !packageInfo.packageName.equals(context.getPackageName())) {
                // 填充实体类
                AppInfo app = new AppInfo();
                app.packageName = packageInfo.packageName;
                app.versionName = packageInfo.versionName;
                app.versionCode = packageInfo.versionCode;
                app.insTime = packageInfo.firstInstallTime;
                app.updTime = packageInfo.lastUpdateTime;
                // 获取应用名
                app.appName = (String) packageInfo.applicationInfo.loadLabel(pm);
                // 获取图标
                app.icon = packageInfo.applicationInfo.loadIcon(pm);
                // 计算程序的大小
                String dir = packageInfo.applicationInfo.publicSourceDir;
                long byteSize = new File(dir).length();
                app.byteSize = byteSize;// 实际大小
                app.size = getSize(byteSize);// 格式化好的大小

                list.add(app);
            }
        }
        return list;
    }

    public static String getSize(long size){
        return new DecimalFormat("0.##").format(size * 1.0/(1024*1024));
    }

    public static String getTime(long millis){
        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    //只能打开第三方APP
    public static void openPackage(Context context, String packageName){

        Intent intent =
                context.getPackageManager().getLaunchIntentForPackage(packageName);
        //创建新打开的应用程序的栈
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //跳转事件
        context.startActivity(intent);
    }

    //requestCode作为请求码确定是从某个界面点击删除的APP
    public static void uninstallApk(Activity context, String packageName,
                                    int requestCode){
        Uri uri= Uri.parse("package:"+packageName);

        Intent intent = new Intent(Intent.ACTION_DELETE,uri);
        //跳转事件,ForResult携带当前界面数据跳转到卸载界面,Activity context类自带
        context.startActivityForResult(intent,requestCode);
    }
}
