package com.example.a911.sity_sign30;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.DropBoxManager;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.sin;

public class MainActivity extends AppCompatActivity {

    // distance of 1 meters in lg and lt
    // public static final double LT_DISTANCE = 0.000009;
    // public static final double LG_DISTANCE = cos(LT_DISTANCE)* 0.111111;

    private double latitude;
    private double longitude;
    private double angle;
    //private int metersY;
    //private int metersX;


    //getters
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAngle() {
        return angle;
    }

    // setters
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    // window elements:

        // for GPS
    private Button button;
    private TextView navigation;
    private LocationManager locationManager;
    private LocationListener locationListener;

        // for sensors
    TextView Pcoordstext;
    TextView tvText;
    TextView northText;
    SensorManager sensorManager;
    Sensor sensorAccel;
    Sensor sensorMagnet;

    StringBuilder sb = new StringBuilder();
    StringBuilder sb1 = new StringBuilder();

    Timer timer;
    int rotation;

        // for function
   // private Button performB;
    private TextView building_tv;
    private ImageView buildingImg;

        // testing arrays
    double[] Blatitude = {49.787942, 49.789036, 49.788638, 49.788292, 49.789778}; // back, front, left, right, vopak
    double[] Blongitude = {24.019340, 24.019796, 24.017456, 24.021807, 24.017719};
    final public int N = 5;

        // precising arrays
    int Pcounter = 100;
    double[] Platitude = new double[Pcounter];



    // constants
    final public String nextLt = "next Latitude: ";
    final public String nextLg = "\nnext Longitude: ";
    final public String NORTH = "\nNORTH!!!";
    public String NAVIGATION;
    public String BUILDING;
    public String nextP = nextLt + getLatitude() + nextLg + getLongitude();
    public final double DIFFERENCE = 0.0003;
    public String angleStr = ""+ getAngle();
    final public String WAITING = "still waiting for the GPS connection";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // window elements initialization

            // for GPS
        button = (Button) findViewById(R.id.button);
        navigation = (TextView) findViewById(R.id.nanvigation);

            // for sensors
        tvText = (TextView) findViewById(R.id.tvText);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

            // for function
       // performB = (Button) findViewById(R.id.performB);
        building_tv = (TextView) findViewById(R.id.building_tv);
        buildingImg = (ImageView) findViewById(R.id.building_img);

            //for GPS   //дослідження залежності кількості нулів після коми від величини дільника
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                /** set current lat and lg of the device*/

                setLongitude(location.getLongitude() );
                setLatitude(location.getLatitude() - DIFFERENCE );

                NAVIGATION = "\nLatitude: " + getLatitude()+ "\nLongitude: "+ getLongitude();
                navigation.setText(NAVIGATION);



                /*
                // precise stuff
                if (Pcounter > 0)
                {
                    Platitude[Pcounter-1] = getLatitude();
                    Pcounter--;
                }
                else
                {
                    Presize presize = new Presize();
                    setLatitude(presize.presize(Platitude));
                    nextP = nextLt + getLatitude() + nextLg + getLongitude();
                    Pcoordstext.setText(nextP);

                }
                */



