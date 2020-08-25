package com.example.sessiontester;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.parse.FunctionCallback;
import com.parse.ParseACL;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;


public class StudentCreateProfile extends AppCompatActivity {

    ImageView nid,stdId;
    EditText gName,relation;
    boolean [] images;
    byte[] nIdPic, schoolIdPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        initialize();

    }


    private void initialize() {

        // Edit Text
        relation = findViewById(R.id.etxt_studentCreateProfile_relation);
        gName = findViewById(R.id.etxt_studentCreateProfile_guardiansName);
        // Parse Files

        // images boolean
        images = new boolean[2];

        // ImageViews
        nid = findViewById(R.id.img_studentCreateProfile_guardiansNid);
        stdId = findViewById(R.id.img_studentCreateProfile_studentId);


    }

    public void uploadNidCP(View view) {
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1);
    }

    public void uploadStdId(View view) {
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null) {
            //Upload Guardian NID CARD
            images[0]=true;
//            uploadNId.setError(null);
            Uri selectedImage = data.getData();
            Glide.with(this)
                    .asBitmap()
                    .load(selectedImage)
                    .into(new CustomTarget<Bitmap>(250, 250){
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            nid.setImageBitmap(resource);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            resource.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            nIdPic = stream.toByteArray();

                        }
                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
        }else if (requestCode==2 && resultCode==RESULT_OK && data!=null){
//            kidId.setError(null);
            images[1]=true;
            //Upload Student School ID card
            Uri selectedImage = data.getData();
            Glide.with(this)
                    .asBitmap()
                    .load(selectedImage)
                    .into(new CustomTarget<Bitmap>(250, 250){
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            stdId.setImageBitmap(resource);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            resource.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            schoolIdPic = stream.toByteArray();




                            HashMap<String, Object> params = new HashMap();
                            params.put("pic1", schoolIdPic);
                            params.put("pic2", schoolIdPic);
                            params.put("pic3", schoolIdPic);
                            params.put("pic4", schoolIdPic);
                            params.put("pic5", schoolIdPic);
                            ParseCloud.callFunctionInBackground("hello", params, new FunctionCallback<String>() {
                                @Override
                                public void done(String object, ParseException e) {
                                    if (e == null) {
                                        Toast.makeText(StudentCreateProfile.this,object , Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(StudentCreateProfile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
        }
    }


    public void submitProfileT(View view) {

        if(TextUtils.isEmpty(relation.getText())){
            relation.setError("Empty Field");
            relation.requestFocus();
        }else if(!images[0]){
            Toast.makeText(this, "Enter img", Toast.LENGTH_SHORT).show();
            nid.setFocusableInTouchMode(true);
            nid.requestFocus();
        }else if(!images[1]){
            Toast.makeText(this, "Enter img", Toast.LENGTH_SHORT).show();
            stdId.setFocusableInTouchMode(true);
            stdId.requestFocus();
        }else{
            save();
        }
    }

    private void save() {
        //Current User
        ParseUser user = ParseUser.getCurrentUser();

        //Setting up user profile
        ParseObject studentProfileObject = new ParseObject("StudentProfile");
        studentProfileObject.put("username", ParseUser.getCurrentUser().getUsername());
        studentProfileObject.add("bytesArray",nIdPic);
        studentProfileObject.add("bytesArray",schoolIdPic);
        studentProfileObject.put("relation",relation.getText().toString());
        studentProfileObject.put("guardianName",gName.getText().toString());
        studentProfileObject.put("verified",false);

        ParseACL groupAcl = new ParseACL();
        groupAcl.setRoleReadAccess("Admin",true);
        groupAcl.setRoleWriteAccess("Admin",true);
        groupAcl.setReadAccess(user,true);
        groupAcl.setWriteAccess(user,true);
        studentProfileObject.setACL(groupAcl);
        //Putting user profile into User
        user.put("sProfile",studentProfileObject);

        user.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Toast.makeText(StudentCreateProfile.this, "Save successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(StudentCreateProfile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Big Error",""+e.getMessage());
                }
            }
        });
//            Empty out bytesArray
//            studentProfileObject.remove("bytesArray");
//            Save locally
//            studentProfileObject.pinInBackground("proS");
//            Go back to previous activity
//            finish();

    }

}
