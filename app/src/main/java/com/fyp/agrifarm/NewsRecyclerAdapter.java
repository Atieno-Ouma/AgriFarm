package com.fyp.agrifarm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fyp.agrifarm.News.NewsEntity;
import com.fyp.agrifarm.beans.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter {

    public static final String TAG = "Recycler" ;
    private Context context;
    private List<NewsEntity> newsList;
    private OnNewsClinkListener onNewsClinkListener;

    public NewsRecyclerAdapter(Context context,OnNewsClinkListener onNewsClinkListener){
        this.context = context;
        this.onNewsClinkListener=onNewsClinkListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.rv_item_news, viewGroup, false);

        return new VideoListViewHolder(view,onNewsClinkListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        NewsEntity record = newsList.get(i);

        VideoListViewHolder holder = (VideoListViewHolder) viewHolder;
        holder.tvNewsTitle.setText(record.getTitle());
        // Picasso will run the task Asynchronously and load into the targeted view upon download complete
        // You can resize thumbnails with resize method
        Picasso.get().load(record.getUrl()).into(holder.ivNewsThumb);
    }

    @Override
    public int getItemCount() {
        if(newsList == null)
            return 0;
        return newsList.size();
    }

    private class VideoListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNewsTitle;
        ImageView ivNewsThumb;
        OnNewsClinkListener onNewsClinkListener;

        public VideoListViewHolder(View view,OnNewsClinkListener onNewsClinkListener) {
            super(view);
            tvNewsTitle = view.findViewById(R.id.tvNewsTitle);
            ivNewsThumb = view.findViewById(R.id.ivNewsThumb);
            this.onNewsClinkListener=onNewsClinkListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNewsClinkListener.OnNewsClick(getAdapterPosition());
        }
    }

    public void changeDataSource(List<NewsEntity> list){
        newsList = list;
        notifyDataSetChanged();
    }

    public interface OnNewsClinkListener{
        void OnNewsClick(int position);
    }
}

