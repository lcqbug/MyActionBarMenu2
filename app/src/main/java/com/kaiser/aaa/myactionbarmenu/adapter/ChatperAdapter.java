package com.kaiser.aaa.myactionbarmenu.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kaiser.aaa.myactionbarmenu.fragment.First_Fragment;
import com.kaiser.aaa.myactionbarmenu.fragment.SecondFragment;
import com.kaiser.aaa.myactionbarmenu.fragment.ThirtFragment;

//main的viewPager的适配器
public class ChatperAdapter extends FragmentPagerAdapter {
	private static final String[] chapter = { "周边游玩", "听说","我的"};


	public ChatperAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
        Fragment fragment=null;
        switch (arg0){
            case 0:
                fragment = new First_Fragment();
               break;

            case 1:
                fragment=new SecondFragment();
               break;
            case 2:
                fragment = new ThirtFragment();
              break;

        }
        Bundle bundle = new  Bundle();
        bundle.putString("chapter", chapter[arg0]);
        fragment.setArguments(bundle);
        return fragment;


	}
    //返回值为需要适配的页面有多少个。
	@Override
	public int getCount() {
		return chapter.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return chapter[position];
	}

}
