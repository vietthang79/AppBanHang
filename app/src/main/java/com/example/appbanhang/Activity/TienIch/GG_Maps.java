package com.example.appbanhang.Activity.TienIch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.appbanhang.R;

public class GG_Maps extends AppCompatActivity implements LocationListener {
    private static final int PERMISSION_CHECK_ACCESS_FINE_LOCATION = 1;
    private LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gg_maps);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);//EP KIEU
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GG_Maps.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CHECK_ACCESS_FINE_LOCATION
            );

            return;
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CHECK_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "Chua cap quyen", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        
    }
}