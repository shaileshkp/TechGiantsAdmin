package com.techgiants.admin.techgiantsadmin.ui;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.techgiants.admin.techgiantsadmin.R;
import com.techgiants.admin.techgiantsadmin.interfaces.ItemClickListener;
import com.techgiants.admin.techgiantsadmin.model.Posts;
import com.techgiants.admin.techgiantsadmin.viewholder.PostsViewHolder;

import java.util.UUID;

import info.hoang8f.widget.FButton;

public class PostsFragment extends Fragment {
    FloatingActionButton floating_button;
    FirebaseDatabase database;
    DatabaseReference nPost;
    Query posts;
    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseRecyclerAdapter<Posts,PostsViewHolder> adapter;

    RecyclerView recycler_posts;
    RecyclerView.LayoutManager layoutManager;

    EditText post_desc;
    FButton btn_post_select_image, btn_post_upload;

    Posts newPost;

    Uri saveUri;
    private final int PICK_IMAGE_REQUEST = 71;

    DrawerLayout drawer;

    public PostsFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_posts, container, false);
        floating_button = (FloatingActionButton) view.findViewById(R.id.posts_add_post);
        recycler_posts = (RecyclerView) view.findViewById(R.id.posts_list);
        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        recycler_posts.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recycler_posts.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();
        nPost = database.getReference("Posts");
        posts = database.getReference("Posts");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        loadPosts();

        floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return  view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null)
        {
            saveUri = data.getData();
            btn_post_select_image.setText("Image selected");

        }
    }

    private void showDialog() {
        AlertDialog.Builder alertDialgo = new AlertDialog.Builder(getContext());
        alertDialgo.setTitle("What's in your mind?");
        alertDialgo.setMessage("Please enter information");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View add_post_layout = inflater.inflate(R.layout.layout_add_post,null);
        post_desc = (EditText) add_post_layout.findViewById(R.id.post_text);
        btn_post_select_image = (FButton) add_post_layout.findViewById(R.id.post_select);
        btn_post_upload = (FButton) add_post_layout.findViewById(R.id.post_post);

        btn_post_select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btn_post_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        alertDialgo.setView(add_post_layout);
        alertDialgo.setIcon(R.drawable.ic_local_post_office_black_24dp);

        alertDialgo.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(newPost != null) {
                    nPost.push().setValue(newPost);
                    Toast.makeText(getActivity(),"New post added",Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialgo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialgo.show();
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading..");
        progressDialog.show();

        String imageName = UUID.randomUUID().toString();
        final StorageReference imageFolder = storageReference.child("images/"+imageName);
        imageFolder.putFile(saveUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Uploaded!!", Toast.LENGTH_SHORT).show();
                        imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                newPost = new Posts(post_desc.getText().toString().trim(),uri.toString());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded "+progress+"%");
                    }
                });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"),PICK_IMAGE_REQUEST);
    }

    private void loadPosts() {

        adapter = new FirebaseRecyclerAdapter<Posts, PostsViewHolder>(
                Posts.class,
                R.layout.layout_posts,
                PostsViewHolder.class,
                posts
        ) {
            @Override
            protected void populateViewHolder(PostsViewHolder viewHolder, Posts model, int position) {
                if(model.getDesc() != null && !model.getDesc().isEmpty())
                    viewHolder.textView.setText(model.getDesc());

                if(model.getImgUrl() != null && !model.getImgUrl().isEmpty())
                    Picasso.with(getContext()).load(model.getImgUrl()).into(viewHolder.imageView);
                else
                    viewHolder.imageView.getLayoutParams().height=0;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        recycler_posts.setAdapter(adapter);
    }
}
