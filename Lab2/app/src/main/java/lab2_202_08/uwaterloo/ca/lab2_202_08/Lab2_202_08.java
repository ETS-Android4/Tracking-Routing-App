package lab2_202_08.uwaterloo.ca.lab2_202_08;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

import ca.uwaterloo.sensortoy.LineGraphView;


public class Lab2_202_08 extends AppCompatActivity {

    TextView lightSensor;             //textView will basically create a text box to put it on the interface
    TextView lightSensorValue;
    TextView accelerometerSensor;
    TextView accelerometerSensorValue;
    TextView graph;
    TextView magneticSensor;
    TextView magneticSensorValue;
    TextView rotationSensor;
    TextView rotationSensorValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2_202_08);

        LinearLayout l = (LinearLayout) findViewById(R.id.layout1);   //linear displayed one by one where relative displayed overlap
        l.setOrientation(LinearLayout.VERTICAL);


        // Graph
        LineGraphView lineGraphView = new LineGraphView(getApplicationContext(), 100, Arrays.asList("x", "y", "z"));
        lineGraphView.setVisibility(View.VISIBLE);
        l.addView(lineGraphView);

        //light Title
        graph = (TextView) findViewById(R.id.label1);
        //lightSensor = new TextView(getApplicationContext());
        graph.setText("Graph:");  // the new text added is in white as in default
        graph.setTextColor(Color.BLACK);
        //l.addView(lightSensor);

        lightSensor = new TextView(getApplicationContext());
        lightSensor.setText("The Light Sensor Value:");
        lightSensor.setTextColor(Color.BLACK);
        l.addView(lightSensor);

        //light value
        lightSensorValue = new TextView(getApplicationContext());
        lightSensorValue.setTextColor(Color.BLACK);
        l.addView(lightSensorValue); // output the tv1

        //Accelerometer title
        accelerometerSensor = new TextView(getApplicationContext());
        accelerometerSensor.setText("The Accelerometer Sensor Value");
        accelerometerSensor.setTextColor(Color.BLACK);
        l.addView(accelerometerSensor);

        //Accelerometer value
        accelerometerSensorValue = new TextView(getApplicationContext());
        accelerometerSensorValue.setTextColor(Color.BLACK);
        l.addView(accelerometerSensorValue);

        //Magnetic Title
        magneticSensor = new TextView(getApplicationContext());
        magneticSensor.setText("The Magnetic Sensor Value:");  // the new text added is in white as in default
        magneticSensor.setTextColor(Color.BLACK);
        l.addView(magneticSensor);

        //Magnetic value
        magneticSensorValue = new TextView(getApplicationContext());
        magneticSensorValue.setTextColor(Color.BLACK);
        l.addView(magneticSensorValue);

        //Rotation Title
        rotationSensor = new TextView(getApplicationContext());
        rotationSensor.setText("The Rotational Vector Sensor Value:");  // the new text added is in white as in default
        rotationSensor.setTextColor(Color.BLACK);
        l.addView(rotationSensor);

        //Rotation value
        rotationSensorValue = new TextView(getApplicationContext());
        rotationSensorValue.setTextColor(Color.BLACK);
        l.addView(rotationSensorValue);

        //The light sensor
        SensorManager lightSensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor LightSensorEventListener =
                lightSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        SensorEventListener lightListener = new LightSensorEventListener(lightSensorValue);       //the selected LightSensorEventListener(XXX) means the text you want to output
        //the output for light is on tv1
        lightSensorManager.registerListener(lightListener, LightSensorEventListener, SensorManager.SENSOR_DELAY_NORMAL);

        // The accelerometer sensor
        SensorManager accelerometerSensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor AccelerometerSensorEventListener =
                accelerometerSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener accelerometerListener = new AccelerometerSensorEventListener(accelerometerSensorValue, lineGraphView);

        accelerometerSensorManager.registerListener(accelerometerListener, AccelerometerSensorEventListener, SensorManager.SENSOR_DELAY_NORMAL);

        // The Magnetic sensor
        SensorManager magneticSensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor MagneticSensorEventListener =
                magneticSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        SensorEventListener magneticListener = new MagneticSensorEventListener(magneticSensorValue);

        magneticSensorManager.registerListener(magneticListener, MagneticSensorEventListener, SensorManager.SENSOR_DELAY_NORMAL);

        //Rotation
        SensorManager rotationSensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor RotationSensorEventListener =
                rotationSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        SensorEventListener rotationListener = new RotationSensorEventListener(rotationSensorValue);

        rotationSensorManager.registerListener(rotationListener, RotationSensorEventListener, SensorManager.SENSOR_DELAY_NORMAL);
    }

}


