package com.gonzmor.fiserv;
import android.content.Context;
import android.util.Base64;

import androidx.annotation.Nullable;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import cz.msebera.android.httpclient.entity.StringEntity;



public class RestClient {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();//used for hmac sha256
    private static final String BASE_URL = "https://cert.api.firstdata.com/gateway/v2/";
    private static final String API_KEY = "5JMsYGxArIPT8Ge2WRu7vGpVz5O95ONU";
    private static final String API_SECRET = "pTJXRhUiFwzt8YJa";

    private AsyncHttpClient client;
    private Context appContext;
    public RestClient(Context ctx){
        this.client = new AsyncHttpClient();
        this.appContext = ctx;
    }

    public void get(String url,@Nullable JSONObject data, AsyncHttpResponseHandler responseHandler) throws  Exception{
        String jsonString = data == null?"":data.toString();
        this.setHeaders(jsonString);
        client.get(getAbsoluteUrl(url),null,responseHandler);
    }

    public void post(String url, @Nullable JSONObject data, AsyncHttpResponseHandler responseHandler) throws Exception {
        String jsonString = data==null?"":data.toString();
        this.setHeaders(jsonString);
        StringEntity entity = new StringEntity(jsonString);
        client.post(this.appContext,getAbsoluteUrl(url),jsonString== ""?null:entity,"application/json",responseHandler);
    }

    public void post(String url, @Nullable JSONArray data, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), null, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private void setHeaders(String jsonString) throws Exception{

        client.removeAllHeaders();
        client.addHeader("Content-Type","application/json");
        String msg = API_KEY + UUID.randomUUID() +System.currentTimeMillis() + jsonString;
        client.addHeader("Message-Signature",hash_hmac(msg,API_SECRET));


    }

    private static Map  createMessageSignature(String payload) throws Exception{
        Map map =  new HashMap<>();
        map.put("Client-Request-Id",UUID.randomUUID().toString());
        map.put("Api-Key",API_KEY);
        map.put("Timestamp",System.currentTimeMillis()+"");
        String msg = (String) map.get("Api-Key") + map.get("Client-Request-Id") +map.get("Timestamp") + payload;
        map.put("Message-Signature",hash_hmac(msg,API_SECRET));
        return  map;
    }

    private static String hash_hmac(String str, String secret) throws Exception{
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secretKey);
        byte[] hashRaw = sha256_HMAC.doFinal(str.getBytes());
        String hex = bytesToHex(hashRaw).toLowerCase();
        String aux =Base64.encodeToString(hex.getBytes(),Base64.NO_WRAP);
        return aux;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}