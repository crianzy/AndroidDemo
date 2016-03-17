package com.zuimeia.aidl_demo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenzhiyong on 16/3/6.
 */
public class Book implements Parcelable {

    private String mDesc;
    private int mPrise;

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

    public int getPrise() {
        return mPrise;
    }

    public void setPrise(int prise) {
        mPrise = prise;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mDesc);
        dest.writeInt(this.mPrise);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.mDesc = in.readString();
        this.mPrise = in.readInt();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
