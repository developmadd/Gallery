package com.madd.madd.gallery.UserGallery.Album;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.madd.madd.gallery.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder>{

    private List<String> albumPictureList;
    private List<String> selectedPictureList;
    private PictureEvents pictureEvents;

    PictureAdapter(List<String> pictureList, List<String> selectedPictureList, PictureEvents pictureEvents) {
        this.selectedPictureList = selectedPictureList;
        this.albumPictureList = pictureList;
        this.pictureEvents = pictureEvents;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.section_picture,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind( albumPictureList.get(position) , position);
    }

    @Override
    public int getItemCount() {
        return albumPictureList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.IV_Section_Picture) ImageView imageView;
        @BindView(R.id.IV_Section_Gallery_Picture_Icon) ImageView imageViewIcon;
        @BindView(R.id.IV_Section_Picture_Background) ImageView imageViewBackground;
        @BindView(R.id.CTNR_Section_Picture) ConstraintLayout relativeLayout;


        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }

        void bind(final String picture, int position){


            Glide.with(imageView).load(picture)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into( imageView );

            if ( selectedPictureList.contains(picture) ){
                imageViewIcon.setVisibility(View.VISIBLE);
                imageViewBackground.setVisibility(View.VISIBLE);
            } else {
                imageViewIcon.setVisibility( View.GONE );
                imageViewBackground.setVisibility(View.GONE);
            }

            relativeLayout.setOnClickListener(view -> {
                pictureEvents.onPictureClick(picture, position);
            });

        }


    }


    interface PictureEvents{
        void onPictureClick(String picture, int position);
    }



}




