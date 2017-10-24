package com.example.enzo.kjscelive;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;

import android.provider.CalendarContract;
import android.support.v4.content.FileProvider;

import android.util.Log;


import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by enzo on 8/12/2017.
 */

public class ImplicitIntentGenerator implements FeedConstants{

    //creates and returns an implicit intent with given image caption
    public static Intent getShareableIntent( String textBody, String path,Context context) {

        File f=new File(path);
        Log.d("INTENT","EXISTS "+f.exists()+"context = "+context);

        Uri uri= FileProvider.getUriForFile(context,BuildConfig.APPLICATION_ID,f);
        Log.d("INTENT","after uri");
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_SEND);
        //intent.setPackage("com.whatsapp");
        intent.putExtra(Intent.EXTRA_TEXT,textBody);
        intent.putExtra(Intent.EXTRA_TITLE,textBody);
        if(uri!=null){
            intent.putExtra(Intent.EXTRA_STREAM,uri);
            intent.setType("image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        return intent;
    }
    public static Intent getCalendarIntent(Feed f){
        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, f.getFeedTitle());
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, f.getFeedVenue());
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, R.string.calendar_event_description);
        try{
            SimpleDateFormat sdf = FEED_DATE_SDF;
            Date parse = sdf.parse("18/08/2012");
            Calendar c = Calendar.getInstance();
            c.setTime(parse);
            System.out.println(c.get(Calendar.MONTH) + c.get(Calendar.DATE) + c.get(Calendar.YEAR));
            GregorianCalendar calDate = new GregorianCalendar();
        }
        catch(ParseException exception){
            Log.d(TAG,"parsing error in ImplicitIntentGenerator");
        }
        finally{
            return calIntent;
        }
    }
}
