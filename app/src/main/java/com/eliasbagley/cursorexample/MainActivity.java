package com.eliasbagley.cursorexample;

import android.database.Cursor;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.ListView;

import com.activeandroid.content.ContentProvider;
import com.eliasbagley.cursorexample.Models.Article;


import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.main_list_view) RecyclerView _listView;

    private ArticleAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

//        _adapter = new ArticleAdapter(this, null, 0);
        _adapter = new ArticleAdapter(null);
        _listView.setAdapter(_adapter);
        _listView.setLayoutManager(new LinearLayoutManager(this));

        insertItems();
        setup();
    }

    private void insertItems() {
        final Article a1 = new Article();
        a1.id = 1;
        a1.title = "a1";
        a1.body = "body1";


        final Article a2 = new Article();
        a2.id = 2;
        a2.title = "a2";
        a2.body = "body2";

        Article a4 = new Article();
        a4.id = 4;
        a4.title = "a4";
        a4.body = "body4";

        Article a5 = new Article();
        a5.id = 5;
        a5.title = "a5";
        a5.body = "body5";


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                a1.title = "zerp";

                a1.save();
//                Article a6 = new Article();
//                a6.id = 6;
//                a6.title = "a6";
//                a6.body = "body6";
//
//                a1.title = "DERRP";
//
//                a1.save();
//                a6.save();
//                a2.delete();

            }
        }, 8000);

        a1.save();
        a2.save();
        a4.save();
        a5.save();
    }

    private void setup() {
        getSupportLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(MainActivity.this,
                        ContentProvider.createUri(Article.class, null),
                        null, null, null, Article.TITLE + " ASC");
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                Timber.e("swapping cursor");
                _adapter.swapCursor(cursor);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                _adapter.swapCursor(null);
            }
        });

    }
}
