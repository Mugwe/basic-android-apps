package com.example.mugwe.hellomap;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.os.Bundle;

public class MapPane extends Activity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng nairobi = new LatLng(-1.28333, 36.81667);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(nairobi, 13));

        map.addMarker(new MarkerOptions()
                .title("Nairobi")
                .snippet("The most beautiful city in Africa.")
                .position(nairobi));
    }
}

