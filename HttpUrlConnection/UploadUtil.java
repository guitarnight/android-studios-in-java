package com.example.httpurlconnection;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class UploadUtil {
    private static final int TIME_OUT = 10*1000;
    private static final String CHARSET = "UTF-8";
    public static int uploadFile(File file, String requestURL){
        int res = 0;
        //Content-Type:
        String CONTENT_TYPE = "multipart/form-data";
        //开始符
        String PREFIX = "--";
        String BOUNDARY = UUID.randomUUID().toString();
        String LINE_END = "\r\n";
        //结束符
        String END = PREFIX + BOUNDARY + PREFIX + LINE_END;

        try{
            URL url = new URL(requestURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(TIME_OUT);
            httpURLConnection.setConnectTimeout(TIME_OUT);

            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            httpURLConnection.setUseCaches(false);

            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setRequestProperty("Charset",CHARSET);

            httpURLConnection.setRequestProperty("Connection","Keep-Alive");
            //上传准备工作：Content-type
            httpURLConnection.setRequestProperty("Content-type",CONTENT_TYPE+";boundary="+BOUNDARY);

            if (file != null){
                Log.e("test","upload start");
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                StringBuffer sb = new StringBuffer();
                //开始符
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                //文件说明
                sb.append("Content-Dispostion: form-data; name=\"file\"; filename=\""+file.getName()+"\""+LINE_END);
                sb.append("Content-Type: application/octet-stream; Charset="+CHARSET+LINE_END);
                sb.append(LINE_END);

                dataOutputStream.write(sb.toString().getBytes());
                //文件本身
                InputStream inputStream = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while((len = inputStream.read()) != -1){
                    dataOutputStream.write(bytes,0,len);
                    Log.d("test","upload bytes:" + len);
                }
                inputStream.close();
                dataOutputStream.write(LINE_END.getBytes());
                //结束符
                dataOutputStream.write(END.getBytes());
                dataOutputStream.flush();
                Log.d("test","upload end");

                res = httpURLConnection.getResponseCode();
                if(res == 200){
                    Log.d("test","upload success");
                    InputStream inputStream1 = httpURLConnection.getInputStream();
                    StringBuffer stringBuffer = new StringBuffer();
                    while((len = inputStream1.read(bytes))!= -1){
                        stringBuffer.append(new String(bytes, 0, len, "UTF-8"));
                    }
                    Log.d("test","result"+stringBuffer.toString());
                }else{
                    Log.d("test","upload fail");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //文件

        //结束符
        return 0;
    }
}
