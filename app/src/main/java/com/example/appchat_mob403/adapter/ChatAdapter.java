package com.example.appchat_mob403.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchat_mob403.DTO.Chat;
import com.example.appchat_mob403.DTO.UserCurrent;
import com.example.appchat_mob403.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_SENDER = 1;
    private static final int VIEW_TYPE_RECEIVER = 2;

    Context context;

    List<Chat> chatList;

    public ChatAdapter(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @Override
    public int getItemViewType(int position) {
        Chat chat = chatList.get(position);
        if (chat.idUser.equals(UserCurrent.getInstance().getCurrentUser().getId())) {
            return VIEW_TYPE_SENDER;
        } else {
            return VIEW_TYPE_RECEIVER;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_SENDER) {
            View senderView = inflater.inflate(R.layout.item_chat_send, parent, false);
            return new SenderMessageViewHolder(senderView);
        } else {
            View receiverView = inflater.inflate(R.layout.item_chat_receiver, parent, false);
            return new ReceiverMessageViewHolder(receiverView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chat chat = chatList.get(position);

        if (holder instanceof SenderMessageViewHolder) {
            // Xử lý tin nhắn từ người gửi
            SenderMessageViewHolder senderViewHolder = (SenderMessageViewHolder) holder;
            senderViewHolder.tv_send.setText(chat.getContent());
        } else if (holder instanceof ReceiverMessageViewHolder) {
            // Xử lý tin nhắn từ người nhận
            ReceiverMessageViewHolder receiverViewHolder = (ReceiverMessageViewHolder) holder;
            receiverViewHolder.tv_receiver.setText(chat.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class SenderMessageViewHolder extends RecyclerView.ViewHolder {
        TextView tv_send;
        public SenderMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_send = itemView.findViewById(R.id.tv_send);
        }
    }

    public class ReceiverMessageViewHolder extends RecyclerView.ViewHolder {
       TextView tv_receiver;
        public ReceiverMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_receiver = itemView.findViewById(R.id.tv_receiver);

        }
    }
}
