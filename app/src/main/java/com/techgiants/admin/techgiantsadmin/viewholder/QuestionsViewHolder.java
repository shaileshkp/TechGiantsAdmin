package com.techgiants.admin.techgiantsadmin.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.techgiants.admin.techgiantsadmin.R;
import com.techgiants.admin.techgiantsadmin.interfaces.ItemClickListener;

/**
 * Created by Shailesh on 10/2/2017.
 */

public class QuestionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtQuestion, txtOpt1,txtOpt2,txtOpt3, txtOpt4, correct;

    private ItemClickListener itemClickListener;

    public QuestionsViewHolder(View itemView) {
        super(itemView);
        txtQuestion = (TextView) itemView.findViewById(R.id.layout_ques_ques);
        txtOpt1 = (TextView) itemView.findViewById(R.id.layout_ques_opt1);
        txtOpt2 = (TextView) itemView.findViewById(R.id.layout_ques_opt2);
        txtOpt3 = (TextView) itemView.findViewById(R.id.layout_ques_opt3);
        txtOpt4 = (TextView) itemView.findViewById(R.id.layout_ques_opt4);
        correct = (TextView) itemView.findViewById(R.id.layout_ques_cans);

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
