package com.example.enzo.kjscelive;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by enzo on 8/29/2017.
 */

public class DetailedFeedFragment extends Fragment implements FeedConstants{
    private static final String ARGUMENT_INDEX="com.example.enzo.kjscelive.DetailedFeedFragment.INDEX";//key to hold index of feed in list
    private static final String ARGUMENT_LIST_TYPE="com.example.enzo.kjscelive.DetailedFeedFragment.LIST_TYPE";//denotes type of list where the feed belongs to
    private Feed mFeed;//holds the feed whose information is renderred on the screen
    //returns instance of DetailedFeedFragment instance with argument appropriately set
    public static DetailedFeedFragment newInstance(int index,int listType){
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_INDEX,index);
        args.putInt(ARGUMENT_LIST_TYPE,listType);
        DetailedFeedFragment f = new DetailedFeedFragment();
        f.setArguments(args);
        return f;
    }
    //TODO : this method has to encapsulate the detailed view of the feed
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        /***************************Setting the mFeed object reference***************************/
        Bundle args = getArguments();
        int index=0;
        int listType=SHOW_CARDS;
        if(args != null){
            index= args.getInt(ARGUMENT_INDEX);
            listType=args.getInt(ARGUMENT_LIST_TYPE);
        }
        mFeed=DataList.getInstance(getActivity()).getList(listType).get(index);
        View v = inflater.inflate(R.layout.detailed_feed,container,false);
        //settting the title
        TextView titleTextView = (TextView) v.findViewById(R.id.detailed_feed_event_title_row);
        titleTextView.setText(mFeed.getFeedTitle());
        //loading image in to the view
        final ImageView imageView = (ImageView)v.findViewById(R.id.detailed_feed_event_image_row);
        final String imageUrl = "http://"+IpClass.getInstance().getIp()+"/images" +
                "/"+((mFeed.getFeedId()%6)+1)+".jpg";
        Log.d("LISTENER",imageUrl);
//        Picasso.with(getActivity()).load(imageUrl).fit().memoryPolicy(MemoryPolicy.NO_CACHE )
//                .networkPolicy(NetworkPolicy.NO_CACHE).into(imageView);//TODO :change this code check if picasso or volley
        Picasso.with(getActivity()).load(imageUrl).memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE).into(imageView);//TODO :change this code check if picasso or volley
        //DataCommunicator.getInstance(getActivity()).loadImage(imageView,mFeed.getFeedImageUrl());
        //loading the content in the view
        TextView contextTextView=(TextView)v.findViewById(R.id.detailed_feed_event_content_row);
        contextTextView.setText(mFeed.getFeedContent());
        //loading the committee name
        TextView committeeTextView = (TextView)v.findViewById(R.id.detailed_feed_event_committee_name);
        committeeTextView.setText(mFeed.getFeedCommittee());
        //loading the publisher name
        TextView publisherTextView = (TextView)v.findViewById(R.id.detailed_feed_event_contact_name);
        publisherTextView.setText(mFeed.getFeedPublisherName());
        //loading the publisher email
        TextView emailTextView = (TextView)v.findViewById(R.id.detailed_feed_event_contact_email);
        emailTextView.setText(mFeed.getFeedPublisherEmail());
        //loading the publisher contact
        TextView phoneTextView = (TextView)v.findViewById(R.id.detailed_feed_event_contact_no);
        ArrayList<String> arr= (ArrayList)mFeed.getPhone();
        String x="";
        for(String temp:arr){
            x=x+temp+"\n";
        }
        if(x.length()<2){
            x="";
        }
        else{
            x=x.substring(0,x.length()-1);
        }
        phoneTextView.setText(x);
        //loading the date
        TextView dateTextView = (TextView)v.findViewById(R.id.detailed_feed_event_date);
        dateTextView.setText("Date:"+mFeed.getFeedDate());
        dateTextView.setVisibility(View.VISIBLE);
        Log.d("LISTENER","date = "+dateTextView.getText());
        //loading the time
        TextView timeTextView = (TextView)v.findViewById(R.id.detailed_feed_event_time);
        dateTextView.setText("Time:"+mFeed.getFeedTime());
        //loading the venue
        TextView venueTextView = (TextView)v.findViewById(R.id.detailed_feed_event_venue);
        dateTextView.setText(mFeed.getFeedVenue());
        //loading the links
        TextView linkTextView = (TextView)v.findViewById(R.id.detailed_feed_event_date);
        arr= (ArrayList)mFeed.getFeedLinks();
        x="";
        for(String temp:arr){
            x=x+temp+"\n";
        }
        if(x.length()<2){
            x="";
        }
        else{
            x=x.substring(0,x.length()-1);
        }
        linkTextView.setText(x);
        //setting the share icon
        ImageView mShareImageView=(ImageView)v.findViewById(R.id.detailed_feed_event_share_row);
        mShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: open sharable options
                Log.d("INTENT","inside share image view");
                //writing the image to the cache
                Bitmap bm = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                String extension = imageUrl.endsWith("jpg")?"jpg":"png";
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

        //setting the add to calendar icon
        ImageView mAddToCalendarImageView = (ImageView)v.findViewById(R.id.detailed_feed_event_add_to_calendar_row);
        if(mFeed.isNews()){
            //render add to calendar invisible
            mAddToCalendarImageView.setVisibility(View.GONE);
        }
        else{
            mAddToCalendarImageView.setVisibility(View.VISIBLE);
        }
        mAddToCalendarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= ImplicitIntentGenerator.getCalendarIntent(mFeed);
                if(i!=null){
                    getActivity().startActivity(i);
                }
                else{
                    Toast.makeText(getActivity(),R.string.add_to_calendar_error, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}
