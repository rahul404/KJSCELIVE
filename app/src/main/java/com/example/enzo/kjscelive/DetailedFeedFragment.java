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

import com.example.enzo.kjscelive.firebaseUtils.realtimeDatabase.DatabaseCommunicator;

import java.util.ArrayList;


/**
 * Created by enzo on 8/29/2017.
 */

public class DetailedFeedFragment extends Fragment implements FeedConstants{
    private static final String ARGUMENT_INDEX="com.example.enzo.kjscelive.DetailedFeedFragment.INDEX";//key to hold index of feed in list
    private static final String ARGUMENT_LIST_TYPE="com.example.enzo.kjscelive.DetailedFeedFragment.LIST_TYPE";//denotes type of list where the feed belongs to
    private Feed mFeed;//holds the feed whose information is rendered on the screen
    //returns instance of DetailedFeedFragment instance with argument appropriately set
    public static DetailedFeedFragment newInstance(int index,int listType){
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_INDEX,index);
        args.putInt(ARGUMENT_LIST_TYPE,listType);
        DetailedFeedFragment f = new DetailedFeedFragment();
        f.setArguments(args);
        return f;
    }

    private void initTitle(View v){
        //settting the title
        TextView titleTextView = (TextView) v.findViewById(R.id.detailed_feed_event_title_row);
        titleTextView.setText(mFeed.getFeedTitle());
    }

    private ImageView initImage(View v){
        //loading image in to the view
        final ImageView imageView = (ImageView)v.findViewById(R.id.detailed_feed_event_image_row);
        final String imageUrl = mFeed.getFeedImageUrl();
        DataCommunicator.getInstance(getActivity()).loadImage(imageView,imageUrl);
        return imageView;
    }
    private void initContent(View v){
        //loading the content in the view
        TextView contextTextView=(TextView) v.findViewById(R.id.detailed_feed_event_content_row);
        contextTextView.setText(mFeed.getFeedContent());
    }
    private void initCommittee(View v){
        //loading the committee name
        TextView committeeTextView = (TextView)v.findViewById(R.id.detailed_feed_event_committee_name);
        committeeTextView.setText(mFeed.getFeedCommittee());
    }
    private void initPublisher(View v){
        //loading the publisher name
        TextView publisherTextView = (TextView)v.findViewById(R.id.detailed_feed_event_contact_name);
        publisherTextView.setText(mFeed.getFeedPublisherName());
    }
    private void initPublisherEmail(View v){
        //loading the publisher email
        TextView emailTextView = (TextView)v.findViewById(R.id.detailed_feed_event_contact_email);
        emailTextView.setText(mFeed.getFeedPublisherEmail());
    }
    private void initPublisherContact(View v){
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
        //Toast.makeText(getActivity(),"phone ="+x,Toast.LENGTH_SHORT).show();
        phoneTextView.setText("Contact : "+x);
    }
    private void initDate(View v){
        //        //loading the date
        TextView dateTextView = (TextView)v.findViewById(R.id.detailed_feed_event_date);
        dateTextView.setText("Date:"+mFeed.getFeedDate());
        //dateTextView.setText("hi bro what up");
        dateTextView.setVisibility(View.VISIBLE);
        //Log.d("LISTENER","date = "+dateTextView.getText());
    }
    private void initTime(View v){
        //loading the time
        TextView timeTextView = (TextView)v.findViewById(R.id.detailed_feed_event_time);
        timeTextView.setText("Time:"+mFeed.getFeedTime());
    }
    private void initVenue(View v){
        //        //loading the venue
        TextView venueTextView = (TextView)v.findViewById(R.id.detailed_feed_event_venue);
        venueTextView.setText("Venue : "+mFeed.getFeedVenue());
    }
    private void initLinks(View v){
        //loading the links
        TextView linkTextView = (TextView)v.findViewById(R.id.detailed_feed_event_links);
        ArrayList<String> arr= (ArrayList)mFeed.getFeedLinks();
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
        linkTextView.setText("Links : \n"+x);
    }
    private void initShareIcon(View v,final ImageView imageView){
                //setting the share icon
        ImageView mShareImageView=(ImageView)v.findViewById(R.id.detailed_feed_event_share_row);
        final String imageUrl = mFeed.getFeedImageUrl();
        mShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    }
    private void initAddToCalendar(View v){
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
    }
    //changes the colour of favorite star
    private void changeFavoriteIcon(ImageView image){
        if(mFeed.isLiked()){
            image.setImageResource(R.mipmap.ic_fav_orange);
        }
        else{
            image.setImageResource(R.mipmap.ic_fav);
        }
    }
    private void initCircledFavorite(View v){
        final ImageView favoriteImage = (ImageView) v.findViewById(R.id.circled_favorite);
        final String email = IpClass.getInstance().getEmail();
        if(email !=null || email.length()!=0){
            Toast.makeText(getActivity(),"email = "+email,Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getActivity(),"email = nul h bro",Toast.LENGTH_SHORT).show();
        }
        changeFavoriteIcon(favoriteImage);
        favoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFeed.isLiked()){
                    DataCommunicator.getInstance(getActivity()).sendUnLiked(mFeed.getFeedId(),email);
                    mFeed.setLiked(false);
                }
                else{
                    DataCommunicator.getInstance(getActivity()).sendLiked(mFeed.getFeedId(),email);
                    mFeed.setLiked(true);
                }
                changeFavoriteIcon(favoriteImage);
                DatabaseCommunicator databaseCommunicator = DatabaseCommunicator.newInstance(getActivity());
                databaseCommunicator.performLike(mFeed.getFeedId(),mFeed.isLiked());
                Bundle args = getArguments();
                int index=0;
                int listType=SHOW_CARDS;
                if(args != null){
                    index= args.getInt(ARGUMENT_INDEX);
                    listType=args.getInt(ARGUMENT_LIST_TYPE);
                }
                //this line of code will update the like status of current feed if present in other list
                DataList.getInstance(getActivity()).updateLikeStatus(index,listType);
            }
        });
    }
    // this method has to encapsulate the detailed view of the feed
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
        initTitle(v);
        ImageView imageView = initImage(v);
        initContent(v);
        initCommittee(v);
        initPublisher(v);
        initPublisherEmail(v);
        initPublisherContact(v);
        initDate(v);
        initTime(v);
        initVenue(v);
        initLinks(v);
        initShareIcon(v,imageView);
        initAddToCalendar(v);
        initCircledFavorite(v);
        return v;
    }
}
