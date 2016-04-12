package com.royken.bracongo.mobile.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.royken.bracongo.mobile.R;
import com.royken.bracongo.mobile.entities.Boisson;
import com.royken.bracongo.mobile.entities.PointDeVente;

import java.util.List;

/**
 * Created by royken on 11/04/16.
 */
public class PoinDeVenteCustomAdapter extends BaseAdapter {

    private static final String TAG = "BoissonCustomAdapter";
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

        TextView tv_Nom = (TextView)layout.findViewById(R.id.nomPdv);
        tv_Nom.setText(pointDeVentes.get(position).getNom());
        TextView tv_Type = (TextView)layout.findViewById(R.id.typePdv);
        tv_Type.setText(pointDeVentes.get(position).getType());
        TextView tv_Regime = (TextView)layout.findViewById(R.id.regPdv);
        tv_Regime.setText(pointDeVentes.get(position).getRegime());

        String categorie = pointDeVentes.get(position).getCategorie();
        Log.i("La couleurrrrr",categorie);
        TextView tv_Categorie = (TextView)layout.findViewById(R.id.catPdv);
        if(categorie.trim() == "Di"){
            tv_Categorie.setBackgroundColor(Color.parseColor("#b9f2ff"));
        }
        if(categorie.trim() == "Br"){
            tv_Categorie.setBackgroundColor(Color.parseColor("#cd7f32"));
        }
        if(categorie.trim() == "Ag"){
            tv_Categorie.setBackgroundColor(Color.parseColor("#c0c0c0"));
        }
        if(categorie.trim().equalsIgnoreCase("0r")){
            Log.i("rrrrrrrrrrrrrrrrrrrrr","J'ai trouve l'orrrrrrrrrrrrr");
            tv_Categorie.setBackgroundColor(Color.parseColor("#ffd700"));
        }
        tv_Categorie.setText(categorie);

        tv_Nom.setTag(position);
        return layout;
    }
}
