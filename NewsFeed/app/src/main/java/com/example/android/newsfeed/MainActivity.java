package com.example.android.newsfeed;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String > {

    /*
    Declare needed variables
     */
    private EditText searchArticles;
    private Button btn;
    private ListView list;
    private TextView noResultsText;
    private static final String TITLE = "webTitle";
    private static final String SECTIONAME = "sectionName";
    private static final String RATING = "starRating";
    private static final String AUTHOR_ARRAY = "tags";
    private static final String AUTHOR = "webTitle";
    private static final String WEBSITE = "webUrl";
    private static final String DATE = "webPublicationDate";
    private ArticleAdapter adapter;
    /*
    --------------------------------------------------------------------
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchArticles = (EditText) findViewById(R.id.searchText);
        list = (ListView) findViewById(R.id.list);
        noResultsText = (TextView) findViewById(R.id.example);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

        //Button pressed to search
        btn = (Button) findViewById(R.id.searchButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What user typed in searchbox
                String queryString = searchArticles.getText().toString();

                //Check for internet connection
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                //If internet is available and query isn't empty then start loader
                if (networkInfo != null && networkInfo.isConnectedOrConnecting() && queryString.length() != 0) {
                    noResultsText.setText("loading...");
                    Bundle queryBundle = new Bundle();
                    queryBundle.putString("queryString", queryString);
                    getSupportLoaderManager().restartLoader(0, queryBundle, MainActivity.this);
                } else {
                    if (queryString.length() == 0) {
                        //If no results display this text
                        noResultsText.setText("No Search term");
                    }
                }
            }
        });

        /*
        Article intents to click and go to each article's web link
        */
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArticleDetails currentArticle = adapter.getItem(position);
                Uri uri = Uri.parse(currentArticle.getWebsite());
                Intent website = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(website);
            }
        });
    }//On Create
    /*
    -------------------------------------------------------------------
     */
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {

        return new JSONLoader(this, args.getString("queryString"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        System.out.print(data); //Print out results for me to see in console
        ArrayList<ArticleDetails> articleList = new ArrayList<>();
        String pic;

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject newsArray = jsonObject.getJSONObject("response");
            JSONArray results = newsArray.getJSONArray("results");

            //For loop to add each relevant article to arraylist
            for (int i = 0; i < results.length(); i++) {
                JSONObject currentarticle = results.getJSONObject(i);
                JSONObject field = currentarticle.getJSONObject("fields");
                JSONArray tag = currentarticle.getJSONArray(AUTHOR_ARRAY);

                //Check to see if picture is available for article
                if (currentarticle.getJSONObject("fields").has("thumbnail")) {
                    pic = currentarticle.getJSONObject("fields").optString("thumbnail", "none");
                } else {
                    pic = "none";
                }

                String titles = currentarticle.optString(TITLE, "No Title");
                String section = currentarticle.optString(SECTIONAME, "No Section Name");
                String rating = field.optString(RATING, "No Rating");

                String authors = tag.getJSONObject(0).optString(AUTHOR, "No Author");
                String website = currentarticle.optString(WEBSITE, "None");
                String date = currentarticle.optString(DATE, "None");

                ArticleDetails news = new ArticleDetails(titles, section, rating, pic, authors, website, date);
                articleList.add(news);

            }//for

            //Set Adapter to display contents in arraylist
            adapter = new ArticleAdapter(MainActivity.this, articleList);
            list.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

}