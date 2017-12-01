package com.zy.testdemo.module.contacts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
    }

    public void getContacts(View v) {
        contactsAdapter.setNewData(readContacts());
    }

    public List<ContactsBean> readContacts() {
        List<ContactsBean> list = new ArrayList<>();
        Cursor cursor = null;

        //获取内容提供器
        ContentResolver contentResolver = getContentResolver();
        //查询联系人数据
        cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null, null);

        assert cursor != null;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
            list.add(new ContactsBean(id, name, phone));
        }
        cursor.close();
        return list;
    }

}
