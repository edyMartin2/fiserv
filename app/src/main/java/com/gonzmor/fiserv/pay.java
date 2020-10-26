package com.gonzmor.fiserv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gonzmor.fiserv.fiservAPPI.*;
import com.gonzmor.fiserv.databaseAll.*;
import com.google.android.material.button.MaterialButton;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.client.HttpResponseException;

public class pay extends AppCompatActivity {
    String price, payment ;
    String [] sqlData ;
    private Spinner sp ;
    private TextView charge;
    private MaterialButton btnPay;
    private controllerWallet db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        //database peticion
        db = new controllerWallet(this);


        price = getIntent().getStringExtra("Price");
        payment = getIntent().getStringExtra("payment");
        sp = findViewById(R.id.spiner);
        sp.setAdapter(llenarCards());
        btnPay = findViewById(R.id.btnPay);
        charge = findViewById(R.id.charge);
        charge.setText("$ " +price );


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create instance
                //Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                String wallet = sp.getSelectedItem().toString();
                String data = db.getDataWalletSelected(wallet);
                String[] datas = data.split(";");
                String[] fecha = datas[2].split("/");
                RestClient cliente = new RestClient(getApplicationContext());

                try{

                    //Build JSON
                    JSONObject saleTransaction = new JSONObject();
                    saleTransaction.put("requestType","PaymentCardSaleTransaction");
                    saleTransaction.put("transactionAmount",
                            (new JSONObject())
                                    .put("total",price)
                                    .put("currency","MXN")
                    );
                    saleTransaction.put("paymentMethod",
                            (new JSONObject()).put("paymentCard",
                                    (new JSONObject())
                                            .put("number",datas[0])
                                            .put("securityCode",datas[1])
                                            .put("expiryDate",
                                                    (new JSONObject())
                                                            .put("month",fecha[0])
                                                            .put("year",fecha[1])
                                            )
                            )
                    );
                    //send POST HTTP REQUEST
                    cliente.post("payments", saleTransaction, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                            super.onSuccess(statusCode, (cz.msebera.android.httpclient.Header[]) headers, response);
                            //handle success when response is a single object
                            Toast.makeText(pay.this, "Listo", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, (cz.msebera.android.httpclient.Header[]) headers, throwable, errorResponse);
                            Toast.makeText(pay.this, "Error de pago", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //Toast.makeText(MainActivity.this, "sssss", Toast.LENGTH_SHORT).show();
                }catch (HttpResponseException ex){
                    Toast.makeText(pay.this, "uno", Toast.LENGTH_SHORT).show();
                    Log.d("HttpResponseException", ex.getMessage());
                }
                catch (JSONException ex){
                    Toast.makeText(pay.this,"dos", Toast.LENGTH_SHORT).show();
                    Log.d("JSONException", ex.getMessage());
                }
                catch (Exception ex){
                    Toast.makeText(pay.this, "tres", Toast.LENGTH_SHORT).show();
                    Log.d("Exception", ex.getMessage());
                }

            }
        });



    }

    public ArrayAdapter<String> llenarCards (){
        ArrayList<String> cards = new ArrayList<>();

        controllerWallet controller = new controllerWallet(this);
        cards = controller.getCards();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cards);
        if (cards != null ){
            return  adapter;
        } else {
            return null;
        }
    }
}