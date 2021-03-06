package com.techgiants.admin.techgiantsadmin.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.techgiants.admin.techgiantsadmin.R;
import com.techgiants.admin.techgiantsadmin.interfaces.ItemClickListener;

/**
 * Created by Shailesh on 10/2/2017.
 */

public class LevelsViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtLevel, txtDuration, txtDesc;
    public ImageButton btnEdit, btnDelete;

    private ItemClickListener itemClickListener;

    public LevelsViewHolder(View itemView) {
        super(itemView);
        txtLevel = (TextView) itemView.findViewById(R.id.layout_level_name);
        txtDuration = (TextView) itemView.findViewById(R.id.layout_level_duration);
        txtDesc = (TextView) itemView.findViewById(R.id.layout_level_desc);

        btnEdit = (ImageButton) itemView.findViewById(R.id.editQuestion);
        btnDelete = (ImageButton) itemView.findViewById(R.id.deleteQuestion);

        itemView.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
