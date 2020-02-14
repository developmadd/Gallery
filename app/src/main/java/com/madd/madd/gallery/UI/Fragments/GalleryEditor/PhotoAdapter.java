package com.madd.madd.gallery.UI.Fragments.GalleryEditor;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.madd.madd.gallery.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoAdapter
        extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>
        implements PhotoItemTouchAdapter.ItemTouchHelperAdapter {

    private List<String> List;
    private AdapterEvents adapterEvents;

    private ItemTouchHelper itemTouchHelper;
    void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    PhotoAdapter(List<String> List,
                 AdapterEvents adapterEvents) {
        this.List = List;
        this.adapterEvents = adapterEvents;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_preview_picture, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(List.get(holder.getAdapterPosition()));

    }


    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        adapterEvents.onPictureDrag(fromPosition,toPosition);
    }

    @Override
    public int getItemCount() {
        return List.size();
    }





    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.IV_Property_Gallery_Picture)  ImageView imageView;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bind( String picturePath ){

            Glide.with(imageView).load(picturePath)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into( imageView );


            imageView.setOnClickListener(view -> {
                adapterEvents.onPictureClick(getAdapterPosition());
            });

            imageView.setOnLongClickListener(view -> {
                itemTouchHelper.startDrag(this);
                return false;
            });
        }



    }

    interface AdapterEvents {
        void onPictureDrag(int fromPosition, int toPosition);
        void onPictureClick(int position);
    }



}