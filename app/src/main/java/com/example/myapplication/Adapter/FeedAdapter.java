package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.myapplication.R;
import com.example.myapplication.model.FeedModel;
import com.example.myapplication.model.MessageModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    ArrayList<FeedModel> list;
    Context context;

    public FeedAdapter(ArrayList<FeedModel> list, Context context){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View uAdapter = LayoutInflater.from(context).inflate(R.layout.user_feed_sample, parent, false);
        return new ViewHolder(uAdapter);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeedModel feed = list.get(position);
        Date date = new Date(feed.getTimeStamp());
        SimpleDateFormat simpleDate = new SimpleDateFormat("HH:mm");
        String dateSet = simpleDate.format(date);

        holder.userName.setText(feed.getUsername());
        holder.feedMessage.setText(feed.getMessage());
        holder.feedTime.setText(dateSet);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView userName, feedMessage, feedTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.feed_userName);
            feedMessage = itemView.findViewById(R.id.feed_message);
            feedTime = itemView.findViewById(R.id.feedTime);
        }
    }
}
