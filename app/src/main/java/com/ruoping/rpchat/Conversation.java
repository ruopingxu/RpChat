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

    public void addMessage(Message message) {
        messageList.add(message);
    }

    public Message getMessage(int index) {
        return messageList.get(index);
    }

    public int getLength() {
        return messageList.size();
    }

    public static class Message {
        public final String text;
        public final String timestamp;
        public final String username;

        public Message(String text, String timestamp, String username) {
            this.text = text;
            this.timestamp = timestamp;
            this.username = username;
        }
    }
}
