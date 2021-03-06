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
import com.royken.bracongo.mobile.entities.PointDeVente;

import java.util.List;

/**
 * Created by royken on 11/04/16.
 */
public class PoinDeVenteCustomAdapter extends BaseAdapter {

    private static final String TAG = "PoinDeVenteCustomAdapter";
    private Context mContext;
    private LayoutInflater mInflater;
    private List<PointDeVente> pointDeVentes;

    public PoinDeVenteCustomAdapter(Context context, List<PointDeVente> pointDeVentes) {
        mContext = context;
        this.pointDeVentes = pointDeVentes;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return pointDeVentes.size();
    }

    @Override
    public Object getItem(int position) {
        return pointDeVentes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pointDeVentes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;
        FrameLayout layout;
        if (convertView == null) {
            layout = (FrameLayout) mInflater.inflate(R.layout.planning, parent, false);
        } else {
            layout = (FrameLayout) convertView;
        }

        ImageView img = (ImageView)layout.findViewById(R.id.image_view);
        String firstLetter = String.valueOf(pointDeVentes.get(position).getNom().charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();
        //int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(firstLetter, color,color); // radius in px

        img.setImageDrawable(drawable);

        TextView tv_Nom = (TextView)layout.findViewById(R.id.nomPdv);
        tv_Nom.setText(pointDeVentes.get(position).getNom());
        TextView tv_Type = (TextView)layout.findViewById(R.id.typePdv);
        tv_Type.setText(pointDeVentes.get(position).getType());
        TextView tv_Regime = (TextView)layout.findViewById(R.id.regPdv);
        tv_Regime.setText(pointDeVentes.get(position).getRegime());

        String categorie = pointDeVentes.get(position).getCategorie();
       // Log.i("La couleurrrrr",categorie);
        TextView tv_Categorie = (TextView)layout.findViewById(R.id.catPdv);
        if(categorie.toString().trim().equalsIgnoreCase("Di")){
            tv_Categorie.setBackgroundColor(Color.parseColor("#b9f2ff"));
        }
        if(categorie.toString().trim().equalsIgnoreCase("Br")){
            tv_Categorie.setBackgroundColor(Color.parseColor("#cd7f32"));
        }
        if(categorie.toString().trim().equalsIgnoreCase("Ag")){
            tv_Categorie.setBackgroundColor(Color.parseColor("#c0c0c0"));
        }
        if(categorie.toString().trim().equalsIgnoreCase("Or")){

            tv_Categorie.setBackgroundColor(Color.parseColor("#ffd700"));
        }

        tv_Categorie.setText(categorie);

        tv_Nom.setTag(position);
        return layout;
    }

    @Override
    public boolean isEmpty() {
        return pointDeVentes.isEmpty();
    }
}
