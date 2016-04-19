package com.royken.bracongo.mobile.util;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.royken.bracongo.mobile.R;

/**
 * Created by royken on 15/04/16.
 */
public class DataHolder {
    private int selected;
    private ArrayAdapter<CharSequence> adapter;

    public DataHolder(Context parent) {
        adapter = ArrayAdapter.createFromResource(parent, R.array.choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public ArrayAdapter<CharSequence> getAdapter() {
        return adapter;
    }

    public String getText() {
        return (String) adapter.getItem(selected);
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
