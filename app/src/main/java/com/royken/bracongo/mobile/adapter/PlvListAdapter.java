package com.royken.bracongo.mobile.adapter;

import android.content.Context;
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
import com.royken.bracongo.mobile.entities.Materiel;
import com.royken.bracongo.mobile.entities.Plv;

import java.util.List;

/**
 * Created by royken on 28/04/16.
 */
public class PlvListAdapter extends BaseAdapter {

    private List<Plv> plvs;

    private Context mContext;
    private LayoutInflater mInflater;
    @Override
    public int getCount() {
        return plvs.size();
    }

    @Override
    public Object getItem(int position) {
        return plvs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public PlvListAdapter(Context context, List<Plv> plvs) {
        mContext = context;
        this.plvs = plvs;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;
        FrameLayout layout;
        if (convertView == null) {
            layout = (FrameLayout) mInflater.inflate(R.layout.plv_list_item, parent, false);
        } else {
            layout = (FrameLayout) convertView;
        }

        ImageView img = (ImageView)layout.findViewById(R.id.image_view_plv);
        String firstLetter = String.valueOf(plvs.get(position).getNom().charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();
        //int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

        img.setImageDrawable(drawable);

        TextView tv_Nom = (TextView)layout.findViewById(R.id.nomPlv);
        tv_Nom.setText(plvs.get(position).getNom());

        tv_Nom.setTag(position);
        return layout;
    }
}
