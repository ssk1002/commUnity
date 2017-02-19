package com.wyp.chalkitup.models;

import java.util.Date;

/**
 * Created by yatinkaushal on 2/18/17.
 */

public class MessageItem {
    public String userId;
    public String username;
    public String photoUrl;
    public String message;
    public Date date;
    public String messageId;

    public MessageItem() {}

    public MessageItem(final String userId, final String username, final String photoUrl, final String message, final Date date) {
        this.userId = userId;
        this.username = username;
        this.photoUrl = photoUrl;
        this.message = message;
        this.date = date;
    }

}
