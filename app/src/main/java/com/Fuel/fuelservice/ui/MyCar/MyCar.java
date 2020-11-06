package com.Fuel.fuelservice.ui.MyCar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.Fuel.fuelservice.Api.ApiClient;
import com.Fuel.fuelservice.Objects.User;
import com.Fuel.fuelservice.R;
import com.Fuel.fuelservice.ui.home.FuelStationFragment;

import org.w3c.dom.Document;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCar extends Fragment {

    EditText editTextUsername, editTextPwd;
    private User user = new User();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car, container, false);

        editTextUsername = view.findViewById(R.id.editTextUsernameOnLogin);
        editTextPwd = view.findViewById(R.id.editTextTextPassword);





        Button loginBtn = (Button) view.findViewById(R.id.loginbtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.loginbtn:
                        sendRequest();
                        break;
                }
            }
        });

        return view;
    }

    public void sendRequest() {
        Call<ResponseBody> call = ApiClient
                .getSINGLETON(true)
                .getApi()
                .getCar("Zt49510", "Hei123");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    System.out.println(response.body());

                    Fragment newFragment = new FuelStationFragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();


                    Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_LONG).show();
                    fragmentTransaction.replace(R.id.fragment_contatiner, newFragment).commit();


                } else {
                    Toast.makeText(getActivity(), "Login Failed, please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }

        });
    }
}