class LightSensorEventListener implements SensorEventListener {

    TextView lightValueOutput;

    public LightSensorEventListener(TextView outputView) {

        lightValueOutput = outputView;

    }

    public void onAccuracyChanged(Sensor s, int i) {

    }

    public void onSensorChanged(SensorEvent se) {
        if (se.sensor.getType() == Sensor.TYPE_LIGHT) {   // if the sensor sense light.
            float lightSensorValue = se.values[0];
            String outputLightValue = String.format("(%f)", lightSensorValue);  //"%f" controls the decimal places and the output format

            lightValueOutput.setText(outputLightValue);
            lightValueOutput.setTextColor(Color.BLACK);

        }

    }

}

class AccelerometerSensorEventListener implements SensorEventListener {

    TextView AccelerometerValueOutput;
    LineGraphView graph;
    float[] smValue = {0, 0, 0};
    int c = 10;

    public AccelerometerSensorEventListener(TextView outputView, LineGraphView graphView) {

        AccelerometerValueOutput = outputView;
        graph = graphView;

    }

    public void onAccuracyChanged(Sensor s, int i) {

    }

    public void onSensorChanged(SensorEvent se) {
        if (se.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {   // if the sensor sense light.
            float AccelerometerSensorValueX = se.values[0];   // the values from the sensors
            float AccelerometerSensorValueY = se.values[1];
            float AccelerometerSensorValueZ = se.values[2];
            smValue[0] += (se.values[0] - smValue[0])/c;
            smValue[1] += (se.values[1] - smValue[1])/c;
            smValue[2] += (se.values[2] - smValue[2])/c;
            String outputAccelerometerValue = String.format("(%f, %f, %f)", AccelerometerSensorValueX, AccelerometerSensorValueY, AccelerometerSensorValueZ);  //(%f) controls the decimal places
            AccelerometerValueOutput.setText(outputAccelerometerValue);
            AccelerometerValueOutput.setTextColor(Color.BLACK);
            graph.addPoint(smValue);

        }

    }

}

class MagneticSensorEventListener implements SensorEventListener {

    TextView MagneticValueOutput;

    public MagneticSensorEventListener (TextView outputView){

        MagneticValueOutput = outputView;

    }

    public void onAccuracyChanged(Sensor s, int i) {

    }

    public void onSensorChanged(SensorEvent se) {
        if (se.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {   // if the sensor sense light.
            float MagneticSensorValueX = se.values[0];   // the values from the sensors
            float MagneticSensorValueY = se.values[1];
            float MagneticSensorValueZ = se.values[2];
            String outputMagneticValue = String.format("(%f, %f, %f)",MagneticSensorValueX,MagneticSensorValueY,MagneticSensorValueZ);  //(%f) controls the decimal places
            MagneticValueOutput.setText(outputMagneticValue);
            MagneticValueOutput.setTextColor(Color.BLACK);

        }

    }

}

class RotationSensorEventListener implements SensorEventListener {

    TextView RotationValueOutput;

    public RotationSensorEventListener (TextView outputView){

        RotationValueOutput = outputView;

    }

    public void onAccuracyChanged(Sensor s, int i) {

    }

    public void onSensorChanged(SensorEvent se) {
        if (se.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            float RotationSensorValueX = se.values[0];
            float RotationSensorValueY = se.values[1];
            float RotationSensorValueZ = se.values[2];
            String outputRotationValue = String.format("(%f, %f, %f)",RotationSensorValueX,RotationSensorValueY,RotationSensorValueZ);  //(%f) controls the decimal places
            RotationValueOutput.setText(outputRotationValue);
            RotationValueOutput.setTextColor(Color.BLACK);

        }

    }

}