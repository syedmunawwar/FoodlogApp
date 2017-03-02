/*
 * Copyright (c) 2016 Krumbs Inc.
 * All rights reserved.
 *
 */
package io.krumbs.sdk.starter;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;

import io.krumbs.sdk.KrumbsSDK;
import io.krumbs.sdk.KrumbsUser;
import io.krumbs.sdk.data.model.Media;
import io.krumbs.sdk.krumbscapture.KMediaUploadListener;
import io.krumbs.sdk.starter.Activitys.MainActivity;
import io.krumbs.sdk.starter.Models.User;
import io.krumbs.sdk.starter.Preferences.LoginPreferences;


public class StarterApplication extends Application {
    public static final String KRUMBS_SDK_APPLICATION_ID = "io.krumbs.sdk.APPLICATION_ID";
    public static final String KRUMBS_SDK_CLIENT_KEY = "io.krumbs.sdk.CLIENT_KEY";
    public static final String SDK_STARTER_PROJECT_USER_FN = "newuser";
    public static final String SDK_STARTER_PROJECT_USER_SN = "Public";
    private LoginPreferences loginPreferences;
    User user;
    @Override
    public void onCreate() {
        super.onCreate();
        loginPreferences = new LoginPreferences(StarterApplication.this);
        user = loginPreferences.getUser();

        String appID = getMetadata(getApplicationContext(), KRUMBS_SDK_APPLICATION_ID);
        String clientKey = getMetadata(getApplicationContext(), KRUMBS_SDK_CLIENT_KEY);
        if (appID != null && clientKey != null) {
        // SDK usage step 1 - initialize the SDK with your application id and client key
        // Make sure the application id and client key are correctly initialized in the Manifest
            KrumbsSDK.initialize(getApplicationContext(), appID, clientKey);

// Implement the interface KMediaUploadListener.
// After a Capture completes, the media (photo and audio) is uploaded to the cloud
// KMediaUploadListener will be used to listen for various state of media upload from the SDK.
            KMediaUploadListener kMediaUploadListener = new KMediaUploadListener() {
                // onMediaUpload listens to various status of media upload to the cloud.
                @Override
                public void onMediaUpload(String id, KMediaUploadListener.MediaUploadStatus mediaUploadStatus,
                                          Media.MediaType mediaType, URL mediaUrl) {
                    if (mediaUploadStatus != null) {
                        Log.i("KRUMBS Status", mediaUploadStatus.toString());
                        if (mediaUploadStatus == KMediaUploadListener.MediaUploadStatus.UPLOAD_SUCCESS) {
                            if (mediaType != null && mediaUrl != null) {
                                Log.i("KRUMBS Media, Type: ", mediaType + ": ID:" + id + ", URL:" + mediaUrl);
                                Toast.makeText(StarterApplication.this, mediaUploadStatus.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            };
            // pass the KMediaUploadListener object to the sdk
            KrumbsSDK.setKMediaUploadListener(this, kMediaUploadListener);

            try {
// SDK usage step 3 (optional) - register users so you can associate their ID (email) with created content with Cloud
// API
                // Register user information (if your app requires login)
                // to improve security on the mediaJSON created.
                //String userEmail = DeviceUtils.getPrimaryUserID(getApplicationContext());
                KrumbsSDK.registerUser(new KrumbsUser.KrumbsUserBuilder()
                        .email(user.getEmail())
                        .firstName(user.getUsername())
                        .lastName(" public ").build());


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getMetadata(Context context, String name) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                return appInfo.metaData.getString(name);
            }
        } catch (PackageManager.NameNotFoundException e) {
// if we can’t find it in the manifest, just return null
        }
        return null;
    }
}