                //navigation.setText(nextLt+ getLatitude()+ nextLg+ getLongitude());
                // setMetersY( (int)((coordFinding(location.getLatitude(),location.getLongitude(),90)[0] - location.getLatitude()) / (LT_DISTANCE) ));
                // setMetersX( (int)((coordFinding(location.getLatitude(),location.getLongitude(),90)[1] - location.getLongitude()) / (LG_DISTANCE) ));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
                return;
            }
            else
            {
                // asking GPS permission
                configureButton();

                    // for finding function
               // performButton();
            }
        }


    }
                    // for GPS
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case 10 :
                if (grantResults.length>0&& grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    configureButton();
                }
                break;
        }

    }


    private void configureButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
               for (int i =0; i<100; i++)
               {
                   locationManager.requestLocationUpdates("gps", 5000, 100, locationListener);
               }

                Find find = new Find();


                //BUILDING = "\nIndex: "+ find.coordFinding(Blatitude,Blongitude,getLatitude(),getLongitude(),getAngle(),N);
                if(getLatitude() != 0 && getLongitude() != 0)
                {
                    buildingImg = testBuildImg( find.coordFinding(Blatitude,Blongitude,getLatitude(),getLongitude(),getAngle(),N), buildingImg);
                    building_tv.setText(testBuildStr(find.coordFinding(Blatitude,Blongitude,getLatitude(),getLongitude(),getAngle(),N)));

                }
                else
                    {
                        building_tv.setText(WAITING);
                    }

            }
        });

    }

                // for Orientation Sensors
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener, sensorMagnet, SensorManager.SENSOR_DELAY_NORMAL);

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getDeviceOrientation();
                        getActualDeviceOrientation();
                        showInfo();
                    }
                });
            }
        };
        timer.schedule(task, 0, 2000);

        WindowManager windowManager = ((WindowManager) getSystemService(Context.WINDOW_SERVICE));
        Display display = windowManager.getDefaultDisplay();
        rotation = display.getRotation();

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);
        timer.cancel();
    }

    String format(float values[]) {
        return String.format("%1$.1f\t\t%2$.1f\t\t%3$.1f", values[0], values[1], values[2]);
    }

    void showInfo() {

       // sb.setLength(0);
       // sb.append("Orientation : " + format(valuesResult))
       //         .append("\nOrientation 2: " + format(valuesResult2))
       // ;

        /* comment this north
        if(valuesResult[0] >= -5.0 && valuesResult[0] <= 5.0)
        {
            getNorth();
            sb1.append("NORTH !!!");
            northText.setText(sb1);
        }

        */

        setAngle(valuesResult[0]);

        angleStr = ""+getAngle();
        tvText.setText(angleStr);

    }

    float[] r = new float[9];

    void getDeviceOrientation() {
        SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet);
        SensorManager.getOrientation(r, valuesResult);

        valuesResult[0] = (float) Math.toDegrees(valuesResult[0]);
        valuesResult[1] = (float) Math.toDegrees(valuesResult[1]);
        valuesResult[2] = (float) Math.toDegrees(valuesResult[2]);
        return;
    }

    float[] inR = new float[9];
    float[] outR = new float[9];

    void getActualDeviceOrientation() {
        SensorManager.getRotationMatrix(inR, null, valuesAccel, valuesMagnet);
        int x_axis = SensorManager.AXIS_X;
        int y_axis = SensorManager.AXIS_Y;
        switch (rotation) {
            case (Surface.ROTATION_0):
                break;
            case (Surface.ROTATION_90):
                x_axis = SensorManager.AXIS_Y;
                y_axis = SensorManager.AXIS_MINUS_X;
                break;
            case (Surface.ROTATION_180):
                y_axis = SensorManager.AXIS_MINUS_Y;
                break;
            case (Surface.ROTATION_270):
                x_axis = SensorManager.AXIS_MINUS_Y;
                y_axis = SensorManager.AXIS_X;
                break;
            default:
                break;
        }
        SensorManager.remapCoordinateSystem(inR, x_axis, y_axis, outR);
        SensorManager.getOrientation(outR, valuesResult2);
        valuesResult2[0] = (float) Math.toDegrees(valuesResult2[0]);
        valuesResult2[1] = (float) Math.toDegrees(valuesResult2[1]);
        valuesResult2[2] = (float) Math.toDegrees(valuesResult2[2]);
        return;
    }

    float[] valuesAccel = new float[3];
    float[] valuesMagnet = new float[3];
    float[] valuesResult = new float[3];
    float[] valuesResult2 = new float[3];


    SensorEventListener listener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    for (int i = 0; i < 3; i++) {
                        valuesAccel[i] = event.values[i];
                    }
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    for (int i = 0; i < 3; i++) {
                        valuesMagnet[i] = event.values[i];
                    }
                    break;
            }
        }


    };

    void getNorth(){
        if(valuesResult[0] >= -5.0 && valuesResult[0] <= 5.0)
        {
            northText.setText(NORTH);

        }

    }


    private String testBuildStr(int index)
    {
        String returnStr;
        switch (index)
        {
            case 0:
                returnStr = "103 Будинок";
                break;
            case 1:
                returnStr = "Будинок за зеленою лавкою";
                break;
            case 2:
                returnStr = "coffee mate danger zone";
                break;
            case 3:
                returnStr = "95 будинок";
                break;
            case 4:
                returnStr = "Вопак";
                break;
            default:
                returnStr = "Будинок відсутній в базі даних";
                break;
        }
        return returnStr;
    }

    private ImageView testBuildImg(int index, ImageView buildingImg)
    {

        switch (index)
        {
            case 0:
                buildingImg.setImageDrawable(getResources().getDrawable(R.drawable.zero));
                break;
            case 1:
                buildingImg.setImageDrawable(getResources().getDrawable(R.drawable.one));
                break;
            case 2:
                buildingImg.setImageDrawable(getResources().getDrawable(R.drawable.two));
                break;
            case 3:
                buildingImg.setImageDrawable(getResources().getDrawable(R.drawable.three));
                break;
            case 4:
                buildingImg.setImageDrawable(getResources().getDrawable(R.drawable.four));
                break;
            default:
                buildingImg.setImageDrawable(getResources().getDrawable(R.drawable.clouded_lviv));
                break;
        }
        return buildingImg;
    }

   // private void performButton()
   // {
   //     performB.setOnClickListener(new View.OnClickListener() {
   //         @Override
   //         public void onClick(View v) {
   //            Find find = new Find();
    //
   //            BUILDING = "\nIndex: "+ find.coordFinding(Blatitude,Blongitude,getLatitude(),getLongitude(),getAngle(),N);
   //            building_tv.setText(BUILDING);
   //         }
   //     });
   // }
}