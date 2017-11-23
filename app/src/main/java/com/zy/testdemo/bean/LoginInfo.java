package com.zy.testdemo.bean;

import java.io.Serializable;

/**
 * <pre>
 *    @author : Zeyo
 *     e-mail : zengyongsun@163.com
 *     time   : 2017/11/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LoginInfo implements Serializable {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
