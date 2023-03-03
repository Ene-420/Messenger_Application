package com.example.myapplication.Adapter;



import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.MessageModel;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessageModel> messageModels;
    String recId;
    Context context;


    int OUTGOING_VIEW_TYPE = 1;
    int INCOMING_VIEW_TYPE = 2;
    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }


    public ChatAdapter(ArrayList<MessageModel> messageModels, String recId, Context context) {
        this.messageModels = messageModels;
        this.recId = recId;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == OUTGOING_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_outgoing,parent,false);
            return new OutgoingViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.sample_incoming,parent,false);
            return new IncomingViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messageModels.get(position).getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            return OUTGOING_VIEW_TYPE;
        }
        else {
            return INCOMING_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel model = messageModels.get(position);

        Date date = new Date(model.getTimeStamp());
        SimpleDateFormat simpleDate = new SimpleDateFormat("HH:mm");
        String dateSet = simpleDate.format(date);
        if(holder.getClass() == OutgoingViewHolder.class){

            ((OutgoingViewHolder) holder).outgoing_msg.setText(model.getMessage());
            ((OutgoingViewHolder) holder).outgoing_time.setText(dateSet);

        }
        else{
            ((IncomingViewHolder) holder).incoming_msg.setText(model.getMessage());
            ((IncomingViewHolder) holder).incoming_time.setText(dateSet);
        }

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class IncomingViewHolder extends RecyclerView.ViewHolder {
        TextView incoming_msg, incoming_time;


        public IncomingViewHolder(@NonNull View itemView) {
            super(itemView);

            incoming_msg = itemView.findViewById(R.id.incoming_text);
            incoming_time= itemView.findViewById(R.id.incoming_time);


        }
    }

    public class OutgoingViewHolder extends RecyclerView.ViewHolder{
        TextView outgoing_msg, outgoing_time;


        public OutgoingViewHolder(@NonNull View itemView) {
            super(itemView);

            outgoing_msg = itemView.findViewById(R.id.outgoing_text);
            outgoing_time = itemView.findViewById(R.id.outgoing_time);

        }
    }
}
