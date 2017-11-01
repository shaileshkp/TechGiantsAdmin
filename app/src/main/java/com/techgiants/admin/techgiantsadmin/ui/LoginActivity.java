package com.techgiants.admin.techgiantsadmin.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.techgiants.admin.techgiantsadmin.R;
import com.techgiants.admin.techgiantsadmin.model.Admin;

public class LoginActivity extends AppCompatActivity {
    MaterialEditText etLoginPass,etLoginPhno;
    Button signIn;

    FirebaseDatabase database;
    DatabaseReference admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database = FirebaseDatabase.getInstance();
        admin = database.getReference("Admin");
        etLoginPhno = (MaterialEditText) findViewById(R.id.edtLoginPhNo);
        etLoginPass = (MaterialEditText) findViewById(R.id.edtLoginPass);
        signIn = (Button) findViewById(R.id.btn_sign_in);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(etLoginPhno.getText().toString().trim(),
                        etLoginPass.getText().toString().trim());
            }
        });
    }
    private void signIn(final String phNo, final String pass) {
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Please wait..");
        dialog.show();
        admin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phNo).exists()) {
                    if (!phNo.isEmpty()) {
                        Admin login = dataSnapshot.child(phNo).getValue(Admin.class);
                        if (login.getPass().equals(pass)) {
                            com.techgiants.admin.techgiantsadmin.constants.User.userId = login.getPhNo();
                            com.techgiants.admin.techgiantsadmin.constants.User.userName = login.getName();
                            com.techgiants.admin.techgiantsadmin.constants.User.imageUrl = login.getImageUrl();
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Login success.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "User not exist.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
            }
        });
    }
}
