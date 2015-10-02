package com.eliasbagley.cursorexample;

import android.database.Cursor;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by eliasbagley on 10/1/15.
 */

class CursorDiffResult {
    public List<Integer> deleted = new ArrayList<>();
    public List<Integer> inserted = new ArrayList<>();
    public List<Integer> updated = new ArrayList<>();
    public List<Pair<Integer, Integer>> moved = new ArrayList<>();
}

public class CursorDiff {

    public static CursorDiffResult diff(Cursor cursor1, Cursor cursor2, String key) {

        CursorDiffResult result = new CursorDiffResult();

        // Maps id to index
        HashMap<Integer, Integer> map1 = new HashMap<>();
        HashMap<Integer, Integer> map2 = new HashMap<>();

        ArrayList<Integer> list1 = convertToList(cursor1, key);
        ArrayList<Integer> list2 = convertToList(cursor2, key);

        for (int i = 0; i < list1.size(); i++) {
            Integer value = list1.get(i);
            map1.put(value, i);
        }

        for (int i = 0; i < list2.size(); i++) {
            Integer value = list2.get(i);
            map2.put(value, i);
        }


        // Find moved and deleted

        for (Integer id : map1.keySet()) {
            Integer idx1 = map1.get(id);
            Integer idx2 = map2.get(id);

            if (idx2 == null) {
                result.deleted.add(idx1);
            } else if (idx1 != idx2) {
                result.moved.add(new Pair(idx1, idx2));
                map2.remove(id);
            } else if (idx1 == idx2) {
                result.updated.add(idx1); // Force an update each time for now
                map2.remove(id);
            }
        }

        // Find inserted index

        for (Integer id : map2.keySet()) {
            Integer idx = map2.get(id);
            result.inserted.add(idx);
        }

        return result;
    }

    private static ArrayList<Integer> convertToList(Cursor cursor, String key) {
        ArrayList<Integer> list = new ArrayList<>();

        if (cursor == null) {
            return list;
        }

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int index = cursor.getColumnIndexOrThrow(key);
            int value = cursor.getInt(index);
            list.add(value);
        }

        return list;
    }
}
