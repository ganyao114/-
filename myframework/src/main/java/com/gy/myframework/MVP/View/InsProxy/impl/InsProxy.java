package com.gy.myframework.MVP.View.InsProxy.impl;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

import com.gy.myframework.MVP.Core.MVP;
import com.gy.myframework.MVP.Entity.MVPEntity;
import com.gy.myframework.MVP.Presenter.IPresenterCallBack;
import com.gy.myframework.MVP.Presenter.Presenter;
import com.gy.myframework.MVP.View.InsProxy.IInsProxy;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by gy on 2016/3/23.
 */
public class InsProxy implements IInsProxy{
    @Override
    public void injctPresent(Context context) {
        //初始化entity
        MVPEntity entity = new MVPEntity();
        entity.setmViews(new SparseArray<WeakReference<View>>());
        entity.setListeners(new HashMap<Class, Object>());
        entity.setCallbackRef(new WeakReference<IPresenterCallBack>((IPresenterCallBack) Presenter.regist(context)));
        MVP.mvpEntity.put(context.getClass(),entity);
        //setContext
        setContext(context);
    }

    @Override
    public void unRegist(Context context) {

    }

    @Override
    public void setContext(Context context) {
        if (MVP.mvpEntity.get(context.getClass())
                .getCallbackRef().get()
                .getContext()!=context)
        MVP.mvpEntity
                .get(context.getClass())
                .getCallbackRef().get()
                .OnPresentSeted(context);
    }

    @Override
    public void setListener(Context context,Class inter, Object listener) {
        MVP.mvpEntity
                .get(context)
                .getListeners()
                .put(inter,listener);
    }

    @Override
    public void callOnListener(Context context, Class inter) {

    }
}
