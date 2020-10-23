package com.gonzmor.fiserv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.client.HttpResponseException;
import cz.msebera.android.httpclient.entity.mime.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    ListView mylist ;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter adapter;
    TextView btn ;
    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn = findViewById(R.id.data);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //create instance
                //Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                RestClient cliente = new RestClient(getApplicationContext());

                try{

                    //Build JSON
                    JSONObject saleTransaction = new JSONObject();
                    saleTransaction.put("requestType","PaymentCardSaleTransaction");
                    saleTransaction.put("transactionAmount",
                            (new JSONObject())
                                    .put("total","1.00")
                                    .put("currency","MXN")
                    );
                    saleTransaction.put("paymentMethod",
                            (new JSONObject()).put("paymentCard",
                                    (new JSONObject())
                                            .put("number","493136000476724")
                                            .put("securityCode","123")
                                            .put("expiryDate",
                                                    (new JSONObject())
                                                            .put("month","12")
                                                            .put("year","25")
                                            )
                            )
                    );
                    //send POST HTTP REQUEST
                    cliente.post("payments", saleTransaction, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                            super.onSuccess(statusCode, (cz.msebera.android.httpclient.Header[]) headers, response);
                            //handle success when response is a single object
                            btn.setText(response.toString());

                            Log.d("SuccessObj", response.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, (cz.msebera.android.httpclient.Header[]) headers, throwable, errorResponse);
                            //handle error when response is an array of objects
                            btn.setText(errorResponse.toString());

                        }
                    });
                    //Toast.makeText(MainActivity.this, "sssss", Toast.LENGTH_SHORT).show();
                }catch (HttpResponseException ex){
                    Toast.makeText(MainActivity.this, "uno", Toast.LENGTH_SHORT).show();
                    Log.d("HttpResponseException", ex.getMessage());
                }
                catch (JSONException ex){
                    Toast.makeText(MainActivity.this, "dos", Toast.LENGTH_SHORT).show();
                    Log.d("JSONException", ex.getMessage());
                }
                catch (Exception ex){
                    Toast.makeText(MainActivity.this, "tres", Toast.LENGTH_SHORT).show();
                    Log.d("Exception", ex.getMessage());
                }

            }
        });
    }
    private void getPost(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        fiservInterface fi = retrofit.create(fiservInterface.class);

        Call<List<POST>> call = fi.getPost();

        call.enqueue(new Callback<List<POST>>() {
            @Override
            public void onResponse(Call<List<POST>> call, Response<List<POST>> response) {
                for (POST post: response.body() ){
                    list.add(post.getTitle());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<POST>> call, Throwable t) {

            }
        });
    }


}