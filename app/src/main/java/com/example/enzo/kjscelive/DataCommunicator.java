package com.example.enzo.kjscelive;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by enzo on 8/10/2017.
 */

public class DataCommunicator implements FeedConstants{
    private static final String LIKE = "LIKE";
    private static DataCommunicator mDataCommunicator;//singleton reference
    private Context mContext;//application context to keep this object alive
    private String mUrl="http://"+IpClass.getInstance().getIp()+URL_FEED;
    private RequestQueue mRequestQueue;
    private String mJsonResponse;
    private long[] mPreferences=new long[0];//query parameter
    private DataCommunicator(Context context){
        mContext=context;
        mRequestQueue= Volley.newRequestQueue(context);
    }
    public static DataCommunicator getInstance(Context context){
        if(mDataCommunicator==null){
            mDataCommunicator=new DataCommunicator(context);
        }
        return mDataCommunicator;
    }

    //loads the image into specified imageview
    public void loadImage(ImageView imageView, String url){
        Log.d("URL","URL = "+url);
        if(url!=null && url.length()>0){
            Picasso.with(mContext).load(url).into(imageView);
        }
    }



    protected Uri.Builder buildUri(long cardId,String email){
        Uri.Builder builder=Uri.parse(mUrl)
                .buildUpon()
                .appendQueryParameter("key",URL_KEY)
                .appendQueryParameter("card_id",""+cardId)
                .appendQueryParameter("email",email);;
        return builder;
    }
    protected Uri.Builder buildUri(String email){
        Uri.Builder builder=Uri.parse(mUrl)
                .buildUpon()
                .appendQueryParameter("key",URL_KEY)
                .appendQueryParameter("email",email);
        return builder;
    }

    //returns url for simple card retrieval
    public String buildUrlCard(String url,String email){
        return buildUri(email).build().toString();
    }
    //returns url for simple event retrieval
    public String buildUrlEvent(String url,String email){
        return buildUri(email).appendQueryParameter("type",""+EVENT_CARDS).build().toString();
    }
    //returns url for simple news retrieval
    public String buildUrlNews(String url,String email){
        return buildUri(email).appendQueryParameter("type",""+NEWS_CARDS).build().toString();
    }

    //build url for fetching younger cards of specified type
    public String buildUrlYoungerCards(long cardId,int listType,String email){
        Uri.Builder builder=buildUri(cardId,email)
                .appendQueryParameter("timeline",""+TIMELINE_NEWER);
        if(listType==SHOW_NEWS){
            builder.appendQueryParameter("type",""+NEWS_CARDS);
        }
        else if(listType==SHOW_EVENTS){
            builder.appendQueryParameter("type",""+EVENT_CARDS);
        }
        return builder.build().toString();
    }
    //build url for fetching older cards with specified list type
    public String buildUrlOlderCards(long cardId,int listType,String email){
        Uri.Builder builder=buildUri(cardId,email)
                .appendQueryParameter("timeline",""+TIMELINE_OLDER);
        if(listType==SHOW_NEWS){
            builder.appendQueryParameter("type",""+NEWS_CARDS);
        }
        else if(listType==SHOW_EVENTS){
            builder.appendQueryParameter("type",""+EVENT_CARDS);
        }
        return builder.build().toString();
    }





    //inserts  cards downloaded from the server at  the  end of feed list
    public void appendList(String url,int listType){
        switch(listType){
            case SHOW_NEWS:
                populateFeedList(url,INSERTION_END,SHOW_NEWS);
                break;
            case SHOW_EVENTS:
                populateFeedList(url,INSERTION_END,SHOW_EVENTS);
                break;
            default:
                populateFeedList(url,INSERTION_END,SHOW_CARDS);
        }
    }
    //inserts  cards downloaded from the server at  the  beginning of feed list
    public void insertList(String url,int listType){
        switch(listType){
            case SHOW_NEWS:
                populateFeedList(url,INSERTION_BEGIN,SHOW_NEWS);
                break;
            case SHOW_EVENTS:
                populateFeedList(url,INSERTION_BEGIN,SHOW_EVENTS);
                break;
            default:
                populateFeedList(url,INSERTION_BEGIN,SHOW_CARDS);
        }
    }


