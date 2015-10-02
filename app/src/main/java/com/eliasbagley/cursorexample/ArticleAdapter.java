package com.eliasbagley.cursorexample;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.eliasbagley.cursorexample.Models.Article;
import com.eliasbagley.cursorexample.Views.ArticleCell;

/**
 * Created by eliasbagley on 9/30/15.
 */

class ArticleHolder extends RecyclerView.ViewHolder {
    public ArticleCell _view;

    public ArticleHolder(ArticleCell itemView) {
        super(itemView);
        _view = itemView;
    }

    public void bind(Article article) {
        _view.populate(article);
    }
}

public class ArticleAdapter extends CursorRecyclerAdapter<ArticleHolder> {
    public ArticleAdapter(Cursor cursor) {
        super(cursor);
    }

    @Override
    public void onBindViewHolderCursor(ArticleHolder holder, Cursor cursor) {
        holder.bind(Article.fromCursor(cursor));
    }

    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup parent, int i) {
        ArticleCell cell = new ArticleCell(parent.getContext());
        return new ArticleHolder(cell);
    }
}
