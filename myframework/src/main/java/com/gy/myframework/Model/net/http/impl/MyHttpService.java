package com.gy.myframework.Model.net.http.impl;

import android.graphics.Bitmap;

import com.gy.myframework.Exception.model.net.http.HttpServiceException;
import com.gy.myframework.Model.net.http.IHttpDealCallBack;
import com.gy.myframework.Model.net.http.IHttpService;
import com.gy.myframework.Model.net.http.IUploadCallBack;
import com.gy.myframework.Model.net.http.entity.CustomMultipartEntity;
import com.gy.myframework.Model.net.http.entity.UploadProgress;
import com.gy.myframework.Model.net.http.factory.HttpClientFactory;
import com.gy.myframework.Service.loader.imgloader.strategy.test.ImageLoader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gy on 2015/11/6.
 */
public class MyHttpService implements IHttpService{

    private IHttpDealCallBack dealCallBack;
    private IUploadCallBack uploadCallBack;
    private String url;
    private Map<String, String> params;
    private Map<String,String> headers;
    private Map<String,String> forms;
    private long totalSize;
    private String Cookies;
    private File uploadFIle;

    public MyHttpService() {
    }

    public MyHttpService(IHttpDealCallBack dealCallBack) {
        this.dealCallBack = dealCallBack;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setUploadCallBack(IUploadCallBack uploadCallBack) {
        this.uploadCallBack = uploadCallBack;
    }

    public void setDealCallBack(IHttpDealCallBack dealCallBack) {
        this.dealCallBack = dealCallBack;
    }

    public IUploadCallBack getUploadCallBack() {
        return uploadCallBack;
    }

    public Map<String, String> getForms() {
        return forms;
    }

    public void setForms(Map<String, String> forms) {
        this.forms = forms;
    }

    public void setUploadFIle(File uploadFIle) {
        this.uploadFIle = uploadFIle;
    }

    @Override
    public Serializable getDataPost() throws Exception {

        HttpClient client = HttpClientFactory.GetHttpClient();

        HttpPost post = new HttpPost(url);



        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if (params != null) {
            for (Map.Entry<String, String> entity : params.entrySet())
                parameters.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
        }
        post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
        if (headers != null){
            for (Map.Entry<String, String> entity : headers.entrySet())
               post.addHeader(entity.getKey(),entity.getValue());
        }
        HttpResponse response = client.execute(post);

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpServiceException("连接失败");
        }
        String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
        //	client.getConnectionManager().shutdown();//关闭连接，未验证
//        HttpClientFactory.CloseHttpClient(client, 20);
        if (result.isEmpty())
            throw new HttpServiceException("未返回数据");
        Serializable back = null;
        if (dealCallBack != null)
            back = dealCallBack.dealReturn(result);
        else
            back = result;

        return back;
    }

    public Bitmap getBitmap(String url,File f) throws Exception{
        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl
                    .openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new HttpServiceException("连接服务器出错");
            }
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);

            ImageLoader.CopyStream(is, os);
            os.close();
            bitmap = ImageLoader.decodeFile(f);
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Serializable getDataGet() throws Exception {
        return null;
    }

    @Override
    public void download() throws Exception {

    }

    @Override
    public String upload(File file) throws Exception {

        final UploadProgress progress = new UploadProgress();
        String serverResponse = null;
        HttpClient client = HttpClientFactory.GetHttpClient();

        HttpContext httpContext = new BasicHttpContext();

        HttpPost post = new HttpPost(url);

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if (params != null) {
            for (Map.Entry<String, String> entity : params.entrySet())
                parameters.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
        }
        post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
        if (headers != null){
            for (Map.Entry<String, String> entity : headers.entrySet())
                post.addHeader(entity.getKey(),entity.getValue());
        }
        try {
            CustomMultipartEntity multipartContent = new CustomMultipartEntity(
                    new CustomMultipartEntity.ProgressListener() {
                        @Override
                        public void transferred(long num) {
                            if (uploadCallBack == null)
                                return;
                            progress.setPersent((int) ((num / (float) totalSize) * 100));
                            uploadCallBack.onProgress(progress);
                        }
                    });

            multipartContent.addPart("data", new FileBody(file));
            totalSize = multipartContent.getContentLength();

            // Send it
            post.setEntity(multipartContent);
            HttpResponse response = client.execute(post, httpContext);
            serverResponse = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return serverResponse;

    }

    @Override
    public String upload() throws Exception {
        return upload(uploadFIle);
    }

    public String getCookies() {
        return Cookies;
    }

    public void setCookies(String cookies) {
        Cookies = cookies;
    }
}
