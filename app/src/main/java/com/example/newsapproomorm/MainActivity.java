package com.example.newsapproomorm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.newsapproomorm.data.NewsItem;
import com.example.newsapproomorm.data.NewsItemViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private ArrayList<NewsItem> articles = new ArrayList<>();
    private NewsRecyclerViewAdapter mAdapter;
    private NewsItemViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);

        mRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        mAdapter = new NewsRecyclerViewAdapter(this, articles);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mViewModel.getAllNews().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                populateRecyclerView(newsItems);
            }
        });

//        getNewsQuery();
//        Log.d(TAG, "adapter size = " + mAdapter.getItemCount());
    }

//    private void getNewsQuery() {
//        Log.d(TAG, "get news query");
//        URL newsAPIUrl = NetworkUtils.buildUrl();
//        new NewsQueryTask().execute(newsAPIUrl);
//    }

//    public class NewsQueryTask extends AsyncTask<URL, Void, String> {
//        @Override
//        protected String doInBackground(URL... params) {
//            URL url = params[0];
//            String newsAPIResults = null;
//            try {
//                newsAPIResults = NetworkUtils.getResponseFromHttpUrl(url);
//                Log.d(TAG, newsAPIResults + " (1)");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return newsAPIResults;
//        }
//
//        @Override
//        protected void onPostExecute(String newsAPIResults) {
//            if (newsAPIResults != null && !newsAPIResults.equals("")) {
//                Log.d(TAG, newsAPIResults + " (2)");
//                populateRecyclerView(newsAPIResults);
//            }
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            mViewModel.sync();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public void populateRecyclerView(String jObject) {
//        Log.d(TAG, "parse news");
//        articles = JsonUtils.parseNews(jObject);
//        mAdapter.mArticles.addAll(articles);
//        mAdapter.notifyDataSetChanged();
//        Log.d(TAG, "adapter size = " + mAdapter.getItemCount());
//    }

    public void populateRecyclerView(List<NewsItem> newsItems) {
        Log.d(TAG, "parse news");
        mAdapter.mArticles.addAll(newsItems);
        mAdapter.notifyDataSetChanged();
        Log.d(TAG, "adapter size = " + mAdapter.getItemCount());
    }


}
