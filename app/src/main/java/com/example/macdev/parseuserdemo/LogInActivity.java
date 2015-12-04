package com.example.macdev.parseuserdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.twitter.Twitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LogInActivity extends AppCompatActivity {
    public static final List<String> mPermissions = new ArrayList<String>() {{
        add("public_profile");
    }};
    ParseUser parseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            Toast.makeText(LogInActivity.this, "A User is loged in!, Hello "+currentUser.getUsername().toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        findViewById(R.id.buttonLogInNow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = (EditText) findViewById(R.id.editTextUserNameLogin);
                EditText pass = (EditText) findViewById(R.id.editTextUserPasswordLogIn);
                if (email.getText().length() < 1 || email == null) {
                    Toast.makeText(LogInActivity.this, "Please Provide an Email Address", Toast.LENGTH_SHORT).show();
                } else if (pass.getText().length() < 1 || pass == null) {
                    Toast.makeText(LogInActivity.this, "Please Provide a password", Toast.LENGTH_SHORT).show();
                } else {

                    ParseUser.logInInBackground(((EditText) findViewById(R.id.editTextUserNameLogin)).getText().toString(), ((EditText) findViewById(R.id.editTextUserPasswordLogIn)).getText().toString(), new LogInCallback() {
                        public void done(ParseUser user, ParseException e) {
                            if (user != null) {
                                // Hooray! The user is logged in.
                                Toast.makeText(LogInActivity.this, "Log In Successful! \n", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Signup failed. Look at the ParseException to see what happened.
                                Toast.makeText(LogInActivity.this, "Log In not Successful! \n" + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }//end else
            }
        });

        findViewById(R.id.buttonCreateNewAccount).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //May be a To Do - to retrieve profile image use
        //https://graph.facebook.com/<CfacebookId>/picture?type=large
        CallbackManager callbackManager = CallbackManager.Factory.create();
        Button loginButton = (Button) findViewById(R.id.buttonFaceBookLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseFacebookUtils.logInWithReadPermissionsInBackground(LogInActivity.this, mPermissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d("test", "DD Uh oh. The user cancelled the Facebook login.");
                        } else if (user.isNew()) {
                            Log.d("test", "DD, from FB User signed up and logged in through Facebook!");
                            getUserDetailsFromFB();
                            linkUser(user);
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Log.d("test", "DD, from Parse  User logged in through Facebook!");
                            getUserDetailsFromParse();
                            linkUser(user);
                            Toast.makeText(LogInActivity.this, "A User is loged in!, Hello " + ParseUser.getCurrentUser().getUsername().toString(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);
                            Log.d("test", "DDDfrom parse name=" + ParseUser.getCurrentUser().getUsername().toString());
                        }
                    }
                });
            }
        });

        Button twitterLogin = (Button) findViewById(R.id.buttonTwitterLogin);
        twitterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ParseTwitterUtils.initialize("a7VKZ5FGQ2HKfRhWCFHDlBny6", "OUGm5hZaowQ7SU4n4NS4srHwyyEDD8MF4bFG10ElPyLBwoHmKC");

                ParseTwitterUtils.logIn(LogInActivity.this, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d("test", "Uh oh. The user cancelled the Twitter login.");
                        } else if (user.isNew()) {
                            linkUser(user);
                            String screen_name = ParseTwitterUtils.getTwitter().getScreenName();
                            //ParseTwitterUtils.getTwitter().getUserId();
                            Log.d("test","user name="+screen_name+" authentication token"+ParseTwitterUtils.getTwitter().getAuthToken().toString());
                            Log.d("test", "User signed up and logged in through Twitter!");
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d("test", "User logged in through Twitter!");
                            linkUser(user);
                            Twitter twitter = ParseTwitterUtils.getTwitter();
                            twitter.getUserId();
                            Log.d("test", "username=" + twitter.getScreenName());
                            Log.d("test"," authentication token"+ParseTwitterUtils.getTwitter().getAuthToken().toString());

                            //twitter.get
                            user.setUsername(twitter.getScreenName());
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);

                        }
                    }
                });

            }
        });

    }







    private void getUserDetailsFromParse() {
        parseUser = ParseUser.getCurrentUser();

        Log.d("test", "email is= " + parseUser.getEmail());
        Log.d("test", "name is= " + parseUser.getUsername());

        Toast.makeText(this, "Welcome back " + parseUser.getUsername().toString(), Toast.LENGTH_SHORT).show();

    }

    private void getUserDetailsFromFB() {
        Log.d("test", "Begin get user detail from FB");

        //"/me?fields=gender,first_name,last_name,name"

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        try {
                            Log.d("test","try of get user detail from FB"+ response.getJSONObject().toString());

                            String  gender = response.getJSONObject().optString("gender");

                            String  name = response.getJSONObject().getString("name");
                            parseUser = ParseUser.getCurrentUser();
                            parseUser.setUsername(name);
                            if (gender.equals("male")) parseUser.put("gender", true);
                            else parseUser.put("gender", false);
                            Log.d("test", "name saved to parse");
                            Log.d("test", "user pulled back from Parser===" + ParseUser.getCurrentUser().getUsername().toString());

                            Log.d("test","genderl= "+gender);
                            Log.d("test", "name= "+name);
                            parseUser.setUsername(name);
                            try {
                                parseUser.save();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("test","ERROR from FB: "+e);
                        }
                        Log.d("test","in facebook retrieval");
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,gender,email,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean ok = ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
        if (ok) {
            Log.d("test", "onActivity ok");
        }
        else Log.d("test", "onActivity NOT ok");

    }

    private void linkUser(final ParseUser user){
        if (!ParseTwitterUtils.isLinked(user)) {
            ParseTwitterUtils.link(user, this, new SaveCallback() {
                @Override
                public void done(ParseException ex) {
                    if (ParseTwitterUtils.isLinked(user)) {
                        Log.d("test", "Woohoo, user logged in with Twitter!");
                    }
                }
            });
        }
    }
}
