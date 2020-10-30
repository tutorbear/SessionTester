package com.example.sessiontester;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {
    ParseObject obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpannableString spannableString = new SpannableString("Let's party @");
        Drawable d = getResources().getDrawable(R.drawable.party);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
        spannableString.setSpan(span, spannableString.toString().indexOf("@"),  spannableString.toString().indexOf("@")+1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

    }

    public void login(View view) {


        ParseUser.logInInBackground("a", "1", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e==null){
                    Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void logout(View view) {
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void create(View view) {
        ParseObject entity = new ParseObject("Game");

        Calendar cal = Calendar.getInstance();
//        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.add(Calendar.DAY_OF_MONTH,1);
//        cal.add(Calendar.HOUR_OF_DAY,6);
        Date d = cal.getTime();
        entity.put("name", "A string");
        entity.put("date",d);
        // Saves the new object.
        // Notice that the SaveCallback is totally optional!
        entity.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    Toast.makeText(MainActivity.this, "Save success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void update(View view) {

        obj.put("name", "B string");

        // All other fields will remain the same
        obj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(MainActivity.this, "DOne", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void delete(View view) {
        obj.deleteInBackground(new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(MainActivity.this, "DOne", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveI(View view) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("debug");
        query.fromLocalDatastore();
        query.getInBackground("o926IPNoLK", new GetCallback<ParseObject>() {
            public void done(ParseObject result, ParseException e) {
                if (e == null) {
//                    result.put("result","new obj");
                    result.saveEventually();
                    Toast.makeText(MainActivity.this, ""+result.get("result"), Toast.LENGTH_SHORT).show();
                } else {
                    // something went wrong
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void read(View view) {
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("debug");
        query2.fromLocalDatastore();
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    objects.get(0).put("result","save done");
                    objects.get(0).saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e==null)
                                Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // something went wrong
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("debug");
//        query.fromLocalDatastore();
//        query.getInBackground("o926IPNoLK", new GetCallback<ParseObject>() {
//            public void done(ParseObject result, ParseException e) {
//                if (e == null) {
////                    result.put("result","bbb");
//                    Toast.makeText(MainActivity.this, ""+result.get("result"), Toast.LENGTH_SHORT).show();
//                } else {
//                    // something went wrong
//                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    public void put(View view) {
        List<String> x = new ArrayList<>();
//        x.add("y");
//        obj.remove("array");
        obj.put("array",x);
//        obj.put("x",obj.getInt("x")+1);
        obj.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                } else {
                    // something went wrong
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Game");
//        query.fromLocalDatastore();
//        query.getInBackground("e7brhKaA9G", new GetCallback<ParseObject>() {
//            public void done(ParseObject result, ParseException e) {
//                if (e == null) {
//                    result.put("name","new name");
//                    result.saveEventually();
//                    Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
//                } else {
//                    // something went wrong
//                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    public void cloud(View view) {

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Game");
        query2.include("pointer");
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Toast.makeText(MainActivity.this, "D", Toast.LENGTH_SHORT).show();
                    objects.get(0).put("name","batman");
                    ParseObject.pinAllInBackground("xxx",objects);
                } else {
                    // something went wrong
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void secondActivity(View view) {
        Toast.makeText(this, ""+ ParseUser.getCurrentUser().getParseObject("pt").get("result"), Toast.LENGTH_SHORT).show();
    }

    public void fetchAndPin(View view) {
        obj.unpinInBackground("xxx",new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(MainActivity.this, "Done delete", Toast.LENGTH_SHORT).show();
                } else {
                    // something went wrong
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}