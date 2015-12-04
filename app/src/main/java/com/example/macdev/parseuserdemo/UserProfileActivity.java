package com.example.macdev.parseuserdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class UserProfileActivity extends AppCompatActivity {
    boolean maleFlag = true;
    ImageView imgAvatar;
    EditText userFullName ;
    EditText pass ;
    EditText confPass ;
    ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userFullName = (EditText) findViewById(R.id.editTextUserNameSignUpProfile);
        pass = (EditText) findViewById(R.id.editTextUserPasswordSignUpProfile);
        confPass = (EditText) findViewById(R.id.editTextPassConfirmProfile);
        imgAvatar = (ImageView) findViewById(R.id.imageViewAvatarProfile);
        user = ParseUser.getCurrentUser();
        userFullName.setText(user.getUsername());

        RadioButton rdGenderMale = (RadioButton) findViewById(R.id.radioMaleProfile);
        RadioButton rdGenderFemail = (RadioButton) findViewById(R.id.radioFemaleProfile);

        if (user.getBoolean("gender")) {
            rdGenderMale.setChecked(true);
            imgAvatar.setImageResource(R.drawable.avatar);
        }
        else {
            rdGenderFemail.setChecked(true);
            imgAvatar.setImageResource(R.drawable.girl_avatar);
        }
        findViewById(R.id.buttonSignUpNowProfile).setOnClickListener(new View.OnClickListener() {
                                                                         @Override
                                                                         public void onClick(View v) {

                                                                             String passIn = pass.getText().toString();
                                                                             String passConfirm = confPass.getText().toString();
                                                                             if (userFullName.getText().length() < 1 || userFullName == null) {
                                                                                 Toast.makeText(UserProfileActivity.this, "Please Provide a User Name", Toast.LENGTH_SHORT).show();
                                                                             } else if (pass.getText().length() < 1 || pass == null) {
                                                                                 Toast.makeText(UserProfileActivity.this, "Please Provide a Password", Toast.LENGTH_SHORT).show();
                                                                             } else if (confPass.getText().length() < 1 || confPass == null) {
                                                                                 Toast.makeText(UserProfileActivity.this, "Please Confirm The Password", Toast.LENGTH_SHORT).show();
                                                                             } else if (!passIn.equals(passConfirm)) {
                                                                                 Toast.makeText(UserProfileActivity.this, "Password and Confirm Password Do Not Match!", Toast.LENGTH_SHORT).show();
                                                                                 Log.d("demopass", "pass=" + pass + ".    ,confpass=" + confPass + ".");
                                                                             } else {
                                                                                 imgAvatar = (ImageView) findViewById(R.id.imageViewAvatarProfile);
                                                                                 RadioGroup rg = (RadioGroup) findViewById(R.id.gender_groupProfile);
                                                                                 int checkedId = rg.getCheckedRadioButtonId();
                                                                                 switch (checkedId) {
                                                                                     case R.id.radioMaleProfile:
                                                                                         maleFlag = true;
                                                                                         imgAvatar.setImageResource(R.drawable.avatar);
                                                                                         break;

                                                                                     case R.id.radioFemaleProfile:
                                                                                         maleFlag = false;
                                                                                         imgAvatar.setImageResource(R.drawable.girl_avatar);
                                                                                         break;
                                                                                 }

                                                                                 rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                                                     public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                                                         Log.d("test", "in listner");

                                                                                         switch (checkedId) {
                                                                                             case R.id.radioMaleProfile:
                                                                                                 maleFlag = true;
                                                                                                 imgAvatar.setImageResource(R.drawable.avatar);
                                                                                                 break;

                                                                                             case R.id.radioFemaleProfile:
                                                                                                 maleFlag = false;
                                                                                                 imgAvatar.setImageResource(R.drawable.girl_avatar);
                                                                                                 break;
                                                                                         }
                                                                                     }
                                                                                 });

                                                                                 Log.d("test", "is user authenticated?" + user.isAuthenticated());
                                                                                 Log.d("test","current session token="+user.getSessionToken());


                                                                                 ParseUser.becomeInBackground(user.getSessionToken(), new LogInCallback() {
                                                                                     public void done(ParseUser user, ParseException e) {
                                                                                         if (user != null) {
                                                                                             user = ParseUser.getCurrentUser();
                                                                                             user.setUsername((((EditText) findViewById(R.id.editTextUserNameSignUpProfile)).getText().toString()));
                                                                                             user.setPassword(((EditText) findViewById(R.id.editTextUserPasswordSignUpProfile)).getText().toString());
                                                                                             user.put("UserFullName", ((EditText) findViewById(R.id.editTextUserNameSignUpProfile)).getText().toString());
                                                                                             user.put("gender", maleFlag);

                                                                                             user.saveInBackground(new SaveCallback() {
                                                                                                 @Override
                                                                                                 public void done(ParseException e) {
                                                                                                     if (e == null) {
                                                                                                         // Hooray! Let them use the app now.
                                                                                                         Log.d("demo", "update OK!");
                                                                                                         Toast.makeText(UserProfileActivity.this, "Update Successful! \n", Toast.LENGTH_SHORT).show();
                                                                                                     } else {
                                                                                                         // Sign up didn't succeed. Look at the ParseException
                                                                                                         // to figure out what went wrong
                                                                                                         Log.d("demo", "update issue!, exception says:" + e);
                                                                                                         Toast.makeText(UserProfileActivity.this, "update not successful, " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                     }
                                                                                                 }
                                                                                             });
                                                                                         } else {
                                                                                             Log.d("test","user token could not be validated!");
                                                                                             // The token could not be validated.
                                                                                         }
                                                                                     }
                                                                                 });
                                                                              Log.d("test", "CURRENT user=" + ParseUser.getCurrentUser());
                                                                                 finish();
                                                                                 Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                                                                                 startActivity(intent);
                                                                             }//end else


                                                                         }//end onclick
                                                                     }

        );

        findViewById(R.id.buttonCancelSignUpProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }


}
