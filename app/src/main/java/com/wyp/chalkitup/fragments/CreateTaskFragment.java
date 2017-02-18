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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wyp.chalkitup.R;
import com.wyp.chalkitup.models.ProjectItem;
import com.wyp.chalkitup.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class CreateTaskFragment extends Fragment {


    private static final int MY_PERMISSIONS_USE_LOCATION = 1;
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
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                MY_PERMISSIONS_USE_LOCATION);
                    } else {
                        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        Criteria criteria = new Criteria();
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_USE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    Criteria criteria = new Criteria();
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    }
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

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    getActivity().finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
