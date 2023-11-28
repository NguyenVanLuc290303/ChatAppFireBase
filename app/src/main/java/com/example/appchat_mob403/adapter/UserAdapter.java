package com.example.appchat_mob403.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchat_mob403.ChatDetails;
import com.example.appchat_mob403.DTO.User;
import com.example.appchat_mob403.R;

import java.util.List;

public class UserAdapter  extends RecyclerView.Adapter<UserAdapter.viewHolder>{

    Context context;
    List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_chat,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        User user = userList.get(position);
        holder.tv_name.setText(user.getName());
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatDetails.class);
                intent.putExtra("userObject", user);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_nameUser);
        }
    }
}
