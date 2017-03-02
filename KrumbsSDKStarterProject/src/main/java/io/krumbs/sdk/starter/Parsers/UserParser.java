package io.krumbs.sdk.starter.Parsers;

import org.json.JSONObject;

import io.krumbs.sdk.starter.Models.User;

/**
 * Created by SYED on 15-02-2017.
 */
public class UserParser {

    public User parse(JSONObject object){
        User user = null;

        String error = "",uid = "", name = "",email = "",gender = "";
        Integer age=-1;


        try {
            if (object.has("error")) {
                error = object.getString("error");
            }
            if (object.has("name")){
                name = object.getString("name");
            }
            if (object.has("email")){
                email = object.getString("email");
            }
            if (object.has("gender")){
                gender = object.getString("gender");
            }
            if (object.has("age")){
                age = object.getInt("age");
            }


            user = new User(uid,name,email,gender,age);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

}
