package com.example.enzo.kjscelive;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by enzo on 8/10/2017.
 */

public class DataCommunicator implements FeedConstants{
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
        Picasso.with(mContext).load(url).fit().into(imageView);
    }

    //builds the url with provided parameters for the card id
    public String buildUrl(String key, int limit, int type, long cardId,long[] preferences,
                                    String timeline,String stringUrl){
        Uri.Builder builder=Uri.parse(stringUrl)
                .buildUpon()
                .appendQueryParameter("key",key)
                .appendQueryParameter("limit",""+limit);
        if(type>0){
            builder.appendQueryParameter("type",""+type);
        }
        if(cardId>0){
            builder.appendQueryParameter("card_id",""+cardId);
        }
        for(int i=0;i<preferences.length;i++){
            builder.appendQueryParameter("preferences",preferences[i]+"");
        }
        if(timeline.length()>0){
            builder.appendQueryParameter("type",timeline);
        }
        return builder.build().toString();
    }


    protected Uri.Builder buildUri(long cardId){
        Uri.Builder builder=Uri.parse(mUrl)
                .buildUpon()
                .appendQueryParameter("key",URL_KEY)
                .appendQueryParameter("card_id",""+cardId);
        return builder;
    }
    protected Uri.Builder buildUri(){
        Uri.Builder builder=Uri.parse(mUrl)
                .buildUpon()
                .appendQueryParameter("key",URL_KEY);
        return builder;
    }

    //returns url for simple card retrieval
    public String buildUrlCard(String url){
        return buildUri().build().toString();
    }
    //returns url for simple event retrieval
    public String buildUrlEvent(String url){
        return buildUri().appendQueryParameter("type",""+EVENT_CARDS).build().toString();
    }
    //returns url for simple news retrieval
    public String buildUrlNews(String url){
        return buildUri().appendQueryParameter("type",""+NEWS_CARDS).build().toString();
    }

    //build url for fetching older cards
    public String buildUrlOlderCards(long cardId){
        return buildUri(cardId).appendQueryParameter("timeline",""+TIMELINE_OLDER)
                .build().toString();
    }




    //build url for fetching younger cards of specified type
    public String buildUrlYoungerCards(long cardId,int listType){
        Uri.Builder builder=buildUri(cardId)
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
    public String buildUrlOlderCards(long cardId,int listType){
        Uri.Builder builder=buildUri(cardId)
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
}
