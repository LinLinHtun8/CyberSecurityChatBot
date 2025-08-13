package com.example.chatbot.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbot.Message;
import com.example.chatbot.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.viewHolder> {

    List<Message> messageList;
    public MessageAdapter(List<Message> messages){
        this.messageList = messages;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View chatview = LayoutInflater.from(parent.getContext()).inflate(R.layout.aichat, null);
        return new viewHolder(chatview);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Message message = messageList.get(position);
        if(message.getSendby().equals(message.getSent_By_User())){
            holder.leftChatView.setVisibility(View.GONE);
            holder.rightChatView.setVisibility(View.VISIBLE);
            holder.rightTextView.setText(message.getMessage());
        }
        if(message.getSendby().equals(message.getSent_By_Model())){
            holder.rightChatView.setVisibility(View.GONE);
            holder.leftChatView.setVisibility(View.VISIBLE);
            holder.leftTextView.setText(message.getMessage());
        }else if (message.getSendby().equals(Message.Typing)) {
            holder.rightChatView.setVisibility(View.GONE);
            holder.leftChatView.setVisibility(View.VISIBLE);
            holder.leftTextView.setText(Message.Typing);

        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        LinearLayout rightChatView, leftChatView;
        TextView leftTextView,rightTextView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatView=itemView.findViewById(R.id.ailayout);
            leftTextView=itemView.findViewById(R.id.aichat);
            rightChatView=itemView.findViewById(R.id.humanlayout);
            rightTextView=itemView.findViewById(R.id.humanchat);
        }
    }

}
