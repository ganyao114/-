package com.gy.myframework.MVP.Model;

import com.gy.myframework.IOC.Model.net.http.impl.HttpInjectUtil;
import com.gy.myframework.IOC.Service.event.impl.EventPoster;

/**
 * Created by gy on 2016/4/22.
 * Model必须是单例的 不允许出现两个相同类型的Model对象!
 */
public abstract class BaseHttpModel<T> implements IBaseModel<T>{

    public BaseHttpModel() {
        initModel();
    }

    public HttpInjectUtil.ExtCall with(String key){
        return HttpInjectUtil.With(this,key);
    }

    @Override
    public <T> void onResult(T object) {
        EventPoster.Broadcast(object);
    }

    @Override
    public void initModel() {
        HttpInjectUtil.Inject(this);
    }

    @Override
    public void destoryModel() {
        HttpInjectUtil.Remove(this);
    }

    @Override
    public void onError(Object object) {
        EventPoster.Broadcast(object);
    }
}
