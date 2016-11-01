package com.gy.myframework.IOC.UI.view.viewbinder.annotation;

import android.widget.ProgressBar;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy on 2016/4/24.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@ListBinderBase(viewType = ProgressBar.class,methodName = "setMax",dataType = int.class)
public @interface BindProgressMax {
    int[] value();
}
