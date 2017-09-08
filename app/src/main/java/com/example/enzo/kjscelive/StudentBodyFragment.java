package com.example.enzo.kjscelive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by enzo on 8/31/2017.
 */

public class StudentBodyFragment extends Fragment{
    private static final String ARGUMENT_INDEX="com.example.enzo.kjscelive.StudentBodyFragment.INDEX";//key to hold index of student body in list
    //private static final String ARGUMENT_LIST_TYPE="com.example.enzo.kjscelive.DetailedFeedFragment.LIST_TYPE";//denotes type of list where the feed belongs to
    private StudentBody mStudentBody;//holds the feed whose information is renderred on the screen
    //returns instance of DetailedFeedFragment instance with argument appropriately set
    public static StudentBodyFragment newInstance(int index){
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_INDEX,index);
        StudentBodyFragment f = new StudentBodyFragment();
        f.setArguments(args);
        return f;
    }
    //TODO : this method has to encapsulate the detailed view of the feed
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        /***************************Setting the mFeed object reference***************************/
        Bundle args = getArguments();
        int index=0;
        if(args != null){
            index= args.getInt(ARGUMENT_INDEX);
        }
        mStudentBody=DataList.getInstance(getActivity()).getStudentBodies().get(index);
        View v = inflater.inflate(R.layout.fragment_student_body,container,false);
        //settting the title
        TextView titleTextView = (TextView) v.findViewById(R.id.student_body_title);
        titleTextView.setText(mStudentBody.getName());
        //loading image in to the view
        ImageView imageView = (ImageView)v.findViewById(R.id.student_body_image);
        //loading the content in the view
        TextView contextTextView=(TextView)v.findViewById(R.id.student_body_description);
        contextTextView.setText(mStudentBody.getDescription());
        return v;
    }
}
