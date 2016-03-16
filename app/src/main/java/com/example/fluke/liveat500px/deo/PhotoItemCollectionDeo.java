package com.example.fluke.liveat500px.deo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by FLUKE on 2/19/2016 AD.
 */
public class PhotoItemCollectionDeo implements Parcelable {

    @SerializedName("success")      private boolean success;
    @SerializedName("data")         private List<PhotoItemDao> data;

    public PhotoItemCollectionDeo() {

    }

    protected PhotoItemCollectionDeo(Parcel in) {
        success = in.readByte() != 0;
        data = in.createTypedArrayList(PhotoItemDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhotoItemCollectionDeo> CREATOR = new Creator<PhotoItemCollectionDeo>() {
        @Override
        public PhotoItemCollectionDeo createFromParcel(Parcel in) {
            return new PhotoItemCollectionDeo(in);
        }

        @Override
        public PhotoItemCollectionDeo[] newArray(int size) {
            return new PhotoItemCollectionDeo[size];
        }
    };

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<PhotoItemDao> getData() {
        return data;
    }

    public void setData(List<PhotoItemDao> data) {
        this.data = data;
    }
}
