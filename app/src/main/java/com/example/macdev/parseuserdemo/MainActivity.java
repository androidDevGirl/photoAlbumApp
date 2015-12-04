package com.example.macdev.parseuserdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final List<String> mPermissions = new ArrayList<String>() {{
        add("public_profile");
        add("email");
    }};
    ParseUser parseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
         if(id == R.id.action_logout){
            try{

     /*           if (ParseTwitterUtils.getTwitter()!= null) {
                    Log.d("test"," authentication token"+ParseTwitterUtils.getTwitter().getAuthToken().toString());
                    ParseTwitterUtils.getTwitter().setAuthToken("");
                    Log.d("test", " authentication token after" + ParseTwitterUtils.getTwitter().getAuthToken().toString());
                }
       */         ParseUser user = ParseUser.getCurrentUser();
                //user.getSessionToken();

                ParseUser.logOut();
                //user.logOut();
                finish();
                Intent goToLogin = new Intent(this,LogInActivity.class);
                startActivity(goToLogin);
                //Log.d("test", "linked to Twitter?="+ParseTwitterUtils.isLinked(ParseUser.getCurrentUser()));
                //ParseTwitterUtils.unlink(ParseUser.getCurrentUser());

            }
            catch(Exception err){
                Log.e("test", "Error: "+err.toString());
            }


            return true;
        }else if(id == R.id.action_profile)
         {
             Intent goToProfile = new Intent(this,UserProfileActivity.class);
             startActivity(goToProfile);
         }

        return super.onOptionsItemSelected(item);
    }

}
