package com.fyp.agrifarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.agrifarm.beans.UserIntrestData;

import java.util.ArrayList;

public class UserIntrestRecyclerViewAdapter extends RecyclerView.Adapter<UserIntrestRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<UserIntrestData> list;

    public UserIntrestRecyclerViewAdapter(Context context, ArrayList<UserIntrestData> list) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userintrest_rv_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserIntrestData data = list.get(position);
        ImageView icon = holder.icon;
        TextView icon_name = holder.icon_title;
        icon.setImageResource(data.getIcon());
        icon_name.setText(data.getIcon_title());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView icon;
        TextView icon_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.userintresticon);
            icon_title=itemView.findViewById(R.id.icontitle);

        }
    }
}
