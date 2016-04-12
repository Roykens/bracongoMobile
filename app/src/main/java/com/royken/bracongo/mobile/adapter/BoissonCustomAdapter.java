package com.royken.bracongo.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.royken.bracongo.mobile.R;
import com.royken.bracongo.mobile.entities.Boisson;

import java.util.List;

/**
 * Created by royken on 11/04/16.
 */
public class BoissonCustomAdapter extends BaseAdapter {

    private static final String TAG = "BoissonCustomAdapter";
    private List<Boisson> boissons;
    private Context mContext;
    private LayoutInflater mInflater;

    public static String getTAG() {
        return TAG;
    }

    public List<Boisson> getBoissons() {
        return boissons;
    }

    public void setBoissons(List<Boisson> boissons) {
        this.boissons = boissons;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public LayoutInflater getmInflater() {
        return mInflater;
    }

    public void setmInflater(LayoutInflater mInflater) {
        this.mInflater = mInflater;
    }

    public BoissonCustomAdapter(Context context, List<Boisson> boissons) {
        mContext = context;
        this.boissons = boissons;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return boissons.size();
    }

    @Override
    public Object getItem(int position) {
        return boissons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return boissons.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layoutItem;
        FrameLayout layout;
        if (convertView == null) {
            layout = (FrameLayout) mInflater.inflate(R.layout.test, parent, false);
        } else {
            layout = (FrameLayout) convertView;
        }

        TextView tv_Nom = (TextView)layout.findViewById(R.id.textView2);
        tv_Nom.setText(boissons.get(position).getNom());
        tv_Nom.setTag(position);
        return layout;

    }
}
