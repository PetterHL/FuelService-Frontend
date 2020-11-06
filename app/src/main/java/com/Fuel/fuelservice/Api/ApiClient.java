package com.Fuel.fuelservice.Api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //URL AT SCHOOL
    //private static final String BASE_URL = "http://10.22.193.141:8080/FuelService/api/";
    //URL AT Petter HOME
    //private static final String BASE_URL = "http://10.22.193.141:8080/FuelService/api/";
    //URL AT Daniel HOME
    private static final String BASE_URL = "http://192.168.1.3:8080/FuelService/api/";

    private static final String CAR_URL = "http://no.registreringsnummerapi.com/api/reg.asmx/";

    private static ApiClient SINGLETON;
    Retrofit retrofit = null;


    public ApiClient(boolean car) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        if(car) {
            System.out.println("2222222222222222222222222222222222222222222222222222222222222222222222222222222");
            retrofit = new Retrofit.Builder()
                    .baseUrl(CAR_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        } else {
            System.out.println("3333333333333333333333333333333333333333333333333333333333333333333333333333333333");
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
    }

    public static synchronized ApiClient getSINGLETON(boolean car) {
        SINGLETON = null;

        SINGLETON = new ApiClient(car);

        return SINGLETON;
    }

    // Connects to the FantApi.
    public FuelStaionApi getApi() {
        return retrofit.create(FuelStaionApi.class);
    }
}
