package io.krumbs.sdk.starter.ServerUtils;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by SYED on 15-02-2017.
 */
public class ServerRequest {

    HttpClientWrapper clientWrapper;
    public  ServerRequest(){
        clientWrapper = new HttpClientWrapper();
    }

    public JSONObject userLogin(Context context, String email, String password){
        JSONObject jsonObject = null;
        try {
            JSONObject request = new JSONObject();
            request.put("email", email);
            request.put("password", password);
            Log.e("JSON-REQUEST", request.toString());
            String response = clientWrapper.doPostRequest(Urls.BASE_URL+Urls.LOGIN_URL,request.toString());
            jsonObject = new JSONObject(response);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject userRegistration(Context context,String name, String email, String password,String gender,Integer age){
        JSONObject jsonObject = null;
        try {
            JSONObject request = new JSONObject();
            request.put("name",name);
            request.put("email", email);
            request.put("password", password);
            request.put("gender", gender );
            request.put("age", age);
            Log.e("JSON-REQUEST",request.toString());
            String response = clientWrapper.doPostRequest(Urls.BASE_URL+Urls.REGISTER_URL,request.toString());
            jsonObject = new JSONObject(response);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
}
