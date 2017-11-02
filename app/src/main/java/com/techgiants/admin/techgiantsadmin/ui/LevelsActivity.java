package com.techgiants.admin.techgiantsadmin.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.techgiants.admin.techgiantsadmin.R;
import com.techgiants.admin.techgiantsadmin.constants.Common;
import com.techgiants.admin.techgiantsadmin.interfaces.ItemClickListener;
import com.techgiants.admin.techgiantsadmin.model.Levels;
import com.techgiants.admin.techgiantsadmin.viewholder.LevelsViewHolder;

import info.hoang8f.widget.FButton;

public class LevelsActivity extends AppCompatActivity {
    FirebaseDatabase database;
    Query levels;
    MaterialEditText txtLevName, txtLevDur, txtLevDesc;
    FButton btnAdd, btnCancle;

    FirebaseRecyclerAdapter<Levels,LevelsViewHolder> adapter;

    RecyclerView recycler_levels;
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton addLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        setTitle(Common.subjName);

        addLevel = (FloatingActionButton) findViewById(R.id.levels_add_level);
        recycler_levels = (RecyclerView) findViewById(R.id.list_levels);
        recycler_levels.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(LevelsActivity.this);
        recycler_levels.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();
        levels = database.getReference("Levels").orderByChild("subjId").equalTo(Common.subjId);

        loadLevels();

        addLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputLevelDialog();
            }
        });
    }

    private void showInputLevelDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LevelsActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View level_input_layout = inflater.inflate(R.layout.layout_add_level, null);
        txtLevName = (MaterialEditText) level_input_layout.findViewById(R.id.txtLevName);
        txtLevDur = (MaterialEditText) level_input_layout.findViewById(R.id.txtLevDur);
        txtLevDesc = (MaterialEditText) level_input_layout.findViewById(R.id.txtLevDesc);

        btnAdd = (FButton) level_input_layout.findViewById(R.id.btnAdd);
        btnCancle = (FButton) level_input_layout.findViewById(R.id.btnCancle);

        builder.setView(level_input_layout);
        final AlertDialog alertDialog = builder.create();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = database.getReference("Levels");
                String id = databaseReference.push().getKey();
                String levName = txtLevName.getText().toString().trim();
                String levDur = txtLevDur.getText().toString().trim();
                String levDesc = txtLevDesc.getText().toString().trim();
                if(!levDesc.isEmpty() && !levDur.isEmpty() && !levName.isEmpty()){
                    Levels levels = new Levels(levName,levDur,levDesc, Common.subjId);
                    databaseReference.child(id).setValue(levels);
                    alertDialog.dismiss();
                    Toast.makeText(LevelsActivity.this, levName+" Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LevelsActivity.this, "Plese input valid values.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void loadLevels() {
        adapter = new FirebaseRecyclerAdapter<Levels, LevelsViewHolder>(
                Levels.class,
                R.layout.layout_levels,
                LevelsViewHolder.class,
                levels
        ) {
            @Override
            protected void populateViewHolder(final LevelsViewHolder viewHolder, final Levels model, int position) {
                viewHolder.txtLevel.setText(model.getLevel());
                viewHolder.txtDesc.setText(model.getDescription());
                viewHolder.txtDuration.setText(model.getDuration()+" min");

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(LevelsActivity.this, QuestionsActivity.class);
                        Common.levelName = adapter.getItem(position).getLevel();
                        Common.levelId = adapter.getRef(position).getKey();
                        startActivity(intent);
                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        recycler_levels.setAdapter(adapter);
    }
}
