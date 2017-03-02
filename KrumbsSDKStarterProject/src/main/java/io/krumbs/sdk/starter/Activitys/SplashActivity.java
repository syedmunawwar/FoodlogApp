package io.krumbs.sdk.starter.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import io.krumbs.sdk.starter.Preferences.LoginPreferences;
import io.krumbs.sdk.starter.R;

/**
 * Created by SYED on 15-02-2017.
 */
public class SplashActivity extends BaseActivity {
    public static int LOGIN_ACTIVITY_REQ_CODE = 1;
    public static int REGISTER_ACTIVITY_REQ_CODE = 2;
    public static int MAIN_ACTIVITY_REQ_CODE = 3;

    LoginPreferences loginPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loginPreferences = new LoginPreferences(SplashActivity.this);
        applyFont(SplashActivity.this,findViewById(R.id.base_layout));

        CountDownTimer countDownTimer = new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Log.e("Logged in as", String.valueOf(loginPreferences.isLoggedIn()));
                if(loginPreferences.isLoggedIn()) {
                    startActivityForResult((new Intent(SplashActivity.this, MainActivity.class)),MAIN_ACTIVITY_REQ_CODE);
                    finish();
                }
                else{
                    startActivityForResult((new Intent(SplashActivity.this, LoginActivity.class)),LOGIN_ACTIVITY_REQ_CODE);
                    finish();
                }
            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_ACTIVITY_REQ_CODE && resultCode == RESULT_OK){
            finish();
        }
        else if (requestCode == MAIN_ACTIVITY_REQ_CODE && resultCode == RESULT_OK){
            finish();
        }
    }
}
