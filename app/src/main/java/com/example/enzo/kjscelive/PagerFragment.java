package com.example.enzo.kjscelive;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by enzo on 8/28/2017.
 */

public class PagerFragment extends Fragment implements FeedConstants{

    public static PagerFragment newInstance(){
        return new PagerFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_pager,container,false);
        //setting the view pager
        ViewPager mViewPager =(ViewPager)v.findViewById(R.id.pager);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                int listType=SHOW_CARDS;
                switch(position){
                    case 1:
                        listType=SHOW_EVENTS;
                        Log.i(TAG,"position = "+position+" list type ="+listType);
                        break;
                    case 2:
                        listType=SHOW_NEWS;
                        Log.i(TAG,"position = "+position+" list type ="+listType);
                        break;
                    case 0:
                        listType=SHOW_CARDS;
                        Log.i(TAG,"position = "+position+" list type ="+listType);
                        break;
                }
                return FeedFragment.newInstance(getActivity(),listType);
            }
            @Override
            public int getCount() {
                return NO_OF_TABS;
            }
            @Override
            public CharSequence getPageTitle(int position){
                switch(position){
                    case 0:
                        return "Feeds";
                    case 1:
                        return "Events";
                    case 2:
                        return "News";
                }
                return "";
            }
        });
        TabLayout mTabLayout = (TabLayout)v.findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        return v;
    }
}
