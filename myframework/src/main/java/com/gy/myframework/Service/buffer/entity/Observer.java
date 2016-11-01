package com.gy.myframework.Service.buffer.entity;

import com.gy.myframework.Service.buffer.IBufferObject;
import com.gy.myframework.Service.buffer.IObserver;

import java.lang.ref.WeakReference;
import java.util.Vector;

/**
 * Created by gy on 2016/4/20.
 */
public class Observer implements IObserver{
    private Vector<WeakReference<IBufferObject>> sublist;

    public Observer() {
        sublist = new Vector<WeakReference<IBufferObject>>();
    }

    @Override
    public void addSuber(IBufferObject object) {
        sublist.add(new WeakReference<IBufferObject>(object));
    }

    @Override
    public void removeSber(IBufferObject object) {
    }

    @Override
    public void notifyAct(ActionType type) {
        for (WeakReference<IBufferObject> ref:sublist)
            ref.get().refresh();
    }
}
