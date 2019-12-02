package com.fyp.agrifarm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.fyp.agrifarm.beans.DummyUser;
import com.fyp.agrifarm.utils.PicassoUtils;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class FirestoreUserRecyclerAdapter extends FirestoreRecyclerAdapter<DummyUser, FirestoreUserRecyclerAdapter.FirestoreUserRecyclerHolder> {
    private OnItemClickListener listener;
    private Context context;
    public FirestoreUserRecyclerAdapter(@NonNull FirestoreRecyclerOptions<DummyUser> options , Context context ) {
        super(options);
        this.context = context;


    }

    @Override
    protected void onBindViewHolder(@NonNull FirestoreUserRecyclerHolder holder, int position, @NonNull DummyUser model) {
        String image = model.getPhotoUri();

        holder.username.setText(model.getFullname());
        Picasso.get().load(image).into(holder.userimage, new Callback() {
            @Override
            public void onSuccess() {
                PicassoUtils.changeToCircularImage(holder.userimage,context.getResources());
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }


    @NonNull
    @Override
    public FirestoreUserRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_user,parent,false);
        return new FirestoreUserRecyclerHolder(v);

    }

    class FirestoreUserRecyclerHolder extends RecyclerView.ViewHolder {
        TextView username;
        ImageView userimage;

        public FirestoreUserRecyclerHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.tvUserName);
            userimage = itemView.findViewById(R.id.ivUserPhoto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null)
                    {
                        listener.OnItemClick(getSnapshots().getSnapshot(position),position);
                    }
//                    Intent intent = new Intent(context, UserInformationActivity.class);
//                    context.startActivity(intent);


                }
            });
        }


    }

    public interface OnItemClickListener {

        void OnItemClick(DocumentSnapshot documentSnapshot , int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }
}