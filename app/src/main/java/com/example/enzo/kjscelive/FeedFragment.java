package com.example.enzo.kjscelive;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enzo.kjscelive.DataList.FeedListChangeListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enzo on 8/9/2017.
 */

public class FeedFragment extends Fragment implements FeedConstants{

    private static final String ARGUMENT="com.example.enzo.kjscelive.FeedFragment";
    private int mDataListType;//hold the constant that represents the data list type SHOW_CARDS ,SHOW_NEWS and SHOW_EVENTS
    private int posChanged=-1;//hold the position of selected card
    private RecyclerView mFeedRecyclerView; // this holds a reference to the recycler view in the fragment
    private FeedAdapter mAdapter;//acts as the adapter for the recycler view
    private Handler mResponseHandler;//handler to perform ui update,used by the DataList class .
    private FeedListChangeListener mFeedListChangeListener;//callback invoked when new Feeds are added to the DataList (data set changed)
    private EndlessRecyclerViewScrollListener scrollListener; //listener for endless list
    SwipeRefreshLayout mSwipeRefresh;// swipre refresh layout for pull down and refresh
    public FeedFragment(){
    }
    //use this method to create fragments
    public static FeedFragment newInstance(Context context){
        return new FeedFragment();
    }
    /*************************** listType ******************************
     *represents any one of the constants from the SHOW_CARDS ,SHOW_NEWS and SHOW_EVENTS
     * */
    public static FeedFragment newInstance(Context context,int listType){
        Bundle args=new Bundle();
        args.putInt(ARGUMENT,listType);
        Log.d(TAG,"argument set to "+listType);
        FeedFragment f=new FeedFragment();
        f.setArguments(args);
        Log.d(TAG,"args="+args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.fragment_feed,container,false);
        //following code initializes the recycler view
        mFeedRecyclerView=(RecyclerView)v.findViewById(R.id.feed_recycler_view);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mFeedRecyclerView.setLayoutManager(lm);
        //getting the handler reference and setting the FeedListChangeListener
        Context context=getActivity().getApplicationContext();
        mResponseHandler=new Handler(Looper.getMainLooper());
        DataList.getInstance(context).setResponseHandler(mResponseHandler);
        mFeedListChangeListener=new FeedListChangeListener() {
            @Override
            public void onFeedListChanged(int type) {
                Log.d("LISTENER"," sendUiUpdateMessage was called  ");
                if(type==COULDNT_REFRESH){
                    Toast.makeText(getActivity(),R.string.couldnt_refresh,Toast.LENGTH_SHORT).show();
                    mSwipeRefresh.setRefreshing(false);
                }
                updateUI();
                if(type==INSERTION_BEGIN){
                    mSwipeRefresh.setRefreshing(false);
                }
            }
        };
        DataList.getInstance(context).setFeedListChangeListener(mFeedListChangeListener);
        //getting the argument for list type
        Bundle args=getArguments();
        if(args!=null){
            mDataListType=args.getInt(ARGUMENT);
        }
        else{
            mDataListType=SHOW_CARDS;
        }
        //setting the endless scroll listener
        scrollListener = new EndlessRecyclerViewScrollListener(lm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view,int scrollType) {
                Log.d("LISTENER","on down scroll was called on "+mDataListType );
                // Triggered only when new data needs to be appended to the list
                Log.d(TAG,"last page ="+page);
                Context c=getActivity();
                DataCommunicator dc=DataCommunicator.getInstance(c);
                String url="";
                long cardId=0;
                if(scrollType == SCROLL_UP){
                    url=dc.buildUrlYoungerCards(DataList.getInstance(c).getMaxOfType(mDataListType),mDataListType);
                }
                else if(scrollType == SCROLL_DOWN){
                    url=dc.buildUrlOlderCards(DataList.getInstance(c).getMinOfType(mDataListType),mDataListType);
                }
                dc.insertList(url,mDataListType);
                Log.d("LISTENER","on down scroll was called on "+mDataListType);
            }
        };
        mFeedRecyclerView.addOnScrollListener(scrollListener);
        mSwipeRefresh =(SwipeRefreshLayout)v.findViewById(R.id.feed_swipe_refresh);
        setSwipeRefresh();
        return v;
    }
    //initializes everything related to swipe refresh layout
    private void setSwipeRefresh(){
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Context c=getActivity();
                DataCommunicator dc=DataCommunicator.getInstance(c);
                String url=dc.buildUrlYoungerCards(DataList.getInstance(c).getMaxOfType(mDataListType),mDataListType);
                Log.d(TAG,"URL= "+url+" min value =" +DataList.getInstance(c).getMinOfType(mDataListType));
                dc.insertList(url,mDataListType);
                Log.d("LISTENER","on down scroll was called on "+mDataListType);
            }
        });
        Resources res=getResources();
        mSwipeRefresh.setColorSchemeColors(res.getColor(R.color.colorBrightOrange));
    }
    //this methods set the recycler adapter if not set and updates the data set of recycler view
    private void updateUI()
    {
        DataList dataList=DataList.getInstance(getActivity());
        List<Feed> feeds=dataList.getList(mDataListType);
        if(mAdapter==null){
            mAdapter=new FeedAdapter(feeds);
            mFeedRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.notifyDataSetChanged();
            //mAdapter.notifyItemChanged(posChanged);
        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(TAG,"rendered visible "+mDataListType);
        updateUI();
    }


    private class FeedHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private int mIndex;
        private Feed mFeed;
        private ImageView mImageView;
        private TextView mTitleTextView;
        private TextView mVenueTextView;
        private TextView mContentTextView;
        private ImageView mShareImageView;
        private ImageView mAddToCalendarImageView;
        private FeedHolder(View itemView)
        {
            super(itemView);
            mTitleTextView=(TextView)itemView.findViewById(R.id.feed_event_title_row);
            mVenueTextView=(TextView)itemView.findViewById(R.id.feed_event_small_venue_row);
            mContentTextView=(TextView)itemView.findViewById(R.id.feed_event_content_row);
            mImageView=(ImageView)itemView.findViewById(R.id.feed_event_image_row);
            mShareImageView=(ImageView)itemView.findViewById(R.id.feed_event_share_row);
            mAddToCalendarImageView=(ImageView)itemView.findViewById(R.id.feed_event_add_to_calendar_row);
//            ColorMatrix cm = new ColorMatrix();
//            cm.setSaturation(0);
//            ColorMatrixColorFilter filter=new ColorMatrixColorFilter(cm);
//            mImageView.setColorFilter(filter);
            itemView.setOnClickListener(this);
        }
        public void bindCrime(Feed feed,int index)
        {
            mIndex=index;
            mFeed=feed;
            mTitleTextView.setText(mFeed.getFeedTitle());
            mVenueTextView.setText(mFeed.getFeedVenue());
            String content=mFeed.getFeedContent();
            mContentTextView.setText(content.substring(0,content.indexOf(' ',262)));
            final String imageUrl = "http://"+IpClass.getInstance().getIp()+"/images" +
                    "/"+((mFeed.getFeedId()%6)+1)+".jpg";
//            Picasso.with(getActivity()).load(imageUrl).fit().memoryPolicy(MemoryPolicy.NO_CACHE )
//                    .networkPolicy(NetworkPolicy.NO_CACHE).into(mImageView);//TODO :change this code check if picasso or volley
            Picasso.with(getActivity()).load(imageUrl).memoryPolicy(MemoryPolicy.NO_CACHE )
                    .networkPolicy(NetworkPolicy.NO_CACHE).into(mImageView);//TODO :change this code check if picasso or volley

            mShareImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: open sharable options

                    Log.d("INTENT","inside share image view");
                    //writing the image to the cache
                    Bitmap bm = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
                    //String extension = imageUrl.endsWith("jpg")?"jpg":"png";
                    String extension = imageUrl.substring(imageUrl.lastIndexOf(".")+1,imageUrl.length());
                    String path = ImageManager.getInstance(getActivity().getApplicationContext())
                            .writeToCacheFromURL(mFeed.getFeedId()+"",bm,extension);
                    //getting the intent
                    Intent i=null;
                    Log.d("INTENT","before try");
                    try{
                        Log.d("INTENT","inside try");
                        i = ImplicitIntentGenerator.getShareableIntent("Sent by KJSCELIVE",path,getActivity().getApplicationContext());
                        Log.d("INTENT","after intent creation call");
                        if(i!=null){
                            Log.d("INTENT","inside start chooser");
                            startActivity(Intent.createChooser(i,getResources()
                                    .getString(R.string.share_intent_dialog_message)));
                        }
                        else{
                            Log.d("INTENT","inside make toast");
                            Toast.makeText(getActivity(),R.string.share_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch(Exception e){
                        Log.e("INTENT"," Shareable intent error "+"\n intent="+i);
                        e.printStackTrace();
                    }

                }
            });
            if(mFeed.isBookmarked()){
                mAddToCalendarImageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_calendar_orange));
            }
            else{
                mAddToCalendarImageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_calendar));
            }
            if(mFeed.isNews()){
                //render add to calendar invisible
                mAddToCalendarImageView.setVisibility(View.GONE);
                itemView.setBackgroundColor(getResources().getColor(R.color.colorNewsBackground));
            }
            else{
                mAddToCalendarImageView.setVisibility(View.VISIBLE);
                itemView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            }
            mAddToCalendarImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i= ImplicitIntentGenerator.getCalendarIntent(mFeed);
                    if(i!=null){
                        mFeed.setBookmarked(true);
                        mAddToCalendarImageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_calendar));
                        getActivity().startActivity(i);
                        //TODO do startACtivityForResult
                    }
                    else{
                        Toast.makeText(getActivity(),R.string.add_to_calendar_error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        @Override
        public void onClick(View view)
        {

            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment f = fm.findFragmentByTag(DOUBLE_FRAGMENT_TAG);
            boolean b=  f==null?false:true;

            if(b){
                Log.e(TAG,"gonna replace it");
                fm.beginTransaction().replace(R.id.pager_container,DetailedFeedFragment.newInstance(mIndex,mDataListType),DOUBLE_FRAGMENT_TAG)
                        .addToBackStack(DOUBLE_FRAGMENT_TAG).commit();
            }
            else{
                fm.beginTransaction().add(R.id.pager_container,DetailedFeedFragment.newInstance(mIndex,mDataListType),DOUBLE_FRAGMENT_TAG)
                        .addToBackStack(DOUBLE_FRAGMENT_TAG).commit();
            }
        }
    }

    private class FeedAdapter extends RecyclerView.Adapter<FeedHolder>
    {
        private List<Feed> mFeeds;

        public FeedAdapter(List<Feed> feeds)
        {
            mFeeds=feeds;
        }


        @Override
        //the viewType is for creating different view in the list
        public FeedHolder onCreateViewHolder(ViewGroup parent ,int viewType)
        {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view =inflater.inflate(R.layout.row_feed,parent,false);
            return new FeedHolder(view);
        }

        @Override
        public void onBindViewHolder(FeedHolder  holder,int position)
        {
            Feed crime=mFeeds.get(position);
            holder.bindCrime(crime,position);
        }
        @Override
        public int getItemCount()
        {
            return mFeeds.size();
        }
    }
}
