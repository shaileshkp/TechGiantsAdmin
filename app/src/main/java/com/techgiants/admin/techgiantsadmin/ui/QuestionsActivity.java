package com.techgiants.admin.techgiantsadmin.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.techgiants.admin.techgiantsadmin.R;
import com.techgiants.admin.techgiantsadmin.constants.Common;
import com.techgiants.admin.techgiantsadmin.interfaces.ItemClickListener;
import com.techgiants.admin.techgiantsadmin.model.Questions;
import com.techgiants.admin.techgiantsadmin.model.Subjects;
import com.techgiants.admin.techgiantsadmin.viewholder.QuestionsViewHolder;
import com.techgiants.admin.techgiantsadmin.viewholder.SubjectViewHolder;

import info.hoang8f.widget.FButton;

public class QuestionsActivity extends AppCompatActivity {

    TextView txtToolbarSubject, txtToolbarLevel;

    FirebaseDatabase database;
    DatabaseReference questions;

    FirebaseRecyclerAdapter<Questions,QuestionsViewHolder> adapter;

    RecyclerView recycler_questions;
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton addQuestion;

    MaterialEditText txtQuestion, txtOpt1, txtOpt2, txtOpt3, txtOpt4;
    RadioButton opt1, opt2, opt3, opt4;
    RadioGroup radioGroup;
    FButton btnAdd, btnCancle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.question_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtToolbarSubject = (TextView) findViewById(R.id.toolbarSubject);
        txtToolbarLevel = (TextView) findViewById(R.id.toolbarLevel);

        txtToolbarLevel.setText(Common.levelName);
        txtToolbarSubject.setText(Common.subjName);

        addQuestion = (FloatingActionButton) findViewById(R.id.questions_add_questions);
        recycler_questions = (RecyclerView) findViewById(R.id.questions_list_questions);
        recycler_questions.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(QuestionsActivity.this);
        recycler_questions.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");
        loadQuestions();

        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputQuestionDialog();
            }
        });
    }
    private void loadQuestions() {

        adapter = new FirebaseRecyclerAdapter<Questions, QuestionsViewHolder>(
                Questions.class,
                R.layout.layout_question,
                QuestionsViewHolder.class,
                questions.orderByChild("levelId").equalTo(Common.levelId)
        ) {
            @Override
            protected void populateViewHolder(QuestionsViewHolder viewHolder, final Questions model, int position) {
                viewHolder.txtQuestion.setText(model.getQuestion());
                viewHolder.txtOpt1.setText(model.getAnswer1());
                viewHolder.txtOpt2.setText(model.getAnswer2());
                viewHolder.txtOpt3.setText(model.getAnswer3());
                viewHolder.txtOpt4.setText(model.getAnswer4());
                viewHolder.correct.setText(model.getCorrectAnswer());
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
//                        Common.subjId = adapter.getRef(position).getKey();
//                        Common.subjName = model.getSubjectName();
//                        Intent intent = new Intent(SubjectsActivity.this, LevelsActivity.class);
//                        startActivity(intent);
                        Toast.makeText(QuestionsActivity.this, "Click", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        recycler_questions.setAdapter(adapter);
    }

    private void showInputQuestionDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View ques_input_layout = inflater.inflate(R.layout.layout_add_question, null);
        txtQuestion = (MaterialEditText) ques_input_layout.findViewById(R.id.txtQues);
        txtOpt1 = (MaterialEditText) ques_input_layout.findViewById(R.id.addQues1);
        txtOpt2 = (MaterialEditText) ques_input_layout.findViewById(R.id.addQues2);
        txtOpt3 = (MaterialEditText) ques_input_layout.findViewById(R.id.addQues3);
        txtOpt4 = (MaterialEditText) ques_input_layout.findViewById(R.id.addQues4);
        opt1 = (RadioButton) ques_input_layout.findViewById(R.id.addQuesOpt1);
        opt2 = (RadioButton) ques_input_layout.findViewById(R.id.addQuesOpt2);
        opt3 = (RadioButton) ques_input_layout.findViewById(R.id.addQuesOpt3);
        opt4 = (RadioButton) ques_input_layout.findViewById(R.id.addQuesOpt4);

        btnAdd = (FButton) ques_input_layout.findViewById(R.id.btnAdd);
        btnCancle = (FButton) ques_input_layout.findViewById(R.id.btnCancle);

        builder.setView(ques_input_layout);
        final AlertDialog alertDialog = builder.create();

        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opt1.setChecked(true);
                opt2.setChecked(false);
                opt3.setChecked(false);
                opt4.setChecked(false);
            }
        });
        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opt1.setChecked(false);
                opt2.setChecked(true);
                opt3.setChecked(false);
                opt4.setChecked(false);
            }
        });
        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opt1.setChecked(false);
                opt2.setChecked(false);
                opt3.setChecked(true);
                opt4.setChecked(false);
            }
        });
        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opt1.setChecked(false);
                opt2.setChecked(false);
                opt3.setChecked(false);
                opt4.setChecked(true);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = database.getReference("Questions");
                String id = databaseReference.push().getKey();
                String question = txtQuestion.getText().toString().trim();
                String opt1 = txtOpt1.getText().toString().trim();
                String opt2 = txtOpt2.getText().toString().trim();
                String opt3 = txtOpt3.getText().toString().trim();
                String opt4 = txtOpt4.getText().toString().trim();
                String correctAns = getCorrectAnswer();
                if(!question.isEmpty() && !opt1.isEmpty() && !opt2.isEmpty()&& !opt3.isEmpty() && !opt4.isEmpty() && !correctAns.isEmpty() ){
                    Questions questions = new Questions(question, opt1, opt2, opt3, opt4, correctAns, Common.levelId);
                    databaseReference.child(id).setValue(questions);
                    alertDialog.dismiss();
                    Toast.makeText(QuestionsActivity.this, "Question Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(QuestionsActivity.this, "Question Not Added", Toast.LENGTH_SHORT).show();
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

    private String getCorrectAnswer() {
        if(opt1.isChecked()) {
            return  txtOpt1.getText().toString().trim();
        } else if(opt2.isChecked()) {
            return  txtOpt2.getText().toString().trim();
        } else if(opt3.isChecked()) {
            return  txtOpt3.getText().toString().trim();
        } else if(opt4.isChecked()) {
            return  txtOpt4.getText().toString().trim();
        } else {
            return "";
        }
    }
}
