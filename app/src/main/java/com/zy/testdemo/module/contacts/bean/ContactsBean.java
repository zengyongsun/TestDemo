package com.zy.testdemo.module.contacts.bean;

import java.util.List;

/**
 * <pre>
 *    @author : Zeyo
 *     e-mail : zengyongsun@163.com
 *     time   : 2017/12/01
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ContactsBean {

    public ContactsBean(String contactsId, String contactsName) {
        this.contactsId = contactsId;
        this.contactsName = contactsName;
    }

    private String contactsId;
    private String contactsName;
    private List<String> phoneNumber;

    private boolean select = false;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public List<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(List<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
