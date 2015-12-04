/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.example.macdev.parseuserdemo;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;


public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    // Add your initialization code here
    //Parse.enableLocalDatastore(this);
    Parse.initialize(this, "bBgMOzOIJG1axq5iGXElmkOpZ2Yte9MtahJU7tbA", "lKnjWeIDCAEpeQaANYiYsePfMNUOd23WshBiBFBI");
    ParseUser.enableRevocableSessionInBackground();
    ParseFacebookUtils.initialize(this);
    FacebookSdk.sdkInitialize(getApplicationContext());
    ParseTwitterUtils.initialize("NTWMgQh7Xg8R4FsMcsYEWK15S", "9X4xTLOkp8McNNEzd8PPiF76H8csV7ZTDD2VHXJE60ezCuSgnl");


  }
}

