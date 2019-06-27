package com.acadview.groupchat.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acadview.groupchat.Models.AllMethods;
import com.acadview.groupchat.Models.Message;
import com.acadview.groupchat.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder>{

    Context context;
    List<Message> messages;
    DatabaseReference messagedb;

    public MessageAdapter(Context context,List<Message> messages,DatabaseReference messagedb){

        this.context = context;
        this.messagedb = messagedb;
        this.messages = messages;

    }

    public void addMessage(Message message){
        this.messages.add(message);
        notifyDataSetChanged();
    }


    @Override
    public MessageAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return  new MessageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageAdapterViewHolder holder, int position) {
        Message message = messages.get(position);
        if(message.getName().equals(AllMethods.name)){
            holder.tvTitle.setText("You: " + message.getMessage());
            holder.tvTitle.setGravity(Gravity.START);
            holder.l1.setBackgroundColor(Color.parseColor("#6b778d"));
        }
        else {
            holder.tvTitle.setText(message.getName() + ":" +message.getMessage());
            holder.ibDelete.setVisibility(View.GONE);
        }
        holder.tvMessage.setText(message.getMessage());

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,tvMessage;
        ImageButton ibDelete;
        LinearLayout l1;

        public MessageAdapterViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ibDelete = (ImageButton) itemView.findViewById(R.id.ibDelete);
            tvMessage = (TextView) itemView.findViewById(R.id.LiMessage);
            l1 = (LinearLayout)itemView.findViewById(R.id.LiMessage);

           ibDelete.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   messagedb.child(messages.get(getPosition()).getKey()).removeValue();
               }
           });
        }
    }
}
