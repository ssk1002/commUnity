package com.wyp.chalkitup.models;

/**
 * Created by yatinkaushal on 2/18/17.
 */

public class ProjectItem {
    String name;
    String description;
    String cost;
    String userId;
    String username;
    String photoUrl;
    public double latitude;
    public double longitude;
    public String id;

    public ProjectItem(String name, String userId, String username, String photoUrl, String description, String cost) {
        this.name = name;
        this.userId = userId;
        this.username = username;
        this.photoUrl = photoUrl;
        this.description = description;
        this.cost = cost;

    }
}
