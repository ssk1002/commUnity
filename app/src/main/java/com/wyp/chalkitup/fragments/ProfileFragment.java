package com.wyp.chalkitup.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyp.chalkitup.R;
import com.wyp.chalkitup.models.User;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    User account;


    public ProfileFragment(User mUser) {
        // Required empty public constructor
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
        return view;
    }


}
