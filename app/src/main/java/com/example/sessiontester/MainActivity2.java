package com.example.sessiontester;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setImage();
    }

    private void setImage() {

        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);

    }

    public void getImage(View view) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Game");

        // The query will search for a ParseObject, given its objectId.
        // When the query finishes running, it will invoke the GetCallback
        // with either the object, or the exception thrown
//        query.getInBackground("CXg1VcJx7T", new GetCallback<ParseObject>() {
//            public void done(ParseObject result, ParseException e) {
//                if (e == null) {
//                    final ParseFile imageFile = (ParseFile)result.get("pic1");
//                    imageFile.getDataInBackground(new GetDataCallback() {
//                        public void done(byte[] data, ParseException e) {
//                            if (e == null) {
//                                Glide.with(MainActivity2.this).load(data).into(imageView);
//                            } else {
//                                // something went wrong
//                            }
//                        }
//                    });
//
//                } else {
//                }
//            }
//        });

        HashMap<String, String> params = new HashMap();
        ArrayList<String> strings = new ArrayList<String>();
        ParseCloud.callFunctionInBackground("test", params, new FunctionCallback<ArrayList>() {
            @Override
            public void done(ArrayList strings, ParseException e) {
                if (e == null) {

                    byte[] imgBytes = Base64.decode(strings.get(0).toString(), Base64.DEFAULT);
                    byte[] imgBytes1 = Base64.decode(strings.get(1).toString(), Base64.DEFAULT);
                    byte[] imgBytes2 = Base64.decode(strings.get(2).toString(), Base64.DEFAULT);
                    byte[] imgBytes3 = Base64.decode(strings.get(3).toString(), Base64.DEFAULT);
                    byte[] imgBytes4 = Base64.decode(strings.get(4).toString(), Base64.DEFAULT);
                    Glide.with(
                            MainActivity2.this).load(imgBytes).into(imageView);
                    Glide.with(
                            MainActivity2.this).load(imgBytes1).into(imageView2);
                    Glide.with(
                            MainActivity2.this).load(imgBytes2).into(imageView3);
                    Glide.with(
                            MainActivity2.this).load(imgBytes3).into(imageView4);
                    Glide.with(
                            MainActivity2.this).load(imgBytes4).into(imageView5);

                    ParseObject object = (ParseObject) strings.get(5);
                    Log.d("parseobjectthing", object.get("name") + "");
                    } else {
                    Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}