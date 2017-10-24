package com.example.enzo.kjscelive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class FeedPagerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,FeedConstants {




    public static Intent newIntent(Context context){
        Intent i=new Intent(context,FeedPagerActivity.class);
        return i;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_pager_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*R.id.hamburger is made the default drawer icon
        * Button click here will open the navigation drawer*/
        final View hamburger=findViewById(R.id.hambuger);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (/*drawer.isDrawerOpen(GravityCompat.START)*/drawer.isDrawerOpen(Gravity.RIGHT)){
                    //drawer.closeDrawer(GravityCompat.START);
                    drawer.closeDrawer(Gravity.RIGHT);
                    //v.animate().rotation(270).setDuration(200);
                    Log.d("animator","inside close drawer");
                } else {
                    //super.onBackPressed();
                    //v.animate().rotation(90).setDuration(200);
                    //drawer.openDrawer(GravityCompat.START);
                    drawer.openDrawer(Gravity.RIGHT);
                    Log.d("animator","inside open drawer");
                }
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hamburger.animate().rotation(270).setDuration(HAMBURGER_ICON_ANIMATION_DELAY);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                hamburger.animate().rotation(0).setDuration(HAMBURGER_ICON_ANIMATION_DELAY);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false); //disable "hamburger to arrow" drawable
        //toggle.setHomeAsUpIndicator(R.mipm); //set your own
        /******end of code **********/


        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.pager_container);
        if (f == null){
            f= PagerFragment.newInstance();
            fm.beginTransaction().add(R.id.pager_container,f).commit();
        }
        DataList dataList=DataList.getInstance(getApplicationContext());
        DataCommunicator dc =DataCommunicator.getInstance(getApplicationContext());
        String url=dc.buildUrlCard("http://"+IpClass.getInstance().getIp()+FeedConstants.URL_FEED,
                IpClass.getInstance().getEmail());
        dc.insertFeedList(url);
        url=dc.buildUrlNews("http://"+IpClass.getInstance().getIp()+FeedConstants.URL_FEED,
                IpClass.getInstance().getEmail());
        dc.insertNewsList(url);
        url=dc.buildUrlEvent("http://"+IpClass.getInstance().getIp()+FeedConstants.URL_FEED,
                IpClass.getInstance().getEmail());
        dc.insertEventList(url);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (/*drawer.isDrawerOpen(GravityCompat.START*/drawer.isDrawerOpen(Gravity.RIGHT)) {
            /*drawer.closeDrawer(GravityCompat.START);*/drawer.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.feed_pager_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //returns true if a second fragment above the view pager already exists
    private boolean isDoubleFragment(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentByTag(DOUBLE_FRAGMENT_TAG);
        return  f==null?false:true;
    }
    //adds a new fragment if isDoubleDragment returns true else pops the backstack and then adds new fragment
    private void showFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        if(isDoubleFragment()){
            Log.e(TAG,"gonna replace it");
//            fm.beginTransaction().replace(R.id.pager_container,fragment,DOUBLE_FRAGMENT_TAG)
//                    .addToBackStack(DOUBLE_FRAGMENT_TAG).commit();
            fm.popBackStack();//this statement pops the existing fragment from the backstack

        }
        fm.beginTransaction().add(R.id.pager_container,fragment,DOUBLE_FRAGMENT_TAG)
                .addToBackStack(DOUBLE_FRAGMENT_TAG).commit();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment f = null;
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.gallery) {
            f= StudentBodyFragment.newInstance(0);
        } else if (id == R.id.about_kjsce) {
            f = StudentBodyFragment.newInstance(0);
        } else if (id == R.id.csi) {
            f = StudentBodyFragment.newInstance(0);
        } else if (id == R.id.ieee) {
            f = StudentBodyFragment.newInstance(0);
        } else if (id == R.id.nav_send) {

        }
        if(f!=null){
            showFragment(f);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        drawer.closeDrawer(Gravity.RIGHT);
        return true;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ImageManager.getInstance(getApplicationContext()).deleteFiles();
    }




}
