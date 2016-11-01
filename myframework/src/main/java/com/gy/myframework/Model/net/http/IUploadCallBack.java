package com.gy.myframework.Model.net.http;

import android.content.Context;

import com.gy.myframework.Model.net.http.entity.UploadProgress;

/**
 * Created by gy on 2016/2/25.
 */
public interface IUploadCallBack {
    public void onStart();
    public void onProgress(UploadProgress progress);
    public void onComplete();
    public void setup(Context context);
}
