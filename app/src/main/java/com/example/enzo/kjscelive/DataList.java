package com.example.enzo.kjscelive;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enzo on 8/9/2017.
 */

public class DataList implements FeedConstants{
    private static final String TAG="TOKEN";
    private long mMaxFeedId=-1;// max value for feed list card id
    private long mMinFeedId=-1;// min value for feed list card id
    private long mMaxNewsId=-1;// max value for news list card id
    private long mMinNewsId=-1;// min value for news list card id
    private long mMaxEventId=-1;// max value for event list card id
    private long mMinEventId=-1;// min value for event list card id
    final private Object mLockObject = new Object();//object used for locking purpose (synchronized)
    RequestQueue mRequestQueue;//queuing the volley download request
    Handler mResponseHandler;//notifies the main thread that the list has been changed
    private Context mContext;//application context
    private ArrayList<Feed> mFeedList;//contains all the feeds/cards
    private ArrayList<Feed> mNewsList;//contains all the news
    private ArrayList<Feed> mEventList;//contains all the events
    private ArrayList<StudentBody> mStudentBodies=new ArrayList<>();//contains all the students body info
    //TODO:add entire functionality of students body
    private static DataList mDataList=null;//singleton reference
    private ArrayList<FeedListChangeListener> mFeedListChangeListeners=new ArrayList<>();//listeners to be implemented by the hosting fragment or activity
    private DataList(Context context){
        mContext=context;
        mRequestQueue= Volley.newRequestQueue(context);
        mFeedList=new ArrayList<>();
        mNewsList=new ArrayList<>();
        mEventList=new ArrayList<>();
        mStudentBodies=new ArrayList<>();
        StudentBody sb= new StudentBody();
        sb.setDescription("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?");
        sb.setName("Random Council Name");
        mStudentBodies.add(sb);
    }
    public static DataList getInstance(Context context){
        if(mDataList==null){
            mDataList=new DataList(context);
        }
        return mDataList;
    }
    //sets the response handler object upon which the UI update message is passed
    public void setResponseHandler(Handler handler){
        mResponseHandler=handler;
    }

    //method called when data set changes
    public void setFeedListChangeListener(FeedListChangeListener listener){
        mFeedListChangeListeners.add(listener);
    }

    //returns the requested list type
    public List<Feed> getList(int listType){
        switch(listType){
            case SHOW_NEWS:
                //Log.d(TAG,"returning mNews List "+mNewsList.get(0));
                return mNewsList;
            case SHOW_EVENTS:
                //Log.d(TAG,"returning Event List "+mEventList.get(0));
                return mEventList;
            default:
                //Log.d(TAG,"returning feed List "+mFeedList.get(0));
                return mFeedList;
        }
    }

    //appends the response to the specified list type
    public void appendList(String response,int listType){
        switch(listType){
            case SHOW_EVENTS:
                appendEvents(response);
                break;
            case SHOW_NEWS:
                appendNews(response);
                break;
            default:
                appendFeeds(response);
        }
    }

    //inserts  the response to the beginning of specified  list type
    public void insertList(String response,int listType){
        switch(listType){
            case SHOW_EVENTS:
                insertEvents(response);
                break;
            case SHOW_NEWS:
                insertNews(response);
                break;
            default:
                insertFeeds(response);
        }
    }

    //adds new cards to the end of the feed list
    public void appendFeeds(String response){
        addAfter(response,mFeedList);
        updateMinFeedId();
        updateMaxFeedId();
    }
    //adds new cards to the beginning of feed list
    public void insertFeeds(String response){
        addBefore(response,mFeedList);
        updateMaxFeedId();
        updateMinFeedId();
    }

    //add new news cards to the end of feed list
    public void appendNews(String response){
        addAfter(response,mNewsList);
        updateMinNewsId();
        updateMaxNewsId();
    }
    //add new news cards to the beginning of feed list
    public void insertNews(String response){
        addBefore(response,mNewsList);
        updateMaxNewsId();
        updateMinNewsId();
    }

    //add new news cards to the end of feed list
    public void appendEvents(String response){
        addAfter(response,mEventList);
        updateMinEventId();
        updateMaxEventId();
    }
    //add new news cards to the beginning of feed list
    public void insertEvents(String response){
        addBefore(response,mEventList);
        updateMaxEventId();
        updateMinEventId();
    }

    //adds the response json file at the end of specified list
    public void addAfter(String response,List<Feed> list){
        synchronized (mLockObject){
            try{
            JSONObject parent=new JSONObject(response);
            JSONArray array=parent.getJSONArray("cards");
            Log.i(TAG,"card array length: "+array.length());
            for(int i=0;i<array.length();i++){
                JSONObject obj=array.getJSONObject(i);
                Feed f=new Feed();
                f.setFeed(obj);
                list.add(f);
            }
        }
        catch(JSONException exception){
            Log.e(TAG,"EXCEPTION JSON"+exception);
        }
        sendUiUpdateMessage(INSERTION_END);
        }
    }
    //adds the response json file at the beginning of specified list
    public void addBefore(String response,List<Feed> list){
        synchronized (mLockObject){
            try{
                JSONObject parent=new JSONObject(response);
                JSONArray array=parent.getJSONArray("cards");
                Log.i(TAG,"card array length: "+array.length());
                for(int i=0;i<array.length();i++){
                    JSONObject obj=array.getJSONObject(i);
                    Feed f=new Feed();
                    f.setFeed(obj);
                    list.add(0+i,f);
                }
            }
            catch(JSONException exception){
                Log.e(TAG,"EXCEPTION JSON"+exception);
            }
            sendUiUpdateMessage(INSERTION_BEGIN);
        }
    }

