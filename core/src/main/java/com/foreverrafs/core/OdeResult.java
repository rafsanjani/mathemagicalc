package com.foreverrafs.core;

import android.os.Parcel;
import android.os.Parcelable;

public class OdeResult implements Parcelable {
    private double x, y, n;

    public OdeResult(double y, double x, double n) {
        this.x = x;
        this.y = y;
        this.n = n;
    }


    public static final Creator<OdeResult> CREATOR = new Creator<OdeResult>() {
        @Override
        public OdeResult createFromParcel(Parcel in) {
            return new OdeResult(in);
        }

        @Override
        public OdeResult[] newArray(int size) {
            return new OdeResult[size];
        }
    };

    protected OdeResult(Parcel in) {
        x = in.readDouble();
        y = in.readDouble();
        n = in.readDouble();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getN() {
        return n;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(x);
        parcel.writeDouble(y);
        parcel.writeDouble(n);
    }
}
