package io.krumbs.sdk.starter.Activitys;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.krumbs.sdk.starter.R;

/**
 * Created by SYED on 15-02-2017.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void applyFont(final Context context, final View root) {
        try {
            if (root instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) root;
                for (int i = 0; i < viewGroup.getChildCount(); i++)
                    applyFont(context, viewGroup.getChildAt(i));
            } else if (root instanceof TextView)
                ((TextView) root).setTypeface(Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font)));
        } catch (Exception e) {
            Log.e("ProjectName", String.format("Error occured when trying to apply %s font for %s view", "DroidSans", root));
            e.printStackTrace();
        }
    }
}