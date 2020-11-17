package com.Fuel.fuelservice.Api;

import com.Fuel.fuelservice.Objects.FuelStations;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;


public interface FuelStaionApi {

    @FormUrlEncoded
    @POST("auth/create")
    public Call<ResponseBody> createUser(@Field("email") String email,
                                         @Field("uid") String username,
                                         @Field("pwd") String password);


    @GET("auth/login")
    public Call<ResponseBody> userLogin(@Query("uid") String username,
                                        @Query("pwd") String password);

    @GET("service/stations")
    public Call<List<FuelStations>> getAllStations();

    @GET("auth/currentuser")
    public Call<ResponseBody> getCurrentUser(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("service/setFavorite")
    public Call<ResponseBody> setFavorite(@Header("Authorization") String token,
                                          @Field("FuelStationId") String id);

    @GET("service/favoriteStations")
    public Call<List<FuelStations>> getAllFavoritedStations(@Header("Authorization") String token);


}