    //inserts  cards downloaded from the server at  the  end of feed list
    public void appendFeedList(String url){
        populateFeedList(url,INSERTION_END,SHOW_CARDS);
    }
    //inserts  cards downloaded from the server at  the  beginning of feed list
    public void insertFeedList(String url){
        populateFeedList(url,INSERTION_BEGIN,SHOW_CARDS);
    }
    //inserts  news downloaded from the server at  the  end of news list
    public void appendNewsList(String url){
        populateFeedList(url,INSERTION_END,SHOW_NEWS);
    }
    //inserts  news downloaded from the server at  the  beginning of news list
    public void insertNewsList(String url){
        populateFeedList(url,INSERTION_BEGIN,SHOW_NEWS);
    }
    //inserts  event downloaded from the server at  the  end of event list
    public void appendEventList(String url){
        populateFeedList(url,INSERTION_END,SHOW_EVENTS);
    }
    //inserts  event downloaded from the server at  the  beginning of event list
    public void insertEventList(String url){
        populateFeedList(url,INSERTION_BEGIN,SHOW_EVENTS);
    }

    //adds cards downloaded from the server in all the lists
    public void populateFeedLists(String url){
        populateFeedList(url ,INSERTION_BEGIN,SHOW_CARDS);
        populateFeedList(url ,INSERTION_BEGIN,SHOW_NEWS);
        populateFeedList(url ,INSERTION_BEGIN,SHOW_EVENTS);
    }

    //responsible for downloading the cards and putting at right position in the right list
    /*
    The url contains complete url path with the parameters set for appropriate download
    insertionType defines whether at the beginning or at the end
    listType defines whether feeds,news or events
     */
    public void populateFeedList(String url , final int insertionType , final int listType){

        if(!isConnected()){
            DataList.getInstance(mContext).sendUiUpdateMessage(COULDNT_REFRESH);
        }
        StringRequest request=new StringRequest(StringRequest.Method.GET
                ,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG,response);
                mJsonResponse=response;
                if(insertionType == INSERTION_END){
                    DataList.getInstance(mContext).appendList(mJsonResponse,listType);
                }
                else if(insertionType == INSERTION_BEGIN){
                    DataList.getInstance(mContext).insertList(mJsonResponse,listType);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Error downloading "+error);
                error.printStackTrace();
                DataList.getInstance(mContext).sendUiUpdateMessage(COULDNT_REFRESH);
            }
        });
        mRequestQueue.add(request);
    }

    public boolean sendRegistrationToServer(String refreshToken){
        return false;//TODO: add the key on he server
    }

    public void sendUnLiked(long cardId,String email){
        String url = "https://"+IpClass.getInstance().getIp()+LIKED_URL;
        String params = "card_id="+cardId+"&email="+email+"&unliked=1";
        url = url+"?"+params;
        try{
            sendLikeRequest(url);
        }
        catch (IOException ioe){
            Log.d(LIKE,"ioe in sendPost");
        }
    }

    public void sendLiked(long cardId,String email){
        String url = "https://"+IpClass.getInstance().getIp()+LIKED_URL;
        String params = "card_id="+cardId+"&email="+email;
        url = url+"?"+params;
        try{
            sendLikeRequest(url);
        }
        catch (IOException ioe){
            Log.d(LIKE,"ioe in sendPost");
        }
    }
    private void sendLikeRequest(String url)throws IOException{
        if(!isConnected()){
            return;
        }
        Log.d(LIKE,"url = "+url);
        try {
            RequestQueue requestQueue = mRequestQueue;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(LIKE, "inside on response "+response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(LIKE, "ERROR"+error.toString());
                }
            });
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /************Sends the email id to the server for registration ************/
    public void register(String email){
        if(email == null || email.length() <=0){
            return;
        }
        String url = "http://"+IpClass.getInstance().getIp()+REGISTER_URL;
        String params = "email="+email;
        url = url+"?"+params;
        sendEmail(url);
    }
    private void sendEmail(String url){
        if(!isConnected()){
            return;
        }
        Log.d(LIKE,"url = "+url);
        Toast.makeText(mContext,"url = "+url,Toast.LENGTH_LONG).show();
        try {
            RequestQueue requestQueue = mRequestQueue;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(LIKE, "inside on response "+response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(LIKE, "ERROR"+error.toString());
                }
            });
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /****
     * Checks if the mobile is connected to the network*/
    public boolean isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
