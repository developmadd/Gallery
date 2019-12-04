package com.madd.madd.gallery.UserGallery.Gallery;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.madd.madd.gallery.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{

    private List<Album> albumList;
    private AlbumEvents albumEvents;


    AlbumAdapter(  List<Album> albumList, AlbumEvents albumEvents ){
        this.albumList = albumList;
        this.albumEvents = albumEvents;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.section_album,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind( albumList.get(position) );
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.TV_Section_Album) TextView textView;
        @BindView(R.id.IV_Section_Album) ImageView imageView;
        @BindView(R.id.CTNR_Section_Album)  ConstraintLayout relativeLayout;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }

        void bind(final Album album){

            textView.setText( album.getName() );

            Glide.with(imageView).load(album.getFirstImagePath())
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into( imageView );

            relativeLayout.setOnClickListener(view -> {
                albumEvents.onAlbumClick(album.getName());
            });


        }
    }

    interface AlbumEvents{
        void onAlbumClick(String albumName);
    }

}




