package com.example.enzo.kjscelive;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class FeedActivity extends AppCompatActivity implements FeedConstants{


    public static Intent newIntent(Context context){
        Intent i=new Intent(context,FeedActivity.class);
        return i;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ViewPager mViewPager =(ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
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
                return FeedFragment.newInstance(FeedActivity.this,listType);
            }
            @Override
            public int getCount() {
                return NO_OF_TABS;
            }
        });
//        DataCommunicator dc =DataCommunicator.getInstance(getApplicationContext());
//        String url=dc.buildUrl("http://"+IpClass.getInstance().getIp()+FeedConstants.URL_FEED);
//        //Log.d(TAG,"before populate UI");
//        dc.populateFeedLists(url);
    }
}
