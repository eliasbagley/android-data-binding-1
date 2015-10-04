package com.eliasbagley.cursorexample;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eliasbagley on 10/1/15.
 */

class ListChange {
    enum ListChangeType {
        INSERTION,
        DELETION,
        MOVE,
    }

    public ListChangeType type;
    public Integer index1;
    public Integer index2;
    public Integer value;

    public ListChange(ListChangeType type, Integer index1, Integer index2, Integer value) {
        this.type = type;
        this.index1 = index1;
        this.index2 = index2;
        this.value = value;
    }

}

public class CursorDiff {

    public static List<ListChange> diff(Cursor cursorA, Cursor cursorB, String key) {
        List<Integer> a = convertToList(cursorA, key);
        List<Integer> b = convertToList(cursorB, key);
        return diff(a, b);
    }

    public static List<ListChange> diff(List<Integer> a, List<Integer> b) {
        List<ListChange> changes = new ArrayList<>();

        recordDeletions(a, b, changes);
        recordInsertions(a, b, changes);
        recordMoves(a, b, changes);

        return changes;
    }

    private static void recordInsertions(List<Integer> a, List<Integer> b, List<ListChange> changes) {
        for (int i = 0; i < b.size(); i++) {
            Integer value = b.get(i);
            if (!a.contains(value)) {
                changes.add(new ListChange(ListChange.ListChangeType.INSERTION, i, null, value));
                a.add(i, value);
                recordInsertions(a, b, changes);
                return;
            }
        }
    }

    private static void recordDeletions(List<Integer> a, List<Integer> b, List<ListChange> changes) {
        for (int i = 0; i < a.size(); i++) {
            Integer value = a.get(i);
            if (!b.contains(value)) {
                changes.add(new ListChange(ListChange.ListChangeType.DELETION, i, null, value));
                a.remove(i);
                recordDeletions(a, b, changes);
                return;
            }
        }
    }

    private static void recordMoves(List<Integer> a, List<Integer> b, List<ListChange> changes) {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) != b.get(i)) {
                Integer value = a.remove(i);

                Integer to = b.indexOf(value);
                a.add(to, value);

                changes.add(new ListChange(ListChange.ListChangeType.MOVE, i, to, value));
                recordMoves(a, b, changes);
                return;
            }
        }
    }

    private static List<Integer> convertToList(Cursor cursor, String key) {
        List<Integer> list = new ArrayList<>();

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
