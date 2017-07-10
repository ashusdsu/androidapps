package com.example.ashu.hometownlocation;

import java.util.Date;

/**
 * Created by ashu on 14/4/17.
 */

public class Chat {
    //public String sender;
    public String sender;
    public String receiver;
    public String senderUid;
    public String receiverUid;
    public String message;
    public Date timestamp;

    public Chat() {}

    public Chat(String sender, String receiver, String senderUid, String receiverUid, String message, Date timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
        this.message = message;
        this.timestamp = timestamp;
    }
}
