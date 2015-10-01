package com.eliasbagley.cursorexample;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.eliasbagley.cursorexample.Models.Article;
import com.eliasbagley.cursorexample.Views.ArticleCell;

import timber.log.Timber;

/**
 * Created by eliasbagley on 9/30/15.
 */
public class ArticleAdapter extends CursorAdapter {
    public ArticleAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public ArticleAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        ArticleCell cell = new ArticleCell(context);
        cell.populate(Article.fromCursor(cursor));
        return cell;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ArticleCell cell = (ArticleCell) view;
        cell.populate(Article.fromCursor(cursor));
    }
}
