package com.gy.myframework.Service.buffer;

/**
 * Created by gy on 2016/4/20.
 */
public interface IBufferObject<T> {
    public void writeBack();
    public T refresh();
}
