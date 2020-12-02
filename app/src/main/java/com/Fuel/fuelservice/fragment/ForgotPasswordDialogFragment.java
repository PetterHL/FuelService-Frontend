package com.Fuel.fuelservice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.Fuel.fuelservice.Api.ApiClient;
import com.Fuel.fuelservice.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordDialogFragment extends DialogFragment {
    Button button;
    EditText uidText;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.forgotpassord_dialog_fragment, container, false);
        button = view.findViewById(R.id.forgotPasswordButton);
        uidText = view.findViewById(R.id.ETEmailAddress);

        String uidTextString = uidText.getText().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = ApiClient
                        .getSINGLETON(false)
                        .getApi()
                        .resetPassword(uidTextString);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                assert response.body() != null;
                                response.body().string();
                                System.out.println(response);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }


                });
            }
        });

        return view;
    }


}
