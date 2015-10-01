package com.eliasbagley.cursorexample.Views;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eliasbagley.cursorexample.Models.Article;
import com.eliasbagley.cursorexample.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by eliasbagley on 9/30/15.
 */

public class ArticleCell extends RelativeLayout {

    @InjectView(R.id.article_title) TextView _title;

    public ArticleCell(Context context) {
        super(context);
        initialize(context);
    }

    public ArticleCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public ArticleCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.cell_article, this);
        ButterKnife.inject(this);
    }

    public void populate(Article article) {
        _title.setText(article.title);
    }
}
