package com.example.myapplication.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.model.LocationModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsFragment extends Fragment{


    private GoogleMap map;
    private LocationManager locManager;
    private LocationListener locListener;
    private FusedLocationProviderClient client;
    private FirebaseDatabase database;
    private String userName = "";
    Location lastKnowLocation;




    public void centerMapOnLocation(Location location, String title){
        if(location!= null) {
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            map.clear();
            map.addMarker(new MarkerOptions().position(userLocation).title(title));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 16));

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==PackageManager.PERMISSION_GRANTED){
                    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locListener);
                    Location lastKnowLocation = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    centerMapOnLocation(lastKnowLocation, "Your Location");
                }

            }
        }
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback(){



        @Override
        public void onMapReady(GoogleMap googleMap) {

            map  = googleMap;

            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    //map.clear();
                    //centerMapOnLocation(location, "My Location");

                }
                @Override
                public void onProviderEnabled(@NonNull String provider) {

                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }
            };
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) ==PackageManager.PERMISSION_GRANTED){
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locListener);
                lastKnowLocation = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                centerMapOnLocation(lastKnowLocation, "Your Location");
                addOtherUsers();

            }
            else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }

            LocationModel model = new LocationModel(userName, lastKnowLocation.getLongitude(), lastKnowLocation.getLatitude());
            database.getReference().child("Location").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(model);
        }
        //LocationModel

    };



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        database = FirebaseDatabase.getInstance();
        //userName = database.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userName").;

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName = snapshot.child("userName").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return inflater.inflate(R.layout.fragment_maps, container, false);

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    public void addOtherUsers(){
        database.getReference().child("Contacts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    String contactId = data.getKey();
                    database.getReference().child("Location").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                if(dataSnapshot.getKey().equals(contactId)){
                                    LocationModel model = dataSnapshot.getValue(LocationModel.class);
                                    if(model!= null) {
                                        LatLng userLocation = new LatLng(model.getuLat(), model.getuLong());
                                        //map.clear();
                                        map.addMarker(new MarkerOptions().position(userLocation).title(model.getUsername()));
                                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 16));
                                    }

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}