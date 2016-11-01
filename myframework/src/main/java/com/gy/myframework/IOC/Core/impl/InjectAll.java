package com.gy.myframework.IOC.Core.impl;

import com.gy.myframework.IOC.Core.entity.InjectOptions;
import com.gy.myframework.IOC.Core.parase.AnnotationCache;
import com.gy.myframework.IOC.Model.net.http.impl.HttpInjectUtil;
import com.gy.myframework.IOC.Service.event.impl.EventPoster;
import com.gy.myframework.IOC.Service.receiver.impl.InjectReceiverUtil;
import com.gy.myframework.IOC.Service.thread.impl.InjectAsycTask;
import com.gy.myframework.IOC.UI.view.viewinject.impl.ViewInjectAll;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gy on 2016/3/9.
 */
public final class InjectAll{

    private static InjectAll injectAll;

    private AnnotationCache methodcache;
    private Map<Class,InjectOptions[]> flags;

    public static InjectAll getInstance(){
        synchronized (InjectAll.class){
            if (injectAll == null)
                injectAll = new InjectAll();
        }
        return injectAll;
    }

    public InjectAll() {
        methodcache = new AnnotationCache();
        flags = new HashMap<Class,InjectOptions[]>();
    }

    public void inject(Object object){
        Class clazz = object.getClass();
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation:annotations){
            if (annotation.annotationType() == com.gy.myframework.IOC.Core.annotation.InjectAll.class)
                doInject(object,annotation);
        }
    }

    public void remove(Object object){
        InjectOptions[] options = flags.get(object.getClass());
        if (options == null)
            return;
        for (InjectOptions option : options) {
            switch (option) {
                case InjectView:
                    ViewInjectAll.Remove(object);
                    break;
                case InjectAsycTask:
                    InjectAsycTask.Remove(object);
                    break;
                case InjectReceiver:
                    InjectReceiverUtil.Unregist(object);
                    break;
                case InjectHttp:
                    HttpInjectUtil.Remove(object);
                    break;
                case InjectEvent:
                    EventPoster.Unregist(object);
                    break;
                case InjectDatabase:
                    break;
            }
        }
        flags.remove(object.getClass());
    }

    private void doInject(Object object, Annotation annotation) {
        InjectOptions[] optionses = ((com.gy.myframework.IOC.Core.annotation.InjectAll)annotation)
                .value();
        flags.put(object.getClass(),optionses);
        for (InjectOptions option : optionses){
            switch (option){
                case InjectView:
                    ViewInjectAll.Inject(object);
                    break;
                case InjectAsycTask:
                    InjectAsycTask.Inject(object);
                    break;
                case InjectReceiver:
                    InjectReceiverUtil.Regist(object);
                    break;
                case InjectHttp:
                    HttpInjectUtil.Inject(object);
                    break;
                case InjectEvent:
                    EventPoster.Regist(object);
                    break;
                case InjectDatabase:
                    break;
            }
        }
    }

    public static void Inject(Object object){
        getInstance().inject(object);
    }

    public static void Remove(Object object){
        getInstance().remove(object);
    }

}
