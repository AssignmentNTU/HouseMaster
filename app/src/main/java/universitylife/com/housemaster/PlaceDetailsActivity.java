package universitylife.com.housemaster;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import bolts.Continuation;
import bolts.Task;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;

public class PlaceDetailsActivity extends AppCompatActivity {
    public static PlaceReview placeReview = null;

    private MapView mapView;
    private TextView phoneText;
    private TextView priceText;
    private TextView addressText;
    private TextView descriptionText;
    private TextView amenitiesText;
    private ImageView photoView;
    private ProgressBar progressBar;
    private LinearLayout fullscreenLayer;
    private LinearLayout mapSmall;
    private Button resizeMapBtn;
    private RelativeLayout mapWithFullscreen;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Iconify.with(new MaterialModule());
        setContentView(R.layout.activity_place_details);

        mapView = (MapView) findViewById(R.id.placeMap);
        phoneText = (TextView) findViewById(R.id.phone);
        priceText = (TextView) findViewById(R.id.price);
        addressText = (TextView) findViewById(R.id.address);
        descriptionText = (TextView) findViewById(R.id.description);
        amenitiesText = (TextView) findViewById(R.id.amenities);
        fullscreenLayer = (LinearLayout) findViewById(R.id.fullscreenLayer);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mapSmall = (LinearLayout) findViewById(R.id.mapSmall);
        photoView = (ImageView) findViewById(R.id.photo);
        mapWithFullscreen = (RelativeLayout) findViewById(R.id.mapWithFullscreen);
        resizeMapBtn = (Button) findViewById(R.id.resizeMapBtn);

        mapView.onCreate(savedInstanceState);
        fullscreenLayer.setVisibility(View.VISIBLE);

        getSupportActionBar().setTitle(placeReview.getHdbName());
        phoneText.setText(placeReview.getPhoneNumber());
        priceText.setText(placeReview.getPrice());
        descriptionText.setText(placeReview.getHdbDescription());
        addressText.setText(placeReview.getLocation());

        final StringBuilder amenities = new StringBuilder();
        for (String amenity : placeReview.getListAmenities()) {
            amenities.append(amenity).append("\n");
        }
        amenitiesText.setText(amenities);


        // add marker on map
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                final LatLng position = placeReview.getPosition();
                map.addMarker(new MarkerOptions().position(position).title(placeReview.getLocation()));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
                ++progress;
                PlaceDetailsActivity.this.hideLoadingIfDone();
                mapView.onResume();
            }
        });

        // fetch photo
        placeReview.getParseFile().getDataInBackground().onSuccess(new Continuation<byte[], Void>() {
            @Override
            public Void then(Task<byte[]> task) throws Exception {
                ++progress;
                byte[] photoByte = task.getResult();
                PlaceDetailsActivity.this.hideLoadingIfDone();
                photoView.setImageBitmap(BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length));
                return null;
            }
        });
    }

    private void hideLoadingIfDone() {
        if (progress < 2) {
            // not done
            return;
        }

        progressBar.setVisibility(View.GONE);
        fullscreenLayer.setAlpha(0);
        fullscreenLayer.setVisibility(View.GONE);
    }

    public boolean isMapFullscreen() {
        return mapSmall.getVisibility() != View.VISIBLE;
    }

    public void resizeMap(View view) {
        if (isMapFullscreen()) {
            fullscreenLayer.animate().setDuration(getResources().getInteger(R.integer.shortAnim)).alpha(0.f).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animator) {
                    resizeMapBtn.setText("{md-fullscreen}");
                    fullscreenLayer.removeView(mapWithFullscreen);
                    fullscreenLayer.setVisibility(View.GONE);
                    mapSmall.addView(mapWithFullscreen);
                    mapSmall.setVisibility(View.VISIBLE);
                }
            });
        } else {
            resizeMapBtn.setText("{md-fullscreen-exit}");
            mapSmall.setVisibility(View.INVISIBLE);
            mapSmall.removeView(mapWithFullscreen);
            fullscreenLayer.addView(mapWithFullscreen);
            fullscreenLayer.setVisibility(View.VISIBLE);
            fullscreenLayer.animate().setListener(null).setDuration(getResources().getInteger(R.integer.shortAnim)).alpha(1.f);
        }
    }

    @Override
    public void onBackPressed() {
        if (isMapFullscreen()) {
            resizeMap(null);
        } else {
            placeReview = null;
            super.onBackPressed();
        }
    }

    public void call(View view) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
            return;
        }

        String number = ((TextView) view).getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));
        startActivity(callIntent);
    }
}
