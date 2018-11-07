package com.example.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder>{
    Context mContext;
    public ArrayList<NewsItem>  mNewsList;

    public NewsRecyclerViewAdapter(Context context, ArrayList<NewsItem> newsList) {
        this.mContext = context;
        this.mNewsList = newsList;
    }


    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news_item, viewGroup, shouldAttachToParentImmediately);
        NewsViewHolder newsViewHolder = new NewsViewHolder(view);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        TextView date;

        public NewsViewHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
           // url = (TextView) itemView.findViewById(R.id.url);
            date = (TextView) itemView.findViewById(R.id.date);
        }

        public void bind(final int listIndex){
            title.setText("Titel:"  + mNewsList.get(listIndex).getTitle());
            description.setText("Description:" +mNewsList.get(listIndex).getDescription());
           // url.setText(mNewsList.get(listIndex).getUrl());
            date.setText("Date:"+mNewsList.get(listIndex).getPublishedAt());
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String urlString = mNewsList.get(listIndex).getUrl();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(urlString));
                    mContext.startActivity(intent);
                }
            });
        }
    }

}
