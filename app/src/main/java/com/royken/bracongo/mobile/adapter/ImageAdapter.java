package com.royken.bracongo.mobile.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by royken on 01/05/16.
 */
public class ImageAdapter extends BaseAdapter {

    private List<String> myList;
    private Context mContext;
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return myList.size();
    }

    public Object getItem(int position) {
        return null;
    }
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else{
            imageView = (ImageView) convertView;
        }
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions. inJustDecodeBounds = false ;
        bmOptions. inSampleSize = 4;
        bmOptions. inPurgeable = true ;
        Bitmap bitmap = BitmapFactory.decodeFile(myList.get(position), bmOptions);
        imageView.setImageBitmap(bitmap);
        return imageView;
    }

}
