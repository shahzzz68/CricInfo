package com.example.shahz.cricinfo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shahz.cricinfo.Dialogs.Video_Playing;
import com.example.shahz.cricinfo.R;
import com.example.shahz.cricinfo.Video_Playing_Activity;
import com.example.shahz.cricinfo.fragments.Video_Gallery;
import com.example.shahz.cricinfo.model_class.Video_MetaData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Video_Gallery_adapter extends RecyclerView.Adapter<Video_Gallery_adapter.ViewHolder> {

    public static final String VIDEO_URI="videouri";

    List<Video_MetaData> list;
    Context context;
    ProgressBar progressBar;

    public Video_Gallery_adapter(List<Video_MetaData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Video_MetaData metaData=list.get(position);

        if (!list.equals(null))
        {
            holder.textView.setText(metaData.getVideoName());

            Picasso.get()
                    .load(metaData.getThumbnail())
                    .centerCrop()
                    .fit()
                    .centerCrop()
                    .into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {

                holder.progressBar.setVisibility(View.GONE);
            }

                        @Override
                        public void onError(Exception e) {

                            holder.progressBar.setVisibility(View.GONE);
                        }
                    });
        }
        else
        {
            list.clear();
            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
        }



        holder.videoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri= list.get(position).getVideoUrl();

                if (!uri.equals(null))
                {
                    Intent intent =new Intent(context, Video_Playing_Activity.class);
                    intent.putExtra(VIDEO_URI,uri);
                    context.startActivity(intent);
                }
                else
                {
                    Toast.makeText(context, "No Video Path", Toast.LENGTH_SHORT).show();
                }



//                Bundle bundle= new Bundle();
//                bundle.putString(VIDEO_URI,uri);
//
//                Video_Playing videoPlaying = new Video_Playing();
//                videoPlaying.setArguments(bundle);
//                videoPlaying.show(((FragmentActivity)context).getSupportFragmentManager(),"play");

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        CardView videoCardView;
        ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);

             imageView=itemView.findViewById(R.id.videoImage);
             textView=itemView.findViewById(R.id.videoName);
             videoCardView=itemView.findViewById(R.id.videoItem);
             progressBar=itemView.findViewById(R.id.loadImgProgress);
        }
    }


}
