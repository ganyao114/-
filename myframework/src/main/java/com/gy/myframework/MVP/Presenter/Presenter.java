package com.gy.myframework.MVP.Presenter;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.gy.myframework.IOC.Model.net.http.impl.HttpInjectUtil;
import com.gy.myframework.IOC.Mvp.annotation.InjectPresenter;
import com.gy.myframework.IOC.Service.thread.impl.InjectAsycTask;
import com.gy.myframework.MVP.View.context.IContext;
import com.gy.myframework.MVP.View.context.activity.IActivity;
import com.gy.myframework.MVP.View.context.entity.ContextChangeEvent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by gy on 2015/12/28.
 */
public abstract class Presenter implements IPresenterCallBack{

    private static Presenter curPresenter;

    private static WeakHashMap<Class<? extends Presenter>,Presenter> presents = new WeakHashMap<Class<? extends Presenter>,Presenter>();;

    private WeakReference<? extends Context> curContextRef;

    private List<Context> contextList;

    public Presenter() {
        contextList = new ArrayList<Context>();
        curPresenter = this;
        InjectAsycTask.getInstance().inject(this);
        HttpInjectUtil.getInstance().inject(this);
    }

    public static Presenter regist(Context context){
        Class<?> type = context.getClass();
        InjectPresenter inject = type.getAnnotation(InjectPresenter.class);
        if (inject == null) {
            try {
                throw new ClassNotFoundException();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        Class<? extends Presenter> clazz = inject.value();
        Presenter presenter = getPresent(clazz);
        if (presenter == null){
            try {
                presenter = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            putPresent(presenter);
        }
        presenter.addContext(context);
        return presenter;
    }

    public static void unregist(Context context){
        Presenter presenter = ((IContext)context).getPresent();
        presenter.removeContext(context);
        if (presenter.getContexts().size() == 0)
            destoryPresenter(presenter);
    }


    @Override
    public void OnPresentSeted(Context context) {

        Context preContext = null;

        if (curContextRef !=null)
            preContext = curContextRef.get();

        this.curContextRef = new WeakReference<Context>(context);

        if (getCurPresenter() != ((IContext)context).getPresent())
            setCurPresnter(((IContext)context).getPresent());

        if (preContext == null){
            OnPresentInited(context);
            return;
        }

        ContextChangeEvent event = new ContextChangeEvent();
        if (preContext instanceof Activity){
            if (context instanceof Activity)
                event.setAction(ContextChangeEvent.ACTIVITY_ACTIVITY);
            else if (preContext instanceof Service)
                event.setAction(ContextChangeEvent.ACTIVITY_SERVICE);
        }else if (preContext instanceof Service){
            if (context instanceof Activity)
                event.setAction(ContextChangeEvent.SERVICE_ACTIVITY);
            else if (preContext instanceof Service)
                event.setAction(ContextChangeEvent.SERVICE_SERVICE);
        }
        event.setContext(context);
        onContextChanged(event);
    }

    @Override
    public void DestoryPresent() {
        HttpInjectUtil.getInstance().remove(this);
        InjectAsycTask.getInstance().remove(this);
    }

    protected abstract void onContextChanged(ContextChangeEvent event);

    @Override
    public Context getContext() {
        return curContextRef.get();
    }

    public Activity getActivityRaw(){
        return (Activity) getContext();
    }

    public IActivity getActivityInter(){
        return (IActivity) getActivityRaw();
    }

    public IActivity getActivityInter(Activity activity){
        return (IActivity) activity;
    }

    public Service getServiceRaw(){
        return (Service) getContext();
    }

    protected  <T extends View> T getView(int ViewId){
        return getActivityInter().getView(ViewId);
    }

    protected void startActivity(Intent intent){
        //OnActivityChangeBefore();
        getActivityRaw().startActivity(intent);
    }

    public static Presenter getCurPresenter() {
        return curPresenter;
    }

    public static Presenter getPresenter(Class<? extends Presenter> key){
        return presents.get(key);
    }

    public static void setCurPresnter(Presenter presnter){
        curPresenter = presnter;
    }

    private static void destoryPresenter(Presenter presenter){
        presenter.DestoryPresent();
        presents.remove(presenter.getClass());
    }

    public static Presenter getPresent(Class clazz){
        return presents.get(clazz);
    }

    public static void putPresent(Presenter presenter){
        presents.put(presenter.getClass(),presenter);
    }

    public void addContext(Context context){
        contextList.add(context);
    }

    public void removeContext(Context context){
        contextList.remove(context);
    }

    public List<Context> getContexts(){
        return contextList;
    }

}
