package io.krumbs.sdk.starter.Utils;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by SYED on 15-02-2017.
 */
public class DialogUtils {
    public static final String TITLE = "title";
    public static final String MESSAGE = "message";
    public static final String POSITIVE_BUTTON = "positive_button";
    public static final String NEGATIVE_BUTTON = "negative_button";
    public static final String RES_ID = "resid";
    public static final String TIME = "time";

    ProgressDialog progressDialog;
    Activity activity;

    public DialogUtils(Activity activity, String type){
        this.activity = activity;
        switch (type){
            case Type.PROGRESS_DIALOG:
                progressDialog = new ProgressDialog(activity);
                break;
            case Type.POPUP_DIALOG:
                break;
            case Type.AUTO_DISMISS_DIALOG:
                break;
        }
    }

    public class Type {
        public static final String PROGRESS_DIALOG = "progress_dialog";
        public static final String POPUP_DIALOG = "popup_dialog";
        public static final String AUTO_DISMISS_DIALOG = "auto_dismiss_dialog";
    }

    public void showProgressDialog(String title, String message, boolean cancelable){
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancelable);
        progressDialog.show();
    }

    public void showProgressDialog(String message, boolean cancelable){
        progressDialog.setMessage(message);
        progressDialog.setCancelable(cancelable);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    public void dismissProgressDialog(){
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
