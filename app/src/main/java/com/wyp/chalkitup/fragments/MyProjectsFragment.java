package com.wyp.chalkitup.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wyp.chalkitup.R;
import com.wyp.chalkitup.adapters.ProjectAdapter;
import com.wyp.chalkitup.models.ProjectItem;
import com.wyp.chalkitup.models.User;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import com.wyp.chalkitup.Globals;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MyProjectsFragment extends Fragment {

    private User mUser;
    private FirebaseDatabase database;
    private List<ProjectItem> projects = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProjectAdapter adapter;

    public MyProjectsFragment(){}

    public MyProjectsFragment(Context context, User mUser) {
        // Required empty public constructor
        this.mUser = mUser;
        adapter = new ProjectAdapter(context, projects, mUser);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_projects, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        database = FirebaseDatabase.getInstance();
        loadData();
        return view;
    }

    private void loadData() {
        DatabaseReference reference = database.getReference(Globals.PROJECTS);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ProjectItem projectItem = dataSnapshot.getValue(ProjectItem.class);
                projects.add(0, projectItem);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
