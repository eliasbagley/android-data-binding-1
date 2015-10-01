package com.eliasbagley.cursorexample.Models;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eliasbagley on 9/29/15.
 */


@Table(name = "Articles", id = BaseColumns._ID)
public class Article extends BaseModel {

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String BODY = "body";

    @SerializedName(ID)
    @Column(name = ID, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public Integer id;

    @SerializedName(TITLE)
    @Column(name = TITLE)
    public String title;

    @SerializedName(BODY)
    @Column(name = BODY)
    public String body;

    public Article() {
    }

    public static Article fromCursor(Cursor cursor) {
        Article article = new Article();
        article.loadFromCursor(cursor);
        return article;
    }
}
