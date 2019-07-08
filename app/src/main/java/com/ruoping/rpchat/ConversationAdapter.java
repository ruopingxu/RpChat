package com.ruoping.rpchat;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.MessageViewHolder> {
    private List<String> messages;
    public ConversationAdapter(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public ConversationAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_layout, parent, false);

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder vh, int position) {
        vh.setContent(messages.get(position));
    }

    @Override
    public int getItemCount(){
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        public View messageLayout;
        public TextView messageText;

        public MessageViewHolder(View messageLayout) {
            super(messageLayout);
            this.messageLayout = messageLayout;
            this.messageText = messageLayout.findViewById(R.id.message_text);
        }

        public void setContent(String text) {
            messageText.setText(text);
        }
    }
}
