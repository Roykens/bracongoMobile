package com.royken.bracongo.mobile.entities.projection;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by royken on 14/04/16.
 */
public class BoissonParcelableCreator implements Parcelable.Creator<BoissonProjection> {
    public BoissonProjection createFromParcel(Parcel source) {
        return new BoissonProjection(source);
    }
    public BoissonProjection[] newArray(int size) {
        return new BoissonProjection[size];
    }
}
