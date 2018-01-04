package com.zy.testdemo.module.contacts;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.zy.testdemo.R;
import com.zy.testdemo.base.BaseActivity;
import com.zy.testdemo.module.contacts.adapter.ImportContactsAdapter;
import com.zy.testdemo.module.contacts.bean.ContactsBean;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

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
public class ImportContactsActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private RecyclerView recyclerView;
    private ImportContactsAdapter contactsAdapter;
    private ImageView imageView;

    public void rotateAnim() {
        Animation anim =new RotateAnimation(0f, 180f,Animation.RELATIVE_TO_SELF,
             0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        // 设置保持动画最后的状态
        anim.setFillAfter(true);
        // 设置动画时间
        anim.setDuration(300);
        // 设置插入器
        anim.setInterpolator(new AccelerateInterpolator());
        imageView.startAnimation(anim);
    }

    private void rotaObj(){
        ObjectAnimator.ofFloat(imageView,"rotationX",0,180).start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_contacts);

        imageView = findViewById(R.id.top_down);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                rotateAnim();
                rotaObj();
            }
        });
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将结果转发给EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private static final int RD_CONTACTS = 100;

    @AfterPermissionGranted(RD_CONTACTS)
    private void metodRequestContactsPermission() {
        String perm = Manifest.permission.READ_CONTACTS;
        if (EasyPermissions.hasPermissions(this, perm)) {
            Logger.d("有权限");
        } else {
            EasyPermissions.requestPermissions(this, "需要联系人读取权限",
                RD_CONTACTS, perm);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            Toast.makeText(this, "从系统设置页面回来", Toast.LENGTH_SHORT).show();
        }

    }
}
