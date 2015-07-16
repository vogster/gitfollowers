package com.unlogicon.gitfollowers.user;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.unlogicon.gitfollowers.Model.User;
import com.unlogicon.gitfollowers.R;
import com.unlogicon.gitfollowers.Utils;
import com.unlogicon.gitfollowers.UtilsApi;

import org.json.JSONObject;

/**
 * Created by Nik on 17.07.2015.
 */
public class UserActivity extends ActionBarActivity {

    private Activity activity;
    private AQuery aq;

    private LinearLayout layout;
    private ProgressBar progressBar;

    private ImageView avatar;
    private TextView login;
    private TextView cnt_repos;
    private TextView date;
    private TextView link;
    private Button web;

    private User user;

    public static final String KEY_LOGIN = "login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        activity = this;
        aq = new AQuery(activity);

        Intent intent = getIntent();

        if (!intent.hasExtra(KEY_LOGIN))
            finish();

        layout = (LinearLayout) findViewById(R.id.layout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        avatar = (ImageView) findViewById(R.id.avatar);
        login = (TextView) findViewById(R.id.login);
        cnt_repos = (TextView) findViewById(R.id.cnt_repos);
        date = (TextView) findViewById(R.id.date);
        link = (TextView) findViewById(R.id.url);
        web = (Button) findViewById(R.id.open_web);

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(user.html_url));
                startActivity(i);
            }
        });

        aq.ajax(UtilsApi.getCallBack(activity, "users/" + intent.getStringExtra(KEY_LOGIN)));
    }


    public void onResponse(String url, String s, AjaxStatus status) {
        if (s != null) {
            switch (status.getCode()) {
                case 200:
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        user = UtilsApi.parseUser(jsonObject);
                    } catch (Exception e) {
                        AQUtility.debug("error List parser", e.toString());
                        e.printStackTrace();
                    }
                    aq.id(avatar).image(user.avatar_url);
                    login.setText(user.login);
                    cnt_repos.setText(getString(R.string.cnt_repos) + " " + String.valueOf(user.public_repos));
                    date.setText(getString(R.string.date) + " " + Utils.parseDate(user.created_at));
                    link.setText(getString(R.string.link) + " " + user.html_url);
                    layout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    break;
            }
        } else {
            switch (status.getCode()){
                case 404:
                    Toast.makeText(activity, status.getError(), Toast.LENGTH_LONG).show();
                    Toast.makeText(activity, getString(R.string.user_not_found), Toast.LENGTH_LONG).show();
                    break;
                case -101:
                    Toast.makeText(activity, status.getMessage(), Toast.LENGTH_LONG).show();
                    Toast.makeText(activity, getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
                    break;
            }
            aq.ajaxCancel();
            finish();
        }
    }


}
