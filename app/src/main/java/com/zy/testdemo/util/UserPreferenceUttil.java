package com.zy.testdemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.zy.testdemo.bean.LoginInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <pre>
 *     author : Zeyo
 *     e-mail : zengyongsun@163.com
 *     time   : 2017/11/20
 *     desc   : 保存登录信息的 SharedPreferences
 *     version: 1.0
 * </pre>
 */
public class UserPreferenceUttil {

    private static final String FILE_NAME = "login_info";
    private static final String INFO_KEY = "login_info";

    /**
     * 保存登录信息
     *
     * @param context
     * @param loginInfo 实体类
     * @throws Exception 实体类没有序列化
     */
    public static void saveLoginInfo(Context context, LoginInfo loginInfo) throws Exception {
        if (loginInfo != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(outputStream);
                //把对象写到流里
                oos.writeObject(loginInfo);
                String temp = new String(Base64.encode(outputStream.toByteArray(), Base64.DEFAULT));
                editor.putString(INFO_KEY, temp);
                editor.apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new Exception("User must implements Serializable");
        }
    }

    /**
     * 获取保存的登录信息
     */
    public static LoginInfo getLoginInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String temp = sharedPreferences.getString(INFO_KEY, "");
        ByteArrayInputStream stream = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
        LoginInfo user = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(stream);
            user = (LoginInfo) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return user;
    }

}
