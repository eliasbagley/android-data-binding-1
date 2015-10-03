package com.eliasbagley.cursorexample;

import android.database.Cursor;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.activeandroid.content.ContentProvider;
import com.eliasbagley.cursorexample.Models.Article;


import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.main_list_view) ListView _listView;
    private ArticleAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        _adapter = new ArticleAdapter(this, null, 0);
        _listView.setAdapter(_adapter);

        insertItems();
        setup();
    }

    private void insertItems() {
        Article a1 = new Article();
        a1.id = 1;
        a1.title = "a1";
        a1.body = "body1";


        Article a2 = new Article();
        a2.id = 2;
        a2.title = "a2";
        a2.body = "body2";

        a1.save();
        a2.save();
    }

    private void setup() {
        getSupportLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(MainActivity.this,
                        ContentProvider.createUri(Article.class, null),
                        null, null, null, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                _adapter.swapCursor(cursor);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                _adapter.swapCursor(null);
            }
        });

    }
}
