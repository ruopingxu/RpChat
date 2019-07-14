package com.ruoping.rpchat;

import java.util.List;
import java.util.Set;

public class Conversation {

    public final String id;
    final List<Message> messageList;
    final Set<String> memberNames;

    public Conversation(String id, List<Message> messageList, Set<String> memberNames) {
        this.id = id;
        this.messageList = messageList;
        this.memberNames = memberNames;
    }

    public void addUser(String username) {
        memberNames.add(username);
    }

    public void addMessage(String text, String username) {
        messageList.add(new Message(text, String.valueOf(System.currentTimeMillis()), username));
    }

    public static class Message {
        public String text;
        public String timestamp;
        public String username;

        public Message(String text, String timestamp, String username) {
            this.text = text;
            this.timestamp = timestamp;
            this.username = username;
        }
    }
}
