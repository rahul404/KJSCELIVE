package com.example.enzo.kjscelive.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enzo.kjscelive.R;
import com.example.enzo.kjscelive.modal.Abhiyantriki;
import com.example.enzo.kjscelive.modal.Fest;
import com.example.enzo.kjscelive.modal.Kjsce;
import com.example.enzo.kjscelive.modal.Shield;
import com.example.enzo.kjscelive.modal.Skream;
import com.example.enzo.kjscelive.modal.Symphony;

/**
 * Created by enzo on 12/16/2017.
 */

public class FestFragment extends Fragment {
    public static final int SHIELD = 1;
    public static final int SKREAM = 2;
    public static final int SYMPHONY = 4;
    public static final int ABHIYANTRIKI = 8;
    public static final int KJSCE = 16;

    private static final String ARGUMENT_TAG = "com.example.enzo.kjscelive.view.FestFragment.Fest";

    private Fest mFest;
    public static FestFragment newInstance(int type){
        FestFragment f = new FestFragment();
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_TAG,type);
        f.setArguments(args);
        return f;
    }

    private void instantiateFest(int category){
        switch (category){
            case SHIELD:
                mFest = Shield.newInstance(getActivity());
                break;
            case SKREAM:
                mFest = Skream.newInstance(getActivity());
                break;
            case SYMPHONY:
                mFest = Symphony.newInstance(getActivity());
                break;
            case ABHIYANTRIKI:
                mFest = Abhiyantriki.newInstance(getActivity());
                break;
            case KJSCE:
                mFest = Kjsce.newInstance(getActivity());
                break;
        }
    }
    private void initCoverImage(View v){
        //setting the title
        TextView titleTextView = (TextView) v.findViewById(R.id.title);
        titleTextView.setText(mFest.getName());
    }
    private void initName(View v){
        //loading image in to the view
        ImageView imageView = (ImageView)v.findViewById(R.id.cover_image);
        imageView.setImageResource(mFest.getImage());
    }
    private void initLogo(View v){
        //setting the logo
        ImageView logo = (ImageView)v.findViewById(R.id.logo);
        logo.setImageResource(mFest.getLogo());
    }
    private void initDescription(View v){
        //loading the content in the view
        TextView contentTextView=(TextView)v.findViewById(R.id.content);
        contentTextView.setText(mFest.getDescription());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_student_body,container,false);
        Bundle args = getArguments();
        if(args==null){
            return v;
        }
        instantiateFest(args.getInt(ARGUMENT_TAG));
        initCoverImage(v);
        initName(v);
        initDescription(v);
        initLogo(v);
        return v;
    }
}
