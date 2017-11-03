package com.techgiants.admin.techgiantsadmin.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
    Subjects subjectsObj;
    String subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        setTitle("Subjects");
        recycler_subjects = (RecyclerView) findViewById(R.id.subjects_list_subject);
        recycler_subjects.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(SubjectsActivity.this);
        recycler_subjects.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();
        subjects = database.getReference("Subjects");
        loadSubjects();
    }
    private void loadSubjects() {

        adapter = new FirebaseRecyclerAdapter<Subjects, SubjectViewHolder>(
                Subjects.class,
                R.layout.layout_subjects,
                SubjectViewHolder.class,
                subjects
        ) {
            @Override
            protected void populateViewHolder(SubjectViewHolder viewHolder, final Subjects model, final int position) {
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

                viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subjectId= adapter.getRef(position).getKey();
                        subjectsObj = adapter.getItem(position);
                        showInputSubjectDialog("UPDATE");
                    }
                });

            }
        };

        adapter.notifyDataSetChanged();
        recycler_subjects.setAdapter(adapter);
    }

    private void showInputSubjectDialog(final String inputType) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(SubjectsActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View subj_input_layout = inflater.inflate(R.layout.layout_subject_add, null);
        txtSubjName = (MaterialEditText) subj_input_layout.findViewById(R.id.txtSubjName);
        txtSubjDesc = (MaterialEditText) subj_input_layout.findViewById(R.id.txtSubjDesc);

        btnAdd = (FButton) subj_input_layout.findViewById(R.id.btnAdd);
        btnAdd.setText((inputType.equals("ADD")?"ADD":"UPDATE"));

        btnCancle = (FButton) subj_input_layout.findViewById(R.id.btnCancle);

        if (inputType.equals("UPDATE"))
            fillUpdateCall();
        builder.setView(subj_input_layout);
        final AlertDialog alertDialog = builder.create();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = database.getReference("Subjects");
                String subjName = txtSubjName.getText().toString().trim();
                String subjDesc = txtSubjDesc.getText().toString().trim();
                if(!subjDesc.isEmpty() && !subjName.isEmpty()){
                    Subjects subjects = new Subjects(subjName, subjDesc);
                    if(inputType.equals("ADD")){
                        String id = databaseReference.push().getKey();
                        databaseReference.child(id).setValue(subjects);
                    }
                    else
                        databaseReference.child(subjectId).setValue(subjects);
                    alertDialog.dismiss();
                    Toast.makeText(SubjectsActivity.this, subjName+" "+inputType, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SubjectsActivity.this, "Please input valid values.", Toast.LENGTH_SHORT).show();
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

    private void fillUpdateCall() {
        txtSubjName.setText(subjectsObj.getSubjectName());
        txtSubjDesc.setText(subjectsObj.getDesc());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            showInputSubjectDialog("ADD");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
