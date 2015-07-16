package com.unlogicon.gitfollowers.followers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.unlogicon.gitfollowers.Model.Followers;
import com.unlogicon.gitfollowers.R;
import com.unlogicon.gitfollowers.UtilsApi;
import com.unlogicon.gitfollowers.user.UserActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nik on 16.07.2015.
 */
public class FollowersActivity extends ActionBarActivity {

    private Activity activity;

    private AQuery aq;

    private TextView no_user;
    private ProgressBar progressBar;

    private ListView listView;
    private FollowersAdapter adapter;
    private ArrayList<Followers> rows;

    public static final String KEY_LOGIN = "login";

    private String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        activity = this;
        aq = new AQuery(activity);

        Intent intent = getIntent();

        if (intent.hasExtra(KEY_LOGIN)){
            login = intent.getStringExtra(KEY_LOGIN);
        } else {
            finish();
        }

        no_user = (TextView) findViewById(R.id.no_user);

        listView = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rows = new ArrayList<Followers>();
        adapter = new FollowersAdapter(activity, rows);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(activity, UserActivity.class);
                i.putExtra(UserActivity.KEY_LOGIN, rows.get(position).login);
                startActivity(i);
            }
        });

        aq.ajax(UtilsApi.getCallBack(activity, "users/" + login + "/followers"));
    }

    public void onResponse(String url, String s, AjaxStatus status) {
        rows.clear();
        if (s != null) {
            switch (status.getCode()) {
                case 200:
                    adapter.notifyDataSetInvalidated();
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = jsonArray.getJSONObject(i);
                            rows.add(UtilsApi.parseFollowers(c));
                        }
                        if (rows.size() == 0){
                            no_user.setVisibility(View.VISIBLE);
                        } else {
                            no_user.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        AQUtility.debug("error List parser", e.toString());
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
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
