package com.gy.myframework.IOC.UI.view.viewbinder.impl;

import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.gy.myframework.IOC.Core.entity.ClassMemberPackage;
import com.gy.myframework.IOC.Core.parase.ClassMemberParase;
import com.gy.myframework.IOC.UI.view.viewbinder.annotation.ListBinderBase;
import com.gy.myframework.IOC.UI.view.viewbinder.annotation.ListBinderLtnBase;
import com.gy.myframework.IOC.UI.view.viewbinder.annotation.ListDataSrc;
import com.gy.myframework.IOC.UI.view.viewbinder.entity.BinderPackage;
import com.gy.myframework.UI.view.baserecycleview.ViewHolder;
import com.gy.myframework.UI.view.collectionview.IAdapterCallBack;
import com.gy.myframework.UI.view.collectionview.adapter.NomListAdapter;
import com.gy.myframework.UI.view.collectionview.viewholder.IViewHolder;
import com.gy.myframework.UI.view.recyclerview.adapter.IRcAdapterCallBack;
import com.gy.myframework.UI.view.recyclerview.adapter.NomRcViewAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by gy on 2016/3/15.
 */
public class ListBinder {

    private static ListBinder utils;

    public static ListBinder getInstance(){
        synchronized (ListBinder.class){
            if (utils == null)
                utils = new ListBinder();
        }
        return utils;
    }

    public ExtCall with(AbsListView view){
        return new ExtCall(view);
    }

    public ExtCall with(RecyclerView view){
        return new ExtCall(view);
    }

    public static ExtCall With(AbsListView view){
        return getInstance().with(view);
    }

    public static ExtCall With(RecyclerView view){
        return getInstance().with(view);
    }