    //method posts the update ui request on the main thread handler
    public void sendUiUpdateMessage(final int type){
        Log.d("LISTENER"," sendUiUpdateMessage was called  ");
        mResponseHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("LISTENER"," inside the thread posted on ui handler ");
                for(FeedListChangeListener listener:mFeedListChangeListeners){
                    listener.onFeedListChanged(type);
                }
            }
        });
    }


    //sets the mMinNewsId to its proper value
    public void updateMinNewsId(){
        if( mMinNewsId <=0 || mMinNewsId>mNewsList.get(mNewsList.size()-1).getFeedId() ){
            mMinNewsId=mNewsList.get(mNewsList.size()-1).getFeedId();
        }
        Log.d("MAXMIN","mMinNewsId = "+mMinNewsId+" arr[0] = "+mNewsList.get(mNewsList.size()-1).getFeedId());
    }

    //sets the mMinEventId to its proper value
    public void updateMinEventId(){
        if( mMinEventId <=0 || mMinEventId>mEventList.get(mEventList.size()-1).getFeedId() ){
            mMinEventId=mEventList.get(mEventList.size()-1).getFeedId();
        }
        Log.d("MAXMIN","mMinEventId = "+mMinEventId+" arr[0] = "+mEventList.get(mEventList.size()-1).getFeedId());
    }

    //sets the mMinEventId to its proper value
    public void updateMinFeedId(){
        if( mMinFeedId <=0 || mMinFeedId >mFeedList.get(mFeedList.size()-1).getFeedId() ){
            Log.d("LISTENER","min feed list id updated");
            mMinFeedId=mFeedList.get(mFeedList.size()-1).getFeedId();
        }
        Log.d("MAXMIN","mMinFeedId = "+mMinFeedId+" arr[0] = "+mFeedList.get(mFeedList.size()-1).getFeedId());
    }

    public void updateMaxNewsId(){
        if(  mMaxNewsId <mNewsList.get(0).getFeedId()){
            mMaxNewsId =mNewsList.get(0).getFeedId();
        }
        Log.d("MAXMIN","mMaxNewsId = "+mMaxNewsId+" arr[0] = "+mNewsList.get(0).getFeedId());
    }

    //sets the mMaxEventId to its proper value
    public void updateMaxEventId(){
        if( mMaxEventId<mEventList.get(0).getFeedId() ){
            mMaxEventId=mEventList.get(0).getFeedId();
        }
        Log.d("MAXMIN","mMaxEventId = "+mMaxEventId+" arr[0] = "+mEventList.get(0).getFeedId());
    }

    //sets the mMaxFeedId to its proper value
    public void updateMaxFeedId(){

        if( mMaxFeedId<mFeedList.get(0).getFeedId() ){
            mMaxFeedId=mFeedList.get(0).getFeedId();
        }
        Log.d("MAXMIN","mMaxFeedId = "+mMaxFeedId+" arr[0] = "+mFeedList.get(0).getFeedId());
    }

    //adds a new feed to the feed list
    public void addToFeedList(Feed f){
        mFeedList.add(f);
    }
    //adds a new news to the news list
    public void addToNewsList(Feed f){
        mNewsList.add(f);
    }
    //adds a new event to the event list
    public void addToEventList(Feed f){
        mEventList.add(f);
    }

    //returns the feed list
    public List<Feed> getFeedList() {
        return mFeedList;
    }
    //returns the news list
    public List<Feed> getNewsList() {
        return mNewsList;
    }
    //returns the event list
    public List<Feed> getEventList() {
        return mEventList;
    }

    //interface to be implemented by the ui who uses this class
    public static interface FeedListChangeListener{
        void onFeedListChanged(int type);
    }

    //returns maximum card id from feed list
    public long getMaxFeedId() {
        return mMaxFeedId;
    }

    //returns minimum card id from feed list
    public long getMinFeedId() {
        return mMinFeedId;
    }

    //sets maximum card id from feed list
    public void setMaxFeedId(long maxFeedId) {
        mMaxFeedId = maxFeedId;
    }

    //sets minimum card id from feed list
    public void setMinFeedId(long minFeedId) {
        mMinFeedId = minFeedId;
    }


    public void setMaxNewsId(long maxNewsId) {
        mMaxNewsId = maxNewsId;
    }

    public void setMinNewsId(long minNewsId) {
        mMinNewsId = minNewsId;
    }

    public void setMaxEventId(long maxEventId) {
        mMaxEventId = maxEventId;
    }

    public void setMinEventId(long minEventId) {
        mMinEventId = minEventId;
    }

    public long getMaxNewsId() {
        return mMaxNewsId;

    }

    //returns maximum card if of the given list
    public long getMaxOfType(int listType){
        switch(listType){
            case SHOW_NEWS:
                return getMaxNewsId();
            case SHOW_EVENTS:
                return getMaxEventId();
            default:
                return getMaxFeedId();
        }
    }

    //returns minimum card if of the given list
    public long getMinOfType(int listType){
        switch(listType){
            case SHOW_NEWS:
                return getMinNewsId();
            case SHOW_EVENTS:
                return getMinEventId();
            default:
                return getMinFeedId();
        }
    }
    public long getMinNewsId() {
        return mMinNewsId;
    }

    public long getMaxEventId() {
        return mMaxEventId;
    }

    public long getMinEventId() {
        return mMinEventId;
    }
    //returns the list containing students body info
    public ArrayList<StudentBody> getStudentBodies(){
        return mStudentBodies;
    }
    //adds the info to list of students bodies
    public void addToStudentBodies(StudentBody studentBody){
        mStudentBodies.add(studentBody);
    }

}
