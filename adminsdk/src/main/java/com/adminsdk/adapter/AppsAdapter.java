package com.adminsdk.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adminsdk.R;
import com.adminsdk.listeners.AppClickListener;
import com.adminsdk.model.AppsModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;


public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> {

    private List<AppsModel> appsModelList;
    private AppClickListener appClickListener;

    public AppsAdapter(List<AppsModel> appsModelList, AppClickListener appClickListener){
        this.appClickListener = appClickListener;
        this.appsModelList = appsModelList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = (ImageView)itemView.findViewById(R.id.ivIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appClickListener.onAppClick(appsModelList.get(getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public AppsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid_ad,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        AppsModel appsModel = appsModelList.get(i);
        loadImageIntoView(viewHolder.ivIcon,appsModel.getApp_icon());
    }

    private void loadImageIntoView(final ImageView imageView, String url){
        Glide.with(imageView.getContext())
                .load(url)
                .thumbnail(0.5f)
                .crossFade()
                .dontAnimate()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return appsModelList.size();
    }
}