    public void bind(AbsListView view,RecyclerView recyclerView,List<?> list,Class clazzType,Object ltnImpl){
        if (list == null)
            return;
        Class clazz = null;
        if (list.size() != 0)
            clazz = list.get(0).getClass();
        else
            clazz = clazzType;
        ListDataSrc listDataSrc = (ListDataSrc) clazz.getAnnotation(ListDataSrc.class);
        if (listDataSrc == null)
            return;
        List<ClassMemberPackage> mems = null;
        try {
            mems = ClassMemberParase.GetFileds(list.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mems == null||mems.size()==0)
            return;
        final BinderPackage binderPackage = new BinderPackage();
        binderPackage.setList(list);
        for (ClassMemberPackage mem : mems){
            Annotation[] annotations = mem.getAnnotations();
            for (Annotation anno : annotations){
                ListBinderBase annobase = anno.annotationType()
                        .getAnnotation(ListBinderBase.class);
                ListBinderLtnBase ltnannobase = anno.annotationType()
                        .getAnnotation(ListBinderLtnBase.class);
                if (annobase!=null){
                    Method method = null;
                    try {
                        method = annobase.viewType().getDeclaredMethod(annobase.methodName(),annobase.dataType());
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if (method != null) {
                        BinderPackage.BinderTarget target =
                                binderPackage.new BinderTarget(annobase.viewType(), mem.getField(), method);

                        for (int item : getItems(anno))
                            binderPackage.getBindlist().put(item, target);
                    }
                }
                if (ltnannobase!=null){
                    Method method = null;
                    try {
                        method = ltnannobase.viewType().getDeclaredMethod(ltnannobase.listenerSetter(),ltnannobase.listenerType());
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    if (method==null)
                        continue;
                    Object ltn = ltnImpl;
                    BinderPackage.BinderTarget target =
                            binderPackage.new BinderTarget(ltn,method,ltnannobase.listenerType());
                    for (int item:getItems(anno)){
                        if (binderPackage.getBindlist().get(item)==null){
                            binderPackage.getBindlist().put(item,target);
                        }else {
                            BinderPackage.BinderTarget tar = binderPackage.getBindlist().get(item);
                            tar.setLtnImpl(ltn);
                            tar.setLtnMethod(method);
                            tar.setLtnType(ltnannobase.listenerType());
                        }
                    }
                }
            }
        }
        if (view != null) {
            IAdapterCallBack callBack = new IAdapterCallBack() {
                @Override
                public void adapterCall(IViewHolder holder, int position) {
                    binderPackage.setHolder(holder, position);
                }
            };
            NomListAdapter adapter = new NomListAdapter(view.getContext(), list, listDataSrc.value(), callBack);
            adapter.setBinder(binderPackage);
            view.setAdapter(adapter);
        }
        if (recyclerView !=null){
            IRcAdapterCallBack rcAdapterCallBack = new IRcAdapterCallBack() {
                @Override
                public void adapterCall(ViewHolder holder, int position) {
                    binderPackage.setHolder(holder,position);
                }

                @Override
                public void adapterCall(ViewHolder holder, Object object) {

                }
            };
            NomRcViewAdapter adapter = new NomRcViewAdapter(recyclerView.getContext(),listDataSrc.value(),list,rcAdapterCallBack);
            recyclerView.setAdapter(adapter);
        }
    }

    public void bind(AbsListView view,List<?> list,IAdapterCallBack callBack,Class clazzType){
        if (list == null)
            return;
        Class<?> clazz = null;
        if (list.size() != 0)
            clazz = list.get(0).getClass();
        else
            clazz = clazzType;
        ListDataSrc listDataSrc = clazz.getAnnotation(ListDataSrc.class);
        if (listDataSrc == null)
            return;
        NomListAdapter adapter = new NomListAdapter(view.getContext(),list,listDataSrc.value(),callBack);
        view.setAdapter(adapter);
    }

    public void bind(RecyclerView view, List<?> list, Class clazzType, Object ltnImpl){

    }

    public void bind(RecyclerView view, List<?> list, IRcAdapterCallBack callBack, Class clazzType){
        if (list == null)
            return;
        Class<?> clazz = null;
        if (list.size() != 0)
            clazz = list.get(0).getClass();
        else
            clazz = clazzType;
        ListDataSrc listDataSrc = clazz.getAnnotation(ListDataSrc.class);
        if (listDataSrc == null)
            return;
        NomRcViewAdapter adapter = new NomRcViewAdapter(view.getContext(),listDataSrc.value(),list,callBack);
        view.setAdapter(adapter);
    }

    private int[] getItems(Annotation annotation){
        Class annoClazz = annotation.getClass();
        Method method = null;
        int[] items = null;
        try {
            method = annoClazz.getDeclaredMethod("value");
        } catch (NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            items = (int[]) method.invoke(annotation);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void refresh(AbsListView view){
        ((BaseAdapter)view.getAdapter()).notifyDataSetChanged();
    }

    public void refresh(RecyclerView view){
        ((RecyclerView.Adapter)view.getAdapter()).notifyDataSetChanged();
    }


    public class ExtCall{
        private AbsListView listView;
        private RecyclerView recyclerView;
        private Class clazz;
        private IAdapterCallBack callBack;
        private IRcAdapterCallBack rcCallBack;
        private Object ltnImpl;
        public ExtCall(AbsListView listView) {
            this.listView = listView;
        }

        public ExtCall(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }
        public ExtCall setClass(Class clazz){
            this.clazz = clazz;
            return this;
        }
        public ExtCall setCallBack(IAdapterCallBack callBack){
            this.callBack = callBack;
            return this;
        }

        public ExtCall setCallBack(IRcAdapterCallBack callBack){
            this.rcCallBack = callBack;
            return this;
        }

        public ExtCall setLtnImpl(Object ltnImpl) {
            this.ltnImpl = ltnImpl;
            return this;
        }

        public void bind(List<?> list){

            if (listView!=null){
                if (callBack == null)
                    getInstance().bind(listView,recyclerView,list,clazz,ltnImpl);
                else
                    getInstance().bind(listView,list,callBack,clazz);
            }

            if (recyclerView!=null){
                if (rcCallBack == null)
                    getInstance().bind(listView,recyclerView,list,clazz,ltnImpl);
                else
                    getInstance().bind(recyclerView,list,rcCallBack,clazz);
            }


        }
        public void refresh(){
            if (listView!=null)
                getInstance().refresh(listView);
            if (recyclerView!=null)
                getInstance().refresh(recyclerView);
        }
    }

}
