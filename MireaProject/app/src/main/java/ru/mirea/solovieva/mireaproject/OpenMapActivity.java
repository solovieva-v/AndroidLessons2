package ru.mirea.solovieva.mireaproject;

import android.os.Bundle;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.solovieva.mireaproject.databinding.ActivityOpenMap2Binding;

public class OpenMapActivity extends AppCompatActivity {

    private ActivityOpenMap2Binding binding;
    private final String MAPKIT_API_KEY = "bf69f1aa-c134-4d5d-bc3e-2a450ef4ef02";
    private final Point ROUTE_START_LOCATION = new Point(55.584777, 37.903695);
    private final Point ROUTE_END_LOCATION = new Point(55.769008, 37.644612);
    private final Point SCREEN_CENTER = new Point(
            (ROUTE_START_LOCATION.getLatitude() + ROUTE_END_LOCATION.getLatitude()) / 2,
            (ROUTE_START_LOCATION.getLongitude() + ROUTE_END_LOCATION.getLongitude()) /
                    2);
    private MapView mapView;
    private int[] colors = {0xFFFF0000, 0xFF00FF00, 0x00FFBBBB, 0xFF0000FF};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_map2);
        mapView = findViewById(R.id.mapView);
        Map map = mapView.getMap();


        PlacemarkMapObject vhMarker = map.getMapObjects().addPlacemark(new Point(55.759714, 37.647181));
        vhMarker.setIcon(ImageProvider.fromResource(this,  org.osmdroid.library.R.drawable.osm_ic_follow_me_on));
        vhMarker.setText("Хачапури и вино");


        PlacemarkMapObject redSquareMarker = map.getMapObjects().addPlacemark(new Point(55.754340, 37.621187));
        redSquareMarker.setIcon(ImageProvider.fromResource(this,  org.osmdroid.library.R.drawable.osm_ic_follow_me_on));
        redSquareMarker.setText("RED SQUARE - ГУМ");

        PlacemarkMapObject climbMarker = map.getMapObjects().addPlacemark(new Point(55.814431, 37.604726));
        climbMarker.setIcon(ImageProvider.fromResource(this,  org.osmdroid.library.R.drawable.osm_ic_follow_me_on));
        climbMarker.setText("Скалодром");


    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }
}