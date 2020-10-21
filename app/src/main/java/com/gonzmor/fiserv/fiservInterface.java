package com.gonzmor.fiserv;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;



public interface fiservInterface {
    String API_URL = "/posts";
    @GET(API_URL)
    Call<List<POST>> getPost();
}
