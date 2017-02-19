package com.wyp.chalkitup.fragments;


import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wyp.chalkitup.R;
import com.wyp.chalkitup.models.ProjectItem;
import com.wyp.chalkitup.models.User;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private FirebaseDatabase database;
    User account;


    public ProfileFragment(User mUser) {
        // Required empty public constructor
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(Globals.PROJECTS);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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
        account = mUser;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        CircleImageView imageView = ((CircleImageView) view.findViewById(R.id.imageView));
        Glide
                .with(getActivity())
                .load(account.photoUrl)
                .into(imageView);
        TextView textView = (TextView) view.findViewById(R.id.name);
        textView.setText(account.name);
        TextView textView1 = (TextView) view.findViewById(R.id.email);
        textView1.setText(account.email);

        ImageView imageView1 = (ImageView) view.findViewById(R.id.ulogo);
        imageView1.setImageResource(R.drawable.ui);

        return view;
    }


}
