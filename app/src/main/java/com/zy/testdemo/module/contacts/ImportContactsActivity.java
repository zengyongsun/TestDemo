package com.zy.testdemo.module.contacts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zy.testdemo.R;
import com.zy.testdemo.base.BaseActivity;
import com.zy.testdemo.module.contacts.adapter.ImportContactsAdapter;
import com.zy.testdemo.module.contacts.bean.ContactsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *    @author : Zeyo
 *     e-mail : zengyongsun@163.com
 *     time   : 2017/12/01
 *     desc   : 从手机导入联系人
 *     联系人是一个表
 *     联系人号码是一个表（一个联系人，多个号码，多条数据）
 *
 *     version: 1.0
 * </pre>
 */
public class ImportContactsActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ImportContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_contacts);

        recyclerView = findViewById(R.id.contacts_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsAdapter = new ImportContactsAdapter();
        recyclerView.setAdapter(contactsAdapter);

        contactsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectItem(adapter, position);
            }
        });

        contactsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                selectItem(adapter, position);
            }
        });
    }

    private void selectItem(BaseQuickAdapter adapter, int position) {
        ContactsBean contactsBean = (ContactsBean) adapter.getItem(position);
        if (contactsBean.isSelect()) {
            contactsBean.setSelect(false);
        } else {
            contactsBean.setSelect(true);
            contactsBean.setPhoneNumber(getPhoneNum(contactsBean.getContactsId()));
        }
        adapter.notifyDataSetChanged();
    }

    public void getContacts(View v) {
//        startActivity(new Intent(this, MainActivity.class));
//        contactsAdapter.setNewData(readContacts());
        contactsAdapter.setNewData(readContacts2());
    }

    public List<ContactsBean> readContacts() {
        List<ContactsBean> list = new ArrayList<>();
        Cursor cursor = null;

        //获取内容提供器
        ContentResolver contentResolver = getContentResolver();
        //查询联系人数据  -- projection返回所有的字段，是低效的
        cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null, null);

        assert cursor != null;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
//            list.add(new ContactsBean(id, name, phone));
        }
        cursor.close();
        return list;
    }

    private static final String[] PHONES_PROJECTION = new String[]{
        ContactsContract.CommonDataKinds.Phone.NUMBER,
    };

    private static final String[] PHONES_PROJECTION1 = new String[]{
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.DISPLAY_NAME,};

    public List<ContactsBean> readContacts2() {
        List<ContactsBean> list = new ArrayList<>();
        Cursor cursor = null;

        //获取内容提供器
        ContentResolver contentResolver = getContentResolver();
        //查询联系人数据  -- projection返回所有的字段，是低效的
        cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
            PHONES_PROJECTION1, null, null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            list.add(new ContactsBean(id, name));
        }
        cursor.close();
        return list;
    }

    public List<String> getPhoneNum(String id) {
        List<String> phoneList = new ArrayList<>();
        Cursor cursor = null;
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            PHONES_PROJECTION, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
            null, null);
        while (phones.moveToNext()) {
            String phone = phones.getString(0);
            phoneList.add(phone);
        }
        phones.close();
        return phoneList;
    }

}
