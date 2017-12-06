package com.zy.testdemo.module.contacts.adapter;

import android.support.v7.widget.AppCompatCheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zy.testdemo.R;
import com.zy.testdemo.module.contacts.bean.ContactsBean;

/**
 * <pre>
 *    @author : Zeyo
 *     e-mail : zengyongsun@163.com
 *     time   : 2017/12/01
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ImportContactsAdapter extends BaseQuickAdapter<ContactsBean, BaseViewHolder> {

    public ImportContactsAdapter() {
        super(R.layout.adapter_import_contacts_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactsBean item) {

        helper.setText(R.id.tvName, item.getContactsName());
        if (item.getPhoneNumber()!=null) {
            helper.setText(R.id.tvNumber, item.getPhoneNumber().toString());
        }
        helper.setText(R.id.tvId,item.getContactsId());

        AppCompatCheckBox checkBox = helper.getView(R.id.checkbox);
        helper.addOnClickListener(R.id.checkbox);
        checkBox.setChecked(item.isSelect());


    }
}
