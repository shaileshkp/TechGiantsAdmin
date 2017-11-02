package com.techgiants.admin.techgiantsadmin.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.techgiants.admin.techgiantsadmin.R;
import com.techgiants.admin.techgiantsadmin.interfaces.ItemClickListener;

/**
 * Created by Shailesh on 9/30/2017.
 */

public class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtSubjectName, txtDesc;
    private ItemClickListener itemClickListener;

    public SubjectViewHolder(View itemView) {
        super(itemView);
        txtSubjectName = (TextView) itemView.findViewById(R.id.layout_subject_subj_name);
        txtDesc = (TextView) itemView.findViewById(R.id.layout_subject_subj_desc);
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
