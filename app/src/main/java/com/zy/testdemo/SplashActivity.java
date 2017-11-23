package com.zy.testdemo;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zy.testdemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *    @author : Zeyo
 *     e-mail : zengyongsun@163.com
 *     time   : 2017/11/20
 *     desc   : 闪屏页面
 *     version: 1.0
 * </pre>
 */
public class SplashActivity extends BaseActivity {

    private SplashAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initData();
        initView();
    }

    private void initData() {
        int pageSize = 4;
        List<View> pageList = new ArrayList<>();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        for (int i = 0; i < pageSize; i++) {
            View  view = layoutInflater.inflate(R.layout.splash_page_view_two,null);
            pageList.add(view);
        }
        adapter = new SplashAdapter(pageList);
    }

    private void initView() {
        ViewPager mSplashViewPager = findViewById(R.id.splash_view_pager);
        mSplashViewPager.setAdapter(adapter);
    }

    private class SplashAdapter extends PagerAdapter {

        SplashAdapter(List<View> viewList) {
            this.viewList = viewList;
        }

        private List<View> viewList;

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }
    }

}
