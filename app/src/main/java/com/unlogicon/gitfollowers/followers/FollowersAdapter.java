package com.unlogicon.gitfollowers.followers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.unlogicon.gitfollowers.Model.Followers;
import com.unlogicon.gitfollowers.R;

import java.util.ArrayList;

/**
 * Created by Nik on 16.07.2015.
 */
public class FollowersAdapter extends BaseAdapter {

    private final AQuery listAq;
    Activity activity;
    ArrayList<Followers> data;
    LayoutInflater layoutInflater;

    public FollowersAdapter(Activity activity, ArrayList<Followers> data) {
        this.data = data;
        this.activity = activity;
        listAq = new AQuery(activity);
        layoutInflater = activity.getLayoutInflater();

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        if (view == null) {
            view = layoutInflater.inflate(R.layout.adapter_followers, null);
        }

        final Followers followers = data.get(position);
        final AQuery aq = listAq.recycle(view);

        aq.id(R.id.avatar).image(followers.avatar_url);
        aq.id(R.id.login).text(followers.login);


        return view;
    }

}
