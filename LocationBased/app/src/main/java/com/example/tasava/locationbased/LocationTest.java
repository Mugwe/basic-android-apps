package com.example.tasava.locationbased;

import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

public class LocationTest extends Activity implements LocationListener {

    private static final String[] A={"n/a","fine","coarse"};
    private static final String[] P={"n/a","low","medium","high"};
    private static final String[] S={"out of service","temporarily unavailable","available"};

    private LocationManager mgr;
    private TextView output;
    private String best;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_test);

        mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        output = (TextView) findViewById(R.id.output);

        log("Location providers:");
        dumpProviders();
        Criteria criteria = new Criteria();
        best = mgr.getBestProvider(criteria, true);
        log("\nBest provider is: " + best);

        log("\nLocations (starting with last known):");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = mgr.getLastKnownLocation(best);
        dumpLocation(location);
    }

    @Override
    protected void onResume() {
        super.onResume();
// Start updates (doc recommends delay >= 60000 ms)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mgr.requestLocationUpdates(best, 15000, 1, this);
    }
    @Override
    protected void onPause() {
        super.onPause();
// Stop updates to save power while app paused
        mgr.removeUpdates(this);
    }

    public void onLocationChanged(Location location) {
        dumpLocation(location);
    }
    public void onProviderDisabled(String provider) {
        log("\nProvider disabled: " + provider);
    }
    public void onProviderEnabled(String provider) {
        log("\nProvider enabled: " + provider);
    }
    public void onStatusChanged(String provider, int status, Bundle extras) {
        log("\nProvider status changed: " + provider + ", status=" + S[status] + ", extras=" + extras);
    }

    private void log(String string) {
        output.append(string + "\n");
    }

    private void dumpProviders() {
        List<String> providers = mgr.getAllProviders();
        for (String provider : providers) {
            dumpProvider(provider);
        }
    }

    private void dumpProvider(String provider) {
        LocationProvider info = mgr.getProvider(provider);
        StringBuilder builder = new StringBuilder();
        builder.append("LocationProvider[")
                .append("name=")
                .append(info.getName())
                .append(",enabled=")
                .append(mgr.isProviderEnabled(provider))
                .append(",getAccuracy=")
                .append(A[info.getAccuracy()])
                .append(",getPowerRequirement=")
                .append(P[info.getPowerRequirement()])
                .append(",hasMonetaryCost=")
                .append(info.hasMonetaryCost())
                .append(",requiresCell=")
                .append(info.requiresCell())
                .append(",requiresNetwork=")
                .append(info.requiresNetwork())
                .append(",requiresSatellite=")
                .append(info.requiresSatellite())
                .append(",supportsAltitude=")
                .append(info.supportsAltitude())
                .append(",supportsBearing=")
                .append(info.supportsBearing())
                .append(",supportsSpeed=")
                .append(info.supportsSpeed())
                .append("]");
        log(builder.toString());
    }

    private void dumpLocation(Location location) {
        if (location == null)
            log("\nLocation[unknown]");
        else
            log("\n" + location.toString());
    }



}

