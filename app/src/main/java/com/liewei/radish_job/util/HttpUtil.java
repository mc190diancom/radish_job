package com.liewei.radish_job.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.liewei.radish_job.async.DException;
import com.loror.lororUtil.convert.MD5Util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("deprecation")
public class HttpUtil {

    // 上传文件到媒体服务器
    // 返回文件下载URL
    public static String uploadFile(String server, File file) throws Exception {
        URL url = new URL(server);
        DefaultHttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
        HttpPost httpPost = new HttpPost(url.toURI());

        MultipartEntity m = new MultipartEntity();
        m.addPart("file", new FileBody(file));
        httpPost.setEntity(m);
        HttpResponse response = httpclient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new DException("网络问题:" + response.getStatusLine().getStatusCode());
        }
        String rst = EntityUtils.toString(response.getEntity(), "UTF-8");
        return rst;
    }

    /*public static String uploadFile(String server, List<FormBodyPart> bodys) throws Exception {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(5000, TimeUnit.MILLISECONDS);
        client.setReadTimeout(30000, TimeUnit.MILLISECONDS);
        MultipartBuilder multibuilder = new MultipartBuilder();
        for (FormBodyPart bodyPart : bodys) {
            if (bodyPart.getBody() instanceof FileBody) {
                FileBody appfileBody = (FileBody) bodyPart.getBody();
                File file = appfileBody.getFile();
                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                multibuilder.addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"" + bodyPart.getName() + "\";filename=\"" + file.getName() + "\""), fileBody);
            } else if (bodyPart.getBody() instanceof StringBody) {
                StringBody appstrBody = (StringBody) bodyPart.getBody();
                InputStreamReader input = (InputStreamReader) appstrBody.getReader();
                String value = getStringByStream(input);
                //Log.e("Http",bodyPart.getName()+"==="+value);
                multibuilder.addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"" + bodyPart.getName()),//fdddddddddddd
                        RequestBody.create(null, value));
            }
        }
        RequestBody requestBody = multibuilder
                .type(MultipartBuilder.FORM)
                .build();
        Request request = new Request.Builder()
                .url(server)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return new String(response.body().string());
        } else {
            throw new IOException("网络问题:" + response);
        }
    }*/
    /**
     * 通过流获取字符串
     */
    protected static String getStringByStream(InputStreamReader input) throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] bodybyte = new char[2048];
        int total;
        while ((total = input.read(bodybyte)) != -1) {
            sb.append(bodybyte, 0, total);
        }
        return sb.toString();
    }
    public static String post(String server, List<NameValuePair> paramList) throws Exception {
        //TODO 重构加cityId
        //	paramList.add(new BasicNameValuePair("cityId", "ChengDu"));
        URL url = new URL(server);
        DefaultHttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);

        HttpPost httpPost = new HttpPost(url.toURI());

        if (paramList != null) {
            httpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
        }

       // Log.e("req11",httpPost+"");
        HttpResponse response = httpclient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() != 200) {
            Log.e("Company",response.getStatusLine().getStatusCode()+"");
            throw new DException("网络问题:"+response.getStatusLine().getStatusCode());
        }
        String rst = EntityUtils.toString(response.getEntity(), "UTF-8");
        Log.e("Companyss",rst);
        /*String decodeRst = new String(CompressUtil.unGZip(Base64.decodeResultToBytes("returnjson",rst)));
       // String decodeRst = Base64.decodeResult("returnjson",rst);
        Log.e("Company",decodeRst);*/
        return rst;
    }

    public static String post2(String server, List<NameValuePair> paramList) throws Exception {
        //TODO 重构加cityId
        //	paramList.add(new BasicNameValuePair("cityId", "ChengDu"));
        URL url = new URL(server);
        DefaultHttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);

        HttpPost httpPost = new HttpPost(url.toURI());

        //TODO 17/3/13 龚强修改
        ArrayList<NameValuePair> encryptParamList = new ArrayList<NameValuePair>();
        for(int i=0,len=paramList.size();i<len;i++){
            encryptParamList.add(new BasicNameValuePair(paramList.get(i).getName(), paramList.get(i).getValue()));
        }
        Log.e("Company",encryptParamList.toString());
        if (encryptParamList != null) {
            httpPost.setEntity(new UrlEncodedFormEntity(encryptParamList, "UTF-8"));
        }
        /*if (paramList != null) {
            httpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
        }*/

        // Log.e("req11",httpPost+"");
        HttpResponse response = httpclient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new DException("网络问题:"+response.getStatusLine().getStatusCode());
        }
        Log.e("Company",response.getStatusLine().getStatusCode()+"");
        String rst = EntityUtils.toString(response.getEntity(), "UTF-8");
       /* Log.e("Companyss",rst);
        String decodeRst = Base64.decodeResult("returnjson",rst);
        Log.e("Company",decodeRst);*/
        return rst;
    }

    public static JSONObject postJsonObject(String server, List<NameValuePair> paramList) throws Exception {
        return new JSONObject(post(server, paramList));
    }

    public static JSONObject postJsonObject(String server) throws Exception {
        return new JSONObject(post(server, null));
    }

    public static JSONArray postJsonArray(String server, List<NameValuePair> paramList) throws Exception {
        return new JSONArray(post(server, paramList));
    }

    public static JSONArray postJsonArray(String server) throws Exception {
        return new JSONArray(post(server, null));
    }

    public static byte[] getBytes(URL url) throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
        HttpGet httpGet = new HttpGet(url.toURI());
        HttpResponse response = httpclient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new DException("网络问题:" + response.getStatusLine().getStatusCode());
        }
        return EntityUtils.toByteArray(response.getEntity());
    }

    public static byte[] getBytes(String url) throws Exception {
        return getBytes(new URL(url));
    }

    public static File downloadImgCache(String url, String md5, boolean isUpdate) {
        if (md5 == null) {
            md5 = MD5Util.md5(url);
        }
        InputStream is = null;
        OutputStream os = null;
        File f = UiTool.createFilePath(md5);
        if (f.exists() && !isUpdate) {
            return f;
        } else {
            try {
                URLConnection conn = new URL(url).openConnection();
                is = conn.getInputStream();
                byte[] bs = new byte[1024 * 4];
                int len;
                os = new FileOutputStream(f);
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
            return f;
        }
    }

    public static byte[] download(String url, String md5) throws Exception {
        File f = UiTool.createFilePath(md5);
        if (!f.exists()) {
            byte[] bs = getBytes(new URL(url));
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(f);
                out.write(bs);
                out.flush();
            } finally {
                if (out != null)
                    out.close();
            }
            return bs;
        }
        return null;
    }

    public static byte[] getCacheBytes(String url) throws Exception {
        return getCacheBytes(url, null);
    }

    public static byte[] getCacheBytes(String url, String md5) throws Exception {
        if (md5 == null) {
            md5 = MD5Util.md5(url);
        }
        File f = UiTool.createFilePath(md5);
        if (f.exists()) {
            byte[] bs = new byte[(int) f.length()];
            FileInputStream in = null;
            try {
                in = new FileInputStream(f);
                in.read(bs);
            } finally {
                if (in != null)
                    in.close();
            }
            return bs;
        } else {
            return download(url, md5);
        }
    }

    public static String saveBitmap(Bitmap bitmap, String bitmapName) throws Exception {
        File f = UiTool.createFilePath(bitmapName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.flush();
        } finally {
            out.close();
        }
        return f.getPath();
    }


    /***************************************************************************************************************************************/
    public static void saveFile(String str) {
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) { // SD卡根目录的hello.text
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + "json.txt";
        } else  // 系统下载缓存根目录的hello.text
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + "json.txt";

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(str.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }














































