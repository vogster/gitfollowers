package com.unlogicon.gitfollowers;

import com.androidquery.callback.AjaxCallback;
import com.unlogicon.gitfollowers.Model.Followers;
import com.unlogicon.gitfollowers.Model.User;

import org.json.JSONObject;

/**
 * Created by Nik on 16.07.2015.
 */
public class UtilsApi {

    public static AjaxCallback getCallBack(Object o, String method) {
        String url = Constants.API_URL + method;
        AjaxCallback<String> cb = new AjaxCallback<String>();
        cb.url(url).type(String.class).weakHandler(o, "onResponse").fileCache(false).expire(-1);
        return cb;
    }

    public static User parseUser(JSONObject userObject){
        User user = new User();
        user.login = userObject.optString("login");
        user.id = userObject.optInt("id");
        user.avatar_url = userObject.optString("avatar_url");
        user.gravatar_id = userObject.optString("gravatar_id");
        user.url = userObject.optString("url");
        user.html_url = userObject.optString("html_url");
        user.followers_url = userObject.optString("followers_url");
        user.following_url = userObject.optString("following_url");
        user.gists_url = userObject.optString("gists_url");
        user.starred_url = userObject.optString("starred_url");
        user.subscriptions_url = userObject.optString("subscriptions_url");
        user.organizations_url = userObject.optString("organizations_url");
        user.repos_url = userObject.optString("repos_url");
        user.events_url = userObject.optString("events_url");
        user.received_events_url = userObject.optString("received_events_url");
        user.type = userObject.optString("type");
        user.site_admin = userObject.optBoolean("site_admin");
        user.public_repos = userObject.optInt("public_repos");
        user.public_gists = userObject.optInt("public_gists");
        user.followers = userObject.optInt("followers");
        user.following = userObject.optInt("following");
        user.created_at = userObject.optString("created_at");
        user.updated_at = userObject.optString("updated_at");
        return user;
    }

    public static Followers parseFollowers(JSONObject followersObject){
        Followers followers = new Followers();
        followers.login = followersObject.optString("login");
        followers.id = followersObject.optInt("id");
        followers.avatar_url = followersObject.optString("avatar_url");
        followers.url = followersObject.optString("url");
        followers.html_url = followersObject.optString("html_url");
        followers.followers_url = followersObject.optString("followers_url");
        followers.following_url = followersObject.optString("following_url");
        followers.gists_url = followersObject.optString("gists_url");
        followers.starred_url = followersObject.optString("starred_url");
        followers.subscriptions_url = followersObject.optString("subscriptions_url");
        followers.organizations_url = followersObject.optString("organizations_url");
        followers.repos_url = followersObject.optString("repos_url");
        followers.events_url = followersObject.optString("events_url");
        followers.received_events_url = followersObject.optString("received_events_url");
        followers.type = followersObject.optString("type");
        followers.site_admin = followersObject.optBoolean("site_admin");
        return followers;
    }

}
