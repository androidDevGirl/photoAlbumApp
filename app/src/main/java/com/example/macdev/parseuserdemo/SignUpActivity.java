package com.example.macdev.parseuserdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import android.widget.RadioGroup.OnCheckedChangeListener;



public class SignUpActivity extends AppCompatActivity  {
    boolean male = true;
    ImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Log.d("demo", "user set");

        findViewById(R.id.buttonSignUpNow).setOnClickListener(new View.OnClickListener() {
                                                                  @Override
                                                                  public void onClick(View v) {
                                                                      //EditText email = (EditText) findViewById(R.id.editTextUserEmail);
                                                                      EditText userFullName = (EditText) findViewById(R.id.editTextUserNameSignUp);
                                                                      EditText pass = (EditText) findViewById(R.id.editTextUserPasswordSignUp);
                                                                      EditText confPass = (EditText) findViewById(R.id.editTextPassConfirm);
                                                                      String passIn = pass.getText().toString();
                                                                      String passConfirm = confPass.getText().toString();
                                                                      if (userFullName.getText().length() < 1 || userFullName == null) {
                                                                          Toast.makeText(SignUpActivity.this, "Please Provide a User Name", Toast.LENGTH_SHORT).show();
                                                                      }  else if (pass.getText().length() < 1 || pass == null) {
                                                                          Toast.makeText(SignUpActivity.this, "Please Provide a Password", Toast.LENGTH_SHORT).show();
                                                                      } else if (confPass.getText().length() < 1 || confPass == null) {
                                                                          Toast.makeText(SignUpActivity.this, "Please Confirm The Password", Toast.LENGTH_SHORT).show();
                                                                      } else if (!passIn.equals(passConfirm)) {
                                                                          Toast.makeText(SignUpActivity.this, "Password and Confirm Password Do Not Match!", Toast.LENGTH_SHORT).show();
                                                                          Log.d("demopass", "pass=" + pass + ".    ,confpass=" + confPass + ".");
                                                                      } else {
                                                                          imgAvatar = (ImageView) findViewById(R.id.imageViewAvatar);
                                                                          RadioGroup rg = (RadioGroup) findViewById(R.id.gender_group);
                                                                          int checkedId = rg.getCheckedRadioButtonId();
                                                                          switch (checkedId) {
                                                                              case R.id.radioMale:
                                                                                  male = true;
                                                                                  Toast.makeText(SignUpActivity.this, "Changed to male", Toast.LENGTH_SHORT).show();
                                                                                  imgAvatar.setImageResource(R.drawable.avatar);
                                                                                  break;

                                                                              case R.id.radioFemale:
                                                                                  male = false;
                                                                                  imgAvatar.setImageResource(R.drawable.girl_avatar);
                                                                                  Toast.makeText(SignUpActivity.this, "Changed to Female", Toast.LENGTH_SHORT).show();
                                                                                  break;
                                                                          }

                                                                          rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                                              public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                                                  Log.d("test", "in listner");

                                                                                  switch (checkedId) {
                                                                                      case R.id.radioMale:
                                                                                          male = true;
                                                                                          Toast.makeText(SignUpActivity.this, "Changed to male", Toast.LENGTH_SHORT).show();
                                                                                          imgAvatar.setImageResource(R.drawable.avatar);
                                                                                          break;

                                                                                      case R.id.radioFemale:
                                                                                          male = false;
                                                                                          imgAvatar.setImageResource(R.drawable.girl_avatar);
                                                                                          Toast.makeText(SignUpActivity.this, "Changed to Female", Toast.LENGTH_SHORT).show();
                                                                                          break;
                                                                                  }
                                                                              }
                                                                          });


                                                                          ParseUser user = new ParseUser();
                                                                          //user.setUsername((((EditText) findViewById(R.id.editTextUserEmail)).getText().toString()));
                                                                          user.setUsername((((EditText) findViewById(R.id.editTextUserNameSignUp)).getText().toString()));
                                                                          user.setPassword(((EditText) findViewById(R.id.editTextUserPasswordSignUp)).getText().toString());
                                                                          user.put("UserFullName", ((EditText) findViewById(R.id.editTextUserNameSignUp)).getText().toString());
                                                                          user.put("gender", male);

                                                                          user.signUpInBackground(new SignUpCallback() {
                                                                              public void done(ParseException e) {
                                                                                  if (e == null) {
                                                                                      // Hooray! Let them use the app now.
                                                                                      Log.d("demo", "signed up OK!");
                                                                                      Toast.makeText(SignUpActivity.this, "Sign Up Successful! \n", Toast.LENGTH_SHORT).show();
                                                                                  } else {
                                                                                      // Sign up didn't succeed. Look at the ParseException
                                                                                      // to figure out what went wrong
                                                                                      Log.d("demo", "issue!, exception says:" + e);
                                                                                      Toast.makeText(SignUpActivity.this, "Sign Up not successful, " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                  }
                                                                              }
                                                                          });


                                                                      }//end else
                                                                  }//end onclick
                                                              }

        );

        findViewById(R.id.buttonCancelSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });


    }

/*    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        imgAvatar = (ImageView) findViewById(R.id.imageViewAvatar);
        RadioGroup rg = (RadioGroup) findViewById(R.id.gender_group);
        Log.d("test", "in listner");

        switch (checkedId) {
            case R.id.radioMale:
                male = true;
                Toast.makeText(SignUpActivity.this, "Changed to male", Toast.LENGTH_SHORT).show();
                imgAvatar.setImageResource(R.drawable.avatar);
                break;

            case R.id.radioFemale:
                male = false;
                imgAvatar.setImageResource(R.drawable.girl_avatar);
                Toast.makeText(SignUpActivity.this, "Changed to Female", Toast.LENGTH_SHORT).show();
                break;
        }
    }
*/

}
