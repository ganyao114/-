package com.gy.myframework.MVP.View.context.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gy.myframework.IOC.UI.view.viewinject.impl.ViewInjectAll;
import com.gy.myframework.IOC.Service.thread.impl.InjectAsycTask;
import com.gy.myframework.MVP.Presenter.IPresenterCallBack;
import com.gy.myframework.MVP.Presenter.Presenter;
import com.gy.myframework.MVP.View.context.IContext;
import com.gy.myframework.MVP.View.context.entity.ActivityOnCreatedListener;
import com.gy.myframework.MVP.View.context.entity.ActivityOnDestoryListener;
import com.gy.myframework.MVP.View.context.entity.ActivityOnPauseListener;
import com.gy.myframework.MVP.View.context.entity.ActivityOnRestartListener;
import com.gy.myframework.MVP.View.context.entity.ActivityOnResumeListener;
import com.gy.myframework.MVP.View.context.entity.ActivityOnStartListener;
import com.gy.myframework.MVP.View.context.entity.ActivityOnStopListener;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gy on 2015/12/27.
 */
public abstract class BasicActivity extends Activity implements IActivity,IContext{
    private SparseArray<View> mViews;
    private Map<Class,Object> listeners;
    private WeakReference<IPresenterCallBack> callbackRef;

    public BasicActivity(){
        mViews = new SparseArray<View>();
        listeners = new HashMap<Class,Object>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectAll.getInstance().inject(this);
        setPresent();
        ActivityOnCreatedListener listener = (ActivityOnCreatedListener) listeners
                .get(ActivityOnCreatedListener.class);
        if (listener!=null)
            listener.ActivityOnCreated(savedInstanceState,this);

    }

    private void setPresent(){
        callbackRef = new WeakReference<IPresenterCallBack>((IPresenterCallBack)Presenter.regist(this));
        setActivity();
    }

    private void setActivity(){
        callbackRef
                .get()
                .OnPresentSeted(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (callbackRef.get().getActivityRaw() != this)
            setActivity();
        ActivityOnStartListener listener = (ActivityOnStartListener) listeners
                .get(ActivityOnStartListener.class);
        if (listener != null)
            listener.ActivityOnStart(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ActivityOnRestartListener listener = (ActivityOnRestartListener) listeners
                .get(ActivityOnRestartListener.class);
        if (listener != null)
            listener.ActivityOnRestart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityOnResumeListener listener = (ActivityOnResumeListener) listeners.get(ActivityOnResumeListener.class);
        if (listener != null)
            listener.ActivityOnResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityOnPauseListener listener = (ActivityOnPauseListener) listeners.get(ActivityOnPauseListener.class);
        if (listener != null)
            listener.ActivityOnPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ActivityOnStopListener listener = (ActivityOnStopListener) listeners.get(ActivityOnStopListener.class);
        if (listener != null)
            listener.ActivityOnStop(this);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        InjectAsycTask.getInstance().remove(this);

        ActivityOnDestoryListener listener = (ActivityOnDestoryListener) listeners.get(ActivityOnDestoryListener.class);
        if (listener!=null)
            listener.ActivityOnDestory(this);

        Presenter.unregist(this);
    }

    public  <T extends View> T getView(int ViewId) {
        T view = (T) mViews.get(ViewId);
        if (view == null) {
            view = (T)findViewById(ViewId);
            mViews.put(ViewId, view);
        }
        return view;
    }


    public void setText(int id,String str){
        TextView view = getView(id);
        view.setText(str);
    }

    public void setImg(int id, Bitmap bitmap){
        ImageView imageView = getView(id);
        imageView.setImageBitmap(bitmap);
    }

    public void setOnCreateListener(ActivityOnCreatedListener listener){
        listeners.put(ActivityOnCreatedListener.class,listener);
    }

    public void setOnDestroyListener(ActivityOnDestoryListener listener){
        listeners.put(ActivityOnDestoryListener.class,listener);
    }

    public void setOnStartListener(ActivityOnStartListener listener){
        listeners.put(ActivityOnStartListener.class,listener);
    }

    public void setOnRestartListener(ActivityOnRestartListener listener){
        listeners.put(ActivityOnRestartListener.class,listener);
    }

    public void setOnResumeListener(ActivityOnResumeListener listener){
        listeners.put(ActivityOnResumeListener.class,listener);
    }

    public void setOnStopListener(ActivityOnStopListener listener){
        listeners.put(ActivityOnStopListener.class,listener);
    }

    public void setOnPauseListener(ActivityOnPauseListener listener){
        listeners.put(ActivityOnPauseListener.class,listener);
    }

    @Override
    public Presenter getPresent() {
        return (Presenter) callbackRef.get();
    }
}
