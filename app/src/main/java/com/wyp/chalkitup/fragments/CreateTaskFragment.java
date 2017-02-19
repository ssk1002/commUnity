package com.wyp.chalkitup.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wyp.chalkitup.Globals;
import com.wyp.chalkitup.MainActivity;
import com.wyp.chalkitup.R;
import com.wyp.chalkitup.models.ProjectItem;
import com.wyp.chalkitup.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class CreateTaskFragment extends Fragment {

    private User mUser;
    private FirebaseDatabase database;
    ProjectItem projectItem;
    EditText nameTxt;
    EditText descriptionTxt;
    EditText costTxt;

    public CreateTaskFragment(User user) {
        // Required empty public constructor
        this.mUser = user;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_task, container, false);
        database = FirebaseDatabase.getInstance();
        nameTxt = (EditText) view.findViewById(R.id.editText);
        descriptionTxt = (EditText) view.findViewById(R.id.editText3);
        costTxt = (EditText) view.findViewById(R.id.editText4);
        Button createBtn = (Button) view.findViewById(R.id.button2);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameTxt.getText().toString().trim().length() != 0 && descriptionTxt.getText().toString().trim().length() != 0 && costTxt.getText().toString().trim().length() != 0) {
                    projectItem = new ProjectItem(nameTxt.getText().toString(), mUser.id, mUser.name, mUser.photoUrl, descriptionTxt.getText().toString(), costTxt.getText().toString());
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        Criteria criteria = new Criteria();
                        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                        projectItem.latitude = location.getLatitude();
                        projectItem.longitude = location.getLongitude();
                        DatabaseReference reference = database.getReference();
                        DatabaseReference reference1 = reference.child(Globals.PROJECTS).push();
                        projectItem.id = reference1.getKey();
                        reference1.setValue(projectItem);
                        nameTxt.setText("");
                        descriptionTxt.setText("");
                        costTxt.setText("");
                        Toast.makeText(getContext(), "Successfully created project!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Cannot leave fields empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


}
