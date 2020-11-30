package com.Fuel.fuelservice.Api;

import com.Fuel.fuelservice.Objects.Car;
import com.Fuel.fuelservice.Objects.FuelStations;
import com.google.android.gms.common.util.JsonUtils;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
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

    @GET("service/cars")
    public Call<List<Car>> getAllCars();

    @GET("service/priceChange")
    public Call<ResponseBody> getPriceChange();

    @GET("service/getOwnerCar")
    public Call<List<Car>> getOwnerCars(@Header("Authorization") String Token);

    @FormUrlEncoded
    @POST("service/addCar")
    public Call<ResponseBody> addCar(@Header("Authorization") String Token,
                                     @Field("RegNumber") String RegNumber,
                                     @Field("manufacturer") String manufacturer,
                                     @Field("model") String model,
                                     @Field("petrol") boolean petrol);

    @GET("CheckNorway")
    public Call<ResponseBody> getCar(@Query("RegistrationNumber") String RegistrationNumber,
                                     @Query("username") String username);
    @GET("auth/currentuser")
    public Call<ResponseBody> getCurrentUser(@Header("Authorization") String token);


    @PUT("service/setFavorite")
    public Call<ResponseBody> setFavorite(@Header("Authorization") String token,
                                          @Query("FuelStationId") String id);
    @GET("service/removeFavorite")
    public Call<ResponseBody> removeFavorite(@Header("Authorization") String token,
                                          @Query("fuelStationId") String id);

    @GET("service/favoriteStations")
    public Call<List<FuelStations>> getAllFavoritedStations(@Header("Authorization") String token);

    @GET("service/cheapestStations")
    public Call<List<FuelStations>> getCheapestStations();


}


