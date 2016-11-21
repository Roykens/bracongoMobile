package com.royken.bracongo.mobile.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.royken.bracongo.mobile.R;
import com.royken.bracongo.mobile.entities.Boisson;

import java.util.List;

/**
 * Created by royken on 28/04/16.
 */
public class BoissonListAdapter extends BaseAdapter {

    private List<Boisson> boissons;
    private Context mContext;
    private LayoutInflater mInflater;
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
        return position;
    }

    public BoissonListAdapter(Context context, List<Boisson> boissons) {
        mContext = context;
        this.boissons = boissons;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;
        FrameLayout layout;
        if (convertView == null) {
            layout = (FrameLayout) mInflater.inflate(R.layout.boisson_list_item, parent, false);
        } else {
            layout = (FrameLayout) convertView;
        }

        ImageView img = (ImageView)layout.findViewById(R.id.image_view_boisson);
        String firstLetter = String.valueOf(boissons.get(position).getNom().charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();
        //int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

        img.setImageDrawable(drawable);

        TextView tv_Nom = (TextView)layout.findViewById(R.id.nomboisson);
        tv_Nom.setText(boissons.get(position).getNom());
        TextView tv_Prix = (TextView)layout.findViewById(R.id.prixBoisson);
        //Log.i("Prix de la bois", boissons.get(position).getPrix()+"");
        tv_Prix.setText(boissons.get(position).getPrix() + " Fc");
        tv_Nom.setTag(position);
        return layout;
    }
}
