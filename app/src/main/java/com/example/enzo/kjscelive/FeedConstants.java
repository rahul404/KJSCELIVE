package com.example.enzo.kjscelive;

import java.text.SimpleDateFormat;

/**
 * Created by enzo on 8/9/2017.
 */

public interface FeedConstants {

    int SCROLL_UP=1;//value if the scroll on recycler view was upwards dy is negative
    int SCROLL_DOWN=2;//value if the scroll on recycler view was downwards dy i spositive
    long EVENT_CARDS=1;//Denotes cards with type = event
    long NEWS_CARDS=2;//denotes cards with type= news
    long DEFAULT_NO_CARDS=10;//this is the default number of requested cards
    String TIMELINE_OLDER="older";//parameter denoting to fetch of cards that are older than passed cardId
    String TIMELINE_NEWER="younger";//parameter denoting to fetch of cards that are newer than passed cardId
    //String URL_FEED="/android/kjscelive/feed.php";//url to be used when fetching json data
    String URL_FEED="/feed.php";//url to be used when fetching json data
    //String URL_IMAGE="/android/kjscelive/get-image.php?key=legendary&id=";//url to be used when fetching image url
    String URL_IMAGE="/get-image.php?key=legendary&id=";//url to be used when fetching image url
    String URL_KEY="legendary";//key parameter
    String TAG="TOKEN";//for log class tag
    int SHOW_CARDS=1;//used by the FeedFragment denotes the type Feed list to be used
    int SHOW_EVENTS=2;//used by the FeedFragment denotes the type Feed list to be used
    int SHOW_NEWS=4;//used by the FeedFragment denotes the type Feed list to be used
    int NO_OF_TABS=3;//represents the number of tabs in the main screen
    SimpleDateFormat DATE_SDF= new SimpleDateFormat("yyyy-M-dd HH:mm:ss");//converts the date into specified format
    SimpleDateFormat FEED_DATE_SDF= new SimpleDateFormat("dd-MM-yy");//converts given date into dd-mm-yy format
    SimpleDateFormat FEED_TIME_SDF= new SimpleDateFormat("hh:mm aa");//converts given date into hh:mm:ss format
    int INSERTION_END=1;//specifies that the json data to be appended to the existing list
    int INSERTION_BEGIN=2;//specifies that the json data to be inserted at the beginning existing list
    int COULDNT_REFRESH=4;//constant to denote the field couldnt be refreshed
    long HAMBURGER_ICON_ANIMATION_DELAY=400;//delay in ms for animation in hamburger icon
    String DOUBLE_FRAGMENT_TAG="DOUBLE_FRAGMENT_TAG";//string tag for all the popup fragments
    String LIKED_URL = "/like.php";//url for the like php file
    String REGISTER_URL = "/register.php";//url for the register.php
}
