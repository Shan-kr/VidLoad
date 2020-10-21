package com.example.vidload;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.Inflater;

public  class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

LayoutInflater inflater;
List<Videos>videos;
private OnItemClickListener mlistener;

public interface OnItemClickListener{
     void OnItemClick(int position);
}

public void setOnItemClickListener(OnItemClickListener listener){
    mlistener=listener;
}


public  Adapter(Context ctc,List<Videos>videos){
    this.inflater=LayoutInflater.from(ctc);
    this.videos=videos;
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view= inflater.inflate(R.layout.listmenu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
          holder.title.setText(videos.get(position).getTitle());
          holder.description.setText(videos.get(position).getDescription());
       // holder.url.setText(videos.get(position).getVideoId());
        Picasso.get().load(videos.get(position).getThumbnail()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail;
        TextView title, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //url = itemView.findViewById(R.id.url);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            thumbnail = itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mlistener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mlistener.OnItemClick(position);
                        }
                    }
                }
            });

        }

    }
}