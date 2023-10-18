package ru.mirea.solovieva.mireaproject.ui.compass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mirea.solovieva.mireaproject.R;

public class CompassFragment extends Fragment implements SensorEventListener {

    @Deprecated
    public static final int TYPE_ORIENTATION = 3;

    private ImageView headerImage;
    private float rotateDegree = 0f;
    private SensorManager mSensorManager;
    private TextView compOrient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compass, container, false);
        headerImage = view.findViewById(R.id.compassview);
        compOrient = view.findViewById(R.id.compassorientation);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float degree = Math.round(event.values[0]);
        compOrient.post(new Runnable() {
            @Override
            public void run() {
                compOrient.setText("Отклонение от севера: " + Float.toString(degree) + " градусов");
            }
        });
        compOrient.setText("Отклонение от севера:" + Float.toString(degree) + " градусов");
        RotateAnimation rotateAnimation = new RotateAnimation(
                rotateDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);

        headerImage.startAnimation(rotateAnimation);
        rotateDegree = -degree;
        compOrient.postInvalidate();

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}