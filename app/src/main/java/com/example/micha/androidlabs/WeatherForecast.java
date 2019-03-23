package com.example.micha.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    ImageView weatherImage;
    TextView curTemp, lowOf, highOf, uvRating;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        weatherImage = findViewById(R.id.weatherImage);
        curTemp = findViewById(R.id.curTemp);
        lowOf = findViewById(R.id.lowOf);
        highOf = findViewById(R.id.highOf);
        uvRating = findViewById(R.id.uvRating);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        ForecastQuery fQuery = new ForecastQuery();
        fQuery.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric",
                "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
    }


    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        String windSpeed;
        String min;
        String max;
        String currentTemp;
        String uv;
        Bitmap wImage;
        String weatherIcon = "weatherIcon";

        protected String doInBackground(String... params) {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(params[0]).openConnection();
                InputStream stream = conn.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                XmlPullParser parser = factory.newPullParser();
                parser.setInput(stream, "UTF-8");

                /* XML Part */
                while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() == XmlPullParser.START_TAG) {
                        String name = parser.getName();
                        if (name.equals("temperature")) {
                            this.currentTemp = parser.getAttributeValue(null, "value");
                            this.max = parser.getAttributeValue(null, "max");
                            this.min = parser.getAttributeValue(null, "min");
                            publishProgress(25);
                        } if (name.equals("speed")) {
                            this.windSpeed = parser.getAttributeValue(null, "value");
                            publishProgress(50);
                        } if (name.equals("weather")) {
                            FileInputStream fis = null;
                            try {
                                String fileName = parser.getAttributeValue(null, "icon");

                                if (fileExistance(fileName)) {
                                    fis = openFileInput(fileName);
                                    wImage = BitmapFactory.decodeStream(fis);
                                    Log.i("No need to download", "Found image locally");
                                } else {
                                    HttpURLConnection connection = (HttpURLConnection) new URL("http://openweathermap.org/img/w/" + fileName + ".png").openConnection();
                                    connection.connect();

                                    wImage = BitmapFactory.decodeStream(connection.getInputStream());

                                    FileOutputStream outputStream = openFileOutput(fileName + ".png", Context.MODE_PRIVATE);
                                    wImage.compress(Bitmap.CompressFormat.PNG, 320, outputStream);
                                    outputStream.flush();
                                    outputStream.close();

                                    publishProgress(75);
                                    Log.i("Download necessary", "Image not locally found");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Issue getting image", e.getMessage());
                            }
                        }
                    }
                    parser.next();
                }


            /* JSON PART */
            URL url = new URL(params[1]);
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            String result = sb.toString();

            JSONObject jObject = new JSONObject(result);
            this.uv = "" + jObject.getLong("value");
            publishProgress(100);

            conn.getInputStream().close();
        } catch(Exception e) {
            Log.e("It do broke : ", e.getMessage());
        }

        return"Something";
    }

        public boolean fileExistance(String fName) {
            File file = getBaseContext().getFileStreamPath(fName);
            return file.exists();
        }

        @Override
        protected void onProgressUpdate(Integer ...value) {
            super.onProgressUpdate(value[0]);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
            weatherImage.setImageBitmap(wImage);
            curTemp.setText("Current Temperature: " + currentTemp);
            lowOf.setText("Low of: " + min);
            highOf.setText("High of: " + min);
            uvRating.setText("UV Index: " + uv);
        }
    }
}