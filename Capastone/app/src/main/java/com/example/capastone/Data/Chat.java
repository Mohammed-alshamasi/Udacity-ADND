package com.example.capastone.Data;

public class Chat {

    private String sender,receiver,msg;
    private boolean isSeen;

    public Chat(String sender, String receiver, String msg,Boolean isSeen) {
        this.sender = sender;
        this.receiver = receiver;
        this.msg = msg;
        this.isSeen=isSeen;
    }

    public Chat() {

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
