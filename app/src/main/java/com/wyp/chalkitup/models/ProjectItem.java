package com.wyp.chalkitup.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yatinkaushal on 2/18/17.
 */

public class ProjectItem implements Parcelable {
    public String name;
    public String description;
    public String cost;
    public String userId;
    public String username;
    public String photoUrl;
    public double latitude;
    public double longitude;
    public String id;

    ProjectItem(){}

    public ProjectItem(String name, String userId, String username, String photoUrl, String description, String cost) {
        this.name = name;
        this.userId = userId;
        this.username = username;
        this.photoUrl = photoUrl;
        this.description = description;
        this.cost = cost;

    }

    protected ProjectItem(Parcel in) {
        name = in.readString();
        description = in.readString();
        cost = in.readString();
        userId = in.readString();
        username = in.readString();
        photoUrl = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        id = in.readString();
    }

    public static final Creator<ProjectItem> CREATOR = new Creator<ProjectItem>() {
        @Override
        public ProjectItem createFromParcel(Parcel in) {
            return new ProjectItem(in);
        }

        @Override
        public ProjectItem[] newArray(int size) {
            return new ProjectItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(cost);
        parcel.writeString(userId);
        parcel.writeString(username);
        parcel.writeString(photoUrl);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(id);
    }
}
