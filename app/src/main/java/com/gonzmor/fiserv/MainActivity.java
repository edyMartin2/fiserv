package com.gonzmor.fiserv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String data = getData();
        TextView x = (TextView) findViewById(R.id.z);
        x.setText(data);

    }

    public String getData(){
        String baseUrl = "https://httpbin.org/get";
        URL url = null;
        String inputLine = "";
        StringBuffer res;
        String json = "";
        JSONArray dataJson = null;
        String dataReq = "ssss";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection connection ;

        try {
            url = new URL(baseUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            BufferedReader read = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            res = new StringBuffer();
            while ((inputLine = read.readLine()) != null){
                res.append(inputLine);
            }

            json = res.toString();

            dataJson = new JSONArray(json);

            for (int i = 0 ; i < dataJson.length(); i++){
                JSONObject obj = dataJson.getJSONObject(i);

                dataReq += "indice"+i+ obj.optString("args") + "\n";
            }
            return  dataReq;
        } catch (MalformedURLException e) {
            return " fgggh" + e.toString();
        } catch (IOException e) {
            return "2" +e.toString();
        } catch (JSONException e) {
            return "hji3" +e.toString();
        }



    }


}