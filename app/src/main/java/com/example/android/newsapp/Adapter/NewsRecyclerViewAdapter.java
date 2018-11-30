package com.example.android.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.newsapp.Models.NewsItem;
import com.example.android.newsapp.Models.NewsItemViewModel;
import com.example.android.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    static Context mContext;
    public static List<NewsItem> mNewsList;

    public void setNewsList(List<NewsItem> newsItems) {
        this.mNewsList = newsItems;
        notifyDataSetChanged();
    }

    public NewsRecyclerViewAdapter(Context context, List<NewsItem> newsList) {
        this.mContext = context;
        this.mNewsList = newsList;
    }

//    public NewsRecyclerViewAdapter(Context context, NewsItemViewModel viewModel) {
//        this.mContext = context;
//        this.mViewModel = viewModel;
//    }

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
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        if (mNewsList != null)
            return mNewsList.size();
        else
            return 0;
    }



//    public List<NewsItem> getNewsList()
//    {return mNewsList;
//    }


    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        TextView date;
        ImageView image;

        public NewsViewHolder(View itemView){
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
           // url = (TextView) itemView.findViewById(R.id.url);
            date = (TextView) itemView.findViewById(R.id.date);

        }

        public void bind(RecyclerView.ViewHolder holder, final int listIndex){


            String imageUrl = mNewsList.get(listIndex).getUrlToImage();
            if (imageUrl != null) {
                Picasso.get().load(imageUrl).into(image);
            }

            title.setText(mNewsList.get(listIndex).getTitle());
            description.setText(mNewsList.get(listIndex).getDescription());
           // url.setText(mNewsList.get(listIndex).getUrl());
            date.setText(mNewsList.get(listIndex).getPublishedAt());
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
