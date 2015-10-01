package com.eliasbagley.cursorexample;

import android.database.Cursor;

import com.eliasbagley.cursorexample.Models.BaseModel;

/**
 * Created by eliasbagley on 9/30/15.
 */

public interface CursorConvertable {
    BaseModel fromCursor(Cursor cursor);
}

