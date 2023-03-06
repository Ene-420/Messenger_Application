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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment{


    private GoogleMap map;
    private LocationManager locManager;
    private LocationListener locListener;
    private FusedLocationProviderClient client;



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
            };
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) ==PackageManager.PERMISSION_GRANTED){
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locListener);
                Location lastKnowLocation = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centerMapOnLocation(lastKnowLocation, "Your Location");
            }
            else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }


        }

    };



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        client = LocationServices.getFusedLocationProviderClient(getActivity());
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
}