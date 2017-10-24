package com.example.enzo.kjscelive.firebaseUtils.realtimeDatabase;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

/**
 * Created by enzo on 10/25/2017.
 */

/*
* Encapsulates the realtime database interaction with the server*/
public class DatabaseCommunicator {
    //private Context mContext;
    private static DatabaseCommunicator mDatabaseCommunicator;//singleton reference
    private DatabaseReference mDatabaseReference;//reference to the remote realtime database
    //private FirebaseDatabase mFirebaseDatabase;//Firebase Database reference
    public DatabaseCommunicator(){
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("card");
    }
//    public DatabaseCommunicator(Context context){
//        mContext = context;
//        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("card");
//    }
    static public DatabaseCommunicator newInstance(){
        if(mDatabaseCommunicator == null){
            //mDatabaseCommunicator= new DatabaseCommunicator(context);
            mDatabaseCommunicator= new DatabaseCommunicator();
        }
        return mDatabaseCommunicator;
    }
    public void performLike(final long id,final boolean incr){
        DatabaseReference reference = mDatabaseReference.child(""+id).child("like");
        reference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if(mutableData.getValue() != null){
                    long count =(Long) mutableData.getValue();
                    if(incr){
                        //incr is set to true means increment the like counter
                        count++;
                    }
                    else{
                        //incr is set to false means decrement the like counter
                        count--;
                    }
                    mutableData.setValue(count);
                }
                return Transaction.success(mutableData);
            }
            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                //nothing to do
                //Toast.makeText(mContext,"output = "+dataSnapshot.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void performViewUpdate(final long id){
        DatabaseReference reference = mDatabaseReference.child(""+id).child("view");
        reference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if(mutableData.getValue() != null){
                    long count =(Long) mutableData.getValue();
                    count++;
                    mutableData.setValue(count);
                }
                return Transaction.success(mutableData);
            }
            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                //nothing to do
                //Toast.makeText(mContext,"output = "+dataSnapshot.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
