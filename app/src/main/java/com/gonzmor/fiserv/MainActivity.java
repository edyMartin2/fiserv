package com.gonzmor.fiserv;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.client.HttpResponseException;


public class MainActivity extends AppCompatActivity {
    TextView btn ;
    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn = findViewById(R.id.textView);
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


}