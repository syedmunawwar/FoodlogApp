package io.krumbs.sdk.starter.Activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import io.krumbs.sdk.starter.Models.User;
import io.krumbs.sdk.starter.Parsers.UserParser;
import io.krumbs.sdk.starter.Preferences.LoginPreferences;
import io.krumbs.sdk.starter.R;
import io.krumbs.sdk.starter.ServerUtils.ServerRequest;
import io.krumbs.sdk.starter.Utils.DialogUtils;

/**
 * Created by SYED on 15-02-2017.
 */
public class LoginActivity extends BaseActivity  {


    EditText emailET, passwordET;
    Button loginBT;
    private TextView btnLinkToRegister;
    public LoginPreferences loginPreferences;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailET = (EditText) findViewById(R.id.email);
        passwordET = (EditText) findViewById(R.id.password);
        loginBT = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (TextView) findViewById(R.id.linkToRegisterScreen);
        loginPreferences = new LoginPreferences(LoginActivity.this);


        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                validateEntries(email, password);
            }
        });

        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void validateEntries(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            loginUser(email, password);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the credentials!", Toast.LENGTH_LONG).show();
        }
    }


    private void loginUser(final String email, final String password) {
        new AsyncTask<Void, Void, JSONObject>() {

            DialogUtils progressDialog = new DialogUtils(LoginActivity.this,DialogUtils.Type.PROGRESS_DIALOG);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.showProgressDialog("Login", "please wait..", false);
                //progress
            }

            @Override
            protected JSONObject doInBackground(Void... params) {
                return new ServerRequest().userLogin(LoginActivity.this, email, password);
            }

            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                progressDialog.dismissProgressDialog();
                if (response != null) {
                    try {
                        if (!response.getBoolean("error")) {
                            //Toast.makeText(LoginActivity.this,"Success",Toast.LENGTH_LONG).show();
                            User user = new UserParser().parse(response.getJSONObject("user"));
                            loginPreferences.loginUser(user);
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }
                        else if(response.getBoolean("error")){
                            Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "oops!..something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }



}