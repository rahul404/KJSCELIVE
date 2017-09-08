package com.example.enzo.kjscelive;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by enzo on 8/30/2017.
 */

public class ImageManager {
    private static ImageManager mImageManager;//singleton reference to the object of this class
    private ArrayList<String> mFilesToDelete;//it contains path to all the files that are to be deleted when the application closes
    Context mContext;//holds a application context
    //private constructor
    private ImageManager(Context context){
        mFilesToDelete = new ArrayList<>();
        mContext=context;
    }
    //factory method
    public static ImageManager getInstance(Context context){
        if(mImageManager == null){
            mImageManager = new ImageManager(context);
        }
        return mImageManager;
    }
    //this method adds the url to given cache and then adds the file path to a list of files to be deleted
    //it returns the file path of the image in side the cache
    public String writeToCacheFromURL(String fileName, Bitmap bmp,String ext) {
        FileOutputStream fileWriter=null;
        BufferedReader bufferedReader=null;
        fileName=fileName+"."+ext;
        System.out.println("file name "+fileName);
        try {
            Log.d("INTENT","inside try of writeToCache");
            fileWriter= (mContext.openFileOutput(fileName,Context.MODE_PRIVATE));
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fileWriter); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
            //fileWriter.flush();
            Log.i("from nwThread","DONE FETCHING");
            String deletePath = mContext.getFilesDir().getCanonicalPath()+ File.separator+fileName;
            mFilesToDelete.add(deletePath);
            Log.d("INTENT","deletePath "+deletePath);
            return deletePath;
        }catch (IOException ioe){
            Log.d("INTENT","inside ioexception of writeToCache");
            ioe.printStackTrace();
        }
        finally {
            Log.d("INTENT","inside finally of writeToCache");
            try {
                if (fileWriter != null)
                    fileWriter.close();
                if (bufferedReader != null)
                    bufferedReader.close();
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
        Log.d("INTENT","returning from  writeToCache");
        return null;
    }
    //deletes all the files downloaded
    public void deleteFiles(){
        File file;
        if(mFilesToDelete!=null)
            for(String s:mFilesToDelete){
                file = new File(s);
                file.delete();
            }
            mFilesToDelete.clear();
    }
}
