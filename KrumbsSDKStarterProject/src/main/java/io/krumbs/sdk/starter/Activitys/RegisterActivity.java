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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.krumbs.sdk.starter.Models.User;
import io.krumbs.sdk.starter.Parsers.UserParser;
import io.krumbs.sdk.starter.Preferences.LoginPreferences;
import io.krumbs.sdk.starter.R;
import io.krumbs.sdk.starter.ServerUtils.ServerRequest;
import io.krumbs.sdk.starter.Utils.DialogUtils;

/**
 * Created by SYED on 15-02-2017.
 */
public class RegisterActivity extends BaseActivity {

    EditText usernameET,emailET,passwordET,ageET,GenderET;
    Button signupBT;
    LoginPreferences loginPreferences;
    private TextView btnLinkToLoginScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginPreferences=new LoginPreferences(RegisterActivity.this);
        emailET=(EditText)findViewById(R.id.email);
        usernameET=(EditText)findViewById(R.id.name);
        passwordET=(EditText)findViewById(R.id.password);
        ageET=(EditText)findViewById(R.id.age);
        GenderET=(EditText)findViewById(R.id.gender);
        signupBT=(Button)findViewById(R.id.btnRegister);
        btnLinkToLoginScreen=(TextView)findViewById(R.id.btnLinkToLoginScreen);

        signupBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString();
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                String ageString=ageET.getText().toString();
                int age = 0;
                String gender=GenderET.getText().toString();
                try{
                    age=Integer.parseInt(ageString) ;
                }catch(NumberFormatException ex){ // handle your exception

                }
                if(gender.toLowerCase()=="male" || gender.toLowerCase()=="female"|| gender.toLowerCase()=="other")
                {
                    Toast.makeText(RegisterActivity.this, "Enter valid Gender", Toast.LENGTH_LONG).show();
                }
                if (emailValidator(email)!= true){
                    Toast.makeText(RegisterActivity.this, "Enter valid Email address", Toast.LENGTH_LONG).show();

                }
                if (email.equals("") || username.equals("") || password.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Enter valid details", Toast.LENGTH_LONG).show();
                }
                else if(username.length()<3){
                    Toast.makeText(RegisterActivity.this,"Length of name be greater than 3",Toast.LENGTH_LONG).show();

                }
                else if ( password.length()<5){
                    Toast.makeText(RegisterActivity.this,"Length of password should be greater than 5",Toast.LENGTH_LONG).show();

                }
                else if(age<0)
                {
                    Toast.makeText(RegisterActivity.this, "Enter valid Age", Toast.LENGTH_LONG).show();
                }
                else
                {
                    registeruser(username,email,password,gender,age);
                }

            }
        });

        btnLinkToLoginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    private void registeruser(final String username,final String email,final String password,final String gender, final int age){
        new AsyncTask<Void,Void,JSONObject>() {
            DialogUtils progressDialog =new DialogUtils(RegisterActivity.this, DialogUtils.Type.PROGRESS_DIALOG);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.showProgressDialog("Registering","please wait..",false);
            }
            @Override
            protected JSONObject doInBackground(Void... params) {
                return new ServerRequest().userRegistration(RegisterActivity.this,username,email,password,gender,age);
            }



            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                progressDialog.dismissProgressDialog();
                if (response != null) {
                    try {
                        if (!response.getBoolean("error")) {
                            Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                            Toast.makeText(RegisterActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                            User user = new UserParser().parse(response.getJSONObject("user"));
                            loginPreferences.loginUser(user);
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        } else if (response.getBoolean("error")) {
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
                            Toast.makeText(RegisterActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "oops!..something went wrong", Toast.LENGTH_LONG).show();

                }
            }
        }.execute();
    }
    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
