package com.permutassep.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.lalongooo.permutassep.R;
import com.permutassep.BaseActivity;

public class ActivityMap extends BaseActivity
        implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private Button btnMapNormal;
    private Button btnMapHybrid;
    private Button btnMapSatellite;
    private Button btnMapTerrain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        btnMapNormal = (Button) findViewById(R.id.btnMapNormal);
        btnMapHybrid = (Button) findViewById(R.id.btnMapHybrid);
        btnMapSatellite = (Button) findViewById(R.id.btnMapSatellite);
        btnMapTerrain = (Button) findViewById(R.id.btnMapTerrain);

        btnMapNormal.setOnClickListener(this);
        btnMapHybrid.setOnClickListener(this);
        btnMapSatellite.setOnClickListener(this);
        btnMapTerrain.setOnClickListener(this);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.mMap = map;


        LatLng mid = midPoint(21.133055, -98.538055, 25.6863753, -100.3731685);
        LatLng origin = new LatLng(21.133055, -98.538055);
        LatLng target = new LatLng(25.6863753, -100.3731685);


        // Instantiates a new Polyline object and adds points to define a rectangle
        PolylineOptions rectOptions = new PolylineOptions().add(origin).add(target);
        map.addPolyline(rectOptions);

        map.addMarker(new MarkerOptions().position(origin).title("Xaltokan!"));
        map.addMarker(new MarkerOptions().position(target).title("Monterrey!"));
        map.addMarker(new MarkerOptions().position(mid).title("Middle!"));

        map.getUiSettings().setZoomControlsEnabled(true);


        //Calculate the markers to get their position
        LatLngBounds.Builder b = new LatLngBounds.Builder();
        b.include(origin);
        b.include(target);

        LatLngBounds bounds = b.build();
        //Change the padding as per needed

        int width = getResources().getDisplayMetrics().widthPixels;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200, 200, 5);
        map.animateCamera(cu);


    }











    private LatLng midPoint(double lat1, double long1, double lat2, double long2) {
        return new LatLng((lat1 + lat2) / 2, (long1 + long2) / 2);
    }

    private float angleBteweenCoordinate(double lat1, double long1, double lat2, double long2) {

        double dLon = (long2 - long1);

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;
        brng = 360 - brng;

        return (float) brng;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMapNormal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.btnMapHybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.btnMapSatellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.btnMapTerrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }

    }
}
