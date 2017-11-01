package com.techgiants.admin.techgiantsadmin.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.techgiants.admin.techgiantsadmin.R;
import com.techgiants.admin.techgiantsadmin.interfaces.ItemClickListener;

/**
 * Created by Shailesh on 10/1/2017.
 */

public class PostsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imageView;
    public TextView textView;

    private ItemClickListener itemClickListener;

    public PostsViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.layout_posts_image);
        textView = (TextView) itemView.findViewById(R.id.layout_posts_desc);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
