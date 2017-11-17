package com.example.android.newsfeed;

import android.content.Context;

import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Jacquelyn Gboyor on 11/16/2017.
 */

public class JSONLoader extends AsyncTaskLoader<String> {

    //variable that stores search query string
    private String mQueryString;

    //Constructor
    public JSONLoader(Context context, String queryString){
        super(context);

        mQueryString = queryString;
    }

    //Invoke Load manager
    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    /**
     * Connect to network and returns results
     * @return
     */
    @Override
    public String loadInBackground() {

        String articles = Utils.getInfo(mQueryString);
        return articles;
    }
}
