package com.example.enzo.kjscelive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="Volley";
    private Button mChangeIpButton;
    private EditText mChangeIpEditext;
    private String mIp;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mChangeIpEditext=(EditText) findViewById(R.id.change_ip_editext);
        mChangeIpButton=(Button)findViewById(R.id.change_ip_button);
        mRequestQueue= Volley.newRequestQueue(MainActivity.this);

        mChangeIpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mIp=mChangeIpEditext.getText().toString();
                IpClass.getInstance().setIp(mIp);
/************************Starts the main app ********************/
                //startActivity(FeedActivity.newIntent(MainActivity.this));
                startActivity(FeedPagerActivity.newIntent(MainActivity.this));
            }
        });
    }
}
