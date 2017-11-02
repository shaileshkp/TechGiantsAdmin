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
import com.rengwuxian.materialedittext.MaterialEditText;
import com.techgiants.admin.techgiantsadmin.R;
import com.techgiants.admin.techgiantsadmin.constants.Common;
import com.techgiants.admin.techgiantsadmin.interfaces.ItemClickListener;
import com.techgiants.admin.techgiantsadmin.model.Levels;
import com.techgiants.admin.techgiantsadmin.model.Subjects;
import com.techgiants.admin.techgiantsadmin.viewholder.SubjectViewHolder;

import info.hoang8f.widget.FButton;

public class SubjectsActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference subjects;

    MaterialEditText txtSubjName, txtSubjDesc;
    FButton btnAdd, btnCancle;

    FirebaseRecyclerAdapter<Subjects,SubjectViewHolder> adapter;

    RecyclerView recycler_subjects;
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton addSubject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        setTitle("Subjects");
        addSubject = (FloatingActionButton) findViewById(R.id.subjects_add_subject);
        recycler_subjects = (RecyclerView) findViewById(R.id.subjects_list_subject);
        recycler_subjects.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(SubjectsActivity.this);
        recycler_subjects.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();
        subjects = database.getReference("Subjects");
        loadSubjects();

        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputSubjectDialog();
            }
        });
    }
    private void loadSubjects() {

        adapter = new FirebaseRecyclerAdapter<Subjects, SubjectViewHolder>(
                Subjects.class,
                R.layout.layout_subjects,
                SubjectViewHolder.class,
                subjects
        ) {
            @Override
            protected void populateViewHolder(SubjectViewHolder viewHolder, final Subjects model, int position) {
                viewHolder.txtSubjectName.setText(model.getSubjectName());
                viewHolder.txtDesc.setText(model.getDesc());
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Common.subjId = adapter.getRef(position).getKey();
                        Common.subjName = model.getSubjectName();
                        Intent intent = new Intent(SubjectsActivity.this, LevelsActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        recycler_subjects.setAdapter(adapter);
    }
    private void showInputSubjectDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(SubjectsActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View subj_input_layout = inflater.inflate(R.layout.layout_subject_add, null);
        txtSubjName = (MaterialEditText) subj_input_layout.findViewById(R.id.txtSubjName);
        txtSubjDesc = (MaterialEditText) subj_input_layout.findViewById(R.id.txtSubjDesc);

        btnAdd = (FButton) subj_input_layout.findViewById(R.id.btnAdd);
        btnCancle = (FButton) subj_input_layout.findViewById(R.id.btnCancle);

        builder.setView(subj_input_layout);
        final AlertDialog alertDialog = builder.create();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = database.getReference("Subjects");
                String id = databaseReference.push().getKey();
                String subjName = txtSubjName.getText().toString().trim();
                String subjDesc = txtSubjDesc.getText().toString().trim();
                if(!subjDesc.isEmpty() && !subjName.isEmpty()){
                    Subjects subjects = new Subjects(subjName, subjDesc);
                    databaseReference.child(id).setValue(subjects);
                    alertDialog.dismiss();
                    Toast.makeText(SubjectsActivity.this, subjName+" Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SubjectsActivity.this, "Plese input valid values.", Toast.LENGTH_SHORT).show();
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
}
