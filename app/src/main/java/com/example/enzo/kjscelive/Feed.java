package com.example.enzo.kjscelive;


import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by enzo on 8/9/2017.
 */

public class Feed implements FeedConstants {
    @SerializedName("card_id")
    private long mFeedId;//unique id for the card
    @SerializedName("title")
    private String mFeedTitle;// title of the new feed
    @SerializedName("priority")
    private int mFeedPriority;//it is the priorit of the feed
    @SerializedName("content")
    private String mFeedContent;//content description
    @SerializedName("card_date")
    private String mFeedDate;//date of event/news
    @SerializedName("venue")
    private String mFeedVenue;//venue of the feed



    @SerializedName("publisher")
    private long mFeedPublisherId;//unique id given to the publisher
    @SerializedName("type")
    private int mFeedType;//it indicates if the feed type is event or a card
    @SerializedName("has_multiple_images")
    private int mFeedHasMultipleImages;//its 1 if the feed has more than one image to show current value is 0
    @SerializedName("name")
    private String mFeedPublisherName;//name of the publisher
    @SerializedName("email")
    private String mFeedPublisherEmail;//email address of the publisher
    @SerializedName("committee_name")
    private String mFeedCommittee;//name of the publishing committee
    @SerializedName("image")
    private String mFeedImageUrl;//url to the description image
    @SerializedName("link")
    private List<String> mFeedLinks;//related links
    @SerializedName("phone_no")
    private List<String> mPhone;//contact numbers
    @SerializedName("is_liked")
    private boolean mFeedIsLiked;//true if the feed is liked by the user

    private boolean mIsBookmarked;//true if the event is added in the calendar

    public void setLiked(boolean liked) {
        mFeedIsLiked = liked;
    }

    public boolean isLiked() {

        return mFeedIsLiked;
    }

    private String mFeedTime;//holds the time for the feed

    public void setBookmarked(boolean bookmarked) {
        mIsBookmarked = bookmarked;
    }

    public boolean isBookmarked() {

        return mIsBookmarked;
    }


    public void setFeedTime(String feedTime) {
        mFeedTime = feedTime;
    }

    public String getFeedTime() {

        return mFeedTime;
    }

    private String mImageUrl;//url to the image
    public long getFeedId() {
        return mFeedId;
    }

    public String getFeedTitle() {
        return mFeedTitle;
    }

    public int getFeedPriority() {
        return mFeedPriority;
    }

    public String getFeedContent() {
        return mFeedContent;
    }

    public String getFeedDate() {
        return mFeedDate;
    }

    public String getFeedVenue() {
        return mFeedVenue;
    }

    public long getFeedPublisherId() {
        return mFeedPublisherId;
    }

    public int getFeedType() {
        return mFeedType;
    }

    public int getFeedHasMultipleImages() {
        return mFeedHasMultipleImages;
    }

    public String getFeedPublisherName() {
        return mFeedPublisherName;
    }

    public String getFeedPublisherEmail() {
        return mFeedPublisherEmail;
    }

    public String getFeedCommittee() {
        return mFeedCommittee;
    }

    public String getFeedImageUrl() {
        return mFeedImageUrl;
    }

    public List<String> getFeedLinks() {
        return mFeedLinks;
    }

    public List<String> getPhone() {
        return mPhone;
    }

    public void setFeedId(long feedId) {
        mFeedId = feedId;
    }

    public void setFeedTitle(String feedTitle) {
        mFeedTitle = feedTitle;
    }

    public void setFeedPriority(int feedPriority) {
        mFeedPriority = feedPriority;
    }

    public void setFeedContent(String feedContent) {
        mFeedContent = feedContent;
    }

    public void setFeedDate(String feedDate) {
        try{
            Date temp = DATE_SDF.parse(feedDate);
            mFeedDate = FEED_DATE_SDF.format(temp).toString();
            mFeedTime=FEED_TIME_SDF.format(temp).toString();
            Log.d(TAG,"date ="+mFeedDate );
        }
        catch(ParseException exception){
            Log.d(TAG,"date = parse exception");
            mFeedDate = feedDate;
        }

    }

    public void setFeedVenue(String feedVenue) {
        mFeedVenue = feedVenue;
    }

    public void setFeedPublisherId(long feedPublisherId) {
        mFeedPublisherId = feedPublisherId;
    }

    public void setFeedType(int feedType) {
        mFeedType = feedType;
    }

    public void setFeedHasMultipleImages(int feedHasMultipleImages) {
        mFeedHasMultipleImages = feedHasMultipleImages;
    }

    public void setFeedPublisherName(String feedPublisherName) {
        mFeedPublisherName = feedPublisherName;
    }

    public void setFeedPublisherEmail(String feedPublisherEmail) {
        mFeedPublisherEmail = feedPublisherEmail;
    }

    public void setFeedCommittee(String feedCommittee) {
        mFeedCommittee = feedCommittee;
    }

    public void setFeedImageUrl(String feedImageUrl) {
        mFeedImageUrl = feedImageUrl;
    }

    public void setFeedLinks(List<String> feedLinks) {
        mFeedLinks = feedLinks;
    }

    public void setPhone(List<String> phone) {
        mPhone = phone;
    }
    public boolean isNews(){
        return (mFeedType==NEWS_CARDS);
    }

    public String toString(){
        String title="";
        title=title+mFeedTitle+"\n"+mFeedVenue+"\n"+mFeedContent+"\n"+(isNews());
        return title;
    }
    public void setFeed(JSONObject obj) throws JSONException {
        setFeedId(obj.getLong("card_id"));
        setFeedTitle(obj.getString("title"));
        setFeedPriority(obj.getInt("priority"));
        setFeedContent(obj.getString("content"));
        setFeedDate(obj.getString("card_date"));
        setFeedVenue(obj.getString("venue"));
        setFeedPublisherId(obj.getLong("publisher"));
        setFeedType(obj.getInt("type"));
        setFeedTitle(obj.getString("title"));
        setFeedHasMultipleImages(obj.getInt("has_multiple_images"));
        setFeedPublisherName(obj.getString("name"));
        setFeedPublisherEmail(obj.getString("email"));
        Log.d("URL","inside feed url = "+getFeedImageUrl());
        setFeedCommittee(obj.getString("committee_name"));
        setFeedImageUrl(obj.getString("image"));
        JSONArray tempArray=obj.getJSONArray("link");
        ArrayList<String> tempList=new ArrayList<>();
        for(int j=0;j<tempArray.length();j++){
            tempList.add(tempArray.getString(j));
        }
        setFeedLinks(tempList);
        tempArray=obj.getJSONArray("link");
        tempList=new ArrayList<>();
        for(int j=0;j<tempArray.length();j++){
            tempList.add(tempArray.getString(j));
        }
        setPhone(tempList);
        String isLiked = obj.getString("is_liked");
        if(isLiked.equals("0")){
            setLiked(false);
        }
        else{
            setLiked(true);
        }
        Log.d("LIKE","liked = "+isLiked()+" json value = "+isLiked);
        Log.i(TAG,"FEED: "+toString());
    }
}
