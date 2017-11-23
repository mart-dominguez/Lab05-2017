package ar.edu.utn.frsf.isi.dam.lab05;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class JugadaFragment extends Fragment implements SensorEventListener {

    private TextView tvSacudidas;
    private TextView tvRandom;


    /*
	 * The gForce that is necessary to register as shake.
	 * Must be greater than 1G (one earth gravity unit).
	 * You can install "G-Force", by Blake La Pierre
	 * from the Google Play Store and run it to see how
	 *  many G's it takes to register a shake
	 */
    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
    private static final int SHAKE_SLOP_TIME_MS = 500;
    private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

    private long mShakeTimestamp;
    private int mShakeCount;

        private SensorManager sensorManager;
        private Sensor acelerometro;

    public JugadaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sensorManager = (SensorManager) this.getActivity().getSystemService(Context.SENSOR_SERVICE);
        acelerometro= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_jugada, container, false);
        tvSacudidas = (TextView) v.findViewById(R.id.sacudidas);
        tvRandom= (TextView) v.findViewById(R.id.valorRandom);
        return v;
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float gX = x / SensorManager.GRAVITY_EARTH;
        float gY = y / SensorManager.GRAVITY_EARTH;
        float gZ = z / SensorManager.GRAVITY_EARTH;

        // gForce will be close to 1 when there is no movement.
      //  float gForce = FloatMath.sqrt(gX * gX + gY * gY + gZ * gZ);
        float gForce = (float) Math.sqrt((double) (gX * gX + gY * gY + gZ * gZ));


        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            final long now = System.currentTimeMillis();
            // ignore shake events too close to each other (500ms)
            if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                return;
            }

            // reset the shake count after 3 seconds of no shakes
            if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                mShakeCount = 0;
            }

            mShakeTimestamp = now;
            mShakeCount++;
            tvRandom.setText("Random: "+Math.random());
            tvSacudidas.setText("Sacudidas:  "+mShakeCount);


          //  mListener.onShake(mShakeCount);
        }else{
            tvSacudidas.setText("termino de sacudir:");

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}
