package com.example.enzo.kjscelive;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN=123;//constant for result code for startActivityForResult in firebase UI
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    /*
    * If the mIsNew is false it means the user is already signed
    * It is by default false and set to true only if the user != null yields false in mAuthListener*/
    private boolean mIsNew = false;//flag to see if the user is new or not


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAuth();
        IpClass.getInstance().setContext(getApplicationContext());
        //initDatabase();
        //initFCM();
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void showToast(String msg){
        //Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    private void initAuth(){

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Log.d("LoginActivity", "onAuthStateChanged:signed_in:" + user.getUid());
                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    IpClass.getInstance().setEmail(email);
                    showToast("signed in as "+email);
                    if(mIsNew){
                        DataCommunicator.getInstance(getApplicationContext()).register(email);
                        showToast("mIsNew = "+mIsNew);
                    }
                    startActivity(FeedPagerActivity.newIntent(MainActivity.this));
                } else {
                    // User is signed out
                    //Log.d("LoginActivity", "onAuthStateChanged:signed_out");
                    mIsNew = true;
                    showToast("access denied");
                    signIn();
                }
            }
        };
        GoogleApiClient.OnConnectionFailedListener connectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                // An unresolvable error has occurred and Google APIs (including Sign-In) will not
                // be available.
                Log.d("LoginActivity", "onConnectionFailed:" + connectionResult);
                showToast("Google Play Services error.");
            }

        };
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(MainActivity.this /* FragmentActivity */, connectionFailedListener /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                showToast("bhai bhai bhai");
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                //startActivity(FeedPagerActivity.newIntent(MainActivity.this));
                //IpClass.getInstance().setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            } else {
                showToast("koi nagar paika ko bulao");
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                //updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("LoginActivity", "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        try {

            //dialog.show();
        } catch (Exception e) {

        }
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("LoginActivity", "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("LoginActivity", "signInWithCredential", task.getException());
                            showToast("Firebase Authentication failed.");
                        }
                        // [START_EXCLUDE]

                        try {

                            //dialog.dismiss();
                        } catch (Exception e) {

                        }
                        // [END_EXCLUDE]
                    }
                });
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        // Firebase sign out
        try {
            mAuth.signOut();

            // Google sign out
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            //updateUI(null);
                        }
                    });
        } catch (Exception e) {

        }
    }


}
