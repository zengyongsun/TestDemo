package com.zy.testdemo.base;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * <pre>
 *     author : Zeyo
 *     e-mail : zengyongsun@163.com
 *     time   : 2017/11/13
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class DemoAppliction